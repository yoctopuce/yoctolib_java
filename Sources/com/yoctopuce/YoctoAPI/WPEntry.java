/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.YoctoAPI;

import org.json.JSONException;
import org.json.JSONObject;

class WPEntry {

    private String _logicalName;
    private String _productName;
    private int _productId;
    private String _networkUrl;
    private int _beacon;
    private int _index;
    private boolean _isValid;
    private String _serialNumber;

    public WPEntry(JSONObject json) throws JSONException
    {
        super();
        _serialNumber = json.getString("serialNumber");
        _logicalName = json.getString("logicalName");
        _productName = json.getString("productName");
        _productId = json.getInt("productId");
        _networkUrl = json.getString("networkUrl");
        //Remove the /api of the network URL
        _networkUrl = _networkUrl.substring(0, _networkUrl.length() - 4);
        _beacon = json.getInt("beacon");
        if (json.has("index")) {
            _index = json.getInt("index");
        } else {
            _index = -1;
        }
        _isValid = true;
    }

    @Override
    public String toString()
    {
        return "WPEntry [_index=" + _index + ", _serialNumber=" + _serialNumber + ", _logicalName=" + _logicalName + ", _productName=" + _productName + ", _productId=" + _productId + ", _networkUrl=" + _networkUrl + ", _beacon=" + _beacon + ", _isValid=" + _isValid + "]";
    }

    public WPEntry(int index, String serial, String neturl)
    {
        super();
        _serialNumber = serial;
        _networkUrl = neturl;
        _index = index;
        _isValid = false;
    }

    public boolean isValid()
    {
        return _isValid;
    }

    public void validate()
    {
        _isValid = true;
    }

    public int getIndex()
    {
        return _index;
    }

    public int getBeacon()
    {
        return _beacon;
    }

    public void setBeacon(int _beacon)
    {
        this._beacon = _beacon;
    }

    public void setLogicalName(String _logicalName)
    {
        this._logicalName = _logicalName;
    }

    public String getLogicalName()
    {
        return _logicalName;
    }

    public String getNetworkUrl()
    {
        return _networkUrl;
    }

    public int getProductId()
    {
        return _productId;
    }

    public void setProductId(int pid)
    {
        _productId = pid;
    }

    public String getProductName()
    {
        return _productName;
    }

    public void setProductName(String prodname)
    {
        _productName = prodname;
    }

    public String getSerialNumber()
    {
        return _serialNumber;
    }
}
