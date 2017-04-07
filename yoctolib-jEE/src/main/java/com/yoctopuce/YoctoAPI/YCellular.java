/*********************************************************************
 *
 * $Id: YCellular.java 27108 2017-04-06 22:18:22Z seb $
 *
 * Implements FindCellular(), the high-level API for Cellular functions
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

//--- (generated code: YCellular return codes)
//--- (end of generated code: YCellular return codes)
//--- (generated code: YCellular class start)
/**
 * YCellular Class: Cellular function interface
 *
 * YCellular functions provides control over cellular network parameters
 * and status for devices that are GSM-enabled.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YCellular extends YFunction
{
//--- (end of generated code: YCellular class start)
//--- (generated code: YCellular definitions)
    /**
     * invalid linkQuality value
     */
    public static final int LINKQUALITY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid cellOperator value
     */
    public static final String CELLOPERATOR_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid cellIdentifier value
     */
    public static final String CELLIDENTIFIER_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid cellType value
     */
    public static final int CELLTYPE_GPRS = 0;
    public static final int CELLTYPE_EGPRS = 1;
    public static final int CELLTYPE_WCDMA = 2;
    public static final int CELLTYPE_HSDPA = 3;
    public static final int CELLTYPE_NONE = 4;
    public static final int CELLTYPE_CDMA = 5;
    public static final int CELLTYPE_INVALID = -1;
    /**
     * invalid imsi value
     */
    public static final String IMSI_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid message value
     */
    public static final String MESSAGE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid pin value
     */
    public static final String PIN_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid lockedOperator value
     */
    public static final String LOCKEDOPERATOR_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid airplaneMode value
     */
    public static final int AIRPLANEMODE_OFF = 0;
    public static final int AIRPLANEMODE_ON = 1;
    public static final int AIRPLANEMODE_INVALID = -1;
    /**
     * invalid enableData value
     */
    public static final int ENABLEDATA_HOMENETWORK = 0;
    public static final int ENABLEDATA_ROAMING = 1;
    public static final int ENABLEDATA_NEVER = 2;
    public static final int ENABLEDATA_NEUTRALITY = 3;
    public static final int ENABLEDATA_INVALID = -1;
    /**
     * invalid apn value
     */
    public static final String APN_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid apnSecret value
     */
    public static final String APNSECRET_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid pingInterval value
     */
    public static final int PINGINTERVAL_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid dataSent value
     */
    public static final int DATASENT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid dataReceived value
     */
    public static final int DATARECEIVED_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _linkQuality = LINKQUALITY_INVALID;
    protected String _cellOperator = CELLOPERATOR_INVALID;
    protected String _cellIdentifier = CELLIDENTIFIER_INVALID;
    protected int _cellType = CELLTYPE_INVALID;
    protected String _imsi = IMSI_INVALID;
    protected String _message = MESSAGE_INVALID;
    protected String _pin = PIN_INVALID;
    protected String _lockedOperator = LOCKEDOPERATOR_INVALID;
    protected int _airplaneMode = AIRPLANEMODE_INVALID;
    protected int _enableData = ENABLEDATA_INVALID;
    protected String _apn = APN_INVALID;
    protected String _apnSecret = APNSECRET_INVALID;
    protected int _pingInterval = PINGINTERVAL_INVALID;
    protected int _dataSent = DATASENT_INVALID;
    protected int _dataReceived = DATARECEIVED_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackCellular = null;

    /**
     * Deprecated UpdateCallback for Cellular
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YCellular function, String functionValue);
    }

    /**
     * TimedReportCallback for Cellular
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YCellular  function, YMeasure measure);
    }
    //--- (end of generated code: YCellular definitions)


    /**
     *
     * @param func : functionid
     */
    protected YCellular(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Cellular";
        //--- (generated code: YCellular attributes initialization)
        //--- (end of generated code: YCellular attributes initialization)
    }

    protected YCellular(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }


    //--- (generated code: YCellular implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("linkQuality")) {
            _linkQuality = json_val.getInt("linkQuality");
        }
        if (json_val.has("cellOperator")) {
            _cellOperator = json_val.getString("cellOperator");
        }
        if (json_val.has("cellIdentifier")) {
            _cellIdentifier = json_val.getString("cellIdentifier");
        }
        if (json_val.has("cellType")) {
            _cellType = json_val.getInt("cellType");
        }
        if (json_val.has("imsi")) {
            _imsi = json_val.getString("imsi");
        }
        if (json_val.has("message")) {
            _message = json_val.getString("message");
        }
        if (json_val.has("pin")) {
            _pin = json_val.getString("pin");
        }
        if (json_val.has("lockedOperator")) {
            _lockedOperator = json_val.getString("lockedOperator");
        }
        if (json_val.has("airplaneMode")) {
            _airplaneMode = json_val.getInt("airplaneMode") > 0 ? 1 : 0;
        }
        if (json_val.has("enableData")) {
            _enableData = json_val.getInt("enableData");
        }
        if (json_val.has("apn")) {
            _apn = json_val.getString("apn");
        }
        if (json_val.has("apnSecret")) {
            _apnSecret = json_val.getString("apnSecret");
        }
        if (json_val.has("pingInterval")) {
            _pingInterval = json_val.getInt("pingInterval");
        }
        if (json_val.has("dataSent")) {
            _dataSent = json_val.getInt("dataSent");
        }
        if (json_val.has("dataReceived")) {
            _dataReceived = json_val.getInt("dataReceived");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return LINKQUALITY_INVALID;
                }
            }
            res = _linkQuality;
        }
        return res;
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
     * Returns the name of the cell operator currently in use.
     *
     * @return a string corresponding to the name of the cell operator currently in use
     *
     * @throws YAPI_Exception on error
     */
    public String get_cellOperator() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CELLOPERATOR_INVALID;
                }
            }
            res = _cellOperator;
        }
        return res;
    }

    /**
     * Returns the name of the cell operator currently in use.
     *
     * @return a string corresponding to the name of the cell operator currently in use
     *
     * @throws YAPI_Exception on error
     */
    public String getCellOperator() throws YAPI_Exception
    {
        return get_cellOperator();
    }

    /**
     * Returns the unique identifier of the cellular antenna in use: MCC, MNC, LAC and Cell ID.
     *
     *  @return a string corresponding to the unique identifier of the cellular antenna in use: MCC, MNC,
     * LAC and Cell ID
     *
     * @throws YAPI_Exception on error
     */
    public String get_cellIdentifier() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CELLIDENTIFIER_INVALID;
                }
            }
            res = _cellIdentifier;
        }
        return res;
    }

    /**
     * Returns the unique identifier of the cellular antenna in use: MCC, MNC, LAC and Cell ID.
     *
     *  @return a string corresponding to the unique identifier of the cellular antenna in use: MCC, MNC,
     * LAC and Cell ID
     *
     * @throws YAPI_Exception on error
     */
    public String getCellIdentifier() throws YAPI_Exception
    {
        return get_cellIdentifier();
    }

    /**
     * Active cellular connection type.
     *
     *  @return a value among YCellular.CELLTYPE_GPRS, YCellular.CELLTYPE_EGPRS, YCellular.CELLTYPE_WCDMA,
     * YCellular.CELLTYPE_HSDPA, YCellular.CELLTYPE_NONE and YCellular.CELLTYPE_CDMA
     *
     * @throws YAPI_Exception on error
     */
    public int get_cellType() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return CELLTYPE_INVALID;
                }
            }
            res = _cellType;
        }
        return res;
    }

    /**
     * Active cellular connection type.
     *
     *  @return a value among Y_CELLTYPE_GPRS, Y_CELLTYPE_EGPRS, Y_CELLTYPE_WCDMA, Y_CELLTYPE_HSDPA,
     * Y_CELLTYPE_NONE and Y_CELLTYPE_CDMA
     *
     * @throws YAPI_Exception on error
     */
    public int getCellType() throws YAPI_Exception
    {
        return get_cellType();
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
    public String get_imsi() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return IMSI_INVALID;
                }
            }
            res = _imsi;
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
    public String getImsi() throws YAPI_Exception
    {
        return get_imsi();
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
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MESSAGE_INVALID;
                }
            }
            res = _message;
        }
        return res;
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
    public String get_pin() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return PIN_INVALID;
                }
            }
            res = _pin;
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
    public String getPin() throws YAPI_Exception
    {
        return get_pin();
    }

    /**
     * Changes the PIN code used by the module to access the SIM card.
     * This function does not change the code on the SIM card itself, but only changes
     * the parameter used by the device to try to get access to it. If the SIM code
     * does not work immediately on first try, it will be automatically forgotten
     * and the message will be set to "Enter SIM PIN". The method should then be
     * invoked again with right correct PIN code. After three failed attempts in a row,
     * the message is changed to "Enter SIM PUK" and the SIM card PUK code must be
     * provided using method sendPUK.
     *
     * Remember to call the saveToFlash() method of the module to save the
     * new value in the device flash.
     *
     * @param newval : a string corresponding to the PIN code used by the module to access the SIM card
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_pin(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("pin",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the PIN code used by the module to access the SIM card.
     * This function does not change the code on the SIM card itself, but only changes
     * the parameter used by the device to try to get access to it. If the SIM code
     * does not work immediately on first try, it will be automatically forgotten
     * and the message will be set to "Enter SIM PIN". The method should then be
     * invoked again with right correct PIN code. After three failed attempts in a row,
     * the message is changed to "Enter SIM PUK" and the SIM card PUK code must be
     * provided using method sendPUK.
     *
     * Remember to call the saveToFlash() method of the module to save the
     * new value in the device flash.
     *
     * @param newval : a string corresponding to the PIN code used by the module to access the SIM card
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPin(String newval)  throws YAPI_Exception
    {
        return set_pin(newval);
    }

    /**
     * Returns the name of the only cell operator to use if automatic choice is disabled,
     * or an empty string if the SIM card will automatically choose among available
     * cell operators.
     *
     * @return a string corresponding to the name of the only cell operator to use if automatic choice is disabled,
     *         or an empty string if the SIM card will automatically choose among available
     *         cell operators
     *
     * @throws YAPI_Exception on error
     */
    public String get_lockedOperator() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return LOCKEDOPERATOR_INVALID;
                }
            }
            res = _lockedOperator;
        }
        return res;
    }

    /**
     * Returns the name of the only cell operator to use if automatic choice is disabled,
     * or an empty string if the SIM card will automatically choose among available
     * cell operators.
     *
     * @return a string corresponding to the name of the only cell operator to use if automatic choice is disabled,
     *         or an empty string if the SIM card will automatically choose among available
     *         cell operators
     *
     * @throws YAPI_Exception on error
     */
    public String getLockedOperator() throws YAPI_Exception
    {
        return get_lockedOperator();
    }

    /**
     * Changes the name of the cell operator to be used. If the name is an empty
     * string, the choice will be made automatically based on the SIM card. Otherwise,
     * the selected operator is the only one that will be used.
     *
     * @param newval : a string corresponding to the name of the cell operator to be used
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_lockedOperator(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("lockedOperator",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the name of the cell operator to be used. If the name is an empty
     * string, the choice will be made automatically based on the SIM card. Otherwise,
     * the selected operator is the only one that will be used.
     *
     * @param newval : a string corresponding to the name of the cell operator to be used
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLockedOperator(String newval)  throws YAPI_Exception
    {
        return set_lockedOperator(newval);
    }

    /**
     * Returns true if the airplane mode is active (radio turned off).
     *
     *  @return either YCellular.AIRPLANEMODE_OFF or YCellular.AIRPLANEMODE_ON, according to true if the
     * airplane mode is active (radio turned off)
     *
     * @throws YAPI_Exception on error
     */
    public int get_airplaneMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return AIRPLANEMODE_INVALID;
                }
            }
            res = _airplaneMode;
        }
        return res;
    }

    /**
     * Returns true if the airplane mode is active (radio turned off).
     *
     *  @return either Y_AIRPLANEMODE_OFF or Y_AIRPLANEMODE_ON, according to true if the airplane mode is
     * active (radio turned off)
     *
     * @throws YAPI_Exception on error
     */
    public int getAirplaneMode() throws YAPI_Exception
    {
        return get_airplaneMode();
    }

    /**
     * Changes the activation state of airplane mode (radio turned off).
     *
     *  @param newval : either YCellular.AIRPLANEMODE_OFF or YCellular.AIRPLANEMODE_ON, according to the
     * activation state of airplane mode (radio turned off)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_airplaneMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("airplaneMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation state of airplane mode (radio turned off).
     *
     *  @param newval : either Y_AIRPLANEMODE_OFF or Y_AIRPLANEMODE_ON, according to the activation state
     * of airplane mode (radio turned off)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAirplaneMode(int newval)  throws YAPI_Exception
    {
        return set_airplaneMode(newval);
    }

    /**
     * Returns the condition for enabling IP data services (GPRS).
     * When data services are disabled, SMS are the only mean of communication.
     *
     *  @return a value among YCellular.ENABLEDATA_HOMENETWORK, YCellular.ENABLEDATA_ROAMING,
     *  YCellular.ENABLEDATA_NEVER and YCellular.ENABLEDATA_NEUTRALITY corresponding to the condition for
     * enabling IP data services (GPRS)
     *
     * @throws YAPI_Exception on error
     */
    public int get_enableData() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ENABLEDATA_INVALID;
                }
            }
            res = _enableData;
        }
        return res;
    }

    /**
     * Returns the condition for enabling IP data services (GPRS).
     * When data services are disabled, SMS are the only mean of communication.
     *
     *  @return a value among Y_ENABLEDATA_HOMENETWORK, Y_ENABLEDATA_ROAMING, Y_ENABLEDATA_NEVER and
     * Y_ENABLEDATA_NEUTRALITY corresponding to the condition for enabling IP data services (GPRS)
     *
     * @throws YAPI_Exception on error
     */
    public int getEnableData() throws YAPI_Exception
    {
        return get_enableData();
    }

    /**
     * Changes the condition for enabling IP data services (GPRS).
     * The service can be either fully deactivated, or limited to the SIM home network,
     * or enabled for all partner networks (roaming). Caution: enabling data services
     * on roaming networks may cause prohibitive communication costs !
     *
     * When data services are disabled, SMS are the only mean of communication.
     *
     *  @param newval : a value among YCellular.ENABLEDATA_HOMENETWORK, YCellular.ENABLEDATA_ROAMING,
     *  YCellular.ENABLEDATA_NEVER and YCellular.ENABLEDATA_NEUTRALITY corresponding to the condition for
     * enabling IP data services (GPRS)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_enableData(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("enableData",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the condition for enabling IP data services (GPRS).
     * The service can be either fully deactivated, or limited to the SIM home network,
     * or enabled for all partner networks (roaming). Caution: enabling data services
     * on roaming networks may cause prohibitive communication costs !
     *
     * When data services are disabled, SMS are the only mean of communication.
     *
     *  @param newval : a value among Y_ENABLEDATA_HOMENETWORK, Y_ENABLEDATA_ROAMING, Y_ENABLEDATA_NEVER
     * and Y_ENABLEDATA_NEUTRALITY corresponding to the condition for enabling IP data services (GPRS)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setEnableData(int newval)  throws YAPI_Exception
    {
        return set_enableData(newval);
    }

    /**
     * Returns the Access Point Name (APN) to be used, if needed.
     * When left blank, the APN suggested by the cell operator will be used.
     *
     * @return a string corresponding to the Access Point Name (APN) to be used, if needed
     *
     * @throws YAPI_Exception on error
     */
    public String get_apn() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return APN_INVALID;
                }
            }
            res = _apn;
        }
        return res;
    }

    /**
     * Returns the Access Point Name (APN) to be used, if needed.
     * When left blank, the APN suggested by the cell operator will be used.
     *
     * @return a string corresponding to the Access Point Name (APN) to be used, if needed
     *
     * @throws YAPI_Exception on error
     */
    public String getApn() throws YAPI_Exception
    {
        return get_apn();
    }

    /**
     * Returns the Access Point Name (APN) to be used, if needed.
     * When left blank, the APN suggested by the cell operator will be used.
     *
     * @param newval : a string
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_apn(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("apn",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Returns the Access Point Name (APN) to be used, if needed.
     * When left blank, the APN suggested by the cell operator will be used.
     *
     * @param newval : a string
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setApn(String newval)  throws YAPI_Exception
    {
        return set_apn(newval);
    }

    /**
     * Returns an opaque string if APN authentication parameters have been configured
     * in the device, or an empty string otherwise.
     * To configure these parameters, use set_apnAuth().
     *
     * @return a string corresponding to an opaque string if APN authentication parameters have been configured
     *         in the device, or an empty string otherwise
     *
     * @throws YAPI_Exception on error
     */
    public String get_apnSecret() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return APNSECRET_INVALID;
                }
            }
            res = _apnSecret;
        }
        return res;
    }

    /**
     * Returns an opaque string if APN authentication parameters have been configured
     * in the device, or an empty string otherwise.
     * To configure these parameters, use set_apnAuth().
     *
     * @return a string corresponding to an opaque string if APN authentication parameters have been configured
     *         in the device, or an empty string otherwise
     *
     * @throws YAPI_Exception on error
     */
    public String getApnSecret() throws YAPI_Exception
    {
        return get_apnSecret();
    }

    public int set_apnSecret(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("apnSecret",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Returns the automated connectivity check interval, in seconds.
     *
     * @return an integer corresponding to the automated connectivity check interval, in seconds
     *
     * @throws YAPI_Exception on error
     */
    public int get_pingInterval() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return PINGINTERVAL_INVALID;
                }
            }
            res = _pingInterval;
        }
        return res;
    }

    /**
     * Returns the automated connectivity check interval, in seconds.
     *
     * @return an integer corresponding to the automated connectivity check interval, in seconds
     *
     * @throws YAPI_Exception on error
     */
    public int getPingInterval() throws YAPI_Exception
    {
        return get_pingInterval();
    }

    /**
     * Changes the automated connectivity check interval, in seconds.
     *
     * @param newval : an integer corresponding to the automated connectivity check interval, in seconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_pingInterval(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("pingInterval",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the automated connectivity check interval, in seconds.
     *
     * @param newval : an integer corresponding to the automated connectivity check interval, in seconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPingInterval(int newval)  throws YAPI_Exception
    {
        return set_pingInterval(newval);
    }

    /**
     * Returns the number of bytes sent so far.
     *
     * @return an integer corresponding to the number of bytes sent so far
     *
     * @throws YAPI_Exception on error
     */
    public int get_dataSent() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return DATASENT_INVALID;
                }
            }
            res = _dataSent;
        }
        return res;
    }

    /**
     * Returns the number of bytes sent so far.
     *
     * @return an integer corresponding to the number of bytes sent so far
     *
     * @throws YAPI_Exception on error
     */
    public int getDataSent() throws YAPI_Exception
    {
        return get_dataSent();
    }

    /**
     * Changes the value of the outgoing data counter.
     *
     * @param newval : an integer corresponding to the value of the outgoing data counter
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_dataSent(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("dataSent",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the value of the outgoing data counter.
     *
     * @param newval : an integer corresponding to the value of the outgoing data counter
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDataSent(int newval)  throws YAPI_Exception
    {
        return set_dataSent(newval);
    }

    /**
     * Returns the number of bytes received so far.
     *
     * @return an integer corresponding to the number of bytes received so far
     *
     * @throws YAPI_Exception on error
     */
    public int get_dataReceived() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return DATARECEIVED_INVALID;
                }
            }
            res = _dataReceived;
        }
        return res;
    }

    /**
     * Returns the number of bytes received so far.
     *
     * @return an integer corresponding to the number of bytes received so far
     *
     * @throws YAPI_Exception on error
     */
    public int getDataReceived() throws YAPI_Exception
    {
        return get_dataReceived();
    }

    /**
     * Changes the value of the incoming data counter.
     *
     * @param newval : an integer corresponding to the value of the incoming data counter
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_dataReceived(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("dataReceived",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the value of the incoming data counter.
     *
     * @param newval : an integer corresponding to the value of the incoming data counter
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDataReceived(int newval)  throws YAPI_Exception
    {
        return set_dataReceived(newval);
    }

    public String get_command() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
     * Retrieves a cellular interface for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the cellular interface is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCellular.isOnline() to test if the cellular interface is
     * indeed online at a given time. In case of ambiguity when looking for
     * a cellular interface by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the cellular interface
     *
     * @return a YCellular object allowing you to drive the cellular interface.
     */
    public static YCellular FindCellular(String func)
    {
        YCellular obj;
        synchronized (YAPI.class) {
            obj = (YCellular) YFunction._FindFromCache("Cellular", func);
            if (obj == null) {
                obj = new YCellular(func);
                YFunction._AddToCache("Cellular", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a cellular interface for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the cellular interface is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YCellular.isOnline() to test if the cellular interface is
     * indeed online at a given time. In case of ambiguity when looking for
     * a cellular interface by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the cellular interface
     *
     * @return a YCellular object allowing you to drive the cellular interface.
     */
    public static YCellular FindCellularInContext(YAPIContext yctx,String func)
    {
        YCellular obj;
        synchronized (yctx) {
            obj = (YCellular) YFunction._FindFromCacheInContext(yctx, "Cellular", func);
            if (obj == null) {
                obj = new YCellular(yctx, func);
                YFunction._AddToCache("Cellular", func, obj);
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
        _valueCallbackCellular = callback;
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
        if (_valueCallbackCellular != null) {
            _valueCallbackCellular.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Sends a PUK code to unlock the SIM card after three failed PIN code attempts, and
     * setup a new PIN into the SIM card. Only ten consecutives tentatives are permitted:
     * after that, the SIM card will be blocked permanently without any mean of recovery
     * to use it again. Note that after calling this method, you have usually to invoke
     * method set_pin() to tell the YoctoHub which PIN to use in the future.
     *
     * @param puk : the SIM PUK code
     * @param newPin : new PIN code to configure into the SIM card
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int sendPUK(String puk,String newPin) throws YAPI_Exception
    {
        String gsmMsg;
        gsmMsg = get_message();
        //noinspection DoubleNegation
        if (!((gsmMsg).substring(0, 13).equals("Enter SIM PUK"))) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT,  "PUK not expected at this time");}
        if (newPin.equals("")) {
            return set_command(String.format(Locale.US, "AT+CPIN=%s,0000;+CLCK=SC,0,0000",puk));
        }
        return set_command(String.format(Locale.US, "AT+CPIN=%s,%s",puk,newPin));
    }

    /**
     * Configure authentication parameters to connect to the APN. Both
     * PAP and CHAP authentication are supported.
     *
     * @param username : APN username
     * @param password : APN password
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_apnAuth(String username,String password) throws YAPI_Exception
    {
        return set_apnSecret(String.format(Locale.US, "%s,%s",username,password));
    }

    /**
     * Clear the transmitted data counters.
     *
     * @return YAPI.SUCCESS when the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int clearDataCounters() throws YAPI_Exception
    {
        int retcode;
        
        retcode = set_dataReceived(0);
        if (retcode != YAPI.SUCCESS) {
            return retcode;
        }
        retcode = set_dataSent(0);
        return retcode;
    }

    /**
     * Sends an AT command to the GSM module and returns the command output.
     * The command will only execute when the GSM module is in standard
     * command state, and should leave it in the exact same state.
     * Use this function with great care !
     *
     * @param cmd : the AT command to execute, like for instance: "+CCLK?".
     *
     * @return a string with the result of the commands. Empty lines are
     *         automatically removed from the output.
     */
    public String _AT(String cmd) throws YAPI_Exception
    {
        int chrPos;
        int cmdLen;
        int waitMore;
        String res;
        byte[] buff;
        int bufflen;
        String buffstr;
        int buffstrlen;
        int idx;
        int suffixlen;
        // quote dangerous characters used in AT commands
        cmdLen = (cmd).length();
        chrPos = (cmd).indexOf("#");
        while (chrPos >= 0) {
            cmd = String.format(Locale.US, "%s%c23%s", (cmd).substring(0, chrPos), 37,(cmd).substring( chrPos+1,  chrPos+1 + cmdLen-chrPos-1));
            cmdLen = cmdLen + 2;
            chrPos = (cmd).indexOf("#");
        }
        chrPos = (cmd).indexOf("+");
        while (chrPos >= 0) {
            cmd = String.format(Locale.US, "%s%c2B%s", (cmd).substring(0, chrPos), 37,(cmd).substring( chrPos+1,  chrPos+1 + cmdLen-chrPos-1));
            cmdLen = cmdLen + 2;
            chrPos = (cmd).indexOf("+");
        }
        chrPos = (cmd).indexOf("=");
        while (chrPos >= 0) {
            cmd = String.format(Locale.US, "%s%c3D%s", (cmd).substring(0, chrPos), 37,(cmd).substring( chrPos+1,  chrPos+1 + cmdLen-chrPos-1));
            cmdLen = cmdLen + 2;
            chrPos = (cmd).indexOf("=");
        }
        cmd = String.format(Locale.US, "at.txt?cmd=%s",cmd);
        res = "";
        // max 2 minutes (each iteration may take up to 5 seconds if waiting)
        waitMore = 24;
        while (waitMore > 0) {
            buff = _download(cmd);
            bufflen = (buff).length;
            buffstr = new String(buff);
            buffstrlen = (buffstr).length();
            idx = bufflen - 1;
            while ((idx > 0) && ((buff[idx] & 0xff) != 64) && ((buff[idx] & 0xff) != 10) && ((buff[idx] & 0xff) != 13)) {
                idx = idx - 1;
            }
            if ((buff[idx] & 0xff) == 64) {
                // continuation detected
                suffixlen = bufflen - idx;
                cmd = String.format(Locale.US, "at.txt?cmd=%s",(buffstr).substring( buffstrlen - suffixlen,  buffstrlen - suffixlen + suffixlen));
                buffstr = (buffstr).substring(0, buffstrlen - suffixlen);
                waitMore = waitMore - 1;
            } else {
                // request complete
                waitMore = 0;
            }
            res = String.format(Locale.US, "%s%s", res,buffstr);
        }
        return res;
    }

    /**
     * Returns the list detected cell operators in the neighborhood.
     * This function will typically take between 30 seconds to 1 minute to
     * return. Note that any SIM card can usually only connect to specific
     * operators. All networks returned by this function might therefore
     * not be available for connection.
     *
     * @return a list of string (cell operator names).
     */
    public ArrayList<String> get_availableOperators() throws YAPI_Exception
    {
        String cops;
        int idx;
        int slen;
        ArrayList<String> res = new ArrayList<>();
        
        cops = _AT("+COPS=?");
        slen = (cops).length();
        res.clear();
        idx = (cops).indexOf("(");
        while (idx >= 0) {
            slen = slen - (idx+1);
            cops = (cops).substring( idx+1,  idx+1 + slen);
            idx = (cops).indexOf("\"");
            if (idx > 0) {
                slen = slen - (idx+1);
                cops = (cops).substring( idx+1,  idx+1 + slen);
                idx = (cops).indexOf("\"");
                if (idx > 0) {
                    res.add((cops).substring(0, idx));
                }
            }
            idx = (cops).indexOf("(");
        }
        return res;
    }

    /**
     * Returns a list of nearby cellular antennas, as required for quick
     * geolocation of the device. The first cell listed is the serving
     * cell, and the next ones are the neighboor cells reported by the
     * serving cell.
     *
     * @return a list of YCellRecords.
     */
    public ArrayList<YCellRecord> quickCellSurvey() throws YAPI_Exception
    {
        String moni;
        ArrayList<String> recs = new ArrayList<>();
        int llen;
        String mccs;
        int mcc;
        String mncs;
        int mnc;
        int lac;
        int cellId;
        String dbms;
        int dbm;
        String tads;
        int tad;
        String oper;
        ArrayList<YCellRecord> res = new ArrayList<>();
        
        moni = _AT("+CCED=0;#MONI=7;#MONI");
        mccs = (moni).substring(7, 7 + 3);
        if ((mccs).substring(0, 1).equals("0")) {
            mccs = (mccs).substring(1, 1 + 2);
        }
        if ((mccs).substring(0, 1).equals("0")) {
            mccs = (mccs).substring(1, 1 + 1);
        }
        mcc = YAPIContext._atoi(mccs);
        mncs = (moni).substring(11, 11 + 3);
        if ((mncs).substring(2, 2 + 1).equals(",")) {
            mncs = (mncs).substring(0, 2);
        }
        if ((mncs).substring(0, 1).equals("0")) {
            mncs = (mncs).substring(1, 1 + (mncs).length()-1);
        }
        mnc = YAPIContext._atoi(mncs);
        recs = new ArrayList<>(Arrays.asList(moni.split("#")));
        // process each line in turn
        res.clear();
        for (String ii:recs) {
            llen = (ii).length() - 2;
            if (llen >= 44) {
                if ((ii).substring(41, 41 + 3).equals("dbm")) {
                    lac = Integer.valueOf((ii).substring(16, 16 + 4),16);
                    cellId = Integer.valueOf((ii).substring(23, 23 + 4),16);
                    dbms = (ii).substring(37, 37 + 4);
                    if ((dbms).substring(0, 1).equals(" ")) {
                        dbms = (dbms).substring(1, 1 + 3);
                    }
                    dbm = YAPIContext._atoi(dbms);
                    if (llen > 66) {
                        tads = (ii).substring(54, 54 + 2);
                        if ((tads).substring(0, 1).equals(" ")) {
                            tads = (tads).substring(1, 1 + 3);
                        }
                        tad = YAPIContext._atoi(tads);
                        oper = (ii).substring(66, 66 + llen-66);
                    } else {
                        tad = -1;
                        oper = "";
                    }
                    if (lac < 65535) {
                        res.add(new YCellRecord(mcc, mnc, lac, cellId, dbm, tad, oper));
                    }
                }
            }
        }
        return res;
    }

    /**
     * Continues the enumeration of cellular interfaces started using yFirstCellular().
     *
     * @return a pointer to a YCellular object, corresponding to
     *         a cellular interface currently online, or a null pointer
     *         if there are no more cellular interfaces to enumerate.
     */
    public YCellular nextCellular()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindCellularInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of cellular interfaces currently accessible.
     * Use the method YCellular.nextCellular() to iterate on
     * next cellular interfaces.
     *
     * @return a pointer to a YCellular object, corresponding to
     *         the first cellular interface currently online, or a null pointer
     *         if there are none.
     */
    public static YCellular FirstCellular()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Cellular");
        if (next_hwid == null)  return null;
        return FindCellularInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of cellular interfaces currently accessible.
     * Use the method YCellular.nextCellular() to iterate on
     * next cellular interfaces.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YCellular object, corresponding to
     *         the first cellular interface currently online, or a null pointer
     *         if there are none.
     */
    public static YCellular FirstCellularInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Cellular");
        if (next_hwid == null)  return null;
        return FindCellularInContext(yctx, next_hwid);
    }

    //--- (end of generated code: YCellular implementation)
}

