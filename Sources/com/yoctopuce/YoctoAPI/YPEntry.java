/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
