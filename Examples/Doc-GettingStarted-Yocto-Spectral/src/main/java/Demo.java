/*
 *
 *  $Id: Demo.java 65287 2025-03-24 13:18:15Z seb $
 *
 *  An example that shows how to use a  Yocto-Spectral
 *
 *  You can find more information on our web site:
 *   Yocto-Spectral documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-spectral/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

/*
 *
 *  $Id: Demo.java 65287 2025-03-24 13:18:15Z seb $
 *
 *  An example that shows how to use a  Yocto-Spectral
 *
 *  You can find more information on our web site:
 *   Yocto-Spectral documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-spectral/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.*;

import java.util.ArrayList;

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

        YColorSensor colorSensor;
        if (args.length > 0) {
            colorSensor = YColorSensor.FindColorSensor(args[0] + ".colorSensor");
        } else {
            colorSensor = YColorSensor.FirstColorSensor();
        }
        if (colorSensor == null || !colorSensor.isOnline()) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }
        try {
            colorSensor.set_workingMode(YColorSensor.WORKINGMODE_AUTO); // Set Working Mode Auto
            colorSensor.set_estimationModel(YColorSensor.ESTIMATIONMODEL_REFLECTION); // Set Prediction Model Reflexion
            while (colorSensor.isOnline()) {
                System.out.println("Near color : " + colorSensor.get_nearSimpleColor());
                System.out.println("RGB HEX : #" + colorSensor.get_estimatedRGB());
                System.out.println("------------------------------------");
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(2000);
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
