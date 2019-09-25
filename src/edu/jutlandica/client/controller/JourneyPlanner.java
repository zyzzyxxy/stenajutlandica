package edu.jutlandica.client.controller;

import java.util.ArrayList;
import java.util.List;

import edu.jutlandica.client.dataclasses.Journey;

public class JourneyPlanner implements InputInterface{
	
	FerryConnector ferryconnector; 
	
	public JourneyPlanner() {
		ferryconnector = new FerryConnector();
	}
	
	@Override
	public List<Journey> getJourneys(String from, String to, String time, String date) {
		//Todo get journeys from VT
		return getFerryJourneys("Göteborgs Hamn",to,time,date);
		//Todo Combine journeys

	}
	
	
	private List<Journey> getFerryJourneys(String from, String to, String time, String date){
		return ferryconnector.getJourneys(from, to, time, date);
	}
	

}
