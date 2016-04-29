package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.activity.BaseActivity;
import hamed_gh.ir.divaaremehrabani.app.AppController;

/**
 * Created by 5 on 02/21/2016.
 */
public class testFragment extends BaseFragment {

    @Bind(R.id.name)
    TextView name;

    RelativeLayout toolbarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        ButterKnife.bind(this, rootView);
        init("پیشخوان");
        showFirstBtn(R.mipmap.ic_arrow_back_white_24dp);
        BaseActivity.howToBack = BaseActivity.HowToBack.NOTHING;

        //-- load data ---
        name.setText(AppController.getStoredString("FirstName"));

//        AppController.loadImg(profileIV);
//
//        ((BaseActivity)mainActivity).drawer.setSelection(((BaseActivity)mainActivity).dashboardDrawerItem,false);
        return rootView;
    }

    @Override
    protected void firstBtnListener() {
        replaceFragment(new testFragment(),"به روز رسانی","BACK");
    }
}
