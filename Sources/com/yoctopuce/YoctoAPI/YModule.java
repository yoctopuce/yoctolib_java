/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.YoctoAPI;



public class YModule extends YFunction {


    // --- (generated code: definitions)
    /**
     * invalid productName value
     */
    public static final String PRODUCTNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid serialNumber value
     */
    public static final String SERIALNUMBER_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid productId value
     */
    public static final int PRODUCTID_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid productRelease value
     */
    public static final int PRODUCTRELEASE_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid firmwareRelease value
     */
    public static final String FIRMWARERELEASE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid persistentSettings value
     */
  public static final int PERSISTENTSETTINGS_LOADED = 0;
  public static final int PERSISTENTSETTINGS_SAVED = 1;
  public static final int PERSISTENTSETTINGS_MODIFIED = 2;
  public static final int PERSISTENTSETTINGS_INVALID = -1;

    /**
     * invalid luminosity value
     */
    public static final int LUMINOSITY_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid beacon value
     */
  public static final int BEACON_OFF = 0;
  public static final int BEACON_ON = 1;
  public static final int BEACON_INVALID = -1;

    /**
     * invalid upTime value
     */
    public static final long UPTIME_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid usbCurrent value
     */
    public static final long USBCURRENT_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid rebootCountdown value
     */
    public static final int REBOOTCOUNTDOWN_INVALID = YAPI.INVALID_INT;
    /**
     * invalid usbBandwidth value
     */
  public static final int USBBANDWIDTH_SIMPLE = 0;
  public static final int USBBANDWIDTH_DOUBLE = 1;
  public static final int USBBANDWIDTH_INVALID = -1;

    //--- (end of generated code: definitions)


