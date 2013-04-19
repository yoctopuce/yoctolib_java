/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * YDataRun Class: Sequence of measured data, stored by the data logger
 *
 * A run is a continuous interval of time during which a module was powered on.
 * A data run provides easy access to all data collected during a given run,
 * providing on-the-fly resampling at the desired reporting rate.
 */
public class YDataRun {

    public static final double MINVALUE_INVALID = YAPI.INVALID_DOUBLE;
    public static final double AVERAGEVALUE_INVALID = YAPI.INVALID_DOUBLE;
    public static final double MAXVALUE_INVALID = YAPI.INVALID_DOUBLE;
    private YDataLogger _dataLogger;
    private int _runNo;
    private ArrayList<YDataStream> _streams;
    private ArrayList<String> _measureNames;
    private int _browseInterval;
    private long _startTimeUTC;
    private long _duration;
    private boolean _isLive;
    private HashMap<String, ArrayList<Double>> _minValues;
    private HashMap<String, ArrayList<Double>> _avgValues;
    private HashMap<String, ArrayList<Double>> _maxValues;

    YDataRun(YDataLogger parent, int run)
    {
        _dataLogger = parent;
        _runNo = run;
        _streams = new ArrayList<YDataStream>();
        _browseInterval = 60;
        _startTimeUTC = 0;
        _duration = 0;
        _isLive = false;
    }

    /*
     * Internal: Append a stream to the run
     */
    void addStream(YDataStream stream)
    {
        _streams.add(stream);
        if (_startTimeUTC == 0) {
            if (stream.get_startTimeUTC() > 0) {
                _startTimeUTC = stream.getStartTimeUTC() - stream.getStartTime();
            }
        }
    }

    /*
     * Internal: Compute the total duration of the run, once all streams have
     * been added
     */
    void yfinalize() throws YAPI_Exception
    {

        YDataStream last = _streams.get(_streams.size() - 1);
        _duration = last.getStartTime() + last.getRowCount() * last.getDataSamplesInterval();
        _isLive = (_dataLogger.getCurrentRunIndex() == _runNo
                && _dataLogger.getRecording() == YDataLogger.RECORDING_ON);
        if (_isLive && _startTimeUTC == 0) {
            _startTimeUTC = (System.currentTimeMillis() / 1000L) - _duration;
        }
        _measureNames = _dataLogger.getMeasureNames();
        if (_streams.size() > 0) {
            this.setValueInterval(_streams.get(0).getDataSamplesInterval());
        } else {
            this.setValueInterval(60);
        }
    }

    /*
     * Internal: Update the run with any new data that may have appeared since
     * the run was initially loaded
     */
    private void refresh() throws YAPI_Exception
    {
        if (_isLive) {
            YDataStream last = _streams.get(_streams.size() - 1);
            last.loadStream();
            _duration = last.getStartTime() + last.getRowCount() * last.getDataSamplesInterval();
            if ((System.currentTimeMillis() / 1000L) > (_startTimeUTC + _duration)) {
                // check if new streams have appeared in between
                boolean newStreams = false;
                ArrayList<YDataStream> streams = new ArrayList<YDataStream>();
                _dataLogger.get_dataStreams(streams);
                for (YDataStream stream : streams) {
                    if (stream.get_runIndex() == _runNo && stream.get_startTime() > last.get_startTime()) {
                        addStream(stream);
                        newStreams = true;
                    }
                }
                if (newStreams) {
                    yfinalize();
                }
            }
            _isLive = (_dataLogger.get_recording() == YDataLogger.RECORDING_ON);
        }
    }


    /*
     * Internal: Mark a measure as unavailable
     */
    private void invalidValue(int pos)
    {
        for (String key : _measureNames) {
            _minValues.get(key).set(pos, MINVALUE_INVALID);
            _avgValues.get(key).set(pos, AVERAGEVALUE_INVALID);
            _maxValues.get(key).set(pos, MAXVALUE_INVALID);
        }
    }

