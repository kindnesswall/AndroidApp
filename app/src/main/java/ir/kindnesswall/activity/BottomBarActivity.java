package ir.kindnesswall.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.bottombar.BottomBar;
import ir.kindnesswall.bottombar.OnMenuTabClickListener;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.fragment.HomeCategoryFragment;
import ir.kindnesswall.fragment.HomeFragment;
import ir.kindnesswall.fragment.category.CategoriesGridFragment;
import ir.kindnesswall.fragment.mywall.MyWallFragment;
import ir.kindnesswall.fragment.mywall.mygifts.MyGiftsFragment;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.helper.DeviceInfo;
import ir.kindnesswall.helper.MaterialDialogBuilder;
import ir.kindnesswall.helper.Snackbari;
import ir.kindnesswall.helper.UpdateChecker;
import ir.kindnesswall.interfaces.UpdateCheckerInterface;
import ir.kindnesswall.model.api.Category;
import ir.kindnesswall.model.api.Gift;
import ir.kindnesswall.model.api.StartLastIndex;
import ir.kindnesswall.model.api.output.AppInfoOutput;
import retrofit2.Call;
import retrofit2.Response;

public class BottomBarActivity extends AppCompatActivity implements ApiRequest.Listener , UpdateCheckerInterface {

	@Bind(R.id.toolbar_title_textView)
	public TextView mToolbarTitleTextView;

	@Bind(R.id.main_toolbar)
	Toolbar mToolbar;

	@Bind(R.id.toolbar_new_gift_btn_tv)
	TextView mToolbarNewGiftBtnTv;

	@Bind(R.id.toolbar_right_icon)
	ImageView toolbarRightIcon;

	@Bind(R.id.fab)
	FloatingActionButton fab;

	@Bind(R.id.search_imageview)
	ImageView searchIV;

	int menuItemIdSelected = -1;
	int menuItemIdReSelected = -1;

	//	HomeFragment homeFragment;
	HomeCategoryFragment homeCategoryFragment;
	//	HomeFragment searchFragment;
//	CharitiesFragment charitiesFragment;
//	CategoriesGridFragment categoriesGridFragment;
	MyGiftsFragment myGiftsFragment;

	MyWallFragment myWallFragment;

	private Context context;
	private BottomBar mBottomBar;
	private Boolean unlock = false;
	private String currentVersionName;
	private Bundle savedInstanceState;
	private Drawer drawer;
	private PrimaryDrawerItem myRequestsDrawerItem;
	private PrimaryDrawerItem bookmarksDrawerItem;
	private PrimaryDrawerItem statisticsDrawerItem;
	private PrimaryDrawerItem contactUsDrawerItem;
	private PrimaryDrawerItem aboutKindnessWallDrawerItem;
	private PrimaryDrawerItem ourTeamDrawerItem;
	private PrimaryDrawerItem reportBugsDrawerItem;
	private PrimaryDrawerItem updateAppDrawerItem;
	private PrimaryDrawerItem logoutDrawerItem;
	private PrimaryDrawerItem loginDrawerItem;
	private AccountHeaderBuilder accountHeaderBuilder;
	private DrawerBuilder drawerBuilder;
	private AccountHeader accountHeader;
	private HashMap<Type, PrimaryDrawerItem> drawerItemHashMap;
	private ApiRequest apiRequest;

	@Override
	public void onNotNowBtnClicked() {

	}

	@Override
	public void onNeverBtnClicked() {

	}

	public enum Type {
		myRequests,
		bookmarks,
		statistics,
		contactUs,
		aboutKindnessWall,
		ourTeam,
		reportBugs,
		updateApp,
		logout,
		login,
		divider
	}

	private ArrayList<Type> userDrawerList =
			new ArrayList<Type>(Arrays.asList(
					Type.myRequests,
					Type.bookmarks,
					Type.logout,
					Type.divider,
					Type.statistics,
					Type.updateApp,
					Type.contactUs,
					Type.aboutKindnessWall,
					Type.ourTeam,
					Type.reportBugs
			));

