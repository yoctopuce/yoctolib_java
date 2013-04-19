/**
 * *******************************************************************
 *
 * $Id: YDataLogger.java 10563 2013-03-20 21:37:04Z seb $
 *
 * Implements yFindDataLogger(), the high-level API for DataLogger functions
 *
 * - - - - - - - - - License information: - - - - - - - - -
 *
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 * 1) If you have obtained this file from www.yoctopuce.com, Yoctopuce Sarl
 * licenses to you (hereafter Licensee) the right to use, modify, copy, and
 * integrate this source file into your own solution for the sole purpose of
 * interfacing a Yoctopuce product with Licensee's solution.
 *
 * The use of this file and all relationship between Yoctopuce and Licensee are
 * governed by Yoctopuce General Terms and Conditions.
 *
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION, ANY WARRANTY
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE AND
 * NON-INFRINGEMENT. IN NO EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL,
 * SPECIAL, INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST
 * OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR SERVICES, ANY CLAIMS BY
 * THIRD PARTIES (INCLUDING BUT NOT LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS
 * FOR INDEMNITY OR CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON
 * THE BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF WARRANTY, OR
 * OTHERWISE.
 *
 * 2) If your intent is not to interface with Yoctopuce products, you are not
 * entitled to use, read or create any derived material from this source file.
 *
 ********************************************************************
 */
package com.yoctopuce.YoctoAPI; //test

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

/**
 * YDataLogger Class: DataLogger function interface
 *
 * Yoctopuce sensors include a non-volatile memory capable of storing ongoing
 * measured data automatically, without requiring a permanent connection to a
 * computer. The Yoctopuce application programming interface includes functions
 * to control how this internal data logger works. Beacause the sensors do not
 * include a battery, they do not have an absolute time reference. Therefore,
 * measures are simply indexed by the absolute run number and time relative to
 * the start of the run. Every new power up starts a new run. It is however
 * possible to setup an absolute UTC time by software at a given time, so that
 * the data logger keeps track of it until it is powered off next.
 */
public class YDataLogger extends YFunction {

    protected String _dataLoggerURL;
    protected ArrayList<String> _measureNames;
    protected HashMap<Integer, YDataRun> _dataRuns;
    protected long _liveRun;

    /**
     * Internal function to retrieve datalogger memory
     */
    public JSONTokener getData(Integer runIdx, Integer timeIdx) throws YAPI_Exception
    {
        if (_dataLoggerURL == null) {
            _dataLoggerURL = "/logger.json";
        }

        // get the device serial number
        String devid = this.module().get_serialNumber();

        String httpreq = "GET " + _dataLoggerURL;
        if (timeIdx != null) {
            httpreq += String.format("?run=%d&time=%d", runIdx, timeIdx);
        }
        String result;
        YDevice dev = YAPI.getDevice(devid);
        try {
            result = new String(dev.requestHTTP(httpreq,null, false));
        } catch (YAPI_Exception ex) {
            if (!_dataLoggerURL.equals("/dataLogger.json")) {
                _dataLoggerURL = "/dataLogger.json";
                return getData(runIdx, timeIdx);
            }
            throw ex;
        }
        JSONTokener loadval = new JSONTokener(result);
        return loadval;
    }

    /**
     * Internal function to preload the list of all runs, for high-level
     * functions
     */
    public int loadRuns() throws YAPI_Exception
    {
        _measureNames = new ArrayList<String>();

        _dataRuns = new HashMap<Integer, YDataRun>();
        _liveRun = this.get_currentRunIndex();

        // preload stream list
        ArrayList<YDataStream> streams = new ArrayList<YDataStream>();
        this.get_dataStreams(streams);

        // sort streams into runs
        for (YDataStream stream : streams) {
            int runIdx = stream.get_runIndex();
            if (!_dataRuns.containsKey(runIdx)) {
                YDataRun drun = new YDataRun(this, runIdx);
                _dataRuns.put(runIdx, drun);
            }
            _dataRuns.get(runIdx).addStream(stream);
        }

        // finalize computation of data in each run
        YDataStream stream = streams.get(0);
        String[] names = stream.get_columnNames();

        for (String name : names) {
            if (name.charAt(name.length() - 4) != '_') {
                _measureNames.add(name);
            } else if (name.substring(name.length() - 4).equals("_min")) {
                _measureNames.add(name.substring(name.length() - 4));
            }
        }
        for (YDataRun run : _dataRuns.values()) {
            run.yfinalize();
        }
        return YAPI.SUCCESS;
    }

