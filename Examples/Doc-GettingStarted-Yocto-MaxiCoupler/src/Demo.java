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

        String serial = "";
        if (args.length > 0) {
            serial = args[0];
        } else {
            YRelay tmp = YRelay.FirstRelay();
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

        System.out.println("We will use");

        try {

            System.out.println("Switch on all output");
            for (int channel = 1; channel < 9; channel++) {
                YRelay relay = YRelay.FindRelay(serial + ".relay" + channel);
                relay.set_output(YRelay.OUTPUT_ON);
                YAPI.Sleep(100);
            }
            YAPI.Sleep(500);
            System.out.println("Switch off all output");
            for (int channel = 1; channel < 9; channel++) {
                YRelay relay = YRelay.FindRelay(serial + ".relay" + channel);
                relay.set_output(YRelay.OUTPUT_OFF);
                YAPI.Sleep(100);
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }

        YAPI.FreeAPI();

    }
}
