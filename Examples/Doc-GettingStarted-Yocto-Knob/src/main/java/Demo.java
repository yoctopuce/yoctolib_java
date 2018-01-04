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
            YAnButton tmp = YAnButton.FirstAnButton();
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
        YAnButton input1 = YAnButton.FindAnButton(serial + ".anButton1");
        YAnButton input5 = YAnButton.FindAnButton(serial + ".anButton5");


        while (true) {
            try {
                if (input1.get_isPressed() == YAnButton.ISPRESSED_TRUE) {
                    System.out.print("Button 1: pressed      ");
                } else {
                    System.out.print("Button 1: not pressed  ");
                }
                System.out.println("- analog value:  " + input1.get_calibratedValue());
                if (input5.get_isPressed() == YAnButton.ISPRESSED_TRUE) {
                    System.out.print("Button 5: pressed      ");
                } else {
                    System.out.print("Button 5: not pressed  ");
                }
                System.out.println("- analog value:  " + input5.get_calibratedValue());
                System.out.println("(press both buttons simultaneously to exit)");
                if (input1.get_isPressed() == YAnButton.ISPRESSED_TRUE &&
                    input5.get_isPressed() == YAnButton.ISPRESSED_TRUE)
                    break;
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module not connected (check identification and USB cable)");
                break;
            }

        }

        YAPI.FreeAPI();
    }
}
