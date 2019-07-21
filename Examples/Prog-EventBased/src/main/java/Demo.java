
import com.yoctopuce.YoctoAPI.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo
{

    static class EventHandler implements YAPI.DeviceArrivalCallback, YAPI.DeviceRemovalCallback,
            YAnButton.UpdateCallback, YSensor.UpdateCallback, YSensor.TimedReportCallback, YModule.ConfigChangeCallback, YModule.BeaconCallback, YAPI.LogCallback
    {

        @Override
        public void yNewValue(YAnButton fct, String value)
        {
            try {
                System.out.println(fct.get_hardwareId() + ": " + value + " (new value)");
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void yNewValue(YSensor fct, String value)
        {
            try {
                System.out.println(fct.get_hardwareId() + ": " + value + " " + fct.get_userData() + " (new value)");
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void timedReportCallback(YSensor fct, YMeasure measure)
        {
            try {
                System.out.println(fct.get_hardwareId() + ": " + measure.get_averageValue() + " " + fct.get_userData() + " (timed report)");
            } catch (YAPI_Exception ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void yDeviceArrival(YModule module)
        {
            try {
                String serial = module.get_serialNumber();
                System.out.println("Device arrival : " + serial);
                module.registerConfigChangeCallback(this);
                module.registerBeaconCallback(this);


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
                        String unit = sensor.get_unit();
                        sensor.set_userData(unit);
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
        public void yDeviceRemoval(YModule module)
        {
            System.out.println("Device removal : " + module);
        }

        @Override
        public void configChangeCallback(YModule module)
        {
            System.out.println("Configuration changed for  " + module);
        }

        @Override
        public void beaconCallback(YModule module, int beacon)
        {
            System.out.println(String.format("Beacon changed to %d : %s", beacon, module));
        }

        @Override
        public void yLog(String line)
        {
            System.out.println("Log : " + line.trim());
        }
    }

    public static void main(String[] args)
    {
        try {

            EventHandler handlers = new EventHandler();
            YAPI.RegisterLogFunction(handlers);

            try {
                // setup the API to use local VirtualHub
                YAPI.RegisterHub("127.0.0.1");
            } catch (YAPI_Exception ex) {
                System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
                System.out.println("Ensure that the VirtualHub application is running");
                System.exit(1);
            }

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
