/*********************************************************************
 *
 * $Id: YVoc.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindVoc(), the high-level API for Voc functions
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

//--- (YVoc return codes)
//--- (end of YVoc return codes)
//--- (YVoc class start)
/**
 * YVoc Class: Voc function interface
 *
 * The Yoctopuce class YVoc allows you to read and configure Yoctopuce Volatile Organic
 * Compound sensors. It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YVoc extends YSensor
{
//--- (end of YVoc class start)
//--- (YVoc definitions)
    protected UpdateCallback _valueCallbackVoc = null;
    protected TimedReportCallback _timedReportCallbackVoc = null;

    /**
     * Deprecated UpdateCallback for Voc
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YVoc function, String functionValue);
    }

    /**
     * TimedReportCallback for Voc
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YVoc  function, YMeasure measure);
    }
    //--- (end of YVoc definitions)


    /**
     *
     * @param func : functionid
     */
    protected YVoc(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Voc";
        //--- (YVoc attributes initialization)
        //--- (end of YVoc attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YVoc(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YVoc implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        super._parseAttr(json_val);
    }

    /**
     * Retrieves a Volatile Organic Compound sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the Volatile Organic Compound sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YVoc.isOnline() to test if the Volatile Organic Compound sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a Volatile Organic Compound sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the Volatile Organic Compound sensor
     *
     * @return a YVoc object allowing you to drive the Volatile Organic Compound sensor.
     */
    public static YVoc FindVoc(String func)
    {
        YVoc obj;
        synchronized (YAPI.class) {
            obj = (YVoc) YFunction._FindFromCache("Voc", func);
            if (obj == null) {
                obj = new YVoc(func);
                YFunction._AddToCache("Voc", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a Volatile Organic Compound sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the Volatile Organic Compound sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YVoc.isOnline() to test if the Volatile Organic Compound sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a Volatile Organic Compound sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the Volatile Organic Compound sensor
     *
     * @return a YVoc object allowing you to drive the Volatile Organic Compound sensor.
     */
    public static YVoc FindVocInContext(YAPIContext yctx,String func)
    {
        YVoc obj;
        synchronized (yctx) {
            obj = (YVoc) YFunction._FindFromCacheInContext(yctx, "Voc", func);
            if (obj == null) {
                obj = new YVoc(yctx, func);
                YFunction._AddToCache("Voc", func, obj);
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
        _valueCallbackVoc = callback;
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
        if (_valueCallbackVoc != null) {
            _valueCallbackVoc.yNewValue(this, value);
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
        _timedReportCallbackVoc = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackVoc != null) {
            _timedReportCallbackVoc.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of Volatile Organic Compound sensors started using yFirstVoc().
     *
     * @return a pointer to a YVoc object, corresponding to
     *         a Volatile Organic Compound sensor currently online, or a null pointer
     *         if there are no more Volatile Organic Compound sensors to enumerate.
     */
    public YVoc nextVoc()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindVocInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of Volatile Organic Compound sensors currently accessible.
     * Use the method YVoc.nextVoc() to iterate on
     * next Volatile Organic Compound sensors.
     *
     * @return a pointer to a YVoc object, corresponding to
     *         the first Volatile Organic Compound sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YVoc FirstVoc()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Voc");
        if (next_hwid == null)  return null;
        return FindVocInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of Volatile Organic Compound sensors currently accessible.
     * Use the method YVoc.nextVoc() to iterate on
     * next Volatile Organic Compound sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YVoc object, corresponding to
     *         the first Volatile Organic Compound sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YVoc FirstVocInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Voc");
        if (next_hwid == null)  return null;
        return FindVocInContext(yctx, next_hwid);
    }

    //--- (end of YVoc implementation)
}

