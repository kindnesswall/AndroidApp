package ir.hamed_gh.divaremehrabani.activity;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import ir.hamed_gh.divaremehrabani.fragment.CategoriesGridFragment;
import ir.hamed_gh.divaremehrabani.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

//	@Bind(R.id.viewpager)
//	ViewPager viewPager;

    //	@Bind(R.id.tabs)
//	TabLayout tabLayout;
    private Context context;
    private Toolbar mToolbar;
    private TextView mToolbarTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        adapter.addFragment(new CategoriesGridFragment());
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