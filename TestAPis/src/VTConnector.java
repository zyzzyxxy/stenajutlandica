
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class VTConnector {

    final String KEY = "LKhZ7iZlmxrdSyI4OAExXOfWlkQa";
    final String SECRET = "KHtlJozIqQ0zdiKyiy0sWuzQMu8a";
    final String ACCESS_TOKEN = "2a06cf10-cf84-3f9f-8229-315c5279f448";

    // HTTP GET request
    private final String USER_AGENT = "Mozilla/5.0";

    public void sendGet() throws Exception {


        String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    public void sendPost() throws Exception {

        String url = "https://api.vasttrafik.se/token";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Authorization", "Basic " + KEY + SECRET +
                "grant_type=client_credentials&scope=device_0");


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    public void tryCurl() throws IOException {
        String command = "curl -k -d \"grant_type=client_credentials\" -H \"Authorization: Basic TEtoWjdpWmxteHJkU3lJNE9BRXhYT2ZXbGtRYTpLSHRsSm96SXFRMHpkaUt5aXkwc1d1elFNdThh\" https://api.vasttrafik.se:443/token";
        Process process = Runtime.getRuntime().exec(command);
        InputStream input = process.getInputStream();
        int result = input.read();
        System.out.println(result);
        System.out.println(process.getInputStream());
        System.out.println(process.getInputStream().read());
        System.out.println(process.getInputStream().read());
    }

    public void tryCurl2() throws IOException {
        List<String> output = new ArrayList<>();
        String curl_command = "curl -k -d \"grant_type=client_credentials\" -H \"Authorization: Basic TEtoWjdpWmxteHJkU3lJNE9BRXhYT2ZXbGtRYTpLSHRsSm96SXFRMHpkaUt5aXkwc1d1elFNdThh\" https://api.vasttrafik.se:443/token";
        System.out.println("curl_command: " + curl_command);

        try {
            //Process p = Runtime.getRuntime().exec("curl -k -d \ngrant_type=client_credentials\n -H \nAuthorization: Basic TEtoWjdpWmxteHJkU3lJNE9BRXhYT2ZXbGtRYTpLSHRsSm96SXFRMHpkaUt5aXkwc1d1elFNdThh\n https://api.vasttrafik.se:443/token");
            Process p = Runtime.getRuntime().exec(curl_command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.add(line + "\n");
            }
            System.out.println(output);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