    /*
     * Internal: Compute the resampled measure values for a required position in
     * run
     */
    private void computeValues(int pos) throws YAPI_Exception
    {
        // if there is no data stream, exit immediately
        if (_streams.isEmpty()) {
            invalidValue(pos);
            return;
        }

        // search for the earliest stream with useful data for requested measure
        long time = pos * _browseInterval;
        int prevMissing;
        int si = _streams.size() - 1;
        YDataStream stream = _streams.get(si);
        while (stream.get_startTime() > time && si > 0) {
            si--;
            stream = _streams.get(si);
        }

        int streamInterval = stream.get_dataSamplesInterval();
        int thisAvail = stream.get_startTime() / _browseInterval;
        int nextMissing = (stream.get_startTime() + stream.get_rowCount() * streamInterval + streamInterval - 1) / _browseInterval;
        if (nextMissing * _browseInterval <= time && si < _streams.size() - 1) {
            // we went back one step to much
            prevMissing = nextMissing;
            si++;
            stream = _streams.get(si);
            streamInterval = stream.get_dataSamplesInterval();
            thisAvail = stream.get_startTime() / _browseInterval;
            nextMissing = (stream.get_startTime() + stream.get_rowCount() * streamInterval + streamInterval - 1) / _browseInterval;
        } else {
            // nothing interesting before this stream
            prevMissing = thisAvail - 1;
        }

        int nextAvail;
        YDataStream nextStream;
        if (si + 1 >= _streams.size()) {
            nextAvail = pos + 1;
        } else {
            nextStream = _streams.get(si + 1);
            nextAvail = nextStream.get_startTime() / _browseInterval;
        }
        // Check if we are looking for a missing measure
        if (pos >= prevMissing && pos < thisAvail) {
            for (pos = prevMissing; pos < thisAvail; pos++) {
                invalidValue(pos);
            }
            return;
        }
        if (pos >= nextMissing && pos < nextAvail) {
            for (pos = nextMissing; pos < nextAvail; pos++) {
                invalidValue(pos);
            }
            return;
        }
        int row;
        int startTime;
        // process all useful rows from the stream containing requested position, until completely processed
        if (prevMissing < thisAvail) {
            // stream is not a continuation, start with very beginning of stream
            row = 0;
            pos = thisAvail;
            startTime = stream.get_startTime();
        } else {
            // stream is a continuation, start at next interval boundary
            pos = (stream.get_startTime() + _browseInterval - 1) / _browseInterval;
            row = ((pos * _browseInterval - stream.get_startTime() + streamInterval / 2) / streamInterval);
            startTime = stream.get_startTime() + row * streamInterval;
        }
        boolean stopAsap = false;
        HashMap<String, Integer> minCol = new HashMap<String, Integer>();
        HashMap<String, Integer> avgCol = new HashMap<String, Integer>();
        HashMap<String, Integer> maxCol = new HashMap<String, Integer>();
        //_minValues
        HashMap<String, Double> minVal = new HashMap<String, Double>();
        HashMap<String, Double> avgVal = new HashMap<String, Double>();
        HashMap<String, Double> maxVal = new HashMap<String, Double>();
        int divisor = 0;
        int boundary = (pos + 1) * _browseInterval;
        int stopTime = ((stream.get_startTime() + stream.get_rowCount() * stream.get_dataSamplesInterval()) / _browseInterval) * _browseInterval;
        while (startTime < stopTime) {
            int nextTime = startTime + streamInterval;
            //Print("startTime=$startTime -- nextTime=$nextTime -- stopTime=$stopTime -- boundary=$boundary -- row=$row -- pos=$pos\n");
            if (avgCol.isEmpty()) {
                String[] streamsCols = stream.get_columnNames();
                for (int idx = 0; idx < streamsCols.length; idx++) {
                    String colname = streamsCols[idx];
                    if (colname.charAt(colname.length() - 4) == '_') {
                        String name = colname.substring(0, colname.length() - 4);
                        String suffix = colname.substring(colname.length() - 3);
                        if (suffix.equals("min")) {
                            minCol.put(name, idx);
                        } else if (suffix.equals("avg")) {
                            avgCol.put(name, idx);
                        } else if (suffix.equals("max")) {
                            maxCol.put(name, idx);
                        }
                    } else {
                        minCol.put(colname, idx);
                        avgCol.put(colname, idx);
                        maxCol.put(colname, idx);
                    }
                }
            }
            if (divisor == 0) {
                if (boundary <= nextTime) {
                    while (boundary <= nextTime) {
                        for (String key : _measureNames) {
                            _minValues.get(key).set(pos, stream.get_data(row, minCol.get(key)));
                            _avgValues.get(key).set(pos, stream.get_data(row, avgCol.get(key)));
                            _maxValues.get(key).set(pos, stream.get_data(row, maxCol.get(key)));
                        }
                        pos++;
                        boundary = (pos + 1) * _browseInterval;
                    }
                } else {
                    divisor = streamInterval;
                    for (String key : _measureNames) {
                        minVal.put(key, stream.get_data(row, minCol.get(key)));
                        avgVal.put(key, stream.get_data(row, avgCol.get(key)) * streamInterval);
                        maxVal.put(key, stream.get_data(row, maxCol.get(key)));
                    }
                }
            } else {
                divisor += streamInterval;
                for (String key : _measureNames) {
                    minVal.put(key, Math.min(minVal.get(key), stream.getData(row, minCol.get(key))));
                    double val = avgVal.get(key) + streamInterval * stream.getData(row, avgCol.get(key));
                    avgVal.put(key, val);
                    maxVal.put(key, Math.max(maxVal.get(key), stream.getData(row, maxCol.get(key))));
                }

                if (2 * Math.abs(nextTime - boundary) <= streamInterval) {
                    for (String key : _measureNames) {
                        _minValues.get(key).set(pos, minVal.get(key));
                        _avgValues.get(key).set(pos, avgVal.get(key) / divisor);
                        _maxValues.get(key).set(pos, maxVal.get(key));

                    }
                    divisor = 0;
                    pos++;
                    boundary = (pos + 1) * _browseInterval;
                    if (stopAsap) {
                        break;
                    }
                }
            }
            row++;
            if (row < stream.get_rowCount()) {
                startTime = nextTime;
            } else {
                si++;
                if (si >= _streams.size()) {
                    break;
                }
                stream = _streams.get(si);
                startTime = stream.get_startTime();
                streamInterval = stream.get_dataSamplesInterval();
                row = 0;
                avgCol = new HashMap<String, Integer>();
                stopAsap = true;
            }
        }
        if (divisor > 0) {
            // save partially computed value anyway
            for (String key : _measureNames) {
                _minValues.get(key).set(pos, minVal.get(key));
                _avgValues.get(key).set(pos, avgVal.get(key) / divisor);
                _maxValues.get(key).set(pos, maxVal.get(key));
            }
        }
    }

