package edu.jutlandica.client.controller;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import edu.jutlandica.client.dataclasses.Trip;

import java.util.ArrayList;

public class XMLHandler {
	
	 private final String xmlurl = "test.xml";
	 private String xmlText;
	 private Document doc;
	
public XMLHandler() {
	
}
     
 public ArrayList<Node> searchEndDestXML(String searchString) {
	 doc = XMLParser.parse(xmlText);
	 NodeList listOfNodes = doc.getElementsByTagName("Vehicle");
	 ArrayList<Node> res = new ArrayList<>();
     for( int temps = 0; temps <listOfNodes.getLength();temps++){
         Node sNode = listOfNodes.item(temps);

         if(sNode.getNodeType() == Node.ELEMENT_NODE){
             Element sElement = (Element) sNode;
             String searchslut = sElement.getElementsByTagName("Destiantion").item(0).getFirstChild().getNodeValue();
             if(searchString.compareTo(searchslut) == 0){
                 
             }
         }
     }
	 return res;
 }
 
 public ArrayList<Trip> getFerriesToDes(String searchString){ 
	 httpGetFile(xmlurl, new AsyncCallback<String>() {
	      public void onFailure(Throwable caught) {
	        xmlText = "Error";
	      }
	       
	      public void onSuccess(String xmlData) {
	        xmlText = xmlData;
	      }
	    });
	 ArrayList<Node> resultsInNode = searchEndDestXML(searchString);
	 ArrayList<Trip> resultsInFerries = new ArrayList<>();
	 
	 for(Node Ferries : resultsInNode){
		 if(Ferries.getNodeType() == Node.ELEMENT_NODE) {
		 Element ferriesElement = (Element) Ferries;
		 
		 resultsInFerries.add(
				 new Trip("Göteborgs Hamn",
						 ferriesElement.getElementsByTagName("Destination").item(0).getFirstChild().getNodeValue(),
						 ferriesElement.getElementsByTagName("DepartureTime").item(0).getFirstChild().getNodeValue(),
						 "",
						 ferriesElement.getElementsByTagName("ArrivalTime").item(0).getFirstChild().getNodeValue(),
						 "Ferry",
						 ferriesElement.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue() 
						 ));
		 }
	 }
	 return resultsInFerries;
 }
 public static void httpGetFile(final String url, final AsyncCallback<String> callback) {
	    final RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
	    rb.setCallback(new RequestCallback() {
	      public void onResponseReceived(Request request, Response response) {
	        try {
	          final int responseCode = response.getStatusCode() / 100;
	          if (url.startsWith("file:/") || (responseCode == 2)) {
	            callback.onSuccess(response.getText());
	          } else {
	            callback.onFailure(new IllegalStateException("HttpError#" + response.getStatusCode() + " - " + response.getText()));
	            }
	        } catch (Throwable e) {
	          callback.onFailure(e);
	        }
	      }
	      public void onError(Request request, Throwable exception) {
	        callback.onFailure(exception);
	      }
	    });
	    try {
	      rb.send();
	    } catch (RequestException e) {
	      callback.onFailure(e);
	    }
	  }
   
     
}