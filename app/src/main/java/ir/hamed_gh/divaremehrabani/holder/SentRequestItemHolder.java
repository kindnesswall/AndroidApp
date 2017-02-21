package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;

/**
 * Created by Hamed on 11/11/16.
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
