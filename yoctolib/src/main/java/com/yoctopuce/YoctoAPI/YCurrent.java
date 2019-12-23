/*
 *
 *  $Id: YCurrent.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 *  Implements FindCurrent(), the high-level API for Current functions
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

//--- (YCurrent return codes)
//--- (end of YCurrent return codes)
//--- (YCurrent yapiwrapper)
//--- (end of YCurrent yapiwrapper)
//--- (YCurrent class start)
/**
 *  YCurrent Class: current sensor control interface, available for instance in the Yocto-Amp, the
 * Yocto-Motor-DC or the Yocto-Watt
 *
 * The YCurrent class allows you to read and configure Yoctopuce current sensors.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YCurrent extends YSensor
{
//--- (end of YCurrent class start)
//--- (YCurrent definitions)
    /**
     * invalid enabled value
     */
    public static final int ENABLED_FALSE = 0;
    public static final int ENABLED_TRUE = 1;
    public static final int ENABLED_INVALID = -1;
    protected int _enabled = ENABLED_INVALID;
    protected UpdateCallback _valueCallbackCurrent = null;
    protected TimedReportCallback _timedReportCallbackCurrent = null;

    /**
     * Deprecated UpdateCallback for Current
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YCurrent function, String functionValue);
    }

    /**
     * TimedReportCallback for Current
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YCurrent  function, YMeasure measure);
    }
    //--- (end of YCurrent definitions)


    /**
     *
     * @param func : functionid
     */
    protected YCurrent(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Current";
        //--- (YCurrent attributes initialization)
        //--- (end of YCurrent attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YCurrent(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YCurrent implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("enabled")) {
            _enabled = json_val.getInt("enabled") > 0 ? 1 : 0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the activation state of this input.
     *
     * @return either YCurrent.ENABLED_FALSE or YCurrent.ENABLED_TRUE, according to the activation state of this input
     *
     * @throws YAPI_Exception on error
     */
    public int get_enabled() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ENABLED_INVALID;
                }
            }
            res = _enabled;
        }
        return res;
    }

    /**
     * Returns the activation state of this input.
     *
     * @return either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the activation state of this input
     *
     * @throws YAPI_Exception on error
     */
    public int getEnabled() throws YAPI_Exception
    {
        return get_enabled();
    }

    /**
     * Changes the activation state of this voltage input. When AC measurements are disabled,
     * the device will always assume a DC signal, and vice-versa. When both AC and DC measurements
     * are active, the device switches between AC and DC mode based on the relative amplitude
     * of variations compared to the average value.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : either YCurrent.ENABLED_FALSE or YCurrent.ENABLED_TRUE, according to the activation
     * state of this voltage input
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_enabled(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("enabled",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation state of this voltage input. When AC measurements are disabled,
     * the device will always assume a DC signal, and vice-versa. When both AC and DC measurements
     * are active, the device switches between AC and DC mode based on the relative amplitude
     * of variations compared to the average value.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the activation state of this
     * voltage input
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setEnabled(int newval)  throws YAPI_Exception
    {
        return set_enabled(newval);
    }

    /**
     * Retrieves a current sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the current sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCurrent.isOnline() to test if the current sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a current sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the current sensor, for instance
     *         YAMPMK01.current1.
     *
     * @return a YCurrent object allowing you to drive the current sensor.
     */
    public static YCurrent FindCurrent(String func)
    {
        YCurrent obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YCurrent) YFunction._FindFromCache("Current", func);
            if (obj == null) {
                obj = new YCurrent(func);
                YFunction._AddToCache("Current", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a current sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the current sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCurrent.isOnline() to test if the current sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a current sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the current sensor, for instance
     *         YAMPMK01.current1.
     *
     * @return a YCurrent object allowing you to drive the current sensor.
     */
    public static YCurrent FindCurrentInContext(YAPIContext yctx,String func)
    {
        YCurrent obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YCurrent) YFunction._FindFromCacheInContext(yctx, "Current", func);
            if (obj == null) {
                obj = new YCurrent(yctx, func);
                YFunction._AddToCache("Current", func, obj);
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
        _valueCallbackCurrent = callback;
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
        if (_valueCallbackCurrent != null) {
            _valueCallbackCurrent.yNewValue(this, value);
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
        _timedReportCallbackCurrent = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackCurrent != null) {
            _timedReportCallbackCurrent.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of current sensors started using yFirstCurrent().
     * Caution: You can't make any assumption about the returned current sensors order.
     * If you want to find a specific a current sensor, use Current.findCurrent()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YCurrent object, corresponding to
     *         a current sensor currently online, or a null pointer
     *         if there are no more current sensors to enumerate.
     */
    public YCurrent nextCurrent()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindCurrentInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of current sensors currently accessible.
     * Use the method YCurrent.nextCurrent() to iterate on
     * next current sensors.
     *
     * @return a pointer to a YCurrent object, corresponding to
     *         the first current sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YCurrent FirstCurrent()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Current");
        if (next_hwid == null)  return null;
        return FindCurrentInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of current sensors currently accessible.
     * Use the method YCurrent.nextCurrent() to iterate on
     * next current sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YCurrent object, corresponding to
     *         the first current sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YCurrent FirstCurrentInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Current");
        if (next_hwid == null)  return null;
        return FindCurrentInContext(yctx, next_hwid);
    }

    //--- (end of YCurrent implementation)
}

