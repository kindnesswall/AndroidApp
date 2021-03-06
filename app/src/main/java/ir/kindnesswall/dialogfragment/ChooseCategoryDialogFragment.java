package ir.kindnesswall.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.ChooseCategoriesDialogAdapter;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.interfaces.ChooseCategoryCallback;
import ir.kindnesswall.model.api.Category;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ChooseCategoryDialogFragment extends DialogFragment implements ApiRequest.Listener {

	@Bind(R.id.choose_place_recyclerview)
	RecyclerView recyclerView;

	@Bind(R.id.choose_place_progressBar)
	ProgressView mProgressView;

	ArrayList<Category> categories = new ArrayList<>();
	ApiRequest apiRequest;
	private ChooseCategoriesDialogAdapter chooseCategoriesDialogAdapter;
	private ChooseCategoryCallback mHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.dialogfragment_choose_category, container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		ButterKnife.bind(this, rootView);

		apiRequest = new ApiRequest(getActivity(), this);
		apiRequest.getCategories();

		chooseCategoriesDialogAdapter =
				new ChooseCategoriesDialogAdapter(getActivity(), this, categories);
		recyclerView.setAdapter(chooseCategoriesDialogAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		return rootView;
	}

	public void onCategorySelected(Category category) {
		mHost.onCategorySelected(category);
		dismiss();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (getTargetFragment() == null) {
			mHost = (ChooseCategoryCallback) context;
		} else {
			mHost =
					(ChooseCategoryCallback) getTargetFragment();
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

	@Override
	public void onResponse(Call call, Response response) {
		ArrayList<Category> categories = (ArrayList<Category>) response.body();
		this.categories.addAll(categories);
		chooseCategoriesDialogAdapter.notifyDataSetChanged();

		mProgressView.setVisibility(View.INVISIBLE);
		recyclerView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onFailure(Call call, Throwable t) {
		mProgressView.setVisibility(View.INVISIBLE);
		recyclerView.setVisibility(View.INVISIBLE);
	}
}
