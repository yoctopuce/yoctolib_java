/*
 *
 *  $Id: YArithmeticSensor.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 *  Implements FindArithmeticSensor(), the high-level API for ArithmeticSensor functions
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
import java.util.ArrayList;

//--- (YArithmeticSensor return codes)
//--- (end of YArithmeticSensor return codes)
//--- (YArithmeticSensor yapiwrapper)
//--- (end of YArithmeticSensor yapiwrapper)
//--- (YArithmeticSensor class start)
/**
 *  YArithmeticSensor Class: arithmetic sensor control interface, available for instance in the
 * Yocto-MaxiMicroVolt-Rx
 *
 * The YArithmeticSensor class allows some Yoctopuce devices to compute in real-time
 * values based on an arithmetic formula involving one or more measured signals as
 * well as the temperature. As for any physical sensor, the computed values can be
 * read by callback and stored in the built-in datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YArithmeticSensor extends YSensor
{
//--- (end of YArithmeticSensor class start)
//--- (YArithmeticSensor definitions)
    /**
     * invalid description value
     */
    public static final String DESCRIPTION_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected String _description = DESCRIPTION_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackArithmeticSensor = null;
    protected TimedReportCallback _timedReportCallbackArithmeticSensor = null;

    /**
     * Deprecated UpdateCallback for ArithmeticSensor
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YArithmeticSensor function, String functionValue);
    }

    /**
     * TimedReportCallback for ArithmeticSensor
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YArithmeticSensor  function, YMeasure measure);
    }
    //--- (end of YArithmeticSensor definitions)


    /**
     *
     * @param func : functionid
     */
    protected YArithmeticSensor(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "ArithmeticSensor";
        //--- (YArithmeticSensor attributes initialization)
        //--- (end of YArithmeticSensor attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YArithmeticSensor(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YArithmeticSensor implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("description")) {
            _description = json_val.getString("description");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the measuring unit for the arithmetic sensor.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the arithmetic sensor
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
     * Changes the measuring unit for the arithmetic sensor.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the arithmetic sensor
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
     * Returns a short informative description of the formula.
     *
     * @return a string corresponding to a short informative description of the formula
     *
     * @throws YAPI_Exception on error
     */
    public String get_description() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DESCRIPTION_INVALID;
                }
            }
            res = _description;
        }
        return res;
    }

    /**
     * Returns a short informative description of the formula.
     *
     * @return a string corresponding to a short informative description of the formula
     *
     * @throws YAPI_Exception on error
     */
    public String getDescription() throws YAPI_Exception
    {
        return get_description();
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
     * Retrieves an arithmetic sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the arithmetic sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YArithmeticSensor.isOnline() to test if the arithmetic sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * an arithmetic sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the arithmetic sensor, for instance
     *         RXUVOLT1.arithmeticSensor1.
     *
     * @return a YArithmeticSensor object allowing you to drive the arithmetic sensor.
     */
    public static YArithmeticSensor FindArithmeticSensor(String func)
    {
        YArithmeticSensor obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YArithmeticSensor) YFunction._FindFromCache("ArithmeticSensor", func);
            if (obj == null) {
                obj = new YArithmeticSensor(func);
                YFunction._AddToCache("ArithmeticSensor", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves an arithmetic sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the arithmetic sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YArithmeticSensor.isOnline() to test if the arithmetic sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * an arithmetic sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the arithmetic sensor, for instance
     *         RXUVOLT1.arithmeticSensor1.
     *
     * @return a YArithmeticSensor object allowing you to drive the arithmetic sensor.
     */
    public static YArithmeticSensor FindArithmeticSensorInContext(YAPIContext yctx,String func)
    {
        YArithmeticSensor obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YArithmeticSensor) YFunction._FindFromCacheInContext(yctx, "ArithmeticSensor", func);
            if (obj == null) {
                obj = new YArithmeticSensor(yctx, func);
                YFunction._AddToCache("ArithmeticSensor", func, obj);
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
        _valueCallbackArithmeticSensor = callback;
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
        if (_valueCallbackArithmeticSensor != null) {
            _valueCallbackArithmeticSensor.yNewValue(this, value);
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
        _timedReportCallbackArithmeticSensor = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackArithmeticSensor != null) {
            _timedReportCallbackArithmeticSensor.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Defines the arithmetic function by means of an algebraic expression. The expression
     * may include references to device sensors, by their physical or logical name, to
     * usual math functions and to auxiliary functions defined separately.
     *
     * @param expr : the algebraic expression defining the function.
     * @param descr : short informative description of the expression.
     *
     * @return the current expression value if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public double defineExpression(String expr,String descr) throws YAPI_Exception
    {
        String id;
        String fname;
        String content;
        byte[] data;
        String diags;
        double resval;
        id = get_functionId();
        id = (id).substring( 16,  16 + (id).length() - 16);
        fname = String.format(Locale.US, "arithmExpr%s.txt",id);

        content = String.format(Locale.US, "// %s\n%s", descr,expr);
        data = _uploadEx(fname, (content).getBytes());
        diags = new String(data);
        //noinspection DoubleNegation
        if (!((diags).substring(0, 8).equals("Result: "))) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  diags);}
        resval = Double.valueOf((diags).substring( 8,  8 + (diags).length()-8));
        return resval;
    }

    /**
     * Retrieves the algebraic expression defining the arithmetic function, as previously
     * configured using the defineExpression function.
     *
     * @return a string containing the mathematical expression.
     *
     * @throws YAPI_Exception on error
     */
    public String loadExpression() throws YAPI_Exception
    {
        String id;
        String fname;
        String content;
        int idx;
        id = get_functionId();
        id = (id).substring( 16,  16 + (id).length() - 16);
        fname = String.format(Locale.US, "arithmExpr%s.txt",id);

        content = new String(_download(fname));
        idx = (content).indexOf("\n");
        if (idx > 0) {
            content = (content).substring( idx+1,  idx+1 + (content).length()-(idx+1));
        }
        return content;
    }

    /**
     * Defines a auxiliary function by means of a table of reference points. Intermediate values
     * will be interpolated between specified reference points. The reference points are given
     * as pairs of floating point numbers.
     * The auxiliary function will be available for use by all ArithmeticSensor objects of the
     * device. Up to nine auxiliary function can be defined in a device, each containing up to
     * 96 reference points.
     *
     * @param name : auxiliary function name, up to 16 characters.
     * @param inputValues : array of floating point numbers, corresponding to the function input value.
     * @param outputValues : array of floating point numbers, corresponding to the output value
     *         desired for each of the input value, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int defineAuxiliaryFunction(String name,ArrayList<Double> inputValues,ArrayList<Double> outputValues) throws YAPI_Exception
    {
        int siz;
        String defstr;
        int idx;
        double inputVal;
        double outputVal;
        String fname;
        siz = inputValues.size();
        //noinspection DoubleNegation
        if (!(siz > 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "auxiliary function must be defined by at least two points");}
        //noinspection DoubleNegation
        if (!(siz == outputValues.size())) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "table sizes mismatch");}
        defstr = "";
        idx = 0;
        while (idx < siz) {
            inputVal = inputValues.get(idx).doubleValue();
            outputVal = outputValues.get(idx).doubleValue();
            defstr = String.format(Locale.US, "%s%f:%f\n", defstr, inputVal,outputVal);
            idx = idx + 1;
        }
        fname = String.format(Locale.US, "userMap%s.txt",name);

        return _upload(fname, (defstr).getBytes());
    }

    /**
     * Retrieves the reference points table defining an auxiliary function previously
     * configured using the defineAuxiliaryFunction function.
     *
     * @param name : auxiliary function name, up to 16 characters.
     * @param inputValues : array of floating point numbers, that is filled by the function
     *         with all the function reference input value.
     * @param outputValues : array of floating point numbers, that is filled by the function
     *         output value for each of the input value, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int loadAuxiliaryFunction(String name,ArrayList<Double> inputValues,ArrayList<Double> outputValues) throws YAPI_Exception
    {
        String fname;
        byte[] defbin;
        int siz;

        fname = String.format(Locale.US, "userMap%s.txt",name);
        defbin = _download(fname);
        siz = (defbin).length;
        //noinspection DoubleNegation
        if (!(siz > 0)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "auxiliary function does not exist");}
        inputValues.clear();
        outputValues.clear();
        // FIXME: decode line by line
        return YAPI.SUCCESS;
    }

    /**
     * Continues the enumeration of arithmetic sensors started using yFirstArithmeticSensor().
     * Caution: You can't make any assumption about the returned arithmetic sensors order.
     * If you want to find a specific an arithmetic sensor, use ArithmeticSensor.findArithmeticSensor()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YArithmeticSensor object, corresponding to
     *         an arithmetic sensor currently online, or a null pointer
     *         if there are no more arithmetic sensors to enumerate.
     */
    public YArithmeticSensor nextArithmeticSensor()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindArithmeticSensorInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of arithmetic sensors currently accessible.
     * Use the method YArithmeticSensor.nextArithmeticSensor() to iterate on
     * next arithmetic sensors.
     *
     * @return a pointer to a YArithmeticSensor object, corresponding to
     *         the first arithmetic sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YArithmeticSensor FirstArithmeticSensor()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("ArithmeticSensor");
        if (next_hwid == null)  return null;
        return FindArithmeticSensorInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of arithmetic sensors currently accessible.
     * Use the method YArithmeticSensor.nextArithmeticSensor() to iterate on
     * next arithmetic sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YArithmeticSensor object, corresponding to
     *         the first arithmetic sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YArithmeticSensor FirstArithmeticSensorInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("ArithmeticSensor");
        if (next_hwid == null)  return null;
        return FindArithmeticSensorInContext(yctx, next_hwid);
    }

    //--- (end of YArithmeticSensor implementation)
}

