package hamed_gh.ir.divaaremehrabani.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.activity.DetailActivity;
import hamed_gh.ir.divaaremehrabani.model.Item;

/**
 * Created by 5 on 3/8/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    String schoolId, childID;
    private ArrayList<Item> items;
    private Context mContext;
    private FragmentActivity activity;

    public RecyclerViewAdapter(Context context, ArrayList<Item> items) {
        this.items = items;
        this.mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_ad_gift, null);
        MyHolder mh = new MyHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, final int i) {

//	    myHolder.mGalleryIdTextView.setText(items.get(i).getGalleryId());

        //TODO load image of item
//        ImageLoader.getInstance().displayImage(
//                URIs.BASE_URL + items.get(i).getImageSrc(), myHolder.mImageView)
//        ;
    }

    @Override
    public int getItemCount() {

        return (null != items ? items.size() : 0);

    }

    public class MyHolder extends RecyclerView.ViewHolder {

        protected TextView mGalleryIdTextView;
        protected ImageView mImageView;

        public MyHolder(View itemView) {
            super(itemView);

            mGalleryIdTextView = (TextView) itemView.findViewById(R.id.row_title_textview);
            mImageView = (ImageView) itemView.findViewById(R.id.row_imageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, DetailActivity.class));
                }
            });
        }

    }
}
