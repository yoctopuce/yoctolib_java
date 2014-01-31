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

            System.out.println("Using DataLogger of " + logger.module().get_serialNumber());
            ArrayList<YDataStream> streams = new ArrayList<YDataStream>();
            // Handle recorder on/off state
            // Dump list of available streams
            logger.get_dataStreams(streams);
            System.out.println("Available data streams in the data logger:");
            System.out.println("Run\tRelative time\tUTC time\tMeasures interval");
            for (YDataStream stream : streams) {
                int run = stream.get_runIndex();
                int time = stream.get_startTime();
                long utc = stream.get_startTimeUTC();
                double itv = stream.get_dataSamplesInterval();
                System.out.println(String.format("%03d\t%013d\t%08d\t%f",run,time,utc,itv));


                ArrayList<String> names = stream.get_columnNames();
                for(int c = 0; c < names.size(); c++) {
                    System.out.print(names.get(c)+"\t");
                }
                System.out.println("");
                ArrayList<ArrayList<Double>> table = stream.get_dataRows();
                for(int r = 0; r < table.size(); r++) {
                    ArrayList<Double> row = table.get(r);
                    for(int c = 0; c < row.size(); c++) {
                        System.out.print(row.get(c)+ "\t");
                    }
                    System.out.println("");
                }
            }
            YAPI.FreeAPI();
        } catch (YAPI_Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
