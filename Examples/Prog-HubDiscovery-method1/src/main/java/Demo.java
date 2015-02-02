
import com.yoctopuce.YoctoAPI.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    static class EventHandler implements YAPI.HubDiscoveryCallback {

        private  ArrayList<String> KnownHubs = new ArrayList<String>();

        @Override
        public void yHubDiscoveryCallback(String serial, String url) {
            try {
                // The call-back can be called several times for the same hub
                // (the discovery technique is based on a periodic broadcast)
                // So we use a dictionary to avoid duplicates
                if (KnownHubs.contains(serial)) return;
                
                System.out.println("hub found: " + serial+" ("+url+")");
                
                // connect to the hub
                YAPI.RegisterHub(url);
                
                //  find the hub module
                YModule hub = YModule.FindModule(serial);
                
                // iterate on all functions on the module and find the ports
                int fctCount =  hub.functionCount();
                for (int i=0;i< fctCount;i++) {
                    // retrieve the hardware name of the ith function
                    String fctHwdName = hub.functionId(i);
                    if (fctHwdName.length()>7 && "hubPort".equals(fctHwdName.substring(0,7))) {
                        // The port logical name is always the serial#
                        // of the connected device
                        String deviceid =  hub.functionName(i);
                        System.out.println("  " +fctHwdName+" : "+deviceid);
                    }
                }
                // add the hub to the dictionary so we won't have to
                // process is again.
                KnownHubs.add(serial);
                
                // disconnect from the hub
                YAPI.UnregisterHub(url);
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        EventHandler handlers = new EventHandler();

        System.out.println("Waiting for hubs to signal themselves...");
     
        // register the callback: HubDiscovered will be
        // invoked each time a hub signals its presence
        YAPI.RegisterHubDiscoveryCallback(handlers);

        // wait for 30 seconds, doing nothing.
        for (int i=0 ;i< 30;i++) {
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
