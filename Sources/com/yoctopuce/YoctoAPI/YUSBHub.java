package com.yoctopuce.YoctoAPI;

public class YUSBHub extends YGenericHub
{

    static void SetContextType(Object ctx) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct acces is supported only on Android Platform");
    }

    static void CheckUSBAcces() throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct acces is supported only on Android Platform");
    }

    /*
     * Constuctor
     */
    public YUSBHub(int idx) throws YAPI_Exception
    {
        super(idx);
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct acces is supported only on Android Platform");
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
    void updateDeviceList(boolean forceupdate) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct acces is supported only on Android Platform");
    }

    @Override
    public byte[] devRequest(YDevice device, String req_first_line, byte[] req_head_and_body, Boolean async) throws YAPI_Exception
    {
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct acces is supported only on Android Platform");
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

    @Override
    public void release()
    {
    }

}
