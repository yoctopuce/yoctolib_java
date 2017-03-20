package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class YHash
{

    private final HashMap<String, YDevice> _devs = new HashMap<>(); // hash table of devices, by serial number
    private final HashMap<String, String> _snByName = new HashMap<>(); // serial number for each device, by name
    private final HashMap<String, YFunctionType> _fnByType = new HashMap<>(2); // functions by type
    private final YAPIContext _yctx;

    public YHash(YAPIContext yctx)
    {
        _yctx = yctx;
    }

    void reset()
    {
        _fnByType.put("Module", new YFunctionType("Module", _yctx));
    }

    // Reindex a device in YAPI after a name change detected by device refresh
    synchronized void reindexDevice(YDevice dev)
    {
        String serial = dev.getSerialNumber();
        String lname = dev.getLogicalName();
        _devs.put(serial, dev);

        if (!lname.equals("")) {
            _snByName.put(lname, serial);
        }

        YFunctionType module = _fnByType.get("Module");
        YPEntry moduleYPEntry = dev.getModuleYPEntry();
        module.reindexFunction(moduleYPEntry);
        Collection<YPEntry> functions = dev.getFunctions();
        for (YPEntry yp : functions) {
            String classname = yp.getClassname();
            YFunctionType functionType = _fnByType.get(classname);
            if (functionType == null) {
                functionType = new YFunctionType(classname, _yctx);
                _fnByType.put(classname, functionType);
            }
            functionType.reindexFunction(yp);
        }
    }

    // Return a Device object for a specified URL, serial number or logical
    // device name
    // This function will not cause any network access
    synchronized YDevice getDevice(String device)
    {
        YDevice dev = null;
        // lookup by serial
        if (_devs.containsKey(device)) {
            dev = _devs.get(device);
        } else {
            // fallback to lookup by logical name
            if (_snByName.containsKey(device)) {
                String serial = _snByName.get(device);
                dev = _devs.get(serial);
            }
        }
        return dev;
    }


    // Remove a device from YAPI after an unplug detected by device refresh
    synchronized void forgetDevice(String serial)
    {
        YDevice dev = _devs.get(serial);
        if (dev == null) {
            return;
        }
        String lname = dev.getLogicalName();
        _devs.remove(serial);
        if (_snByName.containsKey(lname) && _snByName.get(lname).equals(serial)) {
            _snByName.remove(lname);
        }
        YFunctionType module = _fnByType.get("Module");
        module.forgetFunction(serial + ".module");
        Collection<YPEntry> functions = dev.getFunctions();
        for (YPEntry yp : functions) {
            YFunctionType functionType = _fnByType.get(yp.getClassname());
            if (functionType != null) {
                functionType.forgetFunction(yp.getHardwareId());
            }
        }
    }

    synchronized private YFunctionType getFnByType(String className)
    {
        if (!_fnByType.containsKey(className)) {
            _fnByType.put(className, new YFunctionType(className, _yctx));
        }
        return _fnByType.get(className);
    }

    // Find the best known identifier (hardware Id) for a given function
    synchronized YPEntry resolveFunction(String className, String func)
            throws YAPI_Exception
    {
        if (!YAPI._BaseType.containsKey(className)) {
            return getFnByType(className).getYPEntry(func);
        } else {
            // using an abstract baseType
            YPEntry.BaseClass baseType = YAPI._BaseType.get(className);
            for (YFunctionType subClassType : _fnByType.values()) {
                try {
                    YPEntry yp = subClassType.getYPEntry(func);
                    if (yp.matchBaseType(baseType)) {
                        return yp;
                    }
                } catch (YAPI_Exception ignore) {
                }
            }
        }
        throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "No function of type " + className + " found");
    }


    synchronized String resolveHwID(String className, String func) throws YAPI_Exception
    {
        return resolveFunction(className, func).getHardwareId();
    }


    synchronized String resolveFuncId(String className, String func) throws YAPI_Exception
    {
        return resolveFunction(className, func).getFuncId();
    }

    synchronized String resolveSerial(String className, String func) throws YAPI_Exception
    {
        return resolveFunction(className, func).getSerial();
    }


    // Retrieve a function object by hardware id, updating the indexes on the
    // fly if needed
    synchronized void setFunction(String className, String func, YFunction yfunc)
    {
        getFnByType(className).setFunction(func, yfunc);
    }

    // Retrieve a function object by hardware id, logicalname, updating the indexes on the
    // fly if needed
    synchronized YFunction getFunction(String className, String func)
    {

        return getFnByType(className).getFunction(func);
    }

    // Set a function advertised value by hardware id
    synchronized void setFunctionValue(String hwid, String pubval)
    {
        String classname = YAPIContext.functionClass(hwid);
        synchronized (this) {
            YFunctionType fnByType = getFnByType(classname);
            fnByType.setFunctionValue(hwid, pubval);
        }
    }


    // Find the hardwareId for the first instance of a given function class
    synchronized String getFirstHardwareId(String className)
    {

        if (!YAPI._BaseType.containsKey(className)) {
            YFunctionType ft = getFnByType(className);
            YPEntry yp = ft.getFirstYPEntry();
            if (yp == null)
                return null;
            return yp.getHardwareId();
        } else {
            // using an abstract baseType
            YPEntry.BaseClass baseType = YAPI._BaseType.get(className);
            for (YFunctionType subClassType : _fnByType.values()) {
                YPEntry yp = subClassType.getFirstYPEntry();
                if (yp != null && yp.matchBaseType(baseType)) {
                    return yp.getHardwareId();
                }
            }
            return null;
        }
    }

    // Find the hardwareId for the next instance of a given function class
    synchronized String getNextHardwareId(String className, String hwid)
    {
        if (!YAPI._BaseType.containsKey(className)) {
            YFunctionType ft = getFnByType(className);
            YPEntry yp = ft.getNextYPEntry(hwid);
            if (yp == null)
                return null;
            return yp.getHardwareId();
        } else {
            // enumeration of an abstract class
            YPEntry.BaseClass baseType = YAPI._BaseType.get(className);
            String prevclass = YAPIContext.functionClass(hwid);
            YPEntry res = getFnByType(prevclass).getNextYPEntry(hwid);
            if (res != null)
                return res.getHardwareId();
            for (String altClassName : _fnByType.keySet()) {
                if (!prevclass.equals("")) {
                    if (!altClassName.equals(prevclass))
                        continue;
                    prevclass = "";
                    continue;
                }
                YFunctionType functionType = _fnByType.get(altClassName);
                res = functionType.getFirstYPEntry();
                if (res != null && res.matchBaseType(baseType)) {
                    return res.getHardwareId();
                }
            }
            return null;
        }
    }

    synchronized public void reindexYellowPages(HashMap<String, ArrayList<YPEntry>> yellowPages)
    {
        // reindex all Yellow pages
        for (String classname : yellowPages.keySet()) {
            YFunctionType ftype = getFnByType(classname);
            ArrayList<YPEntry> ypEntries = yellowPages.get(classname);
            for (YPEntry yprec : ypEntries) {
                ftype.reindexFunction(yprec);
            }
        }
    }


    public void clear()
    {
        _devs.clear();
        _snByName.clear();
        _fnByType.clear();

    }
}
