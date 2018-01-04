import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void main(String[] args)   {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        String serial = "";
        if (args.length == 0) {
            YTemperature tmp = YTemperature.FirstTemperature();
            if (tmp == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
            try {
                serial = tmp.module().get_serialNumber();
            } catch (YAPI_Exception ex) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            serial = args[0];
        }

        // retreive both channels
        YTemperature ch1 = YTemperature.FindTemperature(serial + ".temperature1");
        YTemperature ch2 = YTemperature.FindTemperature(serial + ".temperature2");
        YTemperature ch3 = YTemperature.FindTemperature(serial + ".temperature3");
        YTemperature ch4 = YTemperature.FindTemperature(serial + ".temperature4");
        YTemperature ch5 = YTemperature.FindTemperature(serial + ".temperature5");
        YTemperature ch6 = YTemperature.FindTemperature(serial + ".temperature6");
   
        while (true) {
            try {
                System.out.print("| 1: " + ch1.get_currentValue());
                System.out.print("| 2: " + ch2.get_currentValue());
                System.out.print("| 3: " + ch2.get_currentValue());
                System.out.print("| 4: " + ch2.get_currentValue());
                System.out.print("| 5: " + ch2.get_currentValue());
                System.out.print("| 6: " + ch2.get_currentValue());
                System.out.println(" | deg C|");
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module not connected (check identification and USB cable)");
                break;
            }
        }

        YAPI.FreeAPI();
    }
}
