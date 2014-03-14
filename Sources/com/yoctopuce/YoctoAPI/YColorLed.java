/*********************************************************************
 *
 * $Id: YColorLed.java 15407 2014-03-12 19:34:44Z mvuilleu $
 *
 * Implements yFindColorLed(), the high-level API for ColorLed functions
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

    //--- (YColorLed return codes)
    //--- (end of YColorLed return codes)
//--- (YColorLed class start)
/**
 * YColorLed Class: ColorLed function interface
 * 
 * Yoctopuce application programming interface
 * allows you to drive a color led using RGB coordinates as well as HSL coordinates.
 * The module performs all conversions form RGB to HSL automatically. It is then
 * self-evident to turn on a led with a given hue and to progressively vary its
 * saturation or lightness. If needed, you can find more information on the
 * difference between RGB and HSL in the section following this one.
 */
public class YColorLed extends YFunction
{
//--- (end of YColorLed class start)
//--- (YColorLed definitions)
    public static class YMove
    {
        public int target = YAPI.INVALID_INT;
        public int ms = YAPI.INVALID_INT;
        public int moving = YAPI.INVALID_UINT;
        public YMove(){}
    }

    /**
     * invalid rgbColor value
     */
    public static final int RGBCOLOR_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid hslColor value
     */
    public static final int HSLCOLOR_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid rgbColorAtPowerOn value
     */
    public static final int RGBCOLORATPOWERON_INVALID = YAPI.INVALID_UINT;
    public static final YMove RGBMOVE_INVALID = null;
    public static final YMove HSLMOVE_INVALID = null;
    protected int _rgbColor = RGBCOLOR_INVALID;
    protected int _hslColor = HSLCOLOR_INVALID;
    protected YMove _rgbMove = new YMove();
    protected YMove _hslMove = new YMove();
    protected int _rgbColorAtPowerOn = RGBCOLORATPOWERON_INVALID;
    protected UpdateCallback _valueCallbackColorLed = null;

