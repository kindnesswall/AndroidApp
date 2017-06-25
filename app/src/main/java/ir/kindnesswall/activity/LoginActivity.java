package ir.kindnesswall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.customviews.edit_text.EditTextIranSans;
import ir.kindnesswall.customviews.textviews.TextViewIranSansRegular;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.helper.DeviceInfo;
import ir.kindnesswall.helper.NumberTranslator;
import ir.kindnesswall.helper.Snackbari;
import ir.kindnesswall.helper.Toasti;
import ir.kindnesswall.model.api.input.SetDeviceInput;
import ir.kindnesswall.model.api.output.RegisterOutput;
import ir.kindnesswall.model.api.output.TokenOutput;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ApiRequest.Listener {

	@Bind(R.id.main_toolbar)
	Toolbar mToolbar;

	@Bind(R.id.toolbar_title_textView)
	TextView mToolbarTitleTextView;

	@Bind(R.id.toolbar_back_iv)
	ImageView mBackBtn;

	@Bind(R.id.second_btn_lay)
	RelativeLayout secondBtnLay;

	@Bind(R.id.second_btn_tv)
	TextView secondBtnTv;

	@Bind(R.id.login_get_verification_btn)
	RelativeLayout loginGetVerificationBtn;

	@Bind(R.id.phone_confirimation_code_et)
	EditTextIranSans phoneConfirimationCodeEt;

	@Bind(R.id.login_get_verification_tv)
	TextViewIranSansRegular login_get_verification_tv;

	@Bind(R.id.progressView)
	ProgressView progressView;

	View.OnClickListener enterPhoneNumber;
	View.OnClickListener enterVerificationCodeListener;
	String regexStr = "^[0-9]*$";
	private Context context;
	private ApiRequest apiRequest;
	private View.OnClickListener notRecievedCode;
	private View.OnClickListener enterVerificationCodeState;

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), LoginActivity.class);
		return intent;
	}

	private void settingToolbar() {
		mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		setSupportActionBar(mToolbar);
		try {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		} catch (Exception e) {

		}
		mToolbarTitleTextView.setText("ورود به دیوار مهربانی");
	}

	private void setListeners() {
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		enterVerificationCodeListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String confirmationCode = NumberTranslator.toEnglish(phoneConfirimationCodeEt.getText().toString());

				if (!confirmationCode.trim().matches(regexStr)) {
					Snackbari.showS(
							mBackBtn,
							"کد وارد شده صحیح نمی‌باشد"
					);

					return;
				}
				apiRequest.login(confirmationCode, DeviceInfo.getDeviceID(context));

				progressView.setVisibility(View.VISIBLE);
				login_get_verification_tv.setVisibility(View.INVISIBLE);

				secondBtnLay.setOnClickListener(notRecievedCode);

			}
		};

		enterPhoneNumber = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String phoneNumber = NumberTranslator.toEnglish(phoneConfirimationCodeEt.getText().toString());

				if (phoneNumber.length() != 11 || !phoneNumber.startsWith("09") || !phoneNumber.trim().matches(regexStr)) {
					Snackbari.showS(
							mBackBtn,
							"شماره تلفن وارد شده صحیح نمی‌باشد"
					);

					return;
				}

				apiRequest.register(phoneNumber);

				progressView.setVisibility(View.VISIBLE);
				login_get_verification_tv.setVisibility(View.INVISIBLE);

				secondBtnLay.setOnClickListener(enterVerificationCodeState);

			}
		};


		notRecievedCode = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AppController.storeString(
						Constants.TELEPHONE, null
				);

				enterTelephoneNumber();
			}
		};

		enterVerificationCodeState = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phoneNumber = NumberTranslator.toEnglish(phoneConfirimationCodeEt.getText().toString());

				if (phoneNumber.length() != 11 || !phoneNumber.startsWith("09") || !phoneNumber.trim().matches(regexStr)) {
					Snackbari.showS(
							mBackBtn,
							"شماره تلفن وارد شده صحیح نمی‌باشد"
					);

					return;
				}

				AppController.storeString(
						Constants.TELEPHONE, phoneNumber
				);

				enterVerificationCode();
			}
		};

		if (AppController.getStoredString(Constants.TELEPHONE) == null) {
			enterTelephoneNumber();
		} else {
			enterVerificationCode();
		}
	}

	private void init() {
		context = this;
		apiRequest = new ApiRequest(context, this);
		if (!AppController.getStoredBoolean(Constants.CALLED_SETDEVICE_BEFORE, false)){
			String deviceID = DeviceInfo.getDeviceID(this);
			Log.d("deviceID", deviceID);
			apiRequest.setDevice(
					new SetDeviceInput(
							AppController.getStoredString(Constants.FIREBASE_REG_TOKEN),
							deviceID
					)
			);
		}
		phoneConfirimationCodeEt.setRawInputType(Configuration.KEYBOARD_QWERTY);
		settingToolbar();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);

		init();

		setListeners();
	}


	public void enterTelephoneNumber() {
		AppController.storeString(Constants.TELEPHONE, null);
		phoneConfirimationCodeEt.setHint(getString(R.string.hint_telephone_field));
		phoneConfirimationCodeEt.setText("");

		login_get_verification_tv.setText(getString(R.string.sign_up));
//		secondBtnLay.setVisibility(View.INVISIBLE);
		secondBtnTv.setText("ورود کد تایید");
		secondBtnLay.setOnClickListener(enterVerificationCodeState);

		loginGetVerificationBtn.setOnClickListener(enterPhoneNumber);
	}

	public void enterVerificationCode() {
		phoneConfirimationCodeEt.setHint(getString(R.string.field_hint_verification_code));
		phoneConfirimationCodeEt.setText("");

		login_get_verification_tv.setText(getString(R.string.sign_in));

//		secondBtnLay.setVisibility(View.VISIBLE);
		secondBtnTv.setText(getResources().getString(R.string.verification_not_received));
		secondBtnLay.setOnClickListener(notRecievedCode);

		loginGetVerificationBtn.setOnClickListener(enterVerificationCodeListener);
	}

	@Override
	public void onResponse(Call call, Response response) {
		progressView.setVisibility(View.INVISIBLE);
		login_get_verification_tv.setVisibility(View.VISIBLE);

		if (response.body() instanceof TokenOutput) {
			TokenOutput tokenOutput = (TokenOutput) response.body();
			AppController.storeString(
					Constants.Authorization,
					Constants.BEARER + " " + tokenOutput.access_token);

			AppController.storeString(
					Constants.USERNAME,
					tokenOutput.userName);

			AppController.storeString(
					Constants.USER_ID,
					tokenOutput.userId);

			Toasti.showS(
					"شما با شماره تلفن " +
							NumberTranslator.toPersian(AppController.getStoredString(Constants.TELEPHONE)) +
							" وارد شدید."
			);

			finish();
		} else if (response.body() instanceof RegisterOutput){
			String remainingSeconds = ((RegisterOutput)response.body()).remainingSeconds;

			if (remainingSeconds!=null && !remainingSeconds.equals("") ){
				// Check if no view has focus:
				View view = this.getCurrentFocus();
				if (view != null) {
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}

				Snackbari.showS(mToolbarTitleTextView,
						"لطفا "+
								remainingSeconds
								+" ثانیه دیگر امتحان کنید"
						);
			}else {
				enterVerificationCode();
			}
		}
	}

	@Override
	public void onFailure(Call call, Throwable t) {
		progressView.setVisibility(View.INVISIBLE);
		login_get_verification_tv.setVisibility(View.VISIBLE);
	}
}