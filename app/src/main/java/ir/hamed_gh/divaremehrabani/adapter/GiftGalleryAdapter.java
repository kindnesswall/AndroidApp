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
import ir.hamed_gh.divaremehrabani.holder.GiftGalleryHolder;
import ir.hamed_gh.divaremehrabani.interfaces.UpdateImageGallery;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class GiftGalleryAdapter extends RecyclerView.Adapter<GiftGalleryHolder> {

	private Context mContext;
	private ArrayList<String> giftImageUrls;
	private FragmentActivity activity;

	public GiftGalleryAdapter(Context context, ArrayList<String> giftImageUrls) {
		this.mContext = context;
		this.giftImageUrls = giftImageUrls;
	}

	@Override
	public GiftGalleryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_gift_gallery, null);
		GiftGalleryHolder giftGalleryHolder = new GiftGalleryHolder(v);

		return giftGalleryHolder;
	}

	@Override
	public void onBindViewHolder(GiftGalleryHolder giftGalleryHolder, final int i) {

		giftGalleryHolder.mCloseBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				giftImageUrls.remove(i);
				notifyDataSetChanged();

				((UpdateImageGallery) mContext).onUpdateGallery();
			}
		});

		Glide
				.with(mContext)
				.load(giftImageUrls.get(i))
				.centerCrop()
				.placeholder(R.color.background)
				.crossFade()
				.into(giftGalleryHolder.mImageView);

//        categoryGridHolder.getmCategoryTv().setText(giftImageUrls.get(i).title);
//		categoryGridHolder.getmCategoryIc().setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));
//        categoryGridHolder.getmCategoryFontIcon().setText(fontIcons[i]);

//        giftGalleryHolder.mItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                ((BottomBarActivity) mContext).replaceFragment(
//                        GiftCategoryFilterFragment.newInstance(giftImageUrls.get(i)),
//                        GiftCategoryFilterFragment.class.getName()
//                );
//
//            }
//        });
	}

	@Override
	public int getItemCount() {

		return giftImageUrls != null ? giftImageUrls.size() : 0;

	}
}
