/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
 *
 *  An example that shows how to use a  Yocto-SDI12
 *
 *  You can find more information on our web site:
 *   Yocto-SDI12 documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-sdi12/doc.html
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

        YSdi12Port sdi12Port;
        sdi12Port = YSdi12Port.FirstSdi12Port();
        if (sdi12Port == null || !sdi12Port.isOnline()) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }
        try {
            sdi12Port.reset();
            YSdi12Sensor singleSensor = sdi12Port.discoverSingleSensor();
            System.out.println(String.format("%-35s %s " ,"Sensor address :", singleSensor.get_sensorAddress()));
            System.out.println(String.format("%-35s %s " ,"Sensor SDI-12 compatibility : " , singleSensor.get_sensorProtocol()));
            System.out.println(String.format("%-35s %s " ,"Sensor company name : " , singleSensor.get_sensorVendor()));
            System.out.println(String.format("%-35s %s " ,"Sensor model number : " , singleSensor.get_sensorModel()));
            System.out.println(String.format("%-35s %s " ,"Sensor version : " , singleSensor.get_sensorVersion()));
            System.out.println(String.format("%-35s %s " ,"Sensor serial number : " , singleSensor.get_sensorSerial()));

            ArrayList<Double> valSensor = sdi12Port.readSensor(singleSensor.get_sensorAddress(),"M",5000);

            for (int i = 0; i < valSensor.size(); i = i+1)
            {
                if (singleSensor.get_measureCount() > 1)
                {
                    System.out.println(String.format("%s : %-6.2f %-10s (%s)",
                            singleSensor.get_measureSymbol(i), valSensor.get(i), singleSensor.get_measureUnit(i), singleSensor.get_measureDescription(i)));
                }
                else {
                    System.out.print(valSensor.get(i));
                }

            }

        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }

        YAPI.FreeAPI();
    }
}
