package ir.hamed_gh.divaremehrabani.dialogfragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.customviews.textviews.TextViewIranSansRegular;

/**
 * Created by 5 on 02/21/2016.
 */
public class FilterDialogFragment extends DialogFragment {

    @Bind(R.id.category_filter_lay)
    RelativeLayout categoryFilterLay;

    @Bind(R.id.location_filter_lay)
    RelativeLayout locationFilterLay;

    @Bind(R.id.apply_filter_tv)
    TextViewIranSansRegular applyFilterTv;

    @Bind(R.id.cancel_filter_tv)
    TextViewIranSansRegular cancelFilterTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.dialogfragment_filtering, container, false);

        ButterKnife.bind(this, rootView);

        setListeners();

//        ChooseCityAdapter chooseCityAdapter = new ChooseCityAdapter(getContext(), level2.getPlaces());
//        recyclerView.setAdapter(chooseCityAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    private void setListeners() {
        categoryFilterLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        locationFilterLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        applyFilterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelFilterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
}
