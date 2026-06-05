/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindOrientation(), the high-level API for Orientation functions
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
import java.util.ArrayList;
import java.util.Locale;

//--- (YOrientation return codes)
//--- (end of YOrientation return codes)
//--- (YOrientation yapiwrapper)
//--- (end of YOrientation yapiwrapper)
//--- (YOrientation class start)
/**
 * YOrientation Class: orientation sensor control interface
 *
 * The YOrientation class allows you to read and configure Yoctopuce orientation sensors.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YOrientation extends YSensor
{
//--- (end of YOrientation class start)
//--- (YOrientation definitions)
    /**
     * invalid counterClockwise value
     */
    public static final int COUNTERCLOCKWISE_FALSE = 0;
    public static final int COUNTERCLOCKWISE_TRUE = 1;
    public static final int COUNTERCLOCKWISE_INVALID = -1;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid zeroOffset value
     */
    public static final double ZEROOFFSET_INVALID = YAPI.INVALID_DOUBLE;
    protected int _counterClockwise = COUNTERCLOCKWISE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected double _zeroOffset = ZEROOFFSET_INVALID;
    protected UpdateCallback _valueCallbackOrientation = null;
    protected TimedReportCallback _timedReportCallbackOrientation = null;

    /**
     * Deprecated UpdateCallback for Orientation
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YOrientation function, String functionValue);
    }

    /**
     * TimedReportCallback for Orientation
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YOrientation  function, YMeasure measure);
    }
    //--- (end of YOrientation definitions)


    /**
     *
     * @param func : functionid
     */
    protected YOrientation(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Orientation";
        //--- (YOrientation attributes initialization)
        //--- (end of YOrientation attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YOrientation(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YOrientation implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("counterClockwise")) {
            _counterClockwise = json_val.getInt("counterClockwise") > 0 ? 1 : 0;
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        if (json_val.has("zeroOffset")) {
            _zeroOffset = Math.round(json_val.getDouble("zeroOffset") / 65.536) / 1000.0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns a value indicating whether the sensor is operating in a counterclockwise direction.
     *
     *  @return either YOrientation.COUNTERCLOCKWISE_FALSE or YOrientation.COUNTERCLOCKWISE_TRUE, according
     * to a value indicating whether the sensor is operating in a counterclockwise direction
     *
     * @throws YAPI_Exception on error
     */
    public int get_counterClockwise() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COUNTERCLOCKWISE_INVALID;
                }
            }
            res = _counterClockwise;
        }
        return res;
    }

    /**
     * Returns a value indicating whether the sensor is operating in a counterclockwise direction.
     *
     *  @return either YOrientation.COUNTERCLOCKWISE_FALSE or YOrientation.COUNTERCLOCKWISE_TRUE, according
     * to a value indicating whether the sensor is operating in a counterclockwise direction
     *
     * @throws YAPI_Exception on error
     */
    public int getCounterClockwise() throws YAPI_Exception
    {
        return get_counterClockwise();
    }

    /**
     * Defines the operating direction of the sensor.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : either YOrientation.COUNTERCLOCKWISE_FALSE or YOrientation.COUNTERCLOCKWISE_TRUE
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_counterClockwise(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("counterClockwise",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Defines the operating direction of the sensor.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : either YOrientation.COUNTERCLOCKWISE_FALSE or YOrientation.COUNTERCLOCKWISE_TRUE
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCounterClockwise(int newval)  throws YAPI_Exception
    {
        return set_counterClockwise(newval);
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
     * Sets an offset between the orientation reported by the sensor and the actual orientation. This
     * can typically be used  to compensate for mechanical offset. This offset can also be set
     * automatically using the zero() method.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : a floating point number
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_zeroOffset(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("zeroOffset",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Sets an offset between the orientation reported by the sensor and the actual orientation. This
     * can typically be used  to compensate for mechanical offset. This offset can also be set
     * automatically using the zero() method.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : a floating point number
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setZeroOffset(double newval)  throws YAPI_Exception
    {
        return set_zeroOffset(newval);
    }

    /**
     * Returns the Offset between the orientation reported by the sensor and the actual orientation.
     *
     *  @return a floating point number corresponding to the Offset between the orientation reported by the
     * sensor and the actual orientation
     *
     * @throws YAPI_Exception on error
     */
    public double get_zeroOffset() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ZEROOFFSET_INVALID;
                }
            }
            res = _zeroOffset;
        }
        return res;
    }

    /**
     * Returns the Offset between the orientation reported by the sensor and the actual orientation.
     *
     *  @return a floating point number corresponding to the Offset between the orientation reported by the
     * sensor and the actual orientation
     *
     * @throws YAPI_Exception on error
     */
    public double getZeroOffset() throws YAPI_Exception
    {
        return get_zeroOffset();
    }

    /**
     * Retrieves an orientation sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the orientation sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YOrientation.isOnline() to test if the orientation sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * an orientation sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the orientation sensor, for instance
     *         MyDevice.orientation.
     *
     * @return a YOrientation object allowing you to drive the orientation sensor.
     */
    public static YOrientation FindOrientation(String func)
    {
        YOrientation obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YOrientation) YFunction._FindFromCache("Orientation", func);
            if (obj == null) {
                obj = new YOrientation(func);
                YFunction._AddToCache("Orientation", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves an orientation sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the orientation sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YOrientation.isOnline() to test if the orientation sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * an orientation sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the orientation sensor, for instance
     *         MyDevice.orientation.
     *
     * @return a YOrientation object allowing you to drive the orientation sensor.
     */
    public static YOrientation FindOrientationInContext(YAPIContext yctx,String func)
    {
        YOrientation obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YOrientation) YFunction._FindFromCacheInContext(yctx, "Orientation", func);
            if (obj == null) {
                obj = new YOrientation(yctx, func);
                YFunction._AddToCache("Orientation", func, obj);
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
        _valueCallbackOrientation = callback;
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
        if (_valueCallbackOrientation != null) {
            _valueCallbackOrientation.yNewValue(this, value);
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
        _timedReportCallbackOrientation = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackOrientation != null) {
            _timedReportCallbackOrientation.timedReportCallback(this, value);
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
     * Reset the sensor's zero to current position by automatically setting a new offset.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int zero() throws YAPI_Exception
    {
        return sendCommand("Z");
    }

    /**
     * Modifies the calibration of the MA600A sensor using an array of 32
     * values representing the offset in degrees between the true values and
     * those measured regularly every 11.25 degrees starting from zero. The calibration
     * is applied immediately and is stored permanently in the MA600A sensor.
     * Before calculating the offset values, remember to clear any previous
     * calibration using the clearCalibration function and set
     * the zero offset  to 0. After a calibration change, the sensor will stop
     * measurements for about one second.
     * Do not confuse this function with the generic calibrateFromPoints function,
     * which works at the YSensor level and is not necessarily well suited to
     * a sensor returning circular values.
     *
     * @param offsetValues : array of 32 floating point values in the [-11.25..+11.25] range
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_calibration(ArrayList<Double> offsetValues) throws YAPI_Exception
    {
        String res;
        int npt;
        int idx;
        int corr;
        npt = offsetValues.size();
        if (npt != 32) {
            _throw(YAPI.INVALID_ARGUMENT, "Invalid calibration parameters (32 expected)");
            return YAPI.INVALID_ARGUMENT;
        }
        res = "C";
        idx = 0;
        while (idx < npt) {
            corr = (int) (double)Math.round(offsetValues.get(idx).doubleValue() * 128 / 11.25);
            if ((corr < -128) || (corr > 127)) {
                _throw(YAPI.INVALID_ARGUMENT, "Calibration parameter exceeds permitted range (+/-11.25)");
                return YAPI.INVALID_ARGUMENT;
            }
            if (corr < 0) {
                corr = corr + 256;
            }
            res = String.format(Locale.US, "%s%02x",res,corr);
            idx = idx + 1;
        }
        return sendCommand(res);
    }

    /**
     * Retrieves offset correction data points previously entered using the method
     * set_calibration.
     *
     * @param offsetValues : array of 32 floating point numbers, that will be filled by the
     *         function with the offset values for the correction points.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int get_Calibration(ArrayList<Double> offsetValues) throws YAPI_Exception
    {
        return 0;
    }

    /**
     * Cancels any calibration set with set_calibration. This function
     * is equivalent to calling set_calibration with only zeros.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int clearCalibration() throws YAPI_Exception
    {
        return sendCommand("-");
    }

    /**
     * Continues the enumeration of orientation sensors started using yFirstOrientation().
     * Caution: You can't make any assumption about the returned orientation sensors order.
     * If you want to find a specific an orientation sensor, use Orientation.findOrientation()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YOrientation object, corresponding to
     *         an orientation sensor currently online, or a null pointer
     *         if there are no more orientation sensors to enumerate.
     */
    public YOrientation nextOrientation()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindOrientationInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of orientation sensors currently accessible.
     * Use the method YOrientation.nextOrientation() to iterate on
     * next orientation sensors.
     *
     * @return a pointer to a YOrientation object, corresponding to
     *         the first orientation sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YOrientation FirstOrientation()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Orientation");
        if (next_hwid == null)  return null;
        return FindOrientationInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of orientation sensors currently accessible.
     * Use the method YOrientation.nextOrientation() to iterate on
     * next orientation sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YOrientation object, corresponding to
     *         the first orientation sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YOrientation FirstOrientationInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Orientation");
        if (next_hwid == null)  return null;
        return FindOrientationInContext(yctx, next_hwid);
    }

    //--- (end of YOrientation implementation)
}

