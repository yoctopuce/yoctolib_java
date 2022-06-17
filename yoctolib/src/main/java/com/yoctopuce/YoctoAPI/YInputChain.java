/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindInputChain(), the high-level API for InputChain functions
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
import java.util.ArrayList;
import java.util.Locale;
import java.util.Arrays;

//--- (YInputChain return codes)
//--- (end of YInputChain return codes)
//--- (YInputChain yapiwrapper)
//--- (end of YInputChain yapiwrapper)
//--- (YInputChain class start)
/**
 * YInputChain Class: InputChain function interface
 *
 * The YInputChain class provides access to separate
 * digital inputs connected in a chain.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YInputChain extends YFunction
{
//--- (end of YInputChain class start)
//--- (YInputChain definitions)
    /**
     * invalid expectedNodes value
     */
    public static final int EXPECTEDNODES_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid detectedNodes value
     */
    public static final int DETECTEDNODES_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid loopbackTest value
     */
    public static final int LOOPBACKTEST_OFF = 0;
    public static final int LOOPBACKTEST_ON = 1;
    public static final int LOOPBACKTEST_INVALID = -1;
    /**
     * invalid refreshRate value
     */
    public static final int REFRESHRATE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid bitChain1 value
     */
    public static final String BITCHAIN1_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid bitChain2 value
     */
    public static final String BITCHAIN2_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid bitChain3 value
     */
    public static final String BITCHAIN3_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid bitChain4 value
     */
    public static final String BITCHAIN4_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid bitChain5 value
     */
    public static final String BITCHAIN5_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid bitChain6 value
     */
    public static final String BITCHAIN6_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid bitChain7 value
     */
    public static final String BITCHAIN7_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid watchdogPeriod value
     */
    public static final int WATCHDOGPERIOD_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid chainDiags value
     */
    public static final int CHAINDIAGS_INVALID = YAPI.INVALID_UINT;
    protected int _expectedNodes = EXPECTEDNODES_INVALID;
    protected int _detectedNodes = DETECTEDNODES_INVALID;
    protected int _loopbackTest = LOOPBACKTEST_INVALID;
    protected int _refreshRate = REFRESHRATE_INVALID;
    protected String _bitChain1 = BITCHAIN1_INVALID;
    protected String _bitChain2 = BITCHAIN2_INVALID;
    protected String _bitChain3 = BITCHAIN3_INVALID;
    protected String _bitChain4 = BITCHAIN4_INVALID;
    protected String _bitChain5 = BITCHAIN5_INVALID;
    protected String _bitChain6 = BITCHAIN6_INVALID;
    protected String _bitChain7 = BITCHAIN7_INVALID;
    protected int _watchdogPeriod = WATCHDOGPERIOD_INVALID;
    protected int _chainDiags = CHAINDIAGS_INVALID;
    protected UpdateCallback _valueCallbackInputChain = null;
    protected YEventCallback _eventCallback;
    protected int _prevPos = 0;
    protected int _eventPos = 0;
    protected int _eventStamp = 0;
    protected ArrayList<String> _eventChains = new ArrayList<>();

    /**
     * Deprecated UpdateCallback for InputChain
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YInputChain function, String functionValue);
    }

    /**
     * TimedReportCallback for InputChain
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YInputChain  function, YMeasure measure);
    }
    /**
     * Specialized event Callback for InputChain
     */
    public interface YEventCallback
    {
        void eventCallback(YInputChain inputChain, int timestampr, String evtType, String eventData, String eventChange);
    }

    private UpdateCallback yInternalEventCallback = new UpdateCallback()
    {
        @Override
        public void yNewValue(YInputChain obj, String value)
        {
            try {
                obj._internalEventHandler(value);
            } catch (YAPI_Exception e) {
                e.printStackTrace();
            }
        }
    };

    //--- (end of YInputChain definitions)


    /**
     *
     * @param func : functionid
     */
    protected YInputChain(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "InputChain";
        //--- (YInputChain attributes initialization)
        //--- (end of YInputChain attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YInputChain(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YInputChain implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("expectedNodes")) {
            _expectedNodes = json_val.getInt("expectedNodes");
        }
        if (json_val.has("detectedNodes")) {
            _detectedNodes = json_val.getInt("detectedNodes");
        }
        if (json_val.has("loopbackTest")) {
            _loopbackTest = json_val.getInt("loopbackTest") > 0 ? 1 : 0;
        }
        if (json_val.has("refreshRate")) {
            _refreshRate = json_val.getInt("refreshRate");
        }
        if (json_val.has("bitChain1")) {
            _bitChain1 = json_val.getString("bitChain1");
        }
        if (json_val.has("bitChain2")) {
            _bitChain2 = json_val.getString("bitChain2");
        }
        if (json_val.has("bitChain3")) {
            _bitChain3 = json_val.getString("bitChain3");
        }
        if (json_val.has("bitChain4")) {
            _bitChain4 = json_val.getString("bitChain4");
        }
        if (json_val.has("bitChain5")) {
            _bitChain5 = json_val.getString("bitChain5");
        }
        if (json_val.has("bitChain6")) {
            _bitChain6 = json_val.getString("bitChain6");
        }
        if (json_val.has("bitChain7")) {
            _bitChain7 = json_val.getString("bitChain7");
        }
        if (json_val.has("watchdogPeriod")) {
            _watchdogPeriod = json_val.getInt("watchdogPeriod");
        }
        if (json_val.has("chainDiags")) {
            _chainDiags = json_val.getInt("chainDiags");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the number of nodes expected in the chain.
     *
     * @return an integer corresponding to the number of nodes expected in the chain
     *
     * @throws YAPI_Exception on error
     */
    public int get_expectedNodes() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return EXPECTEDNODES_INVALID;
                }
            }
            res = _expectedNodes;
        }
        return res;
    }

    /**
     * Returns the number of nodes expected in the chain.
     *
     * @return an integer corresponding to the number of nodes expected in the chain
     *
     * @throws YAPI_Exception on error
     */
    public int getExpectedNodes() throws YAPI_Exception
    {
        return get_expectedNodes();
    }

    /**
     * Changes the number of nodes expected in the chain.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the number of nodes expected in the chain
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_expectedNodes(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("expectedNodes",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of nodes expected in the chain.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the number of nodes expected in the chain
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setExpectedNodes(int newval)  throws YAPI_Exception
    {
        return set_expectedNodes(newval);
    }

    /**
     * Returns the number of nodes detected in the chain.
     *
     * @return an integer corresponding to the number of nodes detected in the chain
     *
     * @throws YAPI_Exception on error
     */
    public int get_detectedNodes() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DETECTEDNODES_INVALID;
                }
            }
            res = _detectedNodes;
        }
        return res;
    }

    /**
     * Returns the number of nodes detected in the chain.
     *
     * @return an integer corresponding to the number of nodes detected in the chain
     *
     * @throws YAPI_Exception on error
     */
    public int getDetectedNodes() throws YAPI_Exception
    {
        return get_detectedNodes();
    }

    /**
     * Returns the activation state of the exhaustive chain connectivity test.
     * The connectivity test requires a cable connecting the end of the chain
     * to the loopback test connector.
     *
     *  @return either YInputChain.LOOPBACKTEST_OFF or YInputChain.LOOPBACKTEST_ON, according to the
     * activation state of the exhaustive chain connectivity test
     *
     * @throws YAPI_Exception on error
     */
    public int get_loopbackTest() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LOOPBACKTEST_INVALID;
                }
            }
            res = _loopbackTest;
        }
        return res;
    }

    /**
     * Returns the activation state of the exhaustive chain connectivity test.
     * The connectivity test requires a cable connecting the end of the chain
     * to the loopback test connector.
     *
     *  @return either YInputChain.LOOPBACKTEST_OFF or YInputChain.LOOPBACKTEST_ON, according to the
     * activation state of the exhaustive chain connectivity test
     *
     * @throws YAPI_Exception on error
     */
    public int getLoopbackTest() throws YAPI_Exception
    {
        return get_loopbackTest();
    }

    /**
     * Changes the activation state of the exhaustive chain connectivity test.
     * The connectivity test requires a cable connecting the end of the chain
     * to the loopback test connector.
     *
     *  @param newval : either YInputChain.LOOPBACKTEST_OFF or YInputChain.LOOPBACKTEST_ON, according to
     * the activation state of the exhaustive chain connectivity test
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_loopbackTest(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("loopbackTest",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation state of the exhaustive chain connectivity test.
     * The connectivity test requires a cable connecting the end of the chain
     * to the loopback test connector.
     *
     *  @param newval : either YInputChain.LOOPBACKTEST_OFF or YInputChain.LOOPBACKTEST_ON, according to
     * the activation state of the exhaustive chain connectivity test
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLoopbackTest(int newval)  throws YAPI_Exception
    {
        return set_loopbackTest(newval);
    }

    /**
     * Returns the desired refresh rate, measured in Hz.
     * The higher the refresh rate is set, the higher the
     * communication speed on the chain will be.
     *
     * @return an integer corresponding to the desired refresh rate, measured in Hz
     *
     * @throws YAPI_Exception on error
     */
    public int get_refreshRate() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return REFRESHRATE_INVALID;
                }
            }
            res = _refreshRate;
        }
        return res;
    }

    /**
     * Returns the desired refresh rate, measured in Hz.
     * The higher the refresh rate is set, the higher the
     * communication speed on the chain will be.
     *
     * @return an integer corresponding to the desired refresh rate, measured in Hz
     *
     * @throws YAPI_Exception on error
     */
    public int getRefreshRate() throws YAPI_Exception
    {
        return get_refreshRate();
    }

    /**
     * Changes the desired refresh rate, measured in Hz.
     * The higher the refresh rate is set, the higher the
     * communication speed on the chain will be.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the desired refresh rate, measured in Hz
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_refreshRate(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("refreshRate",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the desired refresh rate, measured in Hz.
     * The higher the refresh rate is set, the higher the
     * communication speed on the chain will be.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the desired refresh rate, measured in Hz
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setRefreshRate(int newval)  throws YAPI_Exception
    {
        return set_refreshRate(newval);
    }

    /**
     * Returns the state of input 1 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 1 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String get_bitChain1() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BITCHAIN1_INVALID;
                }
            }
            res = _bitChain1;
        }
        return res;
    }

    /**
     * Returns the state of input 1 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 1 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String getBitChain1() throws YAPI_Exception
    {
        return get_bitChain1();
    }

    /**
     * Returns the state of input 2 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 2 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String get_bitChain2() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BITCHAIN2_INVALID;
                }
            }
            res = _bitChain2;
        }
        return res;
    }

    /**
     * Returns the state of input 2 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 2 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String getBitChain2() throws YAPI_Exception
    {
        return get_bitChain2();
    }

    /**
     * Returns the state of input 3 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 3 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String get_bitChain3() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BITCHAIN3_INVALID;
                }
            }
            res = _bitChain3;
        }
        return res;
    }

    /**
     * Returns the state of input 3 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 3 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String getBitChain3() throws YAPI_Exception
    {
        return get_bitChain3();
    }

    /**
     * Returns the state of input 4 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 4 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String get_bitChain4() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BITCHAIN4_INVALID;
                }
            }
            res = _bitChain4;
        }
        return res;
    }

    /**
     * Returns the state of input 4 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 4 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String getBitChain4() throws YAPI_Exception
    {
        return get_bitChain4();
    }

    /**
     * Returns the state of input 5 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 5 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String get_bitChain5() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BITCHAIN5_INVALID;
                }
            }
            res = _bitChain5;
        }
        return res;
    }

    /**
     * Returns the state of input 5 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 5 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String getBitChain5() throws YAPI_Exception
    {
        return get_bitChain5();
    }

    /**
     * Returns the state of input 6 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 6 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String get_bitChain6() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BITCHAIN6_INVALID;
                }
            }
            res = _bitChain6;
        }
        return res;
    }

    /**
     * Returns the state of input 6 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 6 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String getBitChain6() throws YAPI_Exception
    {
        return get_bitChain6();
    }

    /**
     * Returns the state of input 7 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 7 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String get_bitChain7() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return BITCHAIN7_INVALID;
                }
            }
            res = _bitChain7;
        }
        return res;
    }

    /**
     * Returns the state of input 7 for all nodes of the input chain,
     * as a hexadecimal string. The node nearest to the controller
     * is the lowest bit of the result.
     *
     * @return a string corresponding to the state of input 7 for all nodes of the input chain,
     *         as a hexadecimal string
     *
     * @throws YAPI_Exception on error
     */
    public String getBitChain7() throws YAPI_Exception
    {
        return get_bitChain7();
    }

    /**
     * Returns the wait time in seconds before triggering an inactivity
     * timeout error.
     *
     * @return an integer corresponding to the wait time in seconds before triggering an inactivity
     *         timeout error
     *
     * @throws YAPI_Exception on error
     */
    public int get_watchdogPeriod() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return WATCHDOGPERIOD_INVALID;
                }
            }
            res = _watchdogPeriod;
        }
        return res;
    }

    /**
     * Returns the wait time in seconds before triggering an inactivity
     * timeout error.
     *
     * @return an integer corresponding to the wait time in seconds before triggering an inactivity
     *         timeout error
     *
     * @throws YAPI_Exception on error
     */
    public int getWatchdogPeriod() throws YAPI_Exception
    {
        return get_watchdogPeriod();
    }

    /**
     * Changes the wait time in seconds before triggering an inactivity
     * timeout error. Remember to call the saveToFlash() method
     * of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the wait time in seconds before triggering an inactivity
     *         timeout error
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_watchdogPeriod(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("watchdogPeriod",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the wait time in seconds before triggering an inactivity
     * timeout error. Remember to call the saveToFlash() method
     * of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the wait time in seconds before triggering an inactivity
     *         timeout error
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setWatchdogPeriod(int newval)  throws YAPI_Exception
    {
        return set_watchdogPeriod(newval);
    }

    /**
     * Returns the controller state diagnostics. Bit 0 indicates a chain length
     * error, bit 1 indicates an inactivity timeout and bit 2 indicates
     * a loopback test failure.
     *
     * @return an integer corresponding to the controller state diagnostics
     *
     * @throws YAPI_Exception on error
     */
    public int get_chainDiags() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CHAINDIAGS_INVALID;
                }
            }
            res = _chainDiags;
        }
        return res;
    }

    /**
     * Returns the controller state diagnostics. Bit 0 indicates a chain length
     * error, bit 1 indicates an inactivity timeout and bit 2 indicates
     * a loopback test failure.
     *
     * @return an integer corresponding to the controller state diagnostics
     *
     * @throws YAPI_Exception on error
     */
    public int getChainDiags() throws YAPI_Exception
    {
        return get_chainDiags();
    }

    /**
     * Retrieves a digital input chain for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the digital input chain is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YInputChain.isOnline() to test if the digital input chain is
     * indeed online at a given time. In case of ambiguity when looking for
     * a digital input chain by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the digital input chain, for instance
     *         MyDevice.inputChain.
     *
     * @return a YInputChain object allowing you to drive the digital input chain.
     */
    public static YInputChain FindInputChain(String func)
    {
        YInputChain obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YInputChain) YFunction._FindFromCache("InputChain", func);
            if (obj == null) {
                obj = new YInputChain(func);
                YFunction._AddToCache("InputChain", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a digital input chain for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the digital input chain is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YInputChain.isOnline() to test if the digital input chain is
     * indeed online at a given time. In case of ambiguity when looking for
     * a digital input chain by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the digital input chain, for instance
     *         MyDevice.inputChain.
     *
     * @return a YInputChain object allowing you to drive the digital input chain.
     */
    public static YInputChain FindInputChainInContext(YAPIContext yctx,String func)
    {
        YInputChain obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YInputChain) YFunction._FindFromCacheInContext(yctx, "InputChain", func);
            if (obj == null) {
                obj = new YInputChain(yctx, func);
                YFunction._AddToCache("InputChain", func, obj);
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
        _valueCallbackInputChain = callback;
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
        if (_valueCallbackInputChain != null) {
            _valueCallbackInputChain.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Resets the application watchdog countdown.
     * If you have setup a non-zero watchdogPeriod, you should
     * call this function on a regular basis to prevent the application
     * inactivity error to be triggered.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int resetWatchdog() throws YAPI_Exception
    {
        return set_watchdogPeriod(-1);
    }

    /**
     * Returns a string with last events observed on the digital input chain.
     * This method return only events that are still buffered in the device memory.
     *
     * @return a string with last events observed (one per line).
     *
     * @throws YAPI_Exception on error
     */
    public String get_lastEvents() throws YAPI_Exception
    {
        byte[] content = new byte[0];

        content = _download("events.txt");
        return new String(content);
    }

    /**
     * Registers a callback function to be called each time that an event is detected on the
     * input chain.The callback is invoked only during the execution of
     * ySleep or yHandleEvents. This provides control over the time when
     * the callback is triggered. For good responsiveness, remember to call one of these
     * two functions periodically. To unregister a callback, pass a null pointer as argument.
     *
     * @param callback : the callback function to call, or a null pointer.
     *         The callback function should take four arguments:
     *         the YInputChain object that emitted the event, the
     *         UTC timestamp of the event, a character string describing
     *         the type of event and a character string with the event data.
     * @throws YAPI_Exception on error
     */
    public int registerEventCallback(YEventCallback callback) throws YAPI_Exception
    {
        if (callback != null) {
            registerValueCallback(yInternalEventCallback);
        } else {
            registerValueCallback((UpdateCallback) null);
        }
        // register user callback AFTER the internal pseudo-event,
        // to make sure we start with future events only
        _eventCallback = callback;
        return 0;
    }

    public int _internalEventHandler(String cbpos) throws YAPI_Exception
    {
        int newPos;
        String url;
        byte[] content = new byte[0];
        String contentStr;
        ArrayList<String> eventArr = new ArrayList<>();
        int arrLen;
        String lenStr;
        int arrPos;
        String eventStr;
        int eventLen;
        String hexStamp;
        int typePos;
        int dataPos;
        int evtStamp;
        String evtType;
        String evtData;
        String evtChange;
        int chainIdx;
        newPos = YAPIContext._atoi(cbpos);
        if (newPos < _prevPos) {
            _eventPos = 0;
        }
        _prevPos = newPos;
        if (newPos < _eventPos) {
            return YAPI.SUCCESS;
        }
        if (!(_eventCallback != null)) {
            // first simulated event, use it to initialize reference values
            _eventPos = newPos;
            _eventChains.clear();
            _eventChains.add(get_bitChain1());
            _eventChains.add(get_bitChain2());
            _eventChains.add(get_bitChain3());
            _eventChains.add(get_bitChain4());
            _eventChains.add(get_bitChain5());
            _eventChains.add(get_bitChain6());
            _eventChains.add(get_bitChain7());
            return YAPI.SUCCESS;
        }
        url = String.format(Locale.US, "events.txt?pos=%d",_eventPos);

        content = _download(url);
        contentStr = new String(content);
        eventArr = new ArrayList<>(Arrays.asList(contentStr.split("\n")));
        arrLen = eventArr.size();
        //noinspection DoubleNegation
        if (!(arrLen > 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "fail to download events");}
        // last element of array is the new position preceeded by '@'
        arrLen = arrLen - 1;
        lenStr = eventArr.get(arrLen);
        lenStr = (lenStr).substring( 1,  1 + (lenStr).length()-1);
        // update processed event position pointer
        _eventPos = YAPIContext._atoi(lenStr);
        // now generate callbacks for each event received
        arrPos = 0;
        while (arrPos < arrLen) {
            eventStr = eventArr.get(arrPos);
            eventLen = (eventStr).length();
            if (eventLen >= 1) {
                hexStamp = (eventStr).substring(0, 8);
                evtStamp = Integer.valueOf(hexStamp,16);
                typePos = (eventStr).indexOf(":")+1;
                if ((evtStamp >= _eventStamp) && (typePos > 8)) {
                    _eventStamp = evtStamp;
                    dataPos = (eventStr).indexOf("=")+1;
                    evtType = (eventStr).substring( typePos,  typePos + 1);
                    evtData = "";
                    evtChange = "";
                    if (dataPos > 10) {
                        evtData = (eventStr).substring( dataPos,  dataPos + (eventStr).length()-dataPos);
                        if (("1234567").indexOf(evtType) >= 0) {
                            chainIdx = YAPIContext._atoi(evtType) - 1;
                            evtChange = _strXor(evtData, _eventChains.get(chainIdx));
                            _eventChains.set( chainIdx, evtData);
                        }
                    }
                    _eventCallback.eventCallback(this, evtStamp, evtType, evtData, evtChange);
                }
            }
            arrPos = arrPos + 1;
        }
        return YAPI.SUCCESS;
    }

    public String _strXor(String a,String b)
    {
        int lenA;
        int lenB;
        String res;
        int idx;
        int digitA;
        int digitB;
        // make sure the result has the same length as first argument
        lenA = (a).length();
        lenB = (b).length();
        if (lenA > lenB) {
            res = (a).substring(0, lenA-lenB);
            a = (a).substring( lenA-lenB,  lenA-lenB + lenB);
            lenA = lenB;
        } else {
            res = "";
            b = (b).substring( lenA-lenB,  lenA-lenB + lenA);
        }
        // scan strings and compare digit by digit
        idx = 0;
        while (idx < lenA) {
            digitA = Integer.valueOf((a).substring( idx,  idx + 1),16);
            digitB = Integer.valueOf((b).substring( idx,  idx + 1),16);
            res = String.format(Locale.US, "%s%x", res,((digitA) ^ (digitB)));
            idx = idx + 1;
        }
        return res;
    }

    public ArrayList<Integer> hex2array(String hexstr)
    {
        int hexlen;
        ArrayList<Integer> res = new ArrayList<>();
        int idx;
        int digit;
        hexlen = (hexstr).length();
        res.clear();
        idx = hexlen;
        while (idx > 0) {
            idx = idx - 1;
            digit = Integer.valueOf((hexstr).substring( idx,  idx + 1),16);
            res.add(((digit) & (1)));
            res.add(((((digit) >> (1))) & (1)));
            res.add(((((digit) >> (2))) & (1)));
            res.add(((((digit) >> (3))) & (1)));
        }
        return res;
    }

    /**
     * Continues the enumeration of digital input chains started using yFirstInputChain().
     * Caution: You can't make any assumption about the returned digital input chains order.
     * If you want to find a specific a digital input chain, use InputChain.findInputChain()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YInputChain object, corresponding to
     *         a digital input chain currently online, or a null pointer
     *         if there are no more digital input chains to enumerate.
     */
    public YInputChain nextInputChain()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindInputChainInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of digital input chains currently accessible.
     * Use the method YInputChain.nextInputChain() to iterate on
     * next digital input chains.
     *
     * @return a pointer to a YInputChain object, corresponding to
     *         the first digital input chain currently online, or a null pointer
     *         if there are none.
     */
    public static YInputChain FirstInputChain()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("InputChain");
        if (next_hwid == null)  return null;
        return FindInputChainInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of digital input chains currently accessible.
     * Use the method YInputChain.nextInputChain() to iterate on
     * next digital input chains.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YInputChain object, corresponding to
     *         the first digital input chain currently online, or a null pointer
     *         if there are none.
     */
    public static YInputChain FirstInputChainInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("InputChain");
        if (next_hwid == null)  return null;
        return FindInputChainInContext(yctx, next_hwid);
    }

    //--- (end of YInputChain implementation)
}

