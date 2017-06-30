package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.kindnesswall.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class SentRequestItemHolder extends RecyclerView.ViewHolder {

	public TextView mItemTitleTv,mItemStatusTv, mUnseenMsgTv;

	public View itemView;

	public RelativeLayout rootLay, btnLay;

	public SentRequestItemHolder(View itemView) {

		super(itemView);

		this.itemView = itemView;

		rootLay = (RelativeLayout) itemView.findViewById(R.id.root_lay);
		btnLay = (RelativeLayout) itemView.findViewById(R.id.btn_lay);

		mItemTitleTv = (TextView) itemView.findViewById(R.id.rw_sent_request_name_tv);
		mItemStatusTv = (TextView) itemView.findViewById(R.id.rw_sent_request_status_tv);

	}
}
