/*********************************************************************
 *
 * $Id: YRealTimeClock.java 12324 2013-08-13 15:10:31Z mvuilleu $
 *
 * Implements yFindRealTimeClock(), the high-level API for RealTimeClock functions
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
 * YRealTimeClock Class: Real Time Clock function interface
 * 
 * The RealTimeClock function maintains and provides current date and time, even accross power cut
 * lasting several days. It is the base for automated wake-up functions provided by the WakeUpScheduler.
 * The current time may represent a local time as well as an UTC time, but no automatic time change
 * will occur to account for daylight saving time.
 */
public class YRealTimeClock extends YFunction
{
    //--- (definitions)
    private YRealTimeClock.UpdateCallback _valueCallbackRealTimeClock;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
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

    //--- (end of definitions)

    /**
     * UdateCallback for RealTimeClock
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YRealTimeClock function, String functionValue);
    }



    //--- (YRealTimeClock implementation)

    /**
     * Returns the logical name of the clock.
     * 
     * @return a string corresponding to the logical name of the clock
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the clock.
     * 
     * @return a string corresponding to the logical name of the clock
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the clock. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the clock
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
     * Changes the logical name of the clock. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the clock
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the clock (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the clock (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the clock (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the clock (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the current time in Unix format (number of elapsed seconds since Jan 1st, 1970).
     * 
     * @return an integer corresponding to the current time in Unix format (number of elapsed seconds
     * since Jan 1st, 1970)
     * 
     * @throws YAPI_Exception
     */
    public long get_unixTime()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("unixTime");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the current time in Unix format (number of elapsed seconds since Jan 1st, 1970).
     * 
     * @return an integer corresponding to the current time in Unix format (number of elapsed seconds
     * since Jan 1st, 1970)
     * 
     * @throws YAPI_Exception
     */
    public long getUnixTime() throws YAPI_Exception

    { return get_unixTime(); }

    /**
     * Changes the current time. Time is specifid in Unix format (number of elapsed seconds since Jan 1st, 1970).
     * If current UTC time is known, utcOffset will be automatically adjusted for the new specified time.
     * 
     * @param newval : an integer corresponding to the current time
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_unixTime( long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("unixTime",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current time. Time is specifid in Unix format (number of elapsed seconds since Jan 1st, 1970).
     * If current UTC time is known, utcOffset will be automatically adjusted for the new specified time.
     * 
     * @param newval : an integer corresponding to the current time
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setUnixTime( long newval)  throws YAPI_Exception

    { return set_unixTime(newval); }

    /**
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss"
     * 
     * @return a string corresponding to the current time in the form "YYYY/MM/DD hh:mm:ss"
     * 
     * @throws YAPI_Exception
     */
    public String get_dateTime()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("dateTime");
        return json_val;
    }

    /**
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss"
     * 
     * @return a string corresponding to the current time in the form "YYYY/MM/DD hh:mm:ss"
     * 
     * @throws YAPI_Exception
     */
    public String getDateTime() throws YAPI_Exception

    { return get_dateTime(); }

    /**
     * Returns the number of seconds between current time and UTC time (time zone).
     * 
     * @return an integer corresponding to the number of seconds between current time and UTC time (time zone)
     * 
     * @throws YAPI_Exception
     */
    public int get_utcOffset()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("utcOffset");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the number of seconds between current time and UTC time (time zone).
     * 
     * @return an integer corresponding to the number of seconds between current time and UTC time (time zone)
     * 
     * @throws YAPI_Exception
     */
    public int getUtcOffset() throws YAPI_Exception

    { return get_utcOffset(); }

    /**
     * Changes the number of seconds between current time and UTC time (time zone).
     * The timezone is automatically rounded to the nearest multiple of 15 minutes.
     * If current UTC time is known, the current time will automatically be updated according to the
     * selected time zone.
     * 
     * @param newval : an integer corresponding to the number of seconds between current time and UTC time (time zone)
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_utcOffset( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("utcOffset",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of seconds between current time and UTC time (time zone).
     * The timezone is automatically rounded to the nearest multiple of 15 minutes.
     * If current UTC time is known, the current time will automatically be updated according to the
     * selected time zone.
     * 
     * @param newval : an integer corresponding to the number of seconds between current time and UTC time (time zone)
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setUtcOffset( int newval)  throws YAPI_Exception

    { return set_utcOffset(newval); }

    /**
     * Returns true if the clock has been set, and false otherwise.
     * 
     * @return either YRealTimeClock.TIMESET_FALSE or YRealTimeClock.TIMESET_TRUE, according to true if
     * the clock has been set, and false otherwise
     * 
     * @throws YAPI_Exception
     */
    public int get_timeSet()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("timeSet");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns true if the clock has been set, and false otherwise.
     * 
     * @return either Y_TIMESET_FALSE or Y_TIMESET_TRUE, according to true if the clock has been set, and
     * false otherwise
     * 
     * @throws YAPI_Exception
     */
    public int getTimeSet() throws YAPI_Exception

    { return get_timeSet(); }

    /**
     * Continues the enumeration of clocks started using yFirstRealTimeClock().
     * 
     * @return a pointer to a YRealTimeClock object, corresponding to
     *         a clock currently online, or a null pointer
     *         if there are no more clocks to enumerate.
     */
    public  YRealTimeClock nextRealTimeClock()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindRealTimeClock(next_hwid);
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
    {   YFunction yfunc = YAPI.getFunction("RealTimeClock", func);
        if (yfunc != null) {
            return (YRealTimeClock) yfunc;
        }
        return new YRealTimeClock(func);
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
        String next_hwid = YAPI.getFirstHardwareId("RealTimeClock");
        if (next_hwid == null)  return null;
        return FindRealTimeClock(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YRealTimeClock(String func)
    {
        super("RealTimeClock", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackRealTimeClock != null) {
            _valueCallbackRealTimeClock.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackRealTimeClock!=null);
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
    public void registerValueCallback(YRealTimeClock.UpdateCallback callback)
    {
         _valueCallbackRealTimeClock =  callback;
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

    //--- (end of YRealTimeClock implementation)
};

