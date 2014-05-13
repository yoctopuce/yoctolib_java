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
        if (args.length > 0) {
            serial = args[0];
        } else {
            YServo tmp = YServo.FirstServo();
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
        YServo servo1 = YServo.FindServo(serial + ".servo1");
        YServo servo5 = YServo.FindServo(serial + ".servo5");



        int pos[] = {-1000,1000,0};
        for(int p : pos) {
            try {
                System.out.println(String.format("Change postition to %d", p));
                servo1.set_position(p);//imediat transition
                servo5.move(p, 1000); // smooth transition
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module not connected (check identification and USB cable)");
                break;
            }
        }

        YAPI.FreeAPI();
    }
}
