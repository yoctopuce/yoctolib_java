/*********************************************************************
 *
 * $Id: YWatchdog.java 10471 2013-03-19 10:39:56Z seb $
 *
 * Implements yFindWatchdog(), the high-level API for Watchdog functions
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
 * YWatchdog Class: Watchdog function interface
 * 
 * The watchog function works like a relay and can cause a brief power cut
 * to an appliance after a preset delay to force this appliance to
 * reset. The Watchdog must be called from time to time to reset the
 * timer and prevent the appliance reset.
 * The watchdog can be driven direcly with <i>pulse</i> and <i>delayedpulse</i> methods to switch
 * off an appliance for a given duration.
 */
public class YWatchdog extends YFunction
{
    //--- (definitions)
    private YWatchdog.UpdateCallback _valueCallbackWatchdog;
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
    public static final YDelayedPulse DELAYEDPULSETIMER_INVALID = new YDelayedPulse("");
    //--- (end of definitions)

    /**
     * UdateCallback for Watchdog
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YWatchdog function, String functionValue);
    }



    //--- (YWatchdog implementation)

    /**
     * invalid delayedPulseTimer
     */
    /**
     * Returns the logical name of the watchdog.
     * 
     * @return a string corresponding to the logical name of the watchdog
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the watchdog.
     * 
     * @return a string corresponding to the logical name of the watchdog
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the watchdog. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the watchdog
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
     * Changes the logical name of the watchdog. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the watchdog
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the watchdog (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the watchdog (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the watchdog (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the watchdog (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the state of the watchdog (A for the idle position, B for the active position).
     * 
     * @return either YWatchdog.STATE_A or YWatchdog.STATE_B, according to the state of the watchdog (A
     * for the idle position, B for the active position)
     * 
     * @throws YAPI_Exception
     */
    public int get_state()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("state");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the state of the watchdog (A for the idle position, B for the active position).
     * 
     * @return either Y_STATE_A or Y_STATE_B, according to the state of the watchdog (A for the idle
     * position, B for the active position)
     * 
     * @throws YAPI_Exception
     */
    public int getState() throws YAPI_Exception

    { return get_state(); }

    /**
     * Changes the state of the watchdog (A for the idle position, B for the active position).
     * 
     * @param newval : either YWatchdog.STATE_A or YWatchdog.STATE_B, according to the state of the
     * watchdog (A for the idle position, B for the active position)
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
     * Changes the state of the watchdog (A for the idle position, B for the active position).
     * 
     * @param newval : either Y_STATE_A or Y_STATE_B, according to the state of the watchdog (A for the
     * idle position, B for the active position)
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setState( int newval)  throws YAPI_Exception

    { return set_state(newval); }

    /**
     * Returns the output state of the watchdog, when used as a simple switch (single throw).
     * 
     * @return either YWatchdog.OUTPUT_OFF or YWatchdog.OUTPUT_ON, according to the output state of the
     * watchdog, when used as a simple switch (single throw)
     * 
     * @throws YAPI_Exception
     */
    public int get_output()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("output");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the output state of the watchdog, when used as a simple switch (single throw).
     * 
     * @return either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the watchdog, when
     * used as a simple switch (single throw)
     * 
     * @throws YAPI_Exception
     */
    public int getOutput() throws YAPI_Exception

    { return get_output(); }

    /**
     * Changes the output state of the watchdog, when used as a simple switch (single throw).
     * 
     * @param newval : either YWatchdog.OUTPUT_OFF or YWatchdog.OUTPUT_ON, according to the output state
     * of the watchdog, when used as a simple switch (single throw)
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
     * Changes the output state of the watchdog, when used as a simple switch (single throw).
     * 
     * @param newval : either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the watchdog,
     * when used as a simple switch (single throw)
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setOutput( int newval)  throws YAPI_Exception

    { return set_output(newval); }

    /**
     * Returns the number of milliseconds remaining before the watchdog is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     * 
     * @return an integer corresponding to the number of milliseconds remaining before the watchdog is
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
     * Returns the number of milliseconds remaining before the watchdog is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     * 
     * @return an integer corresponding to the number of milliseconds remaining before the watchdog is
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
     * Returns the watchdog runing state at module power up.
     * 
     * @return either YWatchdog.AUTOSTART_OFF or YWatchdog.AUTOSTART_ON, according to the watchdog runing
     * state at module power up
     * 
     * @throws YAPI_Exception
     */
    public int get_autoStart()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("autoStart");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the watchdog runing state at module power up.
     * 
     * @return either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the watchdog runing state at module power up
     * 
     * @throws YAPI_Exception
     */
    public int getAutoStart() throws YAPI_Exception

