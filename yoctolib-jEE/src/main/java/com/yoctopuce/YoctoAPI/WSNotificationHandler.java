package com.yoctopuce.YoctoAPI;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.*;

@ClientEndpoint
public class WSNotificationHandler extends NotificationHandler implements MessageHandler
{

    public static final int NB_TCP_CHANNEL = 8;
    public static final int HUB_TCP_CHANNEL = 1;
    public static final int DEVICE_TCP_CHANNEL = 0;
    public static final int DEVICE_ASYNC_TCP_CHANNEL = 2;
    public static final int WS_REQUEST_MAX_DURATION = 50000;

    static final int USB_META_UTCTIME = 1;
    static final int USB_META_DLFLUSH = 2;
    static final int USB_META_ACK_D2H_PACKET = 3;
    static final int USB_META_WS_ANNOUNCE = 4;
    static final int USB_META_WS_AUTHENTICATION = 5;
    static final int USB_META_WS_ERROR = 6;

    static final int USB_META_UTCTIME_SIZE = 5;
    static final int USB_META_DLFLUSH_SIZE = 1;
    static final int USB_META_ACK_D2H_PACKET_SIZE = 2;
    static final int USB_META_WS_ANNOUNCE_SIZE = 8 + YAPI.YOCTO_SERIAL_LEN;
    static final int USB_META_WS_AUTHENTICATION_SIZE = 28;
    static final int USB_META_WS_ERROR_SIZE = 6;


    private static final int USB_META_WS_PROTO_V1 = 1;
    private static final int VERSION_SUPPORT_ASYNC_CLOSE = 1;


    private static final int USB_META_WS_VALID_SHA1 = 1;
    private static final int USB_META_WS_RW = 2;


    private final ExecutorService _executorService;
    private final boolean _isHttpCallback;
    private MessageDigest _sha1 = null;
    private MessageDigest _md5 = null;
    private Session _session;


    private final BlockingQueue<WSRequest> _outRequest = new LinkedBlockingQueue<>();

    private final ArrayList<Queue<WSRequest>> _workingRequests;
    private final Object _stateLock = new Object();
    private ConnectionState _connectionState = ConnectionState.CONNECTING;
    private volatile boolean _firstNotif;
    private String _serial;
    private long _remoteNouce;
    private int _nounce;
    private volatile boolean _muststop;
    private volatile String _session_error;
    private volatile int _session_errno;
    private boolean _rwAccess = false;
    private int _remoteVersion = 0;
    private byte _nextAsyncId = 48;


    private enum ConnectionState
    {
        DEAD, DISCONNECTED, CONNECTING, AUTHENTICATING, CONNECTED
    }

