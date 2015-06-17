/*********************************************************************
 *
 * $Id: YSensor.java 20412 2015-05-22 08:52:39Z seb $
 *
 * Implements yFindSensor(), the high-level API for Sensor functions
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

import java.util.ArrayList;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

//--- (generated code: YSensor class start)
/**
 * YSensor Class: Sensor function interface
 *
 * The YSensor class is the parent class for all Yoctopuce sensors. It can be
 * used to read the current value and unit of any sensor, read the min/max
 * value, configure autonomous recording frequency and access recorded data.
 * It also provide a function to register a callback invoked each time the
 * observed value changes, or at a predefined interval. Using this class rather
 * than a specific subclass makes it possible to create generic applications
 * that work with any Yoctopuce sensor, even those that do not yet exist.
 * Note: The YAnButton class is the only analog input which does not inherit
 * from YSensor.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YSensor extends YFunction
{
//--- (end of generated code: YSensor class start)

    //--- (generated code: YSensor definitions)
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
     * invalid logFrequency value
     */
    public static final String LOGFREQUENCY_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid reportFrequency value
     */
    public static final String REPORTFREQUENCY_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid calibrationParam value
     */
    public static final String CALIBRATIONPARAM_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid resolution value
     */
    public static final double RESOLUTION_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid sensorState value
     */
    public static final int SENSORSTATE_INVALID = YAPI.INVALID_INT;
    protected String _unit = UNIT_INVALID;
    protected double _currentValue = CURRENTVALUE_INVALID;
    protected double _lowestValue = LOWESTVALUE_INVALID;
    protected double _highestValue = HIGHESTVALUE_INVALID;
    protected double _currentRawValue = CURRENTRAWVALUE_INVALID;
    protected String _logFrequency = LOGFREQUENCY_INVALID;
    protected String _reportFrequency = REPORTFREQUENCY_INVALID;
    protected String _calibrationParam = CALIBRATIONPARAM_INVALID;
    protected double _resolution = RESOLUTION_INVALID;
    protected int _sensorState = SENSORSTATE_INVALID;
    protected UpdateCallback _valueCallbackSensor = null;
    protected TimedReportCallback _timedReportCallbackSensor = null;
    protected double _prevTimedReport = 0;
    protected double _iresol = 0;
    protected double _offset = 0;
    protected double _scale = 0;
    protected double _decexp = 0;
    protected boolean _isScal;
    protected boolean _isScal32;
    protected int _caltyp = 0;
    protected ArrayList<Integer> _calpar = new ArrayList<Integer>();
    protected ArrayList<Double> _calraw = new ArrayList<Double>();
    protected ArrayList<Double> _calref = new ArrayList<Double>();
    protected YAPI.CalibrationHandlerCallback _calhdl;

    /**
     * Deprecated UpdateCallback for Sensor
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YSensor function, String functionValue);
    }

    /**
     * TimedReportCallback for Sensor
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YSensor  function, YMeasure measure);
    }
    //--- (end of generated code: YSensor definitions)


    /*
     * Method used to encode calibration points into fixed-point 16-bit integers
     */
    String _encodeCalibrationPoints(ArrayList<Double> rawValues, ArrayList<Double> refValues, String actualCparams) throws YAPI_Exception
    {
        int npt = (rawValues.size() < refValues.size() ? rawValues.size() : refValues.size());
        int rawVal, refVal;
        int calibType;
        int minRaw = 0;
        String res;

        if (npt == 0) {
            return "0";
        }
        int pos = actualCparams.indexOf(',');
        if (actualCparams.equals("") || actualCparams.equals("0") || pos >= 0) {
            _throw(YAPI.NOT_SUPPORTED, "Device does not support new calibration parameters. Please upgrade your firmware");
            return "0";
        }
        ArrayList<Integer> iCalib = YAPI._decodeWords(actualCparams);
        int calibrationOffset = iCalib.get(0);
        int divisor = iCalib.get(1);
        if (divisor > 0) {
            calibType = npt;
        } else {
            calibType = 10 + npt;
        }
        res = Integer.toString(calibType);
        if (calibType <= 10) {
            for (int i = 0; i < npt; i++) {
                rawVal = (int) (rawValues.get(i) * divisor - calibrationOffset + .5);
                if (rawVal >= minRaw && rawVal < 65536) {
                    refVal = (int) (refValues.get(i) * divisor - calibrationOffset + .5);
                    if (refVal >= 0 && refVal < 65536) {
                        res += String.format(",%d,%d", rawVal, refVal);
                        minRaw = rawVal + 1;
                    }
                }
            }
        } else {
            // 16-bit floating-point decimal encoding
            for (int i = 0; i < npt; i++) {
                rawVal = (int) YAPI._doubleToDecimal(rawValues.get(i));
                refVal = (int) YAPI._doubleToDecimal(refValues.get(i));
                res += String.format(",%d,%d", rawVal, refVal);
            }
        }
        return res;
    }


    /*
     * Method used to decode calibration points from fixed-point 16-bit integers
     */
    static int _decodeCalibrationPoints(String calibParams, ArrayList<Integer> intPt, ArrayList<Double> rawPt, ArrayList<Double> calPt) throws YAPI_Exception
    {

        intPt.clear();
        rawPt.clear();
        calPt.clear();
        if (calibParams.equals("") || calibParams.equals("0")) {
            // old format: no calibration
            return 0;
        }
        if (calibParams.indexOf(',') != -1) {
            // old format -> device must do the calibration
            return -1;
        }
        // new format
        ArrayList<Integer> iCalib = YAPI._decodeWords(calibParams);
        if (iCalib.size() < 2) {
            // bad format
            return -1;
        }
        if (iCalib.size() == 2) {
            // no calibration
            return 0;
        }
        int pos = 0;
        double calibrationOffset = iCalib.get(pos++);
        double divisor = iCalib.get(pos++);
        int calibType = iCalib.get(pos++);
        if (calibType == 0) {
            return 0;
        }
        // parse calibration parameters
        while (pos < iCalib.size()) {
            int ival = iCalib.get(pos++);
            double fval;
            if (calibType <= 10) {
                fval = (ival + calibrationOffset) / divisor;
            } else {
                fval = YAPI._decimalToDouble(ival);
            }
            intPt.add(ival);
            if ((intPt.size() & 1) == 1) {
                rawPt.add(fval);
            } else {
                calPt.add(fval);
            }
        }
        if (intPt.size() < 10) {
            return -1;
        }
        return calibType;
    }


    /**
     * @param func : functionid
     */
    protected YSensor(String func)
    {
        super(func);
        _className = "Sensor";
        //--- (generated code: YSensor attributes initialization)
        //--- (end of generated code: YSensor attributes initialization)
    }


    //--- (generated code: YSensor implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("unit")) {
            _unit = json_val.getString("unit");
        }
        if (json_val.has("currentValue")) {
            _currentValue = Math.round(json_val.getDouble("currentValue") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("lowestValue")) {
            _lowestValue = Math.round(json_val.getDouble("lowestValue") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("highestValue")) {
            _highestValue = Math.round(json_val.getDouble("highestValue") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("currentRawValue")) {
            _currentRawValue = Math.round(json_val.getDouble("currentRawValue") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("logFrequency")) {
            _logFrequency = json_val.getString("logFrequency");
        }
        if (json_val.has("reportFrequency")) {
            _reportFrequency = json_val.getString("reportFrequency");
        }
        if (json_val.has("calibrationParam")) {
            _calibrationParam = json_val.getString("calibrationParam");
        }
        if (json_val.has("resolution")) {
            _resolution = Math.round(json_val.getDouble("resolution") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("sensorState")) {
            _sensorState = json_val.getInt("sensorState");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the measuring unit for the measure.
     *
     * @return a string corresponding to the measuring unit for the measure
     *
     * @throws YAPI_Exception on error
     */
    public String get_unit() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return UNIT_INVALID;
            }
        }
        return _unit;
    }

    /**
     * Returns the measuring unit for the measure.
     *
     * @return a string corresponding to the measuring unit for the measure
     *
     * @throws YAPI_Exception on error
     */
    public String getUnit() throws YAPI_Exception
    {
        return get_unit();
    }

    /**
     * Returns the current value of the measure, in the specified unit, as a floating point number.
     *
     *  @return a floating point number corresponding to the current value of the measure, in the specified
     * unit, as a floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double get_currentValue() throws YAPI_Exception
    {
        double res;
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CURRENTVALUE_INVALID;
            }
        }
        res = _applyCalibration(_currentRawValue);
        if (res == CURRENTVALUE_INVALID) {
            res = _currentValue;
        }
        res = res * _iresol;
        return (double)Math.round(res) / _iresol;
    }

    /**
     * Returns the current value of the measure, in the specified unit, as a floating point number.
     *
     *  @return a floating point number corresponding to the current value of the measure, in the specified
     * unit, as a floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double getCurrentValue() throws YAPI_Exception
    {
        return get_currentValue();
    }

    /**
     * Changes the recorded minimal value observed.
     *
     * @param newval : a floating point number corresponding to the recorded minimal value observed
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_lowestValue(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
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
     * @throws YAPI_Exception on error
     */
    public int setLowestValue(double newval)  throws YAPI_Exception
    {
        return set_lowestValue(newval);
    }

    /**
     * Returns the minimal value observed for the measure since the device was started.
     *
     *  @return a floating point number corresponding to the minimal value observed for the measure since
     * the device was started
     *
     * @throws YAPI_Exception on error
     */
    public double get_lowestValue() throws YAPI_Exception
    {
        double res;
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LOWESTVALUE_INVALID;
            }
        }
        res = _lowestValue * _iresol;
        return (double)Math.round(res) / _iresol;
    }

    /**
     * Returns the minimal value observed for the measure since the device was started.
     *
     *  @return a floating point number corresponding to the minimal value observed for the measure since
     * the device was started
     *
     * @throws YAPI_Exception on error
     */
    public double getLowestValue() throws YAPI_Exception
    {
        return get_lowestValue();
    }

    /**
     * Changes the recorded maximal value observed.
     *
     * @param newval : a floating point number corresponding to the recorded maximal value observed
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_highestValue(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
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
     * @throws YAPI_Exception on error
     */
    public int setHighestValue(double newval)  throws YAPI_Exception
    {
        return set_highestValue(newval);
    }

    /**
     * Returns the maximal value observed for the measure since the device was started.
     *
     *  @return a floating point number corresponding to the maximal value observed for the measure since
     * the device was started
     *
     * @throws YAPI_Exception on error
     */
    public double get_highestValue() throws YAPI_Exception
    {
        double res;
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return HIGHESTVALUE_INVALID;
            }
        }
        res = _highestValue * _iresol;
        return (double)Math.round(res) / _iresol;
    }

    /**
     * Returns the maximal value observed for the measure since the device was started.
     *
     *  @return a floating point number corresponding to the maximal value observed for the measure since
     * the device was started
     *
     * @throws YAPI_Exception on error
     */
    public double getHighestValue() throws YAPI_Exception
    {
        return get_highestValue();
    }

    /**
     *  Returns the uncalibrated, unrounded raw value returned by the sensor, in the specified unit, as a
     * floating point number.
     *
     *  @return a floating point number corresponding to the uncalibrated, unrounded raw value returned by
     * the sensor, in the specified unit, as a floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double get_currentRawValue() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CURRENTRAWVALUE_INVALID;
            }
        }
        return _currentRawValue;
    }

    /**
     *  Returns the uncalibrated, unrounded raw value returned by the sensor, in the specified unit, as a
     * floating point number.
     *
     *  @return a floating point number corresponding to the uncalibrated, unrounded raw value returned by
     * the sensor, in the specified unit, as a floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double getCurrentRawValue() throws YAPI_Exception
    {
        return get_currentRawValue();
    }

    /**
     * Returns the datalogger recording frequency for this function, or "OFF"
     * when measures are not stored in the data logger flash memory.
     *
     * @return a string corresponding to the datalogger recording frequency for this function, or "OFF"
     *         when measures are not stored in the data logger flash memory
     *
     * @throws YAPI_Exception on error
     */
    public String get_logFrequency() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LOGFREQUENCY_INVALID;
            }
        }
        return _logFrequency;
    }

    /**
     * Returns the datalogger recording frequency for this function, or "OFF"
     * when measures are not stored in the data logger flash memory.
     *
     * @return a string corresponding to the datalogger recording frequency for this function, or "OFF"
     *         when measures are not stored in the data logger flash memory
     *
     * @throws YAPI_Exception on error
     */
    public String getLogFrequency() throws YAPI_Exception
    {
        return get_logFrequency();
    }

    /**
     * Changes the datalogger recording frequency for this function.
     * The frequency can be specified as samples per second,
     * as sample per minute (for instance "15/m") or in samples per
     * hour (eg. "4/h"). To disable recording for this function, use
     * the value "OFF".
     *
     * @param newval : a string corresponding to the datalogger recording frequency for this function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_logFrequency(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("logFrequency",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the datalogger recording frequency for this function.
     * The frequency can be specified as samples per second,
     * as sample per minute (for instance "15/m") or in samples per
     * hour (eg. "4/h"). To disable recording for this function, use
     * the value "OFF".
     *
     * @param newval : a string corresponding to the datalogger recording frequency for this function
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLogFrequency(String newval)  throws YAPI_Exception
    {
        return set_logFrequency(newval);
    }

    /**
     * Returns the timed value notification frequency, or "OFF" if timed
     * value notifications are disabled for this function.
     *
     * @return a string corresponding to the timed value notification frequency, or "OFF" if timed
     *         value notifications are disabled for this function
     *
     * @throws YAPI_Exception on error
     */
    public String get_reportFrequency() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return REPORTFREQUENCY_INVALID;
            }
        }
        return _reportFrequency;
    }

    /**
     * Returns the timed value notification frequency, or "OFF" if timed
     * value notifications are disabled for this function.
     *
     * @return a string corresponding to the timed value notification frequency, or "OFF" if timed
     *         value notifications are disabled for this function
     *
     * @throws YAPI_Exception on error
     */
    public String getReportFrequency() throws YAPI_Exception
    {
        return get_reportFrequency();
    }

    /**
     * Changes the timed value notification frequency for this function.
     * The frequency can be specified as samples per second,
     * as sample per minute (for instance "15/m") or in samples per
     * hour (eg. "4/h"). To disable timed value notifications for this
     * function, use the value "OFF".
     *
     * @param newval : a string corresponding to the timed value notification frequency for this function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_reportFrequency(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("reportFrequency",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the timed value notification frequency for this function.
     * The frequency can be specified as samples per second,
     * as sample per minute (for instance "15/m") or in samples per
     * hour (eg. "4/h"). To disable timed value notifications for this
     * function, use the value "OFF".
     *
     * @param newval : a string corresponding to the timed value notification frequency for this function
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setReportFrequency(String newval)  throws YAPI_Exception
    {
        return set_reportFrequency(newval);
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String get_calibrationParam() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CALIBRATIONPARAM_INVALID;
            }
        }
        return _calibrationParam;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String getCalibrationParam() throws YAPI_Exception
    {
        return get_calibrationParam();
    }

    public int set_calibrationParam(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("calibrationParam",rest_val);
        return YAPI.SUCCESS;
    }

    public int setCalibrationParam(String newval)  throws YAPI_Exception
    {
        return set_calibrationParam(newval);
    }

    /**
     * Changes the resolution of the measured physical values. The resolution corresponds to the numerical precision
     * when displaying value. It does not change the precision of the measure itself.
     *
     * @param newval : a floating point number corresponding to the resolution of the measured physical values
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_resolution(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
        _setAttr("resolution",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the resolution of the measured physical values. The resolution corresponds to the numerical precision
     * when displaying value. It does not change the precision of the measure itself.
     *
     * @param newval : a floating point number corresponding to the resolution of the measured physical values
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setResolution(double newval)  throws YAPI_Exception
    {
        return set_resolution(newval);
    }

    /**
     * Returns the resolution of the measured values. The resolution corresponds to the numerical precision
     * of the measures, which is not always the same as the actual precision of the sensor.
     *
     * @return a floating point number corresponding to the resolution of the measured values
     *
     * @throws YAPI_Exception on error
     */
    public double get_resolution() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return RESOLUTION_INVALID;
            }
        }
        return _resolution;
    }

    /**
     * Returns the resolution of the measured values. The resolution corresponds to the numerical precision
     * of the measures, which is not always the same as the actual precision of the sensor.
     *
     * @return a floating point number corresponding to the resolution of the measured values
     *
     * @throws YAPI_Exception on error
     */
    public double getResolution() throws YAPI_Exception
    {
        return get_resolution();
    }

    /**
     * Returns the sensor health state code, which is zero when there is an up-to-date measure
     * available or a positive code if the sensor is not able to provide a measure right now.
     *
     *  @return an integer corresponding to the sensor health state code, which is zero when there is an
     * up-to-date measure
     *         available or a positive code if the sensor is not able to provide a measure right now
     *
     * @throws YAPI_Exception on error
     */
    public int get_sensorState() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SENSORSTATE_INVALID;
            }
        }
        return _sensorState;
    }

    /**
     * Returns the sensor health state code, which is zero when there is an up-to-date measure
     * available or a positive code if the sensor is not able to provide a measure right now.
     *
     *  @return an integer corresponding to the sensor health state code, which is zero when there is an
     * up-to-date measure
     *         available or a positive code if the sensor is not able to provide a measure right now
     *
     * @throws YAPI_Exception on error
     */
    public int getSensorState() throws YAPI_Exception
    {
        return get_sensorState();
    }

    /**
     * Retrieves a sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSensor.isOnline() to test if the sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the sensor
     *
     * @return a YSensor object allowing you to drive the sensor.
     */
    public static YSensor FindSensor(String func)
    {
        YSensor obj;
        obj = (YSensor) YFunction._FindFromCache("Sensor", func);
        if (obj == null) {
            obj = new YSensor(func);
            YFunction._AddToCache("Sensor", func, obj);
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
        _valueCallbackSensor = callback;
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
        if (_valueCallbackSensor != null) {
            _valueCallbackSensor.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    @Override
    public int _parserHelper()
    {
        int position;
        int maxpos;
        ArrayList<Integer> iCalib = new ArrayList<Integer>();
        int iRaw;
        int iRef;
        double fRaw;
        double fRef;
        _caltyp = -1;
        _scale = -1;
        _isScal32 = false;
        _calpar.clear();
        _calraw.clear();
        _calref.clear();
        // Store inverted resolution, to provide better rounding
        if (_resolution > 0) {
            _iresol = (double)Math.round(1.0 / _resolution);
        } else {
            _iresol = 10000;
            _resolution = 0.0001;
        }
        // Old format: supported when there is no calibration
        if (_calibrationParam.equals("") || _calibrationParam.equals("0")) {
            _caltyp = 0;
            return 0;
        }
        if ((_calibrationParam).indexOf(",") >= 0) {
            iCalib = SafeYAPI()._decodeFloats(_calibrationParam);
            _caltyp = ((iCalib.get(0).intValue()) / (1000));
            if (_caltyp > 0) {
                if (_caltyp < YAPI.YOCTO_CALIB_TYPE_OFS) {
                    _caltyp = -1;
                    return 0;
                }
                _calhdl = SafeYAPI()._getCalibrationHandler(_caltyp);
                if (!(_calhdl != null)) {
                    _caltyp = -1;
                    return 0;
                }
            }
            _isScal = true;
            _isScal32 = true;
            _offset = 0;
            _scale = 1000;
            maxpos = iCalib.size();
            _calpar.clear();
            position = 1;
            while (position < maxpos) {
                _calpar.add(iCalib.get(position));
                position = position + 1;
            }
            _calraw.clear();
            _calref.clear();
            position = 1;
            while (position + 1 < maxpos) {
                fRaw = iCalib.get(position).doubleValue();
                fRaw = fRaw / 1000.0;
                fRef = iCalib.get(position + 1).doubleValue();
                fRef = fRef / 1000.0;
                _calraw.add(fRaw);
                _calref.add(fRef);
                position = position + 2;
            }
        } else {
            iCalib = SafeYAPI()._decodeWords(_calibrationParam);
            if (iCalib.size() < 2) {
                _caltyp = -1;
                return 0;
            }
            _isScal = (iCalib.get(1).intValue() > 0);
            if (_isScal) {
                _offset = iCalib.get(0).doubleValue();
                if (_offset > 32767) {
                    _offset = _offset - 65536;
                }
                _scale = iCalib.get(1).doubleValue();
                _decexp = 0;
            } else {
                _offset = 0;
                _scale = 1;
                _decexp = 1.0;
                position = iCalib.get(0).intValue();
                while (position > 0) {
                    _decexp = _decexp * 10;
                    position = position - 1;
                }
            }
            if (iCalib.size() == 2) {
                _caltyp = 0;
                return 0;
            }
            _caltyp = iCalib.get(2).intValue();
            _calhdl = SafeYAPI()._getCalibrationHandler(_caltyp);
            if (_caltyp <= 10) {
                maxpos = _caltyp;
            } else {
                if (_caltyp <= 20) {
                    maxpos = _caltyp - 10;
                } else {
                    maxpos = 5;
                }
            }
            maxpos = 3 + 2 * maxpos;
            if (maxpos > iCalib.size()) {
                maxpos = iCalib.size();
            }
            _calpar.clear();
            _calraw.clear();
            _calref.clear();
            position = 3;
            while (position + 1 < maxpos) {
                iRaw = iCalib.get(position).intValue();
                iRef = iCalib.get(position + 1).intValue();
                _calpar.add(iRaw);
                _calpar.add(iRef);
                if (_isScal) {
                    fRaw = iRaw;
                    fRaw = (fRaw - _offset) / _scale;
                    fRef = iRef;
                    fRef = (fRef - _offset) / _scale;
                    _calraw.add(fRaw);
                    _calref.add(fRef);
                } else {
                    _calraw.add(SafeYAPI()._decimalToDouble(iRaw));
                    _calref.add(SafeYAPI()._decimalToDouble(iRef));
                }
                position = position + 2;
            }
        }
        return 0;
    }

    /**
     * Checks if the sensor is currently able to provide an up-to-date measure.
     * Returns false if the device is unreachable, or if the sensor does not have
     * a current measure to transmit. No exception is raised if there is an error
     * while trying to contact the device hosting $THEFUNCTION$.
     *
     * @return true if the sensor can provide an up-to-date measure, and false otherwise
     */
    public boolean isSensorReady()
    {
        if (!(isOnline())) {
            return false;
        }
        if (!(_sensorState == 0)) {
            return false;
        }
        return true;
    }

    /**
     * Starts the data logger on the device. Note that the data logger
     * will only save the measures on this sensor if the logFrequency
     * is not set to "OFF".
     *
     * @return YAPI.SUCCESS if the call succeeds.
     */
    public int startDataLogger() throws YAPI_Exception
    {
        byte[] res;
        // may throw an exception
        res = _download("api/dataLogger/recording?recording=1");
        if (!((res).length>0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "unable to start datalogger");}
        return YAPI.SUCCESS;
    }

    /**
     * Stops the datalogger on the device.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     */
    public int stopDataLogger() throws YAPI_Exception
    {
        byte[] res;
        // may throw an exception
        res = _download("api/dataLogger/recording?recording=0");
        if (!((res).length>0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "unable to stop datalogger");}
        return YAPI.SUCCESS;
    }

    /**
     * Retrieves a DataSet object holding historical data for this
     * sensor, for a specified time interval. The measures will be
     * retrieved from the data logger, which must have been turned
     * on at the desired time. See the documentation of the DataSet
     * class for information on how to get an overview of the
     * recorded data, and how to load progressively a large set
     * of measures from the data logger.
     *
     * This function only works if the device uses a recent firmware,
     * as DataSet objects are not supported by firmwares older than
     * version 13000.
     *
     * @param startTime : the start of the desired measure time interval,
     *         as a Unix timestamp, i.e. the number of seconds since
     *         January 1, 1970 UTC. The special value 0 can be used
     *         to include any meaasure, without initial limit.
     * @param endTime : the end of the desired measure time interval,
     *         as a Unix timestamp, i.e. the number of seconds since
     *         January 1, 1970 UTC. The special value 0 can be used
     *         to include any meaasure, without ending limit.
     *
     * @return an instance of YDataSet, providing access to historical
     *         data. Past measures can be loaded progressively
     *         using methods from the YDataSet object.
     */
    public YDataSet get_recordedData(long startTime,long endTime) throws YAPI_Exception
    {
        String funcid;
        String funit;
        // may throw an exception
        funcid = get_functionId();
        funit = get_unit();
        return new YDataSet(this, funcid, funit, startTime, endTime);
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
        if (callback != null) {
            YFunction._UpdateTimedReportCallbackList(this, true);
        } else {
            YFunction._UpdateTimedReportCallbackList(this, false);
        }
        _timedReportCallbackSensor = callback;
        return 0;
    }

    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackSensor != null) {
            _timedReportCallbackSensor.timedReportCallback(this, value);
        } else {
        }
        return 0;
    }

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
     * @throws YAPI_Exception on error
     */
    public int calibrateFromPoints(ArrayList<Double> rawValues,ArrayList<Double> refValues) throws YAPI_Exception
    {
        String rest_val;
        // may throw an exception
        rest_val = _encodeCalibrationPoints(rawValues, refValues);
        return _setAttr("calibrationParam", rest_val);
    }

    /**
     * Retrieves error correction data points previously entered using the method
     * calibrateFromPoints.
     *
     * @param rawValues : array of floating point numbers, that will be filled by the
     *         function with the raw sensor values for the correction points.
     * @param refValues : array of floating point numbers, that will be filled by the
     *         function with the desired values for the correction points.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int loadCalibrationPoints(ArrayList<Double> rawValues,ArrayList<Double> refValues) throws YAPI_Exception
    {
        rawValues.clear();
        refValues.clear();
        // Load function parameters if not yet loaded
        if (_scale == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return YAPI.DEVICE_NOT_FOUND;
            }
        }
        if (_caltyp < 0) {
            _throw(YAPI.NOT_SUPPORTED, "Calibration parameters format mismatch. Please upgrade your library or firmware.");
            return YAPI.NOT_SUPPORTED;
        }
        rawValues.clear();
        refValues.clear();
        for (double ii:_calraw) {
            rawValues.add(ii);
        }
        for (double ii:_calref) {
            refValues.add(ii);
        }
        return YAPI.SUCCESS;
    }

    public String _encodeCalibrationPoints(ArrayList<Double> rawValues,ArrayList<Double> refValues) throws YAPI_Exception
    {
        String res;
        int npt;
        int idx;
        int iRaw;
        int iRef;
        npt = rawValues.size();
        if (npt != refValues.size()) {
            _throw(YAPI.INVALID_ARGUMENT, "Invalid calibration parameters (size mismatch)");
            return YAPI.INVALID_STRING;
        }
        // Shortcut when building empty calibration parameters
        if (npt == 0) {
            return "0";
        }
        // Load function parameters if not yet loaded
        if (_scale == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return YAPI.INVALID_STRING;
            }
        }
        // Detect old firmware
        if ((_caltyp < 0) || (_scale < 0)) {
            _throw(YAPI.NOT_SUPPORTED, "Calibration parameters format mismatch. Please upgrade your library or firmware.");
            return "0";
        }
        if (_isScal32) {
            res = String.format("%d",YAPI.YOCTO_CALIB_TYPE_OFS);
            idx = 0;
            while (idx < npt) {
                res = String.format("%s,%f,%f", res, rawValues.get(idx).doubleValue(),refValues.get(idx).doubleValue());
                idx = idx + 1;
            }
        } else {
            if (_isScal) {
                res = String.format("%d",npt);
                idx = 0;
                while (idx < npt) {
                    iRaw = (int) (double)Math.round(rawValues.get(idx).doubleValue() * _scale + _offset);
                    iRef = (int) (double)Math.round(refValues.get(idx).doubleValue() * _scale + _offset);
                    res = String.format("%s,%d,%d", res, iRaw,iRef);
                    idx = idx + 1;
                }
            } else {
                res = String.format("%d",10 + npt);
                idx = 0;
                while (idx < npt) {
                    iRaw = (int) SafeYAPI()._doubleToDecimal(rawValues.get(idx).doubleValue());
                    iRef = (int) SafeYAPI()._doubleToDecimal(refValues.get(idx).doubleValue());
                    res = String.format("%s,%d,%d", res, iRaw,iRef);
                    idx = idx + 1;
                }
            }
        }
        return res;
    }

    public double _applyCalibration(double rawValue)
    {
        if (rawValue == CURRENTVALUE_INVALID) {
            return CURRENTVALUE_INVALID;
        }
        if (_caltyp == 0) {
            return rawValue;
        }
        if (_caltyp < 0) {
            return CURRENTVALUE_INVALID;
        }
        if (!(_calhdl != null)) {
            return CURRENTVALUE_INVALID;
        }
        return _calhdl.yCalibrationHandler(rawValue, _caltyp, _calpar, _calraw, _calref);
    }

    public YMeasure _decodeTimedReport(double timestamp,ArrayList<Integer> report)
    {
        int i;
        int byteVal;
        int poww;
        int minRaw;
        int avgRaw;
        int maxRaw;
        int sublen;
        int difRaw;
        double startTime;
        double endTime;
        double minVal;
        double avgVal;
        double maxVal;
        startTime = _prevTimedReport;
        endTime = timestamp;
        _prevTimedReport = endTime;
        if (startTime == 0) {
            startTime = endTime;
        }
        if (report.get(0).intValue() == 2) {
            if (report.size() <= 5) {
                poww = 1;
                avgRaw = 0;
                byteVal = 0;
                i = 1;
                while (i < report.size()) {
                    byteVal = report.get(i).intValue();
                    avgRaw = avgRaw + poww * byteVal;
                    poww = poww * 0x100;
                    i = i + 1;
                }
                if (((byteVal) & (0x80)) != 0) {
                    avgRaw = avgRaw - poww;
                }
                avgVal = avgRaw / 1000.0;
                if (_caltyp != 0) {
                    if (_calhdl != null) {
                        avgVal = _calhdl.yCalibrationHandler(avgVal, _caltyp, _calpar, _calraw, _calref);
                    }
                }
                minVal = avgVal;
                maxVal = avgVal;
            } else {
                sublen = 1 + ((report.get(1).intValue()) & (3));
                poww = 1;
                avgRaw = 0;
                byteVal = 0;
                i = 2;
                while ((sublen > 0) && (i < report.size())) {
                    byteVal = report.get(i).intValue();
                    avgRaw = avgRaw + poww * byteVal;
                    poww = poww * 0x100;
                    i = i + 1;
                    sublen = sublen - 1;
                }
                if (((byteVal) & (0x80)) != 0) {
                    avgRaw = avgRaw - poww;
                }
                sublen = 1 + ((((report.get(1).intValue()) >> (2))) & (3));
                poww = 1;
                difRaw = 0;
                while ((sublen > 0) && (i < report.size())) {
                    byteVal = report.get(i).intValue();
                    difRaw = difRaw + poww * byteVal;
                    poww = poww * 0x100;
                    i = i + 1;
                    sublen = sublen - 1;
                }
                minRaw = avgRaw - difRaw;
                sublen = 1 + ((((report.get(1).intValue()) >> (4))) & (3));
                poww = 1;
                difRaw = 0;
                while ((sublen > 0) && (i < report.size())) {
                    byteVal = report.get(i).intValue();
                    difRaw = difRaw + poww * byteVal;
                    poww = poww * 0x100;
                    i = i + 1;
                    sublen = sublen - 1;
                }
                maxRaw = avgRaw + difRaw;
                avgVal = avgRaw / 1000.0;
                minVal = minRaw / 1000.0;
                maxVal = maxRaw / 1000.0;
                if (_caltyp != 0) {
                    if (_calhdl != null) {
                        avgVal = _calhdl.yCalibrationHandler(avgVal, _caltyp, _calpar, _calraw, _calref);
                        minVal = _calhdl.yCalibrationHandler(minVal, _caltyp, _calpar, _calraw, _calref);
                        maxVal = _calhdl.yCalibrationHandler(maxVal, _caltyp, _calpar, _calraw, _calref);
                    }
                }
            }
        } else {
            if (report.get(0).intValue() == 0) {
                poww = 1;
                avgRaw = 0;
                byteVal = 0;
                i = 1;
                while (i < report.size()) {
                    byteVal = report.get(i).intValue();
                    avgRaw = avgRaw + poww * byteVal;
                    poww = poww * 0x100;
                    i = i + 1;
                }
                if (_isScal) {
                    avgVal = _decodeVal(avgRaw);
                } else {
                    if (((byteVal) & (0x80)) != 0) {
                        avgRaw = avgRaw - poww;
                    }
                    avgVal = _decodeAvg(avgRaw);
                }
                minVal = avgVal;
                maxVal = avgVal;
            } else {
                minRaw = report.get(1).intValue() + 0x100 * report.get(2).intValue();
                maxRaw = report.get(3).intValue() + 0x100 * report.get(4).intValue();
                avgRaw = report.get(5).intValue() + 0x100 * report.get(6).intValue() + 0x10000 * report.get(7).intValue();
                byteVal = report.get(8).intValue();
                if (((byteVal) & (0x80)) == 0) {
                    avgRaw = avgRaw + 0x1000000 * byteVal;
                } else {
                    avgRaw = avgRaw - 0x1000000 * (0x100 - byteVal);
                }
                minVal = _decodeVal(minRaw);
                avgVal = _decodeAvg(avgRaw);
                maxVal = _decodeVal(maxRaw);
            }
        }
        return new YMeasure(startTime, endTime, minVal, avgVal, maxVal);
    }

    public double _decodeVal(int w)
    {
        double val;
        val = w;
        if (_isScal) {
            val = (val - _offset) / _scale;
        } else {
            val = SafeYAPI()._decimalToDouble(w);
        }
        if (_caltyp != 0) {
            if (_calhdl != null) {
                val = _calhdl.yCalibrationHandler(val, _caltyp, _calpar, _calraw, _calref);
            }
        }
        return val;
    }

    public double _decodeAvg(int dw)
    {
        double val;
        val = dw;
        if (_isScal) {
            val = (val / 100 - _offset) / _scale;
        } else {
            val = val / _decexp;
        }
        if (_caltyp != 0) {
            if (_calhdl != null) {
                val = _calhdl.yCalibrationHandler(val, _caltyp, _calpar, _calraw, _calref);
            }
        }
        return val;
    }

    /**
     * Continues the enumeration of sensors started using yFirstSensor().
     *
     * @return a pointer to a YSensor object, corresponding to
     *         a sensor currently online, or a null pointer
     *         if there are no more sensors to enumerate.
     */
    public  YSensor nextSensor()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindSensor(next_hwid);
    }

    /**
     * Starts the enumeration of sensors currently accessible.
     * Use the method YSensor.nextSensor() to iterate on
     * next sensors.
     *
     * @return a pointer to a YSensor object, corresponding to
     *         the first sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YSensor FirstSensor()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Sensor");
        if (next_hwid == null)  return null;
        return FindSensor(next_hwid);
    }

    //--- (end of generated code: YSensor implementation)
}
