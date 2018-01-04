import com.yoctopuce.YoctoAPI.*;

public class Demo
{

    public static void usage()
    {
        System.out.println("Bad command line arguments");
        System.out.println("Usage:");
        System.out.println(" demo <serial_number>  value");
        System.out.println(" demo <logical_name> value");
        System.out.println(" demo value");
        System.out.println("Eg.");
        System.out.println(" demo 15");
        System.out.println(" demo TX420MA1-123456 4");
        System.exit(1);
    }

    public static void main(String[] args)
    {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            return;
        }

        YCurrentLoopOutput loop;
        if (args.length > 1) {
            loop = YCurrentLoopOutput.FindCurrentLoopOutput(args[0]);
        } else {
            loop = YCurrentLoopOutput.FirstCurrentLoopOutput();
            if (loop == null) {
                System.out.println("No module connected (check USB cable)");
                return;
            }
        }

        if (args.length < 1) usage();
        try {
            double value = Double.valueOf(args[args.length - 1]);
            loop.set_current(value);
            int loopPower = loop.get_loopPower();
            if (loopPower == YCurrentLoopOutput.LOOPPOWER_NOPWR) {
                System.out.println("Current loop not powered");
                return;
            }
            if (loopPower == YCurrentLoopOutput.LOOPPOWER_LOWPWR) {
                System.out.println("Insufficient voltage on current loop");
                return;
            }
            System.out.println("current loop set to " + value + " mA");

        } catch (YAPI_Exception e) {
            System.out.println("Module " + loop.describe() + " disconnected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}