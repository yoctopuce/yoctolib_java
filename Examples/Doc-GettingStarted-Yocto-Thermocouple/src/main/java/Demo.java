/*
 *
 *  $Id: Demo.java 32627 2018-10-10 13:37:29Z seb $
 *
 *  An example that show how to use a  Yocto-Thermocouple
 *
 *  You can find more information on our web site:
 *   Yocto-Thermocouple documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-thermocouple/doc.html
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

        String serial = "";
        if (args.length == 0) {
            YTemperature tmp = YTemperature.FirstTemperature();
            if (tmp == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
            try {
                serial = tmp.module().get_serialNumber();
            } catch (YAPI_Exception ex) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            serial = args[0];
        }

        // retreive both channels
        YTemperature ch1 = YTemperature.FindTemperature(serial + ".temperature1");
        YTemperature ch2 = YTemperature.FindTemperature(serial + ".temperature2");
   
        while (true) {
            try {
                System.out.println("Channel 1 temperature: " + ch1.get_currentValue() + " C");
                System.out.println("Channel 2 temperature: " + ch2.get_currentValue() + " C");
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
