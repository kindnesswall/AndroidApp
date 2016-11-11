package ir.hamed_gh.divaaremehrabani.fragment;

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
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import ir.hamed_gh.divaaremehrabani.adapter.RecyclerViewAdapter;
import ir.hamed_gh.divaaremehrabani.app.AppController;
import ir.hamed_gh.divaaremehrabani.customviews.textviews.TextViewDivarIcons;
import ir.hamed_gh.divaaremehrabani.customviews.textviews.TextViewIranSansRegular;
import ir.hamed_gh.divaaremehrabani.helper.EndlessRecyclerViewScrollListener;
import ir.hamed_gh.divaaremehrabani.model.api.Item;
import ir.hamed_gh.divaaremehrabani.model.api.Items;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class CategoryFragment extends BaseFragment {

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

    private RecyclerViewAdapter adapter;

    private ArrayList<Item> galleries = new ArrayList<>();
    private int pageNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        ButterKnife.bind(this, rootView);
        init();

        adapter = new RecyclerViewAdapter(context, galleries);
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
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");
        params.put("pageNo", "1");

        Call<Items> call = AppController.service.getItems("0","10");

        call.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                progressView.setVisibility(View.INVISIBLE);

//                try {
//                    Meta meta = response.body().getMeta();
//                    if (meta.getErrorCode() == 1000) {
                        galleries.addAll(response.body().getItems());
                        adapter.notifyDataSetChanged();
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mMessageTextView.setVisibility(View.INVISIBLE);
//                    } else {
//                        mMessageTextView.setVisibility(View.VISIBLE);
//                        mRecyclerView.setVisibility(View.INVISIBLE);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                progressView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mMessageTextView.setText("خطا در دریافت اطلاعات");
            }
        });
    }
}