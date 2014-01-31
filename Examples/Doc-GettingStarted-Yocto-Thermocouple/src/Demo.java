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
   
        while (true) {
            try {
                System.out.println("Channel 1 temperature: " + ch1.get_currentValue() + " C");
                System.out.println("Channel 2 temperature: " + ch2.get_currentValue() + " C");
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module not connected (check identification and USB cable)");
                break;
            }
        }

        YAPI.FreeAPI();
    }
}
