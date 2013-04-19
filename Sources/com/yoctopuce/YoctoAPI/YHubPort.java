/*********************************************************************
 *
 * $Id: YHubPort.java 9137 2012-12-18 14:50:34Z seb $
 *
 * Implements yFindHubPort(), the high-level API for HubPort functions
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
 * YHubPort Class: Yocto-hub port interface
 * 
 * 
 */
public class YHubPort extends YFunction
{
    //--- (definitions)
    private YHubPort.UpdateCallback _valueCallbackHubPort;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid enabled value
     */
  public static final int ENABLED_FALSE = 0;
  public static final int ENABLED_TRUE = 1;
  public static final int ENABLED_INVALID = -1;

    /**
     * invalid portState value
     */
  public static final int PORTSTATE_OFF = 0;
  public static final int PORTSTATE_ON = 1;
  public static final int PORTSTATE_RUN = 2;
  public static final int PORTSTATE_INVALID = -1;

    /**
     * invalid baudRate value
     */
    public static final int BAUDRATE_INVALID = YAPI.INVALID_UNSIGNED;
    //--- (end of definitions)

    /**
     * UdateCallback for HubPort
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YHubPort function, String functionValue);
    }



    //--- (YHubPort implementation)

    /**
     * Returns the logical name of the Yocto-hub port, which is always the serial number of the
     * connected module.
     * 
     * @return a string corresponding to the logical name of the Yocto-hub port, which is always the
     * serial number of the
     *         connected module
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the Yocto-hub port, which is always the serial number of the
     * connected module.
     * 
     * @return a string corresponding to the logical name of the Yocto-hub port, which is always the
     * serial number of the
     *         connected module
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * It is not possible to configure the logical name of a Yocto-hub port. The logical
     * name is automatically set to the serial number of the connected module.
     * 
     * @param newval : a string
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
     * It is not possible to configure the logical name of a Yocto-hub port. The logical
     * name is automatically set to the serial number of the connected module.
     * 
     * @param newval : a string
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the Yocto-hub port (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the Yocto-hub port (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the Yocto-hub port (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the Yocto-hub port (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns true if the Yocto-hub port is powered, false otherwise.
     * 
     * @return either YHubPort.ENABLED_FALSE or YHubPort.ENABLED_TRUE, according to true if the Yocto-hub
     * port is powered, false otherwise
     * 
     * @throws YAPI_Exception
     */
    public int get_enabled()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("enabled");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns true if the Yocto-hub port is powered, false otherwise.
     * 
     * @return either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to true if the Yocto-hub port is
     * powered, false otherwise
     * 
     * @throws YAPI_Exception
     */
    public int getEnabled() throws YAPI_Exception

    { return get_enabled(); }

    /**
     * Changes the activation of the Yocto-hub port. If the port is enabled, the
     * *      connected module will be powered. Otherwise, port power will be shut down.
     * 
     * @param newval : either YHubPort.ENABLED_FALSE or YHubPort.ENABLED_TRUE, according to the activation
     * of the Yocto-hub port
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_enabled( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("enabled",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation of the Yocto-hub port. If the port is enabled, the
     * *      connected module will be powered. Otherwise, port power will be shut down.
     * 
     * @param newval : either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the activation of the Yocto-hub port
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setEnabled( int newval)  throws YAPI_Exception

    { return set_enabled(newval); }

    /**
     * Returns the current state of the Yocto-hub port.
     * 
     * @return a value among YHubPort.PORTSTATE_OFF, YHubPort.PORTSTATE_ON and YHubPort.PORTSTATE_RUN
     * corresponding to the current state of the Yocto-hub port
     * 
     * @throws YAPI_Exception
     */
    public int get_portState()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("portState");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current state of the Yocto-hub port.
     * 
     * @return a value among Y_PORTSTATE_OFF, Y_PORTSTATE_ON and Y_PORTSTATE_RUN corresponding to the
     * current state of the Yocto-hub port
     * 
     * @throws YAPI_Exception
     */
    public int getPortState() throws YAPI_Exception

    { return get_portState(); }

    /**
     * Returns the current baud rate used by this Yocto-hub port, in kbps.
     * The default value is 1000 kbps, but a slower rate may be used if communication
     * problems are hit.
     * 
     * @return an integer corresponding to the current baud rate used by this Yocto-hub port, in kbps
     * 
     * @throws YAPI_Exception
     */
    public int get_baudRate()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("baudRate");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current baud rate used by this Yocto-hub port, in kbps.
     * The default value is 1000 kbps, but a slower rate may be used if communication
     * problems are hit.
     * 
     * @return an integer corresponding to the current baud rate used by this Yocto-hub port, in kbps
     * 
     * @throws YAPI_Exception
     */
    public int getBaudRate() throws YAPI_Exception

    { return get_baudRate(); }

    /**
     * Continues the enumeration of Yocto-hub ports started using yFirstHubPort().
     * 
     * @return a pointer to a YHubPort object, corresponding to
     *         a Yocto-hub port currently online, or a null pointer
     *         if there are no more Yocto-hub ports to enumerate.
     */
    public  YHubPort nextHubPort()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindHubPort(next_hwid);
    }

    /**
     * Retrieves a Yocto-hub port for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the Yocto-hub port is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YHubPort.isOnline() to test if the Yocto-hub port is
     * indeed online at a given time. In case of ambiguity when looking for
     * a Yocto-hub port by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the Yocto-hub port
     * 
     * @return a YHubPort object allowing you to drive the Yocto-hub port.
     */
    public static YHubPort FindHubPort(String func)
    {   YFunction yfunc = YAPI.getFunction("HubPort", func);
        if (yfunc != null) {
            return (YHubPort) yfunc;
        }
        return new YHubPort(func);
    }

    /**
     * Starts the enumeration of Yocto-hub ports currently accessible.
     * Use the method YHubPort.nextHubPort() to iterate on
     * next Yocto-hub ports.
     * 
     * @return a pointer to a YHubPort object, corresponding to
     *         the first Yocto-hub port currently online, or a null pointer
     *         if there are none.
     */
    public static YHubPort FirstHubPort()
    {
        String next_hwid = YAPI.getFirstHardwareId("HubPort");
        if (next_hwid == null)  return null;
        return FindHubPort(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YHubPort(String func)
    {
        super("HubPort", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackHubPort != null) {
            _valueCallbackHubPort.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackHubPort!=null);
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
    public void registerValueCallback(YHubPort.UpdateCallback callback)
    {
         _valueCallbackHubPort =  callback;
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

    //--- (end of YHubPort implementation)
};

