/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindSdi12SensorInfo(), the high-level API for Sdi12SensorInfo functions
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

//--- (generated code: YSdi12SensorInfo return codes)
//--- (end of generated code: YSdi12SensorInfo return codes)
//--- (generated code: YSdi12SensorInfo yapiwrapper)
//--- (end of generated code: YSdi12SensorInfo yapiwrapper)
//--- (generated code: YSdi12SensorInfo class start)
/**
 *  YSdi12SensorInfo Class: Description of a discovered SDI12 sensor, returned by
 * sdi12Port.discoverSingleSensor and sdi12Port.discoverAllSensors methods
 *
 *
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSdi12SensorInfo
{
//--- (end of generated code: YSdi12SensorInfo class start)
//--- (generated code: YSdi12SensorInfo definitions)
    protected YSdi12Port _sdi12Port;
    protected boolean _isValid;
    protected String _addr;
    protected String _proto;
    protected String _mfg;
    protected String _model;
    protected String _ver;
    protected String _sn;
    protected ArrayList<ArrayList<String>> _valuesDesc = new ArrayList<>();

    //--- (end of generated code: YSdi12SensorInfo definitions)

    YSdi12SensorInfo(YSdi12Port sdi12Port, String json_str) throws YAPI_Exception
    {
        _sdi12Port = sdi12Port;
        _parseInfoStr(json_str);
        //--- (generated code: YSdi12SensorInfo attributes initialization)
        //--- (end of generated code: YSdi12SensorInfo attributes initialization)
    }

    public void _throw(int errcode,String msg) throws YAPI_Exception
    {
        _sdi12Port._throw(errcode,msg);
    }

    //--- (generated code: YSdi12SensorInfo implementation)

    /**
     * Returns the sensor state.
     *
     * @return the sensor state.
     */
    public boolean isValid()
    {
        return _isValid;
    }

    /**
     * Returns the sensor address.
     *
     * @return the sensor address.
     */
    public String get_sensorAddress()
    {
        return _addr;
    }

    /**
     * Returns the compatible SDI-12 version of the sensor.
     *
     * @return the compatible SDI-12 version of the sensor.
     */
    public String get_sensorProtocol()
    {
        return _proto;
    }

    /**
     * Returns the sensor vendor identification.
     *
     * @return the sensor vendor identification.
     */
    public String get_sensorVendor()
    {
        return _mfg;
    }

    /**
     * Returns the sensor model number.
     *
     * @return the sensor model number.
     */
    public String get_sensorModel()
    {
        return _model;
    }

    /**
     * Returns the sensor version.
     *
     * @return the sensor version.
     */
    public String get_sensorVersion()
    {
        return _ver;
    }

    /**
     * Returns the sensor serial number.
     *
     * @return the sensor serial number.
     */
    public String get_sensorSerial()
    {
        return _sn;
    }

    /**
     * Returns the number of sensor measurements.
     * This function only works if the sensor is in version 1.4 SDI-12
     * and supports metadata commands.
     *
     * @return the number of sensor measurements.
     */
    public int get_measureCount()
    {
        return _valuesDesc.size();
    }

    /**
     * Returns the sensor measurement command.
     * This function only works if the sensor is in version 1.4 SDI-12
     * and supports metadata commands.
     *
     * @param measureIndex : measurement index
     *
     * @return the sensor measurement command.
     * @throws YAPI_Exception on error
     */
    public String get_measureCommand(int measureIndex) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(measureIndex < _valuesDesc.size())) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid measure index");}
        return _valuesDesc.get(measureIndex).get(0);
    }

    /**
     * Returns sensor measurement position.
     * This function only works if the sensor is in version 1.4 SDI-12
     * and supports metadata commands.
     *
     * @param measureIndex : measurement index
     *
     * @return the sensor measurement command.
     * @throws YAPI_Exception on error
     */
    public int get_measurePosition(int measureIndex) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(measureIndex < _valuesDesc.size())) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid measure index");}
        return YAPIContext._atoi(_valuesDesc.get(measureIndex).get(2));
    }

    /**
     * Returns the measured value symbol.
     * This function only works if the sensor is in version 1.4 SDI-12
     * and supports metadata commands.
     *
     * @param measureIndex : measurement index
     *
     * @return the sensor measurement command.
     * @throws YAPI_Exception on error
     */
    public String get_measureSymbol(int measureIndex) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(measureIndex < _valuesDesc.size())) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid measure index");}
        return _valuesDesc.get(measureIndex).get(3);
    }

    /**
     * Returns the unit of the measured value.
     * This function only works if the sensor is in version 1.4 SDI-12
     * and supports metadata commands.
     *
     * @param measureIndex : measurement index
     *
     * @return the sensor measurement command.
     * @throws YAPI_Exception on error
     */
    public String get_measureUnit(int measureIndex) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(measureIndex < _valuesDesc.size())) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid measure index");}
        return _valuesDesc.get(measureIndex).get(4);
    }

    /**
     * Returns the description of the measured value.
     * This function only works if the sensor is in version 1.4 SDI-12
     * and supports metadata commands.
     *
     * @param measureIndex : measurement index
     *
     * @return the sensor measurement command.
     * @throws YAPI_Exception on error
     */
    public String get_measureDescription(int measureIndex) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(measureIndex < _valuesDesc.size())) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid measure index");}
        return _valuesDesc.get(measureIndex).get(5);
    }

    public ArrayList<ArrayList<String>> get_typeMeasure()
    {
        return _valuesDesc;
    }

    public void _parseInfoStr(String infoStr) throws YAPI_Exception
    {
        String errmsg;

        if (infoStr.length() > 1) {
            if ((infoStr).substring(0, 2).equals("ER")) {
                errmsg = (infoStr).substring(2, 2 + infoStr.length()-2);
                _addr = errmsg;
                _proto = errmsg;
                _mfg = errmsg;
                _model = errmsg;
                _ver = errmsg;
                _sn = errmsg;
                _isValid = false;
            } else {
                _addr = (infoStr).substring(0, 1);
                _proto = (infoStr).substring(1, 1 + 2);
                _mfg = (infoStr).substring(3, 3 + 8);
                _model = (infoStr).substring(11, 11 + 6);
                _ver = (infoStr).substring(17, 17 + 3);
                _sn = (infoStr).substring(20, 20 + infoStr.length()-20);
                _isValid = true;
            }
        }
    }

    public void _queryValueInfo() throws YAPI_Exception
    {
        ArrayList<ArrayList<String>> val = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        String infoNbVal;
        String cmd;
        String infoVal;
        String value;
        int nbVal;
        int k;
        int i;
        int j;
        ArrayList<String> listVal = new ArrayList<>();
        int size;

        k = 0;
        size = 4;
        while (k < 10) {
            infoNbVal = _sdi12Port.querySdi12(_addr, String.format(Locale.US, "IM%d",k), 5000);
            if (infoNbVal.length() > 1) {
                value = (infoNbVal).substring(4, 4 + infoNbVal.length()-4);
                nbVal = YAPIContext._atoi(value);
                if (nbVal != 0) {
                    val.clear();
                    i = 0;
                    while (i < nbVal) {
                        cmd = String.format(Locale.US, "IM%d_00%d",k,i+1);
                        infoVal = _sdi12Port.querySdi12(_addr, cmd, 5000);
                        data = new ArrayList<>(Arrays.asList(infoVal.split(";")));
                        data = new ArrayList<>(Arrays.asList(data.get(0).split(",")));
                        listVal.clear();
                        listVal.add(String.format(Locale.US, "M%d",k));
                        listVal.add(Integer.toString(i+1));
                        j = 0;
                        while (data.size() < size) {
                            data.add("");
                        }
                        while (j < data.size()) {
                            listVal.add(data.get(j));
                            j = j + 1;
                        }
                        val.add(new ArrayList<String>(listVal));
                        i = i + 1;
                    }
                }
            }
            k = k + 1;
        }
        _valuesDesc = val;
    }

    //--- (end of generated code: YSdi12SensorInfo implementation)
}

