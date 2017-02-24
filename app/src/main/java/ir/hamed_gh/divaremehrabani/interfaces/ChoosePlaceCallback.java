package ir.hamed_gh.divaremehrabani.interfaces;

import ir.hamed_gh.divaremehrabani.model.Place;

/**
 * Created by HamedGh on 3/8/2016.
 */
public interface ChoosePlaceCallback {

    void onCitySelected(Place city);
    void onRegionSelected(Place region);

}
