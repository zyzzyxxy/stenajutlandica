package edu.jutlandica.client.model;

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
		sb.append("<div class=\"vehicle\">");
		for(Trip trip: journey.getTripList()) {
			sb.append("<h1 class=\"buss\">F&#228;rja: \"");
			sb.append(trip.getVehicle());
			sb.append("</h1>");
			
			sb.append("<h1 class=\"buss\">F&#228;rja: \"");
			sb.append(trip.getDep_time());
			sb.append("</h1>");
			
			sb.append("<h1 class=\"buss\">F&#228;rja: \"");
			sb.append(trip.getArrival_time());
			sb.append("</h1>");
			
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
