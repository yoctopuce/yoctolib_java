/*
 * $Id: YDataSet.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 * Implements yFindDataSet(), the high-level API for DataSet functions
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
 */

package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;
import java.util.Locale;

//--- (generated code: YDataSet class start)
/**
 * YDataSet Class: Recorded data sequence, as returned by sensor.get_recordedData()
 *
 * YDataSet objects make it possible to retrieve a set of recorded measures
 * for a given sensor and a specified time interval. They can be used
 * to load data points with a progress report. When the YDataSet object is
 * instantiated by the sensor.get_recordedData()  function, no data is
 * yet loaded from the module. It is only when the loadMore()
 * method is called over and over than data will be effectively loaded
 * from the dataLogger.
 *
 * A preview of available measures is available using the function
 * get_preview() as soon as loadMore() has been called
 * once. Measures themselves are available using function get_measures()
 * when loaded by subsequent calls to loadMore().
 *
 * This class can only be used on devices that use a relatively recent firmware,
 * as YDataSet objects are not supported by firmwares older than version 13000.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YDataSet
{
//--- (end of generated code: YDataSet class start)

    //--- (generated code: YDataSet definitions)
    protected YFunction _parent;
    protected String _hardwareId;
    protected String _functionId;
    protected String _unit;
    protected double _startTimeMs = 0;
    protected double _endTimeMs = 0;
    protected int _progress = 0;
    protected ArrayList<Integer> _calib = new ArrayList<>();
    protected ArrayList<YDataStream> _streams = new ArrayList<>();
    protected YMeasure _summary;
    protected ArrayList<YMeasure> _preview = new ArrayList<>();
    protected ArrayList<YMeasure> _measures = new ArrayList<>();
    protected double _summaryMinVal = 0;
    protected double _summaryMaxVal = 0;
    protected double _summaryTotalAvg = 0;
    protected double _summaryTotalTime = 0;

    //--- (end of generated code: YDataSet definitions)

    // YDataSet constructor, when instantiated directly by a function
    YDataSet(YFunction parent, String functionId, String unit, double startTime, double endTime)
    {
        _parent = parent;
        _functionId = functionId;
        _unit = unit;
        _startTimeMs = startTime*1000;
        _endTimeMs = endTime*1000;
        _progress = -1;
        _hardwareId = "";
        _summary = new YMeasure();
    }

    // YDataSet constructor for the new datalogger
    public YDataSet(YFunction parent)
    {
        _parent = parent;
        _startTimeMs = 0;
        _endTimeMs = 0;
        _hardwareId = "";
        _summary = new YMeasure();
    }

    // YDataSet parser for stream list
    protected int _parse(String json_str) throws YAPI_Exception
    {
        YJSONObject json;
        YJSONArray jstreams;
        double streamStartTime;
        double streamEndTime;

        try {
            json = new YJSONObject(json_str);
            json.parse();
            _functionId = json.getString("id");
            _unit = json.getString("unit");
            if (json.has("calib")) {
                _calib = YAPIContext._decodeFloats(json.getString("calib"));
                _calib.set(0, _calib.get(0) / 1000);
            } else {
                _calib = YAPIContext._decodeWords(json.getString("cal"));
            }
            _streams = new ArrayList<>();
            _preview = new ArrayList<>();
            _measures = new ArrayList<>();
            jstreams = json.getYJSONArray("streams");
            for (int i = 0; i < jstreams.length(); i++) {
                YDataStream stream = _parent._findDataStream(this, jstreams.getString(i));
                streamStartTime = Math.round(stream.get_realStartTimeUTC()*1000);
                streamEndTime = Math.round(streamStartTime + stream.get_realDuration()*1000);
                if (_startTimeMs > 0 && streamEndTime <= _startTimeMs) {
                    // this stream is too early, drop it
                } else if (_endTimeMs > 0 && streamStartTime >= _endTimeMs) {
                    // this stream is too late, drop it
                } else {
                    _streams.add(stream);
                }
            }
        } catch (Exception e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "invalid json structure for YDataSet: " + e.getMessage());
        }
        _progress = 0;
        return this.get_progress();
    }

    //--- (generated code: YDataSet implementation)

    public ArrayList<Integer> _get_calibration()
    {
        return _calib;
    }

    public int loadSummary(byte[] data) throws YAPI_Exception
    {
        ArrayList<ArrayList<Double>> dataRows = new ArrayList<>();
        double tim;
        double mitv;
        double itv;
        double fitv;
        double end_;
        int nCols;
        int minCol;
        int avgCol;
        int maxCol;
        int res;
        int m_pos;
        double previewTotalTime;
        double previewTotalAvg;
        double previewMinVal;
        double previewMaxVal;
        double previewAvgVal;
        double previewStartMs;
        double previewStopMs;
        double previewDuration;
        double streamStartTimeMs;
        double streamDuration;
        double streamEndTimeMs;
        double minVal;
        double avgVal;
        double maxVal;
        double summaryStartMs;
        double summaryStopMs;
        double summaryTotalTime;
        double summaryTotalAvg;
        double summaryMinVal;
        double summaryMaxVal;
        String url;
        String strdata;
        ArrayList<Double> measure_data = new ArrayList<>();

        if (_progress < 0) {
            strdata = new String(data);
            if (strdata.equals("{}")) {
                _parent._throw(YAPI.VERSION_MISMATCH, "device firmware is too old");
                return YAPI.VERSION_MISMATCH;
            }
            res = _parse(strdata);
            if (res < 0) {
                return res;
            }
        }
        summaryTotalTime = 0;
        summaryTotalAvg = 0;
        summaryMinVal = YAPI.MAX_DOUBLE;
        summaryMaxVal = YAPI.MIN_DOUBLE;
        summaryStartMs = YAPI.MAX_DOUBLE;
        summaryStopMs = YAPI.MIN_DOUBLE;

        // Parse complete streams
        for (YDataStream ii: _streams) {
            streamStartTimeMs = (double)Math.round(ii.get_realStartTimeUTC() *1000);
            streamDuration = ii.get_realDuration() ;
            streamEndTimeMs = streamStartTimeMs + (double)Math.round(streamDuration * 1000);
            if ((streamStartTimeMs >= _startTimeMs) && ((_endTimeMs == 0) || (streamEndTimeMs <= _endTimeMs))) {
                // stream that are completely inside the dataset
                previewMinVal = ii.get_minValue();
                previewAvgVal = ii.get_averageValue();
                previewMaxVal = ii.get_maxValue();
                previewStartMs = streamStartTimeMs;
                previewStopMs = streamEndTimeMs;
                previewDuration = streamDuration;
            } else {
                // stream that are partially in the dataset
                // we need to parse data to filter value outside the dataset
                url = ii._get_url();
                data = _parent._download(url);
                ii._parseStream(data);
                dataRows = ii.get_dataRows();
                if (dataRows.size() == 0) {
                    return get_progress();
                }
                tim = streamStartTimeMs;
                fitv = (double)Math.round(ii.get_firstDataSamplesInterval() * 1000);
                itv = (double)Math.round(ii.get_dataSamplesInterval() * 1000);
                nCols = dataRows.get(0).size();
                minCol = 0;
                if (nCols > 2) {
                    avgCol = 1;
                } else {
                    avgCol = 0;
                }
                if (nCols > 2) {
                    maxCol = 2;
                } else {
                    maxCol = 0;
                }
                previewTotalTime = 0;
                previewTotalAvg = 0;
                previewStartMs = streamEndTimeMs;
                previewStopMs = streamStartTimeMs;
                previewMinVal = YAPI.MAX_DOUBLE;
                previewMaxVal = YAPI.MIN_DOUBLE;
                m_pos = 0;
                while (m_pos < dataRows.size()) {
                    measure_data  = dataRows.get(m_pos);
                    if (m_pos == 0) {
                        mitv = fitv;
                    } else {
                        mitv = itv;
                    }
                    end_ = tim + mitv;
                    if ((end_ > _startTimeMs) && ((_endTimeMs == 0) || (tim < _endTimeMs))) {
                        minVal = measure_data.get(minCol).doubleValue();
                        avgVal = measure_data.get(avgCol).doubleValue();
                        maxVal = measure_data.get(maxCol).doubleValue();
                        if (previewStartMs > tim) {
                            previewStartMs = tim;
                        }
                        if (previewStopMs < end_) {
                            previewStopMs = end_;
                        }
                        if (previewMinVal > minVal) {
                            previewMinVal = minVal;
                        }
                        if (previewMaxVal < maxVal) {
                            previewMaxVal = maxVal;
                        }
                        previewTotalAvg = previewTotalAvg + (avgVal * mitv);
                        previewTotalTime = previewTotalTime + mitv;
                    }
                    tim = end_;
                    m_pos = m_pos + 1;
                }
                if (previewTotalTime > 0) {
                    previewAvgVal = previewTotalAvg / previewTotalTime;
                    previewDuration = (previewStopMs - previewStartMs) / 1000.0;
                } else {
                    previewAvgVal = 0.0;
                    previewDuration = 0.0;
                }
            }
            _preview.add(new YMeasure(previewStartMs / 1000.0, previewStopMs / 1000.0, previewMinVal, previewAvgVal, previewMaxVal));
            if (summaryMinVal > previewMinVal) {
                summaryMinVal = previewMinVal;
            }
            if (summaryMaxVal < previewMaxVal) {
                summaryMaxVal = previewMaxVal;
            }
            if (summaryStartMs > previewStartMs) {
                summaryStartMs = previewStartMs;
            }
            if (summaryStopMs < previewStopMs) {
                summaryStopMs = previewStopMs;
            }
            summaryTotalAvg = summaryTotalAvg + (previewAvgVal * previewDuration);
            summaryTotalTime = summaryTotalTime + previewDuration;
        }
        if ((_startTimeMs == 0) || (_startTimeMs > summaryStartMs)) {
            _startTimeMs = summaryStartMs;
        }
        if ((_endTimeMs == 0) || (_endTimeMs < summaryStopMs)) {
            _endTimeMs = summaryStopMs;
        }
        if (summaryTotalTime > 0) {
            _summary = new YMeasure(summaryStartMs / 1000.0, summaryStopMs / 1000.0, summaryMinVal, summaryTotalAvg / summaryTotalTime, summaryMaxVal);
        } else {
            _summary = new YMeasure(0.0, 0.0, YAPI.INVALID_DOUBLE, YAPI.INVALID_DOUBLE, YAPI.INVALID_DOUBLE);
        }
        return get_progress();
    }

    public int processMore(int progress,byte[] data) throws YAPI_Exception
    {
        YDataStream stream;
        ArrayList<ArrayList<Double>> dataRows = new ArrayList<>();
        double tim;
        double itv;
        double fitv;
        double end_;
        int nCols;
        int minCol;
        int avgCol;
        int maxCol;
        boolean firstMeasure;

        if (progress != _progress) {
            return _progress;
        }
        if (_progress < 0) {
            return loadSummary(data);
        }
        stream = _streams.get(_progress);
        stream._parseStream(data);
        dataRows = stream.get_dataRows();
        _progress = _progress + 1;
        if (dataRows.size() == 0) {
            return get_progress();
        }
        tim = (double)Math.round(stream.get_realStartTimeUTC() * 1000);
        fitv = (double)Math.round(stream.get_firstDataSamplesInterval() * 1000);
        itv = (double)Math.round(stream.get_dataSamplesInterval() * 1000);
        if (fitv == 0) {
            fitv = itv;
        }
        if (tim < itv) {
            tim = itv;
        }
        nCols = dataRows.get(0).size();
        minCol = 0;
        if (nCols > 2) {
            avgCol = 1;
        } else {
            avgCol = 0;
        }
        if (nCols > 2) {
            maxCol = 2;
        } else {
            maxCol = 0;
        }

        firstMeasure = true;
        for (ArrayList<Double> ii:dataRows) {
            if (firstMeasure) {
                end_ = tim + fitv;
                firstMeasure = false;
            } else {
                end_ = tim + itv;
            }
            if ((end_ > _startTimeMs) && ((_endTimeMs == 0) || (tim < _endTimeMs))) {
                _measures.add(new YMeasure(tim / 1000, end_ / 1000, ii.get(minCol).doubleValue(), ii.get(avgCol).doubleValue(), ii.get(maxCol).doubleValue()));
            }
            tim = end_;
        }
        return get_progress();
    }

    public ArrayList<YDataStream> get_privateDataStreams()
    {
        return _streams;
    }

    /**
     * Returns the unique hardware identifier of the function who performed the measures,
     * in the form SERIAL.FUNCTIONID. The unique hardware identifier is composed of the
     * device serial number and of the hardware identifier of the function
     * (for example THRMCPL1-123456.temperature1)
     *
     * @return a string that uniquely identifies the function (ex: THRMCPL1-123456.temperature1)
     *
     * @throws YAPI_Exception on error
     */
    public String get_hardwareId() throws YAPI_Exception
    {
        YModule mo;
        if (!(_hardwareId.equals(""))) {
            return _hardwareId;
        }
        mo = _parent.get_module();
        _hardwareId = String.format(Locale.US, "%s.%s", mo.get_serialNumber(),get_functionId());
        return _hardwareId;
    }

    /**
     * Returns the hardware identifier of the function that performed the measure,
     * without reference to the module. For example temperature1.
     *
     * @return a string that identifies the function (ex: temperature1)
     */
    public String get_functionId()
    {
        return _functionId;
    }

    /**
     * Returns the measuring unit for the measured value.
     *
     * @return a string that represents a physical unit.
     *
     * @throws YAPI_Exception on error
     */
    public String get_unit() throws YAPI_Exception
    {
        return _unit;
    }

    /**
     * Returns the start time of the dataset, relative to the Jan 1, 1970.
     * When the YDataSet object is created, the start time is the value passed
     * in parameter to the get_dataSet() function. After the
     * very first call to loadMore(), the start time is updated
     * to reflect the timestamp of the first measure actually found in the
     * dataLogger within the specified range.
     *
     * <b>DEPRECATED</b>: This method has been replaced by get_summary()
     * which contain more precise informations.
     *
     * @return an unsigned number corresponding to the number of seconds
     *         between the Jan 1, 1970 and the beginning of this data
     *         set (i.e. Unix time representation of the absolute time).
     */
    public long get_startTimeUTC()
    {
        return imm_get_startTimeUTC();
    }

    public long imm_get_startTimeUTC()
    {
        return (long) (_startTimeMs / 1000.0);
    }

    /**
     * Returns the end time of the dataset, relative to the Jan 1, 1970.
     * When the YDataSet object is created, the end time is the value passed
     * in parameter to the get_dataSet() function. After the
     * very first call to loadMore(), the end time is updated
     * to reflect the timestamp of the last measure actually found in the
     * dataLogger within the specified range.
     *
     * <b>DEPRECATED</b>: This method has been replaced by get_summary()
     * which contain more precise informations.
     *
     * @return an unsigned number corresponding to the number of seconds
     *         between the Jan 1, 1970 and the end of this data
     *         set (i.e. Unix time representation of the absolute time).
     */
    public long get_endTimeUTC()
    {
        return imm_get_endTimeUTC();
    }

    public long imm_get_endTimeUTC()
    {
        return (long) (double)Math.round(_endTimeMs / 1000.0);
    }

    /**
     * Returns the progress of the downloads of the measures from the data logger,
     * on a scale from 0 to 100. When the object is instantiated by get_dataSet,
     * the progress is zero. Each time loadMore() is invoked, the progress
     * is updated, to reach the value 100 only once all measures have been loaded.
     *
     * @return an integer in the range 0 to 100 (percentage of completion).
     */
    public int get_progress()
    {
        if (_progress < 0) {
            return 0;
        }
        // index not yet loaded
        if (_progress >= _streams.size()) {
            return 100;
        }
        return ((1 + (1 + _progress) * 98) / ((1 + _streams.size())));
    }

    /**
     * Loads the the next block of measures from the dataLogger, and updates
     * the progress indicator.
     *
     * @return an integer in the range 0 to 100 (percentage of completion),
     *         or a negative error code in case of failure.
     *
     * @throws YAPI_Exception on error
     */
    public int loadMore() throws YAPI_Exception
    {
        String url;
        YDataStream stream;
        if (_progress < 0) {
            url = String.format(Locale.US, "logger.json?id=%s",_functionId);
            if (_startTimeMs != 0) {
                url = String.format(Locale.US, "%s&from=%d",url,imm_get_startTimeUTC());
            }
            if (_endTimeMs != 0) {
                url = String.format(Locale.US, "%s&to=%d",url,imm_get_endTimeUTC()+1);
            }
        } else {
            if (_progress >= _streams.size()) {
                return 100;
            } else {
                stream = _streams.get(_progress);
                url = stream._get_url();
            }
        }
        try {
            return processMore(_progress, _parent._download(url));
        } catch (Exception ex) {
            return processMore(_progress, _parent._download(url));
        }
    }

    /**
     * Returns an YMeasure object which summarizes the whole
     * YDataSet. In includes the following information:
     * - the start of a time interval
     * - the end of a time interval
     * - the minimal value observed during the time interval
     * - the average value observed during the time interval
     * - the maximal value observed during the time interval
     *
     * This summary is available as soon as loadMore() has
     * been called for the first time.
     *
     * @return an YMeasure object
     */
    public YMeasure get_summary()
    {
        return _summary;
    }

    /**
     * Returns a condensed version of the measures that can
     * retrieved in this YDataSet, as a list of YMeasure
     * objects. Each item includes:
     * - the start of a time interval
     * - the end of a time interval
     * - the minimal value observed during the time interval
     * - the average value observed during the time interval
     * - the maximal value observed during the time interval
     *
     * This preview is available as soon as loadMore() has
     * been called for the first time.
     *
     * @return a table of records, where each record depicts the
     *         measured values during a time interval
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<YMeasure> get_preview() throws YAPI_Exception
    {
        return _preview;
    }

    /**
     * Returns the detailed set of measures for the time interval corresponding
     * to a given condensed measures previously returned by get_preview().
     * The result is provided as a list of YMeasure objects.
     *
     * @param measure : condensed measure from the list previously returned by
     *         get_preview().
     *
     * @return a table of records, where each record depicts the
     *         measured values during a time interval
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<YMeasure> get_measuresAt(YMeasure measure) throws YAPI_Exception
    {
        double startUtcMs;
        YDataStream stream;
        ArrayList<ArrayList<Double>> dataRows = new ArrayList<>();
        ArrayList<YMeasure> measures = new ArrayList<>();
        double tim;
        double itv;
        double end_;
        int nCols;
        int minCol;
        int avgCol;
        int maxCol;

        startUtcMs = measure.get_startTimeUTC() * 1000;
        stream = null;
        for (YDataStream ii:_streams) {
            if ((double)Math.round(ii.get_realStartTimeUTC() *1000) == startUtcMs) {
                stream = ii;
            }
        }
        if (stream == null) {
            return measures;
        }
        dataRows = stream.get_dataRows();
        if (dataRows.size() == 0) {
            return measures;
        }
        tim = (double)Math.round(stream.get_realStartTimeUTC() * 1000);
        itv = (double)Math.round(stream.get_dataSamplesInterval() * 1000);
        if (tim < itv) {
            tim = itv;
        }
        nCols = dataRows.get(0).size();
        minCol = 0;
        if (nCols > 2) {
            avgCol = 1;
        } else {
            avgCol = 0;
        }
        if (nCols > 2) {
            maxCol = 2;
        } else {
            maxCol = 0;
        }

        for (ArrayList<Double> ii:dataRows) {
            end_ = tim + itv;
            if ((end_ > _startTimeMs) && ((_endTimeMs == 0) || (tim < _endTimeMs))) {
                measures.add(new YMeasure(tim / 1000.0, end_ / 1000.0, ii.get(minCol).doubleValue(), ii.get(avgCol).doubleValue(), ii.get(maxCol).doubleValue()));
            }
            tim = end_;
        }
        return measures;
    }

    /**
     * Returns all measured values currently available for this DataSet,
     * as a list of YMeasure objects. Each item includes:
     * - the start of the measure time interval
     * - the end of the measure time interval
     * - the minimal value observed during the time interval
     * - the average value observed during the time interval
     * - the maximal value observed during the time interval
     *
     * Before calling this method, you should call loadMore()
     * to load data from the device. You may have to call loadMore()
     * several time until all rows are loaded, but you can start
     * looking at available data rows before the load is complete.
     *
     * The oldest measures are always loaded first, and the most
     * recent measures will be loaded last. As a result, timestamps
     * are normally sorted in ascending order within the measure table,
     * unless there was an unexpected adjustment of the datalogger UTC
     * clock.
     *
     * @return a table of records, where each record depicts the
     *         measured value for a given time interval
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<YMeasure> get_measures() throws YAPI_Exception
    {
        return _measures;
    }

    //--- (end of generated code: YDataSet implementation)
}

