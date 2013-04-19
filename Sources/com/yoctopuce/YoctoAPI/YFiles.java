/*********************************************************************
 *
 * $Id: pic24config.php 9668 2013-02-04 12:36:11Z martinm $
 *
 * Implements yFindFiles(), the high-level API for Files functions
 *
 * - - - - - - - - - License information: - - - - - - - - - 
 *
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 * 1) If you have obtained this file from www.yoctopuce.com,
 *    Yoctopuce Sarl licenses to you (hereafter Licensee) the
 *    right to use, modify, copy, and integrate this source file
 *    into your own solution for the sole purpose of interfacing
 *    a Yoctopuce product with Licensee's solution.
 *
 *    The use of this file and all relationship between Yoctopuce 
 *    and Licensee are governed by Yoctopuce General Terms and 
 *    Conditions.
 *
 *    THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *    WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *    WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS 
 *    FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *    EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *    INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, 
 *    COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR 
 *    SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT 
 *    LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *    CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *    BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *    WARRANTY, OR OTHERWISE.
 *
 * 2) If your intent is not to interface with Yoctopuce products,
 *    you are not entitled to use, read or create any derived
 *    material from this source file.
 *
 *********************************************************************/

package com.yoctopuce.YoctoAPI;

  //--- (generated code: globals)
import java.util.ArrayList;
  //--- (end of generated code: globals)
/**
 * YFiles Class: Files function interface
 * 
 * The filesystem interface makes it possible to store files
 * on some devices, for instance to design a custom web UI
 * (for networked devices) or to add fonts (on display
 * devices).
 */
public class YFiles extends YFunction
{
    //--- (generated code: definitions)
    private YFiles.UpdateCallback _valueCallbackFiles;
    /**
     * invalid logicalName value
     */
    public static final String LOGICALNAME_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid advertisedValue value
     */
    public static final String ADVERTISEDVALUE_INVALID = YAPI.INVALID_STRING;
    /**
     * invalid filesCount value
     */
    public static final int FILESCOUNT_INVALID = YAPI.INVALID_UNSIGNED;
    /**
     * invalid freeSpace value
     */
    public static final int FREESPACE_INVALID = YAPI.INVALID_UNSIGNED;
    //--- (end of generated code: definitions)

    /**
     * UdateCallback for Files
     */
    public interface UpdateCallback {
        /**
         * 
         * @param function : the function object of which the value has changed
         * @param functionValue :the character string describing the new advertised value
         */
        void yNewValue(YFiles function, String functionValue);
    }



    //--- (generated code: YFiles implementation)

