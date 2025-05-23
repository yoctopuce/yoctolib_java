/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindCompass(), the high-level API for Compass functions
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

//--- (YCompass return codes)
//--- (end of YCompass return codes)
//--- (YCompass yapiwrapper)
//--- (end of YCompass yapiwrapper)
//--- (YCompass class start)
/**
 * YCompass Class: compass function control interface, available for instance in the Yocto-3D-V2
 *
 * The YCompass class allows you to read and configure Yoctopuce compass functions.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YCompass extends YSensor
{
//--- (end of YCompass class start)
//--- (YCompass definitions)
    /**
     * invalid bandwidth value
     */
    public static final int BANDWIDTH_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid axis value
     */
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int AXIS_Z = 2;
    public static final int AXIS_INVALID = -1;
    /**
     * invalid magneticHeading value
     */
    public static final double MAGNETICHEADING_INVALID = YAPI.INVALID_DOUBLE;
    protected int _bandwidth = BANDWIDTH_INVALID;
    protected int _axis = AXIS_INVALID;
    protected double _magneticHeading = MAGNETICHEADING_INVALID;
    protected UpdateCallback _valueCallbackCompass = null;
    protected TimedReportCallback _timedReportCallbackCompass = null;

    /**
     * Deprecated UpdateCallback for Compass
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YCompass function, String functionValue);
    }

    /**
     * TimedReportCallback for Compass
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YCompass  function, YMeasure measure);
    }
    //--- (end of YCompass definitions)


    /**
     *
     * @param func : functionid
     */
    protected YCompass(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Compass";
        //--- (YCompass attributes initialization)
        //--- (end of YCompass attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YCompass(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YCompass implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("bandwidth")) {
            _bandwidth = json_val.getInt("bandwidth");
        }
        if (json_val.has("axis")) {
            _axis = json_val.getInt("axis");
        }
        if (json_val.has("magneticHeading")) {
            _magneticHeading = Math.round(json_val.getDouble("magneticHeading") / 65.536) / 1000.0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the measure update frequency, measured in Hz.
     *
     * @return an integer corresponding to the measure update frequency, measured in Hz
     *
     * @throws YAPI_Exception on error
     */
    public int get_bandwidth() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BANDWIDTH_INVALID;
                }
            }
            res = _bandwidth;
        }
        return res;
    }

    /**
     * Returns the measure update frequency, measured in Hz.
     *
     * @return an integer corresponding to the measure update frequency, measured in Hz
     *
     * @throws YAPI_Exception on error
     */
    public int getBandwidth() throws YAPI_Exception
    {
        return get_bandwidth();
    }

    /**
     * Changes the measure update frequency, measured in Hz. When the
     * frequency is lower, the device performs averaging.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the measure update frequency, measured in Hz
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_bandwidth(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("bandwidth",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the measure update frequency, measured in Hz. When the
     * frequency is lower, the device performs averaging.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the measure update frequency, measured in Hz
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setBandwidth(int newval)  throws YAPI_Exception
    {
        return set_bandwidth(newval);
    }

    public int get_axis() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return AXIS_INVALID;
                }
            }
            res = _axis;
        }
        return res;
    }

    /**
     * Returns the magnetic heading, regardless of the configured bearing.
     *
     * @return a floating point number corresponding to the magnetic heading, regardless of the configured bearing
     *
     * @throws YAPI_Exception on error
     */
    public double get_magneticHeading() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return MAGNETICHEADING_INVALID;
                }
            }
            res = _magneticHeading;
        }
        return res;
    }

    /**
     * Returns the magnetic heading, regardless of the configured bearing.
     *
     * @return a floating point number corresponding to the magnetic heading, regardless of the configured bearing
     *
     * @throws YAPI_Exception on error
     */
    public double getMagneticHeading() throws YAPI_Exception
    {
        return get_magneticHeading();
    }

    /**
     * Retrieves a compass function for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the compass function is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCompass.isOnline() to test if the compass function is
     * indeed online at a given time. In case of ambiguity when looking for
     * a compass function by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the compass function, for instance
     *         Y3DMK002.compass.
     *
     * @return a YCompass object allowing you to drive the compass function.
     */
    public static YCompass FindCompass(String func)
    {
        YCompass obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YCompass) YFunction._FindFromCache("Compass", func);
            if (obj == null) {
                obj = new YCompass(func);
                YFunction._AddToCache("Compass", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a compass function for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the compass function is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCompass.isOnline() to test if the compass function is
     * indeed online at a given time. In case of ambiguity when looking for
     * a compass function by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the compass function, for instance
     *         Y3DMK002.compass.
     *
     * @return a YCompass object allowing you to drive the compass function.
     */
    public static YCompass FindCompassInContext(YAPIContext yctx,String func)
    {
        YCompass obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YCompass) YFunction._FindFromCacheInContext(yctx, "Compass", func);
            if (obj == null) {
                obj = new YCompass(yctx, func);
                YFunction._AddToCache("Compass", func, obj);
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
        _valueCallbackCompass = callback;
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
        if (_valueCallbackCompass != null) {
            _valueCallbackCompass.yNewValue(this, value);
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
        _timedReportCallbackCompass = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackCompass != null) {
            _timedReportCallbackCompass.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of compass functions started using yFirstCompass().
     * Caution: You can't make any assumption about the returned compass functions order.
     * If you want to find a specific a compass function, use Compass.findCompass()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YCompass object, corresponding to
     *         a compass function currently online, or a null pointer
     *         if there are no more compass functions to enumerate.
     */
    public YCompass nextCompass()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindCompassInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of compass functions currently accessible.
     * Use the method YCompass.nextCompass() to iterate on
     * next compass functions.
     *
     * @return a pointer to a YCompass object, corresponding to
     *         the first compass function currently online, or a null pointer
     *         if there are none.
     */
    public static YCompass FirstCompass()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Compass");
        if (next_hwid == null)  return null;
        return FindCompassInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of compass functions currently accessible.
     * Use the method YCompass.nextCompass() to iterate on
     * next compass functions.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YCompass object, corresponding to
     *         the first compass function currently online, or a null pointer
     *         if there are none.
     */
    public static YCompass FirstCompassInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Compass");
        if (next_hwid == null)  return null;
        return FindCompassInContext(yctx, next_hwid);
    }

    //--- (end of YCompass implementation)
}

