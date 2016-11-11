package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.holder.ItemHolder;
import ir.hamed_gh.divaremehrabani.model.api.Item;

/**
 * Created by 5 on 3/8/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ItemHolder> {

    String schoolId, childID;
    private ArrayList<Item> items;
    private Context mContext;
    private FragmentActivity activity;

    public RecyclerViewAdapter(Context context, ArrayList<Item> items) {
        this.items = items;
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

//	    myHolder.mItemTitleTv.setText(items.get(i).getGalleryId());

        //TODO load image of item
//        ImageLoader.getInstance().displayImage(
//                URIs.BASE_URL + items.get(i).getImageSrc(), myHolder.mItemIv)
//        ;

        myHolder.getmItemTitleTv().setText(items.get(i).title);

    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }
}
