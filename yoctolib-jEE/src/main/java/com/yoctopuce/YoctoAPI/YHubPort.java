/*
 *
 *  $Id: YHubPort.java 38510 2019-11-26 15:36:38Z mvuilleu $
 *
 *  Implements FindHubPort(), the high-level API for HubPort functions
 *
 *  - - - - - - - - - License information: - - - - - - - - -
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
 */

package com.yoctopuce.YoctoAPI;

//--- (YHubPort return codes)
//--- (end of YHubPort return codes)
//--- (YHubPort yapiwrapper)
//--- (end of YHubPort yapiwrapper)
//--- (YHubPort class start)
/**
 * YHubPort Class: Yocto-hub port interface
 *
 * The YHubPort class provides control over the power supply for every port
 *  on a YoctoHub, for instance using a YoctoHub-Ethernet, a YoctoHub-GSM-3G-NA, a YoctoHub-Shield or a
 * YoctoHub-Wireless-g. It provide information about the device connected to it.
 * The logical name of a YHubPort is always automatically set to the
 * unique serial number of the Yoctopuce device connected to it.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YHubPort extends YFunction
{
//--- (end of YHubPort class start)
//--- (YHubPort definitions)
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
    public static final int PORTSTATE_OVRLD = 1;
    public static final int PORTSTATE_ON = 2;
    public static final int PORTSTATE_RUN = 3;
    public static final int PORTSTATE_PROG = 4;
    public static final int PORTSTATE_INVALID = -1;
    /**
     * invalid baudRate value
     */
    public static final int BAUDRATE_INVALID = YAPI.INVALID_UINT;
    protected int _enabled = ENABLED_INVALID;
    protected int _portState = PORTSTATE_INVALID;
    protected int _baudRate = BAUDRATE_INVALID;
    protected UpdateCallback _valueCallbackHubPort = null;

    /**
     * Deprecated UpdateCallback for HubPort
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YHubPort function, String functionValue);
    }

    /**
     * TimedReportCallback for HubPort
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YHubPort  function, YMeasure measure);
    }
    //--- (end of YHubPort definitions)


    /**
     *
     * @param func : functionid
     */
    protected YHubPort(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "HubPort";
        //--- (YHubPort attributes initialization)
        //--- (end of YHubPort attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YHubPort(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YHubPort implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("enabled")) {
            _enabled = json_val.getInt("enabled") > 0 ? 1 : 0;
        }
        if (json_val.has("portState")) {
            _portState = json_val.getInt("portState");
        }
        if (json_val.has("baudRate")) {
            _baudRate = json_val.getInt("baudRate");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns true if the Yocto-hub port is powered, false otherwise.
     *
     *  @return either YHubPort.ENABLED_FALSE or YHubPort.ENABLED_TRUE, according to true if the Yocto-hub
     * port is powered, false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int get_enabled() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ENABLED_INVALID;
                }
            }
            res = _enabled;
        }
        return res;
    }

    /**
     * Returns true if the Yocto-hub port is powered, false otherwise.
     *
     *  @return either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to true if the Yocto-hub port is
     * powered, false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int getEnabled() throws YAPI_Exception
    {
        return get_enabled();
    }

    /**
     * Changes the activation of the Yocto-hub port. If the port is enabled, the
     * connected module is powered. Otherwise, port power is shut down.
     *
     *  @param newval : either YHubPort.ENABLED_FALSE or YHubPort.ENABLED_TRUE, according to the activation
     * of the Yocto-hub port
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_enabled(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("enabled",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation of the Yocto-hub port. If the port is enabled, the
     * connected module is powered. Otherwise, port power is shut down.
     *
     * @param newval : either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the activation of the Yocto-hub port
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setEnabled(int newval)  throws YAPI_Exception
    {
        return set_enabled(newval);
    }

    /**
     * Returns the current state of the Yocto-hub port.
     *
     *  @return a value among YHubPort.PORTSTATE_OFF, YHubPort.PORTSTATE_OVRLD, YHubPort.PORTSTATE_ON,
     * YHubPort.PORTSTATE_RUN and YHubPort.PORTSTATE_PROG corresponding to the current state of the Yocto-hub port
     *
     * @throws YAPI_Exception on error
     */
    public int get_portState() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PORTSTATE_INVALID;
                }
            }
            res = _portState;
        }
        return res;
    }

    /**
     * Returns the current state of the Yocto-hub port.
     *
     *  @return a value among Y_PORTSTATE_OFF, Y_PORTSTATE_OVRLD, Y_PORTSTATE_ON, Y_PORTSTATE_RUN and
     * Y_PORTSTATE_PROG corresponding to the current state of the Yocto-hub port
     *
     * @throws YAPI_Exception on error
     */
    public int getPortState() throws YAPI_Exception
    {
        return get_portState();
    }

    /**
     * Returns the current baud rate used by this Yocto-hub port, in kbps.
     * The default value is 1000 kbps, but a slower rate may be used if communication
     * problems are encountered.
     *
     * @return an integer corresponding to the current baud rate used by this Yocto-hub port, in kbps
     *
     * @throws YAPI_Exception on error
     */
    public int get_baudRate() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BAUDRATE_INVALID;
                }
            }
            res = _baudRate;
        }
        return res;
    }

    /**
     * Returns the current baud rate used by this Yocto-hub port, in kbps.
     * The default value is 1000 kbps, but a slower rate may be used if communication
     * problems are encountered.
     *
     * @return an integer corresponding to the current baud rate used by this Yocto-hub port, in kbps
     *
     * @throws YAPI_Exception on error
     */
    public int getBaudRate() throws YAPI_Exception
    {
        return get_baudRate();
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
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the Yocto-hub port, for instance
     *         YHUBETH1.hubPort1.
     *
     * @return a YHubPort object allowing you to drive the Yocto-hub port.
     */
    public static YHubPort FindHubPort(String func)
    {
        YHubPort obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YHubPort) YFunction._FindFromCache("HubPort", func);
            if (obj == null) {
                obj = new YHubPort(func);
                YFunction._AddToCache("HubPort", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a Yocto-hub port for a given identifier in a YAPI context.
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
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the Yocto-hub port, for instance
     *         YHUBETH1.hubPort1.
     *
     * @return a YHubPort object allowing you to drive the Yocto-hub port.
     */
    public static YHubPort FindHubPortInContext(YAPIContext yctx,String func)
    {
        YHubPort obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YHubPort) YFunction._FindFromCacheInContext(yctx, "HubPort", func);
            if (obj == null) {
                obj = new YHubPort(yctx, func);
                YFunction._AddToCache("HubPort", func, obj);
            }
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
        _valueCallbackHubPort = callback;
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
        if (_valueCallbackHubPort != null) {
            _valueCallbackHubPort.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of Yocto-hub ports started using yFirstHubPort().
     * Caution: You can't make any assumption about the returned Yocto-hub ports order.
     * If you want to find a specific a Yocto-hub port, use HubPort.findHubPort()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YHubPort object, corresponding to
     *         a Yocto-hub port currently online, or a null pointer
     *         if there are no more Yocto-hub ports to enumerate.
     */
    public YHubPort nextHubPort()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindHubPortInContext(_yapi, next_hwid);
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
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("HubPort");
        if (next_hwid == null)  return null;
        return FindHubPortInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of Yocto-hub ports currently accessible.
     * Use the method YHubPort.nextHubPort() to iterate on
     * next Yocto-hub ports.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YHubPort object, corresponding to
     *         the first Yocto-hub port currently online, or a null pointer
     *         if there are none.
     */
    public static YHubPort FirstHubPortInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("HubPort");
        if (next_hwid == null)  return null;
        return FindHubPortInContext(yctx, next_hwid);
    }

    //--- (end of YHubPort implementation)
}

