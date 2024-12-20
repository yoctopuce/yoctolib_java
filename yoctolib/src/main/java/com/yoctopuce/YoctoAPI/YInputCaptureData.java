/*********************************************************************
 *
 * $Id: YInputCaptureData.java 63325 2024-11-13 09:33:33Z seb $
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

//--- (generated code: YInputCaptureData return codes)
//--- (end of generated code: YInputCaptureData return codes)
//--- (generated code: YInputCaptureData class start)
/**
 * YInputCaptureData Class: Sampled data from a Yoctopuce electrical sensor
 *
 * InputCaptureData objects represent raw data
 * sampled by the analog/digital converter present in
 * a Yoctopuce electrical sensor. When several inputs
 * are samples simultaneously, their data are provided
 * as distinct series.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YInputCaptureData
{
//--- (end of generated code: YInputCaptureData class start)
    protected YAPIContext _yapi;

//--- (generated code: YInputCaptureData definitions)
    protected int _fmt = 0;
    protected int _var1size = 0;
    protected int _var2size = 0;
    protected int _var3size = 0;
    protected int _nVars = 0;
    protected int _recOfs = 0;
    protected int _nRecs = 0;
    protected int _samplesPerSec = 0;
    protected int _trigType = 0;
    protected double _trigVal = 0;
    protected int _trigPos = 0;
    protected double _trigUTC = 0;
    protected String _var1unit;
    protected String _var2unit;
    protected String _var3unit;
    protected ArrayList<Double> _var1samples = new ArrayList<>();
    protected ArrayList<Double> _var2samples = new ArrayList<>();
    protected ArrayList<Double> _var3samples = new ArrayList<>();

    //--- (end of generated code: YInputCaptureData definitions)

    YInputCaptureData(YFunction yfun, byte[] sdata) throws YAPI_Exception
    {
        _yapi = yfun._yapi;
        try {
            //--- (generated code: YInputCaptureData attributes initialization)
        //--- (end of generated code: YInputCaptureData attributes initialization)
        } catch (Exception e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "invalid json struct for YInputCaptureData");
        }
        _decodeSnapBin(sdata);
    }

    //--- (generated code: YInputCaptureData implementation)

    public int _decodeU16(byte[] sdata,int ofs)
    {
        int v;
        v = (sdata[ofs] & 0xff);
        v = v + 256 * (sdata[ofs+1] & 0xff);
        return v;
    }

    public double _decodeU32(byte[] sdata,int ofs)
    {
        double v;
        v = _decodeU16(sdata, ofs);
        v = v + 65536.0 * _decodeU16(sdata, ofs+2);
        return v;
    }

    public double _decodeVal(byte[] sdata,int ofs,int len)
    {
        double v;
        double b;
        v = _decodeU16(sdata, ofs);
        b = 65536.0;
        ofs = ofs + 2;
        len = len - 2;
        while (len > 0) {
            v = v + b * (sdata[ofs] & 0xff);
            b = b * 256;
            ofs = ofs + 1;
            len = len - 1;
        }
        if (v > (b/2)) {
            // negative number
            v = v - b;
        }
        return v;
    }

    public int _decodeSnapBin(byte[] sdata) throws YAPI_Exception
    {
        int buffSize;
        int recOfs;
        int ms;
        int recSize;
        int count;
        int mult1;
        int mult2;
        int mult3;
        double v;

        buffSize = (sdata).length;
        //noinspection DoubleNegation
        if (!(buffSize >= 24)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid snapshot data (too short)");}
        _fmt = (sdata[0] & 0xff);
        _var1size = (sdata[1] & 0xff) - 48;
        _var2size = (sdata[2] & 0xff) - 48;
        _var3size = (sdata[3] & 0xff) - 48;
        //noinspection DoubleNegation
        if (!(_fmt == 83)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Unsupported snapshot format");}
        //noinspection DoubleNegation
        if (!((_var1size >= 2) && (_var1size <= 4))) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid sample size");}
        //noinspection DoubleNegation
        if (!((_var2size >= 0) && (_var1size <= 4))) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid sample size");}
        //noinspection DoubleNegation
        if (!((_var3size >= 0) && (_var1size <= 4))) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid sample size");}
        if (_var2size == 0) {
            _nVars = 1;
        } else {
            if (_var3size == 0) {
                _nVars = 2;
            } else {
                _nVars = 3;
            }
        }
        recSize = _var1size + _var2size + _var3size;
        _recOfs = _decodeU16(sdata, 4);
        _nRecs = _decodeU16(sdata, 6);
        _samplesPerSec = _decodeU16(sdata, 8);
        _trigType = _decodeU16(sdata, 10);
        _trigVal = _decodeVal(sdata, 12, 4) / 1000;
        _trigPos = _decodeU16(sdata, 16);
        ms = _decodeU16(sdata, 18);
        _trigUTC = _decodeVal(sdata, 20, 4);
        _trigUTC = _trigUTC + (ms / 1000.0);
        recOfs = 24;
        while ((sdata[recOfs] & 0xff) >= 32) {
            _var1unit = String.format(Locale.US, "%s%c",_var1unit,(sdata[recOfs] & 0xff));
            recOfs = recOfs + 1;
        }
        if (_var2size > 0) {
            recOfs = recOfs + 1;
            while ((sdata[recOfs] & 0xff) >= 32) {
                _var2unit = String.format(Locale.US, "%s%c",_var2unit,(sdata[recOfs] & 0xff));
                recOfs = recOfs + 1;
            }
        }
        if (_var3size > 0) {
            recOfs = recOfs + 1;
            while ((sdata[recOfs] & 0xff) >= 32) {
                _var3unit = String.format(Locale.US, "%s%c",_var3unit,(sdata[recOfs] & 0xff));
                recOfs = recOfs + 1;
            }
        }
        if ((recOfs & 1) == 1) {
            // align to next word
            recOfs = recOfs + 1;
        }
        mult1 = 1;
        mult2 = 1;
        mult3 = 1;
        if (recOfs < _recOfs) {
            // load optional value multiplier
            mult1 = _decodeU16(sdata, recOfs);
            recOfs = recOfs + 2;
            if (_var2size > 0) {
                mult2 = _decodeU16(sdata, recOfs);
                recOfs = recOfs + 2;
            }
            if (_var3size > 0) {
                mult3 = _decodeU16(sdata, recOfs);
                recOfs = recOfs + 2;
            }
        }
        recOfs = _recOfs;
        count = _nRecs;
        while ((count > 0) && (recOfs + _var1size <= buffSize)) {
            v = _decodeVal(sdata, recOfs, _var1size) / 1000.0;
            _var1samples.add(v*mult1);
            recOfs = recOfs + recSize;
        }
        if (_var2size > 0) {
            recOfs = _recOfs + _var1size;
            count = _nRecs;
            while ((count > 0) && (recOfs + _var2size <= buffSize)) {
                v = _decodeVal(sdata, recOfs, _var2size) / 1000.0;
                _var2samples.add(v*mult2);
                recOfs = recOfs + recSize;
            }
        }
        if (_var3size > 0) {
            recOfs = _recOfs + _var1size + _var2size;
            count = _nRecs;
            while ((count > 0) && (recOfs + _var3size <= buffSize)) {
                v = _decodeVal(sdata, recOfs, _var3size) / 1000.0;
                _var3samples.add(v*mult3);
                recOfs = recOfs + recSize;
            }
        }
        return YAPI.SUCCESS;
    }

    /**
     * Returns the number of series available in the capture.
     *
     * @return an integer corresponding to the number of
     *         simultaneous data series available.
     */
    public int get_serieCount()
    {
        return _nVars;
    }

    /**
     * Returns the number of records captured (in a serie).
     * In the exceptional case where it was not possible
     * to transfer all data in time, the number of records
     * actually present in the series might be lower than
     * the number of records captured
     *
     * @return an integer corresponding to the number of
     *         records expected in each serie.
     */
    public int get_recordCount()
    {
        return _nRecs;
    }

    /**
     * Returns the effective sampling rate of the device.
     *
     * @return an integer corresponding to the number of
     *         samples taken each second.
     */
    public int get_samplingRate()
    {
        return _samplesPerSec;
    }

    /**
     * Returns the type of automatic conditional capture
     * that triggered the capture of this data sequence.
     *
     * @return the type of conditional capture.
     */
    public int get_captureType()
    {
        return (int) _trigType;
    }

    /**
     * Returns the threshold value that triggered
     * this automatic conditional capture, if it was
     * not an instant captured triggered manually.
     *
     * @return the conditional threshold value
     *         at the time of capture.
     */
    public double get_triggerValue()
    {
        return _trigVal;
    }

    /**
     * Returns the index in the series of the sample
     * corresponding to the exact time when the capture
     * was triggered. In case of trigger based on average
     * or RMS value, the trigger index corresponds to
     * the end of the averaging period.
     *
     * @return an integer corresponding to a position
     *         in the data serie.
     */
    public int get_triggerPosition()
    {
        return _trigPos;
    }

    /**
     * Returns the absolute time when the capture was
     * triggered, as a Unix timestamp. Milliseconds are
     * included in this timestamp (floating-point number).
     *
     * @return a floating-point number corresponding to
     *         the number of seconds between the Jan 1,
     *         1970 and the moment where the capture
     *         was triggered.
     */
    public double get_triggerRealTimeUTC()
    {
        return _trigUTC;
    }

    /**
     * Returns the unit of measurement for data points in
     * the first serie.
     *
     * @return a string containing to a physical unit of
     *         measurement.
     */
    public String get_serie1Unit()
    {
        return _var1unit;
    }

    /**
     * Returns the unit of measurement for data points in
     * the second serie.
     *
     * @return a string containing to a physical unit of
     *         measurement.
     */
    public String get_serie2Unit() throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(_nVars >= 2)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "There is no serie 2 in this capture data");}
        return _var2unit;
    }

    /**
     * Returns the unit of measurement for data points in
     * the third serie.
     *
     * @return a string containing to a physical unit of
     *         measurement.
     */
    public String get_serie3Unit() throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(_nVars >= 3)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "There is no serie 3 in this capture data");}
        return _var3unit;
    }

    /**
     * Returns the sampled data corresponding to the first serie.
     * The corresponding physical unit can be obtained
     * using the method get_serie1Unit().
     *
     * @return a list of real numbers corresponding to all
     *         samples received for serie 1.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<Double> get_serie1Values() throws YAPI_Exception
    {
        return _var1samples;
    }

    /**
     * Returns the sampled data corresponding to the second serie.
     * The corresponding physical unit can be obtained
     * using the method get_serie2Unit().
     *
     * @return a list of real numbers corresponding to all
     *         samples received for serie 2.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<Double> get_serie2Values() throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(_nVars >= 2)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "There is no serie 2 in this capture data");}
        return _var2samples;
    }

    /**
     * Returns the sampled data corresponding to the third serie.
     * The corresponding physical unit can be obtained
     * using the method get_serie3Unit().
     *
     * @return a list of real numbers corresponding to all
     *         samples received for serie 3.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<Double> get_serie3Values() throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(_nVars >= 3)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "There is no serie 3 in this capture data");}
        return _var3samples;
    }

    //--- (end of generated code: YInputCaptureData implementation)
}

