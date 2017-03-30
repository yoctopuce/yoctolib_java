/*********************************************************************
 * $Id: YHTTPHub.java 26934 2017-03-28 08:00:42Z seb $
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


import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

class YHTTPHub extends YGenericHub
{
    public static final int YIO_DEFAULT_TCP_TIMEOUT = 20000;
    private static final int YIO_1_MINUTE_TCP_TIMEOUT = 60000;
    private static final int YIO_10_MINUTES_TCP_TIMEOUT = 600000;

    private final Object _callbackSession;
    private NotificationHandler _notificationHandler;
    private Thread _thread;
    private String _http_realm = "";
    private String _nounce = "";
    private String _serial = "";
    private int _nounce_count = 0;
    private String _ha1 = "";
    private String _opaque = "";
    private Random _randGen = new Random();
    private MessageDigest mdigest;
    private int _authRetryCount = 0;
    private boolean _writeProtected = false;

    private final Object _authLock = new Object();


    boolean needRetryWithAuth()
    {
        synchronized (_authLock) {
            return _http_params.getUser().length() != 0 && _http_params.getPass().length() != 0 && _authRetryCount++ <= 3;
        }
    }

    void authSucceded()
    {
        synchronized (_authLock) {
            _authRetryCount = 0;
        }
    }

    // Update the hub internal variables according
    // to a received header with WWW-Authenticate
    void parseWWWAuthenticate(String header)
    {
        synchronized (_authLock) {
            int pos = header.indexOf("\r\nWWW-Authenticate:");
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

            String tags[] = header.split(" ");
            for (String tag : tags) {
                String parts[] = tag.split("[=\",]");
                String name, value;
                if (parts.length == 2) {
                    name = parts[0];
                    value = parts[1];
                } else if (parts.length == 3) {
                    name = parts[0];
                    value = parts[2];
                } else {
                    continue;
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

            String plaintext = _http_params.getUser() + ":" + _http_realm + ":" + _http_params.getPass();
            mdigest.reset();
            mdigest.update(plaintext.getBytes());
            byte[] digest = this.mdigest.digest();
            _ha1 = YAPIContext._bytesToHexStr(digest, 0, digest.length).toLowerCase();
        }
    }


    // Return an Authorization header for a given request
    String getAuthorization(String request)
    {
        synchronized (_authLock) {
            if (_http_params.getUser().length() == 0 || _http_realm.length() == 0)
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
            mdigest.update(plaintext.getBytes());
            byte[] digest = this.mdigest.digest();
            String ha2 = YAPIContext._bytesToHexStr(digest, 0, digest.length).toLowerCase();
            plaintext = _ha1 + ":" + _nounce + ":" + nc + ":" + cnonce + ":auth:" + ha2;
            this.mdigest.reset();
            this.mdigest.update(plaintext.getBytes());
            digest = this.mdigest.digest();
            String response = YAPIContext._bytesToHexStr(digest, 0, digest.length).toLowerCase();
            //System.out.print(String.format("Auth Resp ha1=%s nonce=%s nc=%s cnouce=%s ha2=%s -> %s\n", _ha1, _nounce, nc, cnonce, ha2, response));
            return String.format(
                    "Authorization: Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", qop=auth, nc=%s, cnonce=\"%s\", response=\"%s\", opaque=\"%s\"\r\n",
                    _http_params.getUser(), _http_realm, _nounce, uri, nc, cnonce, response, _opaque);
        }
    }


    YHTTPHub(YAPIContext yctx, int idx, HTTPParams httpParams, boolean reportConnnectionLost, Object session) throws YAPI_Exception
    {
        super(yctx, httpParams, idx, reportConnnectionLost);
        _callbackSession = session;
        try {
            mdigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "No MD5 provider");
        }
    }


    @Override
    synchronized void startNotifications() throws YAPI_Exception
    {
        if (_notificationHandler != null) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "notification already started");
        }
        if (_http_params.isWebSocket()) {
            _notificationHandler = new WSNotificationHandler(this, _callbackSession);
        } else {
            _notificationHandler = new TCPNotificationHandler(this);
        }
        _thread = new Thread(_notificationHandler, "Notification handler for " + getHost());
        _thread.start();
    }

    @Override
    synchronized void stopNotifications()
    {
        if (_notificationHandler != null) {
            try {
                boolean requestsUnfinished = _notificationHandler.waitAndFreeAsyncTasks(yHTTPRequest.MAX_REQUEST_MS);
                if (requestsUnfinished) {
                    _yctx._Log(String.format("Stop hub %s before all async request has ended", getHost()));
                }
                _thread.interrupt();
                _thread.join(10000);
            } catch (InterruptedException e) {
                _thread = null;
            }
            _notificationHandler = null;
        }
    }

    @Override
    synchronized void release()
    {
    }

    @Override
    String getRootUrl()
    {
        return _http_params.getUrl();
    }

    @Override
    boolean isSameHub(String url, Object request, Object response, Object session)
    {
        HTTPParams params = new HTTPParams(url);
        boolean url_equals = params.getUrl(false, false).equals(_http_params.getUrl(false, false));
        return url_equals && (_callbackSession == null || _callbackSession.equals(session));
    }


    @Override
    synchronized void updateDeviceList(boolean forceupdate) throws YAPI_Exception, InterruptedException
    {

        long now = YAPI.GetTickCount();
        if (forceupdate) {
            _devListExpires = 0;
        }
        if (_devListExpires > now) {
            return;
        }
        if (_notificationHandler == null || !_notificationHandler.isConnected()) {
            if (_reportConnnectionLost) {
                throw new YAPI_Exception(YAPI.TIMEOUT, "hub " + this._http_params.getUrl() + " is not reachable");
            } else {
                return;
            }
        }

        String json_data;
        try {
            json_data = new String(_notificationHandler.hubRequestSync("GET /api.json", null, YIO_DEFAULT_TCP_TIMEOUT), Charset.forName("ISO_8859_1"));
        } catch (YAPI_Exception ex) {
            if (_reportConnnectionLost) {
                throw ex;
            }
            return;
        }

        HashMap<String, ArrayList<YPEntry>> yellowPages = new HashMap<>();
        ArrayList<WPEntry> whitePages = new ArrayList<>();

        YJSONObject loadval;
        try

        {
            loadval = new YJSONObject(json_data);
            loadval.parse();
            if (!loadval.has("services") || !loadval.getYJSONObject("services").has("whitePages")) {
                throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Device "
                        + _http_params.getHost() + " is not a hub");
            }
            _serial = loadval.getYJSONObject("module").getString("serialNumber");
            YJSONArray whitePages_json = loadval.getYJSONObject("services").getYJSONArray("whitePages");
            YJSONObject yellowPages_json = loadval.getYJSONObject("services").getYJSONObject("yellowPages");
            if (loadval.has("network")) {
                String adminpass = loadval.getYJSONObject("network").getString("adminPassword");
                _writeProtected = adminpass.length() > 0;
            }
            // Reindex all functions from yellow pages
            //HashMap<String, Boolean> refresh = new HashMap<String, Boolean>();
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
            throw new YAPI_Exception(YAPI.IO_ERROR,
                    "Request failed, could not parse API result for "
                            + _http_params.getHost(), e);
        }

        updateFromWpAndYp(whitePages, yellowPages);

        // reset device list cache timeout for this hub
        now = YAPI.GetTickCount();
        _devListExpires = now + _devListValidity;
    }

    @Override
    ArrayList<String> firmwareUpdate(String serial, YFirmwareFile firmware, byte[] settings, UpdateProgress progress) throws YAPI_Exception, InterruptedException
    {
        boolean use_self_flash = false;
        String baseurl = "";
        boolean need_reboot = true;
        if (_serial.startsWith("VIRTHUB")) {
            use_self_flash = false;
        } else if (serial.equals(_serial)) {
            use_self_flash = true;
        } else {
            // check if subdevice support self flashing
            try {
                _notificationHandler.hubRequestSync("GET /bySerial/" + serial + "/flash.json?a=state", null, YIO_DEFAULT_TCP_TIMEOUT);
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
        byte[] bytes = _notificationHandler.hubRequestSync("GET " + baseurl + "/flash.json?a=state", null, YIO_DEFAULT_TCP_TIMEOUT);
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
                String startupConfStr = settingsOnly.toString();
                startupConf = startupConfStr.getBytes();
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
            _notificationHandler.hubRequestSync("GET " + baseurl + "/api/module/rebootCountdown?rebootCountdown=-1003", null, YIO_DEFAULT_TCP_TIMEOUT);
            Thread.sleep(7000);
        } else {
            // reboot device to bootloader if needed
            if (need_reboot) {
                // reboot subdevice
                _notificationHandler.hubRequestSync("GET /bySerial/" + serial + "/api/module/rebootCountdown?rebootCountdown=-2", null, YIO_DEFAULT_TCP_TIMEOUT);
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
        if (_notificationHandler == null || !_notificationHandler.isConnected()) {
            throw new YAPI_Exception(YAPI.TIMEOUT, "hub " + this._http_params.getUrl() + " is not reachable");
        }
        if (_writeProtected && !_notificationHandler.hasRwAccess()) {
            throw new YAPI_Exception(YAPI.UNAUTHORIZED, "Access denied: admin credentials required");
        }

        _notificationHandler.devRequestAsync(device, req_first_line, req_head_and_body, asyncResult, asyncContext);
    }

    @Override
    synchronized byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestProgress progress, Object context) throws YAPI_Exception, InterruptedException
    {
        if (_notificationHandler == null || !_notificationHandler.isConnected()) {
            throw new YAPI_Exception(YAPI.TIMEOUT, "hub " + this._http_params.getUrl() + " is not reachable");
        }
        // Setup timeout counter
        int tcpTimeout = YIO_DEFAULT_TCP_TIMEOUT;
        if (req_first_line.contains("/testcb.txt") || req_first_line.contains("/rxmsg.json")
                || req_first_line.contains("/files.json") || req_first_line.contains("/upload.html")) {
            tcpTimeout = YIO_1_MINUTE_TCP_TIMEOUT;
        } else if (req_first_line.contains("/flash.json")) {
            tcpTimeout = YIO_10_MINUTES_TCP_TIMEOUT;
        }
        return _notificationHandler.devRequestSync(device, req_first_line, req_head_and_body, tcpTimeout, progress, context);
    }

    String getHost()
    {
        return _http_params.getHost();
    }

    int getPort()
    {
        return _http_params.getPort();
    }

    @Override
    public synchronized ArrayList<String> getBootloaders() throws YAPI_Exception, InterruptedException
    {
        ArrayList<String> res = new ArrayList<>();
        byte[] raw_data = _notificationHandler.hubRequestSync("GET /flash.json?a=list", null, YIO_DEFAULT_TCP_TIMEOUT);
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
        // ping dot not use Notification handler but a one shot http request
        yHTTPRequest req = new yHTTPRequest(this, "getBootloaders");
        req.RequestSync("GET /api/module/firmwareRelease.json", null, mstimeout);
        return YAPI.SUCCESS;
    }

}
