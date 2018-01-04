import com.yoctopuce.YoctoAPI.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    public static void dumpSensor(YSensor sensor) throws YAPI_Exception {
        SimpleDateFormat ft = new SimpleDateFormat ("dd MMM yyyy hh:mm:ss,SSS");
        System.out.println("Using DataLogger of " + sensor.get_friendlyName());
        YDataSet dataset = sensor.get_recordedData(0, 0);
        System.out.println("loading summary... ");
        dataset.loadMore();
        YMeasure summary = dataset.get_summary();
        String line = String.format("from %s to %s : min=%.3f%s avg=%.3f%s  max=%.3f%s",
                ft.format(summary.get_startTimeUTC_asDate()),ft.format(summary.get_endTimeUTC_asDate()),summary.get_minValue(),sensor.get_unit(),summary.get_averageValue(),sensor.get_unit(), summary.get_maxValue(),sensor.get_unit());
        System.out.println(line);
        System.out.print("loading details :   0%");
        int progress=0;
        do {
            progress = dataset.loadMore();
            System.out.print(String.format("\b\b\b\b%3d%%",progress));
        } while(progress <100);
        System.out.println("");
        ArrayList<YMeasure> details = dataset.get_measures();
        for(YMeasure m :details) {
            System.out.println(String.format("from %s to %s : min=%.3f%s avg=%.3f%s  max=%.3f%s",
                    ft.format(m.get_startTimeUTC_asDate()),ft.format(m.get_endTimeUTC_asDate()),m.get_minValue(),sensor.get_unit(),m.get_averageValue(),sensor.get_unit(), m.get_maxValue(),sensor.get_unit()));
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


            YSensor sensor;
            if (args.length == 0) {
                sensor = YSensor.FirstSensor();
                if (sensor == null) {
                    System.out.println("No module connected (check USB cable)");
                    System.exit(1);
                }
            } else {
                sensor = YSensor.FindSensor(args[0]);
                if (!sensor.isOnline()) {
                    System.out.println("Sensor " + sensor + " is not connected (check USB cable)");
                    System.exit(1);
                }
            }
            dumpSensor(sensor);
            YAPI.FreeAPI();
        } catch (YAPI_Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
