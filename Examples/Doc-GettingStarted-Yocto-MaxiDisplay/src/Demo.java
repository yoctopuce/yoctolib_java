
import com.yoctopuce.YoctoAPI.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    private static void disp(YDisplay display, String text, YDisplayLayer.ALIGN al) throws YAPI_Exception
    {
        YDisplayLayer layer0 = display.get_displayLayer(0);
        int l = (int) display.get_displayWidth();
        int h = (int) display.get_displayHeight();
        int mx = l / 2;
        int my = h / 2;
        layer0.clear();
        layer0.moveTo(mx, 0);
        layer0.lineTo(mx, h);
        layer0.moveTo(0, my);
        layer0.lineTo(l, my);
        layer0.drawText(mx, my, al, text);
    }

    public static void main(String[] args)
    {

        YDisplay disp;
        YDisplayLayer l0, l1;
        int h, w, y, x, vx, vy;

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
            //clean up
            disp.resetAll();

            // retreive the display size
            w = disp.get_displayWidth();
            h = disp.get_displayHeight();

            // reteive the first layer
            l0 = disp.get_displayLayer(0);

            // display a text in the middle of the screen
            l0.drawText(w / 2, h / 2, YDisplayLayer.ALIGN.CENTER, "Hello world!");

            // visualize each corner
            l0.moveTo(0, 5);
            l0.lineTo(0, 0);
            l0.lineTo(5, 0);
            l0.moveTo(0, h - 6);
            l0.lineTo(0, h - 1);
            l0.lineTo(5, h - 1);
            l0.moveTo(w - 1, h - 6);
            l0.lineTo(w - 1, h - 1);
            l0.lineTo(w - 6, h - 1);
            l0.moveTo(w - 1, 5);
            l0.lineTo(w - 1, 0);
            l0.lineTo(w - 6, 0);

            // draw a circle in the top left corner of layer 1
            l1 = disp.get_displayLayer(1);
            l1.clear();
            l1.drawCircle(h / 8, h / 8, h / 8);

            // and animate the layer
            System.out.println("Use Ctrl-C to stop");
            x = 0;
            y = 0;
            vx = 1;
            vy = 1;
            while (true) {
                x += vx;
                y += vy;
                if ((x < 0) || (x > w - (h / 4))) {
                    vx = -vx;
                }
                if ((y < 0) || (y > h - (h / 4))) {
                    vy = -vy;
                }
                l1.setLayerPosition(x, y, 0);
                YAPI.Sleep(5);
            }

        } catch (YAPI_Exception ex) {
            System.out.println("Exception durring execution (" + ex.getLocalizedMessage() + ")");
            YAPI.FreeAPI();
            System.exit(1);
        }

    }
}
