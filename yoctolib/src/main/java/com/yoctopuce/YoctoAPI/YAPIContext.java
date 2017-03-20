package com.yoctopuce.YoctoAPI;


import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

@SuppressWarnings("unused")
public class YAPIContext
{

    static class DataEvent
    {

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

    static class PlugEvent
    {

        public enum Event
        {

            PLUG, UNPLUG, CHANGE
        }

        public Event ev;
        public YModule module;

        public PlugEvent(YAPIContext yctx, Event ev, String serial)
        {
            this.ev = ev;
            this.module = YModule.FindModuleInContext(yctx, serial + ".module");
        }
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
        ArrayList<Integer> udata = new ArrayList<>();
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
        ArrayList<Integer> idata = new ArrayList<>();
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
        int s = 0;
        if (str.charAt(s) == '+') {
            s++;
        }
        int i = s;
        if (str.charAt(i) == '-') {
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
        str = str.substring(s, i);
        return Integer.valueOf(str);
    }

    final protected static char[] _hexArray = "0123456789ABCDEF".toCharArray();

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


    // Return the class name for a given function ID or full Hardware Id
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

        return funcid.substring(0, 1).toUpperCase(Locale.US)
                + funcid.substring(1, classlen);
    }


    public int DefaultCacheValidity = 5;
    String _defaultEncoding = YAPI.DefaultEncoding;
    final Charset _deviceCharset;
    private int _apiMode;
    final ArrayList<YGenericHub> _hubs = new ArrayList<>(1); // array of root urls
    private boolean _firstArrival;
    private final Queue<PlugEvent> _pendingCallbacks = new LinkedList<>();
    private final Queue<DataEvent> _data_events = new LinkedList<>();
    private YAPI.DeviceArrivalCallback _arrivalCallback;
    private YAPI.DeviceChangeCallback _namechgCallback;
    private YAPI.DeviceRemovalCallback _removalCallback;
    private YAPI.LogCallback _logCallback;
    private final Object _newHubCallbackLock = new Object();
    private YAPI.HubDiscoveryCallback _HubDiscoveryCallback;
    private final HashMap<Integer, YAPI.CalibrationHandlerCallback> _calibHandlers = new HashMap<>();
    private final YSSDP _ssdp;
    final YHash _yHash;
    private final ArrayList<YFunction> _ValueCallbackList = new ArrayList<>();
    private final ArrayList<YFunction> _TimedReportCallbackList = new ArrayList<>();
    private int _pktAckDelay = 0;



    private final YSSDP.YSSDPReportInterface _ssdpCallback = new YSSDP.YSSDPReportInterface()
    {
        @Override
        public void HubDiscoveryCallback(String serial, String urlToRegister, String urlToUnregister)
        {
            if (urlToRegister != null) {
                synchronized (_newHubCallbackLock) {
                    if (_HubDiscoveryCallback != null)
                        _HubDiscoveryCallback.yHubDiscoveryCallback(serial, urlToRegister);
                }
            }
            if ((_apiMode & YAPI.DETECT_NET) != 0) {
                if (urlToRegister != null) {
                    if (urlToUnregister != null) {
                        UnregisterHub(urlToUnregister);
                    }
                    try {
                        PreregisterHub(urlToRegister);
                    } catch (YAPI_Exception ex) {
                        _Log("Unable to register hub " + urlToRegister + " detected by SSDP:" + ex.toString());
                    }
                }
            }
        }
    };


