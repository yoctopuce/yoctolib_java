/*********************************************************************
 *
 * $Id: YWakeUpMonitor.java 12324 2013-08-13 15:10:31Z mvuilleu $
 *
 * Implements yFindWakeUpMonitor(), the high-level API for WakeUpMonitor functions
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

//--- (globals)
//--- (end of globals)
/**
 * YWakeUpMonitor Class: WakeUpMonitor function interface
 * 
 * 
 */
public class YWakeUpMonitor extends YFunction
{
    //--- (definitions)
    private YWakeUpMonitor.UpdateCallback _valueCallbackWakeUpMonitor;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid powerDuration value
     */
    public static final int POWERDURATION_INVALID = YAPI.INVALID_INT;
    /**
     * invalid sleepCountdown value
     */
    public static final int SLEEPCOUNTDOWN_INVALID = YAPI.INVALID_INT;
    /**
     * invalid nextWakeUp value
     */
    public static final long NEXTWAKEUP_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid wakeUpReason value
     */
    public static final int WAKEUPREASON_USBPOWER = 0;
    public static final int WAKEUPREASON_EXTPOWER = 1;
    public static final int WAKEUPREASON_ENDOFSLEEP = 2;
    public static final int WAKEUPREASON_EXTSIG1 = 3;
    public static final int WAKEUPREASON_EXTSIG2 = 4;
    public static final int WAKEUPREASON_EXTSIG3 = 5;
    public static final int WAKEUPREASON_EXTSIG4 = 6;
    public static final int WAKEUPREASON_SCHEDULE1 = 7;
    public static final int WAKEUPREASON_SCHEDULE2 = 8;
    public static final int WAKEUPREASON_SCHEDULE3 = 9;
    public static final int WAKEUPREASON_SCHEDULE4 = 10;
    public static final int WAKEUPREASON_SCHEDULE5 = 11;
    public static final int WAKEUPREASON_SCHEDULE6 = 12;
    public static final int WAKEUPREASON_INVALID = -1;

    /**
     * invalid wakeUpState value
     */
    public static final int WAKEUPSTATE_SLEEPING = 0;
    public static final int WAKEUPSTATE_AWAKE = 1;
    public static final int WAKEUPSTATE_INVALID = -1;

    /**
     * invalid rtcTime value
     */
    public static final long RTCTIME_INVALID = YAPI.INVALID_LONG;
    public static final int _endOfTime = 2145960000;
    //--- (end of definitions)

    /**
     * UdateCallback for WakeUpMonitor
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YWakeUpMonitor function, String functionValue);
    }



    //--- (YWakeUpMonitor implementation)

    /**
     * Returns the logical name of the monitor.
     * 
     * @return a string corresponding to the logical name of the monitor
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the monitor.
     * 
     * @return a string corresponding to the logical name of the monitor
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the monitor. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the monitor
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
     * Changes the logical name of the monitor. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the monitor
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the monitor (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the monitor (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the monitor (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the monitor (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the maximal wake up time (seconds) before going to sleep automatically.
     * 
     * @return an integer corresponding to the maximal wake up time (seconds) before going to sleep automatically
     * 
     * @throws YAPI_Exception
     */
    public int get_powerDuration()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("powerDuration");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the maximal wake up time (seconds) before going to sleep automatically.
     * 
     * @return an integer corresponding to the maximal wake up time (seconds) before going to sleep automatically
     * 
     * @throws YAPI_Exception
     */
    public int getPowerDuration() throws YAPI_Exception

    { return get_powerDuration(); }

    /**
     * Changes the maximal wake up time (seconds) before going to sleep automatically.
     * 
     * @param newval : an integer corresponding to the maximal wake up time (seconds) before going to
     * sleep automatically
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_powerDuration( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("powerDuration",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximal wake up time (seconds) before going to sleep automatically.
     * 
     * @param newval : an integer corresponding to the maximal wake up time (seconds) before going to
     * sleep automatically
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setPowerDuration( int newval)  throws YAPI_Exception

    { return set_powerDuration(newval); }

    /**
     * Returns the delay before next sleep.
     * 
     * @return an integer corresponding to the delay before next sleep
     * 
     * @throws YAPI_Exception
     */
    public int get_sleepCountdown()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("sleepCountdown");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the delay before next sleep.
     * 
     * @return an integer corresponding to the delay before next sleep
     * 
     * @throws YAPI_Exception
     */
    public int getSleepCountdown() throws YAPI_Exception

    { return get_sleepCountdown(); }

