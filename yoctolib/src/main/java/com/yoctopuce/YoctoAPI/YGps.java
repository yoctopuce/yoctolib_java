/*
 *
 *  $Id: YGps.java 39658 2020-03-12 15:36:29Z seb $
 *
 *  Implements FindGps(), the high-level API for Gps functions
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

//--- (YGps return codes)
//--- (end of YGps return codes)
//--- (YGps yapiwrapper)
//--- (end of YGps yapiwrapper)
//--- (YGps class start)
/**
 * YGps Class: Geolocalization control interface (GPS, GNSS, ...), available for instance in the Yocto-GPS-V2
 *
 * The YGps class allows you to retrieve positioning
 * data from a GPS/GNSS sensor. This class can provides
 * complete positioning information. However, if you
 * wish to define callbacks on position changes or record
 * the position in the datalogger, you
 * should use the YLatitude et YLongitude classes.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
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
     * invalid satPerConst value
     */
    public static final long SATPERCONST_INVALID = YAPI.INVALID_LONG;
    /**
     * invalid gpsRefreshRate value
     */
    public static final double GPSREFRESHRATE_INVALID = YAPI.INVALID_DOUBLE;
    /**
     * invalid coordSystem value
     */
    public static final int COORDSYSTEM_GPS_DMS = 0;
    public static final int COORDSYSTEM_GPS_DM = 1;
    public static final int COORDSYSTEM_GPS_D = 2;
    public static final int COORDSYSTEM_INVALID = -1;
    /**
     * invalid constellation value
     */
    public static final int CONSTELLATION_GNSS = 0;
    public static final int CONSTELLATION_GPS = 1;
    public static final int CONSTELLATION_GLONASS = 2;
    public static final int CONSTELLATION_GALILEO = 3;
    public static final int CONSTELLATION_GPS_GLONASS = 4;
    public static final int CONSTELLATION_GPS_GALILEO = 5;
    public static final int CONSTELLATION_GLONASS_GALILEO = 6;
    public static final int CONSTELLATION_INVALID = -1;
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
    protected long _satPerConst = SATPERCONST_INVALID;
    protected double _gpsRefreshRate = GPSREFRESHRATE_INVALID;
    protected int _coordSystem = COORDSYSTEM_INVALID;
    protected int _constellation = CONSTELLATION_INVALID;
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
    public interface UpdateCallback
    {
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
    public interface TimedReportCallback
    {
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
    protected YGps(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "Gps";
        //--- (YGps attributes initialization)
        //--- (end of YGps attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YGps(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (YGps implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("isFixed")) {
            _isFixed = json_val.getInt("isFixed") > 0 ? 1 : 0;
        }
        if (json_val.has("satCount")) {
            _satCount = json_val.getLong("satCount");
        }
        if (json_val.has("satPerConst")) {
            _satPerConst = json_val.getLong("satPerConst");
        }
        if (json_val.has("gpsRefreshRate")) {
            _gpsRefreshRate = Math.round(json_val.getDouble("gpsRefreshRate") * 1000.0 / 65536.0) / 1000.0;
        }
        if (json_val.has("coordSystem")) {
            _coordSystem = json_val.getInt("coordSystem");
        }
        if (json_val.has("constellation")) {
            _constellation = json_val.getInt("constellation");
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
     * Returns TRUE if the receiver has found enough satellites to work.
     *
     *  @return either YGps.ISFIXED_FALSE or YGps.ISFIXED_TRUE, according to TRUE if the receiver has found
     * enough satellites to work
     *
     * @throws YAPI_Exception on error
     */
    public int get_isFixed() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ISFIXED_INVALID;
                }
            }
            res = _isFixed;
        }
        return res;
    }

    /**
     * Returns TRUE if the receiver has found enough satellites to work.
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
     * Returns the total count of satellites used to compute GPS position.
     *
     * @return an integer corresponding to the total count of satellites used to compute GPS position
     *
     * @throws YAPI_Exception on error
     */
    public long get_satCount() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return SATCOUNT_INVALID;
                }
            }
            res = _satCount;
        }
        return res;
    }

    /**
     * Returns the total count of satellites used to compute GPS position.
     *
     * @return an integer corresponding to the total count of satellites used to compute GPS position
     *
     * @throws YAPI_Exception on error
     */
    public long getSatCount() throws YAPI_Exception
    {
        return get_satCount();
    }

    /**
     * Returns the count of visible satellites per constellation encoded
     * on a 32 bit integer: bits 0..5: GPS satellites count,  bits 6..11 : Glonass, bits 12..17 : Galileo.
     * this value is refreshed every 5 seconds only.
     *
     * @return an integer corresponding to the count of visible satellites per constellation encoded
     *         on a 32 bit integer: bits 0.
     *
     * @throws YAPI_Exception on error
     */
    public long get_satPerConst() throws YAPI_Exception
    {
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return SATPERCONST_INVALID;
                }
            }
            res = _satPerConst;
        }
        return res;
    }

    /**
     * Returns the count of visible satellites per constellation encoded
     * on a 32 bit integer: bits 0..5: GPS satellites count,  bits 6..11 : Glonass, bits 12..17 : Galileo.
     * this value is refreshed every 5 seconds only.
     *
     * @return an integer corresponding to the count of visible satellites per constellation encoded
     *         on a 32 bit integer: bits 0.
     *
     * @throws YAPI_Exception on error
     */
    public long getSatPerConst() throws YAPI_Exception
    {
        return get_satPerConst();
    }

    /**
     * Returns effective GPS data refresh frequency.
     * this value is refreshed every 5 seconds only.
     *
     * @return a floating point number corresponding to effective GPS data refresh frequency
     *
     * @throws YAPI_Exception on error
     */
    public double get_gpsRefreshRate() throws YAPI_Exception
    {
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return GPSREFRESHRATE_INVALID;
                }
            }
            res = _gpsRefreshRate;
        }
        return res;
    }

    /**
     * Returns effective GPS data refresh frequency.
     * this value is refreshed every 5 seconds only.
     *
     * @return a floating point number corresponding to effective GPS data refresh frequency
     *
     * @throws YAPI_Exception on error
     */
    public double getGpsRefreshRate() throws YAPI_Exception
    {
        return get_gpsRefreshRate();
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COORDSYSTEM_INVALID;
                }
            }
            res = _coordSystem;
        }
        return res;
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
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
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
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("coordSystem",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the representation system used for positioning data.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
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
     * Returns the the satellites constellation used to compute
     * positioning data.
     *
     *  @return a value among YGps.CONSTELLATION_GNSS, YGps.CONSTELLATION_GPS, YGps.CONSTELLATION_GLONASS,
     *  YGps.CONSTELLATION_GALILEO, YGps.CONSTELLATION_GPS_GLONASS, YGps.CONSTELLATION_GPS_GALILEO and
     * YGps.CONSTELLATION_GLONASS_GALILEO corresponding to the the satellites constellation used to compute
     *         positioning data
     *
     * @throws YAPI_Exception on error
     */
    public int get_constellation() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return CONSTELLATION_INVALID;
                }
            }
            res = _constellation;
        }
        return res;
    }

    /**
     * Returns the the satellites constellation used to compute
     * positioning data.
     *
     *  @return a value among Y_CONSTELLATION_GNSS, Y_CONSTELLATION_GPS, Y_CONSTELLATION_GLONASS,
     *  Y_CONSTELLATION_GALILEO, Y_CONSTELLATION_GPS_GLONASS, Y_CONSTELLATION_GPS_GALILEO and
     * Y_CONSTELLATION_GLONASS_GALILEO corresponding to the the satellites constellation used to compute
     *         positioning data
     *
     * @throws YAPI_Exception on error
     */
    public int getConstellation() throws YAPI_Exception
    {
        return get_constellation();
    }

    /**
     * Changes the satellites constellation used to compute
     * positioning data. Possible  constellations are GNSS ( = all supported constellations),
     * GPS, Glonass, Galileo , and the 3 possible pairs. This setting has  no effect on Yocto-GPS (V1).
     *
     *  @param newval : a value among YGps.CONSTELLATION_GNSS, YGps.CONSTELLATION_GPS,
     *  YGps.CONSTELLATION_GLONASS, YGps.CONSTELLATION_GALILEO, YGps.CONSTELLATION_GPS_GLONASS,
     *  YGps.CONSTELLATION_GPS_GALILEO and YGps.CONSTELLATION_GLONASS_GALILEO corresponding to the
     * satellites constellation used to compute
     *         positioning data
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_constellation(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("constellation",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the satellites constellation used to compute
     * positioning data. Possible  constellations are GNSS ( = all supported constellations),
     * GPS, Glonass, Galileo , and the 3 possible pairs. This setting has  no effect on Yocto-GPS (V1).
     *
     *  @param newval : a value among Y_CONSTELLATION_GNSS, Y_CONSTELLATION_GPS, Y_CONSTELLATION_GLONASS,
     *  Y_CONSTELLATION_GALILEO, Y_CONSTELLATION_GPS_GLONASS, Y_CONSTELLATION_GPS_GALILEO and
     * Y_CONSTELLATION_GLONASS_GALILEO corresponding to the satellites constellation used to compute
     *         positioning data
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setConstellation(int newval)  throws YAPI_Exception
    {
        return set_constellation(newval);
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
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LATITUDE_INVALID;
                }
            }
            res = _latitude;
        }
        return res;
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
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return LONGITUDE_INVALID;
                }
            }
            res = _longitude;
        }
        return res;
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
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DILUTION_INVALID;
                }
            }
            res = _dilution;
        }
        return res;
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
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return ALTITUDE_INVALID;
                }
            }
            res = _altitude;
        }
        return res;
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
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return GROUNDSPEED_INVALID;
                }
            }
            res = _groundSpeed;
        }
        return res;
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
        double res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DIRECTION_INVALID;
                }
            }
            res = _direction;
        }
        return res;
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
        long res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return UNIXTIME_INVALID;
                }
            }
            res = _unixTime;
        }
        return res;
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
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss".
     *
     * @return a string corresponding to the current time in the form "YYYY/MM/DD hh:mm:ss"
     *
     * @throws YAPI_Exception on error
     */
    public String get_dateTime() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return DATETIME_INVALID;
                }
            }
            res = _dateTime;
        }
        return res;
    }

    /**
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss".
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
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return UTCOFFSET_INVALID;
                }
            }
            res = _utcOffset;
        }
        return res;
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
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
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
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("utcOffset",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the number of seconds between current time and UTC time (time zone).
     * The timezone is automatically rounded to the nearest multiple of 15 minutes.
     * If current UTC time is known, the current time is automatically be updated according to the selected time zone.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
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

    public String get_command() throws YAPI_Exception
    {
        String res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return COMMAND_INVALID;
                }
            }
            res = _command;
        }
        return res;
    }

    public int set_command(String  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = newval;
            _setAttr("command",rest_val);
        }
        return YAPI.SUCCESS;
    }


    /**
     * Retrieves a geolocalization module for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the geolocalization module is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YGps.isOnline() to test if the geolocalization module is
     * indeed online at a given time. In case of ambiguity when looking for
     * a geolocalization module by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the geolocalization module, for instance
     *         YGNSSMK2.gps.
     *
     * @return a YGps object allowing you to drive the geolocalization module.
     */
    public static YGps FindGps(String func)
    {
        YGps obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YGps) YFunction._FindFromCache("Gps", func);
            if (obj == null) {
                obj = new YGps(func);
                YFunction._AddToCache("Gps", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a geolocalization module for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the geolocalization module is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YGps.isOnline() to test if the geolocalization module is
     * indeed online at a given time. In case of ambiguity when looking for
     * a geolocalization module by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the geolocalization module, for instance
     *         YGNSSMK2.gps.
     *
     * @return a YGps object allowing you to drive the geolocalization module.
     */
    public static YGps FindGpsInContext(YAPIContext yctx,String func)
    {
        YGps obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YGps) YFunction._FindFromCacheInContext(yctx, "Gps", func);
            if (obj == null) {
                obj = new YGps(yctx, func);
                YFunction._AddToCache("Gps", func, obj);
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
     * Continues the enumeration of geolocalization modules started using yFirstGps().
     * Caution: You can't make any assumption about the returned geolocalization modules order.
     * If you want to find a specific a geolocalization module, use Gps.findGps()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YGps object, corresponding to
     *         a geolocalization module currently online, or a null pointer
     *         if there are no more geolocalization modules to enumerate.
     */
    public YGps nextGps()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindGpsInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of geolocalization modules currently accessible.
     * Use the method YGps.nextGps() to iterate on
     * next geolocalization modules.
     *
     * @return a pointer to a YGps object, corresponding to
     *         the first geolocalization module currently online, or a null pointer
     *         if there are none.
     */
    public static YGps FirstGps()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("Gps");
        if (next_hwid == null)  return null;
        return FindGpsInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of geolocalization modules currently accessible.
     * Use the method YGps.nextGps() to iterate on
     * next geolocalization modules.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YGps object, corresponding to
     *         the first geolocalization module currently online, or a null pointer
     *         if there are none.
     */
    public static YGps FirstGpsInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("Gps");
        if (next_hwid == null)  return null;
        return FindGpsInContext(yctx, next_hwid);
    }

    //--- (end of YGps implementation)
}

