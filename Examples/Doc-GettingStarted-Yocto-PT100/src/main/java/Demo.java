/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
 *
 *  An example that shows how to use a  Yocto-PT100
 *
 *  You can find more information on our web site:
 *   Yocto-PT100 documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-pt100/doc.html
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
        YTemperature tsensor;

        if (args.length == 0) {
            tsensor = YTemperature.FirstTemperature();
            if (tsensor == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            tsensor = YTemperature.FindTemperature(args[0] + ".temperature");
        }

        while (true) {
            try {
                System.out.println("Current temperature: " + tsensor.get_currentValue() + " °C");
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
