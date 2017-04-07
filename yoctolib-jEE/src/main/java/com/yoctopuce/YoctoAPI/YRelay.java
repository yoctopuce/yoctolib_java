/*********************************************************************
 *
 * $Id: YRelay.java 27118 2017-04-06 22:38:36Z seb $
 *
 * Implements FindRelay(), the high-level API for Relay functions
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
import java.util.Locale;

//--- (YRelay return codes)
//--- (end of YRelay return codes)
//--- (YRelay class start)
/**
 * YRelay Class: Relay function interface
 *
 * The Yoctopuce application programming interface allows you to switch the relay state.
 * This change is not persistent: the relay will automatically return to its idle position
 * whenever power is lost or if the module is restarted.
 * The library can also generate automatically short pulses of determined duration.
 * On devices with two output for each relay (double throw), the two outputs are named A and B,
 * with output A corresponding to the idle position (at power off) and the output B corresponding to the
 * active state. If you prefer the alternate default state, simply switch your cables on the board.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YRelay extends YFunction
{
//--- (end of YRelay class start)
//--- (YRelay definitions)
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
    public static final YDelayedPulse DELAYEDPULSETIMER_INVALID = null;
    protected int _state = STATE_INVALID;
    protected int _stateAtPowerOn = STATEATPOWERON_INVALID;
    protected long _maxTimeOnStateA = MAXTIMEONSTATEA_INVALID;
    protected long _maxTimeOnStateB = MAXTIMEONSTATEB_INVALID;
    protected int _output = OUTPUT_INVALID;
    protected long _pulseTimer = PULSETIMER_INVALID;
    protected YDelayedPulse _delayedPulseTimer = new YDelayedPulse();
    protected long _countdown = COUNTDOWN_INVALID;
    protected UpdateCallback _valueCallbackRelay = null;

    /**
     * Deprecated UpdateCallback for Relay
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YRelay function, String functionValue);
    }

    /**
     * TimedReportCallback for Relay
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YRelay  function, YMeasure measure);
    }
    //--- (end of YRelay definitions)


    /**
     *
     * @param func : functionid
     */
    protected YRelay(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Relay";
        //--- (YRelay attributes initialization)
        //--- (end of YRelay attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YRelay(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YRelay implementation)
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
                _delayedPulseTimer.moving = subjson.getInt("target");
            }
            if (subjson.has("ms")) {
                _delayedPulseTimer.moving = subjson.getInt("ms");
            }
        }
        if (json_val.has("countdown")) {
            _countdown = json_val.getLong("countdown");
        }
        super._parseAttr(json_val);
    }

    /**
     * invalid delayedPulseTimer
     */
    /**
     * Returns the state of the relays (A for the idle position, B for the active position).
     *
     *  @return either YRelay.STATE_A or YRelay.STATE_B, according to the state of the relays (A for the
     * idle position, B for the active position)
     *
     * @throws YAPI_Exception on error
     */
    public int get_state() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return STATE_INVALID;
                }
            }
            res = _state;
        }
        return res;
    }

    /**
     * Returns the state of the relays (A for the idle position, B for the active position).
     *
     *  @return either Y_STATE_A or Y_STATE_B, according to the state of the relays (A for the idle
     * position, B for the active position)
     *
     * @throws YAPI_Exception on error
     */
    public int getState() throws YAPI_Exception
    {
        return get_state();
    }

    /**
     * Changes the state of the relays (A for the idle position, B for the active position).
     *
     *  @param newval : either YRelay.STATE_A or YRelay.STATE_B, according to the state of the relays (A
     * for the idle position, B for the active position)
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
     * Changes the state of the relays (A for the idle position, B for the active position).
     *
     *  @param newval : either Y_STATE_A or Y_STATE_B, according to the state of the relays (A for the idle
     * position, B for the active position)
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
     *  Returns the state of the relays at device startup (A for the idle position, B for the active
     * position, UNCHANGED for no change).
     *
     *  @return a value among YRelay.STATEATPOWERON_UNCHANGED, YRelay.STATEATPOWERON_A and
     *  YRelay.STATEATPOWERON_B corresponding to the state of the relays at device startup (A for the idle
     * position, B for the active position, UNCHANGED for no change)
     *
     * @throws YAPI_Exception on error
     */
    public int get_stateAtPowerOn() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return STATEATPOWERON_INVALID;
                }
            }
            res = _stateAtPowerOn;
        }
        return res;
    }

    /**
     *  Returns the state of the relays at device startup (A for the idle position, B for the active
     * position, UNCHANGED for no change).
     *
     *  @return a value among Y_STATEATPOWERON_UNCHANGED, Y_STATEATPOWERON_A and Y_STATEATPOWERON_B
     *  corresponding to the state of the relays at device startup (A for the idle position, B for the
     * active position, UNCHANGED for no change)
     *
     * @throws YAPI_Exception on error
     */
    public int getStateAtPowerOn() throws YAPI_Exception
    {
        return get_stateAtPowerOn();
    }

    /**
     * Preset the state of the relays at device startup (A for the idle position,
     * B for the active position, UNCHANGED for no modification). Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     *  @param newval : a value among YRelay.STATEATPOWERON_UNCHANGED, YRelay.STATEATPOWERON_A and
     * YRelay.STATEATPOWERON_B
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
     * Preset the state of the relays at device startup (A for the idle position,
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
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MAXTIMEONSTATEA_INVALID;
                }
            }
            res = _maxTimeOnStateA;
        }
        return res;
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
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("maxTimeOnStateA",rest_val);
        }
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
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MAXTIMEONSTATEB_INVALID;
                }
            }
            res = _maxTimeOnStateB;
        }
        return res;
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
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("maxTimeOnStateB",rest_val);
        }
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
     * Returns the output state of the relays, when used as a simple switch (single throw).
     *
     *  @return either YRelay.OUTPUT_OFF or YRelay.OUTPUT_ON, according to the output state of the relays,
     * when used as a simple switch (single throw)
     *
     * @throws YAPI_Exception on error
     */
    public int get_output() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return OUTPUT_INVALID;
                }
            }
            res = _output;
        }
        return res;
    }

    /**
     * Returns the output state of the relays, when used as a simple switch (single throw).
     *
     *  @return either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the relays, when used
     * as a simple switch (single throw)
     *
     * @throws YAPI_Exception on error
     */
    public int getOutput() throws YAPI_Exception
    {
        return get_output();
    }

    /**
     * Changes the output state of the relays, when used as a simple switch (single throw).
     *
     *  @param newval : either YRelay.OUTPUT_OFF or YRelay.OUTPUT_ON, according to the output state of the
     * relays, when used as a simple switch (single throw)
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
     * Changes the output state of the relays, when used as a simple switch (single throw).
     *
     *  @param newval : either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the relays,
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
     * Returns the number of milliseconds remaining before the relays is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     *
     *  @return an integer corresponding to the number of milliseconds remaining before the relays is
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return PULSETIMER_INVALID;
                }
            }
            res = _pulseTimer;
        }
        return res;
    }

    /**
     * Returns the number of milliseconds remaining before the relays is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     *
     *  @return an integer corresponding to the number of milliseconds remaining before the relays is
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

    public YDelayedPulse get_delayedPulseTimer() throws YAPI_Exception
    {
        YDelayedPulse res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
     * Retrieves a relay for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the relay is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRelay.isOnline() to test if the relay is
     * indeed online at a given time. In case of ambiguity when looking for
     * a relay by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the relay
     *
     * @return a YRelay object allowing you to drive the relay.
     */
    public static YRelay FindRelay(String func)
    {
        YRelay obj;
        synchronized (YAPI.class) {
            obj = (YRelay) YFunction._FindFromCache("Relay", func);
            if (obj == null) {
                obj = new YRelay(func);
                YFunction._AddToCache("Relay", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a relay for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the relay is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRelay.isOnline() to test if the relay is
     * indeed online at a given time. In case of ambiguity when looking for
     * a relay by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the relay
     *
     * @return a YRelay object allowing you to drive the relay.
     */
    public static YRelay FindRelayInContext(YAPIContext yctx,String func)
    {
        YRelay obj;
        synchronized (yctx) {
            obj = (YRelay) YFunction._FindFromCacheInContext(yctx, "Relay", func);
            if (obj == null) {
                obj = new YRelay(yctx, func);
                YFunction._AddToCache("Relay", func, obj);
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
        _valueCallbackRelay = callback;
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
        if (_valueCallbackRelay != null) {
            _valueCallbackRelay.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of relays started using yFirstRelay().
     *
     * @return a pointer to a YRelay object, corresponding to
     *         a relay currently online, or a null pointer
     *         if there are no more relays to enumerate.
     */
    public YRelay nextRelay()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindRelayInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of relays currently accessible.
     * Use the method YRelay.nextRelay() to iterate on
     * next relays.
     *
     * @return a pointer to a YRelay object, corresponding to
     *         the first relay currently online, or a null pointer
     *         if there are none.
     */
    public static YRelay FirstRelay()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Relay");
        if (next_hwid == null)  return null;
        return FindRelayInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of relays currently accessible.
     * Use the method YRelay.nextRelay() to iterate on
     * next relays.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YRelay object, corresponding to
     *         the first relay currently online, or a null pointer
     *         if there are none.
     */
    public static YRelay FirstRelayInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Relay");
        if (next_hwid == null)  return null;
        return FindRelayInContext(yctx, next_hwid);
    }

    //--- (end of YRelay implementation)
}

