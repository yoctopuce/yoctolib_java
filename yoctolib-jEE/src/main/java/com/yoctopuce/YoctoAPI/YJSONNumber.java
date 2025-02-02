package com.yoctopuce.YoctoAPI;

import java.io.UnsupportedEncodingException;

class YJSONNumber extends YJSONContent
{
    private long _intValue = 0;
    private double _doubleValue = 0;
    private boolean _isFloat = false;

    YJSONNumber(String data, int start, int stop)
    {
        super(data, start, stop, YJSONType.NUMBER);
    }

    @Override
    int parse() throws Exception
    {
        boolean neg = false;
        int start;
        char sti;
        int cur_pos = SkipGarbage(_data, _data_start, _data_boundary);
        if (cur_pos >= _data_boundary || _data == null) {
            throw new Exception("no data");
        }
        sti = _data.charAt(cur_pos);
        if (sti == '-') {
            neg = true;
            cur_pos++;
        }
        start = cur_pos;
        while (cur_pos < _data_boundary) {
            sti = _data.charAt(cur_pos);
            if (sti == '.' && !_isFloat) {
                String int_part = _data.substring(start, cur_pos);
                _intValue = Long.valueOf(int_part);
                _isFloat = true;
            } else if (sti < '0' || sti > '9') {
                return parseNumber(start, cur_pos, neg);
            }
            cur_pos++;
        }
        return parseNumber(start, cur_pos, neg);
    }

    private int parseNumber(int start, int cur_pos, boolean neg)
    {
        String numberpart = _data.substring(start, cur_pos);
        if (_isFloat) {
            _doubleValue = Double.valueOf(numberpart);
        } else {
            _intValue = Long.valueOf(numberpart);
        }
        if (neg) {
            _doubleValue = 0 - _doubleValue;
            _intValue = 0 - _intValue;
        }
        return cur_pos - _data_start;
    }

    @Override
    byte[] toJSON()
    {
        try {
            if (_isFloat) {
                return Double.toString(_doubleValue).getBytes(YAPI.DefaultEncoding);
            } else
                return Long.toString(_intValue).getBytes(YAPI.DefaultEncoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    long getLong()
    {
        if (_isFloat)
            return (long) _doubleValue;
        else
            return _intValue;
    }

    int getInt()
    {
        if (_isFloat)
            return (int) _doubleValue;
        else
            return (int) _intValue;
    }

    double getDouble()
    {
        if (_isFloat)
            return _doubleValue;
        else
            return _intValue;
    }

    @Override
    public String toString()
    {
        if (_isFloat)
            return Double.toString(_doubleValue);
        else
            return Long.toString(_intValue);
    }
}
