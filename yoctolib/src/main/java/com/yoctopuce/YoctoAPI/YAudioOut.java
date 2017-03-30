/*********************************************************************
 *
 * $Id: YAudioOut.java 26934 2017-03-28 08:00:42Z seb $
 *
 * Implements FindAudioOut(), the high-level API for AudioOut functions
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

//--- (YAudioOut return codes)
//--- (end of YAudioOut return codes)
//--- (YAudioOut class start)
/**
 * YAudioOut Class: AudioOut function interface
 *
 * The Yoctopuce application programming interface allows you to configure the volume of the outout.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YAudioOut extends YFunction
{
//--- (end of YAudioOut class start)
//--- (YAudioOut definitions)
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
     * invalid volumeRange value
     */
    public static final String VOLUMERANGE_INVALID = YAPI.INVALID_STRING;
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
    protected String _volumeRange = VOLUMERANGE_INVALID;
    protected int _signal = SIGNAL_INVALID;
    protected int _noSignalFor = NOSIGNALFOR_INVALID;
    protected UpdateCallback _valueCallbackAudioOut = null;

    /**
     * Deprecated UpdateCallback for AudioOut
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YAudioOut function, String functionValue);
    }

    /**
     * TimedReportCallback for AudioOut
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YAudioOut  function, YMeasure measure);
    }
    //--- (end of YAudioOut definitions)


    /**
     *
     * @param func : functionid
     */
    protected YAudioOut(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "AudioOut";
        //--- (YAudioOut attributes initialization)
        //--- (end of YAudioOut attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YAudioOut(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YAudioOut implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("volume")) {
            _volume = json_val.getInt("volume");
        }
        if (json_val.has("mute")) {
            _mute = json_val.getInt("mute") > 0 ? 1 : 0;
        }
        if (json_val.has("volumeRange")) {
            _volumeRange = json_val.getString("volumeRange");
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
     * Returns audio output volume, in per cents.
     *
     * @return an integer corresponding to audio output volume, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public int get_volume() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLUME_INVALID;
                }
            }
            res = _volume;
        }
        return res;
    }

    /**
     * Returns audio output volume, in per cents.
     *
     * @return an integer corresponding to audio output volume, in per cents
     *
     * @throws YAPI_Exception on error
     */
    public int getVolume() throws YAPI_Exception
    {
        return get_volume();
    }

    /**
     * Changes audio output volume, in per cents.
     *
     * @param newval : an integer corresponding to audio output volume, in per cents
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_volume(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("volume",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes audio output volume, in per cents.
     *
     * @param newval : an integer corresponding to audio output volume, in per cents
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
     * @return either YAudioOut.MUTE_FALSE or YAudioOut.MUTE_TRUE, according to the state of the mute function
     *
     * @throws YAPI_Exception on error
     */
    public int get_mute() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return MUTE_INVALID;
                }
            }
            res = _mute;
        }
        return res;
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
     * @param newval : either YAudioOut.MUTE_FALSE or YAudioOut.MUTE_TRUE, according to the state of the mute function
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_mute(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = (newval > 0 ? "1" : "0");
            _setAttr("mute",rest_val);
        }
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
     * Returns the supported volume range. The low value of the
     * range corresponds to the minimal audible value. To
     * completely mute the sound, use set_mute()
     * instead of the set_volume().
     *
     * @return a string corresponding to the supported volume range
     *
     * @throws YAPI_Exception on error
     */
    public String get_volumeRange() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return VOLUMERANGE_INVALID;
                }
            }
            res = _volumeRange;
        }
        return res;
    }

    /**
     * Returns the supported volume range. The low value of the
     * range corresponds to the minimal audible value. To
     * completely mute the sound, use set_mute()
     * instead of the set_volume().
     *
     * @return a string corresponding to the supported volume range
     *
     * @throws YAPI_Exception on error
     */
    public String getVolumeRange() throws YAPI_Exception
    {
        return get_volumeRange();
    }

    /**
     * Returns the detected output current level.
     *
     * @return an integer corresponding to the detected output current level
     *
     * @throws YAPI_Exception on error
     */
    public int get_signal() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return SIGNAL_INVALID;
                }
            }
            res = _signal;
        }
        return res;
    }

    /**
     * Returns the detected output current level.
     *
     * @return an integer corresponding to the detected output current level
     *
     * @throws YAPI_Exception on error
     */
    public int getSignal() throws YAPI_Exception
    {
        return get_signal();
    }

    /**
     * Returns the number of seconds elapsed without detecting a signal.
     *
     * @return an integer corresponding to the number of seconds elapsed without detecting a signal
     *
     * @throws YAPI_Exception on error
     */
    public int get_noSignalFor() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(YAPI.DefaultCacheValidity) != YAPI.SUCCESS) {
                    return NOSIGNALFOR_INVALID;
                }
            }
            res = _noSignalFor;
        }
        return res;
    }

    /**
     * Returns the number of seconds elapsed without detecting a signal.
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
     * Retrieves an audio output for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the audio output is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YAudioOut.isOnline() to test if the audio output is
     * indeed online at a given time. In case of ambiguity when looking for
     * an audio output by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the audio output
     *
     * @return a YAudioOut object allowing you to drive the audio output.
     */
    public static YAudioOut FindAudioOut(String func)
    {
        YAudioOut obj;
        synchronized (YAPI.class) {
            obj = (YAudioOut) YFunction._FindFromCache("AudioOut", func);
            if (obj == null) {
                obj = new YAudioOut(func);
                YFunction._AddToCache("AudioOut", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves an audio output for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the audio output is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YAudioOut.isOnline() to test if the audio output is
     * indeed online at a given time. In case of ambiguity when looking for
     * an audio output by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the audio output
     *
     * @return a YAudioOut object allowing you to drive the audio output.
     */
    public static YAudioOut FindAudioOutInContext(YAPIContext yctx,String func)
    {
        YAudioOut obj;
        synchronized (yctx) {
            obj = (YAudioOut) YFunction._FindFromCacheInContext(yctx, "AudioOut", func);
            if (obj == null) {
                obj = new YAudioOut(yctx, func);
                YFunction._AddToCache("AudioOut", func, obj);
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
        _valueCallbackAudioOut = callback;
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
        if (_valueCallbackAudioOut != null) {
            _valueCallbackAudioOut.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of audio outputs started using yFirstAudioOut().
     *
     * @return a pointer to a YAudioOut object, corresponding to
     *         an audio output currently online, or a null pointer
     *         if there are no more audio outputs to enumerate.
     */
    public YAudioOut nextAudioOut()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindAudioOutInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of audio outputs currently accessible.
     * Use the method YAudioOut.nextAudioOut() to iterate on
     * next audio outputs.
     *
     * @return a pointer to a YAudioOut object, corresponding to
     *         the first audio output currently online, or a null pointer
     *         if there are none.
     */
    public static YAudioOut FirstAudioOut()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("AudioOut");
        if (next_hwid == null)  return null;
        return FindAudioOutInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of audio outputs currently accessible.
     * Use the method YAudioOut.nextAudioOut() to iterate on
     * next audio outputs.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YAudioOut object, corresponding to
     *         the first audio output currently online, or a null pointer
     *         if there are none.
     */
    public static YAudioOut FirstAudioOutInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("AudioOut");
        if (next_hwid == null)  return null;
        return FindAudioOutInContext(yctx, next_hwid);
    }

    //--- (end of YAudioOut implementation)
}

