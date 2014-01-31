
import com.yoctopuce.YoctoAPI.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    static class EventHandler implements YAPI.DeviceArrivalCallback {

       // called each time a new device (networked or not) is detected
        @Override
        public void yDeviceArrival(YModule dev) {
            try {
                boolean isAHub = false;
                // iterate on all functions on the module and find the ports
                int fctCount = dev.functionCount();
                for (int i = 0; i < fctCount; i++) {
                    // retreive the hardware name of the ith function
                    String fctHwdName = dev.functionId(i);
                    if (fctHwdName.length() > 7 && "hubPort".equals(fctHwdName.substring(0, 7))) {
                        // the device contains a  hubPortx function, so it's a hub
                        if (!isAHub) {
                            System.out.println("hub found : " + dev.get_friendlyName());
                            isAHub = true;
                        }
                            // The port logical name is always the serial#
                        // of the connected device
                        String deviceid = dev.functionName(i);
                        System.out.println(" " + fctHwdName + " : " + deviceid);
                    }

                }
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        EventHandler handlers = new EventHandler();

        System.out.println("Waiting for hubs to signal themselves...");
        try {
            // configure the API to contact any networked device
            YAPI.RegisterHub("net");
        } catch (YAPI_Exception ex) {
            System.out.println("RegisterHub error: " + ex.getLocalizedMessage());
            return;
        }

        // each time a new device is connected/discovered
        // arrivalCallback will be called.
        YAPI.RegisterDeviceArrivalCallback(handlers);

        // wait for 30 seconds, doing nothing.
        for (int i = 0; i < 30; i++) {
            try {
                YAPI.UpdateDeviceList();
                YAPI.Sleep(1000);
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        YAPI.FreeAPI();
    }
}
