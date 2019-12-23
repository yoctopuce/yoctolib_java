/*
 *
 *  $Id: YMultiSensController.java 38899 2019-12-20 17:21:03Z mvuilleu $
 *
 *  Implements FindMultiSensController(), the high-level API for MultiSensController functions
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

//--- (YMultiSensController return codes)
//--- (end of YMultiSensController return codes)
//--- (YMultiSensController yapiwrapper)
//--- (end of YMultiSensController yapiwrapper)
//--- (YMultiSensController class start)
/**
 *  YMultiSensController Class: Sensor chain configuration interface, available for instance in the
 * Yocto-Temperature-IR
 *
 * The YMultiSensController class allows you to setup a customized
 * sensor chain on devices featuring that functionality.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YMultiSensController extends YFunction
{
//--- (end of YMultiSensController class start)
//--- (YMultiSensController definitions)
    /**
     * invalid nSensors value
     */
    public static final int NSENSORS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid maxSensors value
     */
    public static final int MAXSENSORS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid maintenanceMode value
     */
    public static final int MAINTENANCEMODE_FALSE = 0;
    public static final int MAINTENANCEMODE_TRUE = 1;
    public static final int MAINTENANCEMODE_INVALID = -1;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _nSensors = NSENSORS_INVALID;
    protected int _maxSensors = MAXSENSORS_INVALID;
    protected int _maintenanceMode = MAINTENANCEMODE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackMultiSensController = null;

    /**
     * Deprecated UpdateCallback for MultiSensController
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YMultiSensController function, String functionValue);
    }

    /**
     * TimedReportCallback for MultiSensController
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YMultiSensController  function, YMeasure measure);
    }
    //--- (end of YMultiSensController definitions)


    /**
     *
     * @param func : functionid
     */
    protected YMultiSensController(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "MultiSensController";
        //--- (YMultiSensController attributes initialization)
        //--- (end of YMultiSensController attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YMultiSensController(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YMultiSensController implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("nSensors")) {
            _nSensors = json_val.getInt("nSensors");
        }
        if (json_val.has("maxSensors")) {
            _maxSensors = json_val.getInt("maxSensors");
        }
        if (json_val.has("maintenanceMode")) {
            _maintenanceMode = json_val.getInt("maintenanceMode") > 0 ? 1 : 0;
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the number of sensors to poll.
     *
     * @return an integer corresponding to the number of sensors to poll
     *
     * @throws YAPI_Exception on error
     */
    public int get_nSensors() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return NSENSORS_INVALID;
                }
            }
            res = _nSensors;
        }
        return res;
    }

    /**
     * Returns the number of sensors to poll.
     *
     * @return an integer corresponding to the number of sensors to poll
     *
     * @throws YAPI_Exception on error
     */
    public int getNSensors() throws YAPI_Exception
    {
        return get_nSensors();
    }

    /**
     * Changes the number of sensors to poll. Remember to call the
     * saveToFlash() method of the module if the
     * modification must be kept. It is recommended to restart the
     * device with  module->reboot() after modifying
     * (and saving) this settings
     *
     * @param newval : an integer corresponding to the number of sensors to poll
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_nSensors(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("nSensors",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of sensors to poll. Remember to call the
     * saveToFlash() method of the module if the
     * modification must be kept. It is recommended to restart the
     * device with  module->reboot() after modifying
     * (and saving) this settings
     *
     * @param newval : an integer corresponding to the number of sensors to poll
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setNSensors(int newval)  throws YAPI_Exception
    {
        return set_nSensors(newval);
    }

    /**
     * Returns the maximum configurable sensor count allowed on this device.
     *
     * @return an integer corresponding to the maximum configurable sensor count allowed on this device
     *
     * @throws YAPI_Exception on error
     */
    public int get_maxSensors() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return MAXSENSORS_INVALID;
                }
            }
            res = _maxSensors;
        }
        return res;
    }

    /**
     * Returns the maximum configurable sensor count allowed on this device.
     *
     * @return an integer corresponding to the maximum configurable sensor count allowed on this device
     *
     * @throws YAPI_Exception on error
     */
    public int getMaxSensors() throws YAPI_Exception
    {
        return get_maxSensors();
    }

    /**
     * Returns true when the device is in maintenance mode.
     *
     *  @return either YMultiSensController.MAINTENANCEMODE_FALSE or
     * YMultiSensController.MAINTENANCEMODE_TRUE, according to true when the device is in maintenance mode
     *
     * @throws YAPI_Exception on error
     */
    public int get_maintenanceMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return MAINTENANCEMODE_INVALID;
                }
            }
            res = _maintenanceMode;
        }
        return res;
    }

    /**
     * Returns true when the device is in maintenance mode.
     *
     *  @return either Y_MAINTENANCEMODE_FALSE or Y_MAINTENANCEMODE_TRUE, according to true when the device
     * is in maintenance mode
     *
     * @throws YAPI_Exception on error
     */
    public int getMaintenanceMode() throws YAPI_Exception
    {
        return get_maintenanceMode();
    }

    /**
     * Changes the device mode to enable maintenance and to stop sensor polling.
     * This way, the device does not automatically restart when it cannot
     * communicate with one of the sensors.
     *
     *  @param newval : either YMultiSensController.MAINTENANCEMODE_FALSE or
     *  YMultiSensController.MAINTENANCEMODE_TRUE, according to the device mode to enable maintenance and
     * to stop sensor polling
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_maintenanceMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("maintenanceMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the device mode to enable maintenance and to stop sensor polling.
     * This way, the device does not automatically restart when it cannot
     * communicate with one of the sensors.
     *
     *  @param newval : either Y_MAINTENANCEMODE_FALSE or Y_MAINTENANCEMODE_TRUE, according to the device
     * mode to enable maintenance and to stop sensor polling
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMaintenanceMode(int newval)  throws YAPI_Exception
    {
        return set_maintenanceMode(newval);
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
     * Retrieves a multi-sensor controller for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the multi-sensor controller is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMultiSensController.isOnline() to test if the multi-sensor controller is
     * indeed online at a given time. In case of ambiguity when looking for
     * a multi-sensor controller by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the multi-sensor controller, for instance
     *         YTEMPIR1.multiSensController.
     *
     * @return a YMultiSensController object allowing you to drive the multi-sensor controller.
     */
    public static YMultiSensController FindMultiSensController(String func)
    {
        YMultiSensController obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YMultiSensController) YFunction._FindFromCache("MultiSensController", func);
            if (obj == null) {
                obj = new YMultiSensController(func);
                YFunction._AddToCache("MultiSensController", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a multi-sensor controller for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the multi-sensor controller is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMultiSensController.isOnline() to test if the multi-sensor controller is
     * indeed online at a given time. In case of ambiguity when looking for
     * a multi-sensor controller by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the multi-sensor controller, for instance
     *         YTEMPIR1.multiSensController.
     *
     * @return a YMultiSensController object allowing you to drive the multi-sensor controller.
     */
    public static YMultiSensController FindMultiSensControllerInContext(YAPIContext yctx,String func)
    {
        YMultiSensController obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YMultiSensController) YFunction._FindFromCacheInContext(yctx, "MultiSensController", func);
            if (obj == null) {
                obj = new YMultiSensController(yctx, func);
                YFunction._AddToCache("MultiSensController", func, obj);
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
        _valueCallbackMultiSensController = callback;
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
        if (_valueCallbackMultiSensController != null) {
            _valueCallbackMultiSensController.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Configures the I2C address of the only sensor connected to the device.
     * It is recommended to put the the device in maintenance mode before
     * changing sensor addresses.  This method is only intended to work with a single
     * sensor connected to the device, if several sensors are connected, the result
     * is unpredictable.
     * Note that the device is probably expecting to find a string of sensors with specific
     * addresses. Check the device documentation to find out which addresses should be used.
     *
     * @param addr : new address of the connected sensor
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int setupAddress(int addr) throws YAPI_Exception
    {
        String cmd;
        cmd = String.format(Locale.US, "A%d",addr);
        return set_command(cmd);
    }

    /**
     * Continues the enumeration of multi-sensor controllers started using yFirstMultiSensController().
     * Caution: You can't make any assumption about the returned multi-sensor controllers order.
     * If you want to find a specific a multi-sensor controller, use MultiSensController.findMultiSensController()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YMultiSensController object, corresponding to
     *         a multi-sensor controller currently online, or a null pointer
     *         if there are no more multi-sensor controllers to enumerate.
     */
    public YMultiSensController nextMultiSensController()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindMultiSensControllerInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of multi-sensor controllers currently accessible.
     * Use the method YMultiSensController.nextMultiSensController() to iterate on
     * next multi-sensor controllers.
     *
     * @return a pointer to a YMultiSensController object, corresponding to
     *         the first multi-sensor controller currently online, or a null pointer
     *         if there are none.
     */
    public static YMultiSensController FirstMultiSensController()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("MultiSensController");
        if (next_hwid == null)  return null;
        return FindMultiSensControllerInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of multi-sensor controllers currently accessible.
     * Use the method YMultiSensController.nextMultiSensController() to iterate on
     * next multi-sensor controllers.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YMultiSensController object, corresponding to
     *         the first multi-sensor controller currently online, or a null pointer
     *         if there are none.
     */
    public static YMultiSensController FirstMultiSensControllerInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("MultiSensController");
        if (next_hwid == null)  return null;
        return FindMultiSensControllerInContext(yctx, next_hwid);
    }

    //--- (end of YMultiSensController implementation)
}

