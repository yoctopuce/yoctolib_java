/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindSoundLevel(), the high-level API for SoundLevel functions
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

//--- (YSoundLevel return codes)
//--- (end of YSoundLevel return codes)
//--- (YSoundLevel yapiwrapper)
//--- (end of YSoundLevel yapiwrapper)
//--- (YSoundLevel class start)
/**
 * YSoundLevel Class: sound pressure level meter control interface
 *
 * The YSoundLevel class allows you to read and configure Yoctopuce sound pressure level meters.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSoundLevel extends YSensor
{
//--- (end of YSoundLevel class start)
//--- (YSoundLevel definitions)
    /**
     * invalid label value
     */
    public static final String LABEL_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid integrationTime value
     */
    public static final int INTEGRATIONTIME_INVALID = YAPI.INVALID_UINT;
    protected String _label = LABEL_INVALID;
    protected int _integrationTime = INTEGRATIONTIME_INVALID;
    protected UpdateCallback _valueCallbackSoundLevel = null;
    protected TimedReportCallback _timedReportCallbackSoundLevel = null;

    /**
     * Deprecated UpdateCallback for SoundLevel
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YSoundLevel function, String functionValue);
    }

    /**
     * TimedReportCallback for SoundLevel
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YSoundLevel  function, YMeasure measure);
    }
    //--- (end of YSoundLevel definitions)


    /**
     *
     * @param func : functionid
     */
    protected YSoundLevel(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "SoundLevel";
        //--- (YSoundLevel attributes initialization)
        //--- (end of YSoundLevel attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YSoundLevel(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YSoundLevel implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("label")) {
            _label = json_val.getString("label");
        }
        if (json_val.has("integrationTime")) {
            _integrationTime = json_val.getInt("integrationTime");
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the measuring unit for the sound pressure level (dBA, dBC or dBZ).
     * That unit will directly determine frequency weighting to be used to compute
     * the measured value. Remember to call the saveToFlash() method of the
     * module if the modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the sound pressure level (dBA, dBC or dBZ)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_unit(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("unit",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the measuring unit for the sound pressure level (dBA, dBC or dBZ).
     * That unit will directly determine frequency weighting to be used to compute
     * the measured value. Remember to call the saveToFlash() method of the
     * module if the modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the sound pressure level (dBA, dBC or dBZ)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUnit(String newval)  throws YAPI_Exception
    {
        return set_unit(newval);
    }

    /**
     * Returns the label for the sound pressure level measurement, as per
     * IEC standard 61672-1:2013.
     *
     * @return a string corresponding to the label for the sound pressure level measurement, as per
     *         IEC standard 61672-1:2013
     *
     * @throws YAPI_Exception on error
     */
    public String get_label() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration == 0) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LABEL_INVALID;
                }
            }
            res = _label;
        }
        return res;
    }

    /**
     * Returns the label for the sound pressure level measurement, as per
     * IEC standard 61672-1:2013.
     *
     * @return a string corresponding to the label for the sound pressure level measurement, as per
     *         IEC standard 61672-1:2013
     *
     * @throws YAPI_Exception on error
     */
    public String getLabel() throws YAPI_Exception
    {
        return get_label();
    }

    /**
     * Returns the integration time in milliseconds for measuring the sound pressure level.
     *
     * @return an integer corresponding to the integration time in milliseconds for measuring the sound pressure level
     *
     * @throws YAPI_Exception on error
     */
    public int get_integrationTime() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return INTEGRATIONTIME_INVALID;
                }
            }
            res = _integrationTime;
        }
        return res;
    }

    /**
     * Returns the integration time in milliseconds for measuring the sound pressure level.
     *
     * @return an integer corresponding to the integration time in milliseconds for measuring the sound pressure level
     *
     * @throws YAPI_Exception on error
     */
    public int getIntegrationTime() throws YAPI_Exception
    {
        return get_integrationTime();
    }

    /**
     * Retrieves a sound pressure level meter for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the sound pressure level meter is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSoundLevel.isOnline() to test if the sound pressure level meter is
     * indeed online at a given time. In case of ambiguity when looking for
     * a sound pressure level meter by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the sound pressure level meter, for instance
     *         MyDevice.soundLevel1.
     *
     * @return a YSoundLevel object allowing you to drive the sound pressure level meter.
     */
    public static YSoundLevel FindSoundLevel(String func)
    {
        YSoundLevel obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YSoundLevel) YFunction._FindFromCache("SoundLevel", func);
            if (obj == null) {
                obj = new YSoundLevel(func);
                YFunction._AddToCache("SoundLevel", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a sound pressure level meter for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the sound pressure level meter is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSoundLevel.isOnline() to test if the sound pressure level meter is
     * indeed online at a given time. In case of ambiguity when looking for
     * a sound pressure level meter by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the sound pressure level meter, for instance
     *         MyDevice.soundLevel1.
     *
     * @return a YSoundLevel object allowing you to drive the sound pressure level meter.
     */
    public static YSoundLevel FindSoundLevelInContext(YAPIContext yctx,String func)
    {
        YSoundLevel obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YSoundLevel) YFunction._FindFromCacheInContext(yctx, "SoundLevel", func);
            if (obj == null) {
                obj = new YSoundLevel(yctx, func);
                YFunction._AddToCache("SoundLevel", func, obj);
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
        _valueCallbackSoundLevel = callback;
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
        if (_valueCallbackSoundLevel != null) {
            _valueCallbackSoundLevel.yNewValue(this, value);
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
        _timedReportCallbackSoundLevel = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackSoundLevel != null) {
            _timedReportCallbackSoundLevel.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of sound pressure level meters started using yFirstSoundLevel().
     * Caution: You can't make any assumption about the returned sound pressure level meters order.
     * If you want to find a specific a sound pressure level meter, use SoundLevel.findSoundLevel()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YSoundLevel object, corresponding to
     *         a sound pressure level meter currently online, or a null pointer
     *         if there are no more sound pressure level meters to enumerate.
     */
    public YSoundLevel nextSoundLevel()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindSoundLevelInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of sound pressure level meters currently accessible.
     * Use the method YSoundLevel.nextSoundLevel() to iterate on
     * next sound pressure level meters.
     *
     * @return a pointer to a YSoundLevel object, corresponding to
     *         the first sound pressure level meter currently online, or a null pointer
     *         if there are none.
     */
    public static YSoundLevel FirstSoundLevel()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("SoundLevel");
        if (next_hwid == null)  return null;
        return FindSoundLevelInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of sound pressure level meters currently accessible.
     * Use the method YSoundLevel.nextSoundLevel() to iterate on
     * next sound pressure level meters.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YSoundLevel object, corresponding to
     *         the first sound pressure level meter currently online, or a null pointer
     *         if there are none.
     */
    public static YSoundLevel FirstSoundLevelInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("SoundLevel");
        if (next_hwid == null)  return null;
        return FindSoundLevelInContext(yctx, next_hwid);
    }

    //--- (end of YSoundLevel implementation)
}

