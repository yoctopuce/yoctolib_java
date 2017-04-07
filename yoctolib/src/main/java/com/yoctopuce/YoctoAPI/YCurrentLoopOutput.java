/*********************************************************************
 *
 * $Id: YCurrentLoopOutput.java 27108 2017-04-06 22:18:22Z seb $
 *
 * Implements FindCurrentLoopOutput(), the high-level API for CurrentLoopOutput functions
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
import java.util.Locale;

//--- (YCurrentLoopOutput return codes)
//--- (end of YCurrentLoopOutput return codes)
//--- (YCurrentLoopOutput class start)
/**
 * YCurrentLoopOutput Class: CurrentLoopOutput function interface
 *
 * The Yoctopuce application programming interface allows you to change the value of the 4-20mA
 * output as well as to know the current loop state.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YCurrentLoopOutput extends YFunction
{
//--- (end of YCurrentLoopOutput class start)
//--- (YCurrentLoopOutput definitions)
    /**
     * invalid current value
     */
    public static final double CURRENT_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid currentTransition value
     */
    public static final String CURRENTTRANSITION_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid currentAtStartUp value
     */
    public static final double CURRENTATSTARTUP_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid loopPower value
     */
    public static final int LOOPPOWER_NOPWR = 0;
    public static final int LOOPPOWER_LOWPWR = 1;
    public static final int LOOPPOWER_POWEROK = 2;
    public static final int LOOPPOWER_INVALID = -1;
    protected double _current = CURRENT_INVALID;
    protected String _currentTransition = CURRENTTRANSITION_INVALID;
    protected double _currentAtStartUp = CURRENTATSTARTUP_INVALID;
    protected int _loopPower = LOOPPOWER_INVALID;
    protected UpdateCallback _valueCallbackCurrentLoopOutput = null;

    /**
     * Deprecated UpdateCallback for CurrentLoopOutput
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YCurrentLoopOutput function, String functionValue);
    }

    /**
     * TimedReportCallback for CurrentLoopOutput
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YCurrentLoopOutput  function, YMeasure measure);
    }
    //--- (end of YCurrentLoopOutput definitions)


    /**
     *
     * @param func : functionid
     */
    protected YCurrentLoopOutput(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "CurrentLoopOutput";
        //--- (YCurrentLoopOutput attributes initialization)
        //--- (end of YCurrentLoopOutput attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YCurrentLoopOutput(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YCurrentLoopOutput implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("current")) {
            _current = Math.round(json_val.getDouble("current") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("currentTransition")) {
            _currentTransition = json_val.getString("currentTransition");
        }
        if (json_val.has("currentAtStartUp")) {
            _currentAtStartUp = Math.round(json_val.getDouble("currentAtStartUp") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("loopPower")) {
            _loopPower = json_val.getInt("loopPower");
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the current loop, the valid range is from 3 to 21mA. If the loop is
     * not propely powered, the  target current is not reached and
     * loopPower is set to LOWPWR.
     *
     * @param newval : a floating point number corresponding to the current loop, the valid range is from 3 to 21mA
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_current(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("current",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current loop, the valid range is from 3 to 21mA. If the loop is
     * not propely powered, the  target current is not reached and
     * loopPower is set to LOWPWR.
     *
     * @param newval : a floating point number corresponding to the current loop, the valid range is from 3 to 21mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrent(double newval)  throws YAPI_Exception
    {
        return set_current(newval);
    }

    /**
     * Returns the loop current set point in mA.
     *
     * @return a floating point number corresponding to the loop current set point in mA
     *
     * @throws YAPI_Exception on error
     */
    public double get_current() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENT_INVALID;
                }
            }
            res = _current;
        }
        return res;
    }

    /**
     * Returns the loop current set point in mA.
     *
     * @return a floating point number corresponding to the loop current set point in mA
     *
     * @throws YAPI_Exception on error
     */
    public double getCurrent() throws YAPI_Exception
    {
        return get_current();
    }

    public String get_currentTransition() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENTTRANSITION_INVALID;
                }
            }
            res = _currentTransition;
        }
        return res;
    }

    public int set_currentTransition(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("currentTransition",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Changes the loop current at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the loop current at device start up
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_currentAtStartUp(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("currentAtStartUp",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the loop current at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the loop current at device start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrentAtStartUp(double newval)  throws YAPI_Exception
    {
        return set_currentAtStartUp(newval);
    }

    /**
     * Returns the current in the loop at device startup, in mA.
     *
     * @return a floating point number corresponding to the current in the loop at device startup, in mA
     *
     * @throws YAPI_Exception on error
     */
    public double get_currentAtStartUp() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENTATSTARTUP_INVALID;
                }
            }
            res = _currentAtStartUp;
        }
        return res;
    }

    /**
     * Returns the current in the loop at device startup, in mA.
     *
     * @return a floating point number corresponding to the current in the loop at device startup, in mA
     *
     * @throws YAPI_Exception on error
     */
    public double getCurrentAtStartUp() throws YAPI_Exception
    {
        return get_currentAtStartUp();
    }

    /**
     * Returns the loop powerstate.  POWEROK: the loop
     * is powered. NOPWR: the loop in not powered. LOWPWR: the loop is not
     * powered enough to maintain the current required (insufficient voltage).
     *
     *  @return a value among YCurrentLoopOutput.LOOPPOWER_NOPWR, YCurrentLoopOutput.LOOPPOWER_LOWPWR and
     * YCurrentLoopOutput.LOOPPOWER_POWEROK corresponding to the loop powerstate
     *
     * @throws YAPI_Exception on error
     */
    public int get_loopPower() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return LOOPPOWER_INVALID;
                }
            }
            res = _loopPower;
        }
        return res;
    }

    /**
     * Returns the loop powerstate.  POWEROK: the loop
     * is powered. NOPWR: the loop in not powered. LOWPWR: the loop is not
     * powered enough to maintain the current required (insufficient voltage).
     *
     *  @return a value among Y_LOOPPOWER_NOPWR, Y_LOOPPOWER_LOWPWR and Y_LOOPPOWER_POWEROK corresponding
     * to the loop powerstate
     *
     * @throws YAPI_Exception on error
     */
    public int getLoopPower() throws YAPI_Exception
    {
        return get_loopPower();
    }

    /**
     * Retrieves a 4-20mA output for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the 4-20mA output is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCurrentLoopOutput.isOnline() to test if the 4-20mA output is
     * indeed online at a given time. In case of ambiguity when looking for
     * a 4-20mA output by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the 4-20mA output
     *
     * @return a YCurrentLoopOutput object allowing you to drive the 4-20mA output.
     */
    public static YCurrentLoopOutput FindCurrentLoopOutput(String func)
    {
        YCurrentLoopOutput obj;
        synchronized (YAPI.class) {
            obj = (YCurrentLoopOutput) YFunction._FindFromCache("CurrentLoopOutput", func);
            if (obj == null) {
                obj = new YCurrentLoopOutput(func);
                YFunction._AddToCache("CurrentLoopOutput", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a 4-20mA output for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the 4-20mA output is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCurrentLoopOutput.isOnline() to test if the 4-20mA output is
     * indeed online at a given time. In case of ambiguity when looking for
     * a 4-20mA output by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the 4-20mA output
     *
     * @return a YCurrentLoopOutput object allowing you to drive the 4-20mA output.
     */
    public static YCurrentLoopOutput FindCurrentLoopOutputInContext(YAPIContext yctx,String func)
    {
        YCurrentLoopOutput obj;
        synchronized (yctx) {
            obj = (YCurrentLoopOutput) YFunction._FindFromCacheInContext(yctx, "CurrentLoopOutput", func);
            if (obj == null) {
                obj = new YCurrentLoopOutput(yctx, func);
                YFunction._AddToCache("CurrentLoopOutput", func, obj);
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
        _valueCallbackCurrentLoopOutput = callback;
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
        if (_valueCallbackCurrentLoopOutput != null) {
            _valueCallbackCurrentLoopOutput.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Performs a smooth transistion of current flowing in the loop. Any current explicit
     * change cancels any ongoing transition process.
     *
     * @param mA_target   : new current value at the end of the transition
     *         (floating-point number, representing the transition duration in mA)
     * @param ms_duration : total duration of the transition, in milliseconds
     *
     * @return YAPI.SUCCESS when the call succeeds.
     */
    public int currentMove(double mA_target,int ms_duration) throws YAPI_Exception
    {
        String newval;
        if (mA_target < 3.0) {
            mA_target  = 3.0;
        }
        if (mA_target > 21.0) {
            mA_target = 21.0;
        }
        newval = String.format(Locale.US, "%d:%d", (int) (double)Math.round(mA_target*1000),ms_duration);
        
        return set_currentTransition(newval);
    }

    /**
     * Continues the enumeration of 4-20mA outputs started using yFirstCurrentLoopOutput().
     *
     * @return a pointer to a YCurrentLoopOutput object, corresponding to
     *         a 4-20mA output currently online, or a null pointer
     *         if there are no more 4-20mA outputs to enumerate.
     */
    public YCurrentLoopOutput nextCurrentLoopOutput()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindCurrentLoopOutputInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of 4-20mA outputs currently accessible.
     * Use the method YCurrentLoopOutput.nextCurrentLoopOutput() to iterate on
     * next 4-20mA outputs.
     *
     * @return a pointer to a YCurrentLoopOutput object, corresponding to
     *         the first 4-20mA output currently online, or a null pointer
     *         if there are none.
     */
    public static YCurrentLoopOutput FirstCurrentLoopOutput()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("CurrentLoopOutput");
        if (next_hwid == null)  return null;
        return FindCurrentLoopOutputInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of 4-20mA outputs currently accessible.
     * Use the method YCurrentLoopOutput.nextCurrentLoopOutput() to iterate on
     * next 4-20mA outputs.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YCurrentLoopOutput object, corresponding to
     *         the first 4-20mA output currently online, or a null pointer
     *         if there are none.
     */
    public static YCurrentLoopOutput FirstCurrentLoopOutputInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("CurrentLoopOutput");
        if (next_hwid == null)  return null;
        return FindCurrentLoopOutputInContext(yctx, next_hwid);
    }

    //--- (end of YCurrentLoopOutput implementation)
}

