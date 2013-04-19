/*********************************************************************
 *
 * $Id: YColorLed.java 9230 2012-12-27 17:33:09Z seb $
 *
 * Implements yFindColorLed(), the high-level API for ColorLed functions
 *
 * - - - - - - - - - License information: - - - - - - - - - 
 *
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 * 1) If you have obtained this file from www.yoctopuce.com,
 *    Yoctopuce Sarl licenses to you (hereafter Licensee) the
 *    right to use, modify, copy, and integrate this source file
 *    into your own solution for the sole purpose of interfacing
 *    a Yoctopuce product with Licensee's solution.
 *
 *    The use of this file and all relationship between Yoctopuce 
 *    and Licensee are governed by Yoctopuce General Terms and 
 *    Conditions.
 *
 *    THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *    WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *    WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS 
 *    FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *    EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *    INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, 
 *    COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR 
 *    SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT 
 *    LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *    CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *    BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *    WARRANTY, OR OTHERWISE.
 *
 * 2) If your intent is not to interface with Yoctopuce products,
 *    you are not entitled to use, read or create any derived
 *    material from this source file.
 *
 *********************************************************************/

package com.yoctopuce.YoctoAPI;

  //--- (globals)
  //--- (end of globals)
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
    //--- (definitions)
    private YColorLed.UpdateCallback _valueCallbackColorLed;
    public static class YMove
    {
        public long target = YAPI.INVALID_LONG;
        public long ms = YAPI.INVALID_LONG;
        public long moving = YAPI.INVALID_LONG;
        public YMove(String attr){}
    }

    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid rgbColor value
     */
    public static final long RGBCOLOR_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid hslColor value
     */
    public static final long HSLCOLOR_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid rgbColorAtPowerOn value
     */
    public static final long RGBCOLORATPOWERON_INVALID = YAPI.INVALID_LONG;
    public static final YMove RGBMOVE_INVALID = new YMove("");
    public static final YMove HSLMOVE_INVALID = new YMove("");
    //--- (end of definitions)

    /**
     * UdateCallback for ColorLed
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YColorLed function, String functionValue);
    }



    //--- (YColorLed implementation)

    /**
     * invalid rgbMove
     */
    /**
     * invalid hslMove
     */
    /**
     * Returns the logical name of the RGB led.
     * 
     * @return a string corresponding to the logical name of the RGB led
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the RGB led.
     * 
     * @return a string corresponding to the logical name of the RGB led
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the RGB led. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the RGB led
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_logicalName( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("logicalName",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the logical name of the RGB led. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the RGB led
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the RGB led (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the RGB led (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the RGB led (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the RGB led (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the current RGB color of the led.
     * 
     * @return an integer corresponding to the current RGB color of the led
     * 
     * @throws YAPI_Exception
     */
    public long get_rgbColor()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("rgbColor");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current RGB color of the led.
     * 
     * @return an integer corresponding to the current RGB color of the led
     * 
     * @throws YAPI_Exception
     */
    public long getRgbColor() throws YAPI_Exception

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
    public int set_rgbColor( long  newval)  throws YAPI_Exception
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
    public int setRgbColor( long newval)  throws YAPI_Exception

    { return set_rgbColor(newval); }

    /**
     * Returns the current HSL color of the led.
     * 
     * @return an integer corresponding to the current HSL color of the led
     * 
     * @throws YAPI_Exception
     */
    public long get_hslColor()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("hslColor");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the current HSL color of the led.
     * 
     * @return an integer corresponding to the current HSL color of the led
     * 
     * @throws YAPI_Exception
     */
    public long getHslColor() throws YAPI_Exception

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
    public int set_hslColor( long  newval)  throws YAPI_Exception
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
    public int setHslColor( long newval)  throws YAPI_Exception

    { return set_hslColor(newval); }

    public YMove get_rgbMove()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("rgbMove");
        return new YMove(json_val);
    }

    public YMove getRgbMove() throws YAPI_Exception

    { return get_rgbMove(); }

    public int set_rgbMove( YMove  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("rgbMove",rest_val);
        return YAPI.SUCCESS;
    }

    public int setRgbMove( YMove newval)  throws YAPI_Exception

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

    public YMove get_hslMove()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("hslMove");
        return new YMove(json_val);
    }

    public YMove getHslMove() throws YAPI_Exception

    { return get_hslMove(); }

    public int set_hslMove( YMove  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = String.format("%d:%d",newval.target,newval.ms);
        _setAttr("hslMove",rest_val);
        return YAPI.SUCCESS;
    }

    public int setHslMove( YMove newval)  throws YAPI_Exception

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
    public long get_rgbColorAtPowerOn()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("rgbColorAtPowerOn");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the configured color to be displayed when the module is turned on.
     * 
     * @return an integer corresponding to the configured color to be displayed when the module is turned on
     * 
     * @throws YAPI_Exception
     */
    public long getRgbColorAtPowerOn() throws YAPI_Exception

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
    public int set_rgbColorAtPowerOn( long  newval)  throws YAPI_Exception
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
    public int setRgbColorAtPowerOn( long newval)  throws YAPI_Exception

    { return set_rgbColorAtPowerOn(newval); }

    /**
     * Continues the enumeration of RGB leds started using yFirstColorLed().
     * 
     * @return a pointer to a YColorLed object, corresponding to
     *         an RGB led currently online, or a null pointer
     *         if there are no more RGB leds to enumerate.
     */
    public  YColorLed nextColorLed()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindColorLed(next_hwid);
    }

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
    {   YFunction yfunc = YAPI.getFunction("ColorLed", func);
        if (yfunc != null) {
            return (YColorLed) yfunc;
        }
        return new YColorLed(func);
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
        String next_hwid = YAPI.getFirstHardwareId("ColorLed");
        if (next_hwid == null)  return null;
        return FindColorLed(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YColorLed(String func)
    {
        super("ColorLed", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackColorLed != null) {
            _valueCallbackColorLed.yNewValue(this, newvalue);
        }
    }

    /**
     * Internal: check if we have a callback interface registered
     * 
     * @return yes if the user has registered a interface
     */
    @Override
     protected boolean hasCallbackRegistered()
    {
        return super.hasCallbackRegistered() || (_valueCallbackColorLed!=null);
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
    public void registerValueCallback(YColorLed.UpdateCallback callback)
    {
         _valueCallbackColorLed =  callback;
         if (callback != null && isOnline()) {
             String newval;
             try {
                 newval = get_advertisedValue();
                 if (!newval.equals("") && !newval.equals("!INVALDI!")) {
                     callback.yNewValue(this, newval);
                 }
             } catch (YAPI_Exception ex) {
             }
         }
    }

    //--- (end of YColorLed implementation)
};

