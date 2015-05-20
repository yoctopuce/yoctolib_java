/*********************************************************************
 *
 * $Id: YWireless.java 20376 2015-05-19 14:18:47Z seb $
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

//--- (generated code: YWireless class start)
/**
 * YWireless Class: Wireless function interface
 *
 * YWireless functions provides control over wireless network parameters
 * and status for devices that are wireless-enabled.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YWireless extends YFunction
{
//--- (end of generated code: YWireless class start)


    //--- (generated code: YWireless definitions)
    /**
     * invalid linkQuality value
     */
    public static final int LINKQUALITY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid ssid value
     */
    public static final String SSID_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid channel value
     */
    public static final int CHANNEL_INVALID = YAPI.INVALID_UINT;
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
    protected int _linkQuality = LINKQUALITY_INVALID;
    protected String _ssid = SSID_INVALID;
    protected int _channel = CHANNEL_INVALID;
    protected int _security = SECURITY_INVALID;
    protected String _message = MESSAGE_INVALID;
    protected String _wlanConfig = WLANCONFIG_INVALID;
    protected UpdateCallback _valueCallbackWireless = null;

    /**
     * Deprecated UpdateCallback for Wireless
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YWireless function, String functionValue);
    }

    /**
     * TimedReportCallback for Wireless
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YWireless  function, YMeasure measure);
    }
    //--- (end of generated code: YWireless definitions)

    /**
     * @param func : functionid
     */
    protected YWireless(String func)
    {
        super(func);
        _className = "Wireless";
        //--- (generated code: YWireless attributes initialization)
        //--- (end of generated code: YWireless attributes initialization)
    }

    //--- (generated code: YWireless implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("linkQuality")) {
            _linkQuality = json_val.getInt("linkQuality");
        }
        if (json_val.has("ssid")) {
            _ssid = json_val.getString("ssid");
        }
        if (json_val.has("channel")) {
            _channel = json_val.getInt("channel");
        }
        if (json_val.has("security")) {
            _security = json_val.getInt("security");
        }
        if (json_val.has("message")) {
            _message = json_val.getString("message");
        }
        if (json_val.has("wlanConfig")) {
            _wlanConfig = json_val.getString("wlanConfig");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the link quality, expressed in percent.
     *
     * @return an integer corresponding to the link quality, expressed in percent
     *
     * @throws YAPI_Exception on error
     */
    public int get_linkQuality() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LINKQUALITY_INVALID;
            }
        }
        return _linkQuality;
    }

    /**
     * Returns the link quality, expressed in percent.
     *
     * @return an integer corresponding to the link quality, expressed in percent
     *
     * @throws YAPI_Exception on error
     */
    public int getLinkQuality() throws YAPI_Exception
    {
        return get_linkQuality();
    }

    /**
     * Returns the wireless network name (SSID).
     *
     * @return a string corresponding to the wireless network name (SSID)
     *
     * @throws YAPI_Exception on error
     */
    public String get_ssid() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SSID_INVALID;
            }
        }
        return _ssid;
    }

    /**
     * Returns the wireless network name (SSID).
     *
     * @return a string corresponding to the wireless network name (SSID)
     *
     * @throws YAPI_Exception on error
     */
    public String getSsid() throws YAPI_Exception
    {
        return get_ssid();
    }

    /**
     * Returns the 802.11 channel currently used, or 0 when the selected network has not been found.
     *
     *  @return an integer corresponding to the 802.11 channel currently used, or 0 when the selected
     * network has not been found
     *
     * @throws YAPI_Exception on error
     */
    public int get_channel() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CHANNEL_INVALID;
            }
        }
        return _channel;
    }

    /**
     * Returns the 802.11 channel currently used, or 0 when the selected network has not been found.
     *
     *  @return an integer corresponding to the 802.11 channel currently used, or 0 when the selected
     * network has not been found
     *
     * @throws YAPI_Exception on error
     */
    public int getChannel() throws YAPI_Exception
    {
        return get_channel();
    }

    /**
     * Returns the security algorithm used by the selected wireless network.
     *
     *  @return a value among YWireless.SECURITY_UNKNOWN, YWireless.SECURITY_OPEN, YWireless.SECURITY_WEP,
     *  YWireless.SECURITY_WPA and YWireless.SECURITY_WPA2 corresponding to the security algorithm used by
     * the selected wireless network
     *
     * @throws YAPI_Exception on error
     */
    public int get_security() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SECURITY_INVALID;
            }
        }
        return _security;
    }

    /**
     * Returns the security algorithm used by the selected wireless network.
     *
     *  @return a value among Y_SECURITY_UNKNOWN, Y_SECURITY_OPEN, Y_SECURITY_WEP, Y_SECURITY_WPA and
     * Y_SECURITY_WPA2 corresponding to the security algorithm used by the selected wireless network
     *
     * @throws YAPI_Exception on error
     */
    public int getSecurity() throws YAPI_Exception
    {
        return get_security();
    }

    /**
     * Returns the latest status message from the wireless interface.
     *
     * @return a string corresponding to the latest status message from the wireless interface
     *
     * @throws YAPI_Exception on error
     */
    public String get_message() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return MESSAGE_INVALID;
            }
        }
        return _message;
    }

    /**
     * Returns the latest status message from the wireless interface.
     *
     * @return a string corresponding to the latest status message from the wireless interface
     *
     * @throws YAPI_Exception on error
     */
    public String getMessage() throws YAPI_Exception
    {
        return get_message();
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String get_wlanConfig() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return WLANCONFIG_INVALID;
            }
        }
        return _wlanConfig;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String getWlanConfig() throws YAPI_Exception
    {
        return get_wlanConfig();
    }

    public int set_wlanConfig(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("wlanConfig",rest_val);
        return YAPI.SUCCESS;
    }

    public int setWlanConfig(String newval)  throws YAPI_Exception
    {
        return set_wlanConfig(newval);
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
    {
        YWireless obj;
        obj = (YWireless) YFunction._FindFromCache("Wireless", func);
        if (obj == null) {
            obj = new YWireless(func);
            YFunction._AddToCache("Wireless", func, obj);
        }
        return obj;
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
     *
     */
    public int registerValueCallback(UpdateCallback callback)
    {
        String val;
        if (callback != null) {
            YFunction._UpdateValueCallbackList(this, true);
        } else {
            YFunction._UpdateValueCallbackList(this, false);
        }
        _valueCallbackWireless = callback;
        // Immediately invoke value callback with current value
        if (callback != null && isOnline()) {
            val = _advertisedValue;
            if (!(val.equals(""))) {
                _invokeValueCallback(val);
            }
        }
        return 0;
    }

    @Override
    public int _invokeValueCallback(String value)
    {
        if (_valueCallbackWireless != null) {
            _valueCallbackWireless.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Changes the configuration of the wireless lan interface to connect to an existing
     * access point (infrastructure mode).
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param ssid : the name of the network to connect to
     * @param securityKey : the network key, as a character string
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int joinNetwork(String ssid,String securityKey) throws YAPI_Exception
    {
        return set_wlanConfig(String.format("INFRA:%s\\%s", ssid,securityKey));
    }

    /**
     * Changes the configuration of the wireless lan interface to create an ad-hoc
     * wireless network, without using an access point. On the YoctoHub-Wireless-g,
     * it is best to use softAPNetworkInstead(), which emulates an access point
     * (Soft AP) which is more efficient and more widely supported than ad-hoc networks.
     *
     * When a security key is specified for an ad-hoc network, the network is protected
     * by a WEP40 key (5 characters or 10 hexadecimal digits) or WEP128 key (13 characters
     * or 26 hexadecimal digits). It is recommended to use a well-randomized WEP128 key
     * using 26 hexadecimal digits to maximize security.
     * Remember to call the saveToFlash() method and then to reboot the module
     * to apply this setting.
     *
     * @param ssid : the name of the network to connect to
     * @param securityKey : the network key, as a character string
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int adhocNetwork(String ssid,String securityKey) throws YAPI_Exception
    {
        return set_wlanConfig(String.format("ADHOC:%s\\%s", ssid,securityKey));
    }

    /**
     * Changes the configuration of the wireless lan interface to create a new wireless
     * network by emulating a WiFi access point (Soft AP). This function can only be
     * used with the YoctoHub-Wireless-g.
     *
     * When a security key is specified for a SoftAP network, the network is protected
     * by a WEP40 key (5 characters or 10 hexadecimal digits) or WEP128 key (13 characters
     * or 26 hexadecimal digits). It is recommended to use a well-randomized WEP128 key
     * using 26 hexadecimal digits to maximize security.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param ssid : the name of the network to connect to
     * @param securityKey : the network key, as a character string
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int softAPNetwork(String ssid,String securityKey) throws YAPI_Exception
    {
        return set_wlanConfig(String.format("SOFTAP:%s\\%s", ssid,securityKey));
    }

    /**
     * Returns a list of YWlanRecord objects that describe detected Wireless networks.
     * This list is not updated when the module is already connected to an acces point (infrastructure mode).
     * To force an update of this list, adhocNetwork() must be called to disconnect
     * the module from the current network. The returned list must be unallocated by the caller.
     *
     * @return a list of YWlanRecord objects, containing the SSID, channel,
     *         link quality and the type of security of the wireless network.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<YWlanRecord> get_detectedWlans() throws YAPI_Exception
    {
        byte[] json;
        ArrayList<String> wlanlist = new ArrayList<String>();
        ArrayList<YWlanRecord> res = new ArrayList<YWlanRecord>();
        // may throw an exception
        json = _download("wlan.json?by=name");
        wlanlist = _json_get_array(json);
        res.clear();
        for (String ii:wlanlist) {
            res.add(new YWlanRecord(ii));
        }
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
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindWireless(next_hwid);
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
        String next_hwid = SafeYAPI().getFirstHardwareId("Wireless");
        if (next_hwid == null)  return null;
        return FindWireless(next_hwid);
    }

    //--- (end of generated code: YWireless implementation)
}

