/*********************************************************************
 *
 * $Id: YFirmwareUpdate.java 20376 2015-05-19 14:18:47Z seb $
 *
 * Implements yFindFirmwareUpdate(), the high-level API for FirmwareUpdate functions
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.yoctopuce.YoctoAPI.YAPI.SafeYAPI;
//--- (generated code: YFirmwareUpdate return codes)
//--- (end of generated code: YFirmwareUpdate return codes)
//--- (generated code: YFirmwareUpdate class start)
/**
 * YFirmwareUpdate Class: Control interface for the firmware update process
 *
 * The YFirmwareUpdate class let you control the firmware update of a Yoctopuce
 * module. This class should not be instantiate directly, instead the method
 * updateFirmware should be called to get an instance of YFirmwareUpdate.
 */
 @SuppressWarnings("UnusedDeclaration")
public class YFirmwareUpdate
{
//--- (end of generated code: YFirmwareUpdate class start)
//--- (generated code: YFirmwareUpdate definitions)
    protected String _serial;
    protected byte[] _settings;
    protected String _firmwarepath;
    protected String _progress_msg;
    protected int _progress_c = 0;
    protected int _progress = 0;
    protected int _restore_step = 0;

    //--- (end of generated code: YFirmwareUpdate definitions)
    private Thread _thread = null;

