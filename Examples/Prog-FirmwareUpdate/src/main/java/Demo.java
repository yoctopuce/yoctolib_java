
import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YFirmwareUpdate;
import com.yoctopuce.YoctoAPI.YModule;

import java.util.ArrayList;

public class Demo
{

    static int upgradeSerialList(ArrayList<String> allserials) throws YAPI_Exception
    {
        for (String serial : allserials) {
            YModule module = YModule.FindModule(serial);
            String product = module.get_productName();
            String current = module.get_firmwareRelease();

            // check if a new firmware is available on yoctopuce.com
            String newfirm = module.checkFirmware("www.yoctopuce.com", true);
            if (newfirm.equals("")) {
                System.out.println(product + " " + serial + "(rev=" + current + ") is up to date");
            } else {
                System.out.println(product + " " + serial + "(rev=" + current + ") need be updated with firmware : ");
                System.out.println("    " + newfirm);
                // execute the firmware upgrade
                YFirmwareUpdate update = module.updateFirmware(newfirm);
                int status = update.startUpdate();
                do {
                    int newstatus = update.get_progress();
                    if (newstatus != status)
                        System.out.println(newstatus + "% " + update.get_progressMessage());
                    YAPI.Sleep(500);
                    status = newstatus;
                } while (status < 100 && status >= 0);
                if (status < 0) {
                    System.out.println("Firmware Update failed: " + update.get_progressMessage());
                    System.exit(1);
                } else {
                    if (module.isOnline()) {
                        System.out.println(status + "% Firmware Updated Successfully!");
                    } else {
                        System.out.println(status + " Firmware Update failed: module " + serial + "is not online");
                        System.exit(1);
                    }
                }
            }
        }
        return 0;
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
            for (String arg : args) {
                System.out.println("Update module connected to hub " + arg);
                YAPI.RegisterHub(arg);
            }


            ArrayList<String> hubs = new ArrayList<String>();
            ArrayList<String> shield = new ArrayList<String>();
            ArrayList<String> devices = new ArrayList<String>();
            //first step construct the list of all hub /shield and devices connected
            YModule module = YModule.FirstModule();
            while (module != null) {
                String product = module.get_productName();
                String serial = module.get_serialNumber();
                if (product.equals("YoctoHub-Shield")) {
                    shield.add(serial);
                } else if (product.startsWith("YoctoHub")) {
                    hubs.add(serial);
                } else if (!product.equals("VirtualHub")) {
                    devices.add(serial);
                }
                module = module.nextModule();
            }
            // first upgrades all Hubs...
            upgradeSerialList(hubs);
            // ... then all shield..
            upgradeSerialList(shield);
            // ... and finally all devices
            upgradeSerialList(devices);
            System.out.println("All devices are now up to date");
        } catch (YAPI_Exception ex) {
            System.out.println("Runtime error (check identification and USB cable)");
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        YAPI.FreeAPI();
    }
}
