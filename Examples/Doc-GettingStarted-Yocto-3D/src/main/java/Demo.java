import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void main(String[] args)
    {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        YTilt anytilt,tilt1, tilt2;
        YCompass compass;
        YAccelerometer accelerometer;
        YGyro gyro;

        if (args.length == 0) {
            anytilt = YTilt.FirstTilt();            
            if (anytilt == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        } else {
            anytilt = YTilt.FindTilt(args[0] + ".tilt1");
            if (!anytilt.isOnline()){ 
                System.out.println("Module not connected (check identification and USB cable)");
                System.exit(1);
            }
        }
        try {
            String serial = anytilt.get_module().get_serialNumber();
            tilt1 = YTilt.FindTilt(serial + ".tilt1");
            tilt2 = YTilt.FindTilt(serial + ".tilt2");
            compass = YCompass.FindCompass(serial + ".compass");
            accelerometer = YAccelerometer.FindAccelerometer(serial + ".accelerometer");
            gyro = YGyro.FindGyro(serial + ".gyro");
            int count = 0;
            while (true) {
                    if (!tilt1.isOnline()) {
                        System.out.println("device disconnected");
                        System.exit(0);
                    }

                    if (count % 10 == 0) 
                        System.out.println("tilt1   tilt2   compass   acc   gyro");

                    System.out.println("" + tilt1.get_currentValue() + "\t" + tilt2.get_currentValue() + 
                        "\t" + compass.get_currentValue() + "\t" + accelerometer.get_currentValue() + "\t" + 
                        gyro.get_currentValue());
                    count++;
                    YAPI.Sleep(250);
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
