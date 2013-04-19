/*********************************************************************
 *
 * $Id: YLed.java 10471 2013-03-19 10:39:56Z seb $
 *
 * Implements yFindLed(), the high-level API for Led functions
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
 * YLed Class: Led function interface
 * 
 * Yoctopuce application programming interface
 * allows you not only to drive the intensity of the led, but also to
 * have it blink at various preset frequencies.
 */
public class YLed extends YFunction
{
    //--- (definitions)
    private YLed.UpdateCallback _valueCallbackLed;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid power value
     */
  public static final int POWER_OFF = 0;
  public static final int POWER_ON = 1;
  public static final int POWER_INVALID = -1;

    /**
     * invalid luminosity value
     */
    public static final int LUMINOSITY_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid blinking value
     */
  public static final int BLINKING_STILL = 0;
  public static final int BLINKING_RELAX = 1;
  public static final int BLINKING_AWARE = 2;
  public static final int BLINKING_RUN = 3;
  public static final int BLINKING_CALL = 4;
  public static final int BLINKING_PANIC = 5;
  public static final int BLINKING_INVALID = -1;

    //--- (end of definitions)

    /**
     * UdateCallback for Led
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YLed function, String functionValue);
    }



    //--- (YLed implementation)

    /**
     * Returns the logical name of the led.
     * 
     * @return a string corresponding to the logical name of the led
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the led.
     * 
     * @return a string corresponding to the logical name of the led
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the led. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the led
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
     * Changes the logical name of the led. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the led
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the led (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the led (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the led (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the led (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the current led state.
     * 
     * @return either YLed.POWER_OFF or YLed.POWER_ON, according to the current led state
     * 
     * @throws YAPI_Exception
     */
    public int get_power()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("power");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current led state.
     * 
     * @return either Y_POWER_OFF or Y_POWER_ON, according to the current led state
     * 
     * @throws YAPI_Exception
     */
    public int getPower() throws YAPI_Exception

    { return get_power(); }

    /**
     * Changes the state of the led.
     * 
     * @param newval : either YLed.POWER_OFF or YLed.POWER_ON, according to the state of the led
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_power( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("power",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the led.
     * 
     * @param newval : either Y_POWER_OFF or Y_POWER_ON, according to the state of the led
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setPower( int newval)  throws YAPI_Exception

    { return set_power(newval); }

    /**
     * Returns the current led intensity (in per cent).
     * 
     * @return an integer corresponding to the current led intensity (in per cent)
     * 
     * @throws YAPI_Exception
     */
    public int get_luminosity()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("luminosity");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current led intensity (in per cent).
     * 
     * @return an integer corresponding to the current led intensity (in per cent)
     * 
     * @throws YAPI_Exception
     */
    public int getLuminosity() throws YAPI_Exception

    { return get_luminosity(); }

    /**
     * Changes the current led intensity (in per cent).
     * 
     * @param newval : an integer corresponding to the current led intensity (in per cent)
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_luminosity( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("luminosity",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current led intensity (in per cent).
     * 
     * @param newval : an integer corresponding to the current led intensity (in per cent)
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLuminosity( int newval)  throws YAPI_Exception

    { return set_luminosity(newval); }

    /**
     * Returns the current led signaling mode.
     * 
     * @return a value among YLed.BLINKING_STILL, YLed.BLINKING_RELAX, YLed.BLINKING_AWARE,
     * YLed.BLINKING_RUN, YLed.BLINKING_CALL and YLed.BLINKING_PANIC corresponding to the current led signaling mode
     * 
     * @throws YAPI_Exception
     */
    public int get_blinking()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("blinking");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current led signaling mode.
     * 
     * @return a value among Y_BLINKING_STILL, Y_BLINKING_RELAX, Y_BLINKING_AWARE, Y_BLINKING_RUN,
     * Y_BLINKING_CALL and Y_BLINKING_PANIC corresponding to the current led signaling mode
     * 
     * @throws YAPI_Exception
     */
    public int getBlinking() throws YAPI_Exception

    { return get_blinking(); }

    /**
     * Changes the current led signaling mode.
     * 
     * @param newval : a value among YLed.BLINKING_STILL, YLed.BLINKING_RELAX, YLed.BLINKING_AWARE,
     * YLed.BLINKING_RUN, YLed.BLINKING_CALL and YLed.BLINKING_PANIC corresponding to the current led signaling mode
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_blinking( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("blinking",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current led signaling mode.
     * 
     * @param newval : a value among Y_BLINKING_STILL, Y_BLINKING_RELAX, Y_BLINKING_AWARE, Y_BLINKING_RUN,
     * Y_BLINKING_CALL and Y_BLINKING_PANIC corresponding to the current led signaling mode
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setBlinking( int newval)  throws YAPI_Exception

    { return set_blinking(newval); }

    /**
     * Continues the enumeration of leds started using yFirstLed().
     * 
     * @return a pointer to a YLed object, corresponding to
     *         a led currently online, or a null pointer
     *         if there are no more leds to enumerate.
     */
    public  YLed nextLed()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindLed(next_hwid);
    }

    /**
     * Retrieves a led for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the led is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YLed.isOnline() to test if the led is
     * indeed online at a given time. In case of ambiguity when looking for
     * a led by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the led
     * 
     * @return a YLed object allowing you to drive the led.
     */
    public static YLed FindLed(String func)
    {   YFunction yfunc = YAPI.getFunction("Led", func);
        if (yfunc != null) {
            return (YLed) yfunc;
        }
        return new YLed(func);
    }

    /**
     * Starts the enumeration of leds currently accessible.
     * Use the method YLed.nextLed() to iterate on
     * next leds.
     * 
     * @return a pointer to a YLed object, corresponding to
     *         the first led currently online, or a null pointer
     *         if there are none.
     */
    public static YLed FirstLed()
    {
        String next_hwid = YAPI.getFirstHardwareId("Led");
        if (next_hwid == null)  return null;
        return FindLed(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YLed(String func)
    {
        super("Led", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackLed != null) {
            _valueCallbackLed.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackLed!=null);
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
    public void registerValueCallback(YLed.UpdateCallback callback)
    {
         _valueCallbackLed =  callback;
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

    //--- (end of YLed implementation)
};

