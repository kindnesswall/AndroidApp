package ir.hamed_gh.divaremehrabani.fragment.mywall;

import android.content.Intent;
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
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.LoginActivity;
import ir.hamed_gh.divaremehrabani.adapter.GiftListAdapter;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.StartLastIndex;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class BookmarkFragment extends BaseFragment {

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.message_textview)
	TextView mMessageTv;

	@Bind(R.id.login_btn)
	RelativeLayout mLoginBtn;

	@Bind(R.id.bookmark_top_lay)
	RelativeLayout mBookmarkTopLay;

	@Bind(R.id.bookmark_main_lay)
	RelativeLayout mBookmarkMainLay;

	private ArrayList<Gift> gifts = new ArrayList<>();
	private GiftListAdapter adapter;
	private LinearLayoutManager linearLayoutManager;

	private int startIndex = 0;
	private View rootView;

	@Override
	protected void init() {
		super.init();

		adapter = new GiftListAdapter(context, gifts);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);

	}

	@Override
	public void onResume() {
		super.onResume();



		if (AppController.getStoredString(Constants.Authorization) != null) {

			mBookmarkTopLay.setVisibility(View.GONE);
			mBookmarkMainLay.setVisibility(View.VISIBLE);

			if (gifts.size()<=0){
				apiRequest.getBookmarkList(
						new StartLastIndex(
								startIndex + "",
								startIndex + Constants.LIMIT + ""
						)
				);
			}

		} else {
			mBookmarkTopLay.setVisibility(View.VISIBLE);
			mBookmarkMainLay.setVisibility(View.INVISIBLE);
		}
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
		rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);

		ButterKnife.bind(this, rootView);
		init();

		gifts.clear();
		if (AppController.getStoredString(Constants.Authorization) != null) {
//			apiRequest.getBookmarkList(
//					new StartLastIndex(
//							startIndex + "",
//							startIndex + Constants.LIMIT + ""
//					)
//			);
		}else {

			mLoginBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), LoginActivity.class));
				}
			});
		}

		return rootView;
	}

	@Override
	public void onResponse(Call call, Response response) {

		mRecyclerView.setVisibility(View.VISIBLE);
		progressView.setVisibility(View.INVISIBLE);

		ArrayList<Gift> gifts = (ArrayList<Gift>) response.body();
//        for (int i = 0; i < gifts.size(); i++) {
		this.gifts.addAll(gifts);
//        }
		adapter.notifyDataSetChanged();

		if (gifts.size() > 0) {
			mRecyclerView.setVisibility(View.VISIBLE);
			mMessageTv.setVisibility(View.INVISIBLE);
		} else {
			mRecyclerView.setVisibility(View.INVISIBLE);
			mMessageTv.setVisibility(View.VISIBLE);
			mMessageTv.setText(
					"شما هیچ هدیه‌ی را به علاقه‌مندیهای خود اضافه نکرده‌اید."
			);
		}

	}

}
