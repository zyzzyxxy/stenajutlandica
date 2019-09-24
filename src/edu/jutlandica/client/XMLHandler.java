package edu.jutlandica.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class XMLHandler {

	private File file;
	private Document doc;

	public XMLHandler() {
		file = new File("/src/test.xml");
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {

		}
	}

	public ArrayList<Node> searchEndDestXML(String searchString) {

		NodeList listOfNodes = doc.getElementsByTagName("Vehicle");
		ArrayList<Node> res = new ArrayList<>();
		for (int temps = 0; temps < listOfNodes.getLength(); temps++) {
			Node sNode = listOfNodes.item(temps);

			if (sNode.getNodeType() == Node.ELEMENT_NODE) {
				Element sElement = (Element) sNode;
				String searchslut = sElement.getElementsByTagName("Destiantion").item(0).getTextContent();
				if (searchString.compareTo(searchslut) == 0) {

				}
			}
		}
		return res;
	}

}