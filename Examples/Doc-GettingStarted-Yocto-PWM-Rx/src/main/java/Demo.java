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
            //  retreive any pwm input available
            YPwmInput tmp = YPwmInput.FirstPwmInput();
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

        // we need to retreive both DC and AC pwmInput from the device.
        YPwmInput pwm1 = YPwmInput.FindPwmInput(serial + ".pwmInput1");
        YPwmInput pwm2 = YPwmInput.FindPwmInput(serial + ".pwmInput2");

        while (true) {
            try {
                System.out.println("PWM1: " + pwm1.get_frequency() + "Hz "
                                   + pwm1.get_dutyCycle() + "% "
                                   + pwm1.get_pulseCounter() + " pulse edges " );
                System.out.println("PWM2: " + pwm2.get_frequency() + "Hz "
                                   + pwm2.get_dutyCycle() + "% "
                                   + pwm2.get_pulseCounter() + " pulse edges " );
                System.out.println("  (press Ctrl-C to exit)");
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module " + pwm1.describe() + " is not connected (check identification and USB cable)");
                break;
            }

        }
        YAPI.FreeAPI();
    }
}
