package hamed_gh.ir.divaaremehrabani.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.app.RestAPI;
import hamed_gh.ir.divaaremehrabani.app.URIs;
import hamed_gh.ir.divaaremehrabani.fragment.ChooseCategoryDialogFragment;
import hamed_gh.ir.divaaremehrabani.helper.Toasti;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterGiftActivity extends AppCompatActivity {

    public RestAPI service;

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

    private Context context;

    private void retrofitInitialization() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("token", "s:s").build();
                                return chain.proceed(request);
                            }
                        }).build();

        //Creating Rest Services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URIs.API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        service = retrofit.create(RestAPI.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_gift);
        ButterKnife.bind(this);

        context = this;

        retrofitInitialization();
        settingToolbar();

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}