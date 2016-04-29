package hamed_gh.ir.divaaremehrabani.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.activity.BaseActivity;
import hamed_gh.ir.divaaremehrabani.helper.Toasti;

/**
 * Created by aseman on 3/28/16.
 */
public class BaseFragment extends Fragment {

    protected Context context;
    protected AppCompatActivity mainActivity;
    private RelativeLayout toolbarLayout;
    private TextView mToolbarTitleTextView;
    private ImageView mFirstIV;
    private ImageView mSecondIV;

    protected void init(){
        context = getActivity();
        mainActivity = (AppCompatActivity) getActivity();

        // Toolbar
        toolbarLayout = (RelativeLayout) ((AppCompatActivity)context).findViewById(R.id.toolbar);

        mSecondIV = (ImageView)toolbarLayout.findViewById(R.id.toolbar_second_iv);
        mFirstIV = (ImageView)toolbarLayout.findViewById(R.id.toolbar_first_iv);

        mToolbarTitleTextView = (TextView) toolbarLayout.findViewById(R.id.toolbar_title_textView);

        setHowToBack();
    }

    protected void setHowToBack(){
        if (getArguments()!= null) {
            String how_to_back = getArguments().getString("how_to_back");
            if (how_to_back!=null) {
                if (how_to_back.equals("HOME")) {
                    BaseActivity.howToBack = BaseActivity.HowToBack.HOME;
                } else if (how_to_back.equals("NOTHING")) {
                    BaseActivity.howToBack = BaseActivity.HowToBack.NOTHING;
                }else if (how_to_back.equals("BACK"))  {
                    BaseActivity.howToBack = BaseActivity.HowToBack.BACK;
                }else {
                    Toasti.showS("how_to_back not set");
                }
            }
        }
    }

    protected void showFirstBtn(int drawableResId){
        mFirstIV.setVisibility(View.VISIBLE);
        mFirstIV.setImageDrawable(ResourcesCompat.getDrawable(getResources(), drawableResId, null));
        mFirstIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstBtnListener();
            }
        });
    }

    protected void showSecondBtn(int drawableResId){
        mSecondIV.setVisibility(View.VISIBLE);
        mSecondIV.setImageDrawable(ResourcesCompat.getDrawable(getResources(), drawableResId, null));
        mSecondIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondBtnListener();
            }
        });
    }

    protected void secondBtnListener(){}

    protected void firstBtnListener(){
        mainActivity.onBackPressed();
    }

}
