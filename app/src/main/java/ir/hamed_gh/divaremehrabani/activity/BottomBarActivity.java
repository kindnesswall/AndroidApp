package ir.hamed_gh.divaremehrabani.activity;

import android.content.ComponentName;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.bottombar.BottomBar;
import ir.hamed_gh.divaremehrabani.bottombar.OnMenuTabClickListener;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.fragment.HomeFragment;
import ir.hamed_gh.divaremehrabani.fragment.category.CategoriesGridFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.BookmarkFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.MyWallFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.StatisticFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.mygifts.MyGiftsFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.requests.MyRequestsFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.requests.RequestsToAGiftFragment;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.DeviceInfo;
import ir.hamed_gh.divaremehrabani.helper.UpdateChecker;
import ir.hamed_gh.divaremehrabani.model.api.output.UpdateOutput;
import retrofit2.Call;
import retrofit2.Response;

public class BottomBarActivity extends AppCompatActivity implements ApiRequest.Listener {

	@Bind(R.id.toolbar_title_textView)
	public TextView mToolbarTitleTextView;
	@Bind(R.id.main_toolbar)
	Toolbar mToolbar;
	@Bind(R.id.toolbar_new_gift_btn_tv)
	TextView mToolbarNewGiftBtnTv;
	int menuItemIdSelected = -1;

	HomeFragment homeFragment;
	HomeFragment searchFragment;
	CategoriesGridFragment categoriesGridFragment;
	MyWallFragment myWallFragment;

	private Context context;
	private BottomBar mBottomBar;
	private Boolean unlock = false;
	private String currentVersionName;
	private Bundle savedInstanceState;

	private void settingToolbar() {
		mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		setSupportActionBar(mToolbar);
		try {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		} catch (Exception e) {

		}
		mToolbarTitleTextView.setText("دیوار مهربانی");
	}

	private void TabSelected(int menuItemId) {

		if (menuItemId == R.id.bottomBarHome) {

			if (menuItemId != menuItemIdSelected) {
				clearStack();

				unlock = true;
				mToolbarTitleTextView.setText("همه هدیه‌های " + AppController.getStoredString(Constants.MY_LOCATION_NAME));
				replaceFragment(
						homeFragment,
						HomeFragment.class.getName() + Constants.HOME_PAGETYPE);
			}
			menuItemIdSelected = menuItemId;

			// The user reselected item number one, scroll your content to top.
		} else if (menuItemId == R.id.bottomBarCategories) {

			if (menuItemId != menuItemIdSelected) {
				clearStack();

				unlock = true;
				mToolbarTitleTextView.setText(R.string.categories);
				replaceFragment(
						categoriesGridFragment,
						CategoriesGridFragment.class.getName()
				);
			}
			menuItemIdSelected = menuItemId;

			// The user selected item number one.
		} else if (menuItemId == R.id.bottomBarSearch) {

			if (menuItemId != menuItemIdSelected) {
				clearStack();

				unlock = true;
				mToolbarTitleTextView.setText(R.string.search);
				replaceFragment(
						searchFragment,
						HomeFragment.class.getName() + Constants.SEARCH_PAGETYPE
				);
			}
			menuItemIdSelected = menuItemId;

			// The user selected item number one.
		} else if (menuItemId == R.id.bottomBarMyWall && unlock) {

			if (menuItemId != menuItemIdSelected) {
				clearStack();

				mToolbarTitleTextView.setText(R.string.my_wall);
				replaceFragment(myWallFragment, MyWallFragment.class.getName());
			}
			menuItemIdSelected = menuItemId;

			// The user selected item number one.
		}
	}

