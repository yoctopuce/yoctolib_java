/*********************************************************************
 *
 * $Id: YHTTPHub.java 17831 2014-09-25 16:31:39Z seb $
 *
 * Internal YHTTPHUB object
 *
 * - - - - - - - - - License information: - - - - - - - - -
 *
 *  Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 *  Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 *  non-exclusive license to use, modify, copy and integrate this
 *  file into your software for the sole purpose of interfacing 
 *  with Yoctopuce products. 
 *
 *  You may reproduce and distribute copies of this file in 
 *  source or object form, as long as the sole purpose of this
 *  code is to interface with Yoctopuce products. You must retain 
 *  this notice in the distributed source file.
 *
 *  You should refer to Yoctopuce General Terms and Conditions
 *  for additional information regarding your rights and 
 *  obligations.
 *
 *  THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *  WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *  WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS 
 *  FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *  EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, 
 *  COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR 
 *  SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT 
 *  LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *  CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *  BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *  WARRANTY, OR OTHERWISE.
 *
 *********************************************************************/
package com.yoctopuce.YoctoAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

class YHTTPHub extends YGenericHub {
    private final static char NOTIFY_NETPKT_NAME = '0';
    private final static char NOTIFY_NETPKT_CHILD = '2';
    private final static char NOTIFY_NETPKT_FUNCNAME = '4';
    private final static char NOTIFY_NETPKT_FUNCVAL = '5';
    private final static char NOTIFY_NETPKT_LOG = '7';
    private final static char NOTIFY_NETPKT_FUNCNAMEYDX = '8';
    private final static char NOTIFY_NETPKT_TIMEV2YDX = 'v';
    private final static char NOTIFY_NETPKT_DEVLOGYDX = 'w';
    private final static char NOTIFY_NETPKT_TIMEVALYDX = 'x';
    private final static char NOTIFY_NETPKT_FUNCVALYDX = 'y';
    private final static char NOTIFY_NETPKT_TIMEAVGYDX = 'z';
    private final static char NOTIFY_NETPKT_NOT_SYNC = '@';
    private static final long YPROG_BOOTLOADER_TIMEOUT = 10000;

    private NotificationHandler _notificationHandler;
    private Thread _thread;
    private final Object    _authLock=new Object();
    private final HTTPParams _http_params;
    private String  _http_realm="";
    private String  _nounce="";
    private String  _serial ="";
    private int     _nounce_count=0;
    private String   _ha1="";
    private String   _opaque="";
    private Random  _randGen=new Random();
    private MessageDigest mdigest;
    private int     _authRetryCount=0;
    private boolean _writeProtected=false;
    private HashMap<YDevice,yHTTPRequest> _httpReqByDev = new HashMap<YDevice, yHTTPRequest>();


     boolean needRetryWithAuth()
    {
        synchronized (_authLock){
            return !(_http_params.geUser().length() == 0 || _http_params.getPass().length() == 0) && _authRetryCount++ <= 3;
        }
    }

     void authSucceded()
    {
        synchronized (_authLock){
            _authRetryCount=0;
        }
    }


    // Update the hub internal variables according
    // to a received header with WWW-Authenticate
    void parseWWWAuthenticate(String header)
    {
        synchronized (_authLock){
                int pos = header.indexOf("\r\nWWW-Authenticate:");
            if(pos == -1) return;
            header = header.substring(pos+19);
            int eol = header.indexOf('\r');
            if(eol >=0) {
                header = header.substring(0, eol);
            }
            _http_realm="";
            _nounce ="";
            _opaque="";
            _nounce_count=0;

            String tags[] = header.split(" ");
            for(String tag : tags) {
                String parts[] = tag.split("[=\",]");
                String name,value;
                if(parts.length==2){
                    name=parts[0];
                    value=parts[1];
                }else if (parts.length==3){
                    name=parts[0];
                    value=parts[2];
                }else{
                    continue;
                }
                if(name.equals("realm")) {
                    _http_realm=value;
                } else if(name.equals("nonce")) {
                    _nounce=value;
                } else if(name.equals("opaque")) {
                    _opaque=value;
                }
            }

            String plaintext = _http_params.geUser()+":"+_http_realm+":"+_http_params.getPass();
            mdigest.reset();
            mdigest.update(plaintext.getBytes());
            byte[] digest = this.mdigest.digest();
            _ha1 = YAPI._bytesToHexStr(digest, 0, digest.length);
        }
    }



