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
class WSNotificationHandler extends NotificationHandler implements MessageHandler
{

    private static final int NB_TCP_CHANNEL = 4;
    private static final int HUB_TCP_CHANNEL = 0;
    private static final int DEVICE_TCP_CHANNEL = 0;
    private static final int WS_REQUEST_MAX_DURATION = 50000;


    // default transport layer parameters
    private static final int DEFAULT_TCP_ROUND_TRIP_TIME = 30;
    private static final int DEFAULT_TCP_MAX_WINDOW_SIZE = 4 * 65536;

    private final ExecutorService _executorService;
    private final boolean _isHttpCallback;
    private MessageDigest _sha1 = null;
    private MessageDigest _md5 = null;
    private Session _session;


    private final BlockingQueue<WSRequest> _pendingRequests = new LinkedBlockingQueue<>();

    private final ArrayList<ArrayList<WSRequest>> _workingRequests;
    private final Object _stateLock = new Object();
    private volatile boolean _firstNotif;
    private volatile boolean _muststop;
    private long _connectionTime = 0;
    private ConnectionState _connectionState = ConnectionState.CONNECTING;
    private int _remoteVersion = 0;
    private String _remoteSerial;
    private long _remoteNouce;
    private int _nounce;
    private volatile int _session_errno;
    private volatile String _session_error;
    private boolean _rwAccess = false;
    private long _tcpRoundTripTime = DEFAULT_TCP_ROUND_TRIP_TIME;
    private int _tcpMaxWindowSize = DEFAULT_TCP_MAX_WINDOW_SIZE;
    private final int[] _lastUploadAckBytes = new int[NB_TCP_CHANNEL];
    private final long[] _lastUploadAckTime = new long[NB_TCP_CHANNEL];
    private final int[] _lastUploadRateBytes = new int[NB_TCP_CHANNEL];
    private final long[] _lastUploadRateTime = new long[NB_TCP_CHANNEL];
    private int _uploadRate = 0;
    private byte _nextAsyncId = 48;
    private long _next_transmit_tm = 0;

    private final Object _sendLock = new Object();

    private enum ConnectionState
    {
        DEAD, DISCONNECTED, CONNECTING, AUTHENTICATING, CONNECTED
    }

