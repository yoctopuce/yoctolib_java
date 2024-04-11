import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Demo
{

    public static void main(String[] args)
    {
        YModule m;
        String errmsg = "";
        String username = "admin";
        String password = "";
        String host = "localhost";
        String url = "secure://" + username + ":" + password + "@" + host;

        // load known TLS certificate into the API
        String trusted_cert = load_cert_from_file(host);
        if (!trusted_cert.isEmpty()) {
            String error = YAPI.AddTrustedCertificates(trusted_cert);
            if (!"".equals(error)) {
                System.out.println(error);
                System.exit(1);
            }
        }
        // test connection with VirtualHub
        int res = 0;
        try {
            res = YAPI.TestHub(url, 1000);
        } catch (YAPI_Exception e) {
            res = e.errorType;
            e.printStackTrace();
        }
        if (res == YAPI.SSL_UNK_CERT) {
            // remote TLS certificate is unknown ask user what to do
            System.out.println("Remote SSL/TLS certificate is unknown");
            System.out.println("You can...");
            System.out.println(" -(A)dd certificate to the API");
            System.out.println(" -(I)gnore this error and continue");
            System.out.println(" -(E)xit");
            System.out.print("Your choice: ");
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader console = new BufferedReader(inputStreamReader);

            String line = null;
            try {
                line = console.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line.startsWith("a")) {
                // download remote certificate and save it locally
                trusted_cert = YAPI.DownloadHostCertificate(url, 5000);
                if (trusted_cert.startsWith("error")) {
                    System.out.println(trusted_cert);
                    System.exit(1);
                }
                save_cert_to_file(host, trusted_cert);
                String error = YAPI.AddTrustedCertificates(trusted_cert);
                if (!"".equals(error)) {
                    System.out.println(error);
                    System.exit(1);
                }
            } else if (line.startsWith("i")) {
                YAPI.SetNetworkSecurityOptions(YAPI.NO_HOSTNAME_CHECK | YAPI.NO_TRUSTED_CA_CHECK |
                        YAPI.NO_EXPIRATION_CHECK);
            } else {
                System.exit(1);
            }
        } else if (res != YAPI.SUCCESS) {
            System.out.println("YAPI.TestHub failed:" + errmsg);
            System.exit(1);
        }
        try {


            if (YAPI.RegisterHub(url) != YAPI.SUCCESS) {
                System.out.println("YAPI.RegisterHub failed:" + errmsg);
                System.exit(1);
            }

            System.out.println("Device list");
            m = YModule.FirstModule();
            while (m != null) {
                System.out.println(m.get_serialNumber() + " (" + m.get_productName() + ")");
                m = m.nextModule();
            }
        } catch (YAPI_Exception e) {
            throw new RuntimeException(e);
        }
        YAPI.FreeAPI();
    }

    private static void save_cert_to_file(String host, String trustedCert)
    {
        String path = host.replace('/', '_').replace(':', '_') + ".crt";
        File file = new File(path);
        FileWriter f;
        try {
            f = new FileWriter(file);
            f.write(trustedCert);
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String load_cert_from_file(String host)
    {
        String path = host.replace('/', '_').replace(':', '_') + ".crt";
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            return "";
        }

    }
}
