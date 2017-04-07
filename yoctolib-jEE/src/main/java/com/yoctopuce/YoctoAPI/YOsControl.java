/*********************************************************************
 *
 * $Id: YOsControl.java 27053 2017-04-04 16:01:11Z seb $
 *
 * Implements FindOsControl(), the high-level API for OsControl functions
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

//--- (YOsControl return codes)
//--- (end of YOsControl return codes)
//--- (YOsControl class start)
/**
 * YOsControl Class: OS control
 *
 * The OScontrol object allows some control over the operating system running a VirtualHub.
 * OsControl is available on the VirtualHub software only. This feature must be activated at the VirtualHub
 * start up with -o option.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YOsControl extends YFunction
{
//--- (end of YOsControl class start)
//--- (YOsControl definitions)
    /**
     * invalid shutdownCountdown value
     */
    public static final int SHUTDOWNCOUNTDOWN_INVALID = YAPI.INVALID_UINT;
    protected int _shutdownCountdown = SHUTDOWNCOUNTDOWN_INVALID;
    protected UpdateCallback _valueCallbackOsControl = null;

    /**
     * Deprecated UpdateCallback for OsControl
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YOsControl function, String functionValue);
    }

    /**
     * TimedReportCallback for OsControl
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YOsControl  function, YMeasure measure);
    }
    //--- (end of YOsControl definitions)


    /**
     *
     * @param func : functionid
     */
    protected YOsControl(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "OsControl";
        //--- (YOsControl attributes initialization)
        //--- (end of YOsControl attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YOsControl(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YOsControl implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("shutdownCountdown")) {
            _shutdownCountdown = json_val.getInt("shutdownCountdown");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the remaining number of seconds before the OS shutdown, or zero when no
     * shutdown has been scheduled.
     *
     * @return an integer corresponding to the remaining number of seconds before the OS shutdown, or zero when no
     *         shutdown has been scheduled
     *
     * @throws YAPI_Exception on error
     */
    public int get_shutdownCountdown() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SHUTDOWNCOUNTDOWN_INVALID;
                }
            }
            res = _shutdownCountdown;
        }
        return res;
    }

    /**
     * Returns the remaining number of seconds before the OS shutdown, or zero when no
     * shutdown has been scheduled.
     *
     * @return an integer corresponding to the remaining number of seconds before the OS shutdown, or zero when no
     *         shutdown has been scheduled
     *
     * @throws YAPI_Exception on error
     */
    public int getShutdownCountdown() throws YAPI_Exception
    {
        return get_shutdownCountdown();
    }

    public int set_shutdownCountdown(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("shutdownCountdown",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Retrieves OS control for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the OS control is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YOsControl.isOnline() to test if the OS control is
     * indeed online at a given time. In case of ambiguity when looking for
     * OS control by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the OS control
     *
     * @return a YOsControl object allowing you to drive the OS control.
     */
    public static YOsControl FindOsControl(String func)
    {
        YOsControl obj;
        synchronized (YAPI.class) {
            obj = (YOsControl) YFunction._FindFromCache("OsControl", func);
            if (obj == null) {
                obj = new YOsControl(func);
                YFunction._AddToCache("OsControl", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves OS control for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the OS control is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YOsControl.isOnline() to test if the OS control is
     * indeed online at a given time. In case of ambiguity when looking for
     * OS control by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the OS control
     *
     * @return a YOsControl object allowing you to drive the OS control.
     */
    public static YOsControl FindOsControlInContext(YAPIContext yctx,String func)
    {
        YOsControl obj;
        synchronized (yctx) {
            obj = (YOsControl) YFunction._FindFromCacheInContext(yctx, "OsControl", func);
            if (obj == null) {
                obj = new YOsControl(yctx, func);
                YFunction._AddToCache("OsControl", func, obj);
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
        _valueCallbackOsControl = callback;
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
        if (_valueCallbackOsControl != null) {
            _valueCallbackOsControl.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Schedules an OS shutdown after a given number of seconds.
     *
     * @param secBeforeShutDown : number of seconds before shutdown
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int shutdown(int secBeforeShutDown) throws YAPI_Exception
    {
        return set_shutdownCountdown(secBeforeShutDown);
    }

    /**
     * Continues the enumeration of OS control started using yFirstOsControl().
     *
     * @return a pointer to a YOsControl object, corresponding to
     *         OS control currently online, or a null pointer
     *         if there are no more OS control to enumerate.
     */
    public YOsControl nextOsControl()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindOsControlInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of OS control currently accessible.
     * Use the method YOsControl.nextOsControl() to iterate on
     * next OS control.
     *
     * @return a pointer to a YOsControl object, corresponding to
     *         the first OS control currently online, or a null pointer
     *         if there are none.
     */
    public static YOsControl FirstOsControl()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("OsControl");
        if (next_hwid == null)  return null;
        return FindOsControlInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of OS control currently accessible.
     * Use the method YOsControl.nextOsControl() to iterate on
     * next OS control.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YOsControl object, corresponding to
     *         the first OS control currently online, or a null pointer
     *         if there are none.
     */
    public static YOsControl FirstOsControlInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("OsControl");
        if (next_hwid == null)  return null;
        return FindOsControlInContext(yctx, next_hwid);
    }

    //--- (end of YOsControl implementation)
}

