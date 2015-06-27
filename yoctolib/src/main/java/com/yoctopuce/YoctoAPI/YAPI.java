/*********************************************************************
 * $Id: YAPI.java 20720 2015-06-23 16:35:09Z seb $
 *
 * High-level programming interface, common to all modules
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

/**
 *
 */
public class YAPI {


    // Return value for invalid strings
    public static final String INVALID_STRING = "!INVALID!";
    public static final double INVALID_DOUBLE = -1.79769313486231E+308;
    public static final int INVALID_INT = -2147483648;
    public static final long INVALID_LONG = -9223372036854775807L;
    public static final int INVALID_UINT = -1;
    public static final String YOCTO_API_VERSION_STR = "1.10";
    public static final String YOCTO_API_BUILD_STR = "20773";
    public static final int YOCTO_API_VERSION_BCD = 0x0110;
    public static final int YOCTO_VENDORID = 0x24e0;
    public static final int YOCTO_DEVID_FACTORYBOOT = 1;
    public static final int YOCTO_DEVID_BOOTLOADER = 2;
    // --- (generated code: YFunction return codes)
    // Yoctopuce error codes, used by default as function return value
    public static final int SUCCESS = 0;                   // everything worked all right
    public static final int NOT_INITIALIZED = -1;          // call yInitAPI() first !
    public static final int INVALID_ARGUMENT = -2;         // one of the arguments passed to the function is invalid
    public static final int NOT_SUPPORTED = -3;            // the operation attempted is (currently) not supported
    public static final int DEVICE_NOT_FOUND = -4;         // the requested device is not reachable
    public static final int VERSION_MISMATCH = -5;         // the device firmware is incompatible with this API version
    public static final int DEVICE_BUSY = -6;              // the device is busy with another task and cannot answer
    public static final int TIMEOUT = -7;                  // the device took too long to provide an answer
    public static final int IO_ERROR = -8;                 // there was an I/O problem while talking to the device
    public static final int NO_MORE_DATA = -9;             // there is no more data to read from
    public static final int EXHAUSTED = -10;               // you have run out of a limited resource, check the documentation
    public static final int DOUBLE_ACCES = -11;            // you have two process that try to access to the same device
    public static final int UNAUTHORIZED = -12;            // unauthorized access to password-protected device
    public static final int RTC_NOT_READY = -13;           // real-time clock has not been initialized (or time was lost)
    public static final int FILE_NOT_FOUND = -14;          // the file is not found

//--- (end of generated code: YFunction return codes)
    static final String DefaultEncoding = "ISO-8859-1";
    static Charset DeviceCharset;

    // Encoding types
    static final int YOCTO_CALIB_TYPE_OFS = 30;

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
    public static final int RESEND_MISSING_PKT = 4;
    public static final int DETECT_ALL = DETECT_USB | DETECT_NET;
    public static final int DEFAULT_PKT_RESEND_DELAY = 50;
    static int pktAckDelay = DEFAULT_PKT_RESEND_DELAY;


    private final YSSDP.YSSDPReportInterface _ssdpCallback = new YSSDP.YSSDPReportInterface() {
        @Override
        public void HubDiscoveryCallback(String serial, String urlToRegister, String urlToUnregister)
        {
            if (urlToRegister != null) {
                synchronized (_newHubCallbackLock) {
                    if (_HubDiscoveryCallback != null)
                        _HubDiscoveryCallback.yHubDiscoveryCallback(serial, urlToRegister);
                }
            }
            if ((_apiMode & DETECT_NET) != 0) {
                if (urlToRegister != null) {
                    if (urlToUnregister != null) {
                        _UnregisterHub(urlToUnregister);
                    }
                    try {
                        _PreregisterHub(urlToRegister);
                    } catch (YAPI_Exception ex) {
                        _Log("Unable to register hub " + urlToRegister + " detected by SSDP:" + ex.toString());
                    }
                }
            }
        }
    };


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

    private static final HashMap<String, YPEntry.BaseClass> _BaseType;

    static {
        _BaseType = new HashMap<String, YPEntry.BaseClass>();
        _BaseType.put("Function", YPEntry.BaseClass.Function);
        _BaseType.put("Sensor", YPEntry.BaseClass.Sensor);
    }