    /**
     * Returns the names of the measures recorded by the data logger. In most
     * case, the measure names match the hardware identifier of the sensor that
     * produced the data.
     *
     * @return a list of strings (the measure names)
     *
     * On failure, throws an exception or returns an empty array.
     */
    public String[] get_measureNames()
    {
        return _measureNames.toArray(new String[0]);
    }

    public String[] getMeasureNames()
    {
        return get_measureNames();
    }

    /**
     * Returns the start time of the data stream, relative to the Jan 1, 1970.
     * If the UTC time was not set in the datalogger at the time of the recording
     * of this data stream, this method returns 0.
     * 
     * This method does not cause any access to the device, as the value
     * is preloaded in the object at instantiation time.
     * 
     * @return an unsigned number corresponding to the number of seconds
     *         between the Jan 1, 1970 and the beginning of this data
     *         stream (i.e. Unix time representation of the absolute time).
     */
    public long get_startTimeUTC()
    {
        return _startTimeUTC;
    }

    public long getStartTimeUTC()
    {
        return _startTimeUTC;
    }

    /**
     * Returns the duration (in seconds) of the data run.
     * When the datalogger is actively recording and the specified run is the current
     * run, calling this method reloads last sequence(s) from device to make sure
     * it includes the latest recorded data.
     * 
     * @return an unsigned number corresponding to the number of seconds
     *         between the beginning of the run (when the module was powered up)
     *         and the last recorded measure.
     */
    public long get_duration() throws YAPI_Exception
    {
        if (_isLive) {
            refresh();
        }
        return _duration;
    }

    public long getDuration() throws YAPI_Exception
    {
        return get_duration();
    }

    /**
     * Returns the number of seconds covered by each value in this run.
     * By default, the value interval is set to the coarsest data rate
     * archived in the data logger flash for this run. The value interval
     * can however be configured at will to a different rate when desired.
     * 
     * @return an unsigned number corresponding to a number of seconds covered
     *         by each data sample in the Run.
     */
    public int get_valueInterval()
    {
        return _browseInterval;
    }

