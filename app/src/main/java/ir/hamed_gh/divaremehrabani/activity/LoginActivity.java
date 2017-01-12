package ir.hamed_gh.divaremehrabani.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.customviews.edit_text.EditTextIranSans;
import ir.hamed_gh.divaremehrabani.customviews.textviews.TextViewIranSansRegular;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.Toasti;
import ir.hamed_gh.divaremehrabani.model.api.TokenOutput;
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

    private Context context;

    View.OnClickListener enterPhoneNumber;
    View.OnClickListener enterVerificationCodeListener;
    private ApiRequest apiRequest;

    private void settingToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {

        }
//        mToolbarTitleTextView.setText("دیوار مهربانی");
    }

    private void setListeners(){
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        enterVerificationCodeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasti.showS("send verification code");
                apiRequest.getToken(phoneConfirimationCodeEt.getText().toString());
            }
        };

        enterPhoneNumber = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiRequest.register(phoneConfirimationCodeEt.getText().toString());
                enterVerificationCode();

            }
        };

        not_recieved_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTelephoneNumber();
            }
        });

        if (AppController.getStoredString(Constants.TELEPHONE)==null){
            enterTelephoneNumber();
            loginGetVerificationBtn.setOnClickListener(enterPhoneNumber);
        }else {
            enterVerificationCode();
            loginGetVerificationBtn.setOnClickListener(enterVerificationCodeListener);
        }
    }

    private void init(){
        context = this;
        apiRequest = new ApiRequest(context,this);

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


    public void enterTelephoneNumber(){
        AppController.storeString(Constants.TELEPHONE, null);
        phoneConfirimationCodeEt.setHint(getString(R.string.hint_telephone_field));
        phoneConfirimationCodeEt.setText("09353703108");

        login_get_verification_tv.setText(getString(R.string.sign_up));
        not_recieved_code_btn.setVisibility(View.INVISIBLE);
    }

    public void enterVerificationCode(){
        phoneConfirimationCodeEt.setHint(getString(R.string.field_hint_verification_code));
        phoneConfirimationCodeEt.setText("111111");

        login_get_verification_tv.setText(getString(R.string.sign_in));

        not_recieved_code_btn.setVisibility(View.VISIBLE);
        loginGetVerificationBtn.setOnClickListener(enterVerificationCodeListener);
    }

    @Override
    public void onResponse(Call call, Response response) {
        Toasti.showS("onResponse");
        if (response.body() instanceof TokenOutput){
            TokenOutput tokenOutput = (TokenOutput) response.body();
            AppController.storeString(
                    Constants.TOKEN,
                    Constants.BEARER + " " + tokenOutput.access_token);
            finish();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Toasti.showS("onFailure");
    }
}