/*********************************************************************
 *
 * $Id: YDataStream.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 * YDataStream Class: Sequence of measured data, stored by the data logger
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

import java.util.ArrayList;
import java.util.Locale;

//--- (generated code: YDataStream class start)
/**
 * YDataStream Class: Unformatted data sequence
 *
 * DataStream objects represent bare recorded measure sequences,
 * exactly as found within the data logger present on Yoctopuce
 * sensors.
 *
 * In most cases, it is not necessary to use DataStream objects
 * directly, as the DataSet objects (returned by the
 * get_recordedData() method from sensors and the
 * get_dataSets() method from the data logger) provide
 * a more convenient interface.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YDataStream
{
//--- (end of generated code: YDataStream class start)
    public static final double DATA_INVALID = YAPI.INVALID_DOUBLE;

    //--- (generated code: YDataStream definitions)
    protected YFunction _parent;
    protected int _runNo = 0;
    protected long _utcStamp = 0;
    protected int _nCols = 0;
    protected int _nRows = 0;
    protected double _startTime = 0;
    protected double _duration = 0;
    protected double _dataSamplesInterval = 0;
    protected double _firstMeasureDuration = 0;
    protected ArrayList<String> _columnNames = new ArrayList<>();
    protected String _functionId;
    protected boolean _isClosed;
    protected boolean _isAvg;
    protected double _minVal = 0;
    protected double _avgVal = 0;
    protected double _maxVal = 0;
    protected int _caltyp = 0;
    protected ArrayList<Integer> _calpar = new ArrayList<>();
    protected ArrayList<Double> _calraw = new ArrayList<>();
    protected ArrayList<Double> _calref = new ArrayList<>();
    protected ArrayList<ArrayList<Double>> _values = new ArrayList<>();

    //--- (end of generated code: YDataStream definitions)
    protected YAPI.CalibrationHandlerCallback _calhdl = null;


    YDataStream(YFunction parent, YDataSet dataset, ArrayList<Integer> encoded)
    {
        _parent = parent;
        _initFromDataSet(dataset, encoded);
    }

    //--- (generated code: YDataStream implementation)

    public int _initFromDataSet(YDataSet dataset,ArrayList<Integer> encoded)
    {
        int val;
        int i;
        int maxpos;
        int ms_offset;
        int samplesPerHour;
        double fRaw;
        double fRef;
        ArrayList<Integer> iCalib = new ArrayList<>();
        // decode sequence header to extract data
        _runNo = encoded.get(0).intValue() + (((encoded.get(1).intValue()) << (16)));
        _utcStamp = encoded.get(2).intValue() + (((encoded.get(3).intValue()) << (16)));
        val = encoded.get(4).intValue();
        _isAvg = (((val) & (0x100)) == 0);
        samplesPerHour = ((val) & (0xff));
        if (((val) & (0x100)) != 0) {
            samplesPerHour = samplesPerHour * 3600;
        } else {
            if (((val) & (0x200)) != 0) {
                samplesPerHour = samplesPerHour * 60;
            }
        }
        _dataSamplesInterval = 3600.0 / samplesPerHour;
        ms_offset = encoded.get(6).intValue();
        if (ms_offset < 1000) {
            // new encoding . add the ms to the UTC timestamp
            _startTime = _utcStamp + (ms_offset / 1000.0);
        } else {
            // legacy encoding subtract the measure interval form the UTC timestamp
            _startTime = _utcStamp -  _dataSamplesInterval;
        }
        _firstMeasureDuration = encoded.get(5).intValue();
        if (!(_isAvg)) {
            _firstMeasureDuration = _firstMeasureDuration / 1000.0;
        }
        val = encoded.get(7).intValue();
        _isClosed = (val != 0xffff);
        if (val == 0xffff) {
            val = 0;
        }
        _nRows = val;
        if (_nRows > 0) {
            if (_firstMeasureDuration > 0) {
                _duration = _firstMeasureDuration + (_nRows - 1) * _dataSamplesInterval;
            } else {
                _duration = _nRows * _dataSamplesInterval;
            }
        } else {
            _duration = 0;
        }
        // precompute decoding parameters
        iCalib = dataset._get_calibration();
        _caltyp = iCalib.get(0).intValue();
        if (_caltyp != 0) {
            _calhdl = _parent._yapi._getCalibrationHandler(_caltyp);
            maxpos = iCalib.size();
            _calpar.clear();
            _calraw.clear();
            _calref.clear();
            i = 1;
            while (i < maxpos) {
                _calpar.add(iCalib.get(i));
                i = i + 1;
            }
            i = 1;
            while (i + 1 < maxpos) {
                fRaw = iCalib.get(i).doubleValue();
                fRaw = fRaw / 1000.0;
                fRef = iCalib.get(i + 1).doubleValue();
                fRef = fRef / 1000.0;
                _calraw.add(fRaw);
                _calref.add(fRef);
                i = i + 2;
            }
        }
        // preload column names for backward-compatibility
        _functionId = dataset.get_functionId();
        if (_isAvg) {
            _columnNames.clear();
            _columnNames.add(String.format(Locale.US, "%s_min",_functionId));
            _columnNames.add(String.format(Locale.US, "%s_avg",_functionId));
            _columnNames.add(String.format(Locale.US, "%s_max",_functionId));
            _nCols = 3;
        } else {
            _columnNames.clear();
            _columnNames.add(_functionId);
            _nCols = 1;
        }
        // decode min/avg/max values for the sequence
        if (_nRows > 0) {
            _avgVal = _decodeAvg(encoded.get(8).intValue() + (((((encoded.get(9).intValue()) ^ (0x8000))) << (16))), 1);
            _minVal = _decodeVal(encoded.get(10).intValue() + (((encoded.get(11).intValue()) << (16))));
            _maxVal = _decodeVal(encoded.get(12).intValue() + (((encoded.get(13).intValue()) << (16))));
        }
        return 0;
    }

    public int _parseStream(byte[] sdata) throws YAPI_Exception
    {
        int idx;
        ArrayList<Integer> udat = new ArrayList<>();
        ArrayList<Double> dat = new ArrayList<>();
        if ((sdata).length == 0) {
            _nRows = 0;
            return YAPI.SUCCESS;
        }

        udat = YAPIContext._decodeWords(_parent._json_get_string(sdata));
        _values.clear();
        idx = 0;
        if (_isAvg) {
            while (idx + 3 < udat.size()) {
                dat.clear();
                dat.add(_decodeVal(udat.get(idx + 2).intValue() + (((udat.get(idx + 3).intValue()) << (16)))));
                dat.add(_decodeAvg(udat.get(idx).intValue() + (((((udat.get(idx + 1).intValue()) ^ (0x8000))) << (16))), 1));
                dat.add(_decodeVal(udat.get(idx + 4).intValue() + (((udat.get(idx + 5).intValue()) << (16)))));
                idx = idx + 6;
                _values.add(new ArrayList<Double>(dat));
            }
        } else {
            while (idx + 1 < udat.size()) {
                dat.clear();
                dat.add(_decodeAvg(udat.get(idx).intValue() + (((((udat.get(idx + 1).intValue()) ^ (0x8000))) << (16))), 1));
                _values.add(new ArrayList<Double>(dat));
                idx = idx + 2;
            }
        }

        _nRows = _values.size();
        return YAPI.SUCCESS;
    }

    public String _get_url()
    {
        String url;
        url = String.format(Locale.US, "logger.json?id=%s&run=%d&utc=%d",
        _functionId,_runNo,_utcStamp);
        return url;
    }

    public int loadStream() throws YAPI_Exception
    {
        return _parseStream(_parent._download(_get_url()));
    }

    public double _decodeVal(int w)
    {
        double val;
        val = w;
        val = val / 1000.0;
        if (_caltyp != 0) {
            if (_calhdl != null) {
                val = _calhdl.yCalibrationHandler(val, _caltyp, _calpar, _calraw, _calref);
            }
        }
        return val;
    }

    public double _decodeAvg(int dw,int count)
    {
        double val;
        val = dw;
        val = val / 1000.0;
        if (_caltyp != 0) {
            if (_calhdl != null) {
                val = _calhdl.yCalibrationHandler(val, _caltyp, _calpar, _calraw, _calref);
            }
        }
        return val;
    }

    public boolean isClosed()
    {
        return _isClosed;
    }

    /**
     * Returns the run index of the data stream. A run can be made of
     * multiple datastreams, for different time intervals.
     *
     * @return an unsigned number corresponding to the run index.
     */
    public int get_runIndex()
    {
        return _runNo;
    }

    /**
     * Returns the relative start time of the data stream, measured in seconds.
     * For recent firmwares, the value is relative to the present time,
     * which means the value is always negative.
     * If the device uses a firmware older than version 13000, value is
     * relative to the start of the time the device was powered on, and
     * is always positive.
     * If you need an absolute UTC timestamp, use get_realStartTimeUTC().
     *
     * <b>DEPRECATED</b>: This method has been replaced by get_realStartTimeUTC().
     *
     * @return an unsigned number corresponding to the number of seconds
     *         between the start of the run and the beginning of this data
     *         stream.
     */
    public int get_startTime()
    {
        return (int)(_utcStamp - (System.currentTimeMillis() / 1000L));
    }

    /**
     * Returns the start time of the data stream, relative to the Jan 1, 1970.
     * If the UTC time was not set in the datalogger at the time of the recording
     * of this data stream, this method returns 0.
     *
     * <b>DEPRECATED</b>: This method has been replaced by get_realStartTimeUTC().
     *
     * @return an unsigned number corresponding to the number of seconds
     *         between the Jan 1, 1970 and the beginning of this data
     *         stream (i.e. Unix time representation of the absolute time).
     */
    public long get_startTimeUTC()
    {
        return (int) (double)Math.round(_startTime);
    }

    /**
     * Returns the start time of the data stream, relative to the Jan 1, 1970.
     * If the UTC time was not set in the datalogger at the time of the recording
     * of this data stream, this method returns 0.
     *
     * @return a floating-point number  corresponding to the number of seconds
     *         between the Jan 1, 1970 and the beginning of this data
     *         stream (i.e. Unix time representation of the absolute time).
     */
    public double get_realStartTimeUTC()
    {
        return _startTime;
    }

    /**
     * Returns the number of milliseconds between two consecutive
     * rows of this data stream. By default, the data logger records one row
     * per second, but the recording frequency can be changed for
     * each device function
     *
     * @return an unsigned number corresponding to a number of milliseconds.
     */
    public int get_dataSamplesIntervalMs()
    {
        return (int) (double)Math.round(_dataSamplesInterval*1000);
    }

    public double get_dataSamplesInterval()
    {
        return _dataSamplesInterval;
    }

    public double get_firstDataSamplesInterval()
    {
        return _firstMeasureDuration;
    }

    /**
     * Returns the number of data rows present in this stream.
     *
     * If the device uses a firmware older than version 13000,
     * this method fetches the whole data stream from the device
     * if not yet done, which can cause a little delay.
     *
     * @return an unsigned number corresponding to the number of rows.
     *
     * @throws YAPI_Exception on error
     */
    public int get_rowCount() throws YAPI_Exception
    {
        if ((_nRows != 0) && _isClosed) {
            return _nRows;
        }
        loadStream();
        return _nRows;
    }

    /**
     * Returns the number of data columns present in this stream.
     * The meaning of the values present in each column can be obtained
     * using the method get_columnNames().
     *
     * If the device uses a firmware older than version 13000,
     * this method fetches the whole data stream from the device
     * if not yet done, which can cause a little delay.
     *
     * @return an unsigned number corresponding to the number of columns.
     *
     * @throws YAPI_Exception on error
     */
    public int get_columnCount() throws YAPI_Exception
    {
        if (_nCols != 0) {
            return _nCols;
        }
        loadStream();
        return _nCols;
    }

    /**
     * Returns the title (or meaning) of each data column present in this stream.
     * In most case, the title of the data column is the hardware identifier
     * of the sensor that produced the data. For streams recorded at a lower
     * recording rate, the dataLogger stores the min, average and max value
     * during each measure interval into three columns with suffixes _min,
     * _avg and _max respectively.
     *
     * If the device uses a firmware older than version 13000,
     * this method fetches the whole data stream from the device
     * if not yet done, which can cause a little delay.
     *
     * @return a list containing as many strings as there are columns in the
     *         data stream.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<String> get_columnNames() throws YAPI_Exception
    {
        if (_columnNames.size() != 0) {
            return _columnNames;
        }
        loadStream();
        return _columnNames;
    }

    /**
     * Returns the smallest measure observed within this stream.
     * If the device uses a firmware older than version 13000,
     * this method will always return YDataStream.DATA_INVALID.
     *
     * @return a floating-point number corresponding to the smallest value,
     *         or YDataStream.DATA_INVALID if the stream is not yet complete (still recording).
     *
     * @throws YAPI_Exception on error
     */
    public double get_minValue() throws YAPI_Exception
    {
        return _minVal;
    }

    /**
     * Returns the average of all measures observed within this stream.
     * If the device uses a firmware older than version 13000,
     * this method will always return YDataStream.DATA_INVALID.
     *
     * @return a floating-point number corresponding to the average value,
     *         or YDataStream.DATA_INVALID if the stream is not yet complete (still recording).
     *
     * @throws YAPI_Exception on error
     */
    public double get_averageValue() throws YAPI_Exception
    {
        return _avgVal;
    }

    /**
     * Returns the largest measure observed within this stream.
     * If the device uses a firmware older than version 13000,
     * this method will always return YDataStream.DATA_INVALID.
     *
     * @return a floating-point number corresponding to the largest value,
     *         or YDataStream.DATA_INVALID if the stream is not yet complete (still recording).
     *
     * @throws YAPI_Exception on error
     */
    public double get_maxValue() throws YAPI_Exception
    {
        return _maxVal;
    }

    public double get_realDuration()
    {
        if (_isClosed) {
            return _duration;
        }
        return (double) (System.currentTimeMillis() / 1000L) - _utcStamp;
    }

    /**
     * Returns the whole data set contained in the stream, as a bidimensional
     * table of numbers.
     * The meaning of the values present in each column can be obtained
     * using the method get_columnNames().
     *
     * This method fetches the whole data stream from the device,
     * if not yet done.
     *
     * @return a list containing as many elements as there are rows in the
     *         data stream. Each row itself is a list of floating-point
     *         numbers.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<ArrayList<Double>> get_dataRows() throws YAPI_Exception
    {
        if ((_values.size() == 0) || !(_isClosed)) {
            loadStream();
        }
        return _values;
    }

    /**
     * Returns a single measure from the data stream, specified by its
     * row and column index.
     * The meaning of the values present in each column can be obtained
     * using the method get_columnNames().
     *
     * This method fetches the whole data stream from the device,
     * if not yet done.
     *
     * @param row : row index
     * @param col : column index
     *
     * @return a floating-point number
     *
     * @throws YAPI_Exception on error
     */
    public double get_data(int row,int col) throws YAPI_Exception
    {
        if ((_values.size() == 0) || !(_isClosed)) {
            loadStream();
        }
        if (row >= _values.size()) {
            return DATA_INVALID;
        }
        if (col >= _values.get(row).size()) {
            return DATA_INVALID;
        }
        return _values.get(row).get(col).doubleValue();
    }

    //--- (end of generated code: YDataStream implementation)
}
