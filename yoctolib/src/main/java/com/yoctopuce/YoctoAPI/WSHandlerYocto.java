package com.yoctopuce.YoctoAPI;

import javax.net.ssl.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

class WSHandlerYocto implements WSHandlerInterface, Runnable
{

    private final WSHandlerResponseInterface _nhandler;
    private MessageDigest _sha1 = null;

    private String _websocket_key;

    private static final String WS_HEADER_START = " HTTP/1.1\r\nSec-WebSocket-Version: 13\r\nUser-Agent: Yoctopuce\r\nSec-WebSocket-Key: ";
    private static final String WS_HEADER_END = "\r\nConnection: keep-alive, Upgrade\r\nUpgrade: websocket\r\n\r\n";
    private static final String WEBSOCKET_MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";


    private Socket _socket;
    private BufferedOutputStream _out;
    private BufferedInputStream _in;
    private final Random _randGen = new Random();
    private ByteArrayOutputStream _fragments = null;
    private boolean _closing;
    private boolean _closed;
    private Thread _thread;

    WSHandlerYocto(WSHandlerResponseInterface notificationHandler)
    {
        _nhandler = notificationHandler;
        try {
            _sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    synchronized private void closeSoket()
    {
        if (_socket != null) {
            try {
                if (_out != null) {
                    _out.flush();
                    _out.close();
                }
                if (_in != null) {
                    _in.close();
                }
                _socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            _out = null;
            _in = null;
            _socket = null;
        }

    }


    @Override
    public void close()
    {
        if (_closed) {
            return;
        }
        _closing = true;
        if (_thread != null) {
            _thread.interrupt();
            try {
                _thread.join(1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        closeSoket();
    }

    @Override
    public boolean isOpen()
    {

        return !isClosed() & !isClosing();
    }

    @Override
    public void sendBinary(ByteBuffer partialByte, boolean isLast) throws YAPI_Exception
    {

        byte[] header = new byte[2];
        byte[] mask = new byte[4];

        _randGen.nextBytes(mask);

        int pktlen = partialByte.remaining();
        try {
            header[0] = (byte) 0x82;
            header[1] = (byte) ((pktlen & 0xff) | 0x80);
            _out.write(header);
            _out.write(mask);
            byte[] buf = new byte[pktlen];
            partialByte.get(buf);
            int i;
            for (i = 0; i < pktlen; i++) {
                buf[i] ^= mask[i & 3];
            }
            _out.write(buf);
            if (isLast) {
                _out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getThreadLabel()
    {
        return "WSYocto";
    }

    @Override
    public boolean isCallback()
    {
        return false;
    }


    private boolean VerifyWebsocketKey(String toValidate)
    {
        String magic = _websocket_key + WEBSOCKET_MAGIC;
        _sha1.reset();
        _sha1.update(magic.getBytes());
        byte[] digest = _sha1.digest();
        String valid = YAPI.Base64Encode(digest, 0, digest.length);
        return toValidate.trim().equals(valid);
    }


    @Override
    public void connect(YHTTPHub hub, boolean first_notification_connection, int mstimeout, int notifAbsPos) throws YAPI_Exception
    {
        long start = System.currentTimeMillis();
        boolean isredirect = false;
        String request = "GET ";
        String subDomain = hub._runtime_http_params.getSubDomain();
        request += subDomain;
        _closed = true;
        _closing = false;
        _fragments = null;
        String host = hub.getHost();
        _nhandler.WSLOG(String.format(Locale.US, "hub(%s) try to open WS connection at %d", hub._runtime_http_params.getOriginalURL(), notifAbsPos));
        if (first_notification_connection) {
            request += "/not.byn";
        } else {
            request += String.format(Locale.US, "/not.byn?abs=%d", notifAbsPos);
        }
        try {
            InetAddress addr = InetAddress.getByName(host);
            _socket = hub.OpenConnectedSocket(addr, hub.getPort(), mstimeout);
            _socket.setTcpNoDelay(true);
            _socket.setSoTimeout(1000);
            _out = new BufferedOutputStream(_socket.getOutputStream());
            _in = new BufferedInputStream(_socket.getInputStream(), 2048);
        } catch (UnknownHostException e) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Unknown host(" + host + ")");
        } catch (IOException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, e.getLocalizedMessage());
        }
        // write request
        try {
            _out.write(request.getBytes(hub._yctx._defaultEncoding));
            _out.write(WS_HEADER_START.getBytes(hub._yctx._defaultEncoding));

            byte[] SecWebSocketKey = new byte[16];
            _randGen.nextBytes(SecWebSocketKey);
            String encodedBytes = YAPI.Base64Encode(SecWebSocketKey, 0, SecWebSocketKey.length);
            _websocket_key = encodedBytes;
            _out.write(encodedBytes.getBytes(hub._yctx._defaultEncoding));
            _out.write(String.format("\r\nHost: %s:%d", host, hub.getPort()).getBytes(hub._yctx._defaultEncoding));
            _out.write(WS_HEADER_END.getBytes(hub._yctx._defaultEncoding));
            _out.flush();
            _closed = false;

            StringBuilder header = new StringBuilder(2048);
            byte[] buffer = new byte[2048];
            boolean websock_ok = false;
            while ((System.currentTimeMillis() - start) < mstimeout) {
                int read = _in.read(buffer, 0, buffer.length);
                if (read < 0) {
                    break;
                }
                String part = new String(buffer, 0, read, hub._yctx._deviceCharset);
                header.append(part);
                int end_of_head = header.indexOf("\r\n\r\n");
                if (end_of_head > 0) {
                    end_of_head += 2;
                    header.setLength(end_of_head);
                    String fullHeader = header.toString();
                    int endl = fullHeader.indexOf("\r\n");
                    String firstline = fullHeader.substring(0, endl);
                    int ofs = firstline.indexOf("HTTP/1.1 ");
                    int endcode = firstline.indexOf(" ", 9);
                    if (ofs != 0 || endcode == -1) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "Invalid HTTP header");
                    }
                    String httpresponse = firstline.substring(9, endcode);
                    int httpcode = Integer.parseInt(httpresponse);
                    if (httpcode == 301 || httpcode == 302 || httpcode == 307 || httpcode == 308) {
                        isredirect = true;
                    } else if (httpcode != 101) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "hub does not support WebSocket");
                    }
                    ofs = fullHeader.indexOf("\r\n");
                    while (ofs > 0 && ofs < fullHeader.length() - 2) {
                        ofs += 2;
                        int nextofs = header.indexOf("\r\n", ofs);
                        int sep = header.indexOf(":", ofs);
                        if (sep > 0 && nextofs > sep) {
                            String field = header.substring(ofs, sep);
                            String lowerCase = field.toLowerCase().trim();
                            if (lowerCase.startsWith("sec-websocket-accept")) {
                                String value = header.substring(sep + 1, nextofs);
                                websock_ok = VerifyWebsocketKey(value);
                                if (websock_ok) {
                                    int start_bin = end_of_head + 2;
                                    setupNewWSConnection(buffer, start_bin, read - start_bin);
                                }
                                break;
                            } else if (isredirect && lowerCase.startsWith("location")) {
                                String value = header.substring(sep + 1, nextofs).trim();
                                _nhandler.WSLOG("redirect to " + value);
                                YGenericHub.HTTPParams new_url = new YGenericHub.HTTPParams(value);
                                // update only host, proto and port
                                hub._runtime_http_params.updateForRedirect(new_url.getHost(), new_url.getPort(), new_url.useSecureSocket());
                                break;
                            }
                        }
                        ofs = nextofs;
                    }
                    break;
                }
            }
            if (!isredirect && !websock_ok) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "hub does not support WebSocket");
            }
        } catch (SSLHandshakeException e) {
            throw new YAPI_Exception(YAPI.SSL_UNK_CERT, "unable to contact " + host + " :" + e.getLocalizedMessage(), e);
        } catch (SSLException e) {
            throw new YAPI_Exception(YAPI.SSL_ERROR, e.getLocalizedMessage());
        } catch (IOException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, e.getLocalizedMessage());
        }
        if (isredirect) {
            close();
            long spent = System.currentTimeMillis() - start;
            if (spent < mstimeout) {
                connect(hub, first_notification_connection, (int) (mstimeout - spent), notifAbsPos);
            }
        }
    }

