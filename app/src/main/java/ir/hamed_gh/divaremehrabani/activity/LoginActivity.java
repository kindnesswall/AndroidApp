package ir.hamed_gh.divaremehrabani.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.customviews.edit_text.EditTextIranSans;
import ir.hamed_gh.divaremehrabani.customviews.textviews.TextViewIranSansRegular;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.NumberTranslator;
import ir.hamed_gh.divaremehrabani.helper.Toasti;
import ir.hamed_gh.divaremehrabani.model.api.output.TokenOutput;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ApiRequest.Listener {

	@Bind(R.id.main_toolbar)
	Toolbar mToolbar;

	@Bind(R.id.toolbar_title_textView)
	TextView mToolbarTitleTextView;

	@Bind(R.id.toolbar_back_iv)
	ImageView mBackBtn;

	@Bind(R.id.not_recieved_code_btn)
	RelativeLayout not_recieved_code_btn;

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
	private Context context;
	private ApiRequest apiRequest;

	String regexStr = "^[0-9]*$";

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), GiftDetailActivity.class);
		return intent;
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

				if (!confirmationCode.trim().matches(regexStr)){
					Toasti.showS("کد وارد شده صحیح نمی‌باشد");
					return;
				}
				apiRequest.login(confirmationCode);

				progressView.setVisibility(View.VISIBLE);
				login_get_verification_tv.setVisibility(View.INVISIBLE);
			}
		};

		enterPhoneNumber = new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String phoneNumber = NumberTranslator.toEnglish(phoneConfirimationCodeEt.getText().toString());

				if (phoneNumber.length() != 11 || !phoneNumber.startsWith("09") || !phoneNumber.trim().matches(regexStr)){
					Toasti.showS("شماره تلفن وارد شده صحیح نمی‌باشد");
					return;
				}

				apiRequest.register(phoneNumber);

				progressView.setVisibility(View.VISIBLE);
				login_get_verification_tv.setVisibility(View.INVISIBLE);
			}
		};

		not_recieved_code_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AppController.storeString(
						Constants.TELEPHONE, null
				);

				enterTelephoneNumber();
			}
		});

		if (AppController.getStoredString(Constants.TELEPHONE) == null) {
			enterTelephoneNumber();
		} else {
			enterVerificationCode();
		}
	}

	private void init() {
		context = this;
		apiRequest = new ApiRequest(context, this);
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
		not_recieved_code_btn.setVisibility(View.INVISIBLE);

		loginGetVerificationBtn.setOnClickListener(enterPhoneNumber);
	}

	public void enterVerificationCode() {
		phoneConfirimationCodeEt.setHint(getString(R.string.field_hint_verification_code));
		phoneConfirimationCodeEt.setText("");

		login_get_verification_tv.setText(getString(R.string.sign_in));

		not_recieved_code_btn.setVisibility(View.VISIBLE);
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

			Toasti.showL("شما با شماره تلفن " + NumberTranslator.toPersian(AppController.getStoredString(Constants.TELEPHONE)) + " وارد شدید.");
			finish();
		} else {
			enterVerificationCode();
		}
	}

	@Override
	public void onFailure(Call call, Throwable t) {
		progressView.setVisibility(View.INVISIBLE);
		login_get_verification_tv.setVisibility(View.VISIBLE);

		Toasti.showS("onFailure");
	}
}