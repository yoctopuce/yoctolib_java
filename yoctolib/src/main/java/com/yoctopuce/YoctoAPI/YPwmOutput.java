/*********************************************************************
 *
 * $Id: YPwmOutput.java 20287 2015-05-08 13:40:21Z seb $
 *
 * Implements FindPwmOutput(), the high-level API for PwmOutput functions
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

//--- (YPwmOutput return codes)
//--- (end of YPwmOutput return codes)
//--- (YPwmOutput class start)
/**
 * YPwmOutput Class: PwmOutput function interface
 *
 * The Yoctopuce application programming interface allows you to configure, start, and stop the PWM.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YPwmOutput extends YFunction
{
//--- (end of YPwmOutput class start)
//--- (YPwmOutput definitions)
    /**
     * invalid enabled value
     */
    public static final int ENABLED_FALSE = 0;
    public static final int ENABLED_TRUE = 1;
    public static final int ENABLED_INVALID = -1;
    /**
     * invalid frequency value
     */
    public static final double FREQUENCY_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid period value
     */
    public static final double PERIOD_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid dutyCycle value
     */
    public static final double DUTYCYCLE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid pulseDuration value
     */
    public static final double PULSEDURATION_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid pwmTransition value
     */
    public static final String PWMTRANSITION_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid enabledAtPowerOn value
     */
    public static final int ENABLEDATPOWERON_FALSE = 0;
    public static final int ENABLEDATPOWERON_TRUE = 1;
    public static final int ENABLEDATPOWERON_INVALID = -1;
    /**
     * invalid dutyCycleAtPowerOn value
     */
    public static final double DUTYCYCLEATPOWERON_INVALID = YAPI.INVALID_DOUBLE;
    protected int _enabled = ENABLED_INVALID;
    protected double _frequency = FREQUENCY_INVALID;
    protected double _period = PERIOD_INVALID;
    protected double _dutyCycle = DUTYCYCLE_INVALID;
    protected double _pulseDuration = PULSEDURATION_INVALID;
    protected String _pwmTransition = PWMTRANSITION_INVALID;
    protected int _enabledAtPowerOn = ENABLEDATPOWERON_INVALID;
    protected double _dutyCycleAtPowerOn = DUTYCYCLEATPOWERON_INVALID;
    protected UpdateCallback _valueCallbackPwmOutput = null;

    /**
     * Deprecated UpdateCallback for PwmOutput
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YPwmOutput function, String functionValue);
    }

    /**
     * TimedReportCallback for PwmOutput
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YPwmOutput  function, YMeasure measure);
    }
    //--- (end of YPwmOutput definitions)


    /**
     *
     * @param func : functionid
     */
    protected YPwmOutput(String func)
    {
        super(func);
        _className = "PwmOutput";
        //--- (YPwmOutput attributes initialization)
        //--- (end of YPwmOutput attributes initialization)
    }

    //--- (YPwmOutput implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("enabled")) {
            _enabled = json_val.getInt("enabled") > 0 ? 1 : 0;
        }
        if (json_val.has("frequency")) {
            _frequency = Math.round(json_val.getDouble("frequency") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("period")) {
            _period = Math.round(json_val.getDouble("period") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("dutyCycle")) {
            _dutyCycle = Math.round(json_val.getDouble("dutyCycle") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("pulseDuration")) {
            _pulseDuration = Math.round(json_val.getDouble("pulseDuration") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("pwmTransition")) {
            _pwmTransition = json_val.getString("pwmTransition");
        }
        if (json_val.has("enabledAtPowerOn")) {
            _enabledAtPowerOn = json_val.getInt("enabledAtPowerOn") > 0 ? 1 : 0;
        }
        if (json_val.has("dutyCycleAtPowerOn")) {
            _dutyCycleAtPowerOn = Math.round(json_val.getDouble("dutyCycleAtPowerOn") * 1000.0 / 65536.0) / 1000.0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the state of the PWMs.
     *
     * @return either YPwmOutput.ENABLED_FALSE or YPwmOutput.ENABLED_TRUE, according to the state of the PWMs
     *
     * @throws YAPI_Exception on error
     */
    public int get_enabled() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ENABLED_INVALID;
            }
        }
        return _enabled;
    }

    /**
     * Returns the state of the PWMs.
     *
     * @return either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the state of the PWMs
     *
     * @throws YAPI_Exception on error
     */
    public int getEnabled() throws YAPI_Exception
    {
        return get_enabled();
    }

    /**
     * Stops or starts the PWM.
     *
     * @param newval : either YPwmOutput.ENABLED_FALSE or YPwmOutput.ENABLED_TRUE
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_enabled(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("enabled",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Stops or starts the PWM.
     *
     * @param newval : either Y_ENABLED_FALSE or Y_ENABLED_TRUE
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setEnabled(int newval)  throws YAPI_Exception
    {
        return set_enabled(newval);
    }

    /**
     * Changes the PWM frequency. The duty cycle is kept unchanged thanks to an
     * automatic pulse width change.
     *
     * @param newval : a floating point number corresponding to the PWM frequency
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_frequency(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
        _setAttr("frequency",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the PWM frequency. The duty cycle is kept unchanged thanks to an
     * automatic pulse width change.
     *
     * @param newval : a floating point number corresponding to the PWM frequency
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setFrequency(double newval)  throws YAPI_Exception
    {
        return set_frequency(newval);
    }

    /**
     * Returns the PWM frequency in Hz.
     *
     * @return a floating point number corresponding to the PWM frequency in Hz
     *
     * @throws YAPI_Exception on error
     */
    public double get_frequency() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return FREQUENCY_INVALID;
            }
        }
        return _frequency;
    }

    /**
     * Returns the PWM frequency in Hz.
     *
     * @return a floating point number corresponding to the PWM frequency in Hz
     *
     * @throws YAPI_Exception on error
     */
    public double getFrequency() throws YAPI_Exception
    {
        return get_frequency();
    }

    /**
     * Changes the PWM period in milliseconds.
     *
     * @param newval : a floating point number corresponding to the PWM period in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_period(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
        _setAttr("period",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the PWM period in milliseconds.
     *
     * @param newval : a floating point number corresponding to the PWM period in milliseconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPeriod(double newval)  throws YAPI_Exception
    {
        return set_period(newval);
    }

    /**
     * Returns the PWM period in milliseconds.
     *
     * @return a floating point number corresponding to the PWM period in milliseconds
     *
     * @throws YAPI_Exception on error
     */
    public double get_period() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PERIOD_INVALID;
            }
        }
        return _period;
    }

    /**
     * Returns the PWM period in milliseconds.
     *
     * @return a floating point number corresponding to the PWM period in milliseconds
     *
     * @throws YAPI_Exception on error
     */
    public double getPeriod() throws YAPI_Exception
    {
        return get_period();
    }

    /**
     * Changes the PWM duty cycle, in per cents.
     *
     * @param newval : a floating point number corresponding to the PWM duty cycle, in per cents
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_dutyCycle(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
        _setAttr("dutyCycle",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the PWM duty cycle, in per cents.
     *
     * @param newval : a floating point number corresponding to the PWM duty cycle, in per cents
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDutyCycle(double newval)  throws YAPI_Exception
    {
        return set_dutyCycle(newval);
    }

    /**
     * Returns the PWM duty cycle, in per cents.
     *
     * @return a floating point number corresponding to the PWM duty cycle, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public double get_dutyCycle() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DUTYCYCLE_INVALID;
            }
        }
        return _dutyCycle;
    }

    /**
     * Returns the PWM duty cycle, in per cents.
     *
     * @return a floating point number corresponding to the PWM duty cycle, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public double getDutyCycle() throws YAPI_Exception
    {
        return get_dutyCycle();
    }

    /**
     *  Changes the PWM pulse length, in milliseconds. A pulse length cannot be longer than period,
     * otherwise it is truncated.
     *
     * @param newval : a floating point number corresponding to the PWM pulse length, in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_pulseDuration(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
        _setAttr("pulseDuration",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     *  Changes the PWM pulse length, in milliseconds. A pulse length cannot be longer than period,
     * otherwise it is truncated.
     *
     * @param newval : a floating point number corresponding to the PWM pulse length, in milliseconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPulseDuration(double newval)  throws YAPI_Exception
    {
        return set_pulseDuration(newval);
    }

    /**
     * Returns the PWM pulse length in milliseconds, as a floating point number.
     *
     *  @return a floating point number corresponding to the PWM pulse length in milliseconds, as a
     * floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double get_pulseDuration() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PULSEDURATION_INVALID;
            }
        }
        return _pulseDuration;
    }

    /**
     * Returns the PWM pulse length in milliseconds, as a floating point number.
     *
     *  @return a floating point number corresponding to the PWM pulse length in milliseconds, as a
     * floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double getPulseDuration() throws YAPI_Exception
    {
        return get_pulseDuration();
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String get_pwmTransition() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PWMTRANSITION_INVALID;
            }
        }
        return _pwmTransition;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String getPwmTransition() throws YAPI_Exception
    {
        return get_pwmTransition();
    }

    public int set_pwmTransition(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("pwmTransition",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPwmTransition(String newval)  throws YAPI_Exception
    {
        return set_pwmTransition(newval);
    }

    /**
     * Returns the state of the PWM at device power on.
     *
     *  @return either YPwmOutput.ENABLEDATPOWERON_FALSE or YPwmOutput.ENABLEDATPOWERON_TRUE, according to
     * the state of the PWM at device power on
     *
     * @throws YAPI_Exception on error
     */
    public int get_enabledAtPowerOn() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ENABLEDATPOWERON_INVALID;
            }
        }
        return _enabledAtPowerOn;
    }

    /**
     * Returns the state of the PWM at device power on.
     *
     *  @return either Y_ENABLEDATPOWERON_FALSE or Y_ENABLEDATPOWERON_TRUE, according to the state of the
     * PWM at device power on
     *
     * @throws YAPI_Exception on error
     */
    public int getEnabledAtPowerOn() throws YAPI_Exception
    {
        return get_enabledAtPowerOn();
    }

    /**
     * Changes the state of the PWM at device power on. Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     *  @param newval : either YPwmOutput.ENABLEDATPOWERON_FALSE or YPwmOutput.ENABLEDATPOWERON_TRUE,
     * according to the state of the PWM at device power on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_enabledAtPowerOn(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("enabledAtPowerOn",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the PWM at device power on. Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     *  @param newval : either Y_ENABLEDATPOWERON_FALSE or Y_ENABLEDATPOWERON_TRUE, according to the state
     * of the PWM at device power on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setEnabledAtPowerOn(int newval)  throws YAPI_Exception
    {
        return set_enabledAtPowerOn(newval);
    }

    /**
     * Changes the PWM duty cycle at device power on. Remember to call the matching
     * module saveToFlash() method, otherwise this call will have no effect.
     *
     * @param newval : a floating point number corresponding to the PWM duty cycle at device power on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_dutyCycleAtPowerOn(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
        _setAttr("dutyCycleAtPowerOn",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the PWM duty cycle at device power on. Remember to call the matching
     * module saveToFlash() method, otherwise this call will have no effect.
     *
     * @param newval : a floating point number corresponding to the PWM duty cycle at device power on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDutyCycleAtPowerOn(double newval)  throws YAPI_Exception
    {
        return set_dutyCycleAtPowerOn(newval);
    }

    /**
     * Returns the PWMs duty cycle at device power on as a floating point number between 0 and 100
     *
     *  @return a floating point number corresponding to the PWMs duty cycle at device power on as a
     * floating point number between 0 and 100
     *
     * @throws YAPI_Exception on error
     */
    public double get_dutyCycleAtPowerOn() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DUTYCYCLEATPOWERON_INVALID;
            }
        }
        return _dutyCycleAtPowerOn;
    }

    /**
     * Returns the PWMs duty cycle at device power on as a floating point number between 0 and 100
     *
     *  @return a floating point number corresponding to the PWMs duty cycle at device power on as a
     * floating point number between 0 and 100
     *
     * @throws YAPI_Exception on error
     */
    public double getDutyCycleAtPowerOn() throws YAPI_Exception
    {
        return get_dutyCycleAtPowerOn();
    }

    /**
     * Retrieves a PWM for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the PWM is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPwmOutput.isOnline() to test if the PWM is
     * indeed online at a given time. In case of ambiguity when looking for
     * a PWM by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the PWM
     *
     * @return a YPwmOutput object allowing you to drive the PWM.
     */
    public static YPwmOutput FindPwmOutput(String func)
    {
        YPwmOutput obj;
        obj = (YPwmOutput) YFunction._FindFromCache("PwmOutput", func);
        if (obj == null) {
            obj = new YPwmOutput(func);
            YFunction._AddToCache("PwmOutput", func, obj);
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
        _valueCallbackPwmOutput = callback;
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
        if (_valueCallbackPwmOutput != null) {
            _valueCallbackPwmOutput.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Performs a smooth transistion of the pulse duration toward a given value. Any period,
     * frequency, duty cycle or pulse width change will cancel any ongoing transition process.
     *
     * @param ms_target   : new pulse duration at the end of the transition
     *         (floating-point number, representing the pulse duration in milliseconds)
     * @param ms_duration : total duration of the transition, in milliseconds
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int pulseDurationMove(double ms_target,int ms_duration) throws YAPI_Exception
    {
        String newval;
        if (ms_target < 0.0) {
            ms_target = 0.0;
        }
        newval = String.format("%dms:%d", (int) (double)Math.round(ms_target*65536),ms_duration);
        return set_pwmTransition(newval);
    }

    /**
     * Performs a smooth change of the pulse duration toward a given value.
     *
     * @param target      : new duty cycle at the end of the transition
     *         (floating-point number, between 0 and 1)
     * @param ms_duration : total duration of the transition, in milliseconds
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int dutyCycleMove(double target,int ms_duration) throws YAPI_Exception
    {
        String newval;
        if (target < 0.0) {
            target = 0.0;
        }
        if (target > 100.0) {
            target = 100.0;
        }
        newval = String.format("%d:%d", (int) (double)Math.round(target*65536),ms_duration);
        return set_pwmTransition(newval);
    }

    /**
     * Continues the enumeration of PWMs started using yFirstPwmOutput().
     *
     * @return a pointer to a YPwmOutput object, corresponding to
     *         a PWM currently online, or a null pointer
     *         if there are no more PWMs to enumerate.
     */
    public  YPwmOutput nextPwmOutput()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindPwmOutput(next_hwid);
    }

    /**
     * Starts the enumeration of PWMs currently accessible.
     * Use the method YPwmOutput.nextPwmOutput() to iterate on
     * next PWMs.
     *
     * @return a pointer to a YPwmOutput object, corresponding to
     *         the first PWM currently online, or a null pointer
     *         if there are none.
     */
    public static YPwmOutput FirstPwmOutput()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("PwmOutput");
        if (next_hwid == null)  return null;
        return FindPwmOutput(next_hwid);
    }

    //--- (end of YPwmOutput implementation)
}