    private final static YAPI.CalibrationHandlerCallback linearCalibrationHandler = new YAPI.CalibrationHandlerCallback()
    {

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

    //INTERNAL METHOD:

    public YAPIContext()
    {
        Charset charset;
        try {
            charset = Charset.forName(YAPI.DefaultEncoding);
        } catch (Exception dummy) {
            charset = Charset.defaultCharset();
        }
        _deviceCharset = charset;
        _yHash = new YHash(this);
        _ssdp = new YSSDP(this);
        resetContext();
    }

    private void resetContext()
    {
        _apiMode = 0;
        _firstArrival = true;
        _pendingCallbacks.clear();
        _data_events.clear();
        _arrivalCallback = null;
        _namechgCallback = null;
        _removalCallback = null;
        _logCallback = null;
        _HubDiscoveryCallback = null;
        _hubs.clear();
        _calibHandlers.clear();
        _ssdp.reset();
        _yHash.reset();
        _ValueCallbackList.clear();
        _TimedReportCallbackList.clear();
        for (int i = 1; i <= 20; i++) {
            _calibHandlers.put(i, linearCalibrationHandler);
        }
        _calibHandlers.put(YAPI.YOCTO_CALIB_TYPE_OFS, linearCalibrationHandler);
    }

    void _pushPlugEvent(PlugEvent.Event ev, String serial)
    {
        synchronized (_pendingCallbacks) {
            _pendingCallbacks.add(new PlugEvent(this, ev, serial));
        }
    }


    // Queue a function data event (timed report of notification value)
    void _PushDataEvent(DataEvent ev)
    {
        synchronized (_data_events) {
            _data_events.add(ev);
        }
    }

    /*
    * Return a the calibration handler for a given type
    */
    YAPI.CalibrationHandlerCallback _getCalibrationHandler(int calibType)
    {
        if (!_calibHandlers.containsKey(calibType)) {
            return null;
        }
        return _calibHandlers.get(calibType);
    }


    YDevice funcGetDevice(String className, String func) throws YAPI_Exception
    {
        String resolved;
        try {
            resolved = _yHash.resolveSerial(className, func);
        } catch (YAPI_Exception ex) {
            if (ex.errorType == YAPI.DEVICE_NOT_FOUND && _hubs.isEmpty()) {
                throw new YAPI_Exception(ex.errorType,
                        "Impossible to contact any device because no hub has been registered");
            } else {
                _updateDeviceList_internal(true, false);
                resolved = _yHash.resolveSerial(className, func);
            }
        }
        YDevice dev = _yHash.getDevice(resolved);
        if (dev == null) {
            // try to force a device list update to check if the device arrived
            // in between
            _updateDeviceList_internal(true, false);
            dev = _yHash.getDevice(resolved);
            if (dev == null) {
                throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "Device [" + resolved + "] not online");
            }

        }
        return dev;
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

    private synchronized int _AddNewHub(String url, boolean reportConnnectionLost, InputStream request, OutputStream response, Object session) throws YAPI_Exception
    {
        for (YGenericHub h : _hubs) {
            if (h.isSameHub(url, request, response, session)) {
                return YAPI.SUCCESS;
            }
        }
        YGenericHub newhub;
        YGenericHub.HTTPParams parsedurl;
        parsedurl = new YGenericHub.HTTPParams(url);
        // Add hub to known list
        if (url.equals("usb")) {
            YUSBHub.CheckUSBAcces();
            newhub = new YUSBHub(this, _hubs.size(), true, _pktAckDelay);
        } else if (url.equals("usb_silent")) {
            YUSBHub.CheckUSBAcces();
            newhub = new YUSBHub(this, _hubs.size(), false, _pktAckDelay);
        } else if (url.equals("net")) {
            if ((_apiMode & YAPI.DETECT_NET) == 0) {
                //noinspection ConstantConditions
                if (YUSBHub.RegisterLocalhost()) {
                    newhub = new YHTTPHub(this, _hubs.size(), new YGenericHub.HTTPParams("localhost"), false, null);
                    _hubs.add(newhub);
                    newhub.startNotifications();
                }
                _apiMode |= YAPI.DETECT_NET;
                _ssdp.addCallback(_ssdpCallback);
            }
            return YAPI.SUCCESS;
        } else if (parsedurl.getHost().equals("callback")) {
            if (session != null) {
                newhub = new YHTTPHub(this, _hubs.size(), parsedurl, reportConnnectionLost, session);
            } else {
                newhub = new YCallbackHub(this, _hubs.size(), parsedurl, request, response);
            }
        } else {
            newhub = new YHTTPHub(this, _hubs.size(), parsedurl, reportConnnectionLost, null);
        }
        _hubs.add(newhub);
        newhub.startNotifications();
        return YAPI.SUCCESS;
    }


    private synchronized void _updateDeviceList_internal(boolean forceupdate, boolean invokecallbacks) throws YAPI_Exception
    {
        if (_firstArrival && invokecallbacks && _arrivalCallback != null) {
            forceupdate = true;
        }

        // Rescan all hubs and update list of online devices
        for (YGenericHub h : _hubs) {
            try {
                h.updateDeviceList(forceupdate);
            } catch (InterruptedException e) {
                throw new YAPI_Exception(YAPI.IO_ERROR,
                        "Thread has been interrupted");
            }
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
                        _yHash.forgetDevice(evt.module.get_serialNumber());
                        break;
                }
            }
            if (_arrivalCallback != null && _firstArrival) {
                _firstArrival = false;
            }
        }
    }

