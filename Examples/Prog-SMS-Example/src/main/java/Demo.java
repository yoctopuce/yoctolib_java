/*
 *
 *  $Id: Demo.java 60035 2024-03-20 09:56:43Z seb $
 *
 *  An example that shows how to use a  Yocto-RS232
 *
 *  You can find more information on our web site:
 *   Yocto-RS232 documentation:
 *      https://www.yoctopuce.com/EN/products/yocto-rs232/doc.html
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YMessageBox;
import com.yoctopuce.YoctoAPI.YSms;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Demo {

    static class EventHandler implements YMessageBox.YSmsCallback
    {

        @Override
        public void smsCallback(YMessageBox mbox, YSms sms)
        {
            System.out.println("- New message dated " + sms.get_timestamp());
            System.out.println("  from " + sms.get_sender());
            System.out.println("  '" + sms.get_textData() + "'");
            try {
                sms.deleteFromSIM();
            } catch (YAPI_Exception ex) {
                ex.printStackTrace();
            }
        }
    }

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

        YMessageBox mbox;
        if (args.length > 0) {
            String target = args[0];
            mbox = YMessageBox.FindMessageBox(target + ".messageBox");
        } else {
            mbox = YMessageBox.FirstMessageBox();
            if (mbox == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }
        System.out.println("Using " + mbox.describe());
        System.out.println();

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader console = new BufferedReader(inputStreamReader);
        try {
            // list messages found on the device
            System.out.println("Messages found on the SIM card:");
            ArrayList<YSms> messages = mbox.get_messages();
            if (messages.size() == 0) {
                System.out.println("* None");
            }
            for (YSms sms: messages) {
                System.out.println("- dated " + sms.get_timestamp());
                System.out.println("  from " + sms.get_sender());
                System.out.println("  '" + sms.get_textData() + "'");
            }

            // register a callback to receive any new message
            EventHandler handler = new EventHandler();
            mbox.registerSmsCallback(handler);

            // offer to send a new message
            System.out.println("To test sending SMS, provide message recipient (+xxxxxxx).");
            System.out.println("To skip sending, leave empty and press Enter.");
            String number = console.readLine();
            if (!number.equals("")) {
                // if that call fails, make sure that your SIM operator
                // allows you to send SMS given your current contract
                mbox.sendTextMessage(number, "Hello from YoctoHub-GSM !");
            }
            System.out.println("Waiting to receive SMS, press Ctrl-C to quit");
            while(true) {
                YAPI.Sleep(500);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        YAPI.FreeAPI();
    }
}
