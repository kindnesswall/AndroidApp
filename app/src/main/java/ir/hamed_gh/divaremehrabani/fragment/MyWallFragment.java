package ir.hamed_gh.divaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.dialogfragment.ChooseCityDialogFragment;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyWallFragment extends BaseFragment {

//    @Bind(R.id.my_gift_txt)
//    TextView mMessageTextView;

    @Bind(R.id.location_lay)
    RelativeLayout locationLayout;

    @Bind(R.id.statistic_lay)
    RelativeLayout statisticLayout;

    @Bind(R.id.my_gift_lay)
    RelativeLayout myGiftLay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_my_wall, container, false);

        ButterKnife.bind(this, rootView);
        init();

        myGiftLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BottomBarActivity) getActivity()).setFragment(
                        new MyGiftListFragment(), MyGiftListFragment.class.getName()
                );
            }
        });

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ChooseCityDialogFragment chooseCityDialogFragment = new ChooseCityDialogFragment();
                chooseCityDialogFragment.show(fm, "fragment_name");
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

        return rootView;
    }
}
