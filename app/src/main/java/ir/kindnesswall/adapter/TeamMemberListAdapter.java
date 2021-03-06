package ir.kindnesswall.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.holder.TeamMemberHolder;
import ir.kindnesswall.model.api.TeamMember;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class TeamMemberListAdapter extends RecyclerView.Adapter<TeamMemberHolder> {

	private ArrayList<TeamMember> members;
	private Context mContext;

	public TeamMemberListAdapter(Context context, ArrayList<TeamMember> members) {
		this.members = members;
		this.mContext = context;
	}

	@Override
	public TeamMemberHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_team_member, null);
		TeamMemberHolder mh = new TeamMemberHolder(v);
		return mh;

	}

	@Override
	public void onBindViewHolder(TeamMemberHolder teamMemberHolder, final int position) {

		teamMemberHolder.mNameTv.setText(members.get(position).name);
		teamMemberHolder.mAboutTv.setText(members.get(position).about);

		String image_url;
		if (members.get(position).imageUrl != null) {
			image_url = members.get(position).imageUrl;
		} else {
			image_url = "";
		}

		Glide
				.with(mContext)
				.load(image_url)
				.centerCrop()
				.placeholder(R.color.background)
				.crossFade()
				.into(teamMemberHolder.mAvatarIv);


//		if (members.get(position).telegramUrl!=null)
		teamMemberHolder.mTelegramIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(members.get(position).telegram==null || members.get(position).telegram.equals("")) return;

				Intent telegram = new Intent(
						Intent.ACTION_VIEW ,
						Uri.parse(members.get(position).telegram)
				);

				mContext.startActivity(telegram);
			}
		});

		teamMemberHolder.mLinkdinIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(members.get(position).linkedin==null || members.get(position).linkedin.equals("")) return;

				Intent browserIntent =
						new Intent(
								Intent.ACTION_VIEW,
								Uri.parse(members.get(position).linkedin)
						);

				mContext.startActivity(browserIntent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return (null != members ? members.size() : 0);
	}
}
