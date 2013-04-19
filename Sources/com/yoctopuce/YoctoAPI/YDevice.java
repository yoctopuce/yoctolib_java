package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

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
public class YDevice {

    public static ArrayList<YDevice> _devCache = new ArrayList<YDevice>();// Device cache entries
    private YGenericHub _hub;
    private WPEntry _wpRec;
    private long _cache_expiration;
    private String _cache_json;
    private HashMap<Integer, String> _functions_funcid;
    private HashMap<Integer, String> _functions_name;

    // Device constructor. Automatically call the YAPI functin to reindex device
     YDevice(YGenericHub hub, WPEntry wpRec, HashMap<String, ArrayList<YPEntry>> ypRecs) throws YAPI_Exception
    {
        // private attributes
        _hub = hub;
        _wpRec =wpRec;
        _cache_expiration = 0;
        _cache_json = "";
        _functions_funcid = new HashMap<Integer, String>();
        _functions_name = new HashMap<Integer, String>();

        

        for (String categ : ypRecs.keySet()) {
            for (YPEntry rec : ypRecs.get(categ)) {
                if (rec.getSerial().equals(wpRec.getSerialNumber())) {
                    int funydx = rec.getIndex();
                    _functions_funcid.put(funydx, rec.getFuncId());
                    _functions_name.put(funydx, rec.getLogicalName());
                }
            }
        }
        YAPI.reindexDevice(this);
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
        String yreq = new String(requestHTTP("GET /api.json",null, false));
        this._cache_expiration = YAPI.GetTickCount() + YAPI.DefaultCacheValidity;
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


            _cache_expiration = YAPI.GetTickCount() + YAPI.DefaultCacheValidity;
            _cache_json = result;

            // parse module and refresh names if needed
            Iterator<?> keys = loadval.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (key.equals("module")) {
                    JSONObject module = loadval.getJSONObject("module");
                    if (!_wpRec.getLogicalName().equals(module.getString("logicalName"))) {
                        _wpRec.setLogicalName(module.getString("logicalName"));
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
                        YAPI.setFunctionValue(_wpRec.getSerialNumber(), pubval);
                    }
                    for (int f = 0; f < _functions_funcid.size(); f++) {
                        if (_functions_funcid.get(f).equals(key)) {
                            if (!_functions_name.get(f).equals(name)) {
                                _functions_name.put(f, name);
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
            YAPI.reindexDevice(this);
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
        return _functions_funcid.size();
    }

    // Retrieve the Id of the nth function (beside "module") in the device
    protected String functionId(int idx)
    {
        if (idx < _functions_funcid.size()) {
            return _functions_funcid.get(idx);
        }
        return "";
    }

    // Retrieve the logical name of the nth function (beside "module") in the device
    protected String functionName(int idx)
    {
        if (idx < _functions_name.size()) {
            return _functions_name.get(idx);
        }
        return "";
    }

    // Retrieve the advertised value of the nth function (beside "module") in the device
    protected String functionValue(int idx)
    {
        if (idx < _functions_funcid.size()) {
            return YAPI.getFunctionValue(_wpRec.getSerialNumber() + "." + _functions_funcid.get(idx));
        }
        return "";
    }

    byte[] requestHTTP(String request,byte[] rest_of_request, boolean async) throws YAPI_Exception
    {
        String[] words = request.split(" ");
        if (words.length < 2) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT,
                    "Invalid request, not enough words; expected a method name and a URL");
        }
        String relativeUrl=words[1];
        if (relativeUrl.charAt(0) != '/') {
            relativeUrl = "/" + relativeUrl;
        }
        String shortRequest = String.format("%s %s%s\r\n", words[0],_wpRec.getNetworkUrl(),relativeUrl);
        return _hub.devRequest(this,shortRequest,rest_of_request, async);
    }
}
