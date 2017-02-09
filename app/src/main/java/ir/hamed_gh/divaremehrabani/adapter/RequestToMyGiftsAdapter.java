package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.holder.SentRequestItemHolder;
import ir.hamed_gh.divaremehrabani.model.api.RequestModel;

/**
 * Created by 5 on 3/8/2016.
 */
public class RequestToMyGiftsAdapter extends RecyclerView.Adapter<SentRequestItemHolder> {

    String schoolId, childID;
    private ArrayList<RequestModel> requestModels;
    private Context mContext;
    private FragmentActivity activity;

    public RequestToMyGiftsAdapter(Context context, ArrayList<RequestModel> requestModels) {
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

    }

    @Override
    public int getItemCount() {
        return (null != requestModels ? requestModels.size() : 0);
    }
}
