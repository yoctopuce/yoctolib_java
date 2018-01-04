package com.yoctopuce.YoctoAPI;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class YFirmwareFile {
    private static final int BYN_REV_V4 = 4;
    private static final int BYN_REV_V5 = 5;
    private static final int BYN_REV_V6 = 6;
    private static final int MAX_ROM_ZONES_PER_FILES = 16;
    private static final int MAX_FLASH_ZONES_PER_FILES = 4;
    private static final int BYN_HEAD_SIZE_V4 = (96 + 8);
    private static final int BYN_HEAD_SIZE_V5 = (96 + 32);
    private static final int BYN_HEAD_SIZE_V6 = (96 + 48);
    private static final int BYN_MD5_OFS_V6 = (96 + 16);

    private final String _path;
    private final String _serial;
    private final String _pictype;
    private final String _product;
    private final String _firmware;
    private final String _prog_version;
    private final int _ROM_nb_zone;
    private final int _FLA_nb_zone;
    private final int _ROM_total_size;
    private final int _FLA_total_size;
    private final byte[] _data;
    private int _zone_ofs;

    public byn_zone getBynZone(int zOfs)
    {
        return new byn_zone(_data, zOfs);
    }

    public int getFirstZoneOfs()
    {
        return _zone_ofs;
    }

    public class byn_zone {

        public static final int SIZE = 8;
        public final int addr_page;
        public final int len;

        public byn_zone(byte[] data, int zOfs)
        {
            final ByteBuffer bb = ByteBuffer.wrap(data, zOfs, 8);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            addr_page = bb.getInt();
            len = bb.getInt();
        }
    }


    private YFirmwareFile(String path, String serial, String pictype, String product, String firmware,
                          String prog_version, int ROM_nb_zone, int FLA_nb_zone, int ROM_total_size,
                          int FLA_total_size, byte[] data, int zone_ofs)
    {
        _path = path;
        _serial = serial;
        _pictype = pictype;
        _product = product;
        _firmware = firmware;
        _prog_version = prog_version;
        _ROM_nb_zone = ROM_nb_zone;
        _FLA_nb_zone = FLA_nb_zone;
        _ROM_total_size = ROM_total_size;
        _FLA_total_size = FLA_total_size;
        _data = data;
        _zone_ofs = zone_ofs;
    }

    public static YFirmwareFile Parse(String path, byte[] data) throws YAPI_Exception
    {
        int i;
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        if (buffer.get() != 'B' || buffer.get() != 'Y' || buffer.get() != 'N' || buffer.get() != 0) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Not a firmware file");
        }
        int rev = buffer.getShort();
        byte[] serial_buf = new byte[YAPI.YOCTO_SERIAL_LEN];
        buffer.get(serial_buf);
        String serial = getString(serial_buf);
        byte[] pictype_buf = new byte[20];
        buffer.get(pictype_buf);
        String pictype = getString(pictype_buf);
        byte[] product_buf = new byte[YAPI.YOCTO_PRODUCTNAME_LEN];
        buffer.get(product_buf);
        String product = getString(product_buf);
        byte[] firmware_buf = new byte[YAPI.YOCTO_FIRMWARE_LEN];
        buffer.get(firmware_buf);
        String firmware = getString(firmware_buf);

        if (serial.length() >= YAPI.YOCTO_SERIAL_LEN) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Bad serial_buf");
        }
        if (product.length() >= YAPI.YOCTO_PRODUCTNAME_LEN) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Bad product name");
        }
        if (firmware.length() >= YAPI.YOCTO_FIRMWARE_LEN) {
            throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Bad firmware revision");
        }

        int ROM_nb_zone = 0;
        int FLA_nb_zone = 0;
        int ROM_total_size = 0;
        int FLA_total_size = 0;
        byte[] prog_buf;
        String prog_version = "";
        int zone_ofs;
        switch (rev) {
            case BYN_REV_V4:
                zone_ofs = BYN_HEAD_SIZE_V4;
                ROM_nb_zone = buffer.getInt();
                int datasize = buffer.getInt();
                if (ROM_nb_zone > MAX_ROM_ZONES_PER_FILES) {
                    throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Too many zones");
                }
                if (datasize != data.length - BYN_HEAD_SIZE_V4) {
                    throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Incorrect file size");
                }
                break;
            case BYN_REV_V5:
                zone_ofs = BYN_HEAD_SIZE_V5;
                prog_buf = new byte[YAPI.YOCTO_FIRMWARE_LEN];
                buffer.get(prog_buf);
                prog_version = checkProgField(prog_buf);
                buffer.getShort();//skip pad
                ROM_nb_zone = buffer.getInt();
                datasize = buffer.getInt();
                if (ROM_nb_zone > MAX_ROM_ZONES_PER_FILES) {
                    throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Too many zones");
                }
                if (datasize != data.length - BYN_HEAD_SIZE_V5) {
                    throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Incorrect file size");
                }
                break;
            case BYN_REV_V6:
                zone_ofs = BYN_HEAD_SIZE_V6;
                byte md5check[] = new byte[16];
                buffer.get(md5check);
                try {
                    // Create MD5 Hash
                    MessageDigest digest = MessageDigest.getInstance("MD5");
                    digest.update(data, BYN_MD5_OFS_V6, data.length - BYN_MD5_OFS_V6);
                    byte messageDigest[] = digest.digest();
                    if (!Arrays.equals(md5check, messageDigest)) {
                        throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Invalid checksum");
                    }
                } catch (NoSuchAlgorithmException ignore) {
                    throw new YAPI_Exception(YAPI.IO_ERROR, "Unable to verfiy MD5 of firmware " + path);
                }
                prog_buf = new byte[YAPI.YOCTO_FIRMWARE_LEN];
                buffer.get(prog_buf);
                prog_version = checkProgField(prog_buf);
                ROM_nb_zone = buffer.get();
                FLA_nb_zone = buffer.get();
                ROM_total_size = buffer.getInt();
                FLA_total_size = buffer.getInt();
                if (ROM_nb_zone > MAX_ROM_ZONES_PER_FILES) {
                    throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Too many ROM zones");
                }
                if (FLA_nb_zone > MAX_FLASH_ZONES_PER_FILES) {
                    throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "Too many FLASH zones");
                }
                break;
            default:
                throw new YAPI_Exception(YAPI.INVALID_ARGUMENT, "unknown BYN file revision");
        }
        return new YFirmwareFile(path, serial, pictype, product, firmware, prog_version,
                ROM_nb_zone, FLA_nb_zone, ROM_total_size, FLA_total_size, data, zone_ofs);
    }

    private static String getString(byte[] serial_buf)
    {
        int i;
        for (i = 0; i < serial_buf.length && serial_buf[i] != 0; ) {
            i++;
        }
        return new String(serial_buf, 0, i, Charset.forName("ISO_8859_1"));
    }

    private static String checkProgField(byte[] prog_buf) throws YAPI_Exception
    {
        String prog_version = getString(prog_buf);
        if (!prog_version.equals("")) {
            int byn = Integer.valueOf(prog_version);
            try {
                int tools = Integer.parseInt(YAPI.YOCTO_API_BUILD_STR);
                if (byn > tools) {
                    throw new YAPI_Exception(YAPI.VERSION_MISMATCH, "Too recent firmware. Please update the yoctopuce library");
                }
            } catch (NumberFormatException ignore) {
            }
        }
        return prog_version;
    }

    public String getSerial()
    {
        return _serial;
    }

    public String getPictype()
    {
        return _pictype;
    }

    public String getProduct()
    {
        return _product;
    }

    public String getFirmwareRelease()
    {
        return _firmware;
    }

    public int getFirmwareReleaseAsInt()
    {
        try {
            return Integer.parseInt(_firmware);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public String getProg_version()
    {
        return _prog_version;
    }

    public int getROM_nb_zone()
    {
        return _ROM_nb_zone;
    }

    public int getFLA_nb_zone()
    {
        return _FLA_nb_zone;
    }

    public int getROM_total_size()
    {
        return _ROM_total_size;
    }

    public int getFLA_total_size()
    {
        return _FLA_total_size;
    }

    public byte[] getData()
    {
        return _data;
    }

    public String getPath()
    {
        return _path;
    }


}
