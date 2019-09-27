package edu.jutlandica.client.dataclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information of a whole VT jorney in form of trips
 */
public class Journey implements Journey_Interface, Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<Trip> tripList;

    public Journey() {
        tripList = new ArrayList<>();
    }


    @Override
    public void addTrip(Object o) {
        tripList.add((Trip) o);
    }

    public List<Trip> getTripList() {
        return tripList;
    }
    
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	
    	if (tripList.isEmpty()) s.append("NO TRIPS");
    	
    	for (Trip t : tripList) {
    		s.append(t.toString());
    		s.append("\n");
    	}
    	
		return s.toString();    	
    }

}
