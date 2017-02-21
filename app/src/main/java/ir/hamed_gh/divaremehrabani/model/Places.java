package ir.hamed_gh.divaremehrabani.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class Places {

    @SerializedName("places")
    ArrayList<Place> places;

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void addPlace(Place place) {
        places.add(place);
    }

}
