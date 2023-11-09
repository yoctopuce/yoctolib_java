/*
 *
 *  $Id: Demo.java 55641 2023-07-26 09:43:42Z seb $
 *
 *  An example that show how to use a  Yocto-Inclinometer
 *
 *  You can find more information on our web site:
 *   Yocto-Inclinometer documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-inclinometer/doc.html
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

        YTilt anytilt, tilt1, tilt2, tilt3;

        if (args.length == 0) {
            anytilt = YTilt.FirstTilt();
            if (anytilt == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            anytilt = YTilt.FindTilt(args[0] + ".tilt1");
            if (!anytilt.isOnline()) {
                System.out.println("Module not connected (check identification and USB cable)");
                System.exit(1);
            }
        }
        try {
            String serial = anytilt.get_module().get_serialNumber();
            tilt1 = YTilt.FindTilt(serial + ".tilt1");
            tilt2 = YTilt.FindTilt(serial + ".tilt2");
            tilt3 = YTilt.FindTilt(serial + ".tilt3");

            int count = 0;
            while (true) {
                if (!tilt1.isOnline()) {
                    System.out.println("device disconnected");
                    System.exit(0);
                }

                if (count % 10 == 0)
                    System.out.println("tilt1\ttilt2\ttilt3");

                System.out.println("" + tilt1.get_currentValue() + "\t" + tilt2.get_currentValue() +
                        "\t" + tilt3.get_currentValue());
                count++;
                YAPI.Sleep(250);
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