    /**
     * Deprecated UpdateCallback for ColorLed
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YColorLed function, String functionValue);
    }

    /**
     * TimedReportCallback for ColorLed
     */
    public interface TimedReportCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YColorLed  function, YMeasure measure);
    }
    //--- (end of YColorLed definitions)


    /**
     * 
     * @param func : functionid
     */
    protected YColorLed(String func)
    {
        super(func);
        _className = "ColorLed";
        //--- (YColorLed attributes initialization)
        //--- (end of YColorLed attributes initialization)
    }

    //--- (YColorLed implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("rgbColor")) {
            _rgbColor =  json_val.getInt("rgbColor");
        }
        if (json_val.has("hslColor")) {
            _hslColor =  json_val.getInt("hslColor");
        }
        if (json_val.has("rgbMove")) {
            JSONObject subjson = json_val.getJSONObject("rgbMove");
            if (subjson.has("moving")) {
                _rgbMove.moving = subjson.getInt("moving");
            }
            if (subjson.has("target")) {
                _rgbMove.moving = subjson.getInt("target");
            }
            if (subjson.has("ms")) {
                _rgbMove.moving = subjson.getInt("ms");
            }
        }
        if (json_val.has("hslMove")) {
            JSONObject subjson = json_val.getJSONObject("hslMove");
            if (subjson.has("moving")) {
                _hslMove.moving = subjson.getInt("moving");
            }
            if (subjson.has("target")) {
                _hslMove.moving = subjson.getInt("target");
            }
            if (subjson.has("ms")) {
                _hslMove.moving = subjson.getInt("ms");
            }
        }
        if (json_val.has("rgbColorAtPowerOn")) {
            _rgbColorAtPowerOn =  json_val.getInt("rgbColorAtPowerOn");
        }
        super._parseAttr(json_val);
    }

    /**
     * invalid rgbMove
     */
    /**
     * invalid hslMove
     */
    /**
     * Returns the current RGB color of the led.
     * 
     * @return an integer corresponding to the current RGB color of the led
     * 
     * @throws YAPI_Exception
     */
    public int get_rgbColor() throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return RGBCOLOR_INVALID;
            }
        }
        return _rgbColor;
    }

    /**
     * Returns the current RGB color of the led.
     * 
     * @return an integer corresponding to the current RGB color of the led
     * 
     * @throws YAPI_Exception
     */
    public int getRgbColor() throws YAPI_Exception

    { return get_rgbColor(); }

    /**
     * Changes the current color of the led, using a RGB color. Encoding is done as follows: 0xRRGGBB.
     * 
     * @param newval : an integer corresponding to the current color of the led, using a RGB color
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_rgbColor(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("0x%06x",newval);
        _setAttr("rgbColor",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current color of the led, using a RGB color. Encoding is done as follows: 0xRRGGBB.
     * 
     * @param newval : an integer corresponding to the current color of the led, using a RGB color
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setRgbColor(int newval)  throws YAPI_Exception

    { return set_rgbColor(newval); }

    /**
     * Returns the current HSL color of the led.
     * 
     * @return an integer corresponding to the current HSL color of the led
     * 
     * @throws YAPI_Exception
     */
    public int get_hslColor() throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return HSLCOLOR_INVALID;
            }
        }
        return _hslColor;
    }

    /**
     * Returns the current HSL color of the led.
     * 
     * @return an integer corresponding to the current HSL color of the led
     * 
     * @throws YAPI_Exception
     */
    public int getHslColor() throws YAPI_Exception

    { return get_hslColor(); }

    /**
     * Changes the current color of the led, using a color HSL. Encoding is done as follows: 0xHHSSLL.
     * 
     * @param newval : an integer corresponding to the current color of the led, using a color HSL
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_hslColor(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("0x%06x",newval);
        _setAttr("hslColor",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the current color of the led, using a color HSL. Encoding is done as follows: 0xHHSSLL.
     * 
     * @param newval : an integer corresponding to the current color of the led, using a color HSL
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setHslColor(int newval)  throws YAPI_Exception

    { return set_hslColor(newval); }

    /**
     * @throws YAPI_Exception
     */
    public YMove get_rgbMove() throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return RGBMOVE_INVALID;
            }
        }
        return _rgbMove;
    }

    /**
     * @throws YAPI_Exception
     */
    public YMove getRgbMove() throws YAPI_Exception

    { return get_rgbMove(); }

    public int set_rgbMove(YMove  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("rgbMove",rest_val);
        return YAPI.SUCCESS;
    }

    public int setRgbMove(YMove newval)  throws YAPI_Exception

    { return set_rgbMove(newval); }

    /**
     * Performs a smooth transition in the RGB color space between the current color and a target color.
     * 
     * @param rgb_target  : desired RGB color at the end of the transition
     * @param ms_duration : duration of the transition, in millisecond
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int rgbMove(int rgb_target,int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",rgb_target,ms_duration);
        _setAttr("rgbMove",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * @throws YAPI_Exception
     */
    public YMove get_hslMove() throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return HSLMOVE_INVALID;
            }
        }
        return _hslMove;
    }

    /**
     * @throws YAPI_Exception
     */
    public YMove getHslMove() throws YAPI_Exception

    { return get_hslMove(); }

    public int set_hslMove(YMove  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("hslMove",rest_val);
        return YAPI.SUCCESS;
    }

    public int setHslMove(YMove newval)  throws YAPI_Exception

    { return set_hslMove(newval); }

    /**
     * Performs a smooth transition in the HSL color space between the current color and a target color.
     * 
     * @param hsl_target  : desired HSL color at the end of the transition
     * @param ms_duration : duration of the transition, in millisecond
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int hslMove(int hsl_target,int ms_duration)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",hsl_target,ms_duration);
        _setAttr("hslMove",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Returns the configured color to be displayed when the module is turned on.
     * 
     * @return an integer corresponding to the configured color to be displayed when the module is turned on
     * 
     * @throws YAPI_Exception
     */
    public int get_rgbColorAtPowerOn() throws YAPI_Exception
    {
        if (_cacheExpiration <= SafeYAPI().GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return RGBCOLORATPOWERON_INVALID;
            }
        }
        return _rgbColorAtPowerOn;
    }

    /**
     * Returns the configured color to be displayed when the module is turned on.
     * 
     * @return an integer corresponding to the configured color to be displayed when the module is turned on
     * 
     * @throws YAPI_Exception
     */
    public int getRgbColorAtPowerOn() throws YAPI_Exception

    { return get_rgbColorAtPowerOn(); }

    /**
     * Changes the color that the led will display by default when the module is turned on.
     * This color will be displayed as soon as the module is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * change should be kept.
     * 
     * @param newval : an integer corresponding to the color that the led will display by default when the
     * module is turned on
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_rgbColorAtPowerOn(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("0x%06x",newval);
        _setAttr("rgbColorAtPowerOn",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the color that the led will display by default when the module is turned on.
     * This color will be displayed as soon as the module is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * change should be kept.
     * 
     * @param newval : an integer corresponding to the color that the led will display by default when the
     * module is turned on
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setRgbColorAtPowerOn(int newval)  throws YAPI_Exception

    { return set_rgbColorAtPowerOn(newval); }

    /**
     * Retrieves an RGB led for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the RGB led is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YColorLed.isOnline() to test if the RGB led is
     * indeed online at a given time. In case of ambiguity when looking for
     * an RGB led by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the RGB led
     * 
     * @return a YColorLed object allowing you to drive the RGB led.
     */
    public static YColorLed FindColorLed(String func)
    {
        YColorLed obj;
        obj = (YColorLed) YFunction._FindFromCache("ColorLed", func);
        if (obj == null) {
            obj = new YColorLed(func);
            YFunction._AddToCache("ColorLed", func, obj);
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
     * @noreturn
     */
    public int registerValueCallback(UpdateCallback callback)
    {
        String val;
        if (callback != null) {
            YFunction._UpdateValueCallbackList(this, true);
        } else {
            YFunction._UpdateValueCallbackList(this, false);
        }
        _valueCallbackColorLed = callback;
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
        if (_valueCallbackColorLed != null) {
            _valueCallbackColorLed.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of RGB leds started using yFirstColorLed().
     * 
     * @return a pointer to a YColorLed object, corresponding to
     *         an RGB led currently online, or a null pointer
     *         if there are no more RGB leds to enumerate.
     */
    public  YColorLed nextColorLed()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindColorLed(next_hwid);
    }

    /**
     * Starts the enumeration of RGB leds currently accessible.
     * Use the method YColorLed.nextColorLed() to iterate on
     * next RGB leds.
     * 
     * @return a pointer to a YColorLed object, corresponding to
     *         the first RGB led currently online, or a null pointer
     *         if there are none.
     */
    public static YColorLed FirstColorLed()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("ColorLed");
        if (next_hwid == null)  return null;
        return FindColorLed(next_hwid);
    }

    //--- (end of YColorLed implementation)
}

