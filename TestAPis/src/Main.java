import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String args[]) throws Exception {
        //TODO send dynamic date
        VTConnector vtc = new VTConnector();
        String auth_token = vtc.authenticate();
        System.out.println(auth_token);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = formatter.format(date);
        String time = Integer.toString(date.getHours())+":"+Integer.toString(date.getMinutes());
        //vtc.getArrivalBoard(auth_token, "centralstationen","angered", time, sDate);
        //vtc.getDepartureBoard(auth_token, "centralstationen","angEred", time, sDate);
        //TODO make work with all end stations
        vtc.getTrip(auth_token, "centralstationen","angEred", time, sDate);
        String[] workingStops = {"angered",};
    }
}
