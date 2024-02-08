/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
 *
 *  An example that shows how to use a  Yocto-WatchdogDC
 *
 *  You can find more information on our web site:
 *   Yocto-WatchdogDC documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-watchdogdc/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.*;

public class Demo
{

    public static void main(String[] args)
    {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        YWatchdog watchdog;
        if (args.length > 0) {
            watchdog = YWatchdog.FindWatchdog(args[0]);
        } else {
            watchdog = YWatchdog.FirstWatchdog();
            if (watchdog == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        try {
            System.out.println("Starting watching dog");
            watchdog.set_state(YWatchdog.RUNNING_ON);
            System.out.println("waiting");
            for (int i = 0; i < 12; i++) {
                YAPI.Sleep(10000);
                System.out.println("Resetting watching dog");
                watchdog.resetWatchdog();
                System.out.println("waiting");
            }
            System.out.println("Stopping watching dog");
            watchdog.set_state(YWatchdog.RUNNING_OFF);
        } catch (YAPI_Exception ex) {
            System.out.println("Module " + watchdog.describe() + " not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
