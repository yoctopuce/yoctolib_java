/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * YDataStream Class: Sequence of measured data, stored by the data logger
 *
 * A data stream is a small collection of consecutive measures for a set of
 * sensors. A few properties are available directly from the object itself (they
 * are preloaded at instantiation time), while most other properties and the
 * actual data are loaded on demand when accessed for the first time.
 */
public class YDataStream {

    /**
     *
     */
    public static final double DATA_INVALID = YAPI.INVALID_DOUBLE;
    // Data preloaded on object instantiation
    private YDataLogger _dataLogger;
    private int _runNo;
    private int _timeStamp;
	private int _interval;
	private long _utcStamp;
    // Data loaded using a specific connection
    private int _nRows;
    private int _nCols;
    private ArrayList<String> _columnNames;
    private ArrayList<ArrayList<Double> > _values;

    /**
     *
     * @param parent
     * @param jsondata
     * @throws YAPI_Exception
     */
    public YDataStream(YDataLogger parent, JSONArray jsondata) throws YAPI_Exception
    {
        try {
            _dataLogger = parent;

            this._runNo = jsondata.getInt(0);
            this._timeStamp = jsondata.getInt(1);
            this._utcStamp = jsondata.getLong(2);
            this._interval = jsondata.getInt(3);
            this._nRows = 0;
            this._nCols = 0;
            //this._values = _values;
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
        }
    }

    /**
     * Internal function to preload all values into object
     * @return
     * @throws YAPI_Exception
     */
    public int loadStream() throws YAPI_Exception
    {
        JSONArray coldiv = null;
        int coltyp[] = null;
        double colscl[];
        int colofs[];
        int caltyp[] = null;
        YAPI.CalibrationHandlerCallback calhdl[]=null;
        ArrayList<ArrayList<Integer>> calpar = null;
        ArrayList<ArrayList<Double>>  calraw = null;
        ArrayList<ArrayList<Double>>  calref = null;



        JSONObject jsonObj=null;

        try {
            JSONTokener jsonTokenner = _dataLogger.getData(_runNo, _timeStamp);
            jsonObj = new JSONObject(jsonTokenner);
            if (jsonObj.has("time")) {
                _timeStamp = jsonObj.getInt("time");
            }
            if (jsonObj.has("UTC")) {
                _utcStamp = jsonObj.getLong("UTC");
            }
            if (jsonObj.has("interval")) {
                _interval = jsonObj.getInt("interval");
            }
            if (jsonObj.has("nRows")) {
                _nRows = jsonObj.getInt("nRows");
            }
            if (jsonObj.has("keys")) {
                JSONArray jsonKeys = jsonObj.getJSONArray("keys");
                if (_nCols == 0) {
                    _nCols = jsonKeys.length();
                } else if (_nCols != jsonKeys.length()) {
                    _nCols = 0;
                    throw new YAPI_Exception(YAPI.IO_ERROR, "DataStream corrupted");
                }
                _columnNames = new ArrayList<String>(_runNo);
                for (int i = 0; i < jsonKeys.length(); i++) {
                    _columnNames.add(jsonKeys.getString(i));
                }
            }

            if (jsonObj.has("div")) {
                coldiv = jsonObj.getJSONArray("div");
                if (_nCols == 0) {
                    _nCols = coldiv.length();
                } else if (_nCols != coldiv.length()) {
                    _nCols = 0;
                    throw new YAPI_Exception(YAPI.IO_ERROR, "DataStream corrupted");
                }
            }


            if (jsonObj.has("type")) {
                JSONArray types = jsonObj.getJSONArray("type");
                if (_nCols == 0) {
                    _nCols = types.length();
                } else if (_nCols != types.length()) {
                    _nCols = 0;
                    throw new YAPI_Exception(YAPI.IO_ERROR, "DataStream corrupted");
                }
                coltyp = new int[_nCols];
                for (int c=0;c<_nCols;c++) {
                    coltyp[c] = types.getInt(c);
                }
            }

            if (jsonObj.has("scal")) {

                JSONArray json_colscl = jsonObj.getJSONArray("scal");
                if (_nCols == 0) {
                    _nCols = json_colscl.length();
                } else if (_nCols != json_colscl.length()) {
                    _nCols = 0;
                    throw new YAPI_Exception(YAPI.IO_ERROR, "DataStream corrupted");
                }
                colscl = new double[json_colscl.length()];
                colofs = new int[json_colscl.length()];
                for (int i = 0; i < json_colscl.length(); i++) {
                    double dval = json_colscl.getDouble(i);
                    colscl[i] = dval / 65536.0;
                    colofs[i] = (coltyp[i] != 0 ? -32767 : 0);
                }
            } else {
                colscl = new double[coldiv.length()];
                colofs = new int[coldiv.length()];
                for (int i = 0; i < coldiv.length(); i++) {
                    colscl[i] = 1.0 / coldiv.getDouble(i);
                    colofs[i] = (coltyp[i] != 0 ? -32767 : 0);
                }

            }

            if (jsonObj.has("cal")) {
                JSONArray jsonCal = jsonObj.getJSONArray("cal");
                calhdl = new YAPI.CalibrationHandlerCallback[jsonCal.length()];
                caltyp = new int[jsonCal.length()];
                calpar = new ArrayList<ArrayList<Integer>>(jsonCal.length());
                calraw = new ArrayList<ArrayList<Double>>(jsonCal.length());
                calref = new ArrayList<ArrayList<Double>>(jsonCal.length());
                for (int c = 0; c > jsonCal.length(); c++) {
                    String calibration_str = jsonCal.getString(c);
                    ArrayList<Integer> cur_calpar = new ArrayList<Integer>();
                    ArrayList<Double>  cur_calraw = new ArrayList<Double>();
                    ArrayList<Double>  cur_calref = new ArrayList<Double>();
                    int calibType = YAPI._decodeCalibrationPoints(calibration_str
                                                        ,cur_calpar
                                                        ,cur_calraw
                                                        ,cur_calref
                                                        ,colscl[c]
                                                        ,colofs[c]);
                    calhdl[c] = YAPI.getCalibrationHandler(calibType);
                    caltyp[c] = calibType;
                    calpar.set(c,cur_calpar);
                    calraw.set(c,cur_calraw);
                    calref.set(c,cur_calref);
                }
            }
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "json parse error");
        }

