/*********************************************************************
 *
 * $Id: YDaisyChain.java 27021 2017-03-31 14:58:15Z seb $
 *
 * Implements FindDaisyChain(), the high-level API for DaisyChain functions
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

//--- (YDaisyChain return codes)
//--- (end of YDaisyChain return codes)
//--- (YDaisyChain class start)
/**
 * YDaisyChain Class: DaisyChain function interface
 *
 * The YDaisyChain interface can be used to verify that devices that
 * are daisy-chained directly from device to device, without a hub,
 * are detected properly.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YDaisyChain extends YFunction
{
//--- (end of YDaisyChain class start)
//--- (YDaisyChain definitions)
    /**
     * invalid daisyState value
     */
    public static final int DAISYSTATE_READY = 0;
    public static final int DAISYSTATE_IS_CHILD = 1;
    public static final int DAISYSTATE_FIRMWARE_MISMATCH = 2;
    public static final int DAISYSTATE_CHILD_MISSING = 3;
    public static final int DAISYSTATE_CHILD_LOST = 4;
    public static final int DAISYSTATE_INVALID = -1;
    /**
     * invalid childCount value
     */
    public static final int CHILDCOUNT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid requiredChildCount value
     */
    public static final int REQUIREDCHILDCOUNT_INVALID = YAPI.INVALID_UINT;
    protected int _daisyState = DAISYSTATE_INVALID;
    protected int _childCount = CHILDCOUNT_INVALID;
    protected int _requiredChildCount = REQUIREDCHILDCOUNT_INVALID;
    protected UpdateCallback _valueCallbackDaisyChain = null;

    /**
     * Deprecated UpdateCallback for DaisyChain
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YDaisyChain function, String functionValue);
    }

    /**
     * TimedReportCallback for DaisyChain
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YDaisyChain  function, YMeasure measure);
    }
    //--- (end of YDaisyChain definitions)


    /**
     *
     * @param func : functionid
     */
    protected YDaisyChain(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "DaisyChain";
        //--- (YDaisyChain attributes initialization)
        //--- (end of YDaisyChain attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YDaisyChain(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YDaisyChain implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("daisyState")) {
            _daisyState = json_val.getInt("daisyState");
        }
        if (json_val.has("childCount")) {
            _childCount = json_val.getInt("childCount");
        }
        if (json_val.has("requiredChildCount")) {
            _requiredChildCount = json_val.getInt("requiredChildCount");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the state of the daisy-link between modules.
     *
     *  @return a value among YDaisyChain.DAISYSTATE_READY, YDaisyChain.DAISYSTATE_IS_CHILD,
     *  YDaisyChain.DAISYSTATE_FIRMWARE_MISMATCH, YDaisyChain.DAISYSTATE_CHILD_MISSING and
     * YDaisyChain.DAISYSTATE_CHILD_LOST corresponding to the state of the daisy-link between modules
     *
     * @throws YAPI_Exception on error
     */
    public int get_daisyState() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return DAISYSTATE_INVALID;
                }
            }
            res = _daisyState;
        }
        return res;
    }

    /**
     * Returns the state of the daisy-link between modules.
     *
     *  @return a value among Y_DAISYSTATE_READY, Y_DAISYSTATE_IS_CHILD, Y_DAISYSTATE_FIRMWARE_MISMATCH,
     *  Y_DAISYSTATE_CHILD_MISSING and Y_DAISYSTATE_CHILD_LOST corresponding to the state of the daisy-link
     * between modules
     *
     * @throws YAPI_Exception on error
     */
    public int getDaisyState() throws YAPI_Exception
    {
        return get_daisyState();
    }

    /**
     * Returns the number of child nodes currently detected.
     *
     * @return an integer corresponding to the number of child nodes currently detected
     *
     * @throws YAPI_Exception on error
     */
    public int get_childCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CHILDCOUNT_INVALID;
                }
            }
            res = _childCount;
        }
        return res;
    }

    /**
     * Returns the number of child nodes currently detected.
     *
     * @return an integer corresponding to the number of child nodes currently detected
     *
     * @throws YAPI_Exception on error
     */
    public int getChildCount() throws YAPI_Exception
    {
        return get_childCount();
    }

    /**
     * Returns the number of child nodes expected in normal conditions.
     *
     * @return an integer corresponding to the number of child nodes expected in normal conditions
     *
     * @throws YAPI_Exception on error
     */
    public int get_requiredChildCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return REQUIREDCHILDCOUNT_INVALID;
                }
            }
            res = _requiredChildCount;
        }
        return res;
    }

    /**
     * Returns the number of child nodes expected in normal conditions.
     *
     * @return an integer corresponding to the number of child nodes expected in normal conditions
     *
     * @throws YAPI_Exception on error
     */
    public int getRequiredChildCount() throws YAPI_Exception
    {
        return get_requiredChildCount();
    }

    /**
     * Changes the number of child nodes expected in normal conditions.
     * If the value is zero, no check is performed. If it is non-zero, the number
     * child nodes is checked on startup and the status will change to error if
     * the count does not match.
     *
     * @param newval : an integer corresponding to the number of child nodes expected in normal conditions
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_requiredChildCount(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("requiredChildCount",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of child nodes expected in normal conditions.
     * If the value is zero, no check is performed. If it is non-zero, the number
     * child nodes is checked on startup and the status will change to error if
     * the count does not match.
     *
     * @param newval : an integer corresponding to the number of child nodes expected in normal conditions
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setRequiredChildCount(int newval)  throws YAPI_Exception
    {
        return set_requiredChildCount(newval);
    }

    /**
     * Retrieves a module chain for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the module chain is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDaisyChain.isOnline() to test if the module chain is
     * indeed online at a given time. In case of ambiguity when looking for
     * a module chain by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the module chain
     *
     * @return a YDaisyChain object allowing you to drive the module chain.
     */
    public static YDaisyChain FindDaisyChain(String func)
    {
        YDaisyChain obj;
        synchronized (YAPI.class) {
            obj = (YDaisyChain) YFunction._FindFromCache("DaisyChain", func);
            if (obj == null) {
                obj = new YDaisyChain(func);
                YFunction._AddToCache("DaisyChain", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a module chain for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the module chain is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDaisyChain.isOnline() to test if the module chain is
     * indeed online at a given time. In case of ambiguity when looking for
     * a module chain by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the module chain
     *
     * @return a YDaisyChain object allowing you to drive the module chain.
     */
    public static YDaisyChain FindDaisyChainInContext(YAPIContext yctx,String func)
    {
        YDaisyChain obj;
        synchronized (yctx) {
            obj = (YDaisyChain) YFunction._FindFromCacheInContext(yctx, "DaisyChain", func);
            if (obj == null) {
                obj = new YDaisyChain(yctx, func);
                YFunction._AddToCache("DaisyChain", func, obj);
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
        _valueCallbackDaisyChain = callback;
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
        if (_valueCallbackDaisyChain != null) {
            _valueCallbackDaisyChain.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of module chains started using yFirstDaisyChain().
     *
     * @return a pointer to a YDaisyChain object, corresponding to
     *         a module chain currently online, or a null pointer
     *         if there are no more module chains to enumerate.
     */
    public YDaisyChain nextDaisyChain()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindDaisyChainInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of module chains currently accessible.
     * Use the method YDaisyChain.nextDaisyChain() to iterate on
     * next module chains.
     *
     * @return a pointer to a YDaisyChain object, corresponding to
     *         the first module chain currently online, or a null pointer
     *         if there are none.
     */
    public static YDaisyChain FirstDaisyChain()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("DaisyChain");
        if (next_hwid == null)  return null;
        return FindDaisyChainInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of module chains currently accessible.
     * Use the method YDaisyChain.nextDaisyChain() to iterate on
     * next module chains.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YDaisyChain object, corresponding to
     *         the first module chain currently online, or a null pointer
     *         if there are none.
     */
    public static YDaisyChain FirstDaisyChainInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("DaisyChain");
        if (next_hwid == null)  return null;
        return FindDaisyChainInContext(yctx, next_hwid);
    }

    //--- (end of YDaisyChain implementation)
}

