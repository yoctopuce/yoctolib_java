package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("unused")
abstract class NotificationHandler implements Runnable
{
    private final static char NOTIFY_NETPKT_NAME = '0';
    private final static char NOTIFY_NETPKT_CHILD = '2';
    private final static char NOTIFY_NETPKT_FUNCNAME = '4';
    private final static char NOTIFY_NETPKT_FUNCVAL = '5';
    private final static char NOTIFY_NETPKT_FUNCNAMEYDX = '8';
    private final static char NOTIFY_NETPKT_FLUSHV2YDX = 't';
    private final static char NOTIFY_NETPKT_FUNCV2YDX = 'u';
    private final static char NOTIFY_NETPKT_TIMEV2YDX = 'v';
    private final static char NOTIFY_NETPKT_DEVLOGYDX = 'w';
    private final static char NOTIFY_NETPKT_TIMEVALYDX = 'x';
    private final static char NOTIFY_NETPKT_FUNCVALYDX = 'y';
    private final static char NOTIFY_NETPKT_TIMEAVGYDX = 'z';
    private final static char NOTIFY_NETPKT_NOT_SYNC = '@';
    private final static char NOTIFY_NETPKT_LOG = '7';
    private static final int NOTIFY_NETPKT_STOP = 10;
    private static final int NET_HUB_NOT_CONNECTION_TIMEOUT = 6000;


    long _notifyPos = -1;
    int _notifRetryCount = 0;
    int _error_delay = 0;


    final YHTTPHub _hub;


    NotificationHandler(YHTTPHub hub)
    {
        _hub = hub;
    }

    // Network notification format: 7x7bit (mapped to 7 chars in range 32..159)
    //                              used to represent 1 flag (RAW6BYTES) + 6 bytes
    // INPUT:  [R765432][1076543][2107654][3210765][4321076][5432107][6543210]
    // OUTPUT: 7 bytes array (1 byte for the funcTypeV2 and 6 bytes of USB like data
    //                     funcTypeV2 + [R][-byte 0][-byte 1-][-byte 2-][-byte 3-][-byte 4-][-byte 5-]
    //
    // return null on error
    //
    private byte[] decodeNetFuncValV2(byte[] p)
    {
        int p_ofs = 0;
        int ch = p[p_ofs] & 0xff;
        int len = 0;
        byte[] funcVal = new byte[7];
        Arrays.fill(funcVal, (byte) 0);
        if (ch < 32 || ch > 32 + 127) {
            return null;
        }
        // get the 7 first bits
        ch -= 32;
        funcVal[0] = (byte) ((ch & 0x40) != 0 ? YGenericHub.NOTIFY_V2_6RAWBYTES : YGenericHub.NOTIFY_V2_TYPEDDATA);
        // clear flag
        ch &= 0x3f;
        while (len < YAPI.YOCTO_PUBVAL_SIZE) {
            p_ofs++;
            if (p_ofs >= p.length)
                break;
            int newCh = p[p_ofs] & 0xff;
            if (newCh == NOTIFY_NETPKT_STOP) {
                break;
            }
            if (newCh < 32 || newCh > 32 + 127) {
                return null;
            }
            newCh -= 32;
            ch = (ch << 7) + newCh;
            funcVal[len + 1] = (byte) (ch >> (5 - len));
            len++;
        }
        return funcVal;
    }

