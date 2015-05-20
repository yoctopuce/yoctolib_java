/*********************************************************************
 *
 * $Id: YDevice.java 20376 2015-05-19 14:18:47Z seb $
 *
 * Internal YDevice class
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

//
// YDevice Class (used internally)
//
// This class is used to store everything we know about connected Yocto-Devices.
// Instances are created when devices are discovered in the white pages
// (or registered manually, for root hubs) and then used to keep track of
// device naming changes. When a device or a function is renamed, this
// object forces the local indexes to be immediately updated, even if not
// yet fully propagated through the yellow pages of the device hub.
//
// In order to regroup multiple function queries on the same physical device,
// this class implements a device-wide API string cache (agnostic of API content).
// This is in addition to the function-specific cache implemented in YFunction.
//
public class YDevice
{
    private YGenericHub _hub;
    private WPEntry _wpRec;
    private long _cache_expiration;
    private String _cache_json;
    private HashMap<Integer, YPEntry> _ypRecs;
    private double _deviceTime;
    private YPEntry _moduleYPEntry;
    private YModule.LogCallback _logCallback = null;
    private int _logpos = 0;
    private boolean _logIsPulling = false;

    // Device constructor. Automatically call the YAPI functin to reindex device
    YDevice(YGenericHub hub, WPEntry wpRec, HashMap<String, ArrayList<YPEntry>> ypRecs) throws YAPI_Exception
    {
        // private attributes
        _hub = hub;
        _wpRec = wpRec;
        _cache_expiration = 0;
        _cache_json = "";
        _moduleYPEntry = new YPEntry(wpRec.getSerialNumber(), "module");
        _moduleYPEntry.setLogicalName(wpRec.getLogicalName());
        _ypRecs = new HashMap<Integer, YPEntry>();
        for (String categ : ypRecs.keySet()) {
            for (YPEntry rec : ypRecs.get(categ)) {
                if (rec.getSerial().equals(wpRec.getSerialNumber())) {
                    int funydx = rec.getIndex();
                    _ypRecs.put(funydx, rec);
                }
            }
        }
        SafeYAPI().reindexDevice(this);
    }

    YGenericHub getHub()
    {
        return _hub;
    }

    // Return the serial number of the device, as found during discovery
    public String getSerialNumber()
    {
        return _wpRec.getSerialNumber();
    }

    // Return the logical name of the device, as found during discovery
    public String getLogicalName()
    {
        return _wpRec.getLogicalName();
    }

    // Return the product name of the device, as found during discovery
    public String getProductName()
    {
        return _wpRec.getProductName();
    }

    // Return the product Id of the device, as found during discovery
    public int getProductId()
    {
        return _wpRec.getProductId();
    }

    String getRelativePath()
    {
        return _wpRec.getNetworkUrl();
    }

    // Return the beacon state of the device, as found during discovery
    public int getBeacon()
    {
        return _wpRec.getBeacon();
    }

    // Return the hub-specific devYdx of the device, as found during discovery
    public int getDevYdx()
    {
        return _wpRec.getIndex();
    }


    // Get the whole REST API string for a device, from cache if possible
    public String requestAPI() throws YAPI_Exception
    {
        if (_cache_expiration > YAPI.GetTickCount()) {
            return _cache_json;
        }
        String yreq = requestHTTPSyncAsString("GET /api.json", null);
        this._cache_expiration = YAPI.GetTickCount() + SafeYAPI().DefaultCacheValidity;
        this._cache_json = yreq;
        return yreq;
    }

    // Reload a device API (store in cache), and update YAPI function lists accordingly
    // Intended to be called within UpdateDeviceList only
    public int refresh() throws YAPI_Exception
    {
        String result = this.requestAPI();
        JSONObject loadval;
        Boolean reindex = false;
        try {
            loadval = new JSONObject(result);


            _cache_expiration = YAPI.GetTickCount() + SafeYAPI().DefaultCacheValidity;
            _cache_json = result;

            // parse module and refresh names if needed
            Iterator<?> keys = loadval.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (key.equals("module")) {
                    JSONObject module = loadval.getJSONObject("module");
                    if (!_wpRec.getLogicalName().equals(module.getString("logicalName"))) {
                        _wpRec.setLogicalName(module.getString("logicalName"));
                        _moduleYPEntry.setLogicalName(_wpRec.getLogicalName());
                        reindex = true;
                    }
                    _wpRec.setBeacon(module.getInt("beacon"));
                } else if (!key.equals("services")) {
                    JSONObject func = loadval.getJSONObject(key);
                    String name;
                    if (func.has("logicalName")) {
                        name = func.getString("logicalName");
                    } else {
                        name = _wpRec.getLogicalName();
                    }
                    if (func.has("advertisedValue")) {
                        String pubval = func.getString("advertisedValue");
                        SafeYAPI().setFunctionValue(_wpRec.getSerialNumber(), pubval);
                    }
                    for (int f = 0; f < _ypRecs.size(); f++) {
                        if (_ypRecs.get(f).getFuncId().equals(key)) {
                            if (!_ypRecs.get(f).getLogicalName().equals(name)) {
                                _ypRecs.get(f).setLogicalName(name);
                                reindex = true;
                            }
                            break;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "Request failed, could not parse API result");
        }

        if (reindex) {
            SafeYAPI().reindexDevice(this);
        }
        return YAPI.SUCCESS;
    }

    // Force the REST API string in cache to expire immediately
    public void dropCache()
    {
        _cache_expiration = 0;
    }

    // Retrieve the number of functions (beside "module") in the device
    protected int functionCount()
    {
        return _ypRecs.size();
    }


    YPEntry getYPEntry(int idx)
    {
        if (idx < _ypRecs.size()) {
            return _ypRecs.get(idx);
        }
        return null;
    }


    byte[] requestHTTPSync(String request, byte[] rest_of_request) throws YAPI_Exception
    {
        String shortRequest = formatRequest(request);
        return _hub.devRequestSync(this, shortRequest, rest_of_request);
    }

    String requestHTTPSyncAsString(String request, byte[] rest_of_request) throws YAPI_Exception
    {
        String shortRequest = formatRequest(request);
        final byte[] bytes = _hub.devRequestSync(this, shortRequest, rest_of_request);
        return new String(bytes, YAPI.DeviceCharset);
    }

    void requestHTTPAsync(String request, byte[] rest_of_request, YGenericHub.RequestAsyncResult asyncResult, Object context) throws YAPI_Exception
    {
        String shortRequest = formatRequest(request);
        _hub.devRequestAsync(this, shortRequest, rest_of_request, asyncResult, context);
    }

    private String formatRequest(String request) throws YAPI_Exception
    {
        String[] words = request.split(" ");
        if (words.length < 2) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT,
                    "Invalid request, not enough words; expected a method name and a URL");
        }
        String relativeUrl = words[1];
        if (relativeUrl.charAt(0) != '/') {
            relativeUrl = "/" + relativeUrl;
        }
        return String.format("%s %s%s", words[0], _wpRec.getNetworkUrl(), relativeUrl);
    }


    public double getDeviceTime()
    {
        return _deviceTime;
    }

    public void setDeviceTime(Integer[] data)
    {
        double time = data[0] + 0x100 * data[1] + 0x10000 * data[2] + 0x1000000 * data[3];
        _deviceTime = time + data[4] / 250.0;
    }

    YPEntry getModuleYPEntry()
    {
        return _moduleYPEntry;
    }

    private YGenericHub.RequestAsyncResult _logCallbackHandler = new YGenericHub.RequestAsyncResult()
    {
        @Override
        public void RequestAsyncDone(Object context, byte[] result, int error, String errmsg)
        {

            if (result == null) {
                _logIsPulling = false;
                return;
            }
            if (_logCallback == null) {
                _logIsPulling = false;
                return;
            }
            String resultStr = new String(result);
            int pos = resultStr.lastIndexOf("@");
            if (pos < 0) {
                _logIsPulling = false;
                return;
            }
            String logs = resultStr.substring(0, pos);
            String posStr = resultStr.substring(pos + 1);
            _logpos = Integer.valueOf(posStr);
            YModule module = YModule.FindModule(getSerialNumber());
            String[] lines = logs.split("\n");
            for (String line : lines) {
                _logCallback.logCallback(module, line);
            }
            _logIsPulling = false;
        }
    };

    void triggerLogPull()
    {
        if (_logCallback == null || _logIsPulling)
            return;
        _logIsPulling = true;
        String request = "GET logs.txt?pos=" + _logpos;
        try {
            requestHTTPAsync(request, null, _logCallbackHandler, new Integer(_logpos));
        } catch (YAPI_Exception ex) {
            SafeYAPI()._Log("LOG error:" + ex.getLocalizedMessage());
        }
    }

    void registerLogCallback(YModule.LogCallback callback)
    {
        _logCallback = callback;
        triggerLogPull();
    }


    static byte[] formatHTTPUpload(String path, byte[] content) throws YAPI_Exception
    {
        Random randomGenerator = new Random();
        String boundary;
        String mp_header = "Content-Disposition: form-data; name=\"" + path + "\"; filename=\"api\"\r\n" +
                "Content-Type: application/octet-stream\r\n" +
                "Content-Transfer-Encoding: binary\r\n\r\n";
        // find a valid boundary
        do {
            boundary = String.format("Zz%06xzZ", randomGenerator.nextInt(0x1000000));
        } while(mp_header.contains(boundary) && YAPI._find_in_bytes(content,boundary.getBytes())>=0);
        //construct header parts
        String header_start = "Content-Type: multipart/form-data; boundary=" + boundary + "\r\n\r\n--" + boundary + "\r\n" + mp_header;
        String header_stop = "\r\n--" + boundary + "--\r\n";
        byte[] head_body = new byte[header_start.length() + content.length + header_stop.length()];
        int pos = 0;
        int len = header_start.length();
        System.arraycopy(header_start.getBytes(), 0, head_body, pos, len);

        pos += len;
        len = content.length;
        System.arraycopy(content, 0, head_body, pos, len);

        pos += len;
        len = header_stop.length();
        System.arraycopy(header_stop.getBytes(), 0, head_body, pos, len);
        System.arraycopy(header_stop.getBytes(), 0, head_body, pos, len);
        return head_body;
    }

    int requestHTTPUpload(String path, byte[] content) throws YAPI_Exception
    {
        String request = "POST /upload.html";
        byte[] head_body = YDevice.formatHTTPUpload(path, content);
        requestHTTPSync(request, head_body);
        return YAPI.SUCCESS;
    }

}