    // Return the internal device object hosting the function
    protected YDevice _getDev() throws YAPI_Exception
    {
        String devid = _func;
        int dotidx = devid.indexOf('.');
        if( dotidx>=0) devid = devid.substring(0, dotidx);
        YDevice dev = YAPI.getDevice(devid);
        if(dev==null) {
            throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "Device ["+devid+"] is not online");
        }
        return dev;
    }

    // Return the number of functions (beside "module") available on the device
    public int functionCount() throws YAPI_Exception
    {
        YDevice dev = _getDev();
        return dev.functionCount();
    }

    // Retrieve the Hardware Id of the nth function (beside "module") in the device
    public String functionId(int functionIndex) throws YAPI_Exception
    {
        YDevice dev = _getDev();
        return dev.functionId(functionIndex);
    }
    // Retrieve the name of the nth function (beside "module") in the device
    public String functionName(int functionIndex) throws YAPI_Exception
    {
        YDevice dev = _getDev();
        return dev.functionName(functionIndex);
    }

    // Retrieve the advertised value of the nth function (beside "module") in the device
    public String functionValue(int functionIndex) throws YAPI_Exception
    {
        YDevice dev = _getDev();
        return dev.functionValue(functionIndex);
    }


    // --- (generated code: YModule implementation)

    /**
     * Returns the commercial name of the module, as set by the factory.
     * 
     * @return a string corresponding to the commercial name of the module, as set by the factory
     * 
     * @throws YAPI_Exception
     */
    public String get_productName()  throws YAPI_Exception
    {
        YDevice dev = _getDev();
        if (dev == null)
            return PRODUCTNAME_INVALID;
        return dev.getProductName();
    }

    /**
     * Returns the commercial name of the module, as set by the factory.
     * 
     * @return a string corresponding to the commercial name of the module, as set by the factory
     * 
     * @throws YAPI_Exception
     */
    public String getProductName() throws YAPI_Exception

    { return get_productName(); }

    /**
     * Returns the serial number of the module, as set by the factory.
     * 
     * @return a string corresponding to the serial number of the module, as set by the factory
     * 
     * @throws YAPI_Exception
     */
    public String get_serialNumber()  throws YAPI_Exception
    {
        YDevice dev = _getDev();
        if (dev == null)
            return SERIALNUMBER_INVALID;
        return dev.getSerialNumber();
    }

    /**
     * Returns the serial number of the module, as set by the factory.
     * 
     * @return a string corresponding to the serial number of the module, as set by the factory
     * 
     * @throws YAPI_Exception
     */
    public String getSerialNumber() throws YAPI_Exception

    { return get_serialNumber(); }

    /**
     * Returns the logical name of the module.
     * 
     * @return a string corresponding to the logical name of the module
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        YDevice dev = _getDev();
        if(dev!=null && _expiration <= YAPI.GetTickCount())
            return dev.getLogicalName();
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the module.
     * 
     * @return a string corresponding to the logical name of the module
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the module. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the module
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_logicalName( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("logicalName",rest_val);
        YDevice dev = _getDev();
        if(dev!=null) dev.refresh();
        return YAPI.SUCCESS;
    }

    /**
     * Changes the logical name of the module. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the module
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the USB device identifier of the module.
     * 
     * @return an integer corresponding to the USB device identifier of the module
     * 
     * @throws YAPI_Exception
     */
    public int get_productId()  throws YAPI_Exception
    {
        YDevice dev = _getDev();
        if (dev == null)
            return PRODUCTID_INVALID;
        return dev.getProductId();
    }

    /**
     * Returns the USB device identifier of the module.
     * 
     * @return an integer corresponding to the USB device identifier of the module
     * 
     * @throws YAPI_Exception
     */
    public int getProductId() throws YAPI_Exception

    { return get_productId(); }

    /**
     * Returns the hardware release version of the module.
     * 
     * @return an integer corresponding to the hardware release version of the module
     * 
     * @throws YAPI_Exception
     */
    public int get_productRelease()  throws YAPI_Exception
    {
        String json_val = (String) _getFixedAttr("productRelease");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the hardware release version of the module.
     * 
     * @return an integer corresponding to the hardware release version of the module
     * 
     * @throws YAPI_Exception
     */
    public int getProductRelease() throws YAPI_Exception

    { return get_productRelease(); }

    /**
     * Returns the version of the firmware embedded in the module.
     * 
     * @return a string corresponding to the version of the firmware embedded in the module
     * 
     * @throws YAPI_Exception
     */
    public String get_firmwareRelease()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("firmwareRelease");
        return json_val;
    }

    /**
     * Returns the version of the firmware embedded in the module.
     * 
     * @return a string corresponding to the version of the firmware embedded in the module
     * 
     * @throws YAPI_Exception
     */
    public String getFirmwareRelease() throws YAPI_Exception

    { return get_firmwareRelease(); }

    /**
     * Returns the current state of persistent module settings.
     * 
     * @return a value among YModule.PERSISTENTSETTINGS_LOADED, YModule.PERSISTENTSETTINGS_SAVED and
     * YModule.PERSISTENTSETTINGS_MODIFIED corresponding to the current state of persistent module settings
     * 
     * @throws YAPI_Exception
     */
    public int get_persistentSettings()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("persistentSettings");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current state of persistent module settings.
     * 
     * @return a value among Y_PERSISTENTSETTINGS_LOADED, Y_PERSISTENTSETTINGS_SAVED and
     * Y_PERSISTENTSETTINGS_MODIFIED corresponding to the current state of persistent module settings
     * 
     * @throws YAPI_Exception
     */
    public int getPersistentSettings() throws YAPI_Exception

    { return get_persistentSettings(); }

    public int set_persistentSettings( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("persistentSettings",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPersistentSettings( int newval)  throws YAPI_Exception

    { return set_persistentSettings(newval); }

    /**
     * Saves current settings in the nonvolatile memory of the module.
     * Warning: the number of allowed save operations during a module life is
     * limited (about 100000 cycles). Do not call this function within a loop.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int saveToFlash()  throws YAPI_Exception
    {
        String rest_val;
        rest_val = "1";
        _setAttr("persistentSettings",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Reloads the settings stored in the nonvolatile memory, as
     * when the module is powered on.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int revertFromFlash()  throws YAPI_Exception
    {
        String rest_val;
        rest_val = "0";
        _setAttr("persistentSettings",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     * 
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     * 
     * @throws YAPI_Exception
     */
    public int get_luminosity()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("luminosity");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     * 
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     * 
     * @throws YAPI_Exception
     */
    public int getLuminosity() throws YAPI_Exception

    { return get_luminosity(); }

    /**
     * Changes the luminosity of the module informative leds. The parameter is a
     * value between 0 and 100.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : an integer corresponding to the luminosity of the module informative leds
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_luminosity( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("luminosity",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the luminosity of the module informative leds. The parameter is a
     * value between 0 and 100.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : an integer corresponding to the luminosity of the module informative leds
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLuminosity( int newval)  throws YAPI_Exception

    { return set_luminosity(newval); }

    /**
     * Returns the state of the localization beacon.
     * 
     * @return either YModule.BEACON_OFF or YModule.BEACON_ON, according to the state of the localization beacon
     * 
     * @throws YAPI_Exception
     */
    public int get_beacon()  throws YAPI_Exception
    {
        YDevice dev = _getDev();
        if(dev!=null && _expiration <= YAPI.GetTickCount())
            return dev.getBeacon();
        String json_val = (String) _getAttr("beacon");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the state of the localization beacon.
     * 
     * @return either Y_BEACON_OFF or Y_BEACON_ON, according to the state of the localization beacon
     * 
     * @throws YAPI_Exception
     */
    public int getBeacon() throws YAPI_Exception

    { return get_beacon(); }

    /**
     * Turns on or off the module localization beacon.
     * 
     * @param newval : either YModule.BEACON_OFF or YModule.BEACON_ON
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_beacon( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("beacon",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Turns on or off the module localization beacon.
     * 
     * @param newval : either Y_BEACON_OFF or Y_BEACON_ON
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setBeacon( int newval)  throws YAPI_Exception

    { return set_beacon(newval); }

    /**
     * Returns the number of milliseconds spent since the module was powered on.
     * 
     * @return an integer corresponding to the number of milliseconds spent since the module was powered on
     * 
     * @throws YAPI_Exception
     */
    public long get_upTime()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("upTime");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the number of milliseconds spent since the module was powered on.
     * 
     * @return an integer corresponding to the number of milliseconds spent since the module was powered on
     * 
     * @throws YAPI_Exception
     */
    public long getUpTime() throws YAPI_Exception

    { return get_upTime(); }

    /**
     * Returns the current consumed by the module on the USB bus, in milli-amps.
     * 
     * @return an integer corresponding to the current consumed by the module on the USB bus, in milli-amps
     * 
     * @throws YAPI_Exception
     */
    public long get_usbCurrent()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("usbCurrent");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current consumed by the module on the USB bus, in milli-amps.
     * 
     * @return an integer corresponding to the current consumed by the module on the USB bus, in milli-amps
     * 
     * @throws YAPI_Exception
     */
    public long getUsbCurrent() throws YAPI_Exception

    { return get_usbCurrent(); }

    /**
     * Returns the remaining number of seconds before the module restarts, or zero when no
     * reboot has been scheduled.
     * 
     * @return an integer corresponding to the remaining number of seconds before the module restarts, or zero when no
     *         reboot has been scheduled
     * 
     * @throws YAPI_Exception
     */
    public int get_rebootCountdown()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("rebootCountdown");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the remaining number of seconds before the module restarts, or zero when no
     * reboot has been scheduled.
     * 
     * @return an integer corresponding to the remaining number of seconds before the module restarts, or zero when no
     *         reboot has been scheduled
     * 
     * @throws YAPI_Exception
     */
    public int getRebootCountdown() throws YAPI_Exception

    { return get_rebootCountdown(); }

    public int set_rebootCountdown( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("rebootCountdown",rest_val);
        return YAPI.SUCCESS;
    }

    public int setRebootCountdown( int newval)  throws YAPI_Exception

    { return set_rebootCountdown(newval); }

    /**
     * Schedules a simple module reboot after the given number of seconds.
     * 
     * @param secBeforeReboot : number of seconds before rebooting
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int reboot(int secBeforeReboot)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(secBeforeReboot);
        _setAttr("rebootCountdown",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Schedules a module reboot into special firmware update mode.
     * 
     * @param secBeforeReboot : number of seconds before rebooting
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int triggerFirmwareUpdate(int secBeforeReboot)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(-secBeforeReboot);
        _setAttr("rebootCountdown",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the number of USB interfaces used by the module.
     * 
     * @return either YModule.USBBANDWIDTH_SIMPLE or YModule.USBBANDWIDTH_DOUBLE, according to the number
     * of USB interfaces used by the module
     * 
     * @throws YAPI_Exception
     */
    public int get_usbBandwidth()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("usbBandwidth");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the number of USB interfaces used by the module.
     * 
     * @return either Y_USBBANDWIDTH_SIMPLE or Y_USBBANDWIDTH_DOUBLE, according to the number of USB
     * interfaces used by the module
     * 
     * @throws YAPI_Exception
     */
    public int getUsbBandwidth() throws YAPI_Exception

    { return get_usbBandwidth(); }

    /**
     * Changes the number of USB interfaces used by the module. You must reboot the module
     * after changing this setting.
     * 
     * @param newval : either YModule.USBBANDWIDTH_SIMPLE or YModule.USBBANDWIDTH_DOUBLE, according to the
     * number of USB interfaces used by the module
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_usbBandwidth( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("usbBandwidth",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of USB interfaces used by the module. You must reboot the module
     * after changing this setting.
     * 
     * @param newval : either Y_USBBANDWIDTH_SIMPLE or Y_USBBANDWIDTH_DOUBLE, according to the number of
     * USB interfaces used by the module
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setUsbBandwidth( int newval)  throws YAPI_Exception

    { return set_usbBandwidth(newval); }

    /**
     * Downloads the specified built-in file and returns a binary buffer with its content.
     * 
     * @param pathname : name of the new file to load
     * 
     * @return a binary buffer with the file content
     * 
     * @throws YAPI_Exception
     */
    public byte[] download(String pathname)  throws YAPI_Exception
    {
        return _download(pathname);
        
    }

    /**
     * Returns the icon of the module. The icon is a PNG image and does not
     * exceeds 1536 bytes.
     * 
     * @return a binary buffer with module icon, in png format.
     */
    public byte[] get_icon2d()  throws YAPI_Exception
    {
        return _download("icon2d.png");
        
    }

    /**
     * Continues the module enumeration started using yFirstModule().
     * 
     * @return a pointer to a YModule object, corresponding to
     *         the next module found, or a null pointer
     *         if there are no more modules to enumerate.
     */
    public  YModule nextModule()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindModule(next_hwid);
    }

    /**
     * Allows you to find a module from its serial number or from its logical name.
     * 
     * This function does not require that the module is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YModule.isOnline() to test if the module is
     * indeed online at a given time. In case of ambiguity when looking for
     * a module by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string containing either the serial number or
     *         the logical name of the desired module
     * 
     * @return a YModule object allowing you to drive the module
     *         or get additional information on the module.
     */
    public static YModule FindModule(String func)
    {   YFunction yfunc = YAPI.getFunction("Module", func);
        if (yfunc != null) {
            return (YModule) yfunc;
        }
        return new YModule(func);
    }

    /**
     * Starts the enumeration of modules currently accessible.
     * Use the method YModule.nextModule() to iterate on the
     * next modules.
     * 
     * @return a pointer to a YModule object, corresponding to
     *         the first module currently online, or a null pointer
     *         if there are none.
     */
    public static YModule FirstModule()
    {
        String next_hwid = YAPI.getFirstHardwareId("Module");
        if (next_hwid == null)  return null;
        return FindModule(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YModule(String func)
    {
        super("Module", func);
    }


    //--- (end of generated code: YModule implementation)

    
}
