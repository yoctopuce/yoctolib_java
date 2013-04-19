/*********************************************************************
 *
 * $Id: YRelay.java 10471 2013-03-19 10:39:56Z seb $
 *
 * Implements yFindRelay(), the high-level API for Relay functions
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
public class YRelay extends YFunction
{
    //--- (definitions)
    private YRelay.UpdateCallback _valueCallbackRelay;
    public static class YDelayedPulse
    {
        public long target = YAPI.INVALID_LONG;
        public long ms = YAPI.INVALID_LONG;
        public long moving = YAPI.INVALID_LONG;
        public YDelayedPulse(String attr){}
    }

    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid state value
     */
  public static final int STATE_A = 0;
  public static final int STATE_B = 1;
  public static final int STATE_INVALID = -1;

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
    public static final YDelayedPulse DELAYEDPULSETIMER_INVALID = new YDelayedPulse("");
    //--- (end of definitions)

    /**
     * UdateCallback for Relay
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YRelay function, String functionValue);
    }



    //--- (YRelay implementation)

    /**
     * invalid delayedPulseTimer
     */
    /**
     * Returns the logical name of the relay.
     * 
     * @return a string corresponding to the logical name of the relay
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the relay.
     * 
     * @return a string corresponding to the logical name of the relay
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the relay. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the relay
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
     * Changes the logical name of the relay. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the relay
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the relay (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the relay (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the relay (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the relay (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the state of the relays (A for the idle position, B for the active position).
     * 
     * @return either YRelay.STATE_A or YRelay.STATE_B, according to the state of the relays (A for the
     * idle position, B for the active position)
     * 
     * @throws YAPI_Exception
     */
    public int get_state()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("state");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the state of the relays (A for the idle position, B for the active position).
     * 
     * @return either Y_STATE_A or Y_STATE_B, according to the state of the relays (A for the idle
     * position, B for the active position)
     * 
     * @throws YAPI_Exception
     */
    public int getState() throws YAPI_Exception

    { return get_state(); }

    /**
     * Changes the state of the relays (A for the idle position, B for the active position).
     * 
     * @param newval : either YRelay.STATE_A or YRelay.STATE_B, according to the state of the relays (A
     * for the idle position, B for the active position)
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_state( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("state",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the relays (A for the idle position, B for the active position).
     * 
     * @param newval : either Y_STATE_A or Y_STATE_B, according to the state of the relays (A for the idle
     * position, B for the active position)
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setState( int newval)  throws YAPI_Exception

    { return set_state(newval); }

    /**
     * Returns the output state of the relays, when used as a simple switch (single throw).
     * 
     * @return either YRelay.OUTPUT_OFF or YRelay.OUTPUT_ON, according to the output state of the relays,
     * when used as a simple switch (single throw)
     * 
     * @throws YAPI_Exception
     */
    public int get_output()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("output");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the output state of the relays, when used as a simple switch (single throw).
     * 
     * @return either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the relays, when used
     * as a simple switch (single throw)
     * 
     * @throws YAPI_Exception
     */
    public int getOutput() throws YAPI_Exception

    { return get_output(); }

    /**
     * Changes the output state of the relays, when used as a simple switch (single throw).
     * 
     * @param newval : either YRelay.OUTPUT_OFF or YRelay.OUTPUT_ON, according to the output state of the
     * relays, when used as a simple switch (single throw)
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_output( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("output",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the output state of the relays, when used as a simple switch (single throw).
     * 
     * @param newval : either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the relays,
     * when used as a simple switch (single throw)
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setOutput( int newval)  throws YAPI_Exception

    { return set_output(newval); }

    /**
     * Returns the number of milliseconds remaining before the relays is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     * 
     * @return an integer corresponding to the number of milliseconds remaining before the relays is
     * returned to idle position
     *         (state A), during a measured pulse generation
     * 
     * @throws YAPI_Exception
     */
    public long get_pulseTimer()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("pulseTimer");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the number of milliseconds remaining before the relays is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     * 
     * @return an integer corresponding to the number of milliseconds remaining before the relays is
     * returned to idle position
     *         (state A), during a measured pulse generation
     * 
     * @throws YAPI_Exception
     */
    public long getPulseTimer() throws YAPI_Exception

    { return get_pulseTimer(); }

    public int set_pulseTimer( long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("pulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPulseTimer( long newval)  throws YAPI_Exception

    { return set_pulseTimer(newval); }

    /**
     * Sets the relay to output B (active) for a specified duration, then brings it
     * automatically back to output A (idle state).
     * 
     * @param ms_duration : pulse duration, in millisecondes
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int pulse(int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(ms_duration);
        _setAttr("pulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    public YDelayedPulse get_delayedPulseTimer()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("delayedPulseTimer");
        return new YDelayedPulse(json_val);
    }

    public YDelayedPulse getDelayedPulseTimer() throws YAPI_Exception

    { return get_delayedPulseTimer(); }

    public int set_delayedPulseTimer( YDelayedPulse  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("delayedPulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    public int setDelayedPulseTimer( YDelayedPulse newval)  throws YAPI_Exception

    { return set_delayedPulseTimer(newval); }

    /**
     * Schedules a pulse.
     * 
     * @param ms_delay : waiting time before the pulse, in millisecondes
     * @param ms_duration : pulse duration, in millisecondes
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public long get_countdown()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("countdown");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the number of milliseconds remaining before a pulse (delayedPulse() call)
     * When there is no scheduled pulse, returns zero.
     * 
     * @return an integer corresponding to the number of milliseconds remaining before a pulse (delayedPulse() call)
     *         When there is no scheduled pulse, returns zero
     * 
     * @throws YAPI_Exception
     */
    public long getCountdown() throws YAPI_Exception

    { return get_countdown(); }

    /**
     * Continues the enumeration of relays started using yFirstRelay().
     * 
     * @return a pointer to a YRelay object, corresponding to
     *         a relay currently online, or a null pointer
     *         if there are no more relays to enumerate.
     */
    public  YRelay nextRelay()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindRelay(next_hwid);
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
    {   YFunction yfunc = YAPI.getFunction("Relay", func);
        if (yfunc != null) {
            return (YRelay) yfunc;
        }
        return new YRelay(func);
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
        String next_hwid = YAPI.getFirstHardwareId("Relay");
        if (next_hwid == null)  return null;
        return FindRelay(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YRelay(String func)
    {
        super("Relay", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackRelay != null) {
            _valueCallbackRelay.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackRelay!=null);
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
    public void registerValueCallback(YRelay.UpdateCallback callback)
    {
         _valueCallbackRelay =  callback;
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

    //--- (end of YRelay implementation)
};

