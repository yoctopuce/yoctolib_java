/*
 *
 *  $Id: Demo.java 75124 2026-07-06 09:15:16Z seb $
 *
 *  An example that shows how to use a  Yocto-Display-ePaper
 *
 *  You can find more information on our web site:
 *   Yocto-Display-ePaper documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-display-epaper/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

/*
 *
 *  $Id: Demo.java 75124 2026-07-06 09:15:16Z seb $
 *
 *  An example that shows how to use a  Yocto-Display
 *
 *  You can find more information on our web site:
 *   Yocto-Display documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-display/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import java.util.*;

import com.yoctopuce.YoctoAPI.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo
{


    public static void main(String[] args)
    {

        YDisplay disp;
        int[] colors = {0xFFFFFF, 0x000000, 0xFF0000, 0xFFFF00};

        // API init
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        if (args.length == 0) {
            disp = YDisplay.FirstDisplay();
            if (disp == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            disp = YDisplay.FindDisplay(args[0] + ".display");
        }

        try {

            // Makes sure the Panel type is set
            String paneltype = disp.get_displayPanel();
            if (Objects.equals(paneltype, "NOT_SET")) {
                System.out.println("Use the virtual to Configure the panel first");
                YAPI.Sleep(3000);
                System.exit(1);
            }

            // retrieve the display size
            int w = disp.get_displayWidth();
            int h = disp.get_displayHeight();
            int middleX = (int) (w / 2);
            int middleY = (int) (h / 2);
            System.out.printf("Using device %s (panel: %s  %d x %d pixels)\n", disp.get_serialNumber(), paneltype, w, h);

            // retrieve the first layer
            YDisplayLayer l0 = disp.get_displayLayer(0);
            l0.selectFont("medium.yfm");
            boolean animation = true;
            disp.triggerRefresh(); // make sure next refresh will be a full one
            Random rnd = new Random();

            while (animation) {
                // prevent refreshing for 2 sec
                disp.postponeRefresh(2000);
                l0.clear();
                // draw a few circle
                for (int i = 0; i < 15; i++) {
                    int cx = rnd.nextInt(w);
                    int cy = rnd.nextInt(h);
                    int r = (int) (h / 20) + rnd.nextInt((int) (h / 10));
                    l0.selectFillColor(colors[rnd.nextInt(4)]);
                    l0.drawDisc(cx, cy, r);
                    l0.drawCircle(cx, cy, r);
                }
                // draw a rectangle with panel type in it
                l0.selectFillColor(0xffffff);
                l0.drawBar(middleX - 75, middleY - 10, middleX + 75, middleY + 12);
                l0.drawRect(middleX - 75, middleY - 10, middleX + 75, middleY + 12);
                l0.drawText(middleX, middleY, YDisplayLayer.ALIGN.CENTER, paneltype);
                disp.triggerRefresh(); // display is allowed to refresh  again
                YAPI.Sleep(1000);
                // if no fast refresh available, don't even try to run animations
                if (paneltype.indexOf("KS") < 0) animation = false;
            }

        } catch (YAPI_Exception ex) {
            System.out.println("Exception durring execution (" + ex.getLocalizedMessage() + ")");
            YAPI.FreeAPI();
            System.exit(1);
        }

    }
}
