package edu.jutlandica.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.jutlandica.client.dataclasses.Journey;

@RemoteServiceRelativePath("message")
public interface MessageService extends RemoteService {
   //Message getMessage(String input);
   List<Journey> getJourneys(String to, String from, String time, String date);   
}