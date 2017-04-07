/*********************************************************************
 *
 * $Id: YStepperMotor.java 27053 2017-04-04 16:01:11Z seb $
 *
 * Implements FindStepperMotor(), the high-level API for StepperMotor functions
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
import java.util.Locale;

//--- (YStepperMotor return codes)
//--- (end of YStepperMotor return codes)
//--- (YStepperMotor class start)
/**
 * YStepperMotor Class: StepperMotor function interface
 *
 * The Yoctopuce application programming interface allows you to drive a stepper motor.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YStepperMotor extends YFunction
{
//--- (end of YStepperMotor class start)
//--- (YStepperMotor definitions)
    /**
     * invalid motorState value
     */
    public static final int MOTORSTATE_ABSENT = 0;
    public static final int MOTORSTATE_ALERT = 1;
    public static final int MOTORSTATE_HI_Z = 2;
    public static final int MOTORSTATE_STOP = 3;
    public static final int MOTORSTATE_RUN = 4;
    public static final int MOTORSTATE_BATCH = 5;
    public static final int MOTORSTATE_INVALID = -1;
    /**
     * invalid diags value
     */
    public static final int DIAGS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid stepPos value
     */
    public static final double STEPPOS_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid speed value
     */
    public static final double SPEED_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid pullinSpeed value
     */
    public static final double PULLINSPEED_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid maxAccel value
     */
    public static final double MAXACCEL_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid maxSpeed value
     */
    public static final double MAXSPEED_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid stepping value
     */
    public static final int STEPPING_MICROSTEP16 = 0;
    public static final int STEPPING_MICROSTEP8 = 1;
    public static final int STEPPING_MICROSTEP4 = 2;
    public static final int STEPPING_HALFSTEP = 3;
    public static final int STEPPING_FULLSTEP = 4;
    public static final int STEPPING_INVALID = -1;
    /**
     * invalid overcurrent value
     */
    public static final int OVERCURRENT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid tCurrStop value
     */
    public static final int TCURRSTOP_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid tCurrRun value
     */
    public static final int TCURRRUN_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid alertMode value
     */
    public static final String ALERTMODE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid auxMode value
     */
    public static final String AUXMODE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid auxSignal value
     */
    public static final int AUXSIGNAL_INVALID = YAPI.INVALID_INT;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _motorState = MOTORSTATE_INVALID;
    protected int _diags = DIAGS_INVALID;
    protected double _stepPos = STEPPOS_INVALID;
    protected double _speed = SPEED_INVALID;
    protected double _pullinSpeed = PULLINSPEED_INVALID;
    protected double _maxAccel = MAXACCEL_INVALID;
    protected double _maxSpeed = MAXSPEED_INVALID;
    protected int _stepping = STEPPING_INVALID;
    protected int _overcurrent = OVERCURRENT_INVALID;
    protected int _tCurrStop = TCURRSTOP_INVALID;
    protected int _tCurrRun = TCURRRUN_INVALID;
    protected String _alertMode = ALERTMODE_INVALID;
    protected String _auxMode = AUXMODE_INVALID;
    protected int _auxSignal = AUXSIGNAL_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackStepperMotor = null;

    /**
     * Deprecated UpdateCallback for StepperMotor
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YStepperMotor function, String functionValue);
    }

    /**
     * TimedReportCallback for StepperMotor
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YStepperMotor  function, YMeasure measure);
    }
    //--- (end of YStepperMotor definitions)


    /**
     *
     * @param func : functionid
     */
    protected YStepperMotor(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "StepperMotor";
        //--- (YStepperMotor attributes initialization)
        //--- (end of YStepperMotor attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YStepperMotor(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YStepperMotor implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("motorState")) {
            _motorState = json_val.getInt("motorState");
        }
        if (json_val.has("diags")) {
            _diags = json_val.getInt("diags");
        }
        if (json_val.has("stepPos")) {
            _stepPos = Math.round(json_val.getDouble("stepPos") / 16.0);
        }
        if (json_val.has("speed")) {
            _speed = Math.round(json_val.getDouble("speed") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("pullinSpeed")) {
            _pullinSpeed = Math.round(json_val.getDouble("pullinSpeed") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("maxAccel")) {
            _maxAccel = Math.round(json_val.getDouble("maxAccel") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("maxSpeed")) {
            _maxSpeed = Math.round(json_val.getDouble("maxSpeed") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("stepping")) {
            _stepping = json_val.getInt("stepping");
        }
        if (json_val.has("overcurrent")) {
            _overcurrent = json_val.getInt("overcurrent");
        }
        if (json_val.has("tCurrStop")) {
            _tCurrStop = json_val.getInt("tCurrStop");
        }
        if (json_val.has("tCurrRun")) {
            _tCurrRun = json_val.getInt("tCurrRun");
        }
        if (json_val.has("alertMode")) {
            _alertMode = json_val.getString("alertMode");
        }
        if (json_val.has("auxMode")) {
            _auxMode = json_val.getString("auxMode");
        }
        if (json_val.has("auxSignal")) {
            _auxSignal = json_val.getInt("auxSignal");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the motor working state.
     *
     *  @return a value among YStepperMotor.MOTORSTATE_ABSENT, YStepperMotor.MOTORSTATE_ALERT,
     *  YStepperMotor.MOTORSTATE_HI_Z, YStepperMotor.MOTORSTATE_STOP, YStepperMotor.MOTORSTATE_RUN and
     * YStepperMotor.MOTORSTATE_BATCH corresponding to the motor working state
     *
     * @throws YAPI_Exception on error
     */
    public int get_motorState() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MOTORSTATE_INVALID;
                }
            }
            res = _motorState;
        }
        return res;
    }

    /**
     * Returns the motor working state.
     *
     *  @return a value among Y_MOTORSTATE_ABSENT, Y_MOTORSTATE_ALERT, Y_MOTORSTATE_HI_Z,
     * Y_MOTORSTATE_STOP, Y_MOTORSTATE_RUN and Y_MOTORSTATE_BATCH corresponding to the motor working state
     *
     * @throws YAPI_Exception on error
     */
    public int getMotorState() throws YAPI_Exception
    {
        return get_motorState();
    }

    /**
     * Returns the stepper motor controller diagnostics, as a bitmap.
     *
     * @return an integer corresponding to the stepper motor controller diagnostics, as a bitmap
     *
     * @throws YAPI_Exception on error
     */
    public int get_diags() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return DIAGS_INVALID;
                }
            }
            res = _diags;
        }
        return res;
    }

    /**
     * Returns the stepper motor controller diagnostics, as a bitmap.
     *
     * @return an integer corresponding to the stepper motor controller diagnostics, as a bitmap
     *
     * @throws YAPI_Exception on error
     */
    public int getDiags() throws YAPI_Exception
    {
        return get_diags();
    }

    /**
     * Changes the current logical motor position, measured in steps.
     * This command does not cause any motor move, as its purpose is only to setup
     * the origin of the position counter. The fractional part of the position,
     * that corresponds to the physical position of the rotor, is not changed.
     * To trigger a motor move, use methods moveTo() or moveRel()
     * instead.
     *
     * @param newval : a floating point number corresponding to the current logical motor position, measured in steps
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_stepPos(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Double.toString(Math.round(newval * 100.0)/100.0);
            _setAttr("stepPos",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current logical motor position, measured in steps.
     * This command does not cause any motor move, as its purpose is only to setup
     * the origin of the position counter. The fractional part of the position,
     * that corresponds to the physical position of the rotor, is not changed.
     * To trigger a motor move, use methods moveTo() or moveRel()
     * instead.
     *
     * @param newval : a floating point number corresponding to the current logical motor position, measured in steps
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setStepPos(double newval)  throws YAPI_Exception
    {
        return set_stepPos(newval);
    }

    /**
     * Returns the current logical motor position, measured in steps.
     * The value may include a fractional part when micro-stepping is in use.
     *
     * @return a floating point number corresponding to the current logical motor position, measured in steps
     *
     * @throws YAPI_Exception on error
     */
    public double get_stepPos() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return STEPPOS_INVALID;
                }
            }
            res = _stepPos;
        }
        return res;
    }

    /**
     * Returns the current logical motor position, measured in steps.
     * The value may include a fractional part when micro-stepping is in use.
     *
     * @return a floating point number corresponding to the current logical motor position, measured in steps
     *
     * @throws YAPI_Exception on error
     */
    public double getStepPos() throws YAPI_Exception
    {
        return get_stepPos();
    }

    /**
     * Returns current motor speed, measured in steps per second.
     * To change speed, use method changeSpeed().
     *
     * @return a floating point number corresponding to current motor speed, measured in steps per second
     *
     * @throws YAPI_Exception on error
     */
    public double get_speed() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SPEED_INVALID;
                }
            }
            res = _speed;
        }
        return res;
    }

    /**
     * Returns current motor speed, measured in steps per second.
     * To change speed, use method changeSpeed().
     *
     * @return a floating point number corresponding to current motor speed, measured in steps per second
     *
     * @throws YAPI_Exception on error
     */
    public double getSpeed() throws YAPI_Exception
    {
        return get_speed();
    }

    /**
     * Changes the motor speed immediately reachable from stop state, measured in steps per second.
     *
     *  @param newval : a floating point number corresponding to the motor speed immediately reachable from
     * stop state, measured in steps per second
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_pullinSpeed(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("pullinSpeed",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the motor speed immediately reachable from stop state, measured in steps per second.
     *
     *  @param newval : a floating point number corresponding to the motor speed immediately reachable from
     * stop state, measured in steps per second
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPullinSpeed(double newval)  throws YAPI_Exception
    {
        return set_pullinSpeed(newval);
    }

    /**
     * Returns the motor speed immediately reachable from stop state, measured in steps per second.
     *
     *  @return a floating point number corresponding to the motor speed immediately reachable from stop
     * state, measured in steps per second
     *
     * @throws YAPI_Exception on error
     */
    public double get_pullinSpeed() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return PULLINSPEED_INVALID;
                }
            }
            res = _pullinSpeed;
        }
        return res;
    }

    /**
     * Returns the motor speed immediately reachable from stop state, measured in steps per second.
     *
     *  @return a floating point number corresponding to the motor speed immediately reachable from stop
     * state, measured in steps per second
     *
     * @throws YAPI_Exception on error
     */
    public double getPullinSpeed() throws YAPI_Exception
    {
        return get_pullinSpeed();
    }

    /**
     * Changes the maximal motor acceleration, measured in steps per second^2.
     *
     *  @param newval : a floating point number corresponding to the maximal motor acceleration, measured
     * in steps per second^2
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_maxAccel(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("maxAccel",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximal motor acceleration, measured in steps per second^2.
     *
     *  @param newval : a floating point number corresponding to the maximal motor acceleration, measured
     * in steps per second^2
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMaxAccel(double newval)  throws YAPI_Exception
    {
        return set_maxAccel(newval);
    }

    /**
     * Returns the maximal motor acceleration, measured in steps per second^2.
     *
     * @return a floating point number corresponding to the maximal motor acceleration, measured in steps per second^2
     *
     * @throws YAPI_Exception on error
     */
    public double get_maxAccel() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MAXACCEL_INVALID;
                }
            }
            res = _maxAccel;
        }
        return res;
    }

    /**
     * Returns the maximal motor acceleration, measured in steps per second^2.
     *
     * @return a floating point number corresponding to the maximal motor acceleration, measured in steps per second^2
     *
     * @throws YAPI_Exception on error
     */
    public double getMaxAccel() throws YAPI_Exception
    {
        return get_maxAccel();
    }

    /**
     * Changes the maximal motor speed, measured in steps per second.
     *
     * @param newval : a floating point number corresponding to the maximal motor speed, measured in steps per second
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_maxSpeed(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Long.toString(Math.round(newval * 65536.0));
            _setAttr("maxSpeed",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the maximal motor speed, measured in steps per second.
     *
     * @param newval : a floating point number corresponding to the maximal motor speed, measured in steps per second
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMaxSpeed(double newval)  throws YAPI_Exception
    {
        return set_maxSpeed(newval);
    }

    /**
     * Returns the maximal motor speed, measured in steps per second.
     *
     * @return a floating point number corresponding to the maximal motor speed, measured in steps per second
     *
     * @throws YAPI_Exception on error
     */
    public double get_maxSpeed() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MAXSPEED_INVALID;
                }
            }
            res = _maxSpeed;
        }
        return res;
    }

    /**
     * Returns the maximal motor speed, measured in steps per second.
     *
     * @return a floating point number corresponding to the maximal motor speed, measured in steps per second
     *
     * @throws YAPI_Exception on error
     */
    public double getMaxSpeed() throws YAPI_Exception
    {
        return get_maxSpeed();
    }

    /**
     * Returns the stepping mode used to drive the motor.
     *
     *  @return a value among YStepperMotor.STEPPING_MICROSTEP16, YStepperMotor.STEPPING_MICROSTEP8,
     *  YStepperMotor.STEPPING_MICROSTEP4, YStepperMotor.STEPPING_HALFSTEP and
     * YStepperMotor.STEPPING_FULLSTEP corresponding to the stepping mode used to drive the motor
     *
     * @throws YAPI_Exception on error
     */
    public int get_stepping() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return STEPPING_INVALID;
                }
            }
            res = _stepping;
        }
        return res;
    }

    /**
     * Returns the stepping mode used to drive the motor.
     *
     *  @return a value among Y_STEPPING_MICROSTEP16, Y_STEPPING_MICROSTEP8, Y_STEPPING_MICROSTEP4,
     * Y_STEPPING_HALFSTEP and Y_STEPPING_FULLSTEP corresponding to the stepping mode used to drive the motor
     *
     * @throws YAPI_Exception on error
     */
    public int getStepping() throws YAPI_Exception
    {
        return get_stepping();
    }

    /**
     * Changes the stepping mode used to drive the motor.
     *
     *  @param newval : a value among YStepperMotor.STEPPING_MICROSTEP16,
     *  YStepperMotor.STEPPING_MICROSTEP8, YStepperMotor.STEPPING_MICROSTEP4,
     *  YStepperMotor.STEPPING_HALFSTEP and YStepperMotor.STEPPING_FULLSTEP corresponding to the stepping
     * mode used to drive the motor
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_stepping(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("stepping",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the stepping mode used to drive the motor.
     *
     *  @param newval : a value among Y_STEPPING_MICROSTEP16, Y_STEPPING_MICROSTEP8, Y_STEPPING_MICROSTEP4,
     * Y_STEPPING_HALFSTEP and Y_STEPPING_FULLSTEP corresponding to the stepping mode used to drive the motor
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setStepping(int newval)  throws YAPI_Exception
    {
        return set_stepping(newval);
    }

    /**
     * Returns the overcurrent alert and emergency stop threshold, measured in mA.
     *
     * @return an integer corresponding to the overcurrent alert and emergency stop threshold, measured in mA
     *
     * @throws YAPI_Exception on error
     */
    public int get_overcurrent() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return OVERCURRENT_INVALID;
                }
            }
            res = _overcurrent;
        }
        return res;
    }

    /**
     * Returns the overcurrent alert and emergency stop threshold, measured in mA.
     *
     * @return an integer corresponding to the overcurrent alert and emergency stop threshold, measured in mA
     *
     * @throws YAPI_Exception on error
     */
    public int getOvercurrent() throws YAPI_Exception
    {
        return get_overcurrent();
    }

    /**
     * Changes the overcurrent alert and emergency stop threshold, measured in mA.
     *
     * @param newval : an integer corresponding to the overcurrent alert and emergency stop threshold, measured in mA
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_overcurrent(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("overcurrent",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the overcurrent alert and emergency stop threshold, measured in mA.
     *
     * @param newval : an integer corresponding to the overcurrent alert and emergency stop threshold, measured in mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setOvercurrent(int newval)  throws YAPI_Exception
    {
        return set_overcurrent(newval);
    }

    /**
     * Returns the torque regulation current when the motor is stopped, measured in mA.
     *
     * @return an integer corresponding to the torque regulation current when the motor is stopped, measured in mA
     *
     * @throws YAPI_Exception on error
     */
    public int get_tCurrStop() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return TCURRSTOP_INVALID;
                }
            }
            res = _tCurrStop;
        }
        return res;
    }

    /**
     * Returns the torque regulation current when the motor is stopped, measured in mA.
     *
     * @return an integer corresponding to the torque regulation current when the motor is stopped, measured in mA
     *
     * @throws YAPI_Exception on error
     */
    public int getTCurrStop() throws YAPI_Exception
    {
        return get_tCurrStop();
    }

    /**
     * Changes the torque regulation current when the motor is stopped, measured in mA.
     *
     *  @param newval : an integer corresponding to the torque regulation current when the motor is
     * stopped, measured in mA
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_tCurrStop(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("tCurrStop",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the torque regulation current when the motor is stopped, measured in mA.
     *
     *  @param newval : an integer corresponding to the torque regulation current when the motor is
     * stopped, measured in mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setTCurrStop(int newval)  throws YAPI_Exception
    {
        return set_tCurrStop(newval);
    }

    /**
     * Returns the torque regulation current when the motor is running, measured in mA.
     *
     * @return an integer corresponding to the torque regulation current when the motor is running, measured in mA
     *
     * @throws YAPI_Exception on error
     */
    public int get_tCurrRun() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return TCURRRUN_INVALID;
                }
            }
            res = _tCurrRun;
        }
        return res;
    }

    /**
     * Returns the torque regulation current when the motor is running, measured in mA.
     *
     * @return an integer corresponding to the torque regulation current when the motor is running, measured in mA
     *
     * @throws YAPI_Exception on error
     */
    public int getTCurrRun() throws YAPI_Exception
    {
        return get_tCurrRun();
    }

    /**
     * Changes the torque regulation current when the motor is running, measured in mA.
     *
     *  @param newval : an integer corresponding to the torque regulation current when the motor is
     * running, measured in mA
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_tCurrRun(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("tCurrRun",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the torque regulation current when the motor is running, measured in mA.
     *
     *  @param newval : an integer corresponding to the torque regulation current when the motor is
     * running, measured in mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setTCurrRun(int newval)  throws YAPI_Exception
    {
        return set_tCurrRun(newval);
    }

    public String get_alertMode() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return ALERTMODE_INVALID;
                }
            }
            res = _alertMode;
        }
        return res;
    }

    public int set_alertMode(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("alertMode",rest_val);
        }
        return YAPI.SUCCESS;
    }


    public String get_auxMode() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return AUXMODE_INVALID;
                }
            }
            res = _auxMode;
        }
        return res;
    }

    public int set_auxMode(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("auxMode",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Returns the current value of the signal generated on the auxiliary output.
     *
     * @return an integer corresponding to the current value of the signal generated on the auxiliary output
     *
     * @throws YAPI_Exception on error
     */
    public int get_auxSignal() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return AUXSIGNAL_INVALID;
                }
            }
            res = _auxSignal;
        }
        return res;
    }

    /**
     * Returns the current value of the signal generated on the auxiliary output.
     *
     * @return an integer corresponding to the current value of the signal generated on the auxiliary output
     *
     * @throws YAPI_Exception on error
     */
    public int getAuxSignal() throws YAPI_Exception
    {
        return get_auxSignal();
    }

    /**
     * Changes the value of the signal generated on the auxiliary output.
     * Acceptable values depend on the auxiliary output signal type configured.
     *
     * @param newval : an integer corresponding to the value of the signal generated on the auxiliary output
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_auxSignal(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("auxSignal",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the value of the signal generated on the auxiliary output.
     * Acceptable values depend on the auxiliary output signal type configured.
     *
     * @param newval : an integer corresponding to the value of the signal generated on the auxiliary output
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setAuxSignal(int newval)  throws YAPI_Exception
    {
        return set_auxSignal(newval);
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
     * Retrieves a stepper motor for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the stepper motor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YStepperMotor.isOnline() to test if the stepper motor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a stepper motor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the stepper motor
     *
     * @return a YStepperMotor object allowing you to drive the stepper motor.
     */
    public static YStepperMotor FindStepperMotor(String func)
    {
        YStepperMotor obj;
        synchronized (YAPI.class) {
            obj = (YStepperMotor) YFunction._FindFromCache("StepperMotor", func);
            if (obj == null) {
                obj = new YStepperMotor(func);
                YFunction._AddToCache("StepperMotor", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a stepper motor for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the stepper motor is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YStepperMotor.isOnline() to test if the stepper motor is
     * indeed online at a given time. In case of ambiguity when looking for
     * a stepper motor by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the stepper motor
     *
     * @return a YStepperMotor object allowing you to drive the stepper motor.
     */
    public static YStepperMotor FindStepperMotorInContext(YAPIContext yctx,String func)
    {
        YStepperMotor obj;
        synchronized (yctx) {
            obj = (YStepperMotor) YFunction._FindFromCacheInContext(yctx, "StepperMotor", func);
            if (obj == null) {
                obj = new YStepperMotor(yctx, func);
                YFunction._AddToCache("StepperMotor", func, obj);
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
        _valueCallbackStepperMotor = callback;
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
        if (_valueCallbackStepperMotor != null) {
            _valueCallbackStepperMotor.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    public int sendCommand(String command) throws YAPI_Exception
    {
        return set_command(command);
    }

    /**
     * Reinitialize the controller and clear all alert flags.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int reset() throws YAPI_Exception
    {
        return sendCommand("Z");
    }

    /**
     * Starts the motor backward at the specified speed, to search for the motor home position.
     *
     * @param speed : desired speed, in steps per second.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int findHomePosition(double speed) throws YAPI_Exception
    {
        return sendCommand(String.format(Locale.US, "H%d",(int) (double)Math.round(1000*speed)));
    }

    /**
     * Starts the motor at a given speed. The time needed to reach the requested speed
     * will depend on the acceleration parameters configured for the motor.
     *
     * @param speed : desired speed, in steps per second. The minimal non-zero speed
     *         is 0.001 pulse per second.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int changeSpeed(double speed) throws YAPI_Exception
    {
        return sendCommand(String.format(Locale.US, "R%d",(int) (double)Math.round(1000*speed)));
    }

    /**
     * Starts the motor to reach a given absolute position. The time needed to reach the requested
     * position will depend on the acceleration and max speed parameters configured for
     * the motor.
     *
     * @param absPos : absolute position, measured in steps from the origin.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int moveTo(double absPos) throws YAPI_Exception
    {
        return sendCommand(String.format(Locale.US, "M%d",(int) (double)Math.round(16*absPos)));
    }

    /**
     * Starts the motor to reach a given relative position. The time needed to reach the requested
     * position will depend on the acceleration and max speed parameters configured for
     * the motor.
     *
     * @param relPos : relative position, measured in steps from the current position.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int moveRel(double relPos) throws YAPI_Exception
    {
        return sendCommand(String.format(Locale.US, "m%d",(int) (double)Math.round(16*relPos)));
    }

    /**
     * Keep the motor in the same state for the specified amount of time, before processing next command.
     *
     * @param waitMs : wait time, specified in milliseconds.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int pause(int waitMs) throws YAPI_Exception
    {
        return sendCommand(String.format(Locale.US, "_%d",waitMs));
    }

    /**
     * Stops the motor with an emergency alert, without taking any additional precaution.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int emergencyStop() throws YAPI_Exception
    {
        return sendCommand("!");
    }

    /**
     * Move one step in the direction opposite the direction set when the most recent alert was raised.
     * The move occures even if the system is still in alert mode (end switch depressed). Caution.
     * use this function with great care as it may cause mechanical damages !
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int alertStepOut() throws YAPI_Exception
    {
        return sendCommand(".");
    }

    /**
     * Stops the motor smoothly as soon as possible, without waiting for ongoing move completion.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int abortAndBrake() throws YAPI_Exception
    {
        return sendCommand("B");
    }

    /**
     * Turn the controller into Hi-Z mode immediately, without waiting for ongoing move completion.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int abortAndHiZ() throws YAPI_Exception
    {
        return sendCommand("z");
    }

    /**
     * Continues the enumeration of stepper motors started using yFirstStepperMotor().
     *
     * @return a pointer to a YStepperMotor object, corresponding to
     *         a stepper motor currently online, or a null pointer
     *         if there are no more stepper motors to enumerate.
     */
    public YStepperMotor nextStepperMotor()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindStepperMotorInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of stepper motors currently accessible.
     * Use the method YStepperMotor.nextStepperMotor() to iterate on
     * next stepper motors.
     *
     * @return a pointer to a YStepperMotor object, corresponding to
     *         the first stepper motor currently online, or a null pointer
     *         if there are none.
     */
    public static YStepperMotor FirstStepperMotor()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("StepperMotor");
        if (next_hwid == null)  return null;
        return FindStepperMotorInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of stepper motors currently accessible.
     * Use the method YStepperMotor.nextStepperMotor() to iterate on
     * next stepper motors.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YStepperMotor object, corresponding to
     *         the first stepper motor currently online, or a null pointer
     *         if there are none.
     */
    public static YStepperMotor FirstStepperMotorInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("StepperMotor");
        if (next_hwid == null)  return null;
        return FindStepperMotorInContext(yctx, next_hwid);
    }

    //--- (end of YStepperMotor implementation)
}

