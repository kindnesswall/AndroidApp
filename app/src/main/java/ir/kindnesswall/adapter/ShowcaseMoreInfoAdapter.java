package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.activity.GiftDetailActivity;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.TapSellConstants;
import ir.kindnesswall.holder.GiftHolder;
import ir.kindnesswall.holder.TapsellListCellAdHolder;
import ir.kindnesswall.holder.TapsellListItemAdHolder;
import ir.kindnesswall.model.api.Gift;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellShowOptions;

/**
 * Created by 5 on 4/26/2016.
 */
public class ShowcaseMoreInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = ShowcaseMoreInfoAdapter.class.getSimpleName();
    //TODO make popupMenuList real
    private Context mContext;
    private ArrayList<Gift> gifts;

    public ShowcaseMoreInfoAdapter(final Context mContext, ArrayList<Gift> gifts) {
        this.mContext = mContext;
        this.gifts = gifts;
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
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_tapsell_ad, null);
            return new TapsellListCellAdHolder(v, mContext);
        }else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.cell_showcase_more_info, viewGroup, false);

            return new ViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder myHolder, final int i) {

        if (gifts.get(i).isAd){
            final TapsellListCellAdHolder tapsellAdHolder = (TapsellListCellAdHolder) myHolder;
            tapsellAdHolder.bindView(gifts.get(i).giftId);
        }else {
            //TODO load image of item
            ViewHolder holder = (ViewHolder) myHolder;


//        Picasso.with(mContext)
//                .load(gifts.get(i).giftImages.get(0))
//                .placeholder(R.color.white)
//                .error(R.color.white)
//                .into(holder.coverImageView);

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
                    .placeholder(R.color.white)
                    .crossFade()
                    .into(holder.coverImageView);

            holder.mCellNameMovieTv.setText(gifts.get(i).title);

            holder.mShowcaseMoreInfoCategoryTv.setText(gifts.get(i).address);

//        holder.mRippleCardview.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
//            @Override
//            public void onComplete(RippleView rippleMoviePic) {
//
//                mContext.startActivity(
//                        GiftDetailActivity.createIntent(gifts.get(i))
//                );
//
//            }
//
//        });

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GiftDetailActivity.start(mContext, gifts.get(i));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null != gifts ? gifts.size() : 0;
    }

    //TODO move this holder into holder directory and name it
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView coverImageView;
        public TextView mCellNameMovieTv, mCellPriceMovieTv, mShowcaseMoreInfoCategoryTv;
//        public RippleView mRippleCardview;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            coverImageView = (ImageView) view.findViewById(R.id.cell_movie_iv);
            mCellNameMovieTv = (TextView) view.findViewById(R.id.cell_name_movie_tv);
            mCellPriceMovieTv = (TextView) view.findViewById(R.id.cell_price_movie_tv);
            mShowcaseMoreInfoCategoryTv = (TextView) view.findViewById(R.id.showcase_more_info_category_tv);
//            mRippleCardview = (RippleView) view.findViewById(R.id.ripple_cell_showcase);

//            view.setOnClickListener(this);
        }

    }

}