/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
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

        YSpectralSensor spectralSensor;
        if (args.length > 0) {
            spectralSensor = YI2cPort.FindSpectralSensor(args[0] + ".spectralSensor");
        } else {
            spectralSensor = YI2cPort.FirstSpectralSensor();
        }
        if (spectralSensorspectralSensor == null || !spectralSensor.isOnline()) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }
        try {
            spectralSensor.set_gain(6);
            spectralSensor.set_integrationTime(150);
            spectralSensor.set_ledCurrent(6);

            System.out.println("Near estimated HTML color detected: " + spectralSensor.get_nearHTMLColor());
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
