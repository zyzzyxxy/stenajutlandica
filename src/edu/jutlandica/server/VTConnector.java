package edu.jutlandica.server;

/**
 * THIS class connects with vasttrafik API and gives methods for fetching data.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.jutlandica.client.controller.*;

import javax.net.ssl.HttpsURLConnection;

import org.json.*;

import edu.jutlandica.client.controller.ParameterStringBuilder;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class VTConnector {

    final String KEY = "LKhZ7iZlmxrdSyI4OAExXOfWlkQa";
    final String SECRET = "KHtlJozIqQ0zdiKyiy0sWuzQMu8a";
    private String ACCESS_TOKEN = "";
    final String USER_AGENT = "Mozilla/5.0";
    final String VT_BASEADRESS = "https://api.vasttrafik.se/bin/rest.exe/v2";

    public void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        sun.net.www.protocol.http.HttpURLConnection con = (sun.net.www.protocol.http.HttpURLConnection) obj.openConnection();

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

    // authenticates and return an auth_key
    public String authenticate() throws Exception {

        String url = "https://api.vasttrafik.se/token/";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String authString = KEY + ":" + SECRET;
        String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());
        con.setRequestProperty("Authorization", "Basic " + encodedString);

        String postString = "grant_type=client_credentials&scope=device_0";
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postString);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println(responseCode);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postString);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        org.json.JSONObject jsonResponse = new org.json.JSONObject(response.toString());
        String result = (String) jsonResponse.get("access_token");
        System.out.println(result);

        return result;
    }

    private JSONObject getInfoFromVT(String auth_token, Map<String, String> parameters, String vt_function) throws Exception {

        String outData = "?" + ParameterStringBuilder.getParamsString(parameters);

        String url = VT_BASEADRESS + vt_function + outData +"&json";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();


        // optional default is GET
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json"); //Doesn´t help
        // con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Authorization", "Bearer " + auth_token);

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);


        con.setDoOutput(true);
//        DataOutputStream out = new DataOutputStream(con.getOutputStream());
//        System.out.println(outData);
//        out.writeBytes(outData);
//        out.flush();
//        out.close();

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

        //System.out.println(response.toString());
        //print result
        return XML.toJSONObject(response.toString());

    }


  
    public List<Journey> getTrip(String auth_token, String start_station, String end_station, String time, String date) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("originId", start_station);
        parameters.put("date", date);
        parameters.put("time", time);
        parameters.put("destId", end_station); //seems to be optional

        String func = "/trip";

        List<Journey> resultJourneyList = new ArrayList<>();

        try {
            JSONObject JResult = getInfoFromVT(auth_token, parameters, func);
            JSONObject triplist = (JSONObject) JResult.get("TripList");
            JSONArray tripArray = (JSONArray) triplist.get("Trip");
            JSONObject trip = (JSONObject) tripArray.get(0);
            trip = (JSONObject) trip.get("Leg");
            String t_start = (String) ((JSONObject) trip.get("Origin")).get("name");
            String t_dep_time = (String) ((JSONObject) trip.get("Origin")).get("time");
            String t_end = (String) ((JSONObject) trip.get("Destination")).get("name");
            String t_arr_time = (String) ((JSONObject) trip.get("Destination")).get("time");
            String t_veh = (String) trip.get("type");
            String t_id;
            try {
                t_id = (String) trip.get("sname");
            } catch (Exception e) {
                t_id = Integer.toString((int) trip.get("sname"));
            }

            String t_direction = (String) trip.get("direction");

            Trip resultTrip = new Trip(t_start, t_end, t_direction, t_dep_time, t_arr_time, t_veh, t_id);
            Journey resJ = new Journey();
            resJ.addTrip(resultTrip);
            resultJourneyList.add(resJ);
            return resultJourneyList;

            //TODO make whole jpouneys out of trips, chekc end stations
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}