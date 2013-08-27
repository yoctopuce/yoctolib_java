/*********************************************************************
 *
 * $Id: YWakeUpSchedule.java 12469 2013-08-22 10:11:58Z seb $
 *
 * Implements yFindWakeUpSchedule(), the high-level API for WakeUpSchedule functions
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
 * YWakeUpSchedule Class: WakeUpSchedule function interface
 * 
 * The WakeUpSchedule function implements a wake-up condition. The wake-up time is
 * specified as a set of months and/or days and/or hours and/or minutes where the
 * wake-up should happen.
 */
public class YWakeUpSchedule extends YFunction
{
    //--- (definitions)
    private YWakeUpSchedule.UpdateCallback _valueCallbackWakeUpSchedule;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid minutesA value
     */
    public static final int MINUTESA_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid minutesB value
     */
    public static final int MINUTESB_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid hours value
     */
    public static final int HOURS_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid weekDays value
     */
    public static final int WEEKDAYS_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid monthDays value
     */
    public static final int MONTHDAYS_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid months value
     */
    public static final int MONTHS_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid nextOccurence value
     */
    public static final long NEXTOCCURENCE_INVALID = YAPI.INVALID_LONG;
    //--- (end of definitions)

    /**
     * UdateCallback for WakeUpSchedule
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YWakeUpSchedule function, String functionValue);
    }



    //--- (YWakeUpSchedule implementation)

    /**
     * Returns the logical name of the wake-up schedule.
     * 
     * @return a string corresponding to the logical name of the wake-up schedule
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the wake-up schedule.
     * 
     * @return a string corresponding to the logical name of the wake-up schedule
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the wake-up schedule. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the wake-up schedule
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
     * Changes the logical name of the wake-up schedule. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the wake-up schedule
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the wake-up schedule (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the wake-up schedule (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the wake-up schedule (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the wake-up schedule (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the minutes 00-29 of each hour scheduled for wake-up.
     * 
     * @return an integer corresponding to the minutes 00-29 of each hour scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int get_minutesA()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("minutesA");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the minutes 00-29 of each hour scheduled for wake-up.
     * 
     * @return an integer corresponding to the minutes 00-29 of each hour scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int getMinutesA() throws YAPI_Exception

    { return get_minutesA(); }

    /**
     * Changes the minutes 00-29 where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the minutes 00-29 where a wake up must take place
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_minutesA( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("minutesA",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the minutes 00-29 where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the minutes 00-29 where a wake up must take place
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setMinutesA( int newval)  throws YAPI_Exception

    { return set_minutesA(newval); }

    /**
     * Returns the minutes 30-59 of each hour scheduled for wake-up.
     * 
     * @return an integer corresponding to the minutes 30-59 of each hour scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int get_minutesB()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("minutesB");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the minutes 30-59 of each hour scheduled for wake-up.
     * 
     * @return an integer corresponding to the minutes 30-59 of each hour scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int getMinutesB() throws YAPI_Exception

    { return get_minutesB(); }

    /**
     * Changes the minutes 30-59 where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the minutes 30-59 where a wake up must take place
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_minutesB( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("minutesB",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the minutes 30-59 where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the minutes 30-59 where a wake up must take place
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setMinutesB( int newval)  throws YAPI_Exception

    { return set_minutesB(newval); }

    /**
     * Returns the hours  scheduled for wake-up.
     * 
     * @return an integer corresponding to the hours  scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int get_hours()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("hours");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the hours  scheduled for wake-up.
     * 
     * @return an integer corresponding to the hours  scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int getHours() throws YAPI_Exception

    { return get_hours(); }

    /**
     * Changes the hours where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the hours where a wake up must take place
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_hours( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("hours",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the hours where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the hours where a wake up must take place
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setHours( int newval)  throws YAPI_Exception

    { return set_hours(newval); }

    /**
     * Returns the days of week scheduled for wake-up.
     * 
     * @return an integer corresponding to the days of week scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int get_weekDays()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("weekDays");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the days of week scheduled for wake-up.
     * 
     * @return an integer corresponding to the days of week scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int getWeekDays() throws YAPI_Exception

    { return get_weekDays(); }

    /**
     * Changes the days of the week where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the days of the week where a wake up must take place
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_weekDays( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("weekDays",rest_val);
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
    public int setWeekDays( int newval)  throws YAPI_Exception

    { return set_weekDays(newval); }

    /**
     * Returns the days of week scheduled for wake-up.
     * 
     * @return an integer corresponding to the days of week scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int get_monthDays()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("monthDays");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the days of week scheduled for wake-up.
     * 
     * @return an integer corresponding to the days of week scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int getMonthDays() throws YAPI_Exception

    { return get_monthDays(); }

    /**
     * Changes the days of the week where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the days of the week where a wake up must take place
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_monthDays( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("monthDays",rest_val);
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
    public int setMonthDays( int newval)  throws YAPI_Exception

    { return set_monthDays(newval); }

    /**
     * Returns the days of week scheduled for wake-up.
     * 
     * @return an integer corresponding to the days of week scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int get_months()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("months");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the days of week scheduled for wake-up.
     * 
     * @return an integer corresponding to the days of week scheduled for wake-up
     * 
     * @throws YAPI_Exception
     */
    public int getMonths() throws YAPI_Exception

