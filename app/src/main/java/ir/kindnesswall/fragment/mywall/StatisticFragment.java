package ir.kindnesswall.fragment.mywall;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.StatisticAdapter;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.model.api.output.StatisticsOutput;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class StatisticFragment extends BaseFragment {

	private View rootView;

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Bind(R.id.recycler_view)
	RecyclerView recyclerView;

	private StatisticAdapter adapter;
	private LinearLayoutManager linearLayoutManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (rootView != null) {
			if (rootView.getParent() != null)
				((ViewGroup) rootView.getParent()).removeView(rootView);
			return rootView;
		}
		rootView = inflater.inflate(R.layout.fragment_statistic, container, false);

		ButterKnife.bind(this, rootView);
		init();

//		((StatisticActivity) getActivity()).mToolbarTitleTextView.setText("آمار و ارقام");



		apiRequest.getStatistics();
		return rootView;
	}

	@Override
	public void onResponse(Call call, Response response) {
		super.onResponse(call, response);

		progressView.setVisibility(View.INVISIBLE);

		StatisticsOutput statisticsOutput = (StatisticsOutput) response.body();


		adapter = new StatisticAdapter(context, statisticsOutput.statistics);
		recyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		recyclerView.setLayoutManager(linearLayoutManager);

	}
}
