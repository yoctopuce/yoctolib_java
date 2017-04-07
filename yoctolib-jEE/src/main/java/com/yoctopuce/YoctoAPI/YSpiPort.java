/*********************************************************************
 *
 * $Id: YSpiPort.java 27108 2017-04-06 22:18:22Z seb $
 *
 * Implements FindSpiPort(), the high-level API for SpiPort functions
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
import java.util.ArrayList;

//--- (YSpiPort return codes)
//--- (end of YSpiPort return codes)
//--- (YSpiPort class start)
/**
 * YSpiPort Class: SPI Port function interface
 *
 * The SpiPort function interface allows you to fully drive a Yoctopuce
 * SPI port, to send and receive data, and to configure communication
 * parameters (baud rate, bit count, parity, flow control and protocol).
 * Note that Yoctopuce SPI ports are not exposed as virtual COM ports.
 * They are meant to be used in the same way as all Yoctopuce devices.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSpiPort extends YFunction
{
//--- (end of YSpiPort class start)
//--- (YSpiPort definitions)
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
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid voltageLevel value
     */
    public static final int VOLTAGELEVEL_OFF = 0;
    public static final int VOLTAGELEVEL_TTL3V = 1;
    public static final int VOLTAGELEVEL_TTL3VR = 2;
    public static final int VOLTAGELEVEL_TTL5V = 3;
    public static final int VOLTAGELEVEL_TTL5VR = 4;
    public static final int VOLTAGELEVEL_RS232 = 5;
    public static final int VOLTAGELEVEL_RS485 = 6;
    public static final int VOLTAGELEVEL_INVALID = -1;
    /**
     * invalid protocol value
     */
    public static final String PROTOCOL_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid spiMode value
     */
    public static final String SPIMODE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid ssPolarity value
     */
    public static final int SSPOLARITY_ACTIVE_LOW = 0;
    public static final int SSPOLARITY_ACTIVE_HIGH = 1;
    public static final int SSPOLARITY_INVALID = -1;
    /**
     * invalid shitftSampling value
     */
    public static final int SHITFTSAMPLING_OFF = 0;
    public static final int SHITFTSAMPLING_ON = 1;
    public static final int SHITFTSAMPLING_INVALID = -1;
    protected int _rxCount = RXCOUNT_INVALID;
    protected int _txCount = TXCOUNT_INVALID;
    protected int _errCount = ERRCOUNT_INVALID;
    protected int _rxMsgCount = RXMSGCOUNT_INVALID;
    protected int _txMsgCount = TXMSGCOUNT_INVALID;
    protected String _lastMsg = LASTMSG_INVALID;
    protected String _currentJob = CURRENTJOB_INVALID;
    protected String _startupJob = STARTUPJOB_INVALID;
    protected String _command = COMMAND_INVALID;
    protected int _voltageLevel = VOLTAGELEVEL_INVALID;
    protected String _protocol = PROTOCOL_INVALID;
    protected String _spiMode = SPIMODE_INVALID;
    protected int _ssPolarity = SSPOLARITY_INVALID;
    protected int _shitftSampling = SHITFTSAMPLING_INVALID;
    protected UpdateCallback _valueCallbackSpiPort = null;
    protected int _rxptr = 0;
    protected byte[] _rxbuff;
    protected int _rxbuffptr = 0;

    /**
     * Deprecated UpdateCallback for SpiPort
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YSpiPort function, String functionValue);
    }

    /**
     * TimedReportCallback for SpiPort
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YSpiPort  function, YMeasure measure);
    }
    //--- (end of YSpiPort definitions)


    /**
     *
     * @param func : functionid
     */
    protected YSpiPort(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "SpiPort";
        //--- (YSpiPort attributes initialization)
        //--- (end of YSpiPort attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YSpiPort(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YSpiPort implementation)
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
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        if (json_val.has("voltageLevel")) {
            _voltageLevel = json_val.getInt("voltageLevel");
        }
        if (json_val.has("protocol")) {
            _protocol = json_val.getString("protocol");
        }
        if (json_val.has("spiMode")) {
            _spiMode = json_val.getString("spiMode");
        }
        if (json_val.has("ssPolarity")) {
            _ssPolarity = json_val.getInt("ssPolarity") > 0 ? 1 : 0;
        }
        if (json_val.has("shitftSampling")) {
            _shitftSampling = json_val.getInt("shitftSampling") > 0 ? 1 : 0;
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
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
     * Returns the voltage level used on the serial line.
     *
     *  @return a value among YSpiPort.VOLTAGELEVEL_OFF, YSpiPort.VOLTAGELEVEL_TTL3V,
     *  YSpiPort.VOLTAGELEVEL_TTL3VR, YSpiPort.VOLTAGELEVEL_TTL5V, YSpiPort.VOLTAGELEVEL_TTL5VR,
     *  YSpiPort.VOLTAGELEVEL_RS232 and YSpiPort.VOLTAGELEVEL_RS485 corresponding to the voltage level used
     * on the serial line
     *
     * @throws YAPI_Exception on error
     */
    public int get_voltageLevel() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLTAGELEVEL_INVALID;
                }
            }
            res = _voltageLevel;
        }
        return res;
    }

    /**
     * Returns the voltage level used on the serial line.
     *
     *  @return a value among Y_VOLTAGELEVEL_OFF, Y_VOLTAGELEVEL_TTL3V, Y_VOLTAGELEVEL_TTL3VR,
     *  Y_VOLTAGELEVEL_TTL5V, Y_VOLTAGELEVEL_TTL5VR, Y_VOLTAGELEVEL_RS232 and Y_VOLTAGELEVEL_RS485
     * corresponding to the voltage level used on the serial line
     *
     * @throws YAPI_Exception on error
     */
    public int getVoltageLevel() throws YAPI_Exception
    {
        return get_voltageLevel();
    }

    /**
     * Changes the voltage type used on the serial line. Valid
     * values  will depend on the Yoctopuce device model featuring
     * the serial port feature.  Check your device documentation
     * to find out which values are valid for that specific model.
     * Trying to set an invalid value will have no effect.
     *
     *  @param newval : a value among YSpiPort.VOLTAGELEVEL_OFF, YSpiPort.VOLTAGELEVEL_TTL3V,
     *  YSpiPort.VOLTAGELEVEL_TTL3VR, YSpiPort.VOLTAGELEVEL_TTL5V, YSpiPort.VOLTAGELEVEL_TTL5VR,
     *  YSpiPort.VOLTAGELEVEL_RS232 and YSpiPort.VOLTAGELEVEL_RS485 corresponding to the voltage type used
     * on the serial line
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_voltageLevel(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("voltageLevel",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the voltage type used on the serial line. Valid
     * values  will depend on the Yoctopuce device model featuring
     * the serial port feature.  Check your device documentation
     * to find out which values are valid for that specific model.
     * Trying to set an invalid value will have no effect.
     *
     *  @param newval : a value among Y_VOLTAGELEVEL_OFF, Y_VOLTAGELEVEL_TTL3V, Y_VOLTAGELEVEL_TTL3VR,
     *  Y_VOLTAGELEVEL_TTL5V, Y_VOLTAGELEVEL_TTL5VR, Y_VOLTAGELEVEL_RS232 and Y_VOLTAGELEVEL_RS485
     * corresponding to the voltage type used on the serial line
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setVoltageLevel(int newval)  throws YAPI_Exception
    {
        return set_voltageLevel(newval);
    }

    /**
     * Returns the type of protocol used over the serial line, as a string.
     * Possible values are "Line" for ASCII messages separated by CR and/or LF,
     * "Frame:[timeout]ms" for binary messages separated by a delay time,
     * "Char" for a continuous ASCII stream or
     * "Byte" for a continuous binary stream.
     *
     * @return a string corresponding to the type of protocol used over the serial line, as a string
     *
     * @throws YAPI_Exception on error
     */
    public String get_protocol() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return PROTOCOL_INVALID;
                }
            }
            res = _protocol;
        }
        return res;
    }

    /**
     * Returns the type of protocol used over the serial line, as a string.
     * Possible values are "Line" for ASCII messages separated by CR and/or LF,
     * "Frame:[timeout]ms" for binary messages separated by a delay time,
     * "Char" for a continuous ASCII stream or
     * "Byte" for a continuous binary stream.
     *
     * @return a string corresponding to the type of protocol used over the serial line, as a string
     *
     * @throws YAPI_Exception on error
     */
    public String getProtocol() throws YAPI_Exception
    {
        return get_protocol();
    }

    /**
     * Changes the type of protocol used over the serial line.
     * Possible values are "Line" for ASCII messages separated by CR and/or LF,
     * "Frame:[timeout]ms" for binary messages separated by a delay time,
     * "Char" for a continuous ASCII stream or
     * "Byte" for a continuous binary stream.
     * The suffix "/[wait]ms" can be added to reduce the transmit rate so that there
     * is always at lest the specified number of milliseconds between each bytes sent.
     *
     * @param newval : a string corresponding to the type of protocol used over the serial line
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
     * Changes the type of protocol used over the serial line.
     * Possible values are "Line" for ASCII messages separated by CR and/or LF,
     * "Frame:[timeout]ms" for binary messages separated by a delay time,
     * "Char" for a continuous ASCII stream or
     * "Byte" for a continuous binary stream.
     * The suffix "/[wait]ms" can be added to reduce the transmit rate so that there
     * is always at lest the specified number of milliseconds between each bytes sent.
     *
     * @param newval : a string corresponding to the type of protocol used over the serial line
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
     * Returns the SPI port communication parameters, as a string such as
     * "125000,0,msb". The string includes the baud rate, the SPI mode (between
     * 0 and 3) and the bit order.
     *
     * @return a string corresponding to the SPI port communication parameters, as a string such as
     *         "125000,0,msb"
     *
     * @throws YAPI_Exception on error
     */
    public String get_spiMode() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SPIMODE_INVALID;
                }
            }
            res = _spiMode;
        }
        return res;
    }

    /**
     * Returns the SPI port communication parameters, as a string such as
     * "125000,0,msb". The string includes the baud rate, the SPI mode (between
     * 0 and 3) and the bit order.
     *
     * @return a string corresponding to the SPI port communication parameters, as a string such as
     *         "125000,0,msb"
     *
     * @throws YAPI_Exception on error
     */
    public String getSpiMode() throws YAPI_Exception
    {
        return get_spiMode();
    }

    /**
     * Changes the SPI port communication parameters, with a string such as
     * "125000,0,msb". The string includes the baud rate, the SPI mode (between
     * 0 and 3) and the bit order.
     *
     * @param newval : a string corresponding to the SPI port communication parameters, with a string such as
     *         "125000,0,msb"
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_spiMode(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("spiMode",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the SPI port communication parameters, with a string such as
     * "125000,0,msb". The string includes the baud rate, the SPI mode (between
     * 0 and 3) and the bit order.
     *
     * @param newval : a string corresponding to the SPI port communication parameters, with a string such as
     *         "125000,0,msb"
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSpiMode(String newval)  throws YAPI_Exception
    {
        return set_spiMode(newval);
    }

    /**
     * Returns the SS line polarity.
     *
     *  @return either YSpiPort.SSPOLARITY_ACTIVE_LOW or YSpiPort.SSPOLARITY_ACTIVE_HIGH, according to the
     * SS line polarity
     *
     * @throws YAPI_Exception on error
     */
    public int get_ssPolarity() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SSPOLARITY_INVALID;
                }
            }
            res = _ssPolarity;
        }
        return res;
    }

    /**
     * Returns the SS line polarity.
     *
     * @return either Y_SSPOLARITY_ACTIVE_LOW or Y_SSPOLARITY_ACTIVE_HIGH, according to the SS line polarity
     *
     * @throws YAPI_Exception on error
     */
    public int getSsPolarity() throws YAPI_Exception
    {
        return get_ssPolarity();
    }

    /**
     * Changes the SS line polarity.
     *
     *  @param newval : either YSpiPort.SSPOLARITY_ACTIVE_LOW or YSpiPort.SSPOLARITY_ACTIVE_HIGH, according
     * to the SS line polarity
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_ssPolarity(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("ssPolarity",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the SS line polarity.
     *
     * @param newval : either Y_SSPOLARITY_ACTIVE_LOW or Y_SSPOLARITY_ACTIVE_HIGH, according to the SS line polarity
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setSsPolarity(int newval)  throws YAPI_Exception
    {
        return set_ssPolarity(newval);
    }

    /**
     * Returns true when the SDI line phase is shifted with regards to the SDO line.
     *
     *  @return either YSpiPort.SHITFTSAMPLING_OFF or YSpiPort.SHITFTSAMPLING_ON, according to true when
     * the SDI line phase is shifted with regards to the SDO line
     *
     * @throws YAPI_Exception on error
     */
    public int get_shitftSampling() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SHITFTSAMPLING_INVALID;
                }
            }
            res = _shitftSampling;
        }
        return res;
    }

    /**
     * Returns true when the SDI line phase is shifted with regards to the SDO line.
     *
     *  @return either Y_SHITFTSAMPLING_OFF or Y_SHITFTSAMPLING_ON, according to true when the SDI line
     * phase is shifted with regards to the SDO line
     *
     * @throws YAPI_Exception on error
     */
    public int getShitftSampling() throws YAPI_Exception
    {
        return get_shitftSampling();
    }

    /**
     * Changes the SDI line sampling shift. When disabled, SDI line is
     * sampled in the middle of data output time. When enabled, SDI line is
     * samples at the end of data output time.
     *
     *  @param newval : either YSpiPort.SHITFTSAMPLING_OFF or YSpiPort.SHITFTSAMPLING_ON, according to the
     * SDI line sampling shift
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_shitftSampling(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("shitftSampling",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the SDI line sampling shift. When disabled, SDI line is
     * sampled in the middle of data output time. When enabled, SDI line is
     * samples at the end of data output time.
     *
     * @param newval : either Y_SHITFTSAMPLING_OFF or Y_SHITFTSAMPLING_ON, according to the SDI line sampling shift
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setShitftSampling(int newval)  throws YAPI_Exception
    {
        return set_shitftSampling(newval);
    }

    /**
     * Retrieves a SPI port for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the SPI port is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSpiPort.isOnline() to test if the SPI port is
     * indeed online at a given time. In case of ambiguity when looking for
     * a SPI port by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the SPI port
     *
     * @return a YSpiPort object allowing you to drive the SPI port.
     */
    public static YSpiPort FindSpiPort(String func)
    {
        YSpiPort obj;
        synchronized (YAPI.class) {
            obj = (YSpiPort) YFunction._FindFromCache("SpiPort", func);
            if (obj == null) {
                obj = new YSpiPort(func);
                YFunction._AddToCache("SpiPort", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a SPI port for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the SPI port is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSpiPort.isOnline() to test if the SPI port is
     * indeed online at a given time. In case of ambiguity when looking for
     * a SPI port by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the SPI port
     *
     * @return a YSpiPort object allowing you to drive the SPI port.
     */
    public static YSpiPort FindSpiPortInContext(YAPIContext yctx,String func)
    {
        YSpiPort obj;
        synchronized (yctx) {
            obj = (YSpiPort) YFunction._FindFromCacheInContext(yctx, "SpiPort", func);
            if (obj == null) {
                obj = new YSpiPort(yctx, func);
                YFunction._AddToCache("SpiPort", func, obj);
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
        _valueCallbackSpiPort = callback;
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
        if (_valueCallbackSpiPort != null) {
            _valueCallbackSpiPort.yNewValue(this, value);
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
     * Sends a single byte to the serial port.
     *
     * @param code : the byte to send
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeByte(int code) throws YAPI_Exception
    {
        return sendCommand(String.format(Locale.US, "$%02x",code));
    }

    /**
     * Sends an ASCII string to the serial port, as is.
     *
     * @param text : the text string to send
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeStr(String text) throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int idx;
        int ch;
        buff = (text).getBytes();
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
                return sendCommand(String.format(Locale.US, "+%s",text));
            }
        }
        // send string using file upload
        return _upload("txdata", buff);
    }

    /**
     * Sends a binary buffer to the serial port, as is.
     *
     * @param buff : the binary buffer to send
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeBin(byte[] buff) throws YAPI_Exception
    {
        return _upload("txdata", buff);
    }

    /**
     * Sends a byte sequence (provided as a list of bytes) to the serial port.
     *
     * @param byteList : a list of byte codes
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeArray(ArrayList<Integer> byteList) throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int idx;
        int hexb;
        int res;
        bufflen = byteList.size();
        buff = new byte[bufflen];
        idx = 0;
        while (idx < bufflen) {
            hexb = byteList.get(idx).intValue();
            buff[idx] = (byte)(hexb & 0xff);
            idx = idx + 1;
        }
        
        res = _upload("txdata", buff);
        return res;
    }

    /**
     * Sends a byte sequence (provided as a hexadecimal string) to the serial port.
     *
     * @param hexString : a string of hexadecimal byte codes
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeHex(String hexString) throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int idx;
        int hexb;
        int res;
        bufflen = (hexString).length();
        if (bufflen < 100) {
            return sendCommand(String.format(Locale.US, "$%s",hexString));
        }
        bufflen = ((bufflen) >> (1));
        buff = new byte[bufflen];
        idx = 0;
        while (idx < bufflen) {
            hexb = Integer.valueOf((hexString).substring( 2 * idx,  2 * idx + 2),16);
            buff[idx] = (byte)(hexb & 0xff);
            idx = idx + 1;
        }
        
        res = _upload("txdata", buff);
        return res;
    }

    /**
     * Sends an ASCII string to the serial port, followed by a line break (CR LF).
     *
     * @param text : the text string to send
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int writeLine(String text) throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int idx;
        int ch;
        buff = (String.format(Locale.US, "%s\r\n",text)).getBytes();
        bufflen = (buff).length-2;
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
                return sendCommand(String.format(Locale.US, "!%s",text));
            }
        }
        // send string using file upload
        return _upload("txdata", buff);
    }

    /**
     * Reads one byte from the receive buffer, starting at current stream position.
     * If data at current stream position is not available anymore in the receive buffer,
     * or if there is no data available yet, the function returns YAPI.NO_MORE_DATA.
     *
     * @return the next byte
     *
     * @throws YAPI_Exception on error
     */
    public int readByte() throws YAPI_Exception
    {
        int currpos;
        int reqlen;
        byte[] buff;
        int bufflen;
        int mult;
        int endpos;
        int res;
        
        // first check if we have the requested character in the look-ahead buffer
        bufflen = (_rxbuff).length;
        if ((_rxptr >= _rxbuffptr) && (_rxptr < _rxbuffptr+bufflen)) {
            res = (_rxbuff[_rxptr-_rxbuffptr] & 0xff);
            _rxptr = _rxptr + 1;
            return res;
        }
        
        // try to preload more than one byte to speed-up byte-per-byte access
        currpos = _rxptr;
        reqlen = 1024;
        buff = readBin(reqlen);
        bufflen = (buff).length;
        if (_rxptr == currpos+bufflen) {
            res = (buff[0] & 0xff);
            _rxptr = currpos+1;
            _rxbuffptr = currpos;
            _rxbuff = buff;
            return res;
        }
        // mixed bidirectional data, retry with a smaller block
        _rxptr = currpos;
        reqlen = 16;
        buff = readBin(reqlen);
        bufflen = (buff).length;
        if (_rxptr == currpos+bufflen) {
            res = (buff[0] & 0xff);
            _rxptr = currpos+1;
            _rxbuffptr = currpos;
            _rxbuff = buff;
            return res;
        }
        // still mixed, need to process character by character
        _rxptr = currpos;
        
        
        buff = _download(String.format(Locale.US, "rxdata.bin?pos=%d&len=1",_rxptr));
        bufflen = (buff).length - 1;
        endpos = 0;
        mult = 1;
        while ((bufflen > 0) && ((buff[bufflen] & 0xff) != 64)) {
            endpos = endpos + mult * ((buff[bufflen] & 0xff) - 48);
            mult = mult * 10;
            bufflen = bufflen - 1;
        }
        _rxptr = endpos;
        if (bufflen == 0) {
            return YAPI.NO_MORE_DATA;
        }
        res = (buff[0] & 0xff);
        return res;
    }

    /**
     * Reads data from the receive buffer as a string, starting at current stream position.
     * If data at current stream position is not available anymore in the receive buffer, the
     * function performs a short read.
     *
     * @param nChars : the maximum number of characters to read
     *
     * @return a string with receive buffer contents
     *
     * @throws YAPI_Exception on error
     */
    public String readStr(int nChars) throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int mult;
        int endpos;
        String res;
        if (nChars > 65535) {
            nChars = 65535;
        }
        
        buff = _download(String.format(Locale.US, "rxdata.bin?pos=%d&len=%d", _rxptr,nChars));
        bufflen = (buff).length - 1;
        endpos = 0;
        mult = 1;
        while ((bufflen > 0) && ((buff[bufflen] & 0xff) != 64)) {
            endpos = endpos + mult * ((buff[bufflen] & 0xff) - 48);
            mult = mult * 10;
            bufflen = bufflen - 1;
        }
        _rxptr = endpos;
        res = (new String(buff)).substring(0, bufflen);
        return res;
    }

    /**
     * Reads data from the receive buffer as a binary buffer, starting at current stream position.
     * If data at current stream position is not available anymore in the receive buffer, the
     * function performs a short read.
     *
     * @param nChars : the maximum number of bytes to read
     *
     * @return a binary object with receive buffer contents
     *
     * @throws YAPI_Exception on error
     */
    public byte[] readBin(int nChars) throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int mult;
        int endpos;
        int idx;
        byte[] res;
        if (nChars > 65535) {
            nChars = 65535;
        }
        
        buff = _download(String.format(Locale.US, "rxdata.bin?pos=%d&len=%d", _rxptr,nChars));
        bufflen = (buff).length - 1;
        endpos = 0;
        mult = 1;
        while ((bufflen > 0) && ((buff[bufflen] & 0xff) != 64)) {
            endpos = endpos + mult * ((buff[bufflen] & 0xff) - 48);
            mult = mult * 10;
            bufflen = bufflen - 1;
        }
        _rxptr = endpos;
        res = new byte[bufflen];
        idx = 0;
        while (idx < bufflen) {
            res[idx] = (byte)((buff[idx] & 0xff) & 0xff);
            idx = idx + 1;
        }
        return res;
    }

    /**
     * Reads data from the receive buffer as a list of bytes, starting at current stream position.
     * If data at current stream position is not available anymore in the receive buffer, the
     * function performs a short read.
     *
     * @param nChars : the maximum number of bytes to read
     *
     * @return a sequence of bytes with receive buffer contents
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<Integer> readArray(int nChars) throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int mult;
        int endpos;
        int idx;
        int b;
        ArrayList<Integer> res = new ArrayList<>();
        if (nChars > 65535) {
            nChars = 65535;
        }
        
        buff = _download(String.format(Locale.US, "rxdata.bin?pos=%d&len=%d", _rxptr,nChars));
        bufflen = (buff).length - 1;
        endpos = 0;
        mult = 1;
        while ((bufflen > 0) && ((buff[bufflen] & 0xff) != 64)) {
            endpos = endpos + mult * ((buff[bufflen] & 0xff) - 48);
            mult = mult * 10;
            bufflen = bufflen - 1;
        }
        _rxptr = endpos;
        res.clear();
        idx = 0;
        while (idx < bufflen) {
            b = (buff[idx] & 0xff);
            res.add(b);
            idx = idx + 1;
        }
        return res;
    }

    /**
     * Reads data from the receive buffer as a hexadecimal string, starting at current stream position.
     * If data at current stream position is not available anymore in the receive buffer, the
     * function performs a short read.
     *
     * @param nBytes : the maximum number of bytes to read
     *
     * @return a string with receive buffer contents, encoded in hexadecimal
     *
     * @throws YAPI_Exception on error
     */
    public String readHex(int nBytes) throws YAPI_Exception
    {
        byte[] buff;
        int bufflen;
        int mult;
        int endpos;
        int ofs;
        String res;
        if (nBytes > 65535) {
            nBytes = 65535;
        }
        
        buff = _download(String.format(Locale.US, "rxdata.bin?pos=%d&len=%d", _rxptr,nBytes));
        bufflen = (buff).length - 1;
        endpos = 0;
        mult = 1;
        while ((bufflen > 0) && ((buff[bufflen] & 0xff) != 64)) {
            endpos = endpos + mult * ((buff[bufflen] & 0xff) - 48);
            mult = mult * 10;
            bufflen = bufflen - 1;
        }
        _rxptr = endpos;
        res = "";
        ofs = 0;
        while (ofs + 3 < bufflen) {
            res = String.format(Locale.US, "%s%02x%02x%02x%02x", res, (buff[ofs] & 0xff), (buff[ofs + 1] & 0xff), (buff[ofs + 2] & 0xff),(buff[ofs + 3] & 0xff));
            ofs = ofs + 4;
        }
        while (ofs < bufflen) {
            res = String.format(Locale.US, "%s%02x", res,(buff[ofs] & 0xff));
            ofs = ofs + 1;
        }
        return res;
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
        
        url = String.format(Locale.US, "rxmsg.json?len=1&maxw=%d&cmd=!%s", maxWait,query);
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
     * Manually sets the state of the SS line. This function has no effect when
     * the SS line is handled automatically.
     *
     * @param val : 1 to turn SS active, 0 to release SS.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_SS(int val) throws YAPI_Exception
    {
        return sendCommand(String.format(Locale.US, "S%d",val));
    }

    /**
     * Continues the enumeration of SPI ports started using yFirstSpiPort().
     *
     * @return a pointer to a YSpiPort object, corresponding to
     *         a SPI port currently online, or a null pointer
     *         if there are no more SPI ports to enumerate.
     */
    public YSpiPort nextSpiPort()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindSpiPortInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of SPI ports currently accessible.
     * Use the method YSpiPort.nextSpiPort() to iterate on
     * next SPI ports.
     *
     * @return a pointer to a YSpiPort object, corresponding to
     *         the first SPI port currently online, or a null pointer
     *         if there are none.
     */
    public static YSpiPort FirstSpiPort()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("SpiPort");
        if (next_hwid == null)  return null;
        return FindSpiPortInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of SPI ports currently accessible.
     * Use the method YSpiPort.nextSpiPort() to iterate on
     * next SPI ports.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YSpiPort object, corresponding to
     *         the first SPI port currently online, or a null pointer
     *         if there are none.
     */
    public static YSpiPort FirstSpiPortInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("SpiPort");
        if (next_hwid == null)  return null;
        return FindSpiPortInContext(yctx, next_hwid);
    }

    //--- (end of YSpiPort implementation)
}

