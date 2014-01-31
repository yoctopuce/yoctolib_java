/*********************************************************************
 *
 * $Id: YTemperature.java 14779 2014-01-30 14:56:39Z seb $
 *
 * Implements yFindTemperature(), the high-level API for Temperature functions
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

    //--- (YTemperature return codes)
    //--- (end of YTemperature return codes)
//--- (YTemperature class start)
/**
 * YTemperature Class: Temperature function interface
 * 
 * The Yoctopuce application programming interface allows you to read an instant
 * measure of the sensor, as well as the minimal and maximal values observed.
 */
public class YTemperature extends YSensor
{
//--- (end of YTemperature class start)
//--- (YTemperature definitions)
    /**
     * invalid sensorType value
     */
    public static final int SENSORTYPE_DIGITAL = 0;
    public static final int SENSORTYPE_TYPE_K = 1;
    public static final int SENSORTYPE_TYPE_E = 2;
    public static final int SENSORTYPE_TYPE_J = 3;
    public static final int SENSORTYPE_TYPE_N = 4;
    public static final int SENSORTYPE_TYPE_R = 5;
    public static final int SENSORTYPE_TYPE_S = 6;
    public static final int SENSORTYPE_TYPE_T = 7;
    public static final int SENSORTYPE_PT100_4WIRES = 8;
    public static final int SENSORTYPE_PT100_3WIRES = 9;
    public static final int SENSORTYPE_PT100_2WIRES = 10;
    public static final int SENSORTYPE_INVALID = -1;

    protected int _sensorType = SENSORTYPE_INVALID;
    protected UpdateCallback _valueCallbackTemperature = null;
    protected TimedReportCallback _timedReportCallbackTemperature = null;

    /**
     * Deprecated UpdateCallback for Temperature
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YTemperature function, String functionValue);
    }

    /**
     * TimedReportCallback for Temperature
     */
    public interface TimedReportCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YTemperature  function, YMeasure measure);
    }
    //--- (end of YTemperature definitions)


    /**
     * 
     * @param func : functionid
     */
    protected YTemperature(String func)
    {
        super(func);
        _className = "Temperature";
        //--- (YTemperature attributes initialization)
        //--- (end of YTemperature attributes initialization)
    }

    //--- (YTemperature implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("sensorType")) {
            _sensorType =  json_val.getInt("sensorType");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the temperature sensor type.
     * 
     * @return a value among YTemperature.SENSORTYPE_DIGITAL, YTemperature.SENSORTYPE_TYPE_K,
     * YTemperature.SENSORTYPE_TYPE_E, YTemperature.SENSORTYPE_TYPE_J, YTemperature.SENSORTYPE_TYPE_N,
     * YTemperature.SENSORTYPE_TYPE_R, YTemperature.SENSORTYPE_TYPE_S, YTemperature.SENSORTYPE_TYPE_T,
     * YTemperature.SENSORTYPE_PT100_4WIRES, YTemperature.SENSORTYPE_PT100_3WIRES and
     * YTemperature.SENSORTYPE_PT100_2WIRES corresponding to the temperature sensor type
     * 
     * @throws YAPI_Exception
     */
    public int get_sensorType()  throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SENSORTYPE_INVALID;
            }
        }
        return _sensorType;
    }

    /**
     * Returns the temperature sensor type.
     * 
     * @return a value among Y_SENSORTYPE_DIGITAL, Y_SENSORTYPE_TYPE_K, Y_SENSORTYPE_TYPE_E,
     * Y_SENSORTYPE_TYPE_J, Y_SENSORTYPE_TYPE_N, Y_SENSORTYPE_TYPE_R, Y_SENSORTYPE_TYPE_S,
     * Y_SENSORTYPE_TYPE_T, Y_SENSORTYPE_PT100_4WIRES, Y_SENSORTYPE_PT100_3WIRES and
     * Y_SENSORTYPE_PT100_2WIRES corresponding to the temperature sensor type
     * 
     * @throws YAPI_Exception
     */
    public int getSensorType() throws YAPI_Exception

    { return get_sensorType(); }

    /**
     * Modify the temperature sensor type.  This function is used to
     * to define the type of thermocouple (K,E...) used with the device.
     * This will have no effect if module is using a digital sensor.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a value among YTemperature.SENSORTYPE_DIGITAL, YTemperature.SENSORTYPE_TYPE_K,
     * YTemperature.SENSORTYPE_TYPE_E, YTemperature.SENSORTYPE_TYPE_J, YTemperature.SENSORTYPE_TYPE_N,
     * YTemperature.SENSORTYPE_TYPE_R, YTemperature.SENSORTYPE_TYPE_S, YTemperature.SENSORTYPE_TYPE_T,
     * YTemperature.SENSORTYPE_PT100_4WIRES, YTemperature.SENSORTYPE_PT100_3WIRES and
     * YTemperature.SENSORTYPE_PT100_2WIRES
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_sensorType(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("sensorType",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Modify the temperature sensor type.  This function is used to
     * to define the type of thermocouple (K,E...) used with the device.
     * This will have no effect if module is using a digital sensor.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a value among Y_SENSORTYPE_DIGITAL, Y_SENSORTYPE_TYPE_K, Y_SENSORTYPE_TYPE_E,
     * Y_SENSORTYPE_TYPE_J, Y_SENSORTYPE_TYPE_N, Y_SENSORTYPE_TYPE_R, Y_SENSORTYPE_TYPE_S,
     * Y_SENSORTYPE_TYPE_T, Y_SENSORTYPE_PT100_4WIRES, Y_SENSORTYPE_PT100_3WIRES and Y_SENSORTYPE_PT100_2WIRES
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setSensorType(int newval)  throws YAPI_Exception

    { return set_sensorType(newval); }

    /**
     * Retrieves a temperature sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the temperature sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YTemperature.isOnline() to test if the temperature sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a temperature sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the temperature sensor
     * 
     * @return a YTemperature object allowing you to drive the temperature sensor.
     */
    public static YTemperature FindTemperature(String func)
    {
        YTemperature obj;
        obj = (YTemperature) YFunction._FindFromCache("Temperature", func);
        if (obj == null) {
            obj = new YTemperature(func);
            YFunction._AddToCache("Temperature", func, obj);
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
        _valueCallbackTemperature = callback;
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
        if (_valueCallbackTemperature != null) {
            _valueCallbackTemperature.yNewValue(this, value);
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
        _timedReportCallbackTemperature = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackTemperature != null) {
            _timedReportCallbackTemperature.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of temperature sensors started using yFirstTemperature().
     * 
     * @return a pointer to a YTemperature object, corresponding to
     *         a temperature sensor currently online, or a null pointer
     *         if there are no more temperature sensors to enumerate.
     */
    public  YTemperature nextTemperature()
    {
        String next_hwid = SafeYAPI().getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindTemperature(next_hwid);
    }

    /**
     * Starts the enumeration of temperature sensors currently accessible.
     * Use the method YTemperature.nextTemperature() to iterate on
     * next temperature sensors.
     * 
     * @return a pointer to a YTemperature object, corresponding to
     *         the first temperature sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YTemperature FirstTemperature()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Temperature");
        if (next_hwid == null)  return null;
        return FindTemperature(next_hwid);
    }

    //--- (end of YTemperature implementation)
}

