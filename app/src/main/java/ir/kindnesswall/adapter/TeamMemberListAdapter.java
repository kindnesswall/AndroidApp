package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.holder.TeamMemberHolder;
import ir.kindnesswall.model.TeamMember;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class TeamMemberListAdapter extends RecyclerView.Adapter<TeamMemberHolder> {

	String schoolId, childID;
	private ArrayList<TeamMember> members;
	private Context mContext;
	private FragmentActivity activity;

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
	public void onBindViewHolder(TeamMemberHolder myHolder, final int i) {

//	    myHolder.mTitleTv.setText(members.get(i).getGalleryId());

		//TODO load image of item
//		String image_url;
//		if (members.get(i).giftImages != null && members.get(i).giftImages.size() > 0) {
//			image_url = members.get(i).giftImages.get(0);
//		} else {
//			image_url = "";
//		}
//
//		Glide
//				.with(mContext)
//				.load(image_url)
//				.centerCrop()
//				.placeholder(R.color.background)
//				.crossFade()
//				.into(myHolder.getmItemIv());

//		myHolder.getGiftTitleTv().setText(members.get(i).title);
//		myHolder.getGiftLocationTv().setText(members.get(i).address);
//		myHolder.getGiftCreatedTimeTv().setText(members.get(i).createDateTime);

	}

	@Override
	public int getItemCount() {
		return (null != members ? members.size() : 0);
	}
}
