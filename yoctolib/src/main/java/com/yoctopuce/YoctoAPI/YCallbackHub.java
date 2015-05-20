/*********************************************************************
 *
 * $Id: YCallbackHub.java 20332 2015-05-12 15:57:31Z seb $
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

public class YCallbackHub extends YGenericHub
{
    private final HTTPParams _http_params;
    private final OutputStream _out;
    private JSONObject _callbackCache;

    YCallbackHub(int idx, HTTPParams httpParams, InputStream request, OutputStream response) throws YAPI_Exception
    {
        super(idx, true);
        _http_params = httpParams;
        _out = response;
        if (request == null || _out == null) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Use RegisterHub(String url, BufferedReader request, PrintWriter response) to start api in callback");
        }
        try {
            loadCallbackCache(request);
        } catch (IOException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
        }
    }

    @Override
    void release()
    {
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

    @Override
    void startNotifications() throws YAPI_Exception
    {
        // nothing to do since there is no notification
        // in this mode
    }

    @Override
    void stopNotifications()
    {
        // nothing to do since there is no notification
        // in this mode
    }

    private void _output(String msg) throws IOException
    {
        _out.write(msg.getBytes(YAPI.DeviceCharset));
    }

    private void loadCallbackCache(InputStream in) throws YAPI_Exception, IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        String data_str = buffer.toString(YAPI.DefaultEncoding);

        if (data_str.length() == 0) {
            String errmsg = "RegisterHub(callback) used without posting YoctoAPI data";
            _output("\n!YoctoAPI:" + errmsg + "\n");
            _callbackCache = null;
            throw new YAPI_Exception(YAPI.IO_ERROR, errmsg);
        } else {
            try {
                _callbackCache = new JSONObject(data_str);
            } catch (JSONException ex) {
                String errmsg = "invalid data:[\n" + ex.toString() + data_str + "\n]";
                _output("\n!YoctoAPI:" + errmsg + "\n");
                _callbackCache = null;
                throw new YAPI_Exception(YAPI.IO_ERROR, errmsg);
            }
            if (!_http_params.getPass().equals("")) {
                MessageDigest mdigest;
                try {
                    mdigest = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException ex) {
                    throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "No MD5 provider");
                }

                // callback data signed, verify signature
                if (!_callbackCache.has("sign")) {
                    String errmsg = "missing signature from incoming YoctoHub (callback password required)";
                    _output("\n!YoctoAPI:" + errmsg + "\n");
                    _callbackCache = null;
                    throw new YAPI_Exception(YAPI.UNAUTHORIZED, errmsg);
                }
                String sign = _callbackCache.optString("sign");
                String pass = _http_params.getPass();
                String salt;
                if (pass.length() == 32) {
                    salt = pass.toLowerCase();
                } else {
                    mdigest.reset();
                    mdigest.update(pass.getBytes(YAPI.DeviceCharset));
                    byte[] md5pass = mdigest.digest();
                    salt = YAPI._bytesToHexStr(md5pass, 0, md5pass.length);
                }

                data_str = data_str.replace(sign, salt);
                mdigest.reset();
                mdigest.update(data_str.getBytes(YAPI.DefaultEncoding));
                byte[] md5 = mdigest.digest();
                String check = YAPI._bytesToHexStr(md5, 0, md5.length);
                if (!check.equals(sign)) {
                    String errmsg = "invalid signature from incoming YoctoHub (invalid callback password)";
                    _output("\n!YoctoAPI:" + errmsg + "\n");
                    _callbackCache = null;
                    throw new YAPI_Exception(YAPI.UNAUTHORIZED, errmsg);
                }
            }
        }
    }

    private byte[] cachedRequest(String query, byte[] header_and_body) throws YAPI_Exception, IOException
    {
        // apply POST remotely
        int endline = query.indexOf("\r");
        if (endline >= 0) {
            query = query.substring(0, endline);
        }

        if (query.startsWith("POST ")) {
            String boundary = "???";
            int body_start = YAPI._find_in_bytes(header_and_body, "\r\n\r\n".getBytes(YAPI.DefaultEncoding)) + 4;
            int endb;
            for (endb = body_start; endb < header_and_body.length; endb++) {
                if (header_and_body[endb] == 13) break;
            }
            String tmp = new String(header_and_body, body_start, 2);
            if (tmp.equals("--") && endb > body_start + 2 && endb < body_start + 20) {
                boundary = new String(header_and_body, body_start + 2, endb - body_start - 2);
            }
            int bodylen = header_and_body.length - body_start;
            _output("\n@YoctoAPI:" + query + " " + Integer.toString(bodylen) + ":" + boundary + "\n");
            _out.write(header_and_body, body_start, bodylen);
            return "".getBytes(YAPI.DefaultEncoding);
        }
        if (!query.startsWith("GET "))
            return null;
        // remove JZON trigger if present (not relevant in callback mode)
        int jzon = query.indexOf("?fw=");
        if (jzon >= 0 && query.indexOf('&', jzon) < 0) {
            query = query.substring(0, jzon);
        }
        // dispatch between cached get and remote set
        if (!query.contains("?") ||
                query.contains("/logs.txt") ||
                query.contains("/logger.json") ||
                query.contains("/ping.txt") ||
                query.contains("/files.json?a=dir")) {
            try {
                // read request, load from cache
                String[] parts = query.split(" ");
                String url = parts[1];
                boolean getmodule = url.contains("api/module.json");
                if (getmodule) {
                    url = url.replace("api/module.json", "api.json");
                }
                if (!_callbackCache.has(url)) {
                    _output("\n!YoctoAPI:" + url + " is not preloaded, adding to list");
                    _output("\n@YoctoAPI:+" + url + "\n");
                    return null;
                }
                JSONObject jsonres = _callbackCache.getJSONObject(url);
                if (getmodule) {
                    jsonres = jsonres.getJSONObject("module");
                }
                return (jsonres.toString()).getBytes(YAPI.DefaultEncoding);
            } catch (JSONException ex) {
                return "".getBytes();
            }
        } else {
            // change request, print to output stream
            _output("\n@YoctoAPI:" + query + "\n");
            return "".getBytes(YAPI.DeviceCharset);
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
        String yreq;
        try {
            yreq = new String(cachedRequest("GET /api.json\r\n", null));
        } catch (IOException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
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
    public ArrayList<String> getBootloaders() throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "Firmware update is not supported in HTTP callback");
    }

    @Override
    public int ping(int mstimeout) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "Not yet implemented");
    }

    @Override
    ArrayList<String> firmwareUpdate(String serial, YFirmwareFile firmware, byte[] settings, UpdateProgress progress) throws YAPI_Exception, InterruptedException
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "Firmware update is not supported in HTTP callback");
    }


    @Override
    void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception
    {
        try {
            cachedRequest(req_first_line, req_head_and_body);
        } catch (IOException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
        }
    }

    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body) throws YAPI_Exception
    {
        try {
            return cachedRequest(req_first_line, req_head_and_body);
        } catch (IOException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
        }
    }
}
