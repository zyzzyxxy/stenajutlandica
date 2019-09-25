package edu.jutlandica.client;

import java.util.ArrayList;
import java.util.Date;

public class BusDepartures {
	private static ArrayList<BusDepartures> departures;
	
	private String line;
	
	public final static String DANMARK = "DANMARKS TERMINALEN";
	public final static String TYSKLAND = "TYSKLANDS TERMINALEN";

	private final String orgin = "Centralstationen";
	private String destination;
	
	private int departureHour;
	private int departureMinute;
	private long departureDelay;	
	private int arrivalHour;
	private int arrivalMinute;
	private long arrivalDelay;
	
	private BusDepartures(String line, String destination, int departureHour, int departureMinute, int arrivalHour, int arrivalMinute) {
		this.line = line;
		this.destination = destination;
		this.departureHour = departureHour;
		this.departureMinute = departureMinute;
		this.arrivalHour = arrivalHour;
		this.arrivalMinute = arrivalMinute;
	}
	
	private static void init() {
		departures = new ArrayList<BusDepartures>();
		departures.add(new BusDepartures("9", DANMARK, 16, 23, 16, 43));
		departures.add(new BusDepartures("32", TYSKLAND, 18, 14, 18, 35));	
	}
	
	public static BusDepartures getNextDeparture(int hour, int minute, String destination) {
		if (departures == null) init();
		
		for (BusDepartures d : departures) {
			if (destination.equals(d.destination)) return d;
		}
		
		return null;		
	}
	
	public static ArrayList getDepartures() {
		return departures;
	}

	public String getLine() {
		return line;
	}

	public String getOrgin() {
		return orgin;
	}

	public String getDestination() {
		return destination;
	}

	public long getDepartureDelay() {
		return departureDelay;
	}
	
	public long getArrivalDelay() {
		return arrivalDelay;
	}
	
	public int getDepartureHour() {
		return departureHour;
	}

	public void setDepartureHour(int departureHour) {
		this.departureHour = departureHour;
	}

	public int getDepartureMinute() {
		return departureMinute;
	}

	public void setDepartureMinute(int departureMinute) {
		this.departureMinute = departureMinute;
	}

	public int getArrivalHour() {
		return arrivalHour;
	}

	public void setArrivalHour(int arrivalHour) {
		this.arrivalHour = arrivalHour;
	}

	public int getArrivalMinute() {
		return arrivalMinute;
	}

	public void setArrivalMinute(int arrivalMinute) {
		this.arrivalMinute = arrivalMinute;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Line: " + line);
		s.append(", Destination: " + destination);
		s.append(", Departure: " + departureHour + ":" + departureMinute);
		s.append(", Arrival: " + arrivalHour + ":" + arrivalMinute);
		return s.toString();		
	}
}