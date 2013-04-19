/*********************************************************************
 *
 * $Id: YAnButton.java 10471 2013-03-19 10:39:56Z seb $
 *
 * Implements yFindAnButton(), the high-level API for AnButton functions
 *
 * - - - - - - - - - License information: - - - - - - - - - 
 *
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 * 1) If you have obtained this file from www.yoctopuce.com,
 *    Yoctopuce Sarl licenses to you (hereafter Licensee) the
 *    right to use, modify, copy, and integrate this source file
 *    into your own solution for the sole purpose of interfacing
 *    a Yoctopuce product with Licensee's solution.
 *
 *    The use of this file and all relationship between Yoctopuce 
 *    and Licensee are governed by Yoctopuce General Terms and 
 *    Conditions.
 *
 *    THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *    WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *    WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS 
 *    FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *    EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *    INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, 
 *    COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR 
 *    SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT 
 *    LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *    CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *    BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *    WARRANTY, OR OTHERWISE.
 *
 * 2) If your intent is not to interface with Yoctopuce products,
 *    you are not entitled to use, read or create any derived
 *    material from this source file.
 *
 *********************************************************************/

package com.yoctopuce.YoctoAPI;

  //--- (globals)
  //--- (end of globals)
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
public class YAnButton extends YFunction
{
    //--- (definitions)
    private YAnButton.UpdateCallback _valueCallbackAnButton;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid calibratedValue value
     */
    public static final int CALIBRATEDVALUE_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid rawValue value
     */
    public static final int RAWVALUE_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid analogCalibration value
     */
  public static final int ANALOGCALIBRATION_OFF = 0;
  public static final int ANALOGCALIBRATION_ON = 1;
  public static final int ANALOGCALIBRATION_INVALID = -1;

    /**
     * invalid calibrationMax value
     */
    public static final int CALIBRATIONMAX_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid calibrationMin value
     */
    public static final int CALIBRATIONMIN_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid sensitivity value
     */
    public static final int SENSITIVITY_INVALID = YAPI.INVALID_UNSIGNED;
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
    //--- (end of definitions)

    /**
     * UdateCallback for AnButton
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YAnButton function, String functionValue);
    }



    //--- (YAnButton implementation)

    /**
     * Returns the logical name of the analog input.
     * 
     * @return a string corresponding to the logical name of the analog input
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the analog input.
     * 
     * @return a string corresponding to the logical name of the analog input
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the analog input. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the analog input
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
     * Changes the logical name of the analog input. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the analog input
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the analog input (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the analog input (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the analog input (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the analog input (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the current calibrated input value (between 0 and 1000, included).
     * 
     * @return an integer corresponding to the current calibrated input value (between 0 and 1000, included)
     * 
     * @throws YAPI_Exception
     */
    public int get_calibratedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("calibratedValue");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current calibrated input value (between 0 and 1000, included).
     * 
     * @return an integer corresponding to the current calibrated input value (between 0 and 1000, included)
     * 
     * @throws YAPI_Exception
     */
    public int getCalibratedValue() throws YAPI_Exception

    { return get_calibratedValue(); }

    /**
     * Returns the current measured input value as-is (between 0 and 4095, included).
     * 
     * @return an integer corresponding to the current measured input value as-is (between 0 and 4095, included)
     * 
     * @throws YAPI_Exception
     */
    public int get_rawValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("rawValue");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current measured input value as-is (between 0 and 4095, included).
     * 
     * @return an integer corresponding to the current measured input value as-is (between 0 and 4095, included)
     * 
     * @throws YAPI_Exception
     */
    public int getRawValue() throws YAPI_Exception

    { return get_rawValue(); }

