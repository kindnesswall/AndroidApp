package ir.kindnesswall.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.customviews.edit_text.EditTextIranSans;
import ir.kindnesswall.customviews.textviews.TextViewIranSansRegular;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.helper.DeviceInfo;
import ir.kindnesswall.helper.NumberTranslator;
import ir.kindnesswall.helper.Snackbari;
import ir.kindnesswall.model.api.input.SetDeviceInput;
import ir.kindnesswall.model.api.output.RegisterOutput;
import ir.kindnesswall.model.api.output.TokenOutput;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static ir.kindnesswall.helper.Toasti.showS;

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
	IntentFilter filter;
	private Context context;
	private ApiRequest apiRequest;
	private View.OnClickListener notRecievedCode;
	private View.OnClickListener enterVerificationCodeState;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
//			Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT).show();

			if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
				Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
				SmsMessage[] msgs = null;
				String msg_from;
				if (bundle != null) {
					//---retrieve the SMS message received---
					try {
						Object[] pdus = (Object[]) bundle.get("pdus");
						msgs = new SmsMessage[pdus.length];
						for (int i = 0; i < msgs.length; i++) {
							msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
							msg_from = msgs[i].getOriginatingAddress();
							String msgBody = msgs[i].getMessageBody();
							Log.d("", "onReceive: " + msg_from);
							Log.d("", "onReceive: " + msgBody);

							if (AppController.getStoredString(Constants.SMS_CENTER)!=null){
								if (msg_from.equals(AppController.getStoredString(Constants.SMS_CENTER))) {
//								if (msg_from.equals("030002189")) {
									Log.d("", "onReceive: " + msgBody);
//									msgBody.substring(msgBody.length()-6);
									loginWithCode(msgBody.substring(msgBody.length()-6));
								}
							}

						}
					} catch (Exception e) {
//                            Log.d("Exception caught",e.getMessage());
					}
				}
			}

		}
	};

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

	private void loginWithCode(String confirmationCode) {

		phoneConfirimationCodeEt.setText(confirmationCode);
		if (!confirmationCode.trim().matches(regexStr)) {
			closeKeyboard();
			Snackbari.showS(
					mBackBtn,
					"کد وارد شده صحیح نمی‌باشد"
			);

			return;
		}
//		AppController.storeString(Constants.FIREBASE_REG_TOKEN, "256");
		if (AppController.getStoredString(Constants.FIREBASE_REG_TOKEN)==null){
			closeKeyboard();
			Snackbari.showL(
					mBackBtn,
					"امکان لاگین وجود ندارد. لطفا با ما تماس بگیرید"
			);

			return;
		}

		if (!AppController.getStoredBoolean(Constants.CALLED_SETDEVICE_BEFORE, false)) {

			String deviceID = DeviceInfo.getDeviceID(this);
			Log.d("deviceID", deviceID);
			apiRequest.setDevice(
					new SetDeviceInput(
							AppController.getStoredString(Constants.FIREBASE_REG_TOKEN),
							deviceID
					)
			);

			closeKeyboard();
			Snackbari.showL(
					mBackBtn,
					"امکان لاگین وجود ندارد. لطفا چند دقیقه بعد مجدد تلاش کنید."
			);
			return;
		}

		apiRequest.login(confirmationCode, DeviceInfo.getDeviceID(context));

		progressView.setVisibility(View.VISIBLE);
		login_get_verification_tv.setVisibility(View.INVISIBLE);

		secondBtnLay.setOnClickListener(notRecievedCode);
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

				loginWithCode(confirmationCode);

			}
		};

		enterPhoneNumber = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String phoneNumber = NumberTranslator.toEnglish(phoneConfirimationCodeEt.getText().toString());

				if (phoneNumber.length() != 11 || !phoneNumber.startsWith("09") || !phoneNumber.trim().matches(regexStr)) {

					closeKeyboard();
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

					closeKeyboard();

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
		if (!AppController.getStoredBoolean(Constants.CALLED_SETDEVICE_BEFORE, false) &&
				AppController.getStoredString(Constants.FIREBASE_REG_TOKEN) != null) {

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

		filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
	}

	@Override
	protected void onResume() {
		registerReceiver(receiver, filter);
		super.onResume();
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

			showS(
					"شما با شماره تلفن " +
							NumberTranslator.toPersian(AppController.getStoredString(Constants.TELEPHONE)) +
							" وارد شدید."
			);

			Intent intent = new Intent(getApplicationContext(), BottomBarActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
//
//			finish();
		} else if (response.body() instanceof RegisterOutput) {
			String remainingSeconds = ((RegisterOutput) response.body()).remainingSeconds;

			if (remainingSeconds != null && !remainingSeconds.equals("")) {
				closeKeyboard();

				Snackbari.showS(mToolbarTitleTextView,
						"لطفا " +
								remainingSeconds
								+ " ثانیه دیگر امتحان کنید"
				);
			} else {
				enterVerificationCode();
			}

		}else if (response.body() instanceof ResponseBody){
			AppController.storeBoolean(Constants.CALLED_SETDEVICE_BEFORE, true);
		}
	}

	private void closeKeyboard() {
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
		);
	}

	@Override
	public void onFailure(Call call, Throwable t) {

		if (t.getMessage().equals("incorrect_user_pass")) {
//			.showS("کد تایید اشتباه است");
			closeKeyboard();

			Snackbari.showL(mToolbar, "کد تایید اشتباه است");
		}
		progressView.setVisibility(View.INVISIBLE);
		login_get_verification_tv.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onStop() {
		unregisterReceiver(receiver);
		super.onStop();
	}

}