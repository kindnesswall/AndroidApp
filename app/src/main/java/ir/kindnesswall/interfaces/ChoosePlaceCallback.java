package ir.kindnesswall.interfaces;

import ir.kindnesswall.model.Place;

/**
 * Created by HamedGh on 3/8/2016.
 */
public interface ChoosePlaceCallback {

	void onCitySelected(Place city);

	void onRegionSelected(Place region);

}
