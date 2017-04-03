/*********************************************************************
 *
 * $Id: YBridgeControl.java 27020 2017-03-31 14:54:19Z seb $
 *
 * Implements FindBridgeControl(), the high-level API for BridgeControl functions
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

//--- (YBridgeControl return codes)
//--- (end of YBridgeControl return codes)
//--- (YBridgeControl class start)
/**
 * YBridgeControl Class: BridgeControl function interface
 *
 * The Yoctopuce class YBridgeControl allows you to control bridge excitation parameters
 * and measure parameters for a Wheatstone bridge sensor. To read the measurements, it
 * is best to use the GenericSensor calss, which will compute the measured value
 * in the optimal way.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YBridgeControl extends YFunction
{
//--- (end of YBridgeControl class start)
//--- (YBridgeControl definitions)
    /**
     * invalid excitationMode value
     */
    public static final int EXCITATIONMODE_INTERNAL_AC = 0;
    public static final int EXCITATIONMODE_INTERNAL_DC = 1;
    public static final int EXCITATIONMODE_EXTERNAL_DC = 2;
    public static final int EXCITATIONMODE_INVALID = -1;
    /**
     * invalid bridgeLatency value
     */
    public static final int BRIDGELATENCY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid adValue value
     */
    public static final int ADVALUE_INVALID = YAPI.INVALID_INT;
    /**
     * invalid adGain value
     */
    public static final int ADGAIN_INVALID = YAPI.INVALID_UINT;
    protected int _excitationMode = EXCITATIONMODE_INVALID;
    protected int _bridgeLatency = BRIDGELATENCY_INVALID;
    protected int _adValue = ADVALUE_INVALID;
    protected int _adGain = ADGAIN_INVALID;
    protected UpdateCallback _valueCallbackBridgeControl = null;

    /**
     * Deprecated UpdateCallback for BridgeControl
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YBridgeControl function, String functionValue);
    }

    /**
     * TimedReportCallback for BridgeControl
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YBridgeControl  function, YMeasure measure);
    }
    //--- (end of YBridgeControl definitions)


    /**
     *
     * @param func : functionid
     */
    protected YBridgeControl(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "BridgeControl";
        //--- (YBridgeControl attributes initialization)
        //--- (end of YBridgeControl attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YBridgeControl(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YBridgeControl implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("excitationMode")) {
            _excitationMode = json_val.getInt("excitationMode");
        }
        if (json_val.has("bridgeLatency")) {
            _bridgeLatency = json_val.getInt("bridgeLatency");
        }
        if (json_val.has("adValue")) {
            _adValue = json_val.getInt("adValue");
        }
        if (json_val.has("adGain")) {
            _adGain = json_val.getInt("adGain");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current Wheatstone bridge excitation method.
     *
     *  @return a value among YBridgeControl.EXCITATIONMODE_INTERNAL_AC,
     *  YBridgeControl.EXCITATIONMODE_INTERNAL_DC and YBridgeControl.EXCITATIONMODE_EXTERNAL_DC
     * corresponding to the current Wheatstone bridge excitation method
     *
     * @throws YAPI_Exception on error
     */
    public int get_excitationMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return EXCITATIONMODE_INVALID;
                }
            }
            res = _excitationMode;
        }
        return res;
    }

    /**
     * Returns the current Wheatstone bridge excitation method.
     *
     *  @return a value among Y_EXCITATIONMODE_INTERNAL_AC, Y_EXCITATIONMODE_INTERNAL_DC and
     * Y_EXCITATIONMODE_EXTERNAL_DC corresponding to the current Wheatstone bridge excitation method
     *
     * @throws YAPI_Exception on error
     */
    public int getExcitationMode() throws YAPI_Exception
    {
        return get_excitationMode();
    }

    /**
     * Changes the current Wheatstone bridge excitation method.
     *
     *  @param newval : a value among YBridgeControl.EXCITATIONMODE_INTERNAL_AC,
     *  YBridgeControl.EXCITATIONMODE_INTERNAL_DC and YBridgeControl.EXCITATIONMODE_EXTERNAL_DC
     * corresponding to the current Wheatstone bridge excitation method
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_excitationMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("excitationMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current Wheatstone bridge excitation method.
     *
     *  @param newval : a value among Y_EXCITATIONMODE_INTERNAL_AC, Y_EXCITATIONMODE_INTERNAL_DC and
     * Y_EXCITATIONMODE_EXTERNAL_DC corresponding to the current Wheatstone bridge excitation method
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setExcitationMode(int newval)  throws YAPI_Exception
    {
        return set_excitationMode(newval);
    }

    /**
     * Returns the current Wheatstone bridge excitation method.
     *
     * @return an integer corresponding to the current Wheatstone bridge excitation method
     *
     * @throws YAPI_Exception on error
     */
    public int get_bridgeLatency() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return BRIDGELATENCY_INVALID;
                }
            }
            res = _bridgeLatency;
        }
        return res;
    }

    /**
     * Returns the current Wheatstone bridge excitation method.
     *
     * @return an integer corresponding to the current Wheatstone bridge excitation method
     *
     * @throws YAPI_Exception on error
     */
    public int getBridgeLatency() throws YAPI_Exception
    {
        return get_bridgeLatency();
    }

    /**
     * Changes the current Wheatstone bridge excitation method.
     *
     * @param newval : an integer corresponding to the current Wheatstone bridge excitation method
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_bridgeLatency(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("bridgeLatency",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current Wheatstone bridge excitation method.
     *
     * @param newval : an integer corresponding to the current Wheatstone bridge excitation method
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setBridgeLatency(int newval)  throws YAPI_Exception
    {
        return set_bridgeLatency(newval);
    }

    /**
     * Returns the raw value returned by the ratiometric A/D converter
     * during last read.
     *
     * @return an integer corresponding to the raw value returned by the ratiometric A/D converter
     *         during last read
     *
     * @throws YAPI_Exception on error
     */
    public int get_adValue() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ADVALUE_INVALID;
                }
            }
            res = _adValue;
        }
        return res;
    }

    /**
     * Returns the raw value returned by the ratiometric A/D converter
     * during last read.
     *
     * @return an integer corresponding to the raw value returned by the ratiometric A/D converter
     *         during last read
     *
     * @throws YAPI_Exception on error
     */
    public int getAdValue() throws YAPI_Exception
    {
        return get_adValue();
    }

    /**
     * Returns the current ratiometric A/D converter gain. The gain is automatically
     * configured according to the signalRange set in the corresponding genericSensor.
     *
     * @return an integer corresponding to the current ratiometric A/D converter gain
     *
     * @throws YAPI_Exception on error
     */
    public int get_adGain() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ADGAIN_INVALID;
                }
            }
            res = _adGain;
        }
        return res;
    }

    /**
     * Returns the current ratiometric A/D converter gain. The gain is automatically
     * configured according to the signalRange set in the corresponding genericSensor.
     *
     * @return an integer corresponding to the current ratiometric A/D converter gain
     *
     * @throws YAPI_Exception on error
     */
    public int getAdGain() throws YAPI_Exception
    {
        return get_adGain();
    }

    /**
     * Retrieves a Wheatstone bridge controller for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the Wheatstone bridge controller is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YBridgeControl.isOnline() to test if the Wheatstone bridge controller is
     * indeed online at a given time. In case of ambiguity when looking for
     * a Wheatstone bridge controller by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the Wheatstone bridge controller
     *
     * @return a YBridgeControl object allowing you to drive the Wheatstone bridge controller.
     */
    public static YBridgeControl FindBridgeControl(String func)
    {
        YBridgeControl obj;
        synchronized (YAPI.class) {
            obj = (YBridgeControl) YFunction._FindFromCache("BridgeControl", func);
            if (obj == null) {
                obj = new YBridgeControl(func);
                YFunction._AddToCache("BridgeControl", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a Wheatstone bridge controller for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the Wheatstone bridge controller is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YBridgeControl.isOnline() to test if the Wheatstone bridge controller is
     * indeed online at a given time. In case of ambiguity when looking for
     * a Wheatstone bridge controller by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the Wheatstone bridge controller
     *
     * @return a YBridgeControl object allowing you to drive the Wheatstone bridge controller.
     */
    public static YBridgeControl FindBridgeControlInContext(YAPIContext yctx,String func)
    {
        YBridgeControl obj;
        synchronized (yctx) {
            obj = (YBridgeControl) YFunction._FindFromCacheInContext(yctx, "BridgeControl", func);
            if (obj == null) {
                obj = new YBridgeControl(yctx, func);
                YFunction._AddToCache("BridgeControl", func, obj);
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
        _valueCallbackBridgeControl = callback;
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
        if (_valueCallbackBridgeControl != null) {
            _valueCallbackBridgeControl.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of Wheatstone bridge controllers started using yFirstBridgeControl().
     *
     * @return a pointer to a YBridgeControl object, corresponding to
     *         a Wheatstone bridge controller currently online, or a null pointer
     *         if there are no more Wheatstone bridge controllers to enumerate.
     */
    public YBridgeControl nextBridgeControl()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindBridgeControlInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of Wheatstone bridge controllers currently accessible.
     * Use the method YBridgeControl.nextBridgeControl() to iterate on
     * next Wheatstone bridge controllers.
     *
     * @return a pointer to a YBridgeControl object, corresponding to
     *         the first Wheatstone bridge controller currently online, or a null pointer
     *         if there are none.
     */
    public static YBridgeControl FirstBridgeControl()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("BridgeControl");
        if (next_hwid == null)  return null;
        return FindBridgeControlInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of Wheatstone bridge controllers currently accessible.
     * Use the method YBridgeControl.nextBridgeControl() to iterate on
     * next Wheatstone bridge controllers.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YBridgeControl object, corresponding to
     *         the first Wheatstone bridge controller currently online, or a null pointer
     *         if there are none.
     */
    public static YBridgeControl FirstBridgeControlInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("BridgeControl");
        if (next_hwid == null)  return null;
        return FindBridgeControlInContext(yctx, next_hwid);
    }

    //--- (end of YBridgeControl implementation)
}

