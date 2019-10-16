package edu.jutlandica.server;
/**
 * THIS class connects with vasttrafik API and gives methods for fetching data.
 * Authors Johan Ericsson, Daniel Danielsson
 */

import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;
import org.json.*;

import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;


public class VTConnector implements APIconnector {
    //KEY and SECRET from personal account... Can be changed to another users account
    final String KEY = "LKhZ7iZlmxrdSyI4OAExXOfWlkQa";
    final String SECRET = "KHtlJozIqQ0zdiKyiy0sWuzQMu8a";
    private String ACCESS_TOKEN = "";
    final String USER_AGENT = "Mozilla/5.0";
    final String VT_BASEADRESS = "https://api.vasttrafik.se/bin/rest.exe/v2";

    /**
     * This method generates an auth_token... Needed for communication with vt api.
     *
     * @return
     * @throws Exception
     */
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

    /**
     * Help class for getTrip
     *
     * @param auth_token
     * @param parameters
     * @param vt_function
     * @return
     * @throws Exception
     */
    private JSONObject getInfoFromVT(String auth_token, Map<String, String> parameters, String vt_function) throws Exception {

        String outData = "?" + ParameterStringBuilder.getParamsString(parameters);
        String url = VT_BASEADRESS + vt_function + outData + "&json";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json"); //Doesn�t help
        // con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Authorization", "Bearer " + auth_token);

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
        return XML.toJSONObject(response.toString());
    }

    /**
     * HelpClass for getTrip
     *
     * @param trip
     * @return
     * @throws JSONException
     */
    private Trip getTripFromJson(JSONObject trip) throws JSONException {
        String t_start = (String) ((JSONObject) trip.get("Origin")).get("name");
        String t_dep_time = (String) ((JSONObject) trip.get("Origin")).get("time");
        String t_end = (String) ((JSONObject) trip.get("Destination")).get("name");
        String t_arr_time = (String) ((JSONObject) trip.get("Destination")).get("time");
        String t_dep_track = (String) ((JSONObject) trip.get("Origin")).get("track");
        String t_arr_track = (String) ((JSONObject) trip.get("Destination")).get("track");
        String t_veh = (String) trip.get("type");
        String t_id;
        //Try catches for 3 variations sname = string, int or when walk use "name"
        try {
            t_id = (String) trip.get("sname");
        } catch (Exception e) {
            try {
                t_id = Integer.toString((int) trip.get("sname"));
            } catch (Exception f) {
                t_id = (String) trip.get("name");
            }
        }
        //Try catch for special case of walking has no direction
        String t_direction = "";
        try {
            t_direction = (String) trip.get("direction");
        } catch (Exception e) {
            t_direction = "";
        }
        Trip resultTrip = new Trip(t_start, t_end, t_direction, t_dep_time, t_arr_time, t_veh, t_id, t_dep_track, t_arr_track);
        return resultTrip;
    }

    /**
     * This method return a list of journeys form the given arguments
     *
     * @param auth_token
     * @param start_station
     * @param end_station
     * @param time
     * @param date
     * @return
     */
//TODO i have taken care of special cases inluding trams, busses and walking... Seems to work but needs som "Stress" testing
    public List<Journey> getTrip(String auth_token, String start_station, String end_station, String time, String date, int arrivalTime) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("originId", start_station);
        parameters.put("date", date);
        parameters.put("time", time);
        parameters.put("destId", end_station);
        parameters.put("searchForArrival", Integer.toString(arrivalTime));

        String func = "/trip";

        List<Journey> resultJourneyList = new ArrayList<>();

        try {
            JSONObject JResult = getInfoFromVT(auth_token, parameters, func);
            JSONObject triplist = (JSONObject) JResult.get("TripList");
            JSONArray tripArray = (JSONArray) triplist.get("Trip");
            System.out.println(JResult.toString());
            System.out.println(triplist.toString());
            System.out.println(tripArray.toString());

            System.out.println("Length of triplist: " + triplist.length());
            System.out.println("Length of triparray: " + tripArray.length());
            for (int i = 0; i < tripArray.length(); i++) {
                Journey resJ = new Journey();
                JSONObject trip = (JSONObject) tripArray.get(i);
                System.out.println("class 1" + trip.get("Leg").getClass());
                Object obj = trip.get("Leg");
                //System.out.println("Objclass: "+obj.getClass());
                //System.out.println("ARRÄJJENLENGTH: ");
                // System.out.println(obj.toString());
                if (obj.getClass().equals(JSONArray.class)) {
                    JSONArray tempArr = (JSONArray) obj;
                    System.out.println("Obj is array of length: " + tempArr.length());
                    System.out.println(tempArr);
                    for (int j = 0; j < tempArr.length(); j++) {
                        JSONObject jsonObject = (JSONObject) tempArr.get(j);
                        System.out.println("Original from arr: " + jsonObject.toString());
                        Trip resultTrip = getTripFromJson(jsonObject);
                        resJ.addTrip(resultTrip);
                    }

                } else if (obj.getClass().equals(JSONObject.class)) {
                    Trip resultTrip = getTripFromJson((JSONObject) obj);
                    resJ.addTrip(resultTrip);
                } else {
                    System.out.println("Something is wrong... Terrible wrong");
                }
                resultJourneyList.add(resJ);
            }
            System.out.println("length of result journeys" + resultJourneyList.toString());
            System.out.println(resultJourneyList);
            return resultJourneyList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJourneyList;
    }

    @Override
    public List<Journey> getJourneys(String to, String from, Date date) throws Exception {
        SimpleDateFormat frmDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat frmTime = new SimpleDateFormat("HH:mm");
        String sDate = frmDate.format(date);
        String sTime = frmTime.format(date);
        String authKey = authenticate();

        String from_id = getIdFromName(from, authKey);
        String to_id = getIdFromName(to, authKey);

        List<Journey> resultList = getTrip(authKey, from_id, to_id, sTime, sDate, 1);

        return resultList;
    }

    private String getIdFromName(String arg, String authToken) throws Exception {
        String func = "/location.name";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("input", arg);

        JSONObject result = getInfoFromVT(authToken, parameters, func);
        System.out.println(result.toString());
        JSONObject level1 = (JSONObject) result.get("LocationList");
        JSONArray level2 = (JSONArray)level1.get("StopLocation");
        JSONObject level3 = (JSONObject) level2.get(0);
        String id = level3.get("id").toString();
        System.out.println(id);
        return id;
    }
    public float[] getLonLatFromStation(String station) throws Exception {
        String authKey = authenticate();
        String func = "/location.name";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("input", station);

        JSONObject result = getInfoFromVT(authKey, parameters, func);
        JSONObject level1 = (JSONObject) result.get("LocationList");
        JSONArray level2 = (JSONArray)level1.get("StopLocation");
        JSONObject level3 = (JSONObject) level2.get(0);
        System.out.println(level3.toString());
        String lon = level3.get("lon").toString();
        String lat = level3.get("lat").toString();
        float[] lonlat ={Float.parseFloat(lon), Float.parseFloat(lat)};

        return lonlat;
    }
}
