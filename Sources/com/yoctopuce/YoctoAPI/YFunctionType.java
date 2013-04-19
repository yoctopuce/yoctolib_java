/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.YoctoAPI;

//
import java.util.HashMap;

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
    private HashMap<String, YFunction> _connectedFns;           // functions requested and available, by Hardware Id
    private HashMap<String, YFunction> _requestedFns;           // functions requested but not yet known, by any type of name
    private HashMap<String, String> _hwIdByName;             // hash table of function Hardware Id by logical name
    private HashMap<String, String> _nameByHwId;             // hash table of function logical name by Hardware Id
    private HashMap<String, String> _valueByHwId;            // hash table of function advertised value by logical name

    public class HWID {

        public String module;
        public String function;

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
        _connectedFns = new HashMap<String, YFunction>();
        _requestedFns = new HashMap<String, YFunction>();
        _hwIdByName = new HashMap<String, String>();
        _nameByHwId = new HashMap<String, String>();
        _valueByHwId = new HashMap<String, String>();
    }

    // Index a single function given by HardwareId and logical name; store any advertised value
    // Return true iff there was a logical name discrepency
    public boolean reindexFunction(String hwid, String name, String val)
    {
        String currname = "";
        boolean res = false;

        if (_nameByHwId.containsKey(hwid)) {
            currname = _nameByHwId.get(hwid);
        }
        if (currname.equals("")) {

            if (!name.equals("")) {
                _nameByHwId.put(hwid, name);
                res = true;
            }
        } else if (!currname.equals(name)) {
            if (_hwIdByName.get(currname).equals(hwid)) {
                _hwIdByName.remove(currname);
            }
            if (!name.equals("")) {
                _nameByHwId.put(hwid, name);
            } else {
                _nameByHwId.remove(hwid);
            }
            res = true;
        }
        if (!name.equals("")) {
            _hwIdByName.put(name, hwid);
        }
        if (val != null) {
            _valueByHwId.put(hwid, val);
        } else {
            if (!_valueByHwId.containsKey(hwid)) {
                _valueByHwId.put(hwid, "");
            }
        }
        return res;
    }

    // Forget a disconnected function given by HardwareId
    public void forgetFunction(String hwid)
    {
        if (_nameByHwId.containsKey(hwid)) {
            String currname = _nameByHwId.get(hwid);
            if (!currname.equals("") && _hwIdByName.get(currname).equals(hwid)) {
                _hwIdByName.remove(currname);
            }
            _nameByHwId.remove(hwid);
        }
        if (_valueByHwId.containsKey(hwid)) {
            _valueByHwId.remove(hwid);
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
            // First case: func is the logicalname of a function
            if (_hwIdByName.containsKey(func)) {
                return new HWID(_hwIdByName.get(func));
            }

            // fallback to assuming that func is a logicalname or serial number of a module
            // with an implicit function name (like serial.module for instance)
            func += String.format(".%c%s", Character.toLowerCase(_className.charAt(0)), _className.substring(1));
        }

        // Second case: func is in the form: device_id.function_id
        HWID hwid = new HWID(func);
        // quick lookup for a known pure hardware id
        if (_valueByHwId.containsKey(hwid.toString())) {
            return hwid;
        }

        if(hwid.module.length()>0){

            // either the device id is a logical name, or the function is unknown
            YDevice dev = YAPI.getDevice(hwid.module);
            if (dev == null) {
                throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "Device [" + hwid.module + "] not online");
            }
            String serial = dev.getSerialNumber();
            hwid = new HWID(serial, hwid.function);
            if (_valueByHwId.containsKey(hwid.toString())) {
                return hwid;
            }


            // not found neither, may be funcid is a function logicalname
            int nfun = dev.functionCount();
            for (int i = 0; i < nfun; i++) {
                hwid = new HWID(serial ,dev.functionId(i));
                if (_nameByHwId.containsKey(hwid.toString())) {
                    String name = _nameByHwId.get(hwid.toString());
                    if (name.equals(hwid.function)) {
                        return hwid;
                    }
                }
            }
        }else {
            // only functionId  (ie ".tempearture")
            for(String hwid_str :_connectedFns.keySet()){
                HWID tmpid = new HWID(hwid_str);
                if(tmpid.function.equals(hwid.function)){
                    return tmpid;
                }
            }
        }
        throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "No function [" + hwid.function + "] found on device [" + hwid.module + "]");
    }

    // Find the exact Hardware Id of the specified function, if currently connected
    // If device is not known as connected, return a clean error
    // This function will not cause any network access
    public HWID friendlyName(String func) throws YAPI_Exception
    {

        HWID hwid = resolve(func);
        if (_className.equals("Module")) {
            String module = hwid.module;
            if (_nameByHwId.containsKey(hwid.toString())) {
                module = _nameByHwId.get(hwid.toString());
            }
            return new HWID(module ,"module");
        } else {
            YFunctionType mod_ftype = YAPI.getFnByType("Module");
            HWID modId = mod_ftype.friendlyName(hwid.module);
            String function= hwid.function;
            if (_nameByHwId.containsKey(hwid.toString())) {
                function = _nameByHwId.get(hwid.toString());
            }
            return new HWID(modId.module, function);
        }

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
            if (_connectedFns.containsKey(hwid)) {
                return _connectedFns.get(hwid);
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

    // Retrieve a function advertised value by hardware id
    public void setFunctionValue(String hwid, String pubval)
    {
        if (_connectedFns.containsKey(hwid)) {
            YFunction conn_fn = _connectedFns.get(hwid);

            if (conn_fn.hasCallbackRegistered()) {
                if (!_valueByHwId.containsKey(hwid)
                        || !_valueByHwId.get(hwid).equals(pubval)) {
                    YAPI.addValueEvent(conn_fn, pubval);
                }
            }
        }
        _valueByHwId.put(hwid, pubval);
    }

    // Retrieve a function advertised value by hardware id
    public String getFunctionValue(String hwid)
    {
        return _valueByHwId.get(hwid);
    }

    // Find the the hardwareId of the first instance of a given function class
    public String getFirstHardwareId()
    {
        for (String key : _valueByHwId.keySet()) {
            return key;
        }
        return null;
    }

    // Find the hardwareId for the next instance of a given function class
    public String getNextHardwareId(String hwid)
    {
        for (String iter_hwid : _valueByHwId.keySet()) {
            if (hwid.equals("!")) {
                return iter_hwid;
            }
            if (hwid.equals(iter_hwid)) {
                hwid = "!";
            }
        }
        return null; // no more instance found
    }
}
