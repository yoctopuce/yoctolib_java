
import com.yoctopuce.YoctoAPI.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    static class EventHandler implements YAPI.DeviceArrivalCallback,YAPI.LogCallback, YModule.LogCallback {


        @Override
        public void yDeviceArrival(YModule module) {
            try {
                String serial = module.get_serialNumber();
                System.out.println("Device arrival : " + serial);
                module.registerLogCallback(this); 
            } catch (YAPI_Exception ex) {
                System.out.println("Device access error : " + ex.getLocalizedMessage());
            }
        }


        @Override
        public void logCallback(YModule module, String logline)
        {
            try {
                String serial = module.get_serialNumber();
                System.out.println(serial + " : " + logline);
            } catch (YAPI_Exception ex) {
                System.out.println("Device access error : " + ex.getLocalizedMessage());
            }
        }

        @Override
        public void yLog(String line)
        {
            System.out.print("YAPI : " + line);
        }

    }

    public static void main(String[] args) {
        try {
            try {
                // setup the API to use local VirtualHub
                YAPI.RegisterHub("127.0.0.1");
            } catch (YAPI_Exception ex) {
                System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
                System.out.println("Ensure that the VirtualHub application is running");
                System.exit(1);
            }

            EventHandler handlers = new EventHandler();
            YAPI.RegisterDeviceArrivalCallback(handlers);
            YAPI.RegisterLogFunction(handlers);
            System.out.println("Hit Ctrl-C to Stop ");

            while (true) {
                YAPI.UpdateDeviceList(); // traps plug/unplug events
                YAPI.Sleep(500);   // traps others events
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Runtime error (check identification and USB cable)");
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        YAPI.FreeAPI();
    }
}
