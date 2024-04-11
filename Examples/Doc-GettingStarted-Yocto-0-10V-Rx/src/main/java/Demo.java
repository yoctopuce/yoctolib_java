/*
 *
 *  $Id: Demo.java 60035 2024-03-20 09:56:43Z seb $
 *
 *  An example that shows how to use a  Yocto-0-10V-Rx
 *
 *  You can find more information on our web site:
 *   Yocto-0-10V-Rx documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-0-10v-rx/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void main(String[] args) {
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
                double value =s1.get_currentValue();
                System.out.println("Channel 1 :" + s1.get_currentValue() + " " + s1.get_unit());		
                value = s2.get_currentValue();
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
