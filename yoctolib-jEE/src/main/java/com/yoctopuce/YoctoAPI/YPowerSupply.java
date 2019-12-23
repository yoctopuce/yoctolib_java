/*
 *
 *  $Id: YPowerSupply.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 *  Implements FindPowerSupply(), the high-level API for PowerSupply functions
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

//--- (YPowerSupply return codes)
//--- (end of YPowerSupply return codes)
//--- (YPowerSupply yapiwrapper)
//--- (end of YPowerSupply yapiwrapper)
//--- (YPowerSupply class start)
/**
 * YPowerSupply Class: regulated power supply control interface
 *
 * The YPowerSupply class allows you to drive a Yoctopuce power supply.
 * It can be use to change the voltage set point,
 * the current limit and the enable/disable the output.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YPowerSupply extends YFunction
{
//--- (end of YPowerSupply class start)
//--- (YPowerSupply definitions)
    /**
     * invalid voltageSetPoint value
     */
    public static final double VOLTAGESETPOINT_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid currentLimit value
     */
    public static final double CURRENTLIMIT_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid powerOutput value
     */
    public static final int POWEROUTPUT_OFF = 0;
    public static final int POWEROUTPUT_ON = 1;
    public static final int POWEROUTPUT_INVALID = -1;
    /**
     * invalid voltageSense value
     */
    public static final int VOLTAGESENSE_INT = 0;
    public static final int VOLTAGESENSE_EXT = 1;
    public static final int VOLTAGESENSE_INVALID = -1;
    /**
     * invalid measuredVoltage value
     */
    public static final double MEASUREDVOLTAGE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid measuredCurrent value
     */
    public static final double MEASUREDCURRENT_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid inputVoltage value
     */
    public static final double INPUTVOLTAGE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid vInt value
     */
    public static final double VINT_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid ldoTemperature value
     */
    public static final double LDOTEMPERATURE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid voltageTransition value
     */
    public static final String VOLTAGETRANSITION_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid voltageAtStartUp value
     */
    public static final double VOLTAGEATSTARTUP_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid currentAtStartUp value
     */
    public static final double CURRENTATSTARTUP_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected double _voltageSetPoint = VOLTAGESETPOINT_INVALID;
    protected double _currentLimit = CURRENTLIMIT_INVALID;
    protected int _powerOutput = POWEROUTPUT_INVALID;
    protected int _voltageSense = VOLTAGESENSE_INVALID;
    protected double _measuredVoltage = MEASUREDVOLTAGE_INVALID;
    protected double _measuredCurrent = MEASUREDCURRENT_INVALID;
    protected double _inputVoltage = INPUTVOLTAGE_INVALID;
    protected double _vInt = VINT_INVALID;
    protected double _ldoTemperature = LDOTEMPERATURE_INVALID;
    protected String _voltageTransition = VOLTAGETRANSITION_INVALID;
    protected double _voltageAtStartUp = VOLTAGEATSTARTUP_INVALID;
    protected double _currentAtStartUp = CURRENTATSTARTUP_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackPowerSupply = null;

    /**
     * Deprecated UpdateCallback for PowerSupply
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YPowerSupply function, String functionValue);
    }

    /**
     * TimedReportCallback for PowerSupply
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YPowerSupply  function, YMeasure measure);
    }
    //--- (end of YPowerSupply definitions)


    /**
     *
     * @param func : functionid
     */
    protected YPowerSupply(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "PowerSupply";
        //--- (YPowerSupply attributes initialization)
        //--- (end of YPowerSupply attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YPowerSupply(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YPowerSupply implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("voltageSetPoint")) {
            _voltageSetPoint = Math.round(json_val.getDouble("voltageSetPoint") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("currentLimit")) {
            _currentLimit = Math.round(json_val.getDouble("currentLimit") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("powerOutput")) {
            _powerOutput = json_val.getInt("powerOutput") > 0 ? 1 : 0;
        }
        if (json_val.has("voltageSense")) {
            _voltageSense = json_val.getInt("voltageSense");
        }
        if (json_val.has("measuredVoltage")) {
            _measuredVoltage = Math.round(json_val.getDouble("measuredVoltage") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("measuredCurrent")) {
            _measuredCurrent = Math.round(json_val.getDouble("measuredCurrent") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("inputVoltage")) {
            _inputVoltage = Math.round(json_val.getDouble("inputVoltage") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("vInt")) {
            _vInt = Math.round(json_val.getDouble("vInt") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("ldoTemperature")) {
            _ldoTemperature = Math.round(json_val.getDouble("ldoTemperature") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("voltageTransition")) {
            _voltageTransition = json_val.getString("voltageTransition");
        }
        if (json_val.has("voltageAtStartUp")) {
            _voltageAtStartUp = Math.round(json_val.getDouble("voltageAtStartUp") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("currentAtStartUp")) {
            _currentAtStartUp = Math.round(json_val.getDouble("currentAtStartUp") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the voltage set point, in V.
     *
     * @param newval : a floating point number corresponding to the voltage set point, in V
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_voltageSetPoint(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("voltageSetPoint",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage set point, in V.
     *
     * @param newval : a floating point number corresponding to the voltage set point, in V
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setVoltageSetPoint(double newval)  throws YAPI_Exception
    {
        return set_voltageSetPoint(newval);
    }

    /**
     * Returns the voltage set point, in V.
     *
     * @return a floating point number corresponding to the voltage set point, in V
     *
     * @throws YAPI_Exception on error
     */
    public double get_voltageSetPoint() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLTAGESETPOINT_INVALID;
                }
            }
            res = _voltageSetPoint;
        }
        return res;
    }

    /**
     * Returns the voltage set point, in V.
     *
     * @return a floating point number corresponding to the voltage set point, in V
     *
     * @throws YAPI_Exception on error
     */
    public double getVoltageSetPoint() throws YAPI_Exception
    {
        return get_voltageSetPoint();
    }

    /**
     * Changes the current limit, in mA.
     *
     * @param newval : a floating point number corresponding to the current limit, in mA
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_currentLimit(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("currentLimit",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current limit, in mA.
     *
     * @param newval : a floating point number corresponding to the current limit, in mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrentLimit(double newval)  throws YAPI_Exception
    {
        return set_currentLimit(newval);
    }

    /**
     * Returns the current limit, in mA.
     *
     * @return a floating point number corresponding to the current limit, in mA
     *
     * @throws YAPI_Exception on error
     */
    public double get_currentLimit() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENTLIMIT_INVALID;
                }
            }
            res = _currentLimit;
        }
        return res;
    }

    /**
     * Returns the current limit, in mA.
     *
     * @return a floating point number corresponding to the current limit, in mA
     *
     * @throws YAPI_Exception on error
     */
    public double getCurrentLimit() throws YAPI_Exception
    {
        return get_currentLimit();
    }

    /**
     * Returns the power supply output switch state.
     *
     *  @return either YPowerSupply.POWEROUTPUT_OFF or YPowerSupply.POWEROUTPUT_ON, according to the power
     * supply output switch state
     *
     * @throws YAPI_Exception on error
     */
    public int get_powerOutput() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return POWEROUTPUT_INVALID;
                }
            }
            res = _powerOutput;
        }
        return res;
    }

    /**
     * Returns the power supply output switch state.
     *
     * @return either Y_POWEROUTPUT_OFF or Y_POWEROUTPUT_ON, according to the power supply output switch state
     *
     * @throws YAPI_Exception on error
     */
    public int getPowerOutput() throws YAPI_Exception
    {
        return get_powerOutput();
    }

    /**
     * Changes the power supply output switch state.
     *
     *  @param newval : either YPowerSupply.POWEROUTPUT_OFF or YPowerSupply.POWEROUTPUT_ON, according to
     * the power supply output switch state
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_powerOutput(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("powerOutput",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the power supply output switch state.
     *
     * @param newval : either Y_POWEROUTPUT_OFF or Y_POWEROUTPUT_ON, according to the power supply output switch state
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPowerOutput(int newval)  throws YAPI_Exception
    {
        return set_powerOutput(newval);
    }

    /**
     * Returns the output voltage control point.
     *
     *  @return either YPowerSupply.VOLTAGESENSE_INT or YPowerSupply.VOLTAGESENSE_EXT, according to the
     * output voltage control point
     *
     * @throws YAPI_Exception on error
     */
    public int get_voltageSense() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLTAGESENSE_INVALID;
                }
            }
            res = _voltageSense;
        }
        return res;
    }

    /**
     * Returns the output voltage control point.
     *
     * @return either Y_VOLTAGESENSE_INT or Y_VOLTAGESENSE_EXT, according to the output voltage control point
     *
     * @throws YAPI_Exception on error
     */
    public int getVoltageSense() throws YAPI_Exception
    {
        return get_voltageSense();
    }

    /**
     * Changes the voltage control point.
     *
     *  @param newval : either YPowerSupply.VOLTAGESENSE_INT or YPowerSupply.VOLTAGESENSE_EXT, according to
     * the voltage control point
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_voltageSense(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("voltageSense",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage control point.
     *
     * @param newval : either Y_VOLTAGESENSE_INT or Y_VOLTAGESENSE_EXT, according to the voltage control point
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setVoltageSense(int newval)  throws YAPI_Exception
    {
        return set_voltageSense(newval);
    }

    /**
     * Returns the measured output voltage, in V.
     *
     * @return a floating point number corresponding to the measured output voltage, in V
     *
     * @throws YAPI_Exception on error
     */
    public double get_measuredVoltage() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return MEASUREDVOLTAGE_INVALID;
                }
            }
            res = _measuredVoltage;
        }
        return res;
    }

    /**
     * Returns the measured output voltage, in V.
     *
     * @return a floating point number corresponding to the measured output voltage, in V
     *
     * @throws YAPI_Exception on error
     */
    public double getMeasuredVoltage() throws YAPI_Exception
    {
        return get_measuredVoltage();
    }

    /**
     * Returns the measured output current, in mA.
     *
     * @return a floating point number corresponding to the measured output current, in mA
     *
     * @throws YAPI_Exception on error
     */
    public double get_measuredCurrent() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return MEASUREDCURRENT_INVALID;
                }
            }
            res = _measuredCurrent;
        }
        return res;
    }

    /**
     * Returns the measured output current, in mA.
     *
     * @return a floating point number corresponding to the measured output current, in mA
     *
     * @throws YAPI_Exception on error
     */
    public double getMeasuredCurrent() throws YAPI_Exception
    {
        return get_measuredCurrent();
    }

    /**
     * Returns the measured input voltage, in V.
     *
     * @return a floating point number corresponding to the measured input voltage, in V
     *
     * @throws YAPI_Exception on error
     */
    public double get_inputVoltage() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return INPUTVOLTAGE_INVALID;
                }
            }
            res = _inputVoltage;
        }
        return res;
    }

    /**
     * Returns the measured input voltage, in V.
     *
     * @return a floating point number corresponding to the measured input voltage, in V
     *
     * @throws YAPI_Exception on error
     */
    public double getInputVoltage() throws YAPI_Exception
    {
        return get_inputVoltage();
    }

    /**
     * Returns the internal voltage, in V.
     *
     * @return a floating point number corresponding to the internal voltage, in V
     *
     * @throws YAPI_Exception on error
     */
    public double get_vInt() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return VINT_INVALID;
                }
            }
            res = _vInt;
        }
        return res;
    }

    /**
     * Returns the internal voltage, in V.
     *
     * @return a floating point number corresponding to the internal voltage, in V
     *
     * @throws YAPI_Exception on error
     */
    public double getVInt() throws YAPI_Exception
    {
        return get_vInt();
    }

    /**
     * Returns the LDO temperature, in Celsius.
     *
     * @return a floating point number corresponding to the LDO temperature, in Celsius
     *
     * @throws YAPI_Exception on error
     */
    public double get_ldoTemperature() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LDOTEMPERATURE_INVALID;
                }
            }
            res = _ldoTemperature;
        }
        return res;
    }

    /**
     * Returns the LDO temperature, in Celsius.
     *
     * @return a floating point number corresponding to the LDO temperature, in Celsius
     *
     * @throws YAPI_Exception on error
     */
    public double getLdoTemperature() throws YAPI_Exception
    {
        return get_ldoTemperature();
    }

    public String get_voltageTransition() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLTAGETRANSITION_INVALID;
                }
            }
            res = _voltageTransition;
        }
        return res;
    }

    public int set_voltageTransition(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("voltageTransition",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Changes the voltage set point at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the voltage set point at device start up
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_voltageAtStartUp(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("voltageAtStartUp",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage set point at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the voltage set point at device start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setVoltageAtStartUp(double newval)  throws YAPI_Exception
    {
        return set_voltageAtStartUp(newval);
    }

    /**
     * Returns the selected voltage set point at device startup, in V.
     *
     * @return a floating point number corresponding to the selected voltage set point at device startup, in V
     *
     * @throws YAPI_Exception on error
     */
    public double get_voltageAtStartUp() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLTAGEATSTARTUP_INVALID;
                }
            }
            res = _voltageAtStartUp;
        }
        return res;
    }

    /**
     * Returns the selected voltage set point at device startup, in V.
     *
     * @return a floating point number corresponding to the selected voltage set point at device startup, in V
     *
     * @throws YAPI_Exception on error
     */
    public double getVoltageAtStartUp() throws YAPI_Exception
    {
        return get_voltageAtStartUp();
    }

    /**
     * Changes the current limit at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the current limit at device start up
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_currentAtStartUp(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("currentAtStartUp",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current limit at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the current limit at device start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrentAtStartUp(double newval)  throws YAPI_Exception
    {
        return set_currentAtStartUp(newval);
    }

    /**
     * Returns the selected current limit at device startup, in mA.
     *
     * @return a floating point number corresponding to the selected current limit at device startup, in mA
     *
     * @throws YAPI_Exception on error
     */
    public double get_currentAtStartUp() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENTATSTARTUP_INVALID;
                }
            }
            res = _currentAtStartUp;
        }
        return res;
    }

    /**
     * Returns the selected current limit at device startup, in mA.
     *
     * @return a floating point number corresponding to the selected current limit at device startup, in mA
     *
     * @throws YAPI_Exception on error
     */
    public double getCurrentAtStartUp() throws YAPI_Exception
    {
        return get_currentAtStartUp();
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
     * Retrieves a regulated power supply for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the regulated power supply is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPowerSupply.isOnline() to test if the regulated power supply is
     * indeed online at a given time. In case of ambiguity when looking for
     * a regulated power supply by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the regulated power supply, for instance
     *         MyDevice.powerSupply.
     *
     * @return a YPowerSupply object allowing you to drive the regulated power supply.
     */
    public static YPowerSupply FindPowerSupply(String func)
    {
        YPowerSupply obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YPowerSupply) YFunction._FindFromCache("PowerSupply", func);
            if (obj == null) {
                obj = new YPowerSupply(func);
                YFunction._AddToCache("PowerSupply", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a regulated power supply for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the regulated power supply is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YPowerSupply.isOnline() to test if the regulated power supply is
     * indeed online at a given time. In case of ambiguity when looking for
     * a regulated power supply by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the regulated power supply, for instance
     *         MyDevice.powerSupply.
     *
     * @return a YPowerSupply object allowing you to drive the regulated power supply.
     */
    public static YPowerSupply FindPowerSupplyInContext(YAPIContext yctx,String func)
    {
        YPowerSupply obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YPowerSupply) YFunction._FindFromCacheInContext(yctx, "PowerSupply", func);
            if (obj == null) {
                obj = new YPowerSupply(yctx, func);
                YFunction._AddToCache("PowerSupply", func, obj);
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
        _valueCallbackPowerSupply = callback;
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
        if (_valueCallbackPowerSupply != null) {
            _valueCallbackPowerSupply.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Performs a smooth transition of output voltage. Any explicit voltage
     * change cancels any ongoing transition process.
     *
     * @param V_target   : new output voltage value at the end of the transition
     *         (floating-point number, representing the end voltage in V)
     * @param ms_duration : total duration of the transition, in milliseconds
     *
     * @return YAPI.SUCCESS when the call succeeds.
     */
    public int voltageMove(double V_target,int ms_duration) throws YAPI_Exception
    {
        String newval;
        if (V_target < 0.0) {
            V_target  = 0.0;
        }
        newval = String.format(Locale.US, "%d:%d", (int) (double)Math.round(V_target*65536),ms_duration);

        return set_voltageTransition(newval);
    }

    /**
     * Continues the enumeration of regulated power supplies started using yFirstPowerSupply().
     * Caution: You can't make any assumption about the returned regulated power supplies order.
     * If you want to find a specific a regulated power supply, use PowerSupply.findPowerSupply()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YPowerSupply object, corresponding to
     *         a regulated power supply currently online, or a null pointer
     *         if there are no more regulated power supplies to enumerate.
     */
    public YPowerSupply nextPowerSupply()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindPowerSupplyInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of regulated power supplies currently accessible.
     * Use the method YPowerSupply.nextPowerSupply() to iterate on
     * next regulated power supplies.
     *
     * @return a pointer to a YPowerSupply object, corresponding to
     *         the first regulated power supply currently online, or a null pointer
     *         if there are none.
     */
    public static YPowerSupply FirstPowerSupply()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("PowerSupply");
        if (next_hwid == null)  return null;
        return FindPowerSupplyInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of regulated power supplies currently accessible.
     * Use the method YPowerSupply.nextPowerSupply() to iterate on
     * next regulated power supplies.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YPowerSupply object, corresponding to
     *         the first regulated power supply currently online, or a null pointer
     *         if there are none.
     */
    public static YPowerSupply FirstPowerSupplyInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("PowerSupply");
        if (next_hwid == null)  return null;
        return FindPowerSupplyInContext(yctx, next_hwid);
    }

    //--- (end of YPowerSupply implementation)
}

