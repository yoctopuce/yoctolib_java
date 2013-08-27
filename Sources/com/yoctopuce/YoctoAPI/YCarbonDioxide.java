/*********************************************************************
 *
 * $Id: YCarbonDioxide.java 12324 2013-08-13 15:10:31Z mvuilleu $
 *
 * Implements yFindCarbonDioxide(), the high-level API for CarbonDioxide functions
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

//--- (globals)
import java.util.ArrayList;
//--- (end of globals)
/**
 * YCarbonDioxide Class: CarbonDioxide function interface
 * 
 * The Yoctopuce application programming interface allows you to read an instant
 * measure of the sensor, as well as the minimal and maximal values observed.
 */
public class YCarbonDioxide extends YFunction
{
    //--- (definitions)
    private YCarbonDioxide.UpdateCallback _valueCallbackCarbonDioxide;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid unit value
     */
    public static final String UNIT_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid currentValue value
     */
    public static final double CURRENTVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid lowestValue value
     */
    public static final double LOWESTVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid highestValue value
     */
    public static final double HIGHESTVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid currentRawValue value
     */
    public static final double CURRENTRAWVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid calibrationParam value
     */
    public static final String CALIBRATIONPARAM_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid resolution value
     */
    public static final double RESOLUTION_INVALID = YAPI.INVALID_DOUBLE;
    public static final int _calibrationOffset = -32767;
    //--- (end of definitions)

    /**
     * UdateCallback for CarbonDioxide
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YCarbonDioxide function, String functionValue);
    }



    //--- (YCarbonDioxide implementation)

    /**
     * Returns the logical name of the CO2 sensor.
     * 
     * @return a string corresponding to the logical name of the CO2 sensor
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the CO2 sensor.
     * 
     * @return a string corresponding to the logical name of the CO2 sensor
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the CO2 sensor. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the CO2 sensor
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_logicalName( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("logicalName",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the logical name of the CO2 sensor. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the CO2 sensor
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the CO2 sensor (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the CO2 sensor (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the CO2 sensor (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the CO2 sensor (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the measuring unit for the measured value.
     * 
     * @return a string corresponding to the measuring unit for the measured value
     * 
     * @throws YAPI_Exception
     */
    public String get_unit()  throws YAPI_Exception
    {
        String json_val = (String) _getFixedAttr("unit");
        return json_val;
    }

    /**
     * Returns the measuring unit for the measured value.
     * 
     * @return a string corresponding to the measuring unit for the measured value
     * 
     * @throws YAPI_Exception
     */
    public String getUnit() throws YAPI_Exception

    { return get_unit(); }

