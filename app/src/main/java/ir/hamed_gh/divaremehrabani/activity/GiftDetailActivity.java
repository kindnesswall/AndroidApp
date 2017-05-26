package ir.hamed_gh.divaremehrabani.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.constants.GiftStatus;
import ir.hamed_gh.divaremehrabani.customviews.customindicator.MyPageIndicator;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.MaterialDialogBuilder;
import ir.hamed_gh.divaremehrabani.helper.ReadJsonFile;
import ir.hamed_gh.divaremehrabani.helper.Snackbari;
import ir.hamed_gh.divaremehrabani.helper.Toasti;
import ir.hamed_gh.divaremehrabani.model.Place;
import ir.hamed_gh.divaremehrabani.model.Places;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.input.RequestGiftInput;
import ir.hamed_gh.divaremehrabani.model.api.output.RequestGiftOutput;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GiftDetailActivity extends AppCompatActivity implements ApiRequest.Listener {

    @Bind(R.id.bookmark_ic)
    ImageView mBookmarkIc;

    @Bind(R.id.share_ic)
    ImageView mShareIc;

    @Bind(R.id.call_iv)
    ImageView mCallBtn;

    @Bind(R.id.sms_iv)
    ImageView mSmsBtn;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;

    @Bind(R.id.detail_title_tv)
    TextView mDetailTitleTv;

    @Bind(R.id.detail_register_time_tv)
    TextView mDetailRegisterTimeTv;

    @Bind(R.id.detail_place_tv)
    TextView mDetailPlaceTv;

    @Bind(R.id.detail_category_tv)
    TextView mDetailCategoryTv;

    @Bind(R.id.request_tv)
    TextView mRequestTv;

    @Bind(R.id.detail_description_tv)
    TextView mDetailDescriptionTv;

    @Bind(R.id.detail_price_tv)
    TextView mDetailPriceTv;

    @Bind(R.id.pagesContainer)
    LinearLayout pagesContainer;

    @Bind(R.id.bottomBarLayBtn)
    RelativeLayout bottomBarLayBtn;

    @Bind(R.id.callSmsBottomBarLayBtn)
    RelativeLayout callSmsBottomBarLayBtn;

    @Bind(R.id.report_lay)
    RelativeLayout mReportLay;

    @Bind(R.id.first_right_icon_ic)
    ImageView mFirstRightIcon;

    @Bind(R.id.request_progressView)
    ProgressView mRequestProgressView;

    View.OnClickListener addToWishList;
    View.OnClickListener removeFromWishList;
    private Gift gift;
    private MyPageIndicator mIndicator;
    private ApiRequest apiRequest;
    private Places allPlaces;
    private String giftId;
    private String giftStatus;
    Context mContext;

    public static Intent createIntent(Gift gift) {
        Intent intent = new Intent(AppController.getAppContext(), GiftDetailActivity.class);
        intent.putExtra(Constants.GIFT, gift);
        return intent;
    }

    public static Intent createIntent(String giftId) {
        Intent intent = new Intent(AppController.getAppContext(), GiftDetailActivity.class);
        intent.putExtra(Constants.GIFT_ID, giftId);
        return intent;
    }

    private void extractDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            giftId = bundle.getString(Constants.GIFT_ID);
            gift = (Gift) bundle.get(Constants.GIFT);
            if (gift!=null) {
                setInfo();
                giftId = gift.giftId;
            }
        }
    }

    private void setInfo() {
        mToolbarTitleTv.setText(gift.title);
        mDetailDescriptionTv.setText(gift.description);
        mDetailTitleTv.setText(gift.title);
        mDetailRegisterTimeTv.setText(gift.createDateTime);
        mDetailPriceTv.setText(gift.price);

        String place = findPlaceRecursion(gift.locationId);
        mDetailPlaceTv.setText(place);

        mDetailCategoryTv.setText(gift.category);

        setupViewPager(viewPager);
        if (gift.giftImages.size()>1) {
            mIndicator = new MyPageIndicator(this, pagesContainer, viewPager, R.drawable.indicator_circle);
            mIndicator.setPageCount(gift.giftImages != null ? gift.giftImages.size() : 0);
            mIndicator.show();
        }else {
            pagesContainer.setVisibility(View.GONE);
        }
    }

    private void readFromJson() {
        String json = ReadJsonFile.loadJSONFromAsset(this);
        Gson gson = new Gson();
        allPlaces = gson.fromJson(json, Places.class);
    }

    private String findPlaceRecursion(String id){
        for (Place thisPlace : allPlaces.getPlaces()) {
            if (thisPlace.id.equals(id)) {
                String parent = findPlaceRecursion(thisPlace.container_id);
                if (!parent.equals("")) {
                    return thisPlace.name + ", " + parent;
                }else
                    return thisPlace.name;
            }
        }
        return "";
    }

    private void setListeners() {
        mFirstRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addToWishList = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppController.getStoredString(Constants.Authorization)!=null) {
                    mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark);
                    mBookmarkIc.setOnClickListener(removeFromWishList);
                    apiRequest.bookmark(giftId);
                }else {
                    Toasti.showS("برای افزودن به علاقه‌مندی باید وارد شوید.");
                }
            }
        };

        removeFromWishList = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark_outline);
                mBookmarkIc.setOnClickListener(addToWishList);
                apiRequest.bookmark(giftId);

            }
        };

        mBookmarkIc.setOnClickListener(addToWishList);

        mShareIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });

        mReportLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog.Builder builder = MaterialDialogBuilder.create(mContext);
                final MaterialDialog dialog = builder
                        .customView(R.layout.dialog_report_gift, false).show();

                RippleView yesBtnRipple = (RippleView) dialog.findViewById(R.id.yes_ripple_btn_cardview);
                yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {

                        Toasti.showS("yes");
                        dialog.dismiss();
                    }
                });

                RippleView noBtnRipple = (RippleView) dialog.findViewById(R.id.no_ripple_btn_cardview);
                noBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {

                        Toasti.showS("no");
                        dialog.dismiss();

                    }
                });

            }
        });
    }

    private void init() {
        ButterKnife.bind(this);
        mContext = this;

        readFromJson();
        apiRequest = new ApiRequest(this, this);

        extractDataFromBundle();
        if (giftId != null) {
            apiRequest.getGift(giftId);
        }
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
        if (response.body() instanceof RequestGiftOutput) {

            cancelMyRequest();
            Snackbari.showS(bottomBarLayBtn, "درخواست ارسال شد");

        }else if (response.body() instanceof Gift){

            gift = (Gift) response.body();
            setInfo();

            giftStatus = gift.status;

            if (gift.userId.equals(AppController.getStoredString(Constants.USER_ID))){
                setDeleteBtn(getResources().getString(R.string.delete_gift));
            }

            switch (gift.status){
                case GiftStatus.REJECTED_BY_ADMIN:
	                setDeleteBtn("هدیه شما پذیرفته نشد.");
                    break;

                case GiftStatus.PUBLISHED:
                    if (!gift.userId.equals(AppController.getStoredString(Constants.USER_ID))){
                       setRequestBtn();
                    }
                    break;

                case GiftStatus.DONATED_TO_ME:
                    callSmsBottomBarLayBtn.setVisibility(View.VISIBLE);
                    bottomBarLayBtn.setVisibility(View.GONE);
                    mCallBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String uri = "tel:" + "00000000000";
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(uri));
                            mContext.startActivity(intent);

                        }
                    });
                    mSmsBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            mContext.startActivity(
                                    new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.fromParts("sms", "00000000000", null)
                                    )
                            );
                        }
                    });
                    break;

                case GiftStatus.DONATED_TO_SOMEONE_ELSE:
                    callSmsBottomBarLayBtn.setVisibility(View.GONE);
                    bottomBarLayBtn.setVisibility(View.GONE);
                    mDetailTitleTv.setText(gift.title + "(این هدیه اهدا شده است) ");

                    break;

                case GiftStatus.I_SENT_MY_REQUEST_FOR_IT:
                    cancelMyRequest();
                    break;
                case GiftStatus.MY_REQUEST_REJECTED:
                    bottomBarLayBtn.setVisibility(View.VISIBLE);

	                mRequestTv.setText("درخواست شما رد شد");
                    break;
            }

            if (gift.bookmark){
                mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark);
                mBookmarkIc.setOnClickListener(removeFromWishList);
            }else {
                mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark_outline);
                mBookmarkIc.setOnClickListener(addToWishList);
            }
            mBookmarkIc.setVisibility(View.VISIBLE);

        }else if(response.body() instanceof ResponseBody){

            if (gift.userId.equals(AppController.getStoredString(Constants.USER_ID))){
                Toasti.showS("هدیه شما با موفقیت حذف شد");
                finish();
            }else {
                setRequestBtn();
            }
        }
    }

    private void setDeleteBtn(String btnText) {
        bottomBarLayBtn.setVisibility(View.VISIBLE);
        mRequestTv.setText(btnText);

        bottomBarLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialDialog.Builder builder = MaterialDialogBuilder.create(mContext);
                final MaterialDialog dialog = builder
                        .customView(R.layout.dialog_delete_gift, false).show();

                RippleView yesBtnRipple = (RippleView) dialog.findViewById(R.id.yes_ripple_btn_cardview);
                yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        mRequestProgressView.setVisibility(View.VISIBLE);
                        mRequestTv.setText("");
                        apiRequest.deleteGift(giftId);
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
            }
        });
    }

    private void setRequestBtn() {
        bottomBarLayBtn.setVisibility(View.VISIBLE);

        mRequestTv.setText("درخواست");
        bottomBarLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppController.getStoredString(Constants.Authorization) != null) {
                    apiRequest.sendRequestGift(
                            new RequestGiftInput(
                                    gift.giftId
                            )
                    );
                    cancelMyRequest();
                } else {
                    Snackbari.showS(bottomBarLayBtn, "ابتدا لاگین شوید");
                }

            }
        });

    }

    private void cancelMyRequest() {
        bottomBarLayBtn.setVisibility(View.VISIBLE);
        mRequestTv.setText("لغو درخواست");
        bottomBarLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiRequest.deleteMyRequest(giftId);
                setRequestBtn();
            }
        });
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
            return (gift.giftImages != null ? gift.giftImages.size() : 0);
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