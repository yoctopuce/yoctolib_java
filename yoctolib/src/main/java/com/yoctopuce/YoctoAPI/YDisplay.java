/*********************************************************************
 *
 * $Id: YDisplay.java 20376 2015-05-19 14:18:47Z seb $
 *
 * Implements yFindDisplay(), the high-level API for Display functions
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

//--- (generated code: YDisplay class start)
/**
 * YDisplay Class: Display function interface
 *
 * Yoctopuce display interface has been designed to easily
 * show information and images. The device provides built-in
 * multi-layer rendering. Layers can be drawn offline, individually,
 * and freely moved on the display. It can also replay recorded
 * sequences (animations).
 */
 @SuppressWarnings("UnusedDeclaration")
public class YDisplay extends YFunction
{
//--- (end of generated code: YDisplay class start)
    //--- (generated code: YDisplay definitions)
    /**
     * invalid enabled value
     */
    public static final int ENABLED_FALSE = 0;
    public static final int ENABLED_TRUE = 1;
    public static final int ENABLED_INVALID = -1;
    /**
     * invalid startupSeq value
     */
    public static final String STARTUPSEQ_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid brightness value
     */
    public static final int BRIGHTNESS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid orientation value
     */
    public static final int ORIENTATION_LEFT = 0;
    public static final int ORIENTATION_UP = 1;
    public static final int ORIENTATION_RIGHT = 2;
    public static final int ORIENTATION_DOWN = 3;
    public static final int ORIENTATION_INVALID = -1;
    /**
     * invalid displayWidth value
     */
    public static final int DISPLAYWIDTH_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid displayHeight value
     */
    public static final int DISPLAYHEIGHT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid displayType value
     */
    public static final int DISPLAYTYPE_MONO = 0;
    public static final int DISPLAYTYPE_GRAY = 1;
    public static final int DISPLAYTYPE_RGB = 2;
    public static final int DISPLAYTYPE_INVALID = -1;
    /**
     * invalid layerWidth value
     */
    public static final int LAYERWIDTH_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid layerHeight value
     */
    public static final int LAYERHEIGHT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid layerCount value
     */
    public static final int LAYERCOUNT_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _enabled = ENABLED_INVALID;
    protected String _startupSeq = STARTUPSEQ_INVALID;
    protected int _brightness = BRIGHTNESS_INVALID;
    protected int _orientation = ORIENTATION_INVALID;
    protected int _displayWidth = DISPLAYWIDTH_INVALID;
    protected int _displayHeight = DISPLAYHEIGHT_INVALID;
    protected int _displayType = DISPLAYTYPE_INVALID;
    protected int _layerWidth = LAYERWIDTH_INVALID;
    protected int _layerHeight = LAYERHEIGHT_INVALID;
    protected int _layerCount = LAYERCOUNT_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackDisplay = null;

    /**
     * Deprecated UpdateCallback for Display
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YDisplay function, String functionValue);
    }

    /**
     * TimedReportCallback for Display
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YDisplay  function, YMeasure measure);
    }
    //--- (end of generated code: YDisplay definitions)


    /**
     * @param func : functionid
     */
    protected YDisplay(String func)
    {
        super(func);
        _className = "Display";
        //--- (generated code: YDisplay attributes initialization)
        //--- (end of generated code: YDisplay attributes initialization)
    }

