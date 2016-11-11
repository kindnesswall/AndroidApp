package ir.hamed_gh.divaaremehrabani.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import ir.hamed_gh.divaaremehrabani.fragment.ChooseCategoryDialogFragment;
import ir.hamed_gh.divaaremehrabani.helper.Toasti;

public class RegisterGiftActivity extends AppCompatActivity {

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