/*********************************************************************
 *
 * $Id: YGenericHub.java 12426 2013-08-20 13:58:34Z seb $
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
