/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindMicroPython(), the high-level API for MicroPython functions
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
import java.util.ArrayList;
import java.util.Arrays;

//--- (YMicroPython return codes)
//--- (end of YMicroPython return codes)
//--- (YMicroPython yapiwrapper)
//--- (end of YMicroPython yapiwrapper)
//--- (YMicroPython class start)
/**
 * YMicroPython Class: MicroPython interpreter control interface
 *
 * The YMicroPython class provides control of the MicroPython interpreter
 * that can be found on some Yoctopuce devices.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YMicroPython extends YFunction
{
//--- (end of YMicroPython class start)
//--- (YMicroPython definitions)
    /**
     * invalid lastMsg value
     */
    public static final String LASTMSG_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid heapUsage value
     */
    public static final int HEAPUSAGE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid xheapUsage value
     */
    public static final int XHEAPUSAGE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid currentScript value
     */
    public static final String CURRENTSCRIPT_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid startupScript value
     */
    public static final String STARTUPSCRIPT_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid debugMode value
     */
    public static final int DEBUGMODE_OFF = 0;
    public static final int DEBUGMODE_ON = 1;
    public static final int DEBUGMODE_INVALID = -1;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected String _lastMsg = LASTMSG_INVALID;
    protected int _heapUsage = HEAPUSAGE_INVALID;
    protected int _xheapUsage = XHEAPUSAGE_INVALID;
    protected String _currentScript = CURRENTSCRIPT_INVALID;
    protected String _startupScript = STARTUPSCRIPT_INVALID;
    protected int _debugMode = DEBUGMODE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackMicroPython = null;
    protected YMicroPythonLogCallback _logCallback;
    protected boolean _isFirstCb;
    protected int _prevCbPos = 0;
    protected int _logPos = 0;
    protected String _prevPartialLog;

    /**
     * Deprecated UpdateCallback for MicroPython
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YMicroPython function, String functionValue);
    }

    /**
     * TimedReportCallback for MicroPython
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YMicroPython  function, YMeasure measure);
    }
    /**
     * Specialized event Callback for MicroPython
     */
    public interface YMicroPythonLogCallback
    {
        void logCallback(YMicroPython obj, String logline);
    }

    private UpdateCallback yInternalEventCallback = new UpdateCallback()
    {
        @Override
        public void yNewValue(YMicroPython obj, String value)
        {
            try {
                obj._internalEventHandler(value);
            } catch (YAPI_Exception e) {
                e.printStackTrace();
            }
        }
    };

    //--- (end of YMicroPython definitions)


    /**
     *
     * @param func : functionid
     */
    protected YMicroPython(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "MicroPython";
        //--- (YMicroPython attributes initialization)
        //--- (end of YMicroPython attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YMicroPython(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YMicroPython implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("lastMsg")) {
            _lastMsg = json_val.getString("lastMsg");
        }
        if (json_val.has("heapUsage")) {
            _heapUsage = json_val.getInt("heapUsage");
        }
        if (json_val.has("xheapUsage")) {
            _xheapUsage = json_val.getInt("xheapUsage");
        }
        if (json_val.has("currentScript")) {
            _currentScript = json_val.getString("currentScript");
        }
        if (json_val.has("startupScript")) {
            _startupScript = json_val.getString("startupScript");
        }
        if (json_val.has("debugMode")) {
            _debugMode = json_val.getInt("debugMode") > 0 ? 1 : 0;
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the last message produced by a python script.
     *
     * @return a string corresponding to the last message produced by a python script
     *
     * @throws YAPI_Exception on error
     */
    public String get_lastMsg() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LASTMSG_INVALID;
                }
            }
            res = _lastMsg;
        }
        return res;
    }

    /**
     * Returns the last message produced by a python script.
     *
     * @return a string corresponding to the last message produced by a python script
     *
     * @throws YAPI_Exception on error
     */
    public String getLastMsg() throws YAPI_Exception
    {
        return get_lastMsg();
    }

    /**
     * Returns the percentage of micropython main memory in use,
     * as observed at the end of the last garbage collection.
     *
     * @return an integer corresponding to the percentage of micropython main memory in use,
     *         as observed at the end of the last garbage collection
     *
     * @throws YAPI_Exception on error
     */
    public int get_heapUsage() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return HEAPUSAGE_INVALID;
                }
            }
            res = _heapUsage;
        }
        return res;
    }

    /**
     * Returns the percentage of micropython main memory in use,
     * as observed at the end of the last garbage collection.
     *
     * @return an integer corresponding to the percentage of micropython main memory in use,
     *         as observed at the end of the last garbage collection
     *
     * @throws YAPI_Exception on error
     */
    public int getHeapUsage() throws YAPI_Exception
    {
        return get_heapUsage();
    }

    /**
     * Returns the percentage of micropython external memory in use,
     * as observed at the end of the last garbage collection.
     *
     * @return an integer corresponding to the percentage of micropython external memory in use,
     *         as observed at the end of the last garbage collection
     *
     * @throws YAPI_Exception on error
     */
    public int get_xheapUsage() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return XHEAPUSAGE_INVALID;
                }
            }
            res = _xheapUsage;
        }
        return res;
    }

    /**
     * Returns the percentage of micropython external memory in use,
     * as observed at the end of the last garbage collection.
     *
     * @return an integer corresponding to the percentage of micropython external memory in use,
     *         as observed at the end of the last garbage collection
     *
     * @throws YAPI_Exception on error
     */
    public int getXheapUsage() throws YAPI_Exception
    {
        return get_xheapUsage();
    }

    /**
     * Returns the name of currently active script, if any.
     *
     * @return a string corresponding to the name of currently active script, if any
     *
     * @throws YAPI_Exception on error
     */
    public String get_currentScript() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENTSCRIPT_INVALID;
                }
            }
            res = _currentScript;
        }
        return res;
    }

    /**
     * Returns the name of currently active script, if any.
     *
     * @return a string corresponding to the name of currently active script, if any
     *
     * @throws YAPI_Exception on error
     */
    public String getCurrentScript() throws YAPI_Exception
    {
        return get_currentScript();
    }

    /**
     * Stops current running script, and/or selects a script to run immediately in a
     * fresh new environment. If the MicroPython interpreter is busy running a script,
     * this function will abort it immediately and reset the execution environment.
     * If a non-empty string is given as argument, the new script will be started.
     *
     * @param newval : a string
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_currentScript(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("currentScript",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Stops current running script, and/or selects a script to run immediately in a
     * fresh new environment. If the MicroPython interpreter is busy running a script,
     * this function will abort it immediately and reset the execution environment.
     * If a non-empty string is given as argument, the new script will be started.
     *
     * @param newval : a string
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrentScript(String newval)  throws YAPI_Exception
    {
        return set_currentScript(newval);
    }

    /**
     * Returns the name of the script to run when the device is powered on.
     *
     * @return a string corresponding to the name of the script to run when the device is powered on
     *
     * @throws YAPI_Exception on error
     */
    public String get_startupScript() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return STARTUPSCRIPT_INVALID;
                }
            }
            res = _startupScript;
        }
        return res;
    }

    /**
     * Returns the name of the script to run when the device is powered on.
     *
     * @return a string corresponding to the name of the script to run when the device is powered on
     *
     * @throws YAPI_Exception on error
     */
    public String getStartupScript() throws YAPI_Exception
    {
        return get_startupScript();
    }

    /**
     * Changes the script to run when the device is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the script to run when the device is powered on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_startupScript(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("startupScript",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the script to run when the device is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the script to run when the device is powered on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setStartupScript(String newval)  throws YAPI_Exception
    {
        return set_startupScript(newval);
    }

    /**
     * Returns the activation state of micropython debugging interface.
     *
     *  @return either YMicroPython.DEBUGMODE_OFF or YMicroPython.DEBUGMODE_ON, according to the activation
     * state of micropython debugging interface
     *
     * @throws YAPI_Exception on error
     */
    public int get_debugMode() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DEBUGMODE_INVALID;
                }
            }
            res = _debugMode;
        }
        return res;
    }

    /**
     * Returns the activation state of micropython debugging interface.
     *
     *  @return either YMicroPython.DEBUGMODE_OFF or YMicroPython.DEBUGMODE_ON, according to the activation
     * state of micropython debugging interface
     *
     * @throws YAPI_Exception on error
     */
    public int getDebugMode() throws YAPI_Exception
    {
        return get_debugMode();
    }

    /**
     * Changes the activation state of micropython debugging interface.
     *
     *  @param newval : either YMicroPython.DEBUGMODE_OFF or YMicroPython.DEBUGMODE_ON, according to the
     * activation state of micropython debugging interface
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_debugMode(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("debugMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the activation state of micropython debugging interface.
     *
     *  @param newval : either YMicroPython.DEBUGMODE_OFF or YMicroPython.DEBUGMODE_ON, according to the
     * activation state of micropython debugging interface
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setDebugMode(int newval)  throws YAPI_Exception
    {
        return set_debugMode(newval);
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
     * Retrieves a MicroPython interpreter for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the MicroPython interpreter is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMicroPython.isOnline() to test if the MicroPython interpreter is
     * indeed online at a given time. In case of ambiguity when looking for
     * a MicroPython interpreter by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the MicroPython interpreter, for instance
     *         MyDevice.microPython.
     *
     * @return a YMicroPython object allowing you to drive the MicroPython interpreter.
     */
    public static YMicroPython FindMicroPython(String func)
    {
        YMicroPython obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YMicroPython) YFunction._FindFromCache("MicroPython", func);
            if (obj == null) {
                obj = new YMicroPython(func);
                YFunction._AddToCache("MicroPython", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a MicroPython interpreter for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the MicroPython interpreter is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YMicroPython.isOnline() to test if the MicroPython interpreter is
     * indeed online at a given time. In case of ambiguity when looking for
     * a MicroPython interpreter by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the MicroPython interpreter, for instance
     *         MyDevice.microPython.
     *
     * @return a YMicroPython object allowing you to drive the MicroPython interpreter.
     */
    public static YMicroPython FindMicroPythonInContext(YAPIContext yctx,String func)
    {
        YMicroPython obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YMicroPython) YFunction._FindFromCacheInContext(yctx, "MicroPython", func);
            if (obj == null) {
                obj = new YMicroPython(yctx, func);
                YFunction._AddToCache("MicroPython", func, obj);
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
        _valueCallbackMicroPython = callback;
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
        if (_valueCallbackMicroPython != null) {
            _valueCallbackMicroPython.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Submit MicroPython code for execution in the interpreter.
     * If the MicroPython interpreter is busy, this function will
     * block until it becomes available. The code is then uploaded,
     * compiled and executed on the fly, without beeing stored on the device filesystem.
     *
     * There is no implicit reset of the MicroPython interpreter with
     * this function. Use method reset() if you need to start
     * from a fresh environment to run your code.
     *
     * Note that although MicroPython is mostly compatible with recent Python 3.x
     * interpreters, the limited ressources on the device impose some restrictions,
     * in particular regarding the libraries that can be used. Please refer to
     * the documentation for more details.
     *
     * @param codeName : name of the code file (used for error reporting only)
     * @param mpyCode : MicroPython code to compile and execute
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int eval(String codeName,String mpyCode) throws YAPI_Exception
    {
        String fullname;
        int res;
        fullname = String.format(Locale.US, "mpy:%s",codeName);
        res = _upload(fullname, (mpyCode).getBytes(_yapi._deviceCharset));
        return res;
    }

    /**
     * Stops current execution, and reset the MicroPython interpreter to initial state.
     * All global variables are cleared, and all imports are forgotten.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int reset() throws YAPI_Exception
    {
        int res;
        String state;

        res = set_command("Z");
        //noinspection DoubleNegation
        if (!(res == YAPI.SUCCESS)) { throw new YAPI_Exception(YAPI.IO_ERROR, "unable to trigger MicroPython reset");}
        // Wait until the reset is effective
        state = (get_advertisedValue()).substring(0, 1);
        while (!(state.equals("z"))) {
            YAPI.Sleep(50);
            state = (get_advertisedValue()).substring(0, 1);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Returns a string with last logs of the MicroPython interpreter.
     * This method return only logs that are still in the module.
     *
     * @return a string with last MicroPython logs.
     * @throws YAPI_Exception on error
     */
    public String get_lastLogs() throws YAPI_Exception
    {
        byte[] buff = new byte[0];
        int bufflen;
        String res;

        buff = _download("mpy.txt");
        bufflen = (buff).length - 1;
        while ((bufflen > 0) && ((buff[bufflen] & 0xff) != 64)) {
            bufflen = bufflen - 1;
        }
        res = (new String(buff, _yapi._deviceCharset)).substring(0, bufflen);
        return res;
    }

    /**
     * Registers a device log callback function. This callback will be called each time
     * microPython sends a new log message.
     *
     * @param callback : the callback function to invoke, or a null pointer.
     *         The callback function should take two arguments:
     *         the module object that emitted the log message,
     *         and the character string containing the log.
     * @throws YAPI_Exception on error
     */
    public int registerLogCallback(YMicroPythonLogCallback callback) throws YAPI_Exception
    {
        String serial;

        serial = get_serialNumber();
        if (serial.equals(YAPI.INVALID_STRING)) {
            return YAPI.DEVICE_NOT_FOUND;
        }
        _logCallback = callback;
        _isFirstCb = true;
        if (callback != null) {
            registerValueCallback(yInternalEventCallback);
        } else {
            registerValueCallback((UpdateCallback) null);
        }
        return 0;
    }

    public YMicroPythonLogCallback get_logCallback()
    {
        return _logCallback;
    }

    public int _internalEventHandler(String cbVal) throws YAPI_Exception
    {
        int cbPos;
        int cbDPos;
        String url;
        byte[] content = new byte[0];
        int endPos;
        String contentStr;
        ArrayList<String> msgArr = new ArrayList<>();
        int arrLen;
        String lenStr;
        int arrPos;
        String logMsg;
        // detect possible power cycle of the reader to clear event pointer
        cbPos = Integer.valueOf((cbVal).substring(1, 1 + cbVal.length()-1),16);
        cbDPos = ((cbPos - _prevCbPos) & 0xfffff);
        _prevCbPos = cbPos;
        if (cbDPos > 65536) {
            _logPos = 0;
        }
        if (!(_logCallback != null)) {
            return YAPI.SUCCESS;
        }
        if (_isFirstCb) {
            // use first emulated value callback caused by registerValueCallback:
            // to retrieve current logs position
            _logPos = 0;
            _prevPartialLog = "";
            url = "mpy.txt";
        } else {
            // load all messages since previous call
            url = String.format(Locale.US, "mpy.txt?pos=%d",_logPos);
        }

        content = _download(url);
        contentStr = new String(content, _yapi._deviceCharset);
        // look for new position indicator at end of logs
        endPos = (content).length - 1;
        while ((endPos >= 0) && ((content[endPos] & 0xff) != 64)) {
            endPos = endPos - 1;
        }
        //noinspection DoubleNegation
        if (!(endPos > 0)) { throw new YAPI_Exception(YAPI.IO_ERROR, "fail to download micropython logs");}
        lenStr = (contentStr).substring(endPos+1, endPos+1 + contentStr.length()-(endPos+1));
        // update processed event position pointer
        _logPos = YAPIContext._atoi(lenStr);
        if (_isFirstCb) {
            // don't generate callbacks log messages before call to registerLogCallback
            _isFirstCb = false;
            return YAPI.SUCCESS;
        }
        // now generate callbacks for each complete log line
        endPos = endPos - 1;
        //noinspection DoubleNegation
        if (!((content[endPos] & 0xff) == 10)) { throw new YAPI_Exception(YAPI.IO_ERROR, "fail to download micropython logs");}
        contentStr = (contentStr).substring(0, endPos);
        msgArr = new ArrayList<>(Arrays.asList(contentStr.split("\n")));
        arrLen = msgArr.size() - 1;
        if (arrLen > 0) {
            logMsg = String.format(Locale.US, "%s%s",_prevPartialLog,msgArr.get(0));
            if (_logCallback != null) {
                _logCallback.logCallback(this, logMsg);
            }
            _prevPartialLog = "";
            arrPos = 1;
            while (arrPos < arrLen) {
                logMsg = msgArr.get(arrPos);
                if (_logCallback != null) {
                    _logCallback.logCallback(this, logMsg);
                }
                arrPos = arrPos + 1;
            }
        }
        _prevPartialLog = String.format(Locale.US, "%s%s",_prevPartialLog,msgArr.get(arrLen));
        return YAPI.SUCCESS;
    }

    /**
     * Continues the enumeration of MicroPython interpreters started using yFirstMicroPython().
     * Caution: You can't make any assumption about the returned MicroPython interpreters order.
     * If you want to find a specific a MicroPython interpreter, use MicroPython.findMicroPython()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YMicroPython object, corresponding to
     *         a MicroPython interpreter currently online, or a null pointer
     *         if there are no more MicroPython interpreters to enumerate.
     */
    public YMicroPython nextMicroPython()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindMicroPythonInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of MicroPython interpreters currently accessible.
     * Use the method YMicroPython.nextMicroPython() to iterate on
     * next MicroPython interpreters.
     *
     * @return a pointer to a YMicroPython object, corresponding to
     *         the first MicroPython interpreter currently online, or a null pointer
     *         if there are none.
     */
    public static YMicroPython FirstMicroPython()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("MicroPython");
        if (next_hwid == null)  return null;
        return FindMicroPythonInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of MicroPython interpreters currently accessible.
     * Use the method YMicroPython.nextMicroPython() to iterate on
     * next MicroPython interpreters.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YMicroPython object, corresponding to
     *         the first MicroPython interpreter currently online, or a null pointer
     *         if there are none.
     */
    public static YMicroPython FirstMicroPythonInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("MicroPython");
        if (next_hwid == null)  return null;
        return FindMicroPythonInContext(yctx, next_hwid);
    }

    //--- (end of YMicroPython implementation)
}

