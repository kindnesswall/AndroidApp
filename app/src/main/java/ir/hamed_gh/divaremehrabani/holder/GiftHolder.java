package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class GiftHolder extends RecyclerView.ViewHolder {

	public TextView mTitleTv, mGiftLocationTv, mGiftCreatedTimeTv;
	public ImageView mItemIv;
	public View itemView;

	public GiftHolder(View itemView) {
		super(itemView);

		this.itemView = itemView;

		mTitleTv = (TextView) itemView.findViewById(R.id.row_title_textview);
		mGiftLocationTv = (TextView) itemView.findViewById(R.id.row_ad_gift_location_tv);
		mGiftCreatedTimeTv = (TextView) itemView.findViewById(R.id.row_ad_gift_time_tv);

		mItemIv = (ImageView) itemView.findViewById(R.id.row_imageview);
	}

	public TextView getGiftTitleTv() {
		return mTitleTv;
	}

	public TextView getGiftLocationTv() {
		return mGiftLocationTv;
	}

	public TextView getGiftCreatedTimeTv() {
		return mGiftCreatedTimeTv;
	}

	public ImageView getmItemIv() {
		return mItemIv;
	}
}
