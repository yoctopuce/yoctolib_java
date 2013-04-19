import com.yoctopuce.YoctoAPI.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {

    public static void main(String[] args)
    {
        try {
            try {
                // setup the API to use local VirtualHub
                YAPI.RegisterHub("http://127.0.0.1:4444/");
            } catch (YAPI_Exception ex) {
                System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
                System.out.println("Ensure that the VirtualHub application is running");
                System.exit(1);
            }


            YDataLogger logger;
            if (args.length == 0) {
                logger = YDataLogger.FirstDataLogger();
                if (logger == null) {
                    System.out.println("No module connected (check USB cable)");
                    System.exit(1);
                }
            } else {
                logger = YDataLogger.FindDataLogger(args[0] + ".datalogger");
                if (!logger.isOnline()) {
                    System.out.println("datalogger " + logger + " is notconnected (check USB cable)");
                    System.exit(1);
                }
            }

            // Main page: display controllers and result frames
            System.out.println("Data Logger demo<");
            System.out.println("Module to use: <input name='serial' value='$serial'><br><br>");
            ArrayList<YDataStream> streams = null;
            // Handle recorder on/off state
            // Dump list of available streams
            logger.get_dataStreams(streams);
            System.out.println("Available data streams in the data logger:<br>");
            System.out.println("<table border=1>\n<tr><th>Run</th><th>Relative time</th>"
                    + "<th>UTC time</th><th>Measures interval</th></tr>\n");
            for (YDataStream stream : streams) {
                int run = stream.getRunIndex();
                int time = stream.getStartTime();
                long utc = stream.getStartTimeUTC();
                int itv = stream.getDataSamplesInterval();
                System.out.println("<tr><td>#$run</td><td>$time [s]</td><td>$utc</td><td>$itv [s]</td>");
            }
            YAPI.FreeAPI();
        } catch (YAPI_Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
