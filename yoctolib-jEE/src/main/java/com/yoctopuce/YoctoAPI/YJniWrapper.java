package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;
import java.util.HashMap;

public class YJniWrapper
{

    private static boolean loaded;


    public static void Check() throws YAPI_Exception
    {
        if (!loaded) {
            String os_arch = System.getProperty("os.arch");
            String os_name = System.getProperty("os.name");
            String os_lower = os_name.toLowerCase();
            if (os_lower.contains("win")) {
                if (os_arch.equals("x86")) {
                    try {
                        System.loadLibrary("yapi");
                    } catch (UnsatisfiedLinkError ex) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "Cannot load yapi.dll. Check your java.library.path property.");
                    }
                    loaded = true;
                } else if (os_arch.equals("amd64")) {
                    try {
                        System.loadLibrary("yapi64");
                    } catch (UnsatisfiedLinkError ex) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "Cannot load yapi64.dll. Check your java.library.path property.");
                    }
                    loaded = true;
                } else {
                    loaded = false;
                    throw new YAPI_Exception("Unknown Windows version:" + os_name + " (" + os_arch + ")");
                }
            }  else if (os_lower.contains("linux")) {
                if (os_arch.equals("x86")) {
                    try {
                        System.loadLibrary("yapi-i386");
                    } catch (UnsatisfiedLinkError ex) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "Cannot load libyapi-i386.so. Check your java.library.path property.");
                    }
                    loaded = true;
                } else if (os_arch.equals("amd64")) {
                    try {
                        System.loadLibrary("yapi-amd64");
                    } catch (UnsatisfiedLinkError ex) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "Cannot load libyapi-amd64.so Check your java.library.path property.");
                    }
                    loaded = true;
                 } else if (os_arch.equals("arm")) {
                    try {
                        System.loadLibrary("yapi-armhf");
                    } catch (UnsatisfiedLinkError ex) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "Cannot load libyapi-armhf.so. Check your java.library.path property.");
                    }
                    loaded = true;
                } else {
                    loaded = false;
                    throw new YAPI_Exception("Unsupported Linux architecture:" + os_name + " (" + os_arch + ")");
                }
            }  else if (os_lower.contains("mac")) {
                if (os_arch.equals("x86_64")) {
                    try {
                        System.loadLibrary("yapi");
                    } catch (UnsatisfiedLinkError ex) {
                        throw new YAPI_Exception(YAPI.IO_ERROR, "Cannot load libyapi.dylib. Check your java.library.path property.");
                    }
                    loaded = true;
                } else {
                    loaded = false;
                    throw new YAPI_Exception("Unsupported Linux architecture:" + os_name + " (" + os_arch + ")");
                }

            }else {
                throw new YAPI_Exception(YAPI.NOT_SUPPORTED, "USB direct access is not supported on " + os_name + " " + os_arch);
            }
        }
    }

    public static native String getAPIVersion();

    public native static void reserveUSBAccess();

    public native static void releaseUSBAccess();

    public native static ArrayList<String> getBootloaders();

    public native static void updateDeviceList(ArrayList<WPEntry> whitePages, ArrayList<YPEntry> functions);

    public native static byte[] devRequestSync(String serialNumber, byte[] req_head_and_body);

    public native static void devRequestAsync(String serialNumber, byte[] req_head_and_body, YGenericHub.RequestAsyncResult asyncResult, Object context);

    public native static void startNotifications(YUSBHub yusbHub);

    public native static void stopNotifications();

    public native static void usbProcess(YUSBHub yusbHub);
}
