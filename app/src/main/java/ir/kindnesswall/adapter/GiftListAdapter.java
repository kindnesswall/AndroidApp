package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.activity.GiftDetailActivity;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.holder.GiftHolder;
import ir.kindnesswall.model.api.Gift;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellShowOptions;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class GiftListAdapter extends RecyclerView.Adapter<GiftHolder> {

	String schoolId, childID;
	private ArrayList<Gift> gifts;
	private Context mContext;
	private FragmentActivity activity;

	public GiftListAdapter(Context context, ArrayList<Gift> gifts) {
		this.gifts = gifts;
		this.mContext = context;
	}

	@Override
	public GiftHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_ad_gift, null);
		GiftHolder mh = new GiftHolder(v);

		return mh;
	}

	@Override
	public void onBindViewHolder(GiftHolder myHolder, final int i) {

//	    myHolder.mTitleTv.setText(gifts.get(i).getGalleryId());

		//TODO load image of item
		String image_url;
		if (gifts.get(i).giftImages != null && gifts.get(i).giftImages.size() > 0) {
			image_url = gifts.get(i).giftImages.get(0);
		} else {
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
		myHolder.getGiftCreatedTimeTv().setText(gifts.get(i).description);
		myHolder.itemView.setOnClickListener(

				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						GiftDetailActivity.start(mContext, gifts.get(i));
					}
				}

		);
	}

	@Override
	public int getItemCount() {
		return (null != gifts ? gifts.size() : 0);
	}
}
