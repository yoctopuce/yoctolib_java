/*********************************************************************
 *
 * $Id: YWatchdog.java 19328 2015-02-17 17:30:45Z seb $
 *
 * Implements FindWatchdog(), the high-level API for Watchdog functions
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

//--- (YWatchdog return codes)
//--- (end of YWatchdog return codes)
//--- (YWatchdog class start)
/**
 * YWatchdog Class: Watchdog function interface
 *
 * The watchog function works like a relay and can cause a brief power cut
 * to an appliance after a preset delay to force this appliance to
 * reset. The Watchdog must be called from time to time to reset the
 * timer and prevent the appliance reset.
 * The watchdog can be driven direcly with <i>pulse</i> and <i>delayedpulse</i> methods to switch
 * off an appliance for a given duration.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YWatchdog extends YFunction
{
//--- (end of YWatchdog class start)
//--- (YWatchdog definitions)
    public static class YDelayedPulse
    {
        public int target = YAPI.INVALID_INT;
        public int ms = YAPI.INVALID_INT;
        public int moving = YAPI.INVALID_UINT;
        public YDelayedPulse(){}
    }

    /**
     * invalid state value
     */
    public static final int STATE_A = 0;
    public static final int STATE_B = 1;
    public static final int STATE_INVALID = -1;
    /**
     * invalid stateAtPowerOn value
     */
    public static final int STATEATPOWERON_UNCHANGED = 0;
    public static final int STATEATPOWERON_A = 1;
    public static final int STATEATPOWERON_B = 2;
    public static final int STATEATPOWERON_INVALID = -1;
    /**
     * invalid maxTimeOnStateA value
     */
    public static final long MAXTIMEONSTATEA_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid maxTimeOnStateB value
     */
    public static final long MAXTIMEONSTATEB_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid output value
     */
    public static final int OUTPUT_OFF = 0;
    public static final int OUTPUT_ON = 1;
    public static final int OUTPUT_INVALID = -1;
    /**
     * invalid pulseTimer value
     */
    public static final long PULSETIMER_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid countdown value
     */
    public static final long COUNTDOWN_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid autoStart value
     */
    public static final int AUTOSTART_OFF = 0;
    public static final int AUTOSTART_ON = 1;
    public static final int AUTOSTART_INVALID = -1;
    /**
     * invalid running value
     */
    public static final int RUNNING_OFF = 0;
    public static final int RUNNING_ON = 1;
    public static final int RUNNING_INVALID = -1;
    /**
     * invalid triggerDelay value
     */
    public static final long TRIGGERDELAY_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid triggerDuration value
     */
    public static final long TRIGGERDURATION_INVALID = YAPI.INVALID_LONG;
    public static final YDelayedPulse DELAYEDPULSETIMER_INVALID = null;
    protected int _state = STATE_INVALID;
    protected int _stateAtPowerOn = STATEATPOWERON_INVALID;
    protected long _maxTimeOnStateA = MAXTIMEONSTATEA_INVALID;
    protected long _maxTimeOnStateB = MAXTIMEONSTATEB_INVALID;
    protected int _output = OUTPUT_INVALID;
    protected long _pulseTimer = PULSETIMER_INVALID;
    protected YDelayedPulse _delayedPulseTimer = new YDelayedPulse();
    protected long _countdown = COUNTDOWN_INVALID;
    protected int _autoStart = AUTOSTART_INVALID;
    protected int _running = RUNNING_INVALID;
    protected long _triggerDelay = TRIGGERDELAY_INVALID;
    protected long _triggerDuration = TRIGGERDURATION_INVALID;
    protected UpdateCallback _valueCallbackWatchdog = null;

    /**
     * Deprecated UpdateCallback for Watchdog
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YWatchdog function, String functionValue);
    }

    /**
     * TimedReportCallback for Watchdog
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YWatchdog  function, YMeasure measure);
    }
    //--- (end of YWatchdog definitions)


    /**
     *
     * @param func : functionid
     */
    protected YWatchdog(String func)
    {
        super(func);
        _className = "Watchdog";
        //--- (YWatchdog attributes initialization)
        //--- (end of YWatchdog attributes initialization)
    }

    //--- (YWatchdog implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("state")) {
            _state = json_val.getInt("state") > 0 ? 1 : 0;
        }
        if (json_val.has("stateAtPowerOn")) {
            _stateAtPowerOn = json_val.getInt("stateAtPowerOn");
        }
        if (json_val.has("maxTimeOnStateA")) {
            _maxTimeOnStateA = json_val.getLong("maxTimeOnStateA");
        }
        if (json_val.has("maxTimeOnStateB")) {
            _maxTimeOnStateB = json_val.getLong("maxTimeOnStateB");
        }
        if (json_val.has("output")) {
            _output = json_val.getInt("output") > 0 ? 1 : 0;
        }
        if (json_val.has("pulseTimer")) {
            _pulseTimer = json_val.getLong("pulseTimer");
        }
        if (json_val.has("delayedPulseTimer")) {
            JSONObject subjson = json_val.getJSONObject("delayedPulseTimer");
            if (subjson.has("moving")) {
                _delayedPulseTimer.moving = subjson.getInt("moving");
            }
            if (subjson.has("target")) {
                _delayedPulseTimer.moving = subjson.getInt("target");
            }
            if (subjson.has("ms")) {
                _delayedPulseTimer.moving = subjson.getInt("ms");
            }
        }
        if (json_val.has("countdown")) {
            _countdown = json_val.getLong("countdown");
        }
        if (json_val.has("autoStart")) {
            _autoStart = json_val.getInt("autoStart") > 0 ? 1 : 0;
        }
        if (json_val.has("running")) {
            _running = json_val.getInt("running") > 0 ? 1 : 0;
        }
        if (json_val.has("triggerDelay")) {
            _triggerDelay = json_val.getLong("triggerDelay");
        }
        if (json_val.has("triggerDuration")) {
            _triggerDuration = json_val.getLong("triggerDuration");
        }
        super._parseAttr(json_val);
    }

    /**
     * invalid delayedPulseTimer
     */
    /**
     * Returns the state of the watchdog (A for the idle position, B for the active position).
     *
     *  @return either YWatchdog.STATE_A or YWatchdog.STATE_B, according to the state of the watchdog (A
     * for the idle position, B for the active position)
     *
     * @throws YAPI_Exception on error
     */
    public int get_state() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return STATE_INVALID;
            }
        }
        return _state;
    }

    /**
     * Returns the state of the watchdog (A for the idle position, B for the active position).
     *
     *  @return either Y_STATE_A or Y_STATE_B, according to the state of the watchdog (A for the idle
     * position, B for the active position)
     *
     * @throws YAPI_Exception on error
     */
    public int getState() throws YAPI_Exception
    {
        return get_state();
    }

    /**
     * Changes the state of the watchdog (A for the idle position, B for the active position).
     *
     *  @param newval : either YWatchdog.STATE_A or YWatchdog.STATE_B, according to the state of the
     * watchdog (A for the idle position, B for the active position)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_state(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("state",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the watchdog (A for the idle position, B for the active position).
     *
     *  @param newval : either Y_STATE_A or Y_STATE_B, according to the state of the watchdog (A for the
     * idle position, B for the active position)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setState(int newval)  throws YAPI_Exception
    {
        return set_state(newval);
    }

    /**
     *  Returns the state of the watchdog at device startup (A for the idle position, B for the active
     * position, UNCHANGED for no change).
     *
     *  @return a value among YWatchdog.STATEATPOWERON_UNCHANGED, YWatchdog.STATEATPOWERON_A and
     *  YWatchdog.STATEATPOWERON_B corresponding to the state of the watchdog at device startup (A for the
     * idle position, B for the active position, UNCHANGED for no change)
     *
     * @throws YAPI_Exception on error
     */
    public int get_stateAtPowerOn() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return STATEATPOWERON_INVALID;
            }
        }
        return _stateAtPowerOn;
    }

    /**
     *  Returns the state of the watchdog at device startup (A for the idle position, B for the active
     * position, UNCHANGED for no change).
     *
     *  @return a value among Y_STATEATPOWERON_UNCHANGED, Y_STATEATPOWERON_A and Y_STATEATPOWERON_B
     *  corresponding to the state of the watchdog at device startup (A for the idle position, B for the
     * active position, UNCHANGED for no change)
     *
     * @throws YAPI_Exception on error
     */
    public int getStateAtPowerOn() throws YAPI_Exception
    {
        return get_stateAtPowerOn();
    }

    /**
     * Preset the state of the watchdog at device startup (A for the idle position,
     * B for the active position, UNCHANGED for no modification). Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     *  @param newval : a value among YWatchdog.STATEATPOWERON_UNCHANGED, YWatchdog.STATEATPOWERON_A and
     * YWatchdog.STATEATPOWERON_B
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_stateAtPowerOn(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("stateAtPowerOn",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Preset the state of the watchdog at device startup (A for the idle position,
     * B for the active position, UNCHANGED for no modification). Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     * @param newval : a value among Y_STATEATPOWERON_UNCHANGED, Y_STATEATPOWERON_A and Y_STATEATPOWERON_B
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setStateAtPowerOn(int newval)  throws YAPI_Exception
    {
        return set_stateAtPowerOn(newval);
    }

    /**
     *  Retourne the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state A before automatically
     * switching back in to B state. Zero means no maximum time.
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public long get_maxTimeOnStateA() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return MAXTIMEONSTATEA_INVALID;
            }
        }
        return _maxTimeOnStateA;
    }

    /**
     *  Retourne the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state A before automatically
     * switching back in to B state. Zero means no maximum time.
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public long getMaxTimeOnStateA() throws YAPI_Exception
    {
        return get_maxTimeOnStateA();
    }

    /**
     *  Sets the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state A before automatically
     * switching back in to B state. Use zero for no maximum time.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_maxTimeOnStateA(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("maxTimeOnStateA",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     *  Sets the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state A before automatically
     * switching back in to B state. Use zero for no maximum time.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMaxTimeOnStateA(long newval)  throws YAPI_Exception
    {
        return set_maxTimeOnStateA(newval);
    }

    /**
     *  Retourne the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state B before automatically
     * switching back in to A state. Zero means no maximum time.
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public long get_maxTimeOnStateB() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return MAXTIMEONSTATEB_INVALID;
            }
        }
        return _maxTimeOnStateB;
    }

    /**
     *  Retourne the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state B before automatically
     * switching back in to A state. Zero means no maximum time.
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public long getMaxTimeOnStateB() throws YAPI_Exception
    {
        return get_maxTimeOnStateB();
    }

    /**
     *  Sets the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state B before automatically
     * switching back in to A state. Use zero for no maximum time.
     *
     * @param newval : an integer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_maxTimeOnStateB(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("maxTimeOnStateB",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     *  Sets the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state B before automatically
     * switching back in to A state. Use zero for no maximum time.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMaxTimeOnStateB(long newval)  throws YAPI_Exception
    {
        return set_maxTimeOnStateB(newval);
    }

    /**
     * Returns the output state of the watchdog, when used as a simple switch (single throw).
     *
     *  @return either YWatchdog.OUTPUT_OFF or YWatchdog.OUTPUT_ON, according to the output state of the
     * watchdog, when used as a simple switch (single throw)
     *
     * @throws YAPI_Exception on error
     */
    public int get_output() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return OUTPUT_INVALID;
            }
        }
        return _output;
    }

    /**
     * Returns the output state of the watchdog, when used as a simple switch (single throw).
     *
     *  @return either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the watchdog, when
     * used as a simple switch (single throw)
     *
     * @throws YAPI_Exception on error
     */
    public int getOutput() throws YAPI_Exception
    {
        return get_output();
    }

    /**
     * Changes the output state of the watchdog, when used as a simple switch (single throw).
     *
     *  @param newval : either YWatchdog.OUTPUT_OFF or YWatchdog.OUTPUT_ON, according to the output state
     * of the watchdog, when used as a simple switch (single throw)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_output(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("output",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the output state of the watchdog, when used as a simple switch (single throw).
     *
     *  @param newval : either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the watchdog,
     * when used as a simple switch (single throw)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setOutput(int newval)  throws YAPI_Exception
    {
        return set_output(newval);
    }

    /**
     * Returns the number of milliseconds remaining before the watchdog is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     *
     *  @return an integer corresponding to the number of milliseconds remaining before the watchdog is
     * returned to idle position
     *         (state A), during a measured pulse generation
     *
     * @throws YAPI_Exception on error
     */
    public long get_pulseTimer() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PULSETIMER_INVALID;
            }
        }
        return _pulseTimer;
    }

    /**
     * Returns the number of milliseconds remaining before the watchdog is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     *
     *  @return an integer corresponding to the number of milliseconds remaining before the watchdog is
     * returned to idle position
     *         (state A), during a measured pulse generation
     *
     * @throws YAPI_Exception on error
     */
    public long getPulseTimer() throws YAPI_Exception
    {
        return get_pulseTimer();
    }

    public int set_pulseTimer(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("pulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPulseTimer(long newval)  throws YAPI_Exception
    {
        return set_pulseTimer(newval);
    }

    /**
     * Sets the relay to output B (active) for a specified duration, then brings it
     * automatically back to output A (idle state).
     *
     * @param ms_duration : pulse duration, in millisecondes
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int pulse(int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(ms_duration);
        _setAttr("pulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public YDelayedPulse get_delayedPulseTimer() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DELAYEDPULSETIMER_INVALID;
            }
        }
        return _delayedPulseTimer;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public YDelayedPulse getDelayedPulseTimer() throws YAPI_Exception
    {
        return get_delayedPulseTimer();
    }

    public int set_delayedPulseTimer(YDelayedPulse  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("delayedPulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    public int setDelayedPulseTimer(YDelayedPulse newval)  throws YAPI_Exception
    {
        return set_delayedPulseTimer(newval);
    }

    /**
     * Schedules a pulse.
     *
     * @param ms_delay : waiting time before the pulse, in millisecondes
     * @param ms_duration : pulse duration, in millisecondes
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int delayedPulse(int ms_delay,int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",ms_delay,ms_duration);
        _setAttr("delayedPulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the number of milliseconds remaining before a pulse (delayedPulse() call)
     * When there is no scheduled pulse, returns zero.
     *
     * @return an integer corresponding to the number of milliseconds remaining before a pulse (delayedPulse() call)
     *         When there is no scheduled pulse, returns zero
     *
     * @throws YAPI_Exception on error
     */
    public long get_countdown() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return COUNTDOWN_INVALID;
            }
        }
        return _countdown;
    }

    /**
     * Returns the number of milliseconds remaining before a pulse (delayedPulse() call)
     * When there is no scheduled pulse, returns zero.
     *
     * @return an integer corresponding to the number of milliseconds remaining before a pulse (delayedPulse() call)
     *         When there is no scheduled pulse, returns zero
     *
     * @throws YAPI_Exception on error
     */
    public long getCountdown() throws YAPI_Exception
    {
        return get_countdown();
    }

    /**
     * Returns the watchdog runing state at module power on.
     *
     *  @return either YWatchdog.AUTOSTART_OFF or YWatchdog.AUTOSTART_ON, according to the watchdog runing
     * state at module power on
     *
     * @throws YAPI_Exception on error
     */
    public int get_autoStart() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return AUTOSTART_INVALID;
            }
        }
        return _autoStart;
    }

    /**
     * Returns the watchdog runing state at module power on.
     *
     * @return either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the watchdog runing state at module power on
     *
     * @throws YAPI_Exception on error
     */
    public int getAutoStart() throws YAPI_Exception
    {
        return get_autoStart();
    }

    /**
     * Changes the watchdog runningsttae at module power on. Remember to call the
     * saveToFlash() method and then to reboot the module to apply this setting.
     *
     *  @param newval : either YWatchdog.AUTOSTART_OFF or YWatchdog.AUTOSTART_ON, according to the watchdog
     * runningsttae at module power on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_autoStart(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("autoStart",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the watchdog runningsttae at module power on. Remember to call the
     * saveToFlash() method and then to reboot the module to apply this setting.
     *
     *  @param newval : either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the watchdog runningsttae at
     * module power on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAutoStart(int newval)  throws YAPI_Exception
    {
        return set_autoStart(newval);
    }

    /**
     * Returns the watchdog running state.
     *
     * @return either YWatchdog.RUNNING_OFF or YWatchdog.RUNNING_ON, according to the watchdog running state
     *
     * @throws YAPI_Exception on error
     */
    public int get_running() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return RUNNING_INVALID;
            }
        }
        return _running;
    }

    /**
     * Returns the watchdog running state.
     *
     * @return either Y_RUNNING_OFF or Y_RUNNING_ON, according to the watchdog running state
     *
     * @throws YAPI_Exception on error
     */
    public int getRunning() throws YAPI_Exception
    {
        return get_running();
    }

    /**
     * Changes the running state of the watchdog.
     *
     *  @param newval : either YWatchdog.RUNNING_OFF or YWatchdog.RUNNING_ON, according to the running
     * state of the watchdog
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_running(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("running",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the running state of the watchdog.
     *
     * @param newval : either Y_RUNNING_OFF or Y_RUNNING_ON, according to the running state of the watchdog
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setRunning(int newval)  throws YAPI_Exception
    {
        return set_running(newval);
    }

    /**
     * Resets the watchdog. When the watchdog is running, this function
     * must be called on a regular basis to prevent the watchog to
     * trigger
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int resetWatchdog()  throws YAPI_Exception
    {
        String rest_val;
        rest_val = "1";
        _setAttr("running",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns  the waiting duration before a reset is automatically triggered by the watchdog, in milliseconds.
     *
     *  @return an integer corresponding to  the waiting duration before a reset is automatically triggered
     * by the watchdog, in milliseconds
     *
     * @throws YAPI_Exception on error
     */
    public long get_triggerDelay() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return TRIGGERDELAY_INVALID;
            }
        }
        return _triggerDelay;
    }

    /**
     * Returns  the waiting duration before a reset is automatically triggered by the watchdog, in milliseconds.
     *
     *  @return an integer corresponding to  the waiting duration before a reset is automatically triggered
     * by the watchdog, in milliseconds
     *
     * @throws YAPI_Exception on error
     */
    public long getTriggerDelay() throws YAPI_Exception
    {
        return get_triggerDelay();
    }

    /**
     * Changes the waiting delay before a reset is triggered by the watchdog, in milliseconds.
     *
     *  @param newval : an integer corresponding to the waiting delay before a reset is triggered by the
     * watchdog, in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_triggerDelay(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("triggerDelay",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the waiting delay before a reset is triggered by the watchdog, in milliseconds.
     *
     *  @param newval : an integer corresponding to the waiting delay before a reset is triggered by the
     * watchdog, in milliseconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setTriggerDelay(long newval)  throws YAPI_Exception
    {
        return set_triggerDelay(newval);
    }

    /**
     * Returns the duration of resets caused by the watchdog, in milliseconds.
     *
     * @return an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     *
     * @throws YAPI_Exception on error
     */
    public long get_triggerDuration() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return TRIGGERDURATION_INVALID;
            }
        }
        return _triggerDuration;
    }

    /**
     * Returns the duration of resets caused by the watchdog, in milliseconds.
     *
     * @return an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     *
     * @throws YAPI_Exception on error
     */
    public long getTriggerDuration() throws YAPI_Exception
    {
        return get_triggerDuration();
    }

    /**
     * Changes the duration of resets caused by the watchdog, in milliseconds.
     *
     * @param newval : an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_triggerDuration(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("triggerDuration",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the duration of resets caused by the watchdog, in milliseconds.
     *
     * @param newval : an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setTriggerDuration(long newval)  throws YAPI_Exception
    {
        return set_triggerDuration(newval);
    }

    /**
     * Retrieves a watchdog for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the watchdog is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YWatchdog.isOnline() to test if the watchdog is
     * indeed online at a given time. In case of ambiguity when looking for
     * a watchdog by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the watchdog
     *
     * @return a YWatchdog object allowing you to drive the watchdog.
     */
    public static YWatchdog FindWatchdog(String func)
    {
        YWatchdog obj;
        obj = (YWatchdog) YFunction._FindFromCache("Watchdog", func);
        if (obj == null) {
            obj = new YWatchdog(func);
            YFunction._AddToCache("Watchdog", func, obj);
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
        _valueCallbackWatchdog = callback;
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
        if (_valueCallbackWatchdog != null) {
            _valueCallbackWatchdog.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of watchdog started using yFirstWatchdog().
     *
     * @return a pointer to a YWatchdog object, corresponding to
     *         a watchdog currently online, or a null pointer
     *         if there are no more watchdog to enumerate.
     */
    public  YWatchdog nextWatchdog()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindWatchdog(next_hwid);
    }

    /**
     * Starts the enumeration of watchdog currently accessible.
     * Use the method YWatchdog.nextWatchdog() to iterate on
     * next watchdog.
     *
     * @return a pointer to a YWatchdog object, corresponding to
     *         the first watchdog currently online, or a null pointer
     *         if there are none.
     */
    public static YWatchdog FirstWatchdog()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Watchdog");
        if (next_hwid == null)  return null;
        return FindWatchdog(next_hwid);
    }

    //--- (end of YWatchdog implementation)
}

