/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.yoctopuce.YoctoAPI.*;

/**
 *
 * @author yocto
 */
public class Demo {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)   {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        YLed led;
        if (args.length > 0) {
            led = YLed.FindLed(args[0]);
        } else {
            led = YLed.FirstLed();
            if (led == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        try {
            System.out.println("Switch led ON");
            led.set_power(YLed.POWER_ON);
            YAPI.Sleep(1000);
            System.out.println("Switch led OFF");
            led.set_power(YLed.POWER_OFF);
        } catch (YAPI_Exception ex) {
            System.out.println("Module "+led.describe()+" not connected (check identification and USB cable)");
        }

        YAPI.FreeAPI();
    }
}
