import com.yoctopuce.YoctoAPI.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Demo {

    public static void main(String[] args)   {
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
            serialPort = YSerialPort.FindSerialPort(args[0]);
        } else {
            serialPort = YSerialPort.FirstSerialPort();
            if (serialPort == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }

        int slave, reg, val;
        String cmd;

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader console = new BufferedReader(inputStreamReader);
        try {
            do {
                System.out.println("Please enter the MODBUS slave address (1...255)");
                System.out.print("Slave: ");
                slave = Integer.parseInt(console.readLine());
            } while(slave < 1 || slave > 255);
            do {
                System.out.println("Please select a Coil No (>=1), Input Bit No (>=10001+),");
                System.out.println("       Input Register No (>=30001) or Register No (>=40001)");
                System.out.print("No: ");
                reg = Integer.parseInt(console.readLine());
            } while(reg < 1 || reg >= 50000 || (reg % 10000) == 0);
            while(true) {
                if(reg >= 40001) {
                    val = serialPort.modbusReadRegisters(slave, reg-40001, 1).get(0);
                } else if(reg >= 30001) {
                    val = serialPort.modbusReadInputRegisters(slave, reg-30001, 1).get(0);
                } else if(reg >= 10001) {
                    val = serialPort.modbusReadInputBits(slave, reg-10001, 1).get(0);
                } else {
                    val = serialPort.modbusReadBits(slave, reg-1, 1).get(0);
                }
                System.out.println("Current value: "+Integer.toString(val));
                System.out.print("Press ENTER to read again, Q to quit");
                if((reg % 30000) < 10000) {
                    System.out.print(" or enter a new value");
                }
                System.out.print(": ");
                cmd = console.readLine();
                if(cmd.equals("q") || cmd.equals("Q")) break;
                if(!cmd.equals("") && (reg % 30000) < 10000) {
                    val = Integer.parseInt(cmd);
                    if(reg >= 30001) {
                        serialPort.modbusWriteRegister(slave, reg-30001, val);
                    } else {
                        serialPort.modbusWriteBit(slave, reg-1, val);
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        YAPI.FreeAPI();
    }
}
