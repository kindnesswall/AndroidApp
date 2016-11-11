package ir.hamed_gh.divaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.RecyclerViewAdapter;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.helper.EndlessRecyclerViewScrollListener;
import ir.hamed_gh.divaremehrabani.model.api.Item;
import ir.hamed_gh.divaremehrabani.model.api.Items;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.message_textview)
    TextView mMessageTextView;

    @Bind(R.id.fragment_progressBar)
    ProgressView progressView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerViewAdapter adapter;

    private ArrayList<Item> items = new ArrayList<>();
    private int pageNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        ButterKnife.bind(this, rootView);
        init();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

//		foo.things(ImmutableMap.of("foo", "bar", "kit", "kat")
        /* Initialize recyclerview */
        adapter = new RecyclerViewAdapter(context, items);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(new LinearLayoutManager(context)) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Toasti.showS("need more data, page: " + page + ", totalItemsCount: " + totalItemsCount);
                pageNumber++;
            }
        });

        sendRequest();

        return rootView;
    }

    void refreshItems() {
        // Load items
        // ...

        pageNumber = 1;
        items.clear();

        sendRequest();

        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    void sendRequest() {

        Call<Items> call = AppController.service.getItems("0","10");

        call.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                progressView.setVisibility(View.INVISIBLE);

//                try {
//                    Meta meta = response.body().getMeta();
//                    if (meta.getErrorCode() == 1000) {
                        items.addAll(response.body().getItems());
                        adapter.notifyDataSetChanged();
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mMessageTextView.setVisibility(View.INVISIBLE);
//                    } else {
//                        mMessageTextView.setVisibility(View.VISIBLE);
//                        mRecyclerView.setVisibility(View.INVISIBLE);
//                    }

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
