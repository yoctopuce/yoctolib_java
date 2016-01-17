package com.yoctopuce.examples;

import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/wscallback")
public class WebSocketEndpoint
{

    @OnOpen
    public void onOpen(final Session session)
    {
        // on each connection start a new thread that will execute the code
        Thread thread = new Thread(new WebSockRSSReader(session), " Thread " +  session.getId());
        thread.start();
    }


    @OnError
    public void onError(Session session, Throwable throwable)
    {

        System.out.println(session.getId() + " error");
        throwable.printStackTrace();
    }


}
