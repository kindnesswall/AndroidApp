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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.customviews.customindicator.MyPageIndicator;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.Snackbari;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.input.RequestGiftInput;
import ir.hamed_gh.divaremehrabani.model.api.output.RequestGiftOutput;
import retrofit2.Call;
import retrofit2.Response;

public class GiftDetailActivity extends AppCompatActivity implements ApiRequest.Listener{

	@Bind(R.id.bookmark_ic)
	ImageView mBookmarkIc;

	@Bind(R.id.share_ic)
	ImageView mShareIc;

	@Bind(R.id.viewpager)
	ViewPager viewPager;

	@Bind(R.id.toolbar_title_tv)
	TextView mToolbarTitleTv;

	@Bind(R.id.detail_title_tv)
	TextView mDetailTitleTv;

	@Bind(R.id.detail_description_tv)
	TextView mDetailDescriptionTv;

	@Bind(R.id.pagesContainer)
	LinearLayout pagesContainer;

	@Bind(R.id.bottomBarLayBtn)
	RelativeLayout bottomBarLayBtn;

	View.OnClickListener addToWishList;
	View.OnClickListener removeFromWishList;
	private Gift gift;
	private MyPageIndicator mIndicator;
    private ApiRequest apiRequest;

    public static Intent createIntent(Gift gift) {
		Intent intent = new Intent(AppController.getAppContext(), GiftDetailActivity.class);
		intent.putExtra(Constants.GIFT, gift);
		return intent;
	}

	private void extractDataFromBundle() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			gift = (Gift) bundle.get(Constants.GIFT);
			mToolbarTitleTv.setText(gift.title);
			mDetailDescriptionTv.setText(gift.description);
			mDetailTitleTv.setText(gift.title);
		}
	}

	private void setListeners(){
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

		bottomBarLayBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                if (AppController.getStoredString(Constants.Authorization)!=null){
                    apiRequest.sendRequestGift(
                            new RequestGiftInput(
                                    gift.giftId
                            )
                    );
                }else {
                    Snackbari.showS(bottomBarLayBtn, "ابتدا لاگین شوید");
                }

			}
		});
	}

	private void init(){
		ButterKnife.bind(this);
		extractDataFromBundle();
		setupViewPager(viewPager);

        apiRequest = new ApiRequest(this, this);

		mIndicator = new MyPageIndicator(this, pagesContainer, viewPager, R.drawable.indicator_circle);
		mIndicator.setPageCount(gift.giftImages != null? gift.giftImages.size():0);
		mIndicator.show();

		setListeners();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		init();
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

    @Override
    public void onResponse(Call call, Response response) {
        if (response.body() instanceof RequestGiftOutput){
            Snackbari.showS(bottomBarLayBtn, "درخواست ارسال شد");
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

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
			return (gift.giftImages != null? gift.giftImages.size() : 0);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((RelativeLayout) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View itemView = mLayoutInflater.inflate(R.layout.vp_image, container, false);

			ImageView imageView = (ImageView) itemView.findViewById(R.id.image_display);
//			imageView.setImageResource(mResources[position]);

			Glide
					.with(mContext)
					.load(gift.giftImages.get(position))
					.centerCrop()
					.placeholder(R.color.background)
					.crossFade()
					.into(imageView);

			container.addView(itemView);

			return itemView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((RelativeLayout) object);
		}
	}
}