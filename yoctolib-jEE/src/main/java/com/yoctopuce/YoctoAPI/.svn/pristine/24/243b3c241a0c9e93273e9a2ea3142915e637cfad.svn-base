/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindRfidTagInfo(), the high-level API for RfidTagInfo functions
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

//--- (generated code: YRfidTagInfo return codes)
//--- (end of generated code: YRfidTagInfo return codes)
//--- (generated code: YRfidTagInfo yapiwrapper)
//--- (end of generated code: YRfidTagInfo yapiwrapper)
//--- (generated code: YRfidTagInfo class start)
/**
 * YRfidTagInfo Class: RFID tag description, used by class YRfidReader
 *
 * YRfidTagInfo objects are used to describe RFID tag attributes,
 * such as the tag type and its storage size. These objects are returned by
 * method get_tagInfo() of class YRfidReader.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YRfidTagInfo
{
//--- (end of generated code: YRfidTagInfo class start)
//--- (generated code: YRfidTagInfo definitions)
    public static final int IEC_15693 = 1;
    public static final int IEC_14443 = 2;
    public static final int IEC_14443_MIFARE_ULTRALIGHT = 3;
    public static final int IEC_14443_MIFARE_CLASSIC1K = 4;
    public static final int IEC_14443_MIFARE_CLASSIC4K = 5;
    public static final int IEC_14443_MIFARE_DESFIRE = 6;
    public static final int IEC_14443_NTAG_213 = 7;
    public static final int IEC_14443_NTAG_215 = 8;
    public static final int IEC_14443_NTAG_216 = 9;
    public static final int IEC_14443_NTAG_424_DNA = 10;
    protected String _tagId;
    protected int _tagType = 0;
    protected String _typeStr;
    protected int _size = 0;
    protected int _usable = 0;
    protected int _blksize = 0;
    protected int _fblk = 0;
    protected int _lblk = 0;

    //--- (end of generated code: YRfidTagInfo definitions)


    protected YRfidTagInfo()
    {
        //--- (generated code: YRfidTagInfo attributes initialization)
        //--- (end of generated code: YRfidTagInfo attributes initialization)
    }

    //--- (generated code: YRfidTagInfo implementation)

    /**
     * Returns the RFID tag identifier.
     *
     * @return a string with the RFID tag identifier.
     */
    public String get_tagId()
    {
        return _tagId;
    }

    /**
     * Returns the type of the RFID tag, as a numeric constant.
     * (IEC_14443_MIFARE_CLASSIC1K, ...).
     *
     * @return an integer corresponding to the RFID tag type
     */
    public int get_tagType()
    {
        return _tagType;
    }

    /**
     * Returns the type of the RFID tag, as a string.
     *
     * @return a string corresponding to the RFID tag type
     */
    public String get_tagTypeStr()
    {
        return _typeStr;
    }

    /**
     * Returns the total memory size of the RFID tag, in bytes.
     *
     * @return the total memory size of the RFID tag
     */
    public int get_tagMemorySize()
    {
        return _size;
    }

    /**
     * Returns the usable storage size of the RFID tag, in bytes.
     *
     * @return the usable storage size of the RFID tag
     */
    public int get_tagUsableSize()
    {
        return _usable;
    }

    /**
     * Returns the block size of the RFID tag, in bytes.
     *
     * @return the block size of the RFID tag
     */
    public int get_tagBlockSize()
    {
        return _blksize;
    }

    /**
     * Returns the index of the first usable storage block on the RFID tag.
     *
     * @return the index of the first usable storage block on the RFID tag
     */
    public int get_tagFirstBlock()
    {
        return _fblk;
    }

    /**
     * Returns the index of the last usable storage block on the RFID tag.
     *
     * @return the index of the last usable storage block on the RFID tag
     */
    public int get_tagLastBlock()
    {
        return _lblk;
    }

    public void imm_init(String tagId,int tagType,int size,int usable,int blksize,int fblk,int lblk)
    {
        String typeStr;
        typeStr = "unknown";
        if (tagType == IEC_15693) {
            typeStr = "IEC 15693";
        }
        if (tagType == IEC_14443) {
            typeStr = "IEC 14443";
        }
        if (tagType == IEC_14443_MIFARE_ULTRALIGHT) {
            typeStr = "MIFARE Ultralight";
        }
        if (tagType == IEC_14443_MIFARE_CLASSIC1K) {
            typeStr = "MIFARE Classic 1K";
        }
        if (tagType == IEC_14443_MIFARE_CLASSIC4K) {
            typeStr = "MIFARE Classic 4K";
        }
        if (tagType == IEC_14443_MIFARE_DESFIRE) {
            typeStr = "MIFARE DESFire";
        }
        if (tagType == IEC_14443_NTAG_213) {
            typeStr = "NTAG 213";
        }
        if (tagType == IEC_14443_NTAG_215) {
            typeStr = "NTAG 215";
        }
        if (tagType == IEC_14443_NTAG_216) {
            typeStr = "NTAG 216";
        }
        if (tagType == IEC_14443_NTAG_424_DNA) {
            typeStr = "NTAG 424 DNA";
        }
        _tagId = tagId;
        _tagType = tagType;
        _typeStr = typeStr;
        _size = size;
        _usable = usable;
        _blksize = blksize;
        _fblk = fblk;
        _lblk = lblk;
    }

    //--- (end of generated code: YRfidTagInfo implementation)
}

