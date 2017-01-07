package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.holder.ItemHolder;
import ir.hamed_gh.divaremehrabani.model.api.Gift;

/**
 * Created by 5 on 3/8/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ItemHolder> {

    String schoolId, childID;
    private ArrayList<Gift> gifts;
    private Context mContext;
    private FragmentActivity activity;

    public RecyclerViewAdapter(Context context, ArrayList<Gift> gifts) {
        this.gifts = gifts;
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_ad_gift, null);
        ItemHolder mh = new ItemHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(ItemHolder myHolder, final int i) {

//	    myHolder.mItemTitleTv.setText(gifts.get(i).getGalleryId());

        //TODO load image of item
        String image_url;
        if (gifts.get(i).giftImages != null && gifts.get(i).giftImages.size() > 0) {
            image_url = gifts.get(i).giftImages.get(0);
        }else {
            image_url = "";
        }

        Glide
                .with(mContext)
                .load(image_url)
                .centerCrop()
                .placeholder(R.color.background)
                .crossFade()
                .into(myHolder.getmItemIv());

        myHolder.getGiftTitleTv().setText(gifts.get(i).title);
        myHolder.getGiftLocationTv().setText(gifts.get(i).address);
        myHolder.getGiftCreatedTimeTv().setText(gifts.get(i).createDateTime);

    }

    @Override
    public int getItemCount() {
        return (null != gifts ? gifts.size() : 0);
    }
}