    public WSNotificationHandler(YHTTPHub hub, Object session)
    {
        super(hub);
        _session = (Session) session;
        _isHttpCallback = session != null;
        if (_isHttpCallback) {
            // server mode
            Whole<ByteBuffer> messageHandler = new Whole<ByteBuffer>()
            {

                @Override
                public void onMessage(ByteBuffer byteBuffer)
                {
                    parseBinaryMessage(byteBuffer);
                }
            };
            _session.addMessageHandler(messageHandler);
        }
        _workingRequests = new ArrayList<>(NB_TCP_CHANNEL);

        for (int i = 0; i < NB_TCP_CHANNEL; i++) {
            _workingRequests.add(i, new LinkedList<WSRequest>());
        }
        _executorService = Executors.newFixedThreadPool(1);
        _muststop = false;

        try {
            _sha1 = MessageDigest.getInstance("SHA-1");
            _md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run()
    {
        _firstNotif = true;
        if (_isHttpCallback) {
            // send go to the VirtualHub
            //sendAnnounceMeta();
            runOnSession();
        } else {
            // client mode
            WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
            String url = "ws://" + _hub._http_params.getUrl(false, false) + "/not.byn";
            URI uri;
            try {
                uri = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }
            while (!Thread.currentThread().isInterrupted() && !_muststop) {
                if (_error_delay > 0) {
                    try {
                        Thread.sleep(_error_delay);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                synchronized (_stateLock) {
                    _connectionState = ConnectionState.CONNECTING;
                }
                try {
                    _session = webSocketContainer.connectToServer(this, uri);
                    runOnSession();
                } catch (DeploymentException | IOException e) {
                    e.printStackTrace();
                }
                _firstNotif = true;
                _notifRetryCount++;
                _hub._devListValidity = 500;
                _error_delay = 100 << (_notifRetryCount > 4 ? 4 : _notifRetryCount);
            }
        }
        try {
            _session.close();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        synchronized (_stateLock) {
            _connectionState = ConnectionState.DEAD;
            if (_session_errno == 0) {
                _session_errno = YAPI.IO_ERROR;
                _session_error = "WS Session is closed";
            }
        }
    }

    private void runOnSession()
    {
        if (!_session.isOpen()) {
            _hub._yctx._Log("WebSocket is not open");
            return;
        }

        RemoteEndpoint.Basic basicRemote = _session.getBasicRemote();
        try {
            long timeout = System.currentTimeMillis() + 10000;
            synchronized (_stateLock) {
                while (_connectionState == ConnectionState.CONNECTING && !_muststop) {
                    _stateLock.wait(1000);
                    if (timeout < System.currentTimeMillis()) {
                        _hub._yctx._Log("YoctoHub did not send any data for 10 secs\n");
                        _connectionState = ConnectionState.DISCONNECTED;
                        _stateLock.notifyAll();
                        return;
                    }
                }
            }

            while (!Thread.currentThread().isInterrupted() && !_muststop) {
                WSRequest request = _outRequest.take();
                if (request.getErrorCode() != YAPI.SUCCESS) {
                    // fake request to unlock thread
                    break;
                }
                int tcp_channel = request.getChannel();
                if (request.getState() == WSRequest.State.OPEN) {
                    synchronized (_workingRequests) {
                        _workingRequests.get(tcp_channel).offer(request);
                    }
                    ByteBuffer requestBB = request.getRequestBytes();
                    while (requestBB.hasRemaining()) {
                        int size = requestBB.remaining();
                        int type;
                        WSStream stream;
                        if (size > WSStream.MAX_DATA_LEN) {
                            size = WSStream.MAX_DATA_LEN;
                            stream = new WSStream(YGenericHub.YSTREAM_TCP, tcp_channel, size, requestBB);
                            basicRemote.sendBinary(stream.getContent(), true);
                        } else {
                            if (request.isAsync() && _remoteVersion >= VERSION_SUPPORT_ASYNC_CLOSE) {
                                if (size == WSStream.MAX_DATA_LEN) {
                                    stream = new WSStream(YGenericHub.YSTREAM_TCP, tcp_channel, size, requestBB);
                                    basicRemote.sendBinary(stream.getContent(), true);
                                    size = 0;
                                }
                                type = YGenericHub.YSTREAM_TCP_ASYNCCLOSE;
                                stream = new WSStream(type, tcp_channel, size, requestBB, request.getAsyncId());
                            } else {
                                stream = new WSStream(YGenericHub.YSTREAM_TCP, tcp_channel, size, requestBB);
                            }
                            basicRemote.sendBinary(stream.getContent(), true);
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException ex) {
            // put all request in error
            synchronized (_workingRequests) {
                for (int i = 0; i < NB_TCP_CHANNEL; i++) {
                    for (WSRequest request : _workingRequests.get(i)) {
                        request.setError(YAPI.IO_ERROR, ex.getLocalizedMessage(), ex);
                    }
                    _workingRequests.get(i).clear();
                }
            }
        }
        synchronized (_stateLock) {
            _connectionState = ConnectionState.DISCONNECTED;
            _stateLock.notifyAll();
        }

    }

    private WSRequest sendRequest(String req_first_line, byte[] req_head_and_body, int tcpchanel, boolean async) throws YAPI_Exception, InterruptedException
    {
        WSRequest request;
        byte[] full_request;
        byte[] req_first_lineBytes;
        if (req_head_and_body == null) {
            req_first_line += "\r\n\r\n";
            req_first_lineBytes = req_first_line.getBytes();
            full_request = req_first_lineBytes;
        } else {
            req_first_line += "\r\n";
            req_first_lineBytes = req_first_line.getBytes();
            full_request = new byte[req_first_lineBytes.length + req_head_and_body.length];
            System.arraycopy(req_first_lineBytes, 0, full_request, 0, req_first_lineBytes.length);
            System.arraycopy(req_head_and_body, 0, full_request, req_first_lineBytes.length, req_head_and_body.length);
        }

        long timeout = System.currentTimeMillis() + WS_REQUEST_MAX_DURATION;
        synchronized (_stateLock) {
            while ((_connectionState != ConnectionState.CONNECTED && _connectionState != ConnectionState.DEAD)) {
                _stateLock.wait(1000);
                if (timeout < System.currentTimeMillis()) {
                    if (_connectionState != ConnectionState.CONNECTED && _connectionState != ConnectionState.CONNECTING) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "IO error with hub");
                    } else {
                        throw new YAPI_Exception(YAPI.TIMEOUT, "Last request did not finished correctly");
                    }
                }
            }
            if (_connectionState == ConnectionState.DEAD) {
                throw new YAPI_Exception(_session_errno, _session_error);
            }
            if (async) {
                request = new WSRequest(tcpchanel, _nextAsyncId++, full_request);
                if (_nextAsyncId >= 127) {
                    _nextAsyncId = 48;
                }
            } else {
                request = new WSRequest(tcpchanel, full_request);
            }
        }
        _outRequest.put(request);
        return request;
    }

    private byte[] getRequestResponse(WSRequest wsRequest, int mstimeout) throws YAPI_Exception, InterruptedException
    {
        WSRequest.State state = wsRequest.waitProcessingEnd(mstimeout);
        if (!state.equals(WSRequest.State.CLOSED)) {
            wsRequest.checkError();
            throw new YAPI_Exception(YAPI.TIMEOUT, "Last request did not finished correctly");

        }
        byte[] full_result = wsRequest.getResponseBytes();
        int okpos = YAPIContext._find_in_bytes(full_result, "OK".getBytes());
        if (okpos != 0) {
            okpos = YAPIContext._find_in_bytes(full_result, "HTTP/1.1 ".getBytes());
            int endl = YAPIContext._find_in_bytes(full_result, "\r\n".getBytes());
            if (okpos == 0 && endl > 8) {
                String line = new String(full_result, 9, endl - 9);
                String[] parts = line.trim().split(" ");
                if (parts[0].equals("401")) {
                    throw new YAPI_Exception(YAPI.UNAUTHORIZED, "Authentication required");
                }
                throw new YAPI_Exception(line.trim());
            }
        }
        int hpos = YAPIContext._find_in_bytes(full_result, "\r\n\r\n".getBytes());
        if (hpos >= 0) {
            return Arrays.copyOfRange(full_result, hpos + 4, full_result.length);
        }
        return full_result;
    }

    @Override
    public byte[] hubRequestSync(String req_first_line, byte[] req_head_and_body, int mstimeout) throws
            YAPI_Exception, InterruptedException
    {
        WSRequest wsRequest = sendRequest(req_first_line, req_head_and_body, HUB_TCP_CHANNEL, false);
        return getRequestResponse(wsRequest, mstimeout);
    }


    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, int mstimeout) throws
            YAPI_Exception, InterruptedException
    {
        WSRequest wsRequest = sendRequest(req_first_line, req_head_and_body, DEVICE_TCP_CHANNEL, false);
        return getRequestResponse(wsRequest, mstimeout);
    }

    @Override
    void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body,
                         final YGenericHub.RequestAsyncResult asyncResult, final Object asyncContext) throws
            YAPI_Exception, InterruptedException
    {
        final WSRequest wsRequest = sendRequest(req_first_line, req_head_and_body, DEVICE_TCP_CHANNEL, true);
        _executorService.execute(new Runnable()
        {
            @Override
            public void run()
            {
                byte[] response = null;
                int error_code = YAPI.SUCCESS;
                String errmsg = null;
                try {
                    response = getRequestResponse(wsRequest, YHTTPHub.YIO_DEFAULT_TCP_TIMEOUT);
                } catch (YAPI_Exception e) {
                    error_code = e.errorType;
                    errmsg = e.getLocalizedMessage();
                } catch (InterruptedException e) {
                    error_code = YAPI.IO_ERROR;
                    errmsg = e.getLocalizedMessage();
                }
                if (asyncResult != null) {
                    asyncResult.RequestAsyncDone(asyncContext, response, error_code, errmsg);
                }
            }
        });
    }

    @Override
    boolean waitAndFreeAsyncTasks(long timeout) throws InterruptedException
    {
        _muststop = true;
        try {
            _session.close();
        } catch (IOException | IllegalStateException e) {
            _hub._yctx._Log("error on ws close : " + e.getMessage());
            e.printStackTrace();
        }
        _executorService.shutdown();
        return !_executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isConnected()
    {
        synchronized (_stateLock) {
            return _connectionState == ConnectionState.CONNECTED ||
                    _connectionState == ConnectionState.AUTHENTICATING ||
                    _connectionState == ConnectionState.CONNECTING;
        }
    }

    @Override
    public boolean hasRwAccess()
    {
        return _rwAccess;
    }


    @OnOpen
    public void onOpen(Session session)
    {
        _session = session;
    }


    @OnMessage
    public void onMessage(ByteBuffer raw_data, Session session)
    {

        if (_session != session) {
            return;
        }
        parseBinaryMessage(raw_data);
    }

    private void parseBinaryMessage(ByteBuffer raw_data)
    {
        WSStream stream;
        WSRequest workingRequest;

        raw_data.order(ByteOrder.LITTLE_ENDIAN);
        byte first_byte = raw_data.get();
        int tcpChanel = first_byte & 0x7;
        int ystream = (first_byte & 0xff) >> 3;

        switch (ystream) {
            case YGenericHub.YSTREAM_TCP_NOTIF:
                if (_firstNotif) {
                    if (!_hub._http_params.hasAuthParam()) {
                        synchronized (_stateLock) {
                            _connectionState = ConnectionState.CONNECTED;
                            _stateLock.notifyAll();
                        }
                        _firstNotif = false;
                    } else {
                        return;
                    }
                }
                byte[] chars = new byte[raw_data.remaining()];
                raw_data.get(chars);
                String tcpNotif = new String(chars, StandardCharsets.ISO_8859_1);
                decodeTCPNotif(tcpNotif);
                break;
            case YGenericHub.YSTREAM_EMPTY:
                return;
            case YGenericHub.YSTREAM_TCP_ASYNCCLOSE:
                synchronized (_workingRequests) {
                    workingRequest = _workingRequests.get(tcpChanel).peek();
                }
                if (workingRequest != null && raw_data.remaining() >= 1) {
                    stream = new WSStream(ystream, tcpChanel, raw_data.remaining() - 1, raw_data);
                    int asyncId = raw_data.get();
                    if (workingRequest.getAsyncId() != asyncId) {
                        _hub._yctx._Log("WS: Incorrect async-close signature on tcpChan " + tcpChanel);
                        return;
                    }
                    workingRequest.addStream(stream);
                    workingRequest.setState(WSRequest.State.CLOSED);
                    synchronized (_workingRequests) {
                        _workingRequests.get(tcpChanel).remove(workingRequest);
                    }
                }
                break;
            case YGenericHub.YSTREAM_TCP:
            case YGenericHub.YSTREAM_TCP_CLOSE:
                synchronized (_workingRequests) {
                    workingRequest = _workingRequests.get(tcpChanel).peek();
                }
                if (workingRequest != null) {
                    stream = new WSStream(ystream, tcpChanel, raw_data.remaining(), raw_data);
                    workingRequest.addStream(stream);
                    if (ystream == YGenericHub.YSTREAM_TCP_CLOSE) {
                        WSStream outstream = new WSStream(YGenericHub.YSTREAM_TCP_CLOSE, tcpChanel, 0, null);
                        try {
                            _session.getBasicRemote().sendBinary(outstream.getContent(), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        workingRequest.setState(WSRequest.State.CLOSED);
                        synchronized (_workingRequests) {
                            _workingRequests.get(tcpChanel).remove(workingRequest);
                        }
                    }
                }
                break;
            case YGenericHub.YSTREAM_META:
                int metatype = raw_data.get() & 0xff;
                long nounce;
                int version;
                switch (metatype)

                {
                    case USB_META_WS_ANNOUNCE:
                        version = raw_data.get() & 0xff;
                        if (version < USB_META_WS_PROTO_V1 || raw_data.limit() < USB_META_WS_ANNOUNCE_SIZE) {
                            return;
                        }
                        _remoteVersion = version;
                        raw_data.getShort(); // ignore reserved word
                        nounce = raw_data.getInt();
                        nounce &= 0xffffffff;
                        byte[] serial_char = new byte[raw_data.remaining()];
                        raw_data.get(serial_char);
                        int len;
                        for (len = YAPI.YOCTO_BASE_SERIAL_LEN; len < serial_char.length; len++) {
                            if (serial_char[len] == 0) {
                                break;
                            }
                        }
                        _serial = new String(serial_char, 0, len, StandardCharsets.ISO_8859_1);
                        _remoteNouce = nounce;
                        Random randomGenerator = new Random();
                        _nounce = randomGenerator.nextInt();
                        synchronized (_stateLock) {
                            _connectionState = ConnectionState.AUTHENTICATING;
                            _stateLock.notifyAll();
                        }
                        sendAuthenticationMeta();
                        break;
                    case USB_META_WS_AUTHENTICATION:
                        synchronized (_stateLock) {
                            if (_connectionState != ConnectionState.AUTHENTICATING)
                                return;
                        }
                        version = raw_data.get() & 0xff;
                        if (version < USB_META_WS_PROTO_V1 || raw_data.limit() < USB_META_WS_AUTHENTICATION_SIZE) {
                            return;
                        }
                        int flags = raw_data.getShort() & 0xffff;
                        raw_data.getInt(); // drop nounce
                        if ((flags & USB_META_WS_RW) != 0)
                            _rwAccess = true;
                        if ((flags & USB_META_WS_VALID_SHA1) != 0) {
                            byte[] remote_sha1 = new byte[20];
                            raw_data.get(remote_sha1);
                            byte[] sha1 = computeAUTH(_hub._http_params.getUser(), _hub._http_params.getPass(), _serial, _nounce);
                            if (Arrays.equals(sha1, remote_sha1)) {
                                synchronized (_stateLock) {
                                    _connectionState = ConnectionState.CONNECTED;
                                    _stateLock.notifyAll();
                                }
                            } else {
                                errorOnSession(YAPI.UNAUTHORIZED, String.format("Authentication as %s failed", _hub._http_params.getUser()));
                                break;
                            }
                        } else {
                            if (!_hub._http_params.hasAuthParam()) {
                                synchronized (_stateLock) {
                                    _connectionState = ConnectionState.CONNECTED;
                                    _stateLock.notifyAll();
                                }
                            } else {
                                if (_hub._http_params.getUser().equals("admin") && !_rwAccess) {
                                    errorOnSession(YAPI.UNAUTHORIZED, String.format("Authentication as %s failed", _hub._http_params.getUser()));
                                } else {
                                    errorOnSession(YAPI.UNAUTHORIZED, String.format("Authentication error : hub has no password for %s", _hub._http_params.getUser()));
                                }
                                break;
                            }
                        }
                        break;
                    case USB_META_WS_ERROR:
                        // drop reserved byte
                        raw_data.get();
                        int html_error = raw_data.getShort() & 0xffff;
                        if (html_error == 401) {
                            errorOnSession(YAPI.UNAUTHORIZED, "Authentication failed");
                        } else {
                            errorOnSession(YAPI.IO_ERROR, String.format("Remote hub closed connection with error %d", html_error));
                        }
                        break;
                }

                break;
            case YGenericHub.YSTREAM_NOTICE:
            case YGenericHub.YSTREAM_REPORT:
            case YGenericHub.YSTREAM_REPORT_V2:
            case YGenericHub.YSTREAM_NOTICE_V2:
            default:
                _hub._yctx._Log(String.format("Invalid WS stream type (%d)", ystream));
        }

    }

    private void sendAuthenticationMeta()
    {
        ByteBuffer auth = ByteBuffer.allocate(USB_META_WS_AUTHENTICATION_SIZE);
        auth.order(ByteOrder.LITTLE_ENDIAN);
        auth.put((byte) USB_META_WS_AUTHENTICATION);
        if (_hub._http_params.hasAuthParam()) {
            auth.put((byte) USB_META_WS_PROTO_V1);
            auth.putShort((short) USB_META_WS_VALID_SHA1);
            auth.putInt(_nounce);
            byte[] sha1 = computeAUTH(_hub._http_params.getUser(), _hub._http_params.getPass(), _serial, _remoteNouce);
            auth.put(sha1);
        } else {
            auth.put((byte) USB_META_WS_PROTO_V1);
            auth.putInt(0);
            for (int i = 0; i < 5; i++) {
                auth.putInt(0);
            }
        }
        auth.rewind();
        WSStream stream = new WSStream(YGenericHub.YSTREAM_META, 0, USB_META_WS_AUTHENTICATION_SIZE, auth);
        RemoteEndpoint.Basic remote = _session.getBasicRemote();
        try {
            remote.sendBinary(stream.getContent(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] computeAUTH(String user, String pass, String serial, long noune)
    {
        String ha1_str = user + ":" + serial + ":" + pass;
        _md5.reset();
        _md5.update(ha1_str.getBytes());
        byte[] digest = _md5.digest();
        String ha1 = YAPIContext._bytesToHexStr(digest, 0, digest.length).toLowerCase();
        String sha1_raw = ha1 + String.format("%02x%02x%02x%02x",
                noune & 0xff, (noune >> 8) & 0xff, (noune >> 16) & 0xff, (noune >> 24) & 0xff);
        _sha1.reset();
        _sha1.update(sha1_raw.getBytes());
        return _sha1.digest();
    }


    private final StringBuilder _notificationsFifo = new StringBuilder();

    private void decodeTCPNotif(String tcpNofif)
    {
        _notificationsFifo.append(tcpNofif);
        int pos;
        do {
            pos = _notificationsFifo.indexOf("\n");
            if (pos < 0) break;
            // discard ping notification (pos==0)
            if (pos > 0) {
                String line = _notificationsFifo.substring(0, pos + 1);
                if (line.indexOf(27) == -1) {
                    // drop notification that contain esc char
                    handleNetNotification(line);
                }
            }
            _notificationsFifo.delete(0, pos + 1);
        }

        while (pos >= 0);
    }


    @OnClose
    public void onClose(@SuppressWarnings("UnusedParameters") Session session, CloseReason closeReason)
    {
        errorOnSession(YAPI.IO_ERROR, closeReason.getReasonPhrase());
    }

    private void errorOnSession(int errno, String closeReason)
    {
        synchronized (_stateLock) {
            if (_connectionState == ConnectionState.DEAD) {
                // already dead
                return;
            }
            _connectionState = ConnectionState.DEAD;
            if (errno != YAPI.SUCCESS) {
                _session_errno = errno;
                _session_error = closeReason;
            }
            _stateLock.notifyAll();
        }
        try {
            _session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WSRequest fakeRequest = new WSRequest(0, null);
        fakeRequest.setState(WSRequest.State.ERROR);
        fakeRequest.setError(YAPI.IO_ERROR, closeReason, null);
        try {
            _outRequest.put(fakeRequest);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
