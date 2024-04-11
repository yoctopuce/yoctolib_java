/*
 *
 *  $Id: svn_id $
 *
 *  Implements FindRfidStatus(), the high-level API for RfidStatus functions
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
import java.util.Locale;

//--- (generated code: YRfidStatus return codes)
//--- (end of generated code: YRfidStatus return codes)
//--- (generated code: YRfidStatus yapiwrapper)
//--- (end of generated code: YRfidStatus yapiwrapper)
//--- (generated code: YRfidStatus class start)
/**
 *  YRfidStatus Class: Detailled information about the result of RFID tag operations, allowing to find
 * out what happened exactly after a tag operation failure.
 *
 * YRfidStatus objects provide additional information about
 * operations on RFID tags, including the range of blocks affected
 * by read/write operations and possible errors when communicating
 * with RFID tags.
 * This makes it possible, for example, to distinguish communication
 * errors that can be recovered by an additional attempt, from
 * security or other errors on the tag.
 * Combined with the EnableDryRun option in RfidOptions,
 * this structure can be used to predict which blocks will be affected
 * by a write operation.
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YRfidStatus
{
//--- (end of generated code: YRfidStatus class start)
//--- (generated code: YRfidStatus definitions)
    public static final int SUCCESS = 0;
    public static final int COMMAND_NOT_SUPPORTED = 1;
    public static final int COMMAND_NOT_RECOGNIZED = 2;
    public static final int COMMAND_OPTION_NOT_RECOGNIZED = 3;
    public static final int COMMAND_CANNOT_BE_PROCESSED_IN_TIME = 4;
    public static final int UNDOCUMENTED_ERROR = 15;
    public static final int BLOCK_NOT_AVAILABLE = 16;
    public static final int BLOCK_ALREADY_LOCKED = 17;
    public static final int BLOCK_LOCKED = 18;
    public static final int BLOCK_NOT_SUCESSFULLY_PROGRAMMED = 19;
    public static final int BLOCK_NOT_SUCESSFULLY_LOCKED = 20;
    public static final int BLOCK_IS_PROTECTED = 21;
    public static final int CRYPTOGRAPHIC_ERROR = 64;
    public static final int READER_BUSY = 1000;
    public static final int TAG_NOTFOUND = 1001;
    public static final int TAG_LEFT = 1002;
    public static final int TAG_JUSTLEFT = 1003;
    public static final int TAG_COMMUNICATION_ERROR = 1004;
    public static final int TAG_NOT_RESPONDING = 1005;
    public static final int TIMEOUT_ERROR = 1006;
    public static final int COLLISION_DETECTED = 1007;
    public static final int INVALID_CMD_ARGUMENTS = -66;
    public static final int UNKNOWN_CAPABILITIES = -67;
    public static final int MEMORY_NOT_SUPPORTED = -68;
    public static final int INVALID_BLOCK_INDEX = -69;
    public static final int MEM_SPACE_UNVERRUN_ATTEMPT = -70;
    public static final int BROWNOUT_DETECTED = -71     ;
    public static final int BUFFER_OVERFLOW = -72;
    public static final int CRC_ERROR = -73;
    public static final int COMMAND_RECEIVE_TIMEOUT = -75;
    public static final int DID_NOT_SLEEP = -76;
    public static final int ERROR_DECIMAL_EXPECTED = -77;
    public static final int HARDWARE_FAILURE = -78;
    public static final int ERROR_HEX_EXPECTED = -79;
    public static final int FIFO_LENGTH_ERROR = -80;
    public static final int FRAMING_ERROR = -81;
    public static final int NOT_IN_CNR_MODE = -82;
    public static final int NUMBER_OU_OF_RANGE = -83;
    public static final int NOT_SUPPORTED = -84;
    public static final int NO_RF_FIELD_ACTIVE = -85;
    public static final int READ_DATA_LENGTH_ERROR = -86;
    public static final int WATCHDOG_RESET = -87;
    public static final int UNKNOW_COMMAND = -91;
    public static final int UNKNOW_ERROR = -92;
    public static final int UNKNOW_PARAMETER = -93;
    public static final int UART_RECEIVE_ERROR = -94;
    public static final int WRONG_DATA_LENGTH = -95;
    public static final int WRONG_MODE = -96;
    public static final int UNKNOWN_DWARFxx_ERROR_CODE = -97;
    public static final int RESPONSE_SHORT = -98;
    public static final int UNEXPECTED_TAG_ID_IN_RESPONSE = -99;
    public static final int UNEXPECTED_TAG_INDEX = -100;
    public static final int READ_EOF = -101;
    public static final int READ_OK_SOFAR = -102;
    public static final int WRITE_DATA_MISSING = -103;
    public static final int WRITE_TOO_MUCH_DATA = -104;
    public static final int TRANSFER_CLOSED = -105;
    public static final int COULD_NOT_BUILD_REQUEST = -106;
    public static final int INVALID_OPTIONS = -107;
    public static final int UNEXPECTED_RESPONSE = -108;
    public static final int AFI_NOT_AVAILABLE = -109;
    public static final int DSFID_NOT_AVAILABLE = -110;
    public static final int TAG_RESPONSE_TOO_SHORT = -111;
    public static final int DEC_EXPECTED = -112 ;
    public static final int HEX_EXPECTED = -113;
    public static final int NOT_SAME_SECOR = -114;
    public static final int MIFARE_AUTHENTICATED = -115;
    public static final int NO_DATABLOCK = -116;
    public static final int KEYB_IS_READABLE = -117;
    public static final int OPERATION_NOT_EXECUTED = -118;
    public static final int BLOK_MODE_ERROR = -119;
    public static final int BLOCK_NOT_WRITABLE = -120;
    public static final int BLOCK_ACCESS_ERROR = -121;
    public static final int BLOCK_NOT_AUTHENTICATED = -122;
    public static final int ACCESS_KEY_BIT_NOT_WRITABLE = -123;
    public static final int USE_KEYA_FOR_AUTH = -124;
    public static final int USE_KEYB_FOR_AUTH = -125;
    public static final int KEY_NOT_CHANGEABLE = -126;
    public static final int BLOCK_TOO_HIGH = -127;
    public static final int AUTH_ERR = -128;
    public static final int NOKEY_SELECT = -129;
    public static final int CARD_NOT_SELECTED = -130;
    public static final int BLOCK_TO_READ_NONE = -131;
    public static final int NO_TAG = -132;
    public static final int TOO_MUCH_DATA = -133;
    public static final int CON_NOT_SATISFIED = -134;
    public static final int BLOCK_IS_SPECIAL = -135;
    public static final int READ_BEYOND_ANNOUNCED_SIZE = -136;
    public static final int BLOCK_ZERO_IS_RESERVED = -137;
    public static final int VALUE_BLOCK_BAD_FORMAT = -138;
    public static final int ISO15693_ONLY_FEATURE = -139;
    public static final int ISO14443_ONLY_FEATURE = -140;
    public static final int MIFARE_CLASSIC_ONLY_FEATURE = -141;
    public static final int BLOCK_MIGHT_BE_PROTECTED = -142;
    public static final int NO_SUCH_BLOCK = -143;
    public static final int COUNT_TOO_BIG = -144;
    public static final int UNKNOWN_MEM_SIZE = -145;
    public static final int MORE_THAN_2BLOCKS_MIGHT_NOT_WORK = -146;
    public static final int READWRITE_NOT_SUPPORTED = -147;
    public static final int UNEXPECTED_VICC_ID_IN_RESPONSE = -148;
    public static final int LOCKBLOCK_NOT_SUPPORTED = -150;
    public static final int INTERNAL_ERROR_SHOULD_NEVER_HAPPEN = -151;
    public static final int INVLD_BLOCK_MODE_COMBINATION = -152;
    public static final int INVLD_ACCESS_MODE_COMBINATION = -153;
    public static final int INVALID_SIZE = -154;
    public static final int BAD_PASSWORD_FORMAT = -155;
    public static final int RADIO_IS_OFF = -156;
    protected String _tagId;
    protected int _errCode = 0;
    protected int _errBlk = 0;
    protected String _errMsg;
    protected int _yapierr = 0;
    protected int _fab = 0;
    protected int _lab = 0;

    //--- (end of generated code: YRfidStatus definitions)


    public YRfidStatus()
    {
        //--- (generated code: YRfidStatus attributes initialization)
        //--- (end of generated code: YRfidStatus attributes initialization)
    }

    //--- (generated code: YRfidStatus implementation)

    /**
     * Returns RFID tag identifier related to the status.
     *
     * @return a string with the RFID tag identifier.
     */
    public String get_tagId()
    {
        return _tagId;
    }

    /**
     * Returns the detailled error code, or 0 if no error happened.
     *
     * @return a numeric error code
     */
    public int get_errorCode()
    {
        return _errCode;
    }

    /**
     * Returns the RFID tag memory block number where the error was encountered, or -1 if the
     * error is not specific to a memory block.
     *
     * @return an RFID tag block number
     */
    public int get_errorBlock()
    {
        return _errBlk;
    }

    /**
     * Returns a string describing precisely the RFID commande result.
     *
     * @return an error message string
     */
    public String get_errorMessage()
    {
        return _errMsg;
    }

    public int get_yapiError()
    {
        return _yapierr;
    }

    /**
     * Returns the block number of the first RFID tag memory block affected
     * by the operation. Depending on the type of operation and on the tag
     * memory granularity, this number may be smaller than the requested
     * memory block index.
     *
     * @return an RFID tag block number
     */
    public int get_firstAffectedBlock()
    {
        return _fab;
    }

    /**
     * Returns the block number of the last RFID tag memory block affected
     * by the operation. Depending on the type of operation and on the tag
     * memory granularity, this number may be bigger than the requested
     * memory block index.
     *
     * @return an RFID tag block number
     */
    public int get_lastAffectedBlock()
    {
        return _lab;
    }

    public void imm_init(String tagId,int errCode,int errBlk,int fab,int lab)
    {
        String errMsg;
        if (errCode == 0) {
            _yapierr = YAPI.SUCCESS;
            errMsg = "Success (no error)";
        } else {
            if (errCode < 0) {
                if (errCode > -50) {
                    _yapierr = errCode;
                    errMsg = String.format(Locale.US, "YoctoLib error %d",errCode);
                } else {
                    _yapierr = YAPI.RFID_HARD_ERROR;
                    errMsg = String.format(Locale.US, "Non-recoverable RFID error %d",errCode);
                }
            } else {
                if (errCode > 1000) {
                    _yapierr = YAPI.RFID_SOFT_ERROR;
                    errMsg = String.format(Locale.US, "Recoverable RFID error %d",errCode);
                } else {
                    _yapierr = YAPI.RFID_HARD_ERROR;
                    errMsg = String.format(Locale.US, "Non-recoverable RFID error %d",errCode);
                }
            }
            if (errCode == TAG_NOTFOUND) {
                errMsg = "Tag not found";
            }
            if (errCode == TAG_JUSTLEFT) {
                errMsg = "Tag left during operation";
            }
            if (errCode == TAG_LEFT) {
                errMsg = "Tag not here anymore";
            }
            if (errCode == READER_BUSY) {
                errMsg = "Reader is busy";
            }
            if (errCode == INVALID_CMD_ARGUMENTS) {
                errMsg = "Invalid command arguments";
            }
            if (errCode == UNKNOWN_CAPABILITIES) {
                errMsg = "Unknown capabilities";
            }
            if (errCode == MEMORY_NOT_SUPPORTED) {
                errMsg = "Memory no present";
            }
            if (errCode == INVALID_BLOCK_INDEX) {
                errMsg = "Invalid block index";
            }
            if (errCode == MEM_SPACE_UNVERRUN_ATTEMPT) {
                errMsg = "Tag memory space overrun attempt";
            }
            if (errCode == COMMAND_NOT_SUPPORTED) {
                errMsg = "The command is not supported";
            }
            if (errCode == COMMAND_NOT_RECOGNIZED) {
                errMsg = "The command is not recognized";
            }
            if (errCode == COMMAND_OPTION_NOT_RECOGNIZED) {
                errMsg = "The command option is not supported.";
            }
            if (errCode == COMMAND_CANNOT_BE_PROCESSED_IN_TIME) {
                errMsg = "The command cannot be processed in time";
            }
            if (errCode == UNDOCUMENTED_ERROR) {
                errMsg = "Error with no information given";
            }
            if (errCode == BLOCK_NOT_AVAILABLE) {
                errMsg = "Block is not available";
            }
            if (errCode == BLOCK_ALREADY_LOCKED) {
                errMsg = "Block / byte is already locked and thus cannot be locked again.";
            }
            if (errCode == BLOCK_LOCKED) {
                errMsg = "Block / byte is locked and its content cannot be changed";
            }
            if (errCode == BLOCK_NOT_SUCESSFULLY_PROGRAMMED) {
                errMsg = "Block was not successfully programmed";
            }
            if (errCode == BLOCK_NOT_SUCESSFULLY_LOCKED) {
                errMsg = "Block was not successfully locked";
            }
            if (errCode == BLOCK_IS_PROTECTED) {
                errMsg = "Block is protected";
            }
            if (errCode == CRYPTOGRAPHIC_ERROR) {
                errMsg = "Generic cryptographic error";
            }
            if (errCode == BROWNOUT_DETECTED) {
                errMsg = "BrownOut detected (BOD)";
            }
            if (errCode == BUFFER_OVERFLOW) {
                errMsg = "Buffer Overflow (BOF)";
            }
            if (errCode == CRC_ERROR) {
                errMsg = "Communication CRC Error (CCE)";
            }
            if (errCode == COLLISION_DETECTED) {
                errMsg = "Collision Detected (CLD/CDT)";
            }
            if (errCode == COMMAND_RECEIVE_TIMEOUT) {
                errMsg = "Command Receive Timeout (CRT)";
            }
            if (errCode == DID_NOT_SLEEP) {
                errMsg = "Did Not Sleep (DNS)";
            }
            if (errCode == ERROR_DECIMAL_EXPECTED) {
                errMsg = "Error Decimal Expected (EDX)";
            }
            if (errCode == HARDWARE_FAILURE) {
                errMsg = "Error Hardware Failure (EHF)";
            }
            if (errCode == ERROR_HEX_EXPECTED) {
                errMsg = "Error Hex Expected (EHX)";
            }
            if (errCode == FIFO_LENGTH_ERROR) {
                errMsg = "FIFO length error (FLE)";
            }
            if (errCode == FRAMING_ERROR) {
                errMsg = "Framing error (FER)";
            }
            if (errCode == NOT_IN_CNR_MODE) {
                errMsg = "Not in CNR Mode (NCM)";
            }
            if (errCode == NUMBER_OU_OF_RANGE) {
                errMsg = "Number Out of Range (NOR)";
            }
            if (errCode == NOT_SUPPORTED) {
                errMsg = "Not Supported (NOS)";
            }
            if (errCode == NO_RF_FIELD_ACTIVE) {
                errMsg = "No RF field active (NRF)";
            }
            if (errCode == READ_DATA_LENGTH_ERROR) {
                errMsg = "Read data length error (RDL)";
            }
            if (errCode == WATCHDOG_RESET) {
                errMsg = "Watchdog reset (SRT)";
            }
            if (errCode == TAG_COMMUNICATION_ERROR) {
                errMsg = "Tag Communication Error (TCE)";
            }
            if (errCode == TAG_NOT_RESPONDING) {
                errMsg = "Tag Not Responding (TNR)";
            }
            if (errCode == TIMEOUT_ERROR) {
                errMsg = "TimeOut Error (TOE)";
            }
            if (errCode == UNKNOW_COMMAND) {
                errMsg = "Unknown Command (UCO)";
            }
            if (errCode == UNKNOW_ERROR) {
                errMsg = "Unknown error (UER)";
            }
            if (errCode == UNKNOW_PARAMETER) {
                errMsg = "Unknown Parameter (UPA)";
            }
            if (errCode == UART_RECEIVE_ERROR) {
                errMsg = "UART Receive Error (URE)";
            }
            if (errCode == WRONG_DATA_LENGTH) {
                errMsg = "Wrong Data Length (WDL)";
            }
            if (errCode == WRONG_MODE) {
                errMsg = "Wrong Mode (WMO)";
            }
            if (errCode == UNKNOWN_DWARFxx_ERROR_CODE) {
                errMsg = "Unknown DWARF15 error code";
            }
            if (errCode == UNEXPECTED_TAG_ID_IN_RESPONSE) {
                errMsg = "Unexpected Tag id in response";
            }
            if (errCode == UNEXPECTED_TAG_INDEX) {
                errMsg = "internal error : unexpected TAG index";
            }
            if (errCode == TRANSFER_CLOSED) {
                errMsg = "transfer closed";
            }
            if (errCode == WRITE_DATA_MISSING) {
                errMsg = "Missing write data";
            }
            if (errCode == WRITE_TOO_MUCH_DATA) {
                errMsg = "Attempt to write too much data";
            }
            if (errCode == COULD_NOT_BUILD_REQUEST) {
                errMsg = "Could not not request";
            }
            if (errCode == INVALID_OPTIONS) {
                errMsg = "Invalid transfer options";
            }
            if (errCode == UNEXPECTED_RESPONSE) {
                errMsg = "Unexpected Tag response";
            }
            if (errCode == AFI_NOT_AVAILABLE) {
                errMsg = "AFI not available";
            }
            if (errCode == DSFID_NOT_AVAILABLE) {
                errMsg = "DSFID not available";
            }
            if (errCode == TAG_RESPONSE_TOO_SHORT) {
                errMsg = "Tag's response too short";
            }
            if (errCode == DEC_EXPECTED) {
                errMsg = "Error Decimal value Expected, or is missing";
            }
            if (errCode == HEX_EXPECTED) {
                errMsg = "Error Hexadecimal value Expected, or is missing";
            }
            if (errCode == NOT_SAME_SECOR) {
                errMsg = "Input and Output block are not in the same Sector";
            }
            if (errCode == MIFARE_AUTHENTICATED) {
                errMsg = "No chip with MIFARE Classic technology Authenticated";
            }
            if (errCode == NO_DATABLOCK) {
                errMsg = "No Data Block";
            }
            if (errCode == KEYB_IS_READABLE) {
                errMsg = "Key B is Readable";
            }
            if (errCode == OPERATION_NOT_EXECUTED) {
                errMsg = "Operation Not Executed, would have caused an overflow";
            }
            if (errCode == BLOK_MODE_ERROR) {
                errMsg = "Block has not been initialized as a 'value block'";
            }
            if (errCode == BLOCK_NOT_WRITABLE) {
                errMsg = "Block Not Writable";
            }
            if (errCode == BLOCK_ACCESS_ERROR) {
                errMsg = "Block Access Error";
            }
            if (errCode == BLOCK_NOT_AUTHENTICATED) {
                errMsg = "Block Not Authenticated";
            }
            if (errCode == ACCESS_KEY_BIT_NOT_WRITABLE) {
                errMsg = "Access bits or Keys not Writable";
            }
            if (errCode == USE_KEYA_FOR_AUTH) {
                errMsg = "Use Key B for authentication";
            }
            if (errCode == USE_KEYB_FOR_AUTH) {
                errMsg = "Use Key A for authentication";
            }
            if (errCode == KEY_NOT_CHANGEABLE) {
                errMsg = "Key(s) not changeable";
            }
            if (errCode == BLOCK_TOO_HIGH) {
                errMsg = "Block index is too high";
            }
            if (errCode == AUTH_ERR) {
                errMsg = "Authentication Error (i.e. wrong key)";
            }
            if (errCode == NOKEY_SELECT) {
                errMsg = "No Key Select, select a temporary or a static key";
            }
            if (errCode == CARD_NOT_SELECTED) {
                errMsg = " Card is Not Selected";
            }
            if (errCode == BLOCK_TO_READ_NONE) {
                errMsg = "Number of Blocks to Read is 0";
            }
            if (errCode == NO_TAG) {
                errMsg = "No Tag detected";
            }
            if (errCode == TOO_MUCH_DATA) {
                errMsg = "Too Much Data (i.e. Uart input buffer overflow)";
            }
            if (errCode == CON_NOT_SATISFIED) {
                errMsg = "Conditions Not Satisfied";
            }
            if (errCode == BLOCK_IS_SPECIAL) {
                errMsg = "Bad parameter: block is a special block";
            }
            if (errCode == READ_BEYOND_ANNOUNCED_SIZE) {
                errMsg = "Attempt to read more than announced size.";
            }
            if (errCode == BLOCK_ZERO_IS_RESERVED) {
                errMsg = "Block 0 is reserved and cannot be used";
            }
            if (errCode == VALUE_BLOCK_BAD_FORMAT) {
                errMsg = "One value block is not properly initialized";
            }
            if (errCode == ISO15693_ONLY_FEATURE) {
                errMsg = "Feature available on ISO 15693 only";
            }
            if (errCode == ISO14443_ONLY_FEATURE) {
                errMsg = "Feature available on ISO 14443 only";
            }
            if (errCode == MIFARE_CLASSIC_ONLY_FEATURE) {
                errMsg = "Feature available on ISO 14443 MIFARE Classic only";
            }
            if (errCode == BLOCK_MIGHT_BE_PROTECTED) {
                errMsg = "Block might be protected";
            }
            if (errCode == NO_SUCH_BLOCK) {
                errMsg = "No such block";
            }
            if (errCode == COUNT_TOO_BIG) {
                errMsg = "Count parameter is too large";
            }
            if (errCode == UNKNOWN_MEM_SIZE) {
                errMsg = "Tag memory size is unknown";
            }
            if (errCode == MORE_THAN_2BLOCKS_MIGHT_NOT_WORK) {
                errMsg = "Writing more than two blocks at once might not be supported by this tag";
            }
            if (errCode == READWRITE_NOT_SUPPORTED) {
                errMsg = "Read/write operation not supported for this tag";
            }
            if (errCode == UNEXPECTED_VICC_ID_IN_RESPONSE) {
                errMsg = "Unexpected VICC ID in response";
            }
            if (errCode == LOCKBLOCK_NOT_SUPPORTED) {
                errMsg = "This tag does not support the Lock block function";
            }
            if (errCode == INTERNAL_ERROR_SHOULD_NEVER_HAPPEN) {
                errMsg = "Yoctopuce RFID code ran into an unexpected state, please contact support";
            }
            if (errCode == INVLD_BLOCK_MODE_COMBINATION) {
                errMsg = "Invalid combination of block mode options";
            }
            if (errCode == INVLD_ACCESS_MODE_COMBINATION) {
                errMsg = "Invalid combination of access mode options";
            }
            if (errCode == INVALID_SIZE) {
                errMsg = "Invalid data size parameter";
            }
            if (errCode == BAD_PASSWORD_FORMAT) {
                errMsg = "Bad password format or type";
            }
            if (errCode == RADIO_IS_OFF) {
                errMsg = "Radio is OFF (refreshRate=0).";
            }
            if (errBlk >= 0) {
                errMsg = String.format(Locale.US, "%s (block %d)", errMsg,errBlk);
            }
        }
        _tagId = tagId;
        _errCode = errCode;
        _errBlk = errBlk;
        _errMsg = errMsg;
        _fab = fab;
        _lab = lab;
    }

    //--- (end of generated code: YRfidStatus implementation)
}

