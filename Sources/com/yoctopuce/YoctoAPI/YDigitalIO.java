/*********************************************************************
 *
 * $Id: pic24config.php 12323 2013-08-13 15:09:18Z mvuilleu $
 *
 * Implements yFindDigitalIO(), the high-level API for DigitalIO functions
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

//--- (globals)
//--- (end of globals)
/**
 * YDigitalIO Class: Digital IO function interface
 * 
 * ....
 */
public class YDigitalIO extends YFunction
{
    //--- (definitions)
    private YDigitalIO.UpdateCallback _valueCallbackDigitalIO;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid portState value
     */
    public static final int PORTSTATE_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid portDirection value
     */
    public static final int PORTDIRECTION_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid portOpenDrain value
     */
    public static final int PORTOPENDRAIN_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid portSize value
     */
    public static final int PORTSIZE_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid outputVoltage value
     */
    public static final int OUTPUTVOLTAGE_USB_5V = 0;
    public static final int OUTPUTVOLTAGE_USB_3V3 = 1;
    public static final int OUTPUTVOLTAGE_EXT_V = 2;
    public static final int OUTPUTVOLTAGE_INVALID = -1;

    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    //--- (end of definitions)

    /**
     * UdateCallback for DigitalIO
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YDigitalIO function, String functionValue);
    }



    //--- (YDigitalIO implementation)

    /**
     * Returns the logical name of the digital IO port.
     * 
     * @return a string corresponding to the logical name of the digital IO port
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the digital IO port.
     * 
     * @return a string corresponding to the logical name of the digital IO port
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the digital IO port. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the digital IO port
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
     * Changes the logical name of the digital IO port. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the digital IO port
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the digital IO port (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the digital IO port (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the digital IO port (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the digital IO port (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the digital IO port state: bit 0 represents input 0, and so on.
     * 
     * @return an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     * 
     * @throws YAPI_Exception
     */
    public int get_portState()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("portState");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the digital IO port state: bit 0 represents input 0, and so on.
     * 
     * @return an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     * 
     * @throws YAPI_Exception
     */
    public int getPortState() throws YAPI_Exception

    { return get_portState(); }

    /**
     * Changes the digital IO port state: bit 0 represents input 0, and so on. This function has no effect
     * on bits configured as input in portDirection.
     * 
     * @param newval : an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_portState( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
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
     * @throws YAPI_Exception
     */
    public int setPortState( int newval)  throws YAPI_Exception

    { return set_portState(newval); }

    /**
     * Returns the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     * 
     * @return an integer corresponding to the IO direction of all bits of the port: 0 makes a bit an
     * input, 1 makes it an output
     * 
     * @throws YAPI_Exception
     */
    public int get_portDirection()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("portDirection");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     * 
     * @return an integer corresponding to the IO direction of all bits of the port: 0 makes a bit an
     * input, 1 makes it an output
     * 
     * @throws YAPI_Exception
     */
    public int getPortDirection() throws YAPI_Exception

    { return get_portDirection(); }

    /**
     * Changes the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     * 
     * @param newval : an integer corresponding to the IO direction of all bits of the port: 0 makes a bit
     * an input, 1 makes it an output
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_portDirection( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("portDirection",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     * 
     * @param newval : an integer corresponding to the IO direction of all bits of the port: 0 makes a bit
     * an input, 1 makes it an output
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setPortDirection( int newval)  throws YAPI_Exception

    { return set_portDirection(newval); }

    /**
     * Returns the electrical interface for each bit of the port. 0 makes a bit a regular input/output, 1 makes
     * it an open-drain (open-collector) input/output.
     * 
     * @return an integer corresponding to the electrical interface for each bit of the port
     * 
     * @throws YAPI_Exception
     */
    public int get_portOpenDrain()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("portOpenDrain");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the electrical interface for each bit of the port. 0 makes a bit a regular input/output, 1 makes
     * it an open-drain (open-collector) input/output.
     * 
     * @return an integer corresponding to the electrical interface for each bit of the port
     * 
     * @throws YAPI_Exception
     */
    public int getPortOpenDrain() throws YAPI_Exception