	private ArrayList<Type> guestDrawerList =
			new ArrayList<Type>(Arrays.asList(
					Type.login,
					Type.divider,
					Type.statistics,
					Type.updateApp,
					Type.contactUs,
					Type.aboutKindnessWall,
					Type.ourTeam,
					Type.reportBugs
			));

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), BottomBarActivity.class);
		return intent;
	}

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
//				mToolbarTitleTextView.setText("همه هدیه‌های " + AppController.getStoredString(Constants.MY_LOCATION_NAME));
				mToolbarTitleTextView.setText("همه هدیه‌ها");
//				replaceFragment(
//						homeFragment,
//						HomeFragment.class.getName() + Constants.HOME_PAGETYPE);

				replaceFragment(
						homeCategoryFragment,
						HomeCategoryFragment.class.getName());
			}
			menuItemIdSelected = menuItemId;

			// The user reselected item number one, scroll your content to top.
		} else if (menuItemId == R.id.bottomBarMyGifts) {

			if (menuItemId != menuItemIdSelected) {
				clearStack();

				unlock = true;
				mToolbarTitleTextView.setText(R.string.mygifts);
				replaceFragment(
						myGiftsFragment,
						MyGiftsFragment.class.getName()
				);
			}
			menuItemIdSelected = menuItemId;

			// The user selected item number one.
		}
//		else if (menuItemId == R.id.bottomBarSearch) {
//
//			if (menuItemId != menuItemIdSelected) {
//				clearStack();
//
//				unlock = true;
//				mToolbarTitleTextView.setText(R.string.search);
//				replaceFragment(
//						charitiesFragment,
//						CharitiesFragment.class.getName()
//				);
//			}
//			menuItemIdSelected = menuItemId;

		// The user selected item number one.
