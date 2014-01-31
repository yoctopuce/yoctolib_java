/*********************************************************************
 *
 * $Id: YModule.java 14779 2014-01-30 14:56:39Z seb $
 *
 * YModule Class: Module control interface
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
import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;
import org.json.JSONException;
import org.json.JSONObject;

//--- (generated code: YModule class start)
/**
 * YModule Class: Module control interface
 * 
 * This interface is identical for all Yoctopuce USB modules.
 * It can be used to control the module global parameters, and
 * to enumerate the functions provided by each module.
 */
public class YModule extends YFunction
{
//--- (end of generated code: YModule class start)


    // --- (generated code: YModule definitions)
    /**
     * invalid productName value
     */
    public static final String PRODUCTNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid serialNumber value
     */
    public static final String SERIALNUMBER_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid productId value
     */
    public static final int PRODUCTID_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid productRelease value
     */
    public static final int PRODUCTRELEASE_INVALID = YAPI.INVALID_UINT;
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
    public static final int LUMINOSITY_INVALID = YAPI.INVALID_UINT;
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
    public static final int USBCURRENT_INVALID = YAPI.INVALID_UINT;
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

    protected String _productName = PRODUCTNAME_INVALID;
    protected String _serialNumber = SERIALNUMBER_INVALID;
    protected int _productId = PRODUCTID_INVALID;
    protected int _productRelease = PRODUCTRELEASE_INVALID;
    protected String _firmwareRelease = FIRMWARERELEASE_INVALID;
    protected int _persistentSettings = PERSISTENTSETTINGS_INVALID;
    protected int _luminosity = LUMINOSITY_INVALID;
    protected int _beacon = BEACON_INVALID;
    protected long _upTime = UPTIME_INVALID;
    protected int _usbCurrent = USBCURRENT_INVALID;
    protected int _rebootCountdown = REBOOTCOUNTDOWN_INVALID;
    protected int _usbBandwidth = USBBANDWIDTH_INVALID;
    protected UpdateCallback _valueCallbackModule = null;

