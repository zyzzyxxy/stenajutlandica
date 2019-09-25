package edu.jutlandica.client.vtAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information of a whole VT jorney in form of trips
 */
public class VT_Journey implements Journey_Interface {

    List<VT_Trip> tripList;

    public VT_Journey() {
        tripList = new ArrayList<>();
    }


    @Override
    public void addTrip(Object o) {
        tripList.add((VT_Trip) o);
    }

    public List<VT_Trip> getTripList() {
        return tripList;
    }

}
