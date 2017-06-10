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
import ir.hamed_gh.divaremehrabani.constants.RequestName;
import ir.hamed_gh.divaremehrabani.customviews.customindicator.MyPageIndicator;
import ir.hamed_gh.divaremehrabani.customviews.edit_text.EditTextIranSans;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.MaterialDialogBuilder;
import ir.hamed_gh.divaremehrabani.helper.ReadJsonFile;
import ir.hamed_gh.divaremehrabani.helper.Snackbari;
import ir.hamed_gh.divaremehrabani.helper.Toasti;
import ir.hamed_gh.divaremehrabani.model.Place;
import ir.hamed_gh.divaremehrabani.model.Places;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.input.ReportInput;
import ir.hamed_gh.divaremehrabani.model.api.input.RequestGiftInput;
import ir.hamed_gh.divaremehrabani.model.api.output.RequestGiftOutput;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GiftDetailActivity extends AppCompatActivity implements ApiRequest.TagListener {

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

	@Bind(R.id.delete_tv)
	TextView mDeleteTv;

	@Bind(R.id.edit_tv)
	TextView mEditTv;

	@Bind(R.id.pagesContainer)
	LinearLayout pagesContainer;

	@Bind(R.id.request_lay)
	RelativeLayout mRequestLay;

	@Bind(R.id.contact_lay)
	RelativeLayout mContactLay;

	@Bind(R.id.edit_delete_lay)
	RelativeLayout mEditDeleteLay;

	@Bind(R.id.edit_lay)
	RelativeLayout mEditLay;

	@Bind(R.id.delete_lay)
	RelativeLayout mDeleteLay;

	@Bind(R.id.report_lay)
	RelativeLayout mReportLay;

	@Bind(R.id.first_right_icon_ic)
	ImageView mFirstRightIcon;

	@Bind(R.id.request_progressView)
	ProgressView mRequestProgressView;

	@Bind(R.id.edit_progressView)
	ProgressView mEditProgressView;

	@Bind(R.id.delete_progressView)
	ProgressView mDeleteProgressView;

	View.OnClickListener addToWishList;
	View.OnClickListener removeFromWishList;
	Context mContext;
	private Gift gift;
	private MyPageIndicator mIndicator;
	private ApiRequest apiRequest;
	private Places allPlaces;
	private String giftId;
	private String giftStatus;

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
			if (gift != null) {
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
		if (gift.giftImages.size() > 1) {
			mIndicator = new MyPageIndicator(this, pagesContainer, viewPager, R.drawable.indicator_circle);
			mIndicator.setPageCount(gift.giftImages != null ? gift.giftImages.size() : 0);
			mIndicator.show();
		} else {
			pagesContainer.setVisibility(View.GONE);
		}
	}

	private void readFromJson() {
		String json = ReadJsonFile.loadJSONFromAsset(this);
		Gson gson = new Gson();
		allPlaces = gson.fromJson(json, Places.class);
	}

	private String findPlaceRecursion(String id) {
		for (Place thisPlace : allPlaces.getPlaces()) {
			if (thisPlace.id.equals(id)) {
				String parent = findPlaceRecursion(thisPlace.container_id);
				if (!parent.equals("")) {
					return thisPlace.name + ", " + parent;
				} else
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

				if (AppController.getStoredString(Constants.Authorization) != null) {
					mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark);
					mBookmarkIc.setOnClickListener(removeFromWishList);
					apiRequest.bookmark(giftId);
				} else {
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

						String message = ((EditTextIranSans)dialog.findViewById(R.id.message_et)).getText().toString();
						if (message!=null && !message.equals("")) {
							ReportInput reportInput = new ReportInput();
							reportInput.giftId = gift.giftId;
							reportInput.message = message;
							apiRequest.reportGift(reportInput);
							dialog.dismiss();
						}else {
							Toasti.showS("لطفا توضیحاتی را ارائه دهید.");
						}
					}
				});

				RippleView noBtnRipple = (RippleView) dialog.findViewById(R.id.no_ripple_btn_cardview);
				noBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
					@Override
					public void onComplete(RippleView rippleView) {

//						Toasti.showS("no");
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

		setListeners();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (giftId != null) {
			apiRequest.getGift(giftId);
		}
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
	public void onResponse(Call call, Response response, String tag) {
		if (response.body() instanceof RequestGiftOutput) {

			setCancelRequestBtn();
			Snackbari.showS(mRequestLay, "درخواست ارسال شد");

		} else if (response.body() instanceof Gift) {

			gift = (Gift) response.body();
			setInfo();

			giftStatus = gift.status;

			if (gift.userId.equals(AppController.getStoredString(Constants.USER_ID))) {
				setEditDeleteBtn();
			}

			switch (gift.status) {
				case GiftStatus.REJECTED_BY_ADMIN:
					showRejectedDialog();
					mDetailTitleTv.setText(" (هدیه شما پذیرفته نشد)" + gift.title);

					break;

				case GiftStatus.PUBLISHED:
					if (!gift.userId.equals(AppController.getStoredString(Constants.USER_ID))) {
						setRequestBtn();
					}
					break;

				case GiftStatus.DONATED_TO_ME:
					setCallSmsBtn();
					break;

				case GiftStatus.DONATED_TO_SOMEONE_ELSE:
					hideAllBottomBtns();
					mDetailTitleTv.setText(" (این هدیه اهدا شده است)" + gift.title);

					break;

				case GiftStatus.I_SENT_MY_REQUEST_FOR_IT:
					setCancelRequestBtn();
					break;

				case GiftStatus.MY_REQUEST_REJECTED:
					hideAllBottomBtns();
					mDetailTitleTv.setText(" (درخواست شما رد شد)" + gift.title);

					break;
			}

			if (gift.bookmark) {
				mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark);
				mBookmarkIc.setOnClickListener(removeFromWishList);
			} else {
				mBookmarkIc.setImageResource(R.mipmap.ic_action_action_bookmark_outline);
				mBookmarkIc.setOnClickListener(addToWishList);
			}
			mBookmarkIc.setVisibility(View.VISIBLE);

		} else if (response.body() instanceof ResponseBody) {

			if (tag.equals(RequestName.SendRequestGift)){
				setCancelRequestBtn();
			}else if (tag.equals(RequestName.DeleteGift)) {
				Toasti.showS("هدیه شما با موفقیت حذف شد");
				finish();
			}else if (tag.equals(RequestName.DeleteMyRequest)) {
				setRequestBtn();
			}else if (tag.equals(RequestName.Bookmark)){

			}else if (tag.equals(RequestName.ReportGift)){
				Toasti.showS("گزارش ارسال گردید");
			}
		}
	}

	private void hideAllBottomBtns(){
		mContactLay.setVisibility(View.GONE);
		mRequestLay.setVisibility(View.GONE);
		mEditDeleteLay.setVisibility(View.GONE);
	}

	private void showRejectedDialog() {

		MaterialDialog.Builder builder = MaterialDialogBuilder.create(mContext).customView(R.layout.dialog_simple_alert, false);

		final MaterialDialog dialog = builder.build();
		((TextView) dialog.findViewById(R.id.message_textview)).setText("هدیه شما توسط مدیریت برنامه پذیرفته نشده است.");

		RippleView noBtnRipple = (RippleView) dialog.findViewById(R.id.no_ripple_btn_cardview);
		noBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
			@Override
			public void onComplete(RippleView rippleView) {
				dialog.dismiss();
			}
		});

		dialog.show();

	}

	private void setCallSmsBtn(){
		mContactLay.setVisibility(View.VISIBLE);
		mRequestLay.setVisibility(View.GONE);
		mEditDeleteLay.setVisibility(View.GONE);

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
	}

	private void setEditDeleteBtn() {
		mEditDeleteLay.setVisibility(View.VISIBLE);
		mRequestLay.setVisibility(View.GONE);
		mContactLay.setVisibility(View.GONE);

		mDeleteLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				MaterialDialog.Builder builder = MaterialDialogBuilder.create(mContext).customView(R.layout.dialog_simple_yes_no, false);

				final MaterialDialog dialog = builder.build();
				((TextView) dialog.findViewById(R.id.message_textview)).setText("آیا از حذف این هدیه مطمئن هستید؟");

				RippleView yesBtnRipple = (RippleView) dialog.findViewById(R.id.yes_ripple_btn_cardview);
				yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
					@Override
					public void onComplete(RippleView rippleView) {

						mDeleteTv.setVisibility(View.INVISIBLE);
						mDeleteProgressView.setVisibility(View.VISIBLE);

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

				dialog.show();
			}
		});

		mEditLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				startActivity(new Intent(mContext, RegisterGiftActivity.class));
				startActivity(
						RegisterGiftActivity.createIntent(
								gift
						)
				);
			}
		});
	}

	private void setRequestBtn() {
		mRequestLay.setVisibility(View.VISIBLE);
		mEditDeleteLay.setVisibility(View.GONE);
		mContactLay.setVisibility(View.GONE);

		mRequestProgressView.setVisibility(View.INVISIBLE);
		mRequestTv.setText("درخواست");

		mRequestLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (AppController.getStoredString(Constants.Authorization) != null) {

					MaterialDialog.Builder builder = MaterialDialogBuilder.create(mContext).customView(R.layout.dialog_simple_yes_no, false);

					final MaterialDialog dialog = builder.build();
					((TextView) dialog.findViewById(R.id.message_textview)).setText("آیا از ارسال درخواست برای دریافت این هدیه مطمئن هستید؟");

					RippleView yesBtnRipple = (RippleView) dialog.findViewById(R.id.yes_ripple_btn_cardview);
					yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
						@Override
						public void onComplete(RippleView rippleView) {

							mRequestProgressView.setVisibility(View.VISIBLE);
							mRequestTv.setText("");

							apiRequest.sendRequestGift(
									new RequestGiftInput(
											gift.giftId
									)
							);
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

					dialog.show();


				} else {
					Snackbari.showS(mRequestLay, "ابتدا لاگین شوید");
				}

			}
		});

	}

	private void setCancelRequestBtn() {
		mRequestLay.setVisibility(View.VISIBLE);
		mEditDeleteLay.setVisibility(View.GONE);
		mContactLay.setVisibility(View.GONE);

		mRequestProgressView.setVisibility(View.INVISIBLE);
		mRequestTv.setText("لغو درخواست");

		mRequestLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				MaterialDialog.Builder builder = MaterialDialogBuilder.create(mContext).customView(R.layout.dialog_simple_yes_no, false);

				final MaterialDialog dialog = builder.build();
				((TextView) dialog.findViewById(R.id.message_textview)).setText("آیا از لغو درخواست خود برای دریافت این هدیه مطمئن هستید؟");

				RippleView yesBtnRipple = (RippleView) dialog.findViewById(R.id.yes_ripple_btn_cardview);
				yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
					@Override
					public void onComplete(RippleView rippleView) {

						mRequestProgressView.setVisibility(View.VISIBLE);
						mRequestTv.setText("");
						apiRequest.deleteMyRequest(giftId);

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

				dialog.show();

			}
		});
	}

	@Override
	public void onFailure(Call call, Throwable t, String tag) {

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