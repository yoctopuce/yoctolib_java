/*********************************************************************
 *
 * $Id: YDualPower.java 19328 2015-02-17 17:30:45Z seb $
 *
 * Implements FindDualPower(), the high-level API for DualPower functions
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
import org.json.JSONException;
import org.json.JSONObject;
import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

//--- (YDualPower return codes)
//--- (end of YDualPower return codes)
//--- (YDualPower class start)
/**
 * YDualPower Class: External power supply control interface
 *
 * Yoctopuce application programming interface allows you to control
 * the power source to use for module functions that require high current.
 * The module can also automatically disconnect the external power
 * when a voltage drop is observed on the external power source
 * (external battery running out of power).
 */
 @SuppressWarnings("UnusedDeclaration")
public class YDualPower extends YFunction
{
//--- (end of YDualPower class start)
//--- (YDualPower definitions)
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
    public static final int EXTVOLTAGE_INVALID = YAPI.INVALID_UINT;
    protected int _powerState = POWERSTATE_INVALID;
    protected int _powerControl = POWERCONTROL_INVALID;
    protected int _extVoltage = EXTVOLTAGE_INVALID;
    protected UpdateCallback _valueCallbackDualPower = null;

    /**
     * Deprecated UpdateCallback for DualPower
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YDualPower function, String functionValue);
    }

    /**
     * TimedReportCallback for DualPower
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YDualPower  function, YMeasure measure);
    }
    //--- (end of YDualPower definitions)


    /**
     *
     * @param func : functionid
     */
    protected YDualPower(String func)
    {
        super(func);
        _className = "DualPower";
        //--- (YDualPower attributes initialization)
        //--- (end of YDualPower attributes initialization)
    }

    //--- (YDualPower implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("powerState")) {
            _powerState = json_val.getInt("powerState");
        }
        if (json_val.has("powerControl")) {
            _powerControl = json_val.getInt("powerControl");
        }
        if (json_val.has("extVoltage")) {
            _extVoltage = json_val.getInt("extVoltage");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current power source for module functions that require lots of current.
     *
     *  @return a value among YDualPower.POWERSTATE_OFF, YDualPower.POWERSTATE_FROM_USB and
     *  YDualPower.POWERSTATE_FROM_EXT corresponding to the current power source for module functions that
     * require lots of current
     *
     * @throws YAPI_Exception on error
     */
    public int get_powerState() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return POWERSTATE_INVALID;
            }
        }
        return _powerState;
    }

    /**
     * Returns the current power source for module functions that require lots of current.
     *
     *  @return a value among Y_POWERSTATE_OFF, Y_POWERSTATE_FROM_USB and Y_POWERSTATE_FROM_EXT
     * corresponding to the current power source for module functions that require lots of current
     *
     * @throws YAPI_Exception on error
     */
    public int getPowerState() throws YAPI_Exception
    {
        return get_powerState();
    }

    /**
     * Returns the selected power source for module functions that require lots of current.
     *
     *  @return a value among YDualPower.POWERCONTROL_AUTO, YDualPower.POWERCONTROL_FROM_USB,
     *  YDualPower.POWERCONTROL_FROM_EXT and YDualPower.POWERCONTROL_OFF corresponding to the selected
     * power source for module functions that require lots of current
     *
     * @throws YAPI_Exception on error
     */
    public int get_powerControl() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return POWERCONTROL_INVALID;
            }
        }
        return _powerControl;
    }

    /**
     * Returns the selected power source for module functions that require lots of current.
     *
     *  @return a value among Y_POWERCONTROL_AUTO, Y_POWERCONTROL_FROM_USB, Y_POWERCONTROL_FROM_EXT and
     * Y_POWERCONTROL_OFF corresponding to the selected power source for module functions that require lots of current
     *
     * @throws YAPI_Exception on error
     */
    public int getPowerControl() throws YAPI_Exception
    {
        return get_powerControl();
    }

    /**
     * Changes the selected power source for module functions that require lots of current.
     *
     *  @param newval : a value among YDualPower.POWERCONTROL_AUTO, YDualPower.POWERCONTROL_FROM_USB,
     *  YDualPower.POWERCONTROL_FROM_EXT and YDualPower.POWERCONTROL_OFF corresponding to the selected
     * power source for module functions that require lots of current
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_powerControl(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("powerControl",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the selected power source for module functions that require lots of current.
     *
     *  @param newval : a value among Y_POWERCONTROL_AUTO, Y_POWERCONTROL_FROM_USB, Y_POWERCONTROL_FROM_EXT
     *  and Y_POWERCONTROL_OFF corresponding to the selected power source for module functions that require
     * lots of current
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPowerControl(int newval)  throws YAPI_Exception
    {
        return set_powerControl(newval);
    }

    /**
     * Returns the measured voltage on the external power source, in millivolts.
     *
     * @return an integer corresponding to the measured voltage on the external power source, in millivolts
     *
     * @throws YAPI_Exception on error
     */
    public int get_extVoltage() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return EXTVOLTAGE_INVALID;
            }
        }
        return _extVoltage;
    }

    /**
     * Returns the measured voltage on the external power source, in millivolts.
     *
     * @return an integer corresponding to the measured voltage on the external power source, in millivolts
     *
     * @throws YAPI_Exception on error
     */
    public int getExtVoltage() throws YAPI_Exception
    {
        return get_extVoltage();
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
    {
        YDualPower obj;
        obj = (YDualPower) YFunction._FindFromCache("DualPower", func);
        if (obj == null) {
            obj = new YDualPower(func);
            YFunction._AddToCache("DualPower", func, obj);
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
        _valueCallbackDualPower = callback;
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
        if (_valueCallbackDualPower != null) {
            _valueCallbackDualPower.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of dual power controls started using yFirstDualPower().
     *
     * @return a pointer to a YDualPower object, corresponding to
     *         a dual power control currently online, or a null pointer
     *         if there are no more dual power controls to enumerate.
     */
    public  YDualPower nextDualPower()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindDualPower(next_hwid);
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
        String next_hwid = SafeYAPI().getFirstHardwareId("DualPower");
        if (next_hwid == null)  return null;
        return FindDualPower(next_hwid);
    }

    //--- (end of YDualPower implementation)
}