    void handleNetNotification(String notification_line)
    {
        String ev = notification_line.trim();

        if (ev.length() >= 3 && ev.charAt(0) >= NOTIFY_NETPKT_FLUSHV2YDX && ev.charAt(0) <= NOTIFY_NETPKT_TIMEAVGYDX) {
            // function value ydx (tiny notification)
            _hub._devListValidity = 10000;
            _notifRetryCount = 0;
            if (_notifyPos >= 0) {
                _notifyPos += ev.length() + 1;
            }
            int devydx = ev.charAt(1) - 65;// from 'A'
            int funydx = ev.charAt(2) - 48;// from '0'

            if ((funydx & 64) != 0) { // high bit of devydx is on second character
                funydx -= 64;
                devydx += 128;
            }
            String value = ev.substring(3);
            String serial = _hub._serialByYdx.get(devydx);
            String funcid;
            if (serial != null) {
                YDevice ydev = _hub._yctx._yHash.getDevice(serial);
                if (ydev != null) {
                    switch (ev.charAt(0)) {
                        case NOTIFY_NETPKT_FUNCVALYDX:
                            funcid = ydev.getYPEntry(funydx).getFuncId();
                            if (!funcid.equals("")) {
                                // function value ydx (tiny notification)
                                _hub.handleValueNotification(serial, funcid, value);
                            }
                            break;
                        case NOTIFY_NETPKT_DEVLOGYDX:
                            ydev.triggerLogPull();
                            break;
                        case NOTIFY_NETPKT_TIMEVALYDX:
                        case NOTIFY_NETPKT_TIMEAVGYDX:
                        case NOTIFY_NETPKT_TIMEV2YDX:
                            if (funydx == 0xf) {
                                Integer[] data = new Integer[5];
                                for (int i = 0; i < 5; i++) {
                                    String part = value.substring(i * 2, i * 2 + 2);
                                    data[i] = Integer.parseInt(part, 16);
                                }
                                ydev.setDeviceTime(data);
                            } else {
                                funcid = ydev.getYPEntry(funydx).getFuncId();
                                if (!funcid.equals("")) {
                                    // timed value report
                                    ArrayList<Integer> report = new ArrayList<>(1 + value.length() / 2);
                                    report.add((ev.charAt(0) == NOTIFY_NETPKT_TIMEVALYDX ? 0 :
                                            (ev.charAt(0) == NOTIFY_NETPKT_TIMEAVGYDX ? 1 : 2)));
                                    for (int pos = 0; pos < value.length(); pos += 2) {
                                        int intval = Integer.parseInt(value.substring(pos, pos + 2), 16);
                                        report.add(intval);
                                    }
                                    _hub.handleTimedNotification(serial, funcid, ydev.getDeviceTime(), report);
                                }
                            }
                            break;
                        case NOTIFY_NETPKT_FUNCV2YDX:
                            funcid = ydev.getYPEntry(funydx).getFuncId();
                            if (!funcid.equals("")) {
                                byte[] rawval = decodeNetFuncValV2(value.getBytes());
                                if (rawval != null) {
                                    String decodedval = YGenericHub.decodePubVal(rawval[0], rawval, 1, 6);
                                    // function value ydx (tiny notification)
                                    _hub.handleValueNotification(serial, funcid, decodedval);
                                }
                            }
                            break;
                        case NOTIFY_NETPKT_FLUSHV2YDX:
                            // To be implemented later
                        default:
                            break;
                    }
                }
            }
        } else if (ev.length() >= 5 && ev.startsWith("YN01")) {
            _hub._devListValidity = 10000;
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
                        _hub._devListExpires = 0;
                        break;
                    case NOTIFY_NETPKT_FUNCVAL: // function value (long notification)
                        String[] parts = ev.substring(5).split(",");
                        _hub.handleValueNotification(parts[0], parts[1], parts[2]);
                        break;
                }
            }
        } else {
            // oops, bad notification ? be safe until a good one comes
            _hub._devListValidity = 500;
            _notifyPos = -1;
        }
    }


    /**
     * @param req_first_line    first line of request without space, HTTP1.1 or \r\n
     * @param req_head_and_body http headers with double \r\n followed by potential body
     * @param mstimeout         number of milisecond allowed to the request to finish
     * @return return the raw response without the http header
     * @throws YAPI_Exception
     * @throws InterruptedException
     */
    abstract byte[] hubRequestSync(String req_first_line, byte[] req_head_and_body, int mstimeout) throws YAPI_Exception, InterruptedException;

    /**
     * @param req_first_line    first line of request without space, HTTP1.1 or \r\n
     * @param req_head_and_body http headers with double \r\n followed by potential body
     * @param mstimeout         number of milisecond allowed to the request to finish
     * @return return the raw response without the http header
     * @throws YAPI_Exception
     */
    @SuppressWarnings("UnusedParameters")
    abstract byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, int mstimeout, YGenericHub.RequestProgress progress, Object context) throws YAPI_Exception, InterruptedException;

    /**
     * @param req_first_line    first line of request without space, HTTP1.1 or \r\n
     * @param req_head_and_body http headers with double \r\n followed by potential body
     * @param asyncResult       the callback to call when the request is done
     * @param asyncContext      a pointer to a context to pass for the asyncResutl
     * @throws YAPI_Exception
     */
    abstract void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, YGenericHub.RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception, InterruptedException;

    /**
     * Wait until all pending async request have finished
     *
     * @param timeout the nubmer of milisecond before going into timeout
     * @return true if some request are still pendin
     * @throws InterruptedException
     */
    abstract boolean waitAndFreeAsyncTasks(long timeout) throws InterruptedException;

    public abstract boolean isConnected();

    public abstract boolean hasRwAccess();

}
