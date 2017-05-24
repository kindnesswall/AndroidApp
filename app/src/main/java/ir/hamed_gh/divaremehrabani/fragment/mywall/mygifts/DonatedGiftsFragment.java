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
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.StartLastIndex;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class DonatedGiftsFragment extends BaseFragment {

    @Bind(R.id.fragment_progressBar)
    ProgressView progressView;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.message_textview)
    TextView mMessageTv;

    View rootView;
    private ArrayList<Gift> gifts = new ArrayList<>();
    private GiftListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private int startIndex = 0;
    private String userId;

    public static DonatedGiftsFragment newInstance(String userId){
        DonatedGiftsFragment donatedGiftsFragment = new DonatedGiftsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.USER_ID, userId);
        donatedGiftsFragment.setArguments(bundle);

        return donatedGiftsFragment;
    }

    @Override
    protected void init() {
        super.init();

        adapter = new GiftListAdapter(context, gifts);
        mRecyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        if (getArguments()!=null){
            userId = getArguments().getString(Constants.USER_ID);
        }
        if (userId == null)
            userId = AppController.getStoredString(Constants.USER_ID);

        apiRequest.getDonatedGifts(
                userId,
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
        if (rootView != null) {
            if (rootView.getParent() != null)
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);

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
                    "شما هیچ هدیه‌ی اهدا نکرده اید."
            );
        }

    }

}