    public int getValueInterval()
    {
        return get_valueInterval();
    }

    /**
     * Changes the number of seconds covered by each value in this run.
     * By default, the value interval is set to the coarsest data rate
     * archived in the data logger flash for this run. The value interval
     * can however be configured at will to a different rate when desired.
     * 
     * @param valueInterval : an integer number of seconds.
     * 
     * @return nothing
     */
    public void set_valueInterval(int valueInterval) throws YAPI_Exception
    {
        YDataStream last = _streams.get(_streams.size() - 1);
        String names[] = last.getColumnNames();
        _minValues = new HashMap<String, ArrayList<Double>>();
        _avgValues = new HashMap<String, ArrayList<Double>>();
        _maxValues = new HashMap<String, ArrayList<Double>>();
        for (String name : names) {
            if (name.charAt(name.length() - 4) == '_') {
                name = name.substring(0, name.length() - 4);
            }
            if (!_minValues.containsKey(name)) {
                _minValues.put(name, new ArrayList<Double>());
                _avgValues.put(name, new ArrayList<Double>());
                _maxValues.put(name, new ArrayList<Double>());
            }
        }
        _browseInterval = valueInterval;
    }

    public void setValueInterval(int valueInterval) throws YAPI_Exception
    {
        set_valueInterval(valueInterval);
    }

    /**
     * Returns the number of values accessible in this run, given the selected data
     * samples interval.
     * When the datalogger is actively recording and the specified run is the current
     * run, calling this method reloads last sequence(s) from device to make sure
     * it includes the latest recorded data.
     * 
     * @return an unsigned number corresponding to the run duration divided by the
     *         samples interval.
     */
    public int get_valueCount() throws YAPI_Exception
    {
        if (_isLive) {
            refresh();
        }
        return (int) (_duration / _browseInterval);
    }

    public int getValueCount() throws YAPI_Exception
    {
        return get_valueCount();
    }

    /**
     * Returns the minimal value of the measure observed at the specified time
     * period.
     * 
     * @param measureName : the name of the desired measure (one of the names
     *         returned by get_measureNames)
     * @param pos : the position index, between 0 and the value returned by
     *         get_valueCount
     * 
     * @return a floating point number (the minimal value)
     * 
     * @throws YAPI_Exception
     */
    public double get_minValue(String measureName, int pos) throws YAPI_Exception
    {
        if (!_minValues.containsKey(measureName) || _minValues.get(measureName).size() <= pos) {
            computeValues(pos);
        }
        return _minValues.get(measureName).get(pos);
    }

    public double getMinValue(String measureName, int pos) throws YAPI_Exception
    {
        return get_minValue(measureName, pos);
    }

    /**
     * Returns the average value of the measure observed at the specified time
     * period.
     * 
     * @param measureName : the name of the desired measure (one of the names
     *         returned by get_measureNames)
     * @param pos : the position index, between 0 and the value returned by
     *         get_valueCount
     * 
     * @return a floating point number (the average value)
     * 
     * @throws YAPI_Exception
     */
    public double get_averageValue(String measureName, int pos) throws YAPI_Exception
    {
        if (!_avgValues.containsKey(measureName) || _avgValues.get(measureName).size() <= pos) {
            computeValues(pos);
        }
        return _avgValues.get(measureName).get(pos);
    }

    public double getAverageValue(String measureName, int pos) throws YAPI_Exception
    {
        return get_averageValue(measureName, pos);
    }

    /**
     * Returns the maximal value of the measure observed at the specified time
     * period.
     * 
     * @param measureName : the name of the desired measure (one of the names
     *         returned by get_measureNames)
     * @param pos : the position index, between 0 and the value returned by
     *         get_valueCount
     * 
     * @return a floating point number (the maximal value)
     * 
     * @throws YAPI_Exception
     */
    public double get_maxValue(String measureName, int pos) throws YAPI_Exception
    {
        if (!_maxValues.containsKey(measureName) || _maxValues.get(measureName).size() <= pos) {
            computeValues(pos);
        }
        return _maxValues.get(measureName).get(pos);
    }

    public double getMaxValue(String measureName, int pos) throws YAPI_Exception
    {
        return get_maxValue(measureName, pos);
    }
}
