package ir.kindnesswall.fragment.chat;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import ir.kindnesswall.activity.ChatActivity;
import ir.kindnesswall.adapter.chat.ChatListAdapter;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.helper.EndlessRecyclerViewScrollListener;
import ir.kindnesswall.model.Chat;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ChatListFragment extends BaseFragment {

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.message_textview)
	TextView mMessageTv;

	@Bind(R.id.swipeRefreshLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;

	View rootView;
	private ArrayList<Chat> chats = new ArrayList<>();
	private ChatListAdapter adapter;
	private LinearLayoutManager linearLayoutManager;
	private int startIndex = 0;

	@Override
	public void onResume() {
		super.onResume();
		((ChatActivity) context).showFab();
	}

	@Override
	protected void init() {
		super.init();

		adapter = new ChatListAdapter(context, chats);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Toasti.showS("need more data, page: " + page + ", totalItemsCount: " + totalItemsCount);
//				if (page > 1)
//					getSentRequestList();
			}
		});

//		getSentRequestList();

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Refresh gifts
				refreshItems();
			}
		});
	}

	private void getSentRequestList() {
//		apiRequest.getSentRequestList(
//				new StartLastIndex(
//						startIndex + "",
//						startIndex + Constants.LIMIT + ""
//				)
//		);

		startIndex += Constants.LIMIT;
	}

	void refreshItems() {
		// Load gifts
		// ...

//		chats.clear();
//
//		startIndex = 0;

//		getSentRequestList();

		// Load complete
		onItemsLoadComplete();
	}
	void onItemsLoadComplete() {
		// Update the adapter and notify data set changed
		// ...

		// Stop refresh animation
		mSwipeRefreshLayout.setRefreshing(false);
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

		mRecyclerView.setVisibility(View.VISIBLE);
		mMessageTv.setVisibility(View.INVISIBLE);
		progressView.setVisibility(View.INVISIBLE);

		return rootView;
	}

	@Override
	public void onResponse(Call call, Response response) {

		progressView.setVisibility(View.INVISIBLE);

//		ArrayList<Chat> chats = (ArrayList<Chat>) response.body();
//		this.chats.addAll(chats);
//		adapter.notifyDataSetChanged();
//
//		if (this.chats.size() > 0) {
//			mRecyclerView.setVisibility(View.VISIBLE);
//			mMessageTv.setVisibility(View.INVISIBLE);
//		} else {
//			mRecyclerView.setVisibility(View.INVISIBLE);
//			mMessageTv.setVisibility(View.VISIBLE);
//			mMessageTv.setText(
//					"شما هیچ درخواستی ارسال نکرده‌اید."
//			);
//		}

	}
}