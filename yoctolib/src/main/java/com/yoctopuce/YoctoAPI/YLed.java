/*********************************************************************
 *
 * $Id: YLed.java 19328 2015-02-17 17:30:45Z seb $
 *
 * Implements FindLed(), the high-level API for Led functions
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

//--- (YLed return codes)
//--- (end of YLed return codes)
//--- (YLed class start)
/**
 * YLed Class: Led function interface
 *
 * Yoctopuce application programming interface
 * allows you not only to drive the intensity of the led, but also to
 * have it blink at various preset frequencies.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YLed extends YFunction
{
//--- (end of YLed class start)
//--- (YLed definitions)
    /**
     * invalid power value
     */
    public static final int POWER_OFF = 0;
    public static final int POWER_ON = 1;
    public static final int POWER_INVALID = -1;
    /**
     * invalid luminosity value
     */
    public static final int LUMINOSITY_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid blinking value
     */
    public static final int BLINKING_STILL = 0;
    public static final int BLINKING_RELAX = 1;
    public static final int BLINKING_AWARE = 2;
    public static final int BLINKING_RUN = 3;
    public static final int BLINKING_CALL = 4;
    public static final int BLINKING_PANIC = 5;
    public static final int BLINKING_INVALID = -1;
    protected int _power = POWER_INVALID;
    protected int _luminosity = LUMINOSITY_INVALID;
    protected int _blinking = BLINKING_INVALID;
    protected UpdateCallback _valueCallbackLed = null;

    /**
     * Deprecated UpdateCallback for Led
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YLed function, String functionValue);
    }

    /**
     * TimedReportCallback for Led
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YLed  function, YMeasure measure);
    }
    //--- (end of YLed definitions)


    /**
     *
     * @param func : functionid
     */
    protected YLed(String func)
    {
        super(func);
        _className = "Led";
        //--- (YLed attributes initialization)
        //--- (end of YLed attributes initialization)
    }

    //--- (YLed implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("power")) {
            _power = json_val.getInt("power") > 0 ? 1 : 0;
        }
        if (json_val.has("luminosity")) {
            _luminosity = json_val.getInt("luminosity");
        }
        if (json_val.has("blinking")) {
            _blinking = json_val.getInt("blinking");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the current led state.
     *
     * @return either YLed.POWER_OFF or YLed.POWER_ON, according to the current led state
     *
     * @throws YAPI_Exception on error
     */
    public int get_power() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return POWER_INVALID;
            }
        }
        return _power;
    }

    /**
     * Returns the current led state.
     *
     * @return either Y_POWER_OFF or Y_POWER_ON, according to the current led state
     *
     * @throws YAPI_Exception on error
     */
    public int getPower() throws YAPI_Exception
    {
        return get_power();
    }

    /**
     * Changes the state of the led.
     *
     * @param newval : either YLed.POWER_OFF or YLed.POWER_ON, according to the state of the led
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_power(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("power",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the state of the led.
     *
     * @param newval : either Y_POWER_OFF or Y_POWER_ON, according to the state of the led
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setPower(int newval)  throws YAPI_Exception
    {
        return set_power(newval);
    }

    /**
     * Returns the current led intensity (in per cent).
     *
     * @return an integer corresponding to the current led intensity (in per cent)
     *
     * @throws YAPI_Exception on error
     */
    public int get_luminosity() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LUMINOSITY_INVALID;
            }
        }
        return _luminosity;
    }

    /**
     * Returns the current led intensity (in per cent).
     *
     * @return an integer corresponding to the current led intensity (in per cent)
     *
     * @throws YAPI_Exception on error
     */
    public int getLuminosity() throws YAPI_Exception
    {
        return get_luminosity();
    }

    /**
     * Changes the current led intensity (in per cent).
     *
     * @param newval : an integer corresponding to the current led intensity (in per cent)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_luminosity(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("luminosity",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current led intensity (in per cent).
     *
     * @param newval : an integer corresponding to the current led intensity (in per cent)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLuminosity(int newval)  throws YAPI_Exception
    {
        return set_luminosity(newval);
    }

    /**
     * Returns the current led signaling mode.
     *
     *  @return a value among YLed.BLINKING_STILL, YLed.BLINKING_RELAX, YLed.BLINKING_AWARE,
     * YLed.BLINKING_RUN, YLed.BLINKING_CALL and YLed.BLINKING_PANIC corresponding to the current led signaling mode
     *
     * @throws YAPI_Exception on error
     */
    public int get_blinking() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return BLINKING_INVALID;
            }
        }
        return _blinking;
    }

    /**
     * Returns the current led signaling mode.
     *
     *  @return a value among Y_BLINKING_STILL, Y_BLINKING_RELAX, Y_BLINKING_AWARE, Y_BLINKING_RUN,
     * Y_BLINKING_CALL and Y_BLINKING_PANIC corresponding to the current led signaling mode
     *
     * @throws YAPI_Exception on error
     */
    public int getBlinking() throws YAPI_Exception
    {
        return get_blinking();
    }

    /**
     * Changes the current led signaling mode.
     *
     *  @param newval : a value among YLed.BLINKING_STILL, YLed.BLINKING_RELAX, YLed.BLINKING_AWARE,
     * YLed.BLINKING_RUN, YLed.BLINKING_CALL and YLed.BLINKING_PANIC corresponding to the current led signaling mode
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_blinking(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("blinking",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current led signaling mode.
     *
     *  @param newval : a value among Y_BLINKING_STILL, Y_BLINKING_RELAX, Y_BLINKING_AWARE, Y_BLINKING_RUN,
     * Y_BLINKING_CALL and Y_BLINKING_PANIC corresponding to the current led signaling mode
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setBlinking(int newval)  throws YAPI_Exception
    {
        return set_blinking(newval);
    }

    /**
     * Retrieves a led for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the led is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YLed.isOnline() to test if the led is
     * indeed online at a given time. In case of ambiguity when looking for
     * a led by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the led
     *
     * @return a YLed object allowing you to drive the led.
     */
    public static YLed FindLed(String func)
    {
        YLed obj;
        obj = (YLed) YFunction._FindFromCache("Led", func);
        if (obj == null) {
            obj = new YLed(func);
            YFunction._AddToCache("Led", func, obj);
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
        _valueCallbackLed = callback;
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
        if (_valueCallbackLed != null) {
            _valueCallbackLed.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of leds started using yFirstLed().
     *
     * @return a pointer to a YLed object, corresponding to
     *         a led currently online, or a null pointer
     *         if there are no more leds to enumerate.
     */
    public  YLed nextLed()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindLed(next_hwid);
    }

    /**
     * Starts the enumeration of leds currently accessible.
     * Use the method YLed.nextLed() to iterate on
     * next leds.
     *
     * @return a pointer to a YLed object, corresponding to
     *         the first led currently online, or a null pointer
     *         if there are none.
     */
    public static YLed FirstLed()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Led");
        if (next_hwid == null)  return null;
        return FindLed(next_hwid);
    }

    //--- (end of YLed implementation)
}