    /**
     * Changes the delay before next sleep.
     * 
     * @param newval : an integer corresponding to the delay before next sleep
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_sleepCountdown( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("sleepCountdown",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the delay before next sleep.
     * 
     * @param newval : an integer corresponding to the delay before next sleep
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setSleepCountdown( int newval)  throws YAPI_Exception

    { return set_sleepCountdown(newval); }

    /**
     * Returns the next scheduled wake-up date/time (UNIX format)
     * 
     * @return an integer corresponding to the next scheduled wake-up date/time (UNIX format)
     * 
     * @throws YAPI_Exception
     */
    public long get_nextWakeUp()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("nextWakeUp");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the next scheduled wake-up date/time (UNIX format)
     * 
     * @return an integer corresponding to the next scheduled wake-up date/time (UNIX format)
     * 
     * @throws YAPI_Exception
     */
    public long getNextWakeUp() throws YAPI_Exception

    { return get_nextWakeUp(); }

    /**
     * Changes the days of the week where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the days of the week where a wake up must take place
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_nextWakeUp( long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("nextWakeUp",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the days of the week where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the days of the week where a wake up must take place
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setNextWakeUp( long newval)  throws YAPI_Exception

    { return set_nextWakeUp(newval); }

    /**
     * Return the last wake up reason.
     * 
     * @return a value among YWakeUpMonitor.WAKEUPREASON_USBPOWER, YWakeUpMonitor.WAKEUPREASON_EXTPOWER,
     * YWakeUpMonitor.WAKEUPREASON_ENDOFSLEEP, YWakeUpMonitor.WAKEUPREASON_EXTSIG1,
     * YWakeUpMonitor.WAKEUPREASON_EXTSIG2, YWakeUpMonitor.WAKEUPREASON_EXTSIG3,
     * YWakeUpMonitor.WAKEUPREASON_EXTSIG4, YWakeUpMonitor.WAKEUPREASON_SCHEDULE1,
     * YWakeUpMonitor.WAKEUPREASON_SCHEDULE2, YWakeUpMonitor.WAKEUPREASON_SCHEDULE3,
     * YWakeUpMonitor.WAKEUPREASON_SCHEDULE4, YWakeUpMonitor.WAKEUPREASON_SCHEDULE5 and
     * YWakeUpMonitor.WAKEUPREASON_SCHEDULE6
     * 
     * @throws YAPI_Exception
     */
    public int get_wakeUpReason()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("wakeUpReason");
        return Integer.parseInt(json_val);
    }

    /**
     * Return the last wake up reason.
     * 
     * @return a value among Y_WAKEUPREASON_USBPOWER, Y_WAKEUPREASON_EXTPOWER, Y_WAKEUPREASON_ENDOFSLEEP,
     * Y_WAKEUPREASON_EXTSIG1, Y_WAKEUPREASON_EXTSIG2, Y_WAKEUPREASON_EXTSIG3, Y_WAKEUPREASON_EXTSIG4,
     * Y_WAKEUPREASON_SCHEDULE1, Y_WAKEUPREASON_SCHEDULE2, Y_WAKEUPREASON_SCHEDULE3,
     * Y_WAKEUPREASON_SCHEDULE4, Y_WAKEUPREASON_SCHEDULE5 and Y_WAKEUPREASON_SCHEDULE6
     * 
     * @throws YAPI_Exception
     */
    public int getWakeUpReason() throws YAPI_Exception

    { return get_wakeUpReason(); }

    /**
     * Returns  the current state of the monitor
     * 
     * @return either YWakeUpMonitor.WAKEUPSTATE_SLEEPING or YWakeUpMonitor.WAKEUPSTATE_AWAKE, according
     * to  the current state of the monitor
     * 
     * @throws YAPI_Exception
     */
    public int get_wakeUpState()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("wakeUpState");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns  the current state of the monitor
     * 
     * @return either Y_WAKEUPSTATE_SLEEPING or Y_WAKEUPSTATE_AWAKE, according to  the current state of the monitor
     * 
     * @throws YAPI_Exception
     */
    public int getWakeUpState() throws YAPI_Exception

    { return get_wakeUpState(); }

    public int set_wakeUpState( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("wakeUpState",rest_val);
        return YAPI.SUCCESS;
    }

    public int setWakeUpState( int newval)  throws YAPI_Exception

    { return set_wakeUpState(newval); }

    public long get_rtcTime()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("rtcTime");
        return Long.parseLong(json_val);
    }

    public long getRtcTime() throws YAPI_Exception

    { return get_rtcTime(); }

    /**
     * Forces a wakeup.
     */
    public int wakeUp()  throws YAPI_Exception
    {
        return set_wakeUpState(WAKEUPSTATE_AWAKE);
        
    }

    /**
     * Go to sleep until the next wakeup condition is met,  the
     * RTC time must have been set before calling this function.
     * 
     * @param secBeforeSleep : number of seconds before going into sleep mode,
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int sleep(int secBeforeSleep)  throws YAPI_Exception
    {
        int currTime;
        currTime = (int)(get_rtcTime());
        if (!(currTime != 0)) { throw new YAPI_Exception( YAPI.RTC_NOT_READY,  "RTC time not set");};
        set_nextWakeUp(_endOfTime);
        set_sleepCountdown(secBeforeSleep);
        return YAPI.SUCCESS; 
        
    }

    /**
     * Go to sleep for a specific time or until the next wakeup condition is met, the
     * RTC time must have been set before calling this function. The count down before sleep
     * can be canceled with resetSleepCountDown.
     * 
     * @param secUntilWakeUp : sleep duration, in secondes
     * @param secBeforeSleep : number of seconds before going into sleep mode
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int sleepFor(int secUntilWakeUp,int secBeforeSleep)  throws YAPI_Exception
    {
        int currTime;
        currTime = (int)(get_rtcTime());
        if (!(currTime != 0)) { throw new YAPI_Exception( YAPI.RTC_NOT_READY,  "RTC time not set");};
        set_nextWakeUp(currTime+secUntilWakeUp);
        set_sleepCountdown(secBeforeSleep);
        return YAPI.SUCCESS; 
        
    }

    /**
     * Go to sleep until a specific date is reached or until the next wakeup condition is met, the
     * RTC time must have been set before calling this function. The count down before sleep
     * can be canceled with resetSleepCountDown.
     * 
     * @param wakeUpTime : wake-up datetime (UNIX format)
     * @param secBeforeSleep : number of seconds before going into sleep mode
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int sleepUntil(int wakeUpTime,int secBeforeSleep)  throws YAPI_Exception
    {
        int currTime;
        currTime = (int)(get_rtcTime());
        if (!(currTime != 0)) { throw new YAPI_Exception( YAPI.RTC_NOT_READY,  "RTC time not set");};
        set_nextWakeUp(wakeUpTime);
        set_sleepCountdown(secBeforeSleep);
        return YAPI.SUCCESS; 
        
    }

    /**
     * Reset the sleep countdown.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception
     */
    public int resetSleepCountDown()  throws YAPI_Exception
    {
        set_sleepCountdown(0);
        set_nextWakeUp(0);
        return YAPI.SUCCESS; 
        
    }

    /**
     * Continues the enumeration of monitors started using yFirstWakeUpMonitor().
     * 
     * @return a pointer to a YWakeUpMonitor object, corresponding to
     *         a monitor currently online, or a null pointer
     *         if there are no more monitors to enumerate.
     */
    public  YWakeUpMonitor nextWakeUpMonitor()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindWakeUpMonitor(next_hwid);
    }

    /**
     * Retrieves a monitor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the monitor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YWakeUpMonitor.isOnline() to test if the monitor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a monitor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the monitor
     * 
     * @return a YWakeUpMonitor object allowing you to drive the monitor.
     */
    public static YWakeUpMonitor FindWakeUpMonitor(String func)
    {   YFunction yfunc = YAPI.getFunction("WakeUpMonitor", func);
        if (yfunc != null) {
            return (YWakeUpMonitor) yfunc;
        }
        return new YWakeUpMonitor(func);
    }

    /**
     * Starts the enumeration of monitors currently accessible.
     * Use the method YWakeUpMonitor.nextWakeUpMonitor() to iterate on
     * next monitors.
     * 
     * @return a pointer to a YWakeUpMonitor object, corresponding to
     *         the first monitor currently online, or a null pointer
     *         if there are none.
     */
    public static YWakeUpMonitor FirstWakeUpMonitor()
    {
        String next_hwid = YAPI.getFirstHardwareId("WakeUpMonitor");
        if (next_hwid == null)  return null;
        return FindWakeUpMonitor(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YWakeUpMonitor(String func)
    {
        super("WakeUpMonitor", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackWakeUpMonitor != null) {
            _valueCallbackWakeUpMonitor.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackWakeUpMonitor!=null);
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
    public void registerValueCallback(YWakeUpMonitor.UpdateCallback callback)
    {
         _valueCallbackWakeUpMonitor =  callback;
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

    //--- (end of YWakeUpMonitor implementation)
};

