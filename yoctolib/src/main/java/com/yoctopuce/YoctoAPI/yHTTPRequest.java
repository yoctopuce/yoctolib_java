/*********************************************************************
 *
 * $Id: yHTTPRequest.java 17678 2014-09-16 16:31:26Z seb $
 *
 * internal yHTTPRequest object
 *
 * - - - - - - - - - License information: - - - - - - - - -
 *
 *  Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 *  Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 *  non-exclusive license to use, modify, copy and integrate this
 *  file into your software for the sole purpose of interfacing 
 *  with Yoctopuce products. 
 *
 *  You may reproduce and distribute copies of this file in 
 *  source or object form, as long as the sole purpose of this
 *  code is to interface with Yoctopuce products. You must retain 
 *  this notice in the distributed source file.
 *
 *  You should refer to Yoctopuce General Terms and Conditions
 *  for additional information regarding your rights and 
 *  obligations.
 *
 *  THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *  WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *  WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS 
 *  FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *  EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, 
 *  COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR 
 *  SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT 
 *  LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *  CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *  BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *  WARRANTY, OR OTHERWISE.
 *
 *********************************************************************/

package com.yoctopuce.YoctoAPI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

class yHTTPRequest implements Runnable {
    private Object _context;
    private YGenericHub.RequestAsyncResult _resultCallback;

    private enum State {
        AVAIL, IN_REQUEST, STOPPED,
    }

    public static final int MAX_REQUEST_MS = 5000;

    private final YHTTPHub _hub;
    private Socket _socket = null;
    private boolean _reuse_socket = false;
    private BufferedOutputStream _out = null;
    private BufferedInputStream _in = null;
    private State _state = State.AVAIL;
    private boolean _eof;
    private String _firstLine;
    private byte[] _rest_of_request;
    private final String _dbglabel;
    private final StringBuilder _header = new StringBuilder(1024);
    private Boolean _header_found;
    private final ByteArrayOutputStream _result = new ByteArrayOutputStream(1024);
    private long _startRequestTime;
    private long _lastReceiveTime;
    private volatile int _noTrafficTimeout = 0;
    private volatile int _soTimeout = MAX_REQUEST_MS;

    yHTTPRequest(YHTTPHub hub,String dbglabel)
    {
        _hub = hub;
        _dbglabel = dbglabel;
    }

    public void setNoTrafficTimeout(int noTrafficTimeout) throws SocketException {
        this._noTrafficTimeout = noTrafficTimeout;
        if(this._soTimeout> noTrafficTimeout) {
            this._soTimeout = noTrafficTimeout;
            if (_socket != null) {
                _socket.setSoTimeout(_soTimeout);
            }
        }
    }


    synchronized  void _requestReserve() throws YAPI_Exception
    {
        long timeout = YAPI.GetTickCount() + MAX_REQUEST_MS + 1000;
        while (timeout > YAPI.GetTickCount() && _state != State.AVAIL) {
            try {
                long toWait = timeout - YAPI.GetTickCount();
                wait(toWait);
            } catch (InterruptedException ie) {
                throw new YAPI_Exception(YAPI.TIMEOUT, "Last Http request did not finished");
            }
        }
        if (_state != State.AVAIL)
            throw new YAPI_Exception(YAPI.TIMEOUT, "Last Http request did not finished");
        _state = State.IN_REQUEST;
    }

    synchronized void _requestRelease()
    {
        _state   = State.AVAIL;
        notify();
    }



