import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YSerialPort;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

        YSerialPort serialPort;
        if (args.length > 0) {
            String target = args[0];
            serialPort = YSerialPort.FindSerialPort(target + ".serialPort");
        } else {
            serialPort = YSerialPort.FirstSerialPort();
            if (serialPort == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader console = new BufferedReader(inputStreamReader);
        try {
            serialPort.set_serialMode("9600,8N1");
            serialPort.set_protocol("Line");
            serialPort.reset();
            String line;
            do {
                YAPI.Sleep(500);
                do {
                    line = serialPort.readLine();
                    if (!line.equals("")) {
                        System.out.println("Received: " + line);
                    }
                } while (!line.equals(""));
                System.out.println("Type line to send, or Ctrl-C to exit:");
                line = console.readLine();
                serialPort.writeLine(line);
            } while (!line.equals(""));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        YAPI.FreeAPI();
    }
}
