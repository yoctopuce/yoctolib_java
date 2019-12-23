/*
 *
 *  $Id: YVoltageOutput.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 *  Implements FindVoltageOutput(), the high-level API for VoltageOutput functions
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
import java.util.Locale;

//--- (YVoltageOutput return codes)
//--- (end of YVoltageOutput return codes)
//--- (YVoltageOutput yapiwrapper)
//--- (end of YVoltageOutput yapiwrapper)
//--- (YVoltageOutput class start)
/**
 * YVoltageOutput Class: voltage output control interface, available for instance in the Yocto-0-10V-Tx
 *
 * The YVoltageOutput class allows you to drive a voltage output.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YVoltageOutput extends YFunction
{
//--- (end of YVoltageOutput class start)
//--- (YVoltageOutput definitions)
    /**
     * invalid currentVoltage value
     */
    public static final double CURRENTVOLTAGE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid voltageTransition value
     */
    public static final String VOLTAGETRANSITION_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid voltageAtStartUp value
     */
    public static final double VOLTAGEATSTARTUP_INVALID = YAPI.INVALID_DOUBLE;
    protected double _currentVoltage = CURRENTVOLTAGE_INVALID;
    protected String _voltageTransition = VOLTAGETRANSITION_INVALID;
    protected double _voltageAtStartUp = VOLTAGEATSTARTUP_INVALID;
    protected UpdateCallback _valueCallbackVoltageOutput = null;

    /**
     * Deprecated UpdateCallback for VoltageOutput
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YVoltageOutput function, String functionValue);
    }

    /**
     * TimedReportCallback for VoltageOutput
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YVoltageOutput  function, YMeasure measure);
    }
    //--- (end of YVoltageOutput definitions)


    /**
     *
     * @param func : functionid
     */
    protected YVoltageOutput(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "VoltageOutput";
        //--- (YVoltageOutput attributes initialization)
        //--- (end of YVoltageOutput attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YVoltageOutput(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YVoltageOutput implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("currentVoltage")) {
            _currentVoltage = Math.round(json_val.getDouble("currentVoltage") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("voltageTransition")) {
            _voltageTransition = json_val.getString("voltageTransition");
        }
        if (json_val.has("voltageAtStartUp")) {
            _voltageAtStartUp = Math.round(json_val.getDouble("voltageAtStartUp") * 1000.0 / 65536.0) / 1000.0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the output voltage, in V. Valid range is from 0 to 10V.
     *
     * @param newval : a floating point number corresponding to the output voltage, in V
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_currentVoltage(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("currentVoltage",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the output voltage, in V. Valid range is from 0 to 10V.
     *
     * @param newval : a floating point number corresponding to the output voltage, in V
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrentVoltage(double newval)  throws YAPI_Exception
    {
        return set_currentVoltage(newval);
    }

    /**
     * Returns the output voltage set point, in V.
     *
     * @return a floating point number corresponding to the output voltage set point, in V
     *
     * @throws YAPI_Exception on error
     */
    public double get_currentVoltage() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENTVOLTAGE_INVALID;
                }
            }
            res = _currentVoltage;
        }
        return res;
    }

    /**
     * Returns the output voltage set point, in V.
     *
     * @return a floating point number corresponding to the output voltage set point, in V
     *
     * @throws YAPI_Exception on error
     */
    public double getCurrentVoltage() throws YAPI_Exception
    {
        return get_currentVoltage();
    }

    public String get_voltageTransition() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLTAGETRANSITION_INVALID;
                }
            }
            res = _voltageTransition;
        }
        return res;
    }

    public int set_voltageTransition(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("voltageTransition",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Changes the output voltage at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the output voltage at device start up
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_voltageAtStartUp(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("voltageAtStartUp",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the output voltage at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the output voltage at device start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setVoltageAtStartUp(double newval)  throws YAPI_Exception
    {
        return set_voltageAtStartUp(newval);
    }

    /**
     * Returns the selected voltage output at device startup, in V.
     *
     * @return a floating point number corresponding to the selected voltage output at device startup, in V
     *
     * @throws YAPI_Exception on error
     */
    public double get_voltageAtStartUp() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLTAGEATSTARTUP_INVALID;
                }
            }
            res = _voltageAtStartUp;
        }
        return res;
    }

    /**
     * Returns the selected voltage output at device startup, in V.
     *
     * @return a floating point number corresponding to the selected voltage output at device startup, in V
     *
     * @throws YAPI_Exception on error
     */
    public double getVoltageAtStartUp() throws YAPI_Exception
    {
        return get_voltageAtStartUp();
    }

    /**
     * Retrieves a voltage output for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the voltage output is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YVoltageOutput.isOnline() to test if the voltage output is
     * indeed online at a given time. In case of ambiguity when looking for
     * a voltage output by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the voltage output, for instance
     *         TX010V01.voltageOutput1.
     *
     * @return a YVoltageOutput object allowing you to drive the voltage output.
     */
    public static YVoltageOutput FindVoltageOutput(String func)
    {
        YVoltageOutput obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YVoltageOutput) YFunction._FindFromCache("VoltageOutput", func);
            if (obj == null) {
                obj = new YVoltageOutput(func);
                YFunction._AddToCache("VoltageOutput", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a voltage output for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the voltage output is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YVoltageOutput.isOnline() to test if the voltage output is
     * indeed online at a given time. In case of ambiguity when looking for
     * a voltage output by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the voltage output, for instance
     *         TX010V01.voltageOutput1.
     *
     * @return a YVoltageOutput object allowing you to drive the voltage output.
     */
    public static YVoltageOutput FindVoltageOutputInContext(YAPIContext yctx,String func)
    {
        YVoltageOutput obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YVoltageOutput) YFunction._FindFromCacheInContext(yctx, "VoltageOutput", func);
            if (obj == null) {
                obj = new YVoltageOutput(yctx, func);
                YFunction._AddToCache("VoltageOutput", func, obj);
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
        _valueCallbackVoltageOutput = callback;
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
        if (_valueCallbackVoltageOutput != null) {
            _valueCallbackVoltageOutput.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Performs a smooth transition of output voltage. Any explicit voltage
     * change cancels any ongoing transition process.
     *
     * @param V_target   : new output voltage value at the end of the transition
     *         (floating-point number, representing the end voltage in V)
     * @param ms_duration : total duration of the transition, in milliseconds
     *
     * @return YAPI.SUCCESS when the call succeeds.
     */
    public int voltageMove(double V_target,int ms_duration) throws YAPI_Exception
    {
        String newval;
        if (V_target < 0.0) {
            V_target  = 0.0;
        }
        if (V_target > 10.0) {
            V_target = 10.0;
        }
        newval = String.format(Locale.US, "%d:%d", (int) (double)Math.round(V_target*65536),ms_duration);

        return set_voltageTransition(newval);
    }

    /**
     * Continues the enumeration of voltage outputs started using yFirstVoltageOutput().
     * Caution: You can't make any assumption about the returned voltage outputs order.
     * If you want to find a specific a voltage output, use VoltageOutput.findVoltageOutput()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YVoltageOutput object, corresponding to
     *         a voltage output currently online, or a null pointer
     *         if there are no more voltage outputs to enumerate.
     */
    public YVoltageOutput nextVoltageOutput()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindVoltageOutputInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of voltage outputs currently accessible.
     * Use the method YVoltageOutput.nextVoltageOutput() to iterate on
     * next voltage outputs.
     *
     * @return a pointer to a YVoltageOutput object, corresponding to
     *         the first voltage output currently online, or a null pointer
     *         if there are none.
     */
    public static YVoltageOutput FirstVoltageOutput()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("VoltageOutput");
        if (next_hwid == null)  return null;
        return FindVoltageOutputInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of voltage outputs currently accessible.
     * Use the method YVoltageOutput.nextVoltageOutput() to iterate on
     * next voltage outputs.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YVoltageOutput object, corresponding to
     *         the first voltage output currently online, or a null pointer
     *         if there are none.
     */
    public static YVoltageOutput FirstVoltageOutputInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("VoltageOutput");
        if (next_hwid == null)  return null;
        return FindVoltageOutputInContext(yctx, next_hwid);
    }

    //--- (end of YVoltageOutput implementation)
}

