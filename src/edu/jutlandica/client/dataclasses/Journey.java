package edu.jutlandica.client.dataclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information of a whole VT jorney in form of trips
 */
public class Journey implements Journey_Interface {

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
    	StringBuilder sb = new StringBuilder();
    	for(Trip trip : tripList) {
    		sb.append(trip.toString()+ "\n");
    	}
    	return sb.toString();
    }

}
