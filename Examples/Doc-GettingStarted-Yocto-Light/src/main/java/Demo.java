import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void main(String[] args)
    {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("http://127.0.0.1:4444/");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        YLightSensor sensor;
        if (args.length > 0) {
            sensor = YLightSensor.FindLightSensor(args[0] + ".lightSensor");
        } else {
            sensor = YLightSensor.FirstLightSensor();
            if (sensor == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }
        while (true) {
            try {
                System.out.println("Current ambient light: " + sensor.get_currentValue() + " lx");
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module " + sensor + "not connected (check identification and USB cable)");
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        }
    }
}
