/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
 *
 *  An example that shows how to use a  Yocto-Bridge
 *
 *  You can find more information on our web site:
 *   Yocto-Bridge documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-bridge/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YWeighScale;

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

        YWeighScale sensor;
        if (args.length > 0) {
            sensor = YWeighScale.FindWeighScale(args[0]);
        } else {
            sensor = YWeighScale.FirstWeighScale();
        }
        if (sensor == null) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }
        try {
            if (sensor.isOnline()) {
                // On startup, enable excitation and tare weigh scale
                System.out.println("Resetting tare weight...");
                sensor.set_excitation(YWeighScale.EXCITATION_AC);
                YAPI.Sleep(3000);
                sensor.tare();
            }

            while (sensor.isOnline()) {
                System.out.println("Weight :" + sensor.get_currentValue() + " " + sensor.get_unit());
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(1000);
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module " + sensor.describe() + " disconnected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
