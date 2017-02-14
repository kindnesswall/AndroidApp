package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ir.hamed_gh.divaremehrabani.R;


/**
 * Created by Hamed on 6/10/16.
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
