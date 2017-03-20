package com.yoctopuce.YoctoAPI;

class WSNotificationHandler extends NotificationHandler
{
    @SuppressWarnings("UnusedParameters")
    public WSNotificationHandler(YHTTPHub hub, Object callbackSession) throws YAPI_Exception
    {
        super(hub);
        throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "This yoctolib does not support WebSocket. Use yoctolib-jEE");
    }

    @Override
    byte[] hubRequestSync(String req_first_line, byte[] req_head_and_body, int mstimeout) throws YAPI_Exception, InterruptedException
    {
        return new byte[0];
    }

    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, int mstimeout, YGenericHub.RequestProgress progress, Object context) throws
            YAPI_Exception, InterruptedException
    {
        return new byte[0];
    }

    @Override
    void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, YGenericHub.RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception, InterruptedException
    {

    }

    @Override
    boolean waitAndFreeAsyncTasks(long timeout) throws InterruptedException
    {
        return false;
    }

    @Override
    public boolean isConnected()
    {
        return false;
    }

    @Override
    public boolean hasRwAccess()
    {
        return false;
    }

    @Override
    public void run()
    {

    }
}
