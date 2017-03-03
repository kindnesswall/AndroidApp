package ir.hamed_gh.divaremehrabani.fragment.mywall.requests;

import android.os.Bundle;
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
import ir.hamed_gh.divaremehrabani.activity.GiftDetailActivity;
import ir.hamed_gh.divaremehrabani.adapter.RequestToAGiftAdapter;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.fragment.BaseFragment;
import ir.hamed_gh.divaremehrabani.model.api.RequestModel;
import ir.hamed_gh.divaremehrabani.model.api.input.RecievedRequestListInput;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestsToAGiftFragment extends BaseFragment {

    @Bind(R.id.fragment_progressBar)
    ProgressView progressView;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.message_textview)
    TextView mMessageTv;

    @Bind(R.id.info_lay)
    RelativeLayout mInfoLay;

    private ArrayList<RequestModel> requestModels = new ArrayList<>();
    private RequestToAGiftAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private int startIndex = 0;
    private String giftId;

    @Override
    protected void init() {
        super.init();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            giftId = bundle.getString(Constants.GIFT_ID);
        }

        adapter = new RequestToAGiftAdapter(context, requestModels);
        mRecyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        apiRequest.getRecievedRequestList(
                new RecievedRequestListInput(
                        giftId,
                        startIndex + "",
                        startIndex + Constants.LIMIT + ""
                )
        );

        mInfoLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(
                        GiftDetailActivity.createIntent(giftId)
                );
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_requests_toagift, container, false);

        ButterKnife.bind(this, rootView);
        init();

        return rootView;
    }

    @Override
    public void onResponse(Call call, Response response) {

        progressView.setVisibility(View.INVISIBLE);

        ArrayList<RequestModel> requestModels = (ArrayList<RequestModel>) response.body();
        this.requestModels.addAll(requestModels);
        adapter.notifyDataSetChanged();

        if (requestModels.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mMessageTv.setVisibility(View.INVISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mMessageTv.setVisibility(View.VISIBLE);
            mMessageTv.setText(
                    "شما هیچ درخواستی دریافت نکرده‌اید."
            );
        }

    }

}
