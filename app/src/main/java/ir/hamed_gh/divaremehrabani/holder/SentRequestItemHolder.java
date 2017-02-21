package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class SentRequestItemHolder extends RecyclerView.ViewHolder {

    public TextView mItemTitleTv;

    public View itemView;

    public SentRequestItemHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;

        mItemTitleTv = (TextView) itemView.findViewById(R.id.rw_sent_request_name_tv);

    }
}
