package edu.jutlandica.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.jutlandica.client.dataclasses.Journey;

public class SearchEngine {
	public SearchEngine() {
	}

	public List<Journey> getJourneys(String to, String from, Date date) {
		List<Journey> list = new ArrayList<Journey>();

		APIconnector vt = new VTConnector();
		try {
			list.addAll(vt.getJourneys(to, from, date));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
