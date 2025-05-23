/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindSegmentedDisplay(), the high-level API for SegmentedDisplay functions
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

//--- (YSegmentedDisplay return codes)
//--- (end of YSegmentedDisplay return codes)
//--- (YSegmentedDisplay yapiwrapper)
//--- (end of YSegmentedDisplay yapiwrapper)
//--- (YSegmentedDisplay class start)
/**
 * YSegmentedDisplay Class: segmented display control interface
 *
 * The SegmentedDisplay class allows you to drive segmented displays.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSegmentedDisplay extends YFunction
{
//--- (end of YSegmentedDisplay class start)
//--- (YSegmentedDisplay definitions)
    /**
     * invalid displayedText value
     */
    public static final String DISPLAYEDTEXT_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid displayMode value
     */
    public static final int DISPLAYMODE_DISCONNECTED = 0;
    public static final int DISPLAYMODE_MANUAL = 1;
    public static final int DISPLAYMODE_AUTO1 = 2;
    public static final int DISPLAYMODE_AUTO60 = 3;
    public static final int DISPLAYMODE_INVALID = -1;
    protected String _displayedText = DISPLAYEDTEXT_INVALID;
    protected int _displayMode = DISPLAYMODE_INVALID;
    protected UpdateCallback _valueCallbackSegmentedDisplay = null;

    /**
     * Deprecated UpdateCallback for SegmentedDisplay
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YSegmentedDisplay function, String functionValue);
    }

    /**
     * TimedReportCallback for SegmentedDisplay
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YSegmentedDisplay  function, YMeasure measure);
    }
    //--- (end of YSegmentedDisplay definitions)


    /**
     *
     * @param func : functionid
     */
    protected YSegmentedDisplay(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "SegmentedDisplay";
        //--- (YSegmentedDisplay attributes initialization)
        //--- (end of YSegmentedDisplay attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YSegmentedDisplay(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YSegmentedDisplay implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("displayedText")) {
            _displayedText = json_val.getString("displayedText");
        }
        if (json_val.has("displayMode")) {
            _displayMode = json_val.getInt("displayMode");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the text currently displayed on the screen.
     *
     * @return a string corresponding to the text currently displayed on the screen
     *
     * @throws YAPI_Exception on error
     */
    public String get_displayedText() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DISPLAYEDTEXT_INVALID;
                }
            }
            res = _displayedText;
        }
        return res;
    }

    /**
     * Returns the text currently displayed on the screen.
     *
     * @return a string corresponding to the text currently displayed on the screen
     *
     * @throws YAPI_Exception on error
     */
    public String getDisplayedText() throws YAPI_Exception
    {
        return get_displayedText();
    }

    /**
     * Changes the text currently displayed on the screen.
     *
     * @param newval : a string corresponding to the text currently displayed on the screen
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_displayedText(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("displayedText",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the text currently displayed on the screen.
     *
     * @param newval : a string corresponding to the text currently displayed on the screen
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDisplayedText(String newval)  throws YAPI_Exception
    {
        return set_displayedText(newval);
    }

    public int get_displayMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DISPLAYMODE_INVALID;
                }
            }
            res = _displayMode;
        }
        return res;
    }

    public int set_displayMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("displayMode",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Retrieves a segmented display for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the segmented display is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSegmentedDisplay.isOnline() to test if the segmented display is
     * indeed online at a given time. In case of ambiguity when looking for
     * a segmented display by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the segmented display, for instance
     *         MyDevice.segmentedDisplay.
     *
     * @return a YSegmentedDisplay object allowing you to drive the segmented display.
     */
    public static YSegmentedDisplay FindSegmentedDisplay(String func)
    {
        YSegmentedDisplay obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YSegmentedDisplay) YFunction._FindFromCache("SegmentedDisplay", func);
            if (obj == null) {
                obj = new YSegmentedDisplay(func);
                YFunction._AddToCache("SegmentedDisplay", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a segmented display for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the segmented display is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSegmentedDisplay.isOnline() to test if the segmented display is
     * indeed online at a given time. In case of ambiguity when looking for
     * a segmented display by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the segmented display, for instance
     *         MyDevice.segmentedDisplay.
     *
     * @return a YSegmentedDisplay object allowing you to drive the segmented display.
     */
    public static YSegmentedDisplay FindSegmentedDisplayInContext(YAPIContext yctx,String func)
    {
        YSegmentedDisplay obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YSegmentedDisplay) YFunction._FindFromCacheInContext(yctx, "SegmentedDisplay", func);
            if (obj == null) {
                obj = new YSegmentedDisplay(yctx, func);
                YFunction._AddToCache("SegmentedDisplay", func, obj);
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
        _valueCallbackSegmentedDisplay = callback;
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
        if (_valueCallbackSegmentedDisplay != null) {
            _valueCallbackSegmentedDisplay.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of segmented displays started using yFirstSegmentedDisplay().
     * Caution: You can't make any assumption about the returned segmented displays order.
     * If you want to find a specific a segmented display, use SegmentedDisplay.findSegmentedDisplay()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YSegmentedDisplay object, corresponding to
     *         a segmented display currently online, or a null pointer
     *         if there are no more segmented displays to enumerate.
     */
    public YSegmentedDisplay nextSegmentedDisplay()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindSegmentedDisplayInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of segmented displays currently accessible.
     * Use the method YSegmentedDisplay.nextSegmentedDisplay() to iterate on
     * next segmented displays.
     *
     * @return a pointer to a YSegmentedDisplay object, corresponding to
     *         the first segmented display currently online, or a null pointer
     *         if there are none.
     */
    public static YSegmentedDisplay FirstSegmentedDisplay()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("SegmentedDisplay");
        if (next_hwid == null)  return null;
        return FindSegmentedDisplayInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of segmented displays currently accessible.
     * Use the method YSegmentedDisplay.nextSegmentedDisplay() to iterate on
     * next segmented displays.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YSegmentedDisplay object, corresponding to
     *         the first segmented display currently online, or a null pointer
     *         if there are none.
     */
    public static YSegmentedDisplay FirstSegmentedDisplayInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("SegmentedDisplay");
        if (next_hwid == null)  return null;
        return FindSegmentedDisplayInContext(yctx, next_hwid);
    }

    //--- (end of YSegmentedDisplay implementation)
}

