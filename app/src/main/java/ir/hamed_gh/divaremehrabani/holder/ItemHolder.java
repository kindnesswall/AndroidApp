package ir.hamed_gh.divaremehrabani.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hamed_gh.ir.divaaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.DetailActivity;

/**
 * Created by Hamed on 11/11/16.
 */

public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected TextView mItemTitleTv;
    protected ImageView mItemIv;
    View itemView;

    public TextView getmItemTitleTv() {
        return mItemTitleTv;
    }

    public ImageView getmItemIv() {
        return mItemIv;
    }

    public ItemHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;

        mItemTitleTv = (TextView) itemView.findViewById(R.id.row_title_textview);
        mItemIv = (ImageView) itemView.findViewById(R.id.row_imageview);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemView.getContext().startActivity(new Intent(itemView.getContext(), DetailActivity.class));
    }
}
