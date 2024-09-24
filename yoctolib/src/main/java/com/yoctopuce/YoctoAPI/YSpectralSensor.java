/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindSpectralSensor(), the high-level API for SpectralSensor functions
 *
 *  - - - - - - - - - License information: - - - - - - - - -
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
 */

package com.yoctopuce.YoctoAPI;

//--- (YSpectralSensor return codes)
//--- (end of YSpectralSensor return codes)
//--- (YSpectralSensor yapiwrapper)
//--- (end of YSpectralSensor yapiwrapper)
//--- (YSpectralSensor class start)
/**
 * YSpectralSensor Class: spectral sensor control interface
 *
 * The YSpectralSensor class allows you to read and configure Yoctopuce spectral sensors.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSpectralSensor extends YFunction
{
//--- (end of YSpectralSensor class start)
//--- (YSpectralSensor definitions)
    /**
     * invalid ledCurrent value
     */
    public static final int LEDCURRENT_INVALID = YAPI.INVALID_INT;
    /**
     * invalid resolution value
     */
    public static final double RESOLUTION_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid integrationTime value
     */
    public static final int INTEGRATIONTIME_INVALID = YAPI.INVALID_INT;
    /**
     * invalid gain value
     */
    public static final int GAIN_INVALID = YAPI.INVALID_INT;
    /**
     * invalid saturation value
     */
    public static final int SATURATION_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid ledCurrentAtPowerOn value
     */
    public static final int LEDCURRENTATPOWERON_INVALID = YAPI.INVALID_INT;
    /**
     * invalid integrationTimeAtPowerOn value
     */
    public static final int INTEGRATIONTIMEATPOWERON_INVALID = YAPI.INVALID_INT;
    /**
     * invalid gainAtPowerOn value
     */
    public static final int GAINATPOWERON_INVALID = YAPI.INVALID_INT;
    protected int _ledCurrent = LEDCURRENT_INVALID;
    protected double _resolution = RESOLUTION_INVALID;
    protected int _integrationTime = INTEGRATIONTIME_INVALID;
    protected int _gain = GAIN_INVALID;
    protected int _saturation = SATURATION_INVALID;
    protected int _ledCurrentAtPowerOn = LEDCURRENTATPOWERON_INVALID;
    protected int _integrationTimeAtPowerOn = INTEGRATIONTIMEATPOWERON_INVALID;
    protected int _gainAtPowerOn = GAINATPOWERON_INVALID;
    protected UpdateCallback _valueCallbackSpectralSensor = null;

    /**
     * Deprecated UpdateCallback for SpectralSensor
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YSpectralSensor function, String functionValue);
    }

    /**
     * TimedReportCallback for SpectralSensor
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YSpectralSensor  function, YMeasure measure);
    }
    //--- (end of YSpectralSensor definitions)


    /**
     *
     * @param func : functionid
     */
    protected YSpectralSensor(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "SpectralSensor";
        //--- (YSpectralSensor attributes initialization)
        //--- (end of YSpectralSensor attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YSpectralSensor(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YSpectralSensor implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("ledCurrent")) {
            _ledCurrent = json_val.getInt("ledCurrent");
        }
        if (json_val.has("resolution")) {
            _resolution = Math.round(json_val.getDouble("resolution") / 65.536) / 1000.0;
        }
        if (json_val.has("integrationTime")) {
            _integrationTime = json_val.getInt("integrationTime");
        }
        if (json_val.has("gain")) {
            _gain = json_val.getInt("gain");
        }
        if (json_val.has("saturation")) {
            _saturation = json_val.getInt("saturation");
        }
        if (json_val.has("ledCurrentAtPowerOn")) {
            _ledCurrentAtPowerOn = json_val.getInt("ledCurrentAtPowerOn");
        }
        if (json_val.has("integrationTimeAtPowerOn")) {
            _integrationTimeAtPowerOn = json_val.getInt("integrationTimeAtPowerOn");
        }
        if (json_val.has("gainAtPowerOn")) {
            _gainAtPowerOn = json_val.getInt("gainAtPowerOn");
        }
        super._parseAttr(json_val);
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_ledCurrent() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LEDCURRENT_INVALID;
                }
            }
            res = _ledCurrent;
        }
        return res;
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getLedCurrent() throws YAPI_Exception
    {
        return get_ledCurrent();
    }

    /**
     * Changes the luminosity of the module leds. The parameter is a
     * value between 0 and 100.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the luminosity of the module leds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_ledCurrent(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("ledCurrent",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the luminosity of the module leds. The parameter is a
     * value between 0 and 100.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the luminosity of the module leds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLedCurrent(int newval)  throws YAPI_Exception
    {
        return set_ledCurrent(newval);
    }

    /**
     * Changes the resolution of the measured physical values. The resolution corresponds to the numerical precision
     * when displaying value. It does not change the precision of the measure itself.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : a floating point number corresponding to the resolution of the measured physical values
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_resolution(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("resolution",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the resolution of the measured physical values. The resolution corresponds to the numerical precision
     * when displaying value. It does not change the precision of the measure itself.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : a floating point number corresponding to the resolution of the measured physical values
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setResolution(double newval)  throws YAPI_Exception
    {
        return set_resolution(newval);
    }

    /**
     * Returns the resolution of the measured values. The resolution corresponds to the numerical precision
     * of the measures, which is not always the same as the actual precision of the sensor.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @return a floating point number corresponding to the resolution of the measured values
     *
     * @throws YAPI_Exception on error
     */
    public double get_resolution() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return RESOLUTION_INVALID;
                }
            }
            res = _resolution;
        }
        return res;
    }

    /**
     * Returns the resolution of the measured values. The resolution corresponds to the numerical precision
     * of the measures, which is not always the same as the actual precision of the sensor.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @return a floating point number corresponding to the resolution of the measured values
     *
     * @throws YAPI_Exception on error
     */
    public double getResolution() throws YAPI_Exception
    {
        return get_resolution();
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_integrationTime() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return INTEGRATIONTIME_INVALID;
                }
            }
            res = _integrationTime;
        }
        return res;
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getIntegrationTime() throws YAPI_Exception
    {
        return get_integrationTime();
    }

    /**
     * Change the integration time for a measure. The parameter is a
     * value between 0 and 100.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_integrationTime(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("integrationTime",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Change the integration time for a measure. The parameter is a
     * value between 0 and 100.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setIntegrationTime(int newval)  throws YAPI_Exception
    {
        return set_integrationTime(newval);
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_gain() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return GAIN_INVALID;
                }
            }
            res = _gain;
        }
        return res;
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getGain() throws YAPI_Exception
    {
        return get_gain();
    }

    /**
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_gain(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("gain",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setGain(int newval)  throws YAPI_Exception
    {
        return set_gain(newval);
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_saturation() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return SATURATION_INVALID;
                }
            }
            res = _saturation;
        }
        return res;
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getSaturation() throws YAPI_Exception
    {
        return get_saturation();
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_ledCurrentAtPowerOn() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LEDCURRENTATPOWERON_INVALID;
                }
            }
            res = _ledCurrentAtPowerOn;
        }
        return res;
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getLedCurrentAtPowerOn() throws YAPI_Exception
    {
        return get_ledCurrentAtPowerOn();
    }

    /**
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_ledCurrentAtPowerOn(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("ledCurrentAtPowerOn",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLedCurrentAtPowerOn(int newval)  throws YAPI_Exception
    {
        return set_ledCurrentAtPowerOn(newval);
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_integrationTimeAtPowerOn() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return INTEGRATIONTIMEATPOWERON_INVALID;
                }
            }
            res = _integrationTimeAtPowerOn;
        }
        return res;
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getIntegrationTimeAtPowerOn() throws YAPI_Exception
    {
        return get_integrationTimeAtPowerOn();
    }

    /**
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_integrationTimeAtPowerOn(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("integrationTimeAtPowerOn",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setIntegrationTimeAtPowerOn(int newval)  throws YAPI_Exception
    {
        return set_integrationTimeAtPowerOn(newval);
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_gainAtPowerOn() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return GAINATPOWERON_INVALID;
                }
            }
            res = _gainAtPowerOn;
        }
        return res;
    }

    /**
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getGainAtPowerOn() throws YAPI_Exception
    {
        return get_gainAtPowerOn();
    }

    /**
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_gainAtPowerOn(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("gainAtPowerOn",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setGainAtPowerOn(int newval)  throws YAPI_Exception
    {
        return set_gainAtPowerOn(newval);
    }

    /**
     * Retrieves a spectral sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the spectral sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSpectralSensor.isOnline() to test if the spectral sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a spectral sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the spectral sensor, for instance
     *         MyDevice.spectralSensor.
     *
     * @return a YSpectralSensor object allowing you to drive the spectral sensor.
     */
    public static YSpectralSensor FindSpectralSensor(String func)
    {
        YSpectralSensor obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YSpectralSensor) YFunction._FindFromCache("SpectralSensor", func);
            if (obj == null) {
                obj = new YSpectralSensor(func);
                YFunction._AddToCache("SpectralSensor", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a spectral sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the spectral sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSpectralSensor.isOnline() to test if the spectral sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a spectral sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the spectral sensor, for instance
     *         MyDevice.spectralSensor.
     *
     * @return a YSpectralSensor object allowing you to drive the spectral sensor.
     */
    public static YSpectralSensor FindSpectralSensorInContext(YAPIContext yctx,String func)
    {
        YSpectralSensor obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YSpectralSensor) YFunction._FindFromCacheInContext(yctx, "SpectralSensor", func);
            if (obj == null) {
                obj = new YSpectralSensor(yctx, func);
                YFunction._AddToCache("SpectralSensor", func, obj);
            }
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
        _valueCallbackSpectralSensor = callback;
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
        if (_valueCallbackSpectralSensor != null) {
            _valueCallbackSpectralSensor.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of spectral sensors started using yFirstSpectralSensor().
     * Caution: You can't make any assumption about the returned spectral sensors order.
     * If you want to find a specific a spectral sensor, use SpectralSensor.findSpectralSensor()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YSpectralSensor object, corresponding to
     *         a spectral sensor currently online, or a null pointer
     *         if there are no more spectral sensors to enumerate.
     */
    public YSpectralSensor nextSpectralSensor()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindSpectralSensorInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of spectral sensors currently accessible.
     * Use the method YSpectralSensor.nextSpectralSensor() to iterate on
     * next spectral sensors.
     *
     * @return a pointer to a YSpectralSensor object, corresponding to
     *         the first spectral sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YSpectralSensor FirstSpectralSensor()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("SpectralSensor");
        if (next_hwid == null)  return null;
        return FindSpectralSensorInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of spectral sensors currently accessible.
     * Use the method YSpectralSensor.nextSpectralSensor() to iterate on
     * next spectral sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YSpectralSensor object, corresponding to
     *         the first spectral sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YSpectralSensor FirstSpectralSensorInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("SpectralSensor");
        if (next_hwid == null)  return null;
        return FindSpectralSensorInContext(yctx, next_hwid);
    }

    //--- (end of YSpectralSensor implementation)
}

