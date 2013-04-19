/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;
import java.util.HashMap;

import com.yoctopuce.YoctoAPI.YAPI.PlugEvent.Event;

abstract class YGenericHub {

    protected int _hubidx;
    protected long _notifyTrigger = 0;
    protected Object _notifyHandle = null;
    protected long _devListValidity = 500;
    protected long _devListExpires = 0;
    protected HashMap<Integer, String> _serialByYdx = new HashMap<Integer, String>();
    protected HashMap<String, YDevice> _devices = new HashMap< String, YDevice>();
    //protected ArrayList<WPEntry> wpages = new ArrayList<WPEntry>();

    public YGenericHub(int idx)
    {
        _hubidx = idx;
    }

    abstract public void release();
    
    abstract public String getRootUrl();
    abstract public boolean isSameRootUrl(String url);

    abstract public void startNotifications() throws YAPI_Exception;

    abstract public void stopNotifications();

    protected void updateFromWpAndYp(ArrayList<WPEntry> whitePages, HashMap<String, ArrayList<YPEntry>> yellowPages) throws YAPI_Exception
    {
        // by default consider all known device as unpluged
        ArrayList<YDevice> toRemove = new ArrayList<YDevice>(_devices.values());
 
        for (WPEntry wp : whitePages) {
            String serial = wp.getSerialNumber();
            if (_devices.containsKey(serial)) {
                // allready there
                YDevice currdev = _devices.get(serial);
                if (!currdev.getLogicalName().equals(wp.getLogicalName())) {
                    // Reindex device from its own data
                    currdev.refresh();
                    YAPI.pushPlugEvent(Event.CHANGE, serial);
                } else if (currdev.getBeacon() > 0 != wp.getBeacon() > 0) {
                    currdev.refresh();
                }
                toRemove.remove(currdev);
            } else {
                YDevice dev = new YDevice(this, wp, yellowPages);
                _devices.put(serial, dev);
                YAPI.pushPlugEvent(Event.PLUG, serial);
            	YAPI.Log("HUB: device "+serial+" has been pluged\n");
            }
        }
        
        for (YDevice dev : toRemove) {
        	String serial = dev.getSerialNumber();
            YAPI.pushPlugEvent(Event.UNPLUG, serial);
        	YAPI.Log("HUB: device "+serial+" has been unpluged\n");
        	_devices.remove(serial);
        }
    }

    abstract void updateDeviceList(boolean forceupdate) throws YAPI_Exception;

    abstract public byte[] devRequest(YDevice device,String req_first_line,byte[] req_head_and_body, Boolean async) throws YAPI_Exception;
}
