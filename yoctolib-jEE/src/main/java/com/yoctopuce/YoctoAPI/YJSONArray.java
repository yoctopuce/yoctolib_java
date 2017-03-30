package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;

class YJSONArray extends YJSONContent
{

    private ArrayList<YJSONContent> _arrayValue = new ArrayList<>();

    YJSONArray(String data, int start, int stop)
    {
        super(data, start, stop, YJSONType.ARRAY);
    }

    YJSONArray(String data)
    {
        super(data, 0, data.length(), YJSONType.ARRAY);
    }

    YJSONArray()
    {
        super(YJSONType.ARRAY);
    }

    int length()
    {
        return _arrayValue.size();
    }

    @Override
    int parse() throws Exception
    {
        int cur_pos = SkipGarbage(_data, _data_start, _data_boundary);

        if (_data.charAt(cur_pos) != '[') {
            throw new Exception(formatError("Opening braces was expected", cur_pos));
        }
        cur_pos++;
        Tjstate state = Tjstate.JWAITFORDATA;

        while (cur_pos < _data_boundary) {
            char sti = _data.charAt(cur_pos);
            switch (state) {
                case JWAITFORDATA:
                    if (sti == '{') {
                        YJSONObject jobj = new YJSONObject(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _arrayValue.add(jobj);
                        state = Tjstate.JWAITFORNEXTARRAYITEM;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '[') {
                        YJSONArray jobj = new YJSONArray(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _arrayValue.add(jobj);
                        state = Tjstate.JWAITFORNEXTARRAYITEM;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '"') {
                        YJSONString jobj = new YJSONString(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _arrayValue.add(jobj);
                        state = Tjstate.JWAITFORNEXTARRAYITEM;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '-' || (sti >= '0' && sti <= '9')) {
                        YJSONNumber jobj = new YJSONNumber(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _arrayValue.add(jobj);
                        state = Tjstate.JWAITFORNEXTARRAYITEM;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == ']') {
                        _data_len = cur_pos + 1 - _data_start;
                        return _data_len;
                    } else if (sti != ' ' && sti != '\n' && sti != '\r') {
                        throw new Exception(formatError("invalid char: was expecting  \",0..9,t or f", cur_pos));
                    }
                    break;
                case JWAITFORNEXTARRAYITEM:
                    if (sti == ',') {
                        state = Tjstate.JWAITFORDATA;
                    } else if (sti == ']') {
                        _data_len = cur_pos + 1 - _data_start;
                        return _data_len;
                    } else {
                        if (sti != ' ' && sti != '\n' && sti != '\r') {
                            throw new Exception(formatError("invalid char: was expecting ,", cur_pos));
                        }
                    }
                    break;
                default:
                    throw new Exception(formatError("invalid state for YJSONObject", cur_pos));
            }
            cur_pos++;
        }
        throw new Exception(formatError("unexpected end of data", cur_pos));
    }


    YJSONObject getYJSONObject(int i)
    {
        return (YJSONObject) _arrayValue.get(i);
    }

    String getString(int i)
    {
        YJSONString ystr = (YJSONString) _arrayValue.get(i);
        return ystr.getString();
    }

    YJSONContent get(int i)
    {
        return _arrayValue.get(i);
    }

    YJSONArray getYJSONArray(int i)
    {
        return (YJSONArray) _arrayValue.get(i);
    }

    int getInt(int i)
    {
        YJSONNumber ystr = (YJSONNumber) _arrayValue.get(i);
        return ystr.getInt();
    }

    long getLong(int i)
    {
        YJSONNumber ystr = (YJSONNumber) _arrayValue.get(i);
        return ystr.getLong();
    }

    double getDouble(int i)
    {
        YJSONNumber ystr = (YJSONNumber) _arrayValue.get(i);
        return ystr.getDouble();
    }

    void put(String flatAttr)
    {
        YJSONString strobj = new YJSONString();
        strobj.setContent(flatAttr);
        _arrayValue.add(strobj);
    }

    @Override
    String toJSON()
    {

        StringBuilder res = new StringBuilder();
        res.append('[');
        String sep = "";
        for (YJSONContent yjsonContent : _arrayValue) {
            String subres = yjsonContent.toJSON();
            res.append(sep);
            res.append(subres);
            sep = ",";
        }
        res.append(']');
        return res.toString();
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append('[');
        String sep = "";
        for (YJSONContent yjsonContent : _arrayValue) {
            String subres = yjsonContent.toString();
            res.append(sep);
            res.append(subres);
            sep = ",";
        }
        res.append(']');
        return res.toString();
    }

}
