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

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.TeamMemberListAdapter;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.model.api.TeamMember;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class OurTeamFragment  extends BaseFragment {

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.btn_lay)
	RelativeLayout joinUsLay;

	@Bind(R.id.fragment_progressBar)
	ProgressView fragmentProgressBar;

	private ArrayList<TeamMember> teamMembers = new ArrayList<>();
	private TeamMemberListAdapter adapter;
	private LinearLayoutManager linearLayoutManager;

	private View rootView;
	Context context;

	@Override
	protected void init() {
		super.init();
		
		apiRequest.getTeamMembers();

		adapter = new TeamMemberListAdapter(context, teamMembers);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);

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


	@Override
	public void onResponse(Call call, Response response) {

		fragmentProgressBar.setVisibility(View.INVISIBLE);
		mRecyclerView.setVisibility(View.VISIBLE);

		teamMembers.addAll((ArrayList<TeamMember>) response.body());
		adapter.notifyDataSetChanged();

	}

}
