package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyGiftListFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_my_gifts, container, false);

        ButterKnife.bind(this, rootView);
        init();

        return rootView;
    }
}
