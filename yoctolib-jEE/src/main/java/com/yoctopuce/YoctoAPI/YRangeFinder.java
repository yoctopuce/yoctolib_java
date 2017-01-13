/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements FindRangeFinder(), the high-level API for RangeFinder functions
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
import org.json.JSONException;
import org.json.JSONObject;

//--- (YRangeFinder return codes)
//--- (end of YRangeFinder return codes)
//--- (YRangeFinder class start)
/**
 * YRangeFinder Class: RangeFinder function interface
 *
 * The Yoctopuce class YRangeFinder allows you to use and configure Yoctopuce range finders
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to easily perform a one-point linear calibration
 * to compensate the effect of a glass or filter placed in front of the sensor.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YRangeFinder extends YSensor
{
//--- (end of YRangeFinder class start)
//--- (YRangeFinder definitions)
    /**
     * invalid rangeFinderMode value
     */
    public static final int RANGEFINDERMODE_DEFAULT = 0;
    public static final int RANGEFINDERMODE_LONG_RANGE = 1;
    public static final int RANGEFINDERMODE_HIGH_ACCURACY = 2;
    public static final int RANGEFINDERMODE_HIGH_SPEED = 3;
    public static final int RANGEFINDERMODE_INVALID = -1;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _rangeFinderMode = RANGEFINDERMODE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackRangeFinder = null;
    protected TimedReportCallback _timedReportCallbackRangeFinder = null;

    /**
     * Deprecated UpdateCallback for RangeFinder
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YRangeFinder function, String functionValue);
    }

    /**
     * TimedReportCallback for RangeFinder
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YRangeFinder  function, YMeasure measure);
    }
    //--- (end of YRangeFinder definitions)


    /**
     *
     * @param func : functionid
     */
    protected YRangeFinder(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "RangeFinder";
        //--- (YRangeFinder attributes initialization)
        //--- (end of YRangeFinder attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YRangeFinder(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YRangeFinder implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("rangeFinderMode")) {
            _rangeFinderMode = json_val.getInt("rangeFinderMode");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the measuring unit for the measured temperature. That unit is a string.
     * String value can be " or mm. Any other value will be ignored.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     * WARNING: if a specific calibration is defined for the rangeFinder function, a
     * unit system change will probably break it.
     *
     * @param newval : a string corresponding to the measuring unit for the measured temperature
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_unit(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("unit",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the measuring unit for the measured temperature. That unit is a string.
     * String value can be " or mm. Any other value will be ignored.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     * WARNING: if a specific calibration is defined for the rangeFinder function, a
     * unit system change will probably break it.
     *
     * @param newval : a string corresponding to the measuring unit for the measured temperature
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
     * Returns the rangefinder running mode. The rangefinder running mode
     * allows to put priority on precision, speed or maximum range.
     *
     *  @return a value among YRangeFinder.RANGEFINDERMODE_DEFAULT, YRangeFinder.RANGEFINDERMODE_LONG_RANGE,
     *  YRangeFinder.RANGEFINDERMODE_HIGH_ACCURACY and YRangeFinder.RANGEFINDERMODE_HIGH_SPEED
     * corresponding to the rangefinder running mode
     *
     * @throws YAPI_Exception on error
     */
    public int get_rangeFinderMode() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPIContext.GetTickCount()) {
            if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                return RANGEFINDERMODE_INVALID;
            }
        }
        return _rangeFinderMode;
    }

    /**
     * Returns the rangefinder running mode. The rangefinder running mode
     * allows to put priority on precision, speed or maximum range.
     *
     *  @return a value among Y_RANGEFINDERMODE_DEFAULT, Y_RANGEFINDERMODE_LONG_RANGE,
     * Y_RANGEFINDERMODE_HIGH_ACCURACY and Y_RANGEFINDERMODE_HIGH_SPEED corresponding to the rangefinder running mode
     *
     * @throws YAPI_Exception on error
     */
    public int getRangeFinderMode() throws YAPI_Exception
    {
        return get_rangeFinderMode();
    }

    /**
     * Changes the rangefinder running mode, allowing to put priority on
     * precision, speed or maximum range.
     *
     *  @param newval : a value among YRangeFinder.RANGEFINDERMODE_DEFAULT,
     *  YRangeFinder.RANGEFINDERMODE_LONG_RANGE, YRangeFinder.RANGEFINDERMODE_HIGH_ACCURACY and
     *  YRangeFinder.RANGEFINDERMODE_HIGH_SPEED corresponding to the rangefinder running mode, allowing to
     * put priority on
     *         precision, speed or maximum range
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_rangeFinderMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("rangeFinderMode",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the rangefinder running mode, allowing to put priority on
     * precision, speed or maximum range.
     *
     *  @param newval : a value among Y_RANGEFINDERMODE_DEFAULT, Y_RANGEFINDERMODE_LONG_RANGE,
     *  Y_RANGEFINDERMODE_HIGH_ACCURACY and Y_RANGEFINDERMODE_HIGH_SPEED corresponding to the rangefinder
     * running mode, allowing to put priority on
     *         precision, speed or maximum range
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setRangeFinderMode(int newval)  throws YAPI_Exception
    {
        return set_rangeFinderMode(newval);
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String get_command() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPIContext.GetTickCount()) {
            if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                return COMMAND_INVALID;
            }
        }
        return _command;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String getCommand() throws YAPI_Exception
    {
        return get_command();
    }

    public int set_command(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("command",rest_val);
        return YAPI.SUCCESS;
    }

    public int setCommand(String newval)  throws YAPI_Exception
    {
        return set_command(newval);
    }

    /**
     * Retrieves a range finder for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the range finder is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRangeFinder.isOnline() to test if the range finder is
     * indeed online at a given time. In case of ambiguity when looking for
     * a range finder by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the range finder
     *
     * @return a YRangeFinder object allowing you to drive the range finder.
     */
    public static YRangeFinder FindRangeFinder(String func)
    {
        YRangeFinder obj;
        obj = (YRangeFinder) YFunction._FindFromCache("RangeFinder", func);
        if (obj == null) {
            obj = new YRangeFinder(func);
            YFunction._AddToCache("RangeFinder", func, obj);
        }
        return obj;
    }

    /**
     * Retrieves a range finder for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the range finder is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRangeFinder.isOnline() to test if the range finder is
     * indeed online at a given time. In case of ambiguity when looking for
     * a range finder by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the range finder
     *
     * @return a YRangeFinder object allowing you to drive the range finder.
     */
    public static YRangeFinder FindRangeFinderInContext(YAPIContext yctx,String func)
    {
        YRangeFinder obj;
        obj = (YRangeFinder) YFunction._FindFromCacheInContext(yctx, "RangeFinder", func);
        if (obj == null) {
            obj = new YRangeFinder(yctx, func);
            YFunction._AddToCache("RangeFinder", func, obj);
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
        _valueCallbackRangeFinder = callback;
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
        if (_valueCallbackRangeFinder != null) {
            _valueCallbackRangeFinder.yNewValue(this, value);
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
        _timedReportCallbackRangeFinder = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackRangeFinder != null) {
            _timedReportCallbackRangeFinder.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Triggers a sensor calibration according to the current ambient temperature. That
     * calibration process needs no physical interaction with the sensor. It is performed
     * automatically at device startup, but it is recommended to start it again when the
     * temperature delta since last calibration exceeds 8°C.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int triggerTempCalibration() throws YAPI_Exception
    {
        return set_command("T");
    }

    /**
     * Continues the enumeration of range finders started using yFirstRangeFinder().
     *
     * @return a pointer to a YRangeFinder object, corresponding to
     *         a range finder currently online, or a null pointer
     *         if there are no more range finders to enumerate.
     */
    public YRangeFinder nextRangeFinder()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindRangeFinderInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of range finders currently accessible.
     * Use the method YRangeFinder.nextRangeFinder() to iterate on
     * next range finders.
     *
     * @return a pointer to a YRangeFinder object, corresponding to
     *         the first range finder currently online, or a null pointer
     *         if there are none.
     */
    public static YRangeFinder FirstRangeFinder()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("RangeFinder");
        if (next_hwid == null)  return null;
        return FindRangeFinderInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of range finders currently accessible.
     * Use the method YRangeFinder.nextRangeFinder() to iterate on
     * next range finders.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YRangeFinder object, corresponding to
     *         the first range finder currently online, or a null pointer
     *         if there are none.
     */
    public static YRangeFinder FirstRangeFinderInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("RangeFinder");
        if (next_hwid == null)  return null;
        return FindRangeFinderInContext(yctx, next_hwid);
    }

    //--- (end of YRangeFinder implementation)
}