    // Return an Authorization header for a given request
    String getAuthorization(String request) throws YAPI_Exception
    {
        synchronized(_authLock) {
            if(_http_params.geUser().length()==0 || _http_realm.length()==0)
                return "";
            _nounce_count++;
            int pos = request.indexOf(' ');
            String method = request.substring(0, pos);
            int enduri = request.indexOf(' ',pos+1);
            if(enduri<0)
                enduri=request.length();
            String uri = request.substring(pos+1, enduri);
            String nc = String.format("%08x", _nounce_count);
            String cnonce = String.format("%08x", _randGen.nextInt());

            String plaintext = method+":"+uri;
            mdigest.reset();
            mdigest.update(plaintext.getBytes());
            byte[] digest = this.mdigest.digest();
            String ha2 = YAPI._bytesToHexStr(digest, 0, digest.length);
            plaintext =_ha1+":"+_nounce+":"+nc+":"+cnonce+":auth:"+ha2;
            this.mdigest.reset();
            this.mdigest.update(plaintext.getBytes());
            digest = this.mdigest.digest();
            String response = YAPI._bytesToHexStr(digest, 0, digest.length);
            return String.format(
                    "Authorization: Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", qop=auth, nc=%s, cnonce=\"%s\", response=\"%s\", opaque=\"%s\"\r\n",
                    _http_params.geUser(), _http_realm, _nounce, uri, nc, cnonce, response, _opaque);
        }
    }

    YHTTPHub(int idx, HTTPParams httpParams, boolean reportConnnectionLost) throws YAPI_Exception
    {
        super(idx, reportConnnectionLost);
        _http_params = httpParams;
        try {
            mdigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new YAPI_Exception(YAPI.NOT_SUPPORTED,"No MD5 provider");
        }
    }

