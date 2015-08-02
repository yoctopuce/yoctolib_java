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


        YHumidity hsensor;
        YTemperature tsensor;
        YPressure psensor;

        if (args.length == 0) {
            hsensor = YHumidity.FirstHumidity();
            tsensor = YTemperature.FirstTemperature();
            psensor = YPressure.FirstPressure();
            if (hsensor == null || tsensor == null || psensor == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            hsensor = YHumidity.FindHumidity(args[0] + ".humidity");
            tsensor = YTemperature.FindTemperature(args[0] + ".temperature");
            psensor = YPressure.FindPressure(args[0] + ".pressure");
        }

        while (true) {
            try {
                System.out.println("Current humidity: " + hsensor.get_currentValue() + " %RH");
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
