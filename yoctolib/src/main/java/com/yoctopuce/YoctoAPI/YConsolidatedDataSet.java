/*
 * $Id: YConsolidatedDataSet.java 33713 2018-12-14 14:20:19Z seb $
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

//--- (generated code: YConsolidatedDataSet class start)
/**
 * YConsolidatedDataSet Class: Cross-sensor consolidated data sequence.
 *
 * YConsolidatedDataSet objects make it possible to retrieve a set of
 * recorded measures from multiple sensors, for a specified time interval.
 * They can be used to load data points progressively, and to receive
 * data records by timestamp, one by one..
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YConsolidatedDataSet
{
//--- (end of generated code: YConsolidatedDataSet class start)

    //--- (generated code: YConsolidatedDataSet definitions)
    protected double _start = 0;
    protected double _end = 0;
    protected int _nsensors = 0;
    protected ArrayList<YSensor> _sensors = new ArrayList<>();
    protected ArrayList<YDataSet> _datasets = new ArrayList<>();
    protected ArrayList<Integer> _progresss = new ArrayList<>();
    protected ArrayList<Integer> _nextidx = new ArrayList<>();
    protected ArrayList<Double> _nexttim = new ArrayList<>();

    //--- (end of generated code: YConsolidatedDataSet definitions)

    // YConsolidatedDataSet constructor, when instantiated directly by a function
    public YConsolidatedDataSet(double startTime, double endTime, ArrayList<YSensor> sensorList)
    {
        imm_init(startTime, endTime, sensorList);
    }


    //--- (generated code: YConsolidatedDataSet implementation)

    public int imm_init(double startt,double endt,ArrayList<YSensor> sensorList)
    {
        _start = startt;
        _end = endt;
        _sensors = sensorList;
        _nsensors = -1;
        return YAPI.SUCCESS;
    }

    /**
     * Returns an object holding historical data for multiple
     * sensors, for a specified time interval.
     * The measures will be retrieved from the data logger, which must have been turned
     * on at the desired time. The resulting object makes it possible to load progressively
     * a large set of measures from multiple sensors, consolidating data on the fly
     * to align records based on measurement timestamps.
     *
     * @param sensorNames : array of logical names or hardware identifiers of the sensors
     *         for which data must be loaded from their data logger.
     * @param startTime : the start of the desired measure time interval,
     *         as a Unix timestamp, i.e. the number of seconds since
     *         January 1, 1970 UTC. The special value 0 can be used
     *         to include any measure, without initial limit.
     * @param endTime : the end of the desired measure time interval,
     *         as a Unix timestamp, i.e. the number of seconds since
     *         January 1, 1970 UTC. The special value 0 can be used
     *         to include any measure, without ending limit.
     *
     * @return an instance of YConsolidatedDataSet, providing access to
     *         consolidated historical data. Records can be loaded progressively
     *         using the YConsolidatedDataSet.nextRecord() method.
     */
    public static YConsolidatedDataSet Init(ArrayList<String> sensorNames,double startTime,double endTime) throws YAPI_Exception
    {
        int nSensors;
        ArrayList<YSensor> sensorList = new ArrayList<>();
        int idx;
        String sensorName;
        YSensor s;
        YConsolidatedDataSet obj;
        nSensors = sensorNames.size();
        sensorList.clear();
        idx = 0;
        while (idx < nSensors) {
            sensorName = sensorNames.get(idx);
            s = YSensor.FindSensor(sensorName);
            sensorList.add(s);
            idx = idx + 1;
        }
        obj = new YConsolidatedDataSet(startTime, endTime, sensorList);
        return obj;
    }

    /**
     * Extracts the next data record from the data logger of all sensors linked to this
     * object.
     *
     * @param datarec : array of floating point numbers, that will be filled by the
     *         function with the timestamp of the measure in first position,
     *         followed by the measured value in next positions.
     *
     * @return an integer in the range 0 to 100 (percentage of completion),
     *         or a negative error code in case of failure.
     *
     * @throws YAPI_Exception on error
     */
    public int nextRecord(ArrayList<Double> datarec) throws YAPI_Exception
    {
        int s;
        int idx;
        YSensor sensor;
        YDataSet newdataset;
        int globprogress;
        int currprogress;
        double currnexttim;
        double newvalue;
        ArrayList<YMeasure> measures = new ArrayList<>();
        double nexttime;
        //
        // Ensure the dataset have been retrieved
        //
        if (_nsensors == -1) {
            _nsensors = _sensors.size();
            _datasets.clear();
            _progresss.clear();
            _nextidx.clear();
            _nexttim.clear();
            s = 0;
            while (s < _nsensors) {
                sensor = _sensors.get(s);
                newdataset = sensor.get_recordedData(_start, _end);
                _datasets.add(newdataset);
                _progresss.add(0);
                _nextidx.add(0);
                _nexttim.add(0.0);
                s = s + 1;
            }
        }
        datarec.clear();
        //
        // Find next timestamp to process
        //
        nexttime = 0;
        s = 0;
        while (s < _nsensors) {
            currnexttim = _nexttim.get(s).doubleValue();
            if (currnexttim == 0) {
                idx = _nextidx.get(s).intValue();
                measures = _datasets.get(s).get_measures();
                currprogress = _progresss.get(s).intValue();
                while ((idx >= measures.size()) && (currprogress < 100)) {
                    currprogress = _datasets.get(s).loadMore();
                    if (currprogress < 0) {
                        currprogress = 100;
                    }
                    _progresss.set( s, currprogress);
                    measures = _datasets.get(s).get_measures();
                }
                if (idx < measures.size()) {
                    currnexttim = measures.get(idx).get_endTimeUTC();
                    _nexttim.set( s, currnexttim);
                }
            }
            if (currnexttim > 0) {
                if ((nexttime == 0) || (nexttime > currnexttim)) {
                    nexttime = currnexttim;
                }
            }
            s = s + 1;
        }
        if (nexttime == 0) {
            return 100;
        }
        //
        // Extract data for this timestamp
        //
        datarec.clear();
        datarec.add(nexttime);
        globprogress = 0;
        s = 0;
        while (s < _nsensors) {
            if (_nexttim.get(s).doubleValue() == nexttime) {
                idx = _nextidx.get(s).intValue();
                measures = _datasets.get(s).get_measures();
                newvalue = measures.get(idx).get_averageValue();
                datarec.add(newvalue);
                _nexttim.set( s, 0.0);
                _nextidx.set( s, idx+1);
            } else {
                datarec.add(Double.NaN);
            }
            currprogress = _progresss.get(s).intValue();
            globprogress = globprogress + currprogress;
            s = s + 1;
        }
        if (globprogress > 0) {
            globprogress = ((globprogress) / (_nsensors));
            if (globprogress > 99) {
                globprogress = 99;
            }
        }
        return globprogress;
    }

    //--- (end of generated code: YConsolidatedDataSet implementation)
}

