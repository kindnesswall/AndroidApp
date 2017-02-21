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
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.fragment.mywall.requests.RequestsToAGiftFragment;
import ir.hamed_gh.divaremehrabani.holder.SentRequestItemHolder;
import ir.hamed_gh.divaremehrabani.model.api.Gift;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestToMyGiftsAdapter extends RecyclerView.Adapter<SentRequestItemHolder> {

    private ArrayList<Gift> gifts;
    private Context mContext;

    public RequestToMyGiftsAdapter(Context context, ArrayList<Gift> gifts) {
        this.gifts = gifts;
        this.mContext = context;
    }

    @Override
    public SentRequestItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sent_request, null);
        SentRequestItemHolder mh = new SentRequestItemHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(final SentRequestItemHolder myHolder, final int i) {

        myHolder.mItemTitleTv.setText(gifts.get(i).title);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestsToAGiftFragment requestsToAGiftFragment = new RequestsToAGiftFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GIFT_ID, gifts.get(i).giftId);
                requestsToAGiftFragment.setArguments(bundle);

                ((BottomBarActivity) mContext).setFragment(
                        requestsToAGiftFragment, RequestsToAGiftFragment.class.getName()
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != gifts ? gifts.size() : 0);
    }
}
