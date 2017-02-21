package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.UserProfileActivity;
import ir.hamed_gh.divaremehrabani.holder.SentRequestItemHolder;
import ir.hamed_gh.divaremehrabani.model.api.RequestModel;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestToAGiftAdapter extends RecyclerView.Adapter<SentRequestItemHolder> {

    private ArrayList<RequestModel> requestModels;
    private Context mContext;

    public RequestToAGiftAdapter(Context context, ArrayList<RequestModel> requestModels) {
        this.requestModels = requestModels;
        this.mContext = context;
    }

    @Override
    public SentRequestItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sent_request, null);
        SentRequestItemHolder mh = new SentRequestItemHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(SentRequestItemHolder myHolder, final int i) {

        myHolder.mItemTitleTv.setText(requestModels.get(i).gift);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((BottomBarActivity) mContext).setFragment(
//                        new RequestsToAGiftFragment(), RequestsToAGiftFragment.class.getName()
//                );

                mContext.startActivity(UserProfileActivity.createIntent(requestModels.get(i).fromUserId));

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != requestModels ? requestModels.size() : 0);
    }
}
