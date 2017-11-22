package ir.kindnesswall.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.HomeCategoriesAdapter;
import ir.kindnesswall.customviews.textviews.TextViewIranSansRegular;
import ir.kindnesswall.model.api.Category;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Hamed on 11/21/17.
 */

public class HomeCategoryFragment extends BaseFragment {

	@Bind(R.id.message_textview)
	TextViewIranSansRegular messageTextview;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.fragment_progressBar)
	ProgressView mProgressView;

	@Bind(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;

	private View rootView;
	ArrayList<Category> categories = new ArrayList<>();

	HomeCategoriesAdapter homeCategoriesAdapter;
	private LinearLayoutManager linearLayoutManager;

	public static HomeCategoryFragment newInstance() {
		HomeCategoryFragment fragment = new HomeCategoryFragment();
		Bundle args = new Bundle();
//		args.putString(Constants.PAGETYPE, pageType);
//		args.putParcelable(Constants.CATEGORY_PARCELABLE, category);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected void init() {
		super.init();

		apiRequest.getCategories();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (rootView != null) {
			if (rootView.getParent() != null)
				((ViewGroup) rootView.getParent()).removeView(rootView);
			return rootView;
		}
		rootView = inflater.inflate(R.layout.fragment_information, container, false);
		ButterKnife.bind(this, rootView);

		init();
		setListeners();

		return rootView;
	}

	private void setListeners() {


		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Refresh gifts
				apiRequest.getCategories();
			}
		});
	}


	@Override
	public void onResponse(Call call, Response response) {

		mRecyclerView.setVisibility(View.VISIBLE);
		mProgressView.setVisibility(View.INVISIBLE);
		messageTextview.setVisibility(View.INVISIBLE);
		mSwipeRefreshLayout.setRefreshing(false);

		ArrayList<Category> categories = (ArrayList<Category>) response.body();
		this.categories.clear();
		this.categories.addAll(categories);

		homeCategoriesAdapter = new HomeCategoriesAdapter(context, this.categories);
		mRecyclerView.setAdapter(homeCategoriesAdapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);

//		homeCategoriesAdapter.notifyDataSetChanged();

	}

}