    private void setupNewWSConnection(byte[] buffer, int ofs, int len) throws YAPI_Exception
    {
        _closing = false;
        //_nhandler.WSLOG("Websocket handshake done");
        if (len > 0) {
            decodeFrame(buffer, ofs, len);
        }
        _thread = new Thread(this);
        _thread.setName("WSYocto_helpler");
        _thread.start();
    }


    @Override
    public void run()
    {
        byte[] rawbuffer = new byte[2048];
        int ofs = 0;
        int max = rawbuffer.length;

        int readBytes;

        try {
            while (!isClosing() && !isClosed()) {
                try {
                    readBytes = _in.read(rawbuffer, ofs, max);
                } catch (SocketTimeoutException ex) {
                    // read timeout check if we need to retry
                    continue;
                }
                if (readBytes < 0) {
                    break;
                }

                int end_ofs = ofs + readBytes;
                int ptr = 0;
                int avail = end_ofs;
                int consumed;
                do {
                    consumed = decodeFrame(rawbuffer, ptr, avail);
                    ptr += consumed;
                    avail -= consumed;
                    //_nhandler.WSLOG(String.format(Locale.US, "IN (%d + %d) - %d", ofs, readBytes, consumed));
                } while (consumed > 0 && avail > 2);

                if (avail > 0 && ptr > 0) {
                    System.arraycopy(rawbuffer, ptr, rawbuffer, 0, avail);
                }
                ofs = avail;
                max = rawbuffer.length - ofs;
            }
        } catch (IOException e) {
            _nhandler.errorOnSession(YAPI.IO_ERROR, e.getLocalizedMessage());
        } catch (YAPI_Exception e) {
            _nhandler.errorOnSession(e.errorType, e.getLocalizedMessage());
        }
        closeSoket();
        _closed = true;
    }


