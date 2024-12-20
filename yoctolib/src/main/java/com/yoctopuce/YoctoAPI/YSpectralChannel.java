/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindSpectralChannel(), the high-level API for SpectralChannel functions
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

//--- (YSpectralChannel return codes)
//--- (end of YSpectralChannel return codes)
//--- (YSpectralChannel yapiwrapper)
//--- (end of YSpectralChannel yapiwrapper)
//--- (YSpectralChannel class start)
/**
 * YSpectralChannel Class: spectral analysis channel control interface
 *
 * The YSpectralChannel class allows you to read and configure Yoctopuce spectral analysis channels.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSpectralChannel extends YSensor
{
//--- (end of YSpectralChannel class start)
//--- (YSpectralChannel definitions)
    /**
     * invalid rawCount value
     */
    public static final int RAWCOUNT_INVALID = YAPI.INVALID_INT;
    protected int _rawCount = RAWCOUNT_INVALID;
    protected UpdateCallback _valueCallbackSpectralChannel = null;
    protected TimedReportCallback _timedReportCallbackSpectralChannel = null;

    /**
     * Deprecated UpdateCallback for SpectralChannel
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YSpectralChannel function, String functionValue);
    }

    /**
     * TimedReportCallback for SpectralChannel
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YSpectralChannel  function, YMeasure measure);
    }
    //--- (end of YSpectralChannel definitions)


    /**
     *
     * @param func : functionid
     */
    protected YSpectralChannel(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "SpectralChannel";
        //--- (YSpectralChannel attributes initialization)
        //--- (end of YSpectralChannel attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YSpectralChannel(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YSpectralChannel implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("rawCount")) {
            _rawCount = json_val.getInt("rawCount");
        }
        super._parseAttr(json_val);
    }

    /**
     * Retrieves the raw count of data samples.
     * This method returns the current value of rawCount, representing the total number of samples collected
     * by the sensor.
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int get_rawCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return RAWCOUNT_INVALID;
                }
            }
            res = _rawCount;
        }
        return res;
    }

    /**
     * Retrieves the raw count of data samples.
     * This method returns the current value of rawCount, representing the total number of samples collected
     * by the sensor.
     *
     * @return an integer
     *
     * @throws YAPI_Exception on error
     */
    public int getRawCount() throws YAPI_Exception
    {
        return get_rawCount();
    }

    /**
     * Retrieves a spectral analysis channel for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the spectral analysis channel is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSpectralChannel.isOnline() to test if the spectral analysis channel is
     * indeed online at a given time. In case of ambiguity when looking for
     * a spectral analysis channel by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the spectral analysis channel, for instance
     *         MyDevice.spectralChannel1.
     *
     * @return a YSpectralChannel object allowing you to drive the spectral analysis channel.
     */
    public static YSpectralChannel FindSpectralChannel(String func)
    {
        YSpectralChannel obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YSpectralChannel) YFunction._FindFromCache("SpectralChannel", func);
            if (obj == null) {
                obj = new YSpectralChannel(func);
                YFunction._AddToCache("SpectralChannel", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a spectral analysis channel for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the spectral analysis channel is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSpectralChannel.isOnline() to test if the spectral analysis channel is
     * indeed online at a given time. In case of ambiguity when looking for
     * a spectral analysis channel by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the spectral analysis channel, for instance
     *         MyDevice.spectralChannel1.
     *
     * @return a YSpectralChannel object allowing you to drive the spectral analysis channel.
     */
    public static YSpectralChannel FindSpectralChannelInContext(YAPIContext yctx,String func)
    {
        YSpectralChannel obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YSpectralChannel) YFunction._FindFromCacheInContext(yctx, "SpectralChannel", func);
            if (obj == null) {
                obj = new YSpectralChannel(yctx, func);
                YFunction._AddToCache("SpectralChannel", func, obj);
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
        _valueCallbackSpectralChannel = callback;
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
        if (_valueCallbackSpectralChannel != null) {
            _valueCallbackSpectralChannel.yNewValue(this, value);
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
        _timedReportCallbackSpectralChannel = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackSpectralChannel != null) {
            _timedReportCallbackSpectralChannel.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of spectral analysis channels started using yFirstSpectralChannel().
     * Caution: You can't make any assumption about the returned spectral analysis channels order.
     * If you want to find a specific a spectral analysis channel, use SpectralChannel.findSpectralChannel()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YSpectralChannel object, corresponding to
     *         a spectral analysis channel currently online, or a null pointer
     *         if there are no more spectral analysis channels to enumerate.
     */
    public YSpectralChannel nextSpectralChannel()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindSpectralChannelInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of spectral analysis channels currently accessible.
     * Use the method YSpectralChannel.nextSpectralChannel() to iterate on
     * next spectral analysis channels.
     *
     * @return a pointer to a YSpectralChannel object, corresponding to
     *         the first spectral analysis channel currently online, or a null pointer
     *         if there are none.
     */
    public static YSpectralChannel FirstSpectralChannel()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("SpectralChannel");
        if (next_hwid == null)  return null;
        return FindSpectralChannelInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of spectral analysis channels currently accessible.
     * Use the method YSpectralChannel.nextSpectralChannel() to iterate on
     * next spectral analysis channels.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YSpectralChannel object, corresponding to
     *         the first spectral analysis channel currently online, or a null pointer
     *         if there are none.
     */
    public static YSpectralChannel FirstSpectralChannelInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("SpectralChannel");
        if (next_hwid == null)  return null;
        return FindSpectralChannelInContext(yctx, next_hwid);
    }

    //--- (end of YSpectralChannel implementation)
}

