/*********************************************************************
 *
 * $Id: YSdi12Sensor.java 56150 2023-08-17 13:41:37Z mvuilleu $
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
import java.util.Arrays;
import java.util.Locale;

//--- (generated code: YSdi12Sensor return codes)
//--- (end of generated code: YSdi12Sensor return codes)
//--- (generated code: YSdi12Sensor class start)
/**
 *  YSdi12Sensor Class: Description of a discovered SDI12 sensor, returned by
 * sdi12Port.discoverSingleSensor and sdi12Port.discoverAllSensors methods
 *
 *
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSdi12Sensor
{
//--- (end of generated code: YSdi12Sensor class start)
//--- (generated code: YSdi12Sensor definitions)
    protected YSdi12Port _sdi12Port;
    protected String _addr;
    protected String _proto;
    protected String _mfg;
    protected String _model;
    protected String _ver;
    protected String _sn;
    protected ArrayList<ArrayList<String>> _valuesDesc = new ArrayList<>();

    //--- (end of generated code: YSdi12Sensor definitions)

    YSdi12Sensor(YSdi12Port sdi12Port, String json_str) throws YAPI_Exception
    {
        _sdi12Port = sdi12Port;
        _parseInfoStr(json_str);

    }

    //--- (generated code: YSdi12Sensor implementation)

    public String get_sensorAddress()
    {
        return _addr;
    }

    public String get_sensorProtocol()
    {
        return _proto;
    }

    public String get_sensorVendor()
    {
        return _mfg;
    }

    public String get_sensorModel()
    {
        return _model;
    }

    public String get_sensorVersion()
    {
        return _ver;
    }

    public String get_sensorSerial()
    {
        return _sn;
    }

    public int get_measureCount()
    {
        return _valuesDesc.size();
    }

    public String get_measureCommand(int measureIndex)
    {
        return _valuesDesc.get(measureIndex).get(0);
    }

    public int get_measurePosition(int measureIndex)
    {
        return YAPIContext._atoi(_valuesDesc.get(measureIndex).get(2));
    }

    public String get_measureSymbol(int measureIndex)
    {
        return _valuesDesc.get(measureIndex).get(3);
    }

    public String get_measureUnit(int measureIndex)
    {
        return _valuesDesc.get(measureIndex).get(4);
    }

    public String get_measureDescription(int measureIndex)
    {
        return _valuesDesc.get(measureIndex).get(5);
    }

    public ArrayList<ArrayList<String>> get_typeMeasure()
    {
        return _valuesDesc;
    }

    public void _parseInfoStr(String infoStr) throws YAPI_Exception
    {
        String errmsg;

        if ((infoStr).length() > 1) {
            if ((infoStr).substring(0, 2).equals("ER")) {
                errmsg = (infoStr).substring( 2,  2 + (infoStr).length()-2);
                _addr = errmsg;
                _proto = errmsg;
                _mfg = errmsg;
                _model = errmsg;
                _ver = errmsg;
                _sn = errmsg;
            } else {
                _addr = (infoStr).substring(0, 1);
                _proto = (infoStr).substring( 1,  1 + 2);
                _mfg = (infoStr).substring( 3,  3 + 8);
                _model = (infoStr).substring( 11,  11 + 6);
                _ver = (infoStr).substring( 17,  17 + 3);
                _sn = (infoStr).substring( 20,  20 + (infoStr).length()-20);
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

        k = 0;
        while (k < 10) {
            infoNbVal = _sdi12Port.querySdi12(_addr, String.format(Locale.US, "IM%d",k), 5000);
            if ((infoNbVal).length() > 1) {
                value = (infoNbVal).substring( 4,  4 + (infoNbVal).length()-4);
                nbVal = YAPIContext._atoi(value);
                if (nbVal != 0) {
                    val.clear();
                    i = 0;
                    while (i < nbVal) {
                        cmd = String.format(Locale.US, "IM%d_00%d", k,i+1);
                        infoVal = _sdi12Port.querySdi12(_addr, cmd, 5000);
                        data = new ArrayList<>(Arrays.asList(infoVal.split(";")));
                        data = new ArrayList<>(Arrays.asList(data.get(0).split(",")));
                        listVal.clear();
                        listVal.add(String.format(Locale.US, "M%d",k));
                        listVal.add(Integer.toString(i+1));
                        j = 0;
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

    //--- (end of generated code: YSdi12Sensor implementation)
}

