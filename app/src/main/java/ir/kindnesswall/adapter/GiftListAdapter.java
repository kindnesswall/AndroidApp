package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.activity.GiftDetailActivity;
import ir.kindnesswall.holder.GiftHolder;
import ir.kindnesswall.holder.TapsellListItemAdHolder;
import ir.kindnesswall.model.api.Gift;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class GiftListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	String schoolId, childID;
	private ArrayList<Gift> gifts;
	private Context mContext;
	private FragmentActivity activity;

	public GiftListAdapter(Context context, ArrayList<Gift> gifts) {
		this.gifts = gifts;
		this.mContext = context;
	}

	@Override
	public int getItemViewType(int position) {
		if (gifts.get(position).isAd) {
			return 0;
		}else {
			return 1;
		}

	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v;
		if (i == 0){
			v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gift_tapsell_ad, null);
			return new TapsellListItemAdHolder(v, mContext);
		}else {
			v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gift, null);
			return new GiftHolder(v);
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {

//	    myHolder.mTitleTv.setText(gifts.get(i).getGalleryId());

		if (gifts.get(i).isAd){
			final TapsellListItemAdHolder tapsellAdHolder = (TapsellListItemAdHolder) holder;
			tapsellAdHolder.bindView(gifts.get(i).giftId);
		}else {
			//TODO load image of item
			GiftHolder myHolder = (GiftHolder)holder;

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
	}

	@Override
	public int getItemCount() {
		return (null != gifts ? gifts.size() : 0);
	}
}
