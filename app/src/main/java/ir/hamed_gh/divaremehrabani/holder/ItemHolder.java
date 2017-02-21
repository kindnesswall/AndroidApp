package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected TextView mItemTitleTv, mGiftLocationTv, mGiftCreatedTimeTv;
    protected ImageView mItemIv;
    public View itemView;

    public TextView getGiftTitleTv() {
        return mItemTitleTv;
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

    public ItemHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;

        mItemTitleTv = (TextView) itemView.findViewById(R.id.row_title_textview);
        mGiftLocationTv = (TextView) itemView.findViewById(R.id.row_ad_gift_location_tv);
        mGiftCreatedTimeTv = (TextView) itemView.findViewById(R.id.row_ad_gift_time_tv);

        mItemIv = (ImageView) itemView.findViewById(R.id.row_imageview);
    }

    @Override
    public void onClick(View v) {

    }
}
