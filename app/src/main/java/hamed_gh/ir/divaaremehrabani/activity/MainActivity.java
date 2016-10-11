package hamed_gh.ir.divaaremehrabani.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.app.RestAPI;
import hamed_gh.ir.divaaremehrabani.app.URIs;
import hamed_gh.ir.divaaremehrabani.fragment.CategoriesFragment;
import hamed_gh.ir.divaaremehrabani.fragment.HomeFragment;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

//	@Bind(R.id.viewpager)
//	ViewPager viewPager;

	//	@Bind(R.id.tabs)
//	TabLayout tabLayout;
	public RestAPI service;
	private Context context;
	private Toolbar mToolbar;
	private TextView mToolbarTitleTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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


		context = this;

		setContentView(R.layout.activity_main);

		// -- set Toolbar ---
		mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
		mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		setSupportActionBar(mToolbar);
		try {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		} catch (Exception e) {

		}

		mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title_textView);

		mToolbarTitleTextView.setText("دیوار مهربانی");

		ButterKnife.bind(this);

//		setupViewPager(viewPager);
//		tabLayout.setupWithViewPager(viewPager);
//		Toasti.showS(viewPager.getCurrentItem()+"");
	}

	@Override
	protected void onResume() {
		super.onResume();
//		viewPager.setCurrentItem(3);
	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new HomeFragment());
		adapter.addFragment(new HomeFragment());
		adapter.addFragment(new CategoriesFragment());
		adapter.addFragment(new HomeFragment());
		viewPager.setAdapter(adapter);
	}

	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();

		private int[] imageResId = {
				R.mipmap.ic_person_white_24dp,
				R.mipmap.ic_search_white_24dp,
				R.mipmap.ic_list_white_24dp,
				R.mipmap.ic_home_white_24dp
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