    //--- (generated code: YDisplay implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("enabled")) {
            _enabled = json_val.getInt("enabled") > 0 ? 1 : 0;
        }
        if (json_val.has("startupSeq")) {
            _startupSeq = json_val.getString("startupSeq");
        }
        if (json_val.has("brightness")) {
            _brightness = json_val.getInt("brightness");
        }
        if (json_val.has("orientation")) {
            _orientation = json_val.getInt("orientation");
        }
        if (json_val.has("displayWidth")) {
            _displayWidth = json_val.getInt("displayWidth");
        }
        if (json_val.has("displayHeight")) {
            _displayHeight = json_val.getInt("displayHeight");
        }
        if (json_val.has("displayType")) {
            _displayType = json_val.getInt("displayType");
        }
        if (json_val.has("layerWidth")) {
            _layerWidth = json_val.getInt("layerWidth");
        }
        if (json_val.has("layerHeight")) {
            _layerHeight = json_val.getInt("layerHeight");
        }
        if (json_val.has("layerCount")) {
            _layerCount = json_val.getInt("layerCount");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns true if the screen is powered, false otherwise.
     *
     *  @return either YDisplay.ENABLED_FALSE or YDisplay.ENABLED_TRUE, according to true if the screen is
     * powered, false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int get_enabled() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ENABLED_INVALID;
            }
        }
        return _enabled;
    }

    /**
     * Returns true if the screen is powered, false otherwise.
     *
     * @return either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to true if the screen is powered, false otherwise
     *
     * @throws YAPI_Exception on error
     */
    public int getEnabled() throws YAPI_Exception
    {
        return get_enabled();
    }

    /**
     * Changes the power state of the display.
     *
     *  @param newval : either YDisplay.ENABLED_FALSE or YDisplay.ENABLED_TRUE, according to the power
     * state of the display
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_enabled(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("enabled",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the power state of the display.
     *
     * @param newval : either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the power state of the display
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setEnabled(int newval)  throws YAPI_Exception
    {
        return set_enabled(newval);
    }

    /**
     * Returns the name of the sequence to play when the displayed is powered on.
     *
     * @return a string corresponding to the name of the sequence to play when the displayed is powered on
     *
     * @throws YAPI_Exception on error
     */
    public String get_startupSeq() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return STARTUPSEQ_INVALID;
            }
        }
        return _startupSeq;
    }

    /**
     * Returns the name of the sequence to play when the displayed is powered on.
     *
     * @return a string corresponding to the name of the sequence to play when the displayed is powered on
     *
     * @throws YAPI_Exception on error
     */
    public String getStartupSeq() throws YAPI_Exception
    {
        return get_startupSeq();
    }

    /**
     * Changes the name of the sequence to play when the displayed is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the name of the sequence to play when the displayed is powered on
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_startupSeq(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("startupSeq",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the name of the sequence to play when the displayed is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the name of the sequence to play when the displayed is powered on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setStartupSeq(String newval)  throws YAPI_Exception
    {
        return set_startupSeq(newval);
    }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     *
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     *
     * @throws YAPI_Exception on error
     */
    public int get_brightness() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return BRIGHTNESS_INVALID;
            }
        }
        return _brightness;
    }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     *
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     *
     * @throws YAPI_Exception on error
     */
    public int getBrightness() throws YAPI_Exception
    {
        return get_brightness();
    }

    /**
     * Changes the brightness of the display. The parameter is a value between 0 and
     * 100. Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the brightness of the display
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_brightness(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("brightness",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the brightness of the display. The parameter is a value between 0 and
     * 100. Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the brightness of the display
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setBrightness(int newval)  throws YAPI_Exception
    {
        return set_brightness(newval);
    }

    /**
     * Returns the currently selected display orientation.
     *
     *  @return a value among YDisplay.ORIENTATION_LEFT, YDisplay.ORIENTATION_UP,
     *  YDisplay.ORIENTATION_RIGHT and YDisplay.ORIENTATION_DOWN corresponding to the currently selected
     * display orientation
     *
     * @throws YAPI_Exception on error
     */
    public int get_orientation() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ORIENTATION_INVALID;
            }
        }
        return _orientation;
    }

    /**
     * Returns the currently selected display orientation.
     *
     *  @return a value among Y_ORIENTATION_LEFT, Y_ORIENTATION_UP, Y_ORIENTATION_RIGHT and
     * Y_ORIENTATION_DOWN corresponding to the currently selected display orientation
     *
     * @throws YAPI_Exception on error
     */
    public int getOrientation() throws YAPI_Exception
    {
        return get_orientation();
    }

    /**
     * Changes the display orientation. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : a value among YDisplay.ORIENTATION_LEFT, YDisplay.ORIENTATION_UP,
     * YDisplay.ORIENTATION_RIGHT and YDisplay.ORIENTATION_DOWN corresponding to the display orientation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_orientation(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("orientation",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the display orientation. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     *  @param newval : a value among Y_ORIENTATION_LEFT, Y_ORIENTATION_UP, Y_ORIENTATION_RIGHT and
     * Y_ORIENTATION_DOWN corresponding to the display orientation
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setOrientation(int newval)  throws YAPI_Exception
    {
        return set_orientation(newval);
    }

    /**
     * Returns the display width, in pixels.
     *
     * @return an integer corresponding to the display width, in pixels
     *
     * @throws YAPI_Exception on error
     */
    public int get_displayWidth() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DISPLAYWIDTH_INVALID;
            }
        }
        return _displayWidth;
    }

    /**
     * Returns the display width, in pixels.
     *
     * @return an integer corresponding to the display width, in pixels
     *
     * @throws YAPI_Exception on error
     */
    public int getDisplayWidth() throws YAPI_Exception
    {
        return get_displayWidth();
    }

    /**
     * Returns the display height, in pixels.
     *
     * @return an integer corresponding to the display height, in pixels
     *
     * @throws YAPI_Exception on error
     */
    public int get_displayHeight() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DISPLAYHEIGHT_INVALID;
            }
        }
        return _displayHeight;
    }

    /**
     * Returns the display height, in pixels.
     *
     * @return an integer corresponding to the display height, in pixels
     *
     * @throws YAPI_Exception on error
     */
    public int getDisplayHeight() throws YAPI_Exception
    {
        return get_displayHeight();
    }

    /**
     * Returns the display type: monochrome, gray levels or full color.
     *
     *  @return a value among YDisplay.DISPLAYTYPE_MONO, YDisplay.DISPLAYTYPE_GRAY and
     * YDisplay.DISPLAYTYPE_RGB corresponding to the display type: monochrome, gray levels or full color
     *
     * @throws YAPI_Exception on error
     */
    public int get_displayType() throws YAPI_Exception
    {
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DISPLAYTYPE_INVALID;
            }
        }
        return _displayType;
    }

    /**
     * Returns the display type: monochrome, gray levels or full color.
     *
     *  @return a value among Y_DISPLAYTYPE_MONO, Y_DISPLAYTYPE_GRAY and Y_DISPLAYTYPE_RGB corresponding to
     * the display type: monochrome, gray levels or full color
     *
     * @throws YAPI_Exception on error
     */
    public int getDisplayType() throws YAPI_Exception
    {
        return get_displayType();
    }

    /**
     * Returns the width of the layers to draw on, in pixels.
     *
     * @return an integer corresponding to the width of the layers to draw on, in pixels
     *
     * @throws YAPI_Exception on error
     */
    public int get_layerWidth() throws YAPI_Exception
    {
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LAYERWIDTH_INVALID;
            }
        }
        return _layerWidth;
    }

    /**
     * Returns the width of the layers to draw on, in pixels.
     *
     * @return an integer corresponding to the width of the layers to draw on, in pixels
     *
     * @throws YAPI_Exception on error
     */
    public int getLayerWidth() throws YAPI_Exception
    {
        return get_layerWidth();
    }

    /**
     * Returns the height of the layers to draw on, in pixels.
     *
     * @return an integer corresponding to the height of the layers to draw on, in pixels
     *
     * @throws YAPI_Exception on error
     */
    public int get_layerHeight() throws YAPI_Exception
    {
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LAYERHEIGHT_INVALID;
            }
        }
        return _layerHeight;
    }

    /**
     * Returns the height of the layers to draw on, in pixels.
     *
     * @return an integer corresponding to the height of the layers to draw on, in pixels
     *
     * @throws YAPI_Exception on error
     */
    public int getLayerHeight() throws YAPI_Exception
    {
        return get_layerHeight();
    }

    /**
     * Returns the number of available layers to draw on.
     *
     * @return an integer corresponding to the number of available layers to draw on
     *
     * @throws YAPI_Exception on error
     */
    public int get_layerCount() throws YAPI_Exception
    {
        if (_cacheExpiration == 0) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LAYERCOUNT_INVALID;
            }
        }
        return _layerCount;
    }

    /**
     * Returns the number of available layers to draw on.
     *
     * @return an integer corresponding to the number of available layers to draw on
     *
     * @throws YAPI_Exception on error
     */
    public int getLayerCount() throws YAPI_Exception
    {
        return get_layerCount();
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
     * Retrieves a display for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the display is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YDisplay.isOnline() to test if the display is
     * indeed online at a given time. In case of ambiguity when looking for
     * a display by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the display
     *
     * @return a YDisplay object allowing you to drive the display.
     */
    public static YDisplay FindDisplay(String func)
    {
        YDisplay obj;
        obj = (YDisplay) YFunction._FindFromCache("Display", func);
        if (obj == null) {
            obj = new YDisplay(func);
            YFunction._AddToCache("Display", func, obj);
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
        _valueCallbackDisplay = callback;
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
        if (_valueCallbackDisplay != null) {
            _valueCallbackDisplay.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Clears the display screen and resets all display layers to their default state.
     * Using this function in a sequence will kill the sequence play-back. Don't use that
     * function to reset the display at sequence start-up.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int resetAll() throws YAPI_Exception
    {
        flushLayers();
        resetHiddenLayerFlags();
        return sendCommand("Z");
    }

    /**
     * Smoothly changes the brightness of the screen to produce a fade-in or fade-out
     * effect.
     *
     * @param brightness : the new screen brightness
     * @param duration : duration of the brightness transition, in milliseconds.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int fade(int brightness,int duration) throws YAPI_Exception
    {
        flushLayers();
        return sendCommand(String.format("+%d,%d",brightness,duration));
    }

    /**
     * Starts to record all display commands into a sequence, for later replay.
     * The name used to store the sequence is specified when calling
     * saveSequence(), once the recording is complete.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int newSequence() throws YAPI_Exception
    {
        flushLayers();
        _sequence = "";
        _recording = true;
        return YAPI.SUCCESS;
    }

    /**
     * Stops recording display commands and saves the sequence into the specified
     * file on the display internal memory. The sequence can be later replayed
     * using playSequence().
     *
     * @param sequenceName : the name of the newly created sequence
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int saveSequence(String sequenceName) throws YAPI_Exception
    {
        flushLayers();
        _recording = false;
        _upload(sequenceName, _sequence.getBytes());
        //We need to use YPRINTF("") for Objective-C
        _sequence = String.format("");
        return YAPI.SUCCESS;
    }

    /**
     * Replays a display sequence previously recorded using
     * newSequence() and saveSequence().
     *
     * @param sequenceName : the name of the newly created sequence
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int playSequence(String sequenceName) throws YAPI_Exception
    {
        flushLayers();
        return sendCommand(String.format("S%s",sequenceName));
    }

    /**
     * Waits for a specified delay (in milliseconds) before playing next
     * commands in current sequence. This method can be used while
     * recording a display sequence, to insert a timed wait in the sequence
     * (without any immediate effect). It can also be used dynamically while
     * playing a pre-recorded sequence, to suspend or resume the execution of
     * the sequence. To cancel a delay, call the same method with a zero delay.
     *
     * @param delay_ms : the duration to wait, in milliseconds
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int pauseSequence(int delay_ms) throws YAPI_Exception
    {
        flushLayers();
        return sendCommand(String.format("W%d",delay_ms));
    }

    /**
     * Stops immediately any ongoing sequence replay.
     * The display is left as is.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int stopSequence() throws YAPI_Exception
    {
        flushLayers();
        return sendCommand("S");
    }

    /**
     * Uploads an arbitrary file (for instance a GIF file) to the display, to the
     * specified full path name. If a file already exists with the same path name,
     * its content is overwritten.
     *
     * @param pathname : path and name of the new file to create
     * @param content : binary buffer with the content to set
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int upload(String pathname,byte[] content) throws YAPI_Exception
    {
        return _upload(pathname, content);
    }

    /**
     * Copies the whole content of a layer to another layer. The color and transparency
     * of all the pixels from the destination layer are set to match the source pixels.
     * This method only affects the displayed content, but does not change any
     * property of the layer object.
     * Note that layer 0 has no transparency support (it is always completely opaque).
     *
     * @param srcLayerId : the identifier of the source layer (a number in range 0..layerCount-1)
     * @param dstLayerId : the identifier of the destination layer (a number in range 0..layerCount-1)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int copyLayerContent(int srcLayerId,int dstLayerId) throws YAPI_Exception
    {
        flushLayers();
        return sendCommand(String.format("o%d,%d",srcLayerId,dstLayerId));
    }

    /**
     * Swaps the whole content of two layers. The color and transparency of all the pixels from
     * the two layers are swapped. This method only affects the displayed content, but does
     * not change any property of the layer objects. In particular, the visibility of each
     * layer stays unchanged. When used between onae hidden layer and a visible layer,
     * this method makes it possible to easily implement double-buffering.
     * Note that layer 0 has no transparency support (it is always completely opaque).
     *
     * @param layerIdA : the first layer (a number in range 0..layerCount-1)
     * @param layerIdB : the second layer (a number in range 0..layerCount-1)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int swapLayerContent(int layerIdA,int layerIdB) throws YAPI_Exception
    {
        flushLayers();
        return sendCommand(String.format("E%d,%d",layerIdA,layerIdB));
    }

    /**
     * Continues the enumeration of displays started using yFirstDisplay().
     *
     * @return a pointer to a YDisplay object, corresponding to
     *         a display currently online, or a null pointer
     *         if there are no more displays to enumerate.
     */
    public  YDisplay nextDisplay()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindDisplay(next_hwid);
    }

    /**
     * Starts the enumeration of displays currently accessible.
     * Use the method YDisplay.nextDisplay() to iterate on
     * next displays.
     *
     * @return a pointer to a YDisplay object, corresponding to
     *         the first display currently online, or a null pointer
     *         if there are none.
     */
    public static YDisplay FirstDisplay()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Display");
        if (next_hwid == null)  return null;
        return FindDisplay(next_hwid);
    }

    //--- (end of generated code: YDisplay implementation)
    private YDisplayLayer[] _allDisplayLayers = null;
    private Boolean _recording = false;
    private String _sequence;

    /**
     * Returns a YDisplayLayer object that can be used to draw on the specified
     * layer. The content is displayed only when the layer is active on the
     * screen (and not masked by other overlapping layers).
     *
     * @param layerId : the identifier of the layer (a number in range 0..layerCount-1)
     *
     * @return an YDisplayLayer object
     *
     * @throws YAPI_Exception on error
     */
    public synchronized YDisplayLayer get_displayLayer(int layerId) throws YAPI_Exception
    {
        if (_allDisplayLayers == null) {
            int nb_display_layer = this.get_layerCount();
            _allDisplayLayers = new YDisplayLayer[nb_display_layer];
            for (int i = 0; i < nb_display_layer; i++) {
                _allDisplayLayers[i] = new YDisplayLayer(this, i);
            }
        }
        if (layerId < 0 || layerId >= _allDisplayLayers.length) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid layerId");
        }
        return _allDisplayLayers[layerId];
    }

    public synchronized int flushLayers() throws YAPI_Exception
    {
        if (_allDisplayLayers != null) {
            for (int i = 0; i < _allDisplayLayers.length; i++) {
                _allDisplayLayers[i].flush_now();
            }
        }
        return YAPI.SUCCESS;
    }

    public synchronized void resetHiddenLayerFlags() throws YAPI_Exception
    {
        if (_allDisplayLayers != null) {
            for (int i = 0; i < _allDisplayLayers.length; i++) {
                _allDisplayLayers[i].resetHiddenFlag();
            }
        }
    }

    public synchronized int sendCommand(String cmd) throws YAPI_Exception
    {
        if (!_recording) {
            return this.set_command(cmd);
        }
        this._sequence += cmd + "\n";
        return YAPI.SUCCESS;
    }
}

