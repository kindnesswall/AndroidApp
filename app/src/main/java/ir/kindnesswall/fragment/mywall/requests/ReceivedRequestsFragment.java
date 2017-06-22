package ir.kindnesswall.fragment.mywall.requests;

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
import ir.hamed_gh.kindnesswall.R;
import ir.kindnesswall.adapter.RequestToMyGiftsAdapter;
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
public class ReceivedRequestsFragment extends BaseFragment {

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.message_textview)
	TextView mMessageTv;
	View rootView;
	private ArrayList<Gift> gifts = new ArrayList<>();
	private RequestToMyGiftsAdapter adapter;
	private LinearLayoutManager linearLayoutManager;
	private int startIndex = 0;

	@Override
	protected void init() {
		super.init();

		adapter = new RequestToMyGiftsAdapter(context, gifts);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Toasti.showS("need more data, page: " + page + ", totalItemsCount: " + totalItemsCount);
				getRequestsToMyGifts();
			}
		});
		getRequestsToMyGifts();
	}

	private void getRequestsToMyGifts() {
		apiRequest.getRequestsToMyGifts(
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
					"شما هیچ درخواستی دریافت نکرده‌اید."
			);
		}

	}

}
