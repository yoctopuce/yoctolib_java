/*********************************************************************
 *
 * $Id: YLed.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindLed(), the high-level API for Led functions
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

//--- (YLed return codes)
//--- (end of YLed return codes)
//--- (YLed class start)
/**
 * YLed Class: Led function interface
 *
 * The Yoctopuce application programming interface
 * allows you not only to drive the intensity of the LED, but also to
 * have it blink at various preset frequencies.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YLed extends YFunction
{
//--- (end of YLed class start)
//--- (YLed definitions)
    /**
     * invalid power value
     */
    public static final int POWER_OFF = 0;
    public static final int POWER_ON = 1;
    public static final int POWER_INVALID = -1;
    /**
     * invalid luminosity value
     */
    public static final int LUMINOSITY_INVALID = YAPI.INVALID_UINT;
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
    protected int _power = POWER_INVALID;
    protected int _luminosity = LUMINOSITY_INVALID;
    protected int _blinking = BLINKING_INVALID;
    protected UpdateCallback _valueCallbackLed = null;

    /**
     * Deprecated UpdateCallback for Led
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YLed function, String functionValue);
    }

    /**
     * TimedReportCallback for Led
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YLed  function, YMeasure measure);
    }
    //--- (end of YLed definitions)


    /**
     *
     * @param func : functionid
     */
    protected YLed(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Led";
        //--- (YLed attributes initialization)
        //--- (end of YLed attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YLed(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YLed implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("power")) {
            _power = json_val.getInt("power") > 0 ? 1 : 0;
        }
        if (json_val.has("luminosity")) {
            _luminosity = json_val.getInt("luminosity");
        }
        if (json_val.has("blinking")) {
            _blinking = json_val.getInt("blinking");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current LED state.
     *
     * @return either YLed.POWER_OFF or YLed.POWER_ON, according to the current LED state
     *
     * @throws YAPI_Exception on error
     */
    public int get_power() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return POWER_INVALID;
                }
            }
            res = _power;
        }
        return res;
    }

    /**
     * Returns the current LED state.
     *
     * @return either Y_POWER_OFF or Y_POWER_ON, according to the current LED state
     *
     * @throws YAPI_Exception on error
     */
    public int getPower() throws YAPI_Exception
    {
        return get_power();
    }

    /**
     * Changes the state of the LED.
     *
     * @param newval : either YLed.POWER_OFF or YLed.POWER_ON, according to the state of the LED
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_power(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("power",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the LED.
     *
     * @param newval : either Y_POWER_OFF or Y_POWER_ON, according to the state of the LED
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPower(int newval)  throws YAPI_Exception
    {
        return set_power(newval);
    }

    /**
     * Returns the current LED intensity (in per cent).
     *
     * @return an integer corresponding to the current LED intensity (in per cent)
     *
     * @throws YAPI_Exception on error
     */
    public int get_luminosity() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return LUMINOSITY_INVALID;
                }
            }
            res = _luminosity;
        }
        return res;
    }

    /**
     * Returns the current LED intensity (in per cent).
     *
     * @return an integer corresponding to the current LED intensity (in per cent)
     *
     * @throws YAPI_Exception on error
     */
    public int getLuminosity() throws YAPI_Exception
    {
        return get_luminosity();
    }

    /**
     * Changes the current LED intensity (in per cent).
     *
     * @param newval : an integer corresponding to the current LED intensity (in per cent)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_luminosity(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("luminosity",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current LED intensity (in per cent).
     *
     * @param newval : an integer corresponding to the current LED intensity (in per cent)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLuminosity(int newval)  throws YAPI_Exception
    {
        return set_luminosity(newval);
    }

    /**
     * Returns the current LED signaling mode.
     *
     *  @return a value among YLed.BLINKING_STILL, YLed.BLINKING_RELAX, YLed.BLINKING_AWARE,
     * YLed.BLINKING_RUN, YLed.BLINKING_CALL and YLed.BLINKING_PANIC corresponding to the current LED signaling mode
     *
     * @throws YAPI_Exception on error
     */
    public int get_blinking() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return BLINKING_INVALID;
                }
            }
            res = _blinking;
        }
        return res;
    }

    /**
     * Returns the current LED signaling mode.
     *
     *  @return a value among Y_BLINKING_STILL, Y_BLINKING_RELAX, Y_BLINKING_AWARE, Y_BLINKING_RUN,
     * Y_BLINKING_CALL and Y_BLINKING_PANIC corresponding to the current LED signaling mode
     *
     * @throws YAPI_Exception on error
     */
    public int getBlinking() throws YAPI_Exception
    {
        return get_blinking();
    }

    /**
     * Changes the current LED signaling mode.
     *
     *  @param newval : a value among YLed.BLINKING_STILL, YLed.BLINKING_RELAX, YLed.BLINKING_AWARE,
     * YLed.BLINKING_RUN, YLed.BLINKING_CALL and YLed.BLINKING_PANIC corresponding to the current LED signaling mode
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_blinking(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("blinking",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current LED signaling mode.
     *
     *  @param newval : a value among Y_BLINKING_STILL, Y_BLINKING_RELAX, Y_BLINKING_AWARE, Y_BLINKING_RUN,
     * Y_BLINKING_CALL and Y_BLINKING_PANIC corresponding to the current LED signaling mode
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setBlinking(int newval)  throws YAPI_Exception
    {
        return set_blinking(newval);
    }

    /**
     * Retrieves a LED for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the LED is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YLed.isOnline() to test if the LED is
     * indeed online at a given time. In case of ambiguity when looking for
     * a LED by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the LED
     *
     * @return a YLed object allowing you to drive the LED.
     */
    public static YLed FindLed(String func)
    {
        YLed obj;
        synchronized (YAPI.class) {
            obj = (YLed) YFunction._FindFromCache("Led", func);
            if (obj == null) {
                obj = new YLed(func);
                YFunction._AddToCache("Led", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a LED for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the LED is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YLed.isOnline() to test if the LED is
     * indeed online at a given time. In case of ambiguity when looking for
     * a LED by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the LED
     *
     * @return a YLed object allowing you to drive the LED.
     */
    public static YLed FindLedInContext(YAPIContext yctx,String func)
    {
        YLed obj;
        synchronized (yctx) {
            obj = (YLed) YFunction._FindFromCacheInContext(yctx, "Led", func);
            if (obj == null) {
                obj = new YLed(yctx, func);
                YFunction._AddToCache("Led", func, obj);
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
        _valueCallbackLed = callback;
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
        if (_valueCallbackLed != null) {
            _valueCallbackLed.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of LEDs started using yFirstLed().
     *
     * @return a pointer to a YLed object, corresponding to
     *         a LED currently online, or a null pointer
     *         if there are no more LEDs to enumerate.
     */
    public YLed nextLed()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindLedInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of LEDs currently accessible.
     * Use the method YLed.nextLed() to iterate on
     * next LEDs.
     *
     * @return a pointer to a YLed object, corresponding to
     *         the first LED currently online, or a null pointer
     *         if there are none.
     */
    public static YLed FirstLed()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Led");
        if (next_hwid == null)  return null;
        return FindLedInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of LEDs currently accessible.
     * Use the method YLed.nextLed() to iterate on
     * next LEDs.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YLed object, corresponding to
     *         the first LED currently online, or a null pointer
     *         if there are none.
     */
    public static YLed FirstLedInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Led");
        if (next_hwid == null)  return null;
        return FindLedInContext(yctx, next_hwid);
    }

    //--- (end of YLed implementation)
}

