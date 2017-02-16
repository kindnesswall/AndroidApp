package ir.hamed_gh.divaremehrabani.dialogfragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.interfaces.ChooseCategoryCallback;
import ir.hamed_gh.divaremehrabani.interfaces.ChoosePlaceCallback;
import ir.hamed_gh.divaremehrabani.interfaces.HomeFilteringCallback;
import ir.hamed_gh.divaremehrabani.model.Place;
import ir.hamed_gh.divaremehrabani.model.api.Category;

/**
 * Created by 5 on 02/21/2016.
 */
public class HomeFilteringDialogFragment
		extends DialogFragment
		implements ChoosePlaceCallback, ChooseCategoryCallback {

	@Bind(R.id.category_filter_lay)
	RelativeLayout categoryFilterLay;

	@Bind(R.id.location_filter_lay)
	RelativeLayout locationFilterLay;

	@Bind(R.id.apply_filter_tv)
	TextView applyFilterTv;

	@Bind(R.id.cancel_filter_tv)
	TextView cancelFilterTv;

	@Bind(R.id.categoryـfilter_tv)
	TextView mCategoryFilterTv;

	@Bind(R.id.location_filter_tv)
	TextView mLocationFilterTv;

	Category choosenCategory;
	Place choosenPlace;

	@Override
	public void onPlaceSelected(Place place) {
		mLocationFilterTv.setText(
				getText(R.string.place_equal) + " " + place.name);
		choosenPlace = place;
	}

	@Override
	public void onCategorySelected(Category category) {
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

		ButterKnife.bind(this, rootView);

		setListeners();

//        ChoosePlaceAdapter chooseCityAdapter = new ChoosePlaceAdapter(getContext(), level2.getPlaces());
//        recyclerView.setAdapter(chooseCityAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
//				Bundle bundle = new Bundle();
//				bundle.putString(Constants.FROM_ACTIVITY, HomeFilteringDialogFragment.class.getName());

				FragmentManager fm = getActivity().getSupportFragmentManager();
				ChoosePlaceDialogFragment choosePlaceDialogFragment = new ChoosePlaceDialogFragment();
//				choosePlaceDialogFragment.setArguments(bundle);

				choosePlaceDialogFragment.show(fm, ChoosePlaceDialogFragment.class.getName());
				choosePlaceDialogFragment.setTargetFragment(HomeFilteringDialogFragment.this, 0);
//                choosePlaceDialogFragment.onDismiss(HomeFilteringDialogFragment.this);
			}
		});

		applyFilterTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				((HomeFilteringCallback)getTargetFragment())
						.onApplyFiltering(choosenPlace,choosenCategory);
				dismiss();

			}
		});

		cancelFilterTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

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