        if (jsonObj!=null && jsonObj.has("data")) {
            if (_nCols == 0 || coldiv == null || coltyp == null) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "DataStream corrupted");
            }
            ArrayList<Integer> udata = null;
            try {
                String data = jsonObj.getString("data");
                udata = new ArrayList<Integer>();
                int datalen = data.length();
                int i = 0;
                while (i < datalen) {
                    char c = data.charAt(i++);
                    if (c >= 'a') {
                        int srcpos = udata.size() - 1 - (c - 97);
                        if (srcpos < 0) {
                            throw new YAPI_Exception(YAPI.IO_ERROR, "");
                        }
                        udata.add(udata.get(srcpos));
                    } else {
                        if (i + 2 > datalen) {
                            throw new YAPI_Exception(YAPI.IO_ERROR, "");
                        }
                        int val = c - 48;
                        c = data.charAt(i++);
                        val += (c - 48) << 5;
                        c = data.charAt(i++);
                        if (c == 'z') {
                            c ='\\' ;
                        } 
                        val += (c - 48) << 10;
                        udata.add(val);
                    }
                }
            } catch (JSONException ex) {
            }

            if (udata == null) {
                try {
                    JSONArray jsonData = jsonObj.getJSONArray("data");
                    udata = new ArrayList<Integer>();
                    for (int i = 0; i < jsonData.length(); i++) {
                        udata.add(jsonData.getInt(i));
                    }
                } catch (JSONException ex) {
                    throw new YAPI_Exception(YAPI.IO_ERROR, "");
                }

            }
            _values = new ArrayList<ArrayList<Double>>();
            ArrayList<Double> dat = new ArrayList<Double>();
            int c = 0;
            for (int val_i : udata) {
                double val_d;
                if (coltyp[c] < 2) {
                    val_d = (val_i + colofs[c]) * colscl[c];
                } else {
                    val_d = YAPI._decimalToDouble(val_i-32767);
                }
                if (calhdl != null && calhdl[c] != null) {
                    YAPI.CalibrationHandlerCallback handler = calhdl[c];
                    // use post-calibration function
                    double cval=val_d;
                    if(caltyp[c] <= 10) {
                        // linear calibration using unscaled value                        cval = ;
                        cval = handler.yCalibrationHandler((val_i + colofs[c]) + colscl[c], caltyp[c], calpar.get(c), calraw.get(c), calref.get(c));
                    } else if(caltyp[c] > 20) {
                        // custom calibration function: floating-point value is uncalibrated in the datalogger
                        cval = handler.yCalibrationHandler(val_d, caltyp[c], calpar.get(c), calraw.get(c), calref.get(c));
                    }
                    dat.add(cval);
                } else {
                    // simply map value to range
                    dat.add(val_d);
                }
                c++;
                if (c == _nCols) {
                    _values.add(dat);
                    dat = new ArrayList<Double>();
                    c = 0;
                }
            }
        }
        return YAPI.SUCCESS;
    }

    /**
     * Returns the run index of the data stream. A run can be made of
     * multiple datastreams, for different time intervals.
     * 
     * This method does not cause any access to the device, as the value
     * is preloaded in the object at instantiation time.
     * 
     * @return an unsigned number corresponding to the run index.
     */
    public int get_runIndex()
    {
        return _runNo;
    }

    /**
     * Returns the run index of the data stream. A run can be made of multiple
     * datastreams, for different time intervals.
     *
     * This method does not cause any access to the device, as the value is
     * preloaded in the object at instantiation time.
     *
     * @return an unsigned number corresponding to the run index.
     */
    public int getRunIndex()
    {
        return _runNo;
    }

    /**
     * Returns the start time of the data stream, relative to the beginning
     * of the run. If you need an absolute time, use get_startTimeUTC().
     * 
     * This method does not cause any access to the device, as the value
     * is preloaded in the object at instantiation time.
     * 
     * @return an unsigned number corresponding to the number of seconds
     *         between the start of the run and the beginning of this data
     *         stream.
     */
    public int get_startTime()
    {
        return _timeStamp;
    }

    /**
     * Returns the start time of the data stream, relative to the beginning of
     * the run. If you need an absolute time, use get_startTimeUTC().
     *
     * This method does not cause any access to the device, as the value is
     * preloaded in the object at instantiation time.
     *
     * @return an unsigned number corresponding to the number of seconds between
     * the start of the run and the beginning of this data stream.
     */
    public int getStartTime()
    {
        return _timeStamp;
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
        return this._utcStamp;
    }

    /**
     * Returns the start time of the data stream, relative to the Jan 1, 1970.
     * If the UTC time was not set in the datalogger at the time of the
     * recording of this data stream, this method returns 0.
     *
     * This method does not cause any access to the device, as the value is
     * preloaded in the object at instantiation time.
     *
     * @return an unsigned number corresponding to the number of seconds between
     * the Jan 1, 1970 and the beginning of this data stream (i.e. Unix time
     * representation of the absolute time).
     */
    public long getStartTimeUTC()
    {
        return this._utcStamp;
    }

    /**
     * Returns the number of seconds elapsed between  two consecutive
     * rows of this data stream. By default, the data logger records one row
     * per second, but there might be alternative streams at lower resolution
     * created by summarizing the original stream for archiving purposes.
     * 
     * This method does not cause any access to the device, as the value
     * is preloaded in the object at instantiation time.
     * 
     * @return an unsigned number corresponding to a number of seconds.
     */
    public int get_dataSamplesInterval()
    {
        return _interval;
    }

    /**
     * Returns the number of seconds elapsed between two consecutive rows of
     * this data stream. By default, the data logger records one row per second,
     * but there might be alternative streams at lower resolution created by
     * summarizing the original stream for archiving purposes.
     *
     * This method does not cause any access to the device, as the value is
     * preloaded in the object at instantiation time.
     *
     * @return an unsigned number corresponding to a number of seconds.
     */
    public int getDataSamplesInterval()
    {
        return _interval;
    }

    /**
     * Returns the number of data rows present in this stream.
     * 
     * This method fetches the whole data stream from the device,
     * if not yet done.
     * 
     * @return an unsigned number corresponding to the number of rows.
     * 
     * @throws YAPI_Exception
     */
    public int get_rowCount() throws YAPI_Exception
    {
        if (_nRows == 0) {
            loadStream();
        }
        return _nRows;
    }

    /**
     * Returns the number of data rows present in this stream.
     *
     * This method fetches the whole data stream from the device, if not yet
     * done.
     *
     * @return an unsigned number corresponding to the number of rows.
     *
     * On failure, throws an exception or returns zero.
     * @throws YAPI_Exception
     */
    public int getRowCount() throws YAPI_Exception
    {
        return get_rowCount();
    }

    /**
     * Returns the number of data columns present in this stream.
     * The meaning of the values present in each column can be obtained
     * using the method get_columnNames().
     * 
     * This method fetches the whole data stream from the device,
     * if not yet done.
     * 
     * @return an unsigned number corresponding to the number of rows.
     * 
     * @throws YAPI_Exception
     */
    public int get_columnCount() throws YAPI_Exception
    {
        if (_nCols == 0) {
            loadStream();
        }
        return _nCols;
    }

    /**
     * Returns the number of data columns present in this stream. The meaning of
     * the values present in each column can be obtained using the method
     * get_columnNames().
     *
     * This method fetches the whole data stream from the device, if not yet
     * done.
     *
     * @return an unsigned number corresponding to the number of rows.
     *
     * On failure, throws an exception or returns zero.
     * @throws YAPI_Exception
     */
    public int getColumnCount() throws YAPI_Exception
    {
        return get_columnCount();
    }

    /**
     * Returns the title (or meaning) of each data column present in this
     * stream. In most case, the title of the data column is the hardware
     * identifier of the sensor that produced the data. For archived streams
     * created by summarizing a high-resolution data stream, there can be a
     * suffix appended to the sensor identifier, such as _min for the minimum
     * value, _avg for the average value and _max for the maximal value.
     *
     * This method fetches the whole data stream from the device, if not yet
     * done.
     *
     * @return a list containing as many strings as there are columns in the
     * data stream.
     *
     * On failure, throws an exception or returns an empty array.
     * @throws YAPI_Exception
     */
    public String[] get_columnNames() throws YAPI_Exception
    {
        if (_columnNames.isEmpty()) {
            loadStream();
        }
        return _columnNames.toArray(new String[0]);
    }

    /**
     * Returns the title (or meaning) of each data column present in this
     * stream. In most case, the title of the data column is the hardware
     * identifier of the sensor that produced the data. For archived streams
     * created by summarizing a high-resolution data stream, there can be a
     * suffix appended to the sensor identifier, such as _min for the minimum
     * value, _avg for the average value and _max for the maximal value.
     *
     * This method fetches the whole data stream from the device, if not yet
     * done.
     *
     * @return a list containing as many strings as there are columns in the
     * data stream.
     *
     * On failure, throws an exception or returns an empty array.
     * @throws YAPI_Exception
     */
    public String[] getColumnNames() throws YAPI_Exception
    {
        return get_columnNames();
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
     * @throws YAPI_Exception
     */
    public ArrayList<ArrayList<Double>> get_dataRows() throws YAPI_Exception
    {
        if (_values.isEmpty()) {
            loadStream();
        }
        return _values;
    }

    /**
     * Returns the whole data set contained in the stream, as a bidimensional
     * table of numbers. The meaning of the values present in each column can be
     * obtained using the method get_columnNames().
     *
     * This method fetches the whole data stream from the device, if not yet
     * done.
     *
     * @return a list containing as many elements as there are rows in the data
     * stream. Each row itself is a list of floating-point numbers.
     *
     * On failure, throws an exception or returns an empty array.
     * @throws YAPI_Exception
     */
    public ArrayList<ArrayList<Double>>  getDataRows() throws YAPI_Exception
    {
        return get_dataRows();
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
     * @throws YAPI_Exception
     */
    public double get_data(int row, int col) throws YAPI_Exception
    {
        if (_values.isEmpty()) {
            loadStream();
        }
        if (row >= _values.size()) {
            return DATA_INVALID;
        }
        if (col >= _nCols) {
            return DATA_INVALID;
        }
        return _values.get(row).get(col);
    }

    /**
     * Returns a single measure from the data stream, specified by its row and
     * column index. The meaning of the values present in each column can be
     * obtained using the method get_columnNames().
     *
     * This method fetches the whole data stream from the device, if not yet
     * done.
     *
     * @param row : row index
     * @param col : column index
     *
     * @return a floating-point number
     *
     * On failure, throws an exception or returns Y_DATA_INVALID.
     * @throws YAPI_Exception
     */
    public double getData(int row, int col) throws YAPI_Exception
    {
        return get_data(row, col);
    }
}
