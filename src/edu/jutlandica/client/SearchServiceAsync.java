package edu.jutlandica.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.jutlandica.client.dataclasses.Journey;

public interface SearchServiceAsync {
   //void getMessage(String input, AsyncCallback<Message> callback);
   void getJourneys(String to, String from, Date date, AsyncCallback<List<Journey>> callback); 
}