package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.activity.BottomBarActivity;

/**
 * Created by 5 on 02/21/2016.
 */
public class StatisticFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_statistic, container, false);

        ButterKnife.bind(this, rootView);
        init();

        ((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("آمار و ارقام");

        return rootView;
    }
}
