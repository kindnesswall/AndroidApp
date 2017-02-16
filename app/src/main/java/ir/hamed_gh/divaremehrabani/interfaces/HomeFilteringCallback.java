package ir.hamed_gh.divaremehrabani.interfaces;

import ir.hamed_gh.divaremehrabani.model.Place;
import ir.hamed_gh.divaremehrabani.model.api.Category;

/**
 * Created by Hamed on 2/15/17.
 */

public interface HomeFilteringCallback {

	public void onApplyFiltering(Place place, Category category);

}
