/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
 *
 *  An example that shows how to use a  Yocto-PowerColor
 *
 *  You can find more information on our web site:
 *   Yocto-PowerColor documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-powercolor/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

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
        YColorLed led1;
        if (args.length > 0) {
            led1 = YColorLed.FindColorLed(args[0] + ".colorLed1");
        } else {
            led1 = YColorLed.FirstColorLed();
            if (led1 == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        int all_colors[] = {0xff0000,0x00ff00,0x0000ff};
        for(int color : all_colors) {
            try {
                System.out.println(String.format("Change color to 0x%06x", color));
                led1.set_rgbColor(color);//led2.rgbMove(color, 1000); // smooth transition
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                System.out.println("Module not connected (check identification and USB cable)");
                break;
            }
        }

        YAPI.FreeAPI();
    }
}
