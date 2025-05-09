/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindBluetoothLink(), the high-level API for BluetoothLink functions
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

//--- (YBluetoothLink return codes)
//--- (end of YBluetoothLink return codes)
//--- (YBluetoothLink yapiwrapper)
//--- (end of YBluetoothLink yapiwrapper)
//--- (YBluetoothLink class start)
/**
 * YBluetoothLink Class: Bluetooth sound controller control interface
 *
 * BluetoothLink function provides control over Bluetooth link
 * and status for devices that are Bluetooth-enabled.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YBluetoothLink extends YFunction
{
//--- (end of YBluetoothLink class start)
//--- (YBluetoothLink definitions)
    /**
     * invalid ownAddress value
     */
    public static final String OWNADDRESS_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid pairingPin value
     */
    public static final String PAIRINGPIN_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid remoteAddress value
     */
    public static final String REMOTEADDRESS_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid remoteName value
     */
    public static final String REMOTENAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid mute value
     */
    public static final int MUTE_FALSE = 0;
    public static final int MUTE_TRUE = 1;
    public static final int MUTE_INVALID = -1;
    /**
     * invalid preAmplifier value
     */
    public static final int PREAMPLIFIER_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid volume value
     */
    public static final int VOLUME_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid linkState value
     */
    public static final int LINKSTATE_DOWN = 0;
    public static final int LINKSTATE_FREE = 1;
    public static final int LINKSTATE_SEARCH = 2;
    public static final int LINKSTATE_EXISTS = 3;
    public static final int LINKSTATE_LINKED = 4;
    public static final int LINKSTATE_PLAY = 5;
    public static final int LINKSTATE_INVALID = -1;
    /**
     * invalid linkQuality value
     */
    public static final int LINKQUALITY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected String _ownAddress = OWNADDRESS_INVALID;
    protected String _pairingPin = PAIRINGPIN_INVALID;
    protected String _remoteAddress = REMOTEADDRESS_INVALID;
    protected String _remoteName = REMOTENAME_INVALID;
    protected int _mute = MUTE_INVALID;
    protected int _preAmplifier = PREAMPLIFIER_INVALID;
    protected int _volume = VOLUME_INVALID;
    protected int _linkState = LINKSTATE_INVALID;
    protected int _linkQuality = LINKQUALITY_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackBluetoothLink = null;

    /**
     * Deprecated UpdateCallback for BluetoothLink
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YBluetoothLink function, String functionValue);
    }

    /**
     * TimedReportCallback for BluetoothLink
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YBluetoothLink  function, YMeasure measure);
    }
    //--- (end of YBluetoothLink definitions)


    /**
     *
     * @param func : functionid
     */
    protected YBluetoothLink(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "BluetoothLink";
        //--- (YBluetoothLink attributes initialization)
        //--- (end of YBluetoothLink attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YBluetoothLink(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YBluetoothLink implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("ownAddress")) {
            _ownAddress = json_val.getString("ownAddress");
        }
        if (json_val.has("pairingPin")) {
            _pairingPin = json_val.getString("pairingPin");
        }
        if (json_val.has("remoteAddress")) {
            _remoteAddress = json_val.getString("remoteAddress");
        }
        if (json_val.has("remoteName")) {
            _remoteName = json_val.getString("remoteName");
        }
        if (json_val.has("mute")) {
            _mute = json_val.getInt("mute") > 0 ? 1 : 0;
        }
        if (json_val.has("preAmplifier")) {
            _preAmplifier = json_val.getInt("preAmplifier");
        }
        if (json_val.has("volume")) {
            _volume = json_val.getInt("volume");
        }
        if (json_val.has("linkState")) {
            _linkState = json_val.getInt("linkState");
        }
        if (json_val.has("linkQuality")) {
            _linkQuality = json_val.getInt("linkQuality");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the MAC-48 address of the bluetooth interface, which is unique on the bluetooth network.
     *
     *  @return a string corresponding to the MAC-48 address of the bluetooth interface, which is unique on
     * the bluetooth network
     *
     * @throws YAPI_Exception on error
     */
    public String get_ownAddress() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return OWNADDRESS_INVALID;
                }
            }
            res = _ownAddress;
        }
        return res;
    }

    /**
     * Returns the MAC-48 address of the bluetooth interface, which is unique on the bluetooth network.
     *
     *  @return a string corresponding to the MAC-48 address of the bluetooth interface, which is unique on
     * the bluetooth network
     *
     * @throws YAPI_Exception on error
     */
    public String getOwnAddress() throws YAPI_Exception
    {
        return get_ownAddress();
    }

    /**
     * Returns an opaque string if a PIN code has been configured in the device to access
     * the SIM card, or an empty string if none has been configured or if the code provided
     * was rejected by the SIM card.
     *
     * @return a string corresponding to an opaque string if a PIN code has been configured in the device to access
     *         the SIM card, or an empty string if none has been configured or if the code provided
     *         was rejected by the SIM card
     *
     * @throws YAPI_Exception on error
     */
    public String get_pairingPin() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PAIRINGPIN_INVALID;
                }
            }
            res = _pairingPin;
        }
        return res;
    }

    /**
     * Returns an opaque string if a PIN code has been configured in the device to access
     * the SIM card, or an empty string if none has been configured or if the code provided
     * was rejected by the SIM card.
     *
     * @return a string corresponding to an opaque string if a PIN code has been configured in the device to access
     *         the SIM card, or an empty string if none has been configured or if the code provided
     *         was rejected by the SIM card
     *
     * @throws YAPI_Exception on error
     */
    public String getPairingPin() throws YAPI_Exception
    {
        return get_pairingPin();
    }

    /**
     * Changes the PIN code used by the module for bluetooth pairing.
     * Remember to call the saveToFlash() method of the module to save the
     * new value in the device flash.
     *
     * @param newval : a string corresponding to the PIN code used by the module for bluetooth pairing
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_pairingPin(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("pairingPin",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the PIN code used by the module for bluetooth pairing.
     * Remember to call the saveToFlash() method of the module to save the
     * new value in the device flash.
     *
     * @param newval : a string corresponding to the PIN code used by the module for bluetooth pairing
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPairingPin(String newval)  throws YAPI_Exception
    {
        return set_pairingPin(newval);
    }

    /**
     * Returns the MAC-48 address of the remote device to connect to.
     *
     * @return a string corresponding to the MAC-48 address of the remote device to connect to
     *
     * @throws YAPI_Exception on error
     */
    public String get_remoteAddress() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return REMOTEADDRESS_INVALID;
                }
            }
            res = _remoteAddress;
        }
        return res;
    }

    /**
     * Returns the MAC-48 address of the remote device to connect to.
     *
     * @return a string corresponding to the MAC-48 address of the remote device to connect to
     *
     * @throws YAPI_Exception on error
     */
    public String getRemoteAddress() throws YAPI_Exception
    {
        return get_remoteAddress();
    }

    /**
     * Changes the MAC-48 address defining which remote device to connect to.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : a string corresponding to the MAC-48 address defining which remote device to connect to
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_remoteAddress(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("remoteAddress",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the MAC-48 address defining which remote device to connect to.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : a string corresponding to the MAC-48 address defining which remote device to connect to
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setRemoteAddress(String newval)  throws YAPI_Exception
    {
        return set_remoteAddress(newval);
    }

    /**
     * Returns the bluetooth name the remote device, if found on the bluetooth network.
     *
     * @return a string corresponding to the bluetooth name the remote device, if found on the bluetooth network
     *
     * @throws YAPI_Exception on error
     */
    public String get_remoteName() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return REMOTENAME_INVALID;
                }
            }
            res = _remoteName;
        }
        return res;
    }

    /**
     * Returns the bluetooth name the remote device, if found on the bluetooth network.
     *
     * @return a string corresponding to the bluetooth name the remote device, if found on the bluetooth network
     *
     * @throws YAPI_Exception on error
     */
    public String getRemoteName() throws YAPI_Exception
    {
        return get_remoteName();
    }

    /**
     * Returns the state of the mute function.
     *
     *  @return either YBluetoothLink.MUTE_FALSE or YBluetoothLink.MUTE_TRUE, according to the state of the
     * mute function
     *
     * @throws YAPI_Exception on error
     */
    public int get_mute() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return MUTE_INVALID;
                }
            }
            res = _mute;
        }
        return res;
    }

    /**
     * Returns the state of the mute function.
     *
     *  @return either YBluetoothLink.MUTE_FALSE or YBluetoothLink.MUTE_TRUE, according to the state of the
     * mute function
     *
     * @throws YAPI_Exception on error
     */
    public int getMute() throws YAPI_Exception
    {
        return get_mute();
    }

    /**
     * Changes the state of the mute function. Remember to call the matching module
     * saveToFlash() method to save the setting permanently.
     *
     *  @param newval : either YBluetoothLink.MUTE_FALSE or YBluetoothLink.MUTE_TRUE, according to the
     * state of the mute function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_mute(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("mute",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the mute function. Remember to call the matching module
     * saveToFlash() method to save the setting permanently.
     *
     *  @param newval : either YBluetoothLink.MUTE_FALSE or YBluetoothLink.MUTE_TRUE, according to the
     * state of the mute function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMute(int newval)  throws YAPI_Exception
    {
        return set_mute(newval);
    }

    /**
     * Returns the audio pre-amplifier volume, in per cents.
     *
     * @return an integer corresponding to the audio pre-amplifier volume, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public int get_preAmplifier() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PREAMPLIFIER_INVALID;
                }
            }
            res = _preAmplifier;
        }
        return res;
    }

    /**
     * Returns the audio pre-amplifier volume, in per cents.
     *
     * @return an integer corresponding to the audio pre-amplifier volume, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public int getPreAmplifier() throws YAPI_Exception
    {
        return get_preAmplifier();
    }

    /**
     * Changes the audio pre-amplifier volume, in per cents.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the audio pre-amplifier volume, in per cents
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_preAmplifier(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("preAmplifier",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the audio pre-amplifier volume, in per cents.
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the audio pre-amplifier volume, in per cents
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPreAmplifier(int newval)  throws YAPI_Exception
    {
        return set_preAmplifier(newval);
    }

    /**
     * Returns the connected headset volume, in per cents.
     *
     * @return an integer corresponding to the connected headset volume, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public int get_volume() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLUME_INVALID;
                }
            }
            res = _volume;
        }
        return res;
    }

    /**
     * Returns the connected headset volume, in per cents.
     *
     * @return an integer corresponding to the connected headset volume, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public int getVolume() throws YAPI_Exception
    {
        return get_volume();
    }

    /**
     * Changes the connected headset volume, in per cents.
     *
     * @param newval : an integer corresponding to the connected headset volume, in per cents
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_volume(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("volume",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the connected headset volume, in per cents.
     *
     * @param newval : an integer corresponding to the connected headset volume, in per cents
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setVolume(int newval)  throws YAPI_Exception
    {
        return set_volume(newval);
    }

    /**
     * Returns the bluetooth link state.
     *
     *  @return a value among YBluetoothLink.LINKSTATE_DOWN, YBluetoothLink.LINKSTATE_FREE,
     *  YBluetoothLink.LINKSTATE_SEARCH, YBluetoothLink.LINKSTATE_EXISTS, YBluetoothLink.LINKSTATE_LINKED
     * and YBluetoothLink.LINKSTATE_PLAY corresponding to the bluetooth link state
     *
     * @throws YAPI_Exception on error
     */
    public int get_linkState() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LINKSTATE_INVALID;
                }
            }
            res = _linkState;
        }
        return res;
    }

    /**
     * Returns the bluetooth link state.
     *
     *  @return a value among YBluetoothLink.LINKSTATE_DOWN, YBluetoothLink.LINKSTATE_FREE,
     *  YBluetoothLink.LINKSTATE_SEARCH, YBluetoothLink.LINKSTATE_EXISTS, YBluetoothLink.LINKSTATE_LINKED
     * and YBluetoothLink.LINKSTATE_PLAY corresponding to the bluetooth link state
     *
     * @throws YAPI_Exception on error
     */
    public int getLinkState() throws YAPI_Exception
    {
        return get_linkState();
    }

    /**
     * Returns the bluetooth receiver signal strength, in pourcents, or 0 if no connection is established.
     *
     *  @return an integer corresponding to the bluetooth receiver signal strength, in pourcents, or 0 if
     * no connection is established
     *
     * @throws YAPI_Exception on error
     */
    public int get_linkQuality() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LINKQUALITY_INVALID;
                }
            }
            res = _linkQuality;
        }
        return res;
    }

    /**
     * Returns the bluetooth receiver signal strength, in pourcents, or 0 if no connection is established.
     *
     *  @return an integer corresponding to the bluetooth receiver signal strength, in pourcents, or 0 if
     * no connection is established
     *
     * @throws YAPI_Exception on error
     */
    public int getLinkQuality() throws YAPI_Exception
    {
        return get_linkQuality();
    }

    public String get_command() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COMMAND_INVALID;
                }
            }
            res = _command;
        }
        return res;
    }

    public int set_command(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("command",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Retrieves a Bluetooth sound controller for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the Bluetooth sound controller is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YBluetoothLink.isOnline() to test if the Bluetooth sound controller is
     * indeed online at a given time. In case of ambiguity when looking for
     * a Bluetooth sound controller by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the Bluetooth sound controller, for instance
     *         MyDevice.bluetoothLink1.
     *
     * @return a YBluetoothLink object allowing you to drive the Bluetooth sound controller.
     */
    public static YBluetoothLink FindBluetoothLink(String func)
    {
        YBluetoothLink obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YBluetoothLink) YFunction._FindFromCache("BluetoothLink", func);
            if (obj == null) {
                obj = new YBluetoothLink(func);
                YFunction._AddToCache("BluetoothLink", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a Bluetooth sound controller for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the Bluetooth sound controller is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YBluetoothLink.isOnline() to test if the Bluetooth sound controller is
     * indeed online at a given time. In case of ambiguity when looking for
     * a Bluetooth sound controller by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the Bluetooth sound controller, for instance
     *         MyDevice.bluetoothLink1.
     *
     * @return a YBluetoothLink object allowing you to drive the Bluetooth sound controller.
     */
    public static YBluetoothLink FindBluetoothLinkInContext(YAPIContext yctx,String func)
    {
        YBluetoothLink obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YBluetoothLink) YFunction._FindFromCacheInContext(yctx, "BluetoothLink", func);
            if (obj == null) {
                obj = new YBluetoothLink(yctx, func);
                YFunction._AddToCache("BluetoothLink", func, obj);
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
        _valueCallbackBluetoothLink = callback;
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
        if (_valueCallbackBluetoothLink != null) {
            _valueCallbackBluetoothLink.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Attempt to connect to the previously selected remote device.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int connect() throws YAPI_Exception
    {
        return set_command("C");
    }

    /**
     * Disconnect from the previously selected remote device.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int disconnect() throws YAPI_Exception
    {
        return set_command("D");
    }

    /**
     * Continues the enumeration of Bluetooth sound controllers started using yFirstBluetoothLink().
     * Caution: You can't make any assumption about the returned Bluetooth sound controllers order.
     * If you want to find a specific a Bluetooth sound controller, use BluetoothLink.findBluetoothLink()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YBluetoothLink object, corresponding to
     *         a Bluetooth sound controller currently online, or a null pointer
     *         if there are no more Bluetooth sound controllers to enumerate.
     */
    public YBluetoothLink nextBluetoothLink()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindBluetoothLinkInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of Bluetooth sound controllers currently accessible.
     * Use the method YBluetoothLink.nextBluetoothLink() to iterate on
     * next Bluetooth sound controllers.
     *
     * @return a pointer to a YBluetoothLink object, corresponding to
     *         the first Bluetooth sound controller currently online, or a null pointer
     *         if there are none.
     */
    public static YBluetoothLink FirstBluetoothLink()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("BluetoothLink");
        if (next_hwid == null)  return null;
        return FindBluetoothLinkInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of Bluetooth sound controllers currently accessible.
     * Use the method YBluetoothLink.nextBluetoothLink() to iterate on
     * next Bluetooth sound controllers.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YBluetoothLink object, corresponding to
     *         the first Bluetooth sound controller currently online, or a null pointer
     *         if there are none.
     */
    public static YBluetoothLink FirstBluetoothLinkInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("BluetoothLink");
        if (next_hwid == null)  return null;
        return FindBluetoothLinkInContext(yctx, next_hwid);
    }

    //--- (end of YBluetoothLink implementation)
}

