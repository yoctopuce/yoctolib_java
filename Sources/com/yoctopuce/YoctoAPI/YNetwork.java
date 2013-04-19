/*********************************************************************
 *
 * $Id: YNetwork.java 10549 2013-03-20 11:33:18Z mvuilleu $
 *
 * Implements yFindNetwork(), the high-level API for Network functions
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

  //--- (globals)
  //--- (end of globals)
/**
 * YNetwork Class: Network function interface
 * 
 * YNetwork objects provide access to TCP/IP parameters of Yoctopuce
 * modules that include a built-in network interface.
 */
public class YNetwork extends YFunction
{
    //--- (definitions)
    private YNetwork.UpdateCallback _valueCallbackNetwork;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
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
     * invalid userPassword value
     */
    public static final String USERPASSWORD_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid adminPassword value
     */
    public static final String ADMINPASSWORD_INVALID = YAPI.INVALID_STRING;
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
  public static final int CALLBACKENCODING_INVALID = -1;

    /**
     * invalid callbackCredentials value
     */
    public static final String CALLBACKCREDENTIALS_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid callbackMinDelay value
     */
    public static final int CALLBACKMINDELAY_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid callbackMaxDelay value
     */
    public static final int CALLBACKMAXDELAY_INVALID = YAPI.INVALID_UNSIGNED;
    //--- (end of definitions)

    /**
     * UdateCallback for Network
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YNetwork function, String functionValue);
    }



    //--- (YNetwork implementation)

    /**
     * Returns the logical name of the network interface, corresponding to the network name of the module.
     * 
     * @return a string corresponding to the logical name of the network interface, corresponding to the
     * network name of the module
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the network interface, corresponding to the network name of the module.
     * 
     * @return a string corresponding to the logical name of the network interface, corresponding to the
     * network name of the module
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the network interface, corresponding to the network name of the module.
     * You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the network interface, corresponding
     * to the network name of the module
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
     * Changes the logical name of the network interface, corresponding to the network name of the module.
     * You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the network interface, corresponding
     * to the network name of the module
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the network interface (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the network interface (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the network interface (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the network interface (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the current established working mode of the network interface.
     * Level zero (DOWN_0) means that no hardware link has been detected. Either there is no signal
     * on the network cable, or the selected wireless access point cannot be detected.
     * Level 1 (LIVE_1) is reached when the network is detected, but is not yet connected,
     * For a wireless network, this shows that the requested SSID is present.
     * Level 2 (LINK_2) is reached when the hardware connection is established.
     * For a wired network connection, level 2 means that the cable is attached on both ends.
     * For a connection to a wireless access point, it shows that the security parameters
     * are properly configured. For an ad-hoc wireless connection, it means that there is
     * at least one other device connected on the ad-hoc network.
     * Level 3 (DHCP_3) is reached when an IP address has been obtained using DHCP.
     * Level 4 (DNS_4) is reached when the DNS server is reachable on the network.
     * Level 5 (WWW_5) is reached when global connectivity is demonstrated by properly loading
     * current time from an NTP server.
     * 
     * @return a value among YNetwork.READINESS_DOWN, YNetwork.READINESS_EXISTS,
     * YNetwork.READINESS_LINKED, YNetwork.READINESS_LAN_OK and YNetwork.READINESS_WWW_OK corresponding to
     * the current established working mode of the network interface
     * 
     * @throws YAPI_Exception
     */
    public int get_readiness()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("readiness");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current established working mode of the network interface.
     * Level zero (DOWN_0) means that no hardware link has been detected. Either there is no signal
     * on the network cable, or the selected wireless access point cannot be detected.
     * Level 1 (LIVE_1) is reached when the network is detected, but is not yet connected,
     * For a wireless network, this shows that the requested SSID is present.
     * Level 2 (LINK_2) is reached when the hardware connection is established.
     * For a wired network connection, level 2 means that the cable is attached on both ends.
     * For a connection to a wireless access point, it shows that the security parameters
     * are properly configured. For an ad-hoc wireless connection, it means that there is
     * at least one other device connected on the ad-hoc network.
     * Level 3 (DHCP_3) is reached when an IP address has been obtained using DHCP.
     * Level 4 (DNS_4) is reached when the DNS server is reachable on the network.
     * Level 5 (WWW_5) is reached when global connectivity is demonstrated by properly loading
     * current time from an NTP server.
     * 
     * @return a value among Y_READINESS_DOWN, Y_READINESS_EXISTS, Y_READINESS_LINKED, Y_READINESS_LAN_OK
     * and Y_READINESS_WWW_OK corresponding to the current established working mode of the network interface
     * 
     * @throws YAPI_Exception
     */
    public int getReadiness() throws YAPI_Exception

