package ir.kindnesswall.fragment.mywall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.TeamMemberListAdapter;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.model.TeamMember;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class OurTeamFragment  extends BaseFragment {

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.btn_lay)
	RelativeLayout joinUsLay;

	private ArrayList<TeamMember> teamMembers = new ArrayList<>();
	private TeamMemberListAdapter adapter;
	private LinearLayoutManager linearLayoutManager;

	private View rootView;
	Context context;

	@Override
	protected void init() {
		super.init();

		createListOfTeamMembers();
		adapter = new TeamMemberListAdapter(context, teamMembers);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);

	}

	private void createListOfTeamMembers() {

		TeamMember teamMember1 = new TeamMember();
		teamMember1.name = "حامد قدیریان";
		teamMember1.about = "بنیان‌گذار، توسعه دهنده اندروید، IOS";
		teamMember1.telegramUrl = "https://telegram.me/Hamed_Ghadirian";
		teamMember1.linkdinUrl = "https://www.linkedin.com/in/hamedghadirian";
		teamMember1.drawableResId = R.drawable.hamed;

		teamMembers.add(teamMember1);

		TeamMember teamMember2 = new TeamMember();
		teamMember2.name = "علی دهقان";
		teamMember2.about = "بنیان‌گذار، توسعه دهنده Net.";
		teamMember2.telegramUrl = "https://telegram.me/ali1d1";
		teamMember2.linkdinUrl = "https://www.linkedin.com/in/ali-dehqan-b69b2056/";
		teamMember2.drawableResId = R.drawable.ali;

		teamMembers.add(teamMember2);

		TeamMember teamMember3 = new TeamMember();
		teamMember3.name = "امیرحسین زکی‌زاده";
		teamMember3.about = "توسعه‌دهنده IOS";
		teamMember3.telegramUrl = "https://telegram.me/amirhz72";
		teamMember3.linkdinUrl = "";
		teamMember3.drawableResId = R.drawable.amirhossein;

		teamMembers.add(teamMember3);

		TeamMember teamMember4 = new TeamMember();
		teamMember4.name = "مهرشاد نجار";
		teamMember4.about = "طراح رابط کاربری";
		teamMember4.telegramUrl = "https://telegram.me/Mehrshad_na";
		teamMember4.linkdinUrl = "https://www.linkedin.com/in/mehrshad-najar";
		teamMember4.drawableResId = R.drawable.mehrshad;

		teamMembers.add(teamMember4);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (rootView != null) {
			if (rootView.getParent() != null)
				((ViewGroup) rootView.getParent()).removeView(rootView);
			return rootView;
		}
		rootView = inflater.inflate(R.layout.fragment_our_team, container, false);

		context = getContext();

		ButterKnife.bind(this, rootView);
		init();

		joinUsLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/Kindness_Wall_Admin"));
				startActivity(telegram);
			}
		});

		return rootView;
	}



}
