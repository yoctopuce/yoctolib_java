/*********************************************************************
 *
 * $Id: YServo.java 10471 2013-03-19 10:39:56Z seb $
 *
 * Implements yFindServo(), the high-level API for Servo functions
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
 * YServo Class: Servo function interface
 * 
 * Yoctopuce application programming interface allows you not only to move
 * a servo to a given position, but also to specify the time interval
 * in which the move should be performed. This makes it possible to
 * synchronize two servos involved in a same move.
 */
public class YServo extends YFunction
{
    //--- (definitions)
    private YServo.UpdateCallback _valueCallbackServo;
    public static class YMove
    {
        public long target = YAPI.INVALID_LONG;
        public long ms = YAPI.INVALID_LONG;
        public long moving = YAPI.INVALID_LONG;
        public YMove(String attr){}
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
     * invalid position value
     */
    public static final int POSITION_INVALID = YAPI.INVALID_INT;
    /**
     * invalid range value
     */
    public static final int RANGE_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid neutral value
     */
    public static final int NEUTRAL_INVALID = YAPI.INVALID_UNSIGNED;
    public static final YMove MOVE_INVALID = new YMove("");
    //--- (end of definitions)

    /**
     * UdateCallback for Servo
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YServo function, String functionValue);
    }



    //--- (YServo implementation)

    /**
     * invalid move
     */
    /**
     * Returns the logical name of the servo.
     * 
     * @return a string corresponding to the logical name of the servo
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the servo.
     * 
     * @return a string corresponding to the logical name of the servo
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the servo. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the servo
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
     * Changes the logical name of the servo. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the servo
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the servo (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the servo (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the servo (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the servo (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the current servo position.
     * 
     * @return an integer corresponding to the current servo position
     * 
     * @throws YAPI_Exception
     */
    public int get_position()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("position");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current servo position.
     * 
     * @return an integer corresponding to the current servo position
     * 
     * @throws YAPI_Exception
     */
    public int getPosition() throws YAPI_Exception

    { return get_position(); }

    /**
     * Changes immediately the servo driving position.
     * 
     * @param newval : an integer corresponding to immediately the servo driving position
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_position( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("position",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes immediately the servo driving position.
     * 
     * @param newval : an integer corresponding to immediately the servo driving position
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setPosition( int newval)  throws YAPI_Exception

    { return set_position(newval); }

    /**
     * Returns the current range of use of the servo.
     * 
     * @return an integer corresponding to the current range of use of the servo
     * 
     * @throws YAPI_Exception
     */
    public int get_range()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("range");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current range of use of the servo.
     * 
     * @return an integer corresponding to the current range of use of the servo
     * 
     * @throws YAPI_Exception
     */
    public int getRange() throws YAPI_Exception

    { return get_range(); }

    /**
     * Changes the range of use of the servo, specified in per cents.
     * A range of 100% corresponds to a standard control signal, that varies
     * from 1 [ms] to 2 [ms], When using a servo that supports a double range,
     * from 0.5 [ms] to 2.5 [ms], you can select a range of 200%.
     * Be aware that using a range higher than what is supported by the servo
     * is likely to damage the servo.
     * 
     * @param newval : an integer corresponding to the range of use of the servo, specified in per cents
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_range( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("range",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the range of use of the servo, specified in per cents.
     * A range of 100% corresponds to a standard control signal, that varies
     * from 1 [ms] to 2 [ms], When using a servo that supports a double range,
     * from 0.5 [ms] to 2.5 [ms], you can select a range of 200%.
     * Be aware that using a range higher than what is supported by the servo
     * is likely to damage the servo.
     * 
     * @param newval : an integer corresponding to the range of use of the servo, specified in per cents
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setRange( int newval)  throws YAPI_Exception

    { return set_range(newval); }

    /**
     * Returns the duration in microseconds of a neutral pulse for the servo.
     * 
     * @return an integer corresponding to the duration in microseconds of a neutral pulse for the servo
     * 
     * @throws YAPI_Exception
     */
    public int get_neutral()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("neutral");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the duration in microseconds of a neutral pulse for the servo.
     * 
     * @return an integer corresponding to the duration in microseconds of a neutral pulse for the servo
     * 
     * @throws YAPI_Exception
     */
    public int getNeutral() throws YAPI_Exception

    { return get_neutral(); }

    /**
     * Changes the duration of the pulse corresponding to the neutral position of the servo.
     * The duration is specified in microseconds, and the standard value is 1500 [us].
     * This setting makes it possible to shift the range of use of the servo.
     * Be aware that using a range higher than what is supported by the servo is
     * likely to damage the servo.
     * 
     * @param newval : an integer corresponding to the duration of the pulse corresponding to the neutral
     * position of the servo
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_neutral( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("neutral",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the duration of the pulse corresponding to the neutral position of the servo.
     * The duration is specified in microseconds, and the standard value is 1500 [us].
     * This setting makes it possible to shift the range of use of the servo.
     * Be aware that using a range higher than what is supported by the servo is
     * likely to damage the servo.
     * 
     * @param newval : an integer corresponding to the duration of the pulse corresponding to the neutral
     * position of the servo
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setNeutral( int newval)  throws YAPI_Exception

    { return set_neutral(newval); }

    public YMove get_move()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("move");
        return new YMove(json_val);
    }

    public YMove getMove() throws YAPI_Exception

    { return get_move(); }

    public int set_move( YMove  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("move",rest_val);
        return YAPI.SUCCESS;
    }

    public int setMove( YMove newval)  throws YAPI_Exception

    { return set_move(newval); }

    /**
     * Performs a smooth move at constant speed toward a given position.
     * 
     * @param target      : new position at the end of the move
     * @param ms_duration : total duration of the move, in milliseconds
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int move(int target,int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",target,ms_duration);
        _setAttr("move",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Continues the enumeration of servos started using yFirstServo().
     * 
     * @return a pointer to a YServo object, corresponding to
     *         a servo currently online, or a null pointer
     *         if there are no more servos to enumerate.
     */
    public  YServo nextServo()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindServo(next_hwid);
    }

    /**
     * Retrieves a servo for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the servo is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YServo.isOnline() to test if the servo is
     * indeed online at a given time. In case of ambiguity when looking for
     * a servo by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the servo
     * 
     * @return a YServo object allowing you to drive the servo.
     */
    public static YServo FindServo(String func)
    {   YFunction yfunc = YAPI.getFunction("Servo", func);
        if (yfunc != null) {
            return (YServo) yfunc;
        }
        return new YServo(func);
    }

    /**
     * Starts the enumeration of servos currently accessible.
     * Use the method YServo.nextServo() to iterate on
     * next servos.
     * 
     * @return a pointer to a YServo object, corresponding to
     *         the first servo currently online, or a null pointer
     *         if there are none.
     */
    public static YServo FirstServo()
    {
        String next_hwid = YAPI.getFirstHardwareId("Servo");
        if (next_hwid == null)  return null;
        return FindServo(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YServo(String func)
    {
        super("Servo", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackServo != null) {
            _valueCallbackServo.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackServo!=null);
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
    public void registerValueCallback(YServo.UpdateCallback callback)
    {
         _valueCallbackServo =  callback;
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

    //--- (end of YServo implementation)
};

