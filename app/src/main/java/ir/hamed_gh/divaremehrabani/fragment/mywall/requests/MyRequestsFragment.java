package ir.hamed_gh.divaremehrabani.fragment.mywall.requests;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.activity.LoginActivity;
import ir.hamed_gh.divaremehrabani.adapter.ViewPagerAdapter;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.customviews.CustomTabLayout;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class MyRequestsFragment extends BaseFragment {

	@Bind(R.id.my_gift_login_btn)
	RelativeLayout myGiftLoginBtn;

	@Bind(R.id.my_gift_top_lay)
	RelativeLayout myGiftTopLay;

	@Bind(R.id.my_gift_bottom_lay)
	RelativeLayout myGiftBottomLay;

	@Bind(R.id.main_tabs)
	CustomTabLayout mainTabs;

	@Bind(R.id.main_vp)
	ViewPager mainVp;
	private View rootView;

	@Override
	protected void init() {
		super.init();

		((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("درخواست‌های من");

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
		rootView = inflater.inflate(R.layout.fragment_my_requests, container, false);

		ButterKnife.bind(this, rootView);
		init();

		return rootView;
	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

		SentRequestsFragment sentRequestsFragment = new SentRequestsFragment();
		ReceivedRequestsFragment receivedRequestsFragment = new ReceivedRequestsFragment();

		adapter.addFrag(sentRequestsFragment, "ارسالی");
		adapter.addFrag(receivedRequestsFragment, "دریافتی");

		viewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();

		if (AppController.getStoredString(Constants.Authorization) != null) {
			setupViewPager(mainVp);
			mainTabs.setupWithViewPager(mainVp);
			mainVp.setCurrentItem(1, false);

			myGiftTopLay.setVisibility(View.GONE);
			myGiftBottomLay.setVisibility(View.VISIBLE);
			mainVp.setCurrentItem(1, false);
		} else {
			myGiftTopLay.setVisibility(View.VISIBLE);
			myGiftBottomLay.setVisibility(View.INVISIBLE);

			myGiftLoginBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), LoginActivity.class));
				}
			});
		}
	}
}
