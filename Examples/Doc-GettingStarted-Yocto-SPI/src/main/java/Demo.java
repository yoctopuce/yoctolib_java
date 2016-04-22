import com.yoctopuce.YoctoAPI.*;

import java.util.ArrayList;

public class Demo
{

    public static void usage()
    {
        System.out.println("Usage");
        System.out.println("demo <serial_number> <value>");
        System.out.println("demo <logical_name>  <value>");
        System.out.println("demo any  <value>   (use any discovered device)");
        System.exit(1);
    }

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

        if (args.length < 2) usage();
        String target = args[0].toUpperCase();
        int value = Integer.valueOf(args[1]);

        if (target.equals("ANY")) {
            YSpiPort tmp = YSpiPort.FirstSpiPort();
            if (tmp == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
            try {
                target = tmp.module().get_serialNumber();
            } catch (YAPI_Exception ex) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        YSpiPort spiPort = YSpiPort.FindSpiPort(target + ".spiPort");
        try {
            // sample code driving MAX7219 7-segment display driver
            // such as SPI7SEGDISP8.56 from www.embedded-lab.com
            spiPort.set_spiMode("250000,2,msb");
            spiPort.set_ssPolarity(YSpiPort.SSPOLARITY_ACTIVE_LOW);
            spiPort.set_protocol("Frame:5ms");
            spiPort.reset();
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
