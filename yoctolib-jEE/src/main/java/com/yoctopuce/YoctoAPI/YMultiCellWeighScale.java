/*
 *
 *  $Id: YMultiCellWeighScale.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 *  Implements FindMultiCellWeighScale(), the high-level API for MultiCellWeighScale functions
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

//--- (YMultiCellWeighScale return codes)
//--- (end of YMultiCellWeighScale return codes)
//--- (YMultiCellWeighScale yapiwrapper)
//--- (end of YMultiCellWeighScale yapiwrapper)
//--- (YMultiCellWeighScale class start)
/**
 *  YMultiCellWeighScale Class: multi-cell weighing scale sensor control interface, available for
 * instance in the Yocto-MaxiBridge
 *
 * The YMultiCellWeighScale class provides a weight measurement from a set of ratiometric
 * sensors. It can be used to control the bridge excitation parameters, in order to avoid
 * measure shifts caused by temperature variation in the electronics, and can also
 * automatically apply an additional correction factor based on temperature to
 * compensate for offsets in the load cells themselves.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YMultiCellWeighScale extends YSensor
{
//--- (end of YMultiCellWeighScale class start)
//--- (YMultiCellWeighScale definitions)
    /**
     * invalid cellCount value
     */
    public static final int CELLCOUNT_INVALID = YAPI.INVALID_UINT;
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
    protected int _cellCount = CELLCOUNT_INVALID;
    protected int _excitation = EXCITATION_INVALID;
    protected double _tempAvgAdaptRatio = TEMPAVGADAPTRATIO_INVALID;
    protected double _tempChgAdaptRatio = TEMPCHGADAPTRATIO_INVALID;
    protected double _compTempAvg = COMPTEMPAVG_INVALID;
    protected double _compTempChg = COMPTEMPCHG_INVALID;
    protected double _compensation = COMPENSATION_INVALID;
    protected double _zeroTracking = ZEROTRACKING_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackMultiCellWeighScale = null;
    protected TimedReportCallback _timedReportCallbackMultiCellWeighScale = null;

    /**
     * Deprecated UpdateCallback for MultiCellWeighScale
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YMultiCellWeighScale function, String functionValue);
    }

    /**
     * TimedReportCallback for MultiCellWeighScale
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YMultiCellWeighScale  function, YMeasure measure);
    }
    //--- (end of YMultiCellWeighScale definitions)


    /**
     *
     * @param func : functionid
     */
    protected YMultiCellWeighScale(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "MultiCellWeighScale";
        //--- (YMultiCellWeighScale attributes initialization)
        //--- (end of YMultiCellWeighScale attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YMultiCellWeighScale(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YMultiCellWeighScale implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("cellCount")) {
            _cellCount = json_val.getInt("cellCount");
        }
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
     * Returns the number of load cells in use.
     *
     * @return an integer corresponding to the number of load cells in use
     *
     * @throws YAPI_Exception on error
     */
    public int get_cellCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CELLCOUNT_INVALID;
                }
            }
            res = _cellCount;
        }
        return res;
    }

    /**
     * Returns the number of load cells in use.
     *
     * @return an integer corresponding to the number of load cells in use
     *
     * @throws YAPI_Exception on error
     */
    public int getCellCount() throws YAPI_Exception
    {
        return get_cellCount();
    }

    /**
     * Changes the number of load cells in use. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the number of load cells in use
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_cellCount(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("cellCount",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of load cells in use. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the number of load cells in use
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCellCount(int newval)  throws YAPI_Exception
    {
        return set_cellCount(newval);
    }

    /**
     * Returns the current load cell bridge excitation method.
     *
     *  @return a value among YMultiCellWeighScale.EXCITATION_OFF, YMultiCellWeighScale.EXCITATION_DC and
     * YMultiCellWeighScale.EXCITATION_AC corresponding to the current load cell bridge excitation method
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
     *  @param newval : a value among YMultiCellWeighScale.EXCITATION_OFF,
     *  YMultiCellWeighScale.EXCITATION_DC and YMultiCellWeighScale.EXCITATION_AC corresponding to the
     * current load cell bridge excitation method
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
     * Retrieves a multi-cell weighing scale sensor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the multi-cell weighing scale sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMultiCellWeighScale.isOnline() to test if the multi-cell weighing scale sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a multi-cell weighing scale sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the multi-cell weighing scale sensor, for instance
     *         YWMBRDG1.multiCellWeighScale.
     *
     * @return a YMultiCellWeighScale object allowing you to drive the multi-cell weighing scale sensor.
     */
    public static YMultiCellWeighScale FindMultiCellWeighScale(String func)
    {
        YMultiCellWeighScale obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YMultiCellWeighScale) YFunction._FindFromCache("MultiCellWeighScale", func);
            if (obj == null) {
                obj = new YMultiCellWeighScale(func);
                YFunction._AddToCache("MultiCellWeighScale", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a multi-cell weighing scale sensor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the multi-cell weighing scale sensor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMultiCellWeighScale.isOnline() to test if the multi-cell weighing scale sensor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a multi-cell weighing scale sensor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the multi-cell weighing scale sensor, for instance
     *         YWMBRDG1.multiCellWeighScale.
     *
     * @return a YMultiCellWeighScale object allowing you to drive the multi-cell weighing scale sensor.
     */
    public static YMultiCellWeighScale FindMultiCellWeighScaleInContext(YAPIContext yctx,String func)
    {
        YMultiCellWeighScale obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YMultiCellWeighScale) YFunction._FindFromCacheInContext(yctx, "MultiCellWeighScale", func);
            if (obj == null) {
                obj = new YMultiCellWeighScale(yctx, func);
                YFunction._AddToCache("MultiCellWeighScale", func, obj);
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
        _valueCallbackMultiCellWeighScale = callback;
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
        if (_valueCallbackMultiCellWeighScale != null) {
            _valueCallbackMultiCellWeighScale.yNewValue(this, value);
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
        _timedReportCallbackMultiCellWeighScale = callback;
        return 0;
    }

    @Override
    public int _invokeTimedReportCallback(YMeasure value)
    {
        if (_timedReportCallbackMultiCellWeighScale != null) {
            _timedReportCallbackMultiCellWeighScale.timedReportCallback(this, value);
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
     * Configures the load cells span parameters (stored in the corresponding genericSensors)
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

    /**
     * Continues the enumeration of multi-cell weighing scale sensors started using yFirstMultiCellWeighScale().
     * Caution: You can't make any assumption about the returned multi-cell weighing scale sensors order.
     *  If you want to find a specific a multi-cell weighing scale sensor, use
     * MultiCellWeighScale.findMultiCellWeighScale()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YMultiCellWeighScale object, corresponding to
     *         a multi-cell weighing scale sensor currently online, or a null pointer
     *         if there are no more multi-cell weighing scale sensors to enumerate.
     */
    public YMultiCellWeighScale nextMultiCellWeighScale()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindMultiCellWeighScaleInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of multi-cell weighing scale sensors currently accessible.
     * Use the method YMultiCellWeighScale.nextMultiCellWeighScale() to iterate on
     * next multi-cell weighing scale sensors.
     *
     * @return a pointer to a YMultiCellWeighScale object, corresponding to
     *         the first multi-cell weighing scale sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YMultiCellWeighScale FirstMultiCellWeighScale()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("MultiCellWeighScale");
        if (next_hwid == null)  return null;
        return FindMultiCellWeighScaleInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of multi-cell weighing scale sensors currently accessible.
     * Use the method YMultiCellWeighScale.nextMultiCellWeighScale() to iterate on
     * next multi-cell weighing scale sensors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YMultiCellWeighScale object, corresponding to
     *         the first multi-cell weighing scale sensor currently online, or a null pointer
     *         if there are none.
     */
    public static YMultiCellWeighScale FirstMultiCellWeighScaleInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("MultiCellWeighScale");
        if (next_hwid == null)  return null;
        return FindMultiCellWeighScaleInContext(yctx, next_hwid);
    }

    //--- (end of YMultiCellWeighScale implementation)
}

