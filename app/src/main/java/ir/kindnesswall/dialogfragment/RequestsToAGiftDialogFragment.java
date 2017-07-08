package ir.kindnesswall.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.activity.GiftDetailActivity;
import ir.kindnesswall.adapter.RequestToAGiftAdapter;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.helper.EndlessRecyclerViewScrollListener;
import ir.kindnesswall.model.api.RequestModel;
import ir.kindnesswall.model.api.input.RecievedRequestListInput;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestsToAGiftDialogFragment extends DialogFragment implements ApiRequest.Listener {

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.message_textview)
	TextView mMessageTv;

//	@Bind(R.id.gift_name_tv)
//	TextViewIranSansRegular mGiftNameTv;

	@Bind(R.id.info_lay)
	RelativeLayout mInfoLay;

	@Bind(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;

	View rootView;
	private ArrayList<RequestModel> requestModels = new ArrayList<>();
	private RequestToAGiftAdapter adapter;
	private LinearLayoutManager linearLayoutManager;
	private int startIndex = 0;
	private String giftId;

	protected Context context;
	protected AppCompatActivity mainActivity;
	protected ApiRequest apiRequest;

	public static RequestsToAGiftDialogFragment newInstance(
			String giftId) {

		Bundle bundle = new Bundle();
		bundle.putString(Constants.GIFT_ID, giftId);

		RequestsToAGiftDialogFragment fragment = new RequestsToAGiftDialogFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	protected void init() {
		context = getActivity();
		mainActivity = (AppCompatActivity) getActivity();

		apiRequest = new ApiRequest(context, this);


		Bundle bundle = this.getArguments();
		if (bundle != null) {
			giftId = bundle.getString(Constants.GIFT_ID);
		}

//		mInfoLay.setVisibility(View.GONE);

		adapter = new RequestToAGiftAdapter(context, requestModels);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);

		mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Toasti.showS("need more data, page: " + page + ", totalItemsCount: " + totalItemsCount);
				if (page > 1)
					getRecievedRequestList();
			}
		});

		getRecievedRequestList();

		mInfoLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getActivity().startActivity(
						GiftDetailActivity.createIntent(giftId)
				);
			}
		});

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Refresh gifts
				refreshItems();
			}
		});
	}

	void refreshItems() {
		// Load gifts
		// ...

		requestModels.clear();

		startIndex = 0;

		getRecievedRequestList();

		// Load complete
		onItemsLoadComplete();
	}
	void onItemsLoadComplete() {
		// Update the adapter and notify data set changed
		// ...

		// Stop refresh animation
		mSwipeRefreshLayout.setRefreshing(false);
	}

	private void getRecievedRequestList() {

		apiRequest.getRecievedRequestList(
				new RecievedRequestListInput(
						giftId,
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
		rootView = inflater.inflate(R.layout.dialogfragment_requests_toagift, container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		ButterKnife.bind(this, rootView);
		requestModels.clear();
		init();

		return rootView;
	}

	@Override
	public void onResponse(Call call, Response response) {

		progressView.setVisibility(View.INVISIBLE);

		ArrayList<RequestModel> requestModels = (ArrayList<RequestModel>) response.body();
		this.requestModels.addAll(requestModels);
		adapter.notifyDataSetChanged();

		if (this.requestModels.size() > 0) {
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

	@Override
	public void onFailure(Call call, Throwable t) {

	}

}
