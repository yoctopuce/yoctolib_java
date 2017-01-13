/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements FindProximity(), the high-level API for Proximity functions
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
import org.json.JSONException;
import org.json.JSONObject;

//--- (YProximity return codes)
//--- (end of YProximity return codes)
//--- (YProximity class start)
/**
 * YProximity Class: Proximity function interface
 *
 * The Yoctopuce class YProximity allows you to use and configure Yoctopuce proximity
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to easily perform a one-point linear calibration
 * to compensate the effect of a glass or filter placed in front of the sensor.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YProximity extends YSensor
{
//--- (end of YProximity class start)
//--- (YProximity definitions)
    /**
     * invalid detectionThreshold value
     */
    public static final int DETECTIONTHRESHOLD_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid isPresent value
     */
    public static final int ISPRESENT_FALSE = 0;
    public static final int ISPRESENT_TRUE = 1;
    public static final int ISPRESENT_INVALID = -1;
    /**
     * invalid lastTimeApproached value
     */
    public static final long LASTTIMEAPPROACHED_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid lastTimeRemoved value
     */
    public static final long LASTTIMEREMOVED_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid pulseCounter value
     */
    public static final long PULSECOUNTER_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid pulseTimer value
     */
    public static final long PULSETIMER_INVALID = YAPI.INVALID_LONG;
    protected int _detectionThreshold = DETECTIONTHRESHOLD_INVALID;
    protected int _isPresent = ISPRESENT_INVALID;
    protected long _lastTimeApproached = LASTTIMEAPPROACHED_INVALID;
    protected long _lastTimeRemoved = LASTTIMEREMOVED_INVALID;
    protected long _pulseCounter = PULSECOUNTER_INVALID;
    protected long _pulseTimer = PULSETIMER_INVALID;
    protected UpdateCallback _valueCallbackProximity = null;
    protected TimedReportCallback _timedReportCallbackProximity = null;

    /**
     * Deprecated UpdateCallback for Proximity
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YProximity function, String functionValue);
    }

    /**
     * TimedReportCallback for Proximity
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YProximity  function, YMeasure measure);
    }
    //--- (end of YProximity definitions)


    /**
     *
     * @param func : functionid
     */
    protected YProximity(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Proximity";
        //--- (YProximity attributes initialization)
        //--- (end of YProximity attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YProximity(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YProximity implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("detectionThreshold")) {
            _detectionThreshold = json_val.getInt("detectionThreshold");
        }
        if (json_val.has("isPresent")) {
            _isPresent = json_val.getInt("isPresent") > 0 ? 1 : 0;
        }
        if (json_val.has("lastTimeApproached")) {
            _lastTimeApproached = json_val.getLong("lastTimeApproached");
        }
        if (json_val.has("lastTimeRemoved")) {
            _lastTimeRemoved = json_val.getLong("lastTimeRemoved");
        }
        if (json_val.has("pulseCounter")) {
            _pulseCounter = json_val.getLong("pulseCounter");
        }
        if (json_val.has("pulseTimer")) {
            _pulseTimer = json_val.getLong("pulseTimer");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the threshold used to determine the logical state of the proximity sensor, when considered
     * as a binary input (on/off).
     *
     *  @return an integer corresponding to the threshold used to determine the logical state of the
     * proximity sensor, when considered
     *         as a binary input (on/off)
     *
     * @throws YAPI_Exception on error
     */
    public int get_detectionThreshold() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPIContext.GetTickCount()) {
            if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                return DETECTIONTHRESHOLD_INVALID;
            }
        }
        return _detectionThreshold;
    }

    /**
     * Returns the threshold used to determine the logical state of the proximity sensor, when considered
     * as a binary input (on/off).
     *
     *  @return an integer corresponding to the threshold used to determine the logical state of the
     * proximity sensor, when considered
     *         as a binary input (on/off)
     *
     * @throws YAPI_Exception on error
     */
    public int getDetectionThreshold() throws YAPI_Exception
    {
        return get_detectionThreshold();
    }

    /**
     * Changes the threshold used to determine the logical state of the proximity sensor, when considered
     * as a binary input (on/off).
     *
     *  @param newval : an integer corresponding to the threshold used to determine the logical state of
     * the proximity sensor, when considered
     *         as a binary input (on/off)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_detectionThreshold(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("detectionThreshold",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the threshold used to determine the logical state of the proximity sensor, when considered
     * as a binary input (on/off).
     *
     *  @param newval : an integer corresponding to the threshold used to determine the logical state of
     * the proximity sensor, when considered
     *         as a binary input (on/off)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDetectionThreshold(int newval)  throws YAPI_Exception
    {
        return set_detectionThreshold(newval);
    }

    /**
     *  Returns true if the input (considered as binary) is active (detection value is smaller than the
     * specified threshold), and false otherwise.
     *
     *  @return either YProximity.ISPRESENT_FALSE or YProximity.ISPRESENT_TRUE, according to true if the
     *  input (considered as binary) is active (detection value is smaller than the specified threshold),
     * and false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int get_isPresent() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPIContext.GetTickCount()) {
            if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                return ISPRESENT_INVALID;
            }
        }
        return _isPresent;
    }

    /**
     *  Returns true if the input (considered as binary) is active (detection value is smaller than the
     * specified threshold), and false otherwise.
     *
     *  @return either Y_ISPRESENT_FALSE or Y_ISPRESENT_TRUE, according to true if the input (considered as
     * binary) is active (detection value is smaller than the specified threshold), and false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int getIsPresent() throws YAPI_Exception
    {
        return get_isPresent();
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last observed
     * detection (the input contact transitioned from absent to present).
     *
     *  @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last observed
     *         detection (the input contact transitioned from absent to present)
     *
     * @throws YAPI_Exception on error
     */
    public long get_lastTimeApproached() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPIContext.GetTickCount()) {
            if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                return LASTTIMEAPPROACHED_INVALID;
            }
        }
        return _lastTimeApproached;
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last observed
     * detection (the input contact transitioned from absent to present).
     *
     *  @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last observed
     *         detection (the input contact transitioned from absent to present)
     *
     * @throws YAPI_Exception on error
     */
    public long getLastTimeApproached() throws YAPI_Exception
    {
        return get_lastTimeApproached();
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last observed
     * detection (the input contact transitioned from present to absent).
     *
     *  @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last observed
     *         detection (the input contact transitioned from present to absent)
     *
     * @throws YAPI_Exception on error
     */
    public long get_lastTimeRemoved() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPIContext.GetTickCount()) {
            if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                return LASTTIMEREMOVED_INVALID;
            }
        }
        return _lastTimeRemoved;
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last observed
     * detection (the input contact transitioned from present to absent).
     *
     *  @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last observed
     *         detection (the input contact transitioned from present to absent)
     *
     * @throws YAPI_Exception on error
     */
    public long getLastTimeRemoved() throws YAPI_Exception
    {
        return get_lastTimeRemoved();
    }

    /**
     * Returns the pulse counter value. The value is a 32 bit integer. In case
     * of overflow (>=2^32), the counter will wrap. To reset the counter, just
     * call the resetCounter() method.
     *
     * @return an integer corresponding to the pulse counter value
     *
     * @throws YAPI_Exception on error
     */
    public long get_pulseCounter() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPIContext.GetTickCount()) {
            if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                return PULSECOUNTER_INVALID;
            }
        }
        return _pulseCounter;
    }

    /**
     * Returns the pulse counter value. The value is a 32 bit integer. In case
     * of overflow (>=2^32), the counter will wrap. To reset the counter, just
     * call the resetCounter() method.
     *
     * @return an integer corresponding to the pulse counter value
     *
     * @throws YAPI_Exception on error
     */
    public long getPulseCounter() throws YAPI_Exception
    {
        return get_pulseCounter();
    }

    public int set_pulseCounter(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("pulseCounter",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPulseCounter(long newval)  throws YAPI_Exception
    {
        return set_pulseCounter(newval);
    }

    /**
     * Returns the timer of the pulses counter (ms).
     *
     * @return an integer corresponding to the timer of the pulses counter (ms)
     *
     * @throws YAPI_Exception on error
     */
    public long get_pulseTimer() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPIContext.GetTickCount()) {
            if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                return PULSETIMER_INVALID;
            }
        }
        return _pulseTimer;
    }

    /**
     * Returns the timer of the pulses counter (ms).
     *
     * @return an integer corresponding to the timer of the pulses counter (ms)
     *
     * @throws YAPI_Exception on error
     */
    public long getPulseTimer() throws YAPI_Exception
    {
        return get_pulseTimer();
    }

    /**
     * Retrieves a proximity sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the proximity sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YProximity.isOnline() to test if the proximity sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a proximity sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the proximity sensor
     *
     * @return a YProximity object allowing you to drive the proximity sensor.
     */
    public static YProximity FindProximity(String func)
    {
        YProximity obj;
        obj = (YProximity) YFunction._FindFromCache("Proximity", func);
        if (obj == null) {
            obj = new YProximity(func);
            YFunction._AddToCache("Proximity", func, obj);
        }
        return obj;
    }

    /**
     * Retrieves a proximity sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the proximity sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YProximity.isOnline() to test if the proximity sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a proximity sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the proximity sensor
     *
     * @return a YProximity object allowing you to drive the proximity sensor.
     */
    public static YProximity FindProximityInContext(YAPIContext yctx,String func)
    {
        YProximity obj;
        obj = (YProximity) YFunction._FindFromCacheInContext(yctx, "Proximity", func);
        if (obj == null) {
            obj = new YProximity(yctx, func);
            YFunction._AddToCache("Proximity", func, obj);
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
        _valueCallbackProximity = callback;
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
        if (_valueCallbackProximity != null) {
            _valueCallbackProximity.yNewValue(this, value);
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
        _timedReportCallbackProximity = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackProximity != null) {
            _timedReportCallbackProximity.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Returns the pulse counter value as well as its timer.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int resetCounter() throws YAPI_Exception
    {
        return set_pulseCounter(0);
    }

    /**
     * Continues the enumeration of proximity sensors started using yFirstProximity().
     *
     * @return a pointer to a YProximity object, corresponding to
     *         a proximity sensor currently online, or a null pointer
     *         if there are no more proximity sensors to enumerate.
     */
    public YProximity nextProximity()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindProximityInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of proximity sensors currently accessible.
     * Use the method YProximity.nextProximity() to iterate on
     * next proximity sensors.
     *
     * @return a pointer to a YProximity object, corresponding to
     *         the first proximity sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YProximity FirstProximity()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Proximity");
        if (next_hwid == null)  return null;
        return FindProximityInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of proximity sensors currently accessible.
     * Use the method YProximity.nextProximity() to iterate on
     * next proximity sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YProximity object, corresponding to
     *         the first proximity sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YProximity FirstProximityInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Proximity");
        if (next_hwid == null)  return null;
        return FindProximityInContext(yctx, next_hwid);
    }

    //--- (end of YProximity implementation)
}

