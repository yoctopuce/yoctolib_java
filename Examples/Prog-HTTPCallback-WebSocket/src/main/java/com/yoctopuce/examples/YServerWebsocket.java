package com.yoctopuce.examples;

import com.yoctopuce.YoctoAPI.YAPIContext;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/wscallback")
public class YServerWebsocket
{

    @OnOpen
    public void onOpen(Session session)
    {
        // log onOpen for debug purpose
        System.out.println(session.getId() + " has open a connection");
        // since all connection use the same process create a private context
        final YAPIContext yctx = new YAPIContext();

        // register the YoctoHub/VirtualHub that start the connection
        try {
            yctx.PreregisterHubWebSocketCallback(session);
        } catch (YAPI_Exception e) {
            e.printStackTrace();
            return;
        }
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try {

                    // list all devices connected on this hub (only for debug propose)
                    System.out.println("Device list:");
                    yctx.UpdateDeviceList();
                    YModule module = YModule.FirstModuleInContext(yctx);
                    while (module != null) {
                        System.out.println("   " + module.get_serialNumber() + " (" + module.get_productName() + ")");
                        module = module.nextModule();
                    }
                } catch (YAPI_Exception ex) {
                    System.out.println(" error (" + ex.getLocalizedMessage() + ")");
                    ex.printStackTrace();
                }
                // no not forget to FreeAPI to ensure that all pending operation
                // are finished and freed
                yctx.FreeAPI();
            }
        });
        thread.start();
    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason)
    {
        // log onClose for debug purpose
        System.out.println(session.getId() + " has close a connection");
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // log onError for debug purpose
        System.out.println(session.getId() + " error : " + throwable.getMessage());
        throwable.printStackTrace();
    }

}
