package edu.jutlandica.server;

import java.util.Date;
import java.util.List;

import edu.jutlandica.client.dataclasses.Journey;

public interface APIconnector {
	List<Journey> getJourneys(String to, String from, Date date) throws Exception;
}
