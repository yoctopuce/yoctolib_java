/*********************************************************************
 *
 * $Id: YFileRecord.java 19328 2015-02-17 17:30:45Z seb $
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

//--- (generated code: YFileRecord class start)
/**
 * YFileRecord Class: Description of a file on the device filesystem
 *
 *
 */
 @SuppressWarnings("UnusedDeclaration")
public class YFileRecord
{
//--- (end of generated code: YFileRecord class start)

    //--- (generated code: YFileRecord definitions)
    protected String _name;
    protected int _size = 0;
    protected int _crc = 0;

    //--- (end of generated code: YFileRecord definitions)

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

    public String get_name()
    {
        return _name;
    }

    public int get_size()
    {
        return _size;
    }

    public int get_crc()
    {
        return _crc;
    }

    //--- (end of generated code: YFileRecord implementation)
}

