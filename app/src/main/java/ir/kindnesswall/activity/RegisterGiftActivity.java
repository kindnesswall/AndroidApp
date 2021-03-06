package ir.kindnesswall.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.ProgressView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.GiftGalleryAdapter;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.customviews.edit_text.EditTextIranSans;
import ir.kindnesswall.dialogfragment.ChooseCategoryDialogFragment;
import ir.kindnesswall.dialogfragment.ChoosePlaceDialogFragment;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.helper.FileUtils;
import ir.kindnesswall.helper.ProgressRequestBody;
import ir.kindnesswall.helper.ReadJsonFile;
import ir.kindnesswall.helper.Snackbari;
import ir.kindnesswall.helper.Toasti;
import ir.kindnesswall.interfaces.ChooseCategoryCallback;
import ir.kindnesswall.interfaces.ChoosePlaceCallback;
import ir.kindnesswall.interfaces.UpdateImageGallery;
import ir.kindnesswall.model.Place;
import ir.kindnesswall.model.Places;
import ir.kindnesswall.model.api.Category;
import ir.kindnesswall.model.api.Gift;
import ir.kindnesswall.model.api.output.UploadFileOutput;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterGiftActivity extends AppCompatActivity
		implements ProgressRequestBody.UploadCallbacks, ApiRequest.Listener, ChooseCategoryCallback, UpdateImageGallery, ChoosePlaceCallback {

	@Bind(R.id.main_toolbar)
	Toolbar mToolbar;

	@Bind(R.id.toolbar_title_textView)
	TextView mToolbarTitleTextView;

	@Bind(R.id.toolbar_send_btn_tv)
	TextView mToolbarSendBtnTv;

	@Bind(R.id.description_et)
	EditTextIranSans mDescriptionEt;

	@Bind(R.id.title_et)
	EditTextIranSans mTitleEt;

	@Bind(R.id.price_et)
	EditTextIranSans mPriceEt;

	@Bind(R.id.choose_category_btn)
	RelativeLayout mChooseCategoryBtn;

	@Bind(R.id.after_category_select_lay)
	RelativeLayout mAfterCategorySelectLay;

	@Bind(R.id.choose_city_btn)
	RelativeLayout mChooseCityBtn;

	@Bind(R.id.choose_region_btn)
	RelativeLayout mChooseRegionBtn;

	@Bind(R.id.toolbar_back_iv)
	ImageView mBackBtn;

	@Bind(R.id.toolbar_cancel_iv)
	ImageView mCancelBtn;

	@Bind(R.id.toolbar_save_iv)
	ImageView mSaveBtn;

	@Bind(R.id.upload_img_circular_progress)
	ProgressView mUploadImgCircularProgress;

	@Bind(R.id.send_progressView)
	ProgressView mSendProgressView;

	@Bind(R.id.choose_image_btn)
	RelativeLayout mChooseImageBtn;

	@Bind(R.id.choose_category_lay)
	RelativeLayout mChooseCategoryLay;

	@Bind(R.id.choose_category_tv)
	TextView mChooseCategoryTv;

	@Bind(R.id.choose_category_btn_txt)
	TextView mChooseCategoryBtnTxt;

	@Bind(R.id.choose_city_btn_txt)
	TextView mChooseCityBtnTxt;

	@Bind(R.id.choose_region_btn_txt)
	TextView mChooseRegionBtnTxt;

	@Bind(R.id.choose_image_txt)
	TextView mChooseImageTxt;

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.old_txt)
	TextView mOldTxt;

	@Bind(R.id.new_txt)
	TextView mNewTxt;

	@Bind(R.id.old_new_radiogr)
	RadioGroup old_new_radiogr;

	@Bind(R.id.old_radiobtn)
	RadioButton mOldRadiobtn;

	@Bind(R.id.new_radiobtn)
	RadioButton mNewRadiobtn;

	@Bind(R.id.charities_txt)
	TextView mCharitiesTxt;

	@Bind(R.id.benefactors_txt)
	TextView mBenefactorsTxt;

	@Bind(R.id.users_checkbox)
	CheckBox mUsersCheckbox;

	@Bind(R.id.benefactors_checkbox)
	CheckBox mBenefactorsCheckbox;

