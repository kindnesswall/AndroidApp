package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ir.kindnesswall.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class GiftGalleryHolder extends RecyclerView.ViewHolder {

	public ImageView mImageView, mCloseBtn;
	public View mItemView;

	public GiftGalleryHolder(View itemView) {
		super(itemView);

		mImageView = (ImageView) itemView.findViewById(R.id.gift_imageview);
		mCloseBtn = (ImageView) itemView.findViewById(R.id.close_btn_lay);

		this.mItemView = itemView;
	}

}