//		}
		else if (menuItemId == R.id.bottomBarMyWall && unlock) {

			if (menuItemId != menuItemIdSelected) {
				clearStack();

//				mToolbarTitleTextView.setText(R.string.my_wall);
//				replaceFragment(myWallFragment, MyWallFragment.class.getName());

				mToolbarTitleTextView.setText(R.string.bookshelf);
				Category category = new Category();
				category.categoryId = "4";
				category.title = "کتاب";

				replaceFragment(
						HomeFragment.newInstance(Constants.CATEGORY_PAGETYPE, category),
						HomeFragment.class.getName()
				);
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
				if (menuItemId == R.id.bottomBarHome) {
//					Toasti.showS("Home reselected");
//					// The user reselected item number one, scroll your content to top.
//				} else if (menuItemId == R.id.bottomBarCategories) {
//					Toasti.showS("Catagories reselected");
//					// The user selected item number one.

//					int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
//					FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
//					String tag = backEntry.getName();
//
					if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
						onBackPressed();
					}


				} else if (menuItemId == R.id.bottomBarMyGifts) {

//	              int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
//	              FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
//	              String tag = backEntry.getName();
//
//	              if (!tag.equals(MyGiftsFragment.class.getName())){
//		              onBackPressed();
//	              }

//              	onBackPressed();
//					Toasti.showS("Search reselected");
					// The user selected item number one.
				} else if (menuItemId == R.id.bottomBarMyWall) {

//              	if (menuItemId != menuItemIdReSelected){
//                }

//					int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
//					FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
//					String tag = backEntry.getName();
//
//					if (!tag.equals(MyWallFragment.class.getName())) { // && !tag.contains(HomeFragment.class.getName())){
//						onBackPressed();
//					}

//	              clearStack();
//	              mToolbarTitleTextView.setText(R.string.my_wall);
//	              replaceFragment(myWallFragment, MyWallFragment.class.getName());
//              	onBackPressed();
//					Toasti.showS("MyWall reselected");
					// The user selected item number one.
				}
//				menuItemIdReSelected = menuItemId;

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
		apiRequest = new ApiRequest(this, this);


		settingToolbar();

		currentVersionName = UpdateChecker.getAppVersion(context);

		toolbarRightIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				drawer.openDrawer();
			}
		});

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (AppController.getStoredString(Constants.Authorization) != null) {
					startActivity(RegisterGiftActivity.createIntent());
				} else {
					startActivity(LoginActivity.createIntent());
				}

			}
		});

		searchIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(SearchActivity.createIntent());
			}
		});

		setContent();

		if (AppController.getStoredString(Constants.Authorization) != null) {
			setUserDrawer();

			apiRequest.getRequestsToMyGifts(
					new StartLastIndex(
							"0",
							Constants.LIMIT + ""
					)
			);
		}else{
			setGuestDrawer();
		}

	}

	private void hideDrawerAccountSwitcher() {
		ImageView switcher = accountHeader.getView().findViewById(R.id.material_drawer_account_header_text_switcher);
		switcher.setVisibility(View.GONE);
	}

	private void setGuestDrawer() {
		setBaseDrawer();
		accountHeader.addProfiles(
				new ProfileDrawerItem().withName("")
						.withEmail("")
						.withIcon(getResources().getDrawable(R.drawable.app_icon))
		);
		hideDrawerAccountSwitcher();

		for (Type t : guestDrawerList) {
			PrimaryDrawerItem primaryDrawerItem =  drawerItemHashMap.get(t);
			if (primaryDrawerItem!=null){
				drawerBuilder.addDrawerItems(primaryDrawerItem);
			}else {
				drawerBuilder.addDrawerItems(new DividerDrawerItem());
			}
		}

		drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
			@Override
			public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
				drawerItemClickListener(guestDrawerList.get(position-1));
				return true;
			}
		});

		drawer = drawerBuilder.build();
	}

	private void setUserDrawer() {
		setBaseDrawer();

		accountHeader.addProfiles(
				new ProfileDrawerItem().withName("")
						.withEmail(
								AppController.getStoredString(Constants.TELEPHONE)
						)
						.withIcon(getResources().getDrawable(R.drawable.app_icon))
		);
		hideDrawerAccountSwitcher();

		for (Type t : userDrawerList) {
			PrimaryDrawerItem primaryDrawerItem =  drawerItemHashMap.get(t);
			if (primaryDrawerItem!=null){
				drawerBuilder.addDrawerItems(primaryDrawerItem);
			}else {
				drawerBuilder.addDrawerItems(new DividerDrawerItem());
			}
		}

		drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
			@Override
			public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
				drawerItemClickListener(userDrawerList.get(position-1));
				return true;
			}
		});

		drawer = drawerBuilder.build();
	}

	private void drawerItemClickListener(Type type) {
		switch (type){

			case myRequests:
//				Toasti.showS("myRequests");
				startActivity(MyRequestsActivity.createIntent());

				break;
			case bookmarks:
//				Toasti.showS("bookmarks");
				startActivity(BookmarkActivity.createIntent());

				break;
			case statistics:
//				Toasti.showS("statistics");
				startActivity(StatisticActivity.createIntent());

				break;
			case contactUs:
//				Toasti.showS("contactUs");
				startActivity(ContactUsActivity.createIntent());

				break;
			case aboutKindnessWall:
//				Toasti.showS("aboutKindnessWall");
				startActivity(AppIntro.createIntent());

				break;
			case ourTeam:
//				Toasti.showS("ourTeam");
				startActivity(OurTeamActivity.createIntent());

				break;
			case reportBugs:
//				Toasti.showS("reportBugs");

				Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/Kindness_Wall_Admin"));
				startActivity(telegram);

				break;
			case updateApp:
//				Toasti.showS("updateApp");

				AppController.storeString(Constants.VERSION_SKIP_UPDATE, null);
				apiRequest.getAppInfo();

				break;
			case logout:
//				Toasti.showS("logout");

				AppController.clearInfo();
				startActivity(BottomBarActivity.createIntent());
				finish();

				break;
			case login:
//				Toasti.showS("login");

				startActivity(LoginActivity.createIntent());


				break;
			case divider:
//				Toasti.showS("divider");
				break;

		}

		drawer.closeDrawer();

	}

	private void setBaseDrawer() {
		createDrawerItems();

		// Create the AccountHeader
		accountHeaderBuilder = new AccountHeaderBuilder()
				.withCompactStyle(true)
				.withActivity(this)
				.withHeaderBackground(R.color.colorPrimary)
				.withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
					@Override
					public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
						return false;
					}
				});

		accountHeader = accountHeaderBuilder.build();

		drawerBuilder = new DrawerBuilder()
				.withDrawerGravity(Gravity.RIGHT)
				.withAccountHeader(accountHeader)
				.withSelectedItem(-1)
				.withActivity(this);

	}

	private void createDrawerItems() {
		int lightBlackResColor = getResources().getColor(R.color.light_black);
		int darkWhiteResColor = getResources().getColor(R.color.dark_white);

		myRequestsDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.my_requests))
				.withIcon(R.mipmap.ic_human_handsup_grey600_18dp).withIconTintingEnabled(true)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		bookmarksDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.bookmarks))
				.withIcon(R.mipmap.ic_bookmark_white_24dp).withIconTintingEnabled(true)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		logoutDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.exit))
				.withIcon(R.mipmap.ic_logout_grey600_18dp).withIconTintingEnabled(false)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		loginDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.login))
				.withIcon(R.mipmap.ic_login_grey600_18dp).withIconTintingEnabled(false)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		statisticsDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.statistics))
				.withIcon(R.mipmap.ic_chart_bar_grey600_18dp).withIconTintingEnabled(false)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		contactUsDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.contact_us))
				.withIcon(R.mipmap.ic_contact_mail_grey600_18dp).withIconTintingEnabled(false)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		aboutKindnessWallDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.about_kindnesswall))
				.withIcon(R.mipmap.ic_kindness_logo).withIconTintingEnabled(false)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		ourTeamDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.our_team))
				.withIcon(R.mipmap.ic_account_multiple_grey600_18dp).withIconTintingEnabled(false)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		reportBugsDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.report_bugs))
				.withIcon(R.mipmap.ic_alert_octagram_grey600_18dp).withIconTintingEnabled(false)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);

		updateAppDrawerItem = new PrimaryDrawerItem()
				.withName(getString(R.string.update_app))
				.withIcon(R.mipmap.ic_update_grey600_18dp).withIconTintingEnabled(false)
				.withTextColor(lightBlackResColor)
				.withIconColor(darkWhiteResColor);


		drawerItemHashMap = new HashMap<Type , PrimaryDrawerItem>();

		drawerItemHashMap.put(Type.myRequests, myRequestsDrawerItem);
		drawerItemHashMap.put(Type.bookmarks, bookmarksDrawerItem);
		drawerItemHashMap.put(Type.logout, logoutDrawerItem);
		drawerItemHashMap.put(Type.login, loginDrawerItem);

		drawerItemHashMap.put(Type.statistics, statisticsDrawerItem);
		drawerItemHashMap.put(Type.updateApp, updateAppDrawerItem);
		drawerItemHashMap.put(Type.contactUs, contactUsDrawerItem);
		drawerItemHashMap.put(Type.aboutKindnessWall, aboutKindnessWallDrawerItem);
		drawerItemHashMap.put(Type.ourTeam, ourTeamDrawerItem);
		drawerItemHashMap.put(Type.reportBugs, reportBugsDrawerItem);

	}

	private void setContent() {

//		homeFragment = HomeFragment.newInstance(Constants.SEARCH_PAGETYPE, null);
		homeCategoryFragment = HomeCategoryFragment.newInstance();

//		searchFragment = HomeFragment.newInstance(Constants.SEARCH_PAGETYPE, null);
//		charitiesFragment = CharitiesFragment.newInstance();
//		categoriesGridFragment = new CategoriesGridFragment();
		myWallFragment = new MyWallFragment();
		myGiftsFragment = new MyGiftsFragment();

		settingBottomBar(savedInstanceState);
		mBottomBar.selectTabAtPosition(2, false);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Necessary to restore the BottomBar's state, otherwise we would
		// lose the current tab on orientation change.
		if (mBottomBar != null)
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

//			if (tag.equals(HomeFragment.class.getName() + Constants.HOME_PAGETYPE)
			if (tag.equals(HomeCategoryFragment.class.getName())
					) {
				finish();
			} else if (
//					tag.equals(BookmarkFragment.class.getName()) ||
//							tag.equals(StatisticFragment.class.getName()) ||
//							tag.equals(MyRequestsFragment.class.getName()) ||
//							tag.equals(RequestsToAGiftFragment.class.getName()) ||
////							tag.equals(MyGiftsFragment.class.getName()) ||
//							tag.equals(OurTeamFragment.class.getName()) ||
//							tag.equals(ContactUsFragment.class.getName()) ||
							tag.equals(HomeFragment.class.getName() + CategoriesGridFragment.class.getName())
					) {

				super.onBackPressed();

			} else {
				clearStack();

//				mToolbarTitleTextView.setText("همه هدیه‌های " + AppController.getStoredString(Constants.MY_LOCATION_NAME));
				mToolbarTitleTextView.setText("همه هدیه‌ها");
				replaceFragment(
						homeCategoryFragment,
						HomeCategoryFragment.class.getName());
//				replaceFragment(
//						homeFragment,
//						HomeFragment.class.getName() + Constants.HOME_PAGETYPE);
				menuItemIdSelected = R.id.bottomBarHome;
			}
		}

	}

	@Override
	public void onResponse(Call call, Response response) {
		if (response.body() instanceof AppInfoOutput) {

			onUpdateVersionResponse((AppInfoOutput) response.body());

		}else if (response.body() instanceof ArrayList) {

			ArrayList<Gift> gifts = (ArrayList<Gift>) response.body();
			if (gifts.size() > 0) {
				MaterialDialog.Builder builder =
						MaterialDialogBuilder.create(this)
								.customView(R.layout.dialog_simple_yes_no, false);

				final MaterialDialog dialog = builder.build();
				((TextView) dialog.findViewById(R.id.message_textview)).setText(
						"افرادی مایل به دریافت هدیه‌های شما هستند، آیا میخواهید درخواستها را مشاهده کنید؟"
				);

				RippleView yesBtnRipple = (RippleView) dialog.findViewById(R.id.yes_ripple_btn_cardview);
				yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
					@Override
					public void onComplete(RippleView rippleView) {

//						ReceivedRequestsFragment receivedRequestsFragment = new ReceivedRequestsFragment();
//
//						replaceFragment(receivedRequestsFragment, ReceivedRequestsFragment.class.getName());
//						mToolbarTitleTextView.setText("درخواستهای دریافتی");

						startActivity(MyRequestsActivity.createIntent());

						dialog.dismiss();
					}
				});

				RippleView noBtnRipple = (RippleView) dialog.findViewById(R.id.no_ripple_btn_cardview);
				noBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
					@Override
					public void onComplete(RippleView rippleView) {
						dialog.dismiss();
					}
				});

				dialog.show();
			}

		}else {
			Snackbari.showS(mToolbarTitleTextView, "شما با موفقیت از حساب خارج شدید");
		}
	}

	private void onUpdateVersionResponse(AppInfoOutput appInfoOutput) {

		AppController.storeString(Constants.SMS_CENTER, appInfoOutput.smsCenter);
		boolean isForcedUpdate;

		if (appInfoOutput.updateInfo.force_update != null && appInfoOutput.updateInfo.force_update.equalsIgnoreCase("true")) {
			isForcedUpdate = true;
		} else {
			isForcedUpdate = false;//todo use this
		}

		UpdateChecker updateChecker = new UpdateChecker(
				this,
				getResources().getString(R.string.app_name),
				appInfoOutput.updateInfo.version,
				appInfoOutput.updateInfo.apk_url,
				null,
				appInfoOutput.updateInfo.changes);

		if (isForcedUpdate ||
				DeviceInfo.getAppVersionCode() < Integer.valueOf(updateChecker.mUpdateDetail.latestVersion)) {

			//Notify Update
			Intent[] intents = new Intent[1];
			intents[0] = Intent.makeMainActivity(new ComponentName(AppController.getAppContext(),
					BottomBarActivity.class));
			// intents[1] = new Intent(AppController.getAppContext(), HomeActivity.class);
			updateChecker.showUpdaterDialog(
					context,
					getString(R.string.update_to_new_version),
					getString(R.string.exist_new_version),
					appInfoOutput.updateInfo.changes,
					appInfoOutput.updateInfo.version,
					intents,
					isForcedUpdate);

			AppController.getInstance().setIsCheckedUpdate(true);

		} else {

			Snackbari.showS(mToolbarTitleTextView, "برنامه شما به روز است.");

		}

	}


	@Override
	public void onFailure(Call call, Throwable t) {

	}
}