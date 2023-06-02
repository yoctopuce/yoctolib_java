package com.yoctopuce.YoctoAPI;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

@ClientEndpoint
public class WSHandlerJEE implements WSHandlerInterface, MessageHandler
{
    private final boolean _isHttpCallback;
    private final WSHandlerInterface.WSHandlerResponseInterface  _nhandler;
    private Session _session;

    WSHandlerJEE(WSHandlerResponseInterface nhandler, Object session)
    {
        _isHttpCallback = session != null;
        _nhandler = nhandler;
        _session= (Session) session;
        if (_isHttpCallback) {
            // server mode
            MessageHandler.Whole<ByteBuffer> messageHandler = new MessageHandler.Whole<ByteBuffer>()
            {
                @Override
                public void onMessage(ByteBuffer byteBuffer)
                {

                    try {
                        _nhandler.parseBinaryMessage(byteBuffer);
                    } catch (YAPI_Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            _session.addMessageHandler(messageHandler);
        }

    }

    @Override
    public void connect(YHTTPHub hub, boolean first_notification_connection, int mstimeout, int notifAbsPos) throws YAPI_Exception
    {
        if (!_isHttpCallback) {
            // client mode
            WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
            String url = hub._runtime_http_params.getUrl(true, false,false) + "/not.byn";
            URI uri;
            try {
                uri = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }

            try {
                _session = webSocketContainer.connectToServer(this, uri);
            } catch (DeploymentException | IOException e) {
                e.printStackTrace();
                throw new YAPI_Exception(YAPI.IO_ERROR, e.getLocalizedMessage());
            }
        }

    }


    @Override
    public void close()
    {
        try {
            _session.close();
        } catch (IOException | IllegalStateException ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    public boolean isOpen()
    {
        return _session.isOpen();
    }


    @OnOpen
    public void onOpen(Session session)
    {
        _session = session;
    }


    @OnMessage
    public void onMessage(ByteBuffer raw_data, Session session) throws YAPI_Exception
    {

        if (_session != session) {
            return;
        }
        _nhandler.parseBinaryMessage(raw_data);
    }


    @Override
    public void sendBinary(ByteBuffer partialByte, boolean isLast) throws YAPI_Exception
    {
        try {
            RemoteEndpoint.Basic remote = _session.getBasicRemote();
            remote.sendBinary(partialByte, isLast);
        } catch (IOException e) {
            e.printStackTrace();
            throw new YAPI_Exception(YAPI.IO_ERROR, e.getLocalizedMessage());
        }
    }

    @OnClose
    public void onClose(@SuppressWarnings("UnusedParameters") Session session, CloseReason closeReason)
    {
        _nhandler.errorOnSession(YAPI.IO_ERROR, closeReason.getReasonPhrase());
    }

    public String getThreadLabel()
    {
        String label = "WS Notification handler session ";
        if (_session != null) {
            label += "(session " + _session.getId() + ")";
        }
        return label;
    }

    @Override
    public boolean isCallback()
    {
        return _isHttpCallback;
    }


}
