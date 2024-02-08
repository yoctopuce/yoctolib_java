/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindThreshold(), the high-level API for Threshold functions
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

//--- (YThreshold return codes)
//--- (end of YThreshold return codes)
//--- (YThreshold yapiwrapper)
//--- (end of YThreshold yapiwrapper)
//--- (YThreshold class start)
/**
 * YThreshold Class: Control interface to define a threshold
 *
 * The Threshold class allows you define a threshold on a Yoctopuce sensor
 * to trigger a predefined action, on specific devices where this is implemented.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YThreshold extends YFunction
{
//--- (end of YThreshold class start)
//--- (YThreshold definitions)
    /**
     * invalid thresholdState value
     */
    public static final int THRESHOLDSTATE_SAFE = 0;
    public static final int THRESHOLDSTATE_ALERT = 1;
    public static final int THRESHOLDSTATE_INVALID = -1;
    /**
     * invalid targetSensor value
     */
    public static final String TARGETSENSOR_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid alertLevel value
     */
    public static final double ALERTLEVEL_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid safeLevel value
     */
    public static final double SAFELEVEL_INVALID = YAPI.INVALID_DOUBLE;
    protected int _thresholdState = THRESHOLDSTATE_INVALID;
    protected String _targetSensor = TARGETSENSOR_INVALID;
    protected double _alertLevel = ALERTLEVEL_INVALID;
    protected double _safeLevel = SAFELEVEL_INVALID;
    protected UpdateCallback _valueCallbackThreshold = null;

    /**
     * Deprecated UpdateCallback for Threshold
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YThreshold function, String functionValue);
    }

    /**
     * TimedReportCallback for Threshold
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YThreshold  function, YMeasure measure);
    }
    //--- (end of YThreshold definitions)


    /**
     *
     * @param func : functionid
     */
    protected YThreshold(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Threshold";
        //--- (YThreshold attributes initialization)
        //--- (end of YThreshold attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YThreshold(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YThreshold implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("thresholdState")) {
            _thresholdState = json_val.getInt("thresholdState");
        }
        if (json_val.has("targetSensor")) {
            _targetSensor = json_val.getString("targetSensor");
        }
        if (json_val.has("alertLevel")) {
            _alertLevel = Math.round(json_val.getDouble("alertLevel") / 65.536) / 1000.0;
        }
        if (json_val.has("safeLevel")) {
            _safeLevel = Math.round(json_val.getDouble("safeLevel") / 65.536) / 1000.0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns current state of the threshold function.
     *
     *  @return either YThreshold.THRESHOLDSTATE_SAFE or YThreshold.THRESHOLDSTATE_ALERT, according to
     * current state of the threshold function
     *
     * @throws YAPI_Exception on error
     */
    public int get_thresholdState() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return THRESHOLDSTATE_INVALID;
                }
            }
            res = _thresholdState;
        }
        return res;
    }

    /**
     * Returns current state of the threshold function.
     *
     *  @return either YThreshold.THRESHOLDSTATE_SAFE or YThreshold.THRESHOLDSTATE_ALERT, according to
     * current state of the threshold function
     *
     * @throws YAPI_Exception on error
     */
    public int getThresholdState() throws YAPI_Exception
    {
        return get_thresholdState();
    }

    /**
     * Returns the name of the sensor monitored by the threshold function.
     *
     * @return a string corresponding to the name of the sensor monitored by the threshold function
     *
     * @throws YAPI_Exception on error
     */
    public String get_targetSensor() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return TARGETSENSOR_INVALID;
                }
            }
            res = _targetSensor;
        }
        return res;
    }

    /**
     * Returns the name of the sensor monitored by the threshold function.
     *
     * @return a string corresponding to the name of the sensor monitored by the threshold function
     *
     * @throws YAPI_Exception on error
     */
    public String getTargetSensor() throws YAPI_Exception
    {
        return get_targetSensor();
    }

    /**
     * Changes the sensor alert level triggering the threshold function.
     * Remember to call the matching module saveToFlash()
     * method if you want to preserve the setting after reboot.
     *
     *  @param newval : a floating point number corresponding to the sensor alert level triggering the
     * threshold function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_alertLevel(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("alertLevel",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the sensor alert level triggering the threshold function.
     * Remember to call the matching module saveToFlash()
     * method if you want to preserve the setting after reboot.
     *
     *  @param newval : a floating point number corresponding to the sensor alert level triggering the
     * threshold function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAlertLevel(double newval)  throws YAPI_Exception
    {
        return set_alertLevel(newval);
    }

    /**
     * Returns the sensor alert level, triggering the threshold function.
     *
     * @return a floating point number corresponding to the sensor alert level, triggering the threshold function
     *
     * @throws YAPI_Exception on error
     */
    public double get_alertLevel() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ALERTLEVEL_INVALID;
                }
            }
            res = _alertLevel;
        }
        return res;
    }

    /**
     * Returns the sensor alert level, triggering the threshold function.
     *
     * @return a floating point number corresponding to the sensor alert level, triggering the threshold function
     *
     * @throws YAPI_Exception on error
     */
    public double getAlertLevel() throws YAPI_Exception
    {
        return get_alertLevel();
    }

    /**
     * Changes the sensor acceptable level for disabling the threshold function.
     * Remember to call the matching module saveToFlash()
     * method if you want to preserve the setting after reboot.
     *
     *  @param newval : a floating point number corresponding to the sensor acceptable level for disabling
     * the threshold function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_safeLevel(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("safeLevel",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the sensor acceptable level for disabling the threshold function.
     * Remember to call the matching module saveToFlash()
     * method if you want to preserve the setting after reboot.
     *
     *  @param newval : a floating point number corresponding to the sensor acceptable level for disabling
     * the threshold function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSafeLevel(double newval)  throws YAPI_Exception
    {
        return set_safeLevel(newval);
    }

    /**
     * Returns the sensor acceptable level for disabling the threshold function.
     *
     *  @return a floating point number corresponding to the sensor acceptable level for disabling the
     * threshold function
     *
     * @throws YAPI_Exception on error
     */
    public double get_safeLevel() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return SAFELEVEL_INVALID;
                }
            }
            res = _safeLevel;
        }
        return res;
    }

    /**
     * Returns the sensor acceptable level for disabling the threshold function.
     *
     *  @return a floating point number corresponding to the sensor acceptable level for disabling the
     * threshold function
     *
     * @throws YAPI_Exception on error
     */
    public double getSafeLevel() throws YAPI_Exception
    {
        return get_safeLevel();
    }

    /**
     * Retrieves a threshold function for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the threshold function is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YThreshold.isOnline() to test if the threshold function is
     * indeed online at a given time. In case of ambiguity when looking for
     * a threshold function by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the threshold function, for instance
     *         MyDevice.threshold1.
     *
     * @return a YThreshold object allowing you to drive the threshold function.
     */
    public static YThreshold FindThreshold(String func)
    {
        YThreshold obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YThreshold) YFunction._FindFromCache("Threshold", func);
            if (obj == null) {
                obj = new YThreshold(func);
                YFunction._AddToCache("Threshold", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a threshold function for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the threshold function is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YThreshold.isOnline() to test if the threshold function is
     * indeed online at a given time. In case of ambiguity when looking for
     * a threshold function by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the threshold function, for instance
     *         MyDevice.threshold1.
     *
     * @return a YThreshold object allowing you to drive the threshold function.
     */
    public static YThreshold FindThresholdInContext(YAPIContext yctx,String func)
    {
        YThreshold obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YThreshold) YFunction._FindFromCacheInContext(yctx, "Threshold", func);
            if (obj == null) {
                obj = new YThreshold(yctx, func);
                YFunction._AddToCache("Threshold", func, obj);
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
        _valueCallbackThreshold = callback;
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
        if (_valueCallbackThreshold != null) {
            _valueCallbackThreshold.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of threshold functions started using yFirstThreshold().
     * Caution: You can't make any assumption about the returned threshold functions order.
     * If you want to find a specific a threshold function, use Threshold.findThreshold()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YThreshold object, corresponding to
     *         a threshold function currently online, or a null pointer
     *         if there are no more threshold functions to enumerate.
     */
    public YThreshold nextThreshold()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindThresholdInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of threshold functions currently accessible.
     * Use the method YThreshold.nextThreshold() to iterate on
     * next threshold functions.
     *
     * @return a pointer to a YThreshold object, corresponding to
     *         the first threshold function currently online, or a null pointer
     *         if there are none.
     */
    public static YThreshold FirstThreshold()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Threshold");
        if (next_hwid == null)  return null;
        return FindThresholdInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of threshold functions currently accessible.
     * Use the method YThreshold.nextThreshold() to iterate on
     * next threshold functions.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YThreshold object, corresponding to
     *         the first threshold function currently online, or a null pointer
     *         if there are none.
     */
    public static YThreshold FirstThresholdInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Threshold");
        if (next_hwid == null)  return null;
        return FindThresholdInContext(yctx, next_hwid);
    }

    //--- (end of YThreshold implementation)
}

