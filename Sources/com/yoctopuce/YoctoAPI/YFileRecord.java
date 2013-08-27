/*********************************************************************
 *
 * $Id: YFileRecord.java 12426 2013-08-20 13:58:34Z seb $
 *
 * YFileRecord Class: Description of a file on the device filesystem
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

  //--- (generated code: globals)
//--- (end of generated code: globals)
/**
 * YFileRecord Class: Description of a file on the device filesystem
 * 
 * 
 */
public class YFileRecord
{
    private String _name;
    private int _crc;
    private int _size;
    
    
    //--- (generated code: definitions)
    //--- (end of generated code: definitions)
    public YFileRecord(String json_str) throws YAPI_Exception
    {
        JSONObject json;
        try {
            json = new JSONObject(json_str);
            _name = json.getString("name");
            _crc = json.getInt("crc");
            _size = json.getInt("size");
        } catch (JSONException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "invalid json struct for YFileRecord");
        }
    }
    //--- (generated code: YFileRecord implementation)

    public String get_name()  throws YAPI_Exception
    {
        return _name;
    }

    public int get_size()  throws YAPI_Exception
    {
        return _size;
    }

    public int get_crc()  throws YAPI_Exception
    {
        return _crc;
    }

    public String name()  throws YAPI_Exception
    {
        return _name;
    }

    public int size()  throws YAPI_Exception
    {
        return _size;
    }

    public int crc()  throws YAPI_Exception
    {
        return _crc;
    }

    //--- (end of generated code: YFileRecord implementation)
};

