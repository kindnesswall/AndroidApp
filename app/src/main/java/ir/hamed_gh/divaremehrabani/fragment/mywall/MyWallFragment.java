package ir.hamed_gh.divaremehrabani.fragment.mywall;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.dialogfragment.ChoosePlaceDialogFragment;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.mygifts.MyGiftsFragment;
import ir.hamed_gh.divaremehrabani.fragment.mywall.requests.MyRequestsFragment;
import ir.hamed_gh.divaremehrabani.helper.Snackbari;
import ir.hamed_gh.divaremehrabani.interfaces.ChoosePlaceCallback;
import ir.hamed_gh.divaremehrabani.model.Place;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyWallFragment extends BaseFragment implements ChoosePlaceCallback{

    @Bind(R.id.location_tv)
    TextView mLocationTv;

    @Bind(R.id.location_lay)
    RelativeLayout locationLayout;

    @Bind(R.id.statistic_lay)
    RelativeLayout statisticLayout;

    @Bind(R.id.my_gift_lay)
    RelativeLayout myGiftLay;

    @Bind(R.id.my_request_lay)
    RelativeLayout myRequestsLay;

    @Bind(R.id.logout_lay)
    RelativeLayout mLogoutLay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_my_wall, container, false);

        ButterKnife.bind(this, rootView);
        init();

        setListeners();

        return rootView;
    }

    @Override
    protected void init() {
        super.init();

        if (AppController.getStoredString(Constants.Authorization)!= null){
            mLogoutLay.setVisibility(View.VISIBLE);
        }else {
            mLogoutLay.setVisibility(View.INVISIBLE);
        }

        mLocationTv.setText(AppController.getStoredString(Constants.MY_LOCATION_NAME));
    }

    @Override
    public void onResume() {
        super.onResume();

        ((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("دیوار من");

    }

    @Override
    public void onResponse(Call call, Response response) {
        Snackbari.showS(mLogoutLay, "شما با موفقیت از حساب خارج شدید");
    }

    private void setListeners() {
        mLogoutLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiRequest.logout();
                mLogoutLay.setVisibility(View.INVISIBLE);
                AppController.storeString(Constants.Authorization,null);
                AppController.storeString(Constants.TELEPHONE, null);

            }
        });

        myGiftLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BottomBarActivity) getActivity()).setFragment(
                        new MyGiftsFragment(), MyGiftsFragment.class.getName()
                );
            }
        });

        myRequestsLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BottomBarActivity) getActivity()).setFragment(
                        new MyRequestsFragment(), MyRequestsFragment.class.getName()
                );
            }
        });

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ChoosePlaceDialogFragment choosePlaceDialogFragment = new ChoosePlaceDialogFragment();
                choosePlaceDialogFragment.setTargetFragment(MyWallFragment.this, 0);
                choosePlaceDialogFragment.show(fm, "fragment_name");
            }
        });

        statisticLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BottomBarActivity) getActivity()).setFragment(
                        new StatisticFragment(), StatisticFragment.class.getName()
                );

            }
        });
    }

    @Override
    public void onPlaceSelected(Place place) {

        AppController.storeString(Constants.MY_LOCATION_ID, place.id);
        AppController.storeString(Constants.MY_LOCATION_NAME, place.name);

        mLocationTv.setText(place.name);

    }
}
