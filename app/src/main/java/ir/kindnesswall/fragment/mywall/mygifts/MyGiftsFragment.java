package ir.kindnesswall.fragment.mywall.mygifts;

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
import ir.kindnesswall.activity.BottomBarActivity;
import ir.kindnesswall.activity.LoginActivity;
import ir.kindnesswall.adapter.ViewPagerAdapter;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.customviews.CustomTabLayout;
import ir.kindnesswall.fragment.BaseFragment;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class MyGiftsFragment extends BaseFragment {

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
	private boolean hasNotAuthorityFirstTime;
	private boolean executeOnCreate;

	@Override
	protected void init() {
		super.init();

		((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("هدیه‌های من");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (rootView != null) {
//			if (rootView.getParent() != null)
//				((ViewGroup) rootView.getParent()).removeView(rootView);
//			return rootView;
//		}
		rootView = inflater.inflate(R.layout.fragment_my_gifts, container, false);

		ButterKnife.bind(this, rootView);
		init();

		if (AppController.getStoredString(Constants.Authorization) != null) {
			setupViewPager(mainVp);
			mainTabs.setupWithViewPager(mainVp);
			mainVp.setCurrentItem(2, false);

		} else {

			hasNotAuthorityFirstTime = true;
			myGiftLoginBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), LoginActivity.class));
				}
			});
		}

		if (AppController.getStoredString(Constants.Authorization) != null) {

			myGiftTopLay.setVisibility(View.GONE);
			myGiftBottomLay.setVisibility(View.VISIBLE);

			if (hasNotAuthorityFirstTime){
				setupViewPager(mainVp);
				mainTabs.setupWithViewPager(mainVp);
				mainVp.setCurrentItem(2, false);
			}

		} else {
			myGiftTopLay.setVisibility(View.VISIBLE);
			myGiftBottomLay.setVisibility(View.INVISIBLE);
		}
		executeOnCreate = true;

		return rootView;
	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

		RegisteredGiftsFragment registeredGiftsFragment = new RegisteredGiftsFragment();
		DonatedGiftsFragment donatedGiftsFragment = new DonatedGiftsFragment();
		ReceivedGiftsFragment receivedGiftsFragment = new ReceivedGiftsFragment();

		adapter.addFrag(receivedGiftsFragment, "دریافتی");
		adapter.addFrag(donatedGiftsFragment, "اهدایی");
		adapter.addFrag(registeredGiftsFragment, "ثبت شده");

		viewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();

		if (executeOnCreate){
			executeOnCreate = false;
			return;
		}
		if (AppController.getStoredString(Constants.Authorization) != null) {

			myGiftTopLay.setVisibility(View.GONE);
			myGiftBottomLay.setVisibility(View.VISIBLE);

			if (hasNotAuthorityFirstTime){
				setupViewPager(mainVp);
				mainTabs.setupWithViewPager(mainVp);
				mainVp.setCurrentItem(2, false);
			}

		} else {
			myGiftTopLay.setVisibility(View.VISIBLE);
			myGiftBottomLay.setVisibility(View.INVISIBLE);
		}
	}
}
