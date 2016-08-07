package hamed_gh.ir.divaaremehrabani.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.helper.Toasti;
import me.relex.circleindicator.CircleIndicator;

public class DetailActivity extends AppCompatActivity {

	@Bind(R.id.viewpager)
	ViewPager viewPager;

    @Bind(R.id.container_body)
    FrameLayout c;

	int[] mResources = {
			R.drawable.rectangle_blue,
			R.drawable.rectangle_red
	};
    private BottomBar mBottomBar;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

		ButterKnife.bind(this);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
		setupViewPager(viewPager);
		indicator.setViewPager(viewPager);


        mBottomBar = BottomBar.attach(this, savedInstanceState);
//        mBottomBar.noTopOffset();
        mBottomBar.setItems(R.menu.menu_bottombar);

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarHome) {
                    Toasti.showS("Home selected");
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
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
	}


	private void setupViewPager( ViewPager viewPager) {
        CustomPagerAdapter adapter = new CustomPagerAdapter(this);
		viewPager.setAdapter(adapter);
	}


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.vp_image, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.image_display);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}