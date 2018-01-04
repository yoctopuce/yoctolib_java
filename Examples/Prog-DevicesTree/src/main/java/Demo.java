import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YHubPort;
import com.yoctopuce.YoctoAPI.YModule;

import java.util.ArrayList;
import java.util.List;

public class Demo
{

// This example will found all hubs on the local network,
// connect to them one by one, retreive information, and
// then disconnect.

    static class YoctoShield
    {
        private String _serial;
        private List<String> _subDevices;

        public YoctoShield(String serial)
        {
            _serial = serial;
            _subDevices = new ArrayList<String>();
        }

        public String getSerial()
        {
            return _serial;
        }

        public boolean addSubdevice(String serial)
        {
            for (int i = 1; i <= 4; i++) {
                YHubPort p = YHubPort.FindHubPort(String.format("%s.hubPort%d", this._serial, i));
                try {
                    if (p.get_logicalName().equals(serial)) {
                        _subDevices.add(serial);
                        return true;
                    }
                } catch (YAPI_Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        public void removeSubDevice(String serial)
        {
            _subDevices.remove(serial);
        }

        public void describe()
        {
            System.out.println("  " + this._serial);
            for (String subdevice : _subDevices) System.out.println("    " + subdevice);
        }

    }

    static class RootDevice
    {
        private String _serial;
        private String _url;
        private List<YoctoShield> _shields;
        private List<String> _subDevices;

        public RootDevice(String serialnumber, String url)
        {
            _serial = serialnumber;
            _url = url;
            _shields = new ArrayList<YoctoShield>();
            _subDevices = new ArrayList<String>();
        }


        public String getSerial()
        {
            return _serial;
        }

        public void addSubDevice(String serial)
        {

            if (serial.substring(0, 7).equals("YHUBSHL")) {
                _shields.add(new YoctoShield(serial));
            } else {
                // Device to plug look if the device is plugged on a shield
                for (YoctoShield shield : _shields) {
                    if (shield.addSubdevice(serial))
                        return;
                }
                _subDevices.add(serial);
            }
        }


        public void removeSubDevice(String serial)
        {
            _subDevices.remove(serial);
            for (int i = _shields.size() - 1; i >= 0; i--) {
                YoctoShield yoctoShield = _shields.get(i);
                if (yoctoShield.getSerial().equals(serial)) {
                    _shields.remove(i);
                } else {
                    yoctoShield.removeSubDevice(serial);
                }
            }
        }

        public void describe()
        {
            System.out.println(this._serial + " (" + _url + ")");
            for (String subdevice : _subDevices) {
                System.out.println("  " + subdevice);
            }
            for (YoctoShield shield : _shields) {
                shield.describe();
            }
        }
    }


    static List<RootDevice> __rootDevices = new ArrayList<RootDevice>();

    static RootDevice getYoctoHub(String serial)
    {
        for (RootDevice rootDevice : __rootDevices)
            if (rootDevice.getSerial().equals(serial))
                return rootDevice;
        return null;
    }

    static RootDevice addRootDevice(String serial, String url)
    {
        for (RootDevice rootDevice : __rootDevices)
            if (rootDevice.getSerial().equals(serial))
                return rootDevice;
        RootDevice rootDevice = new RootDevice(serial, url);
        __rootDevices.add(rootDevice);
        return rootDevice;

    }

    static void showNetwork()
    {
        System.out.println("**** device inventory *****");
        for (RootDevice hub : __rootDevices) {
            hub.describe();
        }
    }


    public static void main(String[] args)
    {
        // configure the API to contact any networked device
        try {
            YAPI.RegisterHub("usb");
            YAPI.RegisterHub("net");

            // each time a new device is connected/discovered
            // arrivalCallback will be called.
            YAPI.RegisterDeviceArrivalCallback(new YAPI.DeviceArrivalCallback()
            {
                public void yDeviceArrival(YModule module)
                {
                    try {
                        String serial = module.get_serialNumber();
                        String parentHub = module.get_parentHub();
                        if (parentHub.equals("")) {
                            // root device (
                            String url = module.get_url();
                            addRootDevice(serial, url);
                        } else {
                            RootDevice hub = getYoctoHub(parentHub);
                            if (hub != null)
                                hub.addSubDevice(serial);
                        }
                    } catch (YAPI_Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            // each time a device is disconnected/removed
            // arrivalCallback will be called.
            YAPI.RegisterDeviceRemovalCallback(new YAPI.DeviceRemovalCallback()
            {
                public void yDeviceRemoval(YModule module)
                {
                    String serial = null;
                    try {
                        serial = module.get_serialNumber();
                        for (int i = __rootDevices.size() - 1; i >= 0; i--) {
                            __rootDevices.get(i).removeSubDevice(serial);
                            if (__rootDevices.get(i).getSerial().equals(serial))
                                __rootDevices.remove(i);
                        }
                    } catch (YAPI_Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Waiting for hubs to signal themselves...");
            // wait for 5 seconds, doing nothing.
            //noinspection InfiniteLoopStatement
            while (true) {
                YAPI.UpdateDeviceList();
                YAPI.Sleep(1000);
                showNetwork();
            }
        } catch (YAPI_Exception e) {
            e.printStackTrace();
        }

        YAPI.FreeAPI();
    }

}
