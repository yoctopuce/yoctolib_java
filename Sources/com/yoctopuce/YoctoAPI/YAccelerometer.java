/*********************************************************************
 *
 * $Id: YAccelerometer.java 15407 2014-03-12 19:34:44Z mvuilleu $
 *
 * Implements yFindAccelerometer(), the high-level API for Accelerometer functions
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
import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

    //--- (YAccelerometer return codes)
    //--- (end of YAccelerometer return codes)
//--- (YAccelerometer class start)
/**
 * YAccelerometer Class: Accelerometer function interface
 * 
 * The Yoctopuce application programming interface allows you to read an instant
 * measure of the sensor, as well as the minimal and maximal values observed.
 */
public class YAccelerometer extends YSensor
{
//--- (end of YAccelerometer class start)
//--- (YAccelerometer definitions)
    /**
     * invalid xValue value
     */
    public static final double XVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid yValue value
     */
    public static final double YVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid zValue value
     */
    public static final double ZVALUE_INVALID = YAPI.INVALID_DOUBLE;
    protected double _xValue = XVALUE_INVALID;
    protected double _yValue = YVALUE_INVALID;
    protected double _zValue = ZVALUE_INVALID;
    protected UpdateCallback _valueCallbackAccelerometer = null;
    protected TimedReportCallback _timedReportCallbackAccelerometer = null;

    /**
     * Deprecated UpdateCallback for Accelerometer
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YAccelerometer function, String functionValue);
    }

    /**
     * TimedReportCallback for Accelerometer
     */
    public interface TimedReportCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YAccelerometer  function, YMeasure measure);
    }
    //--- (end of YAccelerometer definitions)


    /**
     * 
     * @param func : functionid
     */
    protected YAccelerometer(String func)
    {
        super(func);
        _className = "Accelerometer";
        //--- (YAccelerometer attributes initialization)
        //--- (end of YAccelerometer attributes initialization)
    }

    //--- (YAccelerometer implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("xValue")) {
            _xValue =  json_val.getDouble("xValue")/65536.0;
        }
        if (json_val.has("yValue")) {
            _yValue =  json_val.getDouble("yValue")/65536.0;
        }
        if (json_val.has("zValue")) {
            _zValue =  json_val.getDouble("zValue")/65536.0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the X component of the acceleration, as a floating point number.
     * 
     * @return a floating point number corresponding to the X component of the acceleration, as a floating point number
     * 
     * @throws YAPI_Exception
     */
    public double get_xValue() throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return XVALUE_INVALID;
            }
        }
        return _xValue;
    }

    /**
     * Returns the X component of the acceleration, as a floating point number.
     * 
     * @return a floating point number corresponding to the X component of the acceleration, as a floating point number
     * 
     * @throws YAPI_Exception
     */
    public double getXValue() throws YAPI_Exception

    { return get_xValue(); }

    /**
     * Returns the Y component of the acceleration, as a floating point number.
     * 
     * @return a floating point number corresponding to the Y component of the acceleration, as a floating point number
     * 
     * @throws YAPI_Exception
     */
    public double get_yValue() throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return YVALUE_INVALID;
            }
        }
        return _yValue;
    }

    /**
     * Returns the Y component of the acceleration, as a floating point number.
     * 
     * @return a floating point number corresponding to the Y component of the acceleration, as a floating point number
     * 
     * @throws YAPI_Exception
     */
    public double getYValue() throws YAPI_Exception

    { return get_yValue(); }

    /**
     * Returns the Z component of the acceleration, as a floating point number.
     * 
     * @return a floating point number corresponding to the Z component of the acceleration, as a floating point number
     * 
     * @throws YAPI_Exception
     */
    public double get_zValue() throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ZVALUE_INVALID;
            }
        }
        return _zValue;
    }

    /**
     * Returns the Z component of the acceleration, as a floating point number.
     * 
     * @return a floating point number corresponding to the Z component of the acceleration, as a floating point number
     * 
     * @throws YAPI_Exception
     */
    public double getZValue() throws YAPI_Exception

    { return get_zValue(); }

    /**
     * Retrieves an accelerometer for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the accelerometer is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YAccelerometer.isOnline() to test if the accelerometer is
     * indeed online at a given time. In case of ambiguity when looking for
     * an accelerometer by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the accelerometer
     * 
     * @return a YAccelerometer object allowing you to drive the accelerometer.
     */
    public static YAccelerometer FindAccelerometer(String func)
    {
        YAccelerometer obj;
        obj = (YAccelerometer) YFunction._FindFromCache("Accelerometer", func);
        if (obj == null) {
            obj = new YAccelerometer(func);
            YFunction._AddToCache("Accelerometer", func, obj);
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
     * @noreturn
     */
    public int registerValueCallback(UpdateCallback callback)
    {
        String val;
        if (callback != null) {
            YFunction._UpdateValueCallbackList(this, true);
        } else {
            YFunction._UpdateValueCallbackList(this, false);
        }
        _valueCallbackAccelerometer = callback;
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
        if (_valueCallbackAccelerometer != null) {
            _valueCallbackAccelerometer.yNewValue(this, value);
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
     * @noreturn
     */
    public int registerTimedReportCallback(TimedReportCallback callback)
    {
        if (callback != null) {
            YFunction._UpdateTimedReportCallbackList(this, true);
        } else {
            YFunction._UpdateTimedReportCallbackList(this, false);
        }
        _timedReportCallbackAccelerometer = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackAccelerometer != null) {
            _timedReportCallbackAccelerometer.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of accelerometers started using yFirstAccelerometer().
     * 
     * @return a pointer to a YAccelerometer object, corresponding to
     *         an accelerometer currently online, or a null pointer
     *         if there are no more accelerometers to enumerate.
     */
    public  YAccelerometer nextAccelerometer()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindAccelerometer(next_hwid);
    }

    /**
     * Starts the enumeration of accelerometers currently accessible.
     * Use the method YAccelerometer.nextAccelerometer() to iterate on
     * next accelerometers.
     * 
     * @return a pointer to a YAccelerometer object, corresponding to
     *         the first accelerometer currently online, or a null pointer
     *         if there are none.
     */
    public static YAccelerometer FirstAccelerometer()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Accelerometer");
        if (next_hwid == null)  return null;
        return FindAccelerometer(next_hwid);
    }

    //--- (end of YAccelerometer implementation)
}

