/*
 *
 *  $Id: Demo.java 55641 2023-07-26 09:43:42Z seb $
 *
 *  An example that show how to use a  Yocto-I2C
 *
 *  You can find more information on our web site:
 *   Yocto-I2C documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-i2c/doc.html
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

        YI2cPort i2cPort;
        if (args.length > 0) {
            i2cPort = YI2cPort.FindI2cPort(args[0] + ".i2cPort");
        } else {
            i2cPort = YI2cPort.FirstI2cPort();
        }
        if (i2cPort == null || !i2cPort.isOnline()) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }
        try {
            // sample code reading MCP9804 temperature sensor
            i2cPort.set_i2cMode("100kbps");
            i2cPort.set_i2cVoltageLevel(YI2cPort.I2CVOLTAGELEVEL_3V3);
            i2cPort.reset();
            // do not forget to configure the powerOutput of the Yocto-I2C
            // (for MCP9804 powerOutput need to be set at 3.3V)
            System.out.println("****************************");
            System.out.println("* make sure voltage levels *");
            System.out.println("* are properly configured  *");
            System.out.println("****************************");

            ArrayList<Integer> toSend = new ArrayList<>(1);
            ArrayList<Integer> received;
            toSend.add(0x05);
            received = i2cPort.i2cSendAndReceiveArray(0x1f, toSend, 2);
            int tempReg = (received.get(0) << 8) + received.get(1);
            if((tempReg & 0x1000) != 0) {
                tempReg -= 0x2000;   // perform sign extension
            } else {
                tempReg &= 0x0fff;   // clear status bits
            }
            System.out.println(String.format("Ambiant temperature: %.3f", tempReg/16.0));
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
