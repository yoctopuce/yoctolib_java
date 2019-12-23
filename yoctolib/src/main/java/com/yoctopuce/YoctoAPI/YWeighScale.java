/*
 *
 *  $Id: YWeighScale.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 *  Implements FindWeighScale(), the high-level API for WeighScale functions
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
import java.util.Locale;
import java.util.ArrayList;

//--- (YWeighScale return codes)
//--- (end of YWeighScale return codes)
//--- (YWeighScale yapiwrapper)
//--- (end of YWeighScale yapiwrapper)
//--- (YWeighScale class start)
/**
 *  YWeighScale Class: weighing scale sensor control interface, available for instance in the
 * Yocto-Bridge or the Yocto-MaxiBridge
 *
 * The YWeighScale class provides a weight measurement from a ratiometric sensor.
 * It can be used to control the bridge excitation parameters, in order to avoid
 * measure shifts caused by temperature variation in the electronics, and can also
 * automatically apply an additional correction factor based on temperature to
 * compensate for offsets in the load cell itself.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YWeighScale extends YSensor
{
//--- (end of YWeighScale class start)
//--- (YWeighScale definitions)
    /**
     * invalid excitation value
     */
    public static final int EXCITATION_OFF = 0;
    public static final int EXCITATION_DC = 1;
    public static final int EXCITATION_AC = 2;
    public static final int EXCITATION_INVALID = -1;
    /**
     * invalid tempAvgAdaptRatio value
     */
    public static final double TEMPAVGADAPTRATIO_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid tempChgAdaptRatio value
     */
    public static final double TEMPCHGADAPTRATIO_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid compTempAvg value
     */
    public static final double COMPTEMPAVG_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid compTempChg value
     */
    public static final double COMPTEMPCHG_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid compensation value
     */
    public static final double COMPENSATION_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid zeroTracking value
     */
    public static final double ZEROTRACKING_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _excitation = EXCITATION_INVALID;
    protected double _tempAvgAdaptRatio = TEMPAVGADAPTRATIO_INVALID;
    protected double _tempChgAdaptRatio = TEMPCHGADAPTRATIO_INVALID;
    protected double _compTempAvg = COMPTEMPAVG_INVALID;
    protected double _compTempChg = COMPTEMPCHG_INVALID;
    protected double _compensation = COMPENSATION_INVALID;
    protected double _zeroTracking = ZEROTRACKING_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackWeighScale = null;
    protected TimedReportCallback _timedReportCallbackWeighScale = null;

    /**
     * Deprecated UpdateCallback for WeighScale
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YWeighScale function, String functionValue);
    }

    /**
     * TimedReportCallback for WeighScale
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YWeighScale  function, YMeasure measure);
    }
    //--- (end of YWeighScale definitions)


    /**
     *
     * @param func : functionid
     */
    protected YWeighScale(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "WeighScale";
        //--- (YWeighScale attributes initialization)
        //--- (end of YWeighScale attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YWeighScale(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YWeighScale implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("excitation")) {
            _excitation = json_val.getInt("excitation");
        }
        if (json_val.has("tempAvgAdaptRatio")) {
            _tempAvgAdaptRatio = Math.round(json_val.getDouble("tempAvgAdaptRatio") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("tempChgAdaptRatio")) {
            _tempChgAdaptRatio = Math.round(json_val.getDouble("tempChgAdaptRatio") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("compTempAvg")) {
            _compTempAvg = Math.round(json_val.getDouble("compTempAvg") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("compTempChg")) {
            _compTempChg = Math.round(json_val.getDouble("compTempChg") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("compensation")) {
            _compensation = Math.round(json_val.getDouble("compensation") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("zeroTracking")) {
            _zeroTracking = Math.round(json_val.getDouble("zeroTracking") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the measuring unit for the weight.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the weight
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
     * Changes the measuring unit for the weight.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the weight
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUnit(String newval)  throws YAPI_Exception
    {
        return set_unit(newval);
    }

    /**
     * Returns the current load cell bridge excitation method.
     *
     *  @return a value among YWeighScale.EXCITATION_OFF, YWeighScale.EXCITATION_DC and
     * YWeighScale.EXCITATION_AC corresponding to the current load cell bridge excitation method
     *
     * @throws YAPI_Exception on error
     */
    public int get_excitation() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return EXCITATION_INVALID;
                }
            }
            res = _excitation;
        }
        return res;
    }

    /**
     * Returns the current load cell bridge excitation method.
     *
     *  @return a value among Y_EXCITATION_OFF, Y_EXCITATION_DC and Y_EXCITATION_AC corresponding to the
     * current load cell bridge excitation method
     *
     * @throws YAPI_Exception on error
     */
    public int getExcitation() throws YAPI_Exception
    {
        return get_excitation();
    }

    /**
     * Changes the current load cell bridge excitation method.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     *  @param newval : a value among YWeighScale.EXCITATION_OFF, YWeighScale.EXCITATION_DC and
     * YWeighScale.EXCITATION_AC corresponding to the current load cell bridge excitation method
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_excitation(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("excitation",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current load cell bridge excitation method.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     *  @param newval : a value among Y_EXCITATION_OFF, Y_EXCITATION_DC and Y_EXCITATION_AC corresponding
     * to the current load cell bridge excitation method
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setExcitation(int newval)  throws YAPI_Exception
    {
        return set_excitation(newval);
    }

    /**
     * Changes the averaged temperature update rate, in per mille.
     * The purpose of this adaptation ratio is to model the thermal inertia of the load cell.
     * The averaged temperature is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambient temperature and the current compensation
     * temperature. The standard rate is 0.2 per mille, and the maximal rate is 65 per mille.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a floating point number corresponding to the averaged temperature update rate, in per mille
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_tempAvgAdaptRatio(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("tempAvgAdaptRatio",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the averaged temperature update rate, in per mille.
     * The purpose of this adaptation ratio is to model the thermal inertia of the load cell.
     * The averaged temperature is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambient temperature and the current compensation
     * temperature. The standard rate is 0.2 per mille, and the maximal rate is 65 per mille.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a floating point number corresponding to the averaged temperature update rate, in per mille
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setTempAvgAdaptRatio(double newval)  throws YAPI_Exception
    {
        return set_tempAvgAdaptRatio(newval);
    }

    /**
     * Returns the averaged temperature update rate, in per mille.
     * The purpose of this adaptation ratio is to model the thermal inertia of the load cell.
     * The averaged temperature is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambient temperature and the current compensation
     * temperature. The standard rate is 0.2 per mille, and the maximal rate is 65 per mille.
     *
     * @return a floating point number corresponding to the averaged temperature update rate, in per mille
     *
     * @throws YAPI_Exception on error
     */
    public double get_tempAvgAdaptRatio() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return TEMPAVGADAPTRATIO_INVALID;
                }
            }
            res = _tempAvgAdaptRatio;
        }
        return res;
    }

    /**
     * Returns the averaged temperature update rate, in per mille.
     * The purpose of this adaptation ratio is to model the thermal inertia of the load cell.
     * The averaged temperature is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambient temperature and the current compensation
     * temperature. The standard rate is 0.2 per mille, and the maximal rate is 65 per mille.
     *
     * @return a floating point number corresponding to the averaged temperature update rate, in per mille
     *
     * @throws YAPI_Exception on error
     */
    public double getTempAvgAdaptRatio() throws YAPI_Exception
    {
        return get_tempAvgAdaptRatio();
    }

    /**
     * Changes the temperature change update rate, in per mille.
     * The temperature change is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambient temperature and the current temperature used for
     * change compensation. The standard rate is 0.6 per mille, and the maximal rate is 65 per mille.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a floating point number corresponding to the temperature change update rate, in per mille
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_tempChgAdaptRatio(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("tempChgAdaptRatio",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the temperature change update rate, in per mille.
     * The temperature change is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambient temperature and the current temperature used for
     * change compensation. The standard rate is 0.6 per mille, and the maximal rate is 65 per mille.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a floating point number corresponding to the temperature change update rate, in per mille
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setTempChgAdaptRatio(double newval)  throws YAPI_Exception
    {
        return set_tempChgAdaptRatio(newval);
    }

    /**
     * Returns the temperature change update rate, in per mille.
     * The temperature change is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambient temperature and the current temperature used for
     * change compensation. The standard rate is 0.6 per mille, and the maximal rate is 65 per mille.
     *
     * @return a floating point number corresponding to the temperature change update rate, in per mille
     *
     * @throws YAPI_Exception on error
     */
    public double get_tempChgAdaptRatio() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return TEMPCHGADAPTRATIO_INVALID;
                }
            }
            res = _tempChgAdaptRatio;
        }
        return res;
    }

    /**
     * Returns the temperature change update rate, in per mille.
     * The temperature change is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambient temperature and the current temperature used for
     * change compensation. The standard rate is 0.6 per mille, and the maximal rate is 65 per mille.
     *
     * @return a floating point number corresponding to the temperature change update rate, in per mille
     *
     * @throws YAPI_Exception on error
     */
    public double getTempChgAdaptRatio() throws YAPI_Exception
    {
        return get_tempChgAdaptRatio();
    }

    /**
     * Returns the current averaged temperature, used for thermal compensation.
     *
     * @return a floating point number corresponding to the current averaged temperature, used for thermal compensation
     *
     * @throws YAPI_Exception on error
     */
    public double get_compTempAvg() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COMPTEMPAVG_INVALID;
                }
            }
            res = _compTempAvg;
        }
        return res;
    }

    /**
     * Returns the current averaged temperature, used for thermal compensation.
     *
     * @return a floating point number corresponding to the current averaged temperature, used for thermal compensation
     *
     * @throws YAPI_Exception on error
     */
    public double getCompTempAvg() throws YAPI_Exception
    {
        return get_compTempAvg();
    }

    /**
     * Returns the current temperature variation, used for thermal compensation.
     *
     *  @return a floating point number corresponding to the current temperature variation, used for
     * thermal compensation
     *
     * @throws YAPI_Exception on error
     */
    public double get_compTempChg() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COMPTEMPCHG_INVALID;
                }
            }
            res = _compTempChg;
        }
        return res;
    }

    /**
     * Returns the current temperature variation, used for thermal compensation.
     *
     *  @return a floating point number corresponding to the current temperature variation, used for
     * thermal compensation
     *
     * @throws YAPI_Exception on error
     */
    public double getCompTempChg() throws YAPI_Exception
    {
        return get_compTempChg();
    }

    /**
     * Returns the current current thermal compensation value.
     *
     * @return a floating point number corresponding to the current current thermal compensation value
     *
     * @throws YAPI_Exception on error
     */
    public double get_compensation() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COMPENSATION_INVALID;
                }
            }
            res = _compensation;
        }
        return res;
    }

    /**
     * Returns the current current thermal compensation value.
     *
     * @return a floating point number corresponding to the current current thermal compensation value
     *
     * @throws YAPI_Exception on error
     */
    public double getCompensation() throws YAPI_Exception
    {
        return get_compensation();
    }

    /**
     * Changes the zero tracking threshold value. When this threshold is larger than
     * zero, any measure under the threshold will automatically be ignored and the
     * zero compensation will be updated.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a floating point number corresponding to the zero tracking threshold value
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_zeroTracking(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("zeroTracking",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the zero tracking threshold value. When this threshold is larger than
     * zero, any measure under the threshold will automatically be ignored and the
     * zero compensation will be updated.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a floating point number corresponding to the zero tracking threshold value
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setZeroTracking(double newval)  throws YAPI_Exception
    {
        return set_zeroTracking(newval);
    }

    /**
     * Returns the zero tracking threshold value. When this threshold is larger than
     * zero, any measure under the threshold will automatically be ignored and the
     * zero compensation will be updated.
     *
     * @return a floating point number corresponding to the zero tracking threshold value
     *
     * @throws YAPI_Exception on error
     */
    public double get_zeroTracking() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ZEROTRACKING_INVALID;
                }
            }
            res = _zeroTracking;
        }
        return res;
    }

    /**
     * Returns the zero tracking threshold value. When this threshold is larger than
     * zero, any measure under the threshold will automatically be ignored and the
     * zero compensation will be updated.
     *
     * @return a floating point number corresponding to the zero tracking threshold value
     *
     * @throws YAPI_Exception on error
     */
    public double getZeroTracking() throws YAPI_Exception
    {
        return get_zeroTracking();
    }

    public String get_command() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COMMAND_INVALID;
                }
            }
            res = _command;
        }
        return res;
    }

    public int set_command(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("command",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Retrieves a weighing scale sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the weighing scale sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YWeighScale.isOnline() to test if the weighing scale sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a weighing scale sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the weighing scale sensor, for instance
     *         YWBRIDG1.weighScale1.
     *
     * @return a YWeighScale object allowing you to drive the weighing scale sensor.
     */
    public static YWeighScale FindWeighScale(String func)
    {
        YWeighScale obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YWeighScale) YFunction._FindFromCache("WeighScale", func);
            if (obj == null) {
                obj = new YWeighScale(func);
                YFunction._AddToCache("WeighScale", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a weighing scale sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the weighing scale sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YWeighScale.isOnline() to test if the weighing scale sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a weighing scale sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the weighing scale sensor, for instance
     *         YWBRIDG1.weighScale1.
     *
     * @return a YWeighScale object allowing you to drive the weighing scale sensor.
     */
    public static YWeighScale FindWeighScaleInContext(YAPIContext yctx,String func)
    {
        YWeighScale obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YWeighScale) YFunction._FindFromCacheInContext(yctx, "WeighScale", func);
            if (obj == null) {
                obj = new YWeighScale(yctx, func);
                YFunction._AddToCache("WeighScale", func, obj);
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
        _valueCallbackWeighScale = callback;
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
        if (_valueCallbackWeighScale != null) {
            _valueCallbackWeighScale.yNewValue(this, value);
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
        _timedReportCallbackWeighScale = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackWeighScale != null) {
            _timedReportCallbackWeighScale.timedReportCallback(this, value);
        } else {
            super._invokeTimedReportCallback(value);
        }
        return 0;
    }

    /**
     * Adapts the load cell signal bias (stored in the corresponding genericSensor)
     * so that the current signal corresponds to a zero weight.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int tare() throws YAPI_Exception
    {
        return set_command("T");
    }

    /**
     * Configures the load cell span parameters (stored in the corresponding genericSensor)
     * so that the current signal corresponds to the specified reference weight.
     *
     * @param currWeight : reference weight presently on the load cell.
     * @param maxWeight : maximum weight to be expected on the load cell.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setupSpan(double currWeight,double maxWeight) throws YAPI_Exception
    {
        return set_command(String.format(Locale.US, "S%d:%d", (int) (double)Math.round(1000*currWeight),(int) (double)Math.round(1000*maxWeight)));
    }

    public int setCompensationTable(int tableIndex,ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        int siz;
        int res;
        int idx;
        int found;
        double prev;
        double curr;
        double currComp;
        double idxTemp;
        siz = tempValues.size();
        //noinspection DoubleNegation
        if (!(siz != 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "thermal compensation table must have at least two points");}
        //noinspection DoubleNegation
        if (!(siz == compValues.size())) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "table sizes mismatch");}

        res = set_command(String.format(Locale.US, "%dZ",tableIndex));
        //noinspection DoubleNegation
        if (!(res==YAPI.SUCCESS)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "unable to reset thermal compensation table");}
        // add records in growing temperature value
        found = 1;
        prev = -999999.0;
        while (found > 0) {
            found = 0;
            curr = 99999999.0;
            currComp = -999999.0;
            idx = 0;
            while (idx < siz) {
                idxTemp = tempValues.get(idx).doubleValue();
                if ((idxTemp > prev) && (idxTemp < curr)) {
                    curr = idxTemp;
                    currComp = compValues.get(idx).doubleValue();
                    found = 1;
                }
                idx = idx + 1;
            }
            if (found > 0) {
                res = set_command(String.format(Locale.US, "%dm%d:%d", tableIndex, (int) (double)Math.round(1000*curr),(int) (double)Math.round(1000*currComp)));
                //noinspection DoubleNegation
                if (!(res==YAPI.SUCCESS)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "unable to set thermal compensation table");}
                prev = curr;
            }
        }
        return YAPI.SUCCESS;
    }

    public int loadCompensationTable(int tableIndex,ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        String id;
        byte[] bin_json;
        ArrayList<String> paramlist = new ArrayList<>();
        int siz;
        int idx;
        double temp;
        double comp;

        id = get_functionId();
        id = (id).substring( 10,  10 + (id).length() - 10);
        bin_json = _download(String.format(Locale.US, "extra.json?page=%d",(4*YAPIContext._atoi(id))+tableIndex));
        paramlist = _json_get_array(bin_json);
        // convert all values to float and append records
        siz = ((paramlist.size()) >> (1));
        tempValues.clear();
        compValues.clear();
        idx = 0;
        while (idx < siz) {
            temp = Double.valueOf(paramlist.get(2*idx))/1000.0;
            comp = Double.valueOf(paramlist.get(2*idx+1))/1000.0;
            tempValues.add(temp);
            compValues.add(comp);
            idx = idx + 1;
        }
        return YAPI.SUCCESS;
    }

    /**
     * Records a weight offset thermal compensation table, in order to automatically correct the
     * measured weight based on the averaged compensation temperature.
     * The weight correction will be applied by linear interpolation between specified points.
     *
     * @param tempValues : array of floating point numbers, corresponding to all averaged
     *         temperatures for which an offset correction is specified.
     * @param compValues : array of floating point numbers, corresponding to the offset correction
     *         to apply for each of the temperature included in the first
     *         argument, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_offsetAvgCompensationTable(ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        return setCompensationTable(0, tempValues, compValues);
    }

    /**
     * Retrieves the weight offset thermal compensation table previously configured using the
     * set_offsetAvgCompensationTable function.
     * The weight correction is applied by linear interpolation between specified points.
     *
     * @param tempValues : array of floating point numbers, that is filled by the function
     *         with all averaged temperatures for which an offset correction is specified.
     * @param compValues : array of floating point numbers, that is filled by the function
     *         with the offset correction applied for each of the temperature
     *         included in the first argument, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int loadOffsetAvgCompensationTable(ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        return loadCompensationTable(0, tempValues, compValues);
    }

    /**
     * Records a weight offset thermal compensation table, in order to automatically correct the
     * measured weight based on the variation of temperature.
     * The weight correction will be applied by linear interpolation between specified points.
     *
     * @param tempValues : array of floating point numbers, corresponding to temperature
     *         variations for which an offset correction is specified.
     * @param compValues : array of floating point numbers, corresponding to the offset correction
     *         to apply for each of the temperature variation included in the first
     *         argument, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_offsetChgCompensationTable(ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        return setCompensationTable(1, tempValues, compValues);
    }

    /**
     * Retrieves the weight offset thermal compensation table previously configured using the
     * set_offsetChgCompensationTable function.
     * The weight correction is applied by linear interpolation between specified points.
     *
     * @param tempValues : array of floating point numbers, that is filled by the function
     *         with all temperature variations for which an offset correction is specified.
     * @param compValues : array of floating point numbers, that is filled by the function
     *         with the offset correction applied for each of the temperature
     *         variation included in the first argument, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int loadOffsetChgCompensationTable(ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        return loadCompensationTable(1, tempValues, compValues);
    }

    /**
     * Records a weight span thermal compensation table, in order to automatically correct the
     * measured weight based on the compensation temperature.
     * The weight correction will be applied by linear interpolation between specified points.
     *
     * @param tempValues : array of floating point numbers, corresponding to all averaged
     *         temperatures for which a span correction is specified.
     * @param compValues : array of floating point numbers, corresponding to the span correction
     *         (in percents) to apply for each of the temperature included in the first
     *         argument, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_spanAvgCompensationTable(ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        return setCompensationTable(2, tempValues, compValues);
    }

    /**
     * Retrieves the weight span thermal compensation table previously configured using the
     * set_spanAvgCompensationTable function.
     * The weight correction is applied by linear interpolation between specified points.
     *
     * @param tempValues : array of floating point numbers, that is filled by the function
     *         with all averaged temperatures for which an span correction is specified.
     * @param compValues : array of floating point numbers, that is filled by the function
     *         with the span correction applied for each of the temperature
     *         included in the first argument, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int loadSpanAvgCompensationTable(ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        return loadCompensationTable(2, tempValues, compValues);
    }

    /**
     * Records a weight span thermal compensation table, in order to automatically correct the
     * measured weight based on the variation of temperature.
     * The weight correction will be applied by linear interpolation between specified points.
     *
     * @param tempValues : array of floating point numbers, corresponding to all variations of
     *         temperatures for which a span correction is specified.
     * @param compValues : array of floating point numbers, corresponding to the span correction
     *         (in percents) to apply for each of the temperature variation included
     *         in the first argument, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_spanChgCompensationTable(ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        return setCompensationTable(3, tempValues, compValues);
    }

    /**
     * Retrieves the weight span thermal compensation table previously configured using the
     * set_spanChgCompensationTable function.
     * The weight correction is applied by linear interpolation between specified points.
     *
     * @param tempValues : array of floating point numbers, that is filled by the function
     *         with all variation of temperature for which an span correction is specified.
     * @param compValues : array of floating point numbers, that is filled by the function
     *         with the span correction applied for each of variation of temperature
     *         included in the first argument, index by index.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int loadSpanChgCompensationTable(ArrayList<Double> tempValues,ArrayList<Double> compValues) throws YAPI_Exception
    {
        return loadCompensationTable(3, tempValues, compValues);
    }

    /**
     * Continues the enumeration of weighing scale sensors started using yFirstWeighScale().
     * Caution: You can't make any assumption about the returned weighing scale sensors order.
     * If you want to find a specific a weighing scale sensor, use WeighScale.findWeighScale()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YWeighScale object, corresponding to
     *         a weighing scale sensor currently online, or a null pointer
     *         if there are no more weighing scale sensors to enumerate.
     */
    public YWeighScale nextWeighScale()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindWeighScaleInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of weighing scale sensors currently accessible.
     * Use the method YWeighScale.nextWeighScale() to iterate on
     * next weighing scale sensors.
     *
     * @return a pointer to a YWeighScale object, corresponding to
     *         the first weighing scale sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YWeighScale FirstWeighScale()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("WeighScale");
        if (next_hwid == null)  return null;
        return FindWeighScaleInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of weighing scale sensors currently accessible.
     * Use the method YWeighScale.nextWeighScale() to iterate on
     * next weighing scale sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YWeighScale object, corresponding to
     *         the first weighing scale sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YWeighScale FirstWeighScaleInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("WeighScale");
        if (next_hwid == null)  return null;
        return FindWeighScaleInContext(yctx, next_hwid);
    }

    //--- (end of YWeighScale implementation)
}