    /**
     * Clears the data logger memory and discards all recorded data streams.
     * This method also resets the current run index to zero.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int forgetAllDataStreams() throws YAPI_Exception
    {
        return set_clearHistory(CLEARHISTORY_TRUE);
    }

    /**
     * Returns the names of the measures recorded by the data logger.
     * In most case, the measure names match the hardware identifier
     * of the sensor that produced the data.
     * 
     * @return a list of strings (the measure names)
     * 
     * @throws YAPI_Exception
     */
    public ArrayList<String> get_measureNames()
    {
        return _measureNames;
    }

    public ArrayList<String> getMeasureNames()
    {
        return _measureNames;
    }

    /**
     * Returns a data run object holding all measured data for a given
     * period during which the module was turned on (a run). This object can then
     * be used to retrieve measures (min, average and max) at a desired data rate.
     * 
     * @param runIdx : the index of the desired run
     * 
     * @return an YDataRun object
     * 
     * @throws YAPI_Exception
     */
    public YDataRun get_dataRun(int runIdx) throws YAPI_Exception
    {
        if (_dataRuns == null || runIdx > _liveRun) {
            loadRuns();
        }
        if (!_dataRuns.containsKey(runIdx)) {
            return null;
        }
        return _dataRuns.get(runIdx);

    }

    public YDataRun getDataRun(int runIdx) throws YAPI_Exception
    {
        return get_dataRun(runIdx);
    }

    /**
     * Builds a list of all data streams hold by the data logger.
     * The caller must pass by reference an empty array to hold YDataStream
     * objects, and the function fills it with objects describing available
     * data sequences.
     * 
     * @param v : an array of YDataStream objects to be filled in
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int get_dataStreams(ArrayList<YDataStream> v) throws YAPI_Exception
    {

        JSONTokener loadval = this.getData(null, null);
        try {
            JSONArray jsonAllStreams = new JSONArray(loadval);
            for (int i = 0; i < jsonAllStreams.length(); i++) {
                JSONArray jsonStream = jsonAllStreams.getJSONArray(i);
                //            $v[] = new YDataStream($this,$arr[0],$arr[1],$arr[2],$arr[3]);
                YDataStream stream = new YDataStream(this, jsonStream);
                v.add(stream);
            }
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
        }


        return YAPI.SUCCESS;
    }

    public int getDataStreams(ArrayList<YDataStream> v) throws YAPI_Exception
    {
        return this.get_dataStreams(v);
    }
    //--- (generated code: definitions)
    private YDataLogger.UpdateCallback _valueCallbackDataLogger;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid oldestRunIndex value
     */
    public static final int OLDESTRUNINDEX_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid currentRunIndex value
     */
    public static final int CURRENTRUNINDEX_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid samplingInterval value
     */
    public static final int SAMPLINGINTERVAL_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid timeUTC value
     */
    public static final long TIMEUTC_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid recording value
     */
  public static final int RECORDING_OFF = 0;
  public static final int RECORDING_ON = 1;
  public static final int RECORDING_INVALID = -1;

    /**
     * invalid autoStart value
     */
  public static final int AUTOSTART_OFF = 0;
  public static final int AUTOSTART_ON = 1;
  public static final int AUTOSTART_INVALID = -1;

