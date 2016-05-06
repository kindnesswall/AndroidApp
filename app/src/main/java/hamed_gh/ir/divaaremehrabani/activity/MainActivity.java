package hamed_gh.ir.divaaremehrabani.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.fragment.testFragment;

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
	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new testFragment(), "کتاب");
		adapter.addFragment(new testFragment(), "پوشاک");
		adapter.addFragment(new testFragment(), "لوازم");
		adapter.addFragment(new testFragment(), "غذا");
		viewPager.setAdapter(adapter);
	}

	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

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

		public void addFragment(Fragment fragment, String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}
	}
}