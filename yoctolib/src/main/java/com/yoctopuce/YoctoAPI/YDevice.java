/*********************************************************************
 * $Id: YDevice.java 52478 2022-12-21 10:33:33Z seb $
 *
 * Internal YDevice class
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


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
class YDevice
{
    private final YGenericHub _hub;
    private final WPEntry _wpRec;
    private final HashMap<Integer, YPEntry> _ypRecs;
    private long _cache_expiration;
    private YJSONObject _cache_json;
    private double _lastTimeRef;
    private double _lastDuration;
    private YPEntry _moduleYPEntry;
    private YModule.LogCallback _logCallback = null;
    private int _logpos = 0;
    private boolean _logIsPulling = false;
    private boolean _logNeedPulling = false;

    // Device constructor. Automatically call the YAPI functin to reindex device
    YDevice(YGenericHub hub, WPEntry wpRec, HashMap<String, ArrayList<YPEntry>> ypRecs)
    {
        // private attributes
        _hub = hub;
        _wpRec = wpRec;
        _cache_expiration = 0;
        _cache_json = null;
        _moduleYPEntry = new YPEntry(wpRec.getSerialNumber(), "module", YPEntry.BaseClass.Function);
        _moduleYPEntry.setLogicalName(wpRec.getLogicalName());
        _ypRecs = new HashMap<>();
        Set<String> keySet = ypRecs.keySet();
        for (String categ : keySet) {
            for (YPEntry rec : ypRecs.get(categ)) {
                if (rec.getSerial().equals(wpRec.getSerialNumber())) {
                    int funydx = rec.getIndex();
                    _ypRecs.put(funydx, rec);
                }
            }
        }
    }

    YGenericHub getHub()
    {
        return _hub;
    }

    String getNetworkUrl()
    {
        return _wpRec.getNetworkUrl();
    }

    // Return the serial number of the device, as found during discovery
    String getSerialNumber()
    {
        return _wpRec.getSerialNumber();
    }

    // Return the logical name of the device, as found during discovery
    String getLogicalName()
    {
        return _wpRec.getLogicalName();
    }

    // Return the beacon state of the device, as found during discovery
    int getBeacon()
    {
        return _wpRec.getBeacon();
    }


    String getProductName()
    {
        return _wpRec.getProductName();
    }

    int getProductId()
    {
        return _wpRec.getProductId();
    }

    // Get the whole REST API string for a device, from cache if possible
    synchronized YJSONObject requestAPI() throws YAPI_Exception
    {
        long tickCount = YAPI.GetTickCount();
        if (_cache_expiration > tickCount) {
            return _cache_json;
        }

        String request = "GET /api.json";
        if (_cache_json != null) {
            String fwrelease = _cache_json.getYJSONObject("module").getString("firmwareRelease");
            try {
                fwrelease = URLEncoder.encode(fwrelease, _hub._yctx._defaultEncoding);
            } catch (UnsupportedEncodingException ignored) {
            }
            request += "?fw=" + fwrelease;
        }
        String yreq;
        try {
            yreq = requestHTTPSyncAsString(request, null);
        } catch (YAPI_Exception ex) {
            try {
                _hub.updateDeviceList(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new YAPI_Exception(YAPI.IO_ERROR, "Thread interrupted", e);
            }
            yreq = requestHTTPSyncAsString(request, null);
        }
        YJSONObject cache_json;
        try {
            cache_json = new YJSONObject(yreq);
            cache_json.parseWithRef(_cache_json);
        } catch (Exception ex) {
            _cache_json = null;
            throw new YAPI_Exception(YAPI.IO_ERROR,
                    "Request failed, could not parse API result for " + this);
        }
        this._cache_expiration = YAPI.GetTickCount() + _hub._yctx._defaultCacheValidity;
        this._cache_json = cache_json;
        return cache_json;
    }

    // Reload a device API (store in cache), and update YAPI function lists accordingly
    // Intended to be called within UpdateDeviceList only
    synchronized void refresh() throws YAPI_Exception
    {
        YJSONObject loadval = requestAPI();
        Boolean reindex = false;
        try {
            // parse module and refresh names if needed
            Set<String> keys = loadval.getKeys();
            for (String key : keys) {
                if (key.equals("module")) {
                    YJSONObject module = loadval.getYJSONObject("module");
                    if (!_wpRec.getLogicalName().equals(module.getString("logicalName"))) {
                        _wpRec.setLogicalName(module.getString("logicalName"));
                        _moduleYPEntry.setLogicalName(_wpRec.getLogicalName());
                        reindex = true;
                    }
                    _wpRec.setBeacon(module.getInt("beacon"));
                } else if (!key.equals("services")) {
                    YJSONObject func = loadval.getYJSONObject(key);
                    String name;
                    if (func.has("logicalName")) {
                        name = func.getString("logicalName");
                    } else {
                        name = _wpRec.getLogicalName();
                    }
                    if (func.has("advertisedValue")) {
                        String pubval = func.getString("advertisedValue");
                        _hub._yctx._yHash.setFunctionValue(_wpRec.getSerialNumber() + "." + key, pubval);
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
        } catch (Exception e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "Request failed, could not parse API result");
        }

        if (reindex) {
            _hub._yctx._yHash.reindexDevice(this);
        }
    }

    // Force the REST API string in cache to expire immediately
    synchronized void clearCache()
    {
        _cache_expiration = 0;
    }

    // Retrieve the number of functions (beside "module") in the device

    Collection<YPEntry> getFunctions()
    {
        return _ypRecs.values();
    }


    YPEntry getYPEntry(int idx)
    {
        return _ypRecs.get(idx);
    }

    synchronized byte[] requestHTTPSync(String request, byte[] rest_of_request) throws YAPI_Exception
    {
        String shortRequest = formatRequest(request);
        byte[] res;
        try {
            res = _hub.devRequestSync(this, shortRequest, rest_of_request, null, null);
        } catch (InterruptedException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR,
                    "Thread has been interrupted");
        }
        if (_logNeedPulling) {
            triggerLogPull();
        }
        return res;
    }

    @SuppressWarnings("SameParameterValue")
    String requestHTTPSyncAsString(String request, byte[] rest_of_request) throws YAPI_Exception
    {
        final byte[] bytes = requestHTTPSync(request, rest_of_request);
        if (_logNeedPulling) {
            triggerLogPull();
        }
        return new String(bytes, _hub._yctx._deviceCharset);
    }


    @SuppressWarnings("SameParameterValue")
    void requestHTTPAsync(String request, byte[] rest_of_request, YGenericHub.RequestAsyncResult asyncResult, Object context) throws YAPI_Exception
    {
        String shortRequest = formatRequest(request);
        try {
            _hub.devRequestAsync(this, shortRequest, rest_of_request, asyncResult, context);
        } catch (InterruptedException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR,
                    "Thread has been interrupted");
        }
        if (_logNeedPulling) {
            triggerLogPull();
        }
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


    double getLastTimeRef()
    {
        return _lastTimeRef;
    }

    double getLastDuration()
    {
        return _lastDuration;
    }

    void setLastTimeRef(Integer[] data)
    {
        double time = (data[0] & 0xff) + 0x100 * (data[1] & 0xff) + 0x10000 * (data[2] & 0xff) + 0x1000000 * (data[3] & 0xff);
        long ms = (data[4] & 0xff) * 4;
        if (data.length >= 6) {
            ms += (data[5] & 0xff) >> 6;
            long freq = data[6];
            freq += (data[5] & 0xf) * 0x100;
            if ((data[5] & 0x10) != 0) {
                _lastDuration = freq;
            } else {
                _lastDuration = freq / 1000.0;
            }
        } else {
            _lastDuration = 0;
        }
        _lastTimeRef = time + ms / 1000.0;
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
            YModule module = YModule.FindModuleInContext(_hub._yctx, getSerialNumber());
            String[] lines = logs.split("\n");
            for (String line : lines) {
                _logCallback.logCallback(module, line);
            }
            _logIsPulling = false;
        }
    };

    String getLogRequest() throws YAPI_Exception
    {
        if (_logIsPulling || _logCallback == null)
            return null;
        return formatRequest("GET logs.txt?pos=" + _logpos);
    }

    YGenericHub.RequestAsyncResult getLogCallbackHandler()
    {
        return _logCallbackHandler;
    }

    void triggerLogPull()
    {
        _hub.addDevForLogPull(this);
    }

    void registerLogCallback(YModule.LogCallback callback)
    {
        _logCallback = callback;
        if (callback != null) {
            triggerLogPull();
        }
    }

    static byte[] formatHTTPUpload(String path, byte[] content)
    {
        Random randomGenerator = new Random();
        String boundary;
        String mp_header = "Content-Disposition: form-data; name=\"" + path + "\"; filename=\"api\"\r\n" +
                "Content-Type: application/octet-stream\r\n" +
                "Content-Transfer-Encoding: binary\r\n\r\n";
        // find a valid boundary
        do {
            boundary = String.format("Zz%06xzZ", randomGenerator.nextInt(0x1000000));
        }
        while (mp_header.contains(boundary) && YAPIContext._find_in_bytes(content, boundary.getBytes()) >= 0);
        // VirtualHub-4web quirk: we have to switch from "multipart/form-data" to "x-upload"
        // to bypass PHP own processing of uploads. The exact value has anyway always be
        // ignored by VirtualHub and YoctoHubs, as long as a boundary is defined.
        String header_mp_start = "--" + boundary + "\r\n" + mp_header;
        String header_mp_stop = "\r\n--" + boundary + "--\r\n";
        int body_size = header_mp_start.length() + content.length + header_mp_stop.length();
        String header = "Content-Type: x-upload; boundary=" + boundary + "\r\n";
        header += String.format("Content-Length: %d\r\n\r\n", body_size);
        byte[] head_body = new byte[header.length() + body_size];
        int pos = 0;
        // copy http header
        byte[] headerBytes = header.getBytes();
        System.arraycopy(headerBytes, 0, head_body, pos, headerBytes.length);
        pos += headerBytes.length;
        // copy multipart header
        byte[] header_mp_startBytes = header_mp_start.getBytes();
        System.arraycopy(header_mp_startBytes, 0, head_body, pos, header_mp_startBytes.length);
        pos += header_mp_startBytes.length;
        // copy content
        System.arraycopy(content, 0, head_body, pos, content.length);
        pos += content.length;
        // copy multipart footer
        byte[] header_mp_stopBytes = header_mp_stop.getBytes();
        System.arraycopy(header_mp_stopBytes, 0, head_body, pos, header_mp_stopBytes.length);
        return head_body;
    }

    int requestHTTPUpload(String path, byte[] content) throws YAPI_Exception
    {
        String request = "POST /upload.html";
        byte[] head_body = YDevice.formatHTTPUpload(path, content);
        requestHTTPSync(request, head_body);
        return YAPI.SUCCESS;
    }

    byte[] requestHTTPUploadEx(String path, byte[] content) throws YAPI_Exception
    {
        String request = "POST /upload.html";
        byte[] head_body = YDevice.formatHTTPUpload(path, content);
        return requestHTTPSync(request, head_body);
    }


}
