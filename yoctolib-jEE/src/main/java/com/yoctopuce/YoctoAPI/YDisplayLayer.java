/*********************************************************************
 *
 * $Id: YDisplayLayer.java 75624 2026-08-19 08:17:06Z seb $
 *
 * YDisplayLayer Class: Image layer containing data to display
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

import java.util.ArrayList;
import java.util.Locale;

//--- (generated code: YDisplayLayer class start)
/**
 * YDisplayLayer Class: Interface for drawing into display layers, obtained by calling display.get_displayLayer.
 *
 * Each DisplayLayer represents an image layer containing objects
 * to display (bitmaps, text, etc.). The content is displayed only when
 * the layer is active on the screen (and not masked by other
 * overlapping layers).
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YDisplayLayer
{
//--- (end of generated code: YDisplayLayer class start)
    //--- (generated code: YDisplayLayer definitions)
    public enum ALIGN {
        TOP_LEFT(0),
        CENTER_LEFT(1),
        BASELINE_LEFT(2),
        BOTTOM_LEFT(3),
        TOP_CENTER(4),
        CENTER(5),
        BASELINE_CENTER(6),
        BOTTOM_CENTER(7),
        TOP_DECIMAL(8),
        CENTER_DECIMAL(9),
        BASELINE_DECIMAL(10),
        BOTTOM_DECIMAL(11),
        TOP_RIGHT(12),
        CENTER_RIGHT(13),
        BASELINE_RIGHT(14),
        BOTTOM_RIGHT(15);
        public final int value;
        ALIGN(int val)
        {
            this.value = val;
        }
        public static ALIGN fromInt(int intval)
        {
            switch(intval) {
            case 0:
                return TOP_LEFT;
            case 1:
                return CENTER_LEFT;
            case 2:
                return BASELINE_LEFT;
            case 3:
                return BOTTOM_LEFT;
            case 4:
                return TOP_CENTER;
            case 5:
                return CENTER;
            case 6:
                return BASELINE_CENTER;
            case 7:
                return BOTTOM_CENTER;
            case 8:
                return TOP_DECIMAL;
            case 9:
                return CENTER_DECIMAL;
            case 10:
                return BASELINE_DECIMAL;
            case 11:
                return BOTTOM_DECIMAL;
            case 12:
                return TOP_RIGHT;
            case 13:
                return CENTER_RIGHT;
            case 14:
                return BASELINE_RIGHT;
            case 15:
                return BOTTOM_RIGHT;
            }
            return null;
        }
    }

    public static final int NO_INK = -1;
    public static final int BG_INK = -2;
    public static final int FG_INK = -3;
    protected String _cmdbuff = "";
    protected boolean _hidden = false;
    protected int _polyPrevX = 0;
    protected int _polyPrevY = 0;

    //--- (end of generated code: YDisplayLayer definitions)

    private YDisplay _display;
    private final YAPIContext _yapi;
    private int _id;

    public YDisplayLayer(YDisplay parent, int id)
    {
        this._display = parent;
        this._yapi = parent._yapi;
        this._id = id;
        this._hidden = false;
    }

    //--- (generated code: YDisplayLayer implementation)

    public boolean must_be_flushed()
    {
        return _cmdbuff.length() > 0;
    }

    public int resetHiddenFlag()
    {
        _hidden = false;
        return YAPI.SUCCESS;
    }

    public int flush_now() throws YAPI_Exception
    {
        int res;
        res = YAPI.SUCCESS;
        if (_cmdbuff.length() > 0) {
            res = _display.sendCommand(_cmdbuff);
            _cmdbuff = "";
        }
        return res;
    }

    public int command_push(String cmd) throws YAPI_Exception
    {
        int res;
        res = YAPI.SUCCESS;
        if (_cmdbuff.length() + cmd.length() >= 64) {
            // force flush before, to prevent overflow
            flush_now();
        }
        if (_cmdbuff.length() == 0) {
            // always prepend layer ID first
            _cmdbuff = Integer.toString(_id);
        }
        _cmdbuff = _cmdbuff + cmd;
        return res;
    }

    public int command_flush(String cmd) throws YAPI_Exception
    {
        int res;

        res = command_push(cmd);
        if (_hidden) {
            return res;
        }
        if (_display.isFrozen()) {
            return res;
        }
        return flush_now();
    }

    /**
     * Reverts the layer to its initial state (fully transparent, default settings).
     * Reinitializes the drawing pointer to the upper left position,
     * and selects the most visible pen color. If you only want to erase the layer
     * content, use the method clear() instead.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int reset() throws YAPI_Exception
    {
        _hidden = false;
        return command_flush("X");
    }

    /**
     * Erases the whole content of the layer (makes it fully transparent).
     * This method does not change any other attribute of the layer.
     * To reinitialize the layer attributes to defaults settings, use the method
     * reset() instead.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int clear() throws YAPI_Exception
    {
        return command_flush("x");
    }

    /**
     * Selects the color to be used for all subsequent drawing functions,
     * for filling as well as for line and text drawing.
     * To select a different fill and outline color, use
     * selectFillColor and selectLineColor.
     * The pen color is provided as an RGB value.
     * For grayscale or monochrome displays, the value is
     * automatically converted to the proper range.
     *
     * @param color : the desired pen color, as a 24-bit RGB value
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int selectColorPen(int color) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "c%06x",color));
    }

    /**
     * Selects the pen gray level for all subsequent drawing functions,
     * for filling as well as for line and text drawing.
     * To select a different fill and outline color, use
     * selectFillColor and selectLineColor.
     * The gray level is provided as a number between
     * 0 (black) and 255 (white, or whichever the lightest color is).
     * For monochrome displays (without gray levels), any value
     * lower than 128 is rendered as black, and any value equal
     * or above to 128 is non-black.
     *
     * @param graylevel : the desired gray level, from 0 to 255
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int selectGrayPen(int graylevel) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "g%d",graylevel));
    }

    /**
     * Selects an eraser instead of a pen for all subsequent drawing functions,
     * except for bitmap copy functions. Any point drawn using the eraser
     * becomes transparent (as when the layer is empty), showing the other
     * layers beneath it.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int selectEraser() throws YAPI_Exception
    {
        return command_push("e");
    }

    /**
     * Selects the color to be used for filling rectangular bars,
     * discs and polygons. The color is provided as an RGB value.
     * For grayscale or monochrome displays, the value is
     * automatically converted to the proper range.
     * You can also use the constants FG_INK to use the
     * default drawing colour, BG_INK to use the default
     * background colour, and NO_INK to disable filling.
     *
     * @param color : the desired drawing color, as a 24-bit RGB value,
     *         or one of the constants NO_INK, FG_INK
     *         or BG_INK
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int selectFillColor(int color) throws YAPI_Exception
    {
        int r;
        int g;
        int b;
        if (color==-1) {
            return command_push("f_");
        }
        if (color==-2) {
            return command_push("f-");
        }
        if (color==-3) {
            return command_push("f.");
        }
        r = ((color >> 20) & 15);
        g = ((color >> 12) & 15);
        b = ((color >> 4) & 15);
        return command_push(String.format(Locale.US, "f%x%x%x",r,g,b));
    }

    /**
     * Selects the color to be used for drawing the outline of rectangular
     * bars, discs and polygons, as well as for drawing lines and text.
     * The color is provided as an RGB value.
     * For grayscale or monochrome displays, the value is
     * automatically converted to the proper range.
     * You can also use the constants FG_INK to use the
     * default drawing colour, BG_INK to use the default
     * background colour, and NO_INK to disable outline drawing.
     *
     * @param color : the desired drawing color, as a 24-bit RGB value,
     *         or one of the constants NO_INK, FG_INK
     *         or BG_INK
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int selectLineColor(int color) throws YAPI_Exception
    {
        int r;
        int g;
        int b;
        if (color==-1) {
            return command_push("l_");
        }
        if (color==-2) {
            return command_push("l-");
        }
        if (color==-3) {
            return command_push("l*");
        }
        r = ((color >> 20) & 15);
        g = ((color >> 12) & 15);
        b = ((color >> 4) & 15);
        return command_push(String.format(Locale.US, "l%x%x%x",r,g,b));
    }

    /**
     * Selects the line width for drawing the outline of rectangular
     * bars, discs and polygons, as well as for drawing lines.
     *
     * @param width : the desired line width, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int selectLineWidth(int width) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "t%d",width));
    }

    public int setAntialiasingMode(boolean mode) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "a%d",(mode ? 1 : 0)));
    }

    /**
     * Draws a single pixel at the specified position.
     *
     * @param x : the distance from left of layer, in pixels
     * @param y : the distance from top of layer, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawPixel(int x,int y) throws YAPI_Exception
    {
        return command_flush(String.format(Locale.US, "P%d,%d",x,y));
    }

    /**
     * Draws an empty rectangle at a specified position.
     *
     * @param x1 : the distance from left of layer to the left border of the rectangle, in pixels
     * @param y1 : the distance from top of layer to the top border of the rectangle, in pixels
     * @param x2 : the distance from left of layer to the right border of the rectangle, in pixels
     * @param y2 : the distance from top of layer to the bottom border of the rectangle, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawRect(int x1,int y1,int x2,int y2) throws YAPI_Exception
    {
        return command_flush(String.format(Locale.US, "R%d,%d,%d,%d",x1,y1,x2,y2));
    }

    /**
     * Draws a filled rectangular bar at a specified position.
     *
     * @param x1 : the distance from left of layer to the left border of the rectangle, in pixels
     * @param y1 : the distance from top of layer to the top border of the rectangle, in pixels
     * @param x2 : the distance from left of layer to the right border of the rectangle, in pixels
     * @param y2 : the distance from top of layer to the bottom border of the rectangle, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawBar(int x1,int y1,int x2,int y2) throws YAPI_Exception
    {
        return command_flush(String.format(Locale.US, "B%d,%d,%d,%d",x1,y1,x2,y2));
    }

    /**
     * Draws an empty circle at a specified position.
     *
     * @param x : the distance from left of layer to the center of the circle, in pixels
     * @param y : the distance from top of layer to the center of the circle, in pixels
     * @param r : the radius of the circle, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawCircle(int x,int y,int r) throws YAPI_Exception
    {
        return command_flush(String.format(Locale.US, "C%d,%d,%d",x,y,r));
    }

    /**
     * Draws a filled disc at a given position.
     *
     * @param x : the distance from left of layer to the center of the disc, in pixels
     * @param y : the distance from top of layer to the center of the disc, in pixels
     * @param r : the radius of the disc, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawDisc(int x,int y,int r) throws YAPI_Exception
    {
        return command_flush(String.format(Locale.US, "D%d,%d,%d",x,y,r));
    }

    /**
     * Selects a font to use for the next text drawing functions, by providing the name of the
     * font file. You can use a built-in font as well as a font file that you have previously
     * uploaded to the device built-in memory. If you experience problems selecting a font
     * file, check the device logs for any error message such as missing font file or bad font
     * file format.
     *
     *  @param fontname : the font file name, embedded fonts are 8x8.yfm, Small.yfm, Medium.yfm, Large.yfm
     * (not available on Yocto-MiniDisplay).
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int selectFont(String fontname) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "&%s%c",fontname,27));
    }

    /**
     * Draws a text string at the specified position. The point of the text that is aligned
     * to the specified pixel position is called the anchor point, and can be chosen among
     * several options. Text is rendered from left to right, without implicit wrapping.
     *
     * @param x : the distance from left of layer to the text anchor point, in pixels
     * @param y : the distance from top of layer to the text anchor point, in pixels
     * @param anchor : the text anchor point, chosen among the YDisplayLayer.ALIGN enumeration:
     *         YDisplayLayer.ALIGN.TOP_LEFT,         YDisplayLayer.ALIGN.CENTER_LEFT,
     *         YDisplayLayer.ALIGN.BASELINE_LEFT,    YDisplayLayer.ALIGN.BOTTOM_LEFT,
     *         YDisplayLayer.ALIGN.TOP_CENTER,       YDisplayLayer.ALIGN.CENTER,
     *         YDisplayLayer.ALIGN.BASELINE_CENTER,  YDisplayLayer.ALIGN.BOTTOM_CENTER,
     *         YDisplayLayer.ALIGN.TOP_DECIMAL,      YDisplayLayer.ALIGN.CENTER_DECIMAL,
     *         YDisplayLayer.ALIGN.BASELINE_DECIMAL, YDisplayLayer.ALIGN.BOTTOM_DECIMAL,
     *         YDisplayLayer.ALIGN.TOP_RIGHT,        YDisplayLayer.ALIGN.CENTER_RIGHT,
     *         YDisplayLayer.ALIGN.BASELINE_RIGHT,   YDisplayLayer.ALIGN.BOTTOM_RIGHT.
     * @param text : the text string to draw
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawText(int x,int y,ALIGN anchor,String text) throws YAPI_Exception
    {
        int textlen;
        String destname;
        textlen = text.length();
        if (textlen > 60) {
            if (textlen > 1000) {
                _display._throw(YAPI.INVALID_ARGUMENT, "text too large (max 1000 characters)");
                return YAPI.INVALID_ARGUMENT;
            }
            _display.flushLayers();
            destname = String.format(Locale.US, "layer%d:T%d,%d,%d,",_id,x,y,anchor.value);
            return _display.upload(destname,(text).getBytes(_yapi._deviceCharset));
        }
        return command_flush(String.format(Locale.US, "T%d,%d,%d,%s%c",x,y,anchor.value,text,27));
    }

    /**
     * Draws an image previously uploaded to the device filesystem, at the specified position.
     * At present time, GIF images are the only supported image format. If you experience
     * problems using an image file, check the device logs for any error message such as
     * missing image file or bad image file format.
     *
     * @param x : the distance from left of layer to the left of the image, in pixels
     * @param y : the distance from top of layer to the top of the image, in pixels
     * @param imagename : the GIF file name
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawImage(int x,int y,String imagename) throws YAPI_Exception
    {
        return command_flush(String.format(Locale.US, "*%d,%d,%s%c",x,y,imagename,27));
    }

    /**
     * Draws a GIF image provided as a binary buffer at the specified position.
     * If the image drawing must be included in an animation sequence, save it
     * in the device filesystem first and use drawImage instead.
     *
     * @param x : the distance from left of layer to the left of the image, in pixels
     * @param y : the distance from top of layer to the top of the image, in pixels
     * @param gifimage : a binary object with the content of a GIF file
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawGIF(int x,int y,byte[] gifimage) throws YAPI_Exception
    {
        String destname;
        _display.flushLayers();
        destname = String.format(Locale.US, "layer%d:G,-1@%d,%d",_id,x,y);
        return _display.upload(destname,gifimage);
    }

    /**
     * Draws a bitmap at the specified position. The bitmap is provided as a binary object,
     * where each pixel maps to a bit, from left to right and from top to bottom.
     * The most significant bit of each byte maps to the leftmost pixel, and the least
     * significant bit maps to the rightmost pixel. Bits set to 1 are drawn using the
     * layer selected pen color. Bits set to 0 are drawn using the specified background
     * color, unless NO_INK (-1) is specified, in which case they are not
     * drawn at all (as if transparent).
     *
     * @param x : the distance from left of layer to the left of the bitmap, in pixels
     * @param y : the distance from top of layer to the top of the bitmap, in pixels
     * @param w : the width of the bitmap, in pixels
     * @param bitmap : a binary object
     * @param bgcol : the RGB background color to use for zero bits, as a 24-bit RGB value,
     *         or one of the constants NO_INK, FG_INK or BG_INK
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawBitmap(int x,int y,int w,byte[] bitmap,int bgcol) throws YAPI_Exception
    {
        String destname;
        int r;
        int g;
        int b;
        String rgbcol;
        if ((w < 0) || (w > 512)) {
            _display._throw(YAPI.INVALID_ARGUMENT, "bitmap width must be in range 1..512");
            return YAPI.INVALID_ARGUMENT;
        }
        _display.flushLayers();
        if (bgcol <= 255) {
            if (bgcol >= -1) {
                // backward-compatible behaviour (gray level)
                rgbcol = String.format(Locale.US, "%d",bgcol);
            } else {
                // background color or foreground color
                if (bgcol <= -3) {
                    rgbcol = "#.";
                } else {
                    rgbcol = "#-";
                }
            }
        } else {
            // RGB color
            r = ((bgcol >> 20) & 15);
            g = ((bgcol >> 12) & 15);
            b = ((bgcol >> 4) & 15);
            rgbcol = String.format(Locale.US, "#%x%x%x",r,g,b);
        }
        destname = String.format(Locale.US, "layer%d:%d,%s@%d,%d",_id,w,rgbcol,x,y);
        return _display.upload(destname,bitmap);
    }

    /**
     * Draws a color pixmap at the specified position. The pixmap is provided as a binary
     * object, where each byte maps to one pixel. The 24 bit RGB value corresponding to each
     * byte value is defined in the palette provided as extra argument.
     * The palette maximal size is 8, and it is recommended to use the smallest possible
     * palette size to optimize the size of data to be sent to the display.
     * The height of the pixmap is implicitely given by the pixmap buffer size.
     *
     * @param x : the distance from left of layer to the left of the pixmap, in pixels
     * @param y : the distance from top of layer to the top of the pixmap, in pixels
     * @param w : the width of the pixmap, in pixels
     * @param pixmap : a binary buffer where each byte maps to one pixel
     * @param palette : an array of 24-bit RGB values, defining the color for each byte value in pixmap
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int drawPixmap(int x,int y,int w,byte[] pixmap,ArrayList<Integer> palette) throws YAPI_Exception
    {
        byte[] gifimage;
        gifimage = _display.gifEncode(pixmap, palette, w, false);
        return drawGIF(x, y, gifimage);
    }

    /**
     * Moves the drawing pointer of this layer to the specified position.
     *
     * @param x : the distance from left of layer, in pixels
     * @param y : the distance from top of layer, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int moveTo(int x,int y) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "@%d,%d",x,y));
    }

    /**
     * Draws a line from current drawing pointer position to the specified position.
     * The specified destination pixel is included in the line. The pointer position
     * is then moved to the end point of the line.
     *
     * @param x : the distance from left of layer to the end point of the line, in pixels
     * @param y : the distance from top of layer to the end point of the line, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int lineTo(int x,int y) throws YAPI_Exception
    {
        return command_flush(String.format(Locale.US, "-%d,%d",x,y));
    }

    /**
     * Starts drawing a polygon with the first corner at the specified position.
     *
     * @param x : the distance from left of layer, in pixels
     * @param y : the distance from top of layer, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int polygonStart(int x,int y) throws YAPI_Exception
    {
        _polyPrevX = x;
        _polyPrevY = y;
        return command_push(String.format(Locale.US, "[%d,%d",x,y));
    }

    /**
     * Adds a point to the currently open polygon, previously opened using
     * polygonStart.
     *
     * @param x : the distance from left of layer to the new point, in pixels
     * @param y : the distance from top of layer to the new point, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int polygonAdd(int x,int y) throws YAPI_Exception
    {
        int dx;
        int dy;
        dx = x - _polyPrevX;
        dy = y - _polyPrevY;
        _polyPrevX = x;
        _polyPrevY = y;
        return command_flush(String.format(Locale.US, ";%d,%d",dx,dy));
    }

    /**
     * Closes the currently open polygon, fill its content the fill color currently
     * selected for the layer, and draw its outline using the selected line color.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int polygonEnd() throws YAPI_Exception
    {
        return command_flush("]");
    }

    /**
     * Outputs a message in the console area, and advances the console pointer accordingly.
     * The console pointer position is automatically moved to the beginning
     * of the next line when a newline character is met, or when the right margin
     * is hit. When the new text to display extends below the lower margin, the
     * console area is automatically scrolled up.
     *
     * @param text : the message to display
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int consoleOut(String text) throws YAPI_Exception
    {
        int textlen;
        String destname;
        textlen = text.length();
        if (textlen > 60) {
            if (textlen > 1000) {
                _display._throw(YAPI.INVALID_ARGUMENT, "text too large (max 1000 characters)");
                return YAPI.INVALID_ARGUMENT;
            }
            _display.flushLayers();
            destname = String.format(Locale.US, "layer%d:!",_id);
            return _display.upload(destname,(text).getBytes(_yapi._deviceCharset));
        }
        return command_flush(String.format(Locale.US, "!%s%c",text,27));
    }

    /**
     * Sets up display margins for the consoleOut function.
     *
     * @param x1 : the distance from left of layer to the left margin, in pixels
     * @param y1 : the distance from top of layer to the top margin, in pixels
     * @param x2 : the distance from left of layer to the right margin, in pixels
     * @param y2 : the distance from top of layer to the bottom margin, in pixels
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setConsoleMargins(int x1,int y1,int x2,int y2) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "m%d,%d,%d,%d",x1,y1,x2,y2));
    }

    /**
     * Sets up the background color used by the clearConsole function and by
     * the console scrolling feature.
     *
     * @param bgcol : the background gray level to use when scrolling (0 = black,
     *         255 = white), or -1 for transparent
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setConsoleBackground(int bgcol) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "b%d",bgcol));
    }

    /**
     * Sets up the wrapping behavior used by the consoleOut function.
     *
     * @param wordwrap : true to wrap only between words,
     *         false to wrap on the last column anyway.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setConsoleWordWrap(boolean wordwrap) throws YAPI_Exception
    {
        return command_push(String.format(Locale.US, "w%d",(wordwrap ? 1 : 0)));
    }

    /**
     * Blanks the console area within console margins, and resets the console pointer
     * to the upper left corner of the console.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int clearConsole() throws YAPI_Exception
    {
        return command_flush("^");
    }

    /**
     * Sets the position of the layer relative to the display upper left corner.
     * When smooth scrolling is used, the display offset of the layer is
     * automatically updated during the next milliseconds to animate the move of the layer.
     *
     * @param x : the distance from left of display to the upper left corner of the layer
     * @param y : the distance from top of display to the upper left corner of the layer
     * @param scrollTime : number of milliseconds to use for smooth scrolling, or
     *         0 if the scrolling should be immediate.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setLayerPosition(int x,int y,int scrollTime) throws YAPI_Exception
    {
        return command_flush(String.format(Locale.US, "#%d,%d,%d",x,y,scrollTime));
    }

    /**
     * Hides the layer. The state of the layer is preserved but the layer is not displayed
     * on the screen until the next call to unhide(). Hiding the layer can positively
     * affect the drawing speed, since it postpones the rendering until all operations are
     * completed (double-buffering).
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int hide() throws YAPI_Exception
    {
        command_push("h");
        _hidden = true;
        return flush_now();
    }

    /**
     * Shows the layer. Shows the layer again after a hide command.
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int unhide() throws YAPI_Exception
    {
        _hidden = false;
        return command_flush("s");
    }

    /**
     * Gets parent YDisplay. Returns the parent YDisplay object of the current YDisplayLayer.
     *
     * @return an YDisplay object
     */
    public YDisplay get_display()
    {
        return _display;
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
        return _display.get_displayWidth();
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
        return _display.get_displayHeight();
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
        return _display.get_layerWidth();
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
        return _display.get_layerHeight();
    }

    //--- (end of generated code: YDisplayLayer implementation)
}
