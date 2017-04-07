/*********************************************************************
 *
 * $Id: YAnButton.java 27053 2017-04-04 16:01:11Z seb $
 *
 * Implements FindAnButton(), the high-level API for AnButton functions
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

//--- (YAnButton return codes)
//--- (end of YAnButton return codes)
//--- (YAnButton class start)
/**
 * YAnButton Class: AnButton function interface
 *
 * Yoctopuce application programming interface allows you to measure the state
 * of a simple button as well as to read an analog potentiometer (variable resistance).
 * This can be use for instance with a continuous rotating knob, a throttle grip
 * or a joystick. The module is capable to calibrate itself on min and max values,
 * in order to compute a calibrated value that varies proportionally with the
 * potentiometer position, regardless of its total resistance.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YAnButton extends YFunction
{
//--- (end of YAnButton class start)
//--- (YAnButton definitions)
    /**
     * invalid calibratedValue value
     */
    public static final int CALIBRATEDVALUE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid rawValue value
     */
    public static final int RAWVALUE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid analogCalibration value
     */
    public static final int ANALOGCALIBRATION_OFF = 0;
    public static final int ANALOGCALIBRATION_ON = 1;
    public static final int ANALOGCALIBRATION_INVALID = -1;
    /**
     * invalid calibrationMax value
     */
    public static final int CALIBRATIONMAX_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid calibrationMin value
     */
    public static final int CALIBRATIONMIN_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid sensitivity value
     */
    public static final int SENSITIVITY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid isPressed value
     */
    public static final int ISPRESSED_FALSE = 0;
    public static final int ISPRESSED_TRUE = 1;
    public static final int ISPRESSED_INVALID = -1;
    /**
     * invalid lastTimePressed value
     */
    public static final long LASTTIMEPRESSED_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid lastTimeReleased value
     */
    public static final long LASTTIMERELEASED_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid pulseCounter value
     */
    public static final long PULSECOUNTER_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid pulseTimer value
     */
    public static final long PULSETIMER_INVALID = YAPI.INVALID_LONG;
    protected int _calibratedValue = CALIBRATEDVALUE_INVALID;
    protected int _rawValue = RAWVALUE_INVALID;
    protected int _analogCalibration = ANALOGCALIBRATION_INVALID;
    protected int _calibrationMax = CALIBRATIONMAX_INVALID;
    protected int _calibrationMin = CALIBRATIONMIN_INVALID;
    protected int _sensitivity = SENSITIVITY_INVALID;
    protected int _isPressed = ISPRESSED_INVALID;
    protected long _lastTimePressed = LASTTIMEPRESSED_INVALID;
    protected long _lastTimeReleased = LASTTIMERELEASED_INVALID;
    protected long _pulseCounter = PULSECOUNTER_INVALID;
    protected long _pulseTimer = PULSETIMER_INVALID;
    protected UpdateCallback _valueCallbackAnButton = null;

    /**
     * Deprecated UpdateCallback for AnButton
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YAnButton function, String functionValue);
    }

    /**
     * TimedReportCallback for AnButton
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YAnButton  function, YMeasure measure);
    }
    //--- (end of YAnButton definitions)


    /**
     *
     * @param func : functionid
     */
    protected YAnButton(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "AnButton";
        //--- (YAnButton attributes initialization)
        //--- (end of YAnButton attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YAnButton(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YAnButton implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("calibratedValue")) {
            _calibratedValue = json_val.getInt("calibratedValue");
        }
        if (json_val.has("rawValue")) {
            _rawValue = json_val.getInt("rawValue");
        }
        if (json_val.has("analogCalibration")) {
            _analogCalibration = json_val.getInt("analogCalibration") > 0 ? 1 : 0;
        }
        if (json_val.has("calibrationMax")) {
            _calibrationMax = json_val.getInt("calibrationMax");
        }
        if (json_val.has("calibrationMin")) {
            _calibrationMin = json_val.getInt("calibrationMin");
        }
        if (json_val.has("sensitivity")) {
            _sensitivity = json_val.getInt("sensitivity");
        }
        if (json_val.has("isPressed")) {
            _isPressed = json_val.getInt("isPressed") > 0 ? 1 : 0;
        }
        if (json_val.has("lastTimePressed")) {
            _lastTimePressed = json_val.getLong("lastTimePressed");
        }
        if (json_val.has("lastTimeReleased")) {
            _lastTimeReleased = json_val.getLong("lastTimeReleased");
        }
        if (json_val.has("pulseCounter")) {
            _pulseCounter = json_val.getLong("pulseCounter");
        }
        if (json_val.has("pulseTimer")) {
            _pulseTimer = json_val.getLong("pulseTimer");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current calibrated input value (between 0 and 1000, included).
     *
     * @return an integer corresponding to the current calibrated input value (between 0 and 1000, included)
     *
     * @throws YAPI_Exception on error
     */
    public int get_calibratedValue() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CALIBRATEDVALUE_INVALID;
                }
            }
            res = _calibratedValue;
        }
        return res;
    }

    /**
     * Returns the current calibrated input value (between 0 and 1000, included).
     *
     * @return an integer corresponding to the current calibrated input value (between 0 and 1000, included)
     *
     * @throws YAPI_Exception on error
     */
    public int getCalibratedValue() throws YAPI_Exception
    {
        return get_calibratedValue();
    }

    /**
     * Returns the current measured input value as-is (between 0 and 4095, included).
     *
     * @return an integer corresponding to the current measured input value as-is (between 0 and 4095, included)
     *
     * @throws YAPI_Exception on error
     */
    public int get_rawValue() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return RAWVALUE_INVALID;
                }
            }
            res = _rawValue;
        }
        return res;
    }

    /**
     * Returns the current measured input value as-is (between 0 and 4095, included).
     *
     * @return an integer corresponding to the current measured input value as-is (between 0 and 4095, included)
     *
     * @throws YAPI_Exception on error
     */
    public int getRawValue() throws YAPI_Exception
    {
        return get_rawValue();
    }

    /**
     * Tells if a calibration process is currently ongoing.
     *
     * @return either YAnButton.ANALOGCALIBRATION_OFF or YAnButton.ANALOGCALIBRATION_ON
     *
     * @throws YAPI_Exception on error
     */
    public int get_analogCalibration() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ANALOGCALIBRATION_INVALID;
                }
            }
            res = _analogCalibration;
        }
        return res;
    }

    /**
     * Tells if a calibration process is currently ongoing.
     *
     * @return either Y_ANALOGCALIBRATION_OFF or Y_ANALOGCALIBRATION_ON
     *
     * @throws YAPI_Exception on error
     */
    public int getAnalogCalibration() throws YAPI_Exception
    {
        return get_analogCalibration();
    }

    /**
     * Starts or stops the calibration process. Remember to call the saveToFlash()
     * method of the module at the end of the calibration if the modification must be kept.
     *
     * @param newval : either YAnButton.ANALOGCALIBRATION_OFF or YAnButton.ANALOGCALIBRATION_ON
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_analogCalibration(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("analogCalibration",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Starts or stops the calibration process. Remember to call the saveToFlash()
     * method of the module at the end of the calibration if the modification must be kept.
     *
     * @param newval : either Y_ANALOGCALIBRATION_OFF or Y_ANALOGCALIBRATION_ON
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAnalogCalibration(int newval)  throws YAPI_Exception
    {
        return set_analogCalibration(newval);
    }

    /**
     * Returns the maximal value measured during the calibration (between 0 and 4095, included).
     *
     *  @return an integer corresponding to the maximal value measured during the calibration (between 0
     * and 4095, included)
     *
     * @throws YAPI_Exception on error
     */
    public int get_calibrationMax() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CALIBRATIONMAX_INVALID;
                }
            }
            res = _calibrationMax;
        }
        return res;
    }

    /**
     * Returns the maximal value measured during the calibration (between 0 and 4095, included).
     *
     *  @return an integer corresponding to the maximal value measured during the calibration (between 0
     * and 4095, included)
     *
     * @throws YAPI_Exception on error
     */
    public int getCalibrationMax() throws YAPI_Exception
    {
        return get_calibrationMax();
    }

    /**
     * Changes the maximal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : an integer corresponding to the maximal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_calibrationMax(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("calibrationMax",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : an integer corresponding to the maximal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCalibrationMax(int newval)  throws YAPI_Exception
    {
        return set_calibrationMax(newval);
    }

    /**
     * Returns the minimal value measured during the calibration (between 0 and 4095, included).
     *
     *  @return an integer corresponding to the minimal value measured during the calibration (between 0
     * and 4095, included)
     *
     * @throws YAPI_Exception on error
     */
    public int get_calibrationMin() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CALIBRATIONMIN_INVALID;
                }
            }
            res = _calibrationMin;
        }
        return res;
    }

    /**
     * Returns the minimal value measured during the calibration (between 0 and 4095, included).
     *
     *  @return an integer corresponding to the minimal value measured during the calibration (between 0
     * and 4095, included)
     *
     * @throws YAPI_Exception on error
     */
    public int getCalibrationMin() throws YAPI_Exception
    {
        return get_calibrationMin();
    }

    /**
     * Changes the minimal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : an integer corresponding to the minimal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_calibrationMin(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("calibrationMin",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the minimal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : an integer corresponding to the minimal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCalibrationMin(int newval)  throws YAPI_Exception
    {
        return set_calibrationMin(newval);
    }

    /**
     * Returns the sensibility for the input (between 1 and 1000) for triggering user callbacks.
     *
     *  @return an integer corresponding to the sensibility for the input (between 1 and 1000) for
     * triggering user callbacks
     *
     * @throws YAPI_Exception on error
     */
    public int get_sensitivity() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SENSITIVITY_INVALID;
                }
            }
            res = _sensitivity;
        }
        return res;
    }

    /**
     * Returns the sensibility for the input (between 1 and 1000) for triggering user callbacks.
     *
     *  @return an integer corresponding to the sensibility for the input (between 1 and 1000) for
     * triggering user callbacks
     *
     * @throws YAPI_Exception on error
     */
    public int getSensitivity() throws YAPI_Exception
    {
        return get_sensitivity();
    }

    /**
     * Changes the sensibility for the input (between 1 and 1000) for triggering user callbacks.
     * The sensibility is used to filter variations around a fixed value, but does not preclude the
     * transmission of events when the input value evolves constantly in the same direction.
     * Special case: when the value 1000 is used, the callback will only be thrown when the logical state
     * of the input switches from pressed to released and back.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : an integer corresponding to the sensibility for the input (between 1 and 1000) for
     * triggering user callbacks
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_sensitivity(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("sensitivity",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the sensibility for the input (between 1 and 1000) for triggering user callbacks.
     * The sensibility is used to filter variations around a fixed value, but does not preclude the
     * transmission of events when the input value evolves constantly in the same direction.
     * Special case: when the value 1000 is used, the callback will only be thrown when the logical state
     * of the input switches from pressed to released and back.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     *  @param newval : an integer corresponding to the sensibility for the input (between 1 and 1000) for
     * triggering user callbacks
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSensitivity(int newval)  throws YAPI_Exception
    {
        return set_sensitivity(newval);
    }

    /**
     * Returns true if the input (considered as binary) is active (closed contact), and false otherwise.
     *
     *  @return either YAnButton.ISPRESSED_FALSE or YAnButton.ISPRESSED_TRUE, according to true if the
     * input (considered as binary) is active (closed contact), and false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int get_isPressed() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ISPRESSED_INVALID;
                }
            }
            res = _isPressed;
        }
        return res;
    }

    /**
     * Returns true if the input (considered as binary) is active (closed contact), and false otherwise.
     *
     *  @return either Y_ISPRESSED_FALSE or Y_ISPRESSED_TRUE, according to true if the input (considered as
     * binary) is active (closed contact), and false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int getIsPressed() throws YAPI_Exception
    {
        return get_isPressed();
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was pressed (the input contact transitioned from open to closed).
     *
     *  @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was pressed (the input contact transitioned from open to closed)
     *
     * @throws YAPI_Exception on error
     */
    public long get_lastTimePressed() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return LASTTIMEPRESSED_INVALID;
                }
            }
            res = _lastTimePressed;
        }
        return res;
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was pressed (the input contact transitioned from open to closed).
     *
     *  @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was pressed (the input contact transitioned from open to closed)
     *
     * @throws YAPI_Exception on error
     */
    public long getLastTimePressed() throws YAPI_Exception
    {
        return get_lastTimePressed();
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was released (the input contact transitioned from closed to open).
     *
     *  @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was released (the input contact transitioned from closed to open)
     *
     * @throws YAPI_Exception on error
     */
    public long get_lastTimeReleased() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return LASTTIMERELEASED_INVALID;
                }
            }
            res = _lastTimeReleased;
        }
        return res;
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was released (the input contact transitioned from closed to open).
     *
     *  @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was released (the input contact transitioned from closed to open)
     *
     * @throws YAPI_Exception on error
     */
    public long getLastTimeReleased() throws YAPI_Exception
    {
        return get_lastTimeReleased();
    }

    /**
     * Returns the pulse counter value. The value is a 32 bit integer. In case
     * of overflow (>=2^32), the counter will wrap. To reset the counter, just
     * call the resetCounter() method.
     *
     * @return an integer corresponding to the pulse counter value
     *
     * @throws YAPI_Exception on error
     */
    public long get_pulseCounter() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return PULSECOUNTER_INVALID;
                }
            }
            res = _pulseCounter;
        }
        return res;
    }

    /**
     * Returns the pulse counter value. The value is a 32 bit integer. In case
     * of overflow (>=2^32), the counter will wrap. To reset the counter, just
     * call the resetCounter() method.
     *
     * @return an integer corresponding to the pulse counter value
     *
     * @throws YAPI_Exception on error
     */
    public long getPulseCounter() throws YAPI_Exception
    {
        return get_pulseCounter();
    }

    public int set_pulseCounter(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("pulseCounter",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Returns the timer of the pulses counter (ms).
     *
     * @return an integer corresponding to the timer of the pulses counter (ms)
     *
     * @throws YAPI_Exception on error
     */
    public long get_pulseTimer() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return PULSETIMER_INVALID;
                }
            }
            res = _pulseTimer;
        }
        return res;
    }

    /**
     * Returns the timer of the pulses counter (ms).
     *
     * @return an integer corresponding to the timer of the pulses counter (ms)
     *
     * @throws YAPI_Exception on error
     */
    public long getPulseTimer() throws YAPI_Exception
    {
        return get_pulseTimer();
    }

    /**
     * Retrieves an analog input for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the analog input is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YAnButton.isOnline() to test if the analog input is
     * indeed online at a given time. In case of ambiguity when looking for
     * an analog input by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the analog input
     *
     * @return a YAnButton object allowing you to drive the analog input.
     */
    public static YAnButton FindAnButton(String func)
    {
        YAnButton obj;
        synchronized (YAPI.class) {
            obj = (YAnButton) YFunction._FindFromCache("AnButton", func);
            if (obj == null) {
                obj = new YAnButton(func);
                YFunction._AddToCache("AnButton", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves an analog input for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the analog input is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YAnButton.isOnline() to test if the analog input is
     * indeed online at a given time. In case of ambiguity when looking for
     * an analog input by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the analog input
     *
     * @return a YAnButton object allowing you to drive the analog input.
     */
    public static YAnButton FindAnButtonInContext(YAPIContext yctx,String func)
    {
        YAnButton obj;
        synchronized (yctx) {
            obj = (YAnButton) YFunction._FindFromCacheInContext(yctx, "AnButton", func);
            if (obj == null) {
                obj = new YAnButton(yctx, func);
                YFunction._AddToCache("AnButton", func, obj);
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
        _valueCallbackAnButton = callback;
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
        if (_valueCallbackAnButton != null) {
            _valueCallbackAnButton.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Returns the pulse counter value as well as its timer.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int resetCounter() throws YAPI_Exception
    {
        return set_pulseCounter(0);
    }

    /**
     * Continues the enumeration of analog inputs started using yFirstAnButton().
     *
     * @return a pointer to a YAnButton object, corresponding to
     *         an analog input currently online, or a null pointer
     *         if there are no more analog inputs to enumerate.
     */
    public YAnButton nextAnButton()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindAnButtonInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of analog inputs currently accessible.
     * Use the method YAnButton.nextAnButton() to iterate on
     * next analog inputs.
     *
     * @return a pointer to a YAnButton object, corresponding to
     *         the first analog input currently online, or a null pointer
     *         if there are none.
     */
    public static YAnButton FirstAnButton()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("AnButton");
        if (next_hwid == null)  return null;
        return FindAnButtonInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of analog inputs currently accessible.
     * Use the method YAnButton.nextAnButton() to iterate on
     * next analog inputs.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YAnButton object, corresponding to
     *         the first analog input currently online, or a null pointer
     *         if there are none.
     */
    public static YAnButton FirstAnButtonInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("AnButton");
        if (next_hwid == null)  return null;
        return FindAnButtonInContext(yctx, next_hwid);
    }

    //--- (end of YAnButton implementation)
}

