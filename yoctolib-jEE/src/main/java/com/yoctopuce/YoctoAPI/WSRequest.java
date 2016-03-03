package com.yoctopuce.YoctoAPI;

import java.nio.ByteBuffer;
import java.util.LinkedList;

class WSRequest
{
    private final int _channel;
    private final byte[] _requestData;
    private final LinkedList<WSStream> _responseStream;
    private final boolean _async;
    private final byte _asyncId;
    private volatile State _state;
    private int _errorCode = YAPI.SUCCESS;
    private String _errorMsg = null;
    private Exception _errorEx = null;
    private long _processStart;

    public void checkError() throws YAPI_Exception
    {
        if (_errorCode != YAPI.SUCCESS) {
            throw new YAPI_Exception(_errorCode, _errorMsg, _errorEx);
        }
    }

    public synchronized void setError(int ioError, String reasonPhrase, Exception ex)
    {
        _errorCode = ioError;
        _errorMsg = reasonPhrase;
        _errorEx = ex;
        _state = State.ERROR;
        logProcess("error:" + reasonPhrase);
        this.notifyAll();
    }

    public void logProcess(String msg)
    {
        //System.out.println(String.format("%s:%s in %d ms",this, msg, System.currentTimeMillis() - _processStart));
    }


    public State waitProcessingEnd(int mstimeout) throws InterruptedException
    {
        long expiration_timeout = System.currentTimeMillis() + mstimeout;
        synchronized (this) {
            while (!_state.equals(State.ERROR) && !_state.equals(State.CLOSED) && expiration_timeout > System.currentTimeMillis()) {
                this.wait(expiration_timeout - System.currentTimeMillis());
            }
            return _state;
        }
    }


    public int getErrorCode()
    {
        return _errorCode;
    }

    public boolean isAsync()
    {
        return _async;
    }


    public byte getAsyncId()
    {
        return _asyncId;
    }


    enum State
    {
        OPEN, CLOSED_BY_HUB, CLOSED_BY_API, CLOSED, ERROR
    }

    public WSRequest(int tcpchanel, byte asyncid, byte[] full_request)
    {
        _async = true;
        _asyncId = asyncid;
        _state = State.OPEN;
        _channel = tcpchanel;
        _requestData = full_request;
        _responseStream = new LinkedList<>();
        _processStart = System.currentTimeMillis();
    }

    public WSRequest(int tcpchanel, byte[] full_request)
    {
        _async = false;
        _asyncId = 0;
        _state = State.OPEN;
        _channel = tcpchanel;
        _requestData = full_request;
        _responseStream = new LinkedList<>();
        _processStart = System.currentTimeMillis();
    }

    public ByteBuffer getRequestBytes()
    {
        return ByteBuffer.wrap(_requestData);
    }

    public int getChannel()
    {
        return _channel;
    }

    public synchronized void setState(State state)
    {
        _state = state;
        if (state.equals(State.CLOSED)) {
            logProcess("success");
        }
        this.notifyAll();

    }

    public synchronized State getState()
    {
        return _state;
    }

    public void addStream(WSStream stream)
    {
        _responseStream.add(stream);
    }

    public byte[] getResponseBytes()
    {
        int size = 0;
        for (WSStream s : _responseStream) {
            size += s.getContentLen();
        }

        byte[] full_result = new byte[size];
        ByteBuffer bb = ByteBuffer.wrap(full_result);
        for (WSStream s : _responseStream) {
            s.getContent(bb);
        }
        return full_result;
    }
}
