package edu.jutlandica.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.jutlandica.client.StenaJutlandica;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class FerryConnector implements InputInterface {
	private XMLHandler xmlHandler;


	public FerryConnector() {
		xmlHandler = new XMLHandler();
	}



	@Override
	public List<Journey> getJourneys(String from, String to, String time, String date) {
	
		ArrayList<Trip> FerryTrips = xmlHandler.getFerriesToDes(to);
		ArrayList<Journey> FerryJourneys = new ArrayList<>();
		for(Trip ferry : FerryTrips){
			Journey journey = new Journey(); //TODO: Add more trips to each journey, (checking, walking, etc)
			journey.addTrip(ferry);
			FerryJourneys.add(journey);
		}
		return FerryJourneys;
	}
}
