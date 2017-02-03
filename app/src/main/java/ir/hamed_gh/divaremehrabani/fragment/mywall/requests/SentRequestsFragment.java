package ir.hamed_gh.divaremehrabani.fragment.mywall.requests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;

/**
 * Created by 5 on 02/21/2016.
 */
public class SentRequestsFragment extends BaseFragment {

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_registered_gifts, container, false);

		ButterKnife.bind(this, rootView);
		init();

		return rootView;
	}

}
