package edu.jutlandica.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.jutlandica.client.StenaJutlandica;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class StenaFerries implements InputInterface {
	public final static String JUTLANDICA = "Jutlandica";
	public final static String DANICA = "Danica";
	public final static String VINGA = "Vinga";
	public final static String GERMANICA = "Germanica";

	private static ArrayList<Journey> ferries;
	
	public StenaFerries() {
		init();
	}
	
	private static void init() {
		Journey j;
		ferries = new ArrayList<Journey>();
		
		j = new Journey();
		j.addTrip(new Trip("Gothenburg", StenaJutlandica.FREDRIKSHAMN, "UNKNOWN", "00:30", "04:10", JUTLANDICA, "UNKNOW"));
		ferries.add(j);
		
		j = new Journey();
		j.addTrip(new Trip("Gothenburg", StenaJutlandica.FREDRIKSHAMN, "UNKNOWN", "09:10", "12:30", DANICA, "UNKNOW"));
		ferries.add(j);
		
		j = new Journey();
		j.addTrip(new Trip("Gothenburg", StenaJutlandica.FREDRIKSHAMN, "UNKNOWN", "11:00", "14:45", VINGA, "UNKNOW"));
		ferries.add(j);
		
		j = new Journey();
		j.addTrip(new Trip("Gothenburg", StenaJutlandica.FREDRIKSHAMN, "UNKNOWN", "16:00", "19:25", JUTLANDICA, "UNKNOW"));
		ferries.add(j);
		
		j = new Journey();
		j.addTrip(new Trip("Gothenburg", StenaJutlandica.FREDRIKSHAMN, "UNKNOWN", "18:20", "21:50", DANICA, "UNKNOW"));
		ferries.add(j);
		
		j = new Journey();
		j.addTrip(new Trip("Gothenburg", StenaJutlandica.FREDRIKSHAMN, "UNKNOWN", "22:10", "01:30", VINGA, "UNKNOW"));
		ferries.add(j);
		
		j = new Journey();
		j.addTrip(new Trip("Gothenburg", StenaJutlandica.KIEL, "UNKNOWN", "18:45", "09:15", GERMANICA, "UNKNOW"));
		ferries.add(j);
	}
	
	@Override
	public List<Journey> getJourneys(String from, String to, String time, String date) {
		if (ferries == null)
			init();
		
		ArrayList<Journey> journeys = new ArrayList<Journey>();
		StringTime sTime = StringTime.parseStringTime(time);
		
		Journey ferry = null;
		int valueHour = Integer.MAX_VALUE;
		int valueMinute = Integer.MAX_VALUE;
		for (Journey f : ferries) {
			Trip trip = f.getTripList().get(0);
			StringTime depTime = StringTime.parseStringTime(trip.getDep_time());
			
			int fHour = depTime.getHour() - sTime.getHour();
			int fMinute = depTime.getMinute() - sTime.getMinute();
			
			if (trip.getEnd_station().equals(to)) {
								
				if (fHour > 0 || (fHour == 0 && fMinute > 0)) {
					if (fHour < valueHour || (fHour == valueHour && fMinute < valueMinute)) {
						ferry = f;
						valueHour = fHour;
						valueMinute = fMinute;
					}
				}
			}
		}
		journeys.add(ferry);
		return journeys;
	}
	
	public String toString() {
		return super.toString();
	}
}
