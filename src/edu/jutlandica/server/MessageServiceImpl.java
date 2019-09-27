package edu.jutlandica.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.jutlandica.client.MessageService;
import edu.jutlandica.client.dataclasses.Journey;

public class MessageServiceImpl extends RemoteServiceServlet implements MessageService {

	private static final long serialVersionUID = 1L;

	public List<Journey> getJourneys(String to, String from, String time, String date) {
		System.out.println("Hello");

		List<Journey> list = null;

		VTConnector vt = new VTConnector();
		try {
			String aut = vt.authenticate();
			list = vt.getTrip(aut, to, from, time, date);			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}