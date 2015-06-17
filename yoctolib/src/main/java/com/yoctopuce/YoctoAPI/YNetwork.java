/*********************************************************************
 *
 * $Id: YNetwork.java 20599 2015-06-08 12:16:39Z seb $
 *
 * Implements FindNetwork(), the high-level API for Network functions
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
import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;

//--- (YNetwork return codes)
//--- (end of YNetwork return codes)
//--- (YNetwork class start)
/**
 * YNetwork Class: Network function interface
 *
 * YNetwork objects provide access to TCP/IP parameters of Yoctopuce
 * modules that include a built-in network interface.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YNetwork extends YFunction
{
//--- (end of YNetwork class start)
//--- (YNetwork definitions)
    /**
     * invalid readiness value
     */
    public static final int READINESS_DOWN = 0;
    public static final int READINESS_EXISTS = 1;
    public static final int READINESS_LINKED = 2;
    public static final int READINESS_LAN_OK = 3;
    public static final int READINESS_WWW_OK = 4;
    public static final int READINESS_INVALID = -1;
    /**
     * invalid macAddress value
     */
    public static final String MACADDRESS_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid ipAddress value
     */
    public static final String IPADDRESS_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid subnetMask value
     */
    public static final String SUBNETMASK_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid router value
     */
    public static final String ROUTER_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid ipConfig value
     */
    public static final String IPCONFIG_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid primaryDNS value
     */
    public static final String PRIMARYDNS_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid secondaryDNS value
     */
    public static final String SECONDARYDNS_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid ntpServer value
     */
    public static final String NTPSERVER_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid userPassword value
     */
    public static final String USERPASSWORD_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid adminPassword value
     */
    public static final String ADMINPASSWORD_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid httpPort value
     */
    public static final int HTTPPORT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid defaultPage value
     */
    public static final String DEFAULTPAGE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid discoverable value
     */
    public static final int DISCOVERABLE_FALSE = 0;
    public static final int DISCOVERABLE_TRUE = 1;
    public static final int DISCOVERABLE_INVALID = -1;
    /**
     * invalid wwwWatchdogDelay value
     */
    public static final int WWWWATCHDOGDELAY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid callbackUrl value
     */
    public static final String CALLBACKURL_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid callbackMethod value
     */
    public static final int CALLBACKMETHOD_POST = 0;
    public static final int CALLBACKMETHOD_GET = 1;
    public static final int CALLBACKMETHOD_PUT = 2;
    public static final int CALLBACKMETHOD_INVALID = -1;
    /**
     * invalid callbackEncoding value
     */
    public static final int CALLBACKENCODING_FORM = 0;
    public static final int CALLBACKENCODING_JSON = 1;
    public static final int CALLBACKENCODING_JSON_ARRAY = 2;
    public static final int CALLBACKENCODING_CSV = 3;
    public static final int CALLBACKENCODING_YOCTO_API = 4;
    public static final int CALLBACKENCODING_JSON_NUM = 5;
    public static final int CALLBACKENCODING_EMONCMS = 6;
    public static final int CALLBACKENCODING_INVALID = -1;
    /**
     * invalid callbackCredentials value
     */
    public static final String CALLBACKCREDENTIALS_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid callbackMinDelay value
     */
    public static final int CALLBACKMINDELAY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid callbackMaxDelay value
     */
    public static final int CALLBACKMAXDELAY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid poeCurrent value
     */
    public static final int POECURRENT_INVALID = YAPI.INVALID_UINT;
    protected int _readiness = READINESS_INVALID;
    protected String _macAddress = MACADDRESS_INVALID;
    protected String _ipAddress = IPADDRESS_INVALID;
    protected String _subnetMask = SUBNETMASK_INVALID;
    protected String _router = ROUTER_INVALID;
    protected String _ipConfig = IPCONFIG_INVALID;
    protected String _primaryDNS = PRIMARYDNS_INVALID;
    protected String _secondaryDNS = SECONDARYDNS_INVALID;
    protected String _ntpServer = NTPSERVER_INVALID;
    protected String _userPassword = USERPASSWORD_INVALID;
    protected String _adminPassword = ADMINPASSWORD_INVALID;
    protected int _httpPort = HTTPPORT_INVALID;
    protected String _defaultPage = DEFAULTPAGE_INVALID;
    protected int _discoverable = DISCOVERABLE_INVALID;
    protected int _wwwWatchdogDelay = WWWWATCHDOGDELAY_INVALID;
    protected String _callbackUrl = CALLBACKURL_INVALID;
    protected int _callbackMethod = CALLBACKMETHOD_INVALID;
    protected int _callbackEncoding = CALLBACKENCODING_INVALID;
    protected String _callbackCredentials = CALLBACKCREDENTIALS_INVALID;
    protected int _callbackMinDelay = CALLBACKMINDELAY_INVALID;
    protected int _callbackMaxDelay = CALLBACKMAXDELAY_INVALID;
    protected int _poeCurrent = POECURRENT_INVALID;
    protected UpdateCallback _valueCallbackNetwork = null;

    /**
     * Deprecated UpdateCallback for Network
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YNetwork function, String functionValue);
    }

    /**
     * TimedReportCallback for Network
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YNetwork  function, YMeasure measure);
    }
    //--- (end of YNetwork definitions)


    /**
     *
     * @param func : functionid
     */
    protected YNetwork(String func)
    {
        super(func);
        _className = "Network";
        //--- (YNetwork attributes initialization)
        //--- (end of YNetwork attributes initialization)
    }

    //--- (YNetwork implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("readiness")) {
            _readiness = json_val.getInt("readiness");
        }
        if (json_val.has("macAddress")) {
            _macAddress = json_val.getString("macAddress");
        }
        if (json_val.has("ipAddress")) {
            _ipAddress = json_val.getString("ipAddress");
        }
        if (json_val.has("subnetMask")) {
            _subnetMask = json_val.getString("subnetMask");
        }
        if (json_val.has("router")) {
            _router = json_val.getString("router");
        }
        if (json_val.has("ipConfig")) {
            _ipConfig = json_val.getString("ipConfig");
        }
        if (json_val.has("primaryDNS")) {
            _primaryDNS = json_val.getString("primaryDNS");
        }
        if (json_val.has("secondaryDNS")) {
            _secondaryDNS = json_val.getString("secondaryDNS");
        }
        if (json_val.has("ntpServer")) {
            _ntpServer = json_val.getString("ntpServer");
        }
        if (json_val.has("userPassword")) {
            _userPassword = json_val.getString("userPassword");
        }
        if (json_val.has("adminPassword")) {
            _adminPassword = json_val.getString("adminPassword");
        }
        if (json_val.has("httpPort")) {
            _httpPort = json_val.getInt("httpPort");
        }
        if (json_val.has("defaultPage")) {
            _defaultPage = json_val.getString("defaultPage");
        }
        if (json_val.has("discoverable")) {
            _discoverable = json_val.getInt("discoverable") > 0 ? 1 : 0;
        }
        if (json_val.has("wwwWatchdogDelay")) {
            _wwwWatchdogDelay = json_val.getInt("wwwWatchdogDelay");
        }
        if (json_val.has("callbackUrl")) {
            _callbackUrl = json_val.getString("callbackUrl");
        }
        if (json_val.has("callbackMethod")) {
            _callbackMethod = json_val.getInt("callbackMethod");
        }
        if (json_val.has("callbackEncoding")) {
            _callbackEncoding = json_val.getInt("callbackEncoding");
        }
        if (json_val.has("callbackCredentials")) {
            _callbackCredentials = json_val.getString("callbackCredentials");
        }
        if (json_val.has("callbackMinDelay")) {
            _callbackMinDelay = json_val.getInt("callbackMinDelay");
        }
        if (json_val.has("callbackMaxDelay")) {
            _callbackMaxDelay = json_val.getInt("callbackMaxDelay");
        }
        if (json_val.has("poeCurrent")) {
            _poeCurrent = json_val.getInt("poeCurrent");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current established working mode of the network interface.
     * Level zero (DOWN_0) means that no hardware link has been detected. Either there is no signal
     * on the network cable, or the selected wireless access point cannot be detected.
     * Level 1 (LIVE_1) is reached when the network is detected, but is not yet connected.
     * For a wireless network, this shows that the requested SSID is present.
     * Level 2 (LINK_2) is reached when the hardware connection is established.
     * For a wired network connection, level 2 means that the cable is attached at both ends.
     * For a connection to a wireless access point, it shows that the security parameters
     * are properly configured. For an ad-hoc wireless connection, it means that there is
     * at least one other device connected on the ad-hoc network.
     * Level 3 (DHCP_3) is reached when an IP address has been obtained using DHCP.
     * Level 4 (DNS_4) is reached when the DNS server is reachable on the network.
     * Level 5 (WWW_5) is reached when global connectivity is demonstrated by properly loading the
     * current time from an NTP server.
     *
     *  @return a value among YNetwork.READINESS_DOWN, YNetwork.READINESS_EXISTS,
     *  YNetwork.READINESS_LINKED, YNetwork.READINESS_LAN_OK and YNetwork.READINESS_WWW_OK corresponding to
     * the current established working mode of the network interface
     *
     * @throws YAPI_Exception on error
     */
    public int get_readiness() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return READINESS_INVALID;
            }
        }
        return _readiness;
    }

    /**
     * Returns the current established working mode of the network interface.
     * Level zero (DOWN_0) means that no hardware link has been detected. Either there is no signal
     * on the network cable, or the selected wireless access point cannot be detected.
     * Level 1 (LIVE_1) is reached when the network is detected, but is not yet connected.
     * For a wireless network, this shows that the requested SSID is present.
     * Level 2 (LINK_2) is reached when the hardware connection is established.
     * For a wired network connection, level 2 means that the cable is attached at both ends.
     * For a connection to a wireless access point, it shows that the security parameters
     * are properly configured. For an ad-hoc wireless connection, it means that there is
     * at least one other device connected on the ad-hoc network.
     * Level 3 (DHCP_3) is reached when an IP address has been obtained using DHCP.
     * Level 4 (DNS_4) is reached when the DNS server is reachable on the network.
     * Level 5 (WWW_5) is reached when global connectivity is demonstrated by properly loading the
     * current time from an NTP server.
     *
     *  @return a value among Y_READINESS_DOWN, Y_READINESS_EXISTS, Y_READINESS_LINKED, Y_READINESS_LAN_OK
     * and Y_READINESS_WWW_OK corresponding to the current established working mode of the network interface
     *
     * @throws YAPI_Exception on error
     */
    public int getReadiness() throws YAPI_Exception
    {
        return get_readiness();
    }

    /**
     * Returns the MAC address of the network interface. The MAC address is also available on a sticker
     * on the module, in both numeric and barcode forms.
     *
     * @return a string corresponding to the MAC address of the network interface
     *
     * @throws YAPI_Exception on error
     */
    public String get_macAddress() throws YAPI_Exception
    {
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return MACADDRESS_INVALID;
            }
        }
        return _macAddress;
    }

    /**
     * Returns the MAC address of the network interface. The MAC address is also available on a sticker
     * on the module, in both numeric and barcode forms.
     *
     * @return a string corresponding to the MAC address of the network interface
     *
     * @throws YAPI_Exception on error
     */
    public String getMacAddress() throws YAPI_Exception
    {
        return get_macAddress();
    }

    /**
     * Returns the IP address currently in use by the device. The address may have been configured
     * statically, or provided by a DHCP server.
     *
     * @return a string corresponding to the IP address currently in use by the device
     *
     * @throws YAPI_Exception on error
     */
    public String get_ipAddress() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return IPADDRESS_INVALID;
            }
        }
        return _ipAddress;
    }

    /**
     * Returns the IP address currently in use by the device. The address may have been configured
     * statically, or provided by a DHCP server.
     *
     * @return a string corresponding to the IP address currently in use by the device
     *
     * @throws YAPI_Exception on error
     */
    public String getIpAddress() throws YAPI_Exception
    {
        return get_ipAddress();
    }

    /**
     * Returns the subnet mask currently used by the device.
     *
     * @return a string corresponding to the subnet mask currently used by the device
     *
     * @throws YAPI_Exception on error
     */
    public String get_subnetMask() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SUBNETMASK_INVALID;
            }
        }
        return _subnetMask;
    }

    /**
     * Returns the subnet mask currently used by the device.
     *
     * @return a string corresponding to the subnet mask currently used by the device
     *
     * @throws YAPI_Exception on error
     */
    public String getSubnetMask() throws YAPI_Exception
    {
        return get_subnetMask();
    }

    /**
     * Returns the IP address of the router on the device subnet (default gateway).
     *
     * @return a string corresponding to the IP address of the router on the device subnet (default gateway)
     *
     * @throws YAPI_Exception on error
     */
    public String get_router() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ROUTER_INVALID;
            }
        }
        return _router;
    }

    /**
     * Returns the IP address of the router on the device subnet (default gateway).
     *
     * @return a string corresponding to the IP address of the router on the device subnet (default gateway)
     *
     * @throws YAPI_Exception on error
     */
    public String getRouter() throws YAPI_Exception
    {
        return get_router();
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String get_ipConfig() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return IPCONFIG_INVALID;
            }
        }
        return _ipConfig;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String getIpConfig() throws YAPI_Exception
    {
        return get_ipConfig();
    }

    public int set_ipConfig(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("ipConfig",rest_val);
        return YAPI.SUCCESS;
    }

    public int setIpConfig(String newval)  throws YAPI_Exception
    {
        return set_ipConfig(newval);
    }

    /**
     * Returns the IP address of the primary name server to be used by the module.
     *
     * @return a string corresponding to the IP address of the primary name server to be used by the module
     *
     * @throws YAPI_Exception on error
     */
    public String get_primaryDNS() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PRIMARYDNS_INVALID;
            }
        }
        return _primaryDNS;
    }

    /**
     * Returns the IP address of the primary name server to be used by the module.
     *
     * @return a string corresponding to the IP address of the primary name server to be used by the module
     *
     * @throws YAPI_Exception on error
     */
    public String getPrimaryDNS() throws YAPI_Exception
    {
        return get_primaryDNS();
    }

    /**
     * Changes the IP address of the primary name server to be used by the module.
     * When using DHCP, if a value is specified, it overrides the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the primary name server to be used by the module
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_primaryDNS(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("primaryDNS",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the IP address of the primary name server to be used by the module.
     * When using DHCP, if a value is specified, it overrides the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the primary name server to be used by the module
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPrimaryDNS(String newval)  throws YAPI_Exception
    {
        return set_primaryDNS(newval);
    }

    /**
     * Returns the IP address of the secondary name server to be used by the module.
     *
     * @return a string corresponding to the IP address of the secondary name server to be used by the module
     *
     * @throws YAPI_Exception on error
     */
    public String get_secondaryDNS() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SECONDARYDNS_INVALID;
            }
        }
        return _secondaryDNS;
    }

    /**
     * Returns the IP address of the secondary name server to be used by the module.
     *
     * @return a string corresponding to the IP address of the secondary name server to be used by the module
     *
     * @throws YAPI_Exception on error
     */
    public String getSecondaryDNS() throws YAPI_Exception
    {
        return get_secondaryDNS();
    }

    /**
     * Changes the IP address of the secondary name server to be used by the module.
     * When using DHCP, if a value is specified, it overrides the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the secondary name server to be used by the module
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_secondaryDNS(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("secondaryDNS",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the IP address of the secondary name server to be used by the module.
     * When using DHCP, if a value is specified, it overrides the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the secondary name server to be used by the module
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSecondaryDNS(String newval)  throws YAPI_Exception
    {
        return set_secondaryDNS(newval);
    }

    /**
     * Returns the IP address of the NTP server to be used by the device.
     *
     * @return a string corresponding to the IP address of the NTP server to be used by the device
     *
     * @throws YAPI_Exception on error
     */
    public String get_ntpServer() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return NTPSERVER_INVALID;
            }
        }
        return _ntpServer;
    }

    /**
     * Returns the IP address of the NTP server to be used by the device.
     *
     * @return a string corresponding to the IP address of the NTP server to be used by the device
     *
     * @throws YAPI_Exception on error
     */
    public String getNtpServer() throws YAPI_Exception
    {
        return get_ntpServer();
    }

    /**
     * Changes the IP address of the NTP server to be used by the module.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the NTP server to be used by the module
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_ntpServer(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("ntpServer",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the IP address of the NTP server to be used by the module.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the NTP server to be used by the module
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setNtpServer(String newval)  throws YAPI_Exception
    {
        return set_ntpServer(newval);
    }

    /**
     * Returns a hash string if a password has been set for "user" user,
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hash string if a password has been set for "user" user,
     *         or an empty string otherwise
     *
     * @throws YAPI_Exception on error
     */
    public String get_userPassword() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return USERPASSWORD_INVALID;
            }
        }
        return _userPassword;
    }

    /**
     * Returns a hash string if a password has been set for "user" user,
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hash string if a password has been set for "user" user,
     *         or an empty string otherwise
     *
     * @throws YAPI_Exception on error
     */
    public String getUserPassword() throws YAPI_Exception
    {
        return get_userPassword();
    }

    /**
     * Changes the password for the "user" user. This password becomes instantly required
     * to perform any use of the module. If the specified value is an
     * empty string, a password is not required anymore.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the password for the "user" user
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_userPassword(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("userPassword",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the password for the "user" user. This password becomes instantly required
     * to perform any use of the module. If the specified value is an
     * empty string, a password is not required anymore.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the password for the "user" user
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUserPassword(String newval)  throws YAPI_Exception
    {
        return set_userPassword(newval);
    }

    /**
     * Returns a hash string if a password has been set for user "admin",
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hash string if a password has been set for user "admin",
     *         or an empty string otherwise
     *
     * @throws YAPI_Exception on error
     */
    public String get_adminPassword() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ADMINPASSWORD_INVALID;
            }
        }
        return _adminPassword;
    }

    /**
     * Returns a hash string if a password has been set for user "admin",
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hash string if a password has been set for user "admin",
     *         or an empty string otherwise
     *
     * @throws YAPI_Exception on error
     */
    public String getAdminPassword() throws YAPI_Exception
    {
        return get_adminPassword();
    }

    /**
     * Changes the password for the "admin" user. This password becomes instantly required
     * to perform any change of the module state. If the specified value is an
     * empty string, a password is not required anymore.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the password for the "admin" user
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_adminPassword(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("adminPassword",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the password for the "admin" user. This password becomes instantly required
     * to perform any change of the module state. If the specified value is an
     * empty string, a password is not required anymore.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the password for the "admin" user
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAdminPassword(String newval)  throws YAPI_Exception
    {
        return set_adminPassword(newval);
    }

    /**
     * Returns the HTML page to serve for the URL "/"" of the hub.
     *
     * @return an integer corresponding to the HTML page to serve for the URL "/"" of the hub
     *
     * @throws YAPI_Exception on error
     */
    public int get_httpPort() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return HTTPPORT_INVALID;
            }
        }
        return _httpPort;
    }

    /**
     * Returns the HTML page to serve for the URL "/"" of the hub.
     *
     * @return an integer corresponding to the HTML page to serve for the URL "/"" of the hub
     *
     * @throws YAPI_Exception on error
     */
    public int getHttpPort() throws YAPI_Exception
    {
        return get_httpPort();
    }

    /**
     * Changes the default HTML page returned by the hub. If not value are set the hub return
     * "index.html" which is the web interface of the hub. It is possible de change this page
     * for file that has been uploaded on the hub.
     *
     * @param newval : an integer corresponding to the default HTML page returned by the hub
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_httpPort(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("httpPort",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the default HTML page returned by the hub. If not value are set the hub return
     * "index.html" which is the web interface of the hub. It is possible de change this page
     * for file that has been uploaded on the hub.
     *
     * @param newval : an integer corresponding to the default HTML page returned by the hub
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setHttpPort(int newval)  throws YAPI_Exception
    {
        return set_httpPort(newval);
    }

    /**
     * Returns the HTML page to serve for the URL "/"" of the hub.
     *
     * @return a string corresponding to the HTML page to serve for the URL "/"" of the hub
     *
     * @throws YAPI_Exception on error
     */
    public String get_defaultPage() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DEFAULTPAGE_INVALID;
            }
        }
        return _defaultPage;
    }

    /**
     * Returns the HTML page to serve for the URL "/"" of the hub.
     *
     * @return a string corresponding to the HTML page to serve for the URL "/"" of the hub
     *
     * @throws YAPI_Exception on error
     */
    public String getDefaultPage() throws YAPI_Exception
    {
        return get_defaultPage();
    }

    /**
     * Changes the default HTML page returned by the hub. If not value are set the hub return
     * "index.html" which is the web interface of the hub. It is possible de change this page
     * for file that has been uploaded on the hub.
     *
     * @param newval : a string corresponding to the default HTML page returned by the hub
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_defaultPage(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("defaultPage",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the default HTML page returned by the hub. If not value are set the hub return
     * "index.html" which is the web interface of the hub. It is possible de change this page
     * for file that has been uploaded on the hub.
     *
     * @param newval : a string corresponding to the default HTML page returned by the hub
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDefaultPage(String newval)  throws YAPI_Exception
    {
        return set_defaultPage(newval);
    }

    /**
     * Returns the activation state of the multicast announce protocols to allow easy
     * discovery of the module in the network neighborhood (uPnP/Bonjour protocol).
     *
     *  @return either YNetwork.DISCOVERABLE_FALSE or YNetwork.DISCOVERABLE_TRUE, according to the
     * activation state of the multicast announce protocols to allow easy
     *         discovery of the module in the network neighborhood (uPnP/Bonjour protocol)
     *
     * @throws YAPI_Exception on error
     */
    public int get_discoverable() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DISCOVERABLE_INVALID;
            }
        }
        return _discoverable;
    }

    /**
     * Returns the activation state of the multicast announce protocols to allow easy
     * discovery of the module in the network neighborhood (uPnP/Bonjour protocol).
     *
     *  @return either Y_DISCOVERABLE_FALSE or Y_DISCOVERABLE_TRUE, according to the activation state of
     * the multicast announce protocols to allow easy
     *         discovery of the module in the network neighborhood (uPnP/Bonjour protocol)
     *
     * @throws YAPI_Exception on error
     */
    public int getDiscoverable() throws YAPI_Exception
    {
        return get_discoverable();
    }

    /**
     * Changes the activation state of the multicast announce protocols to allow easy
     * discovery of the module in the network neighborhood (uPnP/Bonjour protocol).
     *
     *  @param newval : either YNetwork.DISCOVERABLE_FALSE or YNetwork.DISCOVERABLE_TRUE, according to the
     * activation state of the multicast announce protocols to allow easy
     *         discovery of the module in the network neighborhood (uPnP/Bonjour protocol)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_discoverable(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("discoverable",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation state of the multicast announce protocols to allow easy
     * discovery of the module in the network neighborhood (uPnP/Bonjour protocol).
     *
     *  @param newval : either Y_DISCOVERABLE_FALSE or Y_DISCOVERABLE_TRUE, according to the activation
     * state of the multicast announce protocols to allow easy
     *         discovery of the module in the network neighborhood (uPnP/Bonjour protocol)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDiscoverable(int newval)  throws YAPI_Exception
    {
        return set_discoverable(newval);
    }

    /**
     * Returns the allowed downtime of the WWW link (in seconds) before triggering an automated
     * reboot to try to recover Internet connectivity. A zero value disables automated reboot
     * in case of Internet connectivity loss.
     *
     *  @return an integer corresponding to the allowed downtime of the WWW link (in seconds) before
     * triggering an automated
     *         reboot to try to recover Internet connectivity
     *
     * @throws YAPI_Exception on error
     */
    public int get_wwwWatchdogDelay() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return WWWWATCHDOGDELAY_INVALID;
            }
        }
        return _wwwWatchdogDelay;
    }

    /**
     * Returns the allowed downtime of the WWW link (in seconds) before triggering an automated
     * reboot to try to recover Internet connectivity. A zero value disables automated reboot
     * in case of Internet connectivity loss.
     *
     *  @return an integer corresponding to the allowed downtime of the WWW link (in seconds) before
     * triggering an automated
     *         reboot to try to recover Internet connectivity
     *
     * @throws YAPI_Exception on error
     */
    public int getWwwWatchdogDelay() throws YAPI_Exception
    {
        return get_wwwWatchdogDelay();
    }

    /**
     * Changes the allowed downtime of the WWW link (in seconds) before triggering an automated
     * reboot to try to recover Internet connectivity. A zero value disables automated reboot
     * in case of Internet connectivity loss. The smallest valid non-zero timeout is
     * 90 seconds.
     *
     *  @param newval : an integer corresponding to the allowed downtime of the WWW link (in seconds)
     * before triggering an automated
     *         reboot to try to recover Internet connectivity
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_wwwWatchdogDelay(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("wwwWatchdogDelay",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the allowed downtime of the WWW link (in seconds) before triggering an automated
     * reboot to try to recover Internet connectivity. A zero value disables automated reboot
     * in case of Internet connectivity loss. The smallest valid non-zero timeout is
     * 90 seconds.
     *
     *  @param newval : an integer corresponding to the allowed downtime of the WWW link (in seconds)
     * before triggering an automated
     *         reboot to try to recover Internet connectivity
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setWwwWatchdogDelay(int newval)  throws YAPI_Exception
    {
        return set_wwwWatchdogDelay(newval);
    }

    /**
     * Returns the callback URL to notify of significant state changes.
     *
     * @return a string corresponding to the callback URL to notify of significant state changes
     *
     * @throws YAPI_Exception on error
     */
    public String get_callbackUrl() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CALLBACKURL_INVALID;
            }
        }
        return _callbackUrl;
    }

    /**
     * Returns the callback URL to notify of significant state changes.
     *
     * @return a string corresponding to the callback URL to notify of significant state changes
     *
     * @throws YAPI_Exception on error
     */
    public String getCallbackUrl() throws YAPI_Exception
    {
        return get_callbackUrl();
    }

    /**
     * Changes the callback URL to notify significant state changes. Remember to call the
     * saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : a string corresponding to the callback URL to notify significant state changes
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_callbackUrl(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("callbackUrl",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the callback URL to notify significant state changes. Remember to call the
     * saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : a string corresponding to the callback URL to notify significant state changes
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCallbackUrl(String newval)  throws YAPI_Exception
    {
        return set_callbackUrl(newval);
    }

    /**
     * Returns the HTTP method used to notify callbacks for significant state changes.
     *
     *  @return a value among YNetwork.CALLBACKMETHOD_POST, YNetwork.CALLBACKMETHOD_GET and
     *  YNetwork.CALLBACKMETHOD_PUT corresponding to the HTTP method used to notify callbacks for
     * significant state changes
     *
     * @throws YAPI_Exception on error
     */
    public int get_callbackMethod() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CALLBACKMETHOD_INVALID;
            }
        }
        return _callbackMethod;
    }

    /**
     * Returns the HTTP method used to notify callbacks for significant state changes.
     *
     *  @return a value among Y_CALLBACKMETHOD_POST, Y_CALLBACKMETHOD_GET and Y_CALLBACKMETHOD_PUT
     * corresponding to the HTTP method used to notify callbacks for significant state changes
     *
     * @throws YAPI_Exception on error
     */
    public int getCallbackMethod() throws YAPI_Exception
    {
        return get_callbackMethod();
    }

    /**
     * Changes the HTTP method used to notify callbacks for significant state changes.
     *
     *  @param newval : a value among YNetwork.CALLBACKMETHOD_POST, YNetwork.CALLBACKMETHOD_GET and
     *  YNetwork.CALLBACKMETHOD_PUT corresponding to the HTTP method used to notify callbacks for
     * significant state changes
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_callbackMethod(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("callbackMethod",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the HTTP method used to notify callbacks for significant state changes.
     *
     *  @param newval : a value among Y_CALLBACKMETHOD_POST, Y_CALLBACKMETHOD_GET and Y_CALLBACKMETHOD_PUT
     * corresponding to the HTTP method used to notify callbacks for significant state changes
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCallbackMethod(int newval)  throws YAPI_Exception
    {
        return set_callbackMethod(newval);
    }

    /**
     * Returns the encoding standard to use for representing notification values.
     *
     *  @return a value among YNetwork.CALLBACKENCODING_FORM, YNetwork.CALLBACKENCODING_JSON,
     *  YNetwork.CALLBACKENCODING_JSON_ARRAY, YNetwork.CALLBACKENCODING_CSV,
     *  YNetwork.CALLBACKENCODING_YOCTO_API, YNetwork.CALLBACKENCODING_JSON_NUM and
     *  YNetwork.CALLBACKENCODING_EMONCMS corresponding to the encoding standard to use for representing
     * notification values
     *
     * @throws YAPI_Exception on error
     */
    public int get_callbackEncoding() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CALLBACKENCODING_INVALID;
            }
        }
        return _callbackEncoding;
    }

    /**
     * Returns the encoding standard to use for representing notification values.
     *
     *  @return a value among Y_CALLBACKENCODING_FORM, Y_CALLBACKENCODING_JSON,
     *  Y_CALLBACKENCODING_JSON_ARRAY, Y_CALLBACKENCODING_CSV, Y_CALLBACKENCODING_YOCTO_API,
     *  Y_CALLBACKENCODING_JSON_NUM and Y_CALLBACKENCODING_EMONCMS corresponding to the encoding standard
     * to use for representing notification values
     *
     * @throws YAPI_Exception on error
     */
    public int getCallbackEncoding() throws YAPI_Exception
    {
        return get_callbackEncoding();
    }

    /**
     * Changes the encoding standard to use for representing notification values.
     *
     *  @param newval : a value among YNetwork.CALLBACKENCODING_FORM, YNetwork.CALLBACKENCODING_JSON,
     *  YNetwork.CALLBACKENCODING_JSON_ARRAY, YNetwork.CALLBACKENCODING_CSV,
     *  YNetwork.CALLBACKENCODING_YOCTO_API, YNetwork.CALLBACKENCODING_JSON_NUM and
     *  YNetwork.CALLBACKENCODING_EMONCMS corresponding to the encoding standard to use for representing
     * notification values
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_callbackEncoding(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("callbackEncoding",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the encoding standard to use for representing notification values.
     *
     *  @param newval : a value among Y_CALLBACKENCODING_FORM, Y_CALLBACKENCODING_JSON,
     *  Y_CALLBACKENCODING_JSON_ARRAY, Y_CALLBACKENCODING_CSV, Y_CALLBACKENCODING_YOCTO_API,
     *  Y_CALLBACKENCODING_JSON_NUM and Y_CALLBACKENCODING_EMONCMS corresponding to the encoding standard
     * to use for representing notification values
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCallbackEncoding(int newval)  throws YAPI_Exception
    {
        return set_callbackEncoding(newval);
    }

    /**
     * Returns a hashed version of the notification callback credentials if set,
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hashed version of the notification callback credentials if set,
     *         or an empty string otherwise
     *
     * @throws YAPI_Exception on error
     */
    public String get_callbackCredentials() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CALLBACKCREDENTIALS_INVALID;
            }
        }
        return _callbackCredentials;
    }

    /**
     * Returns a hashed version of the notification callback credentials if set,
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hashed version of the notification callback credentials if set,
     *         or an empty string otherwise
     *
     * @throws YAPI_Exception on error
     */
    public String getCallbackCredentials() throws YAPI_Exception
    {
        return get_callbackCredentials();
    }

    /**
     * Changes the credentials required to connect to the callback address. The credentials
     * must be provided as returned by function get_callbackCredentials,
     * in the form username:hash. The method used to compute the hash varies according
     * to the the authentication scheme implemented by the callback, For Basic authentication,
     * the hash is the MD5 of the string username:password. For Digest authentication,
     * the hash is the MD5 of the string username:realm:password. For a simpler
     * way to configure callback credentials, use function callbackLogin instead.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the credentials required to connect to the callback address
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_callbackCredentials(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("callbackCredentials",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the credentials required to connect to the callback address. The credentials
     * must be provided as returned by function get_callbackCredentials,
     * in the form username:hash. The method used to compute the hash varies according
     * to the the authentication scheme implemented by the callback, For Basic authentication,
     * the hash is the MD5 of the string username:password. For Digest authentication,
     * the hash is the MD5 of the string username:realm:password. For a simpler
     * way to configure callback credentials, use function callbackLogin instead.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the credentials required to connect to the callback address
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCallbackCredentials(String newval)  throws YAPI_Exception
    {
        return set_callbackCredentials(newval);
    }

    /**
     * Connects to the notification callback and saves the credentials required to
     * log into it. The password is not stored into the module, only a hashed
     * copy of the credentials are saved. Remember to call the
     * saveToFlash() method of the module if the modification must be kept.
     *
     * @param username : username required to log to the callback
     * @param password : password required to log to the callback
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int callbackLogin(String username,String password)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%s:%s", username.toString(), password.toString());
        _setAttr("callbackCredentials",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the minimum waiting time between two callback notifications, in seconds.
     *
     * @return an integer corresponding to the minimum waiting time between two callback notifications, in seconds
     *
     * @throws YAPI_Exception on error
     */
    public int get_callbackMinDelay() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CALLBACKMINDELAY_INVALID;
            }
        }
        return _callbackMinDelay;
    }

    /**
     * Returns the minimum waiting time between two callback notifications, in seconds.
     *
     * @return an integer corresponding to the minimum waiting time between two callback notifications, in seconds
     *
     * @throws YAPI_Exception on error
     */
    public int getCallbackMinDelay() throws YAPI_Exception
    {
        return get_callbackMinDelay();
    }

    /**
     * Changes the minimum waiting time between two callback notifications, in seconds.
     *
     *  @param newval : an integer corresponding to the minimum waiting time between two callback
     * notifications, in seconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_callbackMinDelay(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("callbackMinDelay",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the minimum waiting time between two callback notifications, in seconds.
     *
     *  @param newval : an integer corresponding to the minimum waiting time between two callback
     * notifications, in seconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCallbackMinDelay(int newval)  throws YAPI_Exception
    {
        return set_callbackMinDelay(newval);
    }

    /**
     * Returns the maximum waiting time between two callback notifications, in seconds.
     *
     * @return an integer corresponding to the maximum waiting time between two callback notifications, in seconds
     *
     * @throws YAPI_Exception on error
     */
    public int get_callbackMaxDelay() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CALLBACKMAXDELAY_INVALID;
            }
        }
        return _callbackMaxDelay;
    }

    /**
     * Returns the maximum waiting time between two callback notifications, in seconds.
     *
     * @return an integer corresponding to the maximum waiting time between two callback notifications, in seconds
     *
     * @throws YAPI_Exception on error
     */
    public int getCallbackMaxDelay() throws YAPI_Exception
    {
        return get_callbackMaxDelay();
    }

    /**
     * Changes the maximum waiting time between two callback notifications, in seconds.
     *
     *  @param newval : an integer corresponding to the maximum waiting time between two callback
     * notifications, in seconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_callbackMaxDelay(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("callbackMaxDelay",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximum waiting time between two callback notifications, in seconds.
     *
     *  @param newval : an integer corresponding to the maximum waiting time between two callback
     * notifications, in seconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCallbackMaxDelay(int newval)  throws YAPI_Exception
    {
        return set_callbackMaxDelay(newval);
    }

    /**
     * Returns the current consumed by the module from Power-over-Ethernet (PoE), in milli-amps.
     * The current consumption is measured after converting PoE source to 5 Volt, and should
     * never exceed 1800 mA.
     *
     *  @return an integer corresponding to the current consumed by the module from Power-over-Ethernet
     * (PoE), in milli-amps
     *
     * @throws YAPI_Exception on error
     */
    public int get_poeCurrent() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return POECURRENT_INVALID;
            }
        }
        return _poeCurrent;
    }

    /**
     * Returns the current consumed by the module from Power-over-Ethernet (PoE), in milli-amps.
     * The current consumption is measured after converting PoE source to 5 Volt, and should
     * never exceed 1800 mA.
     *
     *  @return an integer corresponding to the current consumed by the module from Power-over-Ethernet
     * (PoE), in milli-amps
     *
     * @throws YAPI_Exception on error
     */
    public int getPoeCurrent() throws YAPI_Exception
    {
        return get_poeCurrent();
    }

    /**
     * Retrieves a network interface for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the network interface is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YNetwork.isOnline() to test if the network interface is
     * indeed online at a given time. In case of ambiguity when looking for
     * a network interface by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the network interface
     *
     * @return a YNetwork object allowing you to drive the network interface.
     */
    public static YNetwork FindNetwork(String func)
    {
        YNetwork obj;
        obj = (YNetwork) YFunction._FindFromCache("Network", func);
        if (obj == null) {
            obj = new YNetwork(func);
            YFunction._AddToCache("Network", func, obj);
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
        _valueCallbackNetwork = callback;
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
        if (_valueCallbackNetwork != null) {
            _valueCallbackNetwork.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Changes the configuration of the network interface to enable the use of an
     * IP address received from a DHCP server. Until an address is received from a DHCP
     * server, the module uses the IP parameters specified to this function.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param fallbackIpAddr : fallback IP address, to be used when no DHCP reply is received
     * @param fallbackSubnetMaskLen : fallback subnet mask length when no DHCP reply is received, as an
     *         integer (eg. 24 means 255.255.255.0)
     * @param fallbackRouter : fallback router IP address, to be used when no DHCP reply is received
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int useDHCP(String fallbackIpAddr,int fallbackSubnetMaskLen,String fallbackRouter) throws YAPI_Exception
    {
        return set_ipConfig(String.format("DHCP:%s/%d/%s", fallbackIpAddr, fallbackSubnetMaskLen,fallbackRouter));
    }

    /**
     * Changes the configuration of the network interface to use a static IP address.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param ipAddress : device IP address
     * @param subnetMaskLen : subnet mask length, as an integer (eg. 24 means 255.255.255.0)
     * @param router : router IP address (default gateway)
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int useStaticIP(String ipAddress,int subnetMaskLen,String router) throws YAPI_Exception
    {
        return set_ipConfig(String.format("STATIC:%s/%d/%s", ipAddress, subnetMaskLen,router));
    }

    /**
     * Pings str_host to test the network connectivity. Sends four ICMP ECHO_REQUEST requests from the
     * module to the target str_host. This method returns a string with the result of the
     * 4 ICMP ECHO_REQUEST requests.
     *
     * @param host : the hostname or the IP address of the target
     *
     * @return a string with the result of the ping.
     */
    public String ping(String host) throws YAPI_Exception
    {
        byte[] content;
        // may throw an exception
        content = _download(String.format("ping.txt?host=%s",host));
        return new String(content);
    }

    /**
     * Continues the enumeration of network interfaces started using yFirstNetwork().
     *
     * @return a pointer to a YNetwork object, corresponding to
     *         a network interface currently online, or a null pointer
     *         if there are no more network interfaces to enumerate.
     */
    public  YNetwork nextNetwork()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindNetwork(next_hwid);
    }

    /**
     * Starts the enumeration of network interfaces currently accessible.
     * Use the method YNetwork.nextNetwork() to iterate on
     * next network interfaces.
     *
     * @return a pointer to a YNetwork object, corresponding to
     *         the first network interface currently online, or a null pointer
     *         if there are none.
     */
    public static YNetwork FirstNetwork()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Network");
        if (next_hwid == null)  return null;
        return FindNetwork(next_hwid);
    }

    //--- (end of YNetwork implementation)
}

