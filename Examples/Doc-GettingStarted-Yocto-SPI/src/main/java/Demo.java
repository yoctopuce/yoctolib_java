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


        YSpiPort spiPort;
        if (args.length > 0) {
            spiPort = YSpiPort.FindSpiPort(args[0] + ".spiPort");
        } else {
            spiPort = YSpiPort.FirstSpiPort();
        }
        if (spiPort == null || !spiPort.isOnline()) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }
        int value = 12345678;

        try {
            // sample code driving MAX7219 7-segment display driver
            // such as SPI7SEGDISP8.56 from www.embedded-lab.com
            spiPort.set_spiMode("250000,3,msb");
            spiPort.set_ssPolarity(YSpiPort.SSPOLARITY_ACTIVE_LOW);
            spiPort.set_protocol("Frame:5ms");
            spiPort.reset();
            // do not forget to configure the powerOutput of the Yocto-SPI
            // ( for SPI7SEGDISP8.56 powerOutput need to be set at 5v )
            System.out.println("****************************");
            System.out.println("* make sure voltage levels *");
            System.out.println("* are properly configured  *");
            System.out.println("****************************");

            spiPort.writeHex("0c01"); // Exit from shutdown state
            spiPort.writeHex("09ff"); // Enable BCD for all digits
            spiPort.writeHex("0b07"); // Enable digits 0-7 (=8 in total)
            spiPort.writeHex("0a0a"); // Set medium brightness
            for (int i = 1; i <= 8; i++) {
                int digit = value % 10;
                ArrayList<Integer> dataToWrite = new ArrayList<>(2);
                dataToWrite.add(i);
                dataToWrite.add(digit);
                spiPort.writeArray(dataToWrite);
                value = value / 10;
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
