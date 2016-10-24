package hamed_gh.ir.divaaremehrabani.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

	@Bind(R.id.search_backspace_btn)
	ImageView mSearchBackspaceBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);

		ButterKnife.bind(this, rootView);
		init();

		Drawable myIcon = getResources().getDrawable(R.mipmap.ic_backspace_black_24dp);
		myIcon.setColorFilter(getResources().getColor(R.color.dark_white), PorterDuff.Mode.SRC_ATOP);
		mSearchBackspaceBtn.setImageDrawable(myIcon);

		return rootView;
	}
}
