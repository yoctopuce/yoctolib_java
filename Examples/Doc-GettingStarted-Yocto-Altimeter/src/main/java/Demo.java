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


        YAltitude asensor;
        YTemperature tsensor;
        YPressure psensor;

        if (args.length == 0) {
            asensor = YAltitude.FirstAltitude();
            tsensor = YTemperature.FirstTemperature();
            psensor = YPressure.FirstPressure();
            if (asensor == null || tsensor == null || psensor == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            asensor = YAltitude.FindAltitude(args[0] + ".altitude");
            tsensor = YTemperature.FindTemperature(args[0] + ".temperature");
            psensor = YPressure.FindPressure(args[0] + ".pressure");
        }

        while (true) {
            try {
                System.out.println("Current altitude: " + asensor.get_currentValue() + " m "
                                  +"(QNH=" + asensor.get_qnh() + " hpa)");
                System.out.println("Current temperature: " + tsensor.get_currentValue() + " °C");
                System.out.println("Current pressure: " + psensor.get_currentValue() + " hPa");
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
