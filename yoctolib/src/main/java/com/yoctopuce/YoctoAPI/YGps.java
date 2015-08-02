/*********************************************************************
 *
 * $Id: YGps.java 19746 2015-03-17 10:34:00Z seb $
 *
 * Implements FindGps(), the high-level API for Gps functions
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

//--- (YGps return codes)
//--- (end of YGps return codes)
//--- (YGps class start)
/**
 * YGps Class: GPS function interface
 *
 * The Gps function allows you to extract positionning
 * data from the GPS device. This class can provides
 * complete positionning information: However, if you
 * whish to define callbacks on position changes, you
 * should use the YLatitude et YLongitude classes.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YGps extends YFunction
{
//--- (end of YGps class start)
//--- (YGps definitions)
    /**
     * invalid isFixed value
     */
    public static final int ISFIXED_FALSE = 0;
    public static final int ISFIXED_TRUE = 1;
    public static final int ISFIXED_INVALID = -1;
    /**
     * invalid satCount value
     */
    public static final long SATCOUNT_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid coordSystem value
     */
    public static final int COORDSYSTEM_GPS_DMS = 0;
    public static final int COORDSYSTEM_GPS_DM = 1;
    public static final int COORDSYSTEM_GPS_D = 2;
    public static final int COORDSYSTEM_INVALID = -1;
    /**
     * invalid latitude value
     */
    public static final String LATITUDE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid longitude value
     */
    public static final String LONGITUDE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid dilution value
     */
    public static final double DILUTION_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid altitude value
     */
    public static final double ALTITUDE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid groundSpeed value
     */
    public static final double GROUNDSPEED_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid direction value
     */
    public static final double DIRECTION_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid unixTime value
     */
    public static final long UNIXTIME_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid dateTime value
     */
    public static final String DATETIME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid utcOffset value
     */
    public static final int UTCOFFSET_INVALID = YAPI.INVALID_INT;
    /**
     * invalid command value
     */
    public static final String COMMAND_INVALID = YAPI.INVALID_STRING;
    protected int _isFixed = ISFIXED_INVALID;
    protected long _satCount = SATCOUNT_INVALID;
    protected int _coordSystem = COORDSYSTEM_INVALID;
    protected String _latitude = LATITUDE_INVALID;
    protected String _longitude = LONGITUDE_INVALID;
    protected double _dilution = DILUTION_INVALID;
    protected double _altitude = ALTITUDE_INVALID;
    protected double _groundSpeed = GROUNDSPEED_INVALID;
    protected double _direction = DIRECTION_INVALID;
    protected long _unixTime = UNIXTIME_INVALID;
    protected String _dateTime = DATETIME_INVALID;
    protected int _utcOffset = UTCOFFSET_INVALID;
    protected String _command = COMMAND_INVALID;
    protected UpdateCallback _valueCallbackGps = null;

    /**
     * Deprecated UpdateCallback for Gps
     */
    public interface UpdateCallback {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YGps function, String functionValue);
    }

    /**
     * TimedReportCallback for Gps
     */
    public interface TimedReportCallback {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YGps  function, YMeasure measure);
    }
    //--- (end of YGps definitions)


    /**
     *
     * @param func : functionid
     */
    protected YGps(String func)
    {
        super(func);
        _className = "Gps";
        //--- (YGps attributes initialization)
        //--- (end of YGps attributes initialization)
    }

    //--- (YGps implementation)
    @Override
    protected void  _parseAttr(JSONObject json_val) throws JSONException
    {
        if (json_val.has("isFixed")) {
            _isFixed = json_val.getInt("isFixed") > 0 ? 1 : 0;
        }
        if (json_val.has("satCount")) {
            _satCount = json_val.getLong("satCount");
        }
        if (json_val.has("coordSystem")) {
            _coordSystem = json_val.getInt("coordSystem");
        }
        if (json_val.has("latitude")) {
            _latitude = json_val.getString("latitude");
        }
        if (json_val.has("longitude")) {
            _longitude = json_val.getString("longitude");
        }
        if (json_val.has("dilution")) {
            _dilution = Math.round(json_val.getDouble("dilution") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("altitude")) {
            _altitude = Math.round(json_val.getDouble("altitude") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("groundSpeed")) {
            _groundSpeed = Math.round(json_val.getDouble("groundSpeed") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("direction")) {
            _direction = Math.round(json_val.getDouble("direction") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("unixTime")) {
            _unixTime = json_val.getLong("unixTime");
        }
        if (json_val.has("dateTime")) {
            _dateTime = json_val.getString("dateTime");
        }
        if (json_val.has("utcOffset")) {
            _utcOffset = json_val.getInt("utcOffset");
        }
        if (json_val.has("command")) {
            _command = json_val.getString("command");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns TRUE if the receiver has found enough satellites to work
     *
     *  @return either YGps.ISFIXED_FALSE or YGps.ISFIXED_TRUE, according to TRUE if the receiver has found
     * enough satellites to work
     *
     * @throws YAPI_Exception on error
     */
    public int get_isFixed() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ISFIXED_INVALID;
            }
        }
        return _isFixed;
    }

    /**
     * Returns TRUE if the receiver has found enough satellites to work
     *
     *  @return either Y_ISFIXED_FALSE or Y_ISFIXED_TRUE, according to TRUE if the receiver has found
     * enough satellites to work
     *
     * @throws YAPI_Exception on error
     */
    public int getIsFixed() throws YAPI_Exception
    {
        return get_isFixed();
    }

    /**
     * Returns the count of visible satellites.
     *
     * @return an integer corresponding to the count of visible satellites
     *
     * @throws YAPI_Exception on error
     */
    public long get_satCount() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return SATCOUNT_INVALID;
            }
        }
        return _satCount;
    }

    /**
     * Returns the count of visible satellites.
     *
     * @return an integer corresponding to the count of visible satellites
     *
     * @throws YAPI_Exception on error
     */
    public long getSatCount() throws YAPI_Exception
    {
        return get_satCount();
    }

    /**
     * Returns the representation system used for positioning data.
     *
     *  @return a value among YGps.COORDSYSTEM_GPS_DMS, YGps.COORDSYSTEM_GPS_DM and YGps.COORDSYSTEM_GPS_D
     * corresponding to the representation system used for positioning data
     *
     * @throws YAPI_Exception on error
     */
    public int get_coordSystem() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return COORDSYSTEM_INVALID;
            }
        }
        return _coordSystem;
    }

    /**
     * Returns the representation system used for positioning data.
     *
     *  @return a value among Y_COORDSYSTEM_GPS_DMS, Y_COORDSYSTEM_GPS_DM and Y_COORDSYSTEM_GPS_D
     * corresponding to the representation system used for positioning data
     *
     * @throws YAPI_Exception on error
     */
    public int getCoordSystem() throws YAPI_Exception
    {
        return get_coordSystem();
    }

    /**
     * Changes the representation system used for positioning data.
     *
     *  @param newval : a value among YGps.COORDSYSTEM_GPS_DMS, YGps.COORDSYSTEM_GPS_DM and
     * YGps.COORDSYSTEM_GPS_D corresponding to the representation system used for positioning data
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_coordSystem(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("coordSystem",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the representation system used for positioning data.
     *
     *  @param newval : a value among Y_COORDSYSTEM_GPS_DMS, Y_COORDSYSTEM_GPS_DM and Y_COORDSYSTEM_GPS_D
     * corresponding to the representation system used for positioning data
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setCoordSystem(int newval)  throws YAPI_Exception
    {
        return set_coordSystem(newval);
    }

    /**
     * Returns the current latitude.
     *
     * @return a string corresponding to the current latitude
     *
     * @throws YAPI_Exception on error
     */
    public String get_latitude() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LATITUDE_INVALID;
            }
        }
        return _latitude;
    }

    /**
     * Returns the current latitude.
     *
     * @return a string corresponding to the current latitude
     *
     * @throws YAPI_Exception on error
     */
    public String getLatitude() throws YAPI_Exception
    {
        return get_latitude();
    }

    /**
     * Returns the current longitude.
     *
     * @return a string corresponding to the current longitude
     *
     * @throws YAPI_Exception on error
     */
    public String get_longitude() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return LONGITUDE_INVALID;
            }
        }
        return _longitude;
    }

    /**
     * Returns the current longitude.
     *
     * @return a string corresponding to the current longitude
     *
     * @throws YAPI_Exception on error
     */
    public String getLongitude() throws YAPI_Exception
    {
        return get_longitude();
    }

    /**
     * Returns the current horizontal dilution of precision,
     * the smaller that number is, the better .
     *
     * @return a floating point number corresponding to the current horizontal dilution of precision,
     *         the smaller that number is, the better
     *
     * @throws YAPI_Exception on error
     */
    public double get_dilution() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DILUTION_INVALID;
            }
        }
        return _dilution;
    }

    /**
     * Returns the current horizontal dilution of precision,
     * the smaller that number is, the better .
     *
     * @return a floating point number corresponding to the current horizontal dilution of precision,
     *         the smaller that number is, the better
     *
     * @throws YAPI_Exception on error
     */
    public double getDilution() throws YAPI_Exception
    {
        return get_dilution();
    }

    /**
     * Returns the current altitude. Beware:  GPS technology
     * is very inaccurate regarding altitude.
     *
     * @return a floating point number corresponding to the current altitude
     *
     * @throws YAPI_Exception on error
     */
    public double get_altitude() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return ALTITUDE_INVALID;
            }
        }
        return _altitude;
    }

    /**
     * Returns the current altitude. Beware:  GPS technology
     * is very inaccurate regarding altitude.
     *
     * @return a floating point number corresponding to the current altitude
     *
     * @throws YAPI_Exception on error
     */
    public double getAltitude() throws YAPI_Exception
    {
        return get_altitude();
    }

    /**
     * Returns the current ground speed in Km/h.
     *
     * @return a floating point number corresponding to the current ground speed in Km/h
     *
     * @throws YAPI_Exception on error
     */
    public double get_groundSpeed() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return GROUNDSPEED_INVALID;
            }
        }
        return _groundSpeed;
    }

    /**
     * Returns the current ground speed in Km/h.
     *
     * @return a floating point number corresponding to the current ground speed in Km/h
     *
     * @throws YAPI_Exception on error
     */
    public double getGroundSpeed() throws YAPI_Exception
    {
        return get_groundSpeed();
    }

    /**
     * Returns the current move bearing in degrees, zero
     * is the true (geographic) north.
     *
     * @return a floating point number corresponding to the current move bearing in degrees, zero
     *         is the true (geographic) north
     *
     * @throws YAPI_Exception on error
     */
    public double get_direction() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DIRECTION_INVALID;
            }
        }
        return _direction;
    }

    /**
     * Returns the current move bearing in degrees, zero
     * is the true (geographic) north.
     *
     * @return a floating point number corresponding to the current move bearing in degrees, zero
     *         is the true (geographic) north
     *
     * @throws YAPI_Exception on error
     */
    public double getDirection() throws YAPI_Exception
    {
        return get_direction();
    }

    /**
     * Returns the current time in Unix format (number of
     * seconds elapsed since Jan 1st, 1970).
     *
     * @return an integer corresponding to the current time in Unix format (number of
     *         seconds elapsed since Jan 1st, 1970)
     *
     * @throws YAPI_Exception on error
     */
    public long get_unixTime() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return UNIXTIME_INVALID;
            }
        }
        return _unixTime;
    }

    /**
     * Returns the current time in Unix format (number of
     * seconds elapsed since Jan 1st, 1970).
     *
     * @return an integer corresponding to the current time in Unix format (number of
     *         seconds elapsed since Jan 1st, 1970)
     *
     * @throws YAPI_Exception on error
     */
    public long getUnixTime() throws YAPI_Exception
    {
        return get_unixTime();
    }

    /**
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss"
     *
     * @return a string corresponding to the current time in the form "YYYY/MM/DD hh:mm:ss"
     *
     * @throws YAPI_Exception on error
     */
    public String get_dateTime() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return DATETIME_INVALID;
            }
        }
        return _dateTime;
    }

    /**
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss"
     *
     * @return a string corresponding to the current time in the form "YYYY/MM/DD hh:mm:ss"
     *
     * @throws YAPI_Exception on error
     */
    public String getDateTime() throws YAPI_Exception
    {
        return get_dateTime();
    }

    /**
     * Returns the number of seconds between current time and UTC time (time zone).
     *
     * @return an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @throws YAPI_Exception on error
     */
    public int get_utcOffset() throws YAPI_Exception
    {
        if (_cacheExpiration <= YAPI.GetTickCount()) {
            if (load(YAPI.SafeYAPI().DefaultCacheValidity) != YAPI.SUCCESS) {
                return UTCOFFSET_INVALID;
            }
        }
        return _utcOffset;
    }

    /**
     * Returns the number of seconds between current time and UTC time (time zone).
     *
     * @return an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @throws YAPI_Exception on error
     */
    public int getUtcOffset() throws YAPI_Exception
    {
        return get_utcOffset();
    }

    /**
     * Changes the number of seconds between current time and UTC time (time zone).
     * The timezone is automatically rounded to the nearest multiple of 15 minutes.
     * If current UTC time is known, the current time is automatically be updated according to the selected time zone.
     *
     * @param newval : an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_utcOffset(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = Integer.toString(newval);
        _setAttr("utcOffset",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of seconds between current time and UTC time (time zone).
     * The timezone is automatically rounded to the nearest multiple of 15 minutes.
     * If current UTC time is known, the current time is automatically be updated according to the selected time zone.
     *
     * @param newval : an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setUtcOffset(int newval)  throws YAPI_Exception
    {
        return set_utcOffset(newval);
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
     * Retrieves a GPS for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the GPS is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YGps.isOnline() to test if the GPS is
     * indeed online at a given time. In case of ambiguity when looking for
     * a GPS by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param func : a string that uniquely characterizes the GPS
     *
     * @return a YGps object allowing you to drive the GPS.
     */
    public static YGps FindGps(String func)
    {
        YGps obj;
        obj = (YGps) YFunction._FindFromCache("Gps", func);
        if (obj == null) {
            obj = new YGps(func);
            YFunction._AddToCache("Gps", func, obj);
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
        _valueCallbackGps = callback;
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
        if (_valueCallbackGps != null) {
            _valueCallbackGps.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    /**
     * Continues the enumeration of GPS started using yFirstGps().
     *
     * @return a pointer to a YGps object, corresponding to
     *         a GPS currently online, or a null pointer
     *         if there are no more GPS to enumerate.
     */
    public  YGps nextGps()
    {
        String next_hwid;
        try {
            String hwid = SafeYAPI().resolveFunction(_className, _func).getHardwareId();
            next_hwid = SafeYAPI().getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindGps(next_hwid);
    }

    /**
     * Starts the enumeration of GPS currently accessible.
     * Use the method YGps.nextGps() to iterate on
     * next GPS.
     *
     * @return a pointer to a YGps object, corresponding to
     *         the first GPS currently online, or a null pointer
     *         if there are none.
     */
    public static YGps FirstGps()
    {
        String next_hwid = SafeYAPI().getFirstHardwareId("Gps");
        if (next_hwid == null)  return null;
        return FindGps(next_hwid);
    }

    //--- (end of YGps implementation)
}

