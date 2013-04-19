package com.yoctopuce.YoctoAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * YFunction Class (virtual class, used internally)
 *
 * This is the parent class for all public objects representing device functions
 * documented in the high-level programming API. This abstract class does all
 * the real job, but without knowledge of the specific function attributes.
 *
 * Instantiating a child class of YFunction does not cause any communication.
 * The instance simply keeps track of its function identifier, and will
 * dynamically bind to a matching device at the time it is really beeing used to
 * read or set an attribute. In order to allow true hot-plug replacement of one
 * device by another, the binding stay dynamic through the life of the object.
 *
 * The YFunction class implements a generic high-level cache for the attribute
 * values of the specified function, pre-parsed from the REST API string.
 */
public class YFunction {

    public static final String FUNCTIONDESCRIPTOR_INVALID = "!INVALID!";
    protected String _className;
    protected String _func;
    protected int _lastErrorType;
    protected String _lastErrorMsg;
    protected long _expiration;
    protected HashMap<String, String> _cache;
    protected Object _userData;
    protected UpdateCallback _valueCallback;

    public interface UpdateCallback {

        void yNewValue(YFunction function, String functionValue);
    }

    public YFunction(String classname, String func)
    {
        _className = classname;
        _func = func;
        _lastErrorType = YAPI.SUCCESS;
        _lastErrorMsg = "";
        _expiration = 0;
        _cache = new HashMap<String, String>();
        _userData = null;
        _valueCallback = null;
        YAPI.setFunction(classname, func, this);
    }

    /**
     * Returns a short text that describes the function in the form TYPE(NAME)=SERIAL&#46;FUNCTIONID.
     * More precisely,
     * TYPE       is the type of the function,
     * NAME       it the name used for the first access to the function,
     * SERIAL     is the serial number of the module if the module is connected or "unresolved", and
     * FUNCTIONID is  the hardware identifier of the function if the module is connected.
     * For example, this method returns Relay(MyCustomName.relay1)=RELAYLO1-123456.relay1 if the
     * module is already connected or Relay(BadCustomeName.relay1)=unresolved if the module has
     * not yet been connected. This method does not trigger any USB or TCP transaction and can therefore be used in
     * a debugger.
     * 
     * @return a string that describes the function
     *         (ex: Relay(MyCustomName.relay1)=RELAYLO1-123456.relay1)
     */
    public String describe()
    {


        try {
            String hwid = YAPI.resolveFunction(_className, _func);
            return _className + "(" + _func + ")="+hwid;
        } catch (YAPI_Exception ex) {
        }
        return _className + "(" + _func + ")=unresolved";

    }


    /**
     * Returns the unique hardware identifier of the function in the form SERIAL&#46;FUNCTIONID.
     * The unique hardware identifier is composed of the device serial
     * number and of the hardware identifier of the function. (for example RELAYLO1-123456.relay1)
     * 
     * @return a string that uniquely identifies the function (ex: RELAYLO1-123456.relay1)
     * 
     * @throws YAPI_Exception
     */
    public String get_hardwareId() throws YAPI_Exception
    {
        return YAPI.resolveFunction(_className, _func);
    }

    public String getHardwareId() throws YAPI_Exception
    {
        return YAPI.resolveFunction(_className, _func);    	
    }


    /**
     * Returns the hardware identifier of the function, without reference to the module. For example
     * relay1
     * 
     * @return a string that identifies the function (ex: relay1)
     * 
     * @throws YAPI_Exception
     */
    public String get_functionId() throws YAPI_Exception
    {
        String hwid = get_hardwareId();
        int pos = hwid.indexOf('.');
        return hwid.substring(pos+1);
    }

    public String getFunctionId() throws YAPI_Exception
    {
        return get_functionId();
    }


    /**
     * Returns a global identifier of the function in the format MODULE_NAME&#46;FUNCTION_NAME.
     * The returned string uses the logical names of the module and of the function if they are defined,
     * otherwise the serial number of the module and the hardware identifier of the function
     * (for exemple: MyCustomName.relay1)
     * 
     * @return a string that uniquely identifies the function using logical names
     *         (ex: MyCustomName.relay1)
     * 
     * @throws YAPI_Exception
     */
    public String get_friendlyName() throws YAPI_Exception
    {
        YFunctionType ftype = YAPI.getFnByType(_className);
        return ftype.friendlyName(_func).toString();
    }

