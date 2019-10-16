package edu.jutlandica.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class FakeFerryConnector implements APIconnector {
	public static final SimpleDateFormat SIRI_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static final SimpleDateFormat SIRI_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat SIRI_FORMAT_TIME = new SimpleDateFormat("'T'HH:mm:ss");
	private boolean arrivalSearch = true; //if not assume departure search 
	
	public FakeFerryConnector() { }
	
	public boolean isArrivalSearch() {
		return arrivalSearch;
	}

	public void setArrivalSearch(boolean arrivalSearch) {
		this.arrivalSearch = arrivalSearch;
	}

	@Override
	public List<Journey> getJourneys(String to, String from, Date date) throws Exception {
		List<Journey> list = new ArrayList<Journey>();
		
		String dateString = SIRI_FORMAT_DATE.format(date);
		
		Journey j = new Journey();
		
		j = new Journey();
		j.addTrip(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T16:00:00"), SIRI_FORMAT.parse(dateString + "T19:25:00"), "Ferry", "Jutlandica"));
		list.add(j);
		
		j = new Journey();
		j.addTrip(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T18:20:00"), SIRI_FORMAT.parse(dateString + "T21:50:00"), "Ferry", "Danica"));
		list.add(j);
		
		j = new Journey();
		j.addTrip(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T22:10:00"), SIRI_FORMAT.parse(dateString + "T01:40:00"), "Ferry", "Vinga"));
		list.add(j);
		
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		Date date1 = new Date();
		Date date2 = new Date(date1.getTime());
		System.out.println(SIRI_FORMAT.format(date1));
		System.out.println(SIRI_FORMAT.format(date2));
		
		FakeFerryConnector ffc = new FakeFerryConnector();
		List<Journey> list = ffc.getJourneys("Fredrikshamn", "Göteborg", SIRI_FORMAT.parse("2019-12-12T16:00:00"));
		for (Journey j : list) {
			System.out.println(j.toString());
		}
	}

}
