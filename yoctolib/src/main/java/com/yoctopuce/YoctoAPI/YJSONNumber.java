package com.yoctopuce.YoctoAPI;

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
        if (_data == null) {
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
            cur_pos++;
        }
        throw new Exception(formatError("unexpected end of data", cur_pos));
    }

    @Override
    String toJSON()
    {
        if (_isFloat)
            return Double.toString(_doubleValue);
        else
            return Long.toString(_intValue);
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
