/*********************************************************************
 *
 * $Id: pic24config.php 10100 2013-02-28 18:45:50Z mvuilleu $
 *
 * Implements yFindFileRecord(), the high-level API for FileRecord functions
 *
 * - - - - - - - - - License information: - - - - - - - - - 
 *
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 * 1) If you have obtained this file from www.yoctopuce.com,
 *    Yoctopuce Sarl licenses to you (hereafter Licensee) the
 *    right to use, modify, copy, and integrate this source file
 *    into your own solution for the sole purpose of interfacing
 *    a Yoctopuce product with Licensee's solution.
 *
 *    The use of this file and all relationship between Yoctopuce 
 *    and Licensee are governed by Yoctopuce General Terms and 
 *    Conditions.
 *
 *    THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *    WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *    WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS 
 *    FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *    EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *    INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, 
 *    COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR 
 *    SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT 
 *    LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *    CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *    BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *    WARRANTY, OR OTHERWISE.
 *
 * 2) If your intent is not to interface with Yoctopuce products,
 *    you are not entitled to use, read or create any derived
 *    material from this source file.
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