    private final static CalibrationHandlerCallback linearCalibrationHandler = new CalibrationHandlerCallback() {

        @Override
        public double yCalibrationHandler(double rawValue, int calibType, ArrayList<Integer> params, ArrayList<Double> rawValues, ArrayList<Double> refValues)
        {
            // calibration types n=1..10 and 11.20 are meant for linear calibration using n points
            int npt;
            double x = rawValues.get(0);
            double adj = refValues.get(0) - x;
            int i = 0;

            if (calibType < YAPI.YOCTO_CALIB_TYPE_OFS) {
                npt = calibType % 10;
                if (npt > rawValues.size()) npt = rawValues.size();
                if (npt > refValues.size()) npt = refValues.size();
            } else {
                npt = refValues.size();
            }
            while (rawValue > rawValues.get(i) && ++i < npt) {
                double x2 = x;
                double adj2 = adj;

                x = rawValues.get(i);
                adj = refValues.get(i) - x;

                if (rawValue < x && x > x2) {
                    adj = adj2 + (adj - adj2) * (rawValue - x2) / (x - x2);
                }
            }
            return rawValue + adj;
        }
    };


    // Non static Variable
    // Default cache validity (in [ms]) before reloading data from device. This
    // saves a lots of traffic.
    // Note that a value under 2 ms makes little sense since a USB bus itself
    // has a 2ms round trip period
    public int DefaultCacheValidity;
    private int _apiMode;
    ArrayList<YGenericHub> _hubs; // array of root urls
    private HashMap<String, YDevice> _devs; // hash table of devices, by serial number
    private HashMap<String, String> _snByUrl; // serial number for each device, by URL
    private HashMap<String, String> _snByName; // serial number for each device, by name
    private HashMap<String, YFunctionType> _fnByType; // functions by type
    private boolean _firstArrival;
    private final Queue<PlugEvent> _pendingCallbacks = new LinkedList<PlugEvent>();
    private final Queue<DataEvent> _data_events = new LinkedList<DataEvent>();
    private DeviceArrivalCallback _arrivalCallback = null;
    private DeviceChangeCallback _namechgCallback = null;
    private DeviceRemovalCallback _removalCallback = null;
    private LogCallback _logCallback = null;
    private final Object _newHubCallbackLock = new Object();
    private HubDiscoveryCallback _HubDiscoveryCallback = null;
    private HashMap<Integer, CalibrationHandlerCallback> _calibHandlers = new HashMap<Integer, YAPI.CalibrationHandlerCallback>();
    private YSSDP _ssdp;

    //YFunction Callback list
    private static final ArrayList<YFunction> _ValueCallbackList = new ArrayList<YFunction>();
    private static final ArrayList<YFunction> _TimedReportCallbackList = new ArrayList<YFunction>();
    // YDevice cache
    //public static ArrayList<YDevice> _devCache = new ArrayList<YDevice>();// Device cache entries

    public interface HubDiscoveryCallback {
        /**
         * @param serial : the serial number of the discovered Hub
         * @param url    : the URL (with port number) of the discoveredHub
         */
        void yHubDiscoveryCallback(String serial, String url);
    }


    static class DataEvent {

        private final YFunction _fun;
        private final String _value;
        private final ArrayList<Integer> _report;
        private final double _timestamp;

        public DataEvent(YFunction fun, String value)
        {
            _fun = fun;
            _value = value;
            _report = null;
            _timestamp = 0;
        }

        public DataEvent(YFunction fun, double timestamp, ArrayList<Integer> report)
        {
            _fun = fun;
            _value = null;
            _timestamp = timestamp;
            _report = report;
        }

