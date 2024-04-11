package com.yoctopuce.YoctoAPI;

import java.nio.ByteBuffer;

public interface WSHandlerInterface
{
    void connect(YHTTPHub hub, boolean first_notification_connection, int mstimeout, int notifAbsPos) throws YAPI_Exception;

    void close();

    boolean isOpen();

    void sendBinary(ByteBuffer partialByte, boolean isLast) throws YAPI_Exception;

    String getThreadLabel();

    boolean isCallback();

    interface WSHandlerResponseInterface
    {
        void WSLOG(String format);

        void parseBinaryMessage(ByteBuffer data) throws YAPI_Exception;

        void errorOnSession(int ioError, String reasonPhrase);
    }

}
