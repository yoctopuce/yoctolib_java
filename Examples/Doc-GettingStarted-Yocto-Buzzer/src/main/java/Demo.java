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
            String serial = buzzer.get_module().get_serialNumber();
            YLed led1 = YLed.FindLed(serial + ".led1");
            YLed led2 = YLed.FindLed(serial + ".led2");
            YAnButton button1 = YAnButton.FindAnButton(serial + ".anButton1");
            YAnButton button2 = YAnButton.FindAnButton(serial + ".anButton2");
            while (true) {
                Boolean b1 = (button1.get_isPressed() == YAnButton.ISPRESSED_TRUE);
                Boolean b2 = (button2.get_isPressed() == YAnButton.ISPRESSED_TRUE);
                if (b1 || b2) {
                    YLed led;
                    if (b1) {
                        led = led1;
                        frequency = 1500;
                    } else {
                        led = led2;
                        frequency = 750;
                    }
                    led.set_power(YLed.POWER_ON);
                    led.set_luminosity(100);
                    led.set_blinking(YLed.BLINKING_PANIC);
                    int i;
                    for (i = 0; i < 5; i++) {
                        // this can be done using sequence as well
                        buzzer.set_frequency(frequency);
                        buzzer.freqMove(2 * frequency, 250);
                        YAPI.Sleep(250);
                    }
                    buzzer.set_frequency(0);
                    led.set_power(YLed.POWER_OFF);
                }
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
