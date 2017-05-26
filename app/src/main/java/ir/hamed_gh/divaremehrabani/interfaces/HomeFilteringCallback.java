package ir.hamed_gh.divaremehrabani.interfaces;

import ir.hamed_gh.divaremehrabani.model.Place;
import ir.hamed_gh.divaremehrabani.model.api.Category;

/**
 * Created by HamedGh on 3/8/2016.
 */
public interface HomeFilteringCallback {

	void onApplyFiltering(Place place, Place region, Category category);

}
