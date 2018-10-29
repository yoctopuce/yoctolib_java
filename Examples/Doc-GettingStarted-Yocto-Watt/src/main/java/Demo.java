/*
 *
 *  $Id: Demo.java 32627 2018-10-10 13:37:29Z seb $
 *
 *  An example that show how to use a  Yocto-Watt
 *
 *  You can find more information on our web site:
 *   Yocto-Watt documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-watt/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void main(String[] args)   {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }
        YPower psensor;

        if (args.length == 0) {
            psensor = YPower.FirstPower();
            if (psensor == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            psensor = YPower.FindPower(args[0] + ".power");
        }

        while (true) {
            try {
                System.out.println("Current power: " + psensor.get_currentValue() + " W");
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
