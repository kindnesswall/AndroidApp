package ir.hamed_gh.divaaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import ir.hamed_gh.divaaremehrabani.adapter.ChooseCityAdapter;
import ir.hamed_gh.divaaremehrabani.customviews.edit_text.EditTextIranSans;
import ir.hamed_gh.divaaremehrabani.helper.ReadJsonFile;
import ir.hamed_gh.divaaremehrabani.model.Place;
import ir.hamed_gh.divaaremehrabani.model.Places;

/**
 * Created by 5 on 02/21/2016.
 */
public class ChooseCategoryDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.dialogfragment_choose_category, container, false);

        ButterKnife.bind(this, rootView);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.choose_city_recyclerview);
        EditTextIranSans editTextIranSans = (EditTextIranSans) rootView.findViewById(R.id.choose_city_et);
//		int color = AppController.getAppContext().getResources().getColor(R.color.colorAccent);
//		editTextIranSans.getCompoundDrawables()[2].setColorFilter( color, PorterDuff.Mode.SRC_ATOP);
        String json = ReadJsonFile.loadJSONFromAsset(getContext());

        Gson gson = new Gson();

        Places allPlaces = gson.fromJson(json, Places.class);

        Places level2 = new Places();
        level2.setPlaces(new ArrayList<Place>());

        Places level3 = new Places();
        level3.setPlaces(new ArrayList<Place>());

        Places level4 = new Places();
        level4.setPlaces(new ArrayList<Place>());

        Log.d("Gson Test", ">> " + allPlaces.getPlaces().get(1).name);
        Log.d("Gson Test", ">> " + allPlaces.getPlaces().get(1).level);

        for (Place thisPlace : allPlaces.getPlaces()) {
            if (thisPlace.level.equals("place3") && thisPlace.container_id == 5) {
                level2.addPlace(thisPlace);
            }
        }

        ChooseCityAdapter chooseCityAdapter = new ChooseCityAdapter(getContext(), level2.getPlaces());
        recyclerView.setAdapter(chooseCityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
}
