/*
 *
 *  $Id: Demo.java 60291 2024-04-02 09:00:39Z seb $
 *
 *  Doc-GettingStarted-Yocto-RFID example
 *
 *  You can find more information on our web site:
 *   JAVA API Reference:
 *      https://www.yoctopuce.com/EN/doc/reference/yoctolib-java-EN.html
 *
 */

import java.util.ArrayList;
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
        YRfidReader reader;
        if (args.length > 0) {
            reader = YRfidReader.FindRfidReader(args[0]);
        } else {
            reader = YRfidReader.FirstRfidReader();
            if (reader == null) {
                System.out.println("No module connected (check USB cable)");
                System.exit(1);
            }
        }
        if (!reader.isOnline()) {
            System.out.println("Module not connected (check identification and USB cable)");
            System.exit(1);
        }

        try {
            String serial = reader.get_module().get_serialNumber();
            YColorLedCluster leds = YColorLedCluster.FindColorLedCluster(serial + ".colorLedCluster");
            YAnButton button = YAnButton.FindAnButton(serial + ".anButton1");
            YBuzzer buzzer = YBuzzer.FindBuzzer(serial + ".buzzer");
            leds.set_rgbColor(0,1,0x000000);
            buzzer.set_volume(75);
            System.out.println("Place a RFID tag near the antenna");
            ArrayList<String> tagList;
            do
            { YAPI.Sleep(250);
              tagList = reader.get_tagIdList();
                
            } while (tagList.size() <=0);
                     
            String tagId      = tagList.get(0);
            YRfidStatus opStatus   =  new  YRfidStatus();
            YRfidOptions options    = new YRfidOptions();
            YRfidTagInfo  taginfo   = reader.get_tagInfo(tagId,opStatus);
            Integer blocksize  = taginfo.get_tagBlockSize();
            Integer firstBlock = taginfo.get_tagFirstBlock();
            System.out.println("Tag ID          = "+taginfo.get_tagId());
            System.out.println("Tag Memory size = "+Integer.toString(taginfo.get_tagMemorySize())+" bytes");
            System.out.println("Tag Block  size = "+Integer.toString(taginfo.get_tagBlockSize())+" bytes");
            
            String data = reader.tagReadHex(tagId, firstBlock, 3*blocksize, options, opStatus);
            if (opStatus.get_errorCode()==YRfidStatus.SUCCESS)
             { System.out.println ("First 3 blocks  = "+data);
               leds.set_rgbColor(0,1,0x00FF00);
               buzzer.pulse(1000,100);
             }
            else
            { System.out.println("Cannot read tag contents ("+opStatus.get_errorMessage()+")");
              leds.set_rgbColor(0, 1, 0xFF0000);
            }  
            leds.rgb_move(0, 1, 0x000000, 200); 
                                   
        } catch (YAPI_Exception ex) {
            System.out.println("Module not connected (check identification and USB cable)");
        }
        YAPI.FreeAPI();
    }

   
}
