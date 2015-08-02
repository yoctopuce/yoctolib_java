/*********************************************************************
 *
 * $Id: YOldDataStream.java 20376 2015-05-19 14:18:47Z seb $
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * YOldDataStream Class: Sequence of measured data, returned by the data logger
 *  
 * A data stream is a small collection of consecutive measures for a set
 * of sensors. A few properties are available directly from the object itself
 * (they are preloaded at instantiation time), while most other properties and
 * the actual data are loaded on demand when accessed for the first time.
 *
 * This is the old version of the YDataStream class, used for backward-compatibility
 * with devices with firmware < 13000
 */
public class YOldDataStream extends YDataStream
{
    YDataLogger _dataLogger;
    int _timeStamp;
    int _interval;

    public YOldDataStream(YDataLogger parent, int run, int stamp, long utc, int itv)
    {
        super(parent);
        _dataLogger = parent;
        _runNo = run;
        _timeStamp = stamp;
        _utcStamp = utc;
        _interval = itv;
        _samplesPerHour = 3600 / _interval;
        _isClosed = true;
        _minVal = DATA_INVALID;
        _avgVal = DATA_INVALID;
        _maxVal = DATA_INVALID;
    }

    public int loadStream() throws YAPI_Exception
    {
        JSONArray coldiv = null;
        int coltyp[] = null;
        double colscl[];
        int colofs[];

        JSONObject jsonObj = null;

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
                _columnNames = new ArrayList<String>(_nCols);
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
                for (int c = 0; c < _nCols; c++) {
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
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "json parse error");
        }

        if (jsonObj != null && jsonObj.has("data")) {
            if (_nCols == 0 || coldiv == null || coltyp == null) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "DataStream corrupted");
            }
            ArrayList<Integer> udata = null;
            try {
                String data = jsonObj.getString("data");
                udata = YAPI._decodeWords(data);
            } catch (JSONException ignore) {
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
                    val_d = YAPI._decimalToDouble(val_i - 32767);
                }
                dat.add(val_d);
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
     * Returns the relative start time of the data stream, measured in seconds.
     * For recent firmwares, the value is relative to the present time,
     * which means the value is always negative.
     * If the device uses a firmware older than version 13000, value is
     * relative to the start of the time the device was powered on, and
     * is always positive.
     * If you need an absolute UTC timestamp, use get_startTimeUTC().
     * 
     * @return an unsigned number corresponding to the number of seconds
     *         between the start of the run and the beginning of this data
     *         stream.
     */
    @Override
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
    @Override
    public double get_dataSamplesInterval()
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
    public double getDataSamplesInterval()
    {
        return _interval;
    }
}
