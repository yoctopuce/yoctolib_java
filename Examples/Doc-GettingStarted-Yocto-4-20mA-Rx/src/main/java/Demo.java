/*
 *
 *  $Id: Demo.java 32627 2018-10-10 13:37:29Z seb $
 *
 *  An example that show how to use a  Yocto-4-20mA-Rx
 *
 *  You can find more information on our web site:
 *   Yocto-4-20mA-Rx documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-4-20ma-rx/doc.html
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

        YGenericSensor sensor;
        if (args.length > 0) {
            sensor = YGenericSensor.FindGenericSensor(args[0]);
        } else {
            sensor = YGenericSensor.FirstGenericSensor();
        }
        if (sensor == null) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }

        try {
            YGenericSensor s1 = YGenericSensor.FindGenericSensor(sensor.get_module().get_serialNumber() + ".genericSensor1");
            YGenericSensor s2 = YGenericSensor.FindGenericSensor(sensor.get_module().get_serialNumber() + ".genericSensor2");

            while (s1.isOnline() && s2.isOnline()) {
                System.out.println("Channel 1 :" + s1.get_currentValue() + " " + s1.get_unit());
                System.out.println("Channel 2 : " + s2.get_currentValue() + " " + s2.get_unit());
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(1000);
            }

        } catch (YAPI_Exception ex) {
            System.out.println("Module " + sensor.describe() + " disconnected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
