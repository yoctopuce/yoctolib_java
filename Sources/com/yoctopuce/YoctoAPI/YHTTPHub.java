/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.YoctoAPI;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class YHTTPHub extends YGenericHub {

    protected URL url;
    private NotificationThread thread;
    private String  _http_host="";
    private int     _http_port=4444;
    private String  _http_user="";
    private String  _http_pass="";
    private String  _http_realm="";
    private String  _nounce="";
    private int     _nounce_count=0;
    private String   _ha1="";
    private String   _opaque="";
    private Random  _randGen=new Random();
    private MessageDigest mdigest;
    private long    _notifyPos = -1;
    private int     _notifRetryCount=0;
    private int     _authRetryCount=0;
    private boolean _writeProtected=false;

    private HashMap<YDevice,yHTTPRequest> _httpReqByDev = new HashMap<YDevice, yHTTPRequest>();


    synchronized boolean needRetryWithAuth()
    {
        if (_http_user.length()==0 || _http_pass.length()==0)
            return false;

        if(_authRetryCount++>3){
            return false;
        }
        return true;
    }

    synchronized  void authSucceded()
    {
        _authRetryCount=0;
    }


    // Update the hub internal variables according
    // to a received header with WWW-Authenticate
    synchronized void parseWWWAuthenticate(String header)
    {
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

        String plaintext = _http_user+":"+_http_realm+":"+_http_pass;
        mdigest.reset();
        mdigest.update(plaintext.getBytes());
        byte[] digest = this.mdigest.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        _ha1 = bigInt.toString(16);

    }



    // Return an Authorization header for a given request
    synchronized String getAuthorization(String request) throws YAPI_Exception
    {
        if(_http_user.length()==0 || _http_realm.length()==0)
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
        BigInteger bigInt = new BigInteger(1,digest);
        String ha2 = bigInt.toString(16);
        plaintext =_ha1+":"+_nounce+":"+nc+":"+cnonce+":auth:"+ha2;
        this.mdigest.reset();
        this.mdigest.update(plaintext.getBytes());
        digest = this.mdigest.digest();
        bigInt = new BigInteger(1,digest);
        String reponse = bigInt.toString(16);


         String res = String.format(
                 "Authorization: Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", qop=auth, nc=%s, cnonce=\"%s\", response=\"%s\", opaque=\"%s\"\r\n",
                 _http_user,_http_realm,_nounce,uri,nc,cnonce,reponse,_opaque);
        return res;
    }





    String getHttpHost()
    {
        return _http_host;
    }

    String getHttpPass()
    {
        return _http_pass;
    }

    int getHttpPort()
    {
        return _http_port;
    }

    String getHttpUser()
    {
        return _http_user;
    }




    private String _cleanRegisteredUrl(String url)
    {
        int pos = 0;
        if (url.startsWith("http://")) {
            pos =7;
        }

        int end_auth = url.indexOf('@',pos);
        int end_user = url.indexOf(':',pos);
        if(end_auth>0 && end_user>0 && end_user < end_auth) {
            _http_user = url.substring(pos, end_user);
            _http_pass = url.substring(end_user+1,end_auth);
            pos = end_auth+1;
        }

        int end_url = url.indexOf('/',pos);
        if (end_url<0)
            end_url = url.length();

        int portpos = url.indexOf(':', pos);
        if (portpos>0 && portpos < end_url) {
            _http_host = url.substring(pos,portpos);
            _http_port = Integer.parseInt(url.substring(portpos,end_url));
        }else {
            _http_host = url.substring(pos,end_url);
        }
        return url;
    }

    public YHTTPHub(int idx, String url_str) throws YAPI_Exception
    {
        super(idx);
        _cleanRegisteredUrl(url_str);
        try {
            mdigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new YAPI_Exception(YAPI.NOT_SUPPORTED,"No MD5 provider");
        }
    }

    @Override
    public void startNotifications() throws YAPI_Exception
    {
        _notifyPos =-1;
        thread = new YHTTPHub.NotificationThread();
        thread.setupConnection(this);
        thread.setName("Notif_"+_http_host);
        thread.start();
    }

    @Override
    public void stopNotifications()
    {
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join(10000);
            } catch (InterruptedException ex) {
            }
        }
        thread = null;
    }

    
    
    
    @Override
	public void release()
	{
        for( yHTTPRequest req :_httpReqByDev.values()){
            req.WaitRequestEnd();
        }
        _httpReqByDev=null;
	}

	@Override
    public String getRootUrl()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isSameRootUrl(String url)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class NotificationThread extends Thread {
        private yHTTPRequest _yreq;

        public void setupConnection(YHTTPHub hub)
        {
            _yreq = new yHTTPRequest(hub,"Notification of "+_http_host);
        }

        private void handleNetNotification(String notification_line)
        {
            String ev = notification_line.trim();
            //System.out.println(notification_line);
            if (ev.length() >= 3 && ev.charAt(0) == 'y') {
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
                //System.out.println("tiny notification for "+devydx);
                if (_serialByYdx.containsKey(devydx)) {
                    serial = _serialByYdx.get(devydx);
                    funcid = YAPI.getDevice(serial).functionId(funydx);
                    if (!funcid.equals("")) {
                        //System.out.println("new value ("+value+") tiny notification for"+funcid);
                        YAPI.setFunctionValue(serial + "." + funcid, value);
                    }
                }
            } else if (ev.length() >= 5 && ev.startsWith("YN01")) {
                _devListValidity = 10000;
                _notifRetryCount = 0;
                if (_notifyPos >= 0) {
                    _notifyPos += ev.length() + 1;
                }
                char notype = ev.charAt(4);
                if (notype == '@') {
                    _notifyPos = Integer.valueOf(ev.substring(5));
                } else {
                    switch (notype) {
                        case '0': // device name change, or arrival
                        case '2': // device plug/unplug
                        case '4': // function name change
                        case '8': // function name change (ydx)
                            _devListExpires = 0;
                            break;
                        case 5: // function value (long notification)
                            String[] parts = ev.substring(5).split(",");
                            //System.out.println("new value ("+parts[2]+") notification for"+parts[0] + "." + parts[1]);
                            YAPI.setFunctionValue(parts[0] + "." + parts[1],
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
                //YSPRINTF(buffer, 256,"GET /not.byn?abs=%d HTTP/1.1\r\n\r\n" , hub->notifAbsPos);
                try {
                    _yreq._requestReserve();
                    String notUrl;
                    if(_notifyPos<0){
                        notUrl = "GET /not.byn\r\n";
                    }else {
                        notUrl = String.format("GET /not.byn?abs=%d\r\n",_notifyPos);
                    }
                    _yreq._requestStart(notUrl,null);
                    String fifo="";
                    do {
                        _yreq._requestProcesss();
                        byte[] partial =_yreq.getPartialResult();
                        if (partial!=null){
                            fifo += new String(partial);
                        }
                        int pos;
                        do{
                            pos    = fifo.indexOf("\n");
                            if( pos>0){
                                String line = fifo.substring(0, pos+1);
                                handleNetNotification(line);
                                fifo = fifo.substring(pos+1);
                            }
                        }while(pos>0);
                    }while (!isInterrupted());
                    _yreq._requestStop();
                    _yreq._requestRelease();

                } catch (YAPI_Exception ex) {
                    _yreq._requestStop();
                    _yreq._requestRelease();
                    _notifRetryCount++;
                    _devListValidity = 500;
                    if (isInterrupted()) {
                        return;
                    }
                    long delay = 500 << _notifRetryCount;
                    if (delay > 8000) {
                        delay = 8000;
                    }
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex1) {
                    }
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
    void updateDeviceList(boolean forceupdate) throws YAPI_Exception
    {

        long now = YAPI.GetTickCount();
        if (forceupdate) {
            _devListExpires = 0;
        }
        if (_devListExpires > now) {
            return;
        }

        yHTTPRequest req =  new yHTTPRequest(this,"updateDeviceList "+_http_host);


        String yreq = new String(req.RequestSync("GET /api.json\r\n",null));
        HashMap<String, ArrayList<YPEntry>> yellowPages = new HashMap<String, ArrayList<YPEntry>>();
        ArrayList<WPEntry> whitePages = new ArrayList<WPEntry>();


        JSONObject loadval;
        try {
            loadval = new JSONObject(yreq);
            if (!loadval.has("services") || !loadval.getJSONObject("services").has("whitePages")) {
                throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Device "
                        + _http_host + " is not a hub");
            }

            JSONArray whitePages_json = loadval.getJSONObject("services").getJSONArray("whitePages");
            JSONObject yellowPages_json = loadval.getJSONObject("services").getJSONObject("yellowPages");
            if(loadval.has("network")){
                String adminpass =loadval.getJSONObject("network").getString("adminPassword");
                if(adminpass.length()>0){
                    _writeProtected=true;
                }else{
                    _writeProtected=false;
                }
            }


            // Reindex all functions from yellow pages
            //HashMap<String, Boolean> refresh = new HashMap<String, Boolean>();
            Iterator<?> keys = yellowPages_json.keys();
            while (keys.hasNext()) {
                String classname = keys.next().toString();
                YFunctionType ftype = YAPI.getFnByType(classname);
                JSONArray yprecs_json = yellowPages_json.getJSONArray(classname);
                ArrayList<YPEntry> yprecs_arr = new ArrayList<YPEntry>(
                        yprecs_json.length());
                for (int i = 0; i < yprecs_json.length(); i++) {
                    YPEntry yprec = new YPEntry(yprecs_json.getJSONObject(i));
                    yprecs_arr.add(yprec);
                    if (ftype.reindexFunction(yprec.getHardwareId(),
                            yprec.getLogicalName(), yprec.getAdvertisedValue())) {
                    }
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
                    + _http_host, e);
        }
        updateFromWpAndYp(whitePages, yellowPages);

        // reset device list cache timeout for this hub
        now = YAPI.GetTickCount();
        _devListExpires = now + _devListValidity;
    }

    @Override
    public byte[] devRequest(YDevice device,String req_first_line, byte[] req_head_and_body, Boolean async) throws YAPI_Exception
    {
        if (!_httpReqByDev.containsKey(device)){
            _httpReqByDev.put(device, new yHTTPRequest(this,"Device "+device.getSerialNumber()));
        }
        yHTTPRequest req = _httpReqByDev.get(device);
        if (!async) {
            return req.RequestSync(req_first_line,req_head_and_body);
        } else {
            if(_writeProtected && !_http_user.equals("admin")){
                throw new YAPI_Exception(YAPI.UNAUTHORIZED,"Access denied: admin credentials required");
            }
            req.RequestAsync(req_first_line,req_head_and_body);
            return null;
        }
    }
}
