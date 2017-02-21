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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.GiftGalleryAdapter;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.dialogfragment.ChooseCategoryDialogFragment;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.FileUtils;
import ir.hamed_gh.divaremehrabani.helper.ProgressRequestBody;
import ir.hamed_gh.divaremehrabani.helper.Toasti;
import ir.hamed_gh.divaremehrabani.interfaces.ChooseCategoryCallback;
import ir.hamed_gh.divaremehrabani.model.api.Category;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.output.UploadFileOutput;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterGiftActivity extends AppCompatActivity
        implements ProgressRequestBody.UploadCallbacks, ApiRequest.Listener, ChooseCategoryCallback {

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

    @Bind(R.id.toolbar_save_iv)
    ImageView mSaveBtn;

    @Bind(R.id.choose_image_btn)
    RelativeLayout mChooseImageBtn;

    @Bind(R.id.gift_imageview)
    ImageView giftImageview;

    @Bind(R.id.register_gift_btn)
    RelativeLayout mRegisterGiftBtn;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Context context;
    private Uri imageUri;
    private Gift myGift = new Gift();
    private GiftGalleryAdapter giftGalleryAdapter;

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
                AppController.storeBoolean(Constants.MY_GIFT_SAVED, false);
                finish();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMyGift();
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

        mRegisterGiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> giftImages = new ArrayList<String>();
                giftImages.add("http://awsm.ir/Upload/1822aaed-6a7c-4b77-9d63-6940e78633c3.jpg");
                giftImages.add("http://awsm.ir/Upload/6ce02f2a-46b9-48eb-b87e-2ede15497a3b.jpg");

                (new ApiRequest(context, RegisterGiftActivity.this)).registerGift(
                        new Gift(
                                "چقده عالی",
                                "نواب",
                                "نقاشی های رویایی",
                                "20000000",
                                "1",
                                "1",
                                giftImages
                        )
                );
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
                AppController.getStoredString(Constants.Authorization),
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
                if (uploadFileOutput.imageSrc != null) {
                    Log.d("Upload", "onResponse: " + uploadFileOutput.imageSrc);

                    Glide
                            .with(context)
                            .load(uploadFileOutput.imageSrc)
                            .centerCrop()
                            .placeholder(R.color.background)
                            .crossFade()
                            .into(giftImageview);

                    myGift.giftImages.add(uploadFileOutput.imageSrc);
                    giftGalleryAdapter.notifyDataSetChanged();

//					ImageLoader.getInstance().displayImage(uploadFileOutput.imageSrc, giftImageview);
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

    private void saveMyGift() {

        AppController.storeBoolean(Constants.MY_GIFT_SAVED, true);

        AppController.storeString(Constants.MY_GIFT_TITLE, myGift.title);
        AppController.storeString(Constants.MY_GIFT_PRICE, myGift.price);
        AppController.storeString(Constants.MY_GIFT_ADDRESS, myGift.address);
        AppController.storeString(Constants.MY_GIFT_DESCRIPTION, myGift.description);
        AppController.storeString(Constants.MY_GIFT_CATEGORY_ID, myGift.categoryId);
        AppController.storeString(Constants.MY_GIFT_LOCATION_ID, myGift.locationId);

        for (int i = 0; i < myGift.giftImages.size(); i++) {
            AppController.storeString(Constants.MY_GIFT_IMAGE + "_" + i, myGift.giftImages.get(i));
        }
        AppController.storeInt(Constants.MY_GIFT_IMAGE_NUMBER, myGift.giftImages.size());

    }

    private void loadMyGift() {
        myGift.title = AppController.getStoredString(Constants.MY_GIFT_TITLE) != null ?
                AppController.getStoredString(Constants.MY_GIFT_TITLE) : "";

        myGift.price = AppController.getStoredString(Constants.MY_GIFT_PRICE) != null ?
                AppController.getStoredString(Constants.MY_GIFT_PRICE) : "";

        myGift.address = AppController.getStoredString(Constants.MY_GIFT_ADDRESS) != null ?
                AppController.getStoredString(Constants.MY_GIFT_ADDRESS) : "";

        myGift.description = AppController.getStoredString(Constants.MY_GIFT_DESCRIPTION) != null ?
                AppController.getStoredString(Constants.MY_GIFT_DESCRIPTION) : "";

        myGift.categoryId = AppController.getStoredString(Constants.MY_GIFT_CATEGORY_ID) != null ?
                AppController.getStoredString(Constants.MY_GIFT_CATEGORY_ID) : "";

        myGift.locationId = AppController.getStoredString(Constants.MY_GIFT_LOCATION_ID) != null ?
                AppController.getStoredString(Constants.MY_GIFT_LOCATION_ID) : "";

        int numberOfMyImages = AppController.getStoredInt(Constants.MY_GIFT_IMAGE_NUMBER);
        for (int i = 0; i < numberOfMyImages; i++) {
            myGift.giftImages.add(AppController.getStoredString(Constants.MY_GIFT_IMAGE + "_" + i));
        }
    }

    private void init() {
        context = this;

        if (AppController.getStoredBoolean(Constants.MY_GIFT_SAVED, false)) {
            loadMyGift();
        }

        giftGalleryAdapter = new GiftGalleryAdapter(context, myGift.giftImages);
        mRecyclerView.setAdapter(giftGalleryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        settingToolbar();
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
    protected void onResume() {
        super.onResume();
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
        if (response.body() instanceof Gift) {
            Gift gift = (Gift) response.body();
            Toasti.showS(gift.title);
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }

    @Override
    public void onCategorySelected(Category category) {

    }
}