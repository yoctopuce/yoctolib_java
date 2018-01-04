
import com.yoctopuce.YoctoAPI.*;

public class Demo {

    public static void main(String[] args) {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
        } catch (YAPI_Exception ex) {
            System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
            System.out.println("Ensure that the VirtualHub application is running");
            System.exit(1);
        }

        YDigitalIO io;
        if (args.length > 0) {
            io = YDigitalIO.FindDigitalIO(args[0]+ ".digitalIO");
        } else {
            io = YDigitalIO.FirstDigitalIO();
        }
        if (io == null) {
            System.out.println("No module connected (check USB cable)");
            System.exit(1);
        }

        try {
            // lets configure the channels direction
            // bits 0..3 as output
            // bits 4..7 as input
            io.set_portDirection(0x0F);
            io.set_portPolarity(0); // polarity set to regular
            io.set_portOpenDrain(0); // No open drain
            System.out.println("Channels 0..3 are configured as outputs and channels 4..7");
            System.out.println("are configred as inputs, you can connect some inputs to");
            System.out.println("ouputs and see what happens");
            int outputdata = 0;
            while (io.isOnline()) {
                outputdata = (outputdata + 1) % 16; // cycle ouput 0..15
                io.set_portState(outputdata); // We could have used set_bitState as well
                YAPI.Sleep(1000);
                int inputdata = io.get_portState(); // read port values                
                String line = "";  // display port value value as binary
                for (int i = 0; i < 8; i++) {
                    
                    if ((inputdata & (128 >> i)) > 0) {
                        line = line + '1';
                    } else {
                        line = line + '0';
                    }
                }
                System.out.println("port value = "+line);
            }
        } catch (YAPI_Exception ex) {
            System.out.println("Module " + io.describe() + " disconnected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }
}