    /**
     * Decode one frame and return the cosumed bytes
     *
     * @param data data to handle
     * @return the number of consumed bytes
     */
    private int decodeFrame(byte[] data, int ofs, int len) throws YAPI_Exception
    {

        if (len < 2) {
            return 0;
        }
        int pktlen = data[ofs + 1] & 0x7f;
        if (pktlen > 125) {
            // Unsupported long frame, drop all incoming data (probably 1+ frame(s))
            throw new YAPI_Exception(YAPI.IO_ERROR, "Unsupported long WebSocket frame");
        }
        int hdrlen;
        byte[] mask = null;
        if ((data[ofs + 1] & 0x80) != 0) {
            // masked frame
            hdrlen = 6;
            if (len < hdrlen + pktlen) {
                return 0;
            }
            mask = Arrays.copyOfRange(data, ofs + 2, ofs + 2);
        } else {
            // plain frame
            hdrlen = 2;
            if (len < hdrlen + pktlen) {
                return 0;
            }
        }


        if ((data[ofs] & 0x7f) != 0x02) {
            // Non-data frame
            if ((data[ofs] & 0xff) == 0x88) {
                byte[] header = new byte[2];
                header[0] = (byte) (0x88 & 0xff);
                header[1] = (byte) (0x82 & 0xff);
                try {
                    _out.write(header);
                    byte[] outmask = new byte[4];
                    // websocket close, reply with a close
                    _randGen.nextBytes(outmask);
                    _out.write(outmask);
                    header[0] = (byte) (0x03 ^ outmask[0]);
                    header[1] = (byte) (0xe8 ^ outmask[1]);
                    _out.write(header);
                    _out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new YAPI_Exception(YAPI.IO_ERROR, "io error on base socket:" + e.getLocalizedMessage());
                }
                _closed = true;
            } else {
                // unhandled packet
                _nhandler.WSLOG(String.format(Locale.US, "unhandled packet:%x%x\n", data[ofs], data[ofs + 1]));
            }

        }
        boolean fragmented = (data[ofs] & 0x0d) != 0;
        ofs += hdrlen;

        // append
        if (mask != null) {
            int i;
            for (i = 0; i < pktlen; i++) {
                data[ofs + i] ^= mask[i & 3];
            }
        }

        if (fragmented) {
            int ystream = (data[ofs] & 0xff) >> 3;
            if (ystream == YGenericHub.YSTREAM_META) {
                // unsupported fragmented META stream, should never happen
                _nhandler.WSLOG("Warning:fragmented META\n");
                return hdrlen + pktlen;
            }

            //  fragmented binary frame
            if (_fragments == null) {
                _fragments = new ByteArrayOutputStream(1024);
            }
            _fragments.write(data, ofs, pktlen);
        } else {
            if (_fragments != null && _fragments.size() > 0) {
                _fragments.write(data, ofs, pktlen);
                byte[] merged = _fragments.toByteArray();
                ByteBuffer wrap = ByteBuffer.wrap(merged);
                _fragments.reset();
                _nhandler.parseBinaryMessage(wrap);
            } else {
                ByteBuffer wrap = ByteBuffer.wrap(data, ofs, pktlen);
                _nhandler.parseBinaryMessage(wrap);
            }
        }
        return hdrlen + pktlen;
    }


    private boolean isClosed()
    {
        return _closed;
    }

    private boolean isClosing()
    {
        return _closing;
    }


}