    static byte[] _downloadfile(String url) throws YAPI_Exception
    {
        ByteArrayOutputStream result = new ByteArrayOutputStream(1024);
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, e.getLocalizedMessage());
        }
        BufferedInputStream in = null;
        try {
            URLConnection connection = u.openConnection();
            in = new BufferedInputStream(connection.getInputStream());
            byte[] buffer = new byte[1024];
            int readed = 0;
            while (readed >= 0) {
                readed = in.read(buffer, 0, buffer.length);
                if (readed < 0) {
                    // end of connection
                    break;
                } else {
                    result.write(buffer, 0, readed);
                }
            }

        } catch (IOException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "unable to contact www.yoctopuce.com :" + e.getLocalizedMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) {
                }
            }
        }
        return result.toByteArray();
    }


    static YFirmwareFile checkFirmware_r(File folder, String serial_base) throws YAPI_Exception
    {
        YFirmwareFile bestFirmware = null;
        if (folder.isFile()) {
            bestFirmware = _loadFirmwareFile(folder);
        } else {
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {

                for (File subfile : listOfFiles) {
                    if (!subfile.getName().startsWith(serial_base))
                        continue;
                    YFirmwareFile firmware = null;
                    if (subfile.isFile()) {
                        try {
                            firmware = _loadFirmwareFile(subfile);
                        } catch (YAPI_Exception ex) {
                            continue;
                        }
                    } else if (subfile.isDirectory()) {
                        firmware = checkFirmware_r(subfile, serial_base);
                    }
                    if (firmware == null || !firmware.getSerial().startsWith(serial_base))
                        continue;
                    if (bestFirmware == null || bestFirmware.getFirmwareRelaseAsInt() < firmware.getFirmwareRelaseAsInt()) {
                        bestFirmware = firmware;
                    }
                }
            }
        }
        return bestFirmware;
    }

    private static YFirmwareFile _loadFirmwareFile(File file) throws YAPI_Exception
    {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new YAPI_Exception(YAPI.FILE_NOT_FOUND, "File not found");
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream(1024);

        try {
            byte[] buffer = new byte[1024];
            int readed = 0;
            while (readed >= 0) {
                readed = in.read(buffer, 0, buffer.length);
                if (readed < 0) {
                    // end of connection
                    break;
                } else {
                    result.write(buffer, 0, readed);
                }
            }

        } catch (IOException e) {
            throw new YAPI_Exception(YAPI.IO_ERROR, "unable to load file :" + e.getLocalizedMessage());
        } finally {
            try {
                in.close();
            } catch (IOException ignore) {
            }
        }

        return YFirmwareFile.Parse(file.getPath(), result.toByteArray());
    }


    public YFirmwareUpdate(String serial, String path, byte[] settings)
    {
        _serial = serial;
        _firmwarepath = path;
        _settings = settings;
        //--- (generated code: YFirmwareUpdate attributes initialization)
        //--- (end of generated code: YFirmwareUpdate attributes initialization)
    }


    private void _progress(int progress, String msg)
    {
        synchronized (this) {
            _progress = progress;
            _progress_msg = msg;
        }
    }

    private void _processMore(int start)
    {
        synchronized (this) {
            if (start > 0) {
                if (_thread == null || !_thread.isAlive()) {
                    _progress(0, "Firmware update started");
                    _thread = new Thread(new Runnable() {
                        @Override
                        public void run()
                        {
                            YFirmwareFile firmware;
                            try {
                                //1% -> 5%
                                _progress(1, "Loading firmware");
                                if (_firmwarepath.startsWith("www.yoctopuce.com") || _firmwarepath.startsWith("http://www.yoctopuce.com")) {
                                    byte[] bytes = YFirmwareUpdate._downloadfile(_firmwarepath);
                                    firmware = YFirmwareFile.Parse(_firmwarepath, bytes);
                                } else {
                                    firmware = YFirmwareUpdate._loadFirmwareFile(new File(_firmwarepath));
                                }

                                //5% -> 10%
                                _progress(5, "check if module is already in bootloader");
                                YGenericHub hub = null;
                                YModule module = YModule.FindModule(_serial + ".module");
                                if (module.isOnline()) {
                                    YDevice yDevice = module.getYDevice();
                                    hub = yDevice.getHub();
                                } else {
                                    // test if already in bootloader
                                    for (YGenericHub h : SafeYAPI()._hubs) {
                                        ArrayList<String> bootloaders = h.getBootloaders();
                                        if (bootloaders.contains(_serial)) {
                                            hub = h;
                                            break;

                                        }
                                    }
                                }
                                if (hub == null) {
                                    throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "device " + _serial + " is not detected");
                                }

                                hub.firmwareUpdate(_serial, firmware, _settings, new YGenericHub.UpdateProgress() {
                                    @Override
                                    public void firmware_progress(int percent, String message)
                                    {
                                        _progress(5 + percent * 80 / 100, message);
                                    }
                                });
                                //80%-> 98%
                                _progress(80, "wait to the device restart");
                                long timeout = YAPI.GetTickCount() + 30000;
                                while (!module.isOnline() && timeout > YAPI.GetTickCount()) {
                                    Thread.sleep(500);
                                    try {
                                        YAPI.UpdateDeviceList();
                                    } catch (YAPI_Exception ignore) {
                                    }
                                }
                                if (module.isOnline()) {
                                    if (_settings!=null) {
                                        module.set_allSettings(_settings);
                                    }
                                    _progress(100, "Success");
                                } else {
                                    _progress(-1, "Device did not reboot correctly");
                                }
                            } catch (YAPI_Exception e) {
                                _progress(e.errorType, e.getLocalizedMessage());
                                e.printStackTrace();
                            } catch (InterruptedException ignore) {
                            }

                        }
                    }, "Update" + _serial);
                    _thread.start();
                }
            }
        }
    }


    /**
     * Test if the byn file is valid for this module. It's possible to pass an directory instead of a file.
     * In this case this method return the path of the most recent appropriate byn file. This method will
     * ignore firmware that are older than mintrelase.
     *
     * @param serial  : the serial number of the module to update
     * @param path    : the path of a byn file or a directory that contain byn files
     * @param minrelease : an positif integer
     *
     * @return : the path of the byn file to use or a empty string if no byn files match the requirement
     *
     * On failure, returns a string that start with "error:".
     */
    public static String CheckFirmware(String serial, String path, int minrelease) throws YAPI_Exception
    {
        String link = "";
        Integer best_rev = 0;
        Integer current_rev;


        if (path.startsWith("www.yoctopuce.com") || path.startsWith("http://www.yoctopuce.com")) {
            byte[] json = YFirmwareUpdate._downloadfile("http://www.yoctopuce.com//FR/common/getLastFirmwareLink.php?serial=" + serial);
            JSONObject obj = null;
            try {
                obj = new JSONObject(new String(json, YAPI.DeviceCharset));
            } catch (JSONException ex) {
                throw new YAPI_Exception(YAPI.IO_ERROR, ex.getLocalizedMessage());
            }
            try {
                link = obj.getString("link");
                best_rev = obj.getInt("version");
            } catch (JSONException e) {
                throw new YAPI_Exception(YAPI.IO_ERROR, "invalid respond form www.yoctopuce.com" + e.getLocalizedMessage());
            }
        } else {

            File folder = new File(path);
            YFirmwareFile firmware = YFirmwareUpdate.checkFirmware_r(folder, serial.substring(0, YAPI.YOCTO_BASE_SERIAL_LEN));
            if (firmware != null) {
                best_rev = firmware.getFirmwareRelaseAsInt();
                link = firmware.getPath();
            }
        }
        if (minrelease != 0) {
            if (minrelease < best_rev)
                return link;
            else
                return "";
        }
        return link;
    }

    /**
     * Retruns a list of all the modules in "update" mode. Only USB connected
     * devices are listed. For modules connected to a YoctoHub, you must
     * connect yourself to the YoctoHub web interface.
     *
     * @return an array of strings containing the serial list of module in "update" mode.
     */
    public static ArrayList<String> GetAllBootLoaders()
    {
        ArrayList<String> res = new ArrayList<String>();
        for (YGenericHub h : YAPI.SafeYAPI()._hubs) {
            try {
                ArrayList<String> bootloaders = h.getBootloaders();
                if (bootloaders != null) {
                    res.addAll(bootloaders);
                }
            } catch (YAPI_Exception e) {
                YAPI.SafeYAPI()._Log(e.getLocalizedMessage());
            }
        }
        return res;
    }


    //--- (generated code: YFirmwareUpdate implementation)

    //cannot be generated for Java:
    //public int _processMore(int newupdate) throws YAPI_Exception

    //cannot be generated for Java:
    //public static ArrayList<String> GetAllBootLoaders()

    //cannot be generated for Java:
    //public static String CheckFirmware(String serial,String path,int minrelease)

    /**
     * Returns the progress of the firmware update, on a scale from 0 to 100. When the object is
     * instantiated, the progress is zero. The value is updated during the firmware update process until
     * the value of 100 is reached. The 100 value means that the firmware update was completed
     * successfully. If an error occurs during the firmware update, a negative value is returned, and the
     * error message can be retrieved with get_progressMessage.
     *
     * @return an integer in the range 0 to 100 (percentage of completion)
     *         or a negative error code in case of failure.
     */
    public int get_progress()
    {
        _processMore(0);
        return _progress;
    }

    /**
     * Returns the last progress message of the firmware update process. If an error occurs during the
     * firmware update process, the error message is returned
     *
     * @return a string  with the latest progress message, or the error message.
     */
    public String get_progressMessage()
    {
        return _progress_msg;
    }

    /**
     * Starts the firmware update process. This method starts the firmware update process in background. This method
     * returns immediately. You can monitor the progress of the firmware update with the get_progress()
     * and get_progressMessage() methods.
     *
     * @return an integer in the range 0 to 100 (percentage of completion),
     *         or a negative error code in case of failure.
     *
     * On failure returns a negative error code.
     */
    public int startUpdate()
    {
        _progress = 0;
        _progress_c = 0;
        _processMore(1);
        return _progress;
    }

    //--- (end of generated code: YFirmwareUpdate implementation)
}