    /**
     * invalid clearHistory value
     */
  public static final int CLEARHISTORY_FALSE = 0;
  public static final int CLEARHISTORY_TRUE = 1;
  public static final int CLEARHISTORY_INVALID = -1;

    //--- (end of generated code: definitions)
    //--- (generated code: YDataLogger implementation)

    /**
     * Returns the logical name of the data logger.
     * 
     * @return a string corresponding to the logical name of the data logger
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the data logger.
     * 
     * @return a string corresponding to the logical name of the data logger
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the data logger. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the data logger
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_logicalName( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("logicalName",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the logical name of the data logger. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the data logger
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the data logger (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the data logger (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the data logger (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the data logger (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the index of the oldest run for which the non-volatile memory still holds recorded data.
     * 
     * @return an integer corresponding to the index of the oldest run for which the non-volatile memory
     * still holds recorded data
     * 
     * @throws YAPI_Exception
     */
    public int get_oldestRunIndex()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("oldestRunIndex");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the index of the oldest run for which the non-volatile memory still holds recorded data.
     * 
     * @return an integer corresponding to the index of the oldest run for which the non-volatile memory
     * still holds recorded data
     * 
     * @throws YAPI_Exception
     */
    public int getOldestRunIndex() throws YAPI_Exception

    { return get_oldestRunIndex(); }

    /**
     * Returns the current run number, corresponding to the number of times the module was
     * powered on with the dataLogger enabled at some point.
     * 
     * @return an integer corresponding to the current run number, corresponding to the number of times the module was
     *         powered on with the dataLogger enabled at some point
     * 
     * @throws YAPI_Exception
     */
    public int get_currentRunIndex()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("currentRunIndex");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current run number, corresponding to the number of times the module was
     * powered on with the dataLogger enabled at some point.
     * 
     * @return an integer corresponding to the current run number, corresponding to the number of times the module was
     *         powered on with the dataLogger enabled at some point
     * 
     * @throws YAPI_Exception
     */
    public int getCurrentRunIndex() throws YAPI_Exception

    { return get_currentRunIndex(); }

