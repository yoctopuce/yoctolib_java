package com.yoctopuce.YoctoAPI;

import java.util.HashMap;
import java.util.Set;

class YJSONObject extends YJSONContent
{
    private HashMap<String, YJSONContent> _parsed = new HashMap<>();


    YJSONObject(String data)
    {
        super(data, 0, data.length(), YJSONType.OBJECT);
    }

    YJSONObject(String data, int start, int len)
    {
        super(data, start, len, YJSONType.OBJECT);

    }

    @Override
    int parse() throws Exception
    {
        String current_name = "";
        int name_start = _data_start;
        int cur_pos = SkipGarbage(_data, _data_start, _data_boundary);

        if (_data.length() <= cur_pos || _data.charAt(cur_pos) != '{') {
            throw new Exception(formatError("Opening braces was expected", cur_pos));
        }
        cur_pos++;
        Tjstate state = Tjstate.JWAITFORNAME;
        while (cur_pos < _data_boundary) {
            char sti = _data.charAt(cur_pos);
            switch (state) {
                case JWAITFORNAME:
                    if (sti == '"') {
                        state = Tjstate.JWAITFORENDOFNAME;
                        name_start = cur_pos + 1;
                    } else if (sti == '}') {
                        _data_len = cur_pos + 1 - _data_start;
                        return _data_len;
                    } else {
                        if (sti != ' ' && sti != '\n' && sti != '\r') {
                            throw new Exception(formatError("invalid char: was expecting \"", cur_pos));
                        }
                    }
                    break;
                case JWAITFORENDOFNAME:
                    if (sti == '"') {
                        current_name = _data.substring(name_start, cur_pos);
                        state = Tjstate.JWAITFORCOLON;
                    } else {
                        if (sti < 32) {
                            throw new Exception(
                                    formatError("invalid char: was expecting an identifier compliant char", cur_pos));
                        }
                    }
                    break;
                case JWAITFORCOLON:
                    if (sti == ':') {
                        state = Tjstate.JWAITFORDATA;
                    } else {
                        if (sti != ' ' && sti != '\n' && sti != '\r') {
                            throw new Exception(
                                    formatError("invalid char: was expecting \"", cur_pos));
                        }
                    }
                    break;
                case JWAITFORDATA:
                    if (sti == '{') {
                        YJSONObject jobj = new YJSONObject(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _parsed.put(current_name, jobj);
                        state = Tjstate.JWAITFORNEXTSTRUCTMEMBER;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '[') {
                        YJSONArray jobj = new YJSONArray(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _parsed.put(current_name, jobj);
                        state = Tjstate.JWAITFORNEXTSTRUCTMEMBER;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '"') {
                        YJSONString jobj = new YJSONString(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _parsed.put(current_name, jobj);
                        state = Tjstate.JWAITFORNEXTSTRUCTMEMBER;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '-' || (sti >= '0' && sti <= '9')) {
                        YJSONNumber jobj = new YJSONNumber(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _parsed.put(current_name, jobj);
                        state = Tjstate.JWAITFORNEXTSTRUCTMEMBER;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti != ' ' && sti != '\n' && sti != '\r') {
                        throw new Exception(formatError("invalid char: was expecting  \",0..9,t or f", cur_pos));
                    }
                    break;
                case JWAITFORNEXTSTRUCTMEMBER:
                    if (sti == ',') {
                        state = Tjstate.JWAITFORNAME;
                        name_start = cur_pos + 1;
                    } else if (sti == '}') {
                        _data_len = cur_pos + 1 - _data_start;
                        return _data_len;
                    } else {
                        if (sti != ' ' && sti != '\n' && sti != '\r') {
                            throw new Exception(formatError("invalid char: was expecting ,", cur_pos));
                        }
                    }
                    break;

                case JWAITFORNEXTARRAYITEM:
                case JWAITFORSTRINGVALUE:
                case JWAITFORINTVALUE:
                case JWAITFORBOOLVALUE:
                    throw new Exception(formatError("invalid state for YJSONObject", cur_pos));
            }
            cur_pos++;
        }
        throw new Exception(formatError("unexpected end of data", cur_pos));
    }


    boolean has(String key)
    {
        return _parsed.containsKey(key);
    }

    YJSONObject getYJSONObject(String key)
    {
        return (YJSONObject) _parsed.get(key);
    }

    YJSONString getYJSONString(String key)
    {
        return (YJSONString) _parsed.get(key);
    }

    YJSONArray getYJSONArray(String key)
    {
        return (YJSONArray) _parsed.get(key);
    }

    Set<String> getKeys()
    {
        return _parsed.keySet();
    }

    YJSONNumber getYJSONNumber(String key)
    {
        return (YJSONNumber) _parsed.get(key);
    }

    void remove(String key)
    {
        _parsed.remove(key);
    }

    String getString(String key)
    {
        YJSONString ystr = (YJSONString) _parsed.get(key);
        return ystr.getString();
    }

    int getInt(String key)
    {
        YJSONNumber yint = (YJSONNumber) _parsed.get(key);
        return yint.getInt();
    }

    YJSONContent get(String key)
    {
        return _parsed.get(key);
    }

    long getLong(String key)
    {
        YJSONNumber yint = (YJSONNumber) _parsed.get(key);
        return yint.getLong();
    }

    double getDouble(String key)
    {
        YJSONNumber yint = (YJSONNumber) _parsed.get(key);
        return yint.getDouble();
    }

    @Override
    String toJSON()
    {
        StringBuilder res = new StringBuilder();
        res.append('{');
        String sep = "";
        for (String key : _parsed.keySet()) {
            YJSONContent subContent = _parsed.get(key);
            String subres = subContent.toJSON();
            res.append(sep);
            res.append('"');
            res.append(key);
            res.append("\":");
            res.append(subres);
            sep = ",";
        }
        res.append('}');
        return res.toString();
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append('{');
        String sep = "";
        for (String key : _parsed.keySet()) {
            YJSONContent subContent = _parsed.get(key);
            String subres = subContent.toString();
            res.append(sep);
            res.append(key);
            res.append("=>");
            res.append(subres);
            sep = ",";
        }
        res.append('}');
        return res.toString();
    }
}
