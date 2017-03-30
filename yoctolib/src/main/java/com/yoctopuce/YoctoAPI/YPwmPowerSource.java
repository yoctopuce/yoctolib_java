/*********************************************************************
 *
 * $Id: YPwmPowerSource.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindPwmPowerSource(), the high-level API for PwmPowerSource functions
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

//--- (YPwmPowerSource return codes)
//--- (end of YPwmPowerSource return codes)
//--- (YPwmPowerSource class start)
/**
 * YPwmPowerSource Class: PwmPowerSource function interface
 *
 * The Yoctopuce application programming interface allows you to configure
 * the voltage source used by all PWM on the same device.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YPwmPowerSource extends YFunction
{
//--- (end of YPwmPowerSource class start)
//--- (YPwmPowerSource definitions)
    /**
     * invalid powerMode value
     */
    public static final int POWERMODE_USB_5V = 0;
    public static final int POWERMODE_USB_3V = 1;
    public static final int POWERMODE_EXT_V = 2;
    public static final int POWERMODE_OPNDRN = 3;
    public static final int POWERMODE_INVALID = -1;
    protected int _powerMode = POWERMODE_INVALID;
    protected UpdateCallback _valueCallbackPwmPowerSource = null;

    /**
     * Deprecated UpdateCallback for PwmPowerSource
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YPwmPowerSource function, String functionValue);
    }

    /**
     * TimedReportCallback for PwmPowerSource
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YPwmPowerSource  function, YMeasure measure);
    }
    //--- (end of YPwmPowerSource definitions)


    /**
     *
     * @param func : functionid
     */
    protected YPwmPowerSource(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "PwmPowerSource";
        //--- (YPwmPowerSource attributes initialization)
        //--- (end of YPwmPowerSource attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YPwmPowerSource(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YPwmPowerSource implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("powerMode")) {
            _powerMode = json_val.getInt("powerMode");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the selected power source for the PWM on the same device.
     *
     *  @return a value among YPwmPowerSource.POWERMODE_USB_5V, YPwmPowerSource.POWERMODE_USB_3V,
     *  YPwmPowerSource.POWERMODE_EXT_V and YPwmPowerSource.POWERMODE_OPNDRN corresponding to the selected
     * power source for the PWM on the same device
     *
     * @throws YAPI_Exception on error
     */
    public int get_powerMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return POWERMODE_INVALID;
                }
            }
            res = _powerMode;
        }
        return res;
    }

    /**
     * Returns the selected power source for the PWM on the same device.
     *
     *  @return a value among Y_POWERMODE_USB_5V, Y_POWERMODE_USB_3V, Y_POWERMODE_EXT_V and
     * Y_POWERMODE_OPNDRN corresponding to the selected power source for the PWM on the same device
     *
     * @throws YAPI_Exception on error
     */
    public int getPowerMode() throws YAPI_Exception
    {
        return get_powerMode();
    }

    /**
     * Changes  the PWM power source. PWM can use isolated 5V from USB, isolated 3V from USB or
     * voltage from an external power source. The PWM can also work in open drain  mode. In that
     * mode, the PWM actively pulls the line down.
     * Warning: this setting is common to all PWM on the same device. If you change that parameter,
     * all PWM located on the same device are  affected.
     * If you want the change to be kept after a device reboot, make sure  to call the matching
     * module saveToFlash().
     *
     *  @param newval : a value among YPwmPowerSource.POWERMODE_USB_5V, YPwmPowerSource.POWERMODE_USB_3V,
     * YPwmPowerSource.POWERMODE_EXT_V and YPwmPowerSource.POWERMODE_OPNDRN corresponding to  the PWM power source
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_powerMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("powerMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes  the PWM power source. PWM can use isolated 5V from USB, isolated 3V from USB or
     * voltage from an external power source. The PWM can also work in open drain  mode. In that
     * mode, the PWM actively pulls the line down.
     * Warning: this setting is common to all PWM on the same device. If you change that parameter,
     * all PWM located on the same device are  affected.
     * If you want the change to be kept after a device reboot, make sure  to call the matching
     * module saveToFlash().
     *
     *  @param newval : a value among Y_POWERMODE_USB_5V, Y_POWERMODE_USB_3V, Y_POWERMODE_EXT_V and
     * Y_POWERMODE_OPNDRN corresponding to  the PWM power source
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPowerMode(int newval)  throws YAPI_Exception
    {
        return set_powerMode(newval);
    }

    /**
     * Retrieves a voltage source for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the voltage source is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPwmPowerSource.isOnline() to test if the voltage source is
     * indeed online at a given time. In case of ambiguity when looking for
     * a voltage source by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the voltage source
     *
     * @return a YPwmPowerSource object allowing you to drive the voltage source.
     */
    public static YPwmPowerSource FindPwmPowerSource(String func)
    {
        YPwmPowerSource obj;
        synchronized (YAPI.class) {
            obj = (YPwmPowerSource) YFunction._FindFromCache("PwmPowerSource", func);
            if (obj == null) {
                obj = new YPwmPowerSource(func);
                YFunction._AddToCache("PwmPowerSource", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a voltage source for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the voltage source is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPwmPowerSource.isOnline() to test if the voltage source is
     * indeed online at a given time. In case of ambiguity when looking for
     * a voltage source by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the voltage source
     *
     * @return a YPwmPowerSource object allowing you to drive the voltage source.
     */
    public static YPwmPowerSource FindPwmPowerSourceInContext(YAPIContext yctx,String func)
    {
        YPwmPowerSource obj;
        synchronized (yctx) {
            obj = (YPwmPowerSource) YFunction._FindFromCacheInContext(yctx, "PwmPowerSource", func);
            if (obj == null) {
                obj = new YPwmPowerSource(yctx, func);
                YFunction._AddToCache("PwmPowerSource", func, obj);
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
        _valueCallbackPwmPowerSource = callback;
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
        if (_valueCallbackPwmPowerSource != null) {
            _valueCallbackPwmPowerSource.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of Voltage sources started using yFirstPwmPowerSource().
     *
     * @return a pointer to a YPwmPowerSource object, corresponding to
     *         a voltage source currently online, or a null pointer
     *         if there are no more Voltage sources to enumerate.
     */
    public YPwmPowerSource nextPwmPowerSource()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindPwmPowerSourceInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of Voltage sources currently accessible.
     * Use the method YPwmPowerSource.nextPwmPowerSource() to iterate on
     * next Voltage sources.
     *
     * @return a pointer to a YPwmPowerSource object, corresponding to
     *         the first source currently online, or a null pointer
     *         if there are none.
     */
    public static YPwmPowerSource FirstPwmPowerSource()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("PwmPowerSource");
        if (next_hwid == null)  return null;
        return FindPwmPowerSourceInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of Voltage sources currently accessible.
     * Use the method YPwmPowerSource.nextPwmPowerSource() to iterate on
     * next Voltage sources.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YPwmPowerSource object, corresponding to
     *         the first source currently online, or a null pointer
     *         if there are none.
     */
    public static YPwmPowerSource FirstPwmPowerSourceInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("PwmPowerSource");
        if (next_hwid == null)  return null;
        return FindPwmPowerSourceInContext(yctx, next_hwid);
    }

    //--- (end of YPwmPowerSource implementation)
}

