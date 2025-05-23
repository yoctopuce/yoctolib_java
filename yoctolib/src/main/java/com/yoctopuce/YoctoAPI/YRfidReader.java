/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindRfidReader(), the high-level API for RfidReader functions
 *
 *  - - - - - - - - - License information: - - - - - - - - -
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
 */

package com.yoctopuce.YoctoAPI;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Arrays;

//--- (generated code: YRfidReader return codes)
//--- (end of generated code: YRfidReader return codes)
//--- (generated code: YRfidReader yapiwrapper)
//--- (end of generated code: YRfidReader yapiwrapper)
//--- (generated code: YRfidReader class start)
/**
 * YRfidReader Class: RfidReader function interface
 *
 * The YRfidReader class allows you to detect RFID tags, as well as
 * read and write on these tags if the security settings allow it.
 *
 * Short reminder:
 * <ul>
 * <li>A tag's memory is generally organized in fixed-size blocks.</li>
 * <li>At tag level, each block must be read and written in its entirety.</li>
 * <li>Some blocks are special configuration blocks, and may alter the tag's behavior
 * if they are rewritten with arbitrary data.</li>
 * <li>Data blocks can be set to read-only mode, but on many tags, this operation is irreversible.</li>
 * </ul>
 *
 * By default, the RfidReader class automatically manages these blocks so that
 * arbitrary size data  can be manipulated of  without risk and without knowledge of
 * tag architecture.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YRfidReader extends YFunction
{
//--- (end of generated code: YRfidReader class start)
//--- (generated code: YRfidReader definitions)
    /**
     * invalid nTags value
     */
    public static final int NTAGS_INVALID = YAPI.INVALID_UINT;
    /**
     * invalid refreshRate value
     */
    public static final int REFRESHRATE_INVALID = YAPI.INVALID_UINT;
    protected int _nTags = NTAGS_INVALID;
    protected int _refreshRate = REFRESHRATE_INVALID;
    protected UpdateCallback _valueCallbackRfidReader = null;
    protected YEventCallback _eventCallback;
    protected boolean _isFirstCb;
    protected int _prevCbPos = 0;
    protected int _eventPos = 0;
    protected int _eventStamp = 0;

    /**
     * Deprecated UpdateCallback for RfidReader
     */
    public interface UpdateCallback
    {
        /**
         *
         * @param function      : the function object of which the value has changed
         * @param functionValue : the character string describing the new advertised value
         */
        void yNewValue(YRfidReader function, String functionValue);
    }

    /**
     * TimedReportCallback for RfidReader
     */
    public interface TimedReportCallback
    {
        /**
         *
         * @param function : the function object of which the value has changed
         * @param measure  : measure
         */
        void timedReportCallback(YRfidReader  function, YMeasure measure);
    }
    /**
     * Specialized event Callback for RfidReader
     */
    public interface YEventCallback
    {
        void eventCallback(YRfidReader obj, double timestampr, String evtType, String eventData);
    }

    private UpdateCallback yInternalEventCallback = new UpdateCallback()
    {
        @Override
        public void yNewValue(YRfidReader obj, String value)
        {
            try {
                obj._internalEventHandler(value);
            } catch (YAPI_Exception e) {
                e.printStackTrace();
            }
        }
    };

    //--- (end of generated code: YRfidReader definitions)


    /**
     *
     * @param func : functionid
     */
    protected YRfidReader(YAPIContext ctx, String func)
    {
        super(ctx, func);
        _className = "RfidReader";
        //--- (generated code: YRfidReader attributes initialization)
        //--- (end of generated code: YRfidReader attributes initialization)
    }

    /**
     *
     * @param func : functionid
     */
    protected YRfidReader(String func)
    {
        this(YAPI.GetYCtx(true), func);
    }

    //--- (generated code: YRfidReader implementation)
    @SuppressWarnings("EmptyMethod")
    @Override
    protected void  _parseAttr(YJSONObject json_val) throws Exception
    {
        if (json_val.has("nTags")) {
            _nTags = json_val.getInt("nTags");
        }
        if (json_val.has("refreshRate")) {
            _refreshRate = json_val.getInt("refreshRate");
        }
        super._parseAttr(json_val);
    }

    /**
     * Returns the number of RFID tags currently detected.
     *
     * @return an integer corresponding to the number of RFID tags currently detected
     *
     * @throws YAPI_Exception on error
     */
    public int get_nTags() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return NTAGS_INVALID;
                }
            }
            res = _nTags;
        }
        return res;
    }

    /**
     * Returns the number of RFID tags currently detected.
     *
     * @return an integer corresponding to the number of RFID tags currently detected
     *
     * @throws YAPI_Exception on error
     */
    public int getNTags() throws YAPI_Exception
    {
        return get_nTags();
    }

    /**
     * Returns the tag list refresh rate, measured in Hz.
     *
     * @return an integer corresponding to the tag list refresh rate, measured in Hz
     *
     * @throws YAPI_Exception on error
     */
    public int get_refreshRate() throws YAPI_Exception
    {
        int res;
        synchronized (this) {
            if (_cacheExpiration <= YAPIContext.GetTickCount()) {
                if (load(_yapi._defaultCacheValidity) != YAPI.SUCCESS) {
                    return REFRESHRATE_INVALID;
                }
            }
            res = _refreshRate;
        }
        return res;
    }

    /**
     * Returns the tag list refresh rate, measured in Hz.
     *
     * @return an integer corresponding to the tag list refresh rate, measured in Hz
     *
     * @throws YAPI_Exception on error
     */
    public int getRefreshRate() throws YAPI_Exception
    {
        return get_refreshRate();
    }

    /**
     * Changes the present tag list refresh rate, measured in Hz. The reader will do
     * its best to respect it. Note that the reader cannot detect tag arrival or removal
     * while it is  communicating with a tag.  Maximum frequency is limited to 100Hz,
     * but in real life it will be difficult to do better than 50Hz.  A zero value
     * will power off the device radio.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the present tag list refresh rate, measured in Hz
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int set_refreshRate(int  newval)  throws YAPI_Exception
    {
        String rest_val;
        synchronized (this) {
            rest_val = Integer.toString(newval);
            _setAttr("refreshRate",rest_val);
        }
        return YAPI.SUCCESS;
    }

    /**
     * Changes the present tag list refresh rate, measured in Hz. The reader will do
     * its best to respect it. Note that the reader cannot detect tag arrival or removal
     * while it is  communicating with a tag.  Maximum frequency is limited to 100Hz,
     * but in real life it will be difficult to do better than 50Hz.  A zero value
     * will power off the device radio.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the present tag list refresh rate, measured in Hz
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     */
    public int setRefreshRate(int newval)  throws YAPI_Exception
    {
        return set_refreshRate(newval);
    }

    /**
     * Retrieves a RFID reader for a given identifier.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the RFID reader is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRfidReader.isOnline() to test if the RFID reader is
     * indeed online at a given time. In case of ambiguity when looking for
     * a RFID reader by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * If a call to this object's is_online() method returns FALSE although
     * you are certain that the matching device is plugged, make sure that you did
     * call registerHub() at application initialization time.
     *
     * @param func : a string that uniquely characterizes the RFID reader, for instance
     *         MyDevice.rfidReader.
     *
     * @return a YRfidReader object allowing you to drive the RFID reader.
     */
    public static YRfidReader FindRfidReader(String func)
    {
        YRfidReader obj;
        YAPIContext ctx = YAPI.GetYCtx(true);
        synchronized (ctx._functionCacheLock) {
            obj = (YRfidReader) YFunction._FindFromCache("RfidReader", func);
            if (obj == null) {
                obj = new YRfidReader(func);
                YFunction._AddToCache("RfidReader", func, obj);
            }
        }
        return obj;
    }

    /**
     * Retrieves a RFID reader for a given identifier in a YAPI context.
     * The identifier can be specified using several formats:
     * <ul>
     * <li>FunctionLogicalName</li>
     * <li>ModuleSerialNumber.FunctionIdentifier</li>
     * <li>ModuleSerialNumber.FunctionLogicalName</li>
     * <li>ModuleLogicalName.FunctionIdentifier</li>
     * <li>ModuleLogicalName.FunctionLogicalName</li>
     * </ul>
     *
     * This function does not require that the RFID reader is online at the time
     * it is invoked. The returned object is nevertheless valid.
     * Use the method YRfidReader.isOnline() to test if the RFID reader is
     * indeed online at a given time. In case of ambiguity when looking for
     * a RFID reader by logical name, no error is notified: the first instance
     * found is returned. The search is performed first by hardware name,
     * then by logical name.
     *
     * @param yctx : a YAPI context
     * @param func : a string that uniquely characterizes the RFID reader, for instance
     *         MyDevice.rfidReader.
     *
     * @return a YRfidReader object allowing you to drive the RFID reader.
     */
    public static YRfidReader FindRfidReaderInContext(YAPIContext yctx,String func)
    {
        YRfidReader obj;
        synchronized (yctx._functionCacheLock) {
            obj = (YRfidReader) YFunction._FindFromCacheInContext(yctx, "RfidReader", func);
            if (obj == null) {
                obj = new YRfidReader(yctx, func);
                YFunction._AddToCache("RfidReader", func, obj);
            }
        }
        return obj;
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
     *
     */
    public int registerValueCallback(UpdateCallback callback)
    {
        String val;
        if (callback != null) {
            YFunction._UpdateValueCallbackList(this, true);
        } else {
            YFunction._UpdateValueCallbackList(this, false);
        }
        _valueCallbackRfidReader = callback;
        // Immediately invoke value callback with current value
        if (callback != null && isOnline()) {
            val = _advertisedValue;
            if (!(val.equals(""))) {
                _invokeValueCallback(val);
            }
        }
        return 0;
    }

    @Override
    public int _invokeValueCallback(String value)
    {
        if (_valueCallbackRfidReader != null) {
            _valueCallbackRfidReader.yNewValue(this, value);
        } else {
            super._invokeValueCallback(value);
        }
        return 0;
    }

    public int _chkerror(String tagId,byte[] json,YRfidStatus status) throws YAPI_Exception
    {
        String jsonStr;
        int errCode;
        int errBlk;
        int fab;
        int lab;
        int retcode;

        if ((json).length == 0) {
            errCode = get_errorType();
            errBlk = -1;
            fab = -1;
            lab = -1;
        } else {
            jsonStr = new String(json, _yapi._deviceCharset);
            errCode = YAPIContext._atoi(_json_get_key(json, "err"));
            errBlk = YAPIContext._atoi(_json_get_key(json, "errBlk"))-1;
            if (jsonStr.indexOf("\"fab\":") >= 0) {
                fab = YAPIContext._atoi(_json_get_key(json, "fab"))-1;
                lab = YAPIContext._atoi(_json_get_key(json, "lab"))-1;
            } else {
                fab = -1;
                lab = -1;
            }
        }
        status.imm_init(tagId, errCode, errBlk, fab, lab);
        retcode = status.get_yapiError();
        //noinspection DoubleNegation
        if (!(retcode == YAPI.SUCCESS)) { throw new YAPI_Exception(retcode, status.get_errorMessage());}
        return YAPI.SUCCESS;
    }

    public int reset() throws YAPI_Exception
    {
        byte[] json = new byte[0];
        YRfidStatus status;
        status = new YRfidStatus();

        json = _download("rfid.json?a=reset");
        return _chkerror("", json, status);
    }

    /**
     * Returns the list of RFID tags currently detected by the reader.
     *
     * @return a list of strings, corresponding to each tag identifier (UID).
     *
     * @throws YAPI_Exception on error
     */
    public ArrayList<String> get_tagIdList() throws YAPI_Exception
    {
        byte[] json = new byte[0];
        ArrayList<byte[]> jsonList = new ArrayList<>();
        ArrayList<String> taglist = new ArrayList<>();

        json = _download("rfid.json?a=list");
        taglist.clear();
        if ((json).length > 3) {
            jsonList = _json_get_array(json);
            for (byte[] ii_0:jsonList) {
                taglist.add(_json_get_string(ii_0));
            }
        }
        return taglist;
    }

    /**
     * Returns a description of the properties of an existing RFID tag.
     * This function can cause communications with the tag.
     *
     * @param tagId : identifier of the tag to check
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return a YRfidTagInfo object.
     *
     * @throws YAPI_Exception on error
     * When it happens, you can get more information from the status object.
     */
    public YRfidTagInfo get_tagInfo(String tagId,YRfidStatus status) throws YAPI_Exception
    {
        String url;
        byte[] json = new byte[0];
        int tagType;
        int size;
        int usable;
        int blksize;
        int fblk;
        int lblk;
        YRfidTagInfo res;
        url = String.format(Locale.US, "rfid.json?a=info&t=%s",tagId);

        json = _download(url);
        _chkerror(tagId, json, status);
        tagType = YAPIContext._atoi(_json_get_key(json, "type"));
        size = YAPIContext._atoi(_json_get_key(json, "size"));
        usable = YAPIContext._atoi(_json_get_key(json, "usable"));
        blksize = YAPIContext._atoi(_json_get_key(json, "blksize"));
        fblk = YAPIContext._atoi(_json_get_key(json, "fblk"));
        lblk = YAPIContext._atoi(_json_get_key(json, "lblk"));
        res = new YRfidTagInfo();
        res.imm_init(tagId, tagType, size, usable, blksize, fblk, lblk);
        return res;
    }

    /**
     * Changes an RFID tag configuration to prevents any further write to
     * the selected blocks. This operation is definitive and irreversible.
     * Depending on the tag type and block index, adjascent blocks may become
     * read-only as well, based on the locking granularity.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : first block to lock
     * @param nBlocks : number of blocks to lock
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagLockBlocks(String tagId,int firstBlock,int nBlocks,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=lock&t=%s&b=%d&n=%d%s",tagId,firstBlock,nBlocks,optstr);

        json = _download(url);
        return _chkerror(tagId, json, status);
    }

    /**
     * Reads the locked state for RFID tag memory data blocks.
     * FirstBlock cannot be a special block, and any special
     * block encountered in the middle of the read operation will be
     * skipped automatically.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : number of the first block to check
     * @param nBlocks : number of blocks to check
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return a list of booleans with the lock state of selected blocks
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public ArrayList<Boolean> get_tagLockState(String tagId,int firstBlock,int nBlocks,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        byte[] binRes = new byte[0];
        ArrayList<Boolean> res = new ArrayList<>();
        int idx;
        int val;
        boolean isLocked;
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=chkl&t=%s&b=%d&n=%d%s",tagId,firstBlock,nBlocks,optstr);

        json = _download(url);
        _chkerror(tagId, json, status);
        if (status.get_yapiError() != YAPI.SUCCESS) {
            return res;
        }
        binRes = YAPIContext._hexStrToBin(_json_get_key(json, "bitmap"));
        idx = 0;
        while (idx < nBlocks) {
            val = (binRes[(idx >> 3)] & 0xff);
            isLocked = ((val & (1 << (idx & 7))) != 0);
            res.add(isLocked);
            idx = idx + 1;
        }
        return res;
    }

    /**
     * Tells which block of a RFID tag memory are special and cannot be used
     * to store user data. Mistakely writing a special block can lead to
     * an irreversible alteration of the tag.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : number of the first block to check
     * @param nBlocks : number of blocks to check
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return a list of booleans with the lock state of selected blocks
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public ArrayList<Boolean> get_tagSpecialBlocks(String tagId,int firstBlock,int nBlocks,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        byte[] binRes = new byte[0];
        ArrayList<Boolean> res = new ArrayList<>();
        int idx;
        int val;
        boolean isLocked;
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=chks&t=%s&b=%d&n=%d%s",tagId,firstBlock,nBlocks,optstr);

        json = _download(url);
        _chkerror(tagId, json, status);
        if (status.get_yapiError() != YAPI.SUCCESS) {
            return res;
        }
        binRes = YAPIContext._hexStrToBin(_json_get_key(json, "bitmap"));
        idx = 0;
        while (idx < nBlocks) {
            val = (binRes[(idx >> 3)] & 0xff);
            isLocked = ((val & (1 << (idx & 7))) != 0);
            res.add(isLocked);
            idx = idx + 1;
        }
        return res;
    }

    /**
     * Reads data from an RFID tag memory, as an hexadecimal string.
     * The read operation may span accross multiple blocks if the requested
     * number of bytes is larger than the RFID tag block size. By default
     * firstBlock cannot be a special block, and any special block encountered
     * in the middle of the read operation will be skipped automatically.
     * If you rather want to read special blocks, use the EnableRawAccess
     * field from the options parameter.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : block number where read should start
     * @param nBytes : total number of bytes to read
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return an hexadecimal string if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public String tagReadHex(String tagId,int firstBlock,int nBytes,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        String hexbuf;
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=read&t=%s&b=%d&n=%d%s",tagId,firstBlock,nBytes,optstr);

        json = _download(url);
        _chkerror(tagId, json, status);
        if (status.get_yapiError() == YAPI.SUCCESS) {
            hexbuf = _json_get_key(json, "res");
        } else {
            hexbuf = "";
        }
        return hexbuf;
    }

    /**
     * Reads data from an RFID tag memory, as a binary buffer. The read operation
     * may span accross multiple blocks if the requested number of bytes
     * is larger than the RFID tag block size.  By default
     * firstBlock cannot be a special block, and any special block encountered
     * in the middle of the read operation will be skipped automatically.
     * If you rather want to read special blocks, use the EnableRawAccess
     * field frrm the options parameter.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : block number where read should start
     * @param nBytes : total number of bytes to read
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return a binary object with the data read if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public byte[] tagReadBin(String tagId,int firstBlock,int nBytes,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        return YAPIContext._hexStrToBin(tagReadHex(tagId, firstBlock, nBytes, options, status));
    }

    /**
     * Reads data from an RFID tag memory, as a byte list. The read operation
     * may span accross multiple blocks if the requested number of bytes
     * is larger than the RFID tag block size.  By default
     * firstBlock cannot be a special block, and any special block encountered
     * in the middle of the read operation will be skipped automatically.
     * If you rather want to read special blocks, use the EnableRawAccess
     * field from the options parameter.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : block number where read should start
     * @param nBytes : total number of bytes to read
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return a byte list with the data read if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public ArrayList<Integer> tagReadArray(String tagId,int firstBlock,int nBytes,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        byte[] blk = new byte[0];
        int idx;
        int endidx;
        ArrayList<Integer> res = new ArrayList<>();
        blk = tagReadBin(tagId, firstBlock, nBytes, options, status);
        endidx = (blk).length;
        idx = 0;
        while (idx < endidx) {
            res.add((blk[idx] & 0xff));
            idx = idx + 1;
        }
        return res;
    }

    /**
     * Reads data from an RFID tag memory, as a text string. The read operation
     * may span accross multiple blocks if the requested number of bytes
     * is larger than the RFID tag block size.  By default
     * firstBlock cannot be a special block, and any special block encountered
     * in the middle of the read operation will be skipped automatically.
     * If you rather want to read special blocks, use the EnableRawAccess
     * field from the options parameter.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : block number where read should start
     * @param nChars : total number of characters to read
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return a text string with the data read if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public String tagReadStr(String tagId,int firstBlock,int nChars,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        return new String(tagReadBin(tagId, firstBlock, nChars, options, status), _yapi._deviceCharset);
    }

    /**
     * Writes data provided as a binary buffer to an RFID tag memory.
     * The write operation may span accross multiple blocks if the
     * number of bytes to write is larger than the RFID tag block size.
     * By default firstBlock cannot be a special block, and any special block
     * encountered in the middle of the write operation will be skipped
     * automatically. The last data block affected by the operation will
     * be automatically padded with zeros if neccessary.  If you rather want
     * to rewrite special blocks as well,
     * use the EnableRawAccess field from the options parameter.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : block number where write should start
     * @param buff : the binary buffer to write
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagWriteBin(String tagId,int firstBlock,byte[] buff,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String hexstr;
        int buflen;
        String fname;
        byte[] json = new byte[0];
        buflen = (buff).length;
        if (buflen <= 16) {
            // short data, use an URL-based command
            hexstr = YAPIContext._bytesToHexStr(buff, 0, buff.length);
            return tagWriteHex(tagId, firstBlock, hexstr, options, status);
        } else {
            // long data, use an upload command
            optstr = options.imm_getParams();
            fname = String.format(Locale.US, "Rfid:t=%s&b=%d&n=%d%s",tagId,firstBlock,buflen,optstr);
            json = _uploadEx(fname, buff);
            return _chkerror(tagId, json, status);
        }
    }

    /**
     * Writes data provided as a list of bytes to an RFID tag memory.
     * The write operation may span accross multiple blocks if the
     * number of bytes to write is larger than the RFID tag block size.
     * By default firstBlock cannot be a special block, and any special block
     * encountered in the middle of the write operation will be skipped
     * automatically. The last data block affected by the operation will
     * be automatically padded with zeros if neccessary.
     * If you rather want to rewrite special blocks as well,
     * use the EnableRawAccess field from the options parameter.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : block number where write should start
     * @param byteList : a list of byte to write
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagWriteArray(String tagId,int firstBlock,ArrayList<Integer> byteList,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        int bufflen;
        byte[] buff = new byte[0];
        int idx;
        int hexb;
        bufflen = byteList.size();
        buff = new byte[bufflen];
        idx = 0;
        while (idx < bufflen) {
            hexb = byteList.get(idx).intValue();
            buff[idx] = (byte)(hexb & 0xff);
            idx = idx + 1;
        }

        return tagWriteBin(tagId, firstBlock, buff, options, status);
    }

    /**
     * Writes data provided as an hexadecimal string to an RFID tag memory.
     * The write operation may span accross multiple blocks if the
     * number of bytes to write is larger than the RFID tag block size.
     * By default firstBlock cannot be a special block, and any special block
     * encountered in the middle of the write operation will be skipped
     * automatically. The last data block affected by the operation will
     * be automatically padded with zeros if neccessary.
     * If you rather want to rewrite special blocks as well,
     * use the EnableRawAccess field from the options parameter.
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : block number where write should start
     * @param hexString : a string of hexadecimal byte codes to write
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagWriteHex(String tagId,int firstBlock,String hexString,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        int bufflen;
        String optstr;
        String url;
        byte[] json = new byte[0];
        byte[] buff = new byte[0];
        int idx;
        int hexb;
        bufflen = hexString.length();
        bufflen = (bufflen >> 1);
        if (bufflen <= 16) {
            // short data, use an URL-based command
            optstr = options.imm_getParams();
            url = String.format(Locale.US, "rfid.json?a=writ&t=%s&b=%d&w=%s%s",tagId,firstBlock,hexString,optstr);
            json = _download(url);
            return _chkerror(tagId, json, status);
        } else {
            // long data, use an upload command
            buff = new byte[bufflen];
            idx = 0;
            while (idx < bufflen) {
                hexb = Integer.valueOf((hexString).substring(2 * idx, 2 * idx + 2),16);
                buff[idx] = (byte)(hexb & 0xff);
                idx = idx + 1;
            }
            return tagWriteBin(tagId, firstBlock, buff, options, status);
        }
    }

    /**
     * Writes data provided as an ASCII string to an RFID tag memory.
     * The write operation may span accross multiple blocks if the
     * number of bytes to write is larger than the RFID tag block size.
     * Note that only the characters present in the provided string
     * will be written, there is no notion of string length. If your
     * string data have variable length, you'll have to encode the
     * string length yourself, with a terminal zero for instannce.
     *
     * This function only works with ISO-latin characters, if you wish to
     * write strings encoded with alternate character sets, you'll have to
     * use tagWriteBin() function.
     *
     * By default firstBlock cannot be a special block, and any special block
     * encountered in the middle of the write operation will be skipped
     * automatically. The last data block affected by the operation will
     * be automatically padded with zeros if neccessary.
     * If you rather want to rewrite special blocks as well,
     * use the EnableRawAccess field from the options parameter
     * (definitely not recommanded).
     *
     * @param tagId : identifier of the tag to use
     * @param firstBlock : block number where write should start
     * @param text : the text string to write
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagWriteStr(String tagId,int firstBlock,String text,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        byte[] buff = new byte[0];
        buff = (text).getBytes(_yapi._deviceCharset);

        return tagWriteBin(tagId, firstBlock, buff, options, status);
    }

    /**
     * Reads an RFID tag AFI byte (ISO 15693 only).
     *
     * @param tagId : identifier of the tag to use
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return the AFI value (0...255)
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagGetAFI(String tagId,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        int res;
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=rdsf&t=%s&b=0%s",tagId,optstr);

        json = _download(url);
        _chkerror(tagId, json, status);
        if (status.get_yapiError() == YAPI.SUCCESS) {
            res = YAPIContext._atoi(_json_get_key(json, "res"));
        } else {
            res = status.get_yapiError();
        }
        return res;
    }

    /**
     * Changes an RFID tag AFI byte (ISO 15693 only).
     *
     * @param tagId : identifier of the tag to use
     * @param afi : the AFI value to write (0...255)
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagSetAFI(String tagId,int afi,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=wrsf&t=%s&b=0&v=%d%s",tagId,afi,optstr);

        json = _download(url);
        return _chkerror(tagId, json, status);
    }

    /**
     * Locks the RFID tag AFI byte (ISO 15693 only).
     * This operation is definitive and irreversible.
     *
     * @param tagId : identifier of the tag to use
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagLockAFI(String tagId,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=lksf&t=%s&b=0%s",tagId,optstr);

        json = _download(url);
        return _chkerror(tagId, json, status);
    }

    /**
     * Reads an RFID tag DSFID byte (ISO 15693 only).
     *
     * @param tagId : identifier of the tag to use
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return the DSFID value (0...255)
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagGetDSFID(String tagId,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        int res;
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=rdsf&t=%s&b=1%s",tagId,optstr);

        json = _download(url);
        _chkerror(tagId, json, status);
        if (status.get_yapiError() == YAPI.SUCCESS) {
            res = YAPIContext._atoi(_json_get_key(json, "res"));
        } else {
            res = status.get_yapiError();
        }
        return res;
    }

    /**
     * Changes an RFID tag DSFID byte (ISO 15693 only).
     *
     * @param tagId : identifier of the tag to use
     * @param dsfid : the DSFID value to write (0...255)
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagSetDSFID(String tagId,int dsfid,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=wrsf&t=%s&b=1&v=%d%s",tagId,dsfid,optstr);

        json = _download(url);
        return _chkerror(tagId, json, status);
    }

    /**
     * Locks the RFID tag DSFID byte (ISO 15693 only).
     * This operation is definitive and irreversible.
     *
     * @param tagId : identifier of the tag to use
     * @param options : an YRfidOptions object with the optional
     *         command execution parameters, such as security key
     *         if required
     * @param status : an RfidStatus object that will contain
     *         the detailled status of the operation
     *
     * @return YAPI.SUCCESS if the call succeeds.
     *
     * @throws YAPI_Exception on error
     * happens, you can get more information from the status object.
     */
    public int tagLockDSFID(String tagId,YRfidOptions options,YRfidStatus status) throws YAPI_Exception
    {
        String optstr;
        String url;
        byte[] json = new byte[0];
        optstr = options.imm_getParams();
        url = String.format(Locale.US, "rfid.json?a=lksf&t=%s&b=1%s",tagId,optstr);

        json = _download(url);
        return _chkerror(tagId, json, status);
    }

    /**
     * Returns a string with last tag arrival/removal events observed.
     * This method return only events that are still buffered in the device memory.
     *
     * @return a string with last events observed (one per line).
     *
     * @throws YAPI_Exception on error
     */
    public String get_lastEvents() throws YAPI_Exception
    {
        byte[] content = new byte[0];

        content = _download("events.txt?pos=0");
        return new String(content, _yapi._deviceCharset);
    }

    /**
     * Registers a callback function to be called each time that an RFID tag appears or
     * disappears. The callback is invoked only during the execution of
     * ySleep or yHandleEvents. This provides control over the time when
     * the callback is triggered. For good responsiveness, remember to call one of these
     * two functions periodically. To unregister a callback, pass a null pointer as argument.
     *
     * @param callback : the callback function to call, or a null pointer.
     *         The callback function should take four arguments:
     *         the YRfidReader object that emitted the event, the
     *         UTC timestamp of the event, a character string describing
     *         the type of event ("+" or "-") and a character string with the
     *         RFID tag identifier.
     * @throws YAPI_Exception on error
     */
    public int registerEventCallback(YEventCallback callback) throws YAPI_Exception
    {
        _eventCallback = callback;
        _isFirstCb = true;
        if (callback != null) {
            registerValueCallback(yInternalEventCallback);
        } else {
            registerValueCallback((UpdateCallback) null);
        }
        return 0;
    }

    public int _internalEventHandler(String cbVal) throws YAPI_Exception
    {
        int cbPos;
        int cbDPos;
        String url;
        byte[] content = new byte[0];
        String contentStr;
        ArrayList<String> eventArr = new ArrayList<>();
        int arrLen;
        String lenStr;
        int arrPos;
        String eventStr;
        int eventLen;
        String hexStamp;
        int typePos;
        int dataPos;
        int intStamp;
        byte[] binMStamp = new byte[0];
        int msStamp;
        double evtStamp;
        String evtType;
        String evtData;
        // detect possible power cycle of the reader to clear event pointer
        cbPos = YAPIContext._atoi(cbVal);
        cbPos = (cbPos / 1000);
        cbDPos = ((cbPos - _prevCbPos) & 0x7ffff);
        _prevCbPos = cbPos;
        if (cbDPos > 16384) {
            _eventPos = 0;
        }
        if (!(_eventCallback != null)) {
            return YAPI.SUCCESS;
        }
        if (_isFirstCb) {
            // first emulated value callback caused by registerValueCallback:
            // retrieve arrivals of all tags currently present to emulate arrival
            _isFirstCb = false;
            _eventStamp = 0;
            content = _download("events.txt");
            contentStr = new String(content, _yapi._deviceCharset);
            eventArr = new ArrayList<>(Arrays.asList(contentStr.split("\n")));
            arrLen = eventArr.size();
            //noinspection DoubleNegation
            if (!(arrLen > 0)) { throw new YAPI_Exception(YAPI.IO_ERROR, "fail to download events");}
            // first element of array is the new position preceeded by '@'
            arrPos = 1;
            lenStr = eventArr.get(0);
            lenStr = (lenStr).substring(1, 1 + lenStr.length()-1);
            // update processed event position pointer
            _eventPos = YAPIContext._atoi(lenStr);
        } else {
            // load all events since previous call
            url = String.format(Locale.US, "events.txt?pos=%d",_eventPos);
            content = _download(url);
            contentStr = new String(content, _yapi._deviceCharset);
            eventArr = new ArrayList<>(Arrays.asList(contentStr.split("\n")));
            arrLen = eventArr.size();
            //noinspection DoubleNegation
            if (!(arrLen > 0)) { throw new YAPI_Exception(YAPI.IO_ERROR, "fail to download events");}
            // last element of array is the new position preceeded by '@'
            arrPos = 0;
            arrLen = arrLen - 1;
            lenStr = eventArr.get(arrLen);
            lenStr = (lenStr).substring(1, 1 + lenStr.length()-1);
            // update processed event position pointer
            _eventPos = YAPIContext._atoi(lenStr);
        }
        // now generate callbacks for each real event
        while (arrPos < arrLen) {
            eventStr = eventArr.get(arrPos);
            eventLen = eventStr.length();
            typePos = eventStr.indexOf(":")+1;
            if ((eventLen >= 14) && (typePos > 10)) {
                hexStamp = (eventStr).substring(0, 8);
                intStamp = Integer.valueOf(hexStamp,16);
                if (intStamp >= _eventStamp) {
                    _eventStamp = intStamp;
                    binMStamp = ((eventStr).substring(8, 8 + 2)).getBytes(_yapi._deviceCharset);
                    msStamp = ((binMStamp[0] & 0xff)-64) * 32 + (binMStamp[1] & 0xff);
                    evtStamp = intStamp + (0.001 * msStamp);
                    dataPos = eventStr.indexOf("=")+1;
                    evtType = (eventStr).substring(typePos, typePos + 1);
                    evtData = "";
                    if (dataPos > 10) {
                        evtData = (eventStr).substring(dataPos, dataPos + eventLen-dataPos);
                    }
                    if (_eventCallback != null) {
                        _eventCallback.eventCallback(this, evtStamp, evtType, evtData);
                    }
                }
            }
            arrPos = arrPos + 1;
        }
        return YAPI.SUCCESS;
    }

    /**
     * Continues the enumeration of RFID readers started using yFirstRfidReader().
     * Caution: You can't make any assumption about the returned RFID readers order.
     * If you want to find a specific a RFID reader, use RfidReader.findRfidReader()
     * and a hardwareID or a logical name.
     *
     * @return a pointer to a YRfidReader object, corresponding to
     *         a RFID reader currently online, or a null pointer
     *         if there are no more RFID readers to enumerate.
     */
    public YRfidReader nextRfidReader()
    {
        String next_hwid;
        try {
            String hwid = _yapi._yHash.resolveHwID(_className, _func);
            next_hwid = _yapi._yHash.getNextHardwareId(_className, hwid);
        } catch (YAPI_Exception ignored) {
            next_hwid = null;
        }
        if(next_hwid == null) return null;
        return FindRfidReaderInContext(_yapi, next_hwid);
    }

    /**
     * Starts the enumeration of RFID readers currently accessible.
     * Use the method YRfidReader.nextRfidReader() to iterate on
     * next RFID readers.
     *
     * @return a pointer to a YRfidReader object, corresponding to
     *         the first RFID reader currently online, or a null pointer
     *         if there are none.
     */
    public static YRfidReader FirstRfidReader()
    {
        YAPIContext yctx = YAPI.GetYCtx(false);
        if (yctx == null)  return null;
        String next_hwid = yctx._yHash.getFirstHardwareId("RfidReader");
        if (next_hwid == null)  return null;
        return FindRfidReaderInContext(yctx, next_hwid);
    }

    /**
     * Starts the enumeration of RFID readers currently accessible.
     * Use the method YRfidReader.nextRfidReader() to iterate on
     * next RFID readers.
     *
     * @param yctx : a YAPI context.
     *
     * @return a pointer to a YRfidReader object, corresponding to
     *         the first RFID reader currently online, or a null pointer
     *         if there are none.
     */
    public static YRfidReader FirstRfidReaderInContext(YAPIContext yctx)
    {
        String next_hwid = yctx._yHash.getFirstHardwareId("RfidReader");
        if (next_hwid == null)  return null;
        return FindRfidReaderInContext(yctx, next_hwid);
    }

    //--- (end of generated code: YRfidReader implementation)
}

