/*
 *
 *  $Id: Demo.java 32627 2018-10-10 13:37:29Z seb $
 *
 *  An example that show how to use a  Yocto-Color-V2
 *
 *  You can find more information on our web site:
 *   Yocto-Color-V2 documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-color-v2/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YColorLedCluster;

public class Demo
{

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
        YColorLedCluster ledCluster;
        if (args.length > 0) {
            ledCluster = YColorLedCluster.FindColorLedCluster(args[0] + ".colorLed1");
        } else {
            ledCluster = YColorLedCluster.FirstColorLedCluster();
            if (ledCluster == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }
        try {

            int nb_leds = 2;
            ledCluster.set_activeLedCount(nb_leds);
            ledCluster.set_ledType(YColorLedCluster.LEDTYPE_RGB);

            int all_colors[] = {0xff0000, 0x00ff00, 0x0000ff};
            for (int color : all_colors) {
                System.out.println(String.format("Change color to 0x%06x", color));

                // immediate transition for fist half of leds
                ledCluster.set_rgbColor(0, nb_leds / 2, color);
                // immediate transition for second half of leds
                ledCluster.rgb_move(nb_leds / 2, nb_leds / 2, color, 2000);

                YAPI.Sleep(2000);

            }

        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }

        YAPI.FreeAPI();
    }
}
