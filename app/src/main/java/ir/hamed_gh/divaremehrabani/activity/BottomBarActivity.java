package ir.hamed_gh.divaremehrabani.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.bottombar.BottomBar;
import ir.hamed_gh.divaremehrabani.bottombar.OnMenuTabClickListener;
import ir.hamed_gh.divaremehrabani.fragment.HomeFragment;
import ir.hamed_gh.divaremehrabani.fragment.category.CategoriesGridFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.MyWallFragment;
import ir.hamed_gh.divaremehrabani.helper.Toasti;

public class BottomBarActivity extends AppCompatActivity {

	@Bind(R.id.toolbar_title_textView)
	public TextView mToolbarTitleTextView;
	@Bind(R.id.main_toolbar)
	Toolbar mToolbar;
	@Bind(R.id.toolbar_new_gift_btn_tv)
	TextView mToolbarNewGiftBtnTv;

	private Context context;
	private BottomBar mBottomBar;

	private void settingToolbar() {
		mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		setSupportActionBar(mToolbar);
		try {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		} catch (Exception e) {

		}
		mToolbarTitleTextView.setText("دیوار مهربانی");
	}

	private void settingBottomBar(Bundle savedInstanceState) {
		mBottomBar = BottomBar.attach(this, savedInstanceState);
		mBottomBar.noTopOffset();
		mBottomBar.useFixedMode();
		mBottomBar.setTypeFace("fonts/IRANSansMobile_Light-4.1.ttf");
		mBottomBar.setItems(R.menu.menu_bottombar);
		mBottomBar.findViewById(R.id.bb_bottom_bar_background_view).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
		int color = getResources().getColor(R.color.white);

		((ImageView) mBottomBar.findViewById(R.id.bb_bottom_bar_icon)).setColorFilter(color);


		mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
			@Override
			public void onMenuTabSelected(@IdRes int menuItemId) {
				if (menuItemId == R.id.bottomBarHome) {

					mToolbarTitleTextView.setText("همه هدیه‌های " + AppController.getStoredString(Constants.MY_LOCATION_NAME));
					setFragment(
							HomeFragment.newInstance(Constants.HOME_PAGETYPE,null),
							HomeFragment.class.getName() + Constants.HOME_PAGETYPE);
					// The user reselected item number one, scroll your content to top.
				} else if (menuItemId == R.id.bottomBarCategories) {

					mToolbarTitleTextView.setText(R.string.categories);
					setFragment(new CategoriesGridFragment(), CategoriesGridFragment.class.getName());

					// The user selected item number one.
				} else if (menuItemId == R.id.bottomBarSearch) {

					mToolbarTitleTextView.setText(R.string.search);
					setFragment(
							HomeFragment.newInstance(Constants.SEARCH_PAGETYPE, null),
							HomeFragment.class.getName() + Constants.SEARCH_PAGETYPE
					);

					// The user selected item number one.
				} else if (menuItemId == R.id.bottomBarMyWall) {

					mToolbarTitleTextView.setText(R.string.my_wall);
					setFragment(new MyWallFragment(), MyWallFragment.class.getName());

					// The user selected item number one.
				}
			}

			@Override
			public void onMenuTabReSelected(@IdRes int menuItemId) {
				if (menuItemId == R.id.bottomBarHome) {
					Toasti.showS("Home reselected");
					// The user reselected item number one, scroll your content to top.
				} else if (menuItemId == R.id.bottomBarCategories) {
					Toasti.showS("Catagories reselected");
					// The user selected item number one.
				} else if (menuItemId == R.id.bottomBarSearch) {
					Toasti.showS("Search reselected");
					// The user selected item number one.
				} else if (menuItemId == R.id.bottomBarMyWall) {
					Toasti.showS("MyWall reselected");
					// The user selected item number one.
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics());
		setContentView(R.layout.activity_bottombar);
		ButterKnife.bind(this);

		context = this;

		settingToolbar();

		setFragment(
				HomeFragment.newInstance(Constants.HOME_PAGETYPE,null),
				HomeFragment.class.getName() + Constants.HOME_PAGETYPE
		);

		settingBottomBar(savedInstanceState);

		mToolbarNewGiftBtnTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (AppController.getStoredString(Constants.Authorization) != null) {
					startActivity(new Intent(context, RegisterGiftActivity.class));

				} else {

					startActivity(new Intent(context, LoginActivity.class));

				}

			}
		});

		mBottomBar.selectTabAtPosition(3, false);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Necessary to restore the BottomBar's state, otherwise we would
		// lose the current tab on orientation change.
		mBottomBar.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	public void setFragment(Fragment fragment, String title) {
		try {

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			fragmentTransaction.replace(R.id.container_body, fragment, title);
			fragmentTransaction.addToBackStack(title);
			fragmentTransaction.commit();

		} catch (Exception e) {
			//Todo : when app is finishing and homefragment request is not cancled or other requests exists:
			// java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
		}
	}

}