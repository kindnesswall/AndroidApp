package ir.hamed_gh.divaremehrabani.fragment.mywall.requests;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.SentRequestAdapter;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;
import ir.hamed_gh.divaremehrabani.model.api.RequestModel;
import ir.hamed_gh.divaremehrabani.model.api.StartLastIndex;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class SentRequestsFragment extends BaseFragment {

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	private ArrayList<RequestModel> requestModels = new ArrayList<>();
	private SentRequestAdapter adapter;
	private LinearLayoutManager linearLayoutManager;

	private int startIndex = 0;
	@Override
	protected void init() {
		super.init();

		adapter = new SentRequestAdapter(context, requestModels);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);

		apiRequest.getSentRequestList(
				new StartLastIndex(
						startIndex + "",
						startIndex + Constants.LIMIT + ""
				)
		);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_registered_gifts, container, false);

		ButterKnife.bind(this, rootView);
		init();

		return rootView;
	}

	@Override
	public void onResponse(Call call, Response response) {

		mRecyclerView.setVisibility(View.VISIBLE);
		progressView.setVisibility(View.INVISIBLE);

		ArrayList<RequestModel> requestModels = (ArrayList<RequestModel>) response.body();
		this.requestModels.addAll(requestModels);
		adapter.notifyDataSetChanged();

	}
}