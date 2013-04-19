import com.yoctopuce.YoctoAPI.*;

public class Demo {

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
        String serial = "";
        if (args.length > 0) {
            serial = args[0];
        } else {
            YVoltage tmp = YVoltage.FirstVoltage();
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
        }

        // we need to retreive both DC and AC voltage from the device.
        YVoltage sensorDC = YVoltage.FindVoltage(serial + ".voltage1");
        YVoltage sensorAC = YVoltage.FindVoltage(serial + ".voltage2");

        while (true) {
            try {
                System.out.println("DC: " + sensorDC.get_currentValue() + " v ");
                System.out.println("AC: " + sensorAC.get_currentValue() + " v ");
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module " + sensorAC.describe() + " is not connected (check identification and USB cable)");
                break;
            }

        }
        YAPI.FreeAPI();
    }
}
