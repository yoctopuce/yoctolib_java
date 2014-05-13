/*********************************************************************
 *
 * $Id: YHTTPHub.java 15999 2014-05-01 08:28:57Z seb $
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
    private final static char NOTIFY_NETPKT_DEVLOGYDX = 'w';
    private final static char NOTIFY_NETPKT_TIMEVALYDX = 'x';
    private final static char NOTIFY_NETPKT_FUNCVALYDX = 'y';
    private final static char NOTIFY_NETPKT_TIMEAVGYDX = 'z';
    private final static char NOTIFY_NETPKT_NOT_SYNC = '@';

    private NotificationThread thread;
    private final Object    _authLock=new Object();
    private HTTPParams _http_params;
    private String  _http_realm="";
    private String  _nounce="";
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

    YHTTPHub(int idx, HTTPParams httpParams) throws YAPI_Exception
    {
        super(idx);
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
        thread = new YHTTPHub.NotificationThread();
        thread.setupConnection(this);
        synchronized (_authLock){
            thread.setName("Notif_"+_http_params.getHost());
        }
        thread.start();
    }

    @Override
    synchronized void stopNotifications()
    {
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join(10000);
            } catch (InterruptedException ignored) {
            }
        }
        thread = null;
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
        synchronized (_authLock){
	        return _http_params.getUrl();
        }
    }

    @Override
    synchronized boolean isSameRootUrl(String url)
    {
        HTTPParams params = new HTTPParams(url);
        synchronized (_authLock){
            return params.getUrl().equals(_http_params.getUrl());
        }
    }

    private class NotificationThread extends Thread {
        private static final int NET_HUB_NOT_CONNECTION_TIMEOUT = 6000;
        private yHTTPRequest _yreq;
        private long    _notifyPos = -1;
        private int     _notifRetryCount=0;
        private boolean _sendPingNotification=false;
        private boolean _connected=false;
        private int _error_delay;


        public void setupConnection(YHTTPHub hub)
        {
            _yreq = new yHTTPRequest(hub,"Notification of "+_http_params.getHost());
        }

        boolean disconectionDetetcted(){
            return _sendPingNotification && !_connected;
        }

        private void handleNetNotification(String notification_line)
        {
            String ev = notification_line.trim();
    
            if (ev.length() >= 3 && ev.charAt(0) >= NOTIFY_NETPKT_DEVLOGYDX && ev.charAt(0) <= NOTIFY_NETPKT_TIMEAVGYDX) {
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
                                        report.add((ev.charAt(0) == NOTIFY_NETPKT_TIMEAVGYDX ? 1 : 0));
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
                            //System.out.println("new value ("+parts[2]+") notification for"+parts[0] + "." + parts[1]);
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
            while (!isInterrupted()) {
                if(_error_delay>0){
                    try {
                        Thread.sleep(_error_delay);
                    } catch (InterruptedException ex) {
                        if(isInterrupted())
                            return;
                    }
                }
                try {
                    _yreq._requestReserve();
                    String notUrl;
                    if(_notifyPos<0){
                        notUrl = "GET /not.byn";
                    }else {
                        notUrl = String.format("GET /not.byn?abs=%d",_notifyPos);
                    }
                    _yreq._requestStart(notUrl,null,null,null);
                    _connected=true;
                    String fifo="";
                    do {
                        byte[] partial;
                        _yreq._requestProcesss();
                        partial =_yreq.getPartialResult();
                        if (partial!=null){
                            fifo += new String(partial);
                        }
                        int pos;
                        do{
                            pos    = fifo.indexOf("\n");
                            if(pos <0) break;
                            if (pos==0 && !_sendPingNotification){
                                try {
                                    _yreq.setNoTrafficTimeout(NET_HUB_NOT_CONNECTION_TIMEOUT);
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
                    }while (!isInterrupted());
                    _yreq._requestStop();
                    _yreq._requestRelease();
                } catch (YAPI_Exception ex) {
                    _connected=false;
                    _yreq._requestStop();
                    _yreq._requestRelease();
                    _notifRetryCount++;
                    _devListValidity = 500;
                    if (isInterrupted()) {
                        return;
                    }
                    _error_delay = 500 << (_notifRetryCount>4?4:_notifRetryCount);
                }
            }
        }

        @Override
        public void interrupt()
        {
            super.interrupt();
            _yreq._requestStop();
            _yreq._requestRelease();
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

        if(thread.disconectionDetetcted()){
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
    void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception
    {
        if(thread.disconectionDetetcted()){
            throw new YAPI_Exception(YAPI.TIMEOUT,"hub "+this._http_params.getUrl()+" is not reachable");
        }

        if (!_httpReqByDev.containsKey(device)){
            _httpReqByDev.put(device, new yHTTPRequest(this,"Device "+device.getSerialNumber()));
        }
        yHTTPRequest req = _httpReqByDev.get(device);
        if(_writeProtected && !_http_params.geUser().equals("admin")){
            throw new YAPI_Exception(YAPI.UNAUTHORIZED,"Access denied: admin credentials required");
        }
        req.RequestAsync(req_first_line,req_head_and_body,asyncResult,asyncContext);
    }

    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body) throws YAPI_Exception
    {
        if(thread.disconectionDetetcted()){
            throw new YAPI_Exception(YAPI.TIMEOUT,"hub "+this._http_params.getUrl()+" is not reachable");
        }

        if (!_httpReqByDev.containsKey(device)){
            _httpReqByDev.put(device, new yHTTPRequest(this,"Device "+device.getSerialNumber()));
        }
        yHTTPRequest req = _httpReqByDev.get(device);
        return req.RequestSync(req_first_line,req_head_and_body);
    }

    synchronized  String getHost()
    {
        return _http_params.getHost();
    }

    synchronized  int getPort()
    {
        return _http_params.getPort();
    }


}
