/*********************************************************************
 * $Id: YHTTPHub.java 74799 2026-06-22 06:45:40Z seb $
 *
 * Internal YHTTPHUB object
 *
 * - - - - - - - - - License information: - - - - - - - - -
 *
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 * Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 * non-exclusive license to use, modify, copy and integrate this
 * file into your software for the sole purpose of interfacing
 * with Yoctopuce products.
 *
 * You may reproduce and distribute copies of this file in
 * source or object form, as long as the sole purpose of this
 * code is to interface with Yoctopuce products. You must retain
 * this notice in the distributed source file.
 *
 * You should refer to Yoctopuce General Terms and Conditions
 * for additional information regarding your rights and
 * obligations.
 *
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING
 * WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 * EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 * COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 * SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 * LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 * CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 * BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 * WARRANTY, OR OTHERWISE.
 *********************************************************************/
package com.yoctopuce.YoctoAPI;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class YHTTPHub extends YGenericHub
{
    public static final int YIO_DEFAULT_TCP_TIMEOUT = 20000;
    private static final int YIO_1_MINUTE_TCP_TIMEOUT = 60000;
    private static final int YIO_10_MINUTES_TCP_TIMEOUT = 600000;
    static final int NET_HUB_NOT_CONNECTION_TIMEOUT = 6000;
    private YJSONObject _cache_json = null;


    enum WorkerThreadState
    {
        OFF,
        RUNNING,
        STOP_REQUESTED,
    }

    private final Object _callbackSession;
    private NotificationHandler _notificationHandler;
    private Thread _thread;
    private WorkerThreadState _workerThreadState = WorkerThreadState.OFF;
    private String _http_realm = "";
    private String _nounce = "";
    private int _nounce_count = 0;
    private String _ha1 = "";
    private String _opaque = "";
    private Random _randGen = new Random();
    private MessageDigest mdigest;
    private int _authRetryCount = 0;
    private boolean _writeProtected = false;
    boolean _sendPingNotification = false;
    long _lastPing = 0;
    private volatile int _connectionState = YHub.UNREGISTERED;

    private final Object _authLock = new Object();
    /**
     * The runtime http parameters that has been updated with the data from the info.json
     * This is the one that need to be used during execution
     */
    HTTPParams _runtime_http_params = null;
    boolean _usePureHTTP = false;
    ArrayList<PortInfo> _portInfo = new ArrayList<>();
    private HubMode _hubMode;
    private int _securityMode = 0;

    boolean workerThreadMustContinue()
    {
        return _workerThreadState == WorkerThreadState.RUNNING;
    }

    static class PortInfo
    {
        String proto;
        int port;

        public PortInfo(String proto, int port)
        {
            this.proto = proto;
            this.port = port;
        }
    }


    boolean needRetryWithAuth()
    {
        synchronized (_authLock) {
            return _runtime_http_params.getUser().length() != 0 && _runtime_http_params.getPass().length() != 0 && _authRetryCount++ <= 3;
        }
    }

    void authSucceded()
    {
        synchronized (_authLock) {
            _authRetryCount = 0;
        }
    }

    @Override
    public synchronized void addKnownURL(String url)
    {
        super.addKnownURL(url);
        HTTPParams params = new HTTPParams(url);
        if (params.hasAuthParam() && !this._URL_params.hasAuthParam()) {
            this._URL_params.updateAuth(params.getUser(), params.getPass());
            if (this._runtime_http_params != null) {
                this._runtime_http_params.updateAuth(params.getUser(), params.getPass());
            }
        }
    }

    // Update the hub internal variables according
    // to a received header with WWW-Authenticate
    void parseWWWAuthenticate(String header)
    {
        synchronized (_authLock) {
            int pos = header.toLowerCase().indexOf("\r\nwww-authenticate:");
            if (pos == -1) return;
            header = header.substring(pos + 19);
            int eol = header.indexOf('\r');
            if (eol >= 0) {
                header = header.substring(0, eol);
            }
            _http_realm = "";
            _nounce = "";
            _opaque = "";
            _nounce_count = 0;

            header = header.trim();
            if (!header.startsWith("Digest ")) {
                return;
            }
            header = header.substring(7).trim();

            String[] tags = header.split(",");
            for (String tag : tags) {
                int eq = tag.indexOf("=");
                if (eq < 0) {
                    continue;
                }
                String name = tag.substring(0, eq).trim();
                String value = tag.substring(eq + 1).trim();
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                switch (name) {
                    case "realm":
                        _http_realm = value;
                        break;
                    case "nonce":
                        _nounce = value;
                        break;
                    case "opaque":
                        _opaque = value;
                        break;
                }
            }

            String plaintext = _runtime_http_params.getUser() + ":" + _http_realm + ":" + _runtime_http_params.getPass();
            mdigest.reset();
            mdigest.update(plaintext.getBytes(_yctx._deviceCharset));
            byte[] digest = this.mdigest.digest();
            _ha1 = YAPIContext._bytesToHexStr(digest, 0, digest.length).toLowerCase();
        }
    }


    // Return an Authorization header for a given request
    String getAuthorization(String request)
    {
        synchronized (_authLock) {
            if (_runtime_http_params.getUser().length() == 0 || _runtime_http_params.getPass().length() == 0 || _nounce.length() == 0)
                return "";
            _nounce_count++;
            int pos = request.indexOf(' ');
            String method = request.substring(0, pos);
            int enduri = request.indexOf(' ', pos + 1);
            if (enduri < 0)
                enduri = request.length();
            String uri = request.substring(pos + 1, enduri);
            String nc = String.format("%08x", _nounce_count);
            String cnonce = String.format("%08x", _randGen.nextInt());

            String plaintext = method + ":" + uri;
            mdigest.reset();
            mdigest.update(plaintext.getBytes(_yctx._deviceCharset));
            byte[] digest = this.mdigest.digest();
            String ha2 = YAPIContext._bytesToHexStr(digest, 0, digest.length).toLowerCase();
            plaintext = _ha1 + ":" + _nounce + ":" + nc + ":" + cnonce + ":auth:" + ha2;
            this.mdigest.reset();
            this.mdigest.update(plaintext.getBytes());
            digest = this.mdigest.digest();
            String response = YAPIContext._bytesToHexStr(digest, 0, digest.length).toLowerCase();
            String auth_str = String.format(
                    "Authorization: Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", qop=auth, nc=%s, cnonce=\"%s\", response=\"%s\", opaque=\"%s\"\r\n",
                    _runtime_http_params.getUser(), _http_realm, _nounce, uri, nc, cnonce, response, _opaque);
            //System.out.print(auth_str);
            return auth_str;
        }
    }

    public void requestStop()
    {
        NotificationHandler handler = _notificationHandler;
        if (handler != null) {
            boolean requestsUnfinished = false;
            try {
                requestsUnfinished = handler.waitAndFreeAsyncTasks(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            if (requestsUnfinished) {
                _yctx._Log(String.format("Stop hub %s before all async request has ended", getHost()));
            }
        }
    }

    @Override
    public String getConnectionUrl()
    {
        return _runtime_http_params.getUrl(true, false, true);
    }

    YHTTPHub(YAPIContext yctx, HTTPParams httpParams, boolean reportConnnectionLost, Object session) throws YAPI_Exception
    {
        super(yctx, httpParams, reportConnnectionLost);
        _callbackSession = session;
        try {
            mdigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "No MD5 provider");
        }
    }


    enum HubMode
    {
        LEGACY,
        MIXED,
        SECURE,
        PROTO_UNKNOWN
    }


    void yhubUseBestProto() throws YAPI_Exception
    {
        String cur_proto = this._URL_params.getProto();
        _hubMode = HubMode.SECURE;
        if (this._portInfo.isEmpty()) {
            _runtime_http_params = new HTTPParams(_URL_params);
        } else {
            if (this._usePureHTTP) {
                // For VirtualHub-4web we use the first entry available regardless of the protocol and the port set
                // by the user. In this scenario info.json has the most accurate value. Note: redirection from http to
                // https has already done by the http redirect mechanism during the download of info.json
                // Note 2 : Websocket are not supported by VirtualHub-4web
                if (cur_proto.equals("ws") || cur_proto.equals("wss")) {
                    throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "Websocket protocol is not supported by VirtualHub-4web.");
                }
                for (PortInfo portInfo : this._portInfo) {
                    if (portInfo.proto.startsWith("http")) {
                        // handle http and https
                        _yctx._Log(String.format("Hub %s will use %s proto on port %d\n", _URL_params.getHost(), portInfo.proto, portInfo.port));
                        _runtime_http_params = new HTTPParams(_URL_params, portInfo.proto, portInfo.port);
                        break;
                    }
                }

            } else {
                int best_port = 0;
                String best_proto = "ws";
                if (this._portInfo.get(0).proto.equals("http") || this._portInfo.get(0).proto.equals("ws")) {
                    _hubMode = HubMode.LEGACY;
                }

                for (PortInfo portInfo : this._portInfo) {
                    if (_hubMode == HubMode.SECURE && (portInfo.proto.equals("http") || portInfo.proto.equals("ws"))) {
                        _hubMode = HubMode.MIXED;
                    }
                    if (cur_proto.equals("auto") && best_port == 0) {
                        if (portInfo.proto.startsWith("http") || portInfo.proto.startsWith("ws")) {
                            // handle http, https, ws and wss proto
                            best_proto = portInfo.proto;
                            best_port = portInfo.port;
                        }
                    }
                    if (cur_proto.equals("secure") && best_port == 0) {
                        if (portInfo.proto.equals("https") || portInfo.proto.equals("wss")) {
                            // handle http, https, ws and wss proto
                            best_proto = portInfo.proto;
                            best_port = portInfo.port;
                        }
                    }
                }
                if (best_port != 0) {
                    _yctx._Log(String.format("Hub %s will use %s proto on port %d\n", _URL_params.getHost(),
                            best_proto, best_port));
                    _runtime_http_params = new HTTPParams(_URL_params, best_proto, best_port);
                }
            }
            if (_runtime_http_params == null) {
                _runtime_http_params = new HTTPParams(_URL_params);
            }
        }
    }


    @Override
    synchronized void startNotifications() throws YAPI_Exception
    {
        if (!isEnabled()) {
            return;
        }
        if (_thread != null || _workerThreadState != WorkerThreadState.OFF) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "notification already started");
        }
        dbglog(3, "Start NotificationWorker");
        _runtime_http_params = _URL_params;
        _thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                _workerThreadState = WorkerThreadState.RUNNING;
                dbglog(3, "NotificationWorker is running");

                try {
                    startNotificationWorker();
                } catch (Exception e) {
                    _yctx.dbglogExc(3, e);
                    throw e;
                } finally {
                    _workerThreadState = WorkerThreadState.OFF;
                    dbglog(3, "NotificationWorker is stopped");
                }

            }
        }, "Not_" + _URL_params.toString());
        _thread.start();
    }

    void startNotificationWorker()
    {
        this._usePureHTTP = false;
        this._portInfo.clear();
        long expiration = YAPI.GetTickCount() + _networkTimeoutMs;
        if (_URL_params.testInfoJson()) {
            boolean https_req = _URL_params.useSecureSocket();
            if (_URL_params.getPort() == YAPI.YOCTO_DEFAULT_HTTPS_PORT || _URL_params.useSecureSocket()) {
                https_req = true;
            }
            String url = String.format("%s://%s:%d%s/info.json", https_req ? "https" : "http", _URL_params.getHost(), _URL_params.getPort(), _URL_params.getSubDomain());
            int info_timeout = (int) (expiration - YAPI.GetTickCount());
            dbglog(3, String.format("request %s timeout=%dms", url, info_timeout));
            byte[] raw;
            try {
                raw = _yctx.BasicHTTPRequest(url, info_timeout, 0);
                dbglog(3, "parse info.json ");
                String json_str = new String(raw, _yctx._deviceCharset);
                YJSONObject json = new YJSONObject(json_str);
                try {
                    json.parse();
                } catch (Exception e) {
                    throw new YAPI_Exception(YAPI.IO_ERROR, "Invalid info.json file", e);
                }
                if (json.has("serialNumber")) {
                    this.updateHubSerial(json.getString("serialNumber"));
                }
                if (json.has("securityMode")) {
                    this._securityMode = (json.getInt("securityMode"));
                    if (this._securityMode == 0) {
                        throw new YAPI_Exception(YAPI.UNCONFIGURED, "Remote hub is not yet configured");
                    }
                }
                if (json.has("protocol") && json.getString("protocol").equals("HTTP/1.1")) {
                    this._usePureHTTP = true;
                }
                if (json.has("port")) {
                    YJSONArray ports = json.getYJSONArray("port");
                    int i = 0;
                    while (i < ports.length()) {
                        String proto_port = ports.getString(i++);
                        String[] split = proto_port.split(":");
                        String proto = split[0];
                        int port = Integer.parseInt(split[1]);
                        if (port == 0) {
                            break;
                        }
                        this._portInfo.add(new PortInfo(proto, port));
                    }
                }
            } catch (YAPI_Exception ex) {
                if (ex.errorType == YAPI.SSL_ERROR || ex.errorType == YAPI.SSL_UNK_CERT || ex.errorType == YAPI.UNCONFIGURED) {
                    set_connectionState(YHub.ABORTED);
                    saveLastError(ex.errorType, ex.getLocalizedMessage(), ex);
                    return;
                }
                if (_URL_params.useSecureSocket()) {
                    set_connectionState(YHub.ABORTED);
                    saveLastError(ex.errorType, ex.getLocalizedMessage(), ex);
                    return;
                }
            }
        }
        try {
            yhubUseBestProto();
            if (_runtime_http_params.useWebSocket()) {
                dbglog(3, "Use WebSocket handler ");
                _notificationHandler = new WSNotificationHandler(this, _callbackSession);
            } else {
                dbglog(3, "Use HTTP handler ");
                _notificationHandler = new TCPNotificationHandler(this);
            }
            set_connectionState(YHub.TRYING);
            _notificationHandler.run(expiration);
        } catch (YAPI_Exception ex) {
            saveLastError(ex.errorType, ex.getLocalizedMessage(), ex);
        } finally {
            set_connectionState(YHub.ABORTED);
        }

    }


    public synchronized int get_connectionState()
    {
        if (_sendPingNotification && (_lastPing + NET_HUB_NOT_CONNECTION_TIMEOUT) > System.currentTimeMillis()) {
            // if we have ping notifications working and valid do not check _connectionState.
            // this prevents potential trying and reconnecting value that occurs periodically with VirtualHub-4web
            return YHub.CONNECTED;
        }
        return _connectionState;
    }

    public synchronized void set_connectionState(int connectionState)
    {
        dbglog(4, String.format("set connection state to %d", connectionState));
        this._connectionState = connectionState;
        notifyAll();
    }


    @Override
    void stopNotifications()
    {

        // give some time to the pending request to finish
        dbglog(3, "Stop notification");
        if (_notificationHandler != null) {
            boolean requestsUnfinished = false;
            try {
                requestsUnfinished = _notificationHandler.waitAndFreeAsyncTasks(yHTTPRequest.MAX_REQUEST_MS);
            } catch (InterruptedException e) {
                _yctx.dbglogExc(2, e);
            }
            if (requestsUnfinished) {
                _yctx._Log(String.format("Stop hub %s before all async request has ended", getHost()));
            }
        }
        dbglog(4, "Stop notification request thread stop");
        if (_workerThreadState != WorkerThreadState.OFF) {
            _workerThreadState = WorkerThreadState.STOP_REQUESTED;
        }

        if (_thread != null) {
            _thread.interrupt();
        }
        if (_notificationHandler != null) {
            _notificationHandler.stopSocketsOfThread();
        }
        int count = 10;
        while (this._workerThreadState != WorkerThreadState.OFF && count > 0) {
            try {
                dbglog(4, String.format("wait 500ms for the end of thread (state=%s count=%d))", this._workerThreadState, count));
                Thread.sleep(500);
            } catch (InterruptedException e) {
                _yctx.dbglogExc(2, e);
            }
            count--;
        }
        dbglog(4, "Stop notification for hub %s(%s) thread should be stopped");
        _thread = null;
        _notificationHandler = null;
        removeAllDevices();
        dbglog(4, "Stop notification for hub %s(%s) all done");
    }

    @Override
    void release()
    {

    }

    @Override
    String getRootUrl()
    {
        return _runtime_http_params.getUrl();
    }

    @Override
    boolean isSameHub(String url, Object request, Object response, Object session)
    {
        boolean sameHub = super.isSameHub(url, request, response, session);
        HTTPParams params = new HTTPParams(url);
        if (!sameHub && _runtime_http_params != null) {
            String paramsUrl = params.getUrl(false, false, false);
            sameHub = paramsUrl.equals(_runtime_http_params.getUrl(false, false, false));
        }
        return sameHub && (_callbackSession == null || _callbackSession.equals(session));
    }


    @Override
    synchronized void updateDeviceList(boolean forceupdate) throws YAPI_Exception, InterruptedException
    {
        if (!isEnabled()) {
            return;
        }
        long now = YAPI.GetTickCount();
        if (forceupdate) {
            _devListExpires = 0;
        }
        if (_devListExpires > now) {
            return;
        }
        if (!isOnline()) {
            String message = "hub " + this._runtime_http_params.getUrl() + " is not reachable";
            this._yctx._Log(String.format("_sendPingNotification=%b lastping=%x (exp%d) _connectionState=%d\n",
                    _sendPingNotification,_lastPing,(_lastPing + NET_HUB_NOT_CONNECTION_TIMEOUT) - System.currentTimeMillis(),_connectionState));
            saveLastError(YAPI.TIMEOUT, message, null);
            if (_reportConnnectionLost) {
                throw new YAPI_Exception(YAPI.TIMEOUT, message);
            } else {
                removeAllDevices();
                return;
            }
        }

        String yreq;
        String request = "GET /api.json";
        if (_cache_json != null) {
            String fwrelease = _cache_json.getYJSONObject("module").getString("firmwareRelease");
            try {
                fwrelease = URLEncoder.encode(fwrelease, _yctx._defaultEncoding);
            } catch (UnsupportedEncodingException ignored) {
            }
            request += "?fw=" + fwrelease;
        }

        try {
            yreq = new String(_notificationHandler.hubRequestSync(request, null, _networkTimeoutMs), Charset.forName("ISO_8859_1"));
        } catch (YAPI_Exception ex) {
            saveLastError(ex.errorType, ex.getLocalizedMessage(), ex);
            if (_reportConnnectionLost && isEnabled()) {
                throw ex;
            }
            removeAllDevices();
            return;
        }


        HashMap<String, ArrayList<YPEntry>> yellowPages = new HashMap<>();
        ArrayList<WPEntry> whitePages = new ArrayList<>();

        YJSONObject loadval;
        try {
            if (_cache_json != null && yreq.charAt(0) == '[') {
                YJSONArray yzon = new YJSONArray(yreq);
                yzon.parse();
                loadval = (YJSONObject) _cache_json.updateFroJZon(yzon);
            } else {
                loadval = new YJSONObject(yreq);
                loadval.parse();
            }
            this._cache_json = loadval;

            if (!loadval.has("services") || !loadval.getYJSONObject("services").has("whitePages")) {
                throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Device "
                        + _URL_params.getHost() + " is not a hub");
            }
            String serial = loadval.getYJSONObject("module").getString("serialNumber");
            this.updateHubSerial(serial);
            YJSONArray whitePages_json = loadval.getYJSONObject("services").getYJSONArray("whitePages");
            YJSONObject yellowPages_json = loadval.getYJSONObject("services").getYJSONObject("yellowPages");
            if (loadval.has("network")) {
                String adminpass = loadval.getYJSONObject("network").getString("adminPassword");
                _writeProtected = adminpass.length() > 0;
            }
            // Reindex all functions from yellow pages
            Set<String> keys = yellowPages_json.getKeys();
            for (String classname : keys) {
                YJSONArray yprecs_json = yellowPages_json.getYJSONArray(classname);
                ArrayList<YPEntry> yprecs_arr = new ArrayList<>(yprecs_json.length());
                for (int i = 0; i < yprecs_json.length(); i++) {
                    YPEntry yprec = new YPEntry(yprecs_json.getYJSONObject(i));
                    yprecs_arr.add(yprec);
                }
                yellowPages.put(classname, yprecs_arr);
            }

            _serialByYdx.clear();
            // Reindex all devices from white pages
            for (int i = 0; i < whitePages_json.length(); i++) {
                YJSONObject jsonObject = whitePages_json.getYJSONObject(i);
                WPEntry devinfo = new WPEntry(jsonObject);
                int index = jsonObject.getInt("index");
                _serialByYdx.put(index, devinfo.getSerialNumber());
                whitePages.add(devinfo);
            }
        } catch (Exception e) {
            _cache_json = null;
            String message = "Request failed, could not parse API result for " + _URL_params.getHost();
            saveLastError(YAPI.IO_ERROR, message, e);
            throw new YAPI_Exception(YAPI.IO_ERROR, message, e);
        }

        updateFromWpAndYp(whitePages, yellowPages);

        // reset device list cache timeout for this hub
        now = YAPI.GetTickCount();
        if (_isNotifWorking) {
            _devListExpires = now + _yctx._deviceListValidityMs;
        } else {
            _devListExpires = now + 500;
        }
    }

    @Override
    ArrayList<String> firmwareUpdate(String serial, YFirmwareFile firmware, byte[] settings, UpdateProgress progress) throws YAPI_Exception, InterruptedException
    {
        boolean use_self_flash = false;
        String baseurl = "";
        boolean need_reboot = true;
        if (_hubSerialNumber.startsWith("VIRTHUB")) {
            use_self_flash = false;
        } else if (serial.equals(_hubSerialNumber)) {
            use_self_flash = true;
        } else {
            // check if subdevice support self flashing
            try {
                _notificationHandler.hubRequestSync("GET /bySerial/" + serial + "/flash.json?a=state", null, _networkTimeoutMs);
                baseurl = "/bySerial/" + serial;
                use_self_flash = true;
            } catch (YAPI_Exception ignored) {
            }
        }
        //5% -> 10%
        progress.firmware_progress(5, "Enter in bootloader");
        ArrayList<String> bootloaders = getBootloaders();
        boolean is_shield = serial.startsWith("YHUBSHL1");
        for (String bl : bootloaders) {
            if (bl.equals(serial)) {
                need_reboot = false;
            } else if (is_shield) {
                if (bl.startsWith("YHUBSHL1")) {
                    throw new YAPI_Exception(YAPI.IO_ERROR, "Only one YoctoHub-Shield is allowed in update mode");
                }
            }
        }
        if (!use_self_flash && need_reboot && bootloaders.size() >= 4) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "Too many devices in update mode");
        }
        // ensure flash engine is not busy
        byte[] bytes = _notificationHandler.hubRequestSync("GET " + baseurl + "/flash.json?a=state", null, _networkTimeoutMs);
        String uploadstate = new String(bytes);
        try {
            YJSONObject uploadres = new YJSONObject(uploadstate);
            uploadres.parse();
            String state = uploadres.getString("state");
            if (state.equals("uploading") || state.equals("flashing")) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "Cannot start firmware update: busy (" + state + ")");
            }
        } catch (Exception ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "invalid json response :" + ex.getLocalizedMessage());
        }
        // start firmware upload
        //10% -> 40%
        progress.firmware_progress(10, "Send firmware file");
        byte[] head_body = YDevice.formatHTTPUpload("firmware", firmware.getData());
        _notificationHandler.hubRequestSync("POST " + baseurl + "/upload.html", head_body, 0);
        //check firmware upload result
        bytes = _notificationHandler.hubRequestSync("GET " + baseurl + "/flash.json?a=state", null, YIO_10_MINUTES_TCP_TIMEOUT);
        String uploadresstr = new String(bytes);
        try {
            YJSONObject uploadres = new YJSONObject(uploadresstr);
            uploadres.parse();
            if (!uploadres.getString("state").equals("valid")) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "Upload of firmware failed: invalid firmware(" + uploadres.getString("state") + ")");
            }
            if (uploadres.getInt("progress") != 100) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "Upload of firmware failed: incomplete upload");
            }
        } catch (Exception ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "invalid json response :" + ex.getLocalizedMessage());
        }
        if (use_self_flash) {
            byte[] startupConf;
            try {
                String json = new String(settings);
                YJSONObject jsonObject = new YJSONObject(json);
                jsonObject.parse();
                YJSONObject settingsOnly = jsonObject.getYJSONObject("api");
                settingsOnly.remove("services");
                String startupConfStr = new String(settingsOnly.toJSON());
                startupConf = startupConfStr.getBytes(_yctx._deviceCharset);
            } catch (Exception ex) {
                startupConf = new byte[0];
            }
            progress.firmware_progress(20, "Upload startupConf.json");
            head_body = YDevice.formatHTTPUpload("startupConf.json", startupConf);
            _notificationHandler.hubRequestSync("POST " + baseurl + "/upload.html", head_body, YIO_10_MINUTES_TCP_TIMEOUT);
            progress.firmware_progress(20, "Upload firmwareConf");
            head_body = YDevice.formatHTTPUpload("firmwareConf", startupConf);
            _notificationHandler.hubRequestSync("POST " + baseurl + "/upload.html", head_body, YIO_10_MINUTES_TCP_TIMEOUT);
        }

        //40%-> 80%
        if (use_self_flash) {
            progress.firmware_progress(40, "Flash firmware");
            // the hub itself -> reboot in autoflash mode
            _notificationHandler.hubRequestSync("GET " + baseurl + "/api/module/rebootCountdown?rebootCountdown=-1003", null, _networkTimeoutMs);
            Thread.sleep(7000);
        } else {
            // reboot device to bootloader if needed
            if (need_reboot) {
                // reboot subdevice
                _notificationHandler.hubRequestSync("GET /bySerial/" + serial + "/api/module/rebootCountdown?rebootCountdown=-2", null, _networkTimeoutMs);
            }
            // verify that the device is in bootloader
            long timeout = YAPI.GetTickCount() + YPROG_BOOTLOADER_TIMEOUT;
            byte[] res;
            boolean found = false;
            progress.firmware_progress(40, "Wait for device to be in bootloader");
            do {
                ArrayList<String> list = getBootloaders();
                for (String bl : list) {
                    if (bl.equals(serial)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Thread.sleep(100);
                }
            } while (!found && YAPI.GetTickCount() < timeout);
            //start flash
            progress.firmware_progress(45, "Flash firmware");
            res = _notificationHandler.hubRequestSync("GET /flash.json?a=flash&s=" + serial, null, YIO_10_MINUTES_TCP_TIMEOUT);
            try {
                String jsonstr = new String(res);
                YJSONObject flashres = new YJSONObject(jsonstr);
                flashres.parse();
                YJSONArray list = flashres.getYJSONArray("logs");
                ArrayList<String> logs = new ArrayList<>(list.length());
                for (int i = 0; i < list.length(); i++) {
                    logs.add(list.getString(i));
                }
                return logs;
            } catch (Exception ex) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "invalid response");
            }
        }

        return null;
    }

    @Override
    synchronized void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception, InterruptedException
    {
        if (!isOnline()) {
            throw new YAPI_Exception(YAPI.TIMEOUT, "hub " + this._URL_params.getUrl() + " is not reachable");
        }
        if (_writeProtected && !_notificationHandler.hasRwAccess()) {
            throw new YAPI_Exception(YAPI.UNAUTHORIZED, "Access denied: admin credentials required");
        }

        _notificationHandler.devRequestAsync(device, req_first_line, req_head_and_body, asyncResult, asyncContext);
    }

    @Override
    synchronized byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestProgress progress, Object context) throws YAPI_Exception, InterruptedException
    {
        if (!isOnline()) {
            throw new YAPI_Exception(YAPI.TIMEOUT, "hub " + this._URL_params.getUrl() + " is not reachable");
        }
        // Setup timeout counter
        int tcpTimeout = _networkTimeoutMs;
        if (req_first_line.contains("/@YCB")) {
            throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "Preloading of URL is only supported for HTTP callback.");
        }
        if (req_first_line.contains("/testcb.txt") || req_first_line.contains("/logger.json")
                || req_first_line.contains("/rxmsg.json") || req_first_line.contains("/rxdata.bin")
                || req_first_line.contains("/at.txt") || req_first_line.contains("/files.json")) {
            tcpTimeout = YIO_1_MINUTE_TCP_TIMEOUT;
        } else if (req_first_line.contains("/flash.json") || req_first_line.contains("/upload.html")) {
            tcpTimeout = YIO_10_MINUTES_TCP_TIMEOUT;
        }
        return _notificationHandler.devRequestSync(device, req_first_line, req_head_and_body, tcpTimeout, progress, context);
    }

    String getHost()
    {
        return _runtime_http_params.getHost();
    }

    public int getPort()
    {
        return _runtime_http_params.getPort();
    }

    @Override
    public synchronized ArrayList<String> getBootloaders() throws YAPI_Exception, InterruptedException
    {
        ArrayList<String> res = new ArrayList<>();
        byte[] raw_data = _notificationHandler.hubRequestSync("GET /flash.json?a=list", null, _networkTimeoutMs);
        String jsonstr = new String(raw_data);
        try {
            YJSONObject flashres = new YJSONObject(jsonstr);
            flashres.parse();
            YJSONArray list = flashres.getYJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                res.add(list.getString(i));
            }
        } catch (Exception ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "Unable to retrieve bootloader list");
        }
        return res;
    }

    @Override
    public int ping(int mstimeout) throws YAPI_Exception
    {
        startNotifications();
        try {
            waitIsOnline(mstimeout);
        } finally {
            stopNotifications();
        }
        return YAPI.SUCCESS;
    }

    @Override
    boolean isCallbackMode()
    {
        return _callbackSession != null;
    }

    @Override
    boolean isReadOnly()
    {
        return _writeProtected && !_notificationHandler.hasRwAccess();
    }

    @Override
    public boolean isOnline()
    {
        return _notificationHandler != null && get_connectionState() == YHub.CONNECTED;
    }


    public Socket OpenConnectedSocket(InetAddress addr, int port, long expirationMs) throws YAPI_Exception
    {
        Socket socket;
        SocketAddress sockaddr = new InetSocketAddress(addr, port);
        long now = System.currentTimeMillis();
        if (now > expirationMs) {
            throw new YAPI_Exception(YAPI.TIMEOUT, "Unable to open socket in time");
        }
        int mstimeout = (int) (expirationMs - now);
        if (_runtime_http_params.useSecureSocket()) {
            dbglog(5, String.format("new Secure Socket to %s:%d (timeout=%d)", addr.getHostAddress(), port, mstimeout));
            try {
                int sslFlags = 0;
                if (_hubMode == HubMode.MIXED || _hubMode == HubMode.LEGACY) {
                    sslFlags = YAPI.NO_HOSTNAME_CHECK | YAPI.NO_EXPIRATION_CHECK | YAPI.NO_TRUSTED_CA_CHECK;
                }
                socket = _yctx.CreateSSLSocket(sslFlags);
                socket.connect(sockaddr, mstimeout);
            } catch (IOException e) {
                throw new YAPI_Exception(YAPI.IO_ERROR, e.getLocalizedMessage());
            }

        } else {
            dbglog(5, String.format("new Socket to %s:%d (timeout=%d)", addr.getHostAddress(), port, mstimeout));
            socket = new Socket();
            try {
                socket.connect(sockaddr, mstimeout);
            } catch (IOException e) {
                throw new YAPI_Exception(YAPI.IO_ERROR, e.getLocalizedMessage());
            }
        }
        return socket;
    }

    public String getBaseUrl(boolean withProto, boolean withUserPass, boolean withEndSlash)
    {
        return _runtime_http_params.getUrl(withProto, withUserPass, withEndSlash);
    }
}
