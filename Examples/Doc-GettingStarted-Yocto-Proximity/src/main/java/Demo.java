import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YLightSensor;
import com.yoctopuce.YoctoAPI.YProximity;

public class Demo
{

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

        YProximity proximity;
        if (args.length > 0) {
            proximity = YProximity.FindProximity(args[0] + ".proximity1");
        } else {
            proximity = YProximity.FirstProximity();
            if (proximity == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        if (!proximity.isOnline()) {
            System.out.println("Module not connected (check identification and USB cable)");
            System.exit(1);
        }
        System.out.println("press a test button or hit Ctrl-C");
        try {
            String serial = proximity.get_module().get_serialNumber();
            YLightSensor ambiant = YLightSensor.FindLightSensor(serial + ".lightSensor1");
            YLightSensor ir = YLightSensor.FindLightSensor(serial + ".lightSensor2");
            while (true) {
                System.out.println(String.format("Proximity: %f Ambiant: %f IR:  %f",
                        proximity.get_currentValue(), ambiant.get_currentValue(), ir.get_currentValue()));
                YAPI.Sleep(1000);
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module " + proximity + "not connected (check identification and USB cable)");
            System.out.println(ex.getMessage());
            System.exit(1);

        }
    }
}
