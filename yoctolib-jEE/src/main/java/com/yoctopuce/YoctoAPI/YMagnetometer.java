/*********************************************************************
 *
 * $Id: YMagnetometer.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindMagnetometer(), the high-level API for Magnetometer functions
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

//--- (YMagnetometer return codes)
//--- (end of YMagnetometer return codes)
//--- (YMagnetometer class start)
/**
 * YMagnetometer Class: Magnetometer function interface
 *
 * The YSensor class is the parent class for all Yoctopuce sensors. It can be
 * used to read the current value and unit of any sensor, read the min/max
 * value, configure autonomous recording frequency and access recorded data.
 * It also provide a function to register a callback invoked each time the
 * observed value changes, or at a predefined interval. Using this class rather
 * than a specific subclass makes it possible to create generic applications
 * that work with any Yoctopuce sensor, even those that do not yet exist.
 * Note: The YAnButton class is the only analog input which does not inherit
 * from YSensor.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YMagnetometer extends YSensor
{
//--- (end of YMagnetometer class start)
//--- (YMagnetometer definitions)
    /**
     * invalid bandwidth value
     */
    public static final int BANDWIDTH_INVALID = YAPI.INVALID_INT;
    /**
     * invalid xValue value
     */
    public static final double XVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid yValue value
     */
    public static final double YVALUE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid zValue value
     */
    public static final double ZVALUE_INVALID = YAPI.INVALID_DOUBLE;
    protected int _bandwidth = BANDWIDTH_INVALID;
    protected double _xValue = XVALUE_INVALID;
    protected double _yValue = YVALUE_INVALID;
    protected double _zValue = ZVALUE_INVALID;
    protected UpdateCallback _valueCallbackMagnetometer = null;
    protected TimedReportCallback _timedReportCallbackMagnetometer = null;

    /**
     * Deprecated UpdateCallback for Magnetometer
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YMagnetometer function, String functionValue);
    }

    /**
     * TimedReportCallback for Magnetometer
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YMagnetometer  function, YMeasure measure);
    }
    //--- (end of YMagnetometer definitions)


    /**
     *
     * @param func : functionid
     */
    protected YMagnetometer(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Magnetometer";
        //--- (YMagnetometer attributes initialization)
        //--- (end of YMagnetometer attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YMagnetometer(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YMagnetometer implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("bandwidth")) {
            _bandwidth = json_val.getInt("bandwidth");
        }
        if (json_val.has("xValue")) {
            _xValue = Math.round(json_val.getDouble("xValue") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("yValue")) {
            _yValue = Math.round(json_val.getDouble("yValue") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("zValue")) {
            _zValue = Math.round(json_val.getDouble("zValue") * 1000.0 / 65536.0) / 1000.0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the measure update frequency, measured in Hz (Yocto-3D-V2 only).
     *
     * @return an integer corresponding to the measure update frequency, measured in Hz (Yocto-3D-V2 only)
     *
     * @throws YAPI_Exception on error
     */
    public int get_bandwidth() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return BANDWIDTH_INVALID;
                }
            }
            res = _bandwidth;
        }
        return res;
    }

    /**
     * Returns the measure update frequency, measured in Hz (Yocto-3D-V2 only).
     *
     * @return an integer corresponding to the measure update frequency, measured in Hz (Yocto-3D-V2 only)
     *
     * @throws YAPI_Exception on error
     */
    public int getBandwidth() throws YAPI_Exception
    {
        return get_bandwidth();
    }

    /**
     * Changes the measure update frequency, measured in Hz (Yocto-3D-V2 only). When the
     * frequency is lower, the device performs averaging.
     *
     * @param newval : an integer corresponding to the measure update frequency, measured in Hz (Yocto-3D-V2 only)
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
     * Changes the measure update frequency, measured in Hz (Yocto-3D-V2 only). When the
     * frequency is lower, the device performs averaging.
     *
     * @param newval : an integer corresponding to the measure update frequency, measured in Hz (Yocto-3D-V2 only)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setBandwidth(int newval)  throws YAPI_Exception
    {
        return set_bandwidth(newval);
    }

    /**
     * Returns the X component of the magnetic field, as a floating point number.
     *
     *  @return a floating point number corresponding to the X component of the magnetic field, as a
     * floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double get_xValue() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return XVALUE_INVALID;
                }
            }
            res = _xValue;
        }
        return res;
    }

    /**
     * Returns the X component of the magnetic field, as a floating point number.
     *
     *  @return a floating point number corresponding to the X component of the magnetic field, as a
     * floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double getXValue() throws YAPI_Exception
    {
        return get_xValue();
    }

    /**
     * Returns the Y component of the magnetic field, as a floating point number.
     *
     *  @return a floating point number corresponding to the Y component of the magnetic field, as a
     * floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double get_yValue() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return YVALUE_INVALID;
                }
            }
            res = _yValue;
        }
        return res;
    }

    /**
     * Returns the Y component of the magnetic field, as a floating point number.
     *
     *  @return a floating point number corresponding to the Y component of the magnetic field, as a
     * floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double getYValue() throws YAPI_Exception
    {
        return get_yValue();
    }

    /**
     * Returns the Z component of the magnetic field, as a floating point number.
     *
     *  @return a floating point number corresponding to the Z component of the magnetic field, as a
     * floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double get_zValue() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ZVALUE_INVALID;
                }
            }
            res = _zValue;
        }
        return res;
    }

    /**
     * Returns the Z component of the magnetic field, as a floating point number.
     *
     *  @return a floating point number corresponding to the Z component of the magnetic field, as a
     * floating point number
     *
     * @throws YAPI_Exception on error
     */
    public double getZValue() throws YAPI_Exception
    {
        return get_zValue();
    }

    /**
     * Retrieves a magnetometer for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the magnetometer is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMagnetometer.isOnline() to test if the magnetometer is
     * indeed online at a given time. In case of ambiguity when looking for
     * a magnetometer by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the magnetometer
     *
     * @return a YMagnetometer object allowing you to drive the magnetometer.
     */
    public static YMagnetometer FindMagnetometer(String func)
    {
        YMagnetometer obj;
        synchronized (YAPI.class) {
            obj = (YMagnetometer) YFunction._FindFromCache("Magnetometer", func);
            if (obj == null) {
                obj = new YMagnetometer(func);
                YFunction._AddToCache("Magnetometer", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a magnetometer for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the magnetometer is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMagnetometer.isOnline() to test if the magnetometer is
     * indeed online at a given time. In case of ambiguity when looking for
     * a magnetometer by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the magnetometer
     *
     * @return a YMagnetometer object allowing you to drive the magnetometer.
     */
    public static YMagnetometer FindMagnetometerInContext(YAPIContext yctx,String func)
    {
        YMagnetometer obj;
        synchronized (yctx) {
            obj = (YMagnetometer) YFunction._FindFromCacheInContext(yctx, "Magnetometer", func);
            if (obj == null) {
                obj = new YMagnetometer(yctx, func);
                YFunction._AddToCache("Magnetometer", func, obj);
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
        _valueCallbackMagnetometer = callback;
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
        if (_valueCallbackMagnetometer != null) {
            _valueCallbackMagnetometer.yNewValue(this, value);
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
        _timedReportCallbackMagnetometer = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackMagnetometer != null) {
            _timedReportCallbackMagnetometer.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of magnetometers started using yFirstMagnetometer().
     *
     * @return a pointer to a YMagnetometer object, corresponding to
     *         a magnetometer currently online, or a null pointer
     *         if there are no more magnetometers to enumerate.
     */
    public YMagnetometer nextMagnetometer()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindMagnetometerInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of magnetometers currently accessible.
     * Use the method YMagnetometer.nextMagnetometer() to iterate on
     * next magnetometers.
     *
     * @return a pointer to a YMagnetometer object, corresponding to
     *         the first magnetometer currently online, or a null pointer
     *         if there are none.
     */
    public static YMagnetometer FirstMagnetometer()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Magnetometer");
        if (next_hwid == null)  return null;
        return FindMagnetometerInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of magnetometers currently accessible.
     * Use the method YMagnetometer.nextMagnetometer() to iterate on
     * next magnetometers.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YMagnetometer object, corresponding to
     *         the first magnetometer currently online, or a null pointer
     *         if there are none.
     */
    public static YMagnetometer FirstMagnetometerInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Magnetometer");
        if (next_hwid == null)  return null;
        return FindMagnetometerInContext(yctx, next_hwid);
    }

    //--- (end of YMagnetometer implementation)
}

