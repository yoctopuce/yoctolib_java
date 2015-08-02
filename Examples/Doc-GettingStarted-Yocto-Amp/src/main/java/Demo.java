import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void demo(String ac_hardwareid, String dc_hardwareid)
    {

        // we need to retreive both DC and AC voltage from the device.
        YCurrent sensorDC = YCurrent.FindCurrent(dc_hardwareid);
        YCurrent sensorAC = YCurrent.FindCurrent(ac_hardwareid);

        while (true) {
            try {
                System.out.println("DC: " + sensorDC.get_currentValue() + " mA ");
                System.out.println("AC: " + sensorAC.get_currentValue() + " mA ");
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module "+sensorDC.module()+" is not connected");
                return;
            }
        }
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

        String serial="";
        if (args.length > 0) {
            serial = args[0];
        } else {
            YCurrent sensor = YCurrent.FirstCurrent();
            if (sensor == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
            try {
                serial = sensor.module().get_serialNumber();
            } catch (YAPI_Exception ex) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }
        Demo.demo(serial + ".current1",serial + ".current2");

        YAPI.FreeAPI();
    }
}
