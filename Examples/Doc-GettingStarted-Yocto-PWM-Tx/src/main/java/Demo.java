import com.yoctopuce.YoctoAPI.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    public static void usage() {
        System.out.println("Usage");
        System.out.println("demo <serial_number>  <frequency> <dutyCycle>");
        System.out.println("demo <logical_name> <frequency> <dutyCycle>");
        System.out.println("demo any  <frequency> <dutyCycle>   (use any discovered device)");
        System.out.println("     <frequency>: integer between 1Hz and 1000000Hz");
        System.out.println("     <dutyCycle>: floating point number between 0.0 and 100.0");
        System.exit(1);
    }

    public static void main(String[] args)   {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        if (args.length < 3) usage();
        String target = args[0].toUpperCase();
        int frequency = Integer.valueOf(args[1]);
        double dutyCycle = Double.valueOf(args[2]);
  
        if (target.equals("ANY")) {
            YPwmOutput tmp = YPwmOutput.FirstPwmOutput();
            if (tmp == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
            try {
                target = tmp.module().get_serialNumber();
            } catch (YAPI_Exception ex) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }
    
        YPwmOutput pwmoutput1 = YPwmOutput.FindPwmOutput(target + ".pwmOutput1");
        YPwmOutput pwmoutput2 = YPwmOutput.FindPwmOutput(target + ".pwmOutput2");
        try {
            // output 1 : immediate change
            pwmoutput1.set_frequency(frequency);
            pwmoutput1.set_enabled(YPwmOutput.ENABLED_TRUE);
            pwmoutput1.set_dutyCycle(dutyCycle);
            // output 2 : smooth change
            pwmoutput2.set_frequency(frequency);
            pwmoutput2.set_enabled(YPwmOutput.ENABLED_TRUE);
            pwmoutput2.dutyCycleMove(dutyCycle,3000);
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
