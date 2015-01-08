/*********************************************************************
 *
 * $Id: YBuzzer.java 18762 2014-12-16 16:00:39Z seb $
 *
 * Implements FindBuzzer(), the high-level API for Buzzer functions
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

//--- (YBuzzer return codes)
//--- (end of YBuzzer return codes)
//--- (YBuzzer class start)
/**
 * YBuzzer Class: Buzzer function interface
 *
 * The Yoctopuce application programming interface allows you to
 * choose the frequency and volume at which the buzzer must sound.
 * You can also pre-program a play sequence.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YBuzzer extends YFunction
{
//--- (end of YBuzzer class start)
//--- (YBuzzer definitions)
    /**
     * invalid frequency value
     */
    public static final double FREQUENCY_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid volume value
     */
    public static final int VOLUME_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid playSeqSize value
     */
    public static final int PLAYSEQSIZE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid playSeqMaxSize value
     */
    public static final int PLAYSEQMAXSIZE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid playSeqSignature value
     */
    public static final int PLAYSEQSIGNATURE_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected double _frequency = FREQUENCY_INVALID;
    protected int _volume = VOLUME_INVALID;
    protected int _playSeqSize = PLAYSEQSIZE_INVALID;
    protected int _playSeqMaxSize = PLAYSEQMAXSIZE_INVALID;
    protected int _playSeqSignature = PLAYSEQSIGNATURE_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackBuzzer = null;

    /**
     * Deprecated UpdateCallback for Buzzer
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YBuzzer function, String functionValue);
    }

    /**
     * TimedReportCallback for Buzzer
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YBuzzer  function, YMeasure measure);
    }
    //--- (end of YBuzzer definitions)


    /**
     *
     * @param func : functionid
     */
    protected YBuzzer(String func)
    {
        super(func);
        _className = "Buzzer";
        //--- (YBuzzer attributes initialization)
        //--- (end of YBuzzer attributes initialization)
    }

    //--- (YBuzzer implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("frequency")) {
            _frequency = Math.round(json_val.getDouble("frequency") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("volume")) {
            _volume = json_val.getInt("volume");
        }
        if (json_val.has("playSeqSize")) {
            _playSeqSize = json_val.getInt("playSeqSize");
        }
        if (json_val.has("playSeqMaxSize")) {
            _playSeqMaxSize = json_val.getInt("playSeqMaxSize");
        }
        if (json_val.has("playSeqSignature")) {
            _playSeqSignature = json_val.getInt("playSeqSignature");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Changes the frequency of the signal sent to the buzzer. A zero value stops the buzzer.
     *
     * @param newval : a floating point number corresponding to the frequency of the signal sent to the buzzer
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_frequency(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
        _setAttr("frequency",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the frequency of the signal sent to the buzzer. A zero value stops the buzzer.
     *
     * @param newval : a floating point number corresponding to the frequency of the signal sent to the buzzer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setFrequency(double newval)  throws YAPI_Exception
    {
        return set_frequency(newval);
    }

    /**
     * Returns the  frequency of the signal sent to the buzzer/speaker.
     *
     * @return a floating point number corresponding to the  frequency of the signal sent to the buzzer/speaker
     *
     * @throws YAPI_Exception on error
     */
    public double get_frequency() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return FREQUENCY_INVALID;
            }
        }
        return _frequency;
    }

    /**
     * Returns the  frequency of the signal sent to the buzzer/speaker.
     *
     * @return a floating point number corresponding to the  frequency of the signal sent to the buzzer/speaker
     *
     * @throws YAPI_Exception on error
     */
    public double getFrequency() throws YAPI_Exception
    {
        return get_frequency();
    }

    /**
     * Returns the volume of the signal sent to the buzzer/speaker.
     *
     * @return an integer corresponding to the volume of the signal sent to the buzzer/speaker
     *
     * @throws YAPI_Exception on error
     */
    public int get_volume() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return VOLUME_INVALID;
            }
        }
        return _volume;
    }

    /**
     * Returns the volume of the signal sent to the buzzer/speaker.
     *
     * @return an integer corresponding to the volume of the signal sent to the buzzer/speaker
     *
     * @throws YAPI_Exception on error
     */
    public int getVolume() throws YAPI_Exception
    {
        return get_volume();
    }

    /**
     * Changes the volume of the signal sent to the buzzer/speaker.
     *
     * @param newval : an integer corresponding to the volume of the signal sent to the buzzer/speaker
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_volume(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("volume",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the volume of the signal sent to the buzzer/speaker.
     *
     * @param newval : an integer corresponding to the volume of the signal sent to the buzzer/speaker
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setVolume(int newval)  throws YAPI_Exception
    {
        return set_volume(newval);
    }

    /**
     * Returns the current length of the playing sequence
     *
     * @return an integer corresponding to the current length of the playing sequence
     *
     * @throws YAPI_Exception on error
     */
    public int get_playSeqSize() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PLAYSEQSIZE_INVALID;
            }
        }
        return _playSeqSize;
    }

    /**
     * Returns the current length of the playing sequence
     *
     * @return an integer corresponding to the current length of the playing sequence
     *
     * @throws YAPI_Exception on error
     */
    public int getPlaySeqSize() throws YAPI_Exception
    {
        return get_playSeqSize();
    }

    /**
     * Returns the maximum length of the playing sequence
     *
     * @return an integer corresponding to the maximum length of the playing sequence
     *
     * @throws YAPI_Exception on error
     */
    public int get_playSeqMaxSize() throws YAPI_Exception
    {
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PLAYSEQMAXSIZE_INVALID;
            }
        }
        return _playSeqMaxSize;
    }

    /**
     * Returns the maximum length of the playing sequence
     *
     * @return an integer corresponding to the maximum length of the playing sequence
     *
     * @throws YAPI_Exception on error
     */
    public int getPlaySeqMaxSize() throws YAPI_Exception
    {
        return get_playSeqMaxSize();
    }

    /**
     * Returns the playing sequence signature. As playing
     * sequences cannot be read from the device, this can be used
     * to detect if a specific playing sequence is already
     * programmed.
     *
     * @return an integer corresponding to the playing sequence signature
     *
     * @throws YAPI_Exception on error
     */
    public int get_playSeqSignature() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return PLAYSEQSIGNATURE_INVALID;
            }
        }
        return _playSeqSignature;
    }

    /**
     * Returns the playing sequence signature. As playing
     * sequences cannot be read from the device, this can be used
     * to detect if a specific playing sequence is already
     * programmed.
     *
     * @return an integer corresponding to the playing sequence signature
     *
     * @throws YAPI_Exception on error
     */
    public int getPlaySeqSignature() throws YAPI_Exception
    {
        return get_playSeqSignature();
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
     * Retrieves a buzzer for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the buzzer is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YBuzzer.isOnline() to test if the buzzer is
     * indeed online at a given time. In case of ambiguity when looking for
     * a buzzer by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the buzzer
     *
     * @return a YBuzzer object allowing you to drive the buzzer.
     */
    public static YBuzzer FindBuzzer(String func)
    {
        YBuzzer obj;
        obj = (YBuzzer) YFunction._FindFromCache("Buzzer", func);
        if (obj == null) {
            obj = new YBuzzer(func);
            YFunction._AddToCache("Buzzer", func, obj);
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
        _valueCallbackBuzzer = callback;
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
        if (_valueCallbackBuzzer != null) {
            _valueCallbackBuzzer.yNewValue(this, value);
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
     * Adds a new frequency transition to the playing sequence.
     *
     * @param freq    : desired frequency when the transition is completed, in Hz
     * @param msDelay : duration of the frequency transition, in milliseconds.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int addFreqMoveToPlaySeq(int freq,int msDelay) throws YAPI_Exception
    {
        return sendCommand(String.format("A%d,%d",freq,msDelay));
    }

    /**
     * Adds a pulse to the playing sequence.
     *
     * @param freq : pulse frequency, in Hz
     * @param msDuration : pulse duration, in milliseconds.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int addPulseToPlaySeq(int freq,int msDuration) throws YAPI_Exception
    {
        return sendCommand(String.format("B%d,%d",freq,msDuration));
    }

    /**
     * Adds a new volume transition to the playing sequence. Frequency stays untouched:
     * if frequency is at zero, the transition has no effect.
     *
     * @param volume    : desired volume when the transition is completed, as a percentage.
     * @param msDuration : duration of the volume transition, in milliseconds.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int addVolMoveToPlaySeq(int volume,int msDuration) throws YAPI_Exception
    {
        return sendCommand(String.format("C%d,%d",volume,msDuration));
    }

    /**
     * Starts the preprogrammed playing sequence. The sequence
     * runs in loop until it is stopped by stopPlaySeq or an explicit
     * change.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int startPlaySeq() throws YAPI_Exception
    {
        return sendCommand("S");
    }

    /**
     * Stops the preprogrammed playing sequence and sets the frequency to zero.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int stopPlaySeq() throws YAPI_Exception
    {
        return sendCommand("X");
    }

    /**
     * Resets the preprogrammed playing sequence and sets the frequency to zero.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     * @throws YAPI_Exception on error
     */
    public int resetPlaySeq() throws YAPI_Exception
    {
        return sendCommand("Z");
    }

    /**
     * Activates the buzzer for a short duration.
     *
     * @param frequency : pulse frequency, in hertz
     * @param duration : pulse duration in millseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int pulse(int frequency,int duration) throws YAPI_Exception
    {
        return set_command(String.format("P%d,%d",frequency,duration));
    }

    /**
     * Makes the buzzer frequency change over a period of time.
     *
     * @param frequency : frequency to reach, in hertz. A frequency under 25Hz stops the buzzer.
     * @param duration :  pulse duration in millseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int freqMove(int frequency,int duration) throws YAPI_Exception
    {
        return set_command(String.format("F%d,%d",frequency,duration));
    }

    /**
     * Makes the buzzer volume change over a period of time, frequency  stays untouched.
     *
     * @param volume : volume to reach in %
     * @param duration : change duration in millseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int volumeMove(int volume,int duration) throws YAPI_Exception
    {
        return set_command(String.format("V%d,%d",volume,duration));
    }

    /**
     * Continues the enumeration of buzzers started using yFirstBuzzer().
     *
     * @return a pointer to a YBuzzer object, corresponding to
     *         a buzzer currently online, or a null pointer
     *         if there are no more buzzers to enumerate.
     */
    public  YBuzzer nextBuzzer()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindBuzzer(next_hwid);
    }

    /**
     * Starts the enumeration of buzzers currently accessible.
     * Use the method YBuzzer.nextBuzzer() to iterate on
     * next buzzers.
     *
     * @return a pointer to a YBuzzer object, corresponding to
     *         the first buzzer currently online, or a null pointer
     *         if there are none.
     */
    public static YBuzzer FirstBuzzer()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Buzzer");
        if (next_hwid == null)  return null;
        return FindBuzzer(next_hwid);
    }

    //--- (end of YBuzzer implementation)
}

