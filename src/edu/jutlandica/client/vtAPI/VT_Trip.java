package edu.jutlandica.client.vtAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class contains information of one trip with one Line between 2 stations on that specific line
 */
public class VT_Trip {
    private String start_station;
    private String end_station;
    private String dep_time;
    private String direction;
    private String arrival_time;
    private String vehicle;
    private String identifier;
    Map<String, String> classVariables = new HashMap<>();

    public VT_Trip(String start_station, String end_station, String direction, String dep_time, String arrival_time, String vehicle, String identifier) {
        this.start_station = start_station;
        this.end_station = end_station;
        this.dep_time = dep_time;
        this.direction = direction;
        this.arrival_time = arrival_time;
        this.vehicle = vehicle;
        this.identifier = identifier;

        classVariables.put("start_station", start_station);
        classVariables.put("end_station", end_station);
        classVariables.put("dep_time", dep_time);
        classVariables.put("direction", direction);
        classVariables.put("arrival_time", arrival_time);
        classVariables.put("vehicle", vehicle);
        classVariables.put("identifier", identifier);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : classVariables.entrySet()) {
            result.append(entry.getKey() +": " + entry.getValue()+"\n") ;
        }
        return result.toString();
    }
}
