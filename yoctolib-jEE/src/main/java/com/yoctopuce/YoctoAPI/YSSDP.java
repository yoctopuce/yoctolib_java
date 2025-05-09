package com.yoctopuce.YoctoAPI;

import java.io.IOException;
import java.net.*;
import java.util.*;


/**
 * YSSDP Class: network discovery using ssdp
 * <p/>
 * this class is used to detect all YoctoHub using SSDP
 */
class YSSDP
{


    interface YSSDPReportInterface
    {
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
    private InetSocketAddress mMcastAddr;
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
        stopThreads();
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

        //Log.d("SSDP", "update cache for " + uuid + " " + url);
        if (_cache.containsKey(uuid)) {
            YSSDPCacheEntry entry = _cache.get(uuid);
            if (!entry.getURL().equals(url)) {
                _callbacks.HubDiscoveryCallback(entry.getSerial(), url, entry.getURL());
                entry.setURL(url);
            } else {
                if (entry.hasExpired()) {
                    _callbacks.HubDiscoveryCallback(entry.getSerial(), url, null);
                }
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
        //Log.i("SSDP", "Start listening..");

        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            if (netint.isUp() && !netint.isLoopback() && netint.supportsMulticast()) {
                _netInterfaces.add(netint);
            }
        }

        mMcastAddr = new InetSocketAddress(InetAddress.getByName(SSDP_MCAST_ADDR), SSDP_PORT);
        int size = _netInterfaces.size();
        _listenBcastThread = new Thread[size];
        _listenMSearchThread = new Thread[size];
        _Listening = false;
        for (int i = 0; i < size; i++) {
            final NetworkInterface netIf = _netInterfaces.get(i);
            final String if_name = netIf.getDisplayName();
            //Log.i("SSDP", "Start discovery for  " + if_name);
            _listenBcastThread[i] = new Thread(new Runnable()
            {
                public void run()
                {
                    DatagramPacket pkt;
                    byte[] pktContent;
                    String ssdpMessage;
                    MulticastSocket socketReception = null;
                    _yctx._Log("Start listening for SSDP on " + netIf.getDisplayName());
                    try {
                        socketReception = new MulticastSocket(SSDP_PORT);
                        socketReception.joinGroup(mMcastAddr, netIf);
                        socketReception.setSoTimeout(10000);
                    } catch (IOException e) {
                        _yctx._Log(String.format(Locale.US, "Unable to join MCAST group for %s: %s",
                                netIf.getName(), e.getLocalizedMessage()));
                        e.printStackTrace();
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
                    _yctx._Log("Stop listening for SSDP on " + netIf.getDisplayName());

                }
            }, "ssdp_mcast_" + if_name);
            _listenBcastThread[i].start();
            _listenMSearchThread[i] = new Thread(new Runnable()
            {
                public void run()
                {
                    try {
                        doMSearch(netIf);
                    } catch (IOException | InterruptedException e) {
                        _yctx._Log(String.format(Locale.US, "MSearch request failed (%s)", e.getLocalizedMessage()));
                        e.printStackTrace();
                    }
                }
            }, "ssdp_msearch_" + if_name);
            _listenMSearchThread[i].start();
        }
        _Listening = true;
    }

    private void doMSearch(NetworkInterface netIf) throws IOException, InterruptedException
    {
        DatagramPacket pkt;
        byte[] pktContent;
        String ssdpMessage;
        Random rand = new Random();
        _yctx._Log("Start SSDP MSearch on " + netIf.getDisplayName());
        // setup upd socket
        MulticastSocket msearchSocket;
        DatagramPacket outPkt;
        msearchSocket = new MulticastSocket();
        msearchSocket.setTimeToLive(2);
        msearchSocket.setNetworkInterface(netIf);
        // format MSEARCH packet with MX=5s
        byte[] outPktContent = SSDP_DISCOVERY_MESSAGE.getBytes(YAPI.DefaultEncoding);
        outPkt = new DatagramPacket(outPktContent, outPktContent.length, mMcastAddr);
        int response_delay = 20000;
        msearchSocket.setSoTimeout(response_delay);

        for (int i = 0; i < 5; i++) {
            //Log.d("SSDP", String.format(Locale.US, "send MSEACH on %s and wait %dms", netIf.getDisplayName(), response_delay));
            msearchSocket.send(outPkt);
            int sleep_delay = rand.nextInt(100);
            Thread.sleep(sleep_delay);
        }
        long wait_until = System.currentTimeMillis() + response_delay;
        //look for response only during 3 minutes
        while (_Listening && System.currentTimeMillis() < wait_until) {
            pktContent = new byte[1536];
            pkt = new DatagramPacket(pktContent, pktContent.length);
            try {
                msearchSocket.receive(pkt);
                ssdpMessage = new String(pktContent, pkt.getOffset(), pkt.getLength());
                parseIncomingMessage(ssdpMessage);
            } catch (SocketTimeoutException ignore) {
            }
        }
        _yctx._Log("End of SSDP MSearch on " + netIf.getDisplayName());

    }


    void Stop()
    {
        stopThreads();
        _cache.clear();
    }

    private void stopThreads()
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
