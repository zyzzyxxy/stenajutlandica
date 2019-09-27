/*
 * This class connects all journeys from ferries and Vt and returns one united Journey
 */

package edu.jutlandica.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;

import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;
import edu.jutlandica.client.model.JourneyModel;

public class SearchEngine{
	private InputInterface ferriesInput;
	private InputInterface vtInput;
	
	public SearchEngine() {
		ferriesInput = new StenaFerries();
		vtInput  = null;
	}
	
	public List<JourneyModel> search(String from, String to, Date date) {
		ArrayList<JourneyModel> journeys = new ArrayList<JourneyModel>();
		StenaFerries stenaFerries = new StenaFerries();
		Journey ferry;
		int hours = date.getHours();
		int minute = date.getMinutes();
		
		for (int i = 0; i < 4; i++) {
			
			String depTime = (new StringTime(hours, minute)).toString();
			ferry = stenaFerries.getJourneys(from, to, depTime, "").get(0); 
			if (ferry != null) {
				List<Trip> trips = ferry.getTripList();
				Trip firstTrip = trips.get(0);			
				journeys.add(new JourneyModel(ferry));
				StringTime time = StringTime.parseStringTime(firstTrip.getDep_time());
				hours = time.getHour();
				minute = time.getMinute();
			}			
			else break;
		}		
		
		return journeys;
	}
}
