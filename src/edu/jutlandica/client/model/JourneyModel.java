package edu.jutlandica.client.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import edu.jutlandica.client.controller.StenaFerries;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class JourneyModel /* implements Observable */{

	Journey journey;
	final VerticalPanel basePanel;
	private boolean open = false;

	public JourneyModel(){
		basePanel = new VerticalPanel();
	}

	// TODO for all trips in journey dispaly relevant information
	public JourneyModel(Journey journey){
		basePanel = new VerticalPanel();
		this.journey = journey;
	}

	public String getTripTag(Trip trip){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class=\"iconVehicle\">"); //ska vara centrerad. 
		sb.append("<p class=\"identifier\">");
		//sb.append(trip.getVehicle() + ":");
		if(trip.getVehicle().contentEquals("WALK")) 
			sb.append("<img src=\"walking.svg\" height=\"24px\" width=\"24px\" />");
		
		if(trip.getVehicle().contentEquals("BOAT"))
			sb.append("<img src=\"ship.svg\" height=\"24px\" width=\"24px\" />");
		
		if(trip.getVehicle().contentEquals("TRAM")) 
			sb.append("<img src=\"tram.svg\" height=\"24px\" width=\"24px\" />");
			
		if(trip.getVehicle().contentEquals("BUS")) 
			sb.append("<img src=\"bus.svg\" height=\"24px\" width=\"24px\" />");
		
		if(trip.getVehicle().contentEquals("Ferry"))
			sb.append("<img src=\"ship.svg\" height=\"40px\" width=\"40px\" />");
		
		if(trip.getVehicle().contentEquals("WALK/WAIT")) {
			sb.append("<img src=\"building.svg\" height=\"40px\" width=\"40px\" />");
		 	sb.append("<img src=\"walking.svg\" height=\"24px\" width=\"24px\" />");
		}
		
		if(!trip.getVehicle().contentEquals("WALK") && !trip.getVehicle().contentEquals("WALK/WAIT"))
			sb.append(" " + trip.getIdentifier()); 
		sb.append("</p>");
		sb.append("</div>");
		
		if(trip.getVehicle().contentEquals("Ferry"))
			//sb.append("<div class=\"buy\"> ");
			sb.append("<button class=\"bokaresa\" onclick=\" window.open('https://www.stenaline.se/till-danmark','_blank')\"> Köp Biljett</button>");
			//sb.append("</div>");
		
		if(trip.getVehicle().contentEquals("WALK")){
			sb.append("<div class=\"wrapper\">");
				sb.append("<div class=\"row\">");
			
				sb.append("<div class=\"avg\">");
				sb.append("Från: ");
				sb.append("</div>");
			
				sb.append("<div class=\"hall\">");
				sb.append(trip.getStart_station());
				sb.append("</div>");
			
				if(!trip.getDepTrack().contentEquals("")){
					sb.append("<div class=\"lage\">");
					sb.append("L&#xE4;ge " + trip.getDepTrack() + " ");
					sb.append("</div>");
				}
				sb.append("<div class=\"tid\">");
				sb.append(trip.getDep_time());
				sb.append("</div>");
			
				//sb.append("</p> ");
				sb.append("</div>");//row
			
				sb.append("<div class=\"row\">");
			
				sb.append("<div class=\"avg\">");
				sb.append("Till: ");
				sb.append("</div>");
			
				sb.append("<div class=\"hall\">");
				sb.append(trip.getEnd_station());
				sb.append("</div>");
				if(!trip.getArrTrack().contentEquals("")){
					sb.append("<div class=\"lage\">");
					sb.append("L&#xE4;ge " + trip.getArrTrack()+" ");
					sb.append("</div>");
				}	
			
				sb.append("<div class=\"tid\">");
				sb.append(trip.getArrival_time());
				sb.append("</div>");
				
			
				sb.append("</div>");//row
			
				sb.append("</div>");//wrapper
		}
		else {
			sb.append("<div class=\"wrapper\">");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"avg\">");
			sb.append("Avgång: ");
			sb.append("</div>");
			sb.append("<div class=\"hall\">");
			sb.append(trip.getStart_station());
			sb.append("</div>");
			
			if(!trip.getDepTrack().contentEquals("")){
				sb.append("<div class=\"lage\">");
				sb.append("L&#xE4;ge " + trip.getDepTrack() + " ");
				sb.append("</div>");
			}
			
			sb.append("<div class=\"tid\">");
			sb.append(trip.getDep_time());
			sb.append("</div>");	
			sb.append("</div>");//row
			sb.append("<div class=\"row\">");			
			sb.append("<div class=\"avg\">");
			sb.append("Ankomst: ");
			sb.append("</div>");
			sb.append("<div class=\"hall\">");
			sb.append(trip.getEnd_station());
			sb.append("</div>");
			if(!trip.getArrTrack().contentEquals("")){
				sb.append("<div class=\"lage\">");
				sb.append("L&#xE4;ge " + trip.getArrTrack()+" ");
				sb.append("</div>");
			}
			
			sb.append("<div class=\"tid\">");
			sb.append(trip.getArrival_time());
			sb.append("</div>");
			sb.append("</div>");//row
			sb.append("</div>");//wrapper
		}
		return sb.toString();
	}

	public HTML getFirstTrip() {
		StringBuilder sb = new StringBuilder();
		Trip trip = journey.getTripList().get(0);
		sb.append("<div class=\"vehicle\">");
		sb.append(getTripTag(trip));
		sb.append("</div>");
		return new HTML(sb.toString());
	}
	
	public HTML getHeader() {
		StringBuilder sb = new StringBuilder();
		List<Trip> trips = journey.getTripList();
		Date depDate = trips.get(0).getDepDate();
		Date arrivalDate = trips.get(trips.size()-1).getArrivalDate();
		DateTimeFormat fmt1 = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");
		DateTimeFormat fmt2 = DateTimeFormat.getFormat("HH:mm");
		long travelTime = arrivalDate.getTime() - depDate.getTime();
		
		sb.append("<div class=\"header\">");
		
		sb.append("<div class=\"left\"> ");
		sb.append("<p>");
		sb.append(trips.get(0).getStart_station() + " -> ");
		sb.append("</p>");
		sb.append("</div>");
		
		sb.append("<div class=\"right\"> ");
		sb.append("<p>");
		sb.append(fmt1.format(depDate));
		sb.append(" - ");
		sb.append(fmt2.format(arrivalDate));
		sb.append("   Restid " + (travelTime / (60 * 60 * 1000) % 24) + " h " + (travelTime / (60 * 1000) % 60) + " min");
		sb.append("</p>");
		sb.append("</div>");
		
		sb.append("</div>");
		return new HTML(sb.toString());
	}

	public HTML getAllTrips(){
		StringBuilder sb = new StringBuilder();
		List<Trip> trips = journey.getTripList();
		sb.append("<div class=\"vehicle\">");
		for (Trip trip : trips) {
			sb.append(getTripTag(trip));
			if (!trips.get(trips.size() - 1).equals(trip)) {
				sb.append("<br></br>");
			}
		}
		sb.append("</div>");
		return new HTML(sb.toString());
	}
	
	HTML header;
	public Widget getJourneyPanel(){
		header = getHeader();
		open = false;

		header.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				if (open){
					basePanel.clear();
					basePanel.add(header);
					open = false;
				}else{
					basePanel.add(getAllTrips());
					open = true;
				}
			}
		});

		basePanel.add(header);
		return basePanel;
	}

	public String toString(){
		return journey.toString();
	}
}
