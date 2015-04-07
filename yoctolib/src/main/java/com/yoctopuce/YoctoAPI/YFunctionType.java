/*********************************************************************
 *
 * $Id: YFunctionType.java 19328 2015-02-17 17:30:45Z seb $
 *
 * Internal YFunctionType object
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

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;


// YFunctionType Class (used internally)
//
// Instances of this class stores everything we know about a given type of function:
// Mapping between function logical names and Hardware ID as discovered on hubs,
// and existing instances of YFunction (either already connected or simply requested).
// To keep it simple, this implementation separates completely the name resolution
// mechanism, implemented using the yellow pages, and the storage and retrieval of
// existing YFunction instances.
//
class YFunctionType {

    // private attributes, to be used within yocto_api only
    private String _className;
    private HashMap<String, YPEntry> _ypEntries = new HashMap<String, YPEntry>();               // Yellow page by Hardware Id
    private HashMap<String, YFunction> _connectedFns = new HashMap<String, YFunction>();           // functions requested and available, by Hardware Id
    private HashMap<String, YFunction> _requestedFns = new HashMap<String, YFunction>();           // functions requested but not yet known, by any type of name
    private HashMap<String, String> _hwIdByName = new HashMap<String, String>();                // hash table of function Hardware Id by logical name


    // class used to store module.function (can be serial or logical name)
    public static class HWID {

        private final String module;
        private final String function;

        public String getModule() {
            return module;
        }

        public String getFunction() {
            return function;
        }

        public HWID(String module, String function)
        {
            this.module = module;
            this.function = function;
        }

        public HWID(String full_hwid)
        {
            int pos = full_hwid.indexOf('.');
            this.module = full_hwid.substring(0, pos);
            this.function = full_hwid.substring(pos + 1);
        }


        @Override
        public String toString()
        {
            return module + "." + function;
        }


    }

    public YFunctionType(String classname)
    {
        _className = classname;
    }


    public void reindexFunction(YPEntry yp)
    {
        String oldLogicalName = "";

        String hardwareId = yp.getHardwareId();

        if (_ypEntries.containsKey(hardwareId)) {
            oldLogicalName = _ypEntries.get(hardwareId).getLogicalName();
        }

        if (!oldLogicalName.equals("") && !oldLogicalName.equals(yp.getLogicalName())) {
            if (_hwIdByName.get(oldLogicalName).equals(hardwareId)) {
                _hwIdByName.remove(oldLogicalName);
            }
        }
        if (!yp.getLogicalName().equals("")) {
            _hwIdByName.put(yp.getLogicalName(), hardwareId);
        }
        _ypEntries.put(yp.getHardwareId(), yp);
    }


    // Forget a disconnected function given by HardwareId
    public void forgetFunction(String hwid)
    {
        if (_ypEntries.containsKey(hwid)) {
            String currname = _ypEntries.get(hwid).getLogicalName();
            if (!currname.equals("") && _hwIdByName.get(currname).equals(hwid)) {
                _hwIdByName.remove(currname);
            }
            _ypEntries.remove(hwid);
        }
    }

    // Find the exact Hardware Id of the specified function, if currently connected
    // If device is not known as connected, return a clean error
    // This function will not cause any network access


    public HWID resolve(String func) throws YAPI_Exception
    {
        // Try to resolve str_func to a known Function instance, if possible, without any device access
        int dotpos = func.indexOf('.');
        if (dotpos < 0) {
            // First case: func is the logical name of a function
            if (_hwIdByName.containsKey(func)) {
                String hwid = _hwIdByName.get(func);
                return new HWID(hwid);
            }

            // fallback to assuming that func is a logical name or serial number of a module
            // with an implicit function name (like serial.module for instance)
            func += String.format(".%c%s", Character.toLowerCase(_className.charAt(0)), _className.substring(1));
        }

        // Second case: func is in the form: device_id.function_id
        HWID hwid = new HWID(func);
        // quick lookup for a known pure hardware id
        if (_ypEntries.containsKey(hwid.toString())) {
            return hwid;
        }

        if (hwid.module.length() > 0) {

            // either the device id is a logical name, or the function is unknown
            YDevice dev = SafeYAPI().getDevice(hwid.module);
            if (dev == null) {
                throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "Device [" + hwid.module + "] not online");
            }
            String serial = dev.getSerialNumber();
            hwid = new HWID(serial, hwid.getFunction());
            if (_ypEntries.containsKey(hwid.toString())) {
                return hwid;
            }
            // not found neither, may be funcid is a function logicalname
            int nfun = dev.functionCount();
            for (int i = 0; i < nfun; i++) {
                YPEntry yp = dev.getYPEntry(i);
                if (yp.getLogicalName().equals(hwid.getFunction()) && _ypEntries.containsValue(yp)) {
                    return new HWID(serial, yp.getFuncId());
                }
            }
        } else {
            // only functionId  (ie ".temperature")
            for (String hwid_str : _connectedFns.keySet()) {
                HWID tmpid = new HWID(hwid_str);
                if (tmpid.getFunction().equals(hwid.getFunction())) {
                    return tmpid;
                }
            }
        }
        throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "No function [" + hwid.function + "] found on device [" + hwid.module + "]");
    }


    // Retrieve a function object by hardware id, updating the indexes on the fly if needed
    public void setFunction(String func, YFunction yfunc)
    {
        HWID hwid;
        try {
            hwid = resolve(func);
            _connectedFns.put(hwid.toString(), yfunc);
        } catch (YAPI_Exception ex) {
            _requestedFns.put(func, yfunc);
        }
    }

    // Retrieve a function object by hardware id, updating the indexes on the fly if needed
    public YFunction getFunction(String func)
    {
        try {
            HWID hwid = resolve(func);
            // the function has been located on a device
            if (_connectedFns.containsKey(hwid.toString())) {
                return _connectedFns.get(hwid.toString());
            }

            if (_requestedFns.containsKey(func)) {
                YFunction req_fn = _requestedFns.get(func);
                _connectedFns.put(hwid.toString(), req_fn);
                _requestedFns.remove(func);
                return req_fn;
            }

        } catch (YAPI_Exception ex) {
            // the function is still abstract
            if (_requestedFns.containsKey(func)) {
                return _requestedFns.get(func);
            }
        }
        return null;
    }

    public YPEntry getYPEntry(String func) throws YAPI_Exception
    {
        HWID hwid = resolve(func);
        return _ypEntries.get(hwid.toString());
    }


    // Retrieve a function advertised value by hardware id
    public void setFunctionValue(String hwid, String pubval)
    {

        YPEntry yp = _ypEntries.get(hwid);
        if (yp == null)
            // device has not been correctly registered
            return;
        if (yp.getAdvertisedValue().equals(pubval)) {
            return;
        }
        yp.setAdvertisedValue(pubval);
        YFunction conn_fn = SafeYAPI()._GetValueCallback(hwid);
        if (conn_fn != null) {
            SafeYAPI()._PushDataEvent(new YAPI.DataEvent(conn_fn, pubval));
        }
    }

    // Stores a function timed value by hardware id, queue an event if needed
    public void setTimedReport(String hwid, double timestamp, ArrayList<Integer> report)
    {
        YFunction func = SafeYAPI()._GetTimedReportCallback(hwid);
        if (func != null) {
            SafeYAPI()._PushDataEvent(new YAPI.DataEvent(func, timestamp, report));
        }
    }

    // Find the the hardwareId of the first instance of a given function class
    public YPEntry getFirstYPEntry()
    {
        for (String key : _ypEntries.keySet()) {
            return _ypEntries.get(key);
        }
        return null;
    }

    // Find the hardwareId for the next instance of a given function class
    public YPEntry getNextYPEntry(String hwid)
    {
        boolean found = false;
        for (String iter_hwid : _ypEntries.keySet()) {
            if (found) {
                return _ypEntries.get(iter_hwid);
            }
            if (hwid.equals(iter_hwid)) {
                found = true;
            }
        }
        return null; // no more instance found
    }


}