    void _Log(String message)
    {
        if (_logCallback != null) {
            _logCallback.yLog(message);
        }
    }


    //PUBLIC METHOD:


    /**
     * Enables the acknowledge of every USB packet received by the Yoctopuce library.
     * This function allows the library to run on Android phones that tend to loose USB packets.
     * By default, this feature is disabled because it doubles the number of packets sent and slows
     * down the API considerably. Therefore, the acknowledge of incoming USB packets should only be
     * enabled on phones or tablets that loose USB packets. A delay of 50 milliseconds is generally
     * enough. In case of doubt, contact Yoctopuce support. To disable USB packets acknowledge,
     * call this function with the value 0. Note: this feature is only available on Android.
     *
     * @param pktAckDelay : then number of milliseconds before the module
     *         resend the last USB packet.
     */
    public void SetUSBPacketAckMs(int pktAckDelay)
    {
        this._pktAckDelay = pktAckDelay;
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
        return YAPI.GetAPIVersion();
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
    public int InitAPI(int mode) throws YAPI_Exception
    {
        if ((mode & YAPI.DETECT_NET) != 0) {
            RegisterHub("net");
        }
        if ((mode & YAPI.RESEND_MISSING_PKT) != 0) {
            _pktAckDelay = 50;
        }
        if ((mode & YAPI.DETECT_USB) != 0) {
            RegisterHub("usb");
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
    public void FreeAPI()
    {
        if ((_apiMode & YAPI.DETECT_NET) != 0) {
            _ssdp.Stop();
        }
        for (YGenericHub h : _hubs) {
            h.stopNotifications();
            h.release();
        }
        resetContext();
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
    public int RegisterHub(String url) throws YAPI_Exception
    {
        _AddNewHub(url, true, null, null, null);
        // Register device list
        _updateDeviceList_internal(true, false);
        return YAPI.SUCCESS;
    }


    public int RegisterHub(String url, InputStream request, OutputStream response) throws YAPI_Exception
    {
        _AddNewHub(url, true, request, response, null);
        // Register device list
        _updateDeviceList_internal(true, false);
        return YAPI.SUCCESS;
    }

    /**
     *
     */
    public int RegisterHubHTTPCallback(InputStream request, OutputStream response) throws YAPI_Exception
    {
        _AddNewHub("http://callback", true, request, response, null);
        // Register device list
        _updateDeviceList_internal(true, false);
        return YAPI.SUCCESS;
    }


    /**
     *
     */
    public int PreregisterHubWebSocketCallback(Object session) throws YAPI_Exception
    {
        return PreregisterHubWebSocketCallback(session, null, null);
    }

    /**
     *
     */
    public int PreregisterHubWebSocketCallback(Object session, String user, String pass) throws YAPI_Exception
    {
        if (user == null) {
            user = "";
        }
        if (pass != null) {
            user += ":" + pass;
        }
        String url = "ws://" + user + "@callback";
        _AddNewHub(url, true, null, null, session);
        return YAPI.SUCCESS;
    }


    /**
     *
     */
    public void UnregisterHubWebSocketCallback(Object session)
    {
        unregisterHubEx("ws://callback", null, null, session);
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
    public void EnableUSBHost(Object osContext) throws YAPI_Exception
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
    public int PreregisterHub(String url) throws YAPI_Exception
    {
        _AddNewHub(url, false, null, null, null);
        return YAPI.SUCCESS;
    }

    /**
     * Setup the Yoctopuce library to no more use modules connected on a previously
     * registered machine with RegisterHub.
     *
     * @param url : a string containing either "usb" or the
     *         root URL of the hub to monitor
     */
    public void UnregisterHub(String url)
    {
        if (url.equals("net")) {
            _apiMode &= ~YAPI.DETECT_NET;
            return;
        }
        unregisterHubEx(url, null, null, null);
    }

    private void unregisterHubEx(String url, InputStream request, OutputStream response, Object session)
    {
        for (YGenericHub h : _hubs) {
            if (h.isSameHub(url, request, response, session)) {
                h.stopNotifications();
                for (String serial : h._serialByYdx.values()) {
                    _yHash.forgetDevice(serial);
                }
                h.release();
                _hubs.remove(h);
                return;
            }
        }
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
    public int TestHub(String url, int mstimeout) throws YAPI_Exception
    {
        YGenericHub newhub;
        YGenericHub.HTTPParams parsedurl = new YGenericHub.HTTPParams(url);
        // Add hub to known list
        if (url.equals("usb")) {
            YUSBHub.CheckUSBAcces();
            newhub = new YUSBHub(this, 0, true, _pktAckDelay);
        } else if (url.equals("net")) {
            return YAPI.SUCCESS;
        } else if (parsedurl.getHost().equals("callback")) {
            // fixme add TestHub function  for callback
            newhub = new YCallbackHub(this, 0, parsedurl, null, null);
        } else {
            newhub = new YHTTPHub(this, 0, parsedurl, true, null);
        }
        return newhub.ping(mstimeout);
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
    public int UpdateDeviceList() throws YAPI_Exception
    {
        _updateDeviceList_internal(false, true);
        return YAPI.SUCCESS;
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
     * @throws YAPI_Exception on error
     */
    @SuppressWarnings("RedundantThrows")
    public int HandleEvents() throws YAPI_Exception
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
        return YAPI.SUCCESS;
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
    public int Sleep(long ms_duration) throws YAPI_Exception
    {
        long end = GetTickCount() + ms_duration;

        do {
            HandleEvents();
            if (end > GetTickCount()) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    throw new YAPI_Exception(YAPI.IO_ERROR,
                            "Thread has been interrupted");
                }
            }
        } while (end > GetTickCount());
        return YAPI.SUCCESS;
    }

    /**
     * Force a hub discovery, if a callback as been registered with yRegisterDeviceRemovalCallback it
     * will be called for each net work hub that will respond to the discovery.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int TriggerHubDiscovery() throws YAPI_Exception
    {
        // Register device list
        _ssdp.addCallback(_ssdpCallback);
        return YAPI.SUCCESS;
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
    public boolean CheckLogicalName(String name)
    {
        return YAPI.CheckLogicalName(name);
    }

    /**
     * Register a callback function, to be called each time
     * a device is plugged. This callback will be invoked while yUpdateDeviceList
     * is running. You will have to call this function on a regular basis.
     *
     * @param arrivalCallback : a procedure taking a YModule parameter, or null
     *         to unregister a previously registered  callback.
     */
    public void RegisterDeviceArrivalCallback(YAPI.DeviceArrivalCallback arrivalCallback)
    {
        _arrivalCallback = arrivalCallback;
    }

    public void RegisterDeviceChangeCallback(YAPI.DeviceChangeCallback changeCallback)
    {
        _namechgCallback = changeCallback;
    }

    /**
     * Register a callback function, to be called each time
     * a device is unplugged. This callback will be invoked while yUpdateDeviceList
     * is running. You will have to call this function on a regular basis.
     *
     * @param removalCallback : a procedure taking a YModule parameter, or null
     *         to unregister a previously registered  callback.
     */
    public void RegisterDeviceRemovalCallback(YAPI.DeviceRemovalCallback removalCallback)
    {
        _removalCallback = removalCallback;
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
    public void RegisterHubDiscoveryCallback(YAPI.HubDiscoveryCallback hubDiscoveryCallback)
    {
        synchronized (_newHubCallbackLock) {
            _HubDiscoveryCallback = hubDiscoveryCallback;
        }
        try {
            TriggerHubDiscovery();
        } catch (YAPI_Exception ignore) {
        }
    }

    /**
     * Registers a log callback function. This callback will be called each time
     * the API have something to say. Quite useful to debug the API.
     *
     * @param logfun : a procedure taking a string parameter, or null
     *         to unregister a previously registered  callback.
     */
    public void RegisterLogFunction(YAPI.LogCallback logfun)
    {
        _logCallback = logfun;
    }


}
