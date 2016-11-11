package ir.hamed_gh.divaremehrabani.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import ir.hamed_gh.divaremehrabani.customviews.customindicator.MyPageIndicator;

public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.bookmark_ic)
    ImageView mBookmarkIc;

    @Bind(R.id.share_ic)
    ImageView mShareIc;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    int[] mResources = {
            R.drawable.rectangle_blue,
            R.drawable.rectangle_red
    };
    private MyPageIndicator mIndicator;
    View.OnClickListener addToWishList;
    View.OnClickListener removeFromWishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setupViewPager(viewPager);

        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);

        mIndicator = new MyPageIndicator(this, mLinearLayout, viewPager, R.drawable.indicator_circle);
        mIndicator.setPageCount(mResources.length);
        mIndicator.show();

        addToWishList = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark);

                mBookmarkIc.setOnClickListener(removeFromWishList);
            }
        };

        removeFromWishList = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark_outline);
                mBookmarkIc.setOnClickListener(addToWishList);
            }
        };

        mBookmarkIc.setOnClickListener(addToWishList);

        mShareIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "عنوان");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "تصویر");
        startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری با: "));
    }

    private void setupViewPager(ViewPager viewPager) {
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