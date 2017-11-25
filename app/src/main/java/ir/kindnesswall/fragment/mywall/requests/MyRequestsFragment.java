package ir.kindnesswall.fragment.mywall.requests;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.activity.LoginActivity;
import ir.kindnesswall.activity.MyRequestsActivity;
import ir.kindnesswall.adapter.ViewPagerAdapter;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.customviews.CustomTabLayout;
import ir.kindnesswall.fragment.BaseFragment;

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
	private ViewPagerAdapter adapter;
	private boolean hasNotAuthorityFirstTime;

	@Override
	protected void init() {
		super.init();
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

		if (AppController.getStoredString(Constants.Authorization) != null) {
			setupViewPager(mainVp);
			mainTabs.setupWithViewPager(mainVp);
			mainVp.setCurrentItem(1, false);

		} else {

			hasNotAuthorityFirstTime = true;
			myGiftLoginBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), LoginActivity.class));
				}
			});
		}

		return rootView;
	}

	private void setupViewPager(ViewPager viewPager) {
		adapter = new ViewPagerAdapter(getChildFragmentManager());

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

		((MyRequestsActivity) getActivity()).mToolbarTitleTextView.setText("درخواست‌های من");

		if (AppController.getStoredString(Constants.Authorization) != null) {

			myGiftTopLay.setVisibility(View.GONE);
			myGiftBottomLay.setVisibility(View.VISIBLE);

			if (hasNotAuthorityFirstTime){
				setupViewPager(mainVp);
				mainTabs.setupWithViewPager(mainVp);
				mainVp.setCurrentItem(1, false);
			}

		} else {
			myGiftTopLay.setVisibility(View.VISIBLE);
			myGiftBottomLay.setVisibility(View.INVISIBLE);
		}
	}
}
