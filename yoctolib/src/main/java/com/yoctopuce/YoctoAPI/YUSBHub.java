/*********************************************************************
 *
 * $Id: YUSBHub.java 20056 2015-04-15 14:56:45Z seb $
 *
 * YUSBHub stub (native usb is only supported in Android)
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

public class YUSBHub extends YGenericHub
{

    static void SetContextType(Object ctx) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is supported only on Android Platform");
    }

    static void CheckUSBAcces() throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is supported only on Android Platform");
    }

    public YUSBHub(int idx) throws YAPI_Exception
    {
        super(idx,true);
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is supported only on Android Platform");
    }

    @Override
    public void startNotifications()
    {
    }

    @Override
    public void stopNotifications()
    {
    }

	@Override
    void release()
    {
    }
	
    @Override
    void updateDeviceList(boolean forceupdate) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is supported only on Android Platform");
    }

    @Override
    public ArrayList<String> getBootloaders() throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is supported only on Android Platform");
    }

    @Override
    public int ping(int mstimeout) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is supported only on Android Platform");
    }

    @Override
    java.util.ArrayList<String> firmwareUpdate(String serial, YFirmwareFile firmware, byte[] settings, UpdateProgress progress) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "Firmware update over USB is not yet supported");
    }

    @Override
    void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is supported only on Android Platform");
    }

    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is supported only on Android Platform");
    }
    @Override
    public String getRootUrl()
    {
        return "usb";
    }

    @Override
    public boolean isSameRootUrl(String url)
    {
        return url.equals("usb");
    }

  

    public static boolean RegisterLocalhost()
    {
        return true;
    }

    
}
