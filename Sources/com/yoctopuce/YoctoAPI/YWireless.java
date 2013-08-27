/*********************************************************************
 *
 * $Id: YWireless.java 12426 2013-08-20 13:58:34Z seb $
 *
 * Implements yFindWireless(), the high-level API for Wireless functions
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

//--- (generated code: globals)
import java.util.ArrayList;
//--- (end of generated code: globals)
/**
 * YWireless Class: Wireless function interface
 * 
 * 
 */
public class YWireless extends YFunction
{
    //--- (generated code: definitions)
    private YWireless.UpdateCallback _valueCallbackWireless;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid linkQuality value
     */
    public static final int LINKQUALITY_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid ssid value
     */
    public static final String SSID_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid channel value
     */
    public static final int CHANNEL_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid security value
     */
    public static final int SECURITY_UNKNOWN = 0;
    public static final int SECURITY_OPEN = 1;
    public static final int SECURITY_WEP = 2;
    public static final int SECURITY_WPA = 3;
    public static final int SECURITY_WPA2 = 4;
    public static final int SECURITY_INVALID = -1;

    /**
     * invalid message value
     */
    public static final String MESSAGE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid wlanConfig value
     */
    public static final String WLANCONFIG_INVALID = YAPI.INVALID_STRING;
    //--- (end of generated code: definitions)

    /**
     * UdateCallback for Wireless
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YWireless function, String functionValue);
    }



    //--- (generated code: YWireless implementation)

    /**
     * Returns the logical name of the wireless lan interface.
     * 
     * @return a string corresponding to the logical name of the wireless lan interface
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the wireless lan interface.
     * 
     * @return a string corresponding to the logical name of the wireless lan interface
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the wireless lan interface. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the wireless lan interface
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_logicalName( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("logicalName",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the logical name of the wireless lan interface. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the wireless lan interface
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the wireless lan interface (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the wireless lan interface (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the wireless lan interface (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the wireless lan interface (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the link quality, expressed in per cents.
     * 
     * @return an integer corresponding to the link quality, expressed in per cents
     * 
     * @throws YAPI_Exception
     */
    public int get_linkQuality()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("linkQuality");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the link quality, expressed in per cents.
     * 
     * @return an integer corresponding to the link quality, expressed in per cents
     * 
     * @throws YAPI_Exception
     */
    public int getLinkQuality() throws YAPI_Exception

    { return get_linkQuality(); }

