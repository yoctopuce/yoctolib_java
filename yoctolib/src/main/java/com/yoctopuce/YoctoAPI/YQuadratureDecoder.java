/*********************************************************************
 *
 * $Id: YQuadratureDecoder.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindQuadratureDecoder(), the high-level API for QuadratureDecoder functions
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

//--- (YQuadratureDecoder return codes)
//--- (end of YQuadratureDecoder return codes)
//--- (YQuadratureDecoder class start)
/**
 * YQuadratureDecoder Class: QuadratureDecoder function interface
 *
 * The class YQuadratureDecoder allows you to decode a two-wire signal produced by a
 * quadrature encoder. It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YQuadratureDecoder extends YSensor
{
//--- (end of YQuadratureDecoder class start)
//--- (YQuadratureDecoder definitions)
    /**
     * invalid speed value
     */
    public static final double SPEED_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid decoding value
     */
    public static final int DECODING_OFF = 0;
    public static final int DECODING_ON = 1;
    public static final int DECODING_INVALID = -1;
    protected double _speed = SPEED_INVALID;
    protected int _decoding = DECODING_INVALID;
    protected UpdateCallback _valueCallbackQuadratureDecoder = null;
    protected TimedReportCallback _timedReportCallbackQuadratureDecoder = null;

    /**
     * Deprecated UpdateCallback for QuadratureDecoder
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YQuadratureDecoder function, String functionValue);
    }

    /**
     * TimedReportCallback for QuadratureDecoder
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YQuadratureDecoder  function, YMeasure measure);
    }
    //--- (end of YQuadratureDecoder definitions)


    /**
     *
     * @param func : functionid
     */
    protected YQuadratureDecoder(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "QuadratureDecoder";
        //--- (YQuadratureDecoder attributes initialization)
        //--- (end of YQuadratureDecoder attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YQuadratureDecoder(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YQuadratureDecoder implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("speed")) {
            _speed = Math.round(json_val.getDouble("speed") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("decoding")) {
            _decoding = json_val.getInt("decoding") > 0 ? 1 : 0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the current expected position of the quadrature decoder.
     * Invoking this function implicitely activates the quadrature decoder.
     *
     * @param newval : a floating point number corresponding to the current expected position of the quadrature decoder
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_currentValue(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("currentValue",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current expected position of the quadrature decoder.
     * Invoking this function implicitely activates the quadrature decoder.
     *
     * @param newval : a floating point number corresponding to the current expected position of the quadrature decoder
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrentValue(double newval)  throws YAPI_Exception
    {
        return set_currentValue(newval);
    }

    /**
     * Returns the PWM frequency in Hz.
     *
     * @return a floating point number corresponding to the PWM frequency in Hz
     *
     * @throws YAPI_Exception on error
     */
    public double get_speed() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SPEED_INVALID;
                }
            }
            res = _speed;
        }
        return res;
    }

    /**
     * Returns the PWM frequency in Hz.
     *
     * @return a floating point number corresponding to the PWM frequency in Hz
     *
     * @throws YAPI_Exception on error
     */
    public double getSpeed() throws YAPI_Exception
    {
        return get_speed();
    }

    /**
     * Returns the current activation state of the quadrature decoder.
     *
     *  @return either YQuadratureDecoder.DECODING_OFF or YQuadratureDecoder.DECODING_ON, according to the
     * current activation state of the quadrature decoder
     *
     * @throws YAPI_Exception on error
     */
    public int get_decoding() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return DECODING_INVALID;
                }
            }
            res = _decoding;
        }
        return res;
    }

    /**
     * Returns the current activation state of the quadrature decoder.
     *
     *  @return either Y_DECODING_OFF or Y_DECODING_ON, according to the current activation state of the
     * quadrature decoder
     *
     * @throws YAPI_Exception on error
     */
    public int getDecoding() throws YAPI_Exception
    {
        return get_decoding();
    }

    /**
     * Changes the activation state of the quadrature decoder.
     *
     *  @param newval : either YQuadratureDecoder.DECODING_OFF or YQuadratureDecoder.DECODING_ON, according
     * to the activation state of the quadrature decoder
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_decoding(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("decoding",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation state of the quadrature decoder.
     *
     *  @param newval : either Y_DECODING_OFF or Y_DECODING_ON, according to the activation state of the
     * quadrature decoder
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDecoding(int newval)  throws YAPI_Exception
    {
        return set_decoding(newval);
    }

    /**
     * Retrieves a quadrature decoder for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the quadrature decoder is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YQuadratureDecoder.isOnline() to test if the quadrature decoder is
     * indeed online at a given time. In case of ambiguity when looking for
     * a quadrature decoder by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the quadrature decoder
     *
     * @return a YQuadratureDecoder object allowing you to drive the quadrature decoder.
     */
    public static YQuadratureDecoder FindQuadratureDecoder(String func)
    {
        YQuadratureDecoder obj;
        synchronized (YAPI.class) {
            obj = (YQuadratureDecoder) YFunction._FindFromCache("QuadratureDecoder", func);
            if (obj == null) {
                obj = new YQuadratureDecoder(func);
                YFunction._AddToCache("QuadratureDecoder", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a quadrature decoder for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the quadrature decoder is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YQuadratureDecoder.isOnline() to test if the quadrature decoder is
     * indeed online at a given time. In case of ambiguity when looking for
     * a quadrature decoder by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the quadrature decoder
     *
     * @return a YQuadratureDecoder object allowing you to drive the quadrature decoder.
     */
    public static YQuadratureDecoder FindQuadratureDecoderInContext(YAPIContext yctx,String func)
    {
        YQuadratureDecoder obj;
        synchronized (yctx) {
            obj = (YQuadratureDecoder) YFunction._FindFromCacheInContext(yctx, "QuadratureDecoder", func);
            if (obj == null) {
                obj = new YQuadratureDecoder(yctx, func);
                YFunction._AddToCache("QuadratureDecoder", func, obj);
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
        _valueCallbackQuadratureDecoder = callback;
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
        if (_valueCallbackQuadratureDecoder != null) {
            _valueCallbackQuadratureDecoder.yNewValue(this, value);
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
        _timedReportCallbackQuadratureDecoder = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackQuadratureDecoder != null) {
            _timedReportCallbackQuadratureDecoder.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of quadrature decoders started using yFirstQuadratureDecoder().
     *
     * @return a pointer to a YQuadratureDecoder object, corresponding to
     *         a quadrature decoder currently online, or a null pointer
     *         if there are no more quadrature decoders to enumerate.
     */
    public YQuadratureDecoder nextQuadratureDecoder()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindQuadratureDecoderInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of quadrature decoders currently accessible.
     * Use the method YQuadratureDecoder.nextQuadratureDecoder() to iterate on
     * next quadrature decoders.
     *
     * @return a pointer to a YQuadratureDecoder object, corresponding to
     *         the first quadrature decoder currently online, or a null pointer
     *         if there are none.
     */
    public static YQuadratureDecoder FirstQuadratureDecoder()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("QuadratureDecoder");
        if (next_hwid == null)  return null;
        return FindQuadratureDecoderInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of quadrature decoders currently accessible.
     * Use the method YQuadratureDecoder.nextQuadratureDecoder() to iterate on
     * next quadrature decoders.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YQuadratureDecoder object, corresponding to
     *         the first quadrature decoder currently online, or a null pointer
     *         if there are none.
     */
    public static YQuadratureDecoder FirstQuadratureDecoderInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("QuadratureDecoder");
        if (next_hwid == null)  return null;
        return FindQuadratureDecoderInContext(yctx, next_hwid);
    }

    //--- (end of YQuadratureDecoder implementation)
}

