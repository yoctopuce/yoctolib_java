package com.yoctopuce.YoctoAPI;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Created by seb on 05.02.2016.
 */
class WSStream
{
    public static int MAX_DATA_LEN = 124;
    private final int _channel;
    private final int _type;
    private final int _contentLen;
    private final byte[] _data;


    public WSStream(int type, int channel, int size, ByteBuffer requestBB)
    {
        _channel = channel;
        _type = type;
        _contentLen = size;
        _data = new byte[_contentLen + 1];
        _data[0] = (byte) (((_type << 3) + channel) & 0xff);
        if (size > 0) {
            requestBB.get(_data, 1, size);
        }
    }

    public WSStream(int type, int channel, int size, ByteBuffer requestBB, byte lastByte)
    {
        _channel = channel;
        _type = type;
        _contentLen = size + 1;
        _data = new byte[_contentLen + 1];
        _data[0] = (byte) (((_type << 3) + channel) & 0xff);
        if (size > 0) {
            requestBB.get(_data, 1, size);
        }
        _data[size + 1] = lastByte;
    }


    public byte[] getData()
    {
        return _data;
    }


    public int getType()
    {
        return _type;
    }


    @Override
    public String toString()
    {
        String content = new String(_data, 1, _contentLen, StandardCharsets.ISO_8859_1);
        String type = "unk";
        switch (_type) {
            case YGenericHub.YSTREAM_EMPTY:
                type = "EMPTY";
                break;
            case YGenericHub.YSTREAM_TCP:
                type = "TCP";
                break;
            case YGenericHub.YSTREAM_TCP_CLOSE:
                type = "TCP_CLOSE";
                break;
            case YGenericHub.YSTREAM_NOTICE:
                type = "NOTICE";
                break;
            case YGenericHub.YSTREAM_REPORT:
                type = "REPORT";
                break;
            case YGenericHub.YSTREAM_META:
                type = "META";
                break;
            case YGenericHub.YSTREAM_REPORT_V2:
                type = "REPORT_V2";
                break;
            case YGenericHub.YSTREAM_NOTICE_V2:
                type = "NOTICE_V2";
                break;
            case YGenericHub.YSTREAM_TCP_NOTIF:
                type = "TCP_NOTIF";
                break;
            case YGenericHub.YSTREAM_TCP_ASYNCCLOSE:
                type = "TCP_ASYNCCLOSE";
                break;
        }
        return String.format("[c=%d t=%s l=%d]:[%s]", _channel, type, _contentLen, content);
    }

    public int getContentLen()
    {
        return _contentLen;
    }

    public void getContent(ByteBuffer bb)
    {
        bb.put(_data, 1, _contentLen);
    }

    ByteBuffer getContent()
    {
        return ByteBuffer.wrap(_data);
    }
}