    { return get_portOpenDrain(); }

    /**
     * Changes the electrical interface for each bit of the port. 0 makes a bit a regular input/output, 1 makes
     * it an open-drain (open-collector) input/output. Remember to call the
     * saveToFlash() method  to make sure the setting will be kept after a reboot.
     * 
     * @param newval : an integer corresponding to the electrical interface for each bit of the port
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_portOpenDrain( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("portOpenDrain",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the electrical interface for each bit of the port. 0 makes a bit a regular input/output, 1 makes
     * it an open-drain (open-collector) input/output. Remember to call the
     * saveToFlash() method  to make sure the setting will be kept after a reboot.
     * 
     * @param newval : an integer corresponding to the electrical interface for each bit of the port
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setPortOpenDrain( int newval)  throws YAPI_Exception

    { return set_portOpenDrain(newval); }

    /**
     * Returns the number of bits implemented in the I/O port.
     * 
     * @return an integer corresponding to the number of bits implemented in the I/O port
     * 
     * @throws YAPI_Exception
     */
    public int get_portSize()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("portSize");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the number of bits implemented in the I/O port.
     * 
     * @return an integer corresponding to the number of bits implemented in the I/O port
     * 
     * @throws YAPI_Exception
     */
    public int getPortSize() throws YAPI_Exception

    { return get_portSize(); }

    /**
     * Returns the voltage source used to drive output bits.
     * 
     * @return a value among YDigitalIO.OUTPUTVOLTAGE_USB_5V, YDigitalIO.OUTPUTVOLTAGE_USB_3V3 and
     * YDigitalIO.OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     * 
     * @throws YAPI_Exception
     */
    public int get_outputVoltage()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("outputVoltage");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the voltage source used to drive output bits.
     * 
     * @return a value among Y_OUTPUTVOLTAGE_USB_5V, Y_OUTPUTVOLTAGE_USB_3V3 and Y_OUTPUTVOLTAGE_EXT_V
     * corresponding to the voltage source used to drive output bits
     * 
     * @throws YAPI_Exception
     */
    public int getOutputVoltage() throws YAPI_Exception

    { return get_outputVoltage(); }

    /**
     * Changes the voltage source used to drive output bits.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     * 
     * @param newval : a value among YDigitalIO.OUTPUTVOLTAGE_USB_5V, YDigitalIO.OUTPUTVOLTAGE_USB_3V3 and
     * YDigitalIO.OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_outputVoltage( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("outputVoltage",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage source used to drive output bits.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     * 
     * @param newval : a value among Y_OUTPUTVOLTAGE_USB_5V, Y_OUTPUTVOLTAGE_USB_3V3 and
     * Y_OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setOutputVoltage( int newval)  throws YAPI_Exception

    { return set_outputVoltage(newval); }

    public String get_command()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("command");
        return json_val;
    }

    public String getCommand() throws YAPI_Exception

    { return get_command(); }

    public int set_command( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("command",rest_val);
        return YAPI.SUCCESS;
    }

    public int setCommand( String newval)  throws YAPI_Exception

    { return set_command(newval); }

    /**
     * Set a single bit of the I/O port.
     * 
     * @param bitno: the bit number; lowest bit is index 0
     * @param bitval: the value of the bit (1 or 0)
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_bitState(int bitno,int bitval)  throws YAPI_Exception
    {
        if (!(bitval >= 0)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid bitval");};
        if (!(bitval <= 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid bitval");};
        return set_command(String.format("%c%d",82+bitval, bitno)); 
        
    }

    /**
     * Returns the value of a single bit of the I/O port.
     * 
     * @param bitno: the bit number; lowest bit is index 0
     * 
     * @return the bit value (0 or 1)
     * 
     * @throws YAPI_Exception
     */
    public int get_bitState(int bitno)  throws YAPI_Exception
    {
        int portVal;
        portVal = get_portState();
        return ((((portVal) >> (bitno))) & (1));
        
    }

    /**
     * Revert a single bit of the I/O port.
     * 
     * @param bitno: the bit number; lowest bit is index 0
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int toggle_bitState(int bitno)  throws YAPI_Exception
    {
        return set_command(String.format("T%d", bitno)); 
        
    }

    /**
     * Change  the direction of a single bit from the I/O port.
     * 
     * @param bitno: the bit number; lowest bit is index 0
     * @param bitdirection: direction to set, 0 makes the bit an input, 1 makes it an output.
     *         Remember to call the   saveToFlash() method to make sure the setting will be kept after a reboot.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_bitDirection(int bitno,int bitdirection)  throws YAPI_Exception
    {
        if (!(bitdirection >= 0)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid direction");};
        if (!(bitdirection <= 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid direction");};
        return set_command(String.format("%c%d",73+6*bitdirection, bitno)); 
        
    }

    /**
     * Change  the direction of a single bit from the I/O port (0 means the bit is an input, 1  an output).
     * 
     * @param bitno: the bit number; lowest bit is index 0
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int get_bitDirection(int bitno)  throws YAPI_Exception
    {
        int portDir;
        portDir = get_portDirection();
        return ((((portDir) >> (bitno))) & (1));
        
    }

    /**
     * Change  the electrical interface of a single bit from the I/O port.
     * 
     * @param bitno: the bit number; lowest bit is index 0
     * @param opendrain: value to set, 0 makes a bit a regular input/output, 1 makes
     *         it an open-drain (open-collector) input/output. Remember to call the
     *         saveToFlash() method to make sure the setting will be kept after a reboot.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_bitOpenDrain(int bitno,int opendrain)  throws YAPI_Exception
    {
        if (!(opendrain >= 0)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid state");};
        if (!(opendrain <= 1)) { throw new YAPI_Exception( YAPI.INVALID_ARGUMENT,  "invalid state");};
        return set_command(String.format("%c%d",100-32*opendrain, bitno)); 
        
    }

    /**
     * Returns the type of electrical interface of a single bit from the I/O port. (0 means the bit is an
     * input, 1  an output).
     * 
     * @param bitno: the bit number; lowest bit is index 0
     * 
     * @return   0 means the a bit is a regular input/output, 1means the b it an open-drain
     * (open-collector) input/output.
     * 
     * @throws YAPI_Exception
     */
    public int get_bitOpenDrain(int bitno)  throws YAPI_Exception
    {
        int portOpenDrain;
        portOpenDrain = get_portOpenDrain();
        return ((((portOpenDrain) >> (bitno))) & (1));
        
    }

    /**
     * Continues the enumeration of digital IO port started using yFirstDigitalIO().
     * 
     * @return a pointer to a YDigitalIO object, corresponding to
     *         a digital IO port currently online, or a null pointer
     *         if there are no more digital IO port to enumerate.
     */
    public  YDigitalIO nextDigitalIO()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindDigitalIO(next_hwid);
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
    {   YFunction yfunc = YAPI.getFunction("DigitalIO", func);
        if (yfunc != null) {
            return (YDigitalIO) yfunc;
        }
        return new YDigitalIO(func);
    }

    /**
     * Starts the enumeration of digital IO port currently accessible.
     * Use the method YDigitalIO.nextDigitalIO() to iterate on
     * next digital IO port.
     * 
     * @return a pointer to a YDigitalIO object, corresponding to
     *         the first digital IO port currently online, or a null pointer
     *         if there are none.
     */
    public static YDigitalIO FirstDigitalIO()
    {
        String next_hwid = YAPI.getFirstHardwareId("DigitalIO");
        if (next_hwid == null)  return null;
        return FindDigitalIO(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YDigitalIO(String func)
    {
        super("DigitalIO", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackDigitalIO != null) {
            _valueCallbackDigitalIO.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackDigitalIO!=null);
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
    public void registerValueCallback(YDigitalIO.UpdateCallback callback)
    {
         _valueCallbackDigitalIO =  callback;
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

    //--- (end of YDigitalIO implementation)
};

