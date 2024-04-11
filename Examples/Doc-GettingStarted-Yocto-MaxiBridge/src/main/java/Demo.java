/*
 *
 *  $Id: Demo.java 60035 2024-03-20 09:56:43Z seb $
 *
 *  An example that shows how to use a  Yocto-MaxiBridge
 *
 *  You can find more information on our web site:
 *   Yocto-MaxiBridge documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-maxibridge/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YMultiCellWeighScale;

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

        YMultiCellWeighScale sensor;
        if (args.length > 0) {
            sensor = YMultiCellWeighScale.FindMultiCellWeighScale(args[0]);
        } else {
            sensor = YMultiCellWeighScale.FirstMultiCellWeighScale();
        }
        if (sensor == null) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }
        try {
            if (sensor.isOnline()) {
                // On startup, enable excitation and tare weigh scale
                System.out.println("Resetting tare weight...");
                sensor.set_excitation(YMultiCellWeighScale.EXCITATION_AC);
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
