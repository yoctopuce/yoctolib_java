import com.yoctopuce.YoctoAPI.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    public static void main(String[] args) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        try {
            try {
                // setup the API to use local VirtualHub
                YAPI.RegisterHub("127.0.0.1");
            } catch (YAPI_Exception ex) {
                System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
                System.out.println("Ensure that the VirtualHub application is running");
                System.exit(1);
            }

            // Enumerate all connected sensors
            YSensor sensor;
            ArrayList<YSensor> sensorList = new ArrayList<YSensor>();
            sensor = YSensor.FirstSensor();
            while (sensor != null) {
                sensorList.add(sensor);
                sensor = sensor.nextSensor();
            }
            if (sensorList.size() == 0) {
                System.out.println("No Yoctopuce sensor connected (check USB cable)");
                System.exit(1);
            }

            // Generate consolidated CSV output for all sensors
            YConsolidatedDataSet data = new YConsolidatedDataSet(0, 0, sensorList);
            ArrayList<Double> record = new ArrayList<Double>();
            while (data.nextRecord(record) < 100) {
                String line = ft.format(new Date((long) (record.get(0) * 1000)));
                for (int idx = 1; idx < record.size(); idx++) {
                    line = String.format("%s;%.3f", line, record.get(idx));
                }
                System.out.println(line);
            }

            YAPI.FreeAPI();
        } catch (YAPI_Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
