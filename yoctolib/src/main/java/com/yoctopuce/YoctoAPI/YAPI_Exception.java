/*********************************************************************
 *
 * $Id: YAPI_Exception.java 19328 2015-02-17 17:30:45Z seb $
 *
 * Class used to report exceptions within Yocto-API
 *
 * - - - - - - - - - License information: - - - - - - - - -
 *
 *  Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 *  Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 *  non-exclusive license to use, modify, copy and integrate this
 *  file into your software for the sole purpose of interfacing 
 *  with Yoctopuce products. 
 *
 *  You may reproduce and distribute copies of this file in 
 *  source or object form, as long as the sole purpose of this
 *  code is to interface with Yoctopuce products. You must retain 
 *  this notice in the distributed source file.
 *
 *  You should refer to Yoctopuce General Terms and Conditions
 *  for additional information regarding your rights and 
 *  obligations.
 *
 *  THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *  WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *  WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS 
 *  FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *  EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, 
 *  COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR 
 *  SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT 
 *  LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *  CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *  BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *  WARRANTY, OR OTHERWISE.
 *
 *********************************************************************/
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
     * @param code    : an error code defined in YAPI
     * @param message : a message that describe the issue
     */
    public YAPI_Exception(int code, String message)
    {
        super(message);
        errorType = code;
    }

    /**
     * @param code    : an error code defined in YAPI
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