    /**
     * Returns the wireless network name (SSID).
     * 
     * @return a string corresponding to the wireless network name (SSID)
     * 
     * @throws YAPI_Exception
     */
    public String get_ssid()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("ssid");
        return json_val;
    }

    /**
     * Returns the wireless network name (SSID).
     * 
     * @return a string corresponding to the wireless network name (SSID)
     * 
     * @throws YAPI_Exception
     */
    public String getSsid() throws YAPI_Exception

    { return get_ssid(); }

    /**
     * Returns the 802.11 channel currently used, or 0 when the selected network has not been found.
     * 
     * @return an integer corresponding to the 802
     * 
     * @throws YAPI_Exception
     */
    public int get_channel()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("channel");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the 802.11 channel currently used, or 0 when the selected network has not been found.
     * 
     * @return an integer corresponding to the 802
     * 
     * @throws YAPI_Exception
     */
    public int getChannel() throws YAPI_Exception

    { return get_channel(); }

    /**
     * Returns the security algorithm used by the selected wireless network.
     * 
     * @return a value among YWireless.SECURITY_UNKNOWN, YWireless.SECURITY_OPEN, YWireless.SECURITY_WEP,
     * YWireless.SECURITY_WPA and YWireless.SECURITY_WPA2 corresponding to the security algorithm used by
     * the selected wireless network
     * 
     * @throws YAPI_Exception
     */
    public int get_security()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("security");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the security algorithm used by the selected wireless network.
     * 
     * @return a value among Y_SECURITY_UNKNOWN, Y_SECURITY_OPEN, Y_SECURITY_WEP, Y_SECURITY_WPA and
     * Y_SECURITY_WPA2 corresponding to the security algorithm used by the selected wireless network
     * 
     * @throws YAPI_Exception
     */
    public int getSecurity() throws YAPI_Exception

    { return get_security(); }

    /**
     * Returns the last status message from the wireless interface.
     * 
     * @return a string corresponding to the last status message from the wireless interface
     * 
     * @throws YAPI_Exception
     */
    public String get_message()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("message");
        return json_val;
    }

    /**
     * Returns the last status message from the wireless interface.
     * 
     * @return a string corresponding to the last status message from the wireless interface
     * 
     * @throws YAPI_Exception
     */
    public String getMessage() throws YAPI_Exception

    { return get_message(); }

    public String get_wlanConfig()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("wlanConfig");
        return json_val;
    }

    public String getWlanConfig() throws YAPI_Exception

    { return get_wlanConfig(); }

    public int set_wlanConfig( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("wlanConfig",rest_val);
        return YAPI.SUCCESS;
    }

    public int setWlanConfig( String newval)  throws YAPI_Exception

    { return set_wlanConfig(newval); }

    /**
     * Changes the configuration of the wireless lan interface to connect to an existing
     * access point (infrastructure mode).
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param ssid : the name of the network to connect to
     * @param securityKey : the network key, as a character string
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int joinNetwork(String ssid,String securityKey)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("INFRA:%s\\%s", ssid.toString(), securityKey.toString());
        _setAttr("wlanConfig",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the configuration of the wireless lan interface to create an ad-hoc
     * wireless network, without using an access point. If a security key is specified,
     * the network is protected by WEP128, since WPA is not standardized for
     * ad-hoc networks.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param ssid : the name of the network to connect to
     * @param securityKey : the network key, as a character string
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int adhocNetwork(String ssid,String securityKey)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("ADHOC:%s\\%s", ssid.toString(), securityKey.toString());
        _setAttr("wlanConfig",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns a list of YWlanRecord objects which describe detected Wireless networks.
     * This list is not updated when the module is already connected to an acces point (infrastructure mode).
     * To force an update of this list, adhocNetwork() must be called to disconnect
     * the module from the current network. The returned list must be unallocated by caller,
     * 
     * @return a list of YWlanRecord objects, containing the SSID, channel,
     *         link quality and the type of security of the wireless network.
     * 
     * @throws YAPI_Exception
     */
    public ArrayList<YWlanRecord> get_detectedWlans()  throws YAPI_Exception
    {
        byte[] json;
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<YWlanRecord> res = new ArrayList<YWlanRecord>();
        json = _download("wlan.json?by=name");
        list = _json_get_array(json);
        for (String y :list) { res.add(new YWlanRecord(y));};
        return res;
        
    }

    /**
     * Continues the enumeration of wireless lan interfaces started using yFirstWireless().
     * 
     * @return a pointer to a YWireless object, corresponding to
     *         a wireless lan interface currently online, or a null pointer
     *         if there are no more wireless lan interfaces to enumerate.
     */
    public  YWireless nextWireless()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindWireless(next_hwid);
    }

    /**
     * Retrieves a wireless lan interface for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the wireless lan interface is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YWireless.isOnline() to test if the wireless lan interface is
     * indeed online at a given time. In case of ambiguity when looking for
     * a wireless lan interface by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the wireless lan interface
     * 
     * @return a YWireless object allowing you to drive the wireless lan interface.
     */
    public static YWireless FindWireless(String func)
    {   YFunction yfunc = YAPI.getFunction("Wireless", func);
        if (yfunc != null) {
            return (YWireless) yfunc;
        }
        return new YWireless(func);
    }

    /**
     * Starts the enumeration of wireless lan interfaces currently accessible.
     * Use the method YWireless.nextWireless() to iterate on
     * next wireless lan interfaces.
     * 
     * @return a pointer to a YWireless object, corresponding to
     *         the first wireless lan interface currently online, or a null pointer
     *         if there are none.
     */
    public static YWireless FirstWireless()
    {
        String next_hwid = YAPI.getFirstHardwareId("Wireless");
        if (next_hwid == null)  return null;
        return FindWireless(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YWireless(String func)
    {
        super("Wireless", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackWireless != null) {
            _valueCallbackWireless.yNewValue(this, newvalue);
        }
    }

    /**
     * Internal: check if we have a callback interface registered
     * 
     * @return yes if the user has registered a interface
     */
    @Override
     protected boolean hasCallbackRegistered()
    {
        return super.hasCallbackRegistered() || (_valueCallbackWireless!=null);
    }

    /**
     * Registers the callback function that is invoked on every change of advertised value.
     * The callback is invoked only during the execution of ySleep or yHandleEvents.
     * This provides control over the time when the callback is triggered. For good responsiveness, remember to call
     * one of these two functions periodically. To unregister a callback, pass a null pointer as argument.
     * 
     * @param callback : the callback function to call, or a null pointer. The callback function should take two
     *         arguments: the function object of which the value has changed, and the character string describing
     *         the new advertised value.
     * @noreturn
     */
    public void registerValueCallback(YWireless.UpdateCallback callback)
    {
         _valueCallbackWireless =  callback;
         if (callback != null && isOnline()) {
             String newval;
             try {
                 newval = get_advertisedValue();
                 if (!newval.equals("") && !newval.equals("!INVALDI!")) {
                     callback.yNewValue(this, newval);
                 }
             } catch (YAPI_Exception ex) {
             }
         }
    }

    //--- (end of generated code: YWireless implementation)
};

