/*
 *
 *  $Id: Demo.java 55641 2023-07-26 09:43:42Z seb $
 *
 *  An example that show how to use a  Yocto-MaxiKnob
 *
 *  You can find more information on our web site:
 *   Yocto-MaxiKnob documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-maxiknob/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.*;

import static java.lang.Math.exp;
import static java.lang.Math.log;

public class Demo {

    public static void main(String[] args) {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }
        YBuzzer buzzer;
        if (args.length > 0) {
            buzzer = YBuzzer.FindBuzzer(args[0]);
        } else {
            buzzer = YBuzzer.FirstBuzzer();
            if (buzzer == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }
        if (!buzzer.isOnline()) {
            System.out.println("Module not connected (check identification and USB cable)");
            System.exit(1);
        }

        try {
            String serial = buzzer.get_module().get_serialNumber();
            YColorLedCluster leds = YColorLedCluster.FindColorLedCluster(serial + ".colorLedCluster");
            YAnButton button = YAnButton.FindAnButton(serial + ".anButton1");
            YQuadratureDecoder qd = YQuadratureDecoder.FindQuadratureDecoder(serial + ".quadratureDecoder1");

            int lastPos = (int) qd.get_currentValue();
            buzzer.set_volume(75);
            System.out.println("press  button #1, turn encoder #1  or hit Ctrl-C");
            while (button.isOnline()) {
                if ((button.get_isPressed() == YAnButton.ISPRESSED_TRUE) && (lastPos != 0)) {
                    lastPos = 0;
                    qd.set_currentValue(0);
                    buzzer.playNotes("'E32 C8");
                    leds.set_rgbColor(0, 1, 0x000000);
                } else {
                    int p = (int) qd.get_currentValue();
                    if (lastPos != p) {
                        lastPos = p;
                        buzzer.pulse(notefreq(p), 100);
                        leds.set_hslColor(0, 1, 0x00FF7f | (p % 255) << 16);
                    }
                }
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }

    private static int notefreq(int note) {
        return (int) (220.0 * exp(note * log(2) / 12));
    }
}
