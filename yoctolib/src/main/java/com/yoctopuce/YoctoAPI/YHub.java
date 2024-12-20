/*********************************************************************
 *
 * $Id: YHub.java 62907 2024-10-08 07:05:55Z mvuilleu $
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
    //--- (generated code: YHub definitions)
    protected YAPIContext _ctx;
    protected int _hubref = 0;
    protected Object _userData = null;

    //--- (end of generated code: YHub definitions)


    protected YHub(YAPIContext yctx, int hubref)
    {
        //--- (generated code: YHub attributes initialization)
        //--- (end of generated code: YHub attributes initialization)
        _ctx = yctx;
        _hubref = hubref;
    }


    private String _getStrAttr_internal(String attrName)
    {
        YGenericHub hub = _ctx.getGenHub(_hubref);
        if (hub == null) {
            return "";
        }
        switch (attrName) {
            case "registeredUrl":
                return hub._URL_params.getOriginalURL();
            case "connectionUrl":
                return hub.getConnectionUrl();
            case "serialNumber":
                return hub.getSerialNumber();
            case "errorMessage":
                return hub.getLastErrorMessage();
            default:
                return "";
        }
    }

    private int _getIntAttr_internal(String attrName)
    {
        YGenericHub hub = _ctx.getGenHub(_hubref);
        if (attrName.equals("isInUse")) {
            return hub != null ? 1 : 0;
        }
        if (hub == null) {
            return -1;
        }
        switch (attrName) {
            case "isOnline":
                return hub.isOnline() ? 1 : 0;
            case "isReadOnly":
                return hub.isReadOnly() ? 1 : 0;
            case "networkTimeout":
                return hub.get_networkTimeout();
            case "errorType":
                return hub.getLastErrorType();
            default:
                return -1;
        }
    }
    public ArrayList<String> get_knownUrls_internal() throws YAPI_Exception
    {
        ArrayList<String> res = new ArrayList<>();
        YGenericHub hub = _ctx.getGenHub(_hubref);
        if (hub != null) {
            res = hub._knownUrls;
        }
        return res;
    }

    private void _setIntAttr_internal(String attrName, int value)
    {
        YGenericHub hub = _ctx.getGenHub(_hubref);
        if (hub != null && attrName.equals("networkTimeout")) {
            hub.set_networkTimeout(value);
        }
    }
    //--- (generated code: YHub implementation)

    public String _getStrAttr(String attrName)
    {
        return _getStrAttr_internal(attrName);
    }

    //cannot be generated for Java:
    //public String _getStrAttr_internal(String attrName)
    public int _getIntAttr(String attrName)
    {
        return _getIntAttr_internal(attrName);
    }

    //cannot be generated for Java:
    //public int _getIntAttr_internal(String attrName)
    public void _setIntAttr(String attrName,int value)
    {
        _setIntAttr_internal(attrName, value);
    }

    //cannot be generated for Java:
    //public void _setIntAttr_internal(String attrName,int value)
    /**
     * Returns the URL that has been used first to register this hub.
     */
    public String get_registeredUrl()
    {
        return _getStrAttr("registeredUrl");
    }

    /**
     * Returns all known URLs that have been used to register this hub.
     * URLs are pointing to the same hub when the devices connected
     * are sharing the same serial number.
     */
    public ArrayList<String> get_knownUrls() throws YAPI_Exception
    {
        return get_knownUrls_internal();
    }

    //cannot be generated for Java:
    //public ArrayList<String> get_knownUrls_internal() throws YAPI_Exception
    /**
     * Returns the URL currently in use to communicate with this hub.
     */
    public String get_connectionUrl()
    {
        return _getStrAttr("connectionUrl");
    }

    /**
     * Returns the hub serial number, if the hub was already connected once.
     */
    public String get_serialNumber()
    {
        return _getStrAttr("serialNumber");
    }

    /**
     * Tells if this hub is still registered within the API.
     *
     * @return true if the hub has not been unregistered.
     */
    public boolean isInUse()
    {
        return _getIntAttr("isInUse") > 0;
    }

    /**
     * Tells if there is an active communication channel with this hub.
     *
     * @return true if the hub is currently connected.
     */
    public boolean isOnline()
    {
        return _getIntAttr("isOnline") > 0;
    }

    /**
     * Tells if write access on this hub is blocked. Return true if it
     * is not possible to change attributes on this hub
     *
     * @return true if it is not possible to change attributes on this hub.
     */
    public boolean isReadOnly()
    {
        return _getIntAttr("isReadOnly") > 0;
    }

    /**
     * Modifies tthe network connection delay for this hub.
     * The default value is inherited from ySetNetworkTimeout
     * at the time when the hub is registered, but it can be updated
     * afterward for each specific hub if necessary.
     *
     * @param networkMsTimeout : the network connection delay in milliseconds.
     *
     */
    public void set_networkTimeout(int networkMsTimeout)
    {
        _setIntAttr("networkTimeout",networkMsTimeout);
    }

    /**
     * Returns the network connection delay for this hub.
     * The default value is inherited from ySetNetworkTimeout
     * at the time when the hub is registered, but it can be updated
     * afterward for each specific hub if necessary.
     *
     * @return the network connection delay in milliseconds.
     */
    public int get_networkTimeout()
    {
        return _getIntAttr("networkTimeout");
    }

    /**
     * Returns the numerical error code of the latest error with the hub.
     * This method is mostly useful when using the Yoctopuce library with
     * exceptions disabled.
     *
     * @return a number corresponding to the code of the latest error that occurred while
     *         using the hub object
     */
    public int get_errorType()
    {
        return _getIntAttr("errorType");
    }

    /**
     * Returns the error message of the latest error with the hub.
     * This method is mostly useful when using the Yoctopuce library with
     * exceptions disabled.
     *
     * @return a string corresponding to the latest error message that occured while
     *         using the hub object
     */
    public String get_errorMessage()
    {
        return _getStrAttr("errorMessage");
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
        return _userData;
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
        return YAPI.nextHubInUseInternal(-1);
    }

    /**
     * Starts the enumeration of hubs currently in use by the API
     * in a given YAPI context.
     * Use the method YHub.nextHubInUse() to iterate on the
     * next hubs.
     *
     * @param yctx : a YAPI context
     *
     * @return a pointer to a YHub object, corresponding to
     *         the first hub currently in use by the API, or a
     *         null pointer if none has been registered.
     */
    public static YHub FirstHubInUseInContext(YAPIContext yctx)
    {
        return yctx.nextHubInUseInternal(-1);
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
        return _ctx.nextHubInUseInternal(_hubref);
    }

    //--- (end of generated code: YHub implementation)

}

