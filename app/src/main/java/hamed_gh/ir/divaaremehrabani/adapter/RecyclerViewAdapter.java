package hamed_gh.ir.divaaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.app.URIs;
import hamed_gh.ir.divaaremehrabani.model.Gallery;

/**
 * Created by 5 on 3/8/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    private ArrayList<Gallery> galleries;

    private Context mContext;
    String schoolId, childID;
    private FragmentActivity activity;

	public class MyHolder extends RecyclerView.ViewHolder {

		protected TextView mGalleryIdTextView;
		protected ImageView mImageView;

		public MyHolder(View itemView) {
			super(itemView);

			mGalleryIdTextView = (TextView) itemView.findViewById(R.id.row_title_textview);
			mImageView = (ImageView) itemView.findViewById(R.id.row_imageview);
		}

	}

    public RecyclerViewAdapter(Context context, ArrayList<Gallery> galleries) {
        this.galleries = galleries;
        this.mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_ad_gift, null);
	    MyHolder mh = new MyHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder,final int i) {

//	    myHolder.mGalleryIdTextView.setText(galleries.get(i).getGalleryId());
	    ImageLoader.getInstance().displayImage(URIs.DOMAIN+galleries.get(i).getImageSrc(), myHolder.mImageView);

    }

    @Override
    public int getItemCount() {

	    return (null != galleries ? galleries.size() : 0);

    }
}
