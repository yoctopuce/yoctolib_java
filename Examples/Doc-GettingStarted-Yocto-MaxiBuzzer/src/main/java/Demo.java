/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
 *
 *  An example that shows how to use a  Yocto-MaxiBuzzer
 *
 *  You can find more information on our web site:
 *   Yocto-MaxiBuzzer documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-maxibuzzer/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.*;

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
        System.out.println("press a test button or hit Ctrl-C");
        try {
            int frequency = 1000;
            int volume, color;
            String serial = buzzer.get_module().get_serialNumber();
            YColorLed led = YColorLed.FindColorLed(serial + ".colorLed");
            YAnButton button1 = YAnButton.FindAnButton(serial + ".anButton1");
            YAnButton button2 = YAnButton.FindAnButton(serial + ".anButton2");
            while (true) {
                Boolean b1 = (button1.get_isPressed() == YAnButton.ISPRESSED_TRUE);
                Boolean b2 = (button2.get_isPressed() == YAnButton.ISPRESSED_TRUE);
                if (b1 || b2) {
                    if (b1) {
                        volume = 60;
                        frequency = 1500;
                        color = 0xff0000;
                    } else {
                        volume = 30;
                        color = 0x00ff00;
                        frequency = 750;
                    }
                    led.resetBlinkSeq();
                    led.addRgbMoveToBlinkSeq(color, 100);
                    led.addRgbMoveToBlinkSeq(0, 100);
                    led.startBlinkSeq();
                    buzzer.set_volume(volume);
                    int i;
                    for (i = 0; i < 5; i++) {
                        // this can be done using sequence as well
                        buzzer.set_frequency(frequency);
                        buzzer.freqMove(2 * frequency, 250);
                        YAPI.Sleep(250);
                    }
                    buzzer.set_frequency(0);
                    led.stopBlinkSeq();
                    led.set_rgbColor(0);
                }
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
