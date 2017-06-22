package ir.kindnesswall.fragment.mywall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import butterknife.ButterKnife;
import ir.hamed_gh.kindnesswall.R;
import ir.kindnesswall.activity.BottomBarActivity;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.model.api.output.StatisticsOutput;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class StatisticFragment extends BaseFragment {

	private View rootView;

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

		((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("آمار و ارقام");

		apiRequest.getStatistics();
		return rootView;
	}

	@Override
	public void onResponse(Call call, Response response) {
		super.onResponse(call, response);

		StatisticsOutput statisticsOutput = (StatisticsOutput) response.body();


		int i = 0;
		for (Map.Entry<String, String> entry : statisticsOutput.statistics.entrySet()) {

			i++;
			if (i > 10) {
				break;
			}
			String key = entry.getKey();
			String value = entry.getValue();

			TextView valueTxt = (TextView) rootView.findViewById(
					AppController.getAppContext().getResources().getIdentifier("value_tv_" + i, "id", getActivity().getPackageName()));

			valueTxt.setText(value);
			valueTxt.setVisibility(View.VISIBLE);

			TextView keyTxt = (TextView) rootView.findViewById(
					AppController
							.getAppContext()
							.getResources()
							.getIdentifier("key_tv_" + i, "id", getActivity().getPackageName()));

			keyTxt.setText(key);
			keyTxt.setVisibility(View.VISIBLE);

		}
	}
}
