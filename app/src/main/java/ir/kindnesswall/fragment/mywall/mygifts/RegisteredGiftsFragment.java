package ir.kindnesswall.fragment.mywall.mygifts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.GiftListAdapter;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.helper.EndlessRecyclerViewScrollListener;
import ir.kindnesswall.model.api.Gift;
import ir.kindnesswall.model.api.StartLastIndex;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RegisteredGiftsFragment extends BaseFragment {

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.message_textview)
	TextView mMessageTv;
	View rootView;
	private ArrayList<Gift> gifts = new ArrayList<>();
	private GiftListAdapter adapter;
	private LinearLayoutManager linearLayoutManager;
	private int startIndex = 0;
	private String userId;

	public static RegisteredGiftsFragment newInstance(String userId) {
		RegisteredGiftsFragment registeredGiftsFragment = new RegisteredGiftsFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.USER_ID, userId);
		registeredGiftsFragment.setArguments(bundle);

		return registeredGiftsFragment;
	}

	@Override
	protected void init() {
		super.init();

		adapter = new GiftListAdapter(context, gifts);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);

		if (getArguments() != null) {
			userId = getArguments().getString(Constants.USER_ID);
		}
		if (userId == null)
			userId = AppController.getStoredString(Constants.USER_ID);

		mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Toasti.showS("need more data, page: " + page + ", totalItemsCount: " + totalItemsCount);
				if (page > 1)
					getRegisteredGifts();
			}
		});
		getRegisteredGifts();

	}

	private void getRegisteredGifts() {
		apiRequest.getRegisteredGifts(
				userId,
				new StartLastIndex(
						startIndex + "",
						startIndex + Constants.LIMIT + ""
				)
		);

		startIndex += Constants.LIMIT;
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
		rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);

		ButterKnife.bind(this, rootView);
		init();

		return rootView;
	}

	@Override
	public void onResponse(Call call, Response response) {

		mRecyclerView.setVisibility(View.VISIBLE);
		progressView.setVisibility(View.INVISIBLE);

		ArrayList<Gift> gifts = (ArrayList<Gift>) response.body();
		this.gifts.addAll(gifts);
		adapter.notifyDataSetChanged();

		if (gifts.size() > 0) {
			mRecyclerView.setVisibility(View.VISIBLE);
			mMessageTv.setVisibility(View.INVISIBLE);
		} else {
			mRecyclerView.setVisibility(View.INVISIBLE);
			mMessageTv.setVisibility(View.VISIBLE);
			mMessageTv.setText(
					"شما هیچ هدیه‌ی ثبت نکرده اید."
			);
		}

	}

}
