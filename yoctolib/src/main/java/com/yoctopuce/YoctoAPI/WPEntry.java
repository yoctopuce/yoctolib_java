/*********************************************************************
 *
 * $Id: WPEntry.java 21650 2015-09-30 15:35:28Z seb $
 *
 * White page implementation
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

class WPEntry {

    private String _logicalName = "";
    private String _productName = "";
    private int _productId = -1;
    private final String _networkUrl;
    private int _beacon;
    private boolean _isValid;
    private final String _serialNumber;

    public WPEntry(JSONObject json) throws JSONException
    {
        super();
        _serialNumber = json.getString("serialNumber");
        _logicalName = json.getString("logicalName");
        _productName = json.getString("productName");
        _productId = json.getInt("productId");
        String networkUrl = json.getString("networkUrl");
        //Remove the /api of the network URL
        _networkUrl = networkUrl.substring(0, networkUrl.length() - 4);
        _beacon = json.getInt("beacon");
        _isValid = true;
    }

    @Override
    public String toString()
    {
        return "WPEntry [serialNumber=" + _serialNumber + ", logicalName=" + _logicalName + ", productName=" + _productName + ", productId=" + _productId + ", networkUrl=" + _networkUrl + ", beacon=" + _beacon + ", isValid=" + _isValid + "]";
    }

    public WPEntry(String serial, String netUrl)
    {
        super();
        _serialNumber = serial;
        _networkUrl = netUrl;
        _isValid = false;
    }


    public WPEntry(int test,String productName)
    {
        _logicalName = "logicalName";
        //_productName = productName;
        _productId = -1;
        _networkUrl = "networkUrl";
        _beacon = 0;
        _serialNumber = "serialNumber";
        _isValid = false;
    }

    public WPEntry(int productId, int beacon )
    {
        _logicalName = "logicalName";
        _productName = "productName";
        _productId = productId;
        _networkUrl = "networkUrl";
        _beacon = beacon;
        _serialNumber = "serialNumber";
        _isValid = false;
    }


    public WPEntry(String logicalName, String productName, int productId, String networkUrl, int beacon, String serialNumber)
    {
        _logicalName = logicalName;
        _productName = productName;
        _productId = productId;
        _networkUrl = networkUrl;
        _beacon = beacon;
        _serialNumber = serialNumber;
        _isValid = true;
    }

    public boolean isValid()
    {
        return _isValid;
    }

    public void validate()
    {
        _isValid = true;
    }

    public int getBeacon()
    {
        return _beacon;
    }

    public void setBeacon(int _beacon)
    {
        this._beacon = _beacon;
    }

    public void setLogicalName(String logicalName)
    {
        assert logicalName != null;
        this._logicalName = logicalName;
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
