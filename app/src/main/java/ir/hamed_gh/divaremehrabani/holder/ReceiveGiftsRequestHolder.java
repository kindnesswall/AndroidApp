package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ReceiveGiftsRequestHolder extends RecyclerView.ViewHolder {

	public TextView mTitleTv, mUnseenMsgTv;

	public View itemView;

	public RelativeLayout rootLay;

	public ReceiveGiftsRequestHolder(View itemView) {
		super(itemView);

		this.itemView = itemView;
		rootLay = (RelativeLayout) itemView.findViewById(R.id.root_lay);
		mTitleTv = (TextView) itemView.findViewById(R.id.rw_sent_request_name_tv);
		mUnseenMsgTv = (TextView) itemView.findViewById(R.id.unseen_msg_tv);

	}
}
