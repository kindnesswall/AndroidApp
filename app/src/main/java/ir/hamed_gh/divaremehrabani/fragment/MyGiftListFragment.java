package ir.hamed_gh.divaremehrabani.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.activity.LoginActivity;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyGiftListFragment extends BaseFragment {

    @Bind(R.id.my_gift_login_btn)
    RelativeLayout myGiftLoginBtn;

    @Bind(R.id.my_gift_top_lay)
    RelativeLayout myGiftTopLay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_my_gifts, container, false);

        ButterKnife.bind(this, rootView);
        init();

        ((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("هدیه‌های من");

        return rootView;
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
