package edu.jutlandica.client.model;

import com.google.gwt.user.client.ui.HTML;

import edu.jutlandica.client.controller.StenaFerries;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class JourneyModel /*implements Observable*/{
	
	Journey journey;
	
	public JourneyModel(Journey journey) {
		this.journey = journey;
	}
	
	public HTML getJourneyPanel() {
		Trip trip = journey.getTripList().get(0);
		
		HTML html = new HTML("<div class=\"vehicle\">\r\n" + 
				"      <h1 class=\"buss\">F&#228;rja: " + trip.getVehicle() + "</h1>\r\n" + 
				"      <h1 class=\"buss\">Avg&#229;ng:  " + trip.getDep_time() + "</h1>\r\n" + 
				"      <h1 class=\"buss\">Ankomst:  " + trip.getArrival_time() + "</h1>\r\n" + 
				"    </div>");
		return html;
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