    void _requestStart(String firstLine,byte[] rest_of_request, Object context,
            YGenericHub.RequestAsyncResult resultCallback) throws YAPI_Exception
    {
        byte[] full_request;
        _firstLine = firstLine;
        _rest_of_request =rest_of_request;
        _context = context;
        _resultCallback = resultCallback;
        String persistent_tag = firstLine.substring(firstLine.length() - 2);
        if(persistent_tag.equals("&.")) {
            firstLine += " \r\n";
        } else {
            firstLine += " \r\nConnection: close\r\n";
        }
        if(rest_of_request == null){
            String str_request = firstLine+_hub.getAuthorization(firstLine)+"\r\n";
            full_request = str_request.getBytes();
        }else{
            String str_request = firstLine+_hub.getAuthorization(firstLine);
            int len = str_request.length();
            full_request = new byte[len + rest_of_request.length];
            System.arraycopy(str_request.getBytes(), 0, full_request, 0, len);
            System.arraycopy(rest_of_request, 0, full_request, len, rest_of_request.length);
        }
        try {
            if (!_reuse_socket) {
                _socket = new Socket(_hub.getHost(), _hub.getPort());
                _socket.setTcpNoDelay(true);
                _socket.setSoTimeout(_soTimeout);
                _out = new BufferedOutputStream(_socket.getOutputStream());
                _in = new BufferedInputStream(_socket.getInputStream());
            }
            _result.reset();
            _header.setLength(0);
            _header_found = false;
            _eof = false;
            _out.write(full_request);
            _out.flush();
            _reuse_socket = false;
            _lastReceiveTime = -1;
            _startRequestTime = new Date().getTime();
        }
        catch (UnknownHostException e)
        {
            _requestStop();
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT,"Unknown host("+_hub.getHost()+")");
        } catch (SocketException e) {
            _requestStop();
            throw new YAPI_Exception(YAPI.IO_ERROR,e.getLocalizedMessage());
        } catch (IOException e) {
            _requestStop();
            throw new YAPI_Exception(YAPI.IO_ERROR,e.getLocalizedMessage());
        }
    }


    void _requestStop()
    {
        if (!_reuse_socket) {
            if (_out != null) {
                try {
                    _out.close();
                } catch (IOException ignored) {
                }
                _out = null;
            }
            if (_in != null) {
                try {
                    _in.close();
                } catch (IOException ignored) {
                }
                _in = null;
            }
            if (_socket != null) {
                try {
                    _socket.close();
                } catch (IOException ignored) {
                }
                _socket = null;
            }
        }
    }

    void _requestReset() throws YAPI_Exception
    {
        _requestStop();
        _requestStart(_firstLine,_rest_of_request,_context,_resultCallback);
    }


    int _requestProcesss() throws YAPI_Exception
    {
        boolean retry;
        int read = 0;


        if(_eof)
            return -1;

        do {
            retry = false;
            byte[] buffer = new byte[1024];
            try {
                read = _in.read(buffer, 0, buffer.length);
            } catch (SocketTimeoutException e) {
                Date now = new Date();
                // global request timeout
                long duration = now.getTime() - _startRequestTime;
                if (duration > MAX_REQUEST_MS) {
                    throw new YAPI_Exception(YAPI.TIMEOUT, String.format("TCP request took too long (%dms)", duration));
                } else if (duration > MAX_REQUEST_MS / 2) {
                    YAPI.SafeYAPI()._Log(String.format("Slow TCP request on %s (%dms)\n", _hub.getHost(), duration));
                }
                if (_lastReceiveTime < 0 || now.getTime() - _lastReceiveTime < _noTrafficTimeout) {

                    retry = true;
                    continue;
                }
                throw new YAPI_Exception(YAPI.TIMEOUT, String.format("Hub did not send data during %dms", now.getTime() - _lastReceiveTime));
            } catch (IOException e) {
                throw new YAPI_Exception(YAPI.IO_ERROR, e.getLocalizedMessage());
            }
            if (read < 0) {
                // end of connection
                _reuse_socket = false;
                _eof = true;
            } else if (read > 0) {
                _lastReceiveTime = new Date().getTime();
                synchronized (_result) {
                    if (!_header_found) {
                        String partial_head = new String(buffer, 0, read);
                        _header.append(partial_head);
                        int pos = _header.indexOf("\r\n\r\n");
                        if (pos > 0) {
                            pos += 4;
                            try {
                                _result.write(_header.substring(pos).getBytes("ISO-8859-1"));
                            } catch (IOException ex) {
                                throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
                            }
                            _header_found = true;
                            _header.setLength(pos);
                            if (_header.indexOf("0K\r\n") == 0) {
                                _reuse_socket = true;
                            } else if (_header.indexOf("OK\r\n") != 0) {
                                int lpos = _header.indexOf("\r\n");
                                if (_header.indexOf("HTTP/1.1 ") != 0)
                                    throw new YAPI_Exception(YAPI.IO_ERROR, "Invalid HTTP response header");

                                String parts[] = _header.substring(9, lpos).split(" ");
                                if (parts[0].equals("401")) {
                                    if (!_hub.needRetryWithAuth()) {
                                        // No credential provided, give up immediately
                                        throw new YAPI_Exception(YAPI.UNAUTHORIZED, "Authentication required");
                                    } else {
                                        _hub.parseWWWAuthenticate(_header.toString());
                                        _requestReset();
                                        break;
                                    }
                                }
                                if (!parts[0].equals("200") && !parts[0].equals("304")) {
                                    throw new YAPI_Exception(YAPI.IO_ERROR, "Received HTTP status " + parts[0] + " (" + parts[1] + ")");
                                }
                            }
                            _hub.authSucceded();
                        }
                    } else {
                        _result.write(buffer, 0, read);
                    }
                    if (_reuse_socket) {
                        if (_result.toString().endsWith("\r\n")) {
                            _eof = true;
                        }
                    }
                }
            }
        } while (retry);
        return read;
    }


    byte[] getPartialResult() throws YAPI_Exception
    {
        byte[] res;
        synchronized(_result) {
            if(!_header_found)
                return null;
            if(_result.size()==0){
                if(_eof)
                    throw new YAPI_Exception(YAPI.NO_MORE_DATA,"end of file reached");
                return null;
            }
            res = _result.toByteArray();
            _result.reset();
        }
        return res;
    }




    byte[] RequestSync(String req_first_line, byte[] req_head_and_body) throws YAPI_Exception
    {
        byte[] res;
        _requestReserve();
        try {
            _requestStart(req_first_line, req_head_and_body,null,null);
            int read;
            do {
                read = _requestProcesss();
            } while (read >= 0);
            synchronized (_result) {
                res = _result.toByteArray();
                _result.reset();
            }
        } catch (YAPI_Exception ex) {
            _requestStop();
            _requestRelease();
            throw ex;

        }
        _requestStop();
        _requestRelease();
        return res;
    }


    @Override
    public void run()
    {
        byte[] res = null;
        int errorType = YAPI.SUCCESS;
        String errmsg = null;
        try {
            _requestProcesss();
            int read;
            do {
                read = _requestProcesss();
            } while (read >= 0);
            synchronized (_result) {
                res = _result.toByteArray();
                _result.reset();            
            }
        } catch (YAPI_Exception ex) {
            errorType = ex.errorType;
            errmsg = ex.getMessage();
            YAPI.SafeYAPI()._Log("ASYNC request " + _firstLine + "failed:" + errmsg+"\n");
        }
        _requestStop();
        if (_resultCallback!=null) {
            _resultCallback.RequestAsyncDone(_context, res, errorType, errmsg);
        }
        _requestRelease();        
    }


    void RequestAsync(String req_first_line, byte[] req_head_and_body,YGenericHub.RequestAsyncResult callback, Object context) throws YAPI_Exception
    {
        _requestReserve();
        try {
           _requestStart(req_first_line,req_head_and_body, context, callback);
           Thread t = new Thread(this);
           t.setName(_dbglabel);
           t.start();
        } catch (YAPI_Exception ex) {
            _requestRelease();
            throw  ex;
        }
    }


    synchronized void WaitRequestEnd()
    {
        long timeout = YAPI.GetTickCount()+MAX_REQUEST_MS;
        while (timeout > YAPI.GetTickCount() &&  _state == State.IN_REQUEST) {
            long toWait = timeout - YAPI.GetTickCount();
            try {
                wait(toWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        if(_state == State.IN_REQUEST)
            SafeYAPI()._Log("WARNING: Last Http request did not finished");
        // ensure that we close all socket
        _reuse_socket = false;
        _requestStop();
        _state   = State.STOPPED;
    }



}