    { return get_autoStart(); }

    /**
     * Changes the watchdog runningsttae at module power up. Remember to call the
     * saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param newval : either YWatchdog.AUTOSTART_OFF or YWatchdog.AUTOSTART_ON, according to the watchdog
     * runningsttae at module power up
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_autoStart( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("autoStart",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the watchdog runningsttae at module power up. Remember to call the
     * saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param newval : either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the watchdog runningsttae at
     * module power up
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setAutoStart( int newval)  throws YAPI_Exception

    { return set_autoStart(newval); }

    /**
     * Returns the watchdog running state.
     * 
     * @return either YWatchdog.RUNNING_OFF or YWatchdog.RUNNING_ON, according to the watchdog running state
     * 
     * @throws YAPI_Exception
     */
    public int get_running()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("running");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the watchdog running state.
     * 
     * @return either Y_RUNNING_OFF or Y_RUNNING_ON, according to the watchdog running state
     * 
     * @throws YAPI_Exception
     */
    public int getRunning() throws YAPI_Exception

    { return get_running(); }

    /**
     * Changes the running state of the watchdog.
     * 
     * @param newval : either YWatchdog.RUNNING_OFF or YWatchdog.RUNNING_ON, according to the running
     * state of the watchdog
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_running( int  newval)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int setRunning( int newval)  throws YAPI_Exception

    { return set_running(newval); }

    /**
     * Resets the watchdog. When the watchdog is running, this function
     * must be called on a regular basis to prevent the watchog to
     * trigger
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
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
     * @return an integer corresponding to  the waiting duration before a reset is automatically triggered
     * by the watchdog, in milliseconds
     * 
     * @throws YAPI_Exception
     */
    public long get_triggerDelay()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("triggerDelay");
        return Long.parseLong(json_val);
    }

    /**
     * Returns  the waiting duration before a reset is automatically triggered by the watchdog, in milliseconds.
     * 
     * @return an integer corresponding to  the waiting duration before a reset is automatically triggered
     * by the watchdog, in milliseconds
     * 
     * @throws YAPI_Exception
     */
    public long getTriggerDelay() throws YAPI_Exception

    { return get_triggerDelay(); }

    /**
     * Changes the waiting delay before a reset is triggered by the watchdog, in milliseconds.
     * 
     * @param newval : an integer corresponding to the waiting delay before a reset is triggered by the
     * watchdog, in milliseconds
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_triggerDelay( long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("triggerDelay",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the waiting delay before a reset is triggered by the watchdog, in milliseconds.
     * 
     * @param newval : an integer corresponding to the waiting delay before a reset is triggered by the
     * watchdog, in milliseconds
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setTriggerDelay( long newval)  throws YAPI_Exception

    { return set_triggerDelay(newval); }

    /**
     * Returns the duration of resets caused by the watchdog, in milliseconds.
     * 
     * @return an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     * 
     * @throws YAPI_Exception
     */
    public long get_triggerDuration()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("triggerDuration");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the duration of resets caused by the watchdog, in milliseconds.
     * 
     * @return an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     * 
     * @throws YAPI_Exception
     */
    public long getTriggerDuration() throws YAPI_Exception

    { return get_triggerDuration(); }

    /**
     * Changes the duration of resets caused by the watchdog, in milliseconds.
     * 
     * @param newval : an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_triggerDuration( long  newval)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int setTriggerDuration( long newval)  throws YAPI_Exception

    { return set_triggerDuration(newval); }

    /**
     * Continues the enumeration of watchdog started using yFirstWatchdog().
     * 
     * @return a pointer to a YWatchdog object, corresponding to
     *         a watchdog currently online, or a null pointer
     *         if there are no more watchdog to enumerate.
     */
    public  YWatchdog nextWatchdog()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindWatchdog(next_hwid);
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
    {   YFunction yfunc = YAPI.getFunction("Watchdog", func);
        if (yfunc != null) {
            return (YWatchdog) yfunc;
        }
        return new YWatchdog(func);
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
        String next_hwid = YAPI.getFirstHardwareId("Watchdog");
        if (next_hwid == null)  return null;
        return FindWatchdog(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YWatchdog(String func)
    {
        super("Watchdog", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackWatchdog != null) {
            _valueCallbackWatchdog.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackWatchdog!=null);
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
    public void registerValueCallback(YWatchdog.UpdateCallback callback)
    {
         _valueCallbackWatchdog =  callback;
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

    //--- (end of YWatchdog implementation)
};

