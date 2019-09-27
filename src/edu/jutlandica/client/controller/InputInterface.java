package edu.jutlandica.client.controller;

import java.util.List;

import edu.jutlandica.client.dataclasses.Journey;

public interface InputInterface {
	List<Journey> getJourneys(String from, String to, String time, String date);
}
