/*********************************************************************
 *
 * $Id: YWakeUpSchedule.java 27108 2017-04-06 22:18:22Z seb $
 *
 * Implements FindWakeUpSchedule(), the high-level API for WakeUpSchedule functions
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

//--- (YWakeUpSchedule return codes)
//--- (end of YWakeUpSchedule return codes)
//--- (YWakeUpSchedule class start)
/**
 * YWakeUpSchedule Class: WakeUpSchedule function interface
 *
 * The WakeUpSchedule function implements a wake up condition. The wake up time is
 * specified as a set of months and/or days and/or hours and/or minutes when the
 * wake up should happen.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YWakeUpSchedule extends YFunction
{
//--- (end of YWakeUpSchedule class start)
//--- (YWakeUpSchedule definitions)
    /**
     * invalid minutesA value
     */
    public static final int MINUTESA_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid minutesB value
     */
    public static final int MINUTESB_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid hours value
     */
    public static final int HOURS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid weekDays value
     */
    public static final int WEEKDAYS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid monthDays value
     */
    public static final int MONTHDAYS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid months value
     */
    public static final int MONTHS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid nextOccurence value
     */
    public static final long NEXTOCCURENCE_INVALID = YAPI.INVALID_LONG;
    protected int _minutesA = MINUTESA_INVALID;
    protected int _minutesB = MINUTESB_INVALID;
    protected int _hours = HOURS_INVALID;
    protected int _weekDays = WEEKDAYS_INVALID;
    protected int _monthDays = MONTHDAYS_INVALID;
    protected int _months = MONTHS_INVALID;
    protected long _nextOccurence = NEXTOCCURENCE_INVALID;
    protected UpdateCallback _valueCallbackWakeUpSchedule = null;

    /**
     * Deprecated UpdateCallback for WakeUpSchedule
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YWakeUpSchedule function, String functionValue);
    }

    /**
     * TimedReportCallback for WakeUpSchedule
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YWakeUpSchedule  function, YMeasure measure);
    }
    //--- (end of YWakeUpSchedule definitions)


    /**
     *
     * @param func : functionid
     */
    protected YWakeUpSchedule(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "WakeUpSchedule";
        //--- (YWakeUpSchedule attributes initialization)
        //--- (end of YWakeUpSchedule attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YWakeUpSchedule(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YWakeUpSchedule implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("minutesA")) {
            _minutesA = json_val.getInt("minutesA");
        }
        if (json_val.has("minutesB")) {
            _minutesB = json_val.getInt("minutesB");
        }
        if (json_val.has("hours")) {
            _hours = json_val.getInt("hours");
        }
        if (json_val.has("weekDays")) {
            _weekDays = json_val.getInt("weekDays");
        }
        if (json_val.has("monthDays")) {
            _monthDays = json_val.getInt("monthDays");
        }
        if (json_val.has("months")) {
            _months = json_val.getInt("months");
        }
        if (json_val.has("nextOccurence")) {
            _nextOccurence = json_val.getLong("nextOccurence");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the minutes in the 00-29 interval of each hour scheduled for wake up.
     *
     * @return an integer corresponding to the minutes in the 00-29 interval of each hour scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int get_minutesA() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MINUTESA_INVALID;
                }
            }
            res = _minutesA;
        }
        return res;
    }

    /**
     * Returns the minutes in the 00-29 interval of each hour scheduled for wake up.
     *
     * @return an integer corresponding to the minutes in the 00-29 interval of each hour scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int getMinutesA() throws YAPI_Exception
    {
        return get_minutesA();
    }

    /**
     * Changes the minutes in the 00-29 interval when a wake up must take place.
     *
     * @param newval : an integer corresponding to the minutes in the 00-29 interval when a wake up must take place
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_minutesA(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("minutesA",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the minutes in the 00-29 interval when a wake up must take place.
     *
     * @param newval : an integer corresponding to the minutes in the 00-29 interval when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMinutesA(int newval)  throws YAPI_Exception
    {
        return set_minutesA(newval);
    }

    /**
     * Returns the minutes in the 30-59 intervalof each hour scheduled for wake up.
     *
     * @return an integer corresponding to the minutes in the 30-59 intervalof each hour scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int get_minutesB() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MINUTESB_INVALID;
                }
            }
            res = _minutesB;
        }
        return res;
    }

    /**
     * Returns the minutes in the 30-59 intervalof each hour scheduled for wake up.
     *
     * @return an integer corresponding to the minutes in the 30-59 intervalof each hour scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int getMinutesB() throws YAPI_Exception
    {
        return get_minutesB();
    }

    /**
     * Changes the minutes in the 30-59 interval when a wake up must take place.
     *
     * @param newval : an integer corresponding to the minutes in the 30-59 interval when a wake up must take place
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_minutesB(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("minutesB",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the minutes in the 30-59 interval when a wake up must take place.
     *
     * @param newval : an integer corresponding to the minutes in the 30-59 interval when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMinutesB(int newval)  throws YAPI_Exception
    {
        return set_minutesB(newval);
    }

    /**
     * Returns the hours scheduled for wake up.
     *
     * @return an integer corresponding to the hours scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int get_hours() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return HOURS_INVALID;
                }
            }
            res = _hours;
        }
        return res;
    }

    /**
     * Returns the hours scheduled for wake up.
     *
     * @return an integer corresponding to the hours scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int getHours() throws YAPI_Exception
    {
        return get_hours();
    }

    /**
     * Changes the hours when a wake up must take place.
     *
     * @param newval : an integer corresponding to the hours when a wake up must take place
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_hours(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("hours",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the hours when a wake up must take place.
     *
     * @param newval : an integer corresponding to the hours when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setHours(int newval)  throws YAPI_Exception
    {
        return set_hours(newval);
    }

    /**
     * Returns the days of the week scheduled for wake up.
     *
     * @return an integer corresponding to the days of the week scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int get_weekDays() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return WEEKDAYS_INVALID;
                }
            }
            res = _weekDays;
        }
        return res;
    }

    /**
     * Returns the days of the week scheduled for wake up.
     *
     * @return an integer corresponding to the days of the week scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int getWeekDays() throws YAPI_Exception
    {
        return get_weekDays();
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
    public int set_weekDays(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("weekDays",rest_val);
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
    public int setWeekDays(int newval)  throws YAPI_Exception
    {
        return set_weekDays(newval);
    }

    /**
     * Returns the days of the month scheduled for wake up.
     *
     * @return an integer corresponding to the days of the month scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int get_monthDays() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MONTHDAYS_INVALID;
                }
            }
            res = _monthDays;
        }
        return res;
    }

    /**
     * Returns the days of the month scheduled for wake up.
     *
     * @return an integer corresponding to the days of the month scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int getMonthDays() throws YAPI_Exception
    {
        return get_monthDays();
    }

    /**
     * Changes the days of the month when a wake up must take place.
     *
     * @param newval : an integer corresponding to the days of the month when a wake up must take place
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_monthDays(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("monthDays",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the days of the month when a wake up must take place.
     *
     * @param newval : an integer corresponding to the days of the month when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMonthDays(int newval)  throws YAPI_Exception
    {
        return set_monthDays(newval);
    }

    /**
     * Returns the months scheduled for wake up.
     *
     * @return an integer corresponding to the months scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int get_months() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MONTHS_INVALID;
                }
            }
            res = _months;
        }
        return res;
    }

    /**
     * Returns the months scheduled for wake up.
     *
     * @return an integer corresponding to the months scheduled for wake up
     *
     * @throws YAPI_Exception on error
     */
    public int getMonths() throws YAPI_Exception
    {
        return get_months();
    }

    /**
     * Changes the months when a wake up must take place.
     *
     * @param newval : an integer corresponding to the months when a wake up must take place
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_months(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("months",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the months when a wake up must take place.
     *
     * @param newval : an integer corresponding to the months when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMonths(int newval)  throws YAPI_Exception
    {
        return set_months(newval);
    }

    /**
     * Returns the date/time (seconds) of the next wake up occurence.
     *
     * @return an integer corresponding to the date/time (seconds) of the next wake up occurence
     *
     * @throws YAPI_Exception on error
     */
    public long get_nextOccurence() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return NEXTOCCURENCE_INVALID;
                }
            }
            res = _nextOccurence;
        }
        return res;
    }

    /**
     * Returns the date/time (seconds) of the next wake up occurence.
     *
     * @return an integer corresponding to the date/time (seconds) of the next wake up occurence
     *
     * @throws YAPI_Exception on error
     */
    public long getNextOccurence() throws YAPI_Exception
    {
        return get_nextOccurence();
    }

    /**
     * Retrieves a wake up schedule for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the wake up schedule is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YWakeUpSchedule.isOnline() to test if the wake up schedule is
     * indeed online at a given time. In case of ambiguity when looking for
     * a wake up schedule by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the wake up schedule
     *
     * @return a YWakeUpSchedule object allowing you to drive the wake up schedule.
     */
    public static YWakeUpSchedule FindWakeUpSchedule(String func)
    {
        YWakeUpSchedule obj;
        synchronized (YAPI.class) {
            obj = (YWakeUpSchedule) YFunction._FindFromCache("WakeUpSchedule", func);
            if (obj == null) {
                obj = new YWakeUpSchedule(func);
                YFunction._AddToCache("WakeUpSchedule", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a wake up schedule for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the wake up schedule is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YWakeUpSchedule.isOnline() to test if the wake up schedule is
     * indeed online at a given time. In case of ambiguity when looking for
     * a wake up schedule by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the wake up schedule
     *
     * @return a YWakeUpSchedule object allowing you to drive the wake up schedule.
     */
    public static YWakeUpSchedule FindWakeUpScheduleInContext(YAPIContext yctx,String func)
    {
        YWakeUpSchedule obj;
        synchronized (yctx) {
            obj = (YWakeUpSchedule) YFunction._FindFromCacheInContext(yctx, "WakeUpSchedule", func);
            if (obj == null) {
                obj = new YWakeUpSchedule(yctx, func);
                YFunction._AddToCache("WakeUpSchedule", func, obj);
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
        _valueCallbackWakeUpSchedule = callback;
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
        if (_valueCallbackWakeUpSchedule != null) {
            _valueCallbackWakeUpSchedule.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Returns all the minutes of each hour that are scheduled for wake up.
     */
    public long get_minutes() throws YAPI_Exception
    {
        long res;
        
        res = get_minutesB();
        res = ((res) << (30));
        res = res + get_minutesA();
        return res;
    }

    /**
     * Changes all the minutes where a wake up must take place.
     *
     * @param bitmap : Minutes 00-59 of each hour scheduled for wake up.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_minutes(long bitmap) throws YAPI_Exception
    {
        set_minutesA((int)(((bitmap) & (0x3fffffff))));
        bitmap = ((bitmap) >> (30));
        return set_minutesB((int)(((bitmap) & (0x3fffffff))));
    }

    /**
     * Continues the enumeration of wake up schedules started using yFirstWakeUpSchedule().
     *
     * @return a pointer to a YWakeUpSchedule object, corresponding to
     *         a wake up schedule currently online, or a null pointer
     *         if there are no more wake up schedules to enumerate.
     */
    public YWakeUpSchedule nextWakeUpSchedule()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindWakeUpScheduleInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of wake up schedules currently accessible.
     * Use the method YWakeUpSchedule.nextWakeUpSchedule() to iterate on
     * next wake up schedules.
     *
     * @return a pointer to a YWakeUpSchedule object, corresponding to
     *         the first wake up schedule currently online, or a null pointer
     *         if there are none.
     */
    public static YWakeUpSchedule FirstWakeUpSchedule()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("WakeUpSchedule");
        if (next_hwid == null)  return null;
        return FindWakeUpScheduleInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of wake up schedules currently accessible.
     * Use the method YWakeUpSchedule.nextWakeUpSchedule() to iterate on
     * next wake up schedules.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YWakeUpSchedule object, corresponding to
     *         the first wake up schedule currently online, or a null pointer
     *         if there are none.
     */
    public static YWakeUpSchedule FirstWakeUpScheduleInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("WakeUpSchedule");
        if (next_hwid == null)  return null;
        return FindWakeUpScheduleInContext(yctx, next_hwid);
    }

    //--- (end of YWakeUpSchedule implementation)
}