    /**
     * Tells if a calibration process is currently ongoing.
     * 
     * @return either YAnButton.ANALOGCALIBRATION_OFF or YAnButton.ANALOGCALIBRATION_ON
     * 
     * @throws YAPI_Exception
     */
    public int get_analogCalibration()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("analogCalibration");
        return Integer.parseInt(json_val);
    }

    /**
     * Tells if a calibration process is currently ongoing.
     * 
     * @return either Y_ANALOGCALIBRATION_OFF or Y_ANALOGCALIBRATION_ON
     * 
     * @throws YAPI_Exception
     */
    public int getAnalogCalibration() throws YAPI_Exception

    { return get_analogCalibration(); }

    /**
     * Starts or stops the calibration process. Remember to call the saveToFlash()
     * method of the module at the end of the calibration if the modification must be kept.
     * 
     * @param newval : either YAnButton.ANALOGCALIBRATION_OFF or YAnButton.ANALOGCALIBRATION_ON
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_analogCalibration( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("analogCalibration",rest_val);
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
     * @throws YAPI_Exception
     */
    public int setAnalogCalibration( int newval)  throws YAPI_Exception

    { return set_analogCalibration(newval); }

    /**
     * Returns the maximal value measured during the calibration (between 0 and 4095, included).
     * 
     * @return an integer corresponding to the maximal value measured during the calibration (between 0
     * and 4095, included)
     * 
     * @throws YAPI_Exception
     */
    public int get_calibrationMax()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("calibrationMax");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the maximal value measured during the calibration (between 0 and 4095, included).
     * 
     * @return an integer corresponding to the maximal value measured during the calibration (between 0
     * and 4095, included)
     * 
     * @throws YAPI_Exception
     */
    public int getCalibrationMax() throws YAPI_Exception

    { return get_calibrationMax(); }

    /**
     * Changes the maximal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     * 
     * @param newval : an integer corresponding to the maximal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_calibrationMax( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("calibrationMax",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     * 
     * @param newval : an integer corresponding to the maximal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setCalibrationMax( int newval)  throws YAPI_Exception

    { return set_calibrationMax(newval); }

    /**
     * Returns the minimal value measured during the calibration (between 0 and 4095, included).
     * 
     * @return an integer corresponding to the minimal value measured during the calibration (between 0
     * and 4095, included)
     * 
     * @throws YAPI_Exception
     */
    public int get_calibrationMin()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("calibrationMin");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the minimal value measured during the calibration (between 0 and 4095, included).
     * 
     * @return an integer corresponding to the minimal value measured during the calibration (between 0
     * and 4095, included)
     * 
     * @throws YAPI_Exception
     */
    public int getCalibrationMin() throws YAPI_Exception

    { return get_calibrationMin(); }

    /**
     * Changes the minimal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     * 
     * @param newval : an integer corresponding to the minimal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_calibrationMin( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("calibrationMin",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the minimal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     * 
     * @param newval : an integer corresponding to the minimal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setCalibrationMin( int newval)  throws YAPI_Exception

    { return set_calibrationMin(newval); }

    /**
     * Returns the sensibility for the input (between 1 and 255, included) for triggering user callbacks.
     * 
     * @return an integer corresponding to the sensibility for the input (between 1 and 255, included) for
     * triggering user callbacks
     * 
     * @throws YAPI_Exception
     */
    public int get_sensitivity()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("sensitivity");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the sensibility for the input (between 1 and 255, included) for triggering user callbacks.
     * 
     * @return an integer corresponding to the sensibility for the input (between 1 and 255, included) for
     * triggering user callbacks
     * 
     * @throws YAPI_Exception
     */
    public int getSensitivity() throws YAPI_Exception

    { return get_sensitivity(); }

    /**
     * Changes the sensibility for the input (between 1 and 255, included) for triggering user callbacks.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     * 
     * @param newval : an integer corresponding to the sensibility for the input (between 1 and 255,
     * included) for triggering user callbacks
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_sensitivity( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("sensitivity",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the sensibility for the input (between 1 and 255, included) for triggering user callbacks.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     * 
     * @param newval : an integer corresponding to the sensibility for the input (between 1 and 255,
     * included) for triggering user callbacks
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setSensitivity( int newval)  throws YAPI_Exception

    { return set_sensitivity(newval); }

    /**
     * Returns true if the input (considered as binary) is active (closed contact), and false otherwise.
     * 
     * @return either YAnButton.ISPRESSED_FALSE or YAnButton.ISPRESSED_TRUE, according to true if the
     * input (considered as binary) is active (closed contact), and false otherwise
     * 
     * @throws YAPI_Exception
     */
    public int get_isPressed()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("isPressed");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns true if the input (considered as binary) is active (closed contact), and false otherwise.
     * 
     * @return either Y_ISPRESSED_FALSE or Y_ISPRESSED_TRUE, according to true if the input (considered as
     * binary) is active (closed contact), and false otherwise
     * 
     * @throws YAPI_Exception
     */
    public int getIsPressed() throws YAPI_Exception

    { return get_isPressed(); }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was pressed (the input contact transitionned from open to closed).
     * 
     * @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was pressed (the input contact transitionned from open to closed)
     * 
     * @throws YAPI_Exception
     */
    public long get_lastTimePressed()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("lastTimePressed");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was pressed (the input contact transitionned from open to closed).
     * 
     * @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was pressed (the input contact transitionned from open to closed)
     * 
     * @throws YAPI_Exception
     */
    public long getLastTimePressed() throws YAPI_Exception

    { return get_lastTimePressed(); }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was released (the input contact transitionned from closed to open).
     * 
     * @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was released (the input contact transitionned from closed to open)
     * 
     * @throws YAPI_Exception
     */
    public long get_lastTimeReleased()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("lastTimeReleased");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was released (the input contact transitionned from closed to open).
     * 
     * @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was released (the input contact transitionned from closed to open)
     * 
     * @throws YAPI_Exception
     */
    public long getLastTimeReleased() throws YAPI_Exception

    { return get_lastTimeReleased(); }

    /**
     * Returns the pulse counter value
     * 
     * @return an integer corresponding to the pulse counter value
     * 
     * @throws YAPI_Exception
     */
    public long get_pulseCounter()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("pulseCounter");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the pulse counter value
     * 
     * @return an integer corresponding to the pulse counter value
     * 
     * @throws YAPI_Exception
     */
    public long getPulseCounter() throws YAPI_Exception

    { return get_pulseCounter(); }

    public int set_pulseCounter( long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("pulseCounter",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPulseCounter( long newval)  throws YAPI_Exception

    { return set_pulseCounter(newval); }

    /**
     * Returns the pulse counter value as well as his timer
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int resetCounter()  throws YAPI_Exception
    {
        String rest_val;
        rest_val = "0";
        _setAttr("pulseCounter",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the timer of the pulses counter (ms)
     * 
     * @return an integer corresponding to the timer of the pulses counter (ms)
     * 
     * @throws YAPI_Exception
     */
    public long get_pulseTimer()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("pulseTimer");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the timer of the pulses counter (ms)
     * 
     * @return an integer corresponding to the timer of the pulses counter (ms)
     * 
     * @throws YAPI_Exception
     */
    public long getPulseTimer() throws YAPI_Exception

    { return get_pulseTimer(); }

    /**
     * Continues the enumeration of analog inputs started using yFirstAnButton().
     * 
     * @return a pointer to a YAnButton object, corresponding to
     *         an analog input currently online, or a null pointer
     *         if there are no more analog inputs to enumerate.
     */
    public  YAnButton nextAnButton()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindAnButton(next_hwid);
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
    {   YFunction yfunc = YAPI.getFunction("AnButton", func);
        if (yfunc != null) {
            return (YAnButton) yfunc;
        }
        return new YAnButton(func);
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
        String next_hwid = YAPI.getFirstHardwareId("AnButton");
        if (next_hwid == null)  return null;
        return FindAnButton(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YAnButton(String func)
    {
        super("AnButton", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackAnButton != null) {
            _valueCallbackAnButton.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackAnButton!=null);
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
    public void registerValueCallback(YAnButton.UpdateCallback callback)
    {
         _valueCallbackAnButton =  callback;
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

    //--- (end of YAnButton implementation)
};

