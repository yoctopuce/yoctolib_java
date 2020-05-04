/*
 *
 *  $Id: YI2cPort.java 39333 2020-01-30 10:05:40Z mvuilleu $
 *
 *  Implements FindI2cPort(), the high-level API for I2cPort functions
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

//--- (YI2cPort return codes)
//--- (end of YI2cPort return codes)
//--- (YI2cPort yapiwrapper)
//--- (end of YI2cPort yapiwrapper)
//--- (YI2cPort class start)
/**
 * YI2cPort Class: I2C port control interface, available for instance in the Yocto-I2C
 *
 * The YI2cPort classe allows you to fully drive a Yoctopuce I2C port.
 * It can be used to send and receive data, and to configure communication
 * parameters (baud rate, etc).
 * Note that Yoctopuce I2C ports are not exposed as virtual COM ports.
 * They are meant to be used in the same way as all Yoctopuce devices.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YI2cPort extends YFunction
{
//--- (end of YI2cPort class start)
//--- (YI2cPort definitions)
    /**
     * invalid rxCount value
     */
    public static final int RXCOUNT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid txCount value
     */
    public static final int TXCOUNT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid errCount value
     */
    public static final int ERRCOUNT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid rxMsgCount value
     */
    public static final int RXMSGCOUNT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid txMsgCount value
     */
    public static final int TXMSGCOUNT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid lastMsg value
     */
    public static final String LASTMSG_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid currentJob value
     */
    public static final String CURRENTJOB_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid startupJob value
     */
    public static final String STARTUPJOB_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid jobMaxTask value
     */
    public static final int JOBMAXTASK_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid jobMaxSize value
     */
    public static final int JOBMAXSIZE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid protocol value
     */
    public static final String PROTOCOL_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid i2cVoltageLevel value
     */
    public static final int I2CVOLTAGELEVEL_OFF = 0;
    public static final int I2CVOLTAGELEVEL_3V3 = 1;
    public static final int I2CVOLTAGELEVEL_1V8 = 2;
    public static final int I2CVOLTAGELEVEL_INVALID = -1;
    /**
     * invalid i2cMode value
     */
    public static final String I2CMODE_INVALID = YAPI.INVALID_STRING;
    protected int _rxCount = RXCOUNT_INVALID;
    protected int _txCount = TXCOUNT_INVALID;
    protected int _errCount = ERRCOUNT_INVALID;
    protected int _rxMsgCount = RXMSGCOUNT_INVALID;
    protected int _txMsgCount = TXMSGCOUNT_INVALID;
    protected String _lastMsg = LASTMSG_INVALID;
    protected String _currentJob = CURRENTJOB_INVALID;
    protected String _startupJob = STARTUPJOB_INVALID;
    protected int _jobMaxTask = JOBMAXTASK_INVALID;
    protected int _jobMaxSize = JOBMAXSIZE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected String _protocol = PROTOCOL_INVALID;
    protected int _i2cVoltageLevel = I2CVOLTAGELEVEL_INVALID;
    protected String _i2cMode = I2CMODE_INVALID;
    protected UpdateCallback _valueCallbackI2cPort = null;
    protected int _rxptr = 0;
    protected byte[] _rxbuff;
    protected int _rxbuffptr = 0;

    /**
     * Deprecated UpdateCallback for I2cPort
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YI2cPort function, String functionValue);
    }

    /**
     * TimedReportCallback for I2cPort
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YI2cPort  function, YMeasure measure);
    }
    //--- (end of YI2cPort definitions)


    /**
     *
     * @param func : functionid
     */
    protected YI2cPort(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "I2cPort";
        //--- (YI2cPort attributes initialization)
        //--- (end of YI2cPort attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YI2cPort(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YI2cPort implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("rxCount")) {
            _rxCount = json_val.getInt("rxCount");
        }
        if (json_val.has("txCount")) {
            _txCount = json_val.getInt("txCount");
        }
        if (json_val.has("errCount")) {
            _errCount = json_val.getInt("errCount");
        }
        if (json_val.has("rxMsgCount")) {
            _rxMsgCount = json_val.getInt("rxMsgCount");
        }
        if (json_val.has("txMsgCount")) {
            _txMsgCount = json_val.getInt("txMsgCount");
        }
        if (json_val.has("lastMsg")) {
            _lastMsg = json_val.getString("lastMsg");
        }
        if (json_val.has("currentJob")) {
            _currentJob = json_val.getString("currentJob");
        }
        if (json_val.has("startupJob")) {
            _startupJob = json_val.getString("startupJob");
        }
        if (json_val.has("jobMaxTask")) {
            _jobMaxTask = json_val.getInt("jobMaxTask");
        }
        if (json_val.has("jobMaxSize")) {
            _jobMaxSize = json_val.getInt("jobMaxSize");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        if (json_val.has("protocol")) {
            _protocol = json_val.getString("protocol");
        }
        if (json_val.has("i2cVoltageLevel")) {
            _i2cVoltageLevel = json_val.getInt("i2cVoltageLevel");
        }
        if (json_val.has("i2cMode")) {
            _i2cMode = json_val.getString("i2cMode");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the total number of bytes received since last reset.
     *
     * @return an integer corresponding to the total number of bytes received since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int get_rxCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return RXCOUNT_INVALID;
                }
            }
            res = _rxCount;
        }
        return res;
    }

    /**
     * Returns the total number of bytes received since last reset.
     *
     * @return an integer corresponding to the total number of bytes received since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int getRxCount() throws YAPI_Exception
    {
        return get_rxCount();
    }

    /**
     * Returns the total number of bytes transmitted since last reset.
     *
     * @return an integer corresponding to the total number of bytes transmitted since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int get_txCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return TXCOUNT_INVALID;
                }
            }
            res = _txCount;
        }
        return res;
    }

    /**
     * Returns the total number of bytes transmitted since last reset.
     *
     * @return an integer corresponding to the total number of bytes transmitted since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int getTxCount() throws YAPI_Exception
    {
        return get_txCount();
    }

    /**
     * Returns the total number of communication errors detected since last reset.
     *
     * @return an integer corresponding to the total number of communication errors detected since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int get_errCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ERRCOUNT_INVALID;
                }
            }
            res = _errCount;
        }
        return res;
    }

    /**
     * Returns the total number of communication errors detected since last reset.
     *
     * @return an integer corresponding to the total number of communication errors detected since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int getErrCount() throws YAPI_Exception
    {
        return get_errCount();
    }

    /**
     * Returns the total number of messages received since last reset.
     *
     * @return an integer corresponding to the total number of messages received since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int get_rxMsgCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return RXMSGCOUNT_INVALID;
                }
            }
            res = _rxMsgCount;
        }
        return res;
    }

    /**
     * Returns the total number of messages received since last reset.
     *
     * @return an integer corresponding to the total number of messages received since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int getRxMsgCount() throws YAPI_Exception
    {
        return get_rxMsgCount();
    }

    /**
     * Returns the total number of messages send since last reset.
     *
     * @return an integer corresponding to the total number of messages send since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int get_txMsgCount() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return TXMSGCOUNT_INVALID;
                }
            }
            res = _txMsgCount;
        }
        return res;
    }

    /**
     * Returns the total number of messages send since last reset.
     *
     * @return an integer corresponding to the total number of messages send since last reset
     *
     * @throws YAPI_Exception on error
     */
    public int getTxMsgCount() throws YAPI_Exception
    {
        return get_txMsgCount();
    }

    /**
     * Returns the latest message fully received (for Line and Frame protocols).
     *
     * @return a string corresponding to the latest message fully received (for Line and Frame protocols)
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
     * Returns the latest message fully received (for Line and Frame protocols).
     *
     * @return a string corresponding to the latest message fully received (for Line and Frame protocols)
     *
     * @throws YAPI_Exception on error
     */
    public String getLastMsg() throws YAPI_Exception
    {
        return get_lastMsg();
    }

    /**
     * Returns the name of the job file currently in use.
     *
     * @return a string corresponding to the name of the job file currently in use
     *
     * @throws YAPI_Exception on error
     */
    public String get_currentJob() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CURRENTJOB_INVALID;
                }
            }
            res = _currentJob;
        }
        return res;
    }

    /**
     * Returns the name of the job file currently in use.
     *
     * @return a string corresponding to the name of the job file currently in use
     *
     * @throws YAPI_Exception on error
     */
    public String getCurrentJob() throws YAPI_Exception
    {
        return get_currentJob();
    }

    /**
     * Selects a job file to run immediately. If an empty string is
     * given as argument, stops running current job file.
     *
     * @param newval : a string
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_currentJob(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("currentJob",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Selects a job file to run immediately. If an empty string is
     * given as argument, stops running current job file.
     *
     * @param newval : a string
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCurrentJob(String newval)  throws YAPI_Exception
    {
        return set_currentJob(newval);
    }

    /**
     * Returns the job file to use when the device is powered on.
     *
     * @return a string corresponding to the job file to use when the device is powered on
     *
     * @throws YAPI_Exception on error
     */
    public String get_startupJob() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return STARTUPJOB_INVALID;
                }
            }
            res = _startupJob;
        }
        return res;
    }

    /**
     * Returns the job file to use when the device is powered on.
     *
     * @return a string corresponding to the job file to use when the device is powered on
     *
     * @throws YAPI_Exception on error
     */
    public String getStartupJob() throws YAPI_Exception
    {
        return get_startupJob();
    }

    /**
     * Changes the job to use when the device is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the job to use when the device is powered on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_startupJob(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("startupJob",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the job to use when the device is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the job to use when the device is powered on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setStartupJob(String newval)  throws YAPI_Exception
    {
        return set_startupJob(newval);
    }

    /**
     * Returns the maximum number of tasks in a job that the device can handle.
     *
     * @return an integer corresponding to the maximum number of tasks in a job that the device can handle
     *
     * @throws YAPI_Exception on error
     */
    public int get_jobMaxTask() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration == 0) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return JOBMAXTASK_INVALID;
                }
            }
            res = _jobMaxTask;
        }
        return res;
    }

    /**
     * Returns the maximum number of tasks in a job that the device can handle.
     *
     * @return an integer corresponding to the maximum number of tasks in a job that the device can handle
     *
     * @throws YAPI_Exception on error
     */
    public int getJobMaxTask() throws YAPI_Exception
    {
        return get_jobMaxTask();
    }

    /**
     * Returns maximum size allowed for job files.
     *
     * @return an integer corresponding to maximum size allowed for job files
     *
     * @throws YAPI_Exception on error
     */
    public int get_jobMaxSize() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration == 0) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return JOBMAXSIZE_INVALID;
                }
            }
            res = _jobMaxSize;
        }
        return res;
    }

    /**
     * Returns maximum size allowed for job files.
     *
     * @return an integer corresponding to maximum size allowed for job files
     *
     * @throws YAPI_Exception on error
     */
    public int getJobMaxSize() throws YAPI_Exception
    {
        return get_jobMaxSize();
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
     * Returns the type of protocol used to send I2C messages, as a string.
     * Possible values are
     * "Line" for messages separated by LF or
     * "Char" for continuous stream of codes.
     *
     * @return a string corresponding to the type of protocol used to send I2C messages, as a string
     *
     * @throws YAPI_Exception on error
     */
    public String get_protocol() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return PROTOCOL_INVALID;
                }
            }
            res = _protocol;
        }
        return res;
    }

    /**
     * Returns the type of protocol used to send I2C messages, as a string.
     * Possible values are
     * "Line" for messages separated by LF or
     * "Char" for continuous stream of codes.
     *
     * @return a string corresponding to the type of protocol used to send I2C messages, as a string
     *
     * @throws YAPI_Exception on error
     */
    public String getProtocol() throws YAPI_Exception
    {
        return get_protocol();
    }

    /**
     * Changes the type of protocol used to send I2C messages.
     * Possible values are
     * "Line" for messages separated by LF or
     * "Char" for continuous stream of codes.
     * The suffix "/[wait]ms" can be added to reduce the transmit rate so that there
     * is always at lest the specified number of milliseconds between each message sent.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the type of protocol used to send I2C messages
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_protocol(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("protocol",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the type of protocol used to send I2C messages.
     * Possible values are
     * "Line" for messages separated by LF or
     * "Char" for continuous stream of codes.
     * The suffix "/[wait]ms" can be added to reduce the transmit rate so that there
     * is always at lest the specified number of milliseconds between each message sent.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the type of protocol used to send I2C messages
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setProtocol(String newval)  throws YAPI_Exception
    {
        return set_protocol(newval);
    }

    /**
     * Returns the voltage level used on the I2C bus.
     *
     *  @return a value among YI2cPort.I2CVOLTAGELEVEL_OFF, YI2cPort.I2CVOLTAGELEVEL_3V3 and
     * YI2cPort.I2CVOLTAGELEVEL_1V8 corresponding to the voltage level used on the I2C bus
     *
     * @throws YAPI_Exception on error
     */
    public int get_i2cVoltageLevel() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return I2CVOLTAGELEVEL_INVALID;
                }
            }
            res = _i2cVoltageLevel;
        }
        return res;
    }

    /**
     * Returns the voltage level used on the I2C bus.
     *
     *  @return a value among Y_I2CVOLTAGELEVEL_OFF, Y_I2CVOLTAGELEVEL_3V3 and Y_I2CVOLTAGELEVEL_1V8
     * corresponding to the voltage level used on the I2C bus
     *
     * @throws YAPI_Exception on error
     */
    public int getI2cVoltageLevel() throws YAPI_Exception
    {
        return get_i2cVoltageLevel();
    }

    /**
     * Changes the voltage level used on the I2C bus.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     *  @param newval : a value among YI2cPort.I2CVOLTAGELEVEL_OFF, YI2cPort.I2CVOLTAGELEVEL_3V3 and
     * YI2cPort.I2CVOLTAGELEVEL_1V8 corresponding to the voltage level used on the I2C bus
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_i2cVoltageLevel(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("i2cVoltageLevel",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage level used on the I2C bus.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     *  @param newval : a value among Y_I2CVOLTAGELEVEL_OFF, Y_I2CVOLTAGELEVEL_3V3 and
     * Y_I2CVOLTAGELEVEL_1V8 corresponding to the voltage level used on the I2C bus
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setI2cVoltageLevel(int newval)  throws YAPI_Exception
    {
        return set_i2cVoltageLevel(newval);
    }

    /**
     * Returns the I2C port communication parameters, as a string such as
     * "400kbps,2000ms,NoRestart". The string includes the baud rate, the
     * recovery delay after communications errors, and if needed the option
     * NoRestart to use a Stop/Start sequence instead of the
     * Restart state when performing read on the I2C bus.
     *
     * @return a string corresponding to the I2C port communication parameters, as a string such as
     *         "400kbps,2000ms,NoRestart"
     *
     * @throws YAPI_Exception on error
     */
    public String get_i2cMode() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return I2CMODE_INVALID;
                }
            }
            res = _i2cMode;
        }
        return res;
    }

    /**
     * Returns the I2C port communication parameters, as a string such as
     * "400kbps,2000ms,NoRestart". The string includes the baud rate, the
     * recovery delay after communications errors, and if needed the option
     * NoRestart to use a Stop/Start sequence instead of the
     * Restart state when performing read on the I2C bus.
     *
     * @return a string corresponding to the I2C port communication parameters, as a string such as
     *         "400kbps,2000ms,NoRestart"
     *
     * @throws YAPI_Exception on error
     */
    public String getI2cMode() throws YAPI_Exception
    {
        return get_i2cMode();
    }

    /**
     * Changes the I2C port communication parameters, with a string such as
     * "400kbps,2000ms". The string includes the baud rate, the
     * recovery delay after communications errors, and if needed the option
     * NoRestart to use a Stop/Start sequence instead of the
     * Restart state when performing read on the I2C bus.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the I2C port communication parameters, with a string such as
     *         "400kbps,2000ms"
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_i2cMode(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("i2cMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the I2C port communication parameters, with a string such as
     * "400kbps,2000ms". The string includes the baud rate, the
     * recovery delay after communications errors, and if needed the option
     * NoRestart to use a Stop/Start sequence instead of the
     * Restart state when performing read on the I2C bus.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the I2C port communication parameters, with a string such as
     *         "400kbps,2000ms"
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setI2cMode(String newval)  throws YAPI_Exception
    {
        return set_i2cMode(newval);
    }

    /**
     * Retrieves an I2C port for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the I2C port is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YI2cPort.isOnline() to test if the I2C port is
     * indeed online at a given time. In case of ambiguity when looking for
     * an I2C port by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the I2C port, for instance
     *         YI2CMK01.i2cPort.
     *
     * @return a YI2cPort object allowing you to drive the I2C port.
     */
    public static YI2cPort FindI2cPort(String func)
    {
        YI2cPort obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YI2cPort) YFunction._FindFromCache("I2cPort", func);
            if (obj == null) {
                obj = new YI2cPort(func);
                YFunction._AddToCache("I2cPort", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves an I2C port for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the I2C port is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YI2cPort.isOnline() to test if the I2C port is
     * indeed online at a given time. In case of ambiguity when looking for
     * an I2C port by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the I2C port, for instance
     *         YI2CMK01.i2cPort.
     *
     * @return a YI2cPort object allowing you to drive the I2C port.
     */
    public static YI2cPort FindI2cPortInContext(YAPIContext yctx,String func)
    {
        YI2cPort obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YI2cPort) YFunction._FindFromCacheInContext(yctx, "I2cPort", func);
            if (obj == null) {
                obj = new YI2cPort(yctx, func);
                YFunction._AddToCache("I2cPort", func, obj);
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
        _valueCallbackI2cPort = callback;
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
        if (_valueCallbackI2cPort != null) {
            _valueCallbackI2cPort.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    public int sendCommand(String text) throws YAPI_Exception
    {
        return set_command(text);
    }

    /**
     * Reads a single line (or message) from the receive buffer, starting at current stream position.
     * This function is intended to be used when the serial port is configured for a message protocol,
     * such as 'Line' mode or frame protocols.
     *
     * If data at current stream position is not available anymore in the receive buffer,
     * the function returns the oldest available line and moves the stream position just after.
     * If no new full line is received, the function returns an empty line.
     *
     * @return a string with a single line of text
     *
     * @throws YAPI_Exception on error
     */
    public String readLine() throws YAPI_Exception
    {
        String url;
        byte[] msgbin;
        ArrayList<String> msgarr = new ArrayList<>();
        int msglen;
        String res;

        url = String.format(Locale.US, "rxmsg.json?pos=%d&len=1&maxw=1",_rxptr);
        msgbin = _download(url);
        msgarr = _json_get_array(msgbin);
        msglen = msgarr.size();
        if (msglen == 0) {
            return "";
        }
        // last element of array is the new position
        msglen = msglen - 1;
        _rxptr = YAPIContext._atoi(msgarr.get(msglen));
        if (msglen == 0) {
            return "";
        }
        res = _json_get_string((msgarr.get(0)).getBytes());
        return res;
    }

    /**
     * Searches for incoming messages in the serial port receive buffer matching a given pattern,
     * starting at current position. This function will only compare and return printable characters
     * in the message strings. Binary protocols are handled as hexadecimal strings.
     *
     * The search returns all messages matching the expression provided as argument in the buffer.
     * If no matching message is found, the search waits for one up to the specified maximum timeout
     * (in milliseconds).
     *
     * @param pattern : a limited regular expression describing the expected message format,
     *         or an empty string if all messages should be returned (no filtering).
     *         When using binary protocols, the format applies to the hexadecimal
     *         representation of the message.
     * @param maxWait : the maximum number of milliseconds to wait for a message if none is found
     *         in the receive buffer.
     *
     * @return an array of strings containing the messages found, if any.
     *         Binary messages are converted to hexadecimal representation.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<String> readMessages(String pattern,int maxWait) throws YAPI_Exception
    {
        String url;
        byte[] msgbin;
        ArrayList<String> msgarr = new ArrayList<>();
        int msglen;
        ArrayList<String> res = new ArrayList<>();
        int idx;

        url = String.format(Locale.US, "rxmsg.json?pos=%d&maxw=%d&pat=%s", _rxptr, maxWait,pattern);
        msgbin = _download(url);
        msgarr = _json_get_array(msgbin);
        msglen = msgarr.size();
        if (msglen == 0) {
            return res;
        }
        // last element of array is the new position
        msglen = msglen - 1;
        _rxptr = YAPIContext._atoi(msgarr.get(msglen));
        idx = 0;
        while (idx < msglen) {
            res.add(_json_get_string((msgarr.get(idx)).getBytes()));
            idx = idx + 1;
        }
        return res;
    }

    /**
     * Changes the current internal stream position to the specified value. This function
     * does not affect the device, it only changes the value stored in the API object
     * for the next read operations.
     *
     * @param absPos : the absolute position index for next read operations.
     *
     * @return nothing.
     */
    public int read_seek(int absPos)
    {
        _rxptr = absPos;
        return YAPI.SUCCESS;
    }

    /**
     * Returns the current absolute stream position pointer of the API object.
     *
     * @return the absolute position index for next read operations.
     */
    public int read_tell()
    {
        return _rxptr;
    }

    /**
     * Returns the number of bytes available to read in the input buffer starting from the
     * current absolute stream position pointer of the API object.
     *
     * @return the number of bytes available to read
     */
    public int read_avail() throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int res;

        buff = _download(String.format(Locale.US, "rxcnt.bin?pos=%d",_rxptr));
        bufflen = (buff).length - 1;
        while ((bufflen > 0) && ((buff[bufflen] & 0xff) != 64)) {
            bufflen = bufflen - 1;
        }
        res = YAPIContext._atoi((new String(buff)).substring(0, bufflen));
        return res;
    }

    /**
     * Sends a text line query to the serial port, and reads the reply, if any.
     * This function is intended to be used when the serial port is configured for 'Line' protocol.
     *
     * @param query : the line query to send (without CR/LF)
     * @param maxWait : the maximum number of milliseconds to wait for a reply.
     *
     * @return the next text line received after sending the text query, as a string.
     *         Additional lines can be obtained by calling readLine or readMessages.
     *
     * @throws YAPI_Exception on error
     */
    public String queryLine(String query,int maxWait) throws YAPI_Exception
    {
        String url;
        byte[] msgbin;
        ArrayList<String> msgarr = new ArrayList<>();
        int msglen;
        String res;

        url = String.format(Locale.US, "rxmsg.json?len=1&maxw=%d&cmd=!%s", maxWait,_escapeAttr(query));
        msgbin = _download(url);
        msgarr = _json_get_array(msgbin);
        msglen = msgarr.size();
        if (msglen == 0) {
            return "";
        }
        // last element of array is the new position
        msglen = msglen - 1;
        _rxptr = YAPIContext._atoi(msgarr.get(msglen));
        if (msglen == 0) {
            return "";
        }
        res = _json_get_string((msgarr.get(0)).getBytes());
        return res;
    }

    /**
     * Sends a binary message to the serial port, and reads the reply, if any.
     * This function is intended to be used when the serial port is configured for
     * Frame-based protocol.
     *
     * @param hexString : the message to send, coded in hexadecimal
     * @param maxWait : the maximum number of milliseconds to wait for a reply.
     *
     * @return the next frame received after sending the message, as a hex string.
     *         Additional frames can be obtained by calling readHex or readMessages.
     *
     * @throws YAPI_Exception on error
     */
    public String queryHex(String hexString,int maxWait) throws YAPI_Exception
    {
        String url;
        byte[] msgbin;
        ArrayList<String> msgarr = new ArrayList<>();
        int msglen;
        String res;

        url = String.format(Locale.US, "rxmsg.json?len=1&maxw=%d&cmd=$%s", maxWait,hexString);
        msgbin = _download(url);
        msgarr = _json_get_array(msgbin);
        msglen = msgarr.size();
        if (msglen == 0) {
            return "";
        }
        // last element of array is the new position
        msglen = msglen - 1;
        _rxptr = YAPIContext._atoi(msgarr.get(msglen));
        if (msglen == 0) {
            return "";
        }
        res = _json_get_string((msgarr.get(0)).getBytes());
        return res;
    }

    /**
     * Saves the job definition string (JSON data) into a job file.
     * The job file can be later enabled using selectJob().
     *
     * @param jobfile : name of the job file to save on the device filesystem
     * @param jsonDef : a string containing a JSON definition of the job
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int uploadJob(String jobfile,String jsonDef) throws YAPI_Exception
    {
        _upload(jobfile, (jsonDef).getBytes());
        return YAPI.SUCCESS;
    }

    /**
     * Load and start processing the specified job file. The file must have
     * been previously created using the user interface or uploaded on the
     * device filesystem using the uploadJob() function.
     *
     * @param jobfile : name of the job file (on the device filesystem)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int selectJob(String jobfile) throws YAPI_Exception
    {
        return set_currentJob(jobfile);
    }

    /**
     * Clears the serial port buffer and resets counters to zero.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int reset() throws YAPI_Exception
    {
        _rxptr = 0;
        _rxbuffptr = 0;
        _rxbuff = new byte[0];

        return sendCommand("Z");
    }

    /**
     * Sends a one-way message (provided as a a binary buffer) to a device on the I2C bus.
     * This function checks and reports communication errors on the I2C bus.
     *
     * @param slaveAddr : the 7-bit address of the slave device (without the direction bit)
     * @param buff : the binary buffer to be sent
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int i2cSendBin(int slaveAddr,byte[] buff) throws YAPI_Exception
    {
        int nBytes;
        int idx;
        int val;
        String msg;
        String reply;
        msg = String.format(Locale.US, "@%02x:",slaveAddr);
        nBytes = (buff).length;
        idx = 0;
        while (idx < nBytes) {
            val = (buff[idx] & 0xff);
            msg = String.format(Locale.US, "%s%02x", msg,val);
            idx = idx + 1;
        }

        reply = queryLine(msg,1000);
        //noinspection DoubleNegation
        if (!((reply).length() > 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "No response from I2C device");}
        idx = (reply).indexOf("[N]!");
        //noinspection DoubleNegation
        if (!(idx < 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "No I2C ACK received");}
        idx = (reply).indexOf("!");
        //noinspection DoubleNegation
        if (!(idx < 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "I2C protocol error");}
        return YAPI.SUCCESS;
    }

    /**
     * Sends a one-way message (provided as a list of integer) to a device on the I2C bus.
     * This function checks and reports communication errors on the I2C bus.
     *
     * @param slaveAddr : the 7-bit address of the slave device (without the direction bit)
     * @param values : a list of data bytes to be sent
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int i2cSendArray(int slaveAddr,ArrayList<Integer> values) throws YAPI_Exception
    {
        int nBytes;
        int idx;
        int val;
        String msg;
        String reply;
        msg = String.format(Locale.US, "@%02x:",slaveAddr);
        nBytes = values.size();
        idx = 0;
        while (idx < nBytes) {
            val = values.get(idx).intValue();
            msg = String.format(Locale.US, "%s%02x", msg,val);
            idx = idx + 1;
        }

        reply = queryLine(msg,1000);
        //noinspection DoubleNegation
        if (!((reply).length() > 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "No response from I2C device");}
        idx = (reply).indexOf("[N]!");
        //noinspection DoubleNegation
        if (!(idx < 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "No I2C ACK received");}
        idx = (reply).indexOf("!");
        //noinspection DoubleNegation
        if (!(idx < 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "I2C protocol error");}
        return YAPI.SUCCESS;
    }

    /**
     * Sends a one-way message (provided as a a binary buffer) to a device on the I2C bus,
     * then read back the specified number of bytes from device.
     * This function checks and reports communication errors on the I2C bus.
     *
     * @param slaveAddr : the 7-bit address of the slave device (without the direction bit)
     * @param buff : the binary buffer to be sent
     * @param rcvCount : the number of bytes to receive once the data bytes are sent
     *
     * @return a list of bytes with the data received from slave device.
     *
     * @throws YAPI_Exception on error
     */
    public byte[] i2cSendAndReceiveBin(int slaveAddr,byte[] buff,int rcvCount) throws YAPI_Exception
    {
        int nBytes;
        int idx;
        int val;
        String msg;
        String reply;
        byte[] rcvbytes;
        msg = String.format(Locale.US, "@%02x:",slaveAddr);
        nBytes = (buff).length;
        idx = 0;
        while (idx < nBytes) {
            val = (buff[idx] & 0xff);
            msg = String.format(Locale.US, "%s%02x", msg,val);
            idx = idx + 1;
        }
        idx = 0;
        while (idx < rcvCount) {
            msg = String.format(Locale.US, "%sxx",msg);
            idx = idx + 1;
        }

        reply = queryLine(msg,1000);
        rcvbytes = new byte[0];
        //noinspection DoubleNegation
        if (!((reply).length() > 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "No response from I2C device");}
        idx = (reply).indexOf("[N]!");
        //noinspection DoubleNegation
        if (!(idx < 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "No I2C ACK received");}
        idx = (reply).indexOf("!");
        //noinspection DoubleNegation
        if (!(idx < 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "I2C protocol error");}
        reply = (reply).substring( (reply).length()-2*rcvCount,  (reply).length()-2*rcvCount + 2*rcvCount);
        rcvbytes = YAPIContext._hexStrToBin(reply);
        return rcvbytes;
    }

    /**
     * Sends a one-way message (provided as a list of integer) to a device on the I2C bus,
     * then read back the specified number of bytes from device.
     * This function checks and reports communication errors on the I2C bus.
     *
     * @param slaveAddr : the 7-bit address of the slave device (without the direction bit)
     * @param values : a list of data bytes to be sent
     * @param rcvCount : the number of bytes to receive once the data bytes are sent
     *
     * @return a list of bytes with the data received from slave device.
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<Integer> i2cSendAndReceiveArray(int slaveAddr,ArrayList<Integer> values,int rcvCount) throws YAPI_Exception
    {
        int nBytes;
        int idx;
        int val;
        String msg;
        String reply;
        byte[] rcvbytes;
        ArrayList<Integer> res = new ArrayList<>();
        msg = String.format(Locale.US, "@%02x:",slaveAddr);
        nBytes = values.size();
        idx = 0;
        while (idx < nBytes) {
            val = values.get(idx).intValue();
            msg = String.format(Locale.US, "%s%02x", msg,val);
            idx = idx + 1;
        }
        idx = 0;
        while (idx < rcvCount) {
            msg = String.format(Locale.US, "%sxx",msg);
            idx = idx + 1;
        }

        reply = queryLine(msg,1000);
        //noinspection DoubleNegation
        if (!((reply).length() > 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "No response from I2C device");}
        idx = (reply).indexOf("[N]!");
        //noinspection DoubleNegation
        if (!(idx < 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "No I2C ACK received");}
        idx = (reply).indexOf("!");
        //noinspection DoubleNegation
        if (!(idx < 0)) { throw new YAPI_Exception( YAPI.IO_ERROR,  "I2C protocol error");}
        reply = (reply).substring( (reply).length()-2*rcvCount,  (reply).length()-2*rcvCount + 2*rcvCount);
        rcvbytes = YAPIContext._hexStrToBin(reply);
        res.clear();
        idx = 0;
        while (idx < rcvCount) {
            val = (rcvbytes[idx] & 0xff);
            res.add(val);
            idx = idx + 1;
        }
        return res;
    }

    /**
     * Sends a text-encoded I2C code stream to the I2C bus, as is.
     * An I2C code stream is a string made of hexadecimal data bytes,
     * but that may also include the I2C state transitions code:
     * "{S}" to emit a start condition,
     * "{R}" for a repeated start condition,
     * "{P}" for a stop condition,
     * "xx" for receiving a data byte,
     * "{A}" to ack a data byte received and
     * "{N}" to nack a data byte received.
     * If a newline ("\n") is included in the stream, the message
     * will be terminated and a newline will also be added to the
     * receive stream.
     *
     * @param codes : the code stream to send
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeStr(String codes) throws YAPI_Exception
    {
        int bufflen;
        byte[] buff;
        int idx;
        int ch;
        buff = (codes).getBytes();
        bufflen = (buff).length;
        if (bufflen < 100) {
            // if string is pure text, we can send it as a simple command (faster)
            ch = 0x20;
            idx = 0;
            while ((idx < bufflen) && (ch != 0)) {
                ch = (buff[idx] & 0xff);
                if ((ch >= 0x20) && (ch < 0x7f)) {
                    idx = idx + 1;
                } else {
                    ch = 0;
                }
            }
            if (idx >= bufflen) {
                return sendCommand(String.format(Locale.US, "+%s",codes));
            }
        }
        // send string using file upload
        return _upload("txdata", buff);
    }

    /**
     * Sends a text-encoded I2C code stream to the I2C bus, and terminate
     * the message en relâchant le bus.
     * An I2C code stream is a string made of hexadecimal data bytes,
     * but that may also include the I2C state transitions code:
     * "{S}" to emit a start condition,
     * "{R}" for a repeated start condition,
     * "{P}" for a stop condition,
     * "xx" for receiving a data byte,
     * "{A}" to ack a data byte received and
     * "{N}" to nack a data byte received.
     * At the end of the stream, a stop condition is added if missing
     * and a newline is added to the receive buffer as well.
     *
     * @param codes : the code stream to send
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeLine(String codes) throws YAPI_Exception
    {
        int bufflen;
        byte[] buff;
        bufflen = (codes).length();
        if (bufflen < 100) {
            return sendCommand(String.format(Locale.US, "!%s",codes));
        }
        // send string using file upload
        buff = (String.format(Locale.US, "%s\n",codes)).getBytes();
        return _upload("txdata", buff);
    }

    /**
     * Sends a single byte to the I2C bus. Depending on the I2C bus state, the byte
     * will be interpreted as an address byte or a data byte.
     *
     * @param code : the byte to send
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeByte(int code) throws YAPI_Exception
    {
        return sendCommand(String.format(Locale.US, "+%02X",code));
    }

    /**
     * Sends a byte sequence (provided as a hexadecimal string) to the I2C bus.
     * Depending on the I2C bus state, the first byte will be interpreted as an
     * address byte or a data byte.
     *
     * @param hexString : a string of hexadecimal byte codes
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeHex(String hexString) throws YAPI_Exception
    {
        int bufflen;
        byte[] buff;
        bufflen = (hexString).length();
        if (bufflen < 100) {
            return sendCommand(String.format(Locale.US, "+%s",hexString));
        }
        buff = (hexString).getBytes();

        return _upload("txdata", buff);
    }

    /**
     * Sends a binary buffer to the I2C bus, as is.
     * Depending on the I2C bus state, the first byte will be interpreted
     * as an address byte or a data byte.
     *
     * @param buff : the binary buffer to send
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeBin(byte[] buff) throws YAPI_Exception
    {
        int nBytes;
        int idx;
        int val;
        String msg;
        msg = "";
        nBytes = (buff).length;
        idx = 0;
        while (idx < nBytes) {
            val = (buff[idx] & 0xff);
            msg = String.format(Locale.US, "%s%02x", msg,val);
            idx = idx + 1;
        }

        return writeHex(msg);
    }

    /**
     * Sends a byte sequence (provided as a list of bytes) to the I2C bus.
     * Depending on the I2C bus state, the first byte will be interpreted as an
     * address byte or a data byte.
     *
     * @param byteList : a list of byte codes
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeArray(ArrayList<Integer> byteList) throws YAPI_Exception
    {
        int nBytes;
        int idx;
        int val;
        String msg;
        msg = "";
        nBytes = byteList.size();
        idx = 0;
        while (idx < nBytes) {
            val = byteList.get(idx).intValue();
            msg = String.format(Locale.US, "%s%02x", msg,val);
            idx = idx + 1;
        }

        return writeHex(msg);
    }

    /**
     * Continues the enumeration of I2C ports started using yFirstI2cPort().
     * Caution: You can't make any assumption about the returned I2C ports order.
     * If you want to find a specific an I2C port, use I2cPort.findI2cPort()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YI2cPort object, corresponding to
     *         an I2C port currently online, or a null pointer
     *         if there are no more I2C ports to enumerate.
     */
    public YI2cPort nextI2cPort()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindI2cPortInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of I2C ports currently accessible.
     * Use the method YI2cPort.nextI2cPort() to iterate on
     * next I2C ports.
     *
     * @return a pointer to a YI2cPort object, corresponding to
     *         the first I2C port currently online, or a null pointer
     *         if there are none.
     */
    public static YI2cPort FirstI2cPort()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("I2cPort");
        if (next_hwid == null)  return null;
        return FindI2cPortInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of I2C ports currently accessible.
     * Use the method YI2cPort.nextI2cPort() to iterate on
     * next I2C ports.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YI2cPort object, corresponding to
     *         the first I2C port currently online, or a null pointer
     *         if there are none.
     */
    public static YI2cPort FirstI2cPortInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("I2cPort");
        if (next_hwid == null)  return null;
        return FindI2cPortInContext(yctx, next_hwid);
    }

    //--- (end of YI2cPort implementation)
}

