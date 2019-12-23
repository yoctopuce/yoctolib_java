/*********************************************************************
 * $Id: YDataLogger.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 * Implements yFindDataLogger(), the high-level API for DataLogger functions
 *
 * - - - - - - - - - License information: - - - - - - - - -
 *
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 * Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 * non-exclusive license to use, modify, copy and integrate this
 * file into your software for the sole purpose of interfacing
 * with Yoctopuce products.
 *
 * You may reproduce and distribute copies of this file in
 * source or object form, as long as the sole purpose of this
 * code is to interface with Yoctopuce products. You must retain
 * this notice in the distributed source file.
 *
 * You should refer to Yoctopuce General Terms and Conditions
 * for additional information regarding your rights and
 * obligations.
 *
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING
 * WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 * EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 * COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 * SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 * LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 * CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 * BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 * WARRANTY, OR OTHERWISE.
 *********************************************************************/

package com.yoctopuce.YoctoAPI; //test


import java.util.ArrayList;
import java.util.Locale;


//--- (generated code: YDataLogger class start)
/**
 * YDataLogger Class: DataLogger control interface, available on most Yoctopuce sensors.
 *
 * A non-volatile memory for storing ongoing measured data is available on most Yoctopuce
 * sensors. Recording can happen automatically, without requiring a permanent
 * connection to a computer.
 * The YDataLogger class controls the global parameters of the internal data
 * logger. Recording control (start/stop) as well as data retreival is done at
 * sensor objects level.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YDataLogger extends YFunction
{
//--- (end of generated code: YDataLogger class start)
    //--- (generated code: YDataLogger definitions)
    /**
     * invalid currentRunIndex value
     */
    public static final int CURRENTRUNINDEX_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid timeUTC value
     */
    public static final long TIMEUTC_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid recording value
     */
    public static final int RECORDING_OFF = 0;
    public static final int RECORDING_ON = 1;
    public static final int RECORDING_PENDING = 2;
    public static final int RECORDING_INVALID = -1;
    /**
     * invalid autoStart value
     */
    public static final int AUTOSTART_OFF = 0;
    public static final int AUTOSTART_ON = 1;
    public static final int AUTOSTART_INVALID = -1;
    /**
     * invalid beaconDriven value
     */
    public static final int BEACONDRIVEN_OFF = 0;
    public static final int BEACONDRIVEN_ON = 1;
    public static final int BEACONDRIVEN_INVALID = -1;
    /**
     * invalid usage value
     */
    public static final int USAGE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid clearHistory value
     */
    public static final int CLEARHISTORY_FALSE = 0;
    public static final int CLEARHISTORY_TRUE = 1;
    public static final int CLEARHISTORY_INVALID = -1;
    protected int _currentRunIndex = CURRENTRUNINDEX_INVALID;
    protected long _timeUTC = TIMEUTC_INVALID;
    protected int _recording = RECORDING_INVALID;
    protected int _autoStart = AUTOSTART_INVALID;
    protected int _beaconDriven = BEACONDRIVEN_INVALID;
    protected int _usage = USAGE_INVALID;
    protected int _clearHistory = CLEARHISTORY_INVALID;
    protected UpdateCallback _valueCallbackDataLogger = null;

    /**
     * Deprecated UpdateCallback for DataLogger
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YDataLogger function, String functionValue);
    }

    /**
     * TimedReportCallback for DataLogger
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YDataLogger  function, YMeasure measure);
    }
    //--- (end of generated code: YDataLogger definitions)

    protected String _dataLoggerURL;

    /**
     * Internal function to retrieve datalogger memory
     */
    public String getData(Integer runIdx, Integer timeIdx) throws YAPI_Exception
    {
        if (_dataLoggerURL == null) {
            _dataLoggerURL = "/logger.json";
        }
        // get the device serial number
        String devid = this.module().get_serialNumber();
        String httpreq = "GET " + _dataLoggerURL;
        if (timeIdx != null) {
            httpreq += String.format(Locale.US, "?run=%d&time=%d", runIdx, timeIdx);
        }
        String result;
        YDevice dev = _yapi._yHash.getDevice(devid);
        result = dev.requestHTTPSyncAsString(httpreq, null);
        return  result;
    }


    /**
     * @param func : functionid
     */
    protected YDataLogger(YAPIContext yctx, String func)
    {
        super(yctx, func);
        _className = "DataLogger";
        //--- (generated code: YDataLogger attributes initialization)
        //--- (end of generated code: YDataLogger attributes initialization)
    }

    protected YDataLogger(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (generated code: YDataLogger implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("currentRunIndex")) {
            _currentRunIndex = json_val.getInt("currentRunIndex");
        }
        if (json_val.has("timeUTC")) {
            _timeUTC = json_val.getLong("timeUTC");
        }
        if (json_val.has("recording")) {
            _recording = json_val.getInt("recording");
        }
        if (json_val.has("autoStart")) {
            _autoStart = json_val.getInt("autoStart") > 0 ? 1 : 0;
        }
        if (json_val.has("beaconDriven")) {
            _beaconDriven = json_val.getInt("beaconDriven") > 0 ? 1 : 0;
        }
        if (json_val.has("usage")) {
            _usage = json_val.getInt("usage");
        }
        if (json_val.has("clearHistory")) {
            _clearHistory = json_val.getInt("clearHistory") > 0 ? 1 : 0;
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current run number, corresponding to the number of times the module was
     * powered on with the dataLogger enabled at some point.
     *
     * @return an integer corresponding to the current run number, corresponding to the number of times the module was
     *         powered on with the dataLogger enabled at some point
     *
     * @throws YAPI_Exception on error
     */
    public int get_currentRunIndex() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENTRUNINDEX_INVALID;
                }
            }
            res = _currentRunIndex;
        }
        return res;
    }

    /**
     * Returns the current run number, corresponding to the number of times the module was
     * powered on with the dataLogger enabled at some point.
     *
     * @return an integer corresponding to the current run number, corresponding to the number of times the module was
     *         powered on with the dataLogger enabled at some point
     *
     * @throws YAPI_Exception on error
     */
    public int getCurrentRunIndex() throws YAPI_Exception
    {
        return get_currentRunIndex();
    }

    /**
     * Returns the Unix timestamp for current UTC time, if known.
     *
     * @return an integer corresponding to the Unix timestamp for current UTC time, if known
     *
     * @throws YAPI_Exception on error
     */
    public long get_timeUTC() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return TIMEUTC_INVALID;
                }
            }
            res = _timeUTC;
        }
        return res;
    }

    /**
     * Returns the Unix timestamp for current UTC time, if known.
     *
     * @return an integer corresponding to the Unix timestamp for current UTC time, if known
     *
     * @throws YAPI_Exception on error
     */
    public long getTimeUTC() throws YAPI_Exception
    {
        return get_timeUTC();
    }

    /**
     * Changes the current UTC time reference used for recorded data.
     *
     * @param newval : an integer corresponding to the current UTC time reference used for recorded data
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_timeUTC(long  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(newval);
            _setAttr("timeUTC",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current UTC time reference used for recorded data.
     *
     * @param newval : an integer corresponding to the current UTC time reference used for recorded data
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setTimeUTC(long newval)  throws YAPI_Exception
    {
        return set_timeUTC(newval);
    }

    /**
     * Returns the current activation state of the data logger.
     *
     *  @return a value among YDataLogger.RECORDING_OFF, YDataLogger.RECORDING_ON and
     * YDataLogger.RECORDING_PENDING corresponding to the current activation state of the data logger
     *
     * @throws YAPI_Exception on error
     */
    public int get_recording() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return RECORDING_INVALID;
                }
            }
            res = _recording;
        }
        return res;
    }

    /**
     * Returns the current activation state of the data logger.
     *
     *  @return a value among Y_RECORDING_OFF, Y_RECORDING_ON and Y_RECORDING_PENDING corresponding to the
     * current activation state of the data logger
     *
     * @throws YAPI_Exception on error
     */
    public int getRecording() throws YAPI_Exception
    {
        return get_recording();
    }

    /**
     * Changes the activation state of the data logger to start/stop recording data.
     *
     *  @param newval : a value among YDataLogger.RECORDING_OFF, YDataLogger.RECORDING_ON and
     *  YDataLogger.RECORDING_PENDING corresponding to the activation state of the data logger to
     * start/stop recording data
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_recording(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("recording",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation state of the data logger to start/stop recording data.
     *
     *  @param newval : a value among Y_RECORDING_OFF, Y_RECORDING_ON and Y_RECORDING_PENDING corresponding
     * to the activation state of the data logger to start/stop recording data
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setRecording(int newval)  throws YAPI_Exception
    {
        return set_recording(newval);
    }

    /**
     * Returns the default activation state of the data logger on power up.
     *
     *  @return either YDataLogger.AUTOSTART_OFF or YDataLogger.AUTOSTART_ON, according to the default
     * activation state of the data logger on power up
     *
     * @throws YAPI_Exception on error
     */
    public int get_autoStart() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return AUTOSTART_INVALID;
                }
            }
            res = _autoStart;
        }
        return res;
    }

    /**
     * Returns the default activation state of the data logger on power up.
     *
     *  @return either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the default activation state of the
     * data logger on power up
     *
     * @throws YAPI_Exception on error
     */
    public int getAutoStart() throws YAPI_Exception
    {
        return get_autoStart();
    }

    /**
     * Changes the default activation state of the data logger on power up.
     * Do not forget to call the saveToFlash() method of the module to save the
     * configuration change.  Note: if the device doesn't have any time source at his disposal when
     * starting up, it will wait for ~8 seconds before automatically starting to record  with
     * an arbitrary timestamp
     *
     *  @param newval : either YDataLogger.AUTOSTART_OFF or YDataLogger.AUTOSTART_ON, according to the
     * default activation state of the data logger on power up
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_autoStart(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("autoStart",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the default activation state of the data logger on power up.
     * Do not forget to call the saveToFlash() method of the module to save the
     * configuration change.  Note: if the device doesn't have any time source at his disposal when
     * starting up, it will wait for ~8 seconds before automatically starting to record  with
     * an arbitrary timestamp
     *
     *  @param newval : either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the default activation state
     * of the data logger on power up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAutoStart(int newval)  throws YAPI_Exception
    {
        return set_autoStart(newval);
    }

    /**
     * Returns true if the data logger is synchronised with the localization beacon.
     *
     *  @return either YDataLogger.BEACONDRIVEN_OFF or YDataLogger.BEACONDRIVEN_ON, according to true if
     * the data logger is synchronised with the localization beacon
     *
     * @throws YAPI_Exception on error
     */
    public int get_beaconDriven() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BEACONDRIVEN_INVALID;
                }
            }
            res = _beaconDriven;
        }
        return res;
    }

    /**
     * Returns true if the data logger is synchronised with the localization beacon.
     *
     *  @return either Y_BEACONDRIVEN_OFF or Y_BEACONDRIVEN_ON, according to true if the data logger is
     * synchronised with the localization beacon
     *
     * @throws YAPI_Exception on error
     */
    public int getBeaconDriven() throws YAPI_Exception
    {
        return get_beaconDriven();
    }

    /**
     * Changes the type of synchronisation of the data logger.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     *  @param newval : either YDataLogger.BEACONDRIVEN_OFF or YDataLogger.BEACONDRIVEN_ON, according to
     * the type of synchronisation of the data logger
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_beaconDriven(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("beaconDriven",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the type of synchronisation of the data logger.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     *  @param newval : either Y_BEACONDRIVEN_OFF or Y_BEACONDRIVEN_ON, according to the type of
     * synchronisation of the data logger
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setBeaconDriven(int newval)  throws YAPI_Exception
    {
        return set_beaconDriven(newval);
    }

    /**
     * Returns the percentage of datalogger memory in use.
     *
     * @return an integer corresponding to the percentage of datalogger memory in use
     *
     * @throws YAPI_Exception on error
     */
    public int get_usage() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return USAGE_INVALID;
                }
            }
            res = _usage;
        }
        return res;
    }

    /**
     * Returns the percentage of datalogger memory in use.
     *
     * @return an integer corresponding to the percentage of datalogger memory in use
     *
     * @throws YAPI_Exception on error
     */
    public int getUsage() throws YAPI_Exception
    {
        return get_usage();
    }

    public int get_clearHistory() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CLEARHISTORY_INVALID;
                }
            }
            res = _clearHistory;
        }
        return res;
    }

    public int set_clearHistory(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("clearHistory",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Retrieves a data logger for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the data logger is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDataLogger.isOnline() to test if the data logger is
     * indeed online at a given time. In case of ambiguity when looking for
     * a data logger by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the data logger, for instance
     *         Y3DMK002.dataLogger.
     *
     * @return a YDataLogger object allowing you to drive the data logger.
     */
    public static YDataLogger FindDataLogger(String func)
    {
        YDataLogger obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YDataLogger) YFunction._FindFromCache("DataLogger", func);
            if (obj == null) {
                obj = new YDataLogger(func);
                YFunction._AddToCache("DataLogger", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a data logger for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the data logger is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDataLogger.isOnline() to test if the data logger is
     * indeed online at a given time. In case of ambiguity when looking for
     * a data logger by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the data logger, for instance
     *         Y3DMK002.dataLogger.
     *
     * @return a YDataLogger object allowing you to drive the data logger.
     */
    public static YDataLogger FindDataLoggerInContext(YAPIContext yctx,String func)
    {
        YDataLogger obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YDataLogger) YFunction._FindFromCacheInContext(yctx, "DataLogger", func);
            if (obj == null) {
                obj = new YDataLogger(yctx, func);
                YFunction._AddToCache("DataLogger", func, obj);
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
        _valueCallbackDataLogger = callback;
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
        if (_valueCallbackDataLogger != null) {
            _valueCallbackDataLogger.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Clears the data logger memory and discards all recorded data streams.
     * This method also resets the current run index to zero.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int forgetAllDataStreams() throws YAPI_Exception
    {
        return set_clearHistory(CLEARHISTORY_TRUE);
    }

    /**
     * Returns a list of YDataSet objects that can be used to retrieve
     * all measures stored by the data logger.
     *
     * This function only works if the device uses a recent firmware,
     * as YDataSet objects are not supported by firmwares older than
     * version 13000.
     *
     * @return a list of YDataSet object.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<YDataSet> get_dataSets() throws YAPI_Exception
    {
        return parse_dataSets(_download("logger.json"));
    }

    public ArrayList<YDataSet> parse_dataSets(byte[] json) throws YAPI_Exception
    {
        ArrayList<String> dslist = new ArrayList<>();
        YDataSet dataset;
        ArrayList<YDataSet> res = new ArrayList<>();

        dslist = _json_get_array(json);
        res.clear();
        for (String ii:dslist) {
            dataset = new YDataSet(this);
            dataset._parse(ii);
            res.add(dataset);
        }
        return res;
    }

    /**
     * Continues the enumeration of data loggers started using yFirstDataLogger().
     * Caution: You can't make any assumption about the returned data loggers order.
     * If you want to find a specific a data logger, use DataLogger.findDataLogger()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YDataLogger object, corresponding to
     *         a data logger currently online, or a null pointer
     *         if there are no more data loggers to enumerate.
     */
    public YDataLogger nextDataLogger()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindDataLoggerInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of data loggers currently accessible.
     * Use the method YDataLogger.nextDataLogger() to iterate on
     * next data loggers.
     *
     * @return a pointer to a YDataLogger object, corresponding to
     *         the first data logger currently online, or a null pointer
     *         if there are none.
     */
    public static YDataLogger FirstDataLogger()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("DataLogger");
        if (next_hwid == null)  return null;
        return FindDataLoggerInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of data loggers currently accessible.
     * Use the method YDataLogger.nextDataLogger() to iterate on
     * next data loggers.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YDataLogger object, corresponding to
     *         the first data logger currently online, or a null pointer
     *         if there are none.
     */
    public static YDataLogger FirstDataLoggerInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("DataLogger");
        if (next_hwid == null)  return null;
        return FindDataLoggerInContext(yctx, next_hwid);
    }

    //--- (end of generated code: YDataLogger implementation)
}
