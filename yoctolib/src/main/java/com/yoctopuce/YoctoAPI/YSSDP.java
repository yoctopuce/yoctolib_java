package com.yoctopuce.YoctoAPI;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

/**
 * YSSDP Class: network discovery using ssdp
 * <p/>
 * this class is used to detect all YoctoHub using SSDP
 */
public class YSSDP {


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


    private HashMap<String, YSSDPCacheEntry> mCache = new HashMap<String, YSSDPCacheEntry>();
    private InetAddress mMcastAddr;
    private boolean mListening;
    private MulticastSocket mSocketReception;
    private YSSDPReportInterface _callbacks;

    YSSDP() {
        mListening = false;
    }


    synchronized void addCallback(YSSDPReportInterface callback) throws YAPI_Exception {
        if (_callbacks == callback)
            // already started
            return;
        _callbacks = callback;
        if (!mListening) {
            try {
                startListening();
            } catch (IOException e) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "Unable to start SSDP thread : " + e.toString());
            }
        }
    }


    private synchronized void updateCache(String uuid, String url, int cacheValidity) {
        if (cacheValidity <= 0)
            cacheValidity = 1800;
        cacheValidity *= 1000;

        if (mCache.containsKey(uuid)) {
            YSSDPCacheEntry entry = mCache.get(uuid);
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
        mCache.put(uuid, entry);
        _callbacks.HubDiscoveryCallback(entry.getSerial(), entry.getURL(), null);
    }

    private synchronized void checkCacheExpiration() {
        for (YSSDPCacheEntry entry : mCache.values()) {
            if (entry.hasExpired()) {
                _callbacks.HubDiscoveryCallback(entry.getSerial(), null, entry.getURL());
                mCache.remove(entry.getUUID());
            }
        }
    }

    private Thread mListenBcastThread = new Thread(new Runnable() {
        @Override
        public void run() {
            DatagramPacket pkt;
            byte[] pktContent;
            String ssdpMessage;

            while (mListening) {
                pktContent = new byte[1536];
                pkt = new DatagramPacket(pktContent, pktContent.length);
                try {
                    mSocketReception.receive(pkt);
                    ssdpMessage = new String(pktContent, pkt.getOffset(), pkt.getLength());
                    parseIncomingMessage(ssdpMessage);
                } catch (SocketTimeoutException ignored) {
                } catch (IOException e) {
                    SafeYAPI()._Log("SSDP:" + e.getLocalizedMessage());
                }
                checkCacheExpiration();
            }
        }
    });


    private void startListening() throws IOException {
        mMcastAddr = InetAddress.getByName(SSDP_MCAST_ADDR);
        mSocketReception = new MulticastSocket(SSDP_PORT);
        mListening = false;
        mSocketReception.joinGroup(mMcastAddr);
        mSocketReception.setSoTimeout(10000);
        mListening = true;
        mListenBcastThread.start();
        mListenMSearchThread.start();
    }

    void Stop() {
        mListening = false;
        if (mListenMSearchThread.isAlive())
            mListenMSearchThread.interrupt();
        if (mListenBcastThread.isAlive())
            mListenBcastThread.interrupt();
    }


    private void parseIncomingMessage(String message) {
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


    private Thread mListenMSearchThread = new Thread(new Runnable() {
        @Override
        public void run() {
            DatagramPacket pkt;
            byte[] pktContent;
            String ssdpMessage;
            Date date = new Date();

            MulticastSocket mMsearchSocket;
            try {
                mMsearchSocket = new MulticastSocket();
                mMsearchSocket.setTimeToLive(15);
                byte[] outPktContent = SSDP_DISCOVERY_MESSAGE.getBytes();
                DatagramPacket outPkt = new DatagramPacket(outPktContent, outPktContent.length, mMcastAddr, SSDP_PORT);
                mMsearchSocket.send(outPkt);
            } catch (IOException ex) {
                //todo: more user friendy error report
                YAPI.SafeYAPI()._Log("Unable to Send SSDP mSearch:" + ex.getLocalizedMessage());
                return;
            }

            //look for response only during 3 minutes
            while (mListening && (new Date().getTime() - date.getTime()) < 180000) {
                pktContent = new byte[1536];
                pkt = new DatagramPacket(pktContent, pktContent.length);
                try {
                    mMsearchSocket.receive(pkt);
                    ssdpMessage = new String(pktContent, pkt.getOffset(), pkt.getLength());
                    parseIncomingMessage(ssdpMessage);
                } catch (IOException ex) {
                    //todo: more user friendy error report
                    YAPI.SafeYAPI()._Log("SSDP error:" + ex.getLocalizedMessage());
                    return;
                }
            }
        }
    });

}
