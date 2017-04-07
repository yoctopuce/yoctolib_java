/*********************************************************************
 *
 * $Id: YCarbonDioxide.java 27053 2017-04-04 16:01:11Z seb $
 *
 * Implements FindCarbonDioxide(), the high-level API for CarbonDioxide functions
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

//--- (YCarbonDioxide return codes)
//--- (end of YCarbonDioxide return codes)
//--- (YCarbonDioxide class start)
/**
 * YCarbonDioxide Class: CarbonDioxide function interface
 *
 * The Yoctopuce class YCarbonDioxide allows you to read and configure Yoctopuce CO2
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * to register callback functions,  to access the autonomous datalogger.
 * This class adds the ability to perform manual calibration if reuired.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YCarbonDioxide extends YSensor
{
//--- (end of YCarbonDioxide class start)
//--- (YCarbonDioxide definitions)
    /**
     * invalid abcPeriod value
     */
    public static final int ABCPERIOD_INVALID = YAPI.INVALID_INT;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _abcPeriod = ABCPERIOD_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackCarbonDioxide = null;
    protected TimedReportCallback _timedReportCallbackCarbonDioxide = null;

    /**
     * Deprecated UpdateCallback for CarbonDioxide
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YCarbonDioxide function, String functionValue);
    }

    /**
     * TimedReportCallback for CarbonDioxide
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YCarbonDioxide  function, YMeasure measure);
    }
    //--- (end of YCarbonDioxide definitions)


    /**
     *
     * @param func : functionid
     */
    protected YCarbonDioxide(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "CarbonDioxide";
        //--- (YCarbonDioxide attributes initialization)
        //--- (end of YCarbonDioxide attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YCarbonDioxide(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YCarbonDioxide implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("abcPeriod")) {
            _abcPeriod = json_val.getInt("abcPeriod");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the Automatic Baseline Calibration period, in hours. A negative value
     * means that automatic baseline calibration is disabled.
     *
     * @return an integer corresponding to the Automatic Baseline Calibration period, in hours
     *
     * @throws YAPI_Exception on error
     */
    public int get_abcPeriod() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ABCPERIOD_INVALID;
                }
            }
            res = _abcPeriod;
        }
        return res;
    }

    /**
     * Returns the Automatic Baseline Calibration period, in hours. A negative value
     * means that automatic baseline calibration is disabled.
     *
     * @return an integer corresponding to the Automatic Baseline Calibration period, in hours
     *
     * @throws YAPI_Exception on error
     */
    public int getAbcPeriod() throws YAPI_Exception
    {
        return get_abcPeriod();
    }

    /**
     * Modifies Automatic Baseline Calibration period, in hours. If you need
     * to disable automatic baseline calibration (for instance when using the
     * sensor in an environment that is constantly above 400ppm CO2), set the
     * period to -1. Remember to call the saveToFlash() method of the
     * module if the modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_abcPeriod(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("abcPeriod",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Modifies Automatic Baseline Calibration period, in hours. If you need
     * to disable automatic baseline calibration (for instance when using the
     * sensor in an environment that is constantly above 400ppm CO2), set the
     * period to -1. Remember to call the saveToFlash() method of the
     * module if the modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAbcPeriod(int newval)  throws YAPI_Exception
    {
        return set_abcPeriod(newval);
    }

    public String get_command() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
     * Retrieves a CO2 sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the CO2 sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCarbonDioxide.isOnline() to test if the CO2 sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a CO2 sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the CO2 sensor
     *
     * @return a YCarbonDioxide object allowing you to drive the CO2 sensor.
     */
    public static YCarbonDioxide FindCarbonDioxide(String func)
    {
        YCarbonDioxide obj;
        synchronized (YAPI.class) {
            obj = (YCarbonDioxide) YFunction._FindFromCache("CarbonDioxide", func);
            if (obj == null) {
                obj = new YCarbonDioxide(func);
                YFunction._AddToCache("CarbonDioxide", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a CO2 sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the CO2 sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCarbonDioxide.isOnline() to test if the CO2 sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a CO2 sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the CO2 sensor
     *
     * @return a YCarbonDioxide object allowing you to drive the CO2 sensor.
     */
    public static YCarbonDioxide FindCarbonDioxideInContext(YAPIContext yctx,String func)
    {
        YCarbonDioxide obj;
        synchronized (yctx) {
            obj = (YCarbonDioxide) YFunction._FindFromCacheInContext(yctx, "CarbonDioxide", func);
            if (obj == null) {
                obj = new YCarbonDioxide(yctx, func);
                YFunction._AddToCache("CarbonDioxide", func, obj);
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
        _valueCallbackCarbonDioxide = callback;
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
        if (_valueCallbackCarbonDioxide != null) {
            _valueCallbackCarbonDioxide.yNewValue(this, value);
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
        _timedReportCallbackCarbonDioxide = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackCarbonDioxide != null) {
            _timedReportCallbackCarbonDioxide.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Triggers a baseline calibration at standard CO2 ambiant level (400ppm).
     * It is normally not necessary to manually calibrate the sensor, because
     * the built-in automatic baseline calibration procedure will automatically
     * fix any long-term drift based on the lowest level of CO2 observed over the
     * automatic calibration period. However, if you disable automatic baseline
     * calibration, you may want to manually trigger a calibration from time to
     * time. Before starting a baseline calibration, make sure to put the sensor
     * in a standard environment (e.g. outside in fresh air) at around 400ppm.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int triggerBaselineCalibration() throws YAPI_Exception
    {
        return set_command("BC");
    }

    public int triggetBaselineCalibration() throws YAPI_Exception
    {
        return triggerBaselineCalibration();
    }

    /**
     * Triggers a zero calibration of the sensor on carbon dioxide-free air.
     * It is normally not necessary to manually calibrate the sensor, because
     * the built-in automatic baseline calibration procedure will automatically
     * fix any long-term drift based on the lowest level of CO2 observed over the
     * automatic calibration period. However, if you disable automatic baseline
     * calibration, you may want to manually trigger a calibration from time to
     * time. Before starting a zero calibration, you should circulate carbon
     * dioxide-free air within the sensor for a minute or two, using a small pipe
     * connected to the sensor. Please contact support@yoctopuce.com for more details
     * on the zero calibration procedure.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int triggerZeroCalibration() throws YAPI_Exception
    {
        return set_command("ZC");
    }

    public int triggetZeroCalibration() throws YAPI_Exception
    {
        return triggerZeroCalibration();
    }

    /**
     * Continues the enumeration of CO2 sensors started using yFirstCarbonDioxide().
     *
     * @return a pointer to a YCarbonDioxide object, corresponding to
     *         a CO2 sensor currently online, or a null pointer
     *         if there are no more CO2 sensors to enumerate.
     */
    public YCarbonDioxide nextCarbonDioxide()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindCarbonDioxideInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of CO2 sensors currently accessible.
     * Use the method YCarbonDioxide.nextCarbonDioxide() to iterate on
     * next CO2 sensors.
     *
     * @return a pointer to a YCarbonDioxide object, corresponding to
     *         the first CO2 sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YCarbonDioxide FirstCarbonDioxide()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("CarbonDioxide");
        if (next_hwid == null)  return null;
        return FindCarbonDioxideInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of CO2 sensors currently accessible.
     * Use the method YCarbonDioxide.nextCarbonDioxide() to iterate on
     * next CO2 sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YCarbonDioxide object, corresponding to
     *         the first CO2 sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YCarbonDioxide FirstCarbonDioxideInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("CarbonDioxide");
        if (next_hwid == null)  return null;
        return FindCarbonDioxideInContext(yctx, next_hwid);
    }

    //--- (end of YCarbonDioxide implementation)
}

