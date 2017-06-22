package ir.kindnesswall.interfaces;

import ir.kindnesswall.model.Place;
import ir.kindnesswall.model.api.Category;

/**
 * Created by HamedGh on 3/8/2016.
 */
public interface HomeFilteringCallback {

	void onApplyFiltering(Place place, Place region, Category category);

}
