/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
 *
 *  An example that shows how to use a  Yocto-GPS
 *
 *  You can find more information on our web site:
 *   Yocto-GPS documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-gps/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.*;

public class Demo {

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
        YGps gps;

        if (args.length == 0) {
            gps = YGps.FirstGps();
            if (gps == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            gps = YGps.FindGps(args[0] + ".gps");
        }

        while (true) {
            try {
                if (gps.get_isFixed() != YGps.ISFIXED_TRUE) {
                    System.out.println("fixing...");
                } else {
                    System.out.println(gps.get_latitude() + "  " + gps.get_longitude());
                }
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module not connected (check identification and USB cable)");
                break;
            }
        }

        YAPI.FreeAPI();
    }
}
