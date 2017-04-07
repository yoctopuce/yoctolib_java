/*********************************************************************
 * $Id: YSms.java 27108 2017-04-06 22:18:22Z seb $
 *
 * Implements FindSms(), the high-level API for Sms functions
 *
 * - - - - - - - - - License information: - - - - - - - - -
 *
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 * Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 * non-exclusive license to use, modify, copy and integrate this
 * file into your software for the sole purpose of interfacing
 * with Yoctopuce products.
 *
 * You may reproduce and distribute copies of this file in
 * source or object form, as long as the sole purpose of this
 * code is to interface with Yoctopuce products. You must retain
 * this notice in the distributed source file.
 *
 * You should refer to Yoctopuce General Terms and Conditions
 * for additional information regarding your rights and
 * obligations.
 *
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING
 * WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 * EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 * COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 * SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 * LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 * CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 * BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 * WARRANTY, OR OTHERWISE.
 *********************************************************************/

package com.yoctopuce.YoctoAPI;
import java.util.ArrayList;
import java.util.Locale;

//--- (generated code: YSms return codes)
//--- (end of generated code: YSms return codes)
//--- (generated code: YSms class start)
/**
 * YSms Class: SMS message sent or received
 *
 *
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YSms
{
//--- (end of generated code: YSms class start)
    //--- (generated code: YSms definitions)
    protected YMessageBox _mbox;
    protected int _slot = 0;
    protected boolean _deliv;
    protected String _smsc;
    protected int _mref = 0;
    protected String _orig;
    protected String _dest;
    protected int _pid = 0;
    protected int _alphab = 0;
    protected int _mclass = 0;
    protected String _stamp;
    protected byte[] _udh;
    protected byte[] _udata;
    protected int _npdu = 0;
    protected byte[] _pdu;
    protected ArrayList<YSms> _parts = new ArrayList<>();
    protected String _aggSig;
    protected int _aggIdx = 0;
    protected int _aggCnt = 0;

    //--- (end of generated code: YSms definitions)


    /**
     * @param mbox : message box
     */
    protected YSms(YMessageBox mbox)
    {
        _mbox = mbox;
        //--- (generated code: YSms attributes initialization)
        //--- (end of generated code: YSms attributes initialization)
    }


    //--- (generated code: YSms implementation)

    public int get_slot()
    {
        return _slot;
    }

    public String get_smsc()
    {
        return _smsc;
    }

    public int get_msgRef()
    {
        return _mref;
    }

    public String get_sender()
    {
        return _orig;
    }

    public String get_recipient()
    {
        return _dest;
    }

    public int get_protocolId()
    {
        return _pid;
    }

    public boolean isReceived()
    {
        return _deliv;
    }

    public int get_alphabet()
    {
        return _alphab;
    }

    public int get_msgClass()
    {
        if (((_mclass) & (16)) == 0) {
            return -1;
        }
        return ((_mclass) & (3));
    }

    public int get_dcs()
    {
        return ((_mclass) | ((((_alphab) << (2)))));
    }

    public String get_timestamp()
    {
        return _stamp;
    }

    public byte[] get_userDataHeader()
    {
        return _udh;
    }

    public byte[] get_userData()
    {
        return _udata;
    }

    public String get_textData()
    {
        byte[] isolatin;
        int isosize;
        int i;
        
        if (_alphab == 0) {
            // using GSM standard 7-bit alphabet
            return _mbox.gsm2str(_udata);
        }
        if (_alphab == 2) {
            // using UCS-2 alphabet
            isosize = (((_udata).length) >> (1));
            isolatin = new byte[isosize];
            i = 0;
            while (i < isosize) {
                isolatin[i] = (byte)((_udata[2*i+1] & 0xff) & 0xff);
                i = i + 1;
            }
            return new String(isolatin);
        }
        
        // default: convert 8 bit to string as-is
        return new String(_udata);
    }

    public ArrayList<Integer> get_unicodeData()
    {
        ArrayList<Integer> res = new ArrayList<>();
        int unisize;
        int unival;
        int i;
        
        if (_alphab == 0) {
            // using GSM standard 7-bit alphabet
            return _mbox.gsm2unicode(_udata);
        }
        if (_alphab == 2) {
            // using UCS-2 alphabet
            unisize = (((_udata).length) >> (1));
            res.clear();
            i = 0;
            while (i < unisize) {
                unival = 256*(_udata[2*i] & 0xff)+(_udata[2*i+1] & 0xff);
                res.add(unival);
                i = i + 1;
            }
        } else {
            // return straight 8-bit values
            unisize = (_udata).length;
            res.clear();
            i = 0;
            while (i < unisize) {
                res.add((_udata[i] & 0xff)+0);
                i = i + 1;
            }
        }
        return res;
    }

    public int get_partCount()
    {
        if (_npdu == 0) {
            generatePdu();
        }
        return _npdu;
    }

    public byte[] get_pdu()
    {
        if (_npdu == 0) {
            generatePdu();
        }
        return _pdu;
    }

    public ArrayList<YSms> get_parts()
    {
        if (_npdu == 0) {
            generatePdu();
        }
        return _parts;
    }

    public String get_concatSignature()
    {
        if (_npdu == 0) {
            generatePdu();
        }
        return _aggSig;
    }

    public int get_concatIndex()
    {
        if (_npdu == 0) {
            generatePdu();
        }
        return _aggIdx;
    }

    public int get_concatCount()
    {
        if (_npdu == 0) {
            generatePdu();
        }
        return _aggCnt;
    }

    public int set_slot(int val)
    {
        _slot = val;
        return YAPI.SUCCESS;
    }

    public int set_received(boolean val)
    {
        _deliv = val;
        return YAPI.SUCCESS;
    }

    public int set_smsc(String val)
    {
        _smsc = val;
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_msgRef(int val)
    {
        _mref = val;
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_sender(String val)
    {
        _orig = val;
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_recipient(String val)
    {
        _dest = val;
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_protocolId(int val)
    {
        _pid = val;
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_alphabet(int val)
    {
        _alphab = val;
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_msgClass(int val)
    {
        if (val == -1) {
            _mclass = 0;
        } else {
            _mclass = 16+val;
        }
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_dcs(int val)
    {
        _alphab = (((((val) >> (2)))) & (3));
        _mclass = ((val) & (16+3));
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_timestamp(String val)
    {
        _stamp = val;
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int set_userDataHeader(byte[] val)
    {
        _udh = val;
        _npdu = 0;
        parseUserDataHeader();
        return YAPI.SUCCESS;
    }

    public int set_userData(byte[] val)
    {
        _udata = val;
        _npdu = 0;
        return YAPI.SUCCESS;
    }

    public int convertToUnicode()
    {
        ArrayList<Integer> ucs2 = new ArrayList<>();
        int udatalen;
        int i;
        int uni;
        
        if (_alphab == 2) {
            return YAPI.SUCCESS;
        }
        if (_alphab == 0) {
            ucs2 = _mbox.gsm2unicode(_udata);
        } else {
            udatalen = (_udata).length;
            ucs2.clear();
            i = 0;
            while (i < udatalen) {
                uni = (_udata[i] & 0xff);
                ucs2.add(uni);
                i = i + 1;
            }
        }
        _alphab = 2;
        _udata = new byte[0];
        addUnicodeData(ucs2);
        
        return YAPI.SUCCESS;
    }

    public int addText(String val)
    {
        byte[] udata;
        int udatalen;
        byte[] newdata;
        int newdatalen;
        int i;
        
        if ((val).length() == 0) {
            return YAPI.SUCCESS;
        }
        
        if (_alphab == 0) {
            // Try to append using GSM 7-bit alphabet
            newdata = _mbox.str2gsm(val);
            newdatalen = (newdata).length;
            if (newdatalen == 0) {
                // 7-bit not possible, switch to unicode
                convertToUnicode();
                newdata = (val).getBytes();
                newdatalen = (newdata).length;
            }
        } else {
            newdata = (val).getBytes();
            newdatalen = (newdata).length;
        }
        udatalen = (_udata).length;
        if (_alphab == 2) {
            // Append in unicode directly
            udata = new byte[udatalen + 2*newdatalen];
            i = 0;
            while (i < udatalen) {
                udata[i] = (byte)((_udata[i] & 0xff) & 0xff);
                i = i + 1;
            }
            i = 0;
            while (i < newdatalen) {
                udata[udatalen+1] = (byte)((newdata[i] & 0xff) & 0xff);
                udatalen = udatalen + 2;
                i = i + 1;
            }
        } else {
            // Append binary buffers
            udata = new byte[udatalen+newdatalen];
            i = 0;
            while (i < udatalen) {
                udata[i] = (byte)((_udata[i] & 0xff) & 0xff);
                i = i + 1;
            }
            i = 0;
            while (i < newdatalen) {
                udata[udatalen] = (byte)((newdata[i] & 0xff) & 0xff);
                udatalen = udatalen + 1;
                i = i + 1;
            }
        }
        
        return set_userData(udata);
    }

    public int addUnicodeData(ArrayList<Integer> val)
    {
        int arrlen;
        int newdatalen;
        int i;
        int uni;
        byte[] udata;
        int udatalen;
        int surrogate;
        
        if (_alphab != 2) {
            convertToUnicode();
        }
        // compute number of 16-bit code units
        arrlen = val.size();
        newdatalen = arrlen;
        i = 0;
        while (i < arrlen) {
            uni = val.get(i).intValue();
            if (uni > 65535) {
                newdatalen = newdatalen + 1;
            }
            i = i + 1;
        }
        // now build utf-16 buffer
        udatalen = (_udata).length;
        udata = new byte[udatalen+2*newdatalen];
        i = 0;
        while (i < udatalen) {
            udata[i] = (byte)((_udata[i] & 0xff) & 0xff);
            i = i + 1;
        }
        i = 0;
        while (i < arrlen) {
            uni = val.get(i).intValue();
            if (uni >= 65536) {
                surrogate = uni - 65536;
                uni = (((((surrogate) >> (10))) & (1023))) + 55296;
                udata[udatalen] = (byte)(((uni) >> (8)) & 0xff);
                udata[udatalen+1] = (byte)(((uni) & (255)) & 0xff);
                udatalen = udatalen + 2;
                uni = (((surrogate) & (1023))) + 56320;
            }
            udata[udatalen] = (byte)(((uni) >> (8)) & 0xff);
            udata[udatalen+1] = (byte)(((uni) & (255)) & 0xff);
            udatalen = udatalen + 2;
            i = i + 1;
        }
        
        return set_userData(udata);
    }

    public int set_pdu(byte[] pdu)
    {
        _pdu = pdu;
        _npdu = 1;
        return parsePdu(pdu);
    }

    public int set_parts(ArrayList<YSms> parts)
    {
        ArrayList<YSms> sorted = new ArrayList<>();
        int partno;
        int initpartno;
        int i;
        int retcode;
        int totsize;
        YSms subsms;
        byte[] subdata;
        byte[] res;
        _npdu = parts.size();
        if (_npdu == 0) {
            return YAPI.INVALID_ARGUMENT;
        }
        
        sorted.clear();
        partno = 0;
        while (partno < _npdu) {
            initpartno = partno;
            i = 0;
            while (i < _npdu) {
                subsms = parts.get(i);
                if (subsms.get_concatIndex() == partno) {
                    sorted.add(subsms);
                    partno = partno + 1;
                }
                i = i + 1;
            }
            if (initpartno == partno) {
                partno = partno + 1;
            }
        }
        _parts = sorted;
        _npdu = sorted.size();
        // inherit header fields from first part
        subsms = _parts.get(0);
        retcode = parsePdu(subsms.get_pdu());
        if (retcode != YAPI.SUCCESS) {
            return retcode;
        }
        // concatenate user data from all parts
        totsize = 0;
        partno = 0;
        while (partno < _parts.size()) {
            subsms = _parts.get(partno);
            subdata = subsms.get_userData();
            totsize = totsize + (subdata).length;
            partno = partno + 1;
        }
        res = new byte[totsize];
        totsize = 0;
        partno = 0;
        while (partno < _parts.size()) {
            subsms = _parts.get(partno);
            subdata = subsms.get_userData();
            i = 0;
            while (i < (subdata).length) {
                res[totsize] = (byte)((subdata[i] & 0xff) & 0xff);
                totsize = totsize + 1;
                i = i + 1;
            }
            partno = partno + 1;
        }
        _udata = res;
        return YAPI.SUCCESS;
    }

    public byte[] encodeAddress(String addr)
    {
        byte[] bytes;
        int srclen;
        int numlen;
        int i;
        int val;
        int digit;
        byte[] res;
        bytes = (addr).getBytes();
        srclen = (bytes).length;
        numlen = 0;
        i = 0;
        while (i < srclen) {
            val = (bytes[i] & 0xff);
            if ((val >= 48) && (val < 58)) {
                numlen = numlen + 1;
            }
            i = i + 1;
        }
        if (numlen == 0) {
            res = new byte[1];
            res[0] = 0;
            return res;
        }
        res = new byte[2+((numlen+1) >> (1))];
        res[0] = (byte)(numlen & 0xff);
        if ((bytes[0] & 0xff) == 43) {
            res[1] = (byte)(145 & 0xff);
        } else {
            res[1] = (byte)(129 & 0xff);
        }
        numlen = 4;
        digit = 0;
        i = 0;
        while (i < srclen) {
            val = (bytes[i] & 0xff);
            if ((val >= 48) && (val < 58)) {
                if (((numlen) & (1)) == 0) {
                    digit = val - 48;
                } else {
                    res[((numlen) >> (1))] = (byte)(digit + 16*(val-48) & 0xff);
                }
                numlen = numlen + 1;
            }
            i = i + 1;
        }
        // pad with F if needed
        if (((numlen) & (1)) != 0) {
            res[((numlen) >> (1))] = (byte)(digit + 240 & 0xff);
        }
        return res;
    }

    public String decodeAddress(byte[] addr,int ofs,int siz)
    {
        int addrType;
        byte[] gsm7;
        String res;
        int i;
        int rpos;
        int carry;
        int nbits;
        int byt;
        if (siz == 0) {
            return "";
        }
        res = "";
        addrType = (((addr[ofs] & 0xff)) & (112));
        if (addrType == 80) {
            // alphanumeric number
            siz = ((4*siz) / (7));
            gsm7 = new byte[siz];
            rpos = 1;
            carry = 0;
            nbits = 0;
            i = 0;
            while (i < siz) {
                if (nbits == 7) {
                    gsm7[i] = (byte)(carry & 0xff);
                    carry = 0;
                    nbits = 0;
                } else {
                    byt = (addr[ofs+rpos] & 0xff);
                    rpos = rpos + 1;
                    gsm7[i] = (byte)(((carry) | ((((((byt) << (nbits)))) & (127)))) & 0xff);
                    carry = ((byt) >> ((7 - nbits)));
                    nbits = nbits + 1;
                }
                i = i + 1;
            }
            return _mbox.gsm2str(gsm7);
        } else {
            // standard phone number
            if (addrType == 16) {
                res = "+";
            }
            siz = (((siz+1)) >> (1));
            i = 0;
            while (i < siz) {
                byt = (addr[ofs+i+1] & 0xff);
                res = String.format(Locale.US, "%s%x%x", res, ((byt) & (15)),((byt) >> (4)));
                i = i + 1;
            }
            // remove padding digit if needed
            if ((((addr[ofs+siz] & 0xff)) >> (4)) == 15) {
                res = (res).substring(0, (res).length()-1);
            }
            return res;
        }
    }

    public byte[] encodeTimeStamp(String exp)
    {
        int explen;
        int i;
        byte[] res;
        int n;
        byte[] expasc;
        int v1;
        int v2;
        explen = (exp).length();
        if (explen == 0) {
            res = new byte[0];
            return res;
        }
        if ((exp).substring(0, 1).equals("+")) {
            n = YAPIContext._atoi((exp).substring(1, 1 + explen-1));
            res = new byte[1];
            if (n > 30*86400) {
                n = 192+(((n+6*86400)) / ((7*86400)));
            } else {
                if (n > 86400) {
                    n = 166+(((n+86399)) / (86400));
                } else {
                    if (n > 43200) {
                        n = 143+(((n-43200+1799)) / (1800));
                    } else {
                        n = -1+(((n+299)) / (300));
                    }
                }
            }
            if (n < 0) {
                n = 0;
            }
            res[0] = (byte)(n & 0xff);
            return res;
        }
        if ((exp).substring(4, 4 + 1).equals("-") || (exp).substring(4, 4 + 1).equals("/")) {
            // ignore century
            exp = (exp).substring( 2,  2 + explen-2);
            explen = (exp).length();
        }
        expasc = (exp).getBytes();
        res = new byte[7];
        n = 0;
        i = 0;
        while ((i+1 < explen) && (n < 7)) {
            v1 = (expasc[i] & 0xff);
            if ((v1 >= 48) && (v1 < 58)) {
                v2 = (expasc[i+1] & 0xff);
                if ((v2 >= 48) && (v2 < 58)) {
                    v1 = v1 - 48;
                    v2 = v2 - 48;
                    res[n] = (byte)((((v2) << (4))) + v1 & 0xff);
                    n = n + 1;
                    i = i + 1;
                }
            }
            i = i + 1;
        }
        while (n < 7) {
            res[n] = 0;
            n = n + 1;
        }
        if (i+2 < explen) {
            // convert for timezone in cleartext ISO format +/-nn:nn
            v1 = (expasc[i-3] & 0xff);
            v2 = (expasc[i] & 0xff);
            if (((v1 == 43) || (v1 == 45)) && (v2 == 58)) {
                v1 = (expasc[i+1] & 0xff);
                v2 = (expasc[i+2] & 0xff);
                if ((v1 >= 48) && (v1 < 58) && (v1 >= 48) && (v1 < 58)) {
                    v1 = (((10*(v1 - 48)+(v2 - 48))) / (15));
                    n = n - 1;
                    v2 = 4 * (res[n] & 0xff) + v1;
                    if ((expasc[i-3] & 0xff) == 45) {
                        v2 += 128;
                    }
                    res[n] = (byte)(v2 & 0xff);
                }
            }
        }
        return res;
    }

    public String decodeTimeStamp(byte[] exp,int ofs,int siz)
    {
        int n;
        String res;
        int i;
        int byt;
        String sign;
        String hh;
        String ss;
        if (siz < 1) {
            return "";
        }
        if (siz == 1) {
            n = (exp[ofs] & 0xff);
            if (n < 144) {
                n = n * 300;
            } else {
                if (n < 168) {
                    n = (n-143) * 1800;
                } else {
                    if (n < 197) {
                        n = (n-166) * 86400;
                    } else {
                        n = (n-192) * 7 * 86400;
                    }
                }
            }
            return String.format(Locale.US, "+%d",n);
        }
        res = "20";
        i = 0;
        while ((i < siz) && (i < 6)) {
            byt = (exp[ofs+i] & 0xff);
            res = String.format(Locale.US, "%s%x%x", res, ((byt) & (15)),((byt) >> (4)));
            if (i < 3) {
                if (i < 2) {
                    res = String.format(Locale.US, "%s-",res);
                } else {
                    res = String.format(Locale.US, "%s ",res);
                }
            } else {
                if (i < 5) {
                    res = String.format(Locale.US, "%s:",res);
                }
            }
            i = i + 1;
        }
        if (siz == 7) {
            byt = (exp[ofs+i] & 0xff);
            sign = "+";
            if (((byt) & (8)) != 0) {
                byt = byt - 8;
                sign = "-";
            }
            byt = (10*(((byt) & (15)))) + (((byt) >> (4)));
            hh = String.format(Locale.US, "%d",((byt) >> (2)));
            ss = String.format(Locale.US, "%d",15*(((byt) & (3))));
            if ((hh).length()<2) {
                hh = String.format(Locale.US, "0%s",hh);
            }
            if ((ss).length()<2) {
                ss = String.format(Locale.US, "0%s",ss);
            }
            res = String.format(Locale.US, "%s%s%s:%s", res, sign, hh,ss);
        }
        return res;
    }

    public int udataSize()
    {
        int res;
        int udhsize;
        udhsize = (_udh).length;
        res = (_udata).length;
        if (_alphab == 0) {
            if (udhsize > 0) {
                res = res + (((8 + 8*udhsize + 6)) / (7));
            }
            res = (((res * 7 + 7)) / (8));
        } else {
            if (udhsize > 0) {
                res = res + 1 + udhsize;
            }
        }
        return res;
    }

    public byte[] encodeUserData()
    {
        int udsize;
        int udlen;
        int udhsize;
        int udhlen;
        byte[] res;
        int i;
        int wpos;
        int carry;
        int nbits;
        int thisb;
        // nbits = number of bits in carry
        udsize = udataSize();
        udhsize = (_udh).length;
        udlen = (_udata).length;
        res = new byte[1+udsize];
        udhlen = 0;
        nbits = 0;
        carry = 0;
        // 1. Encode UDL
        if (_alphab == 0) {
            // 7-bit encoding
            if (udhsize > 0) {
                udhlen = (((8 + 8*udhsize + 6)) / (7));
                nbits = 7*udhlen - 8 - 8*udhsize;
            }
            res[0] = (byte)(udhlen+udlen & 0xff);
        } else {
            // 8-bit encoding
            res[0] = (byte)(udsize & 0xff);
        }
        // 2. Encode UDHL and UDL
        wpos = 1;
        if (udhsize > 0) {
            res[wpos] = (byte)(udhsize & 0xff);
            wpos = wpos + 1;
            i = 0;
            while (i < udhsize) {
                res[wpos] = (byte)((_udh[i] & 0xff) & 0xff);
                wpos = wpos + 1;
                i = i + 1;
            }
        }
        // 3. Encode UD
        if (_alphab == 0) {
            // 7-bit encoding
            i = 0;
            while (i < udlen) {
                if (nbits == 0) {
                    carry = (_udata[i] & 0xff);
                    nbits = 7;
                } else {
                    thisb = (_udata[i] & 0xff);
                    res[wpos] = (byte)(((carry) | ((((((thisb) << (nbits)))) & (255)))) & 0xff);
                    wpos = wpos + 1;
                    nbits = nbits - 1;
                    carry = ((thisb) >> ((7 - nbits)));
                }
                i = i + 1;
            }
            if (nbits > 0) {
                res[wpos] = (byte)(carry & 0xff);
            }
        } else {
            // 8-bit encoding
            i = 0;
            while (i < udlen) {
                res[wpos] = (byte)((_udata[i] & 0xff) & 0xff);
                wpos = wpos + 1;
                i = i + 1;
            }
        }
        return res;
    }

    public int generateParts()
    {
        int udhsize;
        int udlen;
        int mss;
        int partno;
        int partlen;
        byte[] newud;
        byte[] newudh;
        YSms newpdu;
        int i;
        int wpos;
        udhsize = (_udh).length;
        udlen = (_udata).length;
        mss = 140 - 1 - 5 - udhsize;
        if (_alphab == 0) {
            mss = (((mss * 8 - 6)) / (7));
        }
        _npdu = (((udlen+mss-1)) / (mss));
        _parts.clear();
        partno = 0;
        wpos = 0;
        while (wpos < udlen) {
            partno = partno + 1;
            newudh = new byte[5+udhsize];
            newudh[0] = 0;           // IEI: concatenated message
            newudh[1] = (byte)(3 & 0xff);           // IEDL: 3 bytes
            newudh[2] = (byte)(_mref & 0xff);
            newudh[3] = (byte)(_npdu & 0xff);
            newudh[4] = (byte)(partno & 0xff);
            i = 0;
            while (i < udhsize) {
                newudh[5+i] = (byte)((_udh[i] & 0xff) & 0xff);
                i = i + 1;
            }
            if (wpos+mss < udlen) {
                partlen = mss;
            } else {
                partlen = udlen-wpos;
            }
            newud = new byte[partlen];
            i = 0;
            while (i < partlen) {
                newud[i] = (byte)((_udata[wpos] & 0xff) & 0xff);
                wpos = wpos + 1;
                i = i + 1;
            }
            newpdu = new YSms(_mbox);
            newpdu.set_received(isReceived());
            newpdu.set_smsc(get_smsc());
            newpdu.set_msgRef(get_msgRef());
            newpdu.set_sender(get_sender());
            newpdu.set_recipient(get_recipient());
            newpdu.set_protocolId(get_protocolId());
            newpdu.set_dcs(get_dcs());
            newpdu.set_timestamp(get_timestamp());
            newpdu.set_userDataHeader(newudh);
            newpdu.set_userData(newud);
            _parts.add(newpdu);
        }
        return YAPI.SUCCESS;
    }

    public int generatePdu()
    {
        byte[] sca;
        byte[] hdr;
        byte[] addr;
        byte[] stamp;
        byte[] udata;
        int pdutyp;
        int pdulen;
        int i;
        // Determine if the message can fit within a single PDU
        _parts.clear();
        if (udataSize() > 140) {
            // multiple PDU are needed
            _pdu = new byte[0];
            return generateParts();
        }
        sca = encodeAddress(_smsc);
        if ((sca).length > 0) {
            sca[0] = (byte)((sca).length-1 & 0xff);
        }
        stamp = encodeTimeStamp(_stamp);
        udata = encodeUserData();
        if (_deliv) {
            addr = encodeAddress(_orig);
            hdr = new byte[1];
            pdutyp = 0;
        } else {
            addr = encodeAddress(_dest);
            _mref = _mbox.nextMsgRef();
            hdr = new byte[2];
            hdr[1] = (byte)(_mref & 0xff);
            pdutyp = 1;
            if ((stamp).length > 0) {
                pdutyp = pdutyp + 16;
            }
            if ((stamp).length == 7) {
                pdutyp = pdutyp + 8;
            }
        }
        if ((_udh).length > 0) {
            pdutyp = pdutyp + 64;
        }
        hdr[0] = (byte)(pdutyp & 0xff);
        pdulen = (sca).length+(hdr).length+(addr).length+2+(stamp).length+(udata).length;
        _pdu = new byte[pdulen];
        pdulen = 0;
        i = 0;
        while (i < (sca).length) {
            _pdu[pdulen] = (byte)((sca[i] & 0xff) & 0xff);
            pdulen = pdulen + 1;
            i = i + 1;
        }
        i = 0;
        while (i < (hdr).length) {
            _pdu[pdulen] = (byte)((hdr[i] & 0xff) & 0xff);
            pdulen = pdulen + 1;
            i = i + 1;
        }
        i = 0;
        while (i < (addr).length) {
            _pdu[pdulen] = (byte)((addr[i] & 0xff) & 0xff);
            pdulen = pdulen + 1;
            i = i + 1;
        }
        _pdu[pdulen] = (byte)(_pid & 0xff);
        pdulen = pdulen + 1;
        _pdu[pdulen] = (byte)(get_dcs() & 0xff);
        pdulen = pdulen + 1;
        i = 0;
        while (i < (stamp).length) {
            _pdu[pdulen] = (byte)((stamp[i] & 0xff) & 0xff);
            pdulen = pdulen + 1;
            i = i + 1;
        }
        i = 0;
        while (i < (udata).length) {
            _pdu[pdulen] = (byte)((udata[i] & 0xff) & 0xff);
            pdulen = pdulen + 1;
            i = i + 1;
        }
        _npdu = 1;
        return YAPI.SUCCESS;
    }

    public int parseUserDataHeader()
    {
        int udhlen;
        int i;
        int iei;
        int ielen;
        String sig;
        
        _aggSig = "";
        _aggIdx = 0;
        _aggCnt = 0;
        udhlen = (_udh).length;
        i = 0;
        while (i+1 < udhlen) {
            iei = (_udh[i] & 0xff);
            ielen = (_udh[i+1] & 0xff);
            i = i + 2;
            if (i + ielen <= udhlen) {
                if ((iei == 0) && (ielen == 3)) {
                    // concatenated SMS, 8-bit ref
                    sig = String.format(Locale.US, "%s-%s-%02x-%02x", _orig, _dest,
                    _mref,(_udh[i] & 0xff));
                    _aggSig = sig;
                    _aggCnt = (_udh[i+1] & 0xff);
                    _aggIdx = (_udh[i+2] & 0xff);
                }
                if ((iei == 8) && (ielen == 4)) {
                    // concatenated SMS, 16-bit ref
                    sig = String.format(Locale.US, "%s-%s-%02x-%02x%02x", _orig, _dest,
                    _mref, (_udh[i] & 0xff),(_udh[i+1] & 0xff));
                    _aggSig = sig;
                    _aggCnt = (_udh[i+2] & 0xff);
                    _aggIdx = (_udh[i+3] & 0xff);
                }
            }
            i = i + ielen;
        }
        return YAPI.SUCCESS;
    }

    public int parsePdu(byte[] pdu)
    {
        int rpos;
        int addrlen;
        int pdutyp;
        int tslen;
        int dcs;
        int udlen;
        int udhsize;
        int udhlen;
        int i;
        int carry;
        int nbits;
        int thisb;
        
        _pdu = pdu;
        _npdu = 1;
        
        // parse meta-data
        _smsc = decodeAddress(pdu, 1, 2*((pdu[0] & 0xff)-1));
        rpos = 1+(pdu[0] & 0xff);
        pdutyp = (pdu[rpos] & 0xff);
        rpos = rpos + 1;
        _deliv = (((pdutyp) & (3)) == 0);
        if (_deliv) {
            addrlen = (pdu[rpos] & 0xff);
            rpos = rpos + 1;
            _orig = decodeAddress(pdu, rpos, addrlen);
            _dest = "";
            tslen = 7;
        } else {
            _mref = (pdu[rpos] & 0xff);
            rpos = rpos + 1;
            addrlen = (pdu[rpos] & 0xff);
            rpos = rpos + 1;
            _dest = decodeAddress(pdu, rpos, addrlen);
            _orig = "";
            if ((((pdutyp) & (16))) != 0) {
                if ((((pdutyp) & (8))) != 0) {
                    tslen = 7;
                } else {
                    tslen= 1;
                }
            } else {
                tslen = 0;
            }
        }
        rpos = rpos + ((((addrlen+3)) >> (1)));
        _pid = (pdu[rpos] & 0xff);
        rpos = rpos + 1;
        dcs = (pdu[rpos] & 0xff);
        rpos = rpos + 1;
        _alphab = (((((dcs) >> (2)))) & (3));
        _mclass = ((dcs) & (16+3));
        _stamp = decodeTimeStamp(pdu, rpos, tslen);
        rpos = rpos + tslen;
        
        // parse user data (including udh)
        nbits = 0;
        carry = 0;
        udlen = (pdu[rpos] & 0xff);
        rpos = rpos + 1;
        if (((pdutyp) & (64)) != 0) {
            udhsize = (pdu[rpos] & 0xff);
            rpos = rpos + 1;
            _udh = new byte[udhsize];
            i = 0;
            while (i < udhsize) {
                _udh[i] = (byte)((pdu[rpos] & 0xff) & 0xff);
                rpos = rpos + 1;
                i = i + 1;
            }
            if (_alphab == 0) {
                // 7-bit encoding
                udhlen = (((8 + 8*udhsize + 6)) / (7));
                nbits = 7*udhlen - 8 - 8*udhsize;
                if (nbits > 0) {
                    thisb = (pdu[rpos] & 0xff);
                    rpos = rpos + 1;
                    carry = ((thisb) >> (nbits));
                    nbits = 8 - nbits;
                }
            } else {
                // byte encoding
                udhlen = 1+udhsize;
            }
            udlen = udlen - udhlen;
        } else {
            udhsize = 0;
            _udh = new byte[0];
        }
        _udata = new byte[udlen];
        if (_alphab == 0) {
            // 7-bit encoding
            i = 0;
            while (i < udlen) {
                if (nbits == 7) {
                    _udata[i] = (byte)(carry & 0xff);
                    carry = 0;
                    nbits = 0;
                } else {
                    thisb = (pdu[rpos] & 0xff);
                    rpos = rpos + 1;
                    _udata[i] = (byte)(((carry) | ((((((thisb) << (nbits)))) & (127)))) & 0xff);
                    carry = ((thisb) >> ((7 - nbits)));
                    nbits = nbits + 1;
                }
                i = i + 1;
            }
        } else {
            // 8-bit encoding
            i = 0;
            while (i < udlen) {
                _udata[i] = (byte)((pdu[rpos] & 0xff) & 0xff);
                rpos = rpos + 1;
                i = i + 1;
            }
        }
        parseUserDataHeader();
        
        return YAPI.SUCCESS;
    }

    public int send() throws YAPI_Exception
    {
        int i;
        int retcode;
        YSms pdu;
        
        if (_npdu == 0) {
            generatePdu();
        }
        if (_npdu == 1) {
            return _mbox._upload("sendSMS", _pdu);
        }
        retcode = YAPI.SUCCESS;
        i = 0;
        while ((i < _npdu) && (retcode == YAPI.SUCCESS)) {
            pdu = _parts.get(i);
            retcode= pdu.send();
            i = i + 1;
        }
        return retcode;
    }

    public int deleteFromSIM() throws YAPI_Exception
    {
        int i;
        int retcode;
        YSms pdu;
        
        if (_slot > 0) {
            return _mbox.clearSIMSlot(_slot);
        }
        retcode = YAPI.SUCCESS;
        i = 0;
        while ((i < _npdu) && (retcode == YAPI.SUCCESS)) {
            pdu = _parts.get(i);
            retcode= pdu.deleteFromSIM();
            i = i + 1;
        }
        return retcode;
    }

    //--- (end of generated code: YSms implementation)
}

