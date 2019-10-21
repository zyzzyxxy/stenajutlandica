package edu.jutlandica.server;

import java.text.ParseException;
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
	private boolean arrivalSearch = false; // if not assume departure search

	public FakeFerryConnector() {
	}

	public boolean isArrivalSearch() {
		return arrivalSearch;
	}

	public void setArrivalSearch(boolean arrivalSearch) {
		this.arrivalSearch = arrivalSearch;
	}

	@Override
	public List<Journey> getJourneys(String to, String from, Date date) throws Exception {
		List<Journey> list = new ArrayList<Journey>();

		Date today = date;
		String dateString = SIRI_FORMAT_DATE.format(date);
		Date nextDate;
		if (!isArrivalSearch())
			nextDate= getDateTomorrow(date);
		else
			nextDate= getDateYesterday(date);
		String dateStringTomorrow = SIRI_FORMAT_DATE.format(nextDate);
		
		while (list.size() < 5) {
			List<Trip> trips = getJourneysAtDate(today, dateString, dateStringTomorrow);
			
			for (Trip t : trips) {
				if (t.getDepDate().before(date) && isArrivalSearch() || t.getDepDate().after(date) && !isArrivalSearch())
				if (t.getEnd_station().equals(to) && (list.size() < 5)) {
					Journey j = new Journey();
					j.addTrip(t);
					list.add(j);
				}
			}
			
			today = nextDate;
			if (!isArrivalSearch())
				nextDate= getDateTomorrow(today);
			else
				nextDate= getDateYesterday(today);
			dateString = SIRI_FORMAT_DATE.format(today);
			dateStringTomorrow = SIRI_FORMAT_DATE.format(nextDate);
		}
		return list;
	}
	
	public List<Trip> getJourneysAtDate(Date today, String dateString, String dateStringTomorrow) throws ParseException {
		List<Trip> list = new ArrayList<>();
		int weekday = today.getDay(); // (0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 =Thursday, 5 = Friday, 6 = Saturday) 
		
		switch (weekday) {
		case 1:
		case 2:
		case 3:
		case 4:
			list.addAll(getTripsWeekdays(dateString, dateStringTomorrow));
			break;
		case 5:
		case 6:
		case 0:
			list.addAll(getTripsWeekends(dateString, dateStringTomorrow));
			break;
		}
		
		return list;
	}

	public Date getDateTomorrow(Date date) {
		return new Date(date.getTime() + 1000 * 60 * 60 * 24);
	}
	
	public Date getDateYesterday(Date date) {
		return new Date(date.getTime() - 1000 * 60 * 60 * 24);
	}

	public List<Trip> getTripsWeekdays(String dateString, String dateStringTomorrow) throws ParseException {
		List<Trip> trips = new ArrayList<Trip>();

		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T00:30:00"),
				SIRI_FORMAT.parse(dateString + "T04:10:00"), "Ferry", "Jutlandica"));
		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T09:10:00"),
				SIRI_FORMAT.parse(dateString + "T12:30:00"), "Ferry", "Danica"));
		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T11:10:00"),
				SIRI_FORMAT.parse(dateString + "T14:45:00"), "Ferry", "Vinga"));
		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T16:00:00"),
				SIRI_FORMAT.parse(dateString + "T19:25:00"), "Ferry", "Jutlandica"));
		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T18:20:00"),
				SIRI_FORMAT.parse(dateString + "T21:50:00"), "Ferry", "Danica"));
		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T22:10:00"),
				SIRI_FORMAT.parse(dateStringTomorrow + "T01:40:00"), "Ferry", "Vinga"));
		trips.add(new Trip("Göteborg", "Kiel", "", SIRI_FORMAT.parse(dateString + "T18:45:00"),
				SIRI_FORMAT.parse(dateStringTomorrow + "T09:15:00"), "Ferry", "Germanica"));

		return trips;
	}
	
	

	public List<Trip> getTripsWeekends(String dateString, String dateStringTomorrow) throws ParseException {
		List<Trip> trips = new ArrayList<Trip>();

		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T00:30:00"),
				SIRI_FORMAT.parse(dateString + "T04:10:00"), "Ferry", "Jutlandica"));
		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T08:00:00"),
				SIRI_FORMAT.parse(dateString + "T11:20:00"), "Ferry", "Danica"));
		trips.add(new Trip("Göteborg", "Fredrikshamn", "", SIRI_FORMAT.parse(dateString + "T18:00:00"),
				SIRI_FORMAT.parse(dateStringTomorrow + "T21:10:00"), "Ferry", "Vinga"));
		trips.add(new Trip("Göteborg", "Kiel", "", SIRI_FORMAT.parse(dateString + "T17:45:00"),
				SIRI_FORMAT.parse(dateStringTomorrow + "T09:15:00"), "Ferry", "Scandinavia"));

		return trips;
	}

	public static void main(String[] args) throws Exception {
		Date date1 = new Date();
		Date date2 = new Date(date1.getTime());
		System.out.println(SIRI_FORMAT.format(date1));
		System.out.println(SIRI_FORMAT.format(date2));

		FakeFerryConnector ffc = new FakeFerryConnector();
		List<Journey> list = ffc.getJourneys("Fredrikshamn", "Göteborg", SIRI_FORMAT.parse("2019-12-12T16:00:00"));
		int count = 1;
		for (Journey j : list) {
			System.out.println(count + ": " + j.toString());
			count++;
		}
	}

}
