/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindWatchdog(), the high-level API for Watchdog functions
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
import java.util.Locale;

//--- (YWatchdog return codes)
//--- (end of YWatchdog return codes)
//--- (YWatchdog yapiwrapper)
//--- (end of YWatchdog yapiwrapper)
//--- (YWatchdog class start)
/**
 * YWatchdog Class: watchdog control interface, available for instance in the Yocto-WatchdogDC
 *
 * The YWatchdog class allows you to drive a Yoctopuce watchdog.
 * A watchdog works like a relay, with an extra timer that can automatically
 * trigger a brief power cycle to an appliance after a preset delay, to force this
 * appliance to reset if a problem occurs. During normal use, the watchdog timer
 * is reset periodically by the application to prevent the automated power cycle.
 * Whenever the application dies, the watchdog will automatically trigger the power cycle.
 * The watchdog can also be driven directly with pulse and delayedPulse
 * methods to switch off an appliance for a given duration.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
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
    /**
     * invalid lastTrigger value
     */
    public static final int LASTTRIGGER_INVALID = YAPI.INVALID_UINT;
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
    protected int _lastTrigger = LASTTRIGGER_INVALID;
    protected UpdateCallback _valueCallbackWatchdog = null;
    protected int _firm = 0;

    /**
     * Deprecated UpdateCallback for Watchdog
     */
    public interface UpdateCallback
    {
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
    public interface TimedReportCallback
    {
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
    protected YWatchdog(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Watchdog";
        //--- (YWatchdog attributes initialization)
        //--- (end of YWatchdog attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YWatchdog(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YWatchdog implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
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
            YJSONObject subjson = json_val.getYJSONObject("delayedPulseTimer");
            if (subjson.has("moving")) {
                _delayedPulseTimer.moving = subjson.getInt("moving");
            }
            if (subjson.has("target")) {
                _delayedPulseTimer.target = subjson.getInt("target");
            }
            if (subjson.has("ms")) {
                _delayedPulseTimer.ms = subjson.getInt("ms");
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
        if (json_val.has("lastTrigger")) {
            _lastTrigger = json_val.getInt("lastTrigger");
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return STATE_INVALID;
                }
            }
            res = _state;
        }
        return res;
    }

    /**
     * Returns the state of the watchdog (A for the idle position, B for the active position).
     *
     *  @return either YWatchdog.STATE_A or YWatchdog.STATE_B, according to the state of the watchdog (A
     * for the idle position, B for the active position)
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
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("state",rest_val);
        }
        return YAPI.SUCCESS;
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
    public int setState(int newval)  throws YAPI_Exception
    {
        return set_state(newval);
    }

    /**
     * Returns the state of the watchdog at device startup (A for the idle position,
     * B for the active position, UNCHANGED to leave the relay state as is).
     *
     *  @return a value among YWatchdog.STATEATPOWERON_UNCHANGED, YWatchdog.STATEATPOWERON_A and
     *  YWatchdog.STATEATPOWERON_B corresponding to the state of the watchdog at device startup (A for the
     * idle position,
     *         B for the active position, UNCHANGED to leave the relay state as is)
     *
     * @throws YAPI_Exception on error
     */
    public int get_stateAtPowerOn() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return STATEATPOWERON_INVALID;
                }
            }
            res = _stateAtPowerOn;
        }
        return res;
    }

    /**
     * Returns the state of the watchdog at device startup (A for the idle position,
     * B for the active position, UNCHANGED to leave the relay state as is).
     *
     *  @return a value among YWatchdog.STATEATPOWERON_UNCHANGED, YWatchdog.STATEATPOWERON_A and
     *  YWatchdog.STATEATPOWERON_B corresponding to the state of the watchdog at device startup (A for the
     * idle position,
     *         B for the active position, UNCHANGED to leave the relay state as is)
     *
     * @throws YAPI_Exception on error
     */
    public int getStateAtPowerOn() throws YAPI_Exception
    {
        return get_stateAtPowerOn();
    }

    /**
     * Changes the state of the watchdog at device startup (A for the idle position,
     * B for the active position, UNCHANGED to leave the relay state as is).
     * Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     *  @param newval : a value among YWatchdog.STATEATPOWERON_UNCHANGED, YWatchdog.STATEATPOWERON_A and
     *  YWatchdog.STATEATPOWERON_B corresponding to the state of the watchdog at device startup (A for the
     * idle position,
     *         B for the active position, UNCHANGED to leave the relay state as is)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_stateAtPowerOn(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("stateAtPowerOn",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the watchdog at device startup (A for the idle position,
     * B for the active position, UNCHANGED to leave the relay state as is).
     * Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     *  @param newval : a value among YWatchdog.STATEATPOWERON_UNCHANGED, YWatchdog.STATEATPOWERON_A and
     *  YWatchdog.STATEATPOWERON_B corresponding to the state of the watchdog at device startup (A for the
     * idle position,
     *         B for the active position, UNCHANGED to leave the relay state as is)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setStateAtPowerOn(int newval)  throws YAPI_Exception
    {
        return set_stateAtPowerOn(newval);
    }

    /**
     * Returns the maximum time (ms) allowed for the watchdog to stay in state
     * A before automatically switching back in to B state. Zero means no time limit.
     *
     * @return an integer corresponding to the maximum time (ms) allowed for the watchdog to stay in state
     *         A before automatically switching back in to B state
     *
     * @throws YAPI_Exception on error
     */
    public long get_maxTimeOnStateA() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return MAXTIMEONSTATEA_INVALID;
                }
            }
            res = _maxTimeOnStateA;
        }
        return res;
    }

    /**
     * Returns the maximum time (ms) allowed for the watchdog to stay in state
     * A before automatically switching back in to B state. Zero means no time limit.
     *
     * @return an integer corresponding to the maximum time (ms) allowed for the watchdog to stay in state
     *         A before automatically switching back in to B state
     *
     * @throws YAPI_Exception on error
     */
    public long getMaxTimeOnStateA() throws YAPI_Exception
    {
        return get_maxTimeOnStateA();
    }

    /**
     * Changes the maximum time (ms) allowed for the watchdog to stay in state A
     * before automatically switching back in to B state. Use zero for no time limit.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the maximum time (ms) allowed for the watchdog to stay in state A
     *         before automatically switching back in to B state
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_maxTimeOnStateA(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("maxTimeOnStateA",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximum time (ms) allowed for the watchdog to stay in state A
     * before automatically switching back in to B state. Use zero for no time limit.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the maximum time (ms) allowed for the watchdog to stay in state A
     *         before automatically switching back in to B state
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMaxTimeOnStateA(long newval)  throws YAPI_Exception
    {
        return set_maxTimeOnStateA(newval);
    }

    /**
     * Retourne the maximum time (ms) allowed for the watchdog to stay in state B
     * before automatically switching back in to A state. Zero means no time limit.
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public long get_maxTimeOnStateB() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return MAXTIMEONSTATEB_INVALID;
                }
            }
            res = _maxTimeOnStateB;
        }
        return res;
    }

    /**
     * Retourne the maximum time (ms) allowed for the watchdog to stay in state B
     * before automatically switching back in to A state. Zero means no time limit.
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
     * Changes the maximum time (ms) allowed for the watchdog to stay in state B before
     * automatically switching back in to A state. Use zero for no time limit.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : an integer corresponding to the maximum time (ms) allowed for the watchdog to stay
     * in state B before
     *         automatically switching back in to A state
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_maxTimeOnStateB(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("maxTimeOnStateB",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximum time (ms) allowed for the watchdog to stay in state B before
     * automatically switching back in to A state. Use zero for no time limit.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : an integer corresponding to the maximum time (ms) allowed for the watchdog to stay
     * in state B before
     *         automatically switching back in to A state
     *
     * @return YAPI.SUCCESS if the call succeeds.
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return OUTPUT_INVALID;
                }
            }
            res = _output;
        }
        return res;
    }

    /**
     * Returns the output state of the watchdog, when used as a simple switch (single throw).
     *
     *  @return either YWatchdog.OUTPUT_OFF or YWatchdog.OUTPUT_ON, according to the output state of the
     * watchdog, when used as a simple switch (single throw)
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
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("output",rest_val);
        }
        return YAPI.SUCCESS;
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
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PULSETIMER_INVALID;
                }
            }
            res = _pulseTimer;
        }
        return res;
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
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("pulseTimer",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Sets the relay to output B (active) for a specified duration, then brings it
     * automatically back to output A (idle state).
     *
     * @param ms_duration : pulse duration, in milliseconds
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

    public YDelayedPulse get_delayedPulseTimer() throws YAPI_Exception
    {
        YDelayedPulse res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DELAYEDPULSETIMER_INVALID;
                }
            }
            res = _delayedPulseTimer;
        }
        return res;
    }

    public int set_delayedPulseTimer(YDelayedPulse  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = String.format(Locale.US, "%d:%d",newval.target,newval.ms);
            _setAttr("delayedPulseTimer",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Schedules a pulse.
     *
     * @param ms_delay : waiting time before the pulse, in milliseconds
     * @param ms_duration : pulse duration, in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int delayedPulse(int ms_delay,int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format(Locale.US, "%d:%d",ms_delay,ms_duration);
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
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COUNTDOWN_INVALID;
                }
            }
            res = _countdown;
        }
        return res;
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
     * Returns the watchdog running state at module power on.
     *
     *  @return either YWatchdog.AUTOSTART_OFF or YWatchdog.AUTOSTART_ON, according to the watchdog running
     * state at module power on
     *
     * @throws YAPI_Exception on error
     */
    public int get_autoStart() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return AUTOSTART_INVALID;
                }
            }
            res = _autoStart;
        }
        return res;
    }

    /**
     * Returns the watchdog running state at module power on.
     *
     *  @return either YWatchdog.AUTOSTART_OFF or YWatchdog.AUTOSTART_ON, according to the watchdog running
     * state at module power on
     *
     * @throws YAPI_Exception on error
     */
    public int getAutoStart() throws YAPI_Exception
    {
        return get_autoStart();
    }

    /**
     * Changes the watchdog running state at module power on. Remember to call the
     * saveToFlash() method and then to reboot the module to apply this setting.
     *
     *  @param newval : either YWatchdog.AUTOSTART_OFF or YWatchdog.AUTOSTART_ON, according to the watchdog
     * running state at module power on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_autoStart(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("autoStart",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the watchdog running state at module power on. Remember to call the
     * saveToFlash() method and then to reboot the module to apply this setting.
     *
     *  @param newval : either YWatchdog.AUTOSTART_OFF or YWatchdog.AUTOSTART_ON, according to the watchdog
     * running state at module power on
     *
     * @return YAPI.SUCCESS if the call succeeds.
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return RUNNING_INVALID;
                }
            }
            res = _running;
        }
        return res;
    }

    /**
     * Returns the watchdog running state.
     *
     * @return either YWatchdog.RUNNING_OFF or YWatchdog.RUNNING_ON, according to the watchdog running state
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
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("running",rest_val);
        }
        return YAPI.SUCCESS;
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
    public int setRunning(int newval)  throws YAPI_Exception
    {
        return set_running(newval);
    }

    /**
     * Resets the watchdog. When the watchdog is running, this function
     * must be called on a regular basis to prevent the watchdog to
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
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return TRIGGERDELAY_INVALID;
                }
            }
            res = _triggerDelay;
        }
        return res;
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
     * Changes the waiting delay before a reset is triggered by the watchdog,
     * in milliseconds. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the waiting delay before a reset is triggered by the watchdog,
     *         in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_triggerDelay(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("triggerDelay",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the waiting delay before a reset is triggered by the watchdog,
     * in milliseconds. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the waiting delay before a reset is triggered by the watchdog,
     *         in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
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
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return TRIGGERDURATION_INVALID;
                }
            }
            res = _triggerDuration;
        }
        return res;
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
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
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
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("triggerDuration",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the duration of resets caused by the watchdog, in milliseconds.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setTriggerDuration(long newval)  throws YAPI_Exception
    {
        return set_triggerDuration(newval);
    }

    /**
     * Returns the number of seconds spent since the last output power-up event.
     *
     * @return an integer corresponding to the number of seconds spent since the last output power-up event
     *
     * @throws YAPI_Exception on error
     */
    public int get_lastTrigger() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LASTTRIGGER_INVALID;
                }
            }
            res = _lastTrigger;
        }
        return res;
    }

    /**
     * Returns the number of seconds spent since the last output power-up event.
     *
     * @return an integer corresponding to the number of seconds spent since the last output power-up event
     *
     * @throws YAPI_Exception on error
     */
    public int getLastTrigger() throws YAPI_Exception
    {
        return get_lastTrigger();
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
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the watchdog, for instance
     *         WDOGDC01.watchdog1.
     *
     * @return a YWatchdog object allowing you to drive the watchdog.
     */
    public static YWatchdog FindWatchdog(String func)
    {
        YWatchdog obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YWatchdog) YFunction._FindFromCache("Watchdog", func);
            if (obj == null) {
                obj = new YWatchdog(func);
                YFunction._AddToCache("Watchdog", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a watchdog for a given identifier in a YAPI context.
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
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the watchdog, for instance
     *         WDOGDC01.watchdog1.
     *
     * @return a YWatchdog object allowing you to drive the watchdog.
     */
    public static YWatchdog FindWatchdogInContext(YAPIContext yctx,String func)
    {
        YWatchdog obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YWatchdog) YFunction._FindFromCacheInContext(yctx, "Watchdog", func);
            if (obj == null) {
                obj = new YWatchdog(yctx, func);
                YFunction._AddToCache("Watchdog", func, obj);
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
     * Switch the relay to the opposite state.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int toggle() throws YAPI_Exception
    {
        int sta;
        String fw;
        YModule mo;
        if (_firm == 0) {
            mo = get_module();
            fw = mo.get_firmwareRelease();
            if (fw.equals(YModule.FIRMWARERELEASE_INVALID)) {
                return STATE_INVALID;
            }
            _firm = YAPIContext._atoi(fw);
        }
        if (_firm < 34921) {
            sta = get_state();
            if (sta == STATE_INVALID) {
                return STATE_INVALID;
            }
            if (sta == STATE_B) {
                set_state(STATE_A);
            } else {
                set_state(STATE_B);
            }
            return YAPI.SUCCESS;
        } else {
            return _setAttr("state","X");
        }
    }

    /**
     * Continues the enumeration of watchdog started using yFirstWatchdog().
     * Caution: You can't make any assumption about the returned watchdog order.
     * If you want to find a specific a watchdog, use Watchdog.findWatchdog()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YWatchdog object, corresponding to
     *         a watchdog currently online, or a null pointer
     *         if there are no more watchdog to enumerate.
     */
    public YWatchdog nextWatchdog()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindWatchdogInContext(_yapi, next_hwid);
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
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Watchdog");
        if (next_hwid == null)  return null;
        return FindWatchdogInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of watchdog currently accessible.
     * Use the method YWatchdog.nextWatchdog() to iterate on
     * next watchdog.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YWatchdog object, corresponding to
     *         the first watchdog currently online, or a null pointer
     *         if there are none.
     */
    public static YWatchdog FirstWatchdogInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Watchdog");
        if (next_hwid == null)  return null;
        return FindWatchdogInContext(yctx, next_hwid);
    }

    //--- (end of YWatchdog implementation)
}