	private void clearStack() {

		FragmentManager fm = getSupportFragmentManager();
		for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
			fm.popBackStack();
		}

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
				TabSelected(menuItemId);
			}

			@Override
			public void onMenuTabReSelected(@IdRes int menuItemId) {
				TabSelected(menuItemId);
//				if (menuItemId == R.id.bottomBarHome) {
//					Toasti.showS("Home reselected");
//					// The user reselected item number one, scroll your content to top.
//				} else if (menuItemId == R.id.bottomBarCategories) {
//					Toasti.showS("Catagories reselected");
//					// The user selected item number one.
//				} else if (menuItemId == R.id.bottomBarSearch) {
//					Toasti.showS("Search reselected");
//					// The user selected item number one.
//				} else if (menuItemId == R.id.bottomBarMyWall) {
//					Toasti.showS("MyWall reselected");
//					// The user selected item number one.
//				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;

		setContentView(R.layout.activity_bottombar);
		ButterKnife.bind(this);

		context = this;
		settingToolbar();

		currentVersionName = UpdateChecker.getAppVersion(context);


		mToolbarNewGiftBtnTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (AppController.getStoredString(Constants.Authorization) != null) {
					startActivity(RegisterGiftActivity.createIntent());
				} else {
					startActivity(LoginActivity.createIntent());
				}

			}
		});

		ApiRequest apiRequest = new ApiRequest(this, this);
		apiRequest.getUpdatedVersion();
	}

	private void setContent() {

		homeFragment = HomeFragment.newInstance(Constants.HOME_PAGETYPE, null);
		searchFragment = HomeFragment.newInstance(Constants.SEARCH_PAGETYPE, null);
		categoriesGridFragment = new CategoriesGridFragment();
		myWallFragment = new MyWallFragment();

		settingBottomBar(savedInstanceState);
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

	@Override
	protected void onPostResume() {
		try {
			super.onPostResume();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void replaceFragment(Fragment fragment, String title) {
		try {

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			fragmentTransaction.replace(R.id.container_body, fragment, title);
			fragmentTransaction.addToBackStack(title);
			fragmentTransaction.commit();
			fragmentManager.executePendingTransactions();

		} catch (Exception e) {
			//Todo : when app is finishing and homefragment request is not cancled or other requests exists:
			// java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
		}
	}

	public void addFragment(Fragment fragment, String title) {
		try {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			fragmentTransaction.add(R.id.container_body, fragment, title);
			fragmentTransaction.addToBackStack(title);
			fragmentTransaction.commit();

			fragmentManager.executePendingTransactions();

		} catch (Exception e) {
			//Todo : when app is finishing and homefragment request is not cancled or other requests exists:
			// java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
		}
	}

	@Override
	public void onBackPressed() {

		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
			FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
			String tag = backEntry.getName();

			if (tag.equals(HomeFragment.class.getName() + Constants.HOME_PAGETYPE)
					) {
				finish();
			} else if (
					tag.equals(BookmarkFragment.class.getName()) ||
							tag.equals(StatisticFragment.class.getName()) ||
							tag.equals(MyRequestsFragment.class.getName()) ||
							tag.equals(RequestsToAGiftFragment.class.getName()) ||
							tag.equals(MyGiftsFragment.class.getName()) ||
							tag.equals(HomeFragment.class.getName() + CategoriesGridFragment.class.getName())
					) {
				super.onBackPressed();
			} else {

				mToolbarTitleTextView.setText("همه هدیه‌های " + AppController.getStoredString(Constants.MY_LOCATION_NAME));
				replaceFragment(
						homeFragment,
						HomeFragment.class.getName() + Constants.HOME_PAGETYPE);

			}
		}

//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentByTag(HomeFragment.class.getName() + Constants.HOME_PAGETYPE);
//
//        if (fragment!=null){
//            finish();
//        }else {
//
//            mToolbarTitleTextView.setText("همه هدیه‌های " + AppController.getStoredString(Constants.MY_LOCATION_NAME));
//            replaceFragment(
//                    homeFragment,
//                    HomeFragment.class.getName() + Constants.HOME_PAGETYPE);
//
//        }

//        if (fm.getBackStackEntryCount()<=0) {
//            return;
//        }else {
//
//
//        }
//        finish();
	}

	private void onUpdateVersionResponse(UpdateOutput updateOutput) {

//		switch (updateOutput.status) {
//			case "0":
//				Toasti.showS(mContext, getString(R.string.internal_error));
//				break;
//			case "1":
		boolean isForcedUpdate;

		if (updateOutput.force_update != null && updateOutput.force_update.equalsIgnoreCase("true")) {
			isForcedUpdate = true;
		} else {
			isForcedUpdate = false;//todo use this
		}

		UpdateChecker updateChecker = new UpdateChecker(
				getResources().getString(R.string.app_name),
				updateOutput.version,
				updateOutput.apk_url,
				null,
				updateOutput.changes);

		if (!isForcedUpdate) {
//					callApiGetHomeByTagId();
			setContent();
		}

		if (DeviceInfo.getAppVersionCode() < Integer.valueOf(updateChecker.mUpdateDetail.latestVersion)) {
			//Notify Update
			Intent[] intents = new Intent[1];
			intents[0] = Intent.makeMainActivity(new ComponentName(AppController.getAppContext(),
					BottomBarActivity.class));
			// intents[1] = new Intent(AppController.getAppContext(), HomeActivity.class);
			updateChecker.showUpdaterDialog(
					context,
					getString(R.string.update_to_new_version),
					getString(R.string.exist_new_version),
					updateOutput.changes,
					updateOutput.version,
					intents,
					isForcedUpdate);

			AppController.getInstance().setIsCheckedUpdate(true);

		}

//				break;
//			default:
//				callApiGetHomeByTagId();
//				break;
//		}


	}

	@Override
	public void onResponse(Call call, Response response) {
		if (response.body() instanceof UpdateOutput) {
			onUpdateVersionResponse((UpdateOutput) response.body());
		}
	}

	@Override
	public void onFailure(Call call, Throwable t) {

	}
}