/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.YoctoAPI;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


class yHTTPRequest implements Runnable{




    private enum State {
        AVAIL, IN_REQUEST,STOPED
    }

    public static final long MAX_REQUEST_MS = 60*1000;

    private YHTTPHub                _hub;
    private Socket                  _socket  = null;
    private BufferedOutputStream    _out     = null;
    private BufferedInputStream     _in      = null;
    private State                   _state= State.AVAIL;
    private boolean                 _eof;
    private String                  _firstLine;
    private byte[]                  _rest_of_request;
    @SuppressWarnings("unused")
    private String                  _dbglabel;
    private final StringBuilder     _header = new StringBuilder(1024);
    private Boolean                 _header_found;
    private final ByteArrayOutputStream _result = new ByteArrayOutputStream(1024);

    yHTTPRequest(YHTTPHub hub,String dbglabel)
    {
        _hub = hub;
        _dbglabel =dbglabel;
    }


    synchronized void _requestReserve() throws YAPI_Exception
    {
        long timeout = YAPI.GetTickCount()+MAX_REQUEST_MS;
        while (timeout>YAPI.GetTickCount() &&  _state != State.AVAIL) {
            try {
                long toWait = timeout - YAPI.GetTickCount();
                wait(toWait);
            } catch(InterruptedException ie) {
                throw new YAPI_Exception(YAPI.TIMEOUT, "Last Http request did not finished");
            }
        }
        if(_state != State.AVAIL)
            throw new YAPI_Exception(YAPI.TIMEOUT, "Last Http request did not finished");
        _state   = State.IN_REQUEST;
    }

    synchronized void _requestRelease()
    {
        _state   = State.AVAIL;
        notify();
    }



    void _requestStart(String firstLine,byte[] rest_of_request) throws YAPI_Exception
    {
        byte[] full_request;
        _firstLine =firstLine;
        _rest_of_request =rest_of_request;
        if(rest_of_request == null){
            String str_request = firstLine+_hub.getAuthorization(firstLine)+"Connection: close\r\n\r\n";
            full_request = str_request.getBytes();
        }else{
            String str_request = firstLine+_hub.getAuthorization(firstLine)+"Connection: close\r\n";
            int len = str_request.length();
            full_request = new byte[len + rest_of_request.length];
            System.arraycopy(str_request.getBytes(), 0, full_request, 0, len);
            System.arraycopy(rest_of_request, 0, full_request, len, rest_of_request.length);
        }
        try {
            _socket  = new Socket(_hub.getHttpHost(), _hub.getHttpPort());
            _socket.setTcpNoDelay(true);
            _out     = new BufferedOutputStream(_socket.getOutputStream());
            _in      = new BufferedInputStream(_socket.getInputStream());
            _result.reset();
            _header.setLength(0);
            _header_found=false;
            _eof=false;
	    _out.write(full_request);
            _out.flush();
        }
        catch (UnknownHostException e)
        {
            _requestStop();
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT,"Unknown host("+_hub.getHttpHost()+")");
        }
        catch (IOException e)
        {
            _requestStop();
            throw new YAPI_Exception(YAPI.IO_ERROR,e.getLocalizedMessage());
        }
    }


    void _requestStop()
    {
        try {
            if(_out!=null){
                _out.close();
                _out = null;
            }
            if(_in!=null){
                _in.close();
                _in = null;
            }
            if(_socket!= null){
                _socket.close();
                _socket = null;
            }
        } catch (IOException e) {
        }
    }

    void _requestReset() throws YAPI_Exception
    {
        _requestStop();
        _requestStart(_firstLine,_rest_of_request);
    }


     int _requestProcesss() throws YAPI_Exception
    {
        boolean retry;
        int read;

        if(_eof)
            return -1;

        do {
            retry=false;
            byte[] buffer = new byte[1024];
            try {
                read = _in.read(buffer,0,buffer.length);
            } catch (IOException e) {
                throw new YAPI_Exception(YAPI.IO_ERROR,e.getLocalizedMessage());
            }
            if(read<0){
                // end of connection
                _eof=true;
            }else if(read>0){
                synchronized(_result) {
                    if( !_header_found ) {
                        String partial_head = new String(buffer,0,read);
                        _header.append(partial_head);
                        int pos =_header.indexOf("\r\n\r\n");
                        if( pos > 0 ) {
                            pos+=4;
                            try {
                                _result.write(_header.substring(pos).getBytes("ISO-8859-1"));
                            } catch (IOException ex) {
                                throw new YAPI_Exception(YAPI.IO_ERROR,ex.getLocalizedMessage());
                            }
                            _header_found = true;
                            _header.setLength(pos);
                            if ( _header.indexOf("OK\r\n")!=0 ) {
                                int lpos = _header.indexOf("\r\n");
                                if(_header.indexOf("HTTP/1.1 ")!=0)
                                    throw new YAPI_Exception(YAPI.IO_ERROR,"Invalid HTTP response header");

                                String parts[] = _header.substring(9, lpos).split(" ");
                                if(parts[0].equals("401")){
                                    if(!_hub.needRetryWithAuth()) {
                                        // No credential provided, give up immediately
                                        throw new YAPI_Exception(YAPI.UNAUTHORIZED,"Authentication required");
                                    } else {
                                        _hub.parseWWWAuthenticate(_header.toString());
                                        _requestReset();
                                        retry=true;
                                        break;
                                    }

                                }
                                if(!parts[0].equals("200") && !parts[0].equals("304")){
                                    throw new YAPI_Exception(YAPI.IO_ERROR,"Received HTTP status "+parts[0]+" ("+parts[1]+")");
                                }
                            }
                            _hub.authSucceded();
                        }
                    }else {
                        _result.write(buffer, 0, read);
                    }

                }
            }
        }while(retry);
        return read;
    }


    byte[] getPartialResult() throws YAPI_Exception
    {
        byte[] res=null;
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
            _requestStart(req_first_line, req_head_and_body);
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
        try {
            _requestProcesss();
            int read;
            do {
                read = _requestProcesss();
            } while (read >= 0);
        } catch (YAPI_Exception ex) {
        }
        _requestStop();
        _requestRelease();
    }


    void RequestAsync(String req_first_line, byte[] req_head_and_body) throws YAPI_Exception
    {
        _requestReserve();
        try {
           _requestStart(req_first_line,req_head_and_body);
           Thread t = new Thread(this);
           t.start();
        } catch (YAPI_Exception ex) {
            _requestRelease();
            throw  ex;
        }
    }

    synchronized void WaitRequestEnd()
    {
        long timeout = YAPI.GetTickCount()+MAX_REQUEST_MS;
        while (timeout>YAPI.GetTickCount() &&  _state == State.IN_REQUEST) {
            try {
                long toWait = timeout - YAPI.GetTickCount();
                wait(toWait);
            } catch(InterruptedException ie) {
                break;
            }
        }
        if(_state == State.IN_REQUEST)
            YAPI.Log("WARNING: Last Http request did not finished");
        _state   = State.STOPED;
    }



}
