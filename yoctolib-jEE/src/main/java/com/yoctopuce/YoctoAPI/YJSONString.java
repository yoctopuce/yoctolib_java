package com.yoctopuce.YoctoAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

        if (cur_pos >= _data_boundary || _data.charAt(cur_pos) != '"') {
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
    byte[] toJSON()
    {
        byte[] stringValueBytes = _stringValue.getBytes();
        ByteArrayOutputStream res = new ByteArrayOutputStream(stringValueBytes.length * 2);
        try {
            res.write('"');
            int len = stringValueBytes.length;
            for (int i = 0; i < len; i++) {
                char c = (char)stringValueBytes[i];
                switch (c) {
                    case '"':
                        res.write("\\\"".getBytes(YAPI.DefaultEncoding));
                        break;
                    case '\\':
                        res.write("\\\\".getBytes(YAPI.DefaultEncoding));
                        break;
                    case '/':
                        res.write("\\/".getBytes(YAPI.DefaultEncoding));
                        break;
                    case '\b':
                        res.write("\\b".getBytes(YAPI.DefaultEncoding));
                        break;
                    case '\f':
                        res.write("\\f".getBytes(YAPI.DefaultEncoding));
                        break;
                    case '\n':
                        res.write("\\n".getBytes(YAPI.DefaultEncoding));
                        break;
                    case '\r':
                        res.write("\\r".getBytes(YAPI.DefaultEncoding));
                        break;
                    case '\t':
                        res.write("\\t".getBytes(YAPI.DefaultEncoding));
                        break;
                    default:
                        res.write(c);
                        break;
                }
            }
            res.write('"');
        } catch (IOException ignored) {
        }
        return res.toByteArray();
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
