/*********************************************************************
 *
 * $Id: YQt.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements yFindQt(), the high-level API for Qt functions
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

//--- (generated code: YQt return codes)
//--- (end of generated code: YQt return codes)
//--- (generated code: YQt class start)
/**
 * YQt Class: Quaternion interface
 *
 * The Yoctopuce API YQt class provides direct access to the Yocto3D attitude estimation
 * using a quaternion. It is usually not needed to use the YQt class directly, as the
 * YGyro class provides a more convenient higher-level interface.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YQt extends YSensor
{
//--- (end of generated code: YQt class start)
//--- (generated code: YQt definitions)
    protected UpdateCallback _valueCallbackQt = null;
    protected TimedReportCallback _timedReportCallbackQt = null;

    /**
     * Deprecated UpdateCallback for Qt
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YQt function, String functionValue);
    }

    /**
     * TimedReportCallback for Qt
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YQt  function, YMeasure measure);
    }
    //--- (end of generated code: YQt definitions)


    /**
     * @param func : functionid
     */
    protected YQt(YAPIContext yctx, String func)
    {
        super(yctx, func);
        _className = "Qt";
        //--- (generated code: YQt attributes initialization)
        //--- (end of generated code: YQt attributes initialization)
    }
    protected YQt(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }



    //--- (generated code: YQt implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        super._parseAttr(json_val);
    }

    /**
     * Retrieves a quaternion component for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the quaternion component is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YQt.isOnline() to test if the quaternion component is
     * indeed online at a given time. In case of ambiguity when looking for
     * a quaternion component by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the quaternion component
     *
     * @return a YQt object allowing you to drive the quaternion component.
     */
    public static YQt FindQt(String func)
    {
        YQt obj;
        synchronized (YAPI.class) {
            obj = (YQt) YFunction._FindFromCache("Qt", func);
            if (obj == null) {
                obj = new YQt(func);
                YFunction._AddToCache("Qt", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a quaternion component for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the quaternion component is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YQt.isOnline() to test if the quaternion component is
     * indeed online at a given time. In case of ambiguity when looking for
     * a quaternion component by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the quaternion component
     *
     * @return a YQt object allowing you to drive the quaternion component.
     */
    public static YQt FindQtInContext(YAPIContext yctx,String func)
    {
        YQt obj;
        synchronized (yctx) {
            obj = (YQt) YFunction._FindFromCacheInContext(yctx, "Qt", func);
            if (obj == null) {
                obj = new YQt(yctx, func);
                YFunction._AddToCache("Qt", func, obj);
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
        _valueCallbackQt = callback;
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
        if (_valueCallbackQt != null) {
            _valueCallbackQt.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Registers the callback function that is invoked on every periodic timed notification.
     * The callback is invoked only during the execution of ySleep or yHandleEvents.
     * This provides control over the time when the callback is triggered. For good responsiveness, remember to call
     * one of these two functions periodically. To unregister a callback, pass a null pointer as argument.
     *
     * @param callback : the callback function to call, or a null pointer. The callback function should take two
     *         arguments: the function object of which the value has changed, and an YMeasure object describing
     *         the new advertised value.
     *
     */
    public int registerTimedReportCallback(TimedReportCallback callback)
    {
        YSensor sensor;
        sensor = this;
        if (callback != null) {
            YFunction._UpdateTimedReportCallbackList(sensor, true);
        } else {
            YFunction._UpdateTimedReportCallbackList(sensor, false);
        }
        _timedReportCallbackQt = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackQt != null) {
            _timedReportCallbackQt.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of quaternion components started using yFirstQt().
     *
     * @return a pointer to a YQt object, corresponding to
     *         a quaternion component currently online, or a null pointer
     *         if there are no more quaternion components to enumerate.
     */
    public YQt nextQt()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindQtInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of quaternion components currently accessible.
     * Use the method YQt.nextQt() to iterate on
     * next quaternion components.
     *
     * @return a pointer to a YQt object, corresponding to
     *         the first quaternion component currently online, or a null pointer
     *         if there are none.
     */
    public static YQt FirstQt()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Qt");
        if (next_hwid == null)  return null;
        return FindQtInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of quaternion components currently accessible.
     * Use the method YQt.nextQt() to iterate on
     * next quaternion components.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YQt object, corresponding to
     *         the first quaternion component currently online, or a null pointer
     *         if there are none.
     */
    public static YQt FirstQtInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Qt");
        if (next_hwid == null)  return null;
        return FindQtInContext(yctx, next_hwid);
    }

    //--- (end of generated code: YQt implementation)
}

