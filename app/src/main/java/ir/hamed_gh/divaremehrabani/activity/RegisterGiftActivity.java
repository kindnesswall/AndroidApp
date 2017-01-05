package ir.hamed_gh.divaremehrabani.activity;

import android.app.Activity;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.fragment.ChooseCategoryDialogFragment;
import ir.hamed_gh.divaremehrabani.helper.FileUtils;
import ir.hamed_gh.divaremehrabani.helper.ProgressRequestBody;
import ir.hamed_gh.divaremehrabani.helper.Toasti;
import ir.hamed_gh.divaremehrabani.model.api.UploadFileOutput;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterGiftActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {

	@Bind(R.id.main_toolbar)
	Toolbar mToolbar;

	@Bind(R.id.toolbar_title_textView)
	TextView mToolbarTitleTextView;

	@Bind(R.id.toolbar_send_btn_tv)
	TextView mToolbarSendBtnTv;

	@Bind(R.id.choose_category_btn)
	RelativeLayout mChooseCategoryBtn;

	@Bind(R.id.toolbar_back_iv)
	ImageView mBackBtn;

	@Bind(R.id.toolbar_cancel_iv)
	ImageView mCancelBtn;

	@Bind(R.id.choose_image_btn)
	RelativeLayout mChooseImageBtn;

	@Bind(R.id.gift_imageview)
	ImageView giftImageview;

	private Context context;
	private Uri imageUri;

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
		mToolbarSendBtnTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toasti.showS("ارسال");
			}
		});

		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		mCancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
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


//		File file = new File(filePath);
//		RequestBody requestBody = new ProgressRequestBody(
//				MediaType.parse("application/octet-stream"),
//				file,
//				this);

//		(new ApiRequest(context,this)).uploadFile(requestBody);

//		Call<ResponseBody> result = AppController.service.uploadGiftImage(
//				AppController.getStoredString(Constants.TOKEN),
//				"me.jpg",
//				requestBody
//		);

		File file = new File(filePath);
		RequestBody requestBody = new ProgressRequestBody(
				MediaType.parse("application/octet-stream"),
				file,
				this);

		Call<ResponseBody> call = AppController.service.uploadFile(
				AppController.getStoredString(Constants.TOKEN),
				"me.jpg",
				requestBody
		);

		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call,
			                       Response<ResponseBody> response) {
				if (!response.isSuccessful()) {
					Toasti.showS("خطا در سرور");
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
				if (uploadFileOutput.url!=null) {
					Log.d("Upload", "onResponse: " + uploadFileOutput.url);
					ImageLoader.getInstance().displayImage(uploadFileOutput.url, giftImageview);
				}

//                switch (uploadAvatarOutput.status) {
//                    case Constants.STATUS_INTERNAL_ERORR:
//                        Toasti.showS(mContext, "خطا در سرور");
//                        break;
//                    case Constants.STATUS_DONE:
//                        break;
//                    case Constants.STATUS_WRONG_TOKEN:
//                        //TODO logout
//                        Toasti.showS(mContext, "wrong token");
//                        break;
//                }

			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Log.e("Upload error:", t.getMessage());
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

		if (resultCode == Activity.RESULT_OK) {
			imageUri = getPickImageResultUri(data);

			String path = FileUtils.getPath(this, imageUri);
			if (path != null) {
				uploadFile(path);
			} else {
				Toasti.showS(getString(R.string.could_not_find_path));
			}

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_gift);
		ButterKnife.bind(this);

		context = this;

		settingToolbar();
		setListeners();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onProgressUpdate(int percentage) {
		Log.d("Upload", "onProgressUpdate: " + percentage);
	}

	@Override
	public void onError() {

	}

	@Override
	public void onFinish() {

	}
}