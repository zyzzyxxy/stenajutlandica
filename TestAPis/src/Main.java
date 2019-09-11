import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String args[]) throws Exception {

        HttpCon huc = new HttpCon();
        VTConnector vtc = new VTConnector();

//        System.out.println("Testing 1 - Send Http GET request");
//        huc.sendGet();
//
//        System.out.println("\nTesting 2 - Send Http POST request");
//        huc.sendPost();

        //System.out.println("\nTesting 2 - Send Http POST request");
        //vtc.sendPost();

        System.out.println("Tryig curl");
        vtc.tryCurl2();


    }
}
