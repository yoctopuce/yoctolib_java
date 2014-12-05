/*********************************************************************
 *
 * $Id: YGenericHub.java 18456 2014-11-20 16:29:57Z seb $
 *
 * Internal YGenericHub object
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

import com.yoctopuce.YoctoAPI.YAPI.PlugEvent.Event;

import java.util.ArrayList;
import java.util.HashMap;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

abstract class YGenericHub {

    protected int _hubidx;
    protected long _notifyTrigger = 0;
    protected Object _notifyHandle = null;
    protected volatile long _devListValidity = 500;
    protected long _devListExpires = 0;
    protected HashMap<Integer, String> _serialByYdx = new HashMap<Integer, String>();
    protected HashMap<String, YDevice> _devices = new HashMap<String, YDevice>();
    //protected ArrayList<WPEntry> wpages = new ArrayList<WPEntry>();
    protected final boolean _reportConnnectionLost;

    public YGenericHub(int idx, boolean reportConnnectionLost)
    {
        _hubidx = idx;
        _reportConnnectionLost = reportConnnectionLost;
    }

    abstract void release();

    abstract String getRootUrl();

    abstract boolean isSameRootUrl(String url);

    abstract void startNotifications() throws YAPI_Exception;

    abstract void stopNotifications();

    protected void updateFromWpAndYp(ArrayList<WPEntry> whitePages, HashMap<String, ArrayList<YPEntry>> yellowPages) throws YAPI_Exception
    {
        // by default consider all known device as unplugged
        ArrayList<YDevice> toRemove = new ArrayList<YDevice>(_devices.values());

        for (WPEntry wp : whitePages) {
            String serial = wp.getSerialNumber();
            if (_devices.containsKey(serial)) {
                // already there
                YDevice currdev = _devices.get(serial);
                if (!currdev.getLogicalName().equals(wp.getLogicalName())) {
                    // Reindex device from its own data
                    currdev.refresh();
                    SafeYAPI().pushPlugEvent(Event.CHANGE, serial);
                } else if (currdev.getBeacon() > 0 != wp.getBeacon() > 0) {
                    currdev.refresh();
                }
                toRemove.remove(currdev);
            } else {
                YDevice dev = new YDevice(this, wp, yellowPages);
                _devices.put(serial, dev);
                SafeYAPI().pushPlugEvent(Event.PLUG, serial);
                SafeYAPI()._Log("HUB: device " + serial + " has been plugged\n");
            }
        }

        for (YDevice dev : toRemove) {
            String serial = dev.getSerialNumber();
            SafeYAPI().pushPlugEvent(Event.UNPLUG, serial);
            SafeYAPI()._Log("HUB: device " + serial + " has been unplugged\n");
            _devices.remove(serial);
        }
    }

    abstract void updateDeviceList(boolean forceupdate) throws YAPI_Exception;

    public abstract ArrayList<String> getBootloaders();

    interface UpdateProgress
    {
        public void firmware_progress(int percent, String message);
    }

    abstract ArrayList<String> firmwareUpdate(String serial, YFirmwareFile firmware, byte[] settings, UpdateProgress progress) throws YAPI_Exception, InterruptedException;


    interface RequestAsyncResult {
        void RequestAsyncDone(Object context, byte[] result, int error, String errmsg);
    }

    abstract void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception;

    abstract byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body) throws YAPI_Exception;


    protected static class HTTPParams {

        private final String _host;
        private final int _port;
        private final String _user;
        private final String _pass;

        public HTTPParams(String url)
        {
            int pos = 0;
            if (url.startsWith("http://")) {
                pos = 7;
            }
            int end_auth = url.indexOf('@', pos);
            int end_user = url.indexOf(':', pos);
            if (end_auth >= 0 && end_user >= 0 && end_user < end_auth) {
                _user = url.substring(pos, end_user);
                _pass = url.substring(end_user + 1, end_auth);
                pos = end_auth + 1;
            } else {
                _user = "";
                _pass = "";
            }
            int end_url = url.indexOf('/', pos);
            if (end_url < 0) {
                end_url = url.length();
            }
            int portpos = url.indexOf(':', pos);
            if (portpos > 0 && portpos < end_url) {
                _host = url.substring(pos, portpos);
                _port = Integer.parseInt(url.substring(portpos + 1, end_url));
            } else {
                _host = url.substring(pos, end_url);
                _port = 4444;
            }
        }

        String getHost()
        {
            return _host;
        }

        String getPass()
        {
            return _pass;
        }

        int getPort()
        {
            return _port;
        }

        String geUser()
        {
            return _user;
        }

        public String getUrl()
        {
            StringBuilder url = new StringBuilder();
            if (!_user.equals("")) {
                url.append(_user);
                if (!_pass.equals("")) {
                    url.append(":");
                    url.append(_pass);
                }
                url.append("@");
            }
            url.append(_host);
            url.append(":");
            url.append(_pass);
            return url.toString();
        }
    }
}
