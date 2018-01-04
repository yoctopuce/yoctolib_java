package com.yoctopuce.YoctoAPI;

import java.io.IOException;
import java.net.*;
import java.util.*;


/**
 * YSSDP Class: network discovery using ssdp
 * <p/>
 * this class is used to detect all YoctoHub using SSDP
 */
class YSSDP {


    interface YSSDPReportInterface {
        /**
         * @param serial          : the serial number of the discovered Hub
         * @param urlToRegister   : the new URL to register
         * @param urlToUnregister : the old URL to unregister
         */
        void HubDiscoveryCallback(String serial, String urlToRegister, String urlToUnregister);
    }

    private static final int SSDP_PORT = 1900;
    private static final String SSDP_MCAST_ADDR = "239.255.255.250";
    private static final String SSDP_URN_YOCTOPUCE = "urn:yoctopuce-com:device:hub:1";
    private static final String SSDP_DISCOVERY_MESSAGE =
            "M-SEARCH * HTTP/1.1\r\n" +
                    "HOST: " + SSDP_MCAST_ADDR + ":" + Integer.toString(SSDP_PORT) + "\r\n" +
                    "MAN: \"ssdp:discover\"\r\n" +
                    "MX: 5\r\n" +
                    "ST: " + SSDP_URN_YOCTOPUCE + "\r\n" +
                    "\r\n";
    private static final String SSDP_NOTIFY = "NOTIFY * HTTP/1.1";
    private static final String SSDP_HTTP = "HTTP/1.1 200 OK";


    private final YAPIContext _yctx;
    private final ArrayList<NetworkInterface> _netInterfaces = new ArrayList<>(1);
    private final HashMap<String, YSSDPCacheEntry> _cache = new HashMap<>();
    private InetAddress mMcastAddr;
    private boolean _Listening;
    private YSSDPReportInterface _callbacks;
    private Thread[] _listenMSearchThread;
    private Thread[] _listenBcastThread;

    YSSDP(YAPIContext yctx)
    {
        _yctx = yctx;
    }

    void reset()
    {
        _netInterfaces.clear();
        _cache.clear();
        mMcastAddr = null;
        _Listening = false;
        _callbacks = null;
        _listenMSearchThread = null;
        _listenBcastThread = null;
    }

