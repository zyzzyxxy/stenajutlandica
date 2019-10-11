package edu.jutlandica.server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SIRIreader implements APIconnector {
	public static final SimpleDateFormat SIRI_TIME_FORMAT = new SimpleDateFormat("yy-MM-dd'T'HH:mm:ss"); // SIRI time
																											// format
	private String exampleFile = "SIRI-ET-2019-10-11.xml";
	private String destination = "Fredrikshamn";

	public SIRIreader() throws Exception {
		
	}

	public Element getEstimatedCall(int index, Node estimatedVehicleJourney) {
		Element e = (Element) estimatedVehicleJourney;
		NodeList nl = e.getElementsByTagName("EstimatedCall");
		return (Element) nl.item(index);
	}

	public List<Node> getEstimatedVehicleJourneys(String searchString, Document doc) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		NodeList nList = doc.getElementsByTagName("EstimatedVehicleJourney");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node n = nList.item(temp);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				String searchslut = e.getElementsByTagName("DirectionRef").item(0).getTextContent();
				if (searchslut.equals(destination))
					nodes.add(n);
			}
		}
		return nodes;
	}

	private List<Trip> getTripsToEndDest(List<Node> estimatedVehicleJourneys) throws Exception {
		ArrayList<Trip> ret = new ArrayList<>();

		for (Node n : estimatedVehicleJourneys) {
			Element e = (Element) n;

			String start_station = e.getElementsByTagName("OriginName").item(0).getTextContent();
			String end_station = e.getElementsByTagName("DirectionRef").item(0).getTextContent();
			String vehicle = e.getElementsByTagName("LineRef").item(0).getTextContent();
			String vehicleMode = e.getElementsByTagName("VehicleMode").item(0).getTextContent();

			Date depDate = SIRI_TIME_FORMAT.parse(
					getEstimatedCall(0, n).getElementsByTagName("ExpectedDepartureTime").item(0).getTextContent());
			Date arrivalDate = SIRI_TIME_FORMAT
					.parse(getEstimatedCall(1, n).getElementsByTagName("AimedArrivalTime").item(0).getTextContent());

			ret.add(new Trip(start_station, end_station, end_station, depDate, arrivalDate, vehicleMode,
					vehicle));
		}

		return ret;
	}

	@Override
	public List<Journey> getJourneys(String to, String from, Date date) throws Exception {
		File file = new File(exampleFile);
		destination = to;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		List<Node> estimatedVehicleJourneys = getEstimatedVehicleJourneys(destination, doc);
		List<Trip> trips = getTripsToEndDest(estimatedVehicleJourneys);
		
		List<Journey> ret = new ArrayList<Journey>();
		for(Trip t : trips) {
        	Journey temp = new Journey();
        	temp.addTrip(t);
        	ret.add(temp);
        }
		return ret;
	}

}
