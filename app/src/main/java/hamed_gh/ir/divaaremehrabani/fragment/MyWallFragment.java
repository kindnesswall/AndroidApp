package hamed_gh.ir.divaaremehrabani.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.app.AppController;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyWallFragment extends BaseFragment {

	@Bind(R.id.my_gift_txt)
	TextView mMessageTextView;

	@Bind(R.id.my_gift_iv)
	ImageView mMyGiftIv;

	@Bind(R.id.bookmark_iv)
	ImageView mBookmarIv;

	@Bind(R.id.location_iv)
	ImageView mLocationIv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_my_wall, container, false);

		ButterKnife.bind(this, rootView);
		init();

		int color = AppController.getAppContext().getResources().getColor(R.color.light_black);
		mMyGiftIv.setColorFilter( color, PorterDuff.Mode.SRC_ATOP);
		mBookmarIv.setColorFilter( color, PorterDuff.Mode.SRC_ATOP);
		mLocationIv.setColorFilter( color, PorterDuff.Mode.SRC_ATOP);

		return rootView;
	}
}
