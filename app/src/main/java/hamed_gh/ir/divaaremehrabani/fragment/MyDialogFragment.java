package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.adapter.ChooseCityAdapter;
import hamed_gh.ir.divaaremehrabani.customviews.edit_text.EditTextIranSans;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyDialogFragment extends DialogFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_dialog, container, false);

		ButterKnife.bind(this, rootView);

		RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.choose_city_recyclerview);
		EditTextIranSans editTextIranSans = (EditTextIranSans) rootView.findViewById(R.id.choose_city_et);
//		int color = AppController.getAppContext().getResources().getColor(R.color.colorAccent);
//		editTextIranSans.getCompoundDrawables()[2].setColorFilter( color, PorterDuff.Mode.SRC_ATOP);

		ChooseCityAdapter chooseCityAdapter = new ChooseCityAdapter(getContext());
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
