import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void main(String[] args)  {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }
        YColorLed led1,led2;
        if (args.length > 0) {
            led1 = YColorLed.FindColorLed(args[0] + ".colorLed1");
            led2 = YColorLed.FindColorLed(args[0] + ".colorLed2");
        } else {
            led1 = YColorLed.FirstColorLed();
            if (led1 == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
            led2 = led1.nextColorLed();
        }

        int all_colors[] = {0xff0000,0x00ff00,0x0000ff};
        for(int color : all_colors) {
            try {
                System.out.println(String.format("Change color to 0x%06x", color));
                led1.set_rgbColor(color);//led2.rgbMove(color, 1000); // smooth transition
                led2.rgbMove(color, 1000); // smooth transition
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module not connected (check identification and USB cable)");
                break;
            }
        }

        YAPI.FreeAPI();
    }
}
