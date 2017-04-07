/*********************************************************************
 *
 * $Id: YMultiAxisController.java 27053 2017-04-04 16:01:11Z seb $
 *
 * Implements FindMultiAxisController(), the high-level API for MultiAxisController functions
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
import java.util.Locale;

//--- (YMultiAxisController return codes)
//--- (end of YMultiAxisController return codes)
//--- (YMultiAxisController class start)
/**
 * YMultiAxisController Class: MultiAxisController function interface
 *
 * The Yoctopuce application programming interface allows you to drive a stepper motor.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YMultiAxisController extends YFunction
{
//--- (end of YMultiAxisController class start)
//--- (YMultiAxisController definitions)
    /**
     * invalid nAxis value
     */
    public static final int NAXIS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid globalState value
     */
    public static final int GLOBALSTATE_ABSENT = 0;
    public static final int GLOBALSTATE_ALERT = 1;
    public static final int GLOBALSTATE_HI_Z = 2;
    public static final int GLOBALSTATE_STOP = 3;
    public static final int GLOBALSTATE_RUN = 4;
    public static final int GLOBALSTATE_BATCH = 5;
    public static final int GLOBALSTATE_INVALID = -1;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _nAxis = NAXIS_INVALID;
    protected int _globalState = GLOBALSTATE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackMultiAxisController = null;

    /**
     * Deprecated UpdateCallback for MultiAxisController
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YMultiAxisController function, String functionValue);
    }

    /**
     * TimedReportCallback for MultiAxisController
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YMultiAxisController  function, YMeasure measure);
    }
    //--- (end of YMultiAxisController definitions)


    /**
     *
     * @param func : functionid
     */
    protected YMultiAxisController(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "MultiAxisController";
        //--- (YMultiAxisController attributes initialization)
        //--- (end of YMultiAxisController attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YMultiAxisController(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YMultiAxisController implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("nAxis")) {
            _nAxis = json_val.getInt("nAxis");
        }
        if (json_val.has("globalState")) {
            _globalState = json_val.getInt("globalState");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the number of synchronized controllers.
     *
     * @return an integer corresponding to the number of synchronized controllers
     *
     * @throws YAPI_Exception on error
     */
    public int get_nAxis() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return NAXIS_INVALID;
                }
            }
            res = _nAxis;
        }
        return res;
    }

    /**
     * Returns the number of synchronized controllers.
     *
     * @return an integer corresponding to the number of synchronized controllers
     *
     * @throws YAPI_Exception on error
     */
    public int getNAxis() throws YAPI_Exception
    {
        return get_nAxis();
    }

    /**
     * Changes the number of synchronized controllers.
     *
     * @param newval : an integer corresponding to the number of synchronized controllers
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_nAxis(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("nAxis",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of synchronized controllers.
     *
     * @param newval : an integer corresponding to the number of synchronized controllers
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setNAxis(int newval)  throws YAPI_Exception
    {
        return set_nAxis(newval);
    }

    /**
     * Returns the stepper motor set overall state.
     *
     *  @return a value among YMultiAxisController.GLOBALSTATE_ABSENT,
     *  YMultiAxisController.GLOBALSTATE_ALERT, YMultiAxisController.GLOBALSTATE_HI_Z,
     *  YMultiAxisController.GLOBALSTATE_STOP, YMultiAxisController.GLOBALSTATE_RUN and
     * YMultiAxisController.GLOBALSTATE_BATCH corresponding to the stepper motor set overall state
     *
     * @throws YAPI_Exception on error
     */
    public int get_globalState() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return GLOBALSTATE_INVALID;
                }
            }
            res = _globalState;
        }
        return res;
    }

    /**
     * Returns the stepper motor set overall state.
     *
     *  @return a value among Y_GLOBALSTATE_ABSENT, Y_GLOBALSTATE_ALERT, Y_GLOBALSTATE_HI_Z,
     *  Y_GLOBALSTATE_STOP, Y_GLOBALSTATE_RUN and Y_GLOBALSTATE_BATCH corresponding to the stepper motor
     * set overall state
     *
     * @throws YAPI_Exception on error
     */
    public int getGlobalState() throws YAPI_Exception
    {
        return get_globalState();
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
     * Retrieves a multi-axis controller for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the multi-axis controller is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMultiAxisController.isOnline() to test if the multi-axis controller is
     * indeed online at a given time. In case of ambiguity when looking for
     * a multi-axis controller by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the multi-axis controller
     *
     * @return a YMultiAxisController object allowing you to drive the multi-axis controller.
     */
    public static YMultiAxisController FindMultiAxisController(String func)
    {
        YMultiAxisController obj;
        synchronized (YAPI.class) {
            obj = (YMultiAxisController) YFunction._FindFromCache("MultiAxisController", func);
            if (obj == null) {
                obj = new YMultiAxisController(func);
                YFunction._AddToCache("MultiAxisController", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a multi-axis controller for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the multi-axis controller is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMultiAxisController.isOnline() to test if the multi-axis controller is
     * indeed online at a given time. In case of ambiguity when looking for
     * a multi-axis controller by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the multi-axis controller
     *
     * @return a YMultiAxisController object allowing you to drive the multi-axis controller.
     */
    public static YMultiAxisController FindMultiAxisControllerInContext(YAPIContext yctx,String func)
    {
        YMultiAxisController obj;
        synchronized (yctx) {
            obj = (YMultiAxisController) YFunction._FindFromCacheInContext(yctx, "MultiAxisController", func);
            if (obj == null) {
                obj = new YMultiAxisController(yctx, func);
                YFunction._AddToCache("MultiAxisController", func, obj);
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
        _valueCallbackMultiAxisController = callback;
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
        if (_valueCallbackMultiAxisController != null) {
            _valueCallbackMultiAxisController.yNewValue(this, value);
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
     * Reinitialize all controllers and clear all alert flags.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int reset() throws YAPI_Exception
    {
        return sendCommand("Z");
    }

    /**
     * Starts all motors backward at the specified speeds, to search for the motor home position.
     *
     * @param speed : desired speed for all axis, in steps per second.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int findHomePosition(ArrayList<Double> speed) throws YAPI_Exception
    {
        String cmd;
        int i;
        int ndim;
        ndim = speed.size();
        cmd = String.format(Locale.US, "H%d",(int) (double)Math.round(1000*speed.get(0).intValue()));
        i = 1;
        while (i + 1 < ndim) {
            cmd = String.format(Locale.US, "%s,%d", cmd,(int) (double)Math.round(1000*speed.get(i).intValue()));
            i = i + 1;
        }
        return sendCommand(cmd);
    }

    /**
     * Starts all motors synchronously to reach a given absolute position.
     * The time needed to reach the requested position will depend on the lowest
     * acceleration and max speed parameters configured for all motors.
     * The final position will be reached on all axis at the same time.
     *
     * @param absPos : absolute position, measured in steps from each origin.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int moveTo(ArrayList<Double> absPos) throws YAPI_Exception
    {
        String cmd;
        int i;
        int ndim;
        ndim = absPos.size();
        cmd = String.format(Locale.US, "M%d",(int) (double)Math.round(16*absPos.get(0).intValue()));
        i = 1;
        while (i + 1 < ndim) {
            cmd = String.format(Locale.US, "%s,%d", cmd,(int) (double)Math.round(16*absPos.get(i).intValue()));
            i = i + 1;
        }
        return sendCommand(cmd);
    }

    /**
     * Starts all motors synchronously to reach a given relative position.
     * The time needed to reach the requested position will depend on the lowest
     * acceleration and max speed parameters configured for all motors.
     * The final position will be reached on all axis at the same time.
     *
     * @param relPos : relative position, measured in steps from the current position.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int moveRel(ArrayList<Double> relPos) throws YAPI_Exception
    {
        String cmd;
        int i;
        int ndim;
        ndim = relPos.size();
        cmd = String.format(Locale.US, "m%d",(int) (double)Math.round(16*relPos.get(0).intValue()));
        i = 1;
        while (i + 1 < ndim) {
            cmd = String.format(Locale.US, "%s,%d", cmd,(int) (double)Math.round(16*relPos.get(i).intValue()));
            i = i + 1;
        }
        return sendCommand(cmd);
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
     * Continues the enumeration of multi-axis controllers started using yFirstMultiAxisController().
     *
     * @return a pointer to a YMultiAxisController object, corresponding to
     *         a multi-axis controller currently online, or a null pointer
     *         if there are no more multi-axis controllers to enumerate.
     */
    public YMultiAxisController nextMultiAxisController()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindMultiAxisControllerInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of multi-axis controllers currently accessible.
     * Use the method YMultiAxisController.nextMultiAxisController() to iterate on
     * next multi-axis controllers.
     *
     * @return a pointer to a YMultiAxisController object, corresponding to
     *         the first multi-axis controller currently online, or a null pointer
     *         if there are none.
     */
    public static YMultiAxisController FirstMultiAxisController()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("MultiAxisController");
        if (next_hwid == null)  return null;
        return FindMultiAxisControllerInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of multi-axis controllers currently accessible.
     * Use the method YMultiAxisController.nextMultiAxisController() to iterate on
     * next multi-axis controllers.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YMultiAxisController object, corresponding to
     *         the first multi-axis controller currently online, or a null pointer
     *         if there are none.
     */
    public static YMultiAxisController FirstMultiAxisControllerInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("MultiAxisController");
        if (next_hwid == null)  return null;
        return FindMultiAxisControllerInContext(yctx, next_hwid);
    }

    //--- (end of YMultiAxisController implementation)
}

