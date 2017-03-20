import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YRangeFinder;
import com.yoctopuce.YoctoAPI.YLightSensor;
import com.yoctopuce.YoctoAPI.YTemperature;

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

        YRangeFinder rf;
        if (args.length > 0) {
            rf = YRangeFinder.FindRangeFinder(args[0] + ".rangeFinder1");
        } else {
            rf = YRangeFinder.FirstRangeFinder();
            if (rf == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        if (!rf.isOnline()) {
            System.out.println("Module not connected (check identification and USB cable)");
            System.exit(1);
        }
        System.out.println("press a test button or hit Ctrl-C");
        try {
            String serial = rf.get_module().get_serialNumber();
            YLightSensor ir = YLightSensor.FindLightSensor(serial + ".lightSensor1");
            YTemperature tmp = YTemperature.FindTemperature(serial + ".temperature1");
            while (true) {
                System.out.println(String.format("Distance    : %f", rf.get_currentValue()));
                System.out.println(String.format("Ambiant IR  : %f", ir.get_currentValue()));
                System.out.println(String.format("Temperature : %f", tmp.get_currentValue()));
                YAPI.Sleep(1000);
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module " + rf + " not connected (check identification and USB cable)");
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
}
