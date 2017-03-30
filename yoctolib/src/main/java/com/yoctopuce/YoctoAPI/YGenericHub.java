/*********************************************************************
 * $Id: YGenericHub.java 26952 2017-03-28 15:40:09Z seb $
 *
 * Internal YGenericHub object
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


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;


abstract class YGenericHub
{

    static final int NOTIFY_V2_LEGACY = 0;       // unused (reserved for compatibility with legacy notifications)
    static final int NOTIFY_V2_6RAWBYTES = 1;       // largest type: data is always 6 bytes
    static final int NOTIFY_V2_TYPEDDATA = 2;       // other types: first data byte holds the decoding format
    static final int NOTIFY_V2_FLUSHGROUP = 3;       // no data associated

    // stream type
    static final int YSTREAM_EMPTY = 0;
    static final int YSTREAM_TCP = 1;
    static final int YSTREAM_TCP_CLOSE = 2;
    static final int YSTREAM_NOTICE = 3;
    static final int YSTREAM_REPORT = 4;
    static final int YSTREAM_META = 5;
    static final int YSTREAM_REPORT_V2 = 6;
    static final int YSTREAM_NOTICE_V2 = 7;
    static final int YSTREAM_TCP_NOTIF = 8;
    static final int YSTREAM_TCP_ASYNCCLOSE = 9;


    static final int USB_META_UTCTIME = 1;
    static final int USB_META_DLFLUSH = 2;
    static final int USB_META_ACK_D2H_PACKET = 3;
    static final int USB_META_WS_ANNOUNCE = 4;
    static final int USB_META_WS_AUTHENTICATION = 5;
    static final int USB_META_WS_ERROR = 6;
    static final int USB_META_ACK_UPLOAD = 7;

    static final int USB_META_UTCTIME_SIZE = 5;
    static final int USB_META_DLFLUSH_SIZE = 1;
    static final int USB_META_ACK_D2H_PACKET_SIZE = 2;
    static final int USB_META_WS_ANNOUNCE_SIZE = 8 + YAPI.YOCTO_SERIAL_LEN;
    static final int USB_META_WS_AUTHENTICATION_SIZE = 28;
    static final int USB_META_WS_ERROR_SIZE = 6;
    static final int USB_META_ACK_UPLOAD_SIZE = 6;

    static final int USB_META_WS_PROTO_V1 = 1;  // adding authentication support
    static final int USB_META_WS_PROTO_V2 = 2;  // adding API packets throttling
    static final int VERSION_SUPPORT_ASYNC_CLOSE = 1;


    static final int USB_META_WS_VALID_SHA1 = 1;
    static final int USB_META_WS_AUTH_FLAGS_RW = 2;


    private static final int PUBVAL_LEGACY = 0;   // 0-6 ASCII characters (normally sent as YSTREAM_NOTICE)
    private static final int PUBVAL_1RAWBYTE = 1;   // 1 raw byte  (=2 characters)
    private static final int PUBVAL_2RAWBYTES = 2;   // 2 raw bytes (=4 characters)
    private static final int PUBVAL_3RAWBYTES = 3;   // 3 raw bytes (=6 characters)
    private static final int PUBVAL_4RAWBYTES = 4;   // 4 raw bytes (=8 characters)
    private static final int PUBVAL_5RAWBYTES = 5;   // 5 raw bytes (=10 characters)
    private static final int PUBVAL_6RAWBYTES = 6;   // 6 hex bytes (=12 characters) (sent as V2_6RAWBYTES)
    private static final int PUBVAL_C_LONG = 7;   // 32-bit C signed integer
    private static final int PUBVAL_C_FLOAT = 8;   // 32-bit C float
    private static final int PUBVAL_YOCTO_FLOAT_E3 = 9;   // 32-bit Yocto fixed-point format (e-3)
    private static final int PUBVAL_YOCTO_FLOAT_E6 = 10;   // 32-bit Yocto fixed-point format (e-6)

    static final long YPROG_BOOTLOADER_TIMEOUT = 20000;
    final YAPIContext _yctx;
    final HTTPParams _http_params;
    int _hubidx;
    protected long _notifyTrigger = 0;
    protected Object _notifyHandle = null;
    volatile long _devListValidity = 500;
    long _devListExpires = 0;
    final ConcurrentHashMap<Integer, String> _serialByYdx = new ConcurrentHashMap<>();
    private HashMap<String, YDevice> _devices = new HashMap<>();
    final boolean _reportConnnectionLost;
    private String _hubSerialNumber = null;

    YGenericHub(YAPIContext yctx, HTTPParams httpParams, int idx, boolean reportConnnectionLost)
    {
        _yctx = yctx;
        _hubidx = idx;
        _reportConnnectionLost = reportConnnectionLost;
        _http_params = httpParams;
    }

    abstract void release();

    abstract String getRootUrl();

    @SuppressWarnings("UnusedParameters")
    abstract boolean isSameHub(String url, Object request, Object response, Object session);

    abstract void startNotifications() throws YAPI_Exception;

    abstract void stopNotifications();


    static String decodePubVal(int typeV2, byte[] funcval, int ofs, int funcvallen)
    {
        String buffer = "";

        if (typeV2 == NOTIFY_V2_6RAWBYTES || typeV2 == NOTIFY_V2_TYPEDDATA) {
            int funcValType;

            if (typeV2 == NOTIFY_V2_6RAWBYTES) {
                funcValType = PUBVAL_6RAWBYTES;
            } else {
                funcValType = funcval[ofs++] & 0xff;
            }
            switch (funcValType) {
                case PUBVAL_LEGACY:
                    // fallback to legacy handling, just in case
                    break;
                case PUBVAL_1RAWBYTE:
                case PUBVAL_2RAWBYTES:
                case PUBVAL_3RAWBYTES:
                case PUBVAL_4RAWBYTES:
                case PUBVAL_5RAWBYTES:
                case PUBVAL_6RAWBYTES:
                    // 1..5 hex bytes
                    for (int i = 0; i < funcValType; i++) {
                        int c = funcval[ofs++] & 0xff;
                        int b = c >> 4;
                        buffer += (b > 9) ? b + 'a' - 10 : b + '0';
                        b = c & 0xf;
                        buffer += (b > 9) ? b + 'a' - 10 : b + '0';
                    }
                    return buffer;
                case PUBVAL_C_LONG:
                case PUBVAL_YOCTO_FLOAT_E3:
                    // 32bit integer in little endian format or Yoctopuce 10-3 format
                    int numVal = funcval[ofs++] & 0xff;
                    numVal += (int) (funcval[ofs++] & 0xff) << 8;
                    numVal += (int) (funcval[ofs++] & 0xff) << 16;
                    numVal += (int) (funcval[ofs++] & 0xff) << 24;
                    if (funcValType == PUBVAL_C_LONG) {
                        return String.format(Locale.US, "%d", numVal);
                    } else {
                        buffer = String.format(Locale.US, "%.3f", numVal / 1000.0);
                        int endp = buffer.length();
                        while (endp > 0 && buffer.charAt(endp - 1) == '0') {
                            --endp;
                        }
                        if (endp > 0 && buffer.charAt(endp - 1) == '.') {
                            --endp;
                            buffer = buffer.substring(0, endp);
                        }
                        return buffer;
                    }
                case PUBVAL_C_FLOAT:
                    // 32bit (short) float
                    float floatVal = ByteBuffer.wrap(funcval).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                    buffer = String.format(Locale.US, "%.6f", floatVal);
                    int endp = buffer.length();
                    while (endp > 0 && buffer.charAt(endp - 1) == '0') {
                        --endp;
                    }
                    if (endp > 0 && buffer.charAt(endp - 1) == '.') {
                        --endp;
                        buffer = buffer.substring(0, endp);
                    }
                    return buffer;
                default:
                    return "?";
            }
        }

        // Legacy handling: just pad with NUL up to 7 chars
        int len = 0;
        while (len < YAPI.YOCTO_PUBVAL_SIZE && len < funcvallen) {
            if (funcval[len + ofs] == 0)
                break;
            len++;
        }
        return new String(funcval, ofs, len);
    }


    void updateFromWpAndYp(ArrayList<WPEntry> whitePages, HashMap<String, ArrayList<YPEntry>> yellowPages) throws YAPI_Exception
    {

        // by default consider all known device as unplugged
        ArrayList<YDevice> toRemove = new ArrayList<>(_devices.values());

        for (WPEntry wp : whitePages) {
            String serial = wp.getSerialNumber();
            if (_devices.containsKey(serial)) {
                // already there
                YDevice currdev = _devices.get(serial);
                if (!currdev.getLogicalName().equals(wp.getLogicalName())) {
                    // Reindex device from its own data
                    currdev.refresh();
                    _yctx._pushPlugEvent(YAPIContext.PlugEvent.Event.CHANGE, serial);
                } else if (currdev.getBeacon() > 0 != wp.getBeacon() > 0) {
                    currdev.refresh();
                }
                toRemove.remove(currdev);
            } else {
                YDevice dev = new YDevice(this, wp, yellowPages);
                _yctx._yHash.reindexDevice(dev);
                _devices.put(serial, dev);
                _yctx._pushPlugEvent(YAPIContext.PlugEvent.Event.PLUG, serial);
                _yctx._Log("HUB: device " + serial + " has been plugged\n");
            }
        }

        for (YDevice dev : toRemove) {
            String serial = dev.getSerialNumber();
            _yctx._pushPlugEvent(YAPIContext.PlugEvent.Event.UNPLUG, serial);
            _yctx._Log("HUB: device " + serial + " has been unplugged\n");
            _devices.remove(serial);
        }

        if (_hubSerialNumber == null) {
            for (WPEntry wp : whitePages) {
                if (wp.getNetworkUrl().equals("")) {
                    _hubSerialNumber = wp.getSerialNumber();
                }
            }
        }
        _yctx._yHash.reindexYellowPages(yellowPages);

    }

    String getSerialNumber()
    {
        return _hubSerialNumber;
    }

    public String get_urlOf(String serialNumber)
    {
        for (YDevice dev : _devices.values()) {
            String devSerialNumber = dev.getSerialNumber();
            if (devSerialNumber.equals(serialNumber)) {
                return _http_params.getUrl(true, false) + dev.getNetworkUrl();
            }
        }
        return _http_params.getUrl(true, false);
    }

    public ArrayList<String> get_subDeviceOf(String serialNumber)
    {
        ArrayList<String> res = new ArrayList<>();
        for (YDevice dev : _devices.values()) {
            String devSerialNumber = dev.getSerialNumber();
            if (devSerialNumber.equals(serialNumber)) {
                if (!dev.getNetworkUrl().equals("")) {
                    //
                    res.clear();
                    return res;
                }
            }
            res.add(devSerialNumber);
        }
        return res;
    }

    void handleValueNotification(String serial, String funcid, String value)
    {
        String hwid = serial + "." + funcid;

        _yctx._yHash.setFunctionValue(hwid, value);
        YFunction conn_fn = _yctx._GetValueCallback(hwid);
        if (conn_fn != null) {
            _yctx._PushDataEvent(new YAPIContext.DataEvent(conn_fn, value));
        }

    }

    //called from Jni
    protected void handleTimedNotification(String serial, String funcid, double deviceTime, byte[] report)
    {
        ArrayList<Integer> arrayList = new ArrayList<>(report.length);
        for (byte b : report) {
            int i = b & 0xff;
            arrayList.add(i);
        }
        handleTimedNotification(serial, funcid, deviceTime, arrayList);
    }


    void handleTimedNotification(String serial, String funcid, double deviceTime, ArrayList<Integer> report)
    {
        String hwid = serial + "." + funcid;
        YFunction func = _yctx._GetTimedReportCallback(hwid);
        if (func != null) {
            _yctx._PushDataEvent(new YAPIContext.DataEvent(func, deviceTime, report));
        }
    }

    abstract void updateDeviceList(boolean forceupdate) throws YAPI_Exception, InterruptedException;

    public abstract ArrayList<String> getBootloaders() throws YAPI_Exception, InterruptedException;

    abstract int ping(int mstimeout) throws YAPI_Exception;

    public static String getAPIVersion()
    {
        return "";
    }

    interface UpdateProgress
    {
        void firmware_progress(int percent, String message);
    }

    abstract ArrayList<String> firmwareUpdate(String serial, YFirmwareFile firmware, byte[] settings, UpdateProgress progress) throws YAPI_Exception, InterruptedException;

    interface RequestAsyncResult
    {
        @SuppressWarnings("UnusedParameters")
        void RequestAsyncDone(Object context, byte[] result, int error, String errmsg);
    }

    interface RequestProgress
    {
        void requestProgressUpdate(Object context, int acked, int total);
    }

    abstract void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception, InterruptedException;

    abstract byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestProgress progress, Object context) throws YAPI_Exception, InterruptedException;


    static class HTTPParams
    {

        private final String _host;
        private final int _port;
        private final String _user;
        private final String _pass;
        private final String _proto;

        public HTTPParams(String url)
        {
            int pos = 0;
            if (url.startsWith("ws://")) {
                pos = 5;
                _proto = "ws";
            } else if (url.startsWith("usb://")) {
                pos = 6;
                _proto = "usb";
            } else {
                _proto = "http";
                if (url.startsWith("http://")) {
                    pos = 7;
                }
            }
            int end_auth = url.indexOf('@', pos);
            int end_user = url.indexOf(':', pos);
            if (end_auth >= 0 && end_user >= 0 && end_user < end_auth) {
                _user = url.substring(pos, end_user);
                _pass = url.substring(end_user + 1, end_auth);
                pos = end_auth + 1;
            } else {
                _user = "";
                _pass = "";
            }
            if (url.length() > pos && url.charAt(pos) == '@') {
                pos++;
            }
            int end_url = url.indexOf('/', pos);
            if (end_url < 0) {
                end_url = url.length();
            }
            int portpos = url.indexOf(':', pos);
            if (portpos > 0) {
                if (portpos + 1 < end_url) {
                    _host = url.substring(pos, portpos);
                    _port = Integer.parseInt(url.substring(portpos + 1, end_url));
                } else {
                    _host = url.substring(pos, portpos);
                    _port = 4444;
                }
            } else {
                _host = url.substring(pos, end_url);
                _port = 4444;
            }
        }

        String getHost()
        {
            return _host;
        }

        String getPass()
        {
            return _pass;
        }

        int getPort()
        {
            return _port;
        }

        String getUser()
        {
            return _user;
        }

        String getUrl()
        {
            return getUrl(false, true);
        }


        String getUrl(boolean withProto, boolean withUserPass)
        {
            StringBuilder url = new StringBuilder();
            if (withProto) {
                url.append(_proto).append("://");
            }
            if (withUserPass && !_user.equals("")) {
                url.append(_user);
                if (!_pass.equals("")) {
                    url.append(":");
                    url.append(_pass);
                }
                url.append("@");
            }
            url.append(_host);
            url.append(":");
            url.append(_port);
            return url.toString();
        }

        public boolean isWebSocket()
        {
            return _proto.equals("ws");
        }

        public boolean hasAuthParam()
        {
            return !_user.equals("");
        }
    }
}
