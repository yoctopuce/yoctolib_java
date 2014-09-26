/*********************************************************************
 *
 * $Id: pic24config.php 16414 2014-06-04 12:12:47Z seb $
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
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static com.yoctopuce.YoctoAPI.YAPI.IO_ERROR;
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
    protected int _progress = 0;

    //--- (end of generated code: YFirmwareUpdate definitions)
    private Thread _thread = null;

    static byte[] _downloadfile(String url) throws YAPI_Exception
    {
        ByteArrayOutputStream result = new ByteArrayOutputStream(1024);
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            throw new YAPI_Exception(IO_ERROR,e.getLocalizedMessage());
        }
        BufferedInputStream in = null;
        try {
            URLConnection connection = u.openConnection();
            in = new BufferedInputStream(connection.getInputStream());
            byte[] buffer = new byte[1024];
            int readed=0;
            while (readed >= 0) {
                readed  = in.read(buffer, 0, buffer.length);
                if (readed <0){
                    // end of connection
                    break;
                } else {
                    result.write(buffer,0,readed);
                }
            }

        } catch (IOException e) {
            throw new YAPI_Exception(IO_ERROR, "unable to contact www.yoctopuce.com :" + e.getLocalizedMessage());
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
        if(folder.isFile()) {
            bestFirmware = _loadFirmwareFile(folder);
        }else {
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {

                for (File subfile : listOfFiles) {
                    if (!subfile.getName().startsWith(serial_base))
                        continue;
                    YFirmwareFile firmware = null;
                    if (subfile.isFile()) {
                        try {
                            firmware = _loadFirmwareFile(subfile);
                        }catch (YAPI_Exception ex){
                            continue;
                        }
                    } else if (subfile.isDirectory()) {
                        firmware = checkFirmware_r(subfile,serial_base);
                    }
                    if (firmware==null || !firmware.getSerial().startsWith(serial_base))
                        continue;
                    if (bestFirmware==null || bestFirmware.getFirmwareRelaseAsInt()<firmware.getFirmwareRelaseAsInt()){
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
            throw new YAPI_Exception(YAPI.FILE_NOT_FOUND,"File not found");
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream(1024);

        try {
            byte[] buffer = new byte[1024];
            int readed=0;
            while (readed >= 0) {
                readed  = in.read(buffer, 0, buffer.length);
                if (readed <0){
                    // end of connection
                    break;
                } else {
                    result.write(buffer,0,readed);
                }
            }

        } catch (IOException e) {
            throw new YAPI_Exception(IO_ERROR, "unable to load file :" + e.getLocalizedMessage());
        } finally {
            try {
                in.close();
            } catch (IOException ignore) {
            }
        }

        return YFirmwareFile.Parse(file.getPath() ,result.toByteArray());
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
                    _thread = new Thread(new Runnable()
                    {
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
                                _progress(5, "Enter in bootloader");
                                YModule module = YModule.FindModule(_serial + ".module");
                                if (!module.isOnline()) {
                                    throw new YAPI_Exception(YAPI.DEVICE_NOT_FOUND, "device " + _serial + " is not online");
                                }
                                YDevice yDevice = module.getYDevice();
                                YGenericHub hub = yDevice.getHub();
                                hub.firmwareUpdate(_serial, firmware, _settings, new YGenericHub.UpdateProgress()
                                {
                                    @Override
                                    public void firmware_progress(int percent, String message)
                                    {
                                        _progress(percent, message);
                                    }
                                });
                                //80%-> 98%
                                _progress(80, "wait to the device restart");
                                long timeout = YAPI.GetTickCount() + 20000;
                                while (!module.isOnline() && timeout > YAPI.GetTickCount()) {
                                    Thread.sleep(500);
                                    try {
                                        YAPI.UpdateDeviceList();
                                    } catch (YAPI_Exception ignore) {
                                    }
                                }
                                if (module.isOnline()) {
                                    _progress(100, "Success");
                                } else {
                                    _progress(-1, "Device did not reboot correctly");
                                }
                            } catch (YAPI_Exception e) {
                                _progress(e.errorType, e.getLocalizedMessage());
                            } catch (InterruptedException ignore) {
                            }

                        }
                    }, "Update" + _serial);
                    _thread.start();
                }
            }
        }
    }


    //--- (generated code: YFirmwareUpdate implementation)

    //cannot be generated for Java:
    //public int _processMore(int newupdate)

    public int get_progress() throws YAPI_Exception
    {
        YModule m;
        _processMore(0);
        if ((_progress == 100) && ((_settings).length != 0)) {
            m = YModule.FindModule(_serial);
            if (m.isOnline()) {
                m.set_allSettings(_settings);
                _settings = new byte[0];
            }
        }
        return _progress;
    }

    /**
     * Returns the last progress message of the firmware update process. If an error occur during the
     * firmware update process the error message is returned
     *
     * @return an string  with the last progress message, or the error message.
     */
    public String get_progressMessage()
    {
        return _progress_msg;
    }

    /**
     * Start the firmware update process. This method start the firmware update process in background. This method
     * return immediately. The progress of the firmware update can be monitored with methods get_progress()
     * and get_progressMessage().
     *
     * @return an integer in the range 0 to 100 (percentage of completion),
     *         or a negative error code in case of failure.
     *
     * On failure returns a negative error code.
     */
    public int startUpdate()
    {
        _processMore(1);
        return _progress;
    }

    //--- (end of generated code: YFirmwareUpdate implementation)
}

