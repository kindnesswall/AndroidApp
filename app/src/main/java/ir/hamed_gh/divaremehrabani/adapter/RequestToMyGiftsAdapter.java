package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.fragment.mywall.requests.RequestsToAGiftFragment;
import ir.hamed_gh.divaremehrabani.holder.ReceiveGiftsRequestHolder;
import ir.hamed_gh.divaremehrabani.model.api.Gift;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestToMyGiftsAdapter extends RecyclerView.Adapter<ReceiveGiftsRequestHolder> {

    private ArrayList<Gift> gifts;
    private Context mContext;

    public RequestToMyGiftsAdapter(Context context, ArrayList<Gift> gifts) {
        this.gifts = gifts;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    @Override
    public ReceiveGiftsRequestHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_receive_requests, null);
        ReceiveGiftsRequestHolder mh = new ReceiveGiftsRequestHolder(v);

//        switch (i){
//            case 0:
//                mh.rootLay.setBackgroundColor(mContext.getResources().getColor(R.color.lime_A700));
//                break;
//            case 1:
//                mh.rootLay.setBackgroundColor(mContext.getResources().getColor(R.color.orange_A400));
//                break;
//        }

        return mh;
    }

    @Override
    public void onBindViewHolder(final ReceiveGiftsRequestHolder myHolder, final int i) {

        myHolder.mTitleTv.setText(gifts.get(i).title);
        myHolder.mUnseenMsgTv.setText(gifts.get(i).requestCount);

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestsToAGiftFragment requestsToAGiftFragment = new RequestsToAGiftFragment();

                Bundle bundle = new Bundle();
                bundle.putString(Constants.GIFT_ID, gifts.get(i).giftId);
                bundle.putString(Constants.GIFT_NAME, gifts.get(i).title);
                bundle.putString(Constants.GIFT_REQUEST_COUNT, gifts.get(i).requestCount);

                requestsToAGiftFragment.setArguments(bundle);

                ((BottomBarActivity) mContext).addFragment(
                        requestsToAGiftFragment, RequestsToAGiftFragment.class.getName()
                );
            }
        });

//        switch (getItemViewType(i)){
//            case 0:
//                myHolder.rootLay.setBackgroundColor(mContext.getResources().getColor(R.color.lime_A700));
//                break;
//            case 1:
//                myHolder.rootLay.setBackgroundColor(mContext.getResources().getColor(R.color.orange_A400));
//                break;
//        }

    }

    @Override
    public int getItemCount() {
        return (null != gifts ? gifts.size() : 0);
    }
}
