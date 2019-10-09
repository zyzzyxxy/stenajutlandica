package edu.jutlandica.client.model;

import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;

import com.google.gwt.user.client.ui.HTML;

import edu.jutlandica.client.controller.StenaFerries;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class JourneyModel /*implements Observable*/{
	
	Journey journey;
	//TODO for all trips in journey dispaly relevant information
	public JourneyModel(Journey journey) {
		this.journey = journey;
	}
	
	public HTML getJourneyPanel() {
		StringBuilder sb = new StringBuilder();
		List<Trip> trips = journey.getTripList();
		sb.append("<div class=\"vehicle\">");
		for(Trip trip: trips) {
			sb.append("<h1 class=\"buss\">Fordon: ");
			sb.append(trip.getVehicle() + ": ");
			sb.append(trip.getIdentifier());
			sb.append("</h1>");
			
			sb.append("<h1 class=\"buss\">Avgång: ");
			sb.append(trip.getStart_station() + ": ");
			sb.append(trip.getDep_time());
			sb.append("</h1>");
			
			sb.append("<h1 class=\"buss\">Ankomst: ");
			sb.append(trip.getEnd_station() + ": ");
			sb.append(trip.getArrival_time());
			sb.append("</h1>");
			
			if (!trips.get(trips.size()-1).equals(trip)) {
				sb.append("<h1 class=\"buss\"> ___next_trip___</h1>");
			}
			
		}
		sb.append("</div>");
		
		/*HTML html = new HTML("<div class=\"vehicle\">\r\n" + 
				"      <h1 class=\"buss\">F&#228;rja: " + trip.getVehicle() + "</h1>\r\n" + 
				"      <h1 class=\"buss\">Avg&#229;ng:  " + trip.getDep_time() + "</h1>\r\n" + 
				"      <h1 class=\"buss\">Ankomst:  " + trip.getArrival_time() + "</h1>\r\n" + 
				"    </div>");*/
		return new HTML(sb.toString());
	}
	
	public String toString() {
		return journey.toString();
	}
	
	/*
	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}*/

}
