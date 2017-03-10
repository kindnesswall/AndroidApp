package ir.hamed_gh.divaremehrabani.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.ViewPagerAdapter;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.customviews.CustomTabLayout;
import ir.hamed_gh.divaremehrabani.fragment.mywall.mygifts.DonatedGiftsFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.mygifts.ReceivedGiftsFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.mygifts.RegisteredGiftsFragment;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.Toasti;
import ir.hamed_gh.divaremehrabani.model.api.User;
import retrofit2.Call;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements ApiRequest.Listener {

    @Bind(R.id.main_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_title_textView)
    TextView mToolbarTitleTextView;

    @Bind(R.id.user_telephone_tv)
    TextView mUserTelephoneTv;

    @Bind(R.id.toolbar_back_iv)
    ImageView mBackBtn;

    @Bind(R.id.main_tabs)
    CustomTabLayout mainTabs;

    @Bind(R.id.main_vp)
    ViewPager mainVp;

    private Context context;

    private ApiRequest apiRequest;
    private String userID;

    private int startIndex = 0;

    public static Intent createIntent(String id) {
        Intent intent = new Intent(AppController.getAppContext(), UserProfileActivity.class);
        intent.putExtra(Constants.USER_ID, id);
        return intent;
    }

    private void settingToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {

        }
    }

    private void extractDataFromBundle(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString(Constants.USER_ID);
            //The key argument here must match that used in the other activity
        }
    }

    private void setListeners() {
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init() {
        extractDataFromBundle();
        context = this;
        apiRequest = new ApiRequest(context, this);
        apiRequest.getUser(userID);

        settingToolbar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        init();
        setListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();

        setupViewPager(mainVp);
        mainTabs.setupWithViewPager(mainVp);

        mainVp.setCurrentItem(2, false);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        RegisteredGiftsFragment registeredGiftsFragment = RegisteredGiftsFragment.newInstance(userID);
        DonatedGiftsFragment donatedGiftsFragment =  DonatedGiftsFragment.newInstance(userID);
        ReceivedGiftsFragment receivedGiftsFragment = ReceivedGiftsFragment.newInstance(userID);

        adapter.addFrag(registeredGiftsFragment, "ثبت شده");
        adapter.addFrag(donatedGiftsFragment, "اهدایی");
        adapter.addFrag(receivedGiftsFragment, "دریافتی");

        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResponse(Call call, Response response) {
        User user = (User) response.body();
        mUserTelephoneTv.setText(user.userName);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Toasti.showS("onFailure");
    }
}