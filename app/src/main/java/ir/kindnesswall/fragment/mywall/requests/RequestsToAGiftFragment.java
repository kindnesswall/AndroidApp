package ir.kindnesswall.fragment.mywall.requests;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ir.kindnesswall.customviews.textviews.TextViewIranSansRegular;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.helper.EndlessRecyclerViewScrollListener;
import ir.kindnesswall.model.api.RequestModel;
import ir.kindnesswall.model.api.input.RecievedRequestListInput;
import retrofit2.Call;
import retrofit2.Response;

import static ir.kindnesswall.R.id.total_requests_tv;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestsToAGiftFragment extends BaseFragment {

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.message_textview)
	TextView mMessageTv;

	@Bind(total_requests_tv)
	TextViewIranSansRegular mTotalRequestsTv;

	@Bind(R.id.gift_name_tv)
	TextViewIranSansRegular mGiftNameTv;

	@Bind(R.id.info_lay)
	RelativeLayout mInfoLay;
	View rootView;
	private ArrayList<RequestModel> requestModels = new ArrayList<>();
	private RequestToAGiftAdapter adapter;
	private LinearLayoutManager linearLayoutManager;
	private int startIndex = 0;
	private String giftId;
	private String giftName;
	private String requestCounts;

	@Override
	protected void init() {
		super.init();

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			giftId = bundle.getString(Constants.GIFT_ID);
			giftName = bundle.getString(Constants.GIFT_NAME);
			requestCounts = bundle.getString(Constants.GIFT_REQUEST_COUNT);
			mGiftNameTv.setText(giftName);
			mTotalRequestsTv.setText(requestCounts);
		}

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
		rootView = inflater.inflate(R.layout.fragment_requests_toagift, container, false);

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

		if (requestModels.size() > 0) {
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
