package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.kindnesswall.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class CharityHolder extends RecyclerView.ViewHolder {

	public final ImageView mTelegramIv;
	public final ImageView mTelephoneLink;
	public final ImageView mWebsiteLink;
	public TextView mNameTv, mAboutTv;
	public ImageView mAvatarIv;
	public View itemView;

	public CharityHolder(View itemView) {
		super(itemView);

		this.itemView = itemView;

		mNameTv = (TextView) itemView.findViewById(R.id.member_name_tv);
		mAboutTv = (TextView) itemView.findViewById(R.id.memeber_about_tv);
		mAvatarIv = (ImageView) itemView.findViewById(R.id.member_iv);
		mTelegramIv = (ImageView) itemView.findViewById(R.id.telegram_iv);
		mTelephoneLink = (ImageView) itemView.findViewById(R.id.telephone_iv);
		mWebsiteLink = (ImageView) itemView.findViewById(R.id.website_iv);
	}

}
