package com.yoctopuce.YoctoAPI;

class YJSONString extends YJSONContent
{
    private String _stringValue;

    YJSONString(String data, int start, int stop)
    {
        super(data, start, stop, YJSONType.STRING);
    }

    YJSONString(String data)

    {
        super(data, 0, data.length(), YJSONType.STRING);
    }

    YJSONString()
    {
        super(YJSONType.STRING);
    }


    @Override
    int parse() throws Exception
    {
        int str_start;
        StringBuilder value = new StringBuilder();
        int cur_pos = SkipGarbage(_data, _data_start, _data_boundary);

        if (_data.charAt(cur_pos) != '"') {
            throw new Exception(formatError("double quote was expected", cur_pos));
        }
        cur_pos++;
        str_start = cur_pos;
        Tjstate state = Tjstate.JWAITFORSTRINGVALUE;

        while (cur_pos < _data_boundary) {
            char sti = _data.charAt(cur_pos);
            switch (state) {
                case JWAITFORSTRINGVALUE:
                    if (sti == '\\') {
                        value.append(_data.substring(str_start, cur_pos));
                        str_start = cur_pos;
                        state = Tjstate.JWAITFORSTRINGVALUE_ESC;
                    } else if (sti == '"') {
                        value.append(_data.substring(str_start, cur_pos));
                        _stringValue = value.toString();
                        _data_len = (cur_pos + 1) - _data_start;
                        return _data_len;
                    } else if (sti < 32) {
                        throw new Exception(formatError("invalid char: was expecting string value", cur_pos));
                    }
                    break;
                case JWAITFORSTRINGVALUE_ESC:
                    value.append(sti);
                    state = Tjstate.JWAITFORSTRINGVALUE;
                    str_start = cur_pos + 1;
                    break;
                default:
                    throw new Exception(formatError("invalid state for YJSONObject", cur_pos));
            }
            cur_pos++;
        }
        throw new Exception(formatError("unexpected end of data", cur_pos));
    }

    @Override
    String toJSON()
    {
        StringBuilder res = new StringBuilder(_stringValue.length() * 2);
        res.append('"');
        int len = _stringValue.length();
        for (int i = 0; i < len; i++) {
            char c = _stringValue.charAt(i);
            switch (c) {
                case '"':
                    res.append("\\\"");
                    break;
                case '\\':
                    res.append("\\\\");
                    break;
                case '/':
                    res.append("\\/");
                    break;
                case '\b':
                    res.append("\\b");
                    break;
                case '\f':
                    res.append("\\f");
                    break;
                case '\n':
                    res.append("\\n");
                    break;
                case '\r':
                    res.append("\\r");
                    break;
                case '\t':
                    res.append("\\t");
                    break;
                default:
                    res.append(c);
                    break;
            }
        }
        res.append('"');
        return res.toString();
    }

    String getString()
    {
        return _stringValue;
    }

    @Override
    public String toString()
    {
        return _stringValue;
    }

    void setContent(String value)
    {
        _stringValue = value;
    }
}
