package com.yoctopuce.YoctoAPI;

abstract class YJSONContent
{
    final String _data;
    int _data_start;
    int _data_len;
    int _data_boundary;
    private final YJSONType _type;
    //protected string debug;


    enum YJSONType
    {
        STRING,
        NUMBER,
        ARRAY,
        OBJECT
    }


    enum Tjstate
    {
        JSTART,
        JWAITFORNAME,
        JWAITFORENDOFNAME,
        JWAITFORCOLON,
        JWAITFORDATA,
        JWAITFORNEXTSTRUCTMEMBER,
        JWAITFORNEXTARRAYITEM,
        JWAITFORSTRINGVALUE,
        JWAITFORSTRINGVALUE_ESC,
        JWAITFORINTVALUE,
        JWAITFORBOOLVALUE
    }

    static YJSONContent ParseJson(String data, int start, int stop) throws Exception
    {
        int cur_pos = SkipGarbage(data, start, stop);
        YJSONContent res;
        char c = data.charAt(cur_pos);
        if (c == '[') {
            res = new YJSONArray(data, start, stop);
        } else if (c == '{') {
            res = new YJSONObject(data, start, stop);
        } else if (c == '"') {
            res = new YJSONString(data, start, stop);
        } else {
            res = new YJSONNumber(data, start, stop);
        }
        res.parse();
        return res;
    }


    YJSONContent(String data, int start, int stop, YJSONType type)
    {
        _data = data;
        _data_start = start;
        _data_boundary = stop;
        _type = type;
    }

    YJSONContent(YJSONType type)
    {
        _data = null;
        _type = type;
    }


    YJSONType getJSONType()
    {
        return _type;
    }

    abstract int parse() throws Exception;

    static int SkipGarbage(String data, int start, int stop)
    {
        if (stop <= start) {
            return start;
        }
        while (start < stop) {
            char sti = data.charAt(start);
            if (sti != ' ' && sti != '\n' && sti != '\r') {
                break;
            }
            start++;
        }
        return start;
    }

    String formatError(String errmsg, int cur_pos)
    {
        int ststart = cur_pos - 10;
        int stend = cur_pos + 10;
        if (ststart < 0)
            ststart = 0;
        if (stend > _data_boundary)
            stend = _data_boundary;
        if (_data != null) {
            return errmsg + " near " + _data.substring(ststart, stend);
        }
        return errmsg;
    }

    abstract String toJSON();
}
