/*********************************************************************
 *
 * $Id: YPowerOutput.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindPowerOutput(), the high-level API for PowerOutput functions
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

//--- (YPowerOutput return codes)
//--- (end of YPowerOutput return codes)
//--- (YPowerOutput class start)
/**
 * YPowerOutput Class: External power supply control interface
 *
 * Yoctopuce application programming interface allows you to control
 * the power ouput featured on some devices such as the Yocto-Serial.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YPowerOutput extends YFunction
{
//--- (end of YPowerOutput class start)
//--- (YPowerOutput definitions)
    /**
     * invalid voltage value
     */
    public static final int VOLTAGE_OFF = 0;
    public static final int VOLTAGE_OUT3V3 = 1;
    public static final int VOLTAGE_OUT5V = 2;
    public static final int VOLTAGE_INVALID = -1;
    protected int _voltage = VOLTAGE_INVALID;
    protected UpdateCallback _valueCallbackPowerOutput = null;

    /**
     * Deprecated UpdateCallback for PowerOutput
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YPowerOutput function, String functionValue);
    }

    /**
     * TimedReportCallback for PowerOutput
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YPowerOutput  function, YMeasure measure);
    }
    //--- (end of YPowerOutput definitions)


    /**
     *
     * @param func : functionid
     */
    protected YPowerOutput(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "PowerOutput";
        //--- (YPowerOutput attributes initialization)
        //--- (end of YPowerOutput attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YPowerOutput(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YPowerOutput implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("voltage")) {
            _voltage = json_val.getInt("voltage");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the voltage on the power ouput featured by
     * the module.
     *
     *  @return a value among YPowerOutput.VOLTAGE_OFF, YPowerOutput.VOLTAGE_OUT3V3 and
     * YPowerOutput.VOLTAGE_OUT5V corresponding to the voltage on the power ouput featured by
     *         the module
     *
     * @throws YAPI_Exception on error
     */
    public int get_voltage() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLTAGE_INVALID;
                }
            }
            res = _voltage;
        }
        return res;
    }

    /**
     * Returns the voltage on the power ouput featured by
     * the module.
     *
     *  @return a value among Y_VOLTAGE_OFF, Y_VOLTAGE_OUT3V3 and Y_VOLTAGE_OUT5V corresponding to the
     * voltage on the power ouput featured by
     *         the module
     *
     * @throws YAPI_Exception on error
     */
    public int getVoltage() throws YAPI_Exception
    {
        return get_voltage();
    }

    /**
     * Changes the voltage on the power output provided by the
     * module. Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     *  @param newval : a value among YPowerOutput.VOLTAGE_OFF, YPowerOutput.VOLTAGE_OUT3V3 and
     * YPowerOutput.VOLTAGE_OUT5V corresponding to the voltage on the power output provided by the
     *         module
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_voltage(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("voltage",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage on the power output provided by the
     * module. Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     *  @param newval : a value among Y_VOLTAGE_OFF, Y_VOLTAGE_OUT3V3 and Y_VOLTAGE_OUT5V corresponding to
     * the voltage on the power output provided by the
     *         module
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setVoltage(int newval)  throws YAPI_Exception
    {
        return set_voltage(newval);
    }

    /**
     * Retrieves a dual power  ouput control for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the power ouput control is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPowerOutput.isOnline() to test if the power ouput control is
     * indeed online at a given time. In case of ambiguity when looking for
     * a dual power  ouput control by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the power ouput control
     *
     * @return a YPowerOutput object allowing you to drive the power ouput control.
     */
    public static YPowerOutput FindPowerOutput(String func)
    {
        YPowerOutput obj;
        synchronized (YAPI.class) {
            obj = (YPowerOutput) YFunction._FindFromCache("PowerOutput", func);
            if (obj == null) {
                obj = new YPowerOutput(func);
                YFunction._AddToCache("PowerOutput", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a dual power  ouput control for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the power ouput control is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPowerOutput.isOnline() to test if the power ouput control is
     * indeed online at a given time. In case of ambiguity when looking for
     * a dual power  ouput control by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the power ouput control
     *
     * @return a YPowerOutput object allowing you to drive the power ouput control.
     */
    public static YPowerOutput FindPowerOutputInContext(YAPIContext yctx,String func)
    {
        YPowerOutput obj;
        synchronized (yctx) {
            obj = (YPowerOutput) YFunction._FindFromCacheInContext(yctx, "PowerOutput", func);
            if (obj == null) {
                obj = new YPowerOutput(yctx, func);
                YFunction._AddToCache("PowerOutput", func, obj);
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
        _valueCallbackPowerOutput = callback;
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
        if (_valueCallbackPowerOutput != null) {
            _valueCallbackPowerOutput.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of dual power ouput controls started using yFirstPowerOutput().
     *
     * @return a pointer to a YPowerOutput object, corresponding to
     *         a dual power  ouput control currently online, or a null pointer
     *         if there are no more dual power ouput controls to enumerate.
     */
    public YPowerOutput nextPowerOutput()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindPowerOutputInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of dual power ouput controls currently accessible.
     * Use the method YPowerOutput.nextPowerOutput() to iterate on
     * next dual power ouput controls.
     *
     * @return a pointer to a YPowerOutput object, corresponding to
     *         the first dual power ouput control currently online, or a null pointer
     *         if there are none.
     */
    public static YPowerOutput FirstPowerOutput()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("PowerOutput");
        if (next_hwid == null)  return null;
        return FindPowerOutputInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of dual power ouput controls currently accessible.
     * Use the method YPowerOutput.nextPowerOutput() to iterate on
     * next dual power ouput controls.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YPowerOutput object, corresponding to
     *         the first dual power ouput control currently online, or a null pointer
     *         if there are none.
     */
    public static YPowerOutput FirstPowerOutputInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("PowerOutput");
        if (next_hwid == null)  return null;
        return FindPowerOutputInContext(yctx, next_hwid);
    }

    //--- (end of YPowerOutput implementation)
}

