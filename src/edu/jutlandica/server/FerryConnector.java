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

public class FerryConnector implements APIconnector{

	@Override
	public List<Journey> getJourneys(String to, String from, Date date) throws Exception {
		List<Journey> list = new ArrayList<Journey>();
		
		File file = new File("test.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
		
        List<Node> toDest = searchEndDestXML(to,doc);
        
		return list;
	}
	
	private List<Node> searchEndDestXML(String searchString, Document doc) {

		NodeList listOfFerries = doc.getElementsByTagName("Vehicle");
		ArrayList<Node> res = new ArrayList<>();
		for (int temps = 0; temps < listOfFerries.getLength(); temps++) {
			Node ferry = listOfFerries.item(temps);

			if (ferry.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) ferry;
				String searchslut = e.getElementsByTagName("Destiantion").item(0).getTextContent();
				if (searchString.compareTo(searchslut) == 0) {
					res.add(ferry);
				}
			}
		}
		return res;
	}

}
