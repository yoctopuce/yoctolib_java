import com.yoctopuce.YoctoAPI.*;

public class Demo {

    static class EventHandler implements YAPI.DeviceArrivalCallback, YAPI.DeviceRemovalCallback,
                                        YAnButton.UpdateCallback, YLightSensor.UpdateCallback,YTemperature.UpdateCallback
                                        
    {

        @Override
        public void yDeviceArrival(YModule module)
        {
            try {
                System.out.println("Device arrival          : " + module);
                int fctcount = module.functionCount();
                String fctName, fctFullName;

                for (int i = 0; i < fctcount; i++) {
                    fctName = module.functionId(i);
                    fctFullName = module.get_serialNumber() + "." + fctName;

                    // register call back for anbuttons
                    if (fctName.startsWith("anButton") ) {
                        YAnButton bt = YAnButton.FindAnButton(fctFullName);
                        if (bt.isOnline()) {
                            bt.registerValueCallback(this);
                            System.out.println("Callback registered for : " + fctFullName);
                        }
                    }

                    // register call back for temperature sensors
                    if (fctName.startsWith("temperature")) {
                        YTemperature t = YTemperature.FindTemperature(fctFullName);
                        if (t.isOnline()) {
                            t.registerValueCallback(this);
                            System.out.println("Callback registered for : " + fctFullName);
                        }
                    }

                    // register call back for light sensors
                    if (fctName.startsWith("lightSensor") ) {
                        YLightSensor l = YLightSensor.FindLightSensor(fctFullName);
                        if (l.isOnline()) {
                            l.registerValueCallback(this);
                            System.out.println("Callback registered for : " + fctFullName);
                        }
                    }
                    // and so on for other sensor type.....

                }
            } catch (YAPI_Exception ex) {
                System.out.println("Device access error : " + ex.getLocalizedMessage());
            }
        }

        @Override
        public void yDeviceRemoval(YModule module)
        {
            System.out.println("Device removal          : " + module);
        }

        @Override
        public void yNewValue(YAnButton function, String functionValue)
        {
            System.out.println("Position change    :" + function + " = " + functionValue);
        }


        @Override
        public void yNewValue(YLightSensor function, String functionValue)
        {
            System.out.println("Light change       :    " + function + " = " + functionValue+"lx");
        }

        @Override
        public void yNewValue(YTemperature function, String functionValue)
        {
            System.out.println("Temperature change       :    " + function + " = " + functionValue+"C");
        }

    }


    public static void main(String[] args) 
    {
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
