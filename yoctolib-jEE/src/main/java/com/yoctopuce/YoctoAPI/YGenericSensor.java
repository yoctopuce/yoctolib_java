/*********************************************************************
 *
 * $Id: YGenericSensor.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindGenericSensor(), the high-level API for GenericSensor functions
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

//--- (YGenericSensor return codes)
//--- (end of YGenericSensor return codes)
//--- (YGenericSensor class start)
/**
 * YGenericSensor Class: GenericSensor function interface
 *
 * The YGenericSensor class allows you to read and configure Yoctopuce signal
 * transducers. It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, to access the autonomous datalogger.
 * This class adds the ability to configure the automatic conversion between the
 * measured signal and the corresponding engineering unit.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YGenericSensor extends YSensor
{
//--- (end of YGenericSensor class start)
//--- (YGenericSensor definitions)
    /**
     * invalid signalValue value
     */
    public static final double SIGNALVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid signalUnit value
     */
    public static final String SIGNALUNIT_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid signalRange value
     */
    public static final String SIGNALRANGE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid valueRange value
     */
    public static final String VALUERANGE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid signalBias value
     */
    public static final double SIGNALBIAS_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid signalSampling value
     */
    public static final int SIGNALSAMPLING_HIGH_RATE = 0;
    public static final int SIGNALSAMPLING_HIGH_RATE_FILTERED = 1;
    public static final int SIGNALSAMPLING_LOW_NOISE = 2;
    public static final int SIGNALSAMPLING_LOW_NOISE_FILTERED = 3;
    public static final int SIGNALSAMPLING_INVALID = -1;
    protected double _signalValue = SIGNALVALUE_INVALID;
    protected String _signalUnit = SIGNALUNIT_INVALID;
    protected String _signalRange = SIGNALRANGE_INVALID;
    protected String _valueRange = VALUERANGE_INVALID;
    protected double _signalBias = SIGNALBIAS_INVALID;
    protected int _signalSampling = SIGNALSAMPLING_INVALID;
    protected UpdateCallback _valueCallbackGenericSensor = null;
    protected TimedReportCallback _timedReportCallbackGenericSensor = null;

    /**
     * Deprecated UpdateCallback for GenericSensor
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YGenericSensor function, String functionValue);
    }

    /**
     * TimedReportCallback for GenericSensor
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YGenericSensor  function, YMeasure measure);
    }
    //--- (end of YGenericSensor definitions)


    /**
     *
     * @param func : functionid
     */
    protected YGenericSensor(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "GenericSensor";
        //--- (YGenericSensor attributes initialization)
        //--- (end of YGenericSensor attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YGenericSensor(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YGenericSensor implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("signalValue")) {
            _signalValue = Math.round(json_val.getDouble("signalValue") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("signalUnit")) {
            _signalUnit = json_val.getString("signalUnit");
        }
        if (json_val.has("signalRange")) {
            _signalRange = json_val.getString("signalRange");
        }
        if (json_val.has("valueRange")) {
            _valueRange = json_val.getString("valueRange");
        }
        if (json_val.has("signalBias")) {
            _signalBias = Math.round(json_val.getDouble("signalBias") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("signalSampling")) {
            _signalSampling = json_val.getInt("signalSampling");
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the measuring unit for the measured value.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the measured value
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
     * Changes the measuring unit for the measured value.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the measured value
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
     * Returns the current value of the electrical signal measured by the sensor.
     *
     *  @return a floating point number corresponding to the current value of the electrical signal
     * measured by the sensor
     *
     * @throws YAPI_Exception on error
     */
    public double get_signalValue() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SIGNALVALUE_INVALID;
                }
            }
            res = (double)Math.round(_signalValue * 1000) / 1000;
        }
        return res;
    }

    /**
     * Returns the current value of the electrical signal measured by the sensor.
     *
     *  @return a floating point number corresponding to the current value of the electrical signal
     * measured by the sensor
     *
     * @throws YAPI_Exception on error
     */
    public double getSignalValue() throws YAPI_Exception
    {
        return get_signalValue();
    }

    /**
     * Returns the measuring unit of the electrical signal used by the sensor.
     *
     * @return a string corresponding to the measuring unit of the electrical signal used by the sensor
     *
     * @throws YAPI_Exception on error
     */
    public String get_signalUnit() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration == 0) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SIGNALUNIT_INVALID;
                }
            }
            res = _signalUnit;
        }
        return res;
    }

    /**
     * Returns the measuring unit of the electrical signal used by the sensor.
     *
     * @return a string corresponding to the measuring unit of the electrical signal used by the sensor
     *
     * @throws YAPI_Exception on error
     */
    public String getSignalUnit() throws YAPI_Exception
    {
        return get_signalUnit();
    }

    /**
     * Returns the electric signal range used by the sensor.
     *
     * @return a string corresponding to the electric signal range used by the sensor
     *
     * @throws YAPI_Exception on error
     */
    public String get_signalRange() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SIGNALRANGE_INVALID;
                }
            }
            res = _signalRange;
        }
        return res;
    }

    /**
     * Returns the electric signal range used by the sensor.
     *
     * @return a string corresponding to the electric signal range used by the sensor
     *
     * @throws YAPI_Exception on error
     */
    public String getSignalRange() throws YAPI_Exception
    {
        return get_signalRange();
    }

    /**
     * Changes the electric signal range used by the sensor. Default value is "-999999.999...999999.999".
     *
     * @param newval : a string corresponding to the electric signal range used by the sensor
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_signalRange(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("signalRange",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the electric signal range used by the sensor. Default value is "-999999.999...999999.999".
     *
     * @param newval : a string corresponding to the electric signal range used by the sensor
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSignalRange(String newval)  throws YAPI_Exception
    {
        return set_signalRange(newval);
    }

    /**
     * Returns the physical value range measured by the sensor.
     *
     * @return a string corresponding to the physical value range measured by the sensor
     *
     * @throws YAPI_Exception on error
     */
    public String get_valueRange() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return VALUERANGE_INVALID;
                }
            }
            res = _valueRange;
        }
        return res;
    }

    /**
     * Returns the physical value range measured by the sensor.
     *
     * @return a string corresponding to the physical value range measured by the sensor
     *
     * @throws YAPI_Exception on error
     */
    public String getValueRange() throws YAPI_Exception
    {
        return get_valueRange();
    }

    /**
     * Changes the physical value range measured by the sensor. As a side effect, the range modification may
     * automatically modify the display resolution. Default value is "-999999.999...999999.999".
     *
     * @param newval : a string corresponding to the physical value range measured by the sensor
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_valueRange(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("valueRange",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the physical value range measured by the sensor. As a side effect, the range modification may
     * automatically modify the display resolution. Default value is "-999999.999...999999.999".
     *
     * @param newval : a string corresponding to the physical value range measured by the sensor
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setValueRange(String newval)  throws YAPI_Exception
    {
        return set_valueRange(newval);
    }

    /**
     * Changes the electric signal bias for zero shift adjustment.
     * If your electric signal reads positif when it should be zero, setup
     * a positive signalBias of the same value to fix the zero shift.
     *
     * @param newval : a floating point number corresponding to the electric signal bias for zero shift adjustment
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_signalBias(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("signalBias",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the electric signal bias for zero shift adjustment.
     * If your electric signal reads positif when it should be zero, setup
     * a positive signalBias of the same value to fix the zero shift.
     *
     * @param newval : a floating point number corresponding to the electric signal bias for zero shift adjustment
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSignalBias(double newval)  throws YAPI_Exception
    {
        return set_signalBias(newval);
    }

    /**
     * Returns the electric signal bias for zero shift adjustment.
     * A positive bias means that the signal is over-reporting the measure,
     * while a negative bias means that the signal is underreporting the measure.
     *
     * @return a floating point number corresponding to the electric signal bias for zero shift adjustment
     *
     * @throws YAPI_Exception on error
     */
    public double get_signalBias() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SIGNALBIAS_INVALID;
                }
            }
            res = _signalBias;
        }
        return res;
    }

    /**
     * Returns the electric signal bias for zero shift adjustment.
     * A positive bias means that the signal is over-reporting the measure,
     * while a negative bias means that the signal is underreporting the measure.
     *
     * @return a floating point number corresponding to the electric signal bias for zero shift adjustment
     *
     * @throws YAPI_Exception on error
     */
    public double getSignalBias() throws YAPI_Exception
    {
        return get_signalBias();
    }

    /**
     * Returns the electric signal sampling method to use.
     * The HIGH_RATE method uses the highest sampling frequency, without any filtering.
     * The HIGH_RATE_FILTERED method adds a windowed 7-sample median filter.
     * The LOW_NOISE method uses a reduced acquisition frequency to reduce noise.
     * The LOW_NOISE_FILTERED method combines a reduced frequency with the median filter
     * to get measures as stable as possible when working on a noisy signal.
     *
     *  @return a value among YGenericSensor.SIGNALSAMPLING_HIGH_RATE,
     *  YGenericSensor.SIGNALSAMPLING_HIGH_RATE_FILTERED, YGenericSensor.SIGNALSAMPLING_LOW_NOISE and
     * YGenericSensor.SIGNALSAMPLING_LOW_NOISE_FILTERED corresponding to the electric signal sampling method to use
     *
     * @throws YAPI_Exception on error
     */
    public int get_signalSampling() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SIGNALSAMPLING_INVALID;
                }
            }
            res = _signalSampling;
        }
        return res;
    }

    /**
     * Returns the electric signal sampling method to use.
     * The HIGH_RATE method uses the highest sampling frequency, without any filtering.
     * The HIGH_RATE_FILTERED method adds a windowed 7-sample median filter.
     * The LOW_NOISE method uses a reduced acquisition frequency to reduce noise.
     * The LOW_NOISE_FILTERED method combines a reduced frequency with the median filter
     * to get measures as stable as possible when working on a noisy signal.
     *
     *  @return a value among Y_SIGNALSAMPLING_HIGH_RATE, Y_SIGNALSAMPLING_HIGH_RATE_FILTERED,
     *  Y_SIGNALSAMPLING_LOW_NOISE and Y_SIGNALSAMPLING_LOW_NOISE_FILTERED corresponding to the electric
     * signal sampling method to use
     *
     * @throws YAPI_Exception on error
     */
    public int getSignalSampling() throws YAPI_Exception
    {
        return get_signalSampling();
    }

    /**
     * Changes the electric signal sampling method to use.
     * The HIGH_RATE method uses the highest sampling frequency, without any filtering.
     * The HIGH_RATE_FILTERED method adds a windowed 7-sample median filter.
     * The LOW_NOISE method uses a reduced acquisition frequency to reduce noise.
     * The LOW_NOISE_FILTERED method combines a reduced frequency with the median filter
     * to get measures as stable as possible when working on a noisy signal.
     *
     *  @param newval : a value among YGenericSensor.SIGNALSAMPLING_HIGH_RATE,
     *  YGenericSensor.SIGNALSAMPLING_HIGH_RATE_FILTERED, YGenericSensor.SIGNALSAMPLING_LOW_NOISE and
     * YGenericSensor.SIGNALSAMPLING_LOW_NOISE_FILTERED corresponding to the electric signal sampling method to use
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_signalSampling(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("signalSampling",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the electric signal sampling method to use.
     * The HIGH_RATE method uses the highest sampling frequency, without any filtering.
     * The HIGH_RATE_FILTERED method adds a windowed 7-sample median filter.
     * The LOW_NOISE method uses a reduced acquisition frequency to reduce noise.
     * The LOW_NOISE_FILTERED method combines a reduced frequency with the median filter
     * to get measures as stable as possible when working on a noisy signal.
     *
     *  @param newval : a value among Y_SIGNALSAMPLING_HIGH_RATE, Y_SIGNALSAMPLING_HIGH_RATE_FILTERED,
     *  Y_SIGNALSAMPLING_LOW_NOISE and Y_SIGNALSAMPLING_LOW_NOISE_FILTERED corresponding to the electric
     * signal sampling method to use
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSignalSampling(int newval)  throws YAPI_Exception
    {
        return set_signalSampling(newval);
    }

    /**
     * Retrieves a generic sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the generic sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YGenericSensor.isOnline() to test if the generic sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a generic sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the generic sensor
     *
     * @return a YGenericSensor object allowing you to drive the generic sensor.
     */
    public static YGenericSensor FindGenericSensor(String func)
    {
        YGenericSensor obj;
        synchronized (YAPI.class) {
            obj = (YGenericSensor) YFunction._FindFromCache("GenericSensor", func);
            if (obj == null) {
                obj = new YGenericSensor(func);
                YFunction._AddToCache("GenericSensor", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a generic sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the generic sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YGenericSensor.isOnline() to test if the generic sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a generic sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the generic sensor
     *
     * @return a YGenericSensor object allowing you to drive the generic sensor.
     */
    public static YGenericSensor FindGenericSensorInContext(YAPIContext yctx,String func)
    {
        YGenericSensor obj;
        synchronized (yctx) {
            obj = (YGenericSensor) YFunction._FindFromCacheInContext(yctx, "GenericSensor", func);
            if (obj == null) {
                obj = new YGenericSensor(yctx, func);
                YFunction._AddToCache("GenericSensor", func, obj);
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
        _valueCallbackGenericSensor = callback;
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
        if (_valueCallbackGenericSensor != null) {
            _valueCallbackGenericSensor.yNewValue(this, value);
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
        _timedReportCallbackGenericSensor = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackGenericSensor != null) {
            _timedReportCallbackGenericSensor.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Adjusts the signal bias so that the current signal value is need
     * precisely as zero.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int zeroAdjust() throws YAPI_Exception
    {
        double currSignal;
        double currBias;
        currSignal = get_signalValue();
        currBias = get_signalBias();
        return set_signalBias(currSignal + currBias);
    }

    /**
     * Continues the enumeration of generic sensors started using yFirstGenericSensor().
     *
     * @return a pointer to a YGenericSensor object, corresponding to
     *         a generic sensor currently online, or a null pointer
     *         if there are no more generic sensors to enumerate.
     */
    public YGenericSensor nextGenericSensor()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindGenericSensorInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of generic sensors currently accessible.
     * Use the method YGenericSensor.nextGenericSensor() to iterate on
     * next generic sensors.
     *
     * @return a pointer to a YGenericSensor object, corresponding to
     *         the first generic sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YGenericSensor FirstGenericSensor()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("GenericSensor");
        if (next_hwid == null)  return null;
        return FindGenericSensorInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of generic sensors currently accessible.
     * Use the method YGenericSensor.nextGenericSensor() to iterate on
     * next generic sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YGenericSensor object, corresponding to
     *         the first generic sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YGenericSensor FirstGenericSensorInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("GenericSensor");
        if (next_hwid == null)  return null;
        return FindGenericSensorInContext(yctx, next_hwid);
    }

    //--- (end of YGenericSensor implementation)
}