    /**
     * Returns the logical name of the filesystem.
     * 
     * @return a string corresponding to the logical name of the filesystem
     * 
     * @throws YAPI_Exception
     */
    public String get_logicalName()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("logicalName");
        return json_val;
    }

    /**
     * Returns the logical name of the filesystem.
     * 
     * @return a string corresponding to the logical name of the filesystem
     * 
     * @throws YAPI_Exception
     */
    public String getLogicalName() throws YAPI_Exception

    { return get_logicalName(); }

    /**
     * Changes the logical name of the filesystem. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the filesystem
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int set_logicalName( String  newval)  throws YAPI_Exception
    {
        String rest_val;
        rest_val = newval;
        _setAttr("logicalName",rest_val);
        return YAPI.SUCCESS;
    }

    /**
     * Changes the logical name of the filesystem. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     * 
     * @param newval : a string corresponding to the logical name of the filesystem
     * 
     * @return YAPI_SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int setLogicalName( String newval)  throws YAPI_Exception

    { return set_logicalName(newval); }

    /**
     * Returns the current value of the filesystem (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the filesystem (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String get_advertisedValue()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("advertisedValue");
        return json_val;
    }

    /**
     * Returns the current value of the filesystem (no more than 6 characters).
     * 
     * @return a string corresponding to the current value of the filesystem (no more than 6 characters)
     * 
     * @throws YAPI_Exception
     */
    public String getAdvertisedValue() throws YAPI_Exception

    { return get_advertisedValue(); }

    /**
     * Returns the number of files currently loaded in the filesystem.
     * 
     * @return an integer corresponding to the number of files currently loaded in the filesystem
     * 
     * @throws YAPI_Exception
     */
    public int get_filesCount()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("filesCount");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the number of files currently loaded in the filesystem.
     * 
     * @return an integer corresponding to the number of files currently loaded in the filesystem
     * 
     * @throws YAPI_Exception
     */
    public int getFilesCount() throws YAPI_Exception

    { return get_filesCount(); }

    /**
     * Returns the free space for uploading new files to the filesystem, in bytes.
     * 
     * @return an integer corresponding to the free space for uploading new files to the filesystem, in bytes
     * 
     * @throws YAPI_Exception
     */
    public int get_freeSpace()  throws YAPI_Exception
    {
        String json_val = (String) _getAttr("freeSpace");
        return Integer.parseInt(json_val);
    }

    /**
     * Returns the free space for uploading new files to the filesystem, in bytes.
     * 
     * @return an integer corresponding to the free space for uploading new files to the filesystem, in bytes
     * 
     * @throws YAPI_Exception
     */
    public int getFreeSpace() throws YAPI_Exception

    { return get_freeSpace(); }

    public byte[] sendCommand(String command)  throws YAPI_Exception
    {
        String url;
        url =  String.format("files.json?a=%s",command);
        return _download(url);
        
    }

    /**
     * Reinitializes the filesystem to its clean, unfragmented, empty state.
     * All files previously uploaded are permanently lost.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int format_fs()  throws YAPI_Exception
    {
        byte[] json;
        String res;
        json = sendCommand("format"); 
        res  = _json_get_key(json, "res");
        if (!(res.equals("ok"))) { throw new YAPI_Exception( YAPI.IO_ERROR,  "format failed");};
        return YAPI.SUCCESS;
        
    }

    /**
     * Returns a list of YFileRecord objects that describe files currently loaded
     * in the filesystem.
     * 
     * @param pattern : an optional filter pattern, using star and question marks
     *         as wildcards. When an empty pattern is provided, all file records
     *         are returned.
     * 
     * @return a list of YFileRecord objects, containing the file path
     *         and name, byte size and 32-bit CRC of the file content.
     * 
     * @throws YAPI_Exception
     */
    public ArrayList<YFileRecord> get_list(String pattern)  throws YAPI_Exception
    {
        byte[] json;
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<YFileRecord> res = new ArrayList<YFileRecord>();
        json = sendCommand(String.format("dir&f=%s",pattern));
        list = _json_get_array(json);
        for (String y :list) { res.add(new YFileRecord(y));};
        return res;
        
    }

    /**
     * Downloads the requested file and returns a binary buffer with its content.
     * 
     * @param pathname : path and name of the new file to load
     * 
     * @return a binary buffer with the file content
     * 
     * @throws YAPI_Exception
     */
    public byte[] download(String pathname)  throws YAPI_Exception
    {
        return _download(pathname);
        
    }

    /**
     * Uploads a file to the filesystem, to the specified full path name.
     * If a file already exists with the same path name, its content is overwritten.
     * 
     * @param pathname : path and name of the new file to create
     * @param content : binary buffer with the content to set
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int upload(String pathname,byte[] content)  throws YAPI_Exception
    {
        return _upload(pathname,content);
        
    }

    /**
     * Deletes a file, given by its full path name, from the filesystem.
     * Because of filesystem fragmentation, deleting a file may not always
     * free up the whole space used by the file. However, rewriting a file
     * with the same path name will always reuse any space not freed previously.
     * If you need to ensure that no space is taken by previously deleted files,
     * you can use format_fs to fully reinitialize the filesystem.
     * 
     * @param pathname : path and name of the file to remove.
     * 
     * @return YAPI.SUCCESS if the call succeeds.
     * 
     * @throws YAPI_Exception
     */
    public int remove(String pathname)  throws YAPI_Exception
    {
        byte[] json;
        String res;
        json = sendCommand(String.format("del&f=%s",pathname)); 
        res  = _json_get_key(json, "res");
        if (!(res.equals("ok"))) { throw new YAPI_Exception( YAPI.IO_ERROR,  "unable to remove file");};
        return YAPI.SUCCESS;
        
    }

    /**
     * Continues the enumeration of filesystems started using yFirstFiles().
     * 
     * @return a pointer to a YFiles object, corresponding to
     *         a filesystem currently online, or a null pointer
     *         if there are no more filesystems to enumerate.
     */
    public  YFiles nextFiles()
    {
        String next_hwid = YAPI.getNextHardwareId(_className, _func);
        if(next_hwid == null) return null;
        return FindFiles(next_hwid);
    }

    /**
     * Retrieves a filesystem for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     * 
     * This function does not require that the filesystem is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YFiles.isOnline() to test if the filesystem is
     * indeed online at a given time. In case of ambiguity when looking for
     * a filesystem by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     * 
     * @param func : a string that uniquely characterizes the filesystem
     * 
     * @return a YFiles object allowing you to drive the filesystem.
     */
    public static YFiles FindFiles(String func)
    {   YFunction yfunc = YAPI.getFunction("Files", func);
        if (yfunc != null) {
            return (YFiles) yfunc;
        }
        return new YFiles(func);
    }

    /**
     * Starts the enumeration of filesystems currently accessible.
     * Use the method YFiles.nextFiles() to iterate on
     * next filesystems.
     * 
     * @return a pointer to a YFiles object, corresponding to
     *         the first filesystem currently online, or a null pointer
     *         if there are none.
     */
    public static YFiles FirstFiles()
    {
        String next_hwid = YAPI.getFirstHardwareId("Files");
        if (next_hwid == null)  return null;
        return FindFiles(next_hwid);
    }

    /**
     * 
     * @param func : functionid
     */
    private YFiles(String func)
    {
        super("Files", func);
    }

    @Override
    void advertiseValue(String newvalue)
    {
        super.advertiseValue(newvalue);
        if (_valueCallbackFiles != null) {
            _valueCallbackFiles.yNewValue(this, newvalue);
        }
    }

    /**
     * Internal: check if we have a callback interface registered
     * 
     * @return yes if the user has registered a interface
     */
    @Override
     protected boolean hasCallbackRegistered()
    {
        return super.hasCallbackRegistered() || (_valueCallbackFiles!=null);
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
    public void registerValueCallback(YFiles.UpdateCallback callback)
    {
         _valueCallbackFiles =  callback;
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

    //--- (end of generated code: YFiles implementation)
};

