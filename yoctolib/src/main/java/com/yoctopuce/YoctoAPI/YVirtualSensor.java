/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindVirtualSensor(), the high-level API for VirtualSensor functions
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

//--- (YVirtualSensor return codes)
//--- (end of YVirtualSensor return codes)
//--- (YVirtualSensor yapiwrapper)
//--- (end of YVirtualSensor yapiwrapper)
//--- (YVirtualSensor class start)
/**
 * YVirtualSensor Class: virtual sensor control interface
 *
 * The YVirtualSensor class allows you to use Yoctopuce virtual sensors.
 * These sensors make it possible to show external data collected by the user
 * as a Yoctopuce Sensor. This class inherits from YSensor class the core
 * functions to read measurements, to register callback functions, and to access
 * the autonomous datalogger. It adds the ability to change the sensor value as
 * needed, or to mark current value as invalid.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YVirtualSensor extends YSensor
{
//--- (end of YVirtualSensor class start)
//--- (YVirtualSensor definitions)
    /**
     * invalid invalidValue value
     */
    public static final double INVALIDVALUE_INVALID = YAPI.INVALID_DOUBLE;
    protected double _invalidValue = INVALIDVALUE_INVALID;
    protected UpdateCallback _valueCallbackVirtualSensor = null;
    protected TimedReportCallback _timedReportCallbackVirtualSensor = null;

    /**
     * Deprecated UpdateCallback for VirtualSensor
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YVirtualSensor function, String functionValue);
    }

    /**
     * TimedReportCallback for VirtualSensor
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YVirtualSensor  function, YMeasure measure);
    }
    //--- (end of YVirtualSensor definitions)


    /**
     *
     * @param func : functionid
     */
    protected YVirtualSensor(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "VirtualSensor";
        //--- (YVirtualSensor attributes initialization)
        //--- (end of YVirtualSensor attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YVirtualSensor(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YVirtualSensor implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("invalidValue")) {
            _invalidValue = Math.round(json_val.getDouble("invalidValue") / 65.536) / 1000.0;
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
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUnit(String newval)  throws YAPI_Exception
    {
        return set_unit(newval);
    }

    /**
     * Changes the current value of the sensor (raw value, before calibration).
     *
     *  @param newval : a floating point number corresponding to the current value of the sensor (raw
     * value, before calibration)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_currentRawValue(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("currentRawValue",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current value of the sensor (raw value, before calibration).
     *
     *  @param newval : a floating point number corresponding to the current value of the sensor (raw
     * value, before calibration)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrentRawValue(double newval)  throws YAPI_Exception
    {
        return set_currentRawValue(newval);
    }

    public int set_sensorState(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("sensorState",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Changes the invalid value of the sensor, returned if the sensor is read when in invalid state
     * (for instance before having been set). Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : a floating point number corresponding to the invalid value of the sensor, returned
     * if the sensor is read when in invalid state
     *         (for instance before having been set)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_invalidValue(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("invalidValue",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the invalid value of the sensor, returned if the sensor is read when in invalid state
     * (for instance before having been set). Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : a floating point number corresponding to the invalid value of the sensor, returned
     * if the sensor is read when in invalid state
     *         (for instance before having been set)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setInvalidValue(double newval)  throws YAPI_Exception
    {
        return set_invalidValue(newval);
    }

    /**
     * Returns the invalid value of the sensor, returned if the sensor is read when in invalid state
     * (for instance before having been set).
     *
     *  @return a floating point number corresponding to the invalid value of the sensor, returned if the
     * sensor is read when in invalid state
     *         (for instance before having been set)
     *
     * @throws YAPI_Exception on error
     */
    public double get_invalidValue() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return INVALIDVALUE_INVALID;
                }
            }
            res = _invalidValue;
        }
        return res;
    }

    /**
     * Returns the invalid value of the sensor, returned if the sensor is read when in invalid state
     * (for instance before having been set).
     *
     *  @return a floating point number corresponding to the invalid value of the sensor, returned if the
     * sensor is read when in invalid state
     *         (for instance before having been set)
     *
     * @throws YAPI_Exception on error
     */
    public double getInvalidValue() throws YAPI_Exception
    {
        return get_invalidValue();
    }

    /**
     * Retrieves a virtual sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the virtual sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YVirtualSensor.isOnline() to test if the virtual sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a virtual sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the virtual sensor, for instance
     *         MyDevice.virtualSensor1.
     *
     * @return a YVirtualSensor object allowing you to drive the virtual sensor.
     */
    public static YVirtualSensor FindVirtualSensor(String func)
    {
        YVirtualSensor obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YVirtualSensor) YFunction._FindFromCache("VirtualSensor", func);
            if (obj == null) {
                obj = new YVirtualSensor(func);
                YFunction._AddToCache("VirtualSensor", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a virtual sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the virtual sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YVirtualSensor.isOnline() to test if the virtual sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a virtual sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the virtual sensor, for instance
     *         MyDevice.virtualSensor1.
     *
     * @return a YVirtualSensor object allowing you to drive the virtual sensor.
     */
    public static YVirtualSensor FindVirtualSensorInContext(YAPIContext yctx,String func)
    {
        YVirtualSensor obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YVirtualSensor) YFunction._FindFromCacheInContext(yctx, "VirtualSensor", func);
            if (obj == null) {
                obj = new YVirtualSensor(yctx, func);
                YFunction._AddToCache("VirtualSensor", func, obj);
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
        _valueCallbackVirtualSensor = callback;
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
        if (_valueCallbackVirtualSensor != null) {
            _valueCallbackVirtualSensor.yNewValue(this, value);
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
        _timedReportCallbackVirtualSensor = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackVirtualSensor != null) {
            _timedReportCallbackVirtualSensor.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Changes the current sensor state to invalid (as if no value would have been ever set).
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_sensorAsInvalid() throws YAPI_Exception
    {
        return set_sensorState(1);
    }

    /**
     * Continues the enumeration of virtual sensors started using yFirstVirtualSensor().
     * Caution: You can't make any assumption about the returned virtual sensors order.
     * If you want to find a specific a virtual sensor, use VirtualSensor.findVirtualSensor()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YVirtualSensor object, corresponding to
     *         a virtual sensor currently online, or a null pointer
     *         if there are no more virtual sensors to enumerate.
     */
    public YVirtualSensor nextVirtualSensor()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindVirtualSensorInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of virtual sensors currently accessible.
     * Use the method YVirtualSensor.nextVirtualSensor() to iterate on
     * next virtual sensors.
     *
     * @return a pointer to a YVirtualSensor object, corresponding to
     *         the first virtual sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YVirtualSensor FirstVirtualSensor()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("VirtualSensor");
        if (next_hwid == null)  return null;
        return FindVirtualSensorInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of virtual sensors currently accessible.
     * Use the method YVirtualSensor.nextVirtualSensor() to iterate on
     * next virtual sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YVirtualSensor object, corresponding to
     *         the first virtual sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YVirtualSensor FirstVirtualSensorInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("VirtualSensor");
        if (next_hwid == null)  return null;
        return FindVirtualSensorInContext(yctx, next_hwid);
    }

    //--- (end of YVirtualSensor implementation)
}

