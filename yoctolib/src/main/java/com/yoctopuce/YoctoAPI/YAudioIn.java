/*********************************************************************
 *
 * $Id: YAudioIn.java 20746 2015-06-25 11:15:45Z seb $
 *
 * Implements FindAudioIn(), the high-level API for AudioIn functions
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

//--- (YAudioIn return codes)
//--- (end of YAudioIn return codes)
//--- (YAudioIn class start)
/**
 * YAudioIn Class: AudioIn function interface
 *
 * The Yoctopuce application programming interface allows you to configure the volume of the input channel.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YAudioIn extends YFunction
{
//--- (end of YAudioIn class start)
//--- (YAudioIn definitions)
    /**
     * invalid volume value
     */
    public static final int VOLUME_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid mute value
     */
    public static final int MUTE_FALSE = 0;
    public static final int MUTE_TRUE = 1;
    public static final int MUTE_INVALID = -1;
    /**
     * invalid signal value
     */
    public static final int SIGNAL_INVALID = YAPI.INVALID_INT;
    /**
     * invalid noSignalFor value
     */
    public static final int NOSIGNALFOR_INVALID = YAPI.INVALID_INT;
    protected int _volume = VOLUME_INVALID;
    protected int _mute = MUTE_INVALID;
    protected int _signal = SIGNAL_INVALID;
    protected int _noSignalFor = NOSIGNALFOR_INVALID;
    protected UpdateCallback _valueCallbackAudioIn = null;

    /**
     * Deprecated UpdateCallback for AudioIn
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YAudioIn function, String functionValue);
    }

    /**
     * TimedReportCallback for AudioIn
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YAudioIn  function, YMeasure measure);
    }
    //--- (end of YAudioIn definitions)


    /**
     *
     * @param func : functionid
     */
    protected YAudioIn(String func)
    {
        super(func);
        _className = "AudioIn";
        //--- (YAudioIn attributes initialization)
        //--- (end of YAudioIn attributes initialization)
    }

    //--- (YAudioIn implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("volume")) {
            _volume = json_val.getInt("volume");
        }
        if (json_val.has("mute")) {
            _mute = json_val.getInt("mute") > 0 ? 1 : 0;
        }
        if (json_val.has("signal")) {
            _signal = json_val.getInt("signal");
        }
        if (json_val.has("noSignalFor")) {
            _noSignalFor = json_val.getInt("noSignalFor");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns audio input gain, in per cents.
     *
     * @return an integer corresponding to audio input gain, in per cents
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
     * Returns audio input gain, in per cents.
     *
     * @return an integer corresponding to audio input gain, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public int getVolume() throws YAPI_Exception
    {
        return get_volume();
    }

    /**
     * Changes audio input gain, in per cents.
     *
     * @param newval : an integer corresponding to audio input gain, in per cents
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
     * Changes audio input gain, in per cents.
     *
     * @param newval : an integer corresponding to audio input gain, in per cents
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
     * Returns the state of the mute function.
     *
     * @return either YAudioIn.MUTE_FALSE or YAudioIn.MUTE_TRUE, according to the state of the mute function
     *
     * @throws YAPI_Exception on error
     */
    public int get_mute() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return MUTE_INVALID;
            }
        }
        return _mute;
    }

    /**
     * Returns the state of the mute function.
     *
     * @return either Y_MUTE_FALSE or Y_MUTE_TRUE, according to the state of the mute function
     *
     * @throws YAPI_Exception on error
     */
    public int getMute() throws YAPI_Exception
    {
        return get_mute();
    }

    /**
     * Changes the state of the mute function. Remember to call the matching module
     * saveToFlash() method to save the setting permanently.
     *
     * @param newval : either YAudioIn.MUTE_FALSE or YAudioIn.MUTE_TRUE, according to the state of the mute function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_mute(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("mute",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the mute function. Remember to call the matching module
     * saveToFlash() method to save the setting permanently.
     *
     * @param newval : either Y_MUTE_FALSE or Y_MUTE_TRUE, according to the state of the mute function
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setMute(int newval)  throws YAPI_Exception
    {
        return set_mute(newval);
    }

    /**
     * Returns the detected input signal level.
     *
     * @return an integer corresponding to the detected input signal level
     *
     * @throws YAPI_Exception on error
     */
    public int get_signal() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SIGNAL_INVALID;
            }
        }
        return _signal;
    }

    /**
     * Returns the detected input signal level.
     *
     * @return an integer corresponding to the detected input signal level
     *
     * @throws YAPI_Exception on error
     */
    public int getSignal() throws YAPI_Exception
    {
        return get_signal();
    }

    /**
     * Returns the number of seconds elapsed without detecting a signal
     *
     * @return an integer corresponding to the number of seconds elapsed without detecting a signal
     *
     * @throws YAPI_Exception on error
     */
    public int get_noSignalFor() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return NOSIGNALFOR_INVALID;
            }
        }
        return _noSignalFor;
    }

    /**
     * Returns the number of seconds elapsed without detecting a signal
     *
     * @return an integer corresponding to the number of seconds elapsed without detecting a signal
     *
     * @throws YAPI_Exception on error
     */
    public int getNoSignalFor() throws YAPI_Exception
    {
        return get_noSignalFor();
    }

    /**
     * Retrieves an audio input for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the audio input is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YAudioIn.isOnline() to test if the audio input is
     * indeed online at a given time. In case of ambiguity when looking for
     * an audio input by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the audio input
     *
     * @return a YAudioIn object allowing you to drive the audio input.
     */
    public static YAudioIn FindAudioIn(String func)
    {
        YAudioIn obj;
        obj = (YAudioIn) YFunction._FindFromCache("AudioIn", func);
        if (obj == null) {
            obj = new YAudioIn(func);
            YFunction._AddToCache("AudioIn", func, obj);
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
        _valueCallbackAudioIn = callback;
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
        if (_valueCallbackAudioIn != null) {
            _valueCallbackAudioIn.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of audio inputs started using yFirstAudioIn().
     *
     * @return a pointer to a YAudioIn object, corresponding to
     *         an audio input currently online, or a null pointer
     *         if there are no more audio inputs to enumerate.
     */
    public  YAudioIn nextAudioIn()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindAudioIn(next_hwid);
    }

    /**
     * Starts the enumeration of audio inputs currently accessible.
     * Use the method YAudioIn.nextAudioIn() to iterate on
     * next audio inputs.
     *
     * @return a pointer to a YAudioIn object, corresponding to
     *         the first audio input currently online, or a null pointer
     *         if there are none.
     */
    public static YAudioIn FirstAudioIn()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("AudioIn");
        if (next_hwid == null)  return null;
        return FindAudioIn(next_hwid);
    }

    //--- (end of YAudioIn implementation)
}

