/*********************************************************************
 *
 * $Id: YI2cSnoopingRecord.java 38899 2019-12-20 17:21:03Z mvuilleu $
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

//--- (generated code: YI2cSnoopingRecord return codes)
//--- (end of generated code: YI2cSnoopingRecord return codes)
//--- (generated code: YI2cSnoopingRecord class start)
/**
 * YI2cSnoopingRecord Class: Intercepted I2C message description, returned by i2cPort.snoopMessages method
 *
 *
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YI2cSnoopingRecord
{
//--- (end of generated code: YI2cSnoopingRecord class start)
//--- (generated code: YI2cSnoopingRecord definitions)
    protected int _tim = 0;
    protected int _dir = 0;
    protected String _msg;

    //--- (end of generated code: YI2cSnoopingRecord definitions)

    YI2cSnoopingRecord(String json_str) throws YAPI_Exception
    {
        try {
            YJSONObject json = new YJSONObject(json_str);
            json.parse();
            //--- (generated code: YI2cSnoopingRecord attributes initialization)
        //--- (end of generated code: YI2cSnoopingRecord attributes initialization)
            _tim = json.getInt("t");
            final String m = json.getString("m");
            _dir = (m.charAt(0) == '<' ? 1 : 0);
            _msg = m.substring(1);
        } catch (Exception e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "invalid json struct for YI2cSnoopingRecord");
        }

    }

    //--- (generated code: YI2cSnoopingRecord implementation)

    /**
     * Returns the elapsed time, in ms, since the beginning of the preceding message.
     *
     * @return the elapsed time, in ms, since the beginning of the preceding message.
     */
    public int get_time()
    {
        return _tim;
    }

    /**
     * Returns the message direction (RX=0, TX=1).
     *
     * @return the message direction (RX=0, TX=1).
     */
    public int get_direction()
    {
        return _dir;
    }

    /**
     * Returns the message content.
     *
     * @return the message content.
     */
    public String get_message()
    {
        return _msg;
    }

    //--- (end of generated code: YI2cSnoopingRecord implementation)
}

