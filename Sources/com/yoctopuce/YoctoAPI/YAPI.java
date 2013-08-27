/*********************************************************************
 *
 * $Id: YAPI.java 12426 2013-08-20 13:58:34Z seb $
 *
 * High-level programming interface, common to all modules
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class YAPI {

    private static Boolean _apiInitialized = false;
    // Default cache validity (in [ms]) before reloading data from device. This
    // saves a lots of trafic.
    // Note that a value undger 2 ms makes little sense since a USB bus itself
    // has a 2ms roundtrip period
    // Default cache validity (in [ms]) before reloading data from device. This
    // saves a lots of trafic.
    // Note that a value under 2 ms makes little sense since a USB bus itself
    // has a 2ms roundtrip period
    public static int DefaultCacheValidity = 5;
    // Return value for invalid strings
    public static final String INVALID_STRING = "!INVALID!";
    public static final double INVALID_DOUBLE = -1.79769313486231E+308;
    public static final int INVALID_INT = -2147483648;
    public static final long INVALID_LONG = -9223372036854775807L;
    public static final int INVALID_UNSIGNED = -1;
    public static final String YOCTO_API_VERSION_STR = "1.01";
    public static final int YOCTO_API_VERSION_BCD = 0x0101;
    public static final int YOCTO_VENDORID = 0x24e0;
    public static final int YOCTO_DEVID_FACTORYBOOT = 1;
    public static final int YOCTO_DEVID_BOOTLOADER = 2;
    // --- (generated code: globals)
    // Yoctopuce error codes, used by default as function return value
    public static final int SUCCESS = 0;                   // everything worked allright
    public static final int NOT_INITIALIZED = -1;          // call yInitAPI() first !
    public static final int INVALID_ARGUMENT = -2;         // one of the arguments passed to the function is invalid
    public static final int NOT_SUPPORTED = -3;            // the operation attempted is (currently) not supported
    public static final int DEVICE_NOT_FOUND = -4;         // the requested device is not reachable
    public static final int VERSION_MISMATCH = -5;         // the device firmware is incompatible with this API version
    public static final int DEVICE_BUSY = -6;              // the device is busy with another task and cannot answer
    public static final int TIMEOUT = -7;                  // the device took too long to provide an answer
    public static final int IO_ERROR = -8;                 // there was an I/O problem while talking to the device
    public static final int NO_MORE_DATA = -9;             // there is no more data to read from
    public static final int EXHAUSTED = -10;               // you have run out of a limited ressource, check the documentation
    public static final int DOUBLE_ACCES = -11;            // you have two process that try to acces to the same device
    public static final int UNAUTHORIZED = -12;            // unauthorized access to password-protected device
    public static final int RTC_NOT_READY = -13;           // real-time clock has not been initialized (or time was lost)

//--- (end of generated code: globals)
    static final String   DefaultEncoding = "ISO-8859-1";
    
    // Yoctopuce generic constant
    static final int YOCTO_MANUFACTURER_LEN = 20;
    static final int YOCTO_SERIAL_LEN = 20;
    static final int YOCTO_BASE_SERIAL_LEN = 8;
    static final int YOCTO_PRODUCTNAME_LEN = 28;
    static final int YOCTO_FIRMWARE_LEN = 22;
    static final int YOCTO_LOGICAL_LEN = 20;
    static final int YOCTO_FUNCTION_LEN = 20;
    static final int YOCTO_PUBVAL_SIZE = 6; // Size of the data (can be non null
    static final int YOCTO_PUBVAL_LEN = 16; // Temporary storage, >=
    static final int YOCTO_PASS_LEN = 20;
    static final int YOCTO_REALM_LEN = 20;

    // yInitAPI argument
    public static final int DETECT_NONE = 0;
    public static final int DETECT_USB = 1;
    public static final int DETECT_NET = 2;
    public static final int DETECT_ALL = DETECT_USB | DETECT_NET;

    /**
     *
     */
    public interface DeviceArrivalCallback {

        void yDeviceArrival(YModule module);
    }

    public interface DeviceRemovalCallback {

        void yDeviceRemoval(YModule module);
    }

    public interface DeviceChangeCallback {

        void yDeviceChange(YModule module);
    }

    public interface LogCallback {

        void yLog(String line);
    }

    public interface CalibrationHandlerCallback {

        double yCalibrationHandler(double rawValue, int calibType,
                ArrayList<Integer> params, ArrayList<Double> rawValues, ArrayList<Double> refValues);
    }
    private static ArrayList<YGenericHub> _hubs  = new ArrayList<YGenericHub>(); // array of root urls
    private static HashMap<String, YDevice> _devs = new HashMap<String, YDevice>(); // hash table of devices, by serial number
    private static HashMap<String, String> _snByUrl = new HashMap<String, String>(); // serial number for each device, by URL
    private static HashMap<String, String> _snByName = new HashMap<String, String>(); // serial number for each device, by name
    private static HashMap<String, YFunctionType> _fnByType = new HashMap<String, YFunctionType>(); // functions by type
    private static boolean _firstArrival=true;
    private final static Queue<PlugEvent> _pendingCallbacks = new LinkedList<PlugEvent>();
    private final static Queue<PendingValue> _pendingValues = new LinkedList<PendingValue>();
    private static DeviceArrivalCallback _arrivalCallback=null;
    private static DeviceChangeCallback _namechgCallback=null;
    private static DeviceRemovalCallback _removalCallback=null;
    private static LogCallback _logCallback = null;
    private static HashMap<Integer, CalibrationHandlerCallback> _calibHandlers= new HashMap<Integer, YAPI.CalibrationHandlerCallback>();
    private static CalibrationHandlerCallback linearCalibrationHandler= new CalibrationHandlerCallback() {

        @Override
        public double yCalibrationHandler(double rawValue, int calibType, ArrayList<Integer> params, ArrayList<Double> rawValues, ArrayList<Double> refValues)
        {
            // calibration types n=1..10 and 11.20 are meant for linear calibration using n points
            int    npt = calibType % 10;
            double x   = rawValues.get(0);
            double adj = refValues.get(0) - x;
            int    i   = 0;
    
            if(npt > (int)rawValues.size()) npt = (int)rawValues.size();
            if(npt > (int)refValues.size()) npt = (int)refValues.size();
            while(rawValue > rawValues.get(i) && ++i < npt) {
                double x2   = x;
                double adj2 = adj;
        
                x   = rawValues.get(i);
                adj = refValues.get(i) - x;
        
                if(rawValue < x && x > x2) {
                    adj = adj2 + (adj - adj2) * (rawValue - x2) / (x - x2);
                }
            }
            return rawValue + adj;
        }
    };

    private static class PendingValue {

        public YFunction fun;
        public String value;

        public PendingValue(YFunction fun, String value)
        {
            this.fun = fun;
            this.value = value;
        }
    }

    static class PlugEvent {

        public static enum Event {

            PLUG, UNPLUG, CHANGE
        }
        public Event ev;
        public YModule module;

        public PlugEvent(Event ev, String serial)
        {
            this.ev = ev;
            this.module = YModule.FindModule(serial + ".module");
        }
    }

    static void pushPlugEvent(PlugEvent.Event ev, String serial)
    {
        synchronized(_pendingCallbacks){
            _pendingCallbacks.add(new PlugEvent(ev, serial));
        }
    }

    private static void _updateDeviceList_internal(boolean forceupdate, boolean invokecallbacks) throws YAPI_Exception
    {
        if (_firstArrival && invokecallbacks && _arrivalCallback != null) {
            forceupdate = true;
        }

        // Rescan all hubs and update list of online devices
        for (YGenericHub h : _hubs) {
            h.updateDeviceList(forceupdate);
        }

        // after processing all hubs, invoke pending callbacks if required
        if (invokecallbacks) {
            while (true){
                PlugEvent evt;
                synchronized(_pendingCallbacks){
                    if(_pendingCallbacks.isEmpty()) {
                        break;
                    }
                    evt = _pendingCallbacks.poll();
                }
                switch (evt.ev) {
                    case PLUG:
                        if (_arrivalCallback != null) {
                            _arrivalCallback.yDeviceArrival(evt.module);
                        }
                        break;
                    case CHANGE:
                        if (_namechgCallback != null) {
                            _namechgCallback.yDeviceChange(evt.module);
                        }
                        break;
                    case UNPLUG:
                        if (_removalCallback != null) {
                            _removalCallback.yDeviceRemoval(evt.module);
                        }
                        forgetDevice(_devs.get(evt.module.get_serialNumber()));
                        break;
                }
            }
            if (_arrivalCallback != null && _firstArrival) {
                _firstArrival = false;
            }
        }
    }

    /*
     * Return a the calibration handler for a given type
     */
    public static CalibrationHandlerCallback getCalibrationHandler(int calibType)
    {
        if (!_calibHandlers.containsKey(calibType)) {
            return null;
        }
        return _calibHandlers.get(calibType);
    }




       static double decExp[] = new double[] {
        1.0e-6, 1.0e-5, 1.0e-4, 1.0e-3, 1.0e-2, 1.0e-1, 1.0,
        1.0e1, 1.0e2, 1.0e3, 1.0e4, 1.0e5, 1.0e6, 1.0e7, 1.0e8, 1.0e9 };

    // Convert Yoctopuce 16-bit decimal floats to standard double-precision floats
    //
    static double _decimalToDouble(int val)
    {
        boolean     negate=false;
        double  res;

        if(val == 0)
            return 0.0;
        if(val > 32767) {
            negate = true;
            val = 65536-val;
        } else if(val < 0) {
            negate = true;
            val = -val;
        }
        int exp = val >> 11;
        res = (double)(val & 2047) * decExp[exp];
        return (negate ? -res : res);
    }

    // Convert standard double-precision floats to Yoctopuce 16-bit decimal floats
    //
    static long _doubleToDecimal(double val)
    {
        int     negate = 0;
        double  comp, mant;
        int     decpow;
        long     res;

        if(val == 0.0) {
            return 0;
        }
        if(val < 0) {
            negate = 1;
            val = -val;
        }
        comp = val / 1999.0;
        decpow = 0;
        while(comp > decExp[decpow] && decpow < 15) {
            decpow++;
        }
        mant = val / decExp[decpow];
        if(decpow == 15 && mant > 2047.0) {
            res = (15 << 11) + 2047; // overflow
        } else {
            res = (decpow << 11) +  Math.round(mant);
        }
        return (negate!=0 ? -res : res);
    }


    /*
     * Method used to encode calibration points into fixed-point 16-bit integers
     */
    static String _encodeCalibrationPoints(ArrayList<Double> rawValues, ArrayList<Double> refValues, double resolution, int calibrationOffset,String actualCparams)
    {
        int npt = (rawValues.size() < refValues.size() ? rawValues.size() : refValues.size());
        int rawVal, refVal;
        int calibType;
        int minRaw = 0;
        String res;

        if(npt==0){
            return "";
        }
        if(actualCparams.equals("")){
            calibType =10 + npt;
        }else{
            int pos = actualCparams.indexOf(',');
            calibType = Integer.parseInt(actualCparams.substring(0,pos));
            if(calibType <=10)
                calibType =npt;
            else
                calibType = 10+npt;
        }
        res = Integer.toString(calibType);
        if(calibType<=10){
            for (int i = 0; i < npt; i++) {
                rawVal = (int) (rawValues.get(i) / resolution - calibrationOffset + .5);
                if (rawVal >= minRaw && rawVal < 65536) {
                    refVal = (int) (refValues.get(i) / resolution - calibrationOffset + .5);
                    if (refVal >= 0 && refVal < 65536) {
                        res += String.format(",%d,%d", rawVal, refVal);
                        minRaw = rawVal + 1;
                    }
                }
            }
        } else {
            // 16-bit floating-point decimal encoding
            for(int i = 0; i < npt; i++) {
                rawVal = (int) _doubleToDecimal(rawValues.get(i));
                refVal = (int) _doubleToDecimal(refValues.get(i));
                res += String.format(",%d,%d", rawVal, refVal);
            }
        }
        return res;
    }


    /*
     * Method used to decode calibration points from fixed-point 16-bit integers
     */
    static int _decodeCalibrationPoints(String calibParams, ArrayList<Integer> intPt, ArrayList<Double> rawPt, ArrayList<Double> calPt, double resolution, int calibrationOffset)
    {


        String[] valuesStr = calibParams.split(",",0);
        if(intPt!=null)
            intPt.clear();
        rawPt.clear();
        calPt.clear();
        if(valuesStr[0].equals(""))
            return 0;
        int calibType=0;
        try{
            calibType = Integer.parseInt(valuesStr[0]);
        }catch (java.lang.NumberFormatException e){
            return 0;
        }
        // parse calibration parameters
        int nval = (calibType <= 20 ? 2*(calibType % 10) : 99);
        for (int i =1; i< nval && i< valuesStr.length ;i+=2){
            int rawval = Integer.parseInt(valuesStr[i]);
            int calval = Integer.parseInt(valuesStr[i+1]);
            double rawval_d, calval_d;
            if(calibType <= 10) {
                rawval_d = (rawval + calibrationOffset) * resolution;
                calval_d = (calval + calibrationOffset) * resolution;
            } else {
                rawval_d = _decimalToDouble(rawval);
                calval_d = _decimalToDouble(calval);
            }
            if(intPt!=null){
                intPt.add(rawval);
                intPt.add(calval);
            }
            rawPt.add(rawval_d);
            calPt.add(calval_d);
        }
        return calibType;
    }

    /*
     * Compute the currentValue for the provided function, using the
     * currentRawValue, the calibrationParam and the proper registered
     * calibration handler
     */
    static double applyCalibration(double rawValue, String calibParams,
            int calibOffset, double resolution)
    {

        if(rawValue == INVALID_DOUBLE || resolution == INVALID_DOUBLE) {
            return INVALID_DOUBLE;
        }
        if(calibParams==null || calibParams.indexOf(',')<0)
            return rawValue;

        ArrayList<Integer> cur_calpar = new ArrayList<Integer>();
        ArrayList<Double>  cur_calraw = new ArrayList<Double>();
        ArrayList<Double>  cur_calref = new ArrayList<Double>();
        int calibType = _decodeCalibrationPoints(calibParams
                                                        ,cur_calpar
                                                        ,cur_calraw
                                                        ,cur_calref
                                                        ,resolution
                                                        ,calibOffset);

        if(calibType == 0) {
            return rawValue;
        }
        if (!_calibHandlers.containsKey(calibType)) {
            return INVALID_DOUBLE;
        }
        return _calibHandlers.get(calibType).yCalibrationHandler(rawValue,
                calibType, cur_calpar, cur_calraw, cur_calref);

    }

    // helper function to find pattern in byte[]
    static int _find_in_bytes(byte[] source, byte[] match)
    {
        // sanity checks
        if (source == null || match == null) {
            return -1;
        }
        if (source.length == 0 || match.length == 0) {
            return -1;
        }
        int ret = -1;
        int spos = 0;
        int mpos = 0;
        byte m = match[mpos];
        for (; spos < source.length; spos++) {
            if (m == source[spos]) {
                // starting match
                if (mpos == 0) {
                    ret = spos;
                } // finishing match
                else if (mpos == match.length - 1) {
                    return ret;
                }
                mpos++;
                m = match[mpos];
            } else {
                ret = -1;
                mpos = 0;
                m = match[mpos];
            }
        }
        return ret;
    }
    
    // Return a Device object for a specified URL, serial number or logical
    // device name
    // This function will not cause any network access
    static YDevice getDevice(String device)
    {
        YDevice dev = null;
        if (device.startsWith("http://")) {
            if (_snByUrl.containsKey(device)) {
                String serial = _snByUrl.get(device);
                if (_devs.containsKey(serial)) {
                    dev = _devs.get(serial);
                }
            }
        } else {
            // lookup by serial
            if (_devs.containsKey(device)) {
                dev = _devs.get(device);
            } else {
                // fallback to lookup by logical name
                if (_snByName.containsKey(device)) {
                    String serial = _snByName.get(device);
                    dev = _devs.get(serial);
                }
            }
        }
        return dev;
    }

    // Return the class name for a given function ID or full Hardware Id
    // Also make sure that the function type is registered in the API
    static String functionClass(String funcid)
    {
        int dotpos = funcid.indexOf('.');

        if (dotpos >= 0) {
            funcid = funcid.substring(dotpos + 1);
        }
        int classlen = funcid.length();

        while (funcid.charAt(classlen - 1) <= 57) {
            classlen--;
        }

        String classname = funcid.substring(0, 1).toUpperCase()
                + funcid.substring(1, classlen);
        getFnByType(classname);

        return classname;
    }

    // Reindex a device in YAPI after a name change detected by device refresh
    static void reindexDevice(YDevice dev)
    {
        String serial = dev.getSerialNumber();
        String lname = dev.getLogicalName();
        _devs.put(serial, dev);

        if (!lname.equals("")) {
            _snByName.put(lname, serial);
        }
        _fnByType.get("Module").reindexFunction(serial + ".module", lname, null);
        int count = dev.functionCount();
        for (int i = 0; i < count; i++) {
            String funcid = dev.functionId(i);
            String funcname = dev.functionName(i);
            String classname = functionClass(funcid);
            _fnByType.get(classname).reindexFunction(serial + "." + funcid,
                    funcname, null);
        }
    }

    // Remove a device from YAPI after an unplug detected by device refresh
    static void forgetDevice(YDevice dev)
    {
        String serial = dev.getSerialNumber();
        String lname = dev.getLogicalName();
        _devs.remove(serial);
        if (_snByName.containsKey(lname) && _snByName.get(lname).equals(serial)) {
            _snByName.remove(lname);
        }

        _fnByType.get("Module").forgetFunction(serial + ".module");

        int count = dev.functionCount();
        for (int i = 0; i < count; i++) {

            String funcid = dev.functionId(i);
            String classname = functionClass(funcid);
            _fnByType.get(classname).forgetFunction(serial + funcid);
        }
    }

    static YFunctionType getFnByType(String className)
    {
        if (!_fnByType.containsKey(className)) {
            _fnByType.put(className, new YFunctionType(className));
        }
        return _fnByType.get(className);
    }

    // Find the best known identifier (hardware Id) for a given function
    static String resolveFunction(String className, String func)
            throws YAPI_Exception
    {
        return getFnByType(className).resolve(func).toString();
    }

    // Retrieve a function object by hardware id, updating the indexes on the
    // fly if needed
    static void setFunction(String className, String func, YFunction yfunc)
    {
        getFnByType(className).setFunction(func, yfunc);
    }

    // Retrieve a function object by hardware id, logicalname, updating the indexes on the
    // fly if needed
    static YFunction getFunction(String className, String func)
    {

        return getFnByType(className).getFunction(func);
    }

    // Set a function advertised value by hardware id
    static void setFunctionValue(String hwid, String pubval)
    {
        String classname = functionClass(hwid);
        getFnByType(classname).setFunctionValue(hwid, pubval);
    }

    // Retrieve a function advertised value by hardware id
    static String getFunctionValue(String hwid)
    {
        String classname = functionClass(hwid);
        return getFnByType(classname).getFunctionValue(hwid);
    }

    // Queue a function value event
    static void addValueEvent(YFunction conn_fn, String pubval)
    {
        synchronized(_pendingValues) {
            _pendingValues.add(new PendingValue(conn_fn, pubval));
        }
    }

    // Find the hardwareId for the first instance of a given function class
    static String getFirstHardwareId(String className)
    {
        return getFnByType(className).getFirstHardwareId();
    }

    // Find the hardwareId for the next instance of a given function class
    static String getNextHardwareId(String className, String hwid)
    {
        return getFnByType(className).getNextHardwareId(hwid);
    }

    private static HashMap<String, String> _json2Hash(JSONObject json)
    {

        HashMap<String, String> res = new HashMap<String, String>(json.length());
        Iterator<?> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next().toString();
            String value = json.optString(key);
            res.put(key, value);
        }
        return res;

    }



    static YDevice funcGetDevice(String className, String func) throws YAPI_Exception
    {
        String resolved = null;
        try {
            resolved = resolveFunction(className, func);
        } catch (YAPI_Exception ex) {
            if (ex.errorType == DEVICE_NOT_FOUND && _hubs.isEmpty()) {
                // when USB is supported, check if no USB device is connected
                // before outputing this message
                throw new YAPI_Exception(ex.errorType,
                        "Impossible to contact any device because no hub has been registered");
            } else {
                _updateDeviceList_internal(true, false);
                resolved = resolveFunction(className, func);
            }
        }
        func = resolved;
        int dotpos = func.indexOf('.');
        String devid = func.substring(0, dotpos);
        YDevice dev = getDevice(devid);
        if (dev == null) {
            // try to force a device list update to check if the device arrived
            // in between
            _updateDeviceList_internal(true, false);
            dev = getDevice(devid);
            if (dev == null) {
                throw new YAPI_Exception(DEVICE_NOT_FOUND, "Device [" + devid
                        + "] not online");
            }

        }
        return dev;
    }


    // Load and parse the REST API for a function given by class name and
    // identifier, possibly applying changes
    // Device cache will be preloaded when loading function "module" and
    // leveraged for other modules
    static HashMap<String, String> funcRequest(String className, String func, String extra) throws YAPI_Exception
    {
        YDevice dev = funcGetDevice(className, func);
        func = resolveFunction(className, func);
        JSONObject loadval = null;
        int dotpos = func.indexOf('.');
        String funcid = func.substring(dotpos + 1);
        if (extra.equals("")) {
            // use a cached API string, without reloading unless module is
            // requested
            String yreq = dev.requestAPI();
            try {
                JSONObject jsonval = new JSONObject(yreq);
                loadval = jsonval.getJSONObject(funcid);
            } catch (JSONException ex) {
                throw new YAPI_Exception(IO_ERROR,
                        "Request failed, could not parse API result for " + dev);
            }
        } else {
            dev.dropCache();
        }
        if (loadval == null) {
            // request specified function only to minimize traffic
            if (extra.equals("")) {
                String httpreq = "GET /api/" + funcid + ".json";
                String yreq = new String(dev.requestHTTP(httpreq,null, false));
                try {
                    loadval = new JSONObject(yreq);
                } catch (JSONException ex) {
                    throw new YAPI_Exception(IO_ERROR,
                            "Request failed, could not parse API value for "
                            + httpreq);
                }
            } else {
                String httpreq = "GET /api/" + funcid + extra;
                dev.requestHTTP(httpreq,null, true);
                return null;
            }
        }
        return _json2Hash(loadval);
    }

    private static synchronized void _init_free(boolean mustfree)
    {

        if (!mustfree) {
            if (!_apiInitialized) {
                _fnByType.put("Module", new YFunctionType("Module"));
                _apiInitialized = true;
                for (int i =1 ;i<=20;i++){
                    _calibHandlers.put(i, linearCalibrationHandler);
                }
            }
        } else {
            if (_apiInitialized) {
                for (YGenericHub h : _hubs) {
                    h.stopNotifications();
                    h.release();
                }
                _hubs.clear();
                _devs.clear();
                _snByUrl.clear();
                _snByName.clear();
                _fnByType.clear();
                _firstArrival = true;
                _namechgCallback = null;
                _removalCallback = null;
                _apiInitialized = false;
                _calibHandlers.clear();
            }
        }
    }

    /**
     * Returns the version identifier for the Yoctopuce library in use.
     * The version is a string in the form "Major.Minor.Build",
     * for instance "1.01.5535". For languages using an external
     * DLL (for instance C#, VisualBasic or Delphi), the character string
     * includes as well the DLL version, for instance
     * "1.01.5535 (1.01.5439)".
     * 
     * If you want to verify in your code that the library version is
     * compatible with the version that you have used during development,
     * verify that the major number is strictly equal and that the minor
     * number is greater or equal. The build number is not relevant
     * with respect to the library compatibility.
     * 
     * @return a character string describing the library version.
     */
    public static String GetAPIVersion()
    {
        return "1.01.12553";
    }

    /**
     * Initializes the Yoctopuce programming library explicitly.
     * It is not strictly needed to call yInitAPI(), as the library is
     * automatically  initialized when calling yRegisterHub() for the
     * first time.
     * 
     * When YAPI.DETECT_NONE is used as detection mode,
     * you must explicitly use yRegisterHub() to point the API to the
     * VirtualHub on which your devices are connected before trying to access them.
     * 
     * @param mode : an integer corresponding to the type of automatic
     *         device detection to use. Possible values are
     *         YAPI.DETECT_NONE, YAPI.DETECT_USB, YAPI.DETECT_NET,
     *         and YAPI.DETECT_ALL.
     * @param errmsg : a string passed by reference to receive any error message.
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public static int InitAPI(int mode)
    {
        _init_free(false);
        return YAPI.SUCCESS;
    }

    /**
     * Frees dynamically allocated memory blocks used by the Yoctopuce library.
     * It is generally not required to call this function, unless you
     * want to free all dynamically allocated memory blocks in order to
     * track a memory leak for instance.
     * You should not call any other library function after calling
     * yFreeAPI(), or your program will crash.
     */
    public static void FreeAPI()
    {
        _init_free(true);
    }

  

    /**
     * Setup the Yoctopuce library to use modules connected on a given machine.
     * When using Yoctopuce modules through the VirtualHub gateway,
     * you should provide as parameter the address of the machine on which the
     * VirtualHub software is running (typically "http://127.0.0.1:4444",
     * which represents the local machine).
     * When you use a language which has direct access to the USB hardware,
     * you can use the pseudo-URL "usb" instead.
     * 
     * Be aware that only one application can use direct USB access at a
     * given time on a machine. Multiple access would cause conflicts
     * while trying to access the USB modules. In particular, this means
     * that you must stop the VirtualHub software before starting
     * an application that uses direct USB access. The workaround
     * for this limitation is to setup the library to use the VirtualHub
     * rather than direct USB access.
     * 
     * If acces control has been activated on the VirtualHub you want to
     * reach, the URL parameter should look like:
     * 
     * http://username:password@adresse:port
     * 
     * @param url : a string containing either "usb" or the
     *         root URL of the hub to monitor
     * @param errmsg : a string passed by reference to receive any error message.
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public static int RegisterHub(String url) throws YAPI_Exception
    {
        _init_free(false);

        if (!url.equalsIgnoreCase("usb")) {
            //TODO: test if the host respond to url
        }

        YAPI.PreregisterHub(url);

        // Register device list
        _updateDeviceList_internal(true, false);

        return SUCCESS;
    }

    /**
     * This function is used only on Android. Before calling yRegisterHub("usb")
     * you need to activate the USB host port of the system. This function takes as argument,
     * an object of class android.content.Context (or any subclasee).
     * It is not necessary to call this function to reach modules through the network.
     * 
     * @param osContext : an object of class android.content.Context (or any subclass).
     * 
     * @throws YAPI_Exception
     */
    public static void EnableUSBHost(Object osContext) throws YAPI_Exception
    {
        _init_free(false);
        YUSBHub.SetContextType(osContext);
    }

    /**
     *
     */
    public static int PreregisterHub(String url) throws YAPI_Exception
    {
        _init_free(false);
        for (YGenericHub h : _hubs) {
            if (h.isSameRootUrl(url)) {
                return SUCCESS;
            }
        }
        YGenericHub newhub;
        // Add hub to known list
        if (url.equals("usb")) {
        	YUSBHub.CheckUSBAcces();
            newhub = new YUSBHub(_hubs.size());
        } else {
            newhub = new YHTTPHub(_hubs.size(), url);
        }
        _hubs.add(newhub);
        newhub.startNotifications();
        return SUCCESS;
    }

    /**
     * Setup the Yoctopuce library to no more use modules connected on a previously
     * registered machine with RegisterHub.
     * 
     * @param url : a string containing either "usb" or the
     *         root URL of the hub to monitor
     */
    public static void UnregisterHub(String url)
    {
        for (YGenericHub h : _hubs) {
            if (h.isSameRootUrl(url)) {
                h.stopNotifications();
                for (String serial : h._serialByYdx.values()) {
                    forgetDevice(_devs.get(serial));
                }
                h.release();
                _hubs.remove(h);
                return;
            }
        }
    }

    /**
     * Triggers a (re)detection of connected Yoctopuce modules.
     * The library searches the machines or USB ports previously registered using
     * yRegisterHub(), and invokes any user-defined callback function
     * in case a change in the list of connected devices is detected.
     * 
     * This function can be called as frequently as desired to refresh the device list
     * and to make the application aware of hot-plug events.
     * 
     * @param errmsg : a string passed by reference to receive any error message.
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public static int UpdateDeviceList() throws YAPI_Exception
    {
        _init_free(false);
        _updateDeviceList_internal(false, true);
        return SUCCESS;
    }

    /**
     * Maintains the device-to-library communication channel.
     * If your program includes significant loops, you may want to include
     * a call to this function to make sure that the library takes care of
     * the information pushed by the modules on the communication channels.
     * This is not strictly necessary, but it may improve the reactivity
     * of the library for the following commands.
     * 
     * This function may signal an error in case there is a communication problem
     * while contacting a module.
     * 
     * @param errmsg : a string passed by reference to receive any error message.
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public static void HandleEvents()
    {
        _init_free(false);
        // handle pending events
        while(true) {
            PendingValue pv;
            synchronized(_pendingValues) {
                if(_pendingValues.isEmpty()) {
                    break;
                }
                pv = _pendingValues.poll();
            }
            pv.fun.advertiseValue(pv.value);
        }
    }

    /**
     * Pauses the execution flow for a specified duration.
     * This function implements a passive waiting loop, meaning that it does not
     * consume CPU cycles significatively. The processor is left available for
     * other threads and processes. During the pause, the library nevertheless
     * reads from time to time information from the Yoctopuce modules by
     * calling yHandleEvents(), in order to stay up-to-date.
     * 
     * This function may signal an error in case there is a communication problem
     * while contacting a module.
     * 
     * @param ms_duration : an integer corresponding to the duration of the pause,
     *         in milliseconds.
     * @param errmsg : a string passed by reference to receive any error message.
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public static void Sleep(long ms_duration) throws YAPI_Exception
    {
        long end = GetTickCount() + ms_duration;
        _init_free(false);

        do {
            HandleEvents();
            if (end > GetTickCount()) {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException ex) {
                    Logger.getLogger(YAPI.class.getName()).log(Level.SEVERE,
                            null, ex);
                    throw new YAPI_Exception(YAPI.IO_ERROR,
                            "Thread has been interrupted");
                }
            }
        } while (end > GetTickCount());
    }

    /**
     * Returns the current value of a monotone millisecond-based time counter.
     * This counter can be used to compute delays in relation with
     * Yoctopuce devices, which also uses the milisecond as timebase.
     * 
     * @return a long integer corresponding to the millisecond counter.
     */
    public static long GetTickCount()
    {
        return System.currentTimeMillis();
    }

    /**
     * Checks if a given string is valid as logical name for a module or a function.
     * A valid logical name has a maximum of 19 characters, all among
     * A..Z, a..z, 0..9, _, and -.
     * If you try to configure a logical name with an incorrect string,
     * the invalid characters are ignored.
     * 
     * @param name : a string containing the name to check.
     * 
     * @return true if the name is valid, false otherwise.
     */
    public static boolean CheckLogicalName(String name)
    {
        if (name == null) {
            return false;
        }
        if (name.equals("")) {
            return true;
        }
        if (name.length() > 19) {
            return false;
        }
        return name.matches("^[A-Za-z0-9_-]*$");
    }

    /**
     * Register a callback function, to be called each time
     * a device is pluged. This callback will be invoked while yUpdateDeviceList
     * is running. You will have to call this function on a regular basis.
     * 
     * @param arrivalCallback : a procedure taking a YModule parameter, or null
     *         to unregister a previously registered  callback.
     */
    public static void RegisterDeviceArrivalCallback(
            YAPI.DeviceArrivalCallback arrivalCallback)
    {
        _arrivalCallback = arrivalCallback;
    }

    /**
     * Register a device logical name change callback
     */
    public static void RegisterDeviceChangeCallback(
            YAPI.DeviceChangeCallback changeCallback)
    {
        _namechgCallback = changeCallback;
    }

    /**
     * Register a callback function, to be called each time
     * a device is unpluged. This callback will be invoked while yUpdateDeviceList
     * is running. You will have to call this function on a regular basis.
     * 
     * @param removalCallback : a procedure taking a YModule parameter, or null
     *         to unregister a previously registered  callback.
     */
    public static void RegisterDeviceRemovalCallback(
            YAPI.DeviceRemovalCallback removalCallback)
    {
        _removalCallback = removalCallback;
    }

    /**
     * Registers a log callback function. This callback will be called each time
     * the API have something to say. Quite usefull to debug the API.
     * 
     * @param logfun : a procedure taking a string parameter, or null
     *         to unregister a previously registered  callback.
     */
    public static void RegisterLogFunction(YAPI.LogCallback logfun)
    {
        _logCallback = logfun;
    }

    public static void Log(String message)
    {
        if (_logCallback != null) {
            _logCallback.yLog(message);
        }
    }
}