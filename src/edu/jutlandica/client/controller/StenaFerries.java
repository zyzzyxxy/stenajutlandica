package edu.jutlandica.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.jutlandica.client.StenaJutlandica;
import edu.jutlandica.client.dataclasses.Journey;

public class StenaFerries implements InputInterface {
	public final static String JUTLANDICA = "Jutlandica";
	public final static String DANICA = "Danica";
	public final static String VINGA = "Vinga";
	public final static String GERMANICA = "Germanica";

	private static ArrayList<StenaFerries> ferries;

	private String ferry;
	private String destination;

	private int departureHour;
	private int departureMinute;

	private int arrivalHour;
	private int arrivalMinute;

	private StenaFerries(String ferry, String destination, int departureHour, int departureMinute, int arrivalHour,
			int arrivalMinute) {
		this.ferry = ferry;
		this.destination = destination;
		this.departureHour = departureHour;
		this.departureMinute = departureMinute;
		this.arrivalHour = arrivalHour;
		this.arrivalMinute = arrivalMinute;
	}

	private static void init() {
		ferries = new ArrayList<StenaFerries>();
		ferries.add(new StenaFerries(JUTLANDICA, StenaJutlandica.FREDRIKSHAMN, 0, 30, 4, 10));
		ferries.add(new StenaFerries(DANICA, StenaJutlandica.FREDRIKSHAMN, 9, 10, 12, 30));
		ferries.add(new StenaFerries(VINGA, StenaJutlandica.FREDRIKSHAMN, 11, 0, 14, 45));
		ferries.add(new StenaFerries(JUTLANDICA, StenaJutlandica.FREDRIKSHAMN, 16, 0, 19, 25));
		ferries.add(new StenaFerries(DANICA, StenaJutlandica.FREDRIKSHAMN, 18, 20, 21, 50));
		ferries.add(new StenaFerries(VINGA, StenaJutlandica.FREDRIKSHAMN, 22, 10, 1, 30));
		ferries.add(new StenaFerries(GERMANICA, StenaJutlandica.KIEL, 18, 45, 9, 15));
	}

	public static StenaFerries getNextDeparture(int hour, int minute, String destination) {
		if (ferries == null)
			init();

		StenaFerries ferry = null;
		int valueHour = Integer.MAX_VALUE;
		int valueMinute = Integer.MAX_VALUE;
		for (StenaFerries f : ferries) {
			int fHour = f.departureHour - hour;
			int fMinute = f.departureMinute - minute;

			if (f.destination.equals(destination))
				if (fHour > 0 || (fHour == 0 && fMinute > 0)) {
					if (fHour < valueHour || (fHour == valueHour && fMinute < valueMinute)) {
						ferry = f;
						valueHour = fHour;
						valueMinute = fMinute;
					}
				}
		}

		return ferry;
	}
	
	public String getName() {
		return ferry;
	}
	
	public Date getDepartureDate() {
		Date date = new Date();
		date.setHours(departureHour);
		date.setMinutes(departureMinute);
		return date;		
	}
	
	public Date getArrivalDate() {
		Date date = new Date();
		date.setHours(arrivalHour);
		date.setMinutes(arrivalMinute);
		return date;		
	}
	
	public String getDepartureTime() {
		return (departureHour < 10 ? "0" + departureHour : departureHour) + ":" + (departureMinute < 10 ? "0" + departureMinute : departureMinute);		
	}
	
	public String getArrivalTime() {
		return (arrivalHour < 10 ? "0" + arrivalHour : arrivalHour) + ":" + (arrivalMinute < 10 ? "0" + arrivalMinute : arrivalMinute);		
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(ferry);
		s.append(", departure: " + departureHour + ":" + departureMinute);
		s.append(", arrival: " + arrivalHour + ":" + arrivalMinute);
		s.append(", " + destination);
		return s.toString();
	}

	@Override
	public List<Journey> getJourneys(String from, String to, String time, String date) {
		// TODO Auto-generated method stub
		return null;
	}
}