    /**
     * Returns the current measured value.
     * 
     * @return a floating point number corresponding to the current measured value
     * 
     * @throws YAPI_Exception
     */
    public double get_currentValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("currentValue");
        double res = YAPI.applyCalibration(get_currentRawValue(), get_calibrationParam(), _calibrationOffset, get_resolution());
        if(res != YAPI.INVALID_DOUBLE) return res;
        return (Double.parseDouble(json_val)/6553.6) / 10;
    }

    /**
     * Returns the current measured value.
     * 
     * @return a floating point number corresponding to the current measured value
     * 
     * @throws YAPI_Exception
     */
    public double getCurrentValue() throws YAPI_Exception

    { return get_currentValue(); }

    /**
     * Changes the recorded minimal value observed.
     * 
     * @param newval : a floating point number corresponding to the recorded minimal value observed
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_lowestValue( double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval*65536.0));
        _setAttr("lowestValue",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the recorded minimal value observed.
     * 
     * @param newval : a floating point number corresponding to the recorded minimal value observed
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLowestValue( double newval)  throws YAPI_Exception

    { return set_lowestValue(newval); }

    /**
     * Returns the minimal value observed.
     * 
     * @return a floating point number corresponding to the minimal value observed
     * 
     * @throws YAPI_Exception
     */
    public double get_lowestValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("lowestValue");
        return (Double.parseDouble(json_val)/6553.6) / 10;
    }

    /**
     * Returns the minimal value observed.
     * 
     * @return a floating point number corresponding to the minimal value observed
     * 
     * @throws YAPI_Exception
     */
    public double getLowestValue() throws YAPI_Exception

    { return get_lowestValue(); }

    /**
     * Changes the recorded maximal value observed.
     * 
     * @param newval : a floating point number corresponding to the recorded maximal value observed
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_highestValue( double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval*65536.0));
        _setAttr("highestValue",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the recorded maximal value observed.
     * 
     * @param newval : a floating point number corresponding to the recorded maximal value observed
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setHighestValue( double newval)  throws YAPI_Exception

    { return set_highestValue(newval); }

    /**
     * Returns the maximal value observed.
     * 
     * @return a floating point number corresponding to the maximal value observed
     * 
     * @throws YAPI_Exception
     */
    public double get_highestValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("highestValue");
        return (Double.parseDouble(json_val)/6553.6) / 10;
    }

    /**
     * Returns the maximal value observed.
     * 
     * @return a floating point number corresponding to the maximal value observed
     * 
     * @throws YAPI_Exception
     */
    public double getHighestValue() throws YAPI_Exception

    { return get_highestValue(); }

    /**
     * Returns the uncalibrated, unrounded raw value returned by the sensor.
     * 
     * @return a floating point number corresponding to the uncalibrated, unrounded raw value returned by the sensor
     * 
     * @throws YAPI_Exception
     */
    public double get_currentRawValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("currentRawValue");
        return Double.parseDouble(json_val)/65536.0;
    }

    /**
     * Returns the uncalibrated, unrounded raw value returned by the sensor.
     * 
     * @return a floating point number corresponding to the uncalibrated, unrounded raw value returned by the sensor
     * 
     * @throws YAPI_Exception
     */
    public double getCurrentRawValue() throws YAPI_Exception

    { return get_currentRawValue(); }

    public String get_calibrationParam()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("calibrationParam");
        return json_val;
    }

    public String getCalibrationParam() throws YAPI_Exception

    { return get_calibrationParam(); }

    public int set_calibrationParam( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("calibrationParam",rest_val);
        return YAPI.SUCCESS;
    }

    public int setCalibrationParam( String newval)  throws YAPI_Exception

    { return set_calibrationParam(newval); }

    /**
     * Configures error correction data points, in particular to compensate for
     * a possible perturbation of the measure caused by an enclosure. It is possible
     * to configure up to five correction points. Correction points must be provided
     * in ascending order, and be in the range of the sensor. The device will automatically
     * perform a linear interpolation of the error correction between specified
     * points. Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * For more information on advanced capabilities to refine the calibration of
     * sensors, please contact support@yoctopuce.com.
     * 
     * @param rawValues : array of floating point numbers, corresponding to the raw
     *         values returned by the sensor for the correction points.
     * @param refValues : array of floating point numbers, corresponding to the corrected
     *         values for the correction points.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int calibrateFromPoints(ArrayList<Double> rawValues,ArrayList<Double> refValues)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = YAPI._encodeCalibrationPoints(rawValues,refValues,get_resolution(),_calibrationOffset,get_calibrationParam());
        _setAttr("calibrationParam",rest_val);
        return YAPI.SUCCESS;
    }

    public int loadCalibrationPoints(ArrayList<Double> rawValues,ArrayList<Double> refValues)  throws YAPI_Exception
    {
        return YAPI._decodeCalibrationPoints(get_calibrationParam(),null,rawValues,refValues,get_resolution(),_calibrationOffset);
    }

    /**
     * Returns the resolution of the measured values. The resolution corresponds to the numerical precision
     * of the values, which is not always the same as the actual precision of the sensor.
     * 
     * @return a floating point number corresponding to the resolution of the measured values
     * 
     * @throws YAPI_Exception
     */
    public double get_resolution()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("resolution");
        return (Integer.parseInt(json_val) > 100 ? 1.0 / Math.round(65536.0/Double.parseDouble(json_val)) : 0.001 / Math.round(67.0/Double.parseDouble(json_val)));
    }

    /**
     * Returns the resolution of the measured values. The resolution corresponds to the numerical precision
     * of the values, which is not always the same as the actual precision of the sensor.
     * 
     * @return a floating point number corresponding to the resolution of the measured values
     * 
     * @throws YAPI_Exception
     */
    public double getResolution() throws YAPI_Exception

    { return get_resolution(); }

    /**
     * Continues the enumeration of CO2 sensors started using yFirstCarbonDioxide().
     * 
     * @return a pointer to a YCarbonDioxide object, corresponding to
     *         a CO2 sensor currently online, or a null pointer
     *         if there are no more CO2 sensors to enumerate.
     */
    public  YCarbonDioxide nextCarbonDioxide()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindCarbonDioxide(next_hwid);
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
    {   YFunction yfunc = YAPI.getFunction("CarbonDioxide", func);
        if (yfunc != null) {
            return (YCarbonDioxide) yfunc;
        }
        return new YCarbonDioxide(func);
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
        String next_hwid = YAPI.getFirstHardwareId("CarbonDioxide");
        if (next_hwid == null)  return null;
        return FindCarbonDioxide(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YCarbonDioxide(String func)
    {
        super("CarbonDioxide", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackCarbonDioxide != null) {
            _valueCallbackCarbonDioxide.yNewValue(this, newvalue);
        }
    }

    /**
     * Internal: check if we have a callback interface registered
     * 
     * @return yes if the user has registered a interface
     */
    @Override
     protected boolean hasCallbackRegistered()
    {
        return super.hasCallbackRegistered() || (_valueCallbackCarbonDioxide!=null);
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
    public void registerValueCallback(YCarbonDioxide.UpdateCallback callback)
    {
         _valueCallbackCarbonDioxide =  callback;
         if (callback != null && isOnline()) {
             String newval;
             try {
                 newval = get_advertisedValue();
                 if (!newval.equals("") && !newval.equals("!INVALDI!")) {
                     callback.yNewValue(this, newval);
                 }
             } catch (YAPI_Exception ex) {
             }
         }
    }

    //--- (end of YCarbonDioxide implementation)
};

