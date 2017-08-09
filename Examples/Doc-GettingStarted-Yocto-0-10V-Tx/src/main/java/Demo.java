import com.yoctopuce.YoctoAPI.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    public static void usage() {
        System.out.println("Usage");
        System.out.println("demo <serial_number> <voltage>");
        System.out.println("demo <logical_name>  <voltage>");
        System.out.println("demo any  <voltage>   (use any discovered device)");
        System.out.println("     <voltage>: floating point number between 0.0 and 10.000");
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
        double voltage = Double.valueOf(args[1]);
  
        if (target.equals("ANY")) {
            YVoltageOutput tmp = YVoltageOutput.FirstVoltageOutput();
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
    
        YVoltageOutput vout1 = YVoltageOutput.FindVoltageOutput(target + ".voltageOutput1");
        YVoltageOutput vout2 = YVoltageOutput.FindVoltageOutput(target + ".voltageOutput2");
        try {
            // output 1 : immediate change
            vout1.set_currentVoltage(voltage);
            // output 2 : smooth change
            vout2.voltageMove(voltage,3000);
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}