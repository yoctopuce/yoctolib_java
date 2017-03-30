/*********************************************************************
 *
 * $Id: YRealTimeClock.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindRealTimeClock(), the high-level API for RealTimeClock functions
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

//--- (YRealTimeClock return codes)
//--- (end of YRealTimeClock return codes)
//--- (YRealTimeClock class start)
/**
 * YRealTimeClock Class: Real Time Clock function interface
 *
 * The RealTimeClock function maintains and provides current date and time, even accross power cut
 * lasting several days. It is the base for automated wake-up functions provided by the WakeUpScheduler.
 * The current time may represent a local time as well as an UTC time, but no automatic time change
 * will occur to account for daylight saving time.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YRealTimeClock extends YFunction
{
//--- (end of YRealTimeClock class start)
//--- (YRealTimeClock definitions)
    /**
     * invalid unixTime value
     */
    public static final long UNIXTIME_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid dateTime value
     */
    public static final String DATETIME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid utcOffset value
     */
    public static final int UTCOFFSET_INVALID = YAPI.INVALID_INT;
    /**
     * invalid timeSet value
     */
    public static final int TIMESET_FALSE = 0;
    public static final int TIMESET_TRUE = 1;
    public static final int TIMESET_INVALID = -1;
    protected long _unixTime = UNIXTIME_INVALID;
    protected String _dateTime = DATETIME_INVALID;
    protected int _utcOffset = UTCOFFSET_INVALID;
    protected int _timeSet = TIMESET_INVALID;
    protected UpdateCallback _valueCallbackRealTimeClock = null;

    /**
     * Deprecated UpdateCallback for RealTimeClock
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YRealTimeClock function, String functionValue);
    }

    /**
     * TimedReportCallback for RealTimeClock
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YRealTimeClock  function, YMeasure measure);
    }
    //--- (end of YRealTimeClock definitions)


    /**
     *
     * @param func : functionid
     */
    protected YRealTimeClock(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "RealTimeClock";
        //--- (YRealTimeClock attributes initialization)
        //--- (end of YRealTimeClock attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YRealTimeClock(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YRealTimeClock implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("unixTime")) {
            _unixTime = json_val.getLong("unixTime");
        }
        if (json_val.has("dateTime")) {
            _dateTime = json_val.getString("dateTime");
        }
        if (json_val.has("utcOffset")) {
            _utcOffset = json_val.getInt("utcOffset");
        }
        if (json_val.has("timeSet")) {
            _timeSet = json_val.getInt("timeSet") > 0 ? 1 : 0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current time in Unix format (number of elapsed seconds since Jan 1st, 1970).
     *
     *  @return an integer corresponding to the current time in Unix format (number of elapsed seconds
     * since Jan 1st, 1970)
     *
     * @throws YAPI_Exception on error
     */
    public long get_unixTime() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return UNIXTIME_INVALID;
                }
            }
            res = _unixTime;
        }
        return res;
    }

    /**
     * Returns the current time in Unix format (number of elapsed seconds since Jan 1st, 1970).
     *
     *  @return an integer corresponding to the current time in Unix format (number of elapsed seconds
     * since Jan 1st, 1970)
     *
     * @throws YAPI_Exception on error
     */
    public long getUnixTime() throws YAPI_Exception
    {
        return get_unixTime();
    }

    /**
     * Changes the current time. Time is specifid in Unix format (number of elapsed seconds since Jan 1st, 1970).
     *
     * @param newval : an integer corresponding to the current time
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_unixTime(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("unixTime",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current time. Time is specifid in Unix format (number of elapsed seconds since Jan 1st, 1970).
     *
     * @param newval : an integer corresponding to the current time
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUnixTime(long newval)  throws YAPI_Exception
    {
        return set_unixTime(newval);
    }

    /**
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss".
     *
     * @return a string corresponding to the current time in the form "YYYY/MM/DD hh:mm:ss"
     *
     * @throws YAPI_Exception on error
     */
    public String get_dateTime() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return DATETIME_INVALID;
                }
            }
            res = _dateTime;
        }
        return res;
    }

    /**
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss".
     *
     * @return a string corresponding to the current time in the form "YYYY/MM/DD hh:mm:ss"
     *
     * @throws YAPI_Exception on error
     */
    public String getDateTime() throws YAPI_Exception
    {
        return get_dateTime();
    }

    /**
     * Returns the number of seconds between current time and UTC time (time zone).
     *
     * @return an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @throws YAPI_Exception on error
     */
    public int get_utcOffset() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return UTCOFFSET_INVALID;
                }
            }
            res = _utcOffset;
        }
        return res;
    }

    /**
     * Returns the number of seconds between current time and UTC time (time zone).
     *
     * @return an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @throws YAPI_Exception on error
     */
    public int getUtcOffset() throws YAPI_Exception
    {
        return get_utcOffset();
    }

    /**
     * Changes the number of seconds between current time and UTC time (time zone).
     * The timezone is automatically rounded to the nearest multiple of 15 minutes.
     *
     * @param newval : an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_utcOffset(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("utcOffset",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of seconds between current time and UTC time (time zone).
     * The timezone is automatically rounded to the nearest multiple of 15 minutes.
     *
     * @param newval : an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUtcOffset(int newval)  throws YAPI_Exception
    {
        return set_utcOffset(newval);
    }

    /**
     * Returns true if the clock has been set, and false otherwise.
     *
     *  @return either YRealTimeClock.TIMESET_FALSE or YRealTimeClock.TIMESET_TRUE, according to true if
     * the clock has been set, and false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int get_timeSet() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return TIMESET_INVALID;
                }
            }
            res = _timeSet;
        }
        return res;
    }

    /**
     * Returns true if the clock has been set, and false otherwise.
     *
     *  @return either Y_TIMESET_FALSE or Y_TIMESET_TRUE, according to true if the clock has been set, and
     * false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int getTimeSet() throws YAPI_Exception
    {
        return get_timeSet();
    }

    /**
     * Retrieves a clock for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the clock is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRealTimeClock.isOnline() to test if the clock is
     * indeed online at a given time. In case of ambiguity when looking for
     * a clock by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the clock
     *
     * @return a YRealTimeClock object allowing you to drive the clock.
     */
    public static YRealTimeClock FindRealTimeClock(String func)
    {
        YRealTimeClock obj;
        synchronized (YAPI.class) {
            obj = (YRealTimeClock) YFunction._FindFromCache("RealTimeClock", func);
            if (obj == null) {
                obj = new YRealTimeClock(func);
                YFunction._AddToCache("RealTimeClock", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a clock for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the clock is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRealTimeClock.isOnline() to test if the clock is
     * indeed online at a given time. In case of ambiguity when looking for
     * a clock by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the clock
     *
     * @return a YRealTimeClock object allowing you to drive the clock.
     */
    public static YRealTimeClock FindRealTimeClockInContext(YAPIContext yctx,String func)
    {
        YRealTimeClock obj;
        synchronized (yctx) {
            obj = (YRealTimeClock) YFunction._FindFromCacheInContext(yctx, "RealTimeClock", func);
            if (obj == null) {
                obj = new YRealTimeClock(yctx, func);
                YFunction._AddToCache("RealTimeClock", func, obj);
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
        _valueCallbackRealTimeClock = callback;
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
        if (_valueCallbackRealTimeClock != null) {
            _valueCallbackRealTimeClock.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of clocks started using yFirstRealTimeClock().
     *
     * @return a pointer to a YRealTimeClock object, corresponding to
     *         a clock currently online, or a null pointer
     *         if there are no more clocks to enumerate.
     */
    public YRealTimeClock nextRealTimeClock()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindRealTimeClockInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of clocks currently accessible.
     * Use the method YRealTimeClock.nextRealTimeClock() to iterate on
     * next clocks.
     *
     * @return a pointer to a YRealTimeClock object, corresponding to
     *         the first clock currently online, or a null pointer
     *         if there are none.
     */
    public static YRealTimeClock FirstRealTimeClock()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("RealTimeClock");
        if (next_hwid == null)  return null;
        return FindRealTimeClockInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of clocks currently accessible.
     * Use the method YRealTimeClock.nextRealTimeClock() to iterate on
     * next clocks.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YRealTimeClock object, corresponding to
     *         the first clock currently online, or a null pointer
     *         if there are none.
     */
    public static YRealTimeClock FirstRealTimeClockInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("RealTimeClock");
        if (next_hwid == null)  return null;
        return FindRealTimeClockInContext(yctx, next_hwid);
    }

    //--- (end of YRealTimeClock implementation)
}

