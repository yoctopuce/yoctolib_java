/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindColorSensor(), the high-level API for ColorSensor functions
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

//--- (YColorSensor return codes)
//--- (end of YColorSensor return codes)
//--- (YColorSensor yapiwrapper)
//--- (end of YColorSensor yapiwrapper)
//--- (YColorSensor class start)
/**
 * YColorSensor Class: color sensor control interface
 *
 * The YColorSensor class allows you to read and configure Yoctopuce color sensors.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YColorSensor extends YFunction
{
//--- (end of YColorSensor class start)
//--- (YColorSensor definitions)
    /**
     * invalid estimationModel value
     */
    public static final int ESTIMATIONMODEL_REFLECTION = 0;
    public static final int ESTIMATIONMODEL_EMISSION = 1;
    public static final int ESTIMATIONMODEL_INVALID = -1;
    /**
     * invalid workingMode value
     */
    public static final int WORKINGMODE_AUTO = 0;
    public static final int WORKINGMODE_EXPERT = 1;
    public static final int WORKINGMODE_INVALID = -1;
    /**
     * invalid ledCurrent value
     */
    public static final int LEDCURRENT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid ledCalibration value
     */
    public static final int LEDCALIBRATION_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid integrationTime value
     */
    public static final int INTEGRATIONTIME_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid gain value
     */
    public static final int GAIN_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid saturation value
     */
    public static final int SATURATION_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid estimatedRGB value
     */
    public static final int ESTIMATEDRGB_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid estimatedHSL value
     */
    public static final int ESTIMATEDHSL_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid estimatedXYZ value
     */
    public static final String ESTIMATEDXYZ_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid estimatedOkLab value
     */
    public static final String ESTIMATEDOKLAB_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid nearRAL1 value
     */
    public static final String NEARRAL1_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid nearRAL2 value
     */
    public static final String NEARRAL2_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid nearRAL3 value
     */
    public static final String NEARRAL3_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid nearHTMLColor value
     */
    public static final String NEARHTMLCOLOR_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid nearSimpleColorIndex value
     */
    public static final int NEARSIMPLECOLORINDEX_BROWN = 0;
    public static final int NEARSIMPLECOLORINDEX_RED = 1;
    public static final int NEARSIMPLECOLORINDEX_ORANGE = 2;
    public static final int NEARSIMPLECOLORINDEX_YELLOW = 3;
    public static final int NEARSIMPLECOLORINDEX_WHITE = 4;
    public static final int NEARSIMPLECOLORINDEX_GRAY = 5;
    public static final int NEARSIMPLECOLORINDEX_BLACK = 6;
    public static final int NEARSIMPLECOLORINDEX_GREEN = 7;
    public static final int NEARSIMPLECOLORINDEX_BLUE = 8;
    public static final int NEARSIMPLECOLORINDEX_PURPLE = 9;
    public static final int NEARSIMPLECOLORINDEX_PINK = 10;
    public static final int NEARSIMPLECOLORINDEX_INVALID = -1;
    /**
     * invalid nearSimpleColor value
     */
    public static final String NEARSIMPLECOLOR_INVALID = YAPI.INVALID_STRING;
    protected int _estimationModel = ESTIMATIONMODEL_INVALID;
    protected int _workingMode = WORKINGMODE_INVALID;
    protected int _ledCurrent = LEDCURRENT_INVALID;
    protected int _ledCalibration = LEDCALIBRATION_INVALID;
    protected int _integrationTime = INTEGRATIONTIME_INVALID;
    protected int _gain = GAIN_INVALID;
    protected int _saturation = SATURATION_INVALID;
    protected int _estimatedRGB = ESTIMATEDRGB_INVALID;
    protected int _estimatedHSL = ESTIMATEDHSL_INVALID;
    protected String _estimatedXYZ = ESTIMATEDXYZ_INVALID;
    protected String _estimatedOkLab = ESTIMATEDOKLAB_INVALID;
    protected String _nearRAL1 = NEARRAL1_INVALID;
    protected String _nearRAL2 = NEARRAL2_INVALID;
    protected String _nearRAL3 = NEARRAL3_INVALID;
    protected String _nearHTMLColor = NEARHTMLCOLOR_INVALID;
    protected int _nearSimpleColorIndex = NEARSIMPLECOLORINDEX_INVALID;
    protected String _nearSimpleColor = NEARSIMPLECOLOR_INVALID;
    protected UpdateCallback _valueCallbackColorSensor = null;

    /**
     * Deprecated UpdateCallback for ColorSensor
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YColorSensor function, String functionValue);
    }

    /**
     * TimedReportCallback for ColorSensor
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YColorSensor  function, YMeasure measure);
    }
    //--- (end of YColorSensor definitions)


    /**
     *
     * @param func : functionid
     */
    protected YColorSensor(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "ColorSensor";
        //--- (YColorSensor attributes initialization)
        //--- (end of YColorSensor attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YColorSensor(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YColorSensor implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("estimationModel")) {
            _estimationModel = json_val.getInt("estimationModel");
        }
        if (json_val.has("workingMode")) {
            _workingMode = json_val.getInt("workingMode");
        }
        if (json_val.has("ledCurrent")) {
            _ledCurrent = json_val.getInt("ledCurrent");
        }
        if (json_val.has("ledCalibration")) {
            _ledCalibration = json_val.getInt("ledCalibration");
        }
        if (json_val.has("integrationTime")) {
            _integrationTime = json_val.getInt("integrationTime");
        }
        if (json_val.has("gain")) {
            _gain = json_val.getInt("gain");
        }
        if (json_val.has("saturation")) {
            _saturation = json_val.getInt("saturation");
        }
        if (json_val.has("estimatedRGB")) {
            _estimatedRGB = json_val.getInt("estimatedRGB");
        }
        if (json_val.has("estimatedHSL")) {
            _estimatedHSL = json_val.getInt("estimatedHSL");
        }
        if (json_val.has("estimatedXYZ")) {
            _estimatedXYZ = json_val.getString("estimatedXYZ");
        }
        if (json_val.has("estimatedOkLab")) {
            _estimatedOkLab = json_val.getString("estimatedOkLab");
        }
        if (json_val.has("nearRAL1")) {
            _nearRAL1 = json_val.getString("nearRAL1");
        }
        if (json_val.has("nearRAL2")) {
            _nearRAL2 = json_val.getString("nearRAL2");
        }
        if (json_val.has("nearRAL3")) {
            _nearRAL3 = json_val.getString("nearRAL3");
        }
        if (json_val.has("nearHTMLColor")) {
            _nearHTMLColor = json_val.getString("nearHTMLColor");
        }
        if (json_val.has("nearSimpleColorIndex")) {
            _nearSimpleColorIndex = json_val.getInt("nearSimpleColorIndex");
        }
        if (json_val.has("nearSimpleColor")) {
            _nearSimpleColor = json_val.getString("nearSimpleColor");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the predictive model used for color estimation (reflective or emissive).
     *
     *  @return either YColorSensor.ESTIMATIONMODEL_REFLECTION or YColorSensor.ESTIMATIONMODEL_EMISSION,
     * according to the predictive model used for color estimation (reflective or emissive)
     *
     * @throws YAPI_Exception on error
     */
    public int get_estimationModel() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ESTIMATIONMODEL_INVALID;
                }
            }
            res = _estimationModel;
        }
        return res;
    }

    /**
     * Returns the predictive model used for color estimation (reflective or emissive).
     *
     *  @return either YColorSensor.ESTIMATIONMODEL_REFLECTION or YColorSensor.ESTIMATIONMODEL_EMISSION,
     * according to the predictive model used for color estimation (reflective or emissive)
     *
     * @throws YAPI_Exception on error
     */
    public int getEstimationModel() throws YAPI_Exception
    {
        return get_estimationModel();
    }

    /**
     * Changes the mpredictive model to be used for color estimation (reflective or emissive).
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : either YColorSensor.ESTIMATIONMODEL_REFLECTION or
     *  YColorSensor.ESTIMATIONMODEL_EMISSION, according to the mpredictive model to be used for color
     * estimation (reflective or emissive)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_estimationModel(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("estimationModel",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the mpredictive model to be used for color estimation (reflective or emissive).
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : either YColorSensor.ESTIMATIONMODEL_REFLECTION or
     *  YColorSensor.ESTIMATIONMODEL_EMISSION, according to the mpredictive model to be used for color
     * estimation (reflective or emissive)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setEstimationModel(int newval)  throws YAPI_Exception
    {
        return set_estimationModel(newval);
    }

    /**
     * Returns the sensor working mode.
     * In Auto mode, sensor parameters are automatically set based on the selected estimation model.
     * In Expert mode, sensor parameters such as gain and integration time are configured manually.
     *
     *  @return either YColorSensor.WORKINGMODE_AUTO or YColorSensor.WORKINGMODE_EXPERT, according to the
     * sensor working mode
     *
     * @throws YAPI_Exception on error
     */
    public int get_workingMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return WORKINGMODE_INVALID;
                }
            }
            res = _workingMode;
        }
        return res;
    }

    /**
     * Returns the sensor working mode.
     * In Auto mode, sensor parameters are automatically set based on the selected estimation model.
     * In Expert mode, sensor parameters such as gain and integration time are configured manually.
     *
     *  @return either YColorSensor.WORKINGMODE_AUTO or YColorSensor.WORKINGMODE_EXPERT, according to the
     * sensor working mode
     *
     * @throws YAPI_Exception on error
     */
    public int getWorkingMode() throws YAPI_Exception
    {
        return get_workingMode();
    }

    /**
     * Changes the sensor working mode.
     * In Auto mode, sensor parameters are automatically set based on the selected estimation model.
     * In Expert mode, sensor parameters such as gain and integration time are configured manually.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : either YColorSensor.WORKINGMODE_AUTO or YColorSensor.WORKINGMODE_EXPERT, according
     * to the sensor working mode
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_workingMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("workingMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the sensor working mode.
     * In Auto mode, sensor parameters are automatically set based on the selected estimation model.
     * In Expert mode, sensor parameters such as gain and integration time are configured manually.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : either YColorSensor.WORKINGMODE_AUTO or YColorSensor.WORKINGMODE_EXPERT, according
     * to the sensor working mode
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setWorkingMode(int newval)  throws YAPI_Exception
    {
        return set_workingMode(newval);
    }

    /**
     * Returns the amount of current sent to the illumination LEDs, for reflection measurements.
     * The value is an integer ranging from 0 (LEDs off) to 254 (LEDs at maximum intensity).
     *
     *  @return an integer corresponding to the amount of current sent to the illumination LEDs, for
     * reflection measurements
     *
     * @throws YAPI_Exception on error
     */
    public int get_ledCurrent() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LEDCURRENT_INVALID;
                }
            }
            res = _ledCurrent;
        }
        return res;
    }

    /**
     * Returns the amount of current sent to the illumination LEDs, for reflection measurements.
     * The value is an integer ranging from 0 (LEDs off) to 254 (LEDs at maximum intensity).
     *
     *  @return an integer corresponding to the amount of current sent to the illumination LEDs, for
     * reflection measurements
     *
     * @throws YAPI_Exception on error
     */
    public int getLedCurrent() throws YAPI_Exception
    {
        return get_ledCurrent();
    }

    /**
     * Changes the amount of current sent to the illumination LEDs, for reflection measurements.
     * The value is an integer ranging from 0 (LEDs off) to 254 (LEDs at maximum intensity).
     *
     *  @param newval : an integer corresponding to the amount of current sent to the illumination LEDs,
     * for reflection measurements
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_ledCurrent(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("ledCurrent",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the amount of current sent to the illumination LEDs, for reflection measurements.
     * The value is an integer ranging from 0 (LEDs off) to 254 (LEDs at maximum intensity).
     *
     *  @param newval : an integer corresponding to the amount of current sent to the illumination LEDs,
     * for reflection measurements
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLedCurrent(int newval)  throws YAPI_Exception
    {
        return set_ledCurrent(newval);
    }

    /**
     * Returns the current sent to the illumination LEDs during the last calibration.
     *
     * @return an integer corresponding to the current sent to the illumination LEDs during the last calibration
     *
     * @throws YAPI_Exception on error
     */
    public int get_ledCalibration() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LEDCALIBRATION_INVALID;
                }
            }
            res = _ledCalibration;
        }
        return res;
    }

    /**
     * Returns the current sent to the illumination LEDs during the last calibration.
     *
     * @return an integer corresponding to the current sent to the illumination LEDs during the last calibration
     *
     * @throws YAPI_Exception on error
     */
    public int getLedCalibration() throws YAPI_Exception
    {
        return get_ledCalibration();
    }

    /**
     * Remember the LED current sent to the illumination LEDs during a calibration.
     * Thanks to this, the device will be able to use the same current during measurements.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_ledCalibration(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("ledCalibration",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Remember the LED current sent to the illumination LEDs during a calibration.
     * Thanks to this, the device will be able to use the same current during measurements.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLedCalibration(int newval)  throws YAPI_Exception
    {
        return set_ledCalibration(newval);
    }

    /**
     * Returns the current integration time for spectral measurement, in milliseconds.
     * A longer integration time increase the sensitivity for low light conditions,
     * but reduces the measurement rate and may lead to saturation for lighter colors.
     *
     * @return an integer corresponding to the current integration time for spectral measurement, in milliseconds
     *
     * @throws YAPI_Exception on error
     */
    public int get_integrationTime() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return INTEGRATIONTIME_INVALID;
                }
            }
            res = _integrationTime;
        }
        return res;
    }

    /**
     * Returns the current integration time for spectral measurement, in milliseconds.
     * A longer integration time increase the sensitivity for low light conditions,
     * but reduces the measurement rate and may lead to saturation for lighter colors.
     *
     * @return an integer corresponding to the current integration time for spectral measurement, in milliseconds
     *
     * @throws YAPI_Exception on error
     */
    public int getIntegrationTime() throws YAPI_Exception
    {
        return get_integrationTime();
    }

    /**
     * Changes the integration time for spectral measurement, in milliseconds.
     * A longer integration time increase the sensitivity for low light conditions,
     * but reduces the measurement rate and may lead to saturation for lighter colors.
     * This method can only be used when the sensor is configured in expert mode;
     * when running in auto mode, the change will be ignored.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the integration time for spectral measurement, in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_integrationTime(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("integrationTime",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the integration time for spectral measurement, in milliseconds.
     * A longer integration time increase the sensitivity for low light conditions,
     * but reduces the measurement rate and may lead to saturation for lighter colors.
     * This method can only be used when the sensor is configured in expert mode;
     * when running in auto mode, the change will be ignored.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the integration time for spectral measurement, in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setIntegrationTime(int newval)  throws YAPI_Exception
    {
        return set_integrationTime(newval);
    }

    /**
     * Returns the current spectral channel detector gain exponent.
     * For a value n ranging from 0 to 12, the applied gain is 2^(n-1).
     * 0 corresponds to a gain of 0.5, and 12 corresponds to a gain of 2048.
     *
     * @return an integer corresponding to the current spectral channel detector gain exponent
     *
     * @throws YAPI_Exception on error
     */
    public int get_gain() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return GAIN_INVALID;
                }
            }
            res = _gain;
        }
        return res;
    }

    /**
     * Returns the current spectral channel detector gain exponent.
     * For a value n ranging from 0 to 12, the applied gain is 2^(n-1).
     * 0 corresponds to a gain of 0.5, and 12 corresponds to a gain of 2048.
     *
     * @return an integer corresponding to the current spectral channel detector gain exponent
     *
     * @throws YAPI_Exception on error
     */
    public int getGain() throws YAPI_Exception
    {
        return get_gain();
    }

    /**
     * Changes the spectral channel detector gain exponent.
     * For a value n ranging from 0 to 12, the applied gain is 2^(n-1).
     * 0 corresponds to a gain of 0.5, and 12 corresponds to a gain of 2048.
     * This method can only be used when the sensor is configured in expert mode;
     * when running in auto mode, the change will be ignored.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the spectral channel detector gain exponent
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_gain(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("gain",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the spectral channel detector gain exponent.
     * For a value n ranging from 0 to 12, the applied gain is 2^(n-1).
     * 0 corresponds to a gain of 0.5, and 12 corresponds to a gain of 2048.
     * This method can only be used when the sensor is configured in expert mode;
     * when running in auto mode, the change will be ignored.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the spectral channel detector gain exponent
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setGain(int newval)  throws YAPI_Exception
    {
        return set_gain(newval);
    }

    /**
     * Returns the current saturation state of the sensor, as an integer.
     * Bit 0 indicates saturation of the analog sensor, which can only
     * be corrected by reducing the gain parameters or the luminosity.
     * Bit 1 indicates saturation of the digital interface, which can
     * be corrected by reducing the integration time or the gain.
     *
     * @return an integer corresponding to the current saturation state of the sensor, as an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_saturation() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return SATURATION_INVALID;
                }
            }
            res = _saturation;
        }
        return res;
    }

    /**
     * Returns the current saturation state of the sensor, as an integer.
     * Bit 0 indicates saturation of the analog sensor, which can only
     * be corrected by reducing the gain parameters or the luminosity.
     * Bit 1 indicates saturation of the digital interface, which can
     * be corrected by reducing the integration time or the gain.
     *
     * @return an integer corresponding to the current saturation state of the sensor, as an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getSaturation() throws YAPI_Exception
    {
        return get_saturation();
    }

    /**
     * Returns the estimated color in RGB color model (0xRRGGBB).
     * The RGB color model describes each color using a combination of 3 components:<ul>
     * <li>Red (R): the intensity of red, in thee range 0...255</li>
     * <li>Green (G): the intensity of green, in thee range 0...255</li>
     * <li>Blue (B): the intensity of blue, in thee range 0...255</li></ul>
     *
     * @return an integer corresponding to the estimated color in RGB color model (0xRRGGBB)
     *
     * @throws YAPI_Exception on error
     */
    public int get_estimatedRGB() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ESTIMATEDRGB_INVALID;
                }
            }
            res = _estimatedRGB;
        }
        return res;
    }

    /**
     * Returns the estimated color in RGB color model (0xRRGGBB).
     * The RGB color model describes each color using a combination of 3 components:<ul>
     * <li>Red (R): the intensity of red, in thee range 0...255</li>
     * <li>Green (G): the intensity of green, in thee range 0...255</li>
     * <li>Blue (B): the intensity of blue, in thee range 0...255</li></ul>
     *
     * @return an integer corresponding to the estimated color in RGB color model (0xRRGGBB)
     *
     * @throws YAPI_Exception on error
     */
    public int getEstimatedRGB() throws YAPI_Exception
    {
        return get_estimatedRGB();
    }

    /**
     * Returns the estimated color in HSL color model (0xHHSSLL).
     * The HSL color model describes each color using a combination of 3 components:<ul>
     * <li>Hue (H): the angle on the color wheel (0-360 degrees), mapped to 0...255</li>
     * <li>Saturation (S): the intensity of the color (0-100%), mapped to 0...255</li>
     * <li>Lightness (L): the brightness of the color (0-100%), mapped to 0...255</li></ul>
     *
     * @return an integer corresponding to the estimated color in HSL color model (0xHHSSLL)
     *
     * @throws YAPI_Exception on error
     */
    public int get_estimatedHSL() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ESTIMATEDHSL_INVALID;
                }
            }
            res = _estimatedHSL;
        }
        return res;
    }

    /**
     * Returns the estimated color in HSL color model (0xHHSSLL).
     * The HSL color model describes each color using a combination of 3 components:<ul>
     * <li>Hue (H): the angle on the color wheel (0-360 degrees), mapped to 0...255</li>
     * <li>Saturation (S): the intensity of the color (0-100%), mapped to 0...255</li>
     * <li>Lightness (L): the brightness of the color (0-100%), mapped to 0...255</li></ul>
     *
     * @return an integer corresponding to the estimated color in HSL color model (0xHHSSLL)
     *
     * @throws YAPI_Exception on error
     */
    public int getEstimatedHSL() throws YAPI_Exception
    {
        return get_estimatedHSL();
    }

    /**
     * Returns the estimated color according to the CIE XYZ color model.
     * This color model is based on human vision and light perception, with three components
     * represented by real numbers between 0 and 1:<ul>
     * <li>X: corresponds to a component mixing sensitivity to red and green</li>
     * <li>Y: represents luminance (perceived brightness)</li>
     * <li>Z: corresponds to sensitivity to blue</li></ul>
     *
     * @return a string corresponding to the estimated color according to the CIE XYZ color model
     *
     * @throws YAPI_Exception on error
     */
    public String get_estimatedXYZ() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ESTIMATEDXYZ_INVALID;
                }
            }
            res = _estimatedXYZ;
        }
        return res;
    }

    /**
     * Returns the estimated color according to the CIE XYZ color model.
     * This color model is based on human vision and light perception, with three components
     * represented by real numbers between 0 and 1:<ul>
     * <li>X: corresponds to a component mixing sensitivity to red and green</li>
     * <li>Y: represents luminance (perceived brightness)</li>
     * <li>Z: corresponds to sensitivity to blue</li></ul>
     *
     * @return a string corresponding to the estimated color according to the CIE XYZ color model
     *
     * @throws YAPI_Exception on error
     */
    public String getEstimatedXYZ() throws YAPI_Exception
    {
        return get_estimatedXYZ();
    }

    /**
     * Returns the estimated color according to the OkLab color model.
     * OkLab is a perceptual color model that aims to align human color perception with numerical
     * values, so that visually near colors are also numerically near. Colors are represented using three components:
     * <li>L: lightness, a real number between 0 and 1<li>
     * <li>a: color variations between green and red, between -0.5 and 0.5<li>
     * <li>b: color variations between blue and yellow, between -0.5 and 0.5.</li></ul>
     *
     * @return a string corresponding to the estimated color according to the OkLab color model
     *
     * @throws YAPI_Exception on error
     */
    public String get_estimatedOkLab() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ESTIMATEDOKLAB_INVALID;
                }
            }
            res = _estimatedOkLab;
        }
        return res;
    }

    /**
     * Returns the estimated color according to the OkLab color model.
     * OkLab is a perceptual color model that aims to align human color perception with numerical
     * values, so that visually near colors are also numerically near. Colors are represented using three components:
     * <li>L: lightness, a real number between 0 and 1<li>
     * <li>a: color variations between green and red, between -0.5 and 0.5<li>
     * <li>b: color variations between blue and yellow, between -0.5 and 0.5.</li></ul>
     *
     * @return a string corresponding to the estimated color according to the OkLab color model
     *
     * @throws YAPI_Exception on error
     */
    public String getEstimatedOkLab() throws YAPI_Exception
    {
        return get_estimatedOkLab();
    }

    /**
     * Returns the RAL Classic color closest to the estimated color, with a similarity ratio.
     *
     * @return a string corresponding to the RAL Classic color closest to the estimated color, with a similarity ratio
     *
     * @throws YAPI_Exception on error
     */
    public String get_nearRAL1() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return NEARRAL1_INVALID;
                }
            }
            res = _nearRAL1;
        }
        return res;
    }

    /**
     * Returns the RAL Classic color closest to the estimated color, with a similarity ratio.
     *
     * @return a string corresponding to the RAL Classic color closest to the estimated color, with a similarity ratio
     *
     * @throws YAPI_Exception on error
     */
    public String getNearRAL1() throws YAPI_Exception
    {
        return get_nearRAL1();
    }

    /**
     * Returns the second closest RAL Classic color to the estimated color, with a similarity ratio.
     *
     *  @return a string corresponding to the second closest RAL Classic color to the estimated color, with
     * a similarity ratio
     *
     * @throws YAPI_Exception on error
     */
    public String get_nearRAL2() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return NEARRAL2_INVALID;
                }
            }
            res = _nearRAL2;
        }
        return res;
    }

    /**
     * Returns the second closest RAL Classic color to the estimated color, with a similarity ratio.
     *
     *  @return a string corresponding to the second closest RAL Classic color to the estimated color, with
     * a similarity ratio
     *
     * @throws YAPI_Exception on error
     */
    public String getNearRAL2() throws YAPI_Exception
    {
        return get_nearRAL2();
    }

    /**
     * Returns the third closest RAL Classic color to the estimated color, with a similarity ratio.
     *
     *  @return a string corresponding to the third closest RAL Classic color to the estimated color, with
     * a similarity ratio
     *
     * @throws YAPI_Exception on error
     */
    public String get_nearRAL3() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return NEARRAL3_INVALID;
                }
            }
            res = _nearRAL3;
        }
        return res;
    }

    /**
     * Returns the third closest RAL Classic color to the estimated color, with a similarity ratio.
     *
     *  @return a string corresponding to the third closest RAL Classic color to the estimated color, with
     * a similarity ratio
     *
     * @throws YAPI_Exception on error
     */
    public String getNearRAL3() throws YAPI_Exception
    {
        return get_nearRAL3();
    }

    /**
     * Returns the name of the HTML color closest to the estimated color.
     *
     * @return a string corresponding to the name of the HTML color closest to the estimated color
     *
     * @throws YAPI_Exception on error
     */
    public String get_nearHTMLColor() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return NEARHTMLCOLOR_INVALID;
                }
            }
            res = _nearHTMLColor;
        }
        return res;
    }

    /**
     * Returns the name of the HTML color closest to the estimated color.
     *
     * @return a string corresponding to the name of the HTML color closest to the estimated color
     *
     * @throws YAPI_Exception on error
     */
    public String getNearHTMLColor() throws YAPI_Exception
    {
        return get_nearHTMLColor();
    }

    /**
     * Returns the index of the basic color typically used to refer to the estimated color (enumerated value).
     * The list of basic colors recognized is:<ul>
     * <li>0 - Brown</li>
     * <li>1 - Red</li>
     * <li>2 - Orange</li>
     * <li>3 - Yellow</li>
     * <li>4 - White</li>
     * <li>5 - Gray</li>
     * <li>6 - Black</li>
     * <li>7 - Green</li>
     * <li>8 - Blue</li>
     * <li>9 - Purple</li>
     * <li>10 - Pink</li></ul>
     *
     *  @return a value among YColorSensor.NEARSIMPLECOLORINDEX_BROWN,
     *  YColorSensor.NEARSIMPLECOLORINDEX_RED, YColorSensor.NEARSIMPLECOLORINDEX_ORANGE,
     *  YColorSensor.NEARSIMPLECOLORINDEX_YELLOW, YColorSensor.NEARSIMPLECOLORINDEX_WHITE,
     *  YColorSensor.NEARSIMPLECOLORINDEX_GRAY, YColorSensor.NEARSIMPLECOLORINDEX_BLACK,
     *  YColorSensor.NEARSIMPLECOLORINDEX_GREEN, YColorSensor.NEARSIMPLECOLORINDEX_BLUE,
     *  YColorSensor.NEARSIMPLECOLORINDEX_PURPLE and YColorSensor.NEARSIMPLECOLORINDEX_PINK corresponding
     * to the index of the basic color typically used to refer to the estimated color (enumerated value)
     *
     * @throws YAPI_Exception on error
     */
    public int get_nearSimpleColorIndex() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return NEARSIMPLECOLORINDEX_INVALID;
                }
            }
            res = _nearSimpleColorIndex;
        }
        return res;
    }

    /**
     * Returns the index of the basic color typically used to refer to the estimated color (enumerated value).
     * The list of basic colors recognized is:<ul>
     * <li>0 - Brown</li>
     * <li>1 - Red</li>
     * <li>2 - Orange</li>
     * <li>3 - Yellow</li>
     * <li>4 - White</li>
     * <li>5 - Gray</li>
     * <li>6 - Black</li>
     * <li>7 - Green</li>
     * <li>8 - Blue</li>
     * <li>9 - Purple</li>
     * <li>10 - Pink</li></ul>
     *
     *  @return a value among YColorSensor.NEARSIMPLECOLORINDEX_BROWN,
     *  YColorSensor.NEARSIMPLECOLORINDEX_RED, YColorSensor.NEARSIMPLECOLORINDEX_ORANGE,
     *  YColorSensor.NEARSIMPLECOLORINDEX_YELLOW, YColorSensor.NEARSIMPLECOLORINDEX_WHITE,
     *  YColorSensor.NEARSIMPLECOLORINDEX_GRAY, YColorSensor.NEARSIMPLECOLORINDEX_BLACK,
     *  YColorSensor.NEARSIMPLECOLORINDEX_GREEN, YColorSensor.NEARSIMPLECOLORINDEX_BLUE,
     *  YColorSensor.NEARSIMPLECOLORINDEX_PURPLE and YColorSensor.NEARSIMPLECOLORINDEX_PINK corresponding
     * to the index of the basic color typically used to refer to the estimated color (enumerated value)
     *
     * @throws YAPI_Exception on error
     */
    public int getNearSimpleColorIndex() throws YAPI_Exception
    {
        return get_nearSimpleColorIndex();
    }

    /**
     * Returns the name of the basic color typically used to refer to the estimated color.
     *
     * @return a string corresponding to the name of the basic color typically used to refer to the estimated color
     *
     * @throws YAPI_Exception on error
     */
    public String get_nearSimpleColor() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return NEARSIMPLECOLOR_INVALID;
                }
            }
            res = _nearSimpleColor;
        }
        return res;
    }

    /**
     * Returns the name of the basic color typically used to refer to the estimated color.
     *
     * @return a string corresponding to the name of the basic color typically used to refer to the estimated color
     *
     * @throws YAPI_Exception on error
     */
    public String getNearSimpleColor() throws YAPI_Exception
    {
        return get_nearSimpleColor();
    }

    /**
     * Retrieves a color sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the color sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YColorSensor.isOnline() to test if the color sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a color sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the color sensor, for instance
     *         MyDevice.colorSensor.
     *
     * @return a YColorSensor object allowing you to drive the color sensor.
     */
    public static YColorSensor FindColorSensor(String func)
    {
        YColorSensor obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YColorSensor) YFunction._FindFromCache("ColorSensor", func);
            if (obj == null) {
                obj = new YColorSensor(func);
                YFunction._AddToCache("ColorSensor", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a color sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the color sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YColorSensor.isOnline() to test if the color sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a color sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the color sensor, for instance
     *         MyDevice.colorSensor.
     *
     * @return a YColorSensor object allowing you to drive the color sensor.
     */
    public static YColorSensor FindColorSensorInContext(YAPIContext yctx,String func)
    {
        YColorSensor obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YColorSensor) YFunction._FindFromCacheInContext(yctx, "ColorSensor", func);
            if (obj == null) {
                obj = new YColorSensor(yctx, func);
                YFunction._AddToCache("ColorSensor", func, obj);
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
        _valueCallbackColorSensor = callback;
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
        if (_valueCallbackColorSensor != null) {
            _valueCallbackColorSensor.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Turns on the built-in illumination LEDs using the same current as used during last calibration.
     * @throws YAPI_Exception on error
     */
    public int turnLedOn() throws YAPI_Exception
    {
        return set_ledCurrent(get_ledCalibration());
    }

    /**
     * Turns off the built-in illumination LEDs.
     * @throws YAPI_Exception on error
     */
    public int turnLedOff() throws YAPI_Exception
    {
        return set_ledCurrent(0);
    }

    /**
     * Continues the enumeration of color sensors started using yFirstColorSensor().
     * Caution: You can't make any assumption about the returned color sensors order.
     * If you want to find a specific a color sensor, use ColorSensor.findColorSensor()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YColorSensor object, corresponding to
     *         a color sensor currently online, or a null pointer
     *         if there are no more color sensors to enumerate.
     */
    public YColorSensor nextColorSensor()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindColorSensorInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of color sensors currently accessible.
     * Use the method YColorSensor.nextColorSensor() to iterate on
     * next color sensors.
     *
     * @return a pointer to a YColorSensor object, corresponding to
     *         the first color sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YColorSensor FirstColorSensor()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("ColorSensor");
        if (next_hwid == null)  return null;
        return FindColorSensorInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of color sensors currently accessible.
     * Use the method YColorSensor.nextColorSensor() to iterate on
     * next color sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YColorSensor object, corresponding to
     *         the first color sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YColorSensor FirstColorSensorInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("ColorSensor");
        if (next_hwid == null)  return null;
        return FindColorSensorInContext(yctx, next_hwid);
    }

    //--- (end of YColorSensor implementation)
}

