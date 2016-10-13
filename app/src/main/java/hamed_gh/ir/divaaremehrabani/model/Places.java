package hamed_gh.ir.divaaremehrabani.model;

import java.util.ArrayList;

/**
 * Created by Hamed on 10/12/16.
 */

public class Places {

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void addPlace(Place place){
        places.add(place);
    }
    ArrayList<Place> places;

}
