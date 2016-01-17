package com.yoctopuce.YoctoAPI;

/**
 * Created by seb on 12.01.2016.
 */
public class WSNotificationHandler extends NotificationHandler
{
    public WSNotificationHandler(YHTTPHub hub, Object callbackSession)
    {
        super(hub);
    }

    @Override
    byte[] hubRequestSync(String req_first_line, byte[] req_head_and_body, int mstimeout) throws YAPI_Exception, InterruptedException
    {
        return new byte[0];
    }

    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, int mstimeout) throws YAPI_Exception, InterruptedException
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
    public void run()
    {

    }
}
