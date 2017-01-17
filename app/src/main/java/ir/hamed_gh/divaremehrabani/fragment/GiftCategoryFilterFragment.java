package ir.hamed_gh.divaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.GiftListAdapter;
import ir.hamed_gh.divaremehrabani.customviews.textviews.TextViewDivarIcons;
import ir.hamed_gh.divaremehrabani.customviews.textviews.TextViewIranSansRegular;
import ir.hamed_gh.divaremehrabani.helper.EndlessRecyclerViewScrollListener;
import ir.hamed_gh.divaremehrabani.model.api.Gift;

/**
 * Created by 5 on 02/21/2016.
 */
public class GiftCategoryFilterFragment extends BaseFragment {

    @Bind(R.id.message_textview)
    TextView mMessageTextView;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.search_progressBar)
    ProgressView progressView;

    @Bind(R.id.filter_lay)
    RelativeLayout mFilterLayBtn;

    @Bind(R.id.filter_ic)
    TextViewDivarIcons filterIc;

    @Bind(R.id.filter_txt)
    TextViewIranSansRegular filterTxt;

    private GiftListAdapter adapter;

    private ArrayList<Gift> galleries = new ArrayList<>();
    private int pageNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        ButterKnife.bind(this, rootView);
        init();

        adapter = new GiftListAdapter(context, galleries);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(new LinearLayoutManager(context)) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Toasti.showS("need more data, page: " + page + ", totalItemsCount: " + totalItemsCount);
                pageNumber++;
            }
        });

        mFilterLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FilteringFragment filteringFragment = new FilteringFragment();
                filteringFragment.show(fm, "fragment_name");
            }
        });
        return rootView;
    }

    void sendRequest() {
//        Map<String, String> params = new HashMap<>();
//        params.put("pageSize", "10");
//        params.put("pageNo", "1");

//        Call<ArrayList<Gift>> call = AppController.service.getGifts();
//
//        call.enqueue(new Callback<ArrayList<Gift>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Gift>> call, Response<ArrayList<Gift>> response) {
//                progressView.setVisibility(View.INVISIBLE);
//
////                try {
////                    Meta meta = response.body().getMeta();
////                    if (meta.getErrorCode() == 1000) {
//                        galleries.addAll(response.body());
//                        adapter.notifyDataSetChanged();
//                        mRecyclerView.setVisibility(View.VISIBLE);
//                        mMessageTextView.setVisibility(View.INVISIBLE);
////                    } else {
////                        mMessageTextView.setVisibility(View.VISIBLE);
////                        mRecyclerView.setVisibility(View.INVISIBLE);
////                    }
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Gift>> call, Throwable t) {
//                progressView.setVisibility(View.INVISIBLE);
//                mRecyclerView.setVisibility(View.INVISIBLE);
//                mMessageTextView.setText("خطا در دریافت اطلاعات");
//            }
//        });
    }
}
