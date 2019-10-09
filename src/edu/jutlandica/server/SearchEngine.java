package edu.jutlandica.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.jutlandica.client.StenaJutlandica;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class SearchEngine {
	
	public static final long TERMINAL_WAIT_TIME = 1800000; //Milliseconds
	
	public SearchEngine() {
	}

	public List<Journey> getJourneys(String to, String from, Date date) {
		List<Journey> list = new ArrayList<Journey>();
		String vtTo = "";

		if(to.equals(StenaJutlandica.KIEL))
			vtTo = "Chapmans Torg";
		else 
			vtTo = "masthuggstorget";		
		
		APIconnector vt = new VTConnector();
		
		APIconnector fc = new FerryConnector();
		
		try {
			list.addAll(fc.getJourneys(to, from, date));
			int count = 0;
			for (Journey j : list) {
				long ferryDepTime = j.getTripList().get(0).getDepDate().getTime();
				Date busRequestedArrivalTime = new Date(ferryDepTime - TERMINAL_WAIT_TIME);
				List<Journey> vtData = vt.getJourneys(vtTo, from, busRequestedArrivalTime);
				Date busActualArrivalTime = vtData.get(0).getTripList().get(vtData.get(0).getTripList().size()-1).getArrivalDate();	
				
				List<Trip> ferryTripList = j.getTripList();
				ferryTripList.add(0, new Trip(vtTo, "Terminalen", "", busActualArrivalTime, new Date(busActualArrivalTime.getTime() + TERMINAL_WAIT_TIME), "WALK/WAIT", "GA"));
				ferryTripList.addAll(0, vtData.get(0).getTripList());
				if (++count > 5) break;
			}
		} catch (Exception e) { e.printStackTrace(); }
		return list;
	}
}
