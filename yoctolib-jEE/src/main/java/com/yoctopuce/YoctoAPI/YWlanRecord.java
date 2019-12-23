/*********************************************************************
 *
 * $Id: YWlanRecord.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 * YWlanRecord Class: Description of a wireless network detected
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

//--- (generated code: YWlanRecord class start)
/**
 * YWlanRecord Class: Wireless network description, returned by wireless.get_detectedWlans method
 *
 * YWlanRecord objects are used to describe a wireless network.
 * These objects are  used in particular in conjunction with the
 * YWireless class.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YWlanRecord
{
//--- (end of generated code: YWlanRecord class start)
    //--- (generated code: YWlanRecord definitions)
    protected String _ssid;
    protected int _channel = 0;
    protected String _sec;
    protected int _rssi = 0;

    //--- (end of generated code: YWlanRecord definitions)

    YWlanRecord(String json_str) throws YAPI_Exception
    {
        try {
            YJSONObject json = new YJSONObject(json_str);
            json.parse();
            _ssid = json.getString("ssid");
            _channel = json.getInt("channel");
            _sec = json.getString("sec");
            _rssi = json.getInt("rssi");
        } catch (Exception e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "invalid json struct for YWlanRecord");
        }
    }

    //--- (generated code: YWlanRecord implementation)

    /**
     * Returns the name of the wireless network (SSID).
     *
     * @return a string with the name of the wireless network (SSID).
     */
    public String get_ssid()
    {
        return _ssid;
    }

    /**
     * Returns the 802.11 b/g/n channel number used by this network.
     *
     * @return an integer corresponding to the channel.
     */
    public int get_channel()
    {
        return _channel;
    }

    /**
     * Returns the security algorithm used by the wireless network.
     * If the network implements to security, the value is "OPEN".
     *
     * @return a string with the security algorithm.
     */
    public String get_security()
    {
        return _sec;
    }

    /**
     * Returns the quality of the wireless network link, in per cents.
     *
     * @return an integer between 0 and 100 corresponding to the signal quality.
     */
    public int get_linkQuality()
    {
        return _rssi;
    }

    //--- (end of generated code: YWlanRecord implementation)
}

