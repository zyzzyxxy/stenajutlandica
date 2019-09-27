package edu.jutlandica.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.jutlandica.client.dataclasses.Journey;

public interface MessageServiceAsync {
   //void getMessage(String input, AsyncCallback<Message> callback);
   void getJourneys(String to, String from, String time, String date, AsyncCallback<List<Journey>> callback); 
}