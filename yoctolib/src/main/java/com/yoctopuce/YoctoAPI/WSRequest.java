package com.yoctopuce.YoctoAPI;

import java.nio.ByteBuffer;
import java.util.LinkedList;

class WSRequest
{
    private final int _channel;
    private final ByteBuffer _requestData;
    private final LinkedList<WSStream> _responseStream;
    private final boolean _async;
    private final byte _asyncId;
    private final Object _progressCtx;
    private final YGenericHub.RequestProgress _progressCb;
    private volatile State _state;
    private int _errorCode = YAPI.SUCCESS;
    private String _errorMsg = null;
    private Exception _errorEx = null;
    private final long _expiration;
    private final long _tmOpen;
    private long _tmProcess;
    private long _tmIn;
    private long _tmOut;
    private long _tmClose;
    private final String _dbgLabel;


    @Override
    public String toString()
    {
        return "WSRequest{" +
                "_async=" + _async +
                ", _asyncId=" + _asyncId +
                ", _dbgLabel='" + _dbgLabel + '\'' +
                '}';
    }

    void checkError() throws YAPI_Exception
    {
        if (_errorCode != YAPI.SUCCESS) {
            throw new YAPI_Exception(_errorCode, _errorMsg);
        }
    }

    synchronized void setError(int ioError, String reasonPhrase)
    {
        _errorCode = ioError;
        _errorMsg = reasonPhrase;
        _state = State.FAKE_REQUEST;
        //logProcess("error:" + reasonPhrase);
        this.notifyAll();
    }

    void log(String msg)
    {
        long process = _tmProcess - _tmOpen;
        long write = _tmOut - _tmProcess;
        long read = _tmIn - _tmOut;
        long end = _tmClose - _tmOpen;
        System.out.println(String.format("%s:%s:%s in start:%d + write:%d + read+%d =%d ms", this, msg,_state, process, write, read, end));
    }


    State waitProcessingEnd(long expiration_timeout) throws InterruptedException
    {
        synchronized (this) {
            while (!_state.equals(State.FAKE_REQUEST) && !_state.equals(State.CLOSED) && expiration_timeout > System.currentTimeMillis()) {
                this.wait(expiration_timeout - System.currentTimeMillis());
            }
            return _state;
        }
    }


    boolean isAsync()
    {
        return _async;
    }


    byte getAsyncId()
    {
        return _asyncId;
    }

    void reportProgress(int ackBytes)
    {
        if (_progressCb != null && _requestData.limit() > 0) {
            _progressCb.requestProgressUpdate(_progressCb, ackBytes, _requestData.limit());
        }
    }


    long getExpiration()
    {
        return _expiration;
    }


    enum State
    {
        OPEN, CLOSED_BY_HUB, CLOSED_BY_API, CLOSED, FAKE_REQUEST
    }

    WSRequest(int ioError, String reasonPhrase)
    {

        _async = false;
        _asyncId = 0;
        _channel = 0;
        _requestData = null;
        _responseStream = new LinkedList<>();
        _tmOpen = System.currentTimeMillis();
        _progressCb = null;
        _progressCtx = null;
        _errorCode = ioError;
        _errorMsg = reasonPhrase;
        _state = State.FAKE_REQUEST;
        _dbgLabel = "error:" + reasonPhrase;
        _expiration = 0;
    }


    WSRequest(int tcpchanel, byte asyncid, byte[] full_request, long expiration)
    {
        _async = true;
        _asyncId = asyncid;
        _state = State.OPEN;
        _channel = tcpchanel;
        _requestData = ByteBuffer.wrap(full_request);
        _responseStream = new LinkedList<>();
        _tmOpen = System.currentTimeMillis();
        _progressCb = null;
        _progressCtx = null;
        _dbgLabel = "";//getReqDbgString(full_request);
        _expiration = expiration;
    }

    WSRequest(int tcpchanel, byte[] full_request, long expiration, YGenericHub.RequestProgress progress, Object context)
    {
        _async = false;
        _asyncId = 0;
        _state = State.OPEN;
        _channel = tcpchanel;
        _requestData = ByteBuffer.wrap(full_request);
        _responseStream = new LinkedList<>();
        _tmOpen = System.currentTimeMillis();
        _progressCb = progress;
        _progressCtx = context;
        _dbgLabel = "";//getReqDbgString(full_request);
        _expiration = expiration;
    }


    private String getReqDbgString(byte[] full_request)
    {
        String dbg_req = new String(full_request);
        int pos = dbg_req.indexOf("\r");
        if (pos > 0) {
            dbg_req = dbg_req.substring(0, pos);
        }
        return dbg_req;
    }


    ByteBuffer getRequestBytes()
    {
        return _requestData;
    }

    int getChannel()
    {
        return _channel;
    }

    public synchronized void setState(State state)
    {
        _state = state;
        if (state.equals(State.CLOSED)) {
            _tmClose = System.currentTimeMillis();
            //logProcess("success");
        }
        this.notifyAll();
    }

    public synchronized State getState()
    {
        return _state;
    }

    void addStream(WSStream stream)
    {
        _responseStream.add(stream);
        _tmIn = System.currentTimeMillis();
    }

    void reportDataSent()
    {
        _tmOut = System.currentTimeMillis();
    }

    void reportStartOfProcess()
    {
        _tmProcess = System.currentTimeMillis();
    }


    byte[] getResponseBytes()
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
