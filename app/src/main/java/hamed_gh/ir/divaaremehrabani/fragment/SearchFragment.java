package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;

/**
 * Created by 5 on 02/21/2016.
 */
public class SearchFragment extends BaseFragment {

	@Bind(R.id.message_textview)
	TextView mMessageTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);

		ButterKnife.bind(this, rootView);
		init();

		return rootView;
	}
}