    WSNotificationHandler(YHTTPHub hub, Object session)
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
            _workingRequests.add(i, new ArrayList<WSRequest>());
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
            WSLOG("WebSocket is not open");
            return;
        }

        RemoteEndpoint.Basic basicRemote = _session.getBasicRemote();
        try {
            long timeout = System.currentTimeMillis() + 10000;
            synchronized (_stateLock) {
                while (_connectionState == ConnectionState.CONNECTING && !_muststop) {
                    _stateLock.wait(1000);
                    if (timeout < System.currentTimeMillis()) {
                        WSLOG("YoctoHub did not send any data for 10 secs");
                        _connectionState = ConnectionState.DISCONNECTED;
                        _stateLock.notifyAll();
                        return;
                    }
                }
            }

            while (!Thread.currentThread().isInterrupted() && !_muststop) {
                long now = YAPI.GetTickCount();
                long wait;
                if (_next_transmit_tm >= now) {
                    wait = _next_transmit_tm - now;
                } else {
                    wait = 1000;
                }
                WSRequest request = _pendingRequests.poll(wait, TimeUnit.MILLISECONDS);
                if (request != null) {
                    if (request.getState().equals(WSRequest.State.ERROR)) {
                        // fake request to unlock thread and quit
                        break;
                    }
                    synchronized (_workingRequests) {
                        request.reportStartOfProcess();
                        _workingRequests.get(request.getChannel()).add(request);
                    }
                }
                processRequests(basicRemote);
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

    private WSRequest sendRequest(String req_first_line, byte[] req_head_and_body, int tcpchanel, boolean async, YGenericHub.RequestProgress progress, Object context) throws YAPI_Exception, InterruptedException
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
                request = new WSRequest(tcpchanel, full_request, progress, context);
            }
        }
        _pendingRequests.put(request);
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
        if (mstimeout == 0) {
            // simulate a wait indefinitely
            mstimeout = 86400000; //24h
        }
        WSRequest wsRequest = sendRequest(req_first_line, req_head_and_body, HUB_TCP_CHANNEL, false, null, null);
        return getRequestResponse(wsRequest, mstimeout);
    }


    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, int mstimeout, YGenericHub.RequestProgress progress, Object context) throws
            YAPI_Exception, InterruptedException
    {
        if (mstimeout == 0) {
            // simulate a wait indefinitely
            mstimeout = 86400000; //24h
        }
        WSRequest wsRequest = sendRequest(req_first_line, req_head_and_body, DEVICE_TCP_CHANNEL, false, progress, context);
        return getRequestResponse(wsRequest, mstimeout);
    }

    @Override
    void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body,
                         final YGenericHub.RequestAsyncResult asyncResult, final Object asyncContext) throws
            YAPI_Exception, InterruptedException
    {
        final WSRequest wsRequest = sendRequest(req_first_line, req_head_and_body, DEVICE_TCP_CHANNEL, true, null, null);
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
        _executorService.shutdown();
        boolean allTerminated = _executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        _muststop = true;
        try {
            _session.close();
        } catch (IOException | IllegalStateException e) {
            WSLOG("error on ws close : " + e.getMessage());
            e.printStackTrace();
        }
        return !allTerminated;
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

        ArrayList<WSRequest> requestOfTCPChan = _workingRequests.get(tcpChanel);
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
                    workingRequest = requestOfTCPChan.size() > 0 ? requestOfTCPChan.get(0) : null;
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
                        requestOfTCPChan.remove(workingRequest);
                    }
                }
                break;
            case YGenericHub.YSTREAM_TCP:
            case YGenericHub.YSTREAM_TCP_CLOSE:
                synchronized (_workingRequests) {
                    workingRequest = requestOfTCPChan.size() > 0 ? requestOfTCPChan.get(0) : null;
                }
                if (workingRequest != null) {
                    stream = new WSStream(ystream, tcpChanel, raw_data.remaining(), raw_data);
                    workingRequest.addStream(stream);
                    if (ystream == YGenericHub.YSTREAM_TCP_CLOSE) {
                        WSStream outstream = new WSStream(YGenericHub.YSTREAM_TCP_CLOSE, tcpChanel, 0, null);
                        try {
                            RemoteEndpoint.Basic basicRemote = _session.getBasicRemote();
                            synchronized (_sendLock) {
                                basicRemote.sendBinary(outstream.getContent(), true);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        workingRequest.setState(WSRequest.State.CLOSED);
                        synchronized (_workingRequests) {
                            requestOfTCPChan.remove(workingRequest);
                        }
                    }
                }
                break;
            case YGenericHub.YSTREAM_META:
                int metatype = raw_data.get() & 0xff;
                long nounce;
                int version;
                switch (metatype) {
                    case YGenericHub.USB_META_WS_ANNOUNCE:
                        version = raw_data.get() & 0xff;
                        if (version < YGenericHub.USB_META_WS_PROTO_V1 || raw_data.limit() < YGenericHub.USB_META_WS_ANNOUNCE_SIZE) {
                            return;
                        }
                        _remoteVersion = version;
                        int maxtcpws = raw_data.getShort(); // ignore reserved word
                        if (maxtcpws > 0) {
                            _tcpMaxWindowSize = maxtcpws;
                        }
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
                        _remoteSerial = new String(serial_char, 0, len, StandardCharsets.ISO_8859_1);
                        _remoteNouce = nounce;
                        _connectionTime = YAPI.GetTickCount();
                        Random randomGenerator = new Random();
                        _nounce = randomGenerator.nextInt();
                        synchronized (_stateLock) {
                            _connectionState = ConnectionState.AUTHENTICATING;
                            _stateLock.notifyAll();
                        }
                        sendAuthenticationMeta();
                        break;
                    case YGenericHub.USB_META_WS_AUTHENTICATION:
                        synchronized (_stateLock) {
                            if (_connectionState != ConnectionState.AUTHENTICATING)
                                return;
                        }
                        version = raw_data.get() & 0xff;
                        if (version < YGenericHub.USB_META_WS_PROTO_V1 || raw_data.limit() < YGenericHub.USB_META_WS_AUTHENTICATION_SIZE) {
                            return;
                        }
                        _tcpRoundTripTime = YAPI.GetTickCount() - _connectionTime + 1;
                        long uploadRate = _tcpMaxWindowSize * 1000 / _tcpRoundTripTime;
                        _hub._yctx._Log(String.format("WS:RTT=%dms, WS=%d, uploadRate=%f KB/s\n", _tcpRoundTripTime, _tcpMaxWindowSize, uploadRate / 1000.0));
                        int flags = raw_data.getShort() & 0xffff;
                        raw_data.getInt(); // drop nounce
                        if ((flags & YGenericHub.USB_META_WS_AUTH_FLAGS_RW) != 0)
                            _rwAccess = true;
                        if ((flags & YGenericHub.USB_META_WS_VALID_SHA1) != 0) {
                            byte[] remote_sha1 = new byte[20];
                            raw_data.get(remote_sha1);
                            byte[] sha1 = computeAUTH(_hub._http_params.getUser(), _hub._http_params.getPass(), _remoteSerial, _nounce);
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
                    case YGenericHub.USB_META_WS_ERROR:
                        // drop reserved byte
                        raw_data.get();
                        int html_error = raw_data.getShort() & 0xffff;
                        if (html_error == 401) {
                            errorOnSession(YAPI.UNAUTHORIZED, "Authentication failed");
                        } else {
                            errorOnSession(YAPI.IO_ERROR, String.format("Remote hub closed connection with error %d", html_error));
                        }
                        break;
                    case YGenericHub.USB_META_ACK_UPLOAD:
                        int tcpchan = raw_data.get();
                        synchronized (_workingRequests) {
                            ArrayList<WSRequest> uploadChandRequests = _workingRequests.get(tcpchan);
                            workingRequest = uploadChandRequests.size() > 0 ? uploadChandRequests.get(0) : null;
                        }
                        if (workingRequest != null) {
                            int b0 = raw_data.get() & 0xff;
                            int b1 = raw_data.get() & 0xff;
                            int b2 = raw_data.get() & 0xff;
                            int b3 = raw_data.get() & 0xff;
                            int ackBytes = b0 + (b1 << 8) + (b2 << 16) + (b3 << 24);
                            long ackTime = YAPI.GetTickCount();
                            if (_lastUploadAckTime[tcpchan] != 0 && ackBytes > _lastUploadAckBytes[tcpchan]) {
                                _lastUploadAckBytes[tcpchan] = ackBytes;
                                _lastUploadAckTime[tcpchan] = ackTime;

                                int deltaBytes = ackBytes - _lastUploadRateBytes[tcpchan];
                                long deltaTime = ackTime - _lastUploadRateTime[tcpchan];
                                if (deltaTime < 500) break; // wait more
                                if (deltaTime < 1000 && deltaBytes < 65536) break; // wait more
                                _lastUploadRateBytes[tcpchan] = ackBytes;
                                _lastUploadRateTime[tcpchan] = ackTime;
                                workingRequest.reportProgress(ackBytes);
                                double newRate = deltaBytes * 1000.0 / deltaTime;
                                _uploadRate = (int) (0.8 * _uploadRate + 0.3 * newRate);// +10% intentionally
                                _hub._yctx._Log(String.format("Upload rate: %.2f KB/s (based on %.2f KB in %fs)\n", newRate / 1000.0, deltaBytes / 1000.0, deltaTime / 1000.0));
                            } else {
                                _hub._yctx._Log("First Ack received\n");
                                _lastUploadAckBytes[tcpchan] = ackBytes;
                                _lastUploadAckTime[tcpchan] = ackTime;
                                _lastUploadRateBytes[tcpchan] = ackBytes;
                                _lastUploadRateTime[tcpchan] = ackTime;
                                workingRequest.reportProgress(ackBytes);
                            }
                        }
                        break;
                    default:
                        WSLOG(String.format("unhandled Meta pkt %d", ystream));
                        break;
                }

                break;
            case YGenericHub.YSTREAM_NOTICE:
            case YGenericHub.YSTREAM_REPORT:
            case YGenericHub.YSTREAM_REPORT_V2:
            case YGenericHub.YSTREAM_NOTICE_V2:
            default:
                _hub._yctx._Log(String.format("Invalid WS stream type (%d)\n", ystream));
        }

    }


    /*
    *   look through all pending request if there is some data that we can send
    *
    */
    private void processRequests(RemoteEndpoint.Basic remote) throws IOException
    {
        int tcpchan;

        if (_next_transmit_tm != 0 && _next_transmit_tm > YAPI.GetTickCount()) {
            return;
        }
        for (tcpchan = 0; tcpchan < NB_TCP_CHANNEL; tcpchan++) {
            WSRequest req;
            ArrayList<WSRequest> requestOfTCPChan = _workingRequests.get(tcpchan);
            long chan0 = System.currentTimeMillis();
            int reqIndex = 0;
            synchronized (_workingRequests) {
                req = requestOfTCPChan.size() > reqIndex ? requestOfTCPChan.get(reqIndex) : null;
            }
            while (req != null) {
                ByteBuffer requestBytes = req.getRequestBytes();
                int throttle_start = requestBytes.position();
                int throttle_end = requestBytes.limit();
                if (throttle_end > 2108 && _remoteVersion >= YGenericHub.USB_META_WS_PROTO_V2 && tcpchan == 0) {
                    // Perform throttling on large uploads
                    if (requestBytes.position() == 0) {
                        // First chunk is always first multiple of full window (124 bytes) above 2KB
                        throttle_end = 2108;
                        // Prepare to compute effective transfer rate
                        _lastUploadAckBytes[tcpchan] = 0;
                        _lastUploadAckTime[tcpchan] = 0;
                        // Start with initial RTT based estimate
                        _uploadRate = (int) (_tcpMaxWindowSize * 1000 / _tcpRoundTripTime);
                    } else if (_lastUploadAckTime[tcpchan] == 0) {
                        // first block not yet acked, wait more
                        throttle_end = 0;
                    } else {
                        // adapt window frame to available bandwidth
                        int bytesOnTheAir = requestBytes.position() - _lastUploadAckBytes[tcpchan];
                        long timeOnTheAir = YAPI.GetTickCount() - _lastUploadAckTime[tcpchan];
                        int uploadRate = _uploadRate;
                        int toBeSent = (int) (2 * uploadRate + 1024 - bytesOnTheAir + (uploadRate * timeOnTheAir / 1000));
                        if (toBeSent + bytesOnTheAir > DEFAULT_TCP_MAX_WINDOW_SIZE) {
                            toBeSent = DEFAULT_TCP_MAX_WINDOW_SIZE - bytesOnTheAir;
                        }
                        WSLOG(String.format("throttling: %d bytes/s (%d + %d = %d)", _uploadRate, toBeSent, bytesOnTheAir, bytesOnTheAir + toBeSent));
                        if (toBeSent < 64) {
                            long waitTime = 1000 * (128 - toBeSent) / _uploadRate;
                            if (waitTime < 2) waitTime = 2;
                            _next_transmit_tm = YAPI.GetTickCount() + waitTime;
                            WSLOG(String.format("WS: %d sent %dms ago, waiting %dms...", bytesOnTheAir, timeOnTheAir, waitTime));
                            throttle_end = 0;
                        }
                        if (throttle_end > requestBytes.position() + toBeSent) {
                            // when sending partial content, round up to full frames
                            if (toBeSent > 124) {
                                toBeSent = (toBeSent / 124) * 124;
                            }
                            throttle_end = requestBytes.position() + toBeSent;
                        }
                    }
                }
                while (requestBytes.position() < throttle_end) {
                    int datalen = throttle_end - requestBytes.position();
                    if (datalen > WSStream.MAX_DATA_LEN) {
                        datalen = WSStream.MAX_DATA_LEN;
                    }
                    WSStream wsstream;
                    if (req.isAsync() && (requestBytes.position() + datalen == requestBytes.limit())) {
                        if (datalen == WSStream.MAX_DATA_LEN) {
                            // last frame is already full we must send the async close in another one
                            wsstream = new WSStream(YGenericHub.YSTREAM_TCP, tcpchan, datalen, requestBytes);
                            synchronized (_sendLock) {
                                remote.sendBinary(wsstream.getContent(), true);
                            }
                            req.reportDataSent();
                            //WSLOG(String.format("ws_req:%s: send %d bytes on chan%d (%d/%d)\n", req, datalen, tcpchan, requestBytes.position(), requestBytes.limit()));
                            datalen = 0;
                        }
                        wsstream = new WSStream(YGenericHub.YSTREAM_TCP_ASYNCCLOSE, tcpchan, datalen, requestBytes, req.getAsyncId());
                        synchronized (_sendLock) {
                            remote.sendBinary(wsstream.getContent(), true);
                        }
                        req.reportDataSent();
                        //WSLOG(String.format("req(%s:%s) sent async close %d\n", _hub.getHost(), req, req.getAsyncId()));
                    } else {
                        wsstream = new WSStream(YGenericHub.YSTREAM_TCP, tcpchan, datalen, requestBytes);
                        synchronized (_sendLock) {
                            remote.sendBinary(wsstream.getContent(), true);
                        }
                        req.reportDataSent();
                        //WSLOG("ws_req:%p: sent %d bytes on chan%d (%d/%d)\n", req, datalen, tcpchan, req->ws.requestpos, req->ws.requestsize);
                    }
                }
                if (requestBytes.position() < requestBytes.limit()) {
                    int sent = requestBytes.position() - throttle_start;
                    // not completely sent, cannot do more for now
                    if (sent > 0 && _uploadRate > 0) {
                        long waitTime = 1000 * sent / _uploadRate;
                        if (waitTime < 2) waitTime = 2;
                        _next_transmit_tm = YAPI.GetTickCount() + waitTime;
                        WSLOG(String.format("Sent %dbytes, waiting %dms...", sent, waitTime));
                    } else {
                        _next_transmit_tm = YAPI.GetTickCount() + 100;
                    }
                }
                synchronized (_workingRequests) {
                    reqIndex++;
                    req = requestOfTCPChan.size() > reqIndex ? requestOfTCPChan.get(reqIndex) : null;
                }
            }
        }
    }

    private void WSLOG(String s)
    {
        //System.out.println("WSLOG:"+s);
        _hub._yctx._Log(s + "\n");
    }

    private void sendAuthenticationMeta()
    {
        ByteBuffer auth = ByteBuffer.allocate(YGenericHub.USB_META_WS_AUTHENTICATION_SIZE);
        auth.order(ByteOrder.LITTLE_ENDIAN);
        auth.put((byte) YGenericHub.USB_META_WS_AUTHENTICATION);
        if (_hub._http_params.hasAuthParam()) {
            auth.put((byte) YGenericHub.USB_META_WS_PROTO_V2);
            auth.putShort((short) YGenericHub.USB_META_WS_VALID_SHA1);
            auth.putInt(_nounce);
            byte[] sha1 = computeAUTH(_hub._http_params.getUser(), _hub._http_params.getPass(), _remoteSerial, _remoteNouce);
            auth.put(sha1);
        } else {
            auth.put((byte) YGenericHub.USB_META_WS_PROTO_V2);
            auth.putInt(0);
            for (int i = 0; i < 5; i++) {
                auth.putInt(0);
            }
        }
        auth.rewind();
        WSStream stream = new WSStream(YGenericHub.YSTREAM_META, 0, YGenericHub.USB_META_WS_AUTHENTICATION_SIZE, auth);
        RemoteEndpoint.Basic remote = _session.getBasicRemote();
        try {
            synchronized (_sendLock) {
                remote.sendBinary(stream.getContent(), true);
            }
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
        WSRequest fakeRequest = new WSRequest(YAPI.IO_ERROR, closeReason);
        try {
            _pendingRequests.put(fakeRequest);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
