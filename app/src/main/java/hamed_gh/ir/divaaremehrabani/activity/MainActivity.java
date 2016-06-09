package hamed_gh.ir.divaaremehrabani.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.fragment.testFragment;
import hamed_gh.ir.divaaremehrabani.helper.Toasti;

public class MainActivity extends BaseActivity {

	@Bind(R.id.viewpager)
	ViewPager viewPager;

	@Bind(R.id.tabs)
	TabLayout tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init(R.layout.activity_main);
		setToolbarTitle("دیوار مهربانی");

		ButterKnife.bind(this);

		BaseActivity.howToBack = HowToBack.NOTHING;

		setShowDrawerMenu(true);
		setDrawer();

		setupViewPager(viewPager);
		tabLayout.setupWithViewPager(viewPager);
		Toasti.showS(viewPager.getCurrentItem()+"");
	}

	@Override
	protected void onResume() {
		super.onResume();
		viewPager.setCurrentItem(3);
	}

	private void setupViewPager( ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new testFragment());
		adapter.addFragment(new testFragment());
		adapter.addFragment(new testFragment());
		adapter.addFragment(new testFragment());
		viewPager.setAdapter(adapter);
	}

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