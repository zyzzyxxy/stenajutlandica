package edu.jutlandica.client.dataclasses;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.i18n.client.DateTimeFormat;

import edu.jutlandica.client.controller.StringTime;

/**
 * This class contains information of one trip with one Line between 2 stations
 * on that specific line
 */
public class Trip implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String start_station;
	private String end_station;
	private Date dep_time;
	private String direction;
	private Date arrival_time;
	private String vehicle;
	private String identifier;
	Map<String, String> classVariables = new HashMap<>();
	
	public Trip() {}
	
	@Deprecated
	public Trip(String start_station, String end_station, String direction, String dep_time, String arrival_time,
			String vehicle, String identifier) {
		this.start_station = start_station;
		this.end_station = end_station;
		this.direction = direction;
		this.dep_time = parseDate(dep_time);
		this.arrival_time = parseDate(arrival_time);
		this.vehicle = vehicle;
		this.identifier = identifier;
        this.track = "";


		classVariables.put("start_station", start_station);
		classVariables.put("end_station", end_station);
		classVariables.put("dep_time", dep_time);
		classVariables.put("direction", direction);
		classVariables.put("arrival_time", arrival_time);
		classVariables.put("vehicle", vehicle);
		classVariables.put("identifier", identifier);
        classVariables.put("track", track);

	}
	
	public Trip(String start_station, String end_station, String direction, Date dep_time, Date arrival_time,
			String vehicle, String identifier) {
		this.start_station = start_station;
		this.end_station = end_station;		
		this.direction = direction;
		this.dep_time = dep_time;
		this.arrival_time = arrival_time;
		this.vehicle = vehicle;
		this.identifier = identifier;
        this.track = "";

		classVariables.put("start_station", start_station);
		classVariables.put("end_station", end_station);
		classVariables.put("dep_time", dep_time.toString());
		classVariables.put("direction", direction);
		classVariables.put("arrival_time", arrival_time.toString());
		classVariables.put("vehicle", vehicle);
		classVariables.put("identifier", identifier);
        classVariables.put("track", track);

	}
     public Trip(String start_station, String end_station, String direction, String dep_time, String arrival_time, String vehicle, String identifier, String track) {
        this.start_station = start_station;
        this.end_station = end_station;
        this.dep_time = dep_time;
        this.direction = direction;
        this.arrival_time = arrival_time;
        this.vehicle = vehicle;
        this.identifier = identifier;
        this.track = track;

        classVariables.put("start_station", start_station);
        classVariables.put("end_station", end_station);
        classVariables.put("dep_time", dep_time);
        classVariables.put("direction", direction);
        classVariables.put("arrival_time", arrival_time);
        classVariables.put("vehicle", vehicle);
        classVariables.put("identifier", identifier);
        classVariables.put("track", track);
    }
	
	public Date parseDate(String time) {
		Date date = new Date();
		String[] split = time.split(":");
		int hour = Integer.parseInt(split[0]);
		int minute = Integer.parseInt(split[1]);
		
		date.setHours(hour);
		date.setMinutes(minute);
		
		return date;
	}

	public String getStart_station() {
		return start_station;
	}

	public String getEnd_station() {
		return end_station;
	}
	
	@Deprecated
	public String getDep_time() {
		DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm");
		return fmt.format(dep_time);
	}
	
	public Date getDepDate() {
		return dep_time;
	}

	public String getDirection() {
		return direction;
	}
	
	@Deprecated
	public String getArrival_time() {
		DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm");
		return fmt.format(arrival_time);
	}
	
	public Date getArrivalDate() {
		return arrival_time;
	}

	public String getVehicle() {
		return vehicle;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Map<String, String> getClassVariables() {
		return classVariables;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : classVariables.entrySet()) {
			result.append(entry.getKey() + ": " + entry.getValue() + "\n");
		}
		return result.toString();
	}
}
