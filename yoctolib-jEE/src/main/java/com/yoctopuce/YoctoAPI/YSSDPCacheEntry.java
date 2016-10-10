package com.yoctopuce.YoctoAPI;

import java.util.Date;

class YSSDPCacheEntry {
    private String mSerial;
    private String mUUID;
    private String mURL;
    private Date mDetectedTime;
    private long mMaxAgeInMS;
    private boolean mRegistered;

    YSSDPCacheEntry(String uuid, String URL, long maxAgeInMS) {
        mUUID = uuid;
        mURL = URL;
        mMaxAgeInMS = maxAgeInMS;
        mDetectedTime = new Date();
        mRegistered = false;

        StringBuilder serial = new StringBuilder(YAPI.YOCTO_SERIAL_LEN);
        int i, j;
        for (i = 0, j = 0; i < 4; i++, j += 2) {
            String ch = uuid.substring(j, j + 2);
            serial.append((char) Integer.parseInt(ch, 16));
        }
        j++;
        for (; i < 6; i++, j += 2) {
            String ch = uuid.substring(j, j + 2);
            serial.append((char) Integer.parseInt(ch, 16));
        }
        j++;
        for (; i < 8; i++, j += 2) {
            String ch = uuid.substring(j, j + 2);
            serial.append((char) Integer.parseInt(ch, 16));
        }
        serial.append('-');
        i = uuid.indexOf("-COFF-EE");
        i += 8;
        while (uuid.charAt(i) == '0') i++;
        String numPart = uuid.substring(i);
        for (i = numPart.length(); i < 5; i++) {
            serial.append('0');
        }
        serial.append(numPart);
        mSerial = serial.toString();
    }

    String getSerial() {
        return mSerial;
    }

    void setSerial(String serial) {
        mSerial = serial;
    }

    String getUUID() {
        return mUUID;
    }

    String getURL() {
        return mURL;
    }

    void setURL(String URL) {
        mURL = URL;
    }

    long getMaxAgeInMS() {
        return mMaxAgeInMS;
    }

    void resetExpiration(int cacheValidity) {
        mMaxAgeInMS = cacheValidity;
        mDetectedTime = new Date();
    }

    boolean hasExpired() {
        Date now = new Date();
        //noinspection RedundantIfStatement
        if ((now.getTime() - mDetectedTime.getTime()) > mMaxAgeInMS)
            return true;
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    boolean isRegistered() {
        return mRegistered;
    }

    void setRegistered(boolean registered) {
        mRegistered = registered;
    }
}
