/*********************************************************************
 * $Id: YUSBHub.java 64129 2025-01-13 14:05:07Z seb $
 *
 * YUSBHub stub (native usb is only supported in Android)
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class YUSBHub extends YGenericHub
{

    private Thread _thread;
    private boolean _processNotifications = false;

    static void SetContextType(Object ctx) throws YAPI_Exception
    {
        YJniWrapper.Check();
    }

    static void CheckUSBAcces() throws YAPI_Exception
    {
        YJniWrapper.Check();
    }

    public static String getAPIVersion()
    {
        try {
            YJniWrapper.Check();
            return " (" + YJniWrapper.getAPIVersion() + ")";
        } catch (YAPI_Exception e) {
            return "";
        }
    }

    public static String getYAPISharedLibraryPath()
    {
        try {
            YJniWrapper.Check();
            return YJniWrapper.getYAPISharedLibraryPath();
        } catch (YAPI_Exception e) {
            return "";
        }
    }


    public static String addUdevRule(boolean force)
    {
        try {
            YJniWrapper.Check();
            String res = YJniWrapper.addUdevRule(force ? 1 : 0);
            return res.length() > 0 ? "error: " + res : res;
        } catch (YAPI_Exception e) {
            return "error" + e.getLocalizedMessage();
        }
    }

    @Override
    public void set_networkTimeout(int networkMsTimeout)
    {

    }

    @Override
    public int get_networkTimeout()
    {
        return 2000;
    }

    /*
     * Config change callback are not supported on purpose, because it does
     * not make a lot of sense since only nobody can remotely change de
     * configuration
     */


    @Override
    boolean isCallbackMode()
    {
        return false;
    }

    @Override
    boolean isReadOnly()
    {
        return false;
    }

    @Override
    public boolean isOnline()
    {
        return this._processNotifications;
    }

    @Override
    public String getConnectionUrl()
    {
        return _URL_params.getUrl(true, false, true);
    }

    @Override
    String getSerialNumber()
    {
        return "";
    }

    @Override
    public String get_urlOf(String serialNumber)
    {
        return "usb";
    }

    @Override
    public ArrayList<String> get_subDeviceOf(String serialNumber)
    {
        return new ArrayList<>();
    }

    YUSBHub(YAPIContext yctx, boolean requestPermission, int pktAckDelay) throws YAPI_Exception
    {
        super(yctx, new HTTPParams("usb"), true);
        YJniWrapper.reserveUSBAccess();
    }

    @Override
    public void startNotifications()
    {
        YJniWrapper.startNotifications(this);
        _processNotifications = true;
        _thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (_processNotifications) {
                    YJniWrapper.usbProcess(YUSBHub.this);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        _thread.start();

    }

    @Override
    public void stopNotifications()
    {
        _processNotifications = false;
        _thread.interrupt();
        YJniWrapper.stopNotifications();
        removeAllDevices();
    }

    @Override
    void release()
    {
        YJniWrapper.releaseUSBAccess();
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
        try {
            ArrayList<YPEntry> functions = new ArrayList<YPEntry>();
            ArrayList<WPEntry> whitePages = new ArrayList<WPEntry>();
            HashMap<String, ArrayList<YPEntry>> yellowPages = new HashMap<String, ArrayList<YPEntry>>();
            //whitePages.a
            YJniWrapper.updateDeviceList(whitePages, functions);
            for (YPEntry yp : functions) {
                String classname = yp.getClassname();
                if (!yellowPages.containsKey(classname))
                    yellowPages.put(classname, new ArrayList<YPEntry>());
                yellowPages.get(classname).add(yp);
            }
            // Reindex all devices from white pages
            for (int i = 0; i < whitePages.size(); i++) {
                _serialByYdx.put(i, whitePages.get(i).getSerialNumber());
            }
            updateFromWpAndYp(whitePages, yellowPages);
        } catch (YAPI_Exception ex) {
            this._lastErrorMessage = ex.getLocalizedMessage();
            this._lastErrorType = ex.errorType;
            throw ex;
        }
        // reset device list cache timeout for this hub
        this._lastErrorType = YAPI.SUCCESS;
        this._lastErrorMessage = "";
        now = YAPI.GetTickCount();
        _devListExpires = now + 500;
    }

    @Override
    public ArrayList<String> getBootloaders() throws YAPI_Exception
    {
        return YJniWrapper.getBootloaders();
    }

    @Override
    public int ping(int mstimeout) throws YAPI_Exception
    {
        return 0;
    }

    @Override
    java.util.ArrayList<String> firmwareUpdate(String serial, YFirmwareFile firmware, byte[] settings, UpdateProgress progress) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "Firmware update on USB with JAVA is not yet available");
    }

    @Override
    void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception
    {
        byte[] currentRequest = prepareRequest(req_first_line, req_head_and_body);
        YJniWrapper.devRequestAsync(device.getSerialNumber(), currentRequest, asyncResult, asyncContext);
    }

    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestProgress progress, Object context) throws YAPI_Exception
    {
        if (req_first_line.contains("/@YCB")) {
            throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "Preloading of URL is only supported for HTTP callback.");
        }
        byte[] currentRequest = prepareRequest(req_first_line, req_head_and_body);
        byte[] result = YJniWrapper.devRequestSync(device.getSerialNumber(), currentRequest);
        int hpos = YAPIContext._find_in_bytes(result, "\r\n\r\n".getBytes(_yctx._deviceCharset));
        if (hpos >= 0) {
            return Arrays.copyOfRange(result, hpos + 4, result.length);
        }
        return result;
    }


    private byte[] prepareRequest(String firstLine, byte[] rest_of_request)
    {
        byte[] currentRequest;
        if (rest_of_request == null) {
            currentRequest = (firstLine + "\r\n\r\n").getBytes(_yctx._deviceCharset);
        } else {
            firstLine += "\r\n";
            int len = firstLine.length() + rest_of_request.length;
            currentRequest = new byte[len];
            System.arraycopy(firstLine.getBytes(_yctx._deviceCharset), 0, currentRequest, 0, len);
            System.arraycopy(rest_of_request, 0, currentRequest, firstLine.length(), rest_of_request.length);
        }
        return currentRequest;
    }


    @Override
    public String getRootUrl()
    {
        return "usb";
    }

    @Override
    boolean isSameHub(String url, Object request, Object response, Object session)
    {
        return url.equals("usb");
    }

}
