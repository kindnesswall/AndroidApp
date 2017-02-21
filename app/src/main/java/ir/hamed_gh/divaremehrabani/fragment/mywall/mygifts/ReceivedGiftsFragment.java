package ir.hamed_gh.divaremehrabani.fragment.mywall.mygifts;

import android.os.Bundle;
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
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.GiftListAdapter;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.StartLastIndex;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class ReceivedGiftsFragment extends BaseFragment {

    @Bind(R.id.fragment_progressBar)
    ProgressView progressView;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.message_textview)
    TextView mMessageTv;

    private ArrayList<Gift> gifts = new ArrayList<>();
    private GiftListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private int startIndex = 0;

    @Override
    protected void init() {
        super.init();

        adapter = new GiftListAdapter(context, gifts);
        mRecyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        apiRequest.getReceivedGifts(
                new StartLastIndex(
                        startIndex + "",
                        startIndex + Constants.LIMIT + ""
                )
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        ButterKnife.bind(this, rootView);
        init();

        return rootView;
    }

    @Override
    public void onResponse(Call call, Response response) {

        progressView.setVisibility(View.INVISIBLE);

        ArrayList<Gift> gifts = (ArrayList<Gift>) response.body();
        this.gifts.addAll(gifts);
        adapter.notifyDataSetChanged();

        if (gifts.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mMessageTv.setVisibility(View.INVISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mMessageTv.setVisibility(View.VISIBLE);
            mMessageTv.setText(
                    "شما هیچ هدیه‌ی دریافت نکرده اید."
            );
        }
    }

}
