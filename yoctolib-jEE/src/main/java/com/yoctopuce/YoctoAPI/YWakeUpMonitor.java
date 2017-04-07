/*********************************************************************
 *
 * $Id: YWakeUpMonitor.java 27053 2017-04-04 16:01:11Z seb $
 *
 * Implements FindWakeUpMonitor(), the high-level API for WakeUpMonitor functions
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

//--- (YWakeUpMonitor return codes)
//--- (end of YWakeUpMonitor return codes)
//--- (YWakeUpMonitor class start)
/**
 * YWakeUpMonitor Class: WakeUpMonitor function interface
 *
 * The WakeUpMonitor function handles globally all wake-up sources, as well
 * as automated sleep mode.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YWakeUpMonitor extends YFunction
{
//--- (end of YWakeUpMonitor class start)
//--- (YWakeUpMonitor definitions)
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
    public static final int WAKEUPREASON_SCHEDULE1 = 4;
    public static final int WAKEUPREASON_SCHEDULE2 = 5;
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
    protected int _powerDuration = POWERDURATION_INVALID;
    protected int _sleepCountdown = SLEEPCOUNTDOWN_INVALID;
    protected long _nextWakeUp = NEXTWAKEUP_INVALID;
    protected int _wakeUpReason = WAKEUPREASON_INVALID;
    protected int _wakeUpState = WAKEUPSTATE_INVALID;
    protected long _rtcTime = RTCTIME_INVALID;
    public static final int _endOfTime = 2145960000;
    protected UpdateCallback _valueCallbackWakeUpMonitor = null;

    /**
     * Deprecated UpdateCallback for WakeUpMonitor
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YWakeUpMonitor function, String functionValue);
    }

    /**
     * TimedReportCallback for WakeUpMonitor
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YWakeUpMonitor  function, YMeasure measure);
    }
    //--- (end of YWakeUpMonitor definitions)


    /**
     *
     * @param func : functionid
     */
    protected YWakeUpMonitor(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "WakeUpMonitor";
        //--- (YWakeUpMonitor attributes initialization)
        //--- (end of YWakeUpMonitor attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YWakeUpMonitor(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YWakeUpMonitor implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("powerDuration")) {
            _powerDuration = json_val.getInt("powerDuration");
        }
        if (json_val.has("sleepCountdown")) {
            _sleepCountdown = json_val.getInt("sleepCountdown");
        }
        if (json_val.has("nextWakeUp")) {
            _nextWakeUp = json_val.getLong("nextWakeUp");
        }
        if (json_val.has("wakeUpReason")) {
            _wakeUpReason = json_val.getInt("wakeUpReason");
        }
        if (json_val.has("wakeUpState")) {
            _wakeUpState = json_val.getInt("wakeUpState");
        }
        if (json_val.has("rtcTime")) {
            _rtcTime = json_val.getLong("rtcTime");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the maximal wake up time (in seconds) before automatically going to sleep.
     *
     * @return an integer corresponding to the maximal wake up time (in seconds) before automatically going to sleep
     *
     * @throws YAPI_Exception on error
     */
    public int get_powerDuration() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return POWERDURATION_INVALID;
                }
            }
            res = _powerDuration;
        }
        return res;
    }

    /**
     * Returns the maximal wake up time (in seconds) before automatically going to sleep.
     *
     * @return an integer corresponding to the maximal wake up time (in seconds) before automatically going to sleep
     *
     * @throws YAPI_Exception on error
     */
    public int getPowerDuration() throws YAPI_Exception
    {
        return get_powerDuration();
    }

    /**
     * Changes the maximal wake up time (seconds) before automatically going to sleep.
     *
     *  @param newval : an integer corresponding to the maximal wake up time (seconds) before automatically
     * going to sleep
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_powerDuration(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("powerDuration",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximal wake up time (seconds) before automatically going to sleep.
     *
     *  @param newval : an integer corresponding to the maximal wake up time (seconds) before automatically
     * going to sleep
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPowerDuration(int newval)  throws YAPI_Exception
    {
        return set_powerDuration(newval);
    }

    /**
     * Returns the delay before the  next sleep period.
     *
     * @return an integer corresponding to the delay before the  next sleep period
     *
     * @throws YAPI_Exception on error
     */
    public int get_sleepCountdown() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SLEEPCOUNTDOWN_INVALID;
                }
            }
            res = _sleepCountdown;
        }
        return res;
    }

    /**
     * Returns the delay before the  next sleep period.
     *
     * @return an integer corresponding to the delay before the  next sleep period
     *
     * @throws YAPI_Exception on error
     */
    public int getSleepCountdown() throws YAPI_Exception
    {
        return get_sleepCountdown();
    }

    /**
     * Changes the delay before the next sleep period.
     *
     * @param newval : an integer corresponding to the delay before the next sleep period
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_sleepCountdown(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("sleepCountdown",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the delay before the next sleep period.
     *
     * @param newval : an integer corresponding to the delay before the next sleep period
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSleepCountdown(int newval)  throws YAPI_Exception
    {
        return set_sleepCountdown(newval);
    }

    /**
     * Returns the next scheduled wake up date/time (UNIX format).
     *
     * @return an integer corresponding to the next scheduled wake up date/time (UNIX format)
     *
     * @throws YAPI_Exception on error
     */
    public long get_nextWakeUp() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return NEXTWAKEUP_INVALID;
                }
            }
            res = _nextWakeUp;
        }
        return res;
    }

    /**
     * Returns the next scheduled wake up date/time (UNIX format).
     *
     * @return an integer corresponding to the next scheduled wake up date/time (UNIX format)
     *
     * @throws YAPI_Exception on error
     */
    public long getNextWakeUp() throws YAPI_Exception
    {
        return get_nextWakeUp();
    }

    /**
     * Changes the days of the week when a wake up must take place.
     *
     * @param newval : an integer corresponding to the days of the week when a wake up must take place
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_nextWakeUp(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("nextWakeUp",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the days of the week when a wake up must take place.
     *
     * @param newval : an integer corresponding to the days of the week when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setNextWakeUp(long newval)  throws YAPI_Exception
    {
        return set_nextWakeUp(newval);
    }

    /**
     * Returns the latest wake up reason.
     *
     *  @return a value among YWakeUpMonitor.WAKEUPREASON_USBPOWER, YWakeUpMonitor.WAKEUPREASON_EXTPOWER,
     *  YWakeUpMonitor.WAKEUPREASON_ENDOFSLEEP, YWakeUpMonitor.WAKEUPREASON_EXTSIG1,
     *  YWakeUpMonitor.WAKEUPREASON_SCHEDULE1 and YWakeUpMonitor.WAKEUPREASON_SCHEDULE2 corresponding to
     * the latest wake up reason
     *
     * @throws YAPI_Exception on error
     */
    public int get_wakeUpReason() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return WAKEUPREASON_INVALID;
                }
            }
            res = _wakeUpReason;
        }
        return res;
    }

    /**
     * Returns the latest wake up reason.
     *
     *  @return a value among Y_WAKEUPREASON_USBPOWER, Y_WAKEUPREASON_EXTPOWER, Y_WAKEUPREASON_ENDOFSLEEP,
     *  Y_WAKEUPREASON_EXTSIG1, Y_WAKEUPREASON_SCHEDULE1 and Y_WAKEUPREASON_SCHEDULE2 corresponding to the
     * latest wake up reason
     *
     * @throws YAPI_Exception on error
     */
    public int getWakeUpReason() throws YAPI_Exception
    {
        return get_wakeUpReason();
    }

    /**
     * Returns  the current state of the monitor.
     *
     *  @return either YWakeUpMonitor.WAKEUPSTATE_SLEEPING or YWakeUpMonitor.WAKEUPSTATE_AWAKE, according
     * to  the current state of the monitor
     *
     * @throws YAPI_Exception on error
     */
    public int get_wakeUpState() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return WAKEUPSTATE_INVALID;
                }
            }
            res = _wakeUpState;
        }
        return res;
    }

    /**
     * Returns  the current state of the monitor.
     *
     * @return either Y_WAKEUPSTATE_SLEEPING or Y_WAKEUPSTATE_AWAKE, according to  the current state of the monitor
     *
     * @throws YAPI_Exception on error
     */
    public int getWakeUpState() throws YAPI_Exception
    {
        return get_wakeUpState();
    }

    public int set_wakeUpState(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("wakeUpState",rest_val);
        }
        return YAPI.SUCCESS;
    }


    public long get_rtcTime() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return RTCTIME_INVALID;
                }
            }
            res = _rtcTime;
        }
        return res;
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
    {
        YWakeUpMonitor obj;
        synchronized (YAPI.class) {
            obj = (YWakeUpMonitor) YFunction._FindFromCache("WakeUpMonitor", func);
            if (obj == null) {
                obj = new YWakeUpMonitor(func);
                YFunction._AddToCache("WakeUpMonitor", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a monitor for a given identifier in a YAPI context.
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
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the monitor
     *
     * @return a YWakeUpMonitor object allowing you to drive the monitor.
     */
    public static YWakeUpMonitor FindWakeUpMonitorInContext(YAPIContext yctx,String func)
    {
        YWakeUpMonitor obj;
        synchronized (yctx) {
            obj = (YWakeUpMonitor) YFunction._FindFromCacheInContext(yctx, "WakeUpMonitor", func);
            if (obj == null) {
                obj = new YWakeUpMonitor(yctx, func);
                YFunction._AddToCache("WakeUpMonitor", func, obj);
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
        _valueCallbackWakeUpMonitor = callback;
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
        if (_valueCallbackWakeUpMonitor != null) {
            _valueCallbackWakeUpMonitor.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Forces a wake up.
     */
    public int wakeUp() throws YAPI_Exception
    {
        return set_wakeUpState(WAKEUPSTATE_AWAKE);
    }

    /**
     * Goes to sleep until the next wake up condition is met,  the
     * RTC time must have been set before calling this function.
     *
     * @param secBeforeSleep : number of seconds before going into sleep mode,
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int sleep(int secBeforeSleep) throws YAPI_Exception
    {
        int currTime;
        currTime = (int)(get_rtcTime());
        //noinspection DoubleNegation
        if (!(currTime != 0)) { throw new YAPI_Exception( YAPI.RTC_NOT_READY,  "RTC time not set");}
        set_nextWakeUp(_endOfTime);
        set_sleepCountdown(secBeforeSleep);
        return YAPI.SUCCESS;
    }

    /**
     * Goes to sleep for a specific duration or until the next wake up condition is met, the
     * RTC time must have been set before calling this function. The count down before sleep
     * can be canceled with resetSleepCountDown.
     *
     * @param secUntilWakeUp : number of seconds before next wake up
     * @param secBeforeSleep : number of seconds before going into sleep mode
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int sleepFor(int secUntilWakeUp,int secBeforeSleep) throws YAPI_Exception
    {
        int currTime;
        currTime = (int)(get_rtcTime());
        //noinspection DoubleNegation
        if (!(currTime != 0)) { throw new YAPI_Exception( YAPI.RTC_NOT_READY,  "RTC time not set");}
        set_nextWakeUp(currTime+secUntilWakeUp);
        set_sleepCountdown(secBeforeSleep);
        return YAPI.SUCCESS;
    }

    /**
     * Go to sleep until a specific date is reached or until the next wake up condition is met, the
     * RTC time must have been set before calling this function. The count down before sleep
     * can be canceled with resetSleepCountDown.
     *
     * @param wakeUpTime : wake-up datetime (UNIX format)
     * @param secBeforeSleep : number of seconds before going into sleep mode
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int sleepUntil(int wakeUpTime,int secBeforeSleep) throws YAPI_Exception
    {
        int currTime;
        currTime = (int)(get_rtcTime());
        //noinspection DoubleNegation
        if (!(currTime != 0)) { throw new YAPI_Exception( YAPI.RTC_NOT_READY,  "RTC time not set");}
        set_nextWakeUp(wakeUpTime);
        set_sleepCountdown(secBeforeSleep);
        return YAPI.SUCCESS;
    }

    /**
     * Resets the sleep countdown.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int resetSleepCountDown() throws YAPI_Exception
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
    public YWakeUpMonitor nextWakeUpMonitor()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindWakeUpMonitorInContext(_yapi, next_hwid);
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
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("WakeUpMonitor");
        if (next_hwid == null)  return null;
        return FindWakeUpMonitorInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of monitors currently accessible.
     * Use the method YWakeUpMonitor.nextWakeUpMonitor() to iterate on
     * next monitors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YWakeUpMonitor object, corresponding to
     *         the first monitor currently online, or a null pointer
     *         if there are none.
     */
    public static YWakeUpMonitor FirstWakeUpMonitorInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("WakeUpMonitor");
        if (next_hwid == null)  return null;
        return FindWakeUpMonitorInContext(yctx, next_hwid);
    }

    //--- (end of YWakeUpMonitor implementation)
}