    /**
     * Deprecated UpdateCallback for Module
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YModule function, String functionValue);
    }

    /**
     * TimedReportCallback for Module
     */
    public interface TimedReportCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YModule  function, YMeasure measure);
    }
    //--- (end of generated code: YModule definitions)


    // Return the internal device object hosting the function
    protected YDevice _getDev() throws YAPI_Exception
    {
        String devid = _func;
        int dotidx = devid.indexOf('.');
        if( dotidx>=0) devid = devid.substring(0, dotidx);
        YDevice dev = SafeYAPI().getDevice(devid);
        if(dev==null) {
            throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "Device ["+devid+"] is not online");
        }
        return dev;
    }

    /**
     * @param func : functionid
     */
    protected YModule(String func)
    {
        super(func);
        _className = "Module";
        //--- (generated code: YModule attributes initialization)
        //--- (end of generated code: YModule attributes initialization)
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
        return dev.getYPEntry(functionIndex).getFuncId();
    }
    // Retrieve the name of the nth function (beside "module") in the device
    public String functionName(int functionIndex) throws YAPI_Exception
    {
        YDevice dev = _getDev();
        return dev.getYPEntry(functionIndex).getLogicalName();
    }

    // Retrieve the advertised value of the nth function (beside "module") in the device
    public String functionValue(int functionIndex) throws YAPI_Exception
    {
        YDevice dev = _getDev();
        return dev.getYPEntry(functionIndex).getAdvertisedValue();
    }


    // --- (generated code: YModule implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("productName")) {
            _productName =  json_val.getString("productName"); ;
        }
        if (json_val.has("serialNumber")) {
            _serialNumber =  json_val.getString("serialNumber"); ;
        }
        if (json_val.has("productId")) {
            _productId =  json_val.getInt("productId");
        }
        if (json_val.has("productRelease")) {
            _productRelease =  json_val.getInt("productRelease");
        }
        if (json_val.has("firmwareRelease")) {
            _firmwareRelease =  json_val.getString("firmwareRelease"); ;
        }
        if (json_val.has("persistentSettings")) {
            _persistentSettings =  json_val.getInt("persistentSettings");
        }
        if (json_val.has("luminosity")) {
            _luminosity =  json_val.getInt("luminosity");
        }
        if (json_val.has("beacon")) {
            _beacon =  json_val.getInt("beacon")>0?1:0;
        }
        if (json_val.has("upTime")) {
            _upTime =  json_val.getLong("upTime");
        }
        if (json_val.has("usbCurrent")) {
            _usbCurrent =  json_val.getInt("usbCurrent");
        }
        if (json_val.has("rebootCountdown")) {
            _rebootCountdown =  json_val.getInt("rebootCountdown");
        }
        if (json_val.has("usbBandwidth")) {
            _usbBandwidth =  json_val.getInt("usbBandwidth");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the commercial name of the module, as set by the factory.
     * 
     * @return a string corresponding to the commercial name of the module, as set by the factory
     * 
     * @throws YAPI_Exception
     */
    public String get_productName()  throws YAPI_Exception
    {
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PRODUCTNAME_INVALID;
            }
        }
        return _productName;
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
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SERIALNUMBER_INVALID;
            }
        }
        return _serialNumber;
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
     * Returns the USB device identifier of the module.
     * 
     * @return an integer corresponding to the USB device identifier of the module
     * 
     * @throws YAPI_Exception
     */
    public int get_productId()  throws YAPI_Exception
    {
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PRODUCTID_INVALID;
            }
        }
        return _productId;
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
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PRODUCTRELEASE_INVALID;
            }
        }
        return _productRelease;
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
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return FIRMWARERELEASE_INVALID;
            }
        }
        return _firmwareRelease;
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
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PERSISTENTSETTINGS_INVALID;
            }
        }
        return _persistentSettings;
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

    public int set_persistentSettings(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("persistentSettings",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPersistentSettings(int newval)  throws YAPI_Exception

    { return set_persistentSettings(newval); }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     * 
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     * 
     * @throws YAPI_Exception
     */
    public int get_luminosity()  throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LUMINOSITY_INVALID;
            }
        }
        return _luminosity;
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
    public int set_luminosity(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
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
    public int setLuminosity(int newval)  throws YAPI_Exception

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
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return BEACON_INVALID;
            }
        }
        return _beacon;
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
    public int set_beacon(int  newval)  throws YAPI_Exception
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
    public int setBeacon(int newval)  throws YAPI_Exception

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
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return UPTIME_INVALID;
            }
        }
        return _upTime;
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
    public int get_usbCurrent()  throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return USBCURRENT_INVALID;
            }
        }
        return _usbCurrent;
    }

    /**
     * Returns the current consumed by the module on the USB bus, in milli-amps.
     * 
     * @return an integer corresponding to the current consumed by the module on the USB bus, in milli-amps
     * 
     * @throws YAPI_Exception
     */
    public int getUsbCurrent() throws YAPI_Exception

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
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return REBOOTCOUNTDOWN_INVALID;
            }
        }
        return _rebootCountdown;
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

    public int set_rebootCountdown(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("rebootCountdown",rest_val);
        return YAPI.SUCCESS;
    }

    public int setRebootCountdown(int newval)  throws YAPI_Exception

    { return set_rebootCountdown(newval); }

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
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return USBBANDWIDTH_INVALID;
            }
        }
        return _usbBandwidth;
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
    public int set_usbBandwidth(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
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
    public int setUsbBandwidth(int newval)  throws YAPI_Exception

    { return set_usbBandwidth(newval); }

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
    {
        YModule obj;
        obj = (YModule) YFunction._FindFromCache("Module", func);
        if (obj == null) {
            obj = new YModule(func);
            YFunction._AddToCache("Module", func, obj);
        }
        return obj;
    }

    /**
     * Registers the callback function that is invoked on every change of advertised value.
     * The callback is invoked only during the execution of ySleep or yHandleEvents.
     * This provides control over the time when the callback is triggered. For good responsiveness, remember to call
     * one of these two functions periodically. To unregister a callback, pass a null pointer as argument.
     * 
     * @param callback : the callback function to call, or a null pointer. The callback function should take two
     *         arguments: the function object of which the value has changed, and the character string describing
     *         the new advertised value.
     * @noreturn
     */
    public int registerValueCallback(UpdateCallback callback)
    {
        String val;
        if (callback != null) {
            YFunction._UpdateValueCallbackList(this, true);
        } else {
            YFunction._UpdateValueCallbackList(this, false);
        }
        _valueCallbackModule = callback;
        // Immediately invoke value callback with current value
        if (callback != null && isOnline()) {
            val = _advertisedValue;
            if (!(val.equals(""))) {
                _invokeValueCallback(val);
            }
        }
        return 0;
    }

    @Override
    public int _invokeValueCallback(String value)
    {
        if (_valueCallbackModule != null) {
            _valueCallbackModule.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Saves current settings in the nonvolatile memory of the module.
     * Warning: the number of allowed save operations during a module life is
     * limited (about 100000 cycles). Do not call this function within a loop.
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int saveToFlash()  throws YAPI_Exception
    {
        return set_persistentSettings(PERSISTENTSETTINGS_SAVED);
    }

    /**
     * Reloads the settings stored in the nonvolatile memory, as
     * when the module is powered on.
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int revertFromFlash()  throws YAPI_Exception
    {
        return set_persistentSettings(PERSISTENTSETTINGS_LOADED);
    }

    /**
     * Schedules a simple module reboot after the given number of seconds.
     * 
     * @param secBeforeReboot : number of seconds before rebooting
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int reboot(int secBeforeReboot)  throws YAPI_Exception
    {
        return set_rebootCountdown(secBeforeReboot);
    }

    /**
     * Schedules a module reboot into special firmware update mode.
     * 
     * @param secBeforeReboot : number of seconds before rebooting
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int triggerFirmwareUpdate(int secBeforeReboot)  throws YAPI_Exception
    {
        return set_rebootCountdown(-secBeforeReboot);
    }

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
     * Returns a string with last logs of the module. This method return only
     * logs that are still in the module.
     * 
     * @return a string with last logs of the module.
     */
    public String get_lastLogs()  throws YAPI_Exception
    {
        byte[] content;
        // may throw an exception
        content = _download("logs.txt");
        return new String(content);
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
        String next_hwid = SafeYAPI().getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindModule(next_hwid);
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
        String next_hwid = SafeYAPI().getFirstHardwareId("Module");
        if (next_hwid == null)  return null;
        return FindModule(next_hwid);
    }

    //--- (end of generated code: YModule implementation)

    
}
