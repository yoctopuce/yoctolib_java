/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindSoundSpectrum(), the high-level API for SoundSpectrum functions
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

//--- (YSoundSpectrum return codes)
//--- (end of YSoundSpectrum return codes)
//--- (YSoundSpectrum yapiwrapper)
//--- (end of YSoundSpectrum yapiwrapper)
//--- (YSoundSpectrum class start)
/**
 * YSoundSpectrum Class: sound spectrum analyzer control interface
 *
 * The YSoundSpectrum class allows you to read and configure Yoctopuce sound spectrum analyzers.
 * It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, and to access the autonomous datalogger.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSoundSpectrum extends YFunction
{
//--- (end of YSoundSpectrum class start)
//--- (YSoundSpectrum definitions)
    /**
     * invalid integrationTime value
     */
    public static final int INTEGRATIONTIME_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid spectrumData value
     */
    public static final String SPECTRUMDATA_INVALID = YAPI.INVALID_STRING;
    protected int _integrationTime = INTEGRATIONTIME_INVALID;
    protected String _spectrumData = SPECTRUMDATA_INVALID;
    protected UpdateCallback _valueCallbackSoundSpectrum = null;

    /**
     * Deprecated UpdateCallback for SoundSpectrum
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YSoundSpectrum function, String functionValue);
    }

    /**
     * TimedReportCallback for SoundSpectrum
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YSoundSpectrum  function, YMeasure measure);
    }
    //--- (end of YSoundSpectrum definitions)


    /**
     *
     * @param func : functionid
     */
    protected YSoundSpectrum(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "SoundSpectrum";
        //--- (YSoundSpectrum attributes initialization)
        //--- (end of YSoundSpectrum attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YSoundSpectrum(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YSoundSpectrum implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("integrationTime")) {
            _integrationTime = json_val.getInt("integrationTime");
        }
        if (json_val.has("spectrumData")) {
            _spectrumData = json_val.getString("spectrumData");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the integration time in milliseconds for calculating time
     * weighted spectrum data.
     *
     * @return an integer corresponding to the integration time in milliseconds for calculating time
     *         weighted spectrum data
     *
     * @throws YAPI_Exception on error
     */
    public int get_integrationTime() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return INTEGRATIONTIME_INVALID;
                }
            }
            res = _integrationTime;
        }
        return res;
    }

    /**
     * Returns the integration time in milliseconds for calculating time
     * weighted spectrum data.
     *
     * @return an integer corresponding to the integration time in milliseconds for calculating time
     *         weighted spectrum data
     *
     * @throws YAPI_Exception on error
     */
    public int getIntegrationTime() throws YAPI_Exception
    {
        return get_integrationTime();
    }

    /**
     * Changes the integration time in milliseconds for computing time weighted
     * spectrum data. Be aware that on some devices, changing the integration
     * time for time-weighted spectrum data may also affect the integration
     * period for one or more sound pressure level measurements.
     * Remember to call the saveToFlash() method of the
     * module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the integration time in milliseconds for computing time weighted
     *         spectrum data
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_integrationTime(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("integrationTime",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the integration time in milliseconds for computing time weighted
     * spectrum data. Be aware that on some devices, changing the integration
     * time for time-weighted spectrum data may also affect the integration
     * period for one or more sound pressure level measurements.
     * Remember to call the saveToFlash() method of the
     * module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the integration time in milliseconds for computing time weighted
     *         spectrum data
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setIntegrationTime(int newval)  throws YAPI_Exception
    {
        return set_integrationTime(newval);
    }

    public String get_spectrumData() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return SPECTRUMDATA_INVALID;
                }
            }
            res = _spectrumData;
        }
        return res;
    }

    /**
     * Retrieves a sound spectrum analyzer for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the sound spectrum analyzer is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSoundSpectrum.isOnline() to test if the sound spectrum analyzer is
     * indeed online at a given time. In case of ambiguity when looking for
     * a sound spectrum analyzer by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the sound spectrum analyzer, for instance
     *         MyDevice.soundSpectrum.
     *
     * @return a YSoundSpectrum object allowing you to drive the sound spectrum analyzer.
     */
    public static YSoundSpectrum FindSoundSpectrum(String func)
    {
        YSoundSpectrum obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YSoundSpectrum) YFunction._FindFromCache("SoundSpectrum", func);
            if (obj == null) {
                obj = new YSoundSpectrum(func);
                YFunction._AddToCache("SoundSpectrum", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a sound spectrum analyzer for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the sound spectrum analyzer is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YSoundSpectrum.isOnline() to test if the sound spectrum analyzer is
     * indeed online at a given time. In case of ambiguity when looking for
     * a sound spectrum analyzer by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the sound spectrum analyzer, for instance
     *         MyDevice.soundSpectrum.
     *
     * @return a YSoundSpectrum object allowing you to drive the sound spectrum analyzer.
     */
    public static YSoundSpectrum FindSoundSpectrumInContext(YAPIContext yctx,String func)
    {
        YSoundSpectrum obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YSoundSpectrum) YFunction._FindFromCacheInContext(yctx, "SoundSpectrum", func);
            if (obj == null) {
                obj = new YSoundSpectrum(yctx, func);
                YFunction._AddToCache("SoundSpectrum", func, obj);
            }
        }
        return obj;
    }

    /**
     * Registers the callback function that is invoked on every change of advertised value.
     * The callback is then invoked only during the execution of ySleep or yHandleEvents.
     * This provides control over the time when the callback is triggered. For good responsiveness,
     * remember to call one of these two functions periodically. The callback is called once juste after beeing
     * registered, passing the current advertised value  of the function, provided that it is not an empty string.
     * To unregister a callback, pass a null pointer as argument.
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
        _valueCallbackSoundSpectrum = callback;
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
        if (_valueCallbackSoundSpectrum != null) {
            _valueCallbackSoundSpectrum.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * comment from .yc definition
     */
    public YSoundSpectrum nextSoundSpectrum()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindSoundSpectrumInContext(_yapi, next_hwid);
    }

    /**
     * comment from .yc definition
     */
    public static YSoundSpectrum FirstSoundSpectrum()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("SoundSpectrum");
        if (next_hwid == null)  return null;
        return FindSoundSpectrumInContext(yctx, next_hwid);
    }

    /**
     * comment from .yc definition
     */
    public static YSoundSpectrum FirstSoundSpectrumInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("SoundSpectrum");
        if (next_hwid == null)  return null;
        return FindSoundSpectrumInContext(yctx, next_hwid);
    }

    //--- (end of YSoundSpectrum implementation)
}