    { return get_months(); }

    /**
     * Changes the days of the week where a wake up must take place.
     * 
     * @param newval : an integer corresponding to the days of the week where a wake up must take place
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_months( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("months",rest_val);
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
    public int setMonths( int newval)  throws YAPI_Exception

    { return set_months(newval); }

    /**
     * Returns the  nextwake up date/time (seconds) wake up occurence
     * 
     * @return an integer corresponding to the  nextwake up date/time (seconds) wake up occurence
     * 
     * @throws YAPI_Exception
     */
    public long get_nextOccurence()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("nextOccurence");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the  nextwake up date/time (seconds) wake up occurence
     * 
     * @return an integer corresponding to the  nextwake up date/time (seconds) wake up occurence
     * 
     * @throws YAPI_Exception
     */
    public long getNextOccurence() throws YAPI_Exception

    { return get_nextOccurence(); }

    /**
     * Returns every the minutes of each hour scheduled for wake-up.
     */
    public long get_minutes()  throws YAPI_Exception
    {
        long res;
        res = get_minutesB();
        res = res << 30;
        res = res + get_minutesA();
        return res;
        
    }

    /**
     * Changes all the minutes where a wake up must take place.
     * 
     * @param bitmap : Minutes 00-59 of each hour scheduled for wake-up.,
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_minutes(long bitmap)  throws YAPI_Exception
    {
        set_minutesA((int)(bitmap & 0x3fffffff));
        bitmap = bitmap >> 30;
        return set_minutesB((int)(bitmap & 0x3fffffff));
        
    }

    /**
     * Continues the enumeration of wake-up schedules started using yFirstWakeUpSchedule().
     * 
     * @return a pointer to a YWakeUpSchedule object, corresponding to
     *         a wake-up schedule currently online, or a null pointer
     *         if there are no more wake-up schedules to enumerate.
     */
    public  YWakeUpSchedule nextWakeUpSchedule()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindWakeUpSchedule(next_hwid);
    }

    /**
     * Retrieves a wake-up schedule for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the wake-up schedule is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YWakeUpSchedule.isOnline() to test if the wake-up schedule is
     * indeed online at a given time. In case of ambiguity when looking for
     * a wake-up schedule by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the wake-up schedule
     * 
     * @return a YWakeUpSchedule object allowing you to drive the wake-up schedule.
     */
    public static YWakeUpSchedule FindWakeUpSchedule(String func)
    {   YFunction yfunc = YAPI.getFunction("WakeUpSchedule", func);
        if (yfunc != null) {
            return (YWakeUpSchedule) yfunc;
        }
        return new YWakeUpSchedule(func);
    }

    /**
     * Starts the enumeration of wake-up schedules currently accessible.
     * Use the method YWakeUpSchedule.nextWakeUpSchedule() to iterate on
     * next wake-up schedules.
     * 
     * @return a pointer to a YWakeUpSchedule object, corresponding to
     *         the first wake-up schedule currently online, or a null pointer
     *         if there are none.
     */
    public static YWakeUpSchedule FirstWakeUpSchedule()
    {
        String next_hwid = YAPI.getFirstHardwareId("WakeUpSchedule");
        if (next_hwid == null)  return null;
        return FindWakeUpSchedule(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YWakeUpSchedule(String func)
    {
        super("WakeUpSchedule", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackWakeUpSchedule != null) {
            _valueCallbackWakeUpSchedule.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackWakeUpSchedule!=null);
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
    public void registerValueCallback(YWakeUpSchedule.UpdateCallback callback)
    {
         _valueCallbackWakeUpSchedule =  callback;
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

    //--- (end of YWakeUpSchedule implementation)
};

