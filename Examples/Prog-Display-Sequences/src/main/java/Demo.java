
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

        YDisplay disp;
        YDisplayLayer l0;
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
            disp.resetAll();
            // retreive the display size
            int w = disp.get_displayWidth();
            int h = disp.get_displayHeight();
            //reteive the first layer
            l0 = disp.get_displayLayer(0);
            int count = 8;
            int[] coord = new int[2 * count + 1];
            // precompute the "leds" position
            int ledwidth = (w / count);
            for (int i = 0; i < count; i++) {
                coord[i] = i * ledwidth;
                coord[2 * count - i - 2] = coord[i];
            }
            int framesCount = 2 * count - 2;
            // start recording
            disp.newSequence();
            // build one loop for recording
            for (int i = 0; i < framesCount; i++) {
                l0.selectColorPen(0);
                l0.drawBar(coord[(i + framesCount - 1) % framesCount], h - 1, coord[(i + framesCount - 1) % framesCount] + ledwidth, h - 4);
                l0.selectColorPen(0xffffff);
                l0.drawBar(coord[i], h - 1, coord[i] + ledwidth, h - 4);
                disp.pauseSequence(100);  // records a 50ms pause.
            }
            // self-call : causes an endless looop
            disp.playSequence("K2000.seq");
            // stop recording and save to device filesystem
            disp.saveSequence("K2000.seq");
            // play the sequence
            disp.playSequence("K2000.seq");
            System.out.println("This animation is running in background.");
        } catch (YAPI_Exception ex) {
            System.out.println("Exception:" + ex.getLocalizedMessage() + "\n" + ex.getStackTraceToString());
            System.out.println("Module not connected (check identification and USB cable)");
        }

        YAPI.FreeAPI();
    }
}