        public void invoke()
        {
            if (_value == null) {
                YSensor sensor = (YSensor) _fun;
                YMeasure mesure = sensor._decodeTimedReport(_timestamp, _report);
                sensor._invokeTimedReportCallback(mesure);
            } else {
                // new value
                _fun._invokeValueCallback(_value);
            }
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

    void pushPlugEvent(PlugEvent.Event ev, String serial)
    {
        synchronized (_pendingCallbacks) {
            _pendingCallbacks.add(new PlugEvent(ev, serial));
        }
    }

    private synchronized void _updateDeviceList_internal(boolean forceupdate, boolean invokecallbacks) throws YAPI_Exception
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
            while (true) {
                PlugEvent evt;
                synchronized (_pendingCallbacks) {
                    if (_pendingCallbacks.isEmpty()) {
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
    CalibrationHandlerCallback _getCalibrationHandler(int calibType)
    {
        if (!_calibHandlers.containsKey(calibType)) {
            return null;
        }
        return _calibHandlers.get(calibType);
    }


    private final static double decExp[] = new double[]{
            1.0e-6, 1.0e-5, 1.0e-4, 1.0e-3, 1.0e-2, 1.0e-1, 1.0,
            1.0e1, 1.0e2, 1.0e3, 1.0e4, 1.0e5, 1.0e6, 1.0e7, 1.0e8, 1.0e9};

    // Convert Yoctopuce 16-bit decimal floats to standard double-precision floats
    //
    static double _decimalToDouble(int val)
    {
        boolean negate = false;
        double res;
        int mantis = val & 2047;

        if (mantis == 0) {
            return 0.0;
        }
        if (val > 32767) {
            negate = true;
            val = 65536 - val;
        } else if (val < 0) {
            negate = true;
            val = -val;
        }
        int exp = val >> 11;
        res = (double) mantis * decExp[exp];
        return (negate ? -res : res);
    }

    // Convert standard double-precision floats to Yoctopuce 16-bit decimal floats
    //
    static long _doubleToDecimal(double val)
    {
        int negate = 0;
        double comp, mant;
        int decpow;
        long res;

        if (val == 0.0) {
            return 0;
        }
        if (val < 0) {
            negate = 1;
            val = -val;
        }
        comp = val / 1999.0;
        decpow = 0;
        while (comp > decExp[decpow] && decpow < 15) {
            decpow++;
        }
        mant = val / decExp[decpow];
        if (decpow == 15 && mant > 2047.0) {
            res = (15 << 11) + 2047; // overflow
        } else {
            res = (decpow << 11) + Math.round(mant);
        }
        return (negate != 0 ? -res : res);
    }

    // Parse an array of u16 encoded in a base64-like string with memory-based compression
    static ArrayList<Integer> _decodeWords(String data)
    {
        ArrayList<Integer> udata = new ArrayList<Integer>();
        int datalen = data.length();
        int p = 0;
        while (p < datalen) {
            int val;
            int c = data.charAt(p++);
            if (c == (int) '*') {
                val = 0;
            } else if (c == (int) 'X') {
                val = 0xffff;
            } else if (c == (int) 'Y') {
                val = 0x7fff;
            } else if (c >= (int) 'a') {
                int srcpos = udata.size() - 1 - (c - (int) 'a');
                if (srcpos < 0) {
                    val = 0;
                } else {
                    val = udata.get(srcpos);
                }
            } else {
                if (p + 2 > datalen) {
                    return udata;
                }
                val = c - (int) '0';
                c = data.charAt(p++);
                val += (c - (int) '0') << 5;
                c = data.charAt(p++);
                if (c == (int) 'z') {
                    c = '\\';
                }
                val += (c - (int) '0') << 10;
            }
            udata.add(val);
        }
        return udata;
    }

    // Parse an array of u16 encoded in a base64-like string with memory-based compression
    static ArrayList<Integer> _decodeFloats(String data)
    {
        ArrayList<Integer> idata = new ArrayList<Integer>();
        int datalen = data.length();
        int p = 0;
        while (p < datalen) {
            int val = 0;
            int sign = 1;
            int dec = 0;
            int decInc = 0;
            int c = data.charAt(p++);
            while (c != (int) '-' && (c < (int) '0' || c > (int) '9')) {
                if (p >= datalen) {
                    return idata;
                }
                c = data.charAt(p++);
            }
            if (c == '-') {
                if (p >= datalen) {
                    return idata;
                }
                sign = -sign;
                c = data.charAt(p++);
            }
            while ((c >= '0' && c <= '9') || c == '.') {
                if (c == '.') {
                    decInc = 1;
                } else if (dec < 3) {
                    val = val * 10 + (c - '0');
                    dec += decInc;
                }
                if (p < datalen) {
                    c = data.charAt(p++);
                } else {
                    c = 0;
                }
            }
            if (dec < 3) {
                if (dec == 0) val *= 1000;
                else if (dec == 1) val *= 100;
                else val *= 10;
            }
            idata.add(sign * val);
        }
        return idata;
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

    public static int _atoi(String str)
    {
        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }
        int i = 0;
        if (str.charAt(i) == '-' || str.charAt(i) == '+') {
            i++;
        }
        for (; i < str.length(); i++) {

            //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i)))
                break;
        }
        if (i == 0) {
            return 0;
        }
        str = str.substring(0, i);
        return Integer.valueOf(str);
    }

    final protected static char[] _hexArray = "0123456789abcdef".toCharArray();

    static String _bytesToHexStr(byte[] bytes, int offset, int len)
    {
        char[] hexChars = new char[len * 2];
        for (int j = 0; j < len; j++) {
            int v = bytes[offset + j] & 0xFF;
            hexChars[j * 2] = _hexArray[v >>> 4];
            hexChars[j * 2 + 1] = _hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] _hexStrToBin(String hex_str)
    {
        int len = hex_str.length() / 2;
        byte[] res = new byte[len];
        for (int i = 0; i < len; i++) {
            res[i] = (byte) ((Character.digit(hex_str.charAt(i * 2), 16) << 4)
                    + Character.digit(hex_str.charAt(i * 2 + 1), 16));
        }
        return res;
    }

    public static byte[] _bytesMerge(byte[] array_a, byte[] array_b)
    {
        byte[] res = new byte[array_a.length + array_b.length];
        System.arraycopy(array_a, 0, res, 0, array_a.length);
        System.arraycopy(array_b, 0, res, array_a.length, array_b.length);
        return res;
    }

    // Return a Device object for a specified URL, serial number or logical
    // device name
    // This function will not cause any network access
    YDevice getDevice(String device)
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
    String functionClass(String funcid)
    {
        int dotpos = funcid.indexOf('.');

        if (dotpos >= 0) {
            funcid = funcid.substring(dotpos + 1);
        }
        int classlen = funcid.length();

        while (funcid.charAt(classlen - 1) <= 57) {
            classlen--;
        }

        String classname = funcid.substring(0, 1).toUpperCase(Locale.US)
                + funcid.substring(1, classlen);
        getFnByType(classname);

        return classname;
    }

    // Reindex a device in YAPI after a name change detected by device refresh
    void reindexDevice(YDevice dev)
    {
        String serial = dev.getSerialNumber();
        String lname = dev.getLogicalName();
        _devs.put(serial, dev);

        if (!lname.equals("")) {
            _snByName.put(lname, serial);
        }

        _fnByType.get("Module").reindexFunction(dev.getModuleYPEntry());
        int count = dev.functionCount();
        for (int i = 0; i < count; i++) {
            YPEntry yp = dev.getYPEntry(i);
            _fnByType.get(yp.getClassname()).reindexFunction(yp);
        }
    }

    // Remove a device from YAPI after an unplug detected by device refresh
    void forgetDevice(YDevice dev)
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
            YPEntry yp = dev.getYPEntry(i);
            _fnByType.get(yp.getClassname()).forgetFunction(yp.getHardwareId());
        }
    }

    YFunctionType getFnByType(String className)
    {
        if (!_fnByType.containsKey(className)) {
            _fnByType.put(className, new YFunctionType(className));
        }
        return _fnByType.get(className);
    }

    // Find the best known identifier (hardware Id) for a given function
    YPEntry resolveFunction(String className, String func)
            throws YAPI_Exception
    {
        if (!_BaseType.containsKey(className)) {
            return getFnByType(className).getYPEntry(func);
        } else {
            // using an abstract baseType
            YPEntry.BaseClass baseType = _BaseType.get(className);
            for (YFunctionType subClassType : _fnByType.values()) {
                try {
                    YPEntry yp = subClassType.getYPEntry(func);
                    if (yp.getBaseclass().equals(baseType)) {
                        return yp;
                    }
                } catch (YAPI_Exception ignore) {
                }
            }
        }
        throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "No function of type " + className + " found");
    }


    // Retrieve a function object by hardware id, updating the indexes on the
    // fly if needed
    void setFunction(String className, String func, YFunction yfunc)
    {
        getFnByType(className).setFunction(func, yfunc);
    }

    // Retrieve a function object by hardware id, logicalname, updating the indexes on the
    // fly if needed
    YFunction getFunction(String className, String func)
    {

        return getFnByType(className).getFunction(func);
    }

    // Set a function advertised value by hardware id
    void setFunctionValue(String hwid, String pubval)
    {
        String classname = functionClass(hwid);
        YFunctionType fnByType = getFnByType(classname);
        fnByType.setFunctionValue(hwid, pubval);
    }

    // Set a function advertised value by hardware id
    void setTimedReport(String hwid, double deviceTime, ArrayList<Integer> report)
    {
        String classname = functionClass(hwid);
        getFnByType(classname).setTimedReport(hwid, deviceTime, report);
    }

    // Queue a function data event (timed report of notification value)
    void _PushDataEvent(DataEvent ev)
    {
        synchronized (_data_events) {
            _data_events.add(ev);
        }
    }

    // Find the hardwareId for the first instance of a given function class
    String getFirstHardwareId(String className)
    {

        if (!_BaseType.containsKey(className)) {
            YFunctionType ft = getFnByType(className);
            YPEntry yp = ft.getFirstYPEntry();
            if (yp == null)
                return null;
            return yp.getHardwareId();
        } else {
            // using an abstract baseType
            YPEntry.BaseClass baseType = _BaseType.get(className);
            for (YFunctionType subClassType : _fnByType.values()) {
                YPEntry yp = subClassType.getFirstYPEntry();
                if (yp != null && yp.getBaseclass().equals(baseType)) {
                    return yp.getHardwareId();
                }
            }
            return null;
        }
    }

    // Find the hardwareId for the next instance of a given function class
    String getNextHardwareId(String className, String hwid)
    {
        if (!_BaseType.containsKey(className)) {
            YFunctionType ft = getFnByType(className);
            YPEntry yp = ft.getNextYPEntry(hwid);
            if (yp == null)
                return null;
            return yp.getHardwareId();
        } else {
            // enumeration of an abstract class
            YPEntry.BaseClass baseType = _BaseType.get(className);
            String prevclass = functionClass(hwid);
            YPEntry res = getFnByType(prevclass).getNextYPEntry(hwid);
            if (res != null)
                return res.getHardwareId();
            for (String altClassName : _fnByType.keySet()) {
                if (!prevclass.equals("")) {
                    if (!altClassName.equals(prevclass))
                        continue;
                    prevclass = "";
                    continue;
                }
                res = _fnByType.get(altClassName).getFirstYPEntry();
                if (res != null && res.getBaseclass().equals(baseType)) {
                    return res.getHardwareId();
                }
            }
            return null;
        }
    }

    YDevice funcGetDevice(String className, String func) throws YAPI_Exception
    {
        YPEntry resolved;
        try {
            resolved = resolveFunction(className, func);
        } catch (YAPI_Exception ex) {
            if (ex.errorType == DEVICE_NOT_FOUND && _hubs.isEmpty()) {
                // when USB is supported, check if no USB device is connected
                // before outputting this message
                throw new YAPI_Exception(ex.errorType,
                        "Impossible to contact any device because no hub has been registered");
            } else {
                _updateDeviceList_internal(true, false);
                resolved = resolveFunction(className, func);
            }
        }
        String devid = resolved.getSerial();
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

    protected synchronized int _AddNewHub(String url, boolean reportConnnectionLost, InputStream request, OutputStream response) throws YAPI_Exception
    {
        for (YGenericHub h : _hubs) {
            if (h.isSameRootUrl(url)) {
                return SUCCESS;
            }
        }
        YGenericHub newhub;
        YGenericHub.HTTPParams parsedurl;
        parsedurl = new YGenericHub.HTTPParams(url);
        // Add hub to known list
        if (url.equals("usb")) {
            YUSBHub.CheckUSBAcces();
            newhub = new YUSBHub(_hubs.size());
        } else if (url.equals("net")) {
            if ((_apiMode & DETECT_NET) == 0) {
                if (YUSBHub.RegisterLocalhost()) {
                    newhub = new YHTTPHub(_hubs.size(), new YGenericHub.HTTPParams("localhost"), false);
                    _hubs.add(newhub);
                    newhub.startNotifications();
                }
                _apiMode |= DETECT_NET;
                _ssdp.addCallback(_ssdpCallback);
            }
            return SUCCESS;
        } else if (parsedurl.getHost().equals("callback")) {
            newhub = new YCallbackHub(_hubs.size(), parsedurl, request, response);
        } else {
            newhub = new YHTTPHub(_hubs.size(), parsedurl, reportConnnectionLost);
        }
        _hubs.add(newhub);
        newhub.startNotifications();
        return SUCCESS;
    }

    protected synchronized int _TestHub(String url, int mstimeout, InputStream request, OutputStream response) throws YAPI_Exception
    {
        YGenericHub newhub;
        YGenericHub.HTTPParams parsedurl = new YGenericHub.HTTPParams(url);
        // Add hub to known list
        if (url.equals("usb")) {
            YUSBHub.CheckUSBAcces();
            newhub = new YUSBHub(0);
        } else if (url.equals("net")) {
            return SUCCESS;
        } else if (parsedurl.getHost().equals("callback")) {
            newhub = new YCallbackHub(0, parsedurl, request, response);
        } else {
            newhub = new YHTTPHub(0, parsedurl, true);
        }
        return newhub.ping(mstimeout);
    }


    void _UpdateValueCallbackList(YFunction func, boolean add)
    {
        if (add) {
            func.isOnline();
            synchronized (_ValueCallbackList) {
                if (!_ValueCallbackList.contains(func)) {
                    _ValueCallbackList.add(func);
                }
            }
        } else {
            synchronized (_ValueCallbackList) {
                _ValueCallbackList.remove(func);
            }
        }
    }

    YFunction _GetValueCallback(String hwid)
    {
        synchronized (_ValueCallbackList) {
            for (YFunction func : _ValueCallbackList) {
                try {
                    if (func.getHardwareId().equals(hwid)) {
                        return func;
                    }
                } catch (YAPI_Exception ignore) {
                }
            }
        }
        return null;
    }


    void _UpdateTimedReportCallbackList(YFunction func, boolean add)
    {
        if (add) {
            func.isOnline();
            synchronized (_TimedReportCallbackList) {
                if (!_TimedReportCallbackList.contains(func)) {
                    _TimedReportCallbackList.add(func);
                }
            }
        } else {
            synchronized (_TimedReportCallbackList) {
                _TimedReportCallbackList.remove(func);
            }
        }
    }

    YFunction _GetTimedReportCallback(String hwid)
    {
        synchronized (_TimedReportCallbackList) {
            for (YFunction func : _TimedReportCallbackList) {
                try {
                    if (func.getHardwareId().equals(hwid)) {
                        return func;
                    }
                } catch (YAPI_Exception ignore) {
                }
            }
        }
        return null;
    }


    private static HashMap<Long, YAPI> _MultipleYAPI = null;
    private static YAPI _SingleYAPI = null;


    @SuppressWarnings("UnusedDeclaration")
    public static synchronized void SetThreadSpecificMode() throws YAPI_Exception
    {
        if (_SingleYAPI != null)
            throw new YAPI_Exception(INVALID_ARGUMENT, "SetSingleThreadMode must be called before start using the Yoctopuce API");
        _MultipleYAPI = new HashMap<Long, YAPI>();
    }

    static synchronized YAPI GetYAPI()
    {
        if (_MultipleYAPI != null) {
            return _MultipleYAPI.get(Thread.currentThread().getId());
        } else {
            return _SingleYAPI;
        }
    }

    static synchronized YAPI SafeYAPI()
    {
        YAPI yapi = GetYAPI();
        if (yapi == null) {
            yapi = new YAPI();
            AddYAPI(yapi);
        }
        return yapi;
    }

    static synchronized void AddYAPI(YAPI yapi)
    {
        if (_MultipleYAPI != null) {
            _MultipleYAPI.put(Thread.currentThread().getId(), yapi);
        } else {
            _SingleYAPI = yapi;
        }
    }

    static synchronized void RemoveYAPI()
    {
        if (_MultipleYAPI != null) {
            _MultipleYAPI.remove(Thread.currentThread().getId());
        } else {
            _SingleYAPI = null;
        }
    }


    YAPI()
    {
        try {
            DeviceCharset = Charset.forName(DefaultEncoding);
        } catch (Exception dummy) {
            DeviceCharset = Charset.defaultCharset();
        }
        DefaultCacheValidity = 5;
        _hubs = new ArrayList<YGenericHub>();
        _devs = new HashMap<String, YDevice>();
        _snByUrl = new HashMap<String, String>();
        _snByName = new HashMap<String, String>();
        _fnByType = new HashMap<String, YFunctionType>(2);
        _firstArrival = true;
        _pendingCallbacks.clear();
        _data_events.clear();
        _ssdp = null;

        _fnByType.put("Module", new YFunctionType("Module"));
        for (int i = 1; i <= 20; i++) {
            _calibHandlers.put(i, linearCalibrationHandler);
        }
        _calibHandlers.put(YAPI.YOCTO_CALIB_TYPE_OFS, linearCalibrationHandler);
        _ssdp = new YSSDP();
    }

    void _FreeAPI()
    {
        if ((_apiMode & DETECT_NET) != 0) {
            _ssdp.Stop();
        }
        for (YGenericHub h : _hubs) {
            h.stopNotifications();
            h.release();
        }
    }


    public synchronized int _RegisterHub(String url) throws YAPI_Exception
    {
        _AddNewHub(url, true, null, null);
        // Register device list
        _updateDeviceList_internal(true, false);
        return SUCCESS;
    }


    public synchronized int _RegisterHub(String url, InputStream request, OutputStream response) throws YAPI_Exception
    {
        _AddNewHub(url, true, request, response);
        // Register device list
        _updateDeviceList_internal(true, false);
        return SUCCESS;
    }


    public synchronized int _PreregisterHub(String url) throws YAPI_Exception
    {
        _AddNewHub(url, false, null, null);
        return SUCCESS;
    }

    public synchronized void _UnregisterHub(String url)
    {
        if (url.equals("net")) {
            _apiMode &= ~DETECT_NET;
            return;
        }

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

    public synchronized int _UpdateDeviceList() throws YAPI_Exception
    {
        _updateDeviceList_internal(false, true);
        return SUCCESS;
    }

    public int _HandleEvents() throws YAPI_Exception
    {
        // handle pending events
        while (true) {
            DataEvent pv;
            synchronized (_data_events) {
                if (_data_events.isEmpty()) {
                    break;
                }
                pv = _data_events.poll();
            }
            pv.invoke();
        }
        return SUCCESS;
    }


    public int _Sleep(long ms_duration) throws YAPI_Exception
    {
        long end = GetTickCount() + ms_duration;

        do {
            _HandleEvents();
            if (end > GetTickCount()) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    throw new YAPI_Exception(YAPI.IO_ERROR,
                            "Thread has been interrupted");
                }
            }
        } while (end > GetTickCount());
        return SUCCESS;
    }

    public int _TriggerHubDiscovery() throws YAPI_Exception
    {
        // Register device list
        _ssdp.addCallback(_ssdpCallback);
        return YAPI.SUCCESS;
    }


    public void _RegisterDeviceArrivalCallback(
            YAPI.DeviceArrivalCallback arrivalCallback)
    {
        _arrivalCallback = arrivalCallback;
    }

    public void _RegisterDeviceChangeCallback(
            YAPI.DeviceChangeCallback changeCallback)
    {
        _namechgCallback = changeCallback;
    }

    public synchronized void _RegisterDeviceRemovalCallback(
            YAPI.DeviceRemovalCallback removalCallback)
    {
        _removalCallback = removalCallback;
    }

    public void _RegisterHubDiscoveryCallback(HubDiscoveryCallback hubDiscoveryCallback)
    {
        synchronized (_newHubCallbackLock) {
            _HubDiscoveryCallback = hubDiscoveryCallback;
        }
        try {
            _TriggerHubDiscovery();
        } catch (YAPI_Exception ignore) {
        }
    }

    public void _RegisterLogFunction(YAPI.LogCallback logfun)
    {
        _logCallback = logfun;
    }

    void _Log(String message)
    {
        if (_logCallback != null) {
            _logCallback.yLog(message);
        }
    }


    //PUBLIC STATIC METHOD:

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
        return YOCTO_API_VERSION_STR + ".20773";
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
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public static int InitAPI(int mode) throws YAPI_Exception
    {
        YAPI yapi = SafeYAPI();
        if ((mode & YAPI.DETECT_NET) != 0) {
            yapi._RegisterHub("net");
        }
        if ((mode & YAPI.RESEND_MISSING_PKT) != 0) {
            YAPI.pktAckDelay = DEFAULT_PKT_RESEND_DELAY;
        }
        if ((mode & YAPI.DETECT_USB) != 0) {
            yapi._RegisterHub("usb");
        }
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
        YAPI yapi = GetYAPI();
        if (yapi != null) {
            yapi._FreeAPI();
            RemoveYAPI();
        }
    }


    /**
     * Setup the Yoctopuce library to use modules connected on a given machine. The
     * parameter will determine how the API will work. Use the following values:
     *
     * <b>usb</b>: When the usb keyword is used, the API will work with
     * devices connected directly to the USB bus. Some programming languages such a Javascript,
     * PHP, and Java don't provide direct access to USB hardware, so usb will
     * not work with these. In this case, use a VirtualHub or a networked YoctoHub (see below).
     *
     * <b><i>x.x.x.x</i></b> or <b><i>hostname</i></b>: The API will use the devices connected to the
     * host with the given IP address or hostname. That host can be a regular computer
     * running a VirtualHub, or a networked YoctoHub such as YoctoHub-Ethernet or
     * YoctoHub-Wireless. If you want to use the VirtualHub running on you local
     * computer, use the IP address 127.0.0.1.
     *
     * <b>callback</b>: that keyword make the API run in "<i>HTTP Callback</i>" mode.
     * This a special mode allowing to take control of Yoctopuce devices
     * through a NAT filter when using a VirtualHub or a networked YoctoHub. You only
     * need to configure your hub to call your server script on a regular basis.
     * This mode is currently available for PHP and Node.JS only.
     *
     * Be aware that only one application can use direct USB access at a
     * given time on a machine. Multiple access would cause conflicts
     * while trying to access the USB modules. In particular, this means
     * that you must stop the VirtualHub software before starting
     * an application that uses direct USB access. The workaround
     * for this limitation is to setup the library to use the VirtualHub
     * rather than direct USB access.
     *
     * If access control has been activated on the hub, virtual or not, you want to
     * reach, the URL parameter should look like:
     *
     * http://username:password@address:port
     *
     * You can call <i>RegisterHub</i> several times to connect to several machines.
     *
     * @param url : a string containing either "usb","callback" or the
     *         root URL of the hub to monitor
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public static int RegisterHub(String url) throws YAPI_Exception
    {
        return SafeYAPI()._RegisterHub(url);
    }


    public static int RegisterHub(String url, InputStream request, OutputStream response) throws YAPI_Exception
    {
        return SafeYAPI()._RegisterHub(url, request, response);
    }


    /**
     * This function is used only on Android. Before calling yRegisterHub("usb")
     * you need to activate the USB host port of the system. This function takes as argument,
     * an object of class android.content.Context (or any subclass).
     * It is not necessary to call this function to reach modules through the network.
     *
     * @param osContext : an object of class android.content.Context (or any subclass).
     *
     * @throws YAPI_Exception on error
     */
    public static void EnableUSBHost(Object osContext) throws YAPI_Exception
    {
        YUSBHub.SetContextType(osContext);
    }

    /**
     * Fault-tolerant alternative to RegisterHub(). This function has the same
     * purpose and same arguments as RegisterHub(), but does not trigger
     * an error when the selected hub is not available at the time of the function call.
     * This makes it possible to register a network hub independently of the current
     * connectivity, and to try to contact it only when a device is actively needed.
     *
     * @param url : a string containing either "usb","callback" or the
     *         root URL of the hub to monitor
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public static int PreregisterHub(String url) throws YAPI_Exception
    {
        return SafeYAPI()._PreregisterHub(url);
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
        SafeYAPI()._UnregisterHub(url);
    }


    /**
     * Test if the hub is reachable. This method do not register the hub, it only test if the
     * hub is usable. The url parameter follow the same convention as the RegisterHub
     * method. This method is useful to verify the authentication parameters for a hub. It
     * is possible to force this method to return after mstimeout milliseconds.
     *
     * @param url : a string containing either "usb","callback" or the
     *         root URL of the hub to monitor
     * @param mstimeout : the number of millisecond available to test the connection.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * On failure returns a negative error code.
     */
    public static int TestHub(String url, int mstimeout) throws YAPI_Exception
    {
        return SafeYAPI()._TestHub(url, mstimeout, null, null);
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
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public static int UpdateDeviceList() throws YAPI_Exception
    {
        return SafeYAPI()._UpdateDeviceList();
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
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public static int HandleEvents() throws YAPI_Exception
    {
        return SafeYAPI()._HandleEvents();
    }

    /**
     * Pauses the execution flow for a specified duration.
     * This function implements a passive waiting loop, meaning that it does not
     * consume CPU cycles significantly. The processor is left available for
     * other threads and processes. During the pause, the library nevertheless
     * reads from time to time information from the Yoctopuce modules by
     * calling yHandleEvents(), in order to stay up-to-date.
     *
     * This function may signal an error in case there is a communication problem
     * while contacting a module.
     *
     * @param ms_duration : an integer corresponding to the duration of the pause,
     *         in milliseconds.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public static int Sleep(long ms_duration) throws YAPI_Exception
    {
        return SafeYAPI()._Sleep(ms_duration);
    }

    /**
     * Force a hub discovery, if a callback as been registered with yRegisterDeviceRemovalCallback it
     * will be called for each net work hub that will respond to the discovery.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     * @throws YAPI_Exception on error
     */
    public static int TriggerHubDiscovery() throws YAPI_Exception
    {
        return SafeYAPI()._TriggerHubDiscovery();
    }

    /**
     * Returns the current value of a monotone millisecond-based time counter.
     * This counter can be used to compute delays in relation with
     * Yoctopuce devices, which also uses the millisecond as timebase.
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
        return name != null && (name.equals("") || name.length() <= 19 && name.matches("^[A-Za-z0-9_-]*$"));
    }

    /**
     * Register a callback function, to be called each time
     * a device is plugged. This callback will be invoked while yUpdateDeviceList
     * is running. You will have to call this function on a regular basis.
     *
     * @param arrivalCallback : a procedure taking a YModule parameter, or null
     *         to unregister a previously registered  callback.
     */
    public static void RegisterDeviceArrivalCallback(YAPI.DeviceArrivalCallback arrivalCallback)
    {
        SafeYAPI()._RegisterDeviceArrivalCallback(arrivalCallback);
    }

    public static void RegisterDeviceChangeCallback(YAPI.DeviceChangeCallback changeCallback)
    {
        SafeYAPI()._RegisterDeviceChangeCallback(changeCallback);
    }

    /**
     * Register a callback function, to be called each time
     * a device is unplugged. This callback will be invoked while yUpdateDeviceList
     * is running. You will have to call this function on a regular basis.
     *
     * @param removalCallback : a procedure taking a YModule parameter, or null
     *         to unregister a previously registered  callback.
     */
    public static void RegisterDeviceRemovalCallback(YAPI.DeviceRemovalCallback removalCallback)
    {
        SafeYAPI()._RegisterDeviceRemovalCallback(removalCallback);
    }

    /**
     * Register a callback function, to be called each time an Network Hub send
     * an SSDP message. The callback has two string parameter, the first one
     * contain the serial number of the hub and the second contain the URL of the
     * network hub (this URL can be passed to RegisterHub). This callback will be invoked
     * while yUpdateDeviceList is running. You will have to call this function on a regular basis.
     *
     * @param hubDiscoveryCallback : a procedure taking two string parameter, or null
     *         to unregister a previously registered  callback.
     */
    public static void RegisterHubDiscoveryCallback(HubDiscoveryCallback hubDiscoveryCallback)
    {
        SafeYAPI()._RegisterHubDiscoveryCallback(hubDiscoveryCallback);
    }

    /**
     * Registers a log callback function. This callback will be called each time
     * the API have something to say. Quite useful to debug the API.
     *
     * @param logfun : a procedure taking a string parameter, or null
     *         to unregister a previously registered  callback.
     */
    public static void RegisterLogFunction(YAPI.LogCallback logfun)
    {
        SafeYAPI()._RegisterLogFunction(logfun);
    }

}