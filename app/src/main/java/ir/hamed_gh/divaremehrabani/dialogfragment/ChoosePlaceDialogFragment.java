package ir.hamed_gh.divaremehrabani.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.ChoosePlaceAdapter;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.customviews.edit_text.EditTextIranSans;
import ir.hamed_gh.divaremehrabani.helper.ReadJsonFile;
import ir.hamed_gh.divaremehrabani.interfaces.ChoosePlaceCallback;
import ir.hamed_gh.divaremehrabani.model.Place;
import ir.hamed_gh.divaremehrabani.model.Places;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ChoosePlaceDialogFragment extends DialogFragment {

//    private String fromActivity;

    @Bind(R.id.choose_place_recyclerview)
    RecyclerView recyclerView;

    @Bind(R.id.choose_place_et)
    EditTextIranSans editTextIranSans;

    private Places level2;
    private Places level2Original;

    private ChoosePlaceAdapter choosePlaceAdapter;
    private ChoosePlaceCallback mHost;
    private String locationId;

    private Places level4Original;
    private Places level4;

    public static ChoosePlaceDialogFragment newInstance(String locationId) {
        ChoosePlaceDialogFragment f = new ChoosePlaceDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString(Constants.CITY_ID, locationId);
        f.setArguments(args);

        return f;
    }

    private void readLevel2FromJson() {
        String json = ReadJsonFile.loadJSONFromAsset(getContext());

        Gson gson = new Gson();

        Places allPlaces = gson.fromJson(json, Places.class);

        level2Original = new Places();
        level2Original.setPlaces(new ArrayList<Place>());
        level2 = new Places();
        level2.setPlaces(new ArrayList<Place>());

        Places level3 = new Places();
        level3.setPlaces(new ArrayList<Place>());

        Places level4 = new Places();
        level4.setPlaces(new ArrayList<Place>());

        Log.d("Gson Test", ">> " + allPlaces.getPlaces().get(1).name);
        Log.d("Gson Test", ">> " + allPlaces.getPlaces().get(1).level);

        for (Place thisPlace : allPlaces.getPlaces()) {
            if (thisPlace.level.equals("place2")) {
                level2.addPlace(thisPlace);
                level2Original.addPlace(thisPlace);
            }
        }
    }

    private void readLevel4FromJson() {
        String json = ReadJsonFile.loadJSONFromAsset(getContext());

        Gson gson = new Gson();

        Places allPlaces = gson.fromJson(json, Places.class);

        Places level3 = new Places();
        level3.setPlaces(new ArrayList<Place>());

        for (Place thisPlace : allPlaces.getPlaces()) {
            if (thisPlace.level.equals("place3")
                    && thisPlace.container_id.equals(locationId)) {
                level3.addPlace(thisPlace);
            }
        }

        level4Original = new Places();
        level4Original.setPlaces(new ArrayList<Place>());

        level4 = new Places();
        level4.setPlaces(new ArrayList<Place>());

        for (Place thisPlace : allPlaces.getPlaces()) {
            if (thisPlace.level.equals("place4")) {
                for (Place l3 : level3.getPlaces()) {
                    if (thisPlace.container_id.equals(l3.id)){
                        level4.addPlace(thisPlace);
                        level4Original.addPlace(thisPlace);
                    }
                }
            }
        }
    }

    private void init() {
//		fromActivity = getArguments().getString(Constants.FROM_ACTIVITY);
        Bundle bundle = getArguments();
        if (bundle!=null && getArguments().getString(Constants.CITY_ID)!=null) {
            locationId = getArguments().getString(Constants.CITY_ID);
            readLevel4FromJson();
            choosePlaceAdapter = new ChoosePlaceAdapter(this, getContext(), level4.getPlaces());
        }else {
            readLevel2FromJson();
            choosePlaceAdapter = new ChoosePlaceAdapter(this, getContext(), level2.getPlaces());
        }

        recyclerView.setAdapter(choosePlaceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setListeners();
    }

    public void onPlaceSelected(Place place) {
        if (level4Original!=null && level4Original.getPlaces().size()>0) {
            mHost.onRegionSelected(place);
        }else {
            mHost.onCitySelected(place);
        }
        dismiss();

    }

    private void setListeners() {
        editTextIranSans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (level4Original.getPlaces().size()>0) {
                    level4.getPlaces().clear();
                    for (Place p : level4Original.getPlaces()) {

                        if (p.name.startsWith(charSequence.toString())) {
                            level4.addPlace(p);
                        }
                    }
                }else {
                    level2.getPlaces().clear();
                    for (Place p : level2Original.getPlaces()) {

                        if (p.name.startsWith(charSequence.toString())) {
                            level2.addPlace(p);
                        }
                    }
                }
                choosePlaceAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.dialogfragment_choose_place, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, rootView);

//         = (RecyclerView) rootView.findViewById(R.id.choose_city_recyclerview);
//        EditTextIranSans editTextIranSans = (EditTextIranSans) rootView.findViewById(R.id.choose_city_et);
//		int color = AppController.getAppContext().getResources().getColor(R.color.colorAccent);
//		editTextIranSans.getCompoundDrawables()[2].setColorFilter( color, PorterDuff.Mode.SRC_ATOP);

        init();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getTargetFragment() == null) {
            mHost = (ChoosePlaceCallback) context;
        } else {
            mHost =
                    (ChoosePlaceCallback) getTargetFragment();
        }
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