    public int get_samplingInterval()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("samplingInterval");
        return Integer.parseInt(json_val);
    }

    public int getSamplingInterval() throws YAPI_Exception

    { return get_samplingInterval(); }

    public int set_samplingInterval( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("samplingInterval",rest_val);
        return YAPI.SUCCESS;
    }

    public int setSamplingInterval( int newval)  throws YAPI_Exception

    { return set_samplingInterval(newval); }

    /**
     * Returns the Unix timestamp for current UTC time, if known.
     * 
     * @return an integer corresponding to the Unix timestamp for current UTC time, if known
     * 
     * @throws YAPI_Exception
     */
    public long get_timeUTC()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("timeUTC");
        return Long.parseLong(json_val);
    }

    /**
     * Returns the Unix timestamp for current UTC time, if known.
     * 
     * @return an integer corresponding to the Unix timestamp for current UTC time, if known
     * 
     * @throws YAPI_Exception
     */
    public long getTimeUTC() throws YAPI_Exception

    { return get_timeUTC(); }

    /**
     * Changes the current UTC time reference used for recorded data.
     * 
     * @param newval : an integer corresponding to the current UTC time reference used for recorded data
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_timeUTC( long  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("timeUTC",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current UTC time reference used for recorded data.
     * 
     * @param newval : an integer corresponding to the current UTC time reference used for recorded data
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setTimeUTC( long newval)  throws YAPI_Exception

    { return set_timeUTC(newval); }

    /**
     * Returns the current activation state of the data logger.
     * 
     * @return either YDataLogger.RECORDING_OFF or YDataLogger.RECORDING_ON, according to the current
     * activation state of the data logger
     * 
     * @throws YAPI_Exception
     */
    public int get_recording()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("recording");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current activation state of the data logger.
     * 
     * @return either Y_RECORDING_OFF or Y_RECORDING_ON, according to the current activation state of the data logger
     * 
     * @throws YAPI_Exception
     */
    public int getRecording() throws YAPI_Exception

    { return get_recording(); }

    /**
     * Changes the activation state of the data logger to start/stop recording data.
     * 
     * @param newval : either YDataLogger.RECORDING_OFF or YDataLogger.RECORDING_ON, according to the
     * activation state of the data logger to start/stop recording data
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_recording( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("recording",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation state of the data logger to start/stop recording data.
     * 
     * @param newval : either Y_RECORDING_OFF or Y_RECORDING_ON, according to the activation state of the
     * data logger to start/stop recording data
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setRecording( int newval)  throws YAPI_Exception

    { return set_recording(newval); }

    /**
     * Returns the default activation state of the data logger on power up.
     * 
     * @return either YDataLogger.AUTOSTART_OFF or YDataLogger.AUTOSTART_ON, according to the default
     * activation state of the data logger on power up
     * 
     * @throws YAPI_Exception
     */
    public int get_autoStart()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("autoStart");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the default activation state of the data logger on power up.
     * 
     * @return either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the default activation state of the
     * data logger on power up
     * 
     * @throws YAPI_Exception
     */
    public int getAutoStart() throws YAPI_Exception

    { return get_autoStart(); }

    /**
     * Changes the default activation state of the data logger on power up.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : either YDataLogger.AUTOSTART_OFF or YDataLogger.AUTOSTART_ON, according to the
     * default activation state of the data logger on power up
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_autoStart( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("autoStart",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the default activation state of the data logger on power up.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the default activation state
     * of the data logger on power up
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setAutoStart( int newval)  throws YAPI_Exception

    { return set_autoStart(newval); }

    public int get_clearHistory()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("clearHistory");
        return Integer.parseInt(json_val);
    }

    public int getClearHistory() throws YAPI_Exception

    { return get_clearHistory(); }

    public int set_clearHistory( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("clearHistory",rest_val);
        return YAPI.SUCCESS;
    }

    public int setClearHistory( int newval)  throws YAPI_Exception

    { return set_clearHistory(newval); }

    /**
     * Continues the enumeration of data loggers started using yFirstDataLogger().
     * 
     * @return a pointer to a YDataLogger object, corresponding to
     *         a data logger currently online, or a null pointer
     *         if there are no more data loggers to enumerate.
     */
    public  YDataLogger nextDataLogger()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindDataLogger(next_hwid);
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
     * @param func : a string that uniquely characterizes the data logger
     * 
     * @return a YDataLogger object allowing you to drive the data logger.
     */
    public static YDataLogger FindDataLogger(String func)
    {   YFunction yfunc = YAPI.getFunction("DataLogger", func);
        if (yfunc != null) {
            return (YDataLogger) yfunc;
        }
        return new YDataLogger(func);
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
        String next_hwid = YAPI.getFirstHardwareId("DataLogger");
        if (next_hwid == null)  return null;
        return FindDataLogger(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YDataLogger(String func)
    {
        super("DataLogger", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackDataLogger != null) {
            _valueCallbackDataLogger.yNewValue(this, newvalue);
        }
    }

    /**
     * Internal: check if we have a callback interface registered
     * 
     * @return yes if the user has registered a interface
     */
    @Override
     protected boolean hasCallbackRegistered()
    {
        return super.hasCallbackRegistered() || (_valueCallbackDataLogger!=null);
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
     * @noreturn
     */
    public void registerValueCallback(YDataLogger.UpdateCallback callback)
    {
         _valueCallbackDataLogger =  callback;
         if (callback != null && isOnline()) {
             String newval;
             try {
                 newval = get_advertisedValue();
                 if (!newval.equals("") && !newval.equals("!INVALDI!")) {
                     callback.yNewValue(this, newval);
                 }
             } catch (YAPI_Exception ex) {
             }
         }
    }

    //--- (end of generated code: YDataLogger implementation)
};
