
import com.yoctopuce.YoctoAPI.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    static class EventHandler implements YAPI.DeviceArrivalCallback, YAPI.DeviceRemovalCallback,
            YAnButton.UpdateCallback, YSensor.UpdateCallback, YSensor.TimedReportCallback {

        @Override
        public void yNewValue(YAnButton fct, String value) {
            try {
                int apival = fct.get_calibratedValue();
                fct.clearCache();
                int value1 = fct.get_calibratedValue();
                System.out.println(String.format("%s: %s=%d (%d)", fct.get_hardwareId(), value,  value1,apival));
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void yNewValue(YSensor fct, String value) {
            try {
                System.out.println(fct.get_hardwareId() + ": " + value + " (new value)");
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void timedReportCallback(YSensor fct, YMeasure measure) {
            try {
                System.out.println(fct.get_hardwareId() + ": " + measure.get_averageValue() + " " + fct.get_unit() + " (timed report)");
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void yDeviceArrival(YModule module) {
            try {
                String serial = module.get_serialNumber();
                System.out.println("Device arrival : " + serial);

                // First solution: look for a specific type of function (eg. anButton)
                int fctcount = module.functionCount();
                for (int i = 0; i < fctcount; i++) {
                    String fctName = module.functionId(i);
                    String hardwareId = serial + "." + fctName;

                    // register call back for anbuttons
                    if (fctName.startsWith("anButton")) {
                        System.out.println("- " + hardwareId);
                        YAnButton bt = YAnButton.FindAnButton(hardwareId);
                        bt.registerValueCallback(this);
                    }
                }

                // Alternate solution: register any kind of sensor on the device
                YSensor sensor = YSensor.FirstSensor();
                while (sensor != null) {
                    if (sensor.get_module().get_serialNumber().equals(serial)) {
                        String hardwareId = sensor.get_hardwareId();
                        System.out.println("- " + hardwareId);
                        sensor.registerValueCallback(this);
                        sensor.registerTimedReportCallback(this);
                    }
                    sensor = sensor.nextSensor();
                }
            } catch (YAPI_Exception ex) {
                System.out.println("Device access error : " + ex.getLocalizedMessage());
            }
        }

        @Override
        public void yDeviceRemoval(YModule module) {
            System.out.println("Device removal : " + module);
        }

    }

    public static void main(String[] args) {
        try {
            YAPI.DefaultCacheValidity = 5000;
            try {
                // setup the API to use local VirtualHub
                YAPI.RegisterHub("localhost");
            } catch (YAPI_Exception ex) {
                System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
                System.out.println("Ensure that the VirtualHub application is running");
                System.exit(1);
            }

            EventHandler handlers = new EventHandler();
            YAPI.RegisterDeviceArrivalCallback(handlers);
            YAPI.RegisterDeviceRemovalCallback(handlers);

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
