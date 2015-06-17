/*********************************************************************
 *
 * $Id: YModule.java 20468 2015-05-29 10:24:28Z seb $
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

//--- (generated code: YModule class start)
/**
 * YModule Class: Module control interface
 *
 * This interface is identical for all Yoctopuce USB modules.
 * It can be used to control the module global parameters, and
 * to enumerate the functions provided by each module.
 */
 @SuppressWarnings("UnusedDeclaration")
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
     * invalid userVar value
     */
    public static final int USERVAR_INVALID = YAPI.INVALID_INT;
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
    protected int _userVar = USERVAR_INVALID;
    protected UpdateCallback _valueCallbackModule = null;
    protected YModule.LogCallback _logCallback = null;

    public interface LogCallback {
        /**
         *
         * @param module  : the module object of the device
         * @param logline : the log line (without carriage return)
         */
        void logCallback(YModule module, String logline);
    }
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
        if (dotidx >= 0) devid = devid.substring(0, dotidx);
        YDevice dev = SafeYAPI().getDevice(devid);
        if (dev == null) {
            throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "Device [" + devid + "] is not online");
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

    /**
     * Registers a device log callback function. This callback will be called each time
     * that a module sends a new log message. Mostly useful to debug a Yoctopuce module.
     *
     * @param callback : the callback function to call, or a null pointer. The callback function should take two
     *         arguments: the module object that emitted the log message, and the character string containing the log.
     *
     */
    public void registerLogCallback(LogCallback callback)
    {
        _logCallback = callback;
        YDevice ydev = SafeYAPI().getDevice(_serial);
        if (ydev != null) {
            ydev.registerLogCallback(callback);
        }
    }


    LogCallback get_logCallback()
    {
        return _logCallback;
    }

    private byte[] _flattenJsonStruct(byte[] actualSettings) throws YAPI_Exception
    {
        JSONObject json = null;
        JSONArray out = new JSONArray();
        try {
            json = new JSONObject(new String(actualSettings, YAPI.DeviceCharset));
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
        }
        Iterator functionList = json.keys();
        while (functionList.hasNext()) {
            String fun_key = (String) functionList.next();
            if (!fun_key.equals("services")) {
                JSONObject functionJson = json.optJSONObject(fun_key);
                if (functionJson == null) {
                    continue;
                }
                Iterator attr_keys = functionJson.keys();
                while (attr_keys.hasNext()) {
                    String attr_key = (String) attr_keys.next();
                    if (functionJson.optJSONObject(attr_key) != null) {
                        continue;
                    }
                    Object value = functionJson.opt(attr_key);
                    if (value == null) {
                        continue;
                    }
                    String flat_attr = fun_key + "/" + attr_key + "=" + value.toString();
                    out.put(flat_attr);
                }
            }

        }
        return out.toString().getBytes();
    }


    // --- (generated code: YModule implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("productName")) {
            _productName = json_val.getString("productName");
        }
        if (json_val.has("serialNumber")) {
            _serialNumber = json_val.getString("serialNumber");
        }
        if (json_val.has("productId")) {
            _productId = json_val.getInt("productId");
        }
        if (json_val.has("productRelease")) {
            _productRelease = json_val.getInt("productRelease");
        }
        if (json_val.has("firmwareRelease")) {
            _firmwareRelease = json_val.getString("firmwareRelease");
        }
        if (json_val.has("persistentSettings")) {
            _persistentSettings = json_val.getInt("persistentSettings");
        }
        if (json_val.has("luminosity")) {
            _luminosity = json_val.getInt("luminosity");
        }
        if (json_val.has("beacon")) {
            _beacon = json_val.getInt("beacon") > 0 ? 1 : 0;
        }
        if (json_val.has("upTime")) {
            _upTime = json_val.getLong("upTime");
        }
        if (json_val.has("usbCurrent")) {
            _usbCurrent = json_val.getInt("usbCurrent");
        }
        if (json_val.has("rebootCountdown")) {
            _rebootCountdown = json_val.getInt("rebootCountdown");
        }
        if (json_val.has("userVar")) {
            _userVar = json_val.getInt("userVar");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the commercial name of the module, as set by the factory.
     *
     * @return a string corresponding to the commercial name of the module, as set by the factory
     *
     * @throws YAPI_Exception on error
     */
    public String get_productName() throws YAPI_Exception
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
     * @throws YAPI_Exception on error
     */
    public String getProductName() throws YAPI_Exception
    {
        return get_productName();
    }

    /**
     * Returns the serial number of the module, as set by the factory.
     *
     * @return a string corresponding to the serial number of the module, as set by the factory
     *
     * @throws YAPI_Exception on error
     */
    public String get_serialNumber() throws YAPI_Exception
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
     * @throws YAPI_Exception on error
     */
    public String getSerialNumber() throws YAPI_Exception
    {
        return get_serialNumber();
    }

    /**
     * Returns the USB device identifier of the module.
     *
     * @return an integer corresponding to the USB device identifier of the module
     *
     * @throws YAPI_Exception on error
     */
    public int get_productId() throws YAPI_Exception
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
     * @throws YAPI_Exception on error
     */
    public int getProductId() throws YAPI_Exception
    {
        return get_productId();
    }

    /**
     * Returns the hardware release version of the module.
     *
     * @return an integer corresponding to the hardware release version of the module
     *
     * @throws YAPI_Exception on error
     */
    public int get_productRelease() throws YAPI_Exception
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
     * @throws YAPI_Exception on error
     */
    public int getProductRelease() throws YAPI_Exception
    {
        return get_productRelease();
    }

    /**
     * Returns the version of the firmware embedded in the module.
     *
     * @return a string corresponding to the version of the firmware embedded in the module
     *
     * @throws YAPI_Exception on error
     */
    public String get_firmwareRelease() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
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
     * @throws YAPI_Exception on error
     */
    public String getFirmwareRelease() throws YAPI_Exception
    {
        return get_firmwareRelease();
    }

    /**
     * Returns the current state of persistent module settings.
     *
     *  @return a value among YModule.PERSISTENTSETTINGS_LOADED, YModule.PERSISTENTSETTINGS_SAVED and
     * YModule.PERSISTENTSETTINGS_MODIFIED corresponding to the current state of persistent module settings
     *
     * @throws YAPI_Exception on error
     */
    public int get_persistentSettings() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PERSISTENTSETTINGS_INVALID;
            }
        }
        return _persistentSettings;
    }

    /**
     * Returns the current state of persistent module settings.
     *
     *  @return a value among Y_PERSISTENTSETTINGS_LOADED, Y_PERSISTENTSETTINGS_SAVED and
     * Y_PERSISTENTSETTINGS_MODIFIED corresponding to the current state of persistent module settings
     *
     * @throws YAPI_Exception on error
     */
    public int getPersistentSettings() throws YAPI_Exception
    {
        return get_persistentSettings();
    }

    public int set_persistentSettings(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("persistentSettings",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPersistentSettings(int newval)  throws YAPI_Exception
    {
        return set_persistentSettings(newval);
    }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     *
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     *
     * @throws YAPI_Exception on error
     */
    public int get_luminosity() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
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
     * @throws YAPI_Exception on error
     */
    public int getLuminosity() throws YAPI_Exception
    {
        return get_luminosity();
    }

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
     * @throws YAPI_Exception on error
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
     * @throws YAPI_Exception on error
     */
    public int setLuminosity(int newval)  throws YAPI_Exception
    {
        return set_luminosity(newval);
    }

    /**
     * Returns the state of the localization beacon.
     *
     * @return either YModule.BEACON_OFF or YModule.BEACON_ON, according to the state of the localization beacon
     *
     * @throws YAPI_Exception on error
     */
    public int get_beacon() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
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
     * @throws YAPI_Exception on error
     */
    public int getBeacon() throws YAPI_Exception
    {
        return get_beacon();
    }

    /**
     * Turns on or off the module localization beacon.
     *
     * @param newval : either YModule.BEACON_OFF or YModule.BEACON_ON
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
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
     * @throws YAPI_Exception on error
     */
    public int setBeacon(int newval)  throws YAPI_Exception
    {
        return set_beacon(newval);
    }

    /**
     * Returns the number of milliseconds spent since the module was powered on.
     *
     * @return an integer corresponding to the number of milliseconds spent since the module was powered on
     *
     * @throws YAPI_Exception on error
     */
    public long get_upTime() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
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
     * @throws YAPI_Exception on error
     */
    public long getUpTime() throws YAPI_Exception
    {
        return get_upTime();
    }

    /**
     * Returns the current consumed by the module on the USB bus, in milli-amps.
     *
     * @return an integer corresponding to the current consumed by the module on the USB bus, in milli-amps
     *
     * @throws YAPI_Exception on error
     */
    public int get_usbCurrent() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
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
     * @throws YAPI_Exception on error
     */
    public int getUsbCurrent() throws YAPI_Exception
    {
        return get_usbCurrent();
    }

    /**
     * Returns the remaining number of seconds before the module restarts, or zero when no
     * reboot has been scheduled.
     *
     * @return an integer corresponding to the remaining number of seconds before the module restarts, or zero when no
     *         reboot has been scheduled
     *
     * @throws YAPI_Exception on error
     */
    public int get_rebootCountdown() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
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
     * @throws YAPI_Exception on error
     */
    public int getRebootCountdown() throws YAPI_Exception
    {
        return get_rebootCountdown();
    }

    public int set_rebootCountdown(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("rebootCountdown",rest_val);
        return YAPI.SUCCESS;
    }

    public int setRebootCountdown(int newval)  throws YAPI_Exception
    {
        return set_rebootCountdown(newval);
    }

    /**
     * Returns the value previously stored in this attribute.
     * On startup and after a device reboot, the value is always reset to zero.
     *
     * @return an integer corresponding to the value previously stored in this attribute
     *
     * @throws YAPI_Exception on error
     */
    public int get_userVar() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return USERVAR_INVALID;
            }
        }
        return _userVar;
    }

    /**
     * Returns the value previously stored in this attribute.
     * On startup and after a device reboot, the value is always reset to zero.
     *
     * @return an integer corresponding to the value previously stored in this attribute
     *
     * @throws YAPI_Exception on error
     */
    public int getUserVar() throws YAPI_Exception
    {
        return get_userVar();
    }

    /**
     * Returns the value previously stored in this attribute.
     * On startup and after a device reboot, the value is always reset to zero.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_userVar(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("userVar",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the value previously stored in this attribute.
     * On startup and after a device reboot, the value is always reset to zero.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUserVar(int newval)  throws YAPI_Exception
    {
        return set_userVar(newval);
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
     *
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
     * @throws YAPI_Exception on error
     */
    public int saveToFlash() throws YAPI_Exception
    {
        return set_persistentSettings(PERSISTENTSETTINGS_SAVED);
    }

    /**
     * Reloads the settings stored in the nonvolatile memory, as
     * when the module is powered on.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int revertFromFlash() throws YAPI_Exception
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
     * @throws YAPI_Exception on error
     */
    public int reboot(int secBeforeReboot) throws YAPI_Exception
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
     * @throws YAPI_Exception on error
     */
    public int triggerFirmwareUpdate(int secBeforeReboot) throws YAPI_Exception
    {
        return set_rebootCountdown(-secBeforeReboot);
    }

    /**
     *  Tests whether the byn file is valid for this module. This method is useful to test if the module
     * needs to be updated.
     *  It is possible to pass a directory as argument instead of a file. In this case, this method returns
     * the path of the most recent
     *  appropriate byn file. If the parameter onlynew is true, the function discards firmware that are
     * older or equal to
     * the installed firmware.
     *
     * @param path    : the path of a byn file or a directory that contains byn files
     * @param onlynew : returns only files that are strictly newer
     *
     * @return : the path of the byn file to use or a empty string if no byn files matches the requirement
     *
     * @throws YAPI_Exception on error
     */
    public String checkFirmware(String path,boolean onlynew) throws YAPI_Exception
    {
        String serial;
        int release;
        String tmp_res;
        if (onlynew) {
            release = YAPI._atoi(get_firmwareRelease());
        } else {
            release = 0;
        }
        //may throw an exception
        serial = get_serialNumber();
        tmp_res = YFirmwareUpdate.CheckFirmware(serial,path, release);
        if ((tmp_res).indexOf("error:") == 0) {
            _throw(YAPI.INVALID_ARGUMENT, tmp_res);
        }
        return tmp_res;
    }

    /**
     * Prepares a firmware update of the module. This method returns a YFirmwareUpdate object which
     * handles the firmware update process.
     *
     * @param path : the path of the byn file to use.
     *
     * @return : A YFirmwareUpdate object.
     */
    public YFirmwareUpdate updateFirmware(String path) throws YAPI_Exception
    {
        String serial;
        byte[] settings;
        // may throw an exception
        serial = get_serialNumber();
        settings = get_allSettings();
        return new YFirmwareUpdate(serial, path, settings);
    }

    /**
     * Returns all the settings of the module. Useful to backup all the logical names and calibrations parameters
     * of a connected module.
     *
     * @return a binary buffer with all the settings.
     *
     * @throws YAPI_Exception on error
     */
    public byte[] get_allSettings() throws YAPI_Exception
    {
        return _download("api.json");
    }

    //cannot be generated for Java:
    //public byte[] _flattenJsonStruct(byte[] jsoncomplex) throws YAPI_Exception

    public int calibVersion(String cparams)
    {
        if (cparams.equals("0,")) {
            return 3;
        }
        if ((cparams).indexOf(",") >= 0) {
            if ((cparams).indexOf(" ") > 0) {
                return 3;
            } else {
                return 1;
            }
        }
        if (cparams.equals("") || cparams.equals("0")) {
            return 1;
        }
        if (((cparams).length() < 2) || ((cparams).indexOf(".") >= 0)) {
            return 0;
        } else {
            return 2;
        }
    }

    public int calibScale(String unit_name,String sensorType)
    {
        if (unit_name.equals("g") || unit_name.equals("gauss") || unit_name.equals("W")) {
            return 1000;
        }
        if (unit_name.equals("C")) {
            if (sensorType.equals("")) {
                return 16;
            }
            if (YAPI._atoi(sensorType) < 8) {
                return 16;
            } else {
                return 100;
            }
        }
        if (unit_name.equals("m") || unit_name.equals("deg")) {
            return 10;
        }
        return 1;
    }

    public int calibOffset(String unit_name)
    {
        if (unit_name.equals("% RH") || unit_name.equals("mbar") || unit_name.equals("lx")) {
            return 0;
        }
        return 32767;
    }

    public String calibConvert(String param,String currentFuncValue,String unit_name,String sensorType)
    {
        int paramVer;
        int funVer;
        int funScale;
        int funOffset;
        int paramScale;
        int paramOffset;
        ArrayList<Integer> words = new ArrayList<Integer>();
        ArrayList<String> words_str = new ArrayList<String>();
        ArrayList<Double> calibData = new ArrayList<Double>();
        ArrayList<Integer> iCalib = new ArrayList<Integer>();
        int calibType;
        int i;
        int maxSize;
        double ratio;
        int nPoints;
        double wordVal;
        // Initial guess for parameter encoding
        paramVer = calibVersion(param);
        funVer = calibVersion(currentFuncValue);
        funScale = calibScale(unit_name, sensorType);
        funOffset = calibOffset(unit_name);
        paramScale = funScale;
        paramOffset = funOffset;
        if (funVer < 3) {
            if (funVer == 2) {
                words = SafeYAPI()._decodeWords(currentFuncValue);
                if ((words.get(0).intValue() == 1366) && (words.get(1).intValue() == 12500)) {
                    funScale = 1;
                    funOffset = 0;
                } else {
                    funScale = words.get(1).intValue();
                    funOffset = words.get(0).intValue();
                }
            } else {
                if (funVer == 1) {
                    if (currentFuncValue.equals("") || (YAPI._atoi(currentFuncValue) > 10)) {
                        funScale = 0;
                    }
                }
            }
        }
        calibData.clear();
        calibType = 0;
        if (paramVer < 3) {
            if (paramVer == 2) {
                words = SafeYAPI()._decodeWords(param);
                if ((words.get(0).intValue() == 1366) && (words.get(1).intValue() == 12500)) {
                    paramScale = 1;
                    paramOffset = 0;
                } else {
                    paramScale = words.get(1).intValue();
                    paramOffset = words.get(0).intValue();
                }
                if ((words.size() >= 3) && (words.get(2).intValue() > 0)) {
                    maxSize = 3 + 2 * ((words.get(2).intValue()) % (10));
                    if (maxSize > words.size()) {
                        maxSize = words.size();
                    }
                    i = 3;
                    while (i < maxSize) {
                        calibData.add((double) words.get(i).intValue());
                        i = i + 1;
                    }
                }
            } else {
                if (paramVer == 1) {
                    words_str = new ArrayList<String>(Arrays.asList(param.split(",")));
                    for (String ii:words_str) {
                        words.add(YAPI._atoi(ii));
                    }
                    if (param.equals("") || (words.get(0).intValue() > 10)) {
                        paramScale = 0;
                    }
                    if ((words.size() > 0) && (words.get(0).intValue() > 0)) {
                        maxSize = 1 + 2 * ((words.get(0).intValue()) % (10));
                        if (maxSize > words.size()) {
                            maxSize = words.size();
                        }
                        i = 1;
                        while (i < maxSize) {
                            calibData.add((double) words.get(i).intValue());
                            i = i + 1;
                        }
                    }
                } else {
                    if (paramVer == 0) {
                        ratio = Double.valueOf(param);
                        if (ratio > 0) {
                            calibData.add(0.0);
                            calibData.add(0.0);
                            calibData.add((double)Math.round(65535 / ratio));
                            calibData.add(65535.0);
                        }
                    }
                }
            }
            i = 0;
            while (i < calibData.size()) {
                if (paramScale > 0) {
                    calibData.set( i, (calibData.get(i).doubleValue() - paramOffset) / paramScale);
                } else {
                    calibData.set( i, SafeYAPI()._decimalToDouble((int) (double)Math.round(calibData.get(i).doubleValue())));
                }
                i = i + 1;
            }
        } else {
            iCalib = SafeYAPI()._decodeFloats(param);
            calibType = (int) (double)Math.round(iCalib.get(0).doubleValue() / 1000.0);
            if (calibType >= 30) {
                calibType = calibType - 30;
            }
            i = 1;
            while (i < iCalib.size()) {
                calibData.add(iCalib.get(i).doubleValue() / 1000.0);
                i = i + 1;
            }
        }
        if (funVer >= 3) {
            if (calibData.size() == 0) {
                param = "0,";
            } else {
                param = Integer.toString(30 + calibType);
                i = 0;
                while (i < calibData.size()) {
                    if (((i) & (1)) > 0) {
                        param = param + ":";
                    } else {
                        param = param + " ";
                    }
                    param = param + Integer.toString((int) (double)Math.round(calibData.get(i).doubleValue() * 1000.0 / 1000.0));
                    i = i + 1;
                }
                param = param + ",";
            }
        } else {
            if (funVer >= 1) {
                nPoints = ((calibData.size()) / (2));
                param = Integer.toString(nPoints);
                i = 0;
                while (i < 2 * nPoints) {
                    if (funScale == 0) {
                        wordVal = SafeYAPI()._doubleToDecimal((int) (double)Math.round(calibData.get(i).doubleValue()));
                    } else {
                        wordVal = calibData.get(i).doubleValue() * funScale + funOffset;
                    }
                    param = param + "," + Double.toString((double)Math.round(wordVal));
                    i = i + 1;
                }
            } else {
                if (calibData.size() == 4) {
                    param = Double.toString((double)Math.round(1000 * (calibData.get(3).doubleValue() - calibData.get(1).doubleValue()) / calibData.get(2).doubleValue() - calibData.get(0).doubleValue()));
                }
            }
        }
        return param;
    }

    /**
     * Restores all the settings of the module. Useful to restore all the logical names and calibrations parameters
     * of a module from a backup.Remember to call the saveToFlash() method of the module if the
     * modifications must be kept.
     *
     * @param settings : a binary buffer with all the settings.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_allSettings(byte[] settings) throws YAPI_Exception
    {
        ArrayList<String> restoreLast = new ArrayList<String>();
        byte[] old_json_flat;
        ArrayList<String> old_dslist = new ArrayList<String>();
        ArrayList<String> old_jpath = new ArrayList<String>();
        ArrayList<Integer> old_jpath_len = new ArrayList<Integer>();
        ArrayList<String> old_val_arr = new ArrayList<String>();
        byte[] actualSettings;
        ArrayList<String> new_dslist = new ArrayList<String>();
        ArrayList<String> new_jpath = new ArrayList<String>();
        ArrayList<Integer> new_jpath_len = new ArrayList<Integer>();
        ArrayList<String> new_val_arr = new ArrayList<String>();
        int cpos;
        int eqpos;
        int leng;
        int i;
        int j;
        String njpath;
        String jpath;
        String fun;
        String attr;
        String value;
        String url;
        String tmp;
        String new_calib;
        String sensorType;
        String unit_name;
        String newval;
        String oldval;
        String old_calib;
        String each_str;
        boolean do_update;
        boolean found;
        oldval = "";
        newval = "";
        old_json_flat = _flattenJsonStruct(settings);
        old_dslist = _json_get_array(old_json_flat);
        for (String ii:old_dslist) {
            each_str = _json_get_string(ii.getBytes());
            leng = (each_str).length();
            eqpos = (each_str).indexOf("=");
            if ((eqpos < 0) || (leng == 0)) {
                _throw(YAPI.INVALID_ARGUMENT, "Invalid settings");
                return YAPI.INVALID_ARGUMENT;
            }
            jpath = (each_str).substring( 0,  0 + eqpos);
            eqpos = eqpos + 1;
            value = (each_str).substring( eqpos,  eqpos + leng - eqpos);
            old_jpath.add(jpath);
            old_jpath_len.add((jpath).length());
            old_val_arr.add(value);
        }
        // may throw an exception
        actualSettings = _download("api.json");
        actualSettings = _flattenJsonStruct(actualSettings);
        new_dslist = _json_get_array(actualSettings);
        for (String ii:new_dslist) {
            each_str = _json_get_string(ii.getBytes());
            leng = (each_str).length();
            eqpos = (each_str).indexOf("=");
            if ((eqpos < 0) || (leng == 0)) {
                _throw(YAPI.INVALID_ARGUMENT, "Invalid settings");
                return YAPI.INVALID_ARGUMENT;
            }
            jpath = (each_str).substring( 0,  0 + eqpos);
            eqpos = eqpos + 1;
            value = (each_str).substring( eqpos,  eqpos + leng - eqpos);
            new_jpath.add(jpath);
            new_jpath_len.add((jpath).length());
            new_val_arr.add(value);
        }
        i = 0;
        while (i < new_jpath.size()) {
            njpath = new_jpath.get(i);
            leng = (njpath).length();
            cpos = (njpath).indexOf("/");
            if ((cpos < 0) || (leng == 0)) {
                continue;
            }
            fun = (njpath).substring( 0,  0 + cpos);
            cpos = cpos + 1;
            attr = (njpath).substring( cpos,  cpos + leng - cpos);
            do_update = true;
            if (fun.equals("services")) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("firmwareRelease"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("usbCurrent"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("upTime"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("persistentSettings"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("adminPassword"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("userPassword"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("rebootCountdown"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("advertisedValue"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("poeCurrent"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("readiness"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("ipAddress"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("subnetMask"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("router"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("linkQuality"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("ssid"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("channel"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("security"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("message"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("currentValue"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("currentRawValue"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("currentRunIndex"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("pulseTimer"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("lastTimePressed"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("lastTimeReleased"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("filesCount"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("freeSpace"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("timeUTC"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("rtcTime"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("unixTime"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("dateTime"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("rawValue"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("lastMsg"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("delayedPulseTimer"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("rxCount"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("txCount"))) {
                do_update = false;
            }
            if ((do_update) && (attr.equals("msgCount"))) {
                do_update = false;
            }
            if (do_update) {
                do_update = false;
                newval = new_val_arr.get(i);
                j = 0;
                found = false;
                while ((j < old_jpath.size()) && !(found)) {
                    if ((new_jpath_len.get(i) == old_jpath_len.get(j)) && (new_jpath.get(i).equals(old_jpath.get(j)))) {
                        found = true;
                        oldval = old_val_arr.get(j);
                        if (!(newval.equals(oldval))) {
                            do_update = true;
                        }
                    }
                    j = j + 1;
                }
            }
            if (do_update) {
                if (attr.equals("calibrationParam")) {
                    old_calib = "";
                    unit_name = "";
                    sensorType = "";
                    new_calib = newval;
                    j = 0;
                    found = false;
                    while ((j < old_jpath.size()) && !(found)) {
                        if ((new_jpath_len.get(i) == old_jpath_len.get(j)) && (new_jpath.get(i).equals(old_jpath.get(j)))) {
                            found = true;
                            old_calib = old_val_arr.get(j);
                        }
                        j = j + 1;
                    }
                    tmp = fun + "/unit";
                    j = 0;
                    found = false;
                    while ((j < new_jpath.size()) && !(found)) {
                        if (tmp.equals(new_jpath.get(j))) {
                            found = true;
                            unit_name = new_val_arr.get(j);
                        }
                        j = j + 1;
                    }
                    tmp = fun + "/sensorType";
                    j = 0;
                    found = false;
                    while ((j < new_jpath.size()) && !(found)) {
                        if (tmp.equals(new_jpath.get(j))) {
                            found = true;
                            sensorType = new_val_arr.get(j);
                        }
                        j = j + 1;
                    }
                    newval = calibConvert(old_calib, new_val_arr.get(i), unit_name, sensorType);
                    url = "api/" + fun + ".json?" + attr + "=" + _escapeAttr(newval);
                    _download(url);
                } else {
                    url = "api/" + fun + ".json?" + attr + "=" + _escapeAttr(oldval);
                    if (attr.equals("resolution")) {
                        restoreLast.add(url);
                    } else {
                        _download(url);
                    }
                }
            }
            i = i + 1;
        }
        for (String ii:restoreLast) {
            _download(ii);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Downloads the specified built-in file and returns a binary buffer with its content.
     *
     * @param pathname : name of the new file to load
     *
     * @return a binary buffer with the file content
     *
     * @throws YAPI_Exception on error
     */
    public byte[] download(String pathname) throws YAPI_Exception
    {
        return _download(pathname);
    }

    /**
     * Returns the icon of the module. The icon is a PNG image and does not
     * exceeds 1536 bytes.
     *
     * @return a binary buffer with module icon, in png format.
     * @throws YAPI_Exception on error
     */
    public byte[] get_icon2d() throws YAPI_Exception
    {
        return _download("icon2d.png");
    }

    /**
     * Returns a string with last logs of the module. This method return only
     * logs that are still in the module.
     *
     * @return a string with last logs of the module.
     * @throws YAPI_Exception on error
     */
    public String get_lastLogs() throws YAPI_Exception
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
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
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
