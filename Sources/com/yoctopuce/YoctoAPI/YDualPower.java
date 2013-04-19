/*********************************************************************
 *
 * $Id: YDualPower.java 10471 2013-03-19 10:39:56Z seb $
 *
 * Implements yFindDualPower(), the high-level API for DualPower functions
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
 * YDualPower Class: External power supply control interface
 * 
 * Yoctopuce application programming interface allows you to control
 * the power source to use for module functions that require high current.
 * The module can also automatically disconnect the external power
 * when a voltage drop is observed on the external power source
 * (external battery running out of power).
 */
public class YDualPower extends YFunction
{
    //--- (definitions)
    private YDualPower.UpdateCallback _valueCallbackDualPower;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid powerState value
     */
  public static final int POWERSTATE_OFF = 0;
  public static final int POWERSTATE_FROM_USB = 1;
  public static final int POWERSTATE_FROM_EXT = 2;
  public static final int POWERSTATE_INVALID = -1;

    /**
     * invalid powerControl value
     */
  public static final int POWERCONTROL_AUTO = 0;
  public static final int POWERCONTROL_FROM_USB = 1;
  public static final int POWERCONTROL_FROM_EXT = 2;
  public static final int POWERCONTROL_OFF = 3;
  public static final int POWERCONTROL_INVALID = -1;

    /**
     * invalid extVoltage value
     */
    public static final int EXTVOLTAGE_INVALID = YAPI.INVALID_UNSIGNED;
    //--- (end of definitions)

    /**
     * UdateCallback for DualPower
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YDualPower function, String functionValue);
    }



    //--- (YDualPower implementation)

    /**
     * Returns the logical name of the power control.
     * 
     * @return a string corresponding to the logical name of the power control
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the power control.
     * 
     * @return a string corresponding to the logical name of the power control
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the power control. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the power control
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
     * Changes the logical name of the power control. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the power control
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the power control (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the power control (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the power control (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the power control (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the current power source for module functions that require lots of current.
     * 
     * @return a value among YDualPower.POWERSTATE_OFF, YDualPower.POWERSTATE_FROM_USB and
     * YDualPower.POWERSTATE_FROM_EXT corresponding to the current power source for module functions that
     * require lots of current
     * 
     * @throws YAPI_Exception
     */
    public int get_powerState()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("powerState");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current power source for module functions that require lots of current.
     * 
     * @return a value among Y_POWERSTATE_OFF, Y_POWERSTATE_FROM_USB and Y_POWERSTATE_FROM_EXT
     * corresponding to the current power source for module functions that require lots of current
     * 
     * @throws YAPI_Exception
     */
    public int getPowerState() throws YAPI_Exception

    { return get_powerState(); }

    /**
     * Returns the selected power source for module functions that require lots of current.
     * 
     * @return a value among YDualPower.POWERCONTROL_AUTO, YDualPower.POWERCONTROL_FROM_USB,
     * YDualPower.POWERCONTROL_FROM_EXT and YDualPower.POWERCONTROL_OFF corresponding to the selected
     * power source for module functions that require lots of current
     * 
     * @throws YAPI_Exception
     */
    public int get_powerControl()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("powerControl");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the selected power source for module functions that require lots of current.
     * 
     * @return a value among Y_POWERCONTROL_AUTO, Y_POWERCONTROL_FROM_USB, Y_POWERCONTROL_FROM_EXT and
     * Y_POWERCONTROL_OFF corresponding to the selected power source for module functions that require lots of current
     * 
     * @throws YAPI_Exception
     */
    public int getPowerControl() throws YAPI_Exception

    { return get_powerControl(); }

    /**
     * Changes the selected power source for module functions that require lots of current.
     * 
     * @param newval : a value among YDualPower.POWERCONTROL_AUTO, YDualPower.POWERCONTROL_FROM_USB,
     * YDualPower.POWERCONTROL_FROM_EXT and YDualPower.POWERCONTROL_OFF corresponding to the selected
     * power source for module functions that require lots of current
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_powerControl( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("powerControl",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the selected power source for module functions that require lots of current.
     * 
     * @param newval : a value among Y_POWERCONTROL_AUTO, Y_POWERCONTROL_FROM_USB, Y_POWERCONTROL_FROM_EXT
     * and Y_POWERCONTROL_OFF corresponding to the selected power source for module functions that require
     * lots of current
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setPowerControl( int newval)  throws YAPI_Exception

    { return set_powerControl(newval); }

    /**
     * Returns the measured voltage on the external power source, in millivolts.
     * 
     * @return an integer corresponding to the measured voltage on the external power source, in millivolts
     * 
     * @throws YAPI_Exception
     */
    public int get_extVoltage()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("extVoltage");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the measured voltage on the external power source, in millivolts.
     * 
     * @return an integer corresponding to the measured voltage on the external power source, in millivolts
     * 
     * @throws YAPI_Exception
     */
    public int getExtVoltage() throws YAPI_Exception

    { return get_extVoltage(); }

    /**
     * Continues the enumeration of dual power controls started using yFirstDualPower().
     * 
     * @return a pointer to a YDualPower object, corresponding to
     *         a dual power control currently online, or a null pointer
     *         if there are no more dual power controls to enumerate.
     */
    public  YDualPower nextDualPower()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindDualPower(next_hwid);
    }

    /**
     * Retrieves a dual power control for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the power control is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDualPower.isOnline() to test if the power control is
     * indeed online at a given time. In case of ambiguity when looking for
     * a dual power control by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the power control
     * 
     * @return a YDualPower object allowing you to drive the power control.
     */
    public static YDualPower FindDualPower(String func)
    {   YFunction yfunc = YAPI.getFunction("DualPower", func);
        if (yfunc != null) {
            return (YDualPower) yfunc;
        }
        return new YDualPower(func);
    }

    /**
     * Starts the enumeration of dual power controls currently accessible.
     * Use the method YDualPower.nextDualPower() to iterate on
     * next dual power controls.
     * 
     * @return a pointer to a YDualPower object, corresponding to
     *         the first dual power control currently online, or a null pointer
     *         if there are none.
     */
    public static YDualPower FirstDualPower()
    {
        String next_hwid = YAPI.getFirstHardwareId("DualPower");
        if (next_hwid == null)  return null;
        return FindDualPower(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YDualPower(String func)
    {
        super("DualPower", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackDualPower != null) {
            _valueCallbackDualPower.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackDualPower!=null);
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
    public void registerValueCallback(YDualPower.UpdateCallback callback)
    {
         _valueCallbackDualPower =  callback;
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

    //--- (end of YDualPower implementation)
};

