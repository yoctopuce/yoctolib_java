/*
 *
 *  $Id: Demo.java 58172 2023-11-30 17:10:23Z martinm $
 *
 *  An example that shows how to use a  Yocto-PowerRelay
 *
 *  You can find more information on our web site:
 *   Yocto-PowerRelay documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-powerrelay/doc.html
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

        YRelay relay;
        if (args.length > 0) {
            relay = YRelay.FindRelay(args[0]);
        } else {
            relay = YRelay.FirstRelay();
            if (relay == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        try {

            System.out.println("Switch relay to B");
            relay.set_state(YRelay.STATE_B);
            YAPI.Sleep(1000);
            System.out.println("Switch relay to A");
            relay.set_state(YRelay.STATE_A);
        } catch (YAPI_Exception ex) {
            System.out.println("Module "+relay.describe()+" not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();   
    }
}
