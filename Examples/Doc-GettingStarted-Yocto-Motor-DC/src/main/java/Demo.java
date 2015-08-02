import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void main(String[] args)   {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        String serial = "";
        if (args.length > 0 && !args[0].equalsIgnoreCase("any")) {
            serial = args[0];
        } else {
            YMotor tmp = YMotor.FirstMotor();
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
        int power = 100;
        if (args.length>1) {
            power = Integer.valueOf(args[1]);
        }

        YMotor motor = YMotor.FindMotor(serial + ".motor");
        YCurrent current = YCurrent.FindCurrent(serial + ".current");
        YVoltage voltage = YVoltage.FindVoltage(serial + ".voltage");
        YTemperature temperature = YTemperature.FindTemperature(serial + ".temperature");

        // lets start the motor
        if (motor.isOnline()) {
            // if motor is in error state, reset it.
            try {
                if (motor.get_motorStatus() >= YMotor.MOTORSTATUS_LOVOLT)
                    motor.resetStatus();
                motor.drivingForceMove(power, 2000);  // ramp up to power in 2 seconds
                for (int i = 0 ; i < 60; i++) {
                    // display motor status
                    System.out.println("Status=" + motor.get_advertisedValue() +
                            "  Voltage=" + voltage.get_currentValue() + voltage.get_unit() +
                            "  Current=" + current.get_currentValue()  + current.get_unit()+
                            "  Temp=" + temperature.get_currentValue() + temperature.get_unit());
                    YAPI.Sleep(1000); // wait for one second
                }
            } catch (YAPI_Exception e) {
                e.printStackTrace();
            }
        }

        YAPI.FreeAPI();
    }

    private static void usage()
    {
        System.err.println("Usage:");
        System.err.println("  demo <serial_number> power");
        System.err.println("  demo <logical_name> power");
        System.err.println("  demo any power");
        System.err.println("     power is a integer between -100 and 100%");
        System.err.println("Example:");
        System.err.println("  demo any 75");
        System.exit(0);
    }
}
