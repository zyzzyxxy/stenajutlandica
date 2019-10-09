package edu.jutlandica.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.dataclasses.Trip;

public class FerryConnector implements APIconnector{
/*
 * This class reads a xml file on the server and returns data to the client
 */
	
	@Override
	public List<Journey> getJourneys(String to, String from, Date date) throws Exception {
		List<Journey> ret = new ArrayList<Journey>();
		
		File file = new File("test.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
		
        List<Node> toDest = searchEndDestXML(to,doc);
        List<Trip> ferriesToDest = getTripsToEndDest(toDest);
        for(Trip t : ferriesToDest) {
        	Journey temp = new Journey();
        	temp.addTrip(t);
        	ret.add(temp);
        }
		return ret;
	}
	
	/**
	 * Used to search for all ferries to a specific end destination
	 * @param searchString The end destination, used to compare with each ferry
	 * @param doc (org.w3c) document containing the xmlfile
	 * @return List of nodes containing all ferry to end destination
	 */
	private List<Node> searchEndDestXML(String searchString, Document doc) {

		NodeList listOfFerries = doc.getElementsByTagName("Ferries");
		ArrayList<Node> ret = new ArrayList<>();
		for (int temps = 0; temps < listOfFerries.getLength(); temps++) {
			Node ferry = listOfFerries.item(temps);

			if (ferry.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) ferry;
				String searchslut = e.getElementsByTagName("Destination").item(0).getTextContent();
				if (searchString.compareTo(searchslut) == 0) {
					ret.add(ferry);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Used to create trips from nodes of ferries
	 * @param listOfFerries a list of ferry nodes, will be converted over to trip
	 * @return a list of trips containing the ferries from the list of nodes
	 */
	private List<Trip> getTripsToEndDest(List<Node> listOfFerries){
		ArrayList<Trip> ret = new ArrayList<>();
		
		for(Node ferry: listOfFerries) {
			if (ferry.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) ferry;
				ret.add(new Trip("Goteborgs Hamn",
						e.getElementsByTagName("Destination").item(0).getTextContent(),
						"MOT" + e.getElementsByTagName("Destination").item(0).getTextContent(),
						e.getElementsByTagName("DepartureTime").item(0).getTextContent(),
						e.getElementsByTagName("ArrivalTime").item(0).getTextContent(),
						"Ferry",
						e.getElementsByTagName("Name").item(0).getTextContent()));
				}
			}
		return ret;
	}

}