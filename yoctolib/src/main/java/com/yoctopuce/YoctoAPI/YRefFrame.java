/*********************************************************************
 *
 * $Id: YRefFrame.java 20468 2015-05-29 10:24:28Z seb $
 *
 * Implements FindRefFrame(), the high-level API for RefFrame functions
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
import java.util.ArrayList;

//--- (YRefFrame return codes)
//--- (end of YRefFrame return codes)
//--- (YRefFrame class start)
/**
 * YRefFrame Class: Reference frame configuration
 *
 * This class is used to setup the base orientation of the Yocto-3D, so that
 * the orientation functions, relative to the earth surface plane, use
 * the proper reference frame. The class also implements a tridimensional
 * sensor calibration process, which can compensate for local variations
 * of standard gravity and improve the precision of the tilt sensors.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YRefFrame extends YFunction
{
//--- (end of YRefFrame class start)
//--- (YRefFrame definitions)
    /**
     * invalid mountPos value
     */
    public static final int MOUNTPOS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid bearing value
     */
    public static final double BEARING_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid calibrationParam value
     */
    public static final String CALIBRATIONPARAM_INVALID = YAPI.INVALID_STRING;
    public enum MOUNTPOSITION {
        BOTTOM(0),
        TOP(1),
        FRONT(2),
        REAR(3),
        RIGHT(4),
        LEFT(5);
        public final int value;
        private MOUNTPOSITION(int val)
        {
            this.value = val;
        }
        public static MOUNTPOSITION fromInt(int intval)
        {
            switch(intval) {
            case 0:
                return BOTTOM;
            case 1:
                return TOP;
            case 2:
                return FRONT;
            case 3:
                return REAR;
            case 4:
                return RIGHT;
            case 5:
                return LEFT;
            }
            return null;
        }
    }

    public enum MOUNTORIENTATION {
        TWELVE(0),
        THREE(1),
        SIX(2),
        NINE(3);
        public final int value;
        private MOUNTORIENTATION(int val)
        {
            this.value = val;
        }
        public static MOUNTORIENTATION fromInt(int intval)
        {
            switch(intval) {
            case 0:
                return TWELVE;
            case 1:
                return THREE;
            case 2:
                return SIX;
            case 3:
                return NINE;
            }
            return null;
        }
    }

    protected int _mountPos = MOUNTPOS_INVALID;
    protected double _bearing = BEARING_INVALID;
    protected String _calibrationParam = CALIBRATIONPARAM_INVALID;
    protected UpdateCallback _valueCallbackRefFrame = null;
    protected int _calibStage = 0;
    protected String _calibStageHint;
    protected int _calibStageProgress = 0;
    protected int _calibProgress = 0;
    protected String _calibLogMsg;
    protected String _calibSavedParams;
    protected int _calibCount = 0;
    protected int _calibInternalPos = 0;
    protected int _calibPrevTick = 0;
    protected ArrayList<Integer> _calibOrient = new ArrayList<Integer>();
    protected ArrayList<Double> _calibDataAccX = new ArrayList<Double>();
    protected ArrayList<Double> _calibDataAccY = new ArrayList<Double>();
    protected ArrayList<Double> _calibDataAccZ = new ArrayList<Double>();
    protected ArrayList<Double> _calibDataAcc = new ArrayList<Double>();
    protected double _calibAccXOfs = 0;
    protected double _calibAccYOfs = 0;
    protected double _calibAccZOfs = 0;
    protected double _calibAccXScale = 0;
    protected double _calibAccYScale = 0;
    protected double _calibAccZScale = 0;

    /**
     * Deprecated UpdateCallback for RefFrame
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YRefFrame function, String functionValue);
    }

    /**
     * TimedReportCallback for RefFrame
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YRefFrame  function, YMeasure measure);
    }
    //--- (end of YRefFrame definitions)


    /**
     *
     * @param func : functionid
     */
    protected YRefFrame(String func)
    {
        super(func);
        _className = "RefFrame";
        //--- (YRefFrame attributes initialization)
        //--- (end of YRefFrame attributes initialization)
    }

    //--- (YRefFrame implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("mountPos")) {
            _mountPos = json_val.getInt("mountPos");
        }
        if (json_val.has("bearing")) {
            _bearing = Math.round(json_val.getDouble("bearing") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("calibrationParam")) {
            _calibrationParam = json_val.getString("calibrationParam");
        }
        super._parseAttr(json_val);
    }

    /**
     * @throws YAPI_Exception on error
     */
    public int get_mountPos() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return MOUNTPOS_INVALID;
            }
        }
        return _mountPos;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public int getMountPos() throws YAPI_Exception
    {
        return get_mountPos();
    }

    public int set_mountPos(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("mountPos",rest_val);
        return YAPI.SUCCESS;
    }

    public int setMountPos(int newval)  throws YAPI_Exception
    {
        return set_mountPos(newval);
    }

    /**
     * Changes the reference bearing used by the compass. The relative bearing
     * indicated by the compass is the difference between the measured magnetic
     * heading and the reference bearing indicated here.
     *
     * For instance, if you setup as reference bearing the value of the earth
     * magnetic declination, the compass will provide the orientation relative
     * to the geographic North.
     *
     * Similarly, when the sensor is not mounted along the standard directions
     * because it has an additional yaw angle, you can set this angle in the reference
     * bearing so that the compass provides the expected natural direction.
     *
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : a floating point number corresponding to the reference bearing used by the compass
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_bearing(double  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Long.toString(Math.round(newval * 65536.0));
        _setAttr("bearing",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the reference bearing used by the compass. The relative bearing
     * indicated by the compass is the difference between the measured magnetic
     * heading and the reference bearing indicated here.
     *
     * For instance, if you setup as reference bearing the value of the earth
     * magnetic declination, the compass will provide the orientation relative
     * to the geographic North.
     *
     * Similarly, when the sensor is not mounted along the standard directions
     * because it has an additional yaw angle, you can set this angle in the reference
     * bearing so that the compass provides the expected natural direction.
     *
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : a floating point number corresponding to the reference bearing used by the compass
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setBearing(double newval)  throws YAPI_Exception
    {
        return set_bearing(newval);
    }

    /**
     * Returns the reference bearing used by the compass. The relative bearing
     * indicated by the compass is the difference between the measured magnetic
     * heading and the reference bearing indicated here.
     *
     * @return a floating point number corresponding to the reference bearing used by the compass
     *
     * @throws YAPI_Exception on error
     */
    public double get_bearing() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return BEARING_INVALID;
            }
        }
        return _bearing;
    }

    /**
     * Returns the reference bearing used by the compass. The relative bearing
     * indicated by the compass is the difference between the measured magnetic
     * heading and the reference bearing indicated here.
     *
     * @return a floating point number corresponding to the reference bearing used by the compass
     *
     * @throws YAPI_Exception on error
     */
    public double getBearing() throws YAPI_Exception
    {
        return get_bearing();
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String get_calibrationParam() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return CALIBRATIONPARAM_INVALID;
            }
        }
        return _calibrationParam;
    }

    /**
     * @throws YAPI_Exception on error
     */
    public String getCalibrationParam() throws YAPI_Exception
    {
        return get_calibrationParam();
    }

    public int set_calibrationParam(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("calibrationParam",rest_val);
        return YAPI.SUCCESS;
    }

    public int setCalibrationParam(String newval)  throws YAPI_Exception
    {
        return set_calibrationParam(newval);
    }

    /**
     * Retrieves a reference frame for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the reference frame is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRefFrame.isOnline() to test if the reference frame is
     * indeed online at a given time. In case of ambiguity when looking for
     * a reference frame by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the reference frame
     *
     * @return a YRefFrame object allowing you to drive the reference frame.
     */
    public static YRefFrame FindRefFrame(String func)
    {
        YRefFrame obj;
        obj = (YRefFrame) YFunction._FindFromCache("RefFrame", func);
        if (obj == null) {
            obj = new YRefFrame(func);
            YFunction._AddToCache("RefFrame", func, obj);
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
        _valueCallbackRefFrame = callback;
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
        if (_valueCallbackRefFrame != null) {
            _valueCallbackRefFrame.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Returns the installation position of the device, as configured
     * in order to define the reference frame for the compass and the
     * pitch/roll tilt sensors.
     *
     * @return a value among the YRefFrame.MOUNTPOSITION enumeration
     *         (YRefFrame.MOUNTPOSITION_BOTTOM,   YRefFrame.MOUNTPOSITION_TOP,
     *         YRefFrame.MOUNTPOSITION_FRONT,    YRefFrame.MOUNTPOSITION_RIGHT,
     *         YRefFrame.MOUNTPOSITION_REAR,     YRefFrame.MOUNTPOSITION_LEFT),
     *         corresponding to the installation in a box, on one of the six faces.
     *
     * @throws YAPI_Exception on error
     */
    public MOUNTPOSITION get_mountPosition() throws YAPI_Exception
    {
        int position;
        position = get_mountPos();
        return MOUNTPOSITION.fromInt(((position) >> (2)));
    }

    /**
     * Returns the installation orientation of the device, as configured
     * in order to define the reference frame for the compass and the
     * pitch/roll tilt sensors.
     *
     * @return a value among the enumeration YRefFrame.MOUNTORIENTATION
     *         (YRefFrame.MOUNTORIENTATION_TWELVE, YRefFrame.MOUNTORIENTATION_THREE,
     *         YRefFrame.MOUNTORIENTATION_SIX,     YRefFrame.MOUNTORIENTATION_NINE)
     *         corresponding to the orientation of the "X" arrow on the device,
     *         as on a clock dial seen from an observer in the center of the box.
     *         On the bottom face, the 12H orientation points to the front, while
     *         on the top face, the 12H orientation points to the rear.
     *
     * @throws YAPI_Exception on error
     */
    public MOUNTORIENTATION get_mountOrientation() throws YAPI_Exception
    {
        int position;
        position = get_mountPos();
        return MOUNTORIENTATION.fromInt(((position) & (3)));
    }

    /**
     * Changes the compass and tilt sensor frame of reference. The magnetic compass
     * and the tilt sensors (pitch and roll) naturally work in the plane
     * parallel to the earth surface. In case the device is not installed upright
     * and horizontally, you must select its reference orientation (parallel to
     * the earth surface) so that the measures are made relative to this position.
     *
     * @param position : a value among the YRefFrame.MOUNTPOSITION enumeration
     *         (YRefFrame.MOUNTPOSITION_BOTTOM,   YRefFrame.MOUNTPOSITION_TOP,
     *         YRefFrame.MOUNTPOSITION_FRONT,    YRefFrame.MOUNTPOSITION_RIGHT,
     *         YRefFrame.MOUNTPOSITION_REAR,     YRefFrame.MOUNTPOSITION_LEFT),
     *         corresponding to the installation in a box, on one of the six faces.
     * @param orientation : a value among the enumeration YRefFrame.MOUNTORIENTATION
     *         (YRefFrame.MOUNTORIENTATION_TWELVE, YRefFrame.MOUNTORIENTATION_THREE,
     *         YRefFrame.MOUNTORIENTATION_SIX,     YRefFrame.MOUNTORIENTATION_NINE)
     *         corresponding to the orientation of the "X" arrow on the device,
     *         as on a clock dial seen from an observer in the center of the box.
     *         On the bottom face, the 12H orientation points to the front, while
     *         on the top face, the 12H orientation points to the rear.
     *
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @throws YAPI_Exception on error
     */
    public int set_mountPosition(MOUNTPOSITION position,MOUNTORIENTATION orientation) throws YAPI_Exception
    {
        int mixedPos;
        mixedPos = ((position.value) << (2)) + orientation.value;
        return set_mountPos(mixedPos);
    }

    public int _calibSort(int start,int stopidx)
    {
        int idx;
        int changed;
        double a;
        double b;
        double xa;
        double xb;
        
        // bubble sort is good since we will re-sort again after offset adjustment
        changed = 1;
        while (changed > 0) {
            changed = 0;
            a = _calibDataAcc.get(start).doubleValue();
            idx = start + 1;
            while (idx < stopidx) {
                b = _calibDataAcc.get(idx).doubleValue();
                if (a > b) {
                    _calibDataAcc.set( idx-1, b);
                    _calibDataAcc.set( idx, a);
                    xa = _calibDataAccX.get(idx-1).doubleValue();
                    xb = _calibDataAccX.get(idx).doubleValue();
                    _calibDataAccX.set( idx-1, xb);
                    _calibDataAccX.set( idx, xa);
                    xa = _calibDataAccY.get(idx-1).doubleValue();
                    xb = _calibDataAccY.get(idx).doubleValue();
                    _calibDataAccY.set( idx-1, xb);
                    _calibDataAccY.set( idx, xa);
                    xa = _calibDataAccZ.get(idx-1).doubleValue();
                    xb = _calibDataAccZ.get(idx).doubleValue();
                    _calibDataAccZ.set( idx-1, xb);
                    _calibDataAccZ.set( idx, xa);
                    changed = changed + 1;
                } else {
                    a = b;
                }
                idx = idx + 1;
            }
        }
        return 0;
    }

    /**
     * Initiates the sensors tridimensional calibration process.
     * This calibration is used at low level for inertial position estimation
     * and to enhance the precision of the tilt sensors.
     *
     * After calling this method, the device should be moved according to the
     * instructions provided by method get_3DCalibrationHint,
     * and more3DCalibration should be invoked about 5 times per second.
     * The calibration procedure is completed when the method
     * get_3DCalibrationProgress returns 100. At this point,
     * the computed calibration parameters can be applied using method
     * save3DCalibration. The calibration process can be canceled
     * at any time using method cancel3DCalibration.
     *
     * @throws YAPI_Exception on error
     */
    public int start3DCalibration() throws YAPI_Exception
    {
        if (!(isOnline())) {
            return YAPI.DEVICE_NOT_FOUND;
        }
        if (_calibStage != 0) {
            cancel3DCalibration();
        }
        _calibSavedParams = get_calibrationParam();
        set_calibrationParam("0");
        _calibCount = 50;
        _calibStage = 1;
        _calibStageHint = "Set down the device on a steady horizontal surface";
        _calibStageProgress = 0;
        _calibProgress = 1;
        _calibInternalPos = 0;
        _calibPrevTick = (int) ((YAPI.GetTickCount()) & (0x7FFFFFFF));
        _calibOrient.clear();
        _calibDataAccX.clear();
        _calibDataAccY.clear();
        _calibDataAccZ.clear();
        _calibDataAcc.clear();
        return YAPI.SUCCESS;
    }

    /**
     * Continues the sensors tridimensional calibration process previously
     * initiated using method start3DCalibration.
     * This method should be called approximately 5 times per second, while
     * positioning the device according to the instructions provided by method
     * get_3DCalibrationHint. Note that the instructions change during
     * the calibration process.
     *
     * @throws YAPI_Exception on error
     */
    public int more3DCalibration() throws YAPI_Exception
    {
        int currTick;
        byte[] jsonData;
        double xVal;
        double yVal;
        double zVal;
        double xSq;
        double ySq;
        double zSq;
        double norm;
        int orient;
        int idx;
        int intpos;
        int err;
        // make sure calibration has been started
        if (_calibStage == 0) {
            return YAPI.INVALID_ARGUMENT;
        }
        if (_calibProgress == 100) {
            return YAPI.SUCCESS;
        }
        
        // make sure we leave at least 160ms between samples
        currTick =  (int) ((YAPI.GetTickCount()) & (0x7FFFFFFF));
        if (((currTick - _calibPrevTick) & (0x7FFFFFFF)) < 160) {
            return YAPI.SUCCESS;
        }
        // load current accelerometer values, make sure we are on a straight angle
        // (default timeout to 0,5 sec without reading measure when out of range)
        _calibStageHint = "Set down the device on a steady horizontal surface";
        _calibPrevTick = ((currTick + 500) & (0x7FFFFFFF));
        jsonData = _download("api/accelerometer.json");
        xVal = YAPI._atoi(_json_get_key(jsonData, "xValue")) / 65536.0;
        yVal = YAPI._atoi(_json_get_key(jsonData, "yValue")) / 65536.0;
        zVal = YAPI._atoi(_json_get_key(jsonData, "zValue")) / 65536.0;
        xSq = xVal * xVal;
        if (xSq >= 0.04 && xSq < 0.64) {
            return YAPI.SUCCESS;
        }
        if (xSq >= 1.44) {
            return YAPI.SUCCESS;
        }
        ySq = yVal * yVal;
        if (ySq >= 0.04 && ySq < 0.64) {
            return YAPI.SUCCESS;
        }
        if (ySq >= 1.44) {
            return YAPI.SUCCESS;
        }
        zSq = zVal * zVal;
        if (zSq >= 0.04 && zSq < 0.64) {
            return YAPI.SUCCESS;
        }
        if (zSq >= 1.44) {
            return YAPI.SUCCESS;
        }
        norm = java.lang.Math.sqrt(xSq + ySq + zSq);
        if (norm < 0.8 || norm > 1.2) {
            return YAPI.SUCCESS;
        }
        _calibPrevTick = currTick;
        
        // Determine the device orientation index
        orient = 0;
        if (zSq > 0.5) {
            if (zVal > 0) {
                orient = 0;
            } else {
                orient = 1;
            }
        }
        if (xSq > 0.5) {
            if (xVal > 0) {
                orient = 2;
            } else {
                orient = 3;
            }
        }
        if (ySq > 0.5) {
            if (yVal > 0) {
                orient = 4;
            } else {
                orient = 5;
            }
        }
        
        // Discard measures that are not in the proper orientation
        if (_calibStageProgress == 0) {
            idx = 0;
            err = 0;
            while (idx + 1 < _calibStage) {
                if (_calibOrient.get(idx).intValue() == orient) {
                    err = 1;
                }
                idx = idx + 1;
            }
            if (err != 0) {
                _calibStageHint = "Turn the device on another face";
                return YAPI.SUCCESS;
            }
            _calibOrient.add(orient);
        } else {
            if (orient != _calibOrient.get(_calibStage-1).intValue()) {
                _calibStageHint = "Not yet done, please move back to the previous face";
                return YAPI.SUCCESS;
            }
        }
        
        // Save measure
        _calibStageHint = "calibrating..";
        _calibDataAccX.add(xVal);
        _calibDataAccY.add(yVal);
        _calibDataAccZ.add(zVal);
        _calibDataAcc.add(norm);
        _calibInternalPos = _calibInternalPos + 1;
        _calibProgress = 1 + 16 * (_calibStage - 1) + ((16 * _calibInternalPos) / (_calibCount));
        if (_calibInternalPos < _calibCount) {
            _calibStageProgress = 1 + ((99 * _calibInternalPos) / (_calibCount));
            return YAPI.SUCCESS;
        }
        
        // Stage done, compute preliminary result
        intpos = (_calibStage - 1) * _calibCount;
        _calibSort(intpos, intpos + _calibCount);
        intpos = intpos + ((_calibCount) / (2));
        _calibLogMsg = String.format("Stage %d: median is %d,%d,%d", _calibStage,
        (int) (double)Math.round(1000*_calibDataAccX.get(intpos).doubleValue()),
        (int) (double)Math.round(1000*_calibDataAccY.get(intpos).doubleValue()),(int) (double)Math.round(1000*_calibDataAccZ.get(intpos).doubleValue()));
        
        // move to next stage
        _calibStage = _calibStage + 1;
        if (_calibStage < 7) {
            _calibStageHint = "Turn the device on another face";
            _calibPrevTick = ((currTick + 500) & (0x7FFFFFFF));
            _calibStageProgress = 0;
            _calibInternalPos = 0;
            return YAPI.SUCCESS;
        }
        // Data collection completed, compute accelerometer shift
        xVal = 0;
        yVal = 0;
        zVal = 0;
        idx = 0;
        while (idx < 6) {
            intpos = idx * _calibCount + ((_calibCount) / (2));
            orient = _calibOrient.get(idx).intValue();
            if (orient == 0 || orient == 1) {
                zVal = zVal + _calibDataAccZ.get(intpos).doubleValue();
            }
            if (orient == 2 || orient == 3) {
                xVal = xVal + _calibDataAccX.get(intpos).doubleValue();
            }
            if (orient == 4 || orient == 5) {
                yVal = yVal + _calibDataAccY.get(intpos).doubleValue();
            }
            idx = idx + 1;
        }
        _calibAccXOfs = xVal / 2.0;
        _calibAccYOfs = yVal / 2.0;
        _calibAccZOfs = zVal / 2.0;
        
        // Recompute all norms, taking into account the computed shift, and re-sort
        intpos = 0;
        while (intpos < _calibDataAcc.size()) {
            xVal = _calibDataAccX.get(intpos).doubleValue() - _calibAccXOfs;
            yVal = _calibDataAccY.get(intpos).doubleValue() - _calibAccYOfs;
            zVal = _calibDataAccZ.get(intpos).doubleValue() - _calibAccZOfs;
            norm = java.lang.Math.sqrt(xVal * xVal + yVal * yVal + zVal * zVal);
            _calibDataAcc.set( intpos, norm);
            intpos = intpos + 1;
        }
        idx = 0;
        while (idx < 6) {
            intpos = idx * _calibCount;
            _calibSort(intpos, intpos + _calibCount);
            idx = idx + 1;
        }
        
        // Compute the scaling factor for each axis
        xVal = 0;
        yVal = 0;
        zVal = 0;
        idx = 0;
        while (idx < 6) {
            intpos = idx * _calibCount + ((_calibCount) / (2));
            orient = _calibOrient.get(idx).intValue();
            if (orient == 0 || orient == 1) {
                zVal = zVal + _calibDataAcc.get(intpos).doubleValue();
            }
            if (orient == 2 || orient == 3) {
                xVal = xVal + _calibDataAcc.get(intpos).doubleValue();
            }
            if (orient == 4 || orient == 5) {
                yVal = yVal + _calibDataAcc.get(intpos).doubleValue();
            }
            idx = idx + 1;
        }
        _calibAccXScale = xVal / 2.0;
        _calibAccYScale = yVal / 2.0;
        _calibAccZScale = zVal / 2.0;
        
        // Report completion
        _calibProgress = 100;
        _calibStageHint = "Calibration data ready for saving";
        return YAPI.SUCCESS;
    }

    /**
     * Returns instructions to proceed to the tridimensional calibration initiated with
     * method start3DCalibration.
     *
     * @return a character string.
     */
    public String get_3DCalibrationHint()
    {
        return _calibStageHint;
    }

    /**
     * Returns the global process indicator for the tridimensional calibration
     * initiated with method start3DCalibration.
     *
     * @return an integer between 0 (not started) and 100 (stage completed).
     */
    public int get_3DCalibrationProgress()
    {
        return _calibProgress;
    }

    /**
     * Returns index of the current stage of the calibration
     * initiated with method start3DCalibration.
     *
     * @return an integer, growing each time a calibration stage is completed.
     */
    public int get_3DCalibrationStage()
    {
        return _calibStage;
    }

    /**
     * Returns the process indicator for the current stage of the calibration
     * initiated with method start3DCalibration.
     *
     * @return an integer between 0 (not started) and 100 (stage completed).
     */
    public int get_3DCalibrationStageProgress()
    {
        return _calibStageProgress;
    }

    /**
     * Returns the latest log message from the calibration process.
     * When no new message is available, returns an empty string.
     *
     * @return a character string.
     */
    public String get_3DCalibrationLogMsg()
    {
        String msg;
        msg = _calibLogMsg;
        _calibLogMsg = "";
        return msg;
    }

    /**
     * Applies the sensors tridimensional calibration parameters that have just been computed.
     * Remember to call the saveToFlash()  method of the module if the changes
     * must be kept when the device is restarted.
     *
     * @throws YAPI_Exception on error
     */
    public int save3DCalibration() throws YAPI_Exception
    {
        int shiftX;
        int shiftY;
        int shiftZ;
        int scaleExp;
        int scaleX;
        int scaleY;
        int scaleZ;
        int scaleLo;
        int scaleHi;
        String newcalib;
        if (_calibProgress != 100) {
            return YAPI.INVALID_ARGUMENT;
        }
        
        // Compute integer values (correction unit is 732ug/count)
        shiftX = -(int) (double)Math.round(_calibAccXOfs / 0.000732);
        if (shiftX < 0) {
            shiftX = shiftX + 65536;
        }
        shiftY = -(int) (double)Math.round(_calibAccYOfs / 0.000732);
        if (shiftY < 0) {
            shiftY = shiftY + 65536;
        }
        shiftZ = -(int) (double)Math.round(_calibAccZOfs / 0.000732);
        if (shiftZ < 0) {
            shiftZ = shiftZ + 65536;
        }
        scaleX = (int) (double)Math.round(2048.0 / _calibAccXScale) - 2048;
        scaleY = (int) (double)Math.round(2048.0 / _calibAccYScale) - 2048;
        scaleZ = (int) (double)Math.round(2048.0 / _calibAccZScale) - 2048;
        if (scaleX < -2048 || scaleX >= 2048 || scaleY < -2048 || scaleY >= 2048 || scaleZ < -2048 || scaleZ >= 2048) {
            scaleExp = 3;
        } else {
            if (scaleX < -1024 || scaleX >= 1024 || scaleY < -1024 || scaleY >= 1024 || scaleZ < -1024 || scaleZ >= 1024) {
                scaleExp = 2;
            } else {
                if (scaleX < -512 || scaleX >= 512 || scaleY < -512 || scaleY >= 512 || scaleZ < -512 || scaleZ >= 512) {
                    scaleExp = 1;
                } else {
                    scaleExp = 0;
                }
            }
        }
        if (scaleExp > 0) {
            scaleX = ((scaleX) >> (scaleExp));
            scaleY = ((scaleY) >> (scaleExp));
            scaleZ = ((scaleZ) >> (scaleExp));
        }
        if (scaleX < 0) {
            scaleX = scaleX + 1024;
        }
        if (scaleY < 0) {
            scaleY = scaleY + 1024;
        }
        if (scaleZ < 0) {
            scaleZ = scaleZ + 1024;
        }
        scaleLo = ((((scaleY) & (15))) << (12)) + ((scaleX) << (2)) + scaleExp;
        scaleHi = ((scaleZ) << (6)) + ((scaleY) >> (4));
        
        // Save calibration parameters
        newcalib = String.format("5,%d,%d,%d,%d,%d", shiftX, shiftY, shiftZ, scaleLo,scaleHi);
        _calibStage = 0;
        return set_calibrationParam(newcalib);
    }

    /**
     * Aborts the sensors tridimensional calibration process et restores normal settings.
     *
     * @throws YAPI_Exception on error
     */
    public int cancel3DCalibration() throws YAPI_Exception
    {
        if (_calibStage == 0) {
            return YAPI.SUCCESS;
        }
        // may throw an exception
        _calibStage = 0;
        return set_calibrationParam(_calibSavedParams);
    }

    /**
     * Continues the enumeration of reference frames started using yFirstRefFrame().
     *
     * @return a pointer to a YRefFrame object, corresponding to
     *         a reference frame currently online, or a null pointer
     *         if there are no more reference frames to enumerate.
     */
    public  YRefFrame nextRefFrame()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindRefFrame(next_hwid);
    }

    /**
     * Starts the enumeration of reference frames currently accessible.
     * Use the method YRefFrame.nextRefFrame() to iterate on
     * next reference frames.
     *
     * @return a pointer to a YRefFrame object, corresponding to
     *         the first reference frame currently online, or a null pointer
     *         if there are none.
     */
    public static YRefFrame FirstRefFrame()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("RefFrame");
        if (next_hwid == null)  return null;
        return FindRefFrame(next_hwid);
    }

    //--- (end of YRefFrame implementation)
}

