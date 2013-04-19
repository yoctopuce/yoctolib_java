/*********************************************************************
 *
 * $Id: pic24config.php 8861 2012-11-26 22:00:46Z mvuilleu $
 *
 * Implements yFindDisplay(), the high-level API for Display functions
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

  //--- (generated code: globals)
  //--- (end of generated code: globals)
/**
 * YDisplay Class: Display function interface
 * 
 * Yoctopuce display interface has been designed to eaasily
 * show information and images. The device provides built-in
 * multi-layer rendering. Layers can be drawn offline, individually,
 * and freely moved on the display.
 */
public class YDisplay extends YFunction
{
    //--- (generated code: definitions)
    private YDisplay.UpdateCallback _valueCallbackDisplay;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid powerState value
     */
  public static final int POWERSTATE_OFF = 0;
  public static final int POWERSTATE_ON = 1;
  public static final int POWERSTATE_INVALID = -1;

    /**
     * invalid startupSeq value
     */
    public static final String STARTUPSEQ_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid brightness value
     */
    public static final int BRIGHTNESS_INVALID = YAPI.INVALID_UNSIGNED;
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
    public static final int DISPLAYWIDTH_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid displayHeight value
     */
    public static final int DISPLAYHEIGHT_INVALID = YAPI.INVALID_UNSIGNED;
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
    public static final int LAYERWIDTH_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid layerHeight value
     */
    public static final int LAYERHEIGHT_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid layerCount value
     */
    public static final int LAYERCOUNT_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    //--- (end of generated code: definitions)

    /**
     * UdateCallback for Display
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YDisplay function, String functionValue);
    }



    //--- (generated code: YDisplay implementation)

    /**
     * Returns the logical name of the display.
     * 
     * @return a string corresponding to the logical name of the display
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the display.
     * 
     * @return a string corresponding to the logical name of the display
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the display. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the display
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
     * Changes the logical name of the display. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the display
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the display (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the display (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the display (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the display (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the power state of the display.
     * 
     * @return either YDisplay.POWERSTATE_OFF or YDisplay.POWERSTATE_ON, according to the power state of the display
     * 
     * @throws YAPI_Exception
     */
    public int get_powerState()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("powerState");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the power state of the display.
     * 
     * @return either Y_POWERSTATE_OFF or Y_POWERSTATE_ON, according to the power state of the display
     * 
     * @throws YAPI_Exception
     */
    public int getPowerState() throws YAPI_Exception

    { return get_powerState(); }

