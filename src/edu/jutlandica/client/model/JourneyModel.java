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

public class JourneyModel /* implements Observable */ {

	Journey journey;
	final VerticalPanel basePanel;
	private boolean open = false;

	public JourneyModel() {
		basePanel = new VerticalPanel();
	}

	// TODO for all trips in journey dispaly relevant information
	public JourneyModel(Journey journey) {
		basePanel = new VerticalPanel();
		this.journey = journey;
	}

	public String getTripTag(Trip trip) {
		StringBuilder sb = new StringBuilder();
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
		
		sb.append("<h1 class=\"buss\">");
		sb.append(trips.get(0).getStart_station() + " -> ");
		sb.append(fmt1.format(depDate));
		sb.append(" - ");
		sb.append(fmt2.format(arrivalDate));
		sb.append("   Restid " + (travelTime / (60 * 60 * 1000) % 24) + " h " + (travelTime / (60 * 1000) % 60) + " min");
		sb.append("</h1>");
		
		sb.append("</div>");
		return new HTML(sb.toString());
	}

	public HTML getAllTrips() {
		StringBuilder sb = new StringBuilder();
		List<Trip> trips = journey.getTripList();
		sb.append("<div class=\"vehicle\">");
		for (Trip trip : trips) {
			sb.append(getTripTag(trip));
			if (!trips.get(trips.size() - 1).equals(trip)) {
				sb.append("<h1 class=\"buss\"> ___next_trip___</h1>");
			}
		}
		sb.append("</div>");
		return new HTML(sb.toString());
	}
	
	HTML header;
	public Widget getJourneyPanel() {
		header = getHeader();
		open = false;

		header.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (open) {
					basePanel.clear();
					basePanel.add(header);
					open = false;
				} else {
					basePanel.add(getAllTrips());
					open = true;
				}
			}
		});

		basePanel.add(header);
		return basePanel;
	}

	public String toString() {
		return journey.toString();
	}
}
