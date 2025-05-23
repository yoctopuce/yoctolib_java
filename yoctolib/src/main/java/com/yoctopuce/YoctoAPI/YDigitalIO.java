/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindDigitalIO(), the high-level API for DigitalIO functions
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
import java.util.Locale;

//--- (YDigitalIO return codes)
//--- (end of YDigitalIO return codes)
//--- (YDigitalIO yapiwrapper)
//--- (end of YDigitalIO yapiwrapper)
//--- (YDigitalIO class start)
/**
 *  YDigitalIO Class: digital IO port control interface, available for instance in the Yocto-IO or the
 * Yocto-Maxi-IO-V2
 *
 * The YDigitalIO class allows you drive a Yoctopuce digital input/output port.
 * It can be used to set up the direction of each channel, to read the state of each channel
 * and to switch the state of each channel configures as an output.
 * You can work on all channels at once, or one by one. Most functions
 * use a binary representation for channels where bit 0 matches channel #0 , bit 1 matches channel
 * #1 and so on. If you are not familiar with numbers binary representation, you will find more
 * information here: https://en.wikipedia.org/wiki/Binary_number#Representation. It is also possible
 * to automatically generate short pulses of a determined duration. Electrical behavior
 * of each I/O can be modified (open drain and reverse polarity).
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YDigitalIO extends YFunction
{
//--- (end of YDigitalIO class start)
//--- (YDigitalIO definitions)
    /**
     * invalid portState value
     */
    public static final int PORTSTATE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid portDirection value
     */
    public static final int PORTDIRECTION_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid portOpenDrain value
     */
    public static final int PORTOPENDRAIN_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid portPolarity value
     */
    public static final int PORTPOLARITY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid portDiags value
     */
    public static final int PORTDIAGS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid portSize value
     */
    public static final int PORTSIZE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid outputVoltage value
     */
    public static final int OUTPUTVOLTAGE_USB_5V = 0;
    public static final int OUTPUTVOLTAGE_USB_3V = 1;
    public static final int OUTPUTVOLTAGE_EXT_V = 2;
    public static final int OUTPUTVOLTAGE_INVALID = -1;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _portState = PORTSTATE_INVALID;
    protected int _portDirection = PORTDIRECTION_INVALID;
    protected int _portOpenDrain = PORTOPENDRAIN_INVALID;
    protected int _portPolarity = PORTPOLARITY_INVALID;
    protected int _portDiags = PORTDIAGS_INVALID;
    protected int _portSize = PORTSIZE_INVALID;
    protected int _outputVoltage = OUTPUTVOLTAGE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackDigitalIO = null;

    /**
     * Deprecated UpdateCallback for DigitalIO
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YDigitalIO function, String functionValue);
    }

    /**
     * TimedReportCallback for DigitalIO
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YDigitalIO  function, YMeasure measure);
    }
    //--- (end of YDigitalIO definitions)


    /**
     *
     * @param func : functionid
     */
    protected YDigitalIO(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "DigitalIO";
        //--- (YDigitalIO attributes initialization)
        //--- (end of YDigitalIO attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YDigitalIO(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YDigitalIO implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("portState")) {
            _portState = json_val.getInt("portState");
        }
        if (json_val.has("portDirection")) {
            _portDirection = json_val.getInt("portDirection");
        }
        if (json_val.has("portOpenDrain")) {
            _portOpenDrain = json_val.getInt("portOpenDrain");
        }
        if (json_val.has("portPolarity")) {
            _portPolarity = json_val.getInt("portPolarity");
        }
        if (json_val.has("portDiags")) {
            _portDiags = json_val.getInt("portDiags");
        }
        if (json_val.has("portSize")) {
            _portSize = json_val.getInt("portSize");
        }
        if (json_val.has("outputVoltage")) {
            _outputVoltage = json_val.getInt("outputVoltage");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the digital IO port state as an integer with each bit
     * representing a channel.
     * value 0 = 0b00000000 -> all channels are OFF
     * value 1 = 0b00000001 -> channel #0 is ON
     * value 2 = 0b00000010 -> channel #1 is ON
     * value 3 = 0b00000011 -> channels #0 and #1 are ON
     * value 4 = 0b00000100 -> channel #2 is ON
     * and so on...
     *
     * @return an integer corresponding to the digital IO port state as an integer with each bit
     *         representing a channel
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
     * Returns the digital IO port state as an integer with each bit
     * representing a channel.
     * value 0 = 0b00000000 -> all channels are OFF
     * value 1 = 0b00000001 -> channel #0 is ON
     * value 2 = 0b00000010 -> channel #1 is ON
     * value 3 = 0b00000011 -> channels #0 and #1 are ON
     * value 4 = 0b00000100 -> channel #2 is ON
     * and so on...
     *
     * @return an integer corresponding to the digital IO port state as an integer with each bit
     *         representing a channel
     *
     * @throws YAPI_Exception on error
     */
    public int getPortState() throws YAPI_Exception
    {
        return get_portState();
    }

    /**
     * Changes the state of all digital IO port's channels at once: the parameter
     * is an integer where each bit represents a channel, with bit 0 matching channel #0.
     * To set all channels to  0 -> 0b00000000 -> parameter = 0
     * To set channel #0 to 1 -> 0b00000001 -> parameter =  1
     * To set channel #1 to  1 -> 0b00000010 -> parameter = 2
     * To set channel #0 and #1 -> 0b00000011 -> parameter =  3
     * To set channel #2 to 1 -> 0b00000100 -> parameter =  4
     * an so on....
     * Only channels configured as outputs will be affecter, according to the value
     * configured using set_portDirection.
     *
     * @param newval : an integer corresponding to the state of all digital IO port's channels at once: the parameter
     *         is an integer where each bit represents a channel, with bit 0 matching channel #0
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_portState(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("portState",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of all digital IO port's channels at once: the parameter
     * is an integer where each bit represents a channel, with bit 0 matching channel #0.
     * To set all channels to  0 -> 0b00000000 -> parameter = 0
     * To set channel #0 to 1 -> 0b00000001 -> parameter =  1
     * To set channel #1 to  1 -> 0b00000010 -> parameter = 2
     * To set channel #0 and #1 -> 0b00000011 -> parameter =  3
     * To set channel #2 to 1 -> 0b00000100 -> parameter =  4
     * an so on....
     * Only channels configured as outputs will be affecter, according to the value
     * configured using set_portDirection.
     *
     * @param newval : an integer corresponding to the state of all digital IO port's channels at once: the parameter
     *         is an integer where each bit represents a channel, with bit 0 matching channel #0
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPortState(int newval)  throws YAPI_Exception
    {
        return set_portState(newval);
    }

    /**
     * Returns the I/O direction of all channels of the port (bitmap): 0 makes a bit an input, 1 makes it an output.
     *
     *  @return an integer corresponding to the I/O direction of all channels of the port (bitmap): 0 makes
     * a bit an input, 1 makes it an output
     *
     * @throws YAPI_Exception on error
     */
    public int get_portDirection() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PORTDIRECTION_INVALID;
                }
            }
            res = _portDirection;
        }
        return res;
    }

    /**
     * Returns the I/O direction of all channels of the port (bitmap): 0 makes a bit an input, 1 makes it an output.
     *
     *  @return an integer corresponding to the I/O direction of all channels of the port (bitmap): 0 makes
     * a bit an input, 1 makes it an output
     *
     * @throws YAPI_Exception on error
     */
    public int getPortDirection() throws YAPI_Exception
    {
        return get_portDirection();
    }

    /**
     * Changes the I/O direction of all channels of the port (bitmap): 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     *  @param newval : an integer corresponding to the I/O direction of all channels of the port (bitmap):
     * 0 makes a bit an input, 1 makes it an output
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_portDirection(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("portDirection",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the I/O direction of all channels of the port (bitmap): 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     *  @param newval : an integer corresponding to the I/O direction of all channels of the port (bitmap):
     * 0 makes a bit an input, 1 makes it an output
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPortDirection(int newval)  throws YAPI_Exception
    {
        return set_portDirection(newval);
    }

    /**
     *  Returns the electrical interface for each bit of the port. For each bit set to 0  the matching I/O
     * works in the regular,
     * intuitive way, for each bit set to 1, the I/O works in reverse mode.
     *
     * @return an integer corresponding to the electrical interface for each bit of the port
     *
     * @throws YAPI_Exception on error
     */
    public int get_portOpenDrain() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PORTOPENDRAIN_INVALID;
                }
            }
            res = _portOpenDrain;
        }
        return res;
    }

    /**
     *  Returns the electrical interface for each bit of the port. For each bit set to 0  the matching I/O
     * works in the regular,
     * intuitive way, for each bit set to 1, the I/O works in reverse mode.
     *
     * @return an integer corresponding to the electrical interface for each bit of the port
     *
     * @throws YAPI_Exception on error
     */
    public int getPortOpenDrain() throws YAPI_Exception
    {
        return get_portOpenDrain();
    }

    /**
     * Changes the electrical interface for each bit of the port. 0 makes a bit a regular input/output, 1 makes
     * it an open-drain (open-collector) input/output. Remember to call the
     * saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     * @param newval : an integer corresponding to the electrical interface for each bit of the port
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_portOpenDrain(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("portOpenDrain",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the electrical interface for each bit of the port. 0 makes a bit a regular input/output, 1 makes
     * it an open-drain (open-collector) input/output. Remember to call the
     * saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     * @param newval : an integer corresponding to the electrical interface for each bit of the port
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPortOpenDrain(int newval)  throws YAPI_Exception
    {
        return set_portOpenDrain(newval);
    }

    /**
     * Returns the polarity of all the bits of the port.  For each bit set to 0, the matching I/O works the regular,
     * intuitive way; for each bit set to 1, the I/O works in reverse mode.
     *
     * @return an integer corresponding to the polarity of all the bits of the port
     *
     * @throws YAPI_Exception on error
     */
    public int get_portPolarity() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PORTPOLARITY_INVALID;
                }
            }
            res = _portPolarity;
        }
        return res;
    }

    /**
     * Returns the polarity of all the bits of the port.  For each bit set to 0, the matching I/O works the regular,
     * intuitive way; for each bit set to 1, the I/O works in reverse mode.
     *
     * @return an integer corresponding to the polarity of all the bits of the port
     *
     * @throws YAPI_Exception on error
     */
    public int getPortPolarity() throws YAPI_Exception
    {
        return get_portPolarity();
    }

    /**
     * Changes the polarity of all the bits of the port: For each bit set to 0, the matching I/O works the regular,
     * intuitive way; for each bit set to 1, the I/O works in reverse mode.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     *
     *  @param newval : an integer corresponding to the polarity of all the bits of the port: For each bit
     * set to 0, the matching I/O works the regular,
     *         intuitive way; for each bit set to 1, the I/O works in reverse mode
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_portPolarity(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("portPolarity",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the polarity of all the bits of the port: For each bit set to 0, the matching I/O works the regular,
     * intuitive way; for each bit set to 1, the I/O works in reverse mode.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     *
     *  @param newval : an integer corresponding to the polarity of all the bits of the port: For each bit
     * set to 0, the matching I/O works the regular,
     *         intuitive way; for each bit set to 1, the I/O works in reverse mode
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPortPolarity(int newval)  throws YAPI_Exception
    {
        return set_portPolarity(newval);
    }

    /**
     * Returns the port state diagnostics. Bit 0 indicates a shortcut on output 0, etc.
     * Bit 8 indicates a power failure, and bit 9 signals overheating (overcurrent).
     * During normal use, all diagnostic bits should stay clear.
     *
     * @return an integer corresponding to the port state diagnostics
     *
     * @throws YAPI_Exception on error
     */
    public int get_portDiags() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PORTDIAGS_INVALID;
                }
            }
            res = _portDiags;
        }
        return res;
    }

    /**
     * Returns the port state diagnostics. Bit 0 indicates a shortcut on output 0, etc.
     * Bit 8 indicates a power failure, and bit 9 signals overheating (overcurrent).
     * During normal use, all diagnostic bits should stay clear.
     *
     * @return an integer corresponding to the port state diagnostics
     *
     * @throws YAPI_Exception on error
     */
    public int getPortDiags() throws YAPI_Exception
    {
        return get_portDiags();
    }

    /**
     * Returns the number of bits (i.e. channels)implemented in the I/O port.
     *
     * @return an integer corresponding to the number of bits (i.e
     *
     * @throws YAPI_Exception on error
     */
    public int get_portSize() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration == 0) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PORTSIZE_INVALID;
                }
            }
            res = _portSize;
        }
        return res;
    }

    /**
     * Returns the number of bits (i.e. channels)implemented in the I/O port.
     *
     * @return an integer corresponding to the number of bits (i.e
     *
     * @throws YAPI_Exception on error
     */
    public int getPortSize() throws YAPI_Exception
    {
        return get_portSize();
    }

    /**
     * Returns the voltage source used to drive output bits.
     *
     *  @return a value among YDigitalIO.OUTPUTVOLTAGE_USB_5V, YDigitalIO.OUTPUTVOLTAGE_USB_3V and
     * YDigitalIO.OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     *
     * @throws YAPI_Exception on error
     */
    public int get_outputVoltage() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return OUTPUTVOLTAGE_INVALID;
                }
            }
            res = _outputVoltage;
        }
        return res;
    }

    /**
     * Returns the voltage source used to drive output bits.
     *
     *  @return a value among YDigitalIO.OUTPUTVOLTAGE_USB_5V, YDigitalIO.OUTPUTVOLTAGE_USB_3V and
     * YDigitalIO.OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     *
     * @throws YAPI_Exception on error
     */
    public int getOutputVoltage() throws YAPI_Exception
    {
        return get_outputVoltage();
    }

    /**
     * Changes the voltage source used to drive output bits.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     *  @param newval : a value among YDigitalIO.OUTPUTVOLTAGE_USB_5V, YDigitalIO.OUTPUTVOLTAGE_USB_3V and
     * YDigitalIO.OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_outputVoltage(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("outputVoltage",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage source used to drive output bits.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     *  @param newval : a value among YDigitalIO.OUTPUTVOLTAGE_USB_5V, YDigitalIO.OUTPUTVOLTAGE_USB_3V and
     * YDigitalIO.OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setOutputVoltage(int newval)  throws YAPI_Exception
    {
        return set_outputVoltage(newval);
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
     * Retrieves a digital IO port for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the digital IO port is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDigitalIO.isOnline() to test if the digital IO port is
     * indeed online at a given time. In case of ambiguity when looking for
     * a digital IO port by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the digital IO port, for instance
     *         YMINIIO0.digitalIO.
     *
     * @return a YDigitalIO object allowing you to drive the digital IO port.
     */
    public static YDigitalIO FindDigitalIO(String func)
    {
        YDigitalIO obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YDigitalIO) YFunction._FindFromCache("DigitalIO", func);
            if (obj == null) {
                obj = new YDigitalIO(func);
                YFunction._AddToCache("DigitalIO", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a digital IO port for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the digital IO port is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDigitalIO.isOnline() to test if the digital IO port is
     * indeed online at a given time. In case of ambiguity when looking for
     * a digital IO port by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the digital IO port, for instance
     *         YMINIIO0.digitalIO.
     *
     * @return a YDigitalIO object allowing you to drive the digital IO port.
     */
    public static YDigitalIO FindDigitalIOInContext(YAPIContext yctx,String func)
    {
        YDigitalIO obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YDigitalIO) YFunction._FindFromCacheInContext(yctx, "DigitalIO", func);
            if (obj == null) {
                obj = new YDigitalIO(yctx, func);
                YFunction._AddToCache("DigitalIO", func, obj);
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
        _valueCallbackDigitalIO = callback;
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
        if (_valueCallbackDigitalIO != null) {
            _valueCallbackDigitalIO.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Sets a single bit (i.e. channel) of the I/O port.
     *
     * @param bitno : the bit number; lowest bit has index 0
     * @param bitstate : the state of the bit (1 or 0)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_bitState(int bitno,int bitstate) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(bitstate >= 0)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "invalid bit state");}
        //noinspection DoubleNegation
        if (!(bitstate <= 1)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "invalid bit state");}
        return set_command(String.format(Locale.US, "%c%d",82+bitstate,bitno));
    }

    /**
     * Returns the state of a single bit (i.e. channel)  of the I/O port.
     *
     * @param bitno : the bit number; lowest bit has index 0
     *
     * @return the bit state (0 or 1)
     *
     * @throws YAPI_Exception on error
     */
    public int get_bitState(int bitno) throws YAPI_Exception
    {
        int portVal;
        portVal = get_portState();
        return ((portVal >> bitno) & 1);
    }

    /**
     * Reverts a single bit (i.e. channel) of the I/O port.
     *
     * @param bitno : the bit number; lowest bit has index 0
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int toggle_bitState(int bitno) throws YAPI_Exception
    {
        return set_command(String.format(Locale.US, "T%d",bitno));
    }

    /**
     * Changes  the direction of a single bit (i.e. channel) from the I/O port.
     *
     * @param bitno : the bit number; lowest bit has index 0
     * @param bitdirection : direction to set, 0 makes the bit an input, 1 makes it an output.
     *         Remember to call the   saveToFlash() method to make sure the setting is kept after a reboot.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_bitDirection(int bitno,int bitdirection) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(bitdirection >= 0)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "invalid direction");}
        //noinspection DoubleNegation
        if (!(bitdirection <= 1)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "invalid direction");}
        return set_command(String.format(Locale.US, "%c%d",73+6*bitdirection,bitno));
    }

    /**
     *  Returns the direction of a single bit (i.e. channel) from the I/O port (0 means the bit is an
     * input, 1  an output).
     *
     * @param bitno : the bit number; lowest bit has index 0
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int get_bitDirection(int bitno) throws YAPI_Exception
    {
        int portDir;
        portDir = get_portDirection();
        return ((portDir >> bitno) & 1);
    }

    /**
     * Changes the polarity of a single bit from the I/O port.
     *
     * @param bitno : the bit number; lowest bit has index 0.
     *  @param bitpolarity : polarity to set, 0 makes the I/O work in regular mode, 1 makes the I/O  works
     * in reverse mode.
     *         Remember to call the   saveToFlash() method to make sure the setting is kept after a reboot.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_bitPolarity(int bitno,int bitpolarity) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(bitpolarity >= 0)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "invalid bit polarity");}
        //noinspection DoubleNegation
        if (!(bitpolarity <= 1)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "invalid bit polarity");}
        return set_command(String.format(Locale.US, "%c%d",110+4*bitpolarity,bitno));
    }

    /**
     *  Returns the polarity of a single bit from the I/O port (0 means the I/O works in regular mode, 1
     * means the I/O  works in reverse mode).
     *
     * @param bitno : the bit number; lowest bit has index 0
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int get_bitPolarity(int bitno) throws YAPI_Exception
    {
        int portPol;
        portPol = get_portPolarity();
        return ((portPol >> bitno) & 1);
    }

    /**
     * Changes  the electrical interface of a single bit from the I/O port.
     *
     * @param bitno : the bit number; lowest bit has index 0
     * @param opendrain : 0 makes a bit a regular input/output, 1 makes
     *         it an open-drain (open-collector) input/output. Remember to call the
     *         saveToFlash() method to make sure the setting is kept after a reboot.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_bitOpenDrain(int bitno,int opendrain) throws YAPI_Exception
    {
        //noinspection DoubleNegation
        if (!(opendrain >= 0)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "invalid state");}
        //noinspection DoubleNegation
        if (!(opendrain <= 1)) { throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "invalid state");}
        return set_command(String.format(Locale.US, "%c%d",100-32*opendrain,bitno));
    }

    /**
     *  Returns the type of electrical interface of a single bit from the I/O port. (0 means the bit is an
     * input, 1  an output).
     *
     * @param bitno : the bit number; lowest bit has index 0
     *
     * @return   0 means the a bit is a regular input/output, 1 means the bit is an open-drain
     *         (open-collector) input/output.
     *
     * @throws YAPI_Exception on error
     */
    public int get_bitOpenDrain(int bitno) throws YAPI_Exception
    {
        int portOpenDrain;
        portOpenDrain = get_portOpenDrain();
        return ((portOpenDrain >> bitno) & 1);
    }

    /**
     * Triggers a pulse on a single bit for a specified duration. The specified bit
     * will be turned to 1, and then back to 0 after the given duration.
     *
     * @param bitno : the bit number; lowest bit has index 0
     * @param ms_duration : desired pulse duration in milliseconds. Be aware that the device time
     *         resolution is not guaranteed up to the millisecond.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int pulse(int bitno,int ms_duration) throws YAPI_Exception
    {
        return set_command(String.format(Locale.US, "Z%d,0,%d",bitno,ms_duration));
    }

    /**
     * Schedules a pulse on a single bit for a specified duration. The specified bit
     * will be turned to 1, and then back to 0 after the given duration.
     *
     * @param bitno : the bit number; lowest bit has index 0
     * @param ms_delay : waiting time before the pulse, in milliseconds
     * @param ms_duration : desired pulse duration in milliseconds. Be aware that the device time
     *         resolution is not guaranteed up to the millisecond.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int delayedPulse(int bitno,int ms_delay,int ms_duration) throws YAPI_Exception
    {
        return set_command(String.format(Locale.US, "Z%d,%d,%d",bitno,ms_delay,ms_duration));
    }

    /**
     * Continues the enumeration of digital IO ports started using yFirstDigitalIO().
     * Caution: You can't make any assumption about the returned digital IO ports order.
     * If you want to find a specific a digital IO port, use DigitalIO.findDigitalIO()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YDigitalIO object, corresponding to
     *         a digital IO port currently online, or a null pointer
     *         if there are no more digital IO ports to enumerate.
     */
    public YDigitalIO nextDigitalIO()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindDigitalIOInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of digital IO ports currently accessible.
     * Use the method YDigitalIO.nextDigitalIO() to iterate on
     * next digital IO ports.
     *
     * @return a pointer to a YDigitalIO object, corresponding to
     *         the first digital IO port currently online, or a null pointer
     *         if there are none.
     */
    public static YDigitalIO FirstDigitalIO()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("DigitalIO");
        if (next_hwid == null)  return null;
        return FindDigitalIOInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of digital IO ports currently accessible.
     * Use the method YDigitalIO.nextDigitalIO() to iterate on
     * next digital IO ports.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YDigitalIO object, corresponding to
     *         the first digital IO port currently online, or a null pointer
     *         if there are none.
     */
    public static YDigitalIO FirstDigitalIOInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("DigitalIO");
        if (next_hwid == null)  return null;
        return FindDigitalIOInContext(yctx, next_hwid);
    }

    //--- (end of YDigitalIO implementation)
}