//    @Bind(R.id.cropImageView)
//    CropImageView cropImageView;

	@Bind(R.id.scrollView)
	ScrollView scrollView;

	private Context context;
	private Uri imageUri;
	private Gift myGift = new Gift();
	private GiftGalleryAdapter giftGalleryAdapter;
	private Place city;
	private Category category;
	private Place region;
	private boolean editVersion;
	private int CROP_ACTIVITY = 2;
	private boolean isNew;

	public static Intent createIntent(Gift gift) {
		Intent intent = new Intent(AppController.getAppContext(), RegisterGiftActivity.class);
		intent.putExtra(Constants.GIFT, gift);
		return intent;
	}

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), RegisterGiftActivity.class);
		return intent;
	}

	private boolean extractDataFromBundle() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			myGift = (Gift) bundle.get(Constants.GIFT);
			if (myGift != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onUpdateGallery() {
		if (myGift.giftImages.size() > 0) {
			mRecyclerView.setVisibility(View.VISIBLE);
		} else {
			mRecyclerView.setVisibility(View.GONE);
		}
	}

	private void settingToolbar() {
		mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		setSupportActionBar(mToolbar);
		try {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		} catch (Exception e) {

		}
//        mToolbarTitleTextView.setText("دیوار مهربانی");
	}

	void setListeners() {

		old_new_radiogr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				switch (checkedId){
					case R.id.new_radiobtn:
						isNew = true;
						break;
					case R.id.old_radiobtn:
						isNew = false;
						break;
				}
			}
		});


		mPriceEt.addTextChangedListener(new TextWatcher() {
			boolean isEdiging;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				mPriceEt.removeTextChangedListener(this);

				try {
					String originalString = s.toString();

					Long longval;
					if (originalString.contains(",")) {
						originalString = originalString.replaceAll(",", "");
					}
					longval = Long.parseLong(originalString);

					DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
					formatter.applyPattern("#,###,###,###");
					String formattedString = formatter.format(longval);

					//setting text after format to EditText
					mPriceEt.setText(formattedString);
					mPriceEt.setSelection(mPriceEt.getText().length());
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}

				mPriceEt.addTextChangedListener(this);
			}
		});


		mToolbarSendBtnTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mTitleEt.getText().toString().equals("")) {
					Snackbari.showS(mTitleEt, "عنوان را وارد نمایید");
					return;
				} else if (mPriceEt.getText().toString().equals("")) {
					Snackbari.showS(mTitleEt, "قیمت" +
							" را وارد نمایید");
					return;
				} else if (mDescriptionEt.getText().toString().equals("")) {
					Snackbari.showS(mTitleEt, "توضیحات را وارد نمایید");
					return;
				} else if (category == null) {
					Snackbari.showS(mTitleEt, "دسته‌بندی را وارد نمایید");
					return;
				} else if (city == null) {
					Snackbari.showS(mTitleEt, "محل را وارد نمایید");
					return;
				} else if (myGift.giftImages.size() == 0) {
					Snackbari.showS(mTitleEt, "حداقل یک عکس آپلود نمایید");
					return;
				}

				Gift tempGift = new Gift(
						mDescriptionEt.getText().toString(),
						region != null ? (getPlaceNameById(city.id) + ", " + getPlaceNameById(region.id)) : getPlaceNameById(city.id),
						mTitleEt.getText().toString(),
						mPriceEt.getText().toString().replace(",", ""),
						category.categoryId,
						city.id,
						(region == null ? "0" : region.id),
						isNew,
						myGift.giftImages
				);

				if (editVersion) {
					tempGift.giftId = myGift.giftId;
					(new ApiRequest(context, RegisterGiftActivity.this)).editGift(
							tempGift
					);
				} else {
					(new ApiRequest(context, RegisterGiftActivity.this)).registerGift(
							tempGift
					);
					AppController.deleteSavedGift();
				}
				mToolbarSendBtnTv.setVisibility(View.INVISIBLE);
				mSendProgressView.setVisibility(View.VISIBLE);
			}
		});

		mChooseRegionBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				chooseRegion();
			}
		});

		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!editVersion) {
					saveMyGift();
					Toasti.showS(getString(R.string.gift_info_saved));
				}
				onBackPressed();
			}
		});

		mCancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AppController.deleteSavedGift();
				finish();
			}
		});

		mSaveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				saveMyGift();
				Snackbari.showS(mTitleEt, getString(R.string.gift_info_saved));
			}
		});

		mChooseCategoryBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();
				ChooseCategoryDialogFragment chooseCategoryDF = new ChooseCategoryDialogFragment();
				chooseCategoryDF.show(fm, chooseCategoryDF.getClass().getName());
			}
		});

		mChooseImageBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivityForResult(getPickImageChooserIntent(), 200);
			}
		});

		mChooseCityBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FragmentManager fm = getSupportFragmentManager();
				ChoosePlaceDialogFragment choosePlaceDialogFragment = new ChoosePlaceDialogFragment();
				choosePlaceDialogFragment.show(fm, ChoosePlaceDialogFragment.class.getName());
			}
		});
	}

	/**
	 * Get URI to image received from capture by camera.
	 */
	private Uri getCaptureImageOutputUri() {
		Uri outputFileUri = null;
		File getImage = getExternalCacheDir();
		if (getImage != null) {
			outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
		}
		return outputFileUri;
	}

	/**
	 * Create a chooser intent to select the source to get image from.<br/>
	 * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
	 * All possible sources are added to the intent chooser.
	 */
	public Intent getPickImageChooserIntent() {

		// Determine Uri of camera image to save.
		Uri outputFileUri = getCaptureImageOutputUri();

		List<Intent> allIntents = new ArrayList<>();
		PackageManager packageManager = getPackageManager();

		// collect all camera intents
		Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for (ResolveInfo res : listCam) {
			Intent intent = new Intent(captureIntent);
			intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			intent.setPackage(res.activityInfo.packageName);
			if (outputFileUri != null) {
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			}
			allIntents.add(intent);
		}

		// collect all gallery intents
		Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
		galleryIntent.setType("image/*");
		List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
		for (ResolveInfo res : listGallery) {
			Intent intent = new Intent(galleryIntent);
			intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			intent.setPackage(res.activityInfo.packageName);
			allIntents.add(intent);
		}

		// the main intent is the last in the list so pickup the useless one
		Intent mainIntent = allIntents.get(allIntents.size() - 1);
		for (Intent intent : allIntents) {
			if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
				mainIntent = intent;
				break;
			}
		}
		allIntents.remove(mainIntent);

		// Create a chooser from the main intent
		Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

		// Add all other intents
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

		return chooserIntent;
	}

	private void uploadFile(String filePath) {


		File file = new File(filePath);
		RequestBody requestBody = new ProgressRequestBody(
				MediaType.parse("application/octet-stream"),
				file,
				this);

		Call<ResponseBody> call = AppController.longTimeoutService.uploadFile(
				AppController.getStoredString(Constants.Authorization),
				"me.jpg",
				requestBody
		);

		mUploadImgCircularProgress.setVisibility(View.VISIBLE);
		mChooseImageTxt.setVisibility(View.INVISIBLE);

		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call,
			                       Response<ResponseBody> response) {

				mUploadImgCircularProgress.setVisibility(View.INVISIBLE);
				mChooseImageTxt.setVisibility(View.VISIBLE);

				if (!response.isSuccessful()) {

					if (response.code() == 401) {
						AppController.clearInfo();
						startActivity(LoginActivity.createIntent());
					}

					mUploadImgCircularProgress.setVisibility(View.INVISIBLE);
					mChooseImageTxt.setVisibility(View.VISIBLE);

					return;
				}

				Log.v("Upload", "success");
				UploadFileOutput uploadFileOutput = new UploadFileOutput();
				try {
					uploadFileOutput =
							new Gson().fromJson(response.body().string(), UploadFileOutput.class);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (uploadFileOutput.imageSrc != null) {
					Log.d("Upload", "onResponse: " + uploadFileOutput.imageSrc);

					myGift.giftImages.add(uploadFileOutput.imageSrc);
					giftGalleryAdapter.notifyDataSetChanged();
					onUpdateGallery();

				}

			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Log.e("Upload error:", t.getMessage());
				Toasti.showL("Msg: " + t.getMessage() + " | Cause: " + t.getCause());

				mUploadImgCircularProgress.setVisibility(View.INVISIBLE);
				mChooseImageTxt.setVisibility(View.VISIBLE);
			}
		});

	}

	public Uri getPickImageResultUri(Intent data) {
		boolean isCamera = true;
		if (data != null && data.getData() != null) {
			String action = data.getAction();
			isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
		}
		return isCamera ? getCaptureImageOutputUri() : data.getData();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
			final Uri resultUri = UCrop.getOutput(data);

			String path = FileUtils.getPath(this, resultUri);
			if (path != null) {
				uploadFile(path);
			} else {
				Toasti.showS(getString(R.string.could_not_find_path));
			}
		} else if (resultCode == UCrop.RESULT_ERROR) {
			Log.d("RESULT_ERROR", "onActivityResult: " + UCrop.getError(data));
			final Throwable cropError = UCrop.getError(data);
		} else if (resultCode == RESULT_OK) {
			imageUri = getPickImageResultUri(data);

			UCrop.Options options = new UCrop.Options();
			options.setToolbarTitle("ویرایش تصویر");
			options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
			options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

			String destinationFileName = "temp.jpg";
			UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), destinationFileName)))
					.withAspectRatio(1, 1)
					.withOptions(options)
					.withMaxResultSize(500, 500)
					.start(RegisterGiftActivity.this);
		}

	}

	private void saveMyGift() {
		AppController.storeBoolean(Constants.MY_GIFT_SAVED, true);

		AppController.storeString(Constants.MY_GIFT_TITLE, mTitleEt.getText().toString());
		AppController.storeBoolean(Constants.MY_GIFT_IS_NEW, isNew);

		AppController.storeString(Constants.MY_GIFT_PRICE, myGift.price);
		AppController.storeString(Constants.MY_GIFT_ADDRESS, myGift.address);
		AppController.storeString(Constants.MY_GIFT_DESCRIPTION, mDescriptionEt.getText().toString());
		AppController.storeString(Constants.MY_GIFT_PRICE, mPriceEt.getText().toString());

		AppController.storeString(Constants.MY_GIFT_CATEGORY_ID, myGift.categoryId);
		AppController.storeString(Constants.MY_GIFT_CATEGORY_NAME, myGift.category);

		if (city != null) {
			AppController.storeString(Constants.MY_GIFT_LOCATION_ID, city.id);
			AppController.storeString(Constants.MY_GIFT_LOCATION_NAME, city.name);
		}

		if (region != null) {
			AppController.storeString(Constants.MY_GIFT_REGION_ID, region.id);
			AppController.storeString(Constants.MY_GIFT_REGION_NAME, region.name);
		}

		for (int i = 0; i < myGift.giftImages.size(); i++) {
			AppController.storeString(Constants.MY_GIFT_IMAGE + "_" + i, myGift.giftImages.get(i));
		}
		AppController.storeInt(Constants.MY_GIFT_IMAGE_NUMBER, myGift.giftImages.size());
	}

	private void setInfo() {

		mTitleEt.setText(myGift.title);
		mPriceEt.setText(myGift.price);
		mDescriptionEt.setText(myGift.description);

		setRadioBtn();

		category = new Category();
		category.categoryId = myGift.categoryId;
		category.title = myGift.category;
		changeUIAfterCategorySelect();

		if (myGift.locationId != null && !myGift.locationId.equals("")) {
			city = new Place();
			city.id = myGift.locationId;

			city.name = getPlaceNameById(myGift.locationId);
//			if (myGift.location!=null && !myGift.location.equals("")) {
			mChooseCityBtnTxt.setText(getPlaceNameById(myGift.locationId));
//			}

			if (myGift.regionId != null && !myGift.regionId.equals("")) {
				region = new Place();
				region.id = myGift.regionId;

				region.name = getPlaceNameById(myGift.regionId);
//            if (myGift.region != null && !myGift.region.equals("")) {
				mChooseRegionBtnTxt.setText(getPlaceNameById(myGift.regionId));
//            }
			}

			findCityRegion();
		}

		giftGalleryAdapter = new GiftGalleryAdapter(this, myGift.giftImages);
		mRecyclerView.setAdapter(giftGalleryAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

		giftGalleryAdapter.notifyDataSetChanged();
		onUpdateGallery();

	}

	private void loadMyGiftFromPreference() {
		myGift.title = AppController.getStoredString(Constants.MY_GIFT_TITLE) != null ?
				AppController.getStoredString(Constants.MY_GIFT_TITLE) : "";

		myGift.isNew = AppController.getStoredBoolean(Constants.MY_GIFT_IS_NEW, false);
		setRadioBtn();

		myGift.price = AppController.getStoredString(Constants.MY_GIFT_PRICE) != null ?
				AppController.getStoredString(Constants.MY_GIFT_PRICE) : "";

		myGift.address = AppController.getStoredString(Constants.MY_GIFT_ADDRESS) != null ?
				AppController.getStoredString(Constants.MY_GIFT_ADDRESS) : "";

		myGift.description = AppController.getStoredString(Constants.MY_GIFT_DESCRIPTION) != null ?
				AppController.getStoredString(Constants.MY_GIFT_DESCRIPTION) : "";


		myGift.categoryId = AppController.getStoredString(Constants.MY_GIFT_CATEGORY_ID) != null ?
				AppController.getStoredString(Constants.MY_GIFT_CATEGORY_ID) : "";

		myGift.category = AppController.getStoredString(Constants.MY_GIFT_CATEGORY_NAME) != null ?
				AppController.getStoredString(Constants.MY_GIFT_CATEGORY_NAME) : "";

		myGift.locationId = AppController.getStoredString(Constants.MY_GIFT_LOCATION_ID) != null ?
				AppController.getStoredString(Constants.MY_GIFT_LOCATION_ID) : "";
		if (myGift.locationId != null) {
			myGift.location = AppController.getStoredString(Constants.MY_GIFT_LOCATION_NAME) != null ?
					AppController.getStoredString(Constants.MY_GIFT_LOCATION_NAME) : "";
		}

		myGift.regionId = AppController.getStoredString(Constants.MY_GIFT_REGION_ID) != null ?
				AppController.getStoredString(Constants.MY_GIFT_REGION_ID) : "";
		if (myGift.regionId != null) {
			myGift.region = AppController.getStoredString(Constants.MY_GIFT_REGION_NAME) != null ?
					AppController.getStoredString(Constants.MY_GIFT_REGION_NAME) : "";
		}

		int numberOfMyImages = AppController.getStoredInt(Constants.MY_GIFT_IMAGE_NUMBER);
		for (int i = 0; i < numberOfMyImages; i++) {
			myGift.giftImages.add(AppController.getStoredString(Constants.MY_GIFT_IMAGE + "_" + i));
		}
	}

	private void setRadioBtn() {
		if (myGift.isNew){
			mNewRadiobtn.setChecked(true);
		}else {
			mOldRadiobtn.setChecked(true);
		}
	}

	private void init() {
		context = this;

		giftGalleryAdapter = new GiftGalleryAdapter(this, myGift.giftImages);
		mRecyclerView.setAdapter(giftGalleryAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

		mTitleEt.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		mTitleEt.setInputType(InputType.TYPE_CLASS_TEXT);

		editVersion = extractDataFromBundle();
		if (editVersion) {
			setInfo();
			mSaveBtn.setVisibility(View.INVISIBLE);
			mCancelBtn.setVisibility(View.INVISIBLE);
			mToolbarSendBtnTv.setText("ویرایش");
		} else if (AppController.getStoredBoolean(Constants.MY_GIFT_SAVED, false)) {
			loadMyGiftFromPreference();
			setInfo();
		}

		settingToolbar();

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
		);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_gift);
		ButterKnife.bind(this);

		init();
		setListeners();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onProgressUpdate(int percentage) {
		Log.d("Upload", "onProgressUpdate: " + percentage);
	}

	@Override
	public void onError() {
		Log.d("onError", "onProgressUpdate: ");

	}

	@Override
	public void onFinish() {
		Log.d("onFinish", "onProgressUpdate: ");

	}

	@Override
	public void onResponse(Call call, Response response) {
		if (response.body() instanceof ResponseBody) {
			Toasti.showS("هدیه شما با موفقیت ویرایش شد.");
			finish();
		}
		if (response.body() instanceof Gift) {
			Gift gift = (Gift) response.body();
			Toasti.showS("هدیه شما با موفقیت ثبت شد.");
			finish();
		}
	}

	@Override
	public void onFailure(Call call, Throwable t) {
		mToolbarSendBtnTv.setVisibility(View.VISIBLE);
		mSendProgressView.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onCategorySelected(Category category) {
		this.category = category;
		myGift.categoryId = category.categoryId;
		myGift.category = category.title;

		changeUIAfterCategorySelect();
	}

	private void changeUIAfterCategorySelect() {
//		mChooseCategoryLay.setBackgroundColor(getResources().getColor(R.color.white));
//		mChooseCategoryTv.setVisibility(View.GONE);
		if (!category.title.equals("")) {
			mChooseCategoryBtnTxt.setText(category.title);
		}
		mAfterCategorySelectLay.setVisibility(View.VISIBLE);
	}

	@Override
	public void onCitySelected(Place city) {
		this.city = city;
		mChooseCityBtnTxt.setText(city.name);
		myGift.location = city.name;
		myGift.locationId = city.id;
		mChooseRegionBtnTxt.setText("انتخاب منطقه");
		findCityRegion();
		switch (mChooseRegionBtn.getVisibility()){
			case View.VISIBLE:
				chooseRegion();
				break;
		}
	}

	private void chooseRegion() {
		FragmentManager fm = getSupportFragmentManager();
		ChoosePlaceDialogFragment choosePlaceDialogFragment = ChoosePlaceDialogFragment.newInstance(city.id);
		choosePlaceDialogFragment.show(fm, ChoosePlaceDialogFragment.class.getName());
	}

	@Override
	public void onRegionSelected(Place region) {
		this.region = region;
		mChooseRegionBtnTxt.setText(region.name);
		myGift.location = region.name;
		myGift.locationId = region.id;
	}

	private String getPlaceNameById(String id) {
		String json = ReadJsonFile.loadJSONFromAsset(this);

		Gson gson = new Gson();

		Places allPlaces = gson.fromJson(json, Places.class);

		for (Place thisPlace : allPlaces.getPlaces()) {
			if (thisPlace.id.equals(id)) {
				return thisPlace.name;
			}
		}

		return "";
	}

	private void findCityRegion() {
		String json = ReadJsonFile.loadJSONFromAsset(this);

		Gson gson = new Gson();

		Places allPlaces = gson.fromJson(json, Places.class);

		Places level3 = new Places();
		level3.setPlaces(new ArrayList<Place>());

		for (Place thisPlace : allPlaces.getPlaces()) {
			if (thisPlace.level.equals("place3") && thisPlace.container_id.equals(city.id)) {
				level3.addPlace(thisPlace);
			}
		}

		Places level4 = new Places();
		level4.setPlaces(new ArrayList<Place>());

		for (Place thisPlace : allPlaces.getPlaces()) {
			if (thisPlace.level.equals("place4")) {
				for (Place l3 : level3.getPlaces()) {
					if (thisPlace.container_id.equals(l3.id)) {
						level4.addPlace(thisPlace);
					}
				}
			}
		}
		if (level4.getPlaces().size() > 0) {
			mChooseRegionBtn.setVisibility(View.VISIBLE);
		} else {
			mChooseRegionBtn.setVisibility(View.INVISIBLE);
		}
	}
}