    /**
     * Changes the power state of the display.
     * 
     * @param newval : either YDisplay.POWERSTATE_OFF or YDisplay.POWERSTATE_ON, according to the power
     * state of the display
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_powerState( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = (newval > 0 ? "1" : "0");
        _setAttr("powerState",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the power state of the display.
     * 
     * @param newval : either Y_POWERSTATE_OFF or Y_POWERSTATE_ON, according to the power state of the display
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setPowerState( int newval)  throws YAPI_Exception

    { return set_powerState(newval); }

    /**
     * Returns the name of the sequence to play when the displayed is powered on.
     * 
     * @return a string corresponding to the name of the sequence to play when the displayed is powered on
     * 
     * @throws YAPI_Exception
     */
    public String get_startupSeq()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("startupSeq");
        return json_val;
    }

    /**
     * Returns the name of the sequence to play when the displayed is powered on.
     * 
     * @return a string corresponding to the name of the sequence to play when the displayed is powered on
     * 
     * @throws YAPI_Exception
     */
    public String getStartupSeq() throws YAPI_Exception

    { return get_startupSeq(); }

    /**
     * Changes the name of the sequence to play when the displayed is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the name of the sequence to play when the displayed is powered on
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_startupSeq( String  newval)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int setStartupSeq( String newval)  throws YAPI_Exception

    { return set_startupSeq(newval); }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     * 
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     * 
     * @throws YAPI_Exception
     */
    public int get_brightness()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("brightness");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     * 
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     * 
     * @throws YAPI_Exception
     */
    public int getBrightness() throws YAPI_Exception

    { return get_brightness(); }

    /**
     * Changes the brightness of the display. The parameter is a value between 0 and
     * 100. Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : an integer corresponding to the brightness of the display
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_brightness( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
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
     * @throws YAPI_Exception
     */
    public int setBrightness( int newval)  throws YAPI_Exception

    { return set_brightness(newval); }

    /**
     * Returns the currently selected display orientation.
     * 
     * @return a value among YDisplay.ORIENTATION_LEFT, YDisplay.ORIENTATION_UP,
     * YDisplay.ORIENTATION_RIGHT and YDisplay.ORIENTATION_DOWN corresponding to the currently selected
     * display orientation
     * 
     * @throws YAPI_Exception
     */
    public int get_orientation()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("orientation");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the currently selected display orientation.
     * 
     * @return a value among Y_ORIENTATION_LEFT, Y_ORIENTATION_UP, Y_ORIENTATION_RIGHT and
     * Y_ORIENTATION_DOWN corresponding to the currently selected display orientation
     * 
     * @throws YAPI_Exception
     */
    public int getOrientation() throws YAPI_Exception

    { return get_orientation(); }

    /**
     * Changes the display orientation. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     * 
     * @param newval : a value among YDisplay.ORIENTATION_LEFT, YDisplay.ORIENTATION_UP,
     * YDisplay.ORIENTATION_RIGHT and YDisplay.ORIENTATION_DOWN corresponding to the display orientation
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_orientation( int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(newval);
        _setAttr("orientation",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the display orientation. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     * 
     * @param newval : a value among Y_ORIENTATION_LEFT, Y_ORIENTATION_UP, Y_ORIENTATION_RIGHT and
     * Y_ORIENTATION_DOWN corresponding to the display orientation
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setOrientation( int newval)  throws YAPI_Exception

    { return set_orientation(newval); }

    /**
     * Returns the display width, in pixels.
     * 
     * @return an integer corresponding to the display width, in pixels
     * 
     * @throws YAPI_Exception
     */
    public int get_displayWidth()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("displayWidth");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the display width, in pixels.
     * 
     * @return an integer corresponding to the display width, in pixels
     * 
     * @throws YAPI_Exception
     */
    public int getDisplayWidth() throws YAPI_Exception

    { return get_displayWidth(); }

    /**
     * Returns the display height, in pixels.
     * 
     * @return an integer corresponding to the display height, in pixels
     * 
     * @throws YAPI_Exception
     */
    public int get_displayHeight()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("displayHeight");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the display height, in pixels.
     * 
     * @return an integer corresponding to the display height, in pixels
     * 
     * @throws YAPI_Exception
     */
    public int getDisplayHeight() throws YAPI_Exception

    { return get_displayHeight(); }

    /**
     * Returns the display type: monochrome, gray levels or full color.
     * 
     * @return a value among YDisplay.DISPLAYTYPE_MONO, YDisplay.DISPLAYTYPE_GRAY and
     * YDisplay.DISPLAYTYPE_RGB corresponding to the display type: monochrome, gray levels or full color
     * 
     * @throws YAPI_Exception
     */
    public int get_displayType()  throws YAPI_Exception
    {
        String json_val = (String) _getFixedAttr("displayType");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the display type: monochrome, gray levels or full color.
     * 
     * @return a value among Y_DISPLAYTYPE_MONO, Y_DISPLAYTYPE_GRAY and Y_DISPLAYTYPE_RGB corresponding to
     * the display type: monochrome, gray levels or full color
     * 
     * @throws YAPI_Exception
     */
    public int getDisplayType() throws YAPI_Exception

    { return get_displayType(); }

    /**
     * Returns the width of the layers to draw on, in pixels.
     * 
     * @return an integer corresponding to the width of the layers to draw on, in pixels
     * 
     * @throws YAPI_Exception
     */
    public int get_layerWidth()  throws YAPI_Exception
    {
        String json_val = (String) _getFixedAttr("layerWidth");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the width of the layers to draw on, in pixels.
     * 
     * @return an integer corresponding to the width of the layers to draw on, in pixels
     * 
     * @throws YAPI_Exception
     */
    public int getLayerWidth() throws YAPI_Exception

    { return get_layerWidth(); }

    /**
     * Returns the height of the layers to draw on, in pixels.
     * 
     * @return an integer corresponding to the height of the layers to draw on, in pixels
     * 
     * @throws YAPI_Exception
     */
    public int get_layerHeight()  throws YAPI_Exception
    {
        String json_val = (String) _getFixedAttr("layerHeight");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the height of the layers to draw on, in pixels.
     * 
     * @return an integer corresponding to the height of the layers to draw on, in pixels
     * 
     * @throws YAPI_Exception
     */
    public int getLayerHeight() throws YAPI_Exception

    { return get_layerHeight(); }

    /**
     * Returns the number of available layers to draw on.
     * 
     * @return an integer corresponding to the number of available layers to draw on
     * 
     * @throws YAPI_Exception
     */
    public int get_layerCount()  throws YAPI_Exception
    {
        String json_val = (String) _getFixedAttr("layerCount");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the number of available layers to draw on.
     * 
     * @return an integer corresponding to the number of available layers to draw on
     * 
     * @throws YAPI_Exception
     */
    public int getLayerCount() throws YAPI_Exception

    { return get_layerCount(); }

    public String get_command()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("command");
        return json_val;
    }

    public String getCommand() throws YAPI_Exception

    { return get_command(); }

    public int set_command( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("command",rest_val);
        return YAPI.SUCCESS;
    }

    public int setCommand( String newval)  throws YAPI_Exception

    { return set_command(newval); }

    /**
     * Clears the display screen and resets all display layers to their default state.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int resetAll()  throws YAPI_Exception
    {
        flushLayers(); 
        resetHiddenLayerFlags();
        return sendCommand("Z"); 
        
    }

    /**
     * Smoothly changes the brightness of the screen to produce a fade-in or fade-out
     * effect.
     * 
     * @param brightness: the new screen brightness
     * @param duration: duration of the brightness transition, in milliseconds.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int fade(int brightness,int duration)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int newSequence()  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int saveSequence(String sequenceName)  throws YAPI_Exception
    {
        flushLayers();
        _recording = false; 
        _upload(sequenceName, _sequence);
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
     * @throws YAPI_Exception
     */
    public int playSequence(String sequenceName)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int pauseSequence(int delay_ms)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int stopSequence()  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int upload(String pathname,byte[] content)  throws YAPI_Exception
    {
        return _upload(pathname,content);
        
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
     * @throws YAPI_Exception
     */
    public int copyLayerContent(int srcLayerId,int dstLayerId)  throws YAPI_Exception
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
     * @throws YAPI_Exception
     */
    public int swapLayerContent(int layerIdA,int layerIdB)  throws YAPI_Exception
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
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindDisplay(next_hwid);
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
    {   YFunction yfunc = YAPI.getFunction("Display", func);
        if (yfunc != null) {
            return (YDisplay) yfunc;
        }
        return new YDisplay(func);
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
        String next_hwid = YAPI.getFirstHardwareId("Display");
        if (next_hwid == null)  return null;
        return FindDisplay(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YDisplay(String func)
    {
        super("Display", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackDisplay != null) {
            _valueCallbackDisplay.yNewValue(this, newvalue);
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
        return super.hasCallbackRegistered() || (_valueCallbackDisplay!=null);
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
    public void registerValueCallback(YDisplay.UpdateCallback callback)
    {
         _valueCallbackDisplay =  callback;
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
     * @throws YAPI_Exception
     */
    public synchronized YDisplayLayer get_displayLayer(int layerId) throws YAPI_Exception
    {
        if (_allDisplayLayers==null) {
            int nb_display_layer = this.get_layerCount();
            _allDisplayLayers = new YDisplayLayer[nb_display_layer];
            for(int i=0; i < nb_display_layer; i++) {
                _allDisplayLayers[i] = new YDisplayLayer(this, i);
            }
        }
        if(layerId < 0 || layerId >= _allDisplayLayers.length) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid layerId");
        }
        return _allDisplayLayers[layerId];
    }
    
    /**
     * Force a flush of all commands buffered by all layers.
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * On failure, throws an exception or returns a negative error code.
     */
    public synchronized int flushLayers() throws YAPI_Exception
    {
        if(_allDisplayLayers != null) {
            for(int i = 0; i < _allDisplayLayers.length; i++) {
                _allDisplayLayers[i].flush_now();
            }
        }
        return YAPI.SUCCESS;
    }
    
    public synchronized void resetHiddenLayerFlags() throws YAPI_Exception
    {
        if(_allDisplayLayers != null) {
            for(int i = 0; i < _allDisplayLayers.length; i++) {
                _allDisplayLayers[i].resetHiddenFlag();
            }
        }
    }
    
    /**
     * Add a given command string to the currently recorded display sequence
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * On failure, throws an exception or returns a negative error code.
     */
    public synchronized int sendCommand(String cmd) throws YAPI_Exception
    {
        if(!_recording) {
            return this.set_command(cmd);
        }
        this._sequence += cmd+"\n";
        return YAPI.SUCCESS;
    }    
};

