/*********************************************************************
 *
 * $Id: YDigitalIO.java 19328 2015-02-17 17:30:45Z seb $
 *
 * Implements FindDigitalIO(), the high-level API for DigitalIO functions
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

//--- (YDigitalIO return codes)
//--- (end of YDigitalIO return codes)
//--- (YDigitalIO class start)
/**
 * YDigitalIO Class: Digital IO function interface
 *
 * The Yoctopuce application programming interface allows you to switch the state of each
 * bit of the I/O port. You can switch all bits at once, or one by one. The library
 * can also automatically generate short pulses of a determined duration. Electrical behavior
 * of each I/O can be modified (open drain and reverse polarity).
 */
 @SuppressWarnings("UnusedDeclaration")
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
    protected int _portSize = PORTSIZE_INVALID;
    protected int _outputVoltage = OUTPUTVOLTAGE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackDigitalIO = null;

    /**
     * Deprecated UpdateCallback for DigitalIO
     */
    public interface UpdateCallback {
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
    public interface TimedReportCallback {
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
    protected YDigitalIO(String func)
    {
        super(func);
        _className = "DigitalIO";
        //--- (YDigitalIO attributes initialization)
        //--- (end of YDigitalIO attributes initialization)
    }

    //--- (YDigitalIO implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
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
     * Returns the digital IO port state: bit 0 represents input 0, and so on.
     *
     * @return an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     *
     * @throws YAPI_Exception on error
     */
    public int get_portState() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PORTSTATE_INVALID;
            }
        }
        return _portState;
    }

    /**
     * Returns the digital IO port state: bit 0 represents input 0, and so on.
     *
     * @return an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     *
     * @throws YAPI_Exception on error
     */
    public int getPortState() throws YAPI_Exception
    {
        return get_portState();
    }

    /**
     * Changes the digital IO port state: bit 0 represents input 0, and so on. This function has no effect
     * on bits configured as input in portDirection.
     *
     * @param newval : an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_portState(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("portState",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the digital IO port state: bit 0 represents input 0, and so on. This function has no effect
     * on bits configured as input in portDirection.
     *
     * @param newval : an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPortState(int newval)  throws YAPI_Exception
    {
        return set_portState(newval);
    }

    /**
     * Returns the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     *
     *  @return an integer corresponding to the IO direction of all bits of the port: 0 makes a bit an
     * input, 1 makes it an output
     *
     * @throws YAPI_Exception on error
     */
    public int get_portDirection() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PORTDIRECTION_INVALID;
            }
        }
        return _portDirection;
    }

    /**
     * Returns the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     *
     *  @return an integer corresponding to the IO direction of all bits of the port: 0 makes a bit an
     * input, 1 makes it an output
     *
     * @throws YAPI_Exception on error
     */
    public int getPortDirection() throws YAPI_Exception
    {
        return get_portDirection();
    }

    /**
     * Changes the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     *  @param newval : an integer corresponding to the IO direction of all bits of the port: 0 makes a bit
     * an input, 1 makes it an output
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_portDirection(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("portDirection",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     *  @param newval : an integer corresponding to the IO direction of all bits of the port: 0 makes a bit
     * an input, 1 makes it an output
     *
     * @return YAPI_SUCCESS if the call succeeds.
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
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PORTOPENDRAIN_INVALID;
            }
        }
        return _portOpenDrain;
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
        rest_val = Integer.toString(newval);
        _setAttr("portOpenDrain",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the electrical interface for each bit of the port. 0 makes a bit a regular input/output, 1 makes
     * it an open-drain (open-collector) input/output. Remember to call the
     * saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     * @param newval : an integer corresponding to the electrical interface for each bit of the port
     *
     * @return YAPI_SUCCESS if the call succeeds.
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
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PORTPOLARITY_INVALID;
            }
        }
        return _portPolarity;
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
     * Changes the polarity of all the bits of the port: 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     *
     *  @param newval : an integer corresponding to the polarity of all the bits of the port: 0 makes a bit
     * an input, 1 makes it an output
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_portPolarity(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("portPolarity",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the polarity of all the bits of the port: 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     *
     *  @param newval : an integer corresponding to the polarity of all the bits of the port: 0 makes a bit
     * an input, 1 makes it an output
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPortPolarity(int newval)  throws YAPI_Exception
    {
        return set_portPolarity(newval);
    }

    /**
     * Returns the number of bits implemented in the I/O port.
     *
     * @return an integer corresponding to the number of bits implemented in the I/O port
     *
     * @throws YAPI_Exception on error
     */
    public int get_portSize() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PORTSIZE_INVALID;
            }
        }
        return _portSize;
    }

    /**
     * Returns the number of bits implemented in the I/O port.
     *
     * @return an integer corresponding to the number of bits implemented in the I/O port
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
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return OUTPUTVOLTAGE_INVALID;
            }
        }
        return _outputVoltage;
    }

    /**
     * Returns the voltage source used to drive output bits.
     *
     *  @return a value among Y_OUTPUTVOLTAGE_USB_5V, Y_OUTPUTVOLTAGE_USB_3V and Y_OUTPUTVOLTAGE_EXT_V
     * corresponding to the voltage source used to drive output bits
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
        rest_val = Integer.toString(newval);
        _setAttr("outputVoltage",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage source used to drive output bits.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     *  @param newval : a value among Y_OUTPUTVOLTAGE_USB_5V, Y_OUTPUTVOLTAGE_USB_3V and
     * Y_OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setOutputVoltage(int newval)  throws YAPI_Exception
    {
        return set_outputVoltage(newval);
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String get_command() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return COMMAND_INVALID;
            }
        }
        return _command;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String getCommand() throws YAPI_Exception
    {
        return get_command();
    }

    public int set_command(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("command",rest_val);
        return YAPI.SUCCESS;
    }

    public int setCommand(String newval)  throws YAPI_Exception
    {
        return set_command(newval);
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
     * @param func : a string that uniquely characterizes the digital IO port
     *
     * @return a YDigitalIO object allowing you to drive the digital IO port.
     */
    public static YDigitalIO FindDigitalIO(String func)
    {
        YDigitalIO obj;
        obj = (YDigitalIO) YFunction._FindFromCache("DigitalIO", func);
        if (obj == null) {
            obj = new YDigitalIO(func);
            YFunction._AddToCache("DigitalIO", func, obj);
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
     * Sets a single bit of the I/O port.
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
        if (!(bitstate >= 0)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid bitstate");}
        if (!(bitstate <= 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid bitstate");}
        return set_command(String.format("%c%d",82+bitstate,bitno));
    }

    /**
     * Returns the state of a single bit of the I/O port.
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
        return ((((portVal) >> (bitno))) & (1));
    }

    /**
     * Reverts a single bit of the I/O port.
     *
     * @param bitno : the bit number; lowest bit has index 0
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int toggle_bitState(int bitno) throws YAPI_Exception
    {
        return set_command(String.format("T%d",bitno));
    }

    /**
     * Changes  the direction of a single bit from the I/O port.
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
        if (!(bitdirection >= 0)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid direction");}
        if (!(bitdirection <= 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid direction");}
        return set_command(String.format("%c%d",73+6*bitdirection,bitno));
    }

    /**
     * Returns the direction of a single bit from the I/O port (0 means the bit is an input, 1  an output).
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
        return ((((portDir) >> (bitno))) & (1));
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
        if (!(bitpolarity >= 0)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid bitpolarity");}
        if (!(bitpolarity <= 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid bitpolarity");}
        return set_command(String.format("%c%d",110+4*bitpolarity,bitno));
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
        return ((((portPol) >> (bitno))) & (1));
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
        if (!(opendrain >= 0)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid state");}
        if (!(opendrain <= 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid state");}
        return set_command(String.format("%c%d",100-32*opendrain,bitno));
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
        return ((((portOpenDrain) >> (bitno))) & (1));
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
        return set_command(String.format("Z%d,0,%d", bitno,ms_duration));
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
        return set_command(String.format("Z%d,%d,%d",bitno,ms_delay,ms_duration));
    }

    /**
     * Continues the enumeration of digital IO ports started using yFirstDigitalIO().
     *
     * @return a pointer to a YDigitalIO object, corresponding to
     *         a digital IO port currently online, or a null pointer
     *         if there are no more digital IO ports to enumerate.
     */
    public  YDigitalIO nextDigitalIO()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindDigitalIO(next_hwid);
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
        String next_hwid = SafeYAPI().getFirstHardwareId("DigitalIO");
        if (next_hwid == null)  return null;
        return FindDigitalIO(next_hwid);
    }

    //--- (end of YDigitalIO implementation)
}

