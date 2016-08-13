package hamed_gh.ir.divaaremehrabani.activity;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.app.RestAPI;
import hamed_gh.ir.divaaremehrabani.app.URIs;
import hamed_gh.ir.divaaremehrabani.bottombar.BottomBar;
import hamed_gh.ir.divaaremehrabani.bottombar.OnMenuTabClickListener;
import hamed_gh.ir.divaaremehrabani.fragment.CategoriesFragment;
import hamed_gh.ir.divaaremehrabani.fragment.HomeFragment;
import hamed_gh.ir.divaaremehrabani.helper.Toasti;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BottomBarActivity extends AppCompatActivity {

//	@Bind(R.id.viewpager)
//	ViewPager viewPager;

	private Context context;
	private Toolbar mToolbar;
	private TextView mToolbarTitleTextView;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
    private BottomBar mBottomBar;
    public RestAPI service;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;

		setContentView(R.layout.activity_bottombar);

//	    OkHttpClient httpClient = new OkHttpClient();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("token", "s:s").build();
                                return chain.proceed(request);
                            }
                        }).build();

//	    httpClient.networkInterceptors().add(new Interceptor() {
//		    @Override
//		    public Response intercept(Chain chain) throws IOException {
//			    Request request = chain.request().newBuilder().addHeader("token", "s:s").build();
//			    return chain.proceed(request);
//		    }
//	    });

//	    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).client(httpClient).build();

        //Creating Rest Services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URIs.API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        service = retrofit.create(RestAPI.class);

		// -- set Toolbar ---
        RelativeLayout toolbar_layout = (RelativeLayout)findViewById(R.id.toolbar_layout);
		mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
		mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		setSupportActionBar(mToolbar);
		try {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		} catch (Exception e) {

		}
		mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title_textView);

		mToolbarTitleTextView.setText("دیوار مهربانی");

        setFragment(new HomeFragment(),"Home");

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.noTopOffset();
        mBottomBar.useFixedMode();
        mBottomBar.setTypeFace("fonts/iran_sans_regular.ttf");
        mBottomBar.setItems(R.menu.menu_bottombar);
        mBottomBar.findViewById(R.id.bb_bottom_bar_background_view).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        int color = getResources().getColor(R.color.white);

        ((ImageView)mBottomBar.findViewById(R.id.bb_bottom_bar_icon)).setColorFilter(color);


        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarHome) {
                    Toasti.showS("Home selected");
//                    setFragment(new HomeFragment(),"Home");
                    // The user reselected item number one, scroll your content to top.
                }else if (menuItemId == R.id.bottomBarCategories) {
                    Toasti.showS("Catagories selected");
                    // The user selected item number one.
                }else if (menuItemId == R.id.bottomBarSearch) {
                    Toasti.showS("Search selected");
                    // The user selected item number one.
                }else if (menuItemId == R.id.bottomBarMyWall) {
                    Toasti.showS("MyWall selected");
                    // The user selected item number one.
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarHome) {
                    Toasti.showS("Home reselected");
                    // The user reselected item number one, scroll your content to top.
                }else if (menuItemId == R.id.bottomBarCategories) {
                    Toasti.showS("Catagories reselected");
                    // The user selected item number one.
                }else if (menuItemId == R.id.bottomBarSearch) {
                    Toasti.showS("Search reselected");
                    // The user selected item number one.
                }else if (menuItemId == R.id.bottomBarMyWall) {
                    Toasti.showS("MyWall reselected");
                    // The user selected item number one.
                }
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
//        mBottomBar.mapColorForTab(0, "#7B1FA2");//ContextCompat.getColor(this, R.color.colorAccent));
//        mBottomBar.mapColorForTab(1, "#7B1FA2");
////        mBottomBar.mapColorForTab(1, 0xFF5D4037);
//        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");
//         ATTENTION: This was auto-generated to implement the App Indexing API.
//         See https://g.co/AppIndexing/AndroidStudio for more information.

//		ButterKnife.bind(this);

//		setupViewPager(viewPager);
//		Toasti.showS(viewPager.getCurrentItem() + "");

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
//		viewPager.setCurrentItem(3);
	}

    public void setFragment(Fragment fragment, String title) {
        try {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
//                String name = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();

//                if (name.equals(title)) { //just close drawer when I choose current fragment
//
//                    return;
//                }
//                String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
//                if (getSupportFragmentManager().findFragmentByTag(tag) != null)
//                    fragmentTransaction.remove(getSupportFragmentManager().findFragmentByTag(tag));
//            }
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.addToBackStack(title);
            fragmentTransaction.commit();

        } catch (Exception e) {
            //Todo : when app is finishing and homefragment request is not cancled or other requests exists:
            // java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        }
    }

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new HomeFragment());
		adapter.addFragment(new HomeFragment());
		adapter.addFragment(new CategoriesFragment());
		adapter.addFragment(new HomeFragment());
		viewPager.setAdapter(adapter);
	}

//	@Override
//	public void onStart() {
//		super.onStart();
//
//		// ATTENTION: This was auto-generated to implement the App Indexing API.
//		// See https://g.co/AppIndexing/AndroidStudio for more information.
//		client.connect();
//		Action viewAction = Action.newAction(
//				Action.TYPE_VIEW, // TODO: choose an action type.
//				"BottomBar Page", // TODO: Define a title for the content shown.
//				// TODO: If you have web page content that matches this app activity's content,
//				// make sure this auto-generated web page URL is correct.
//				// Otherwise, set the URL to null.
//				Uri.parse("http://host/path"),
//				// TODO: Make sure this auto-generated app URL is correct.
//				Uri.parse("android-app://hamed_gh.ir.divaaremehrabani.activity/http/host/path")
//		);
//		AppIndex.AppIndexApi.start(client, viewAction);
//	}
//
//	@Override
//	public void onStop() {
//		super.onStop();
//
//		// ATTENTION: This was auto-generated to implement the App Indexing API.
//		// See https://g.co/AppIndexing/AndroidStudio for more information.
//		Action viewAction = Action.newAction(
//				Action.TYPE_VIEW, // TODO: choose an action type.
//				"BottomBar Page", // TODO: Define a title for the content shown.
//				// TODO: If you have web page content that matches this app activity's content,
//				// make sure this auto-generated web page URL is correct.
//				// Otherwise, set the URL to null.
//				Uri.parse("http://host/path"),
//				// TODO: Make sure this auto-generated app URL is correct.
//				Uri.parse("android-app://hamed_gh.ir.divaaremehrabani.activity/http/host/path")
//		);
//		AppIndex.AppIndexApi.end(client, viewAction);
//		client.disconnect();
//	}

	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();

		private int[] imageResId = {
				R.drawable.ic_person_white_24dp,
				R.drawable.ic_search_white_24dp,
				R.drawable.ic_list_white_24dp,
				R.drawable.ic_home_white_24dp
		};

		public ViewPagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void addFragment(Fragment fragment) {
			mFragmentList.add(fragment);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
			image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
			SpannableString sb = new SpannableString(" ");
			ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
			sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			return sb;
		}
	}
}