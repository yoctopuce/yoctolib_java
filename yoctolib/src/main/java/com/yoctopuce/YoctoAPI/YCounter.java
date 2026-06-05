/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindCounter(), the high-level API for Counter functions
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

//--- (YCounter return codes)
//--- (end of YCounter return codes)
//--- (YCounter yapiwrapper)
//--- (end of YCounter yapiwrapper)
//--- (YCounter class start)
/**
 * YCounter Class: counter control interface
 *
 * The YCounter class allows you to read and configure Yoctopuce gcounters.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YCounter extends YSensor
{
//--- (end of YCounter class start)
//--- (YCounter definitions)
    /**
     * invalid decimalMode value
     */
    public static final int DECIMALMODE_FALSE = 0;
    public static final int DECIMALMODE_TRUE = 1;
    public static final int DECIMALMODE_INVALID = -1;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _decimalMode = DECIMALMODE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackCounter = null;
    protected TimedReportCallback _timedReportCallbackCounter = null;

    /**
     * Deprecated UpdateCallback for Counter
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YCounter function, String functionValue);
    }

    /**
     * TimedReportCallback for Counter
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YCounter  function, YMeasure measure);
    }
    //--- (end of YCounter definitions)


    /**
     *
     * @param func : functionid
     */
    protected YCounter(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Counter";
        //--- (YCounter attributes initialization)
        //--- (end of YCounter attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YCounter(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YCounter implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("decimalMode")) {
            _decimalMode = json_val.getInt("decimalMode") > 0 ? 1 : 0;
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns a value indicating if the senseur compute whole or fractional values.
     *
     *  @return either YCounter.DECIMALMODE_FALSE or YCounter.DECIMALMODE_TRUE, according to a value
     * indicating if the senseur compute whole or fractional values
     *
     * @throws YAPI_Exception on error
     */
    public int get_decimalMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DECIMALMODE_INVALID;
                }
            }
            res = _decimalMode;
        }
        return res;
    }

    /**
     * Returns a value indicating if the senseur compute whole or fractional values.
     *
     *  @return either YCounter.DECIMALMODE_FALSE or YCounter.DECIMALMODE_TRUE, according to a value
     * indicating if the senseur compute whole or fractional values
     *
     * @throws YAPI_Exception on error
     */
    public int getDecimalMode() throws YAPI_Exception
    {
        return get_decimalMode();
    }

    /**
     * Changes the sensor's operating mode so that it computes integer or decimal values.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : either YCounter.DECIMALMODE_FALSE or YCounter.DECIMALMODE_TRUE, according to the
     * sensor's operating mode so that it computes integer or decimal values
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_decimalMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("decimalMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the sensor's operating mode so that it computes integer or decimal values.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : either YCounter.DECIMALMODE_FALSE or YCounter.DECIMALMODE_TRUE, according to the
     * sensor's operating mode so that it computes integer or decimal values
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDecimalMode(int newval)  throws YAPI_Exception
    {
        return set_decimalMode(newval);
    }

    public String get_command() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COMMAND_INVALID;
                }
            }
            res = _command;
        }
        return res;
    }

    public int set_command(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("command",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Retrieves a counter for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the counter is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCounter.isOnline() to test if the counter is
     * indeed online at a given time. In case of ambiguity when looking for
     * a counter by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the counter, for instance
     *         MyDevice.counter.
     *
     * @return a YCounter object allowing you to drive the counter.
     */
    public static YCounter FindCounter(String func)
    {
        YCounter obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YCounter) YFunction._FindFromCache("Counter", func);
            if (obj == null) {
                obj = new YCounter(func);
                YFunction._AddToCache("Counter", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a counter for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the counter is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCounter.isOnline() to test if the counter is
     * indeed online at a given time. In case of ambiguity when looking for
     * a counter by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the counter, for instance
     *         MyDevice.counter.
     *
     * @return a YCounter object allowing you to drive the counter.
     */
    public static YCounter FindCounterInContext(YAPIContext yctx,String func)
    {
        YCounter obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YCounter) YFunction._FindFromCacheInContext(yctx, "Counter", func);
            if (obj == null) {
                obj = new YCounter(yctx, func);
                YFunction._AddToCache("Counter", func, obj);
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
        _valueCallbackCounter = callback;
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
        if (_valueCallbackCounter != null) {
            _valueCallbackCounter.yNewValue(this, value);
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
        _timedReportCallbackCounter = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackCounter != null) {
            _timedReportCallbackCounter.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    public int sendCommand(String command) throws YAPI_Exception
    {
        return set_command(command);
    }

    /**
     * Reset the counter to zero.
     *
     * @return YAPI.SUCCESS if the call succeeds. Please note that this function only resets
     *         the integer part of the counter. In CONTINUOUS mode, the decimal part is calculated
     *         from the angle measured by the sensor. To set the decimal part of the sensor to zero,
     *         the origin of the sensor must be changed with the YOrientation.zero().
     *
     * @throws YAPI_Exception on error
     */
    public int zero() throws YAPI_Exception
    {
        return sendCommand("Z");
    }

    /**
     * Continues the enumeration of gcounters started using yFirstCounter().
     * Caution: You can't make any assumption about the returned gcounters order.
     * If you want to find a specific a counter, use Counter.findCounter()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YCounter object, corresponding to
     *         a counter currently online, or a null pointer
     *         if there are no more gcounters to enumerate.
     */
    public YCounter nextCounter()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindCounterInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of gcounters currently accessible.
     * Use the method YCounter.nextCounter() to iterate on
     * next gcounters.
     *
     * @return a pointer to a YCounter object, corresponding to
     *         the first counter currently online, or a null pointer
     *         if there are none.
     */
    public static YCounter FirstCounter()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Counter");
        if (next_hwid == null)  return null;
        return FindCounterInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of gcounters currently accessible.
     * Use the method YCounter.nextCounter() to iterate on
     * next gcounters.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YCounter object, corresponding to
     *         the first counter currently online, or a null pointer
     *         if there are none.
     */
    public static YCounter FirstCounterInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Counter");
        if (next_hwid == null)  return null;
        return FindCounterInContext(yctx, next_hwid);
    }

    //--- (end of YCounter implementation)
}