    { return get_readiness(); }

    /**
     * Returns the MAC address of the network interface. The MAC address is also available on a sticker
     * on the module, in both numeric and barcode forms.
     * 
     * @return a string corresponding to the MAC address of the network interface
     * 
     * @throws YAPI_Exception
     */
    public String get_macAddress()  throws YAPI_Exception
    {
        String json_val = (String) _getFixedAttr("macAddress");
        return json_val;
    }

    /**
     * Returns the MAC address of the network interface. The MAC address is also available on a sticker
     * on the module, in both numeric and barcode forms.
     * 
     * @return a string corresponding to the MAC address of the network interface
     * 
     * @throws YAPI_Exception
     */
    public String getMacAddress() throws YAPI_Exception

    { return get_macAddress(); }

    /**
     * Returns the IP address currently in use by the device. The adress may have been configured
     * statically, or provided by a DHCP server.
     * 
     * @return a string corresponding to the IP address currently in use by the device
     * 
     * @throws YAPI_Exception
     */
    public String get_ipAddress()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("ipAddress");
        return json_val;
    }

    /**
     * Returns the IP address currently in use by the device. The adress may have been configured
     * statically, or provided by a DHCP server.
     * 
     * @return a string corresponding to the IP address currently in use by the device
     * 
     * @throws YAPI_Exception
     */
    public String getIpAddress() throws YAPI_Exception

    { return get_ipAddress(); }

    /**
     * Returns the subnet mask currently used by the device.
     * 
     * @return a string corresponding to the subnet mask currently used by the device
     * 
     * @throws YAPI_Exception
     */
    public String get_subnetMask()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("subnetMask");
        return json_val;
    }

    /**
     * Returns the subnet mask currently used by the device.
     * 
     * @return a string corresponding to the subnet mask currently used by the device
     * 
     * @throws YAPI_Exception
     */
    public String getSubnetMask() throws YAPI_Exception

    { return get_subnetMask(); }

    /**
     * Returns the IP address of the router on the device subnet (default gateway).
     * 
     * @return a string corresponding to the IP address of the router on the device subnet (default gateway)
     * 
     * @throws YAPI_Exception
     */
    public String get_router()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("router");
        return json_val;
    }

    /**
     * Returns the IP address of the router on the device subnet (default gateway).
     * 
     * @return a string corresponding to the IP address of the router on the device subnet (default gateway)
     * 
     * @throws YAPI_Exception
     */
    public String getRouter() throws YAPI_Exception

    { return get_router(); }

    public String get_ipConfig()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("ipConfig");
        return json_val;
    }

    public String getIpConfig() throws YAPI_Exception

    { return get_ipConfig(); }

    public int set_ipConfig( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("ipConfig",rest_val);
        return YAPI.SUCCESS;
    }

    public int setIpConfig( String newval)  throws YAPI_Exception

    { return set_ipConfig(newval); }

    /**
     * Changes the configuration of the network interface to enable the use of an
     * IP address received from a DHCP server. Until an address is received from a DHCP
     * server, the module will use the IP parameters specified to this function.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param fallbackIpAddr : fallback IP address, to be used when no DHCP reply is received
     * @param fallbackSubnetMaskLen : fallback subnet mask length when no DHCP reply is received, as an
     *         integer (eg. 24 means 255.255.255.0)
     * @param fallbackRouter : fallback router IP address, to be used when no DHCP reply is received
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int useDHCP(String fallbackIpAddr,int fallbackSubnetMaskLen,String fallbackRouter)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("DHCP:%s/%d/%s", fallbackIpAddr.toString(), fallbackSubnetMaskLen, fallbackRouter.toString());
        _setAttr("ipConfig",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the configuration of the network interface to use a static IP address.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param ipAddress : device IP address
     * @param subnetMaskLen : subnet mask length, as an integer (eg. 24 means 255.255.255.0)
     * @param router : router IP address (default gateway)
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int useStaticIP(String ipAddress,int subnetMaskLen,String router)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("STATIC:%s/%d/%s", ipAddress.toString(), subnetMaskLen, router.toString());
        _setAttr("ipConfig",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the IP address of the primary name server to be used by the module.
     * 
     * @return a string corresponding to the IP address of the primary name server to be used by the module
     * 
     * @throws YAPI_Exception
     */
    public String get_primaryDNS()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("primaryDNS");
        return json_val;
    }

    /**
     * Returns the IP address of the primary name server to be used by the module.
     * 
     * @return a string corresponding to the IP address of the primary name server to be used by the module
     * 
     * @throws YAPI_Exception
     */
    public String getPrimaryDNS() throws YAPI_Exception

    { return get_primaryDNS(); }

    /**
     * Changes the IP address of the primary name server to be used by the module.
     * When using DHCP, if a value is specified, it will override the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param newval : a string corresponding to the IP address of the primary name server to be used by the module
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_primaryDNS( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("primaryDNS",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the IP address of the primary name server to be used by the module.
     * When using DHCP, if a value is specified, it will override the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param newval : a string corresponding to the IP address of the primary name server to be used by the module
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setPrimaryDNS( String newval)  throws YAPI_Exception

    { return set_primaryDNS(newval); }

    /**
     * Returns the IP address of the secondary name server to be used by the module.
     * 
     * @return a string corresponding to the IP address of the secondary name server to be used by the module
     * 
     * @throws YAPI_Exception
     */
    public String get_secondaryDNS()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("secondaryDNS");
        return json_val;
    }

    /**
     * Returns the IP address of the secondary name server to be used by the module.
     * 
     * @return a string corresponding to the IP address of the secondary name server to be used by the module
     * 
     * @throws YAPI_Exception
     */
    public String getSecondaryDNS() throws YAPI_Exception

    { return get_secondaryDNS(); }

    /**
     * Changes the IP address of the secondarz name server to be used by the module.
     * When using DHCP, if a value is specified, it will override the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param newval : a string corresponding to the IP address of the secondarz name server to be used by the module
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_secondaryDNS( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("secondaryDNS",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the IP address of the secondarz name server to be used by the module.
     * When using DHCP, if a value is specified, it will override the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     * 
     * @param newval : a string corresponding to the IP address of the secondarz name server to be used by the module
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setSecondaryDNS( String newval)  throws YAPI_Exception

    { return set_secondaryDNS(newval); }

    /**
     * Returns a hash string if a password has been set for user "user",
     * or an empty string otherwise.
     * 
     * @return a string corresponding to a hash string if a password has been set for user "user",
     *         or an empty string otherwise
     * 
     * @throws YAPI_Exception
     */
    public String get_userPassword()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("userPassword");
        return json_val;
    }

    /**
     * Returns a hash string if a password has been set for user "user",
     * or an empty string otherwise.
     * 
     * @return a string corresponding to a hash string if a password has been set for user "user",
     *         or an empty string otherwise
     * 
     * @throws YAPI_Exception
     */
    public String getUserPassword() throws YAPI_Exception

    { return get_userPassword(); }

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
     * @throws YAPI_Exception
     */
    public int set_userPassword( String  newval)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int setUserPassword( String newval)  throws YAPI_Exception

    { return set_userPassword(newval); }

    /**
     * Returns a hash string if a password has been set for user "admin",
     * or an empty string otherwise.
     * 
     * @return a string corresponding to a hash string if a password has been set for user "admin",
     *         or an empty string otherwise
     * 
     * @throws YAPI_Exception
     */
    public String get_adminPassword()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("adminPassword");
        return json_val;
    }

    /**
     * Returns a hash string if a password has been set for user "admin",
     * or an empty string otherwise.
     * 
     * @return a string corresponding to a hash string if a password has been set for user "admin",
     *         or an empty string otherwise
     * 
     * @throws YAPI_Exception
     */
    public String getAdminPassword() throws YAPI_Exception

    { return get_adminPassword(); }

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
     * @throws YAPI_Exception
     */
    public int set_adminPassword( String  newval)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int setAdminPassword( String newval)  throws YAPI_Exception

    { return set_adminPassword(newval); }

    /**
     * Returns the callback URL to notify of significant state changes.
     * 
     * @return a string corresponding to the callback URL to notify of significant state changes
     * 
     * @throws YAPI_Exception
     */
    public String get_callbackUrl()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("callbackUrl");
        return json_val;
    }

    /**
     * Returns the callback URL to notify of significant state changes.
     * 
     * @return a string corresponding to the callback URL to notify of significant state changes
     * 
     * @throws YAPI_Exception
     */
    public String getCallbackUrl() throws YAPI_Exception

    { return get_callbackUrl(); }

    /**
     * Changes the callback URL to notify of significant state changes. Remember to call the
     * saveToFlash() method of the module if the modification must be kept.
     * 
     * @param newval : a string corresponding to the callback URL to notify of significant state changes
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_callbackUrl( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("callbackUrl",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the callback URL to notify of significant state changes. Remember to call the
     * saveToFlash() method of the module if the modification must be kept.
     * 
     * @param newval : a string corresponding to the callback URL to notify of significant state changes
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setCallbackUrl( String newval)  throws YAPI_Exception

    { return set_callbackUrl(newval); }

    /**
     * Returns the HTTP Method used to notify callbacks for significant state changes.
     * 
     * @return a value among YNetwork.CALLBACKMETHOD_POST, YNetwork.CALLBACKMETHOD_GET and
     * YNetwork.CALLBACKMETHOD_PUT corresponding to the HTTP Method used to notify callbacks for
     * significant state changes
     * 
     * @throws YAPI_Exception
     */
    public int get_callbackMethod()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("callbackMethod");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the HTTP Method used to notify callbacks for significant state changes.
     * 
     * @return a value among Y_CALLBACKMETHOD_POST, Y_CALLBACKMETHOD_GET and Y_CALLBACKMETHOD_PUT
     * corresponding to the HTTP Method used to notify callbacks for significant state changes
     * 
     * @throws YAPI_Exception
     */
    public int getCallbackMethod() throws YAPI_Exception

    { return get_callbackMethod(); }

    /**
     * Changes the HTTP Method used to notify callbacks for significant state changes.
     * 
     * @param newval : a value among YNetwork.CALLBACKMETHOD_POST, YNetwork.CALLBACKMETHOD_GET and
     * YNetwork.CALLBACKMETHOD_PUT corresponding to the HTTP Method used to notify callbacks for
     * significant state changes
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_callbackMethod( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("callbackMethod",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the HTTP Method used to notify callbacks for significant state changes.
     * 
     * @param newval : a value among Y_CALLBACKMETHOD_POST, Y_CALLBACKMETHOD_GET and Y_CALLBACKMETHOD_PUT
     * corresponding to the HTTP Method used to notify callbacks for significant state changes
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setCallbackMethod( int newval)  throws YAPI_Exception

    { return set_callbackMethod(newval); }

    /**
     * Returns the encoding standard to use for representing notification values.
     * 
     * @return a value among YNetwork.CALLBACKENCODING_FORM, YNetwork.CALLBACKENCODING_JSON,
     * YNetwork.CALLBACKENCODING_JSON_ARRAY, YNetwork.CALLBACKENCODING_CSV and
     * YNetwork.CALLBACKENCODING_YOCTO_API corresponding to the encoding standard to use for representing
     * notification values
     * 
     * @throws YAPI_Exception
     */
    public int get_callbackEncoding()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("callbackEncoding");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the encoding standard to use for representing notification values.
     * 
     * @return a value among Y_CALLBACKENCODING_FORM, Y_CALLBACKENCODING_JSON,
     * Y_CALLBACKENCODING_JSON_ARRAY, Y_CALLBACKENCODING_CSV and Y_CALLBACKENCODING_YOCTO_API
     * corresponding to the encoding standard to use for representing notification values
     * 
     * @throws YAPI_Exception
     */
    public int getCallbackEncoding() throws YAPI_Exception

    { return get_callbackEncoding(); }

    /**
     * Changes the encoding standard to use for representing notification values.
     * 
     * @param newval : a value among YNetwork.CALLBACKENCODING_FORM, YNetwork.CALLBACKENCODING_JSON,
     * YNetwork.CALLBACKENCODING_JSON_ARRAY, YNetwork.CALLBACKENCODING_CSV and
     * YNetwork.CALLBACKENCODING_YOCTO_API corresponding to the encoding standard to use for representing
     * notification values
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_callbackEncoding( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("callbackEncoding",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the encoding standard to use for representing notification values.
     * 
     * @param newval : a value among Y_CALLBACKENCODING_FORM, Y_CALLBACKENCODING_JSON,
     * Y_CALLBACKENCODING_JSON_ARRAY, Y_CALLBACKENCODING_CSV and Y_CALLBACKENCODING_YOCTO_API
     * corresponding to the encoding standard to use for representing notification values
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setCallbackEncoding( int newval)  throws YAPI_Exception

    { return set_callbackEncoding(newval); }

    /**
     * Returns a hashed version of the notification callback credentials if set,
     * or an empty string otherwise.
     * 
     * @return a string corresponding to a hashed version of the notification callback credentials if set,
     *         or an empty string otherwise
     * 
     * @throws YAPI_Exception
     */
    public String get_callbackCredentials()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("callbackCredentials");
        return json_val;
    }

    /**
     * Returns a hashed version of the notification callback credentials if set,
     * or an empty string otherwise.
     * 
     * @return a string corresponding to a hashed version of the notification callback credentials if set,
     *         or an empty string otherwise
     * 
     * @throws YAPI_Exception
     */
    public String getCallbackCredentials() throws YAPI_Exception

    { return get_callbackCredentials(); }

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
     * @throws YAPI_Exception
     */
    public int set_callbackCredentials( String  newval)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int setCallbackCredentials( String newval)  throws YAPI_Exception

    { return set_callbackCredentials(newval); }

    /**
     * Connects to the notification callback and saves the credentials required to
     * log in to it. The password will not be stored into the module, only a hashed
     * copy of the credentials will be saved. Remember to call the
     * saveToFlash() method of the module if the modification must be kept.
     * 
     * @param username : username required to log to the callback
     * @param password : password required to log to the callback
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int callbackLogin(String username,String password)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%s:%s", username.toString(), password.toString());
        _setAttr("callbackCredentials",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the minimum wait time between two callback notifications, in seconds.
     * 
     * @return an integer corresponding to the minimum wait time between two callback notifications, in seconds
     * 
     * @throws YAPI_Exception
     */
    public int get_callbackMinDelay()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("callbackMinDelay");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the minimum wait time between two callback notifications, in seconds.
     * 
     * @return an integer corresponding to the minimum wait time between two callback notifications, in seconds
     * 
     * @throws YAPI_Exception
     */
    public int getCallbackMinDelay() throws YAPI_Exception

    { return get_callbackMinDelay(); }

    /**
     * Changes the minimum wait time between two callback notifications, in seconds.
     * 
     * @param newval : an integer corresponding to the minimum wait time between two callback notifications, in seconds
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_callbackMinDelay( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("callbackMinDelay",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the minimum wait time between two callback notifications, in seconds.
     * 
     * @param newval : an integer corresponding to the minimum wait time between two callback notifications, in seconds
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setCallbackMinDelay( int newval)  throws YAPI_Exception

    { return set_callbackMinDelay(newval); }

    /**
     * Returns the maximum wait time between two callback notifications, in seconds.
     * 
     * @return an integer corresponding to the maximum wait time between two callback notifications, in seconds
     * 
     * @throws YAPI_Exception
     */
    public int get_callbackMaxDelay()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("callbackMaxDelay");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the maximum wait time between two callback notifications, in seconds.
     * 
     * @return an integer corresponding to the maximum wait time between two callback notifications, in seconds
     * 
     * @throws YAPI_Exception
     */
    public int getCallbackMaxDelay() throws YAPI_Exception

    { return get_callbackMaxDelay(); }

    /**
     * Changes the maximum wait time between two callback notifications, in seconds.
     * 
     * @param newval : an integer corresponding to the maximum wait time between two callback notifications, in seconds
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_callbackMaxDelay( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("callbackMaxDelay",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximum wait time between two callback notifications, in seconds.
     * 
     * @param newval : an integer corresponding to the maximum wait time between two callback notifications, in seconds
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setCallbackMaxDelay( int newval)  throws YAPI_Exception

    { return set_callbackMaxDelay(newval); }

    /**
     * Continues the enumeration of network interfaces started using yFirstNetwork().
     * 
     * @return a pointer to a YNetwork object, corresponding to
     *         a network interface currently online, or a null pointer
     *         if there are no more network interfaces to enumerate.
     */
    public  YNetwork nextNetwork()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindNetwork(next_hwid);
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
    {   YFunction yfunc = YAPI.getFunction("Network", func);
        if (yfunc != null) {
            return (YNetwork) yfunc;
        }
        return new YNetwork(func);
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
        String next_hwid = YAPI.getFirstHardwareId("Network");
        if (next_hwid == null)  return null;
        return FindNetwork(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YNetwork(String func)
    {
        super("Network", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackNetwork != null) {
            _valueCallbackNetwork.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackNetwork!=null);
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
    public void registerValueCallback(YNetwork.UpdateCallback callback)
    {
         _valueCallbackNetwork =  callback;
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

    //--- (end of YNetwork implementation)
};

