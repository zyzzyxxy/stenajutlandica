package edu.jutlandica.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.jutlandica.client.SearchService;
import edu.jutlandica.client.dataclasses.Journey;

public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

	private static final long serialVersionUID = 1L;

	public List<Journey> getJourneys(String to, String from, Date date) {
		SearchEngine search = new SearchEngine();
		try {
			return search.getJourneys(to, from, date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}