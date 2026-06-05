/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindAirQuality(), the high-level API for AirQuality functions
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

//--- (YAirQuality return codes)
//--- (end of YAirQuality return codes)
//--- (YAirQuality yapiwrapper)
//--- (end of YAirQuality yapiwrapper)
//--- (YAirQuality class start)
/**
 * YAirQuality Class: air quality sensor control interface
 *
 * The YAirQuality class allows you to read and configure Yoctopuce air quality sensors.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YAirQuality extends YSensor
{
//--- (end of YAirQuality class start)
//--- (YAirQuality definitions)
    /**
     * invalid ubaIndex value
     */
    public static final double UBAINDEX_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid relativeIndex value
     */
    public static final double RELATIVEINDEX_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid aqiMode value
     */
    public static final int AQIMODE_RELATIVE = 0;
    public static final int AQIMODE_UBA = 1;
    public static final int AQIMODE_INVALID = -1;
    protected double _ubaIndex = UBAINDEX_INVALID;
    protected double _relativeIndex = RELATIVEINDEX_INVALID;
    protected int _aqiMode = AQIMODE_INVALID;
    protected UpdateCallback _valueCallbackAirQuality = null;
    protected TimedReportCallback _timedReportCallbackAirQuality = null;

    /**
     * Deprecated UpdateCallback for AirQuality
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YAirQuality function, String functionValue);
    }

    /**
     * TimedReportCallback for AirQuality
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YAirQuality  function, YMeasure measure);
    }
    //--- (end of YAirQuality definitions)


    /**
     *
     * @param func : functionid
     */
    protected YAirQuality(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "AirQuality";
        //--- (YAirQuality attributes initialization)
        //--- (end of YAirQuality attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YAirQuality(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YAirQuality implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("ubaIndex")) {
            _ubaIndex = Math.round(json_val.getDouble("ubaIndex") / 65.536) / 1000.0;
        }
        if (json_val.has("relativeIndex")) {
            _relativeIndex = Math.round(json_val.getDouble("relativeIndex") / 65.536) / 1000.0;
        }
        if (json_val.has("aqiMode")) {
            _aqiMode = json_val.getInt("aqiMode");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current air quality index, according to UBA (from 1 to 5).
     *
     * @return a floating point number corresponding to the current air quality index, according to UBA (from 1 to 5)
     *
     * @throws YAPI_Exception on error
     */
    public double get_ubaIndex() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return UBAINDEX_INVALID;
                }
            }
            res = _ubaIndex;
        }
        return res;
    }

    /**
     * Returns the current air quality index, according to UBA (from 1 to 5).
     *
     * @return a floating point number corresponding to the current air quality index, according to UBA (from 1 to 5)
     *
     * @throws YAPI_Exception on error
     */
    public double getUbaIndex() throws YAPI_Exception
    {
        return get_ubaIndex();
    }

    /**
     * Returns the relative air quality index, according to ScioSense (from 0 to 500).
     * A value below 100 indicates better-than-average air quality compared to the past 24 hours,
     * while a value above 100 indicates poorer-than-average air quality compared to the past 24 hours.
     *
     *  @return a floating point number corresponding to the relative air quality index, according to
     * ScioSense (from 0 to 500)
     *
     * @throws YAPI_Exception on error
     */
    public double get_relativeIndex() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return RELATIVEINDEX_INVALID;
                }
            }
            res = _relativeIndex;
        }
        return res;
    }

    /**
     * Returns the relative air quality index, according to ScioSense (from 0 to 500).
     * A value below 100 indicates better-than-average air quality compared to the past 24 hours,
     * while a value above 100 indicates poorer-than-average air quality compared to the past 24 hours.
     *
     *  @return a floating point number corresponding to the relative air quality index, according to
     * ScioSense (from 0 to 500)
     *
     * @throws YAPI_Exception on error
     */
    public double getRelativeIndex() throws YAPI_Exception
    {
        return get_relativeIndex();
    }

    /**
     * Returns the type of index reported by the get_currentValue function and callbacks (UBA index or relative index).
     *
     *  @return either YAirQuality.AQIMODE_RELATIVE or YAirQuality.AQIMODE_UBA, according to the type of
     * index reported by the get_currentValue function and callbacks (UBA index or relative index)
     *
     * @throws YAPI_Exception on error
     */
    public int get_aqiMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return AQIMODE_INVALID;
                }
            }
            res = _aqiMode;
        }
        return res;
    }

    /**
     * Returns the type of index reported by the get_currentValue function and callbacks (UBA index or relative index).
     *
     *  @return either YAirQuality.AQIMODE_RELATIVE or YAirQuality.AQIMODE_UBA, according to the type of
     * index reported by the get_currentValue function and callbacks (UBA index or relative index)
     *
     * @throws YAPI_Exception on error
     */
    public int getAqiMode() throws YAPI_Exception
    {
        return get_aqiMode();
    }

    /**
     *  Changes the the type of index reported by the get_currentValue function and callbacks (UBA index or
     * relative index).
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : either YAirQuality.AQIMODE_RELATIVE or YAirQuality.AQIMODE_UBA, according to the
     * the type of index reported by the get_currentValue function and callbacks (UBA index or relative index)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_aqiMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("aqiMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     *  Changes the the type of index reported by the get_currentValue function and callbacks (UBA index or
     * relative index).
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : either YAirQuality.AQIMODE_RELATIVE or YAirQuality.AQIMODE_UBA, according to the
     * the type of index reported by the get_currentValue function and callbacks (UBA index or relative index)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAqiMode(int newval)  throws YAPI_Exception
    {
        return set_aqiMode(newval);
    }

    /**
     * Retrieves a air quality sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the air quality sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YAirQuality.isOnline() to test if the air quality sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a air quality sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the air quality sensor, for instance
     *         MyDevice.airQuality.
     *
     * @return a YAirQuality object allowing you to drive the air quality sensor.
     */
    public static YAirQuality FindAirQuality(String func)
    {
        YAirQuality obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YAirQuality) YFunction._FindFromCache("AirQuality", func);
            if (obj == null) {
                obj = new YAirQuality(func);
                YFunction._AddToCache("AirQuality", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a air quality sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the air quality sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YAirQuality.isOnline() to test if the air quality sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a air quality sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the air quality sensor, for instance
     *         MyDevice.airQuality.
     *
     * @return a YAirQuality object allowing you to drive the air quality sensor.
     */
    public static YAirQuality FindAirQualityInContext(YAPIContext yctx,String func)
    {
        YAirQuality obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YAirQuality) YFunction._FindFromCacheInContext(yctx, "AirQuality", func);
            if (obj == null) {
                obj = new YAirQuality(yctx, func);
                YFunction._AddToCache("AirQuality", func, obj);
            }
        }
        return obj;
    }

    /**
     * Registers the callback function that is invoked on every change of advertised value.
     * The callback is then invoked only during the execution of ySleep or yHandleEvents.
     * This provides control over the time when the callback is triggered. For good responsiveness,
     * remember to call one of these two functions periodically. The callback is called once juste after beeing
     * registered, passing the current advertised value  of the function, provided that it is not an empty string.
     * To unregister a callback, pass a null pointer as argument.
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
        _valueCallbackAirQuality = callback;
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
        if (_valueCallbackAirQuality != null) {
            _valueCallbackAirQuality.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Registers the callback function that is invoked on every periodic timed notification.
     * The callback is invoked only during the execution of ySleep or yHandleEvents.
     * This provides control over the time when the callback is triggered. For good responsiveness, remember to call
     * one of these two functions periodically. To unregister a callback, pass a null pointer as argument.
     *
     * @param callback : the callback function to call, or a null pointer. The callback function should take two
     *         arguments: the function object of which the value has changed, and an YMeasure object describing
     *         the new advertised value.
     *
     */
    public int registerTimedReportCallback(TimedReportCallback callback)
    {
        YSensor sensor;
        sensor = this;
        if (callback != null) {
            YFunction._UpdateTimedReportCallbackList(sensor, true);
        } else {
            YFunction._UpdateTimedReportCallbackList(sensor, false);
        }
        _timedReportCallbackAirQuality = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackAirQuality != null) {
            _timedReportCallbackAirQuality.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of air quality sensors started using yFirstAirQuality().
     * Caution: You can't make any assumption about the returned air quality sensors order.
     * If you want to find a specific a air quality sensor, use AirQuality.findAirQuality()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YAirQuality object, corresponding to
     *         a air quality sensor currently online, or a null pointer
     *         if there are no more air quality sensors to enumerate.
     */
    public YAirQuality nextAirQuality()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindAirQualityInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of air quality sensors currently accessible.
     * Use the method YAirQuality.nextAirQuality() to iterate on
     * next air quality sensors.
     *
     * @return a pointer to a YAirQuality object, corresponding to
     *         the first air quality sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YAirQuality FirstAirQuality()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("AirQuality");
        if (next_hwid == null)  return null;
        return FindAirQualityInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of air quality sensors currently accessible.
     * Use the method YAirQuality.nextAirQuality() to iterate on
     * next air quality sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YAirQuality object, corresponding to
     *         the first air quality sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YAirQuality FirstAirQualityInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("AirQuality");
        if (next_hwid == null)  return null;
        return FindAirQualityInContext(yctx, next_hwid);
    }

    //--- (end of YAirQuality implementation)
}

