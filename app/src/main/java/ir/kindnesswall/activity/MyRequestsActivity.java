package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.ViewPagerAdapter;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.customviews.CustomTabLayout;
import ir.kindnesswall.fragment.mywall.requests.ReceivedRequestsFragment;
import ir.kindnesswall.fragment.mywall.requests.SentRequestsFragment;

public class MyRequestsActivity extends AppCompatActivity {

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

	@Bind(R.id.search_imageview)
	ImageView searchIV;

	private View rootView;
	private ViewPagerAdapter adapter;
	private boolean hasNotAuthorityFirstTime;


	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), MyRequestsActivity.class);
//		intent.putExtra(Constants.GIFT, gift);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_requests);
		ButterKnife.bind(this);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		searchIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(SearchActivity.createIntent());
			}
		});

		if (AppController.getStoredString(Constants.Authorization) != null) {
			setupViewPager(mainVp);
			mainTabs.setupWithViewPager(mainVp);
			mainVp.setCurrentItem(1, false);

		} else {

			hasNotAuthorityFirstTime = true;
			myGiftLoginBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(MyRequestsActivity.this, LoginActivity.class));
				}
			});
		}
	}

	@Override
	public void onResume() {
		super.onResume();

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

	private void setupViewPager(ViewPager viewPager) {
		adapter = new ViewPagerAdapter(getSupportFragmentManager());

		SentRequestsFragment sentRequestsFragment = new SentRequestsFragment();
		ReceivedRequestsFragment receivedRequestsFragment = new ReceivedRequestsFragment();

		adapter.addFrag(sentRequestsFragment, "ارسالی");
		adapter.addFrag(receivedRequestsFragment, "دریافتی");

		viewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

}
