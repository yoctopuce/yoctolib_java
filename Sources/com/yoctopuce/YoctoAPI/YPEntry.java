/*********************************************************************
 *
 * $Id: YPEntry.java 12426 2013-08-20 13:58:34Z seb $
 *
 * Yellow page implementation
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

import org.json.JSONException;
import org.json.JSONObject;


class YPEntry {
    private String _serial="";
    private String _funcId="";
    private String _logicalName="";
    private String _advertisedValue="";
    private int    _index=-1;
    private String _categ="";

    public YPEntry(JSONObject json) throws JSONException
    {
        String hardwareId = json.getString("hardwareId");
        int pos = hardwareId.indexOf('.');
        _serial = hardwareId.substring(0, pos);
        _funcId = hardwareId.substring(pos+1);
        _categ = YAPI.functionClass(_funcId);
        _logicalName = json.getString("logicalName");
        _advertisedValue = json.getString("advertisedValue");
        try {
            _index = json.getInt("index");
        } catch (JSONException ex){
            _index =0;
        }
    }

    
    
    
    public YPEntry(String serial, String shortFunctionID)
	{
    	_serial =serial;
    	_funcId = shortFunctionID;
        _categ = YAPI.functionClass(_funcId);
	}


    @Override
	public String toString()
	{
		return "YPEntry [_categ=" + _categ + ", _index=" + _index + ", _serial=" + _serial + ", _funcId=" + _funcId + ", _logicalName=" + _logicalName + ", _advertisedValue=" + _advertisedValue + "]";
	}




	public String getCateg()
    {
    	return _categ;
    }
    
	public String getAdvertisedValue()
    {
        return _advertisedValue;
    }
    
	public void setAdvertisedValue(String _advertisedValue)
    {
        this._advertisedValue = _advertisedValue;
        YAPI.setFunctionValue(_serial+"."+_funcId, _advertisedValue);
    }

    
    public String getHardwareId()
    {
        return  _serial+"."+_funcId;
    }

    public String getSerial()
    {
        return _serial;
    }

    public String getFuncId()
    {
        return _funcId;
    }

    public int getIndex()
    {
        return _index;
    }
    public void setIndex(int index)
    {
        _index= index;
    }

    public String getLogicalName()
    {
        return _logicalName;
    }


    public void setLogicalName(String _logicalName)
    {
        this._logicalName = _logicalName;
    }

}
