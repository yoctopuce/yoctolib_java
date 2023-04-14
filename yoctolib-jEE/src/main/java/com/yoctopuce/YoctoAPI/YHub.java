/*********************************************************************
 *
 * $Id: YHub.java 53894 2023-04-05 10:33:42Z mvuilleu $
 *
 * Implements yFindDisplay(), the high-level API for Display functions
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


import java.util.ArrayList;

//--- (generated code: YHub class start)
/**
 * YHub Class: Hub Interface
 *
 *
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YHub
{
//--- (end of generated code: YHub class start)
    private final YAPIContext _yctx;
    private final YGenericHub _genHub;
    private boolean _inUse;
    //--- (generated code: YHub definitions)
    protected String _regUrl = "";
    protected ArrayList<String> _knownUrls = new ArrayList<>();
    protected Object _userData = null;

    //--- (end of generated code: YHub definitions)


    protected YHub(YAPIContext yctx, YGenericHub genericHub)
    {
        _yctx = yctx;
        _genHub = genericHub;
        _regUrl = genericHub._URL_params.getOriginalURL();
        _knownUrls.add(_regUrl);
        _inUse = true;
        //--- (generated code: YHub attributes initialization)
        //--- (end of generated code: YHub attributes initialization)
    }


    /**
     * Starts the enumeration of hubs currently in use by the API.
     * Use the method YHub.nextHubInUse() to iterate on the
     * next hubs.
     *
     * @return a pointer to a YHub object, corresponding to
     *         the first hub currently in use by the API, or a
     *         null pointer if none has been registered.
     */
    public static YHub FirstHubInUse()
    {
        YAPIContext yapiContext = YAPI.GetYCtx(true);
        return yapiContext.FirstHubInUse();
    }


    /**
     * Returns the URL currently in use to communicate with this hub.
     */
    public String get_connectionUrl()
    {
        return _genHub._URL_params.getUrl(true, false, true);
    }

    /**
     * Continues the module enumeration started using YHub.FirstHubInUse().
     * Caution: You can't make any assumption about the order of returned hubs.
     *
     * @return a pointer to a YHub object, corresponding to
     *         the next hub currenlty in use, or a null pointer
     *         if there are no more hubs to enumerate.
     */
    public YHub nextHubInUse()
    {
        return _yctx.nextHubInUse(this._genHub);

    }

    public boolean isOnline()
    {
        return  _genHub.isOnline();
    }

    public String get_serialNumber()
    {
        return _genHub.getSerialNumber();
    }

    public boolean isInUse()
    {
        return _inUse;
    }

    void setInUse(boolean inUse)
    {
        _inUse = inUse;
    }


    public boolean isReadOnly()
    {
        return _genHub.isReadOnly();
    }

    public int get_errorType()
    {
        return _genHub.getLastErrorType();
    }

    public String get_errorMessage()
    {
        return _genHub.getLastErrorMessage();
    }


    public int get_networkTimeout()
    {
        return _genHub.get_networkTimeout();
    }

    public void set_networkTimeout(int ms)
    {
        _genHub.set_networkTimeout(ms);
    }

    //--- (generated code: YHub implementation)

    /**
     * Returns the URL that has been used first to register this hub.
     */
    public String get_registeredUrl()
    {
        return _regUrl;
    }

    /**
     * Returns all known URLs that have been used to register this hub.
     * URLs are pointing to the same hub when the devices connected
     * are sharing the same serial number.
     */
    public ArrayList<String> get_knownUrls()
    {
        ArrayList<String> knownUrls = new ArrayList<>();
        knownUrls.clear();
        for (String ii_0:_knownUrls) {
            knownUrls.add(ii_0);
        }
        return knownUrls;
    }

    public void imm_inheritFrom(YHub otherHub)
    {
        for (String ii_0:otherHub._knownUrls) {
            _knownUrls.add(ii_0);
        }
    }

    /**
     * Returns the value of the userData attribute, as previously stored
     * using method set_userData.
     * This attribute is never touched directly by the API, and is at
     * disposal of the caller to store a context.
     *
     * @return the object stored previously by the caller.
     */
    public Object get_userData()
    {
        return this._userData;
    }

    /**
     * Stores a user context provided as argument in the userData
     * attribute of the function.
     * This attribute is never touched by the API, and is at
     * disposal of the caller to store a context.
     *
     * @param data : any kind of object to be stored
     *
     */
    public void set_userData(Object data)
    {
        _userData = data;
    }

    //--- (end of generated code: YHub implementation)

}

