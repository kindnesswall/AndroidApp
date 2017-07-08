package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.kindnesswall.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class TeamMemberHolder extends RecyclerView.ViewHolder {

	public final ImageView mTelegramIv;
	public final ImageView mLinkdinIv;
	public TextView mNameTv, mAboutTv;
	public ImageView mAvatarIv;
	public View itemView;

	public TeamMemberHolder(View itemView) {
		super(itemView);

		this.itemView = itemView;

		mNameTv = (TextView) itemView.findViewById(R.id.member_name_tv);
		mAboutTv = (TextView) itemView.findViewById(R.id.memeber_about_tv);
		mAvatarIv = (ImageView) itemView.findViewById(R.id.member_iv);
		mTelegramIv = (ImageView) itemView.findViewById(R.id.telegram_iv);
		mLinkdinIv = (ImageView) itemView.findViewById(R.id.linkdin_iv);
	}

}
