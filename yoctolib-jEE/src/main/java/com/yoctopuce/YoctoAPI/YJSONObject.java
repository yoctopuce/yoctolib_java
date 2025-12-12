package com.yoctopuce.YoctoAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

class YJSONObject extends YJSONContent
{
    private HashMap<String, YJSONContent> _parsed = new HashMap<>();
    private ArrayList<String> _keys = new ArrayList<>(16);


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

        if (_data.length() <= cur_pos || cur_pos >= _data_boundary || _data.charAt(cur_pos) != '{') {
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
                        _keys.add(current_name);
                        state = Tjstate.JWAITFORNEXTSTRUCTMEMBER;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '[') {
                        YJSONArray jobj = new YJSONArray(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _parsed.put(current_name, jobj);
                        _keys.add(current_name);
                        state = Tjstate.JWAITFORNEXTSTRUCTMEMBER;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '"') {
                        YJSONString jobj = new YJSONString(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _parsed.put(current_name, jobj);
                        _keys.add(current_name);
                        state = Tjstate.JWAITFORNEXTSTRUCTMEMBER;
                        //cur_pos is already incremented
                        continue;
                    } else if (sti == '-' || (sti >= '0' && sti <= '9')) {
                        YJSONNumber jobj = new YJSONNumber(_data, cur_pos, _data_boundary);
                        int len = jobj.parse();
                        cur_pos += len;
                        _parsed.put(current_name, jobj);
                        _keys.add(current_name);
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
        if (_parsed.get(key).getJSONType() == YJSONType.STRING) {
            YJSONString ystr = (YJSONString) _parsed.get(key);
            return ystr.getString();
        } else if (_parsed.get(key).getJSONType() == YJSONType.NUMBER) {
            YJSONNumber yint = (YJSONNumber) _parsed.get(key);
            return String.valueOf(yint.getInt());
        } else {
            return "<JSON_getString_error>";
        }
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
    byte[] toJSON()
    {
        ByteArrayOutputStream res = new ByteArrayOutputStream();
        try {
            res.write('{');
            String sep = "";
            for (String key : _parsed.keySet()) {
                YJSONContent subContent = _parsed.get(key);
                byte[] subres = subContent.toJSON();
                res.write(sep.getBytes(YAPI.DefaultEncoding));
                res.write('"');
                res.write(key.getBytes(YAPI.DefaultEncoding));
                res.write("\":".getBytes(YAPI.DefaultEncoding));
                res.write(subres);
                sep = ",";
            }
            res.write('}');
        } catch (IOException ignored) {
        }
        return res.toByteArray();
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

    String getKeyFromIdx(int i)
    {
        return _keys.get(i);
    }


    @Override
    YJSONContent updateFroJZon(YJSONContent newItem) throws Exception
    {
        YJSONType newItemJSONType = newItem.getJSONType();
        if (newItemJSONType != YJSONType.ARRAY && newItemJSONType != YJSONType.OBJECT) {
            throw new Exception(String.format("Unable to convert %s to %s",
                    newItem.getJSONType().toString(), getJSONType().toString()));
        }
        YJSONObject result = new YJSONObject(newItem._data, newItem._data_start, newItem._data_boundary);
        if (newItemJSONType == YJSONType.ARRAY) {
            YJSONArray jzonArr = (YJSONArray) newItem;
            for (int i = 0; i < jzonArr.length(); i++) {
                String key = this.getKeyFromIdx(i);
                YJSONContent jzonContent = jzonArr.get(i);
                YJSONContent updatedContent = _parsed.get(key).updateFroJZon(jzonContent);
                result._keys.add(key);
                result._parsed.put(key, updatedContent);
            }
        } else {
            YJSONObject newObj = (YJSONObject) newItem;
            if (_keys.size() == 0) {
                throw new Exception(String.format("Unable to convert %s to %s (empty object)",
                        newItem.getJSONType().toString(), getJSONType().toString()));
            }
            YJSONContent first_reference = _parsed.get(_keys.get(0));
            for (String key : newObj._keys) {
                YJSONContent jzonContent = newObj.get(key);
                YJSONContent updatedContent;
                if (_keys.contains(key)) {
                    YJSONContent jsonContent = _parsed.get(key);
                    if (jsonContent.getJSONType() == YJSONType.ARRAY && ((YJSONArray) jsonContent).length() == 0) {
                        // special case for yellow page. Use first function type as ref
                        updatedContent = first_reference.updateFroJZon(jzonContent);
                    } else {
                        updatedContent = jsonContent.updateFroJZon(jzonContent);
                    }
                } else {
                    updatedContent = first_reference.updateFroJZon(jzonContent);
                }
                result._keys.add(key);
                result._parsed.put(key, updatedContent);
            }
        }
        return result;
    }
}
