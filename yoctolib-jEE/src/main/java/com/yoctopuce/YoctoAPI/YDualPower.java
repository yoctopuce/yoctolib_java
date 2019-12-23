/*
 *
 *  $Id: YDualPower.java 38913 2019-12-20 18:59:49Z mvuilleu $
 *
 *  Implements FindDualPower(), the high-level API for DualPower functions
 *
 *  - - - - - - - - - License information: - - - - - - - - -
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
 */

package com.yoctopuce.YoctoAPI;

//--- (YDualPower return codes)
//--- (end of YDualPower return codes)
//--- (YDualPower yapiwrapper)
//--- (end of YDualPower yapiwrapper)
//--- (YDualPower class start)
/**
 * YDualPower Class: dual power switch control interface, available for instance in the Yocto-Servo
 *
 * The YDualPower class allows you to control
 * the power source to use for module functions that require high current.
 * The module can also automatically disconnect the external power
 * when a voltage drop is observed on the external power source
 * (external battery running out of power).
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
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
    public interface UpdateCallback
    {
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
    public interface TimedReportCallback
    {
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
    protected YDualPower(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "DualPower";
        //--- (YDualPower attributes initialization)
        //--- (end of YDualPower attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YDualPower(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YDualPower implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return POWERSTATE_INVALID;
                }
            }
            res = _powerState;
        }
        return res;
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return POWERCONTROL_INVALID;
                }
            }
            res = _powerControl;
        }
        return res;
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
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
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
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("powerControl",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the selected power source for module functions that require lots of current.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return EXTVOLTAGE_INVALID;
                }
            }
            res = _extVoltage;
        }
        return res;
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
     * Retrieves a dual power switch for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the dual power switch is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDualPower.isOnline() to test if the dual power switch is
     * indeed online at a given time. In case of ambiguity when looking for
     * a dual power switch by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the dual power switch, for instance
     *         SERVORC1.dualPower.
     *
     * @return a YDualPower object allowing you to drive the dual power switch.
     */
    public static YDualPower FindDualPower(String func)
    {
        YDualPower obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YDualPower) YFunction._FindFromCache("DualPower", func);
            if (obj == null) {
                obj = new YDualPower(func);
                YFunction._AddToCache("DualPower", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a dual power switch for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the dual power switch is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDualPower.isOnline() to test if the dual power switch is
     * indeed online at a given time. In case of ambiguity when looking for
     * a dual power switch by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the dual power switch, for instance
     *         SERVORC1.dualPower.
     *
     * @return a YDualPower object allowing you to drive the dual power switch.
     */
    public static YDualPower FindDualPowerInContext(YAPIContext yctx,String func)
    {
        YDualPower obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YDualPower) YFunction._FindFromCacheInContext(yctx, "DualPower", func);
            if (obj == null) {
                obj = new YDualPower(yctx, func);
                YFunction._AddToCache("DualPower", func, obj);
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
     * Continues the enumeration of dual power switches started using yFirstDualPower().
     * Caution: You can't make any assumption about the returned dual power switches order.
     * If you want to find a specific a dual power switch, use DualPower.findDualPower()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YDualPower object, corresponding to
     *         a dual power switch currently online, or a null pointer
     *         if there are no more dual power switches to enumerate.
     */
    public YDualPower nextDualPower()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindDualPowerInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of dual power switches currently accessible.
     * Use the method YDualPower.nextDualPower() to iterate on
     * next dual power switches.
     *
     * @return a pointer to a YDualPower object, corresponding to
     *         the first dual power switch currently online, or a null pointer
     *         if there are none.
     */
    public static YDualPower FirstDualPower()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("DualPower");
        if (next_hwid == null)  return null;
        return FindDualPowerInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of dual power switches currently accessible.
     * Use the method YDualPower.nextDualPower() to iterate on
     * next dual power switches.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YDualPower object, corresponding to
     *         the first dual power switch currently online, or a null pointer
     *         if there are none.
     */
    public static YDualPower FirstDualPowerInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("DualPower");
        if (next_hwid == null)  return null;
        return FindDualPowerInContext(yctx, next_hwid);
    }

    //--- (end of YDualPower implementation)
}

