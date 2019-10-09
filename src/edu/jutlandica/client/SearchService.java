package edu.jutlandica.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.jutlandica.client.dataclasses.Journey;

@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService {
   //Message getMessage(String input);
   List<Journey> getJourneys(String to, String from, Date date);   
}