package ir.kindnesswall.customviews.customindicator;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Ali Muzaffar on 5/02/2016.
 */
public class MyPageIndicator implements ViewPager.OnPageChangeListener {
	private Context mContext;
	private LinearLayout mContainer;
	private int mDrawable;
	private int mSpacing;
	private int mSize;
	private ViewPager mViewPager;
	private int mPageCount;
	private int mInitialPage = 0;

	private int defaultSizeInDp = 12;
	private int defaultSpacingInDp = 12;

	public MyPageIndicator(@NonNull Context context, @NonNull LinearLayout containerView, @NonNull ViewPager viewPager, @DrawableRes int drawableRes) {
		if (context == null) {
			throw new IllegalArgumentException("context cannot be null");
		} else if (containerView == null) {
			throw new IllegalArgumentException("containerView cannot be null");
		} else if (viewPager == null) {
			throw new IllegalArgumentException("ViewPager cannot be null");
		} else if (viewPager.getAdapter() == null) {
			throw new IllegalArgumentException("ViewPager does not have an adapter set on it.");
		}
		mContext = context;
		mContainer = containerView;
		mDrawable = drawableRes;
		mViewPager = viewPager;

	}

	public void setPageCount(int pageCount) {
		mPageCount = pageCount;
	}

	public void setInitialPage(int page) {
		mInitialPage = page;
	}

	public void setDrawable(@DrawableRes int drawable) {
		mDrawable = drawable;
	}

	public void setSpacingRes(@DimenRes int spacingRes) {
		mSpacing = spacingRes;
	}

	public void setSize(@DimenRes int dimenRes) {
		mSize = dimenRes;
	}

	public void show() {
		initIndicators();
		setIndicatorAsSelected(mInitialPage);
	}

	private void initIndicators() {
		if (mContainer == null || mPageCount <= 0) {
			return;
		}

		mViewPager.addOnPageChangeListener(this);
		Resources res = mContext.getResources();
		mContainer.removeAllViews();
		for (int i = 0; i < mPageCount; i++) {
			View view = new View(mContext);
			int dimen = mSize != 0 ? res.getDimensionPixelSize(mSize) : ((int) res.getDisplayMetrics().density * defaultSizeInDp);
			int margin = mSpacing != 0 ? res.getDimensionPixelSize(mSpacing) : ((int) res.getDisplayMetrics().density * defaultSpacingInDp);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dimen, dimen);
			lp.setMargins(i == 0 ? 0 : margin, 0, 0, 0);
			view.setLayoutParams(lp);
			view.setBackgroundResource(mDrawable);
			view.setSelected(i == 0);
			mContainer.addView(view);
		}
	}

	private void setIndicatorAsSelected(int index) {
		if (mContainer == null) {
			return;
		}
		for (int i = 0; i < mContainer.getChildCount(); i++) {
			View view = mContainer.getChildAt(i);
			view.setSelected(i == index);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		int index = position % mPageCount;
		setIndicatorAsSelected(index);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	public void cleanup() {
		mViewPager.clearOnPageChangeListeners();
	}
}
