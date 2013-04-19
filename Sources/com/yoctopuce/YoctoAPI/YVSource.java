/*********************************************************************
 *
 * $Id: YVSource.java 10471 2013-03-19 10:39:56Z seb $
 *
 * Implements yFindVSource(), the high-level API for VSource functions
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
 * YVSource Class: Voltage source function interface
 * 
 * Yoctopuce application programming interface allows you to control
 * the module voltage output. You affect absolute output values or make
 * transitions
 */
public class YVSource extends YFunction
{
    //--- (definitions)
    private YVSource.UpdateCallback _valueCallbackVSource;
    public static class YMove
    {
        public long target = YAPI.INVALID_LONG;
        public long ms = YAPI.INVALID_LONG;
        public long moving = YAPI.INVALID_LONG;
        public YMove(String attr){}
    }

    public static class YPulse
    {
        public long target = YAPI.INVALID_LONG;
        public long ms = YAPI.INVALID_LONG;
        public long moving = YAPI.INVALID_LONG;
        public YPulse(String attr){}
    }

    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid unit value
     */
    public static final String UNIT_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid voltage value
     */
    public static final int VOLTAGE_INVALID = YAPI.INVALID_INT;
    /**
     * invalid failure value
     */
  public static final int FAILURE_FALSE = 0;
  public static final int FAILURE_TRUE = 1;
  public static final int FAILURE_INVALID = -1;

    /**
     * invalid overHeat value
     */
  public static final int OVERHEAT_FALSE = 0;
  public static final int OVERHEAT_TRUE = 1;
  public static final int OVERHEAT_INVALID = -1;

    /**
     * invalid overCurrent value
     */
  public static final int OVERCURRENT_FALSE = 0;
  public static final int OVERCURRENT_TRUE = 1;
  public static final int OVERCURRENT_INVALID = -1;

    /**
     * invalid overLoad value
     */
  public static final int OVERLOAD_FALSE = 0;
  public static final int OVERLOAD_TRUE = 1;
  public static final int OVERLOAD_INVALID = -1;

    /**
     * invalid regulationFailure value
     */
  public static final int REGULATIONFAILURE_FALSE = 0;
  public static final int REGULATIONFAILURE_TRUE = 1;
  public static final int REGULATIONFAILURE_INVALID = -1;

    /**
     * invalid extPowerFailure value
     */
  public static final int EXTPOWERFAILURE_FALSE = 0;
  public static final int EXTPOWERFAILURE_TRUE = 1;
  public static final int EXTPOWERFAILURE_INVALID = -1;

    public static final YMove MOVE_INVALID = new YMove("");
    public static final YPulse PULSETIMER_INVALID = new YPulse("");
    //--- (end of definitions)

    /**
     * UdateCallback for VSource
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YVSource function, String functionValue);
    }



    //--- (YVSource implementation)

    /**
     * invalid move
     */
    /**
     * invalid pulseTimer
     */
    /**
     * Returns the logical name of the voltage source.
     * 
     * @return a string corresponding to the logical name of the voltage source
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the voltage source.
     * 
     * @return a string corresponding to the logical name of the voltage source
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the voltage source. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the voltage source
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
     * Changes the logical name of the voltage source. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the voltage source
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the voltage source (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the voltage source (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the voltage source (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the voltage source (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the measuring unit for the voltage.
     * 
     * @return a string corresponding to the measuring unit for the voltage
     * 
     * @throws YAPI_Exception
     */
    public String get_unit()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("unit");
        return json_val;
    }

    /**
     * Returns the measuring unit for the voltage.
     * 
     * @return a string corresponding to the measuring unit for the voltage
     * 
     * @throws YAPI_Exception
     */
    public String getUnit() throws YAPI_Exception

    { return get_unit(); }

    /**
     * Returns the voltage output command (mV)
     * 
     * @return an integer corresponding to the voltage output command (mV)
     * 
     * @throws YAPI_Exception
     */
    public int get_voltage()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("voltage");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the voltage output command (mV)
     * 
     * @return an integer corresponding to the voltage output command (mV)
     * 
     * @throws YAPI_Exception
     */
    public int getVoltage() throws YAPI_Exception

    { return get_voltage(); }

    /**
     * Tunes the device output voltage (milliVolts).
     * 
     * @param newval : an integer
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_voltage( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("voltage",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Tunes the device output voltage (milliVolts).
     * 
     * @param newval : an integer
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setVoltage( int newval)  throws YAPI_Exception

    { return set_voltage(newval); }

    /**
     * Returns true if the  module is in failure mode. More information can be obtained by testing
     * get_overheat, get_overcurrent etc... When a error condition is met, the output voltage is
     * set to zéro and cannot be changed until the reset() function is called.
     * 
     * @return either YVSource.FAILURE_FALSE or YVSource.FAILURE_TRUE, according to true if the  module is
     * in failure mode
     * 
     * @throws YAPI_Exception
     */
    public int get_failure()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("failure");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns true if the  module is in failure mode. More information can be obtained by testing
     * get_overheat, get_overcurrent etc... When a error condition is met, the output voltage is
     * set to zéro and cannot be changed until the reset() function is called.
     * 
     * @return either Y_FAILURE_FALSE or Y_FAILURE_TRUE, according to true if the  module is in failure mode
     * 
     * @throws YAPI_Exception
     */
    public int getFailure() throws YAPI_Exception

    { return get_failure(); }

    public int set_failure( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("failure",rest_val);
        return YAPI.SUCCESS;
    }

    public int setFailure( int newval)  throws YAPI_Exception

    { return set_failure(newval); }

    /**
     * Returns TRUE if the  module is overheating.
     * 
     * @return either YVSource.OVERHEAT_FALSE or YVSource.OVERHEAT_TRUE, according to TRUE if the  module
     * is overheating
     * 
     * @throws YAPI_Exception
     */
    public int get_overHeat()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("overHeat");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns TRUE if the  module is overheating.
     * 
     * @return either Y_OVERHEAT_FALSE or Y_OVERHEAT_TRUE, according to TRUE if the  module is overheating
     * 
     * @throws YAPI_Exception
     */
    public int getOverHeat() throws YAPI_Exception

    { return get_overHeat(); }

    /**
     * Returns true if the appliance connected to the device is too greedy .
     * 
     * @return either YVSource.OVERCURRENT_FALSE or YVSource.OVERCURRENT_TRUE, according to true if the
     * appliance connected to the device is too greedy
     * 
     * @throws YAPI_Exception
     */
    public int get_overCurrent()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("overCurrent");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns true if the appliance connected to the device is too greedy .
     * 
     * @return either Y_OVERCURRENT_FALSE or Y_OVERCURRENT_TRUE, according to true if the appliance
     * connected to the device is too greedy
     * 
     * @throws YAPI_Exception
     */
    public int getOverCurrent() throws YAPI_Exception

    { return get_overCurrent(); }

    /**
     * Returns true if the device is not able to maintaint the requested voltage output  .
     * 
     * @return either YVSource.OVERLOAD_FALSE or YVSource.OVERLOAD_TRUE, according to true if the device
     * is not able to maintaint the requested voltage output
     * 
     * @throws YAPI_Exception
     */
    public int get_overLoad()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("overLoad");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns true if the device is not able to maintaint the requested voltage output  .
     * 
     * @return either Y_OVERLOAD_FALSE or Y_OVERLOAD_TRUE, according to true if the device is not able to
     * maintaint the requested voltage output
     * 
     * @throws YAPI_Exception
     */
    public int getOverLoad() throws YAPI_Exception

    { return get_overLoad(); }

    /**
     * Returns true if the voltage output is too high regarding the requested voltage  .
     * 
     * @return either YVSource.REGULATIONFAILURE_FALSE or YVSource.REGULATIONFAILURE_TRUE, according to
     * true if the voltage output is too high regarding the requested voltage
     * 
     * @throws YAPI_Exception
     */
    public int get_regulationFailure()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("regulationFailure");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns true if the voltage output is too high regarding the requested voltage  .
     * 
     * @return either Y_REGULATIONFAILURE_FALSE or Y_REGULATIONFAILURE_TRUE, according to true if the
     * voltage output is too high regarding the requested voltage
     * 
     * @throws YAPI_Exception
     */
    public int getRegulationFailure() throws YAPI_Exception

    { return get_regulationFailure(); }

    /**
     * Returns true if external power supply voltage is too low.
     * 
     * @return either YVSource.EXTPOWERFAILURE_FALSE or YVSource.EXTPOWERFAILURE_TRUE, according to true
     * if external power supply voltage is too low
     * 
     * @throws YAPI_Exception
     */
    public int get_extPowerFailure()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("extPowerFailure");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns true if external power supply voltage is too low.
     * 
     * @return either Y_EXTPOWERFAILURE_FALSE or Y_EXTPOWERFAILURE_TRUE, according to true if external
     * power supply voltage is too low
     * 
     * @throws YAPI_Exception
     */
    public int getExtPowerFailure() throws YAPI_Exception

    { return get_extPowerFailure(); }

    public YMove get_move()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("move");
        return new YMove(json_val);
    }

    public YMove getMove() throws YAPI_Exception

    { return get_move(); }

    public int set_move( YMove  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("move",rest_val);
        return YAPI.SUCCESS;
    }

    public int setMove( YMove newval)  throws YAPI_Exception

    { return set_move(newval); }

    /**
     * Performs a smooth move at constant speed toward a given value.
     * 
     * @param target      : new output value at end of transition, in milliVolts.
     * @param ms_duration : transition duration, in milliseconds
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int voltageMove(int target,int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",target,ms_duration);
        _setAttr("move",rest_val);
        return YAPI.SUCCESS;
    }

    public YPulse get_pulseTimer()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("pulseTimer");
        return new YPulse(json_val);
    }

    public YPulse getPulseTimer() throws YAPI_Exception

    { return get_pulseTimer(); }

    public int set_pulseTimer( YPulse  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("pulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    public int setPulseTimer( YPulse newval)  throws YAPI_Exception

    { return set_pulseTimer(newval); }

    /**
     * Sets device output to a specific volatage, for a specified duration, then brings it
     * automatically to 0V.
     * 
     * @param voltage : pulse voltage, in millivolts
     * @param ms_duration : pulse duration, in millisecondes
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int pulse(int voltage,int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",voltage,ms_duration);
        _setAttr("pulseTimer",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Continues the enumeration of voltage sources started using yFirstVSource().
     * 
     * @return a pointer to a YVSource object, corresponding to
     *         a voltage source currently online, or a null pointer
     *         if there are no more voltage sources to enumerate.
     */
    public  YVSource nextVSource()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindVSource(next_hwid);
    }

    /**
     * Retrieves a voltage source for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the voltage source is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YVSource.isOnline() to test if the voltage source is
     * indeed online at a given time. In case of ambiguity when looking for
     * a voltage source by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the voltage source
     * 
     * @return a YVSource object allowing you to drive the voltage source.
     */
    public static YVSource FindVSource(String func)
    {   YFunction yfunc = YAPI.getFunction("VSource", func);
        if (yfunc != null) {
            return (YVSource) yfunc;
        }
        return new YVSource(func);
    }

    /**
     * Starts the enumeration of voltage sources currently accessible.
     * Use the method YVSource.nextVSource() to iterate on
     * next voltage sources.
     * 
     * @return a pointer to a YVSource object, corresponding to
     *         the first voltage source currently online, or a null pointer
     *         if there are none.
     */
    public static YVSource FirstVSource()
    {
        String next_hwid = YAPI.getFirstHardwareId("VSource");
        if (next_hwid == null)  return null;
        return FindVSource(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YVSource(String func)
    {
        super("VSource", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackVSource != null) {
            _valueCallbackVSource.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackVSource!=null);
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
    public void registerValueCallback(YVSource.UpdateCallback callback)
    {
         _valueCallbackVSource =  callback;
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

    //--- (end of YVSource implementation)
};

