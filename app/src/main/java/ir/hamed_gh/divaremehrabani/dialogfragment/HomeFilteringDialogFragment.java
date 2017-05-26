package ir.hamed_gh.divaremehrabani.dialogfragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.helper.ReadJsonFile;
import ir.hamed_gh.divaremehrabani.interfaces.ChooseCategoryCallback;
import ir.hamed_gh.divaremehrabani.interfaces.ChoosePlaceCallback;
import ir.hamed_gh.divaremehrabani.interfaces.HomeFilteringCallback;
import ir.hamed_gh.divaremehrabani.model.Place;
import ir.hamed_gh.divaremehrabani.model.Places;
import ir.hamed_gh.divaremehrabani.model.api.Category;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class HomeFilteringDialogFragment
		extends DialogFragment
		implements ChoosePlaceCallback, ChooseCategoryCallback {

	@Bind(R.id.category_filter_lay)
	RelativeLayout categoryFilterLay;

	@Bind(R.id.city_filter_lay)
	RelativeLayout locationFilterLay;

	@Bind(R.id.region_filter_lay)
	RelativeLayout regionFilterLay;

	@Bind(R.id.apply_filter_tv)
	TextView applyFilterTv;

	@Bind(R.id.region_filter_tv)
	TextView regionFilterTv;

	@Bind(R.id.cancel_filter_tv)
	TextView cancelFilterTv;

	@Bind(R.id.categoryـfilter_tv)
	TextView mCategoryFilterTv;

	@Bind(R.id.city_filter_tv)
	TextView mLocationFilterTv;

	Category choosenCategory;
	Place choosenPlace;
	Place choosenRegion;

	public static HomeFilteringDialogFragment ShowME(
			Category category,
			Place place,
			Place region) {

		Bundle bundle = new Bundle();
		bundle.putParcelable(Constants.CATEGORY_PARCELABLE, category);
		bundle.putParcelable(Constants.PLACE_PARCELABLE, place);
		bundle.putParcelable(Constants.REGION_PARCELABLE, region);

		HomeFilteringDialogFragment fragment = new HomeFilteringDialogFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	private void extractDataFromBundle() {
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			choosenCategory = bundle.getParcelable(Constants.CATEGORY_PARCELABLE);
			onCategorySelected(choosenCategory);

			choosenPlace = bundle.getParcelable(Constants.PLACE_PARCELABLE);
			onCitySelected(choosenPlace);
			choosenRegion = bundle.getParcelable(Constants.REGION_PARCELABLE);
			onRegionSelected(choosenRegion);
		}
	}

	@Override
	public void onCitySelected(Place city) {
		if (city == null) return;
		if (choosenPlace != city) {
			onRegionSelected(null);
		}
		mLocationFilterTv.setText(
				getText(R.string.place_equal) + " " + city.name);
		choosenPlace = city;
		findCityRegion();
	}

	@Override
	public void onRegionSelected(Place region) {
		this.choosenRegion = region;
		if (region == null) {
			regionFilterTv.setText(
					getText(R.string.region_equal));
		} else {
			regionFilterTv.setText(
					getText(R.string.region_equal) + " " + region.name);
		}
	}

	private void findCityRegion() {
		String json = ReadJsonFile.loadJSONFromAsset(getContext());

		Gson gson = new Gson();

		Places allPlaces = gson.fromJson(json, Places.class);

		Places level3 = new Places();
		level3.setPlaces(new ArrayList<Place>());

		for (Place thisPlace : allPlaces.getPlaces()) {
			if (thisPlace.level.equals("place3") && thisPlace.container_id.equals(choosenPlace.id)) {
				level3.addPlace(thisPlace);
			}
		}

		Places level4 = new Places();
		level4.setPlaces(new ArrayList<Place>());

		for (Place thisPlace : allPlaces.getPlaces()) {
			if (thisPlace.level.equals("place4")) {
				for (Place l3 : level3.getPlaces()) {
					if (thisPlace.container_id.equals(l3.id)) {
						level4.addPlace(thisPlace);
					}
				}
			}
		}
		if (level4.getPlaces().size() > 0) {
			regionFilterLay.setVisibility(View.VISIBLE);
		} else {
			regionFilterLay.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onCategorySelected(Category category) {
		if (category == null) return;

		mCategoryFilterTv.setText(
				getText(R.string.category_equal) +
						" " +
						category.title
		);
		choosenCategory = category;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.dialogfragment_filtering, container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		ButterKnife.bind(this, rootView);

		extractDataFromBundle();

		setListeners();

		return rootView;
	}

	private void setListeners() {

		categoryFilterLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentManager fm = getActivity().getSupportFragmentManager();
				ChooseCategoryDialogFragment chooseCategoryDF = new ChooseCategoryDialogFragment();
				chooseCategoryDF.setTargetFragment(HomeFilteringDialogFragment.this, 0);
				chooseCategoryDF.show(fm, chooseCategoryDF.getClass().getName());

			}
		});

		locationFilterLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentManager fm = getActivity().getSupportFragmentManager();
				ChoosePlaceDialogFragment choosePlaceDialogFragment = new ChoosePlaceDialogFragment();

				choosePlaceDialogFragment.show(fm, ChoosePlaceDialogFragment.class.getName());
				choosePlaceDialogFragment.setTargetFragment(HomeFilteringDialogFragment.this, 0);

			}
		});

		regionFilterLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentManager fm = getActivity().getSupportFragmentManager();
				ChoosePlaceDialogFragment choosePlaceDialogFragment =
						ChoosePlaceDialogFragment.newInstance(choosenPlace.id);

				choosePlaceDialogFragment.show(fm, ChoosePlaceDialogFragment.class.getName());
				choosePlaceDialogFragment.setTargetFragment(HomeFilteringDialogFragment.this, 0);

			}
		});

		applyFilterTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				((HomeFilteringCallback) getTargetFragment())
						.onApplyFiltering(choosenPlace, choosenRegion, choosenCategory);
				dismiss();

			}
		});

		cancelFilterTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((HomeFilteringCallback) getTargetFragment())
						.onApplyFiltering(null, null, null);
				dismiss();
			}
		});
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