    synchronized void addCallback(YSSDPReportInterface callback) throws YAPI_Exception
    {
        if (_callbacks == callback)
            // already started
            return;
        _callbacks = callback;
        if (!_Listening) {
            try {
                startListening();
            } catch (IOException e) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "Unable to start SSDP thread : " + e.toString());
            }
        }
    }


    private synchronized void updateCache(String uuid, String url, int cacheValidity)
    {
        if (cacheValidity <= 0)
            cacheValidity = 1800;
        cacheValidity *= 1000;

        if (_cache.containsKey(uuid)) {
            YSSDPCacheEntry entry = _cache.get(uuid);
            if (!entry.getURL().equals(url)) {
                _callbacks.HubDiscoveryCallback(entry.getSerial(), url, entry.getURL());
                entry.setURL(url);
            } else {
                _callbacks.HubDiscoveryCallback(entry.getSerial(), url, null);
            }
            entry.resetExpiration(cacheValidity);
            return;
        }
        YSSDPCacheEntry entry = new YSSDPCacheEntry(uuid, url, cacheValidity);
        _cache.put(uuid, entry);
        _callbacks.HubDiscoveryCallback(entry.getSerial(), entry.getURL(), null);
    }

    private synchronized void checkCacheExpiration()
    {
        ArrayList<String> to_remove = new ArrayList<>();
        for (YSSDPCacheEntry entry : _cache.values()) {
            if (entry.hasExpired()) {
                _callbacks.HubDiscoveryCallback(entry.getSerial(), null, entry.getURL());
                to_remove.add(entry.getUUID());
            }
        }
        for (String uuid : to_remove) {
            _cache.remove(uuid);
        }
    }


    private void startListening() throws IOException
    {


        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            if (netint.isUp() && !netint.isLoopback() && netint.supportsMulticast()) {
                _netInterfaces.add(netint);
            }
        }

        mMcastAddr = InetAddress.getByName(SSDP_MCAST_ADDR);
        int size = _netInterfaces.size();
        _listenBcastThread = new Thread[size];
        _listenMSearchThread = new Thread[size];
        _Listening = false;
        for (int i = 0; i < size; i++) {
            final NetworkInterface netIf = _netInterfaces.get(i);
            _listenBcastThread[i] = new Thread(new Runnable() {
                public void run()
                {
                    DatagramPacket pkt;
                    byte[] pktContent;
                    String ssdpMessage;
                    MulticastSocket socketReception = null;
                    try {
                        socketReception = new MulticastSocket(SSDP_PORT);
                        socketReception.joinGroup(mMcastAddr);
                        socketReception.setSoTimeout(10000);
                        socketReception.setNetworkInterface(netIf);
                    } catch (IOException e) {
                        e.printStackTrace();
                        //fixme: better error handling
                        return;
                    }

                    while (_Listening) {
                        pktContent = new byte[1536];
                        pkt = new DatagramPacket(pktContent, pktContent.length);
                        try {
                            socketReception.receive(pkt);
                            ssdpMessage = new String(pktContent, pkt.getOffset(), pkt.getLength());
                            parseIncomingMessage(ssdpMessage);
                        } catch (SocketTimeoutException ignored) {
                        } catch (IOException e) {
                            _yctx._Log("SSDP:" + e.getLocalizedMessage());
                        }
                        checkCacheExpiration();
                    }
                }
            });
            _listenBcastThread[i].start();
            _listenMSearchThread[i] = new Thread(new Runnable() {
                public void run()
                {
                    DatagramPacket pkt;
                    byte[] pktContent;
                    String ssdpMessage;
                    Date date = new Date();

                    MulticastSocket msearchSocket;
                    try {
                        msearchSocket = new MulticastSocket();
                        msearchSocket.setTimeToLive(15);
                        msearchSocket.setNetworkInterface(netIf);
                        byte[] outPktContent = SSDP_DISCOVERY_MESSAGE.getBytes();
                        DatagramPacket outPkt = new DatagramPacket(outPktContent, outPktContent.length, mMcastAddr, SSDP_PORT);
                        msearchSocket.send(outPkt);
                    } catch (IOException ex) {
                        //todo: more user friendy error report
                        _yctx._Log("Unable to Send SSDP mSearch:" + ex.getLocalizedMessage());
                        return;
                    }

                    //look for response only during 3 minutes
                    while (_Listening && (new Date().getTime() - date.getTime()) < 180000) {
                        pktContent = new byte[1536];
                        pkt = new DatagramPacket(pktContent, pktContent.length);
                        try {
                            msearchSocket.receive(pkt);
                            ssdpMessage = new String(pktContent, pkt.getOffset(), pkt.getLength());
                            parseIncomingMessage(ssdpMessage);
                        } catch (IOException ex) {
                            //todo: more user friendy error report
                            _yctx._Log("SSDP error:" + ex.getLocalizedMessage());
                            return;
                        }
                    }
                }
            });
            _listenMSearchThread[i].start();
        }
        _Listening = true;
    }


    void Stop()
    {
        _Listening = false;
        for (int i = 0; i < _netInterfaces.size(); i++) {
            if (_listenMSearchThread[i].isAlive())
                _listenMSearchThread[i].interrupt();
            _listenMSearchThread[i] = null;
            if (_listenBcastThread[i].isAlive())
                _listenBcastThread[i].interrupt();
            _listenBcastThread[i] = null;
        }
        _cache.clear();
    }


    private void parseIncomingMessage(String message)
    {
        int i = 0;
        String location = null;
        String usn = null;
        String cache = null;


        String[] lines = message.split("\r\n");
        if (!lines[i].equals(SSDP_HTTP) && !lines[i].equals(SSDP_NOTIFY)) {
            return;
        }
        for (i = 0; i < lines.length; i++) {
            int pos = lines[i].indexOf(":");
            if (pos <= 0) continue;
            if (lines[i].startsWith("LOCATION")) {
                location = lines[i].substring(pos + 1).trim();
            } else if (lines[i].startsWith("USN")) {
                usn = lines[i].substring(pos + 1).trim();
            } else if (lines[i].startsWith("CACHE-CONTROL")) {
                cache = lines[i].substring(pos + 1).trim();
            }
        }
        if (location != null && usn != null && cache != null) {
            // parse USN
            int posuuid = usn.indexOf(':');
            if (posuuid < 0)
                return;
            posuuid++;
            int posurn = usn.indexOf("::", posuuid);
            if (posurn < 0) {
                return;
            }
            String uuid = usn.substring(posuuid, posurn).trim();
            String urn = usn.substring(posurn + 2).trim();
            if (!urn.equals(SSDP_URN_YOCTOPUCE)) {
                return;
            }
            // parse Location
            if (location.startsWith("http://")) {
                location = location.substring(7);
            }
            int posslash = location.indexOf('/');
            if (posslash > 0) {
                location = location.substring(0, posslash);
            }

            int poscache = cache.indexOf('=');
            if (poscache < 0) return;
            cache = cache.substring(poscache + 1).trim();
            int cacheVal = Integer.decode(cache);
            updateCache(uuid, location, cacheVal);
        }
    }

}
