/*********************************************************************
 *
 * $Id: YHumidity.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindHumidity(), the high-level API for Humidity functions
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

//--- (YHumidity return codes)
//--- (end of YHumidity return codes)
//--- (YHumidity class start)
/**
 * YHumidity Class: Humidity function interface
 *
 * The Yoctopuce class YHumidity allows you to read and configure Yoctopuce humidity
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YHumidity extends YSensor
{
//--- (end of YHumidity class start)
//--- (YHumidity definitions)
    /**
     * invalid relHum value
     */
    public static final double RELHUM_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid absHum value
     */
    public static final double ABSHUM_INVALID = YAPI.INVALID_DOUBLE;
    protected double _relHum = RELHUM_INVALID;
    protected double _absHum = ABSHUM_INVALID;
    protected UpdateCallback _valueCallbackHumidity = null;
    protected TimedReportCallback _timedReportCallbackHumidity = null;

    /**
     * Deprecated UpdateCallback for Humidity
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YHumidity function, String functionValue);
    }

    /**
     * TimedReportCallback for Humidity
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YHumidity  function, YMeasure measure);
    }
    //--- (end of YHumidity definitions)


    /**
     *
     * @param func : functionid
     */
    protected YHumidity(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Humidity";
        //--- (YHumidity attributes initialization)
        //--- (end of YHumidity attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YHumidity(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YHumidity implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("relHum")) {
            _relHum = Math.round(json_val.getDouble("relHum") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("absHum")) {
            _absHum = Math.round(json_val.getDouble("absHum") * 1000.0 / 65536.0) / 1000.0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the primary unit for measuring humidity. That unit is a string.
     * If that strings starts with the letter 'g', the primary measured value is the absolute
     * humidity, in g/m3. Otherwise, the primary measured value will be the relative humidity
     * (RH), in per cents.
     *
     * Remember to call the saveToFlash() method of the module if the modification
     * must be kept.
     *
     * @param newval : a string corresponding to the primary unit for measuring humidity
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_unit(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("unit",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the primary unit for measuring humidity. That unit is a string.
     * If that strings starts with the letter 'g', the primary measured value is the absolute
     * humidity, in g/m3. Otherwise, the primary measured value will be the relative humidity
     * (RH), in per cents.
     *
     * Remember to call the saveToFlash() method of the module if the modification
     * must be kept.
     *
     * @param newval : a string corresponding to the primary unit for measuring humidity
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUnit(String newval)  throws YAPI_Exception
    {
        return set_unit(newval);
    }

    /**
     * Returns the current relative humidity, in per cents.
     *
     * @return a floating point number corresponding to the current relative humidity, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public double get_relHum() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return RELHUM_INVALID;
                }
            }
            res = _relHum;
        }
        return res;
    }

    /**
     * Returns the current relative humidity, in per cents.
     *
     * @return a floating point number corresponding to the current relative humidity, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public double getRelHum() throws YAPI_Exception
    {
        return get_relHum();
    }

    /**
     * Returns the current absolute humidity, in grams per cubic meter of air.
     *
     * @return a floating point number corresponding to the current absolute humidity, in grams per cubic meter of air
     *
     * @throws YAPI_Exception on error
     */
    public double get_absHum() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ABSHUM_INVALID;
                }
            }
            res = _absHum;
        }
        return res;
    }

    /**
     * Returns the current absolute humidity, in grams per cubic meter of air.
     *
     * @return a floating point number corresponding to the current absolute humidity, in grams per cubic meter of air
     *
     * @throws YAPI_Exception on error
     */
    public double getAbsHum() throws YAPI_Exception
    {
        return get_absHum();
    }

    /**
     * Retrieves a humidity sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the humidity sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YHumidity.isOnline() to test if the humidity sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a humidity sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the humidity sensor
     *
     * @return a YHumidity object allowing you to drive the humidity sensor.
     */
    public static YHumidity FindHumidity(String func)
    {
        YHumidity obj;
        synchronized (YAPI.class) {
            obj = (YHumidity) YFunction._FindFromCache("Humidity", func);
            if (obj == null) {
                obj = new YHumidity(func);
                YFunction._AddToCache("Humidity", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a humidity sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the humidity sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YHumidity.isOnline() to test if the humidity sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a humidity sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the humidity sensor
     *
     * @return a YHumidity object allowing you to drive the humidity sensor.
     */
    public static YHumidity FindHumidityInContext(YAPIContext yctx,String func)
    {
        YHumidity obj;
        synchronized (yctx) {
            obj = (YHumidity) YFunction._FindFromCacheInContext(yctx, "Humidity", func);
            if (obj == null) {
                obj = new YHumidity(yctx, func);
                YFunction._AddToCache("Humidity", func, obj);
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
        _valueCallbackHumidity = callback;
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
        if (_valueCallbackHumidity != null) {
            _valueCallbackHumidity.yNewValue(this, value);
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
        _timedReportCallbackHumidity = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackHumidity != null) {
            _timedReportCallbackHumidity.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of humidity sensors started using yFirstHumidity().
     *
     * @return a pointer to a YHumidity object, corresponding to
     *         a humidity sensor currently online, or a null pointer
     *         if there are no more humidity sensors to enumerate.
     */
    public YHumidity nextHumidity()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindHumidityInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of humidity sensors currently accessible.
     * Use the method YHumidity.nextHumidity() to iterate on
     * next humidity sensors.
     *
     * @return a pointer to a YHumidity object, corresponding to
     *         the first humidity sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YHumidity FirstHumidity()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Humidity");
        if (next_hwid == null)  return null;
        return FindHumidityInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of humidity sensors currently accessible.
     * Use the method YHumidity.nextHumidity() to iterate on
     * next humidity sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YHumidity object, corresponding to
     *         the first humidity sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YHumidity FirstHumidityInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Humidity");
        if (next_hwid == null)  return null;
        return FindHumidityInContext(yctx, next_hwid);
    }

    //--- (end of YHumidity implementation)
}

