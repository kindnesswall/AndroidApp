package ir.hamed_gh.divaremehrabani.fragment.mywall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;

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

		return rootView;
	}
}