    @Override
    synchronized void startNotifications() throws YAPI_Exception
    {
        if (_notificationHandler != null) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT,"notification already started");
        }
        _notificationHandler = new NotificationHandler();
        _thread = new Thread(_notificationHandler, "Notification handler for " + getHost());
        _thread.start();
    }

    @Override
    synchronized void stopNotifications()
    {
        if (_notificationHandler != null) {
            _thread.interrupt();
            try {
                _thread.join(10000);
            } catch (InterruptedException e) {
                _thread = null;
                _notificationHandler = null;
            }
        }
    }

    @Override
	synchronized void release()
	{
        for( yHTTPRequest req :_httpReqByDev.values()){
            req.WaitRequestEnd();
        }
        _httpReqByDev=null;
	}

	@Override
    String getRootUrl()
    {
        return _http_params.getUrl();
    }

    @Override
    synchronized boolean isSameRootUrl(String url)
    {
        HTTPParams params = new HTTPParams(url);
        return params.getUrl().equals(_http_params.getUrl());
    }

    private class NotificationHandler implements Runnable {
        private static final int NET_HUB_NOT_CONNECTION_TIMEOUT = 6000;
        private long    _notifyPos = -1;
        private int     _notifRetryCount=0;
        private volatile  boolean _sendPingNotification=false;
        private volatile  boolean _connected=false;
        private int _error_delay = 0;



        boolean disconectionDetetcted(){
            return _sendPingNotification && !_connected;
        }

        private void handleNetNotification(String notification_line)
        {
            String ev = notification_line.trim();
    
            if (ev.length() >= 3 && ev.charAt(0) >= NOTIFY_NETPKT_TIMEV2YDX && ev.charAt(0) <= NOTIFY_NETPKT_TIMEAVGYDX) {
                // function value ydx (tiny notification)
                _devListValidity = 10000;
                _notifRetryCount = 0;
                if (_notifyPos >= 0) {
                    _notifyPos += ev.length() + 1;
                }
                int devydx = ev.charAt(1) - 65;// from 'A'
                int funydx = ev.charAt(2) - 48;// from '0'
                if (funydx >= 64) { // high bit of devydx is on second character
                    funydx -= 64;
                    devydx += 128;
                }
                String value = ev.substring(3);
                String serial;
                String funcid;
                if (_serialByYdx.containsKey(devydx)) {
                    serial = _serialByYdx.get(devydx);
                    YDevice ydev = SafeYAPI().getDevice(serial);
                    if(ydev!=null){
                        switch(ev.charAt(0)) {
                            case NOTIFY_NETPKT_FUNCVALYDX:
                                funcid = ydev.getYPEntry(funydx).getFuncId();
                                if (!funcid.equals("")) {
                                    // function value ydx (tiny notification)
                                    SafeYAPI().setFunctionValue(serial + "." + funcid, value);
                                }
                                break;
                            case NOTIFY_NETPKT_DEVLOGYDX:
                                ydev.triggerLogPull();
                                break;
                            case NOTIFY_NETPKT_TIMEVALYDX:
                            case NOTIFY_NETPKT_TIMEAVGYDX:
                            case NOTIFY_NETPKT_TIMEV2YDX:
                                if (funydx == 0xf) {
                                    Integer[] data = new Integer[5];
                                    for(int i = 0; i < 5; i++) {
                                        String part = value.substring(i * 2, i * 2 + 2);
                                        data[i] = Integer.parseInt(part,16);
                                    }
                                    ydev.setDeviceTime(data);
                                } else {
                                    funcid = ydev.getYPEntry(funydx).getFuncId();
                                    if (!funcid.equals("")) {
                                        // timed value report
                                        ArrayList<Integer> report = new ArrayList<Integer>(1+value.length() / 2);
                                        report.add((ev.charAt(0) == NOTIFY_NETPKT_TIMEVALYDX ? 0 :
                                                    (ev.charAt(0) == NOTIFY_NETPKT_TIMEAVGYDX ? 1 : 2)));
                                        for(int pos = 0; pos < value.length(); pos += 2) {
                                            int intval = Integer.parseInt(value.substring(pos,pos + 2),16);
                                            report.add(intval);
                                        }
                                        SafeYAPI().setTimedReport(serial + "." + funcid,ydev.getDeviceTime(), report);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            } else if (ev.length() >= 5 && ev.startsWith("YN01")) {
                _devListValidity = 10000;
                _notifRetryCount = 0;
                if (_notifyPos >= 0) {
                    _notifyPos += ev.length() + 1;
                }
                char notype = ev.charAt(4);
                if (notype == NOTIFY_NETPKT_NOT_SYNC) {
                    _notifyPos = Integer.valueOf(ev.substring(5));
                } else {
                    switch (notype) {
                        case NOTIFY_NETPKT_NAME: // device name change, or arrival
                        case NOTIFY_NETPKT_CHILD: // device plug/unplug
                        case NOTIFY_NETPKT_FUNCNAME: // function name change
                        case NOTIFY_NETPKT_FUNCNAMEYDX: // function name change (ydx)
                            _devListExpires = 0;
                            break;
                        case NOTIFY_NETPKT_FUNCVAL: // function value (long notification)
                            String[] parts = ev.substring(5).split(",");
                            SafeYAPI().setFunctionValue(parts[0] + "." + parts[1],
                                    parts[2]);
                            break;
                    }
                }
            } else {
                // oops, bad notification ? be safe until a good one comes
                _devListValidity = 500;
                _notifyPos = -1;
            }
        }

        @Override
        public void run()
        {
            final yHTTPRequest yreq = new yHTTPRequest(YHTTPHub.this,"Notification of "+_http_params.getHost());
            while (!Thread.currentThread().isInterrupted()) {
                if(_error_delay>0){
                    try {
                        Thread.sleep(_error_delay);
                    } catch (InterruptedException ex) {
                        _connected = false;
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                try {
                    yreq._requestReserve();
                    String notUrl;
                    if(_notifyPos<0){
                        notUrl = "GET /not.byn";
                    }else {
                        notUrl = String.format("GET /not.byn?abs=%d",_notifyPos);
                    }
                    yreq._requestStart(notUrl, null, null, null);
                    _connected=true;
                    String fifo="";
                    do {
                        byte[] partial;
                        yreq._requestProcesss();
                        partial = yreq.getPartialResult();
                        if (partial!=null){
                            fifo += new String(partial);
                        }
                        int pos;
                        do{
                            pos    = fifo.indexOf("\n");
                            if(pos <0) break;
                            if (pos==0 && !_sendPingNotification){
                                try {
                                    yreq.setNoTrafficTimeout(NET_HUB_NOT_CONNECTION_TIMEOUT);
                                    _sendPingNotification=true;
                                } catch (SocketException ex) {
                                    Logger.getLogger(YHTTPHub.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }else{
                                String line = fifo.substring(0, pos+1);
                                handleNetNotification(line);
                            }
                            fifo = fifo.substring(pos+1);
                        }while(pos>=0);
                        _error_delay=0;
                    } while (!Thread.currentThread().isInterrupted());
                    yreq._requestStop();
                    yreq._requestRelease();
                } catch (YAPI_Exception ex) {
                    _connected=false;
                    yreq._requestStop();
                    yreq._requestRelease();
                    _notifRetryCount++;
                    _devListValidity = 500;
                    _error_delay = 100 << (_notifRetryCount>4?4:_notifRetryCount);
                }
            }
            yreq._requestStop();
            yreq._requestRelease();
        }
    }

    @Override
    synchronized void updateDeviceList(boolean forceupdate) throws YAPI_Exception          
    {

        long now = YAPI.GetTickCount();
        if (forceupdate) {
            _devListExpires = 0;
        }
        if (_devListExpires > now) {
            return;
        }

        if(_notificationHandler.disconectionDetetcted()){
            if (_reportConnnectionLost) {
                throw new YAPI_Exception(YAPI.TIMEOUT,"hub "+this._http_params.getUrl()+" is not reachable");
            } else {
                return;
            }            
        }
        
        yHTTPRequest req =  new yHTTPRequest(this,"updateDeviceList "+_http_params.getHost());
        String yreq;
        try {
            yreq = new String(req.RequestSync("GET /api.json",null));
        } catch (YAPI_Exception ex) {
            if (_reportConnnectionLost){
                throw ex;
            }
            return;
        }
        HashMap<String, ArrayList<YPEntry>> yellowPages = new HashMap<String, ArrayList<YPEntry>>();
        ArrayList<WPEntry> whitePages = new ArrayList<WPEntry>();

        JSONObject loadval;
        try {
            loadval = new JSONObject(yreq);
            if (!loadval.has("services") || !loadval.getJSONObject("services").has("whitePages")) {
                throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Device "
                        + _http_params.getHost() + " is not a hub");
            }
            _serial = loadval.getJSONObject("module").getString("serialNumber");
            JSONArray whitePages_json = loadval.getJSONObject("services").getJSONArray("whitePages");
            JSONObject yellowPages_json = loadval.getJSONObject("services").getJSONObject("yellowPages");
            if(loadval.has("network")){
                String adminpass =loadval.getJSONObject("network").getString("adminPassword");
                _writeProtected = adminpass.length()>0;
            }
            // Reindex all functions from yellow pages
            //HashMap<String, Boolean> refresh = new HashMap<String, Boolean>();
            Iterator<?> keys = yellowPages_json.keys();
            while (keys.hasNext()) {
                String classname = keys.next().toString();
                YFunctionType ftype = SafeYAPI().getFnByType(classname);
                JSONArray yprecs_json = yellowPages_json.getJSONArray(classname);
                ArrayList<YPEntry> yprecs_arr = new ArrayList<YPEntry>(
                        yprecs_json.length());
                for (int i = 0; i < yprecs_json.length(); i++) {
                    YPEntry yprec = new YPEntry(yprecs_json.getJSONObject(i));
                    yprecs_arr.add(yprec);
                    ftype.reindexFunction(yprec);
                }
                yellowPages.put(classname, yprecs_arr);
            }

            // Reindex all devices from white pages
            for (int i = 0; i < whitePages_json.length(); i++) {
                WPEntry devinfo = new WPEntry(whitePages_json.getJSONObject(i));
                _serialByYdx.put(devinfo.getIndex(), devinfo.getSerialNumber());
                whitePages.add(devinfo);
            }
        } catch (JSONException e) {
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
        boolean use_self_flash = true;
        String baseurl = "";

        yHTTPRequest req = new yHTTPRequest(this, "hubFUpdate" + serial);


        if (!serial.equals(_serial)) {
            // check if subdevice support self flashing
            try {
                req.RequestSync("GET /bySerial/" + serial + "/flash.json?a=state", null);
                baseurl = "/bySerial/" + serial;
            } catch (YAPI_Exception ex) {
                use_self_flash = false;
            }
        }


        //5% -> 10%
        progress.firmware_progress(5, "Enter in bootloader");
        if (!use_self_flash) {
            // reboot subdevice
            req.RequestSync("GET /bySerial/" + serial + "/api/module/rebootCountdown?rebootCountdown=-1", null);
        }

        //10% -> 40%
        progress.firmware_progress(10, "Send firmware to bootloader");
        byte[] head_body = YDevice.formatHTTPUpload("firmware", firmware.getData());
        req.RequestSync("POST " + baseurl + "/upload.html", head_body);
        //check firmware upload result
        byte[] bytes = req.RequestSync("GET " + baseurl + "/flash.json?a=state", null);
        String uploadresstr = new String(bytes);
        try {
            JSONObject uploadres = new JSONObject(uploadresstr);
            if (!uploadres.getString("state").equals("valid")){
                throw new YAPI_Exception(YAPI.IO_ERROR, "Upload of firmware failed: invalid firmware("+uploadres.getString("state")+")");
            }
            if (uploadres.getInt("progress")!=100){
                throw new YAPI_Exception(YAPI.IO_ERROR, "Upload of firmware failed: incomplete upload");
            }
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "invalid json response :"+ex.getLocalizedMessage());
        }
        if (use_self_flash) {
            progress.firmware_progress(20, "Upload startupConf.json");
            head_body = YDevice.formatHTTPUpload("startupConf.json", settings);
            req.RequestSync("POST " + baseurl + "/upload.html", head_body);
            progress.firmware_progress(20, "Upload firmwareConf");
            head_body = YDevice.formatHTTPUpload("firmwareConf", settings);
            req.RequestSync("POST " + baseurl + "/upload.html", head_body);
        }

        //40%-> 80%
        progress.firmware_progress(40, "Flash firmware");
        if (use_self_flash) {
            // the hub itself -> reboot in autoflash mode
            req.RequestSync("GET " + baseurl + "/api/module/rebootCountdown?rebootCountdown=-1003", null);
            Thread.sleep(7000);
        } else {
            // verify that the device is in bootloader
            long timeout = YAPI.GetTickCount() + YPROG_BOOTLOADER_TIMEOUT;
            byte[] res;
            boolean found = false;
            do {
                res = req.RequestSync("GET /flash.json?a=list", null);
                String jsonstr = new String(res);
                try {
                    JSONObject flashres = new JSONObject(jsonstr);
                    JSONArray list = flashres.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        if (list.getString(i).equals(serial)) {
                            found = true;
                            break;
                        }
                    }
                } catch (JSONException ex) {
                    throw new YAPI_Exception(YAPI.IO_ERROR, "Unable to retrieve bootloader list");
                }
                if (!found) {
                    Thread.sleep(100);
                }
            } while (!found && YAPI.GetTickCount() < timeout);
            //start flash
            res = req.RequestSync("GET /flash.json?a=flash&s=" + serial , null);
            try {
                String jsonstr = new String(res);
                JSONObject flashres = new JSONObject(jsonstr);
                JSONArray list = flashres.getJSONArray("logs");
                ArrayList<String> logs = new ArrayList<String>(list.length());
                for (int i = 0; i < list.length(); i++) {
                    logs.add(list.getString(i));
                }
                return logs;
            } catch (JSONException ex) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "invalid response");
            }
        }

        return null;
    }

    @Override
    synchronized void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception
    {
        if(_notificationHandler.disconectionDetetcted()){
            throw new YAPI_Exception(YAPI.TIMEOUT,"hub "+this._http_params.getUrl()+" is not reachable");
        }

        if (!_httpReqByDev.containsKey(device)){
            _httpReqByDev.put(device, new yHTTPRequest(this,"Device " + device.getSerialNumber()));
        }
        yHTTPRequest req = _httpReqByDev.get(device);
        if(_writeProtected && !_http_params.geUser().equals("admin")){
            throw new YAPI_Exception(YAPI.UNAUTHORIZED,"Access denied: admin credentials required");
        }
        req.RequestAsync(req_first_line,req_head_and_body,asyncResult,asyncContext);
    }

    @Override
    synchronized byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body) throws YAPI_Exception
    {
        if(_notificationHandler.disconectionDetetcted()){
            throw new YAPI_Exception(YAPI.TIMEOUT,"hub "+this._http_params.getUrl()+" is not reachable");
        }

        if (!_httpReqByDev.containsKey(device)){
            _httpReqByDev.put(device, new yHTTPRequest(this,"Device " + device.getSerialNumber()));
        }
        yHTTPRequest req = _httpReqByDev.get(device);
        return req.RequestSync(req_first_line,req_head_and_body);
    }

    String getHost()
    {
        return _http_params.getHost();
    }

    int getPort()
    {
        return _http_params.getPort();
    }


}
