package ir.hamed_gh.divaremehrabani.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.activity.LoginActivity;
import ir.hamed_gh.divaremehrabani.adapter.ViewPagerAdapter;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.customviews.CustomTabLayout;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyRequestsFragment extends BaseFragment {

    @Bind(R.id.my_gift_login_btn)
    RelativeLayout myGiftLoginBtn;

    @Bind(R.id.my_gift_top_lay)
    RelativeLayout myGiftTopLay;

    @Bind(R.id.main_tabs)
    CustomTabLayout mainTabs;

    @Bind(R.id.main_vp)
    ViewPager mainVp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_my_requests, container, false);

        ButterKnife.bind(this, rootView);
        init();

        ((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("هدیه‌های من");

        setupViewPager(mainVp);
        mainTabs.setupWithViewPager(mainVp);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(mainActivity.getSupportFragmentManager());

        RegisteredGiftsFragment registeredGiftsFragment = new RegisteredGiftsFragment();
        RegisteredGiftsFragment registeredGiftsFragment2 = new RegisteredGiftsFragment();

        adapter.addFrag(registeredGiftsFragment, "درخواستی");
        adapter.addFrag(registeredGiftsFragment2, "ارسالی");

        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (AppController.getStoredString(Constants.Authorization)!=null){
            myGiftTopLay.setVisibility(View.GONE);
        }else {
            myGiftTopLay.setVisibility(View.VISIBLE);

            myGiftLoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }
    }
}
