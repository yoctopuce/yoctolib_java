/*********************************************************************
 *
 * $Id: YPressure.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindPressure(), the high-level API for Pressure functions
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

//--- (YPressure return codes)
//--- (end of YPressure return codes)
//--- (YPressure class start)
/**
 * YPressure Class: Pressure function interface
 *
 * The Yoctopuce class YPressure allows you to read and configure Yoctopuce pressure
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YPressure extends YSensor
{
//--- (end of YPressure class start)
//--- (YPressure definitions)
    protected UpdateCallback _valueCallbackPressure = null;
    protected TimedReportCallback _timedReportCallbackPressure = null;

    /**
     * Deprecated UpdateCallback for Pressure
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YPressure function, String functionValue);
    }

    /**
     * TimedReportCallback for Pressure
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YPressure  function, YMeasure measure);
    }
    //--- (end of YPressure definitions)


    /**
     *
     * @param func : functionid
     */
    protected YPressure(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Pressure";
        //--- (YPressure attributes initialization)
        //--- (end of YPressure attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YPressure(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YPressure implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        super._parseAttr(json_val);
    }

    /**
     * Retrieves a pressure sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the pressure sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPressure.isOnline() to test if the pressure sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a pressure sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the pressure sensor
     *
     * @return a YPressure object allowing you to drive the pressure sensor.
     */
    public static YPressure FindPressure(String func)
    {
        YPressure obj;
        synchronized (YAPI.class) {
            obj = (YPressure) YFunction._FindFromCache("Pressure", func);
            if (obj == null) {
                obj = new YPressure(func);
                YFunction._AddToCache("Pressure", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a pressure sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the pressure sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPressure.isOnline() to test if the pressure sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a pressure sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the pressure sensor
     *
     * @return a YPressure object allowing you to drive the pressure sensor.
     */
    public static YPressure FindPressureInContext(YAPIContext yctx,String func)
    {
        YPressure obj;
        synchronized (yctx) {
            obj = (YPressure) YFunction._FindFromCacheInContext(yctx, "Pressure", func);
            if (obj == null) {
                obj = new YPressure(yctx, func);
                YFunction._AddToCache("Pressure", func, obj);
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
        _valueCallbackPressure = callback;
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
        if (_valueCallbackPressure != null) {
            _valueCallbackPressure.yNewValue(this, value);
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
        _timedReportCallbackPressure = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackPressure != null) {
            _timedReportCallbackPressure.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of pressure sensors started using yFirstPressure().
     *
     * @return a pointer to a YPressure object, corresponding to
     *         a pressure sensor currently online, or a null pointer
     *         if there are no more pressure sensors to enumerate.
     */
    public YPressure nextPressure()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindPressureInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of pressure sensors currently accessible.
     * Use the method YPressure.nextPressure() to iterate on
     * next pressure sensors.
     *
     * @return a pointer to a YPressure object, corresponding to
     *         the first pressure sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YPressure FirstPressure()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Pressure");
        if (next_hwid == null)  return null;
        return FindPressureInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of pressure sensors currently accessible.
     * Use the method YPressure.nextPressure() to iterate on
     * next pressure sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YPressure object, corresponding to
     *         the first pressure sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YPressure FirstPressureInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Pressure");
        if (next_hwid == null)  return null;
        return FindPressureInContext(yctx, next_hwid);
    }

    //--- (end of YPressure implementation)
}

