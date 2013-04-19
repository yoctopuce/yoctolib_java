package com.yoctopuce.YoctoAPI;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class YAPI_Exception extends Exception {

    protected String _subStackTrace = "";
    protected Exception _subExeptions = null;
    /**
     *
     */
    private static final long serialVersionUID = -7489291044621894115L;
    /**
     * Class used to report exceptions within Yocto-API Do not instantiate
     * directly
     */
    public int errorType;

    /**
     *
     * @param code : an error code defined in YAPI
     * @param message : a message that describe the issue
     */
    public YAPI_Exception(int code, String message)
    {
        super(message);
        errorType = code;
    }

    /**
     *
     * @param code : an error code defined in YAPI
     * @param message : a message that describe the issue
     */
    public YAPI_Exception(int code, String message, Exception e)
    {
        super(message);
        errorType = code;
        _subExeptions = e;
    }

    public String getStackTraceToString()
    {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        if (_subExeptions != null) {
            _subExeptions.printStackTrace(printWriter);
        } else {
            printStackTrace(printWriter);
        }

        return _subStackTrace + writer.toString();
    }
}