    public String getFriendlyName() throws YAPI_Exception
    {
        return get_friendlyName();
    }


    @Override
    public String toString()
    {
        return describe();
    }

    // Return the value of an attribute from function cache, after reloading it from device if needed
    // Note: the function cache is a typed (parsed) cache, contrarily to the agnostic device cache
    protected Object _getAttr(String attr) throws YAPI_Exception
    {
        if (_expiration <= YAPI.GetTickCount()) {
            // no valid cached value, reload from device
            load(YAPI.DefaultCacheValidity);
        }
        if (!_cache.containsKey(attr)) {
            throw new YAPI_Exception(YAPI.VERSION_MISMATCH, "No such attribute " + attr + " in function");
        }
        return _cache.get(attr);
    }

    // Return the value of an attribute from function cache, after loading it from device if needed
    protected Object _getFixedAttr(String attr) throws YAPI_Exception
    {
        if (_expiration == 0) {
            // no cached value, load from device
            load(YAPI.DefaultCacheValidity);
        }
        if (!_cache.containsKey(attr)) {
            throw new YAPI_Exception(YAPI.VERSION_MISMATCH, "No such attribute " + attr + " in function");
        }
        return _cache.get(attr);
    }

    // Change the value of an attribute on a device, and update cache on the fly
    // Note: the function cache is a typed (parsed) cache, contrarily to the agnostic device cache
    protected void _setAttr(String attr, String newval) throws YAPI_Exception
    {
        if (newval == null) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Undefined value to set for attribute " + attr);
        }
        String attrname;
        String extra;
        try {
            attrname = URLEncoder.encode(attr, "ISO-8859-1");
            extra = "/" + attrname + "?" + attrname + "=" + URLEncoder.encode(newval, "ISO-8859-1");
            extra = extra.replaceAll("%21", "!")
                    .replaceAll("%23", "#").replaceAll("%24", "$")
                    .replaceAll("%27", "'").replaceAll("%28", "(").replaceAll("%29", ")")
                    .replaceAll("%2C", ",").replaceAll("%2F", "/")
                    .replaceAll("%3A", ":").replaceAll("%3B", ";").replaceAll("%3F", "?")
                    .replaceAll("%40", "@").replaceAll("%5B", "[").replaceAll("%5D", "]");
        } catch (UnsupportedEncodingException ex) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Unsupported Encoding");
        }
        YAPI.funcRequest(_className, _func, extra);
        if (_expiration!=0) {
            _expiration = YAPI.GetTickCount();
        }
    }



    private byte[] _request(String req_first_line,byte[] req_head_and_body) throws YAPI_Exception
    {
        YDevice dev = YAPI.funcGetDevice(_className, _func);
        return dev.requestHTTP(req_first_line,req_head_and_body, false);
    }

    protected int _upload(String path, byte[] content) throws YAPI_Exception
    {
        Random randomGenerator = new Random();
        String boundary;
        String request = "POST /upload.html HTTP/1.1\r\n";
        String mp_header = "Content-Disposition: form-data; name=\""+path+"\"; filename=\"api\"\r\n"+
                    "Content-Type: application/octet-stream\r\n"+
                    "Cobntent-Transfer-Encoding: binary\r\n\r\n";
        // find a valid boundary
        do {
            boundary = String.format("Zz%06xzZ", randomGenerator.nextInt(0x1000000));
        } while(mp_header.indexOf(boundary)>=0 && YAPI._find_in_bytes(content,boundary.getBytes())>=0);
        //construct header parts
        String header_start = "Content-Type: multipart/form-data; boundary="+boundary+"\r\n\r\n--"+boundary+"\r\n"+mp_header;
        String header_stop="\r\n--"+boundary+"--\r\n";
        byte[] head_body = new byte[header_start.length()+ content.length+ header_stop.length()];
        int pos=0;int len=header_start.length();
        System.arraycopy(header_start.getBytes(), 0, head_body, pos, len);

        pos+=len;len=content.length;
        System.arraycopy(content, 0, head_body, pos, len);

        pos+=len;len=header_stop.length();
        System.arraycopy(header_stop.getBytes(), 0, head_body, pos, len);
        _request(request,head_body);
        return YAPI.SUCCESS;
    }

    protected int _upload(String pathname, String content) throws YAPI_Exception
    {
        try {
            return this._upload(pathname, content.getBytes(YAPI.DefaultEncoding));
        } catch (UnsupportedEncodingException e) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, e.getLocalizedMessage());
        }
    }

    protected byte[] _download(String url) throws YAPI_Exception
    {
        String   request = "GET /"+url+" HTTP/1.1\r\n\r\n";
        return  _request(request,null);
    }


    protected String _json_get_key(byte[] json, String key) throws YAPI_Exception
    {
        JSONObject obj = null;
        try {
            obj =new JSONObject(new String(json,"ISO-8859-1"));
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR,ex.getLocalizedMessage());
        } catch (UnsupportedEncodingException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR,ex.getLocalizedMessage());
        }
        if(obj.has(key))
            try {
            return obj.getString(key);
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR,ex.getLocalizedMessage());
        }
        throw new YAPI_Exception(YAPI.INVALID_ARGUMENT,"No key "+key+"in JSON struct");
    }

    protected ArrayList<String> _json_get_array(byte[] json) throws YAPI_Exception
    {
        JSONArray array = null;
        try {
            array =new JSONArray(new String(json,"ISO-8859-1"));
        } catch (JSONException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR,ex.getLocalizedMessage());
        } catch (UnsupportedEncodingException ex) {
            throw new YAPI_Exception(YAPI.IO_ERROR,ex.getLocalizedMessage());
        }
        ArrayList<String> list = new ArrayList<String>();
        int len = array.length();
        for (int i=0;i<len;i++){
            try {
                list.add(array.get(i).toString());
            } catch (JSONException ex) {
                throw new YAPI_Exception(YAPI.IO_ERROR,ex.getLocalizedMessage());
            }
        }
        return list;
    }

    
    
    protected boolean hasCallbackRegistered()
    {
        return _valueCallback != null;
    }

    void advertiseValue(String newvalue)
    {
        if (_valueCallback != null) {
            _valueCallback.yNewValue(this, newvalue);
        }
    }

    /**
     * Checks if the function is currently reachable, without raising any error.
     * If there is a cached value for the function in cache, that has not yet
     * expired, the device is considered reachable.
     * No exception is raised if there is an error while trying to contact the
     * device hosting the requested function.
     * 
     * @return true if the function can be reached, and false otherwise
     */
    public boolean isOnline()
    {
        // A valid value in cache means that the device is online
        if (_expiration > YAPI.GetTickCount()) {
            return true;
        }
        try {
            // Check that the function is available without throwing exceptions
            load(YAPI.DefaultCacheValidity);
        } catch (YAPI_Exception ex) {
            return false;
        }

        return true;
    }

    /**
     * Returns the numerical error code of the latest error with this function.
     * This method is mostly useful when using the Yoctopuce library with
     * exceptions disabled.
     * 
     * @return a number corresponding to the code of the latest error that occured while
     *         using this function object
     */
    public int get_errorType()
    {
        return _lastErrorType;
    }

    public int getErrorType()
    {
        return _lastErrorType;
    }

    public int errorType()
    {
        return _lastErrorType;
    }

    public int errType()
    {
        return _lastErrorType;
    }

    /**
     * Returns the error message of the latest error with this function.
     * This method is mostly useful when using the Yoctopuce library with
     * exceptions disabled.
     * 
     * @return a string corresponding to the latest error message that occured while
     *         using this function object
     */
    public String get_errorMessage()
    {
        return _lastErrorMsg;
    }

    public String getErrorMessage()
    {
        return _lastErrorMsg;
    }

    public String errorMessage()
    {
        return _lastErrorMsg;
    }

    public String errMessage()
    {
        return _lastErrorMsg;
    }

    /**
     * Preloads the function cache with a specified validity duration.
     * By default, whenever accessing a device, all function attributes
     * are kept in cache for the standard duration (5 ms). This method can be
     * used to temporarily mark the cache as valid for a longer period, in order
     * to reduce network trafic for instance.
     * 
     * @param msValidity : an integer corresponding to the validity attributed to the
     *         loaded function parameters, in milliseconds
     * 
     * @return YAPI.SUCCESS when the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int load(long msValidity) throws YAPI_Exception
    {
        _cache = YAPI.funcRequest(_className, _func, "");
        _expiration = YAPI.GetTickCount() + msValidity;
        return YAPI.SUCCESS;
    }

    /**
     * Gets the YModule object for the device on which the function is located.
     * If the function cannot be located on any module, the returned instance of
     * YModule is not shown as on-line.
     * 
     * @return an instance of YModule
     */
    public YModule get_module()
    {

        // try to resolve the function name to a device id without query
        String hwid = _func;
        if (hwid.indexOf('.') == -1) {
            try {
                hwid = YAPI.resolveFunction(_className, _func);
            } catch (YAPI_Exception ex) {
            }
        }

        int dotidx = hwid.indexOf('.');
        if (dotidx >= 0) {
            // resolution worked
            return YModule.FindModule(hwid.substring(0, dotidx));
        }
        try {
            // device not resolved for now, force a communication for a last chance resolution
            if (load(YAPI.DefaultCacheValidity) == YAPI.SUCCESS) {
                hwid = YAPI.resolveFunction(_className, _func);
            }
        } catch (YAPI_Exception ex) {
        }

        dotidx = hwid.indexOf('.');
        if (dotidx >= 0) {
            // resolution worked
            return YModule.FindModule(hwid.substring(0, dotidx));
        }
        // return a true yFindModule object even if it is not a module valid for communicating
        return YModule.FindModule("module_of_" + _className + "_" + _func);
    }

    public YModule getModule()
    {
        return get_module();
    }

    public YModule module()
    {
        return get_module();
    }

    /**
     * Returns a unique identifier of type YFUN_DESCR corresponding to the function.
     * This identifier can be used to test if two instances of YFunction reference the same
     * physical function on the same physical device.
     * 
     * @return an identifier of type YFUN_DESCR.
     * 
     * If the function has never been contacted, the returned value is YFunction.FUNCTIONDESCRIPTOR_INVALID.
     */
    public String get_functionDescriptor()
    {
        // try to resolve the function name to a device id without query
        String hwid = _func;
        int dotidx = hwid.indexOf('.');
        if (dotidx < 0) {
            try {
                hwid = YAPI.resolveFunction(_className, _func);
            } catch (YAPI_Exception ex) {
            }
        }
        dotidx = hwid.indexOf('.');
        if (dotidx >= 0) {
            // resolution worked
            return hwid;
        }
        return FUNCTIONDESCRIPTOR_INVALID;
    }

    public String getFunctionDescriptor()
    {
        return get_functionDescriptor();
    }

    public String functionDescriptor()
    {
        return get_functionDescriptor();
    }

    /**
     * Returns the value of the userData attribute, as previously stored using method
     * set_userData.
     * This attribute is never touched directly by the API, and is at disposal of the caller to
     * store a context.
     * 
     * @return the object stored previously by the caller.
     */
    public Object get_userData()
    {
        return _userData;
    }

    public Object getUserData()
    {
        return get_userData();
    }

    public Object userData()
    {
        return get_userData();
    }

    /**
     * Stores a user context provided as argument in the userData attribute of the function.
     * This attribute is never touched by the API, and is at disposal of the caller to store a context.
     * 
     * @param data : any kind of object to be stored
     * @noreturn
     */
    public void set_userData(Object data)
    {
        _userData = data;
    }

    public void setUserData(Object data)
    {
        set_userData(data);
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
     * @noreturn
     */
    public void registerValueCallback(UpdateCallback callback)
    {
        _valueCallback = callback;
        if (callback != null && isOnline()) {
            String newval;
            try {
                newval = get_advertisedValue();
                if (!newval.equals("") && !newval.equals("!INVALDI!")) {
                    callback.yNewValue(this, newval);
                }
            } catch (YAPI_Exception ex) {
            }
        }
    }

    String get_advertisedValue() throws YAPI_Exception
    {
        throw new UnsupportedOperationException("Should be overridden by subclass");
    }
}
