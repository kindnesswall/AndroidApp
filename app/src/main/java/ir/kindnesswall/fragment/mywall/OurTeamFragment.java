package ir.kindnesswall.fragment.mywall;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

	private ArrayList<TeamMember> teamMembers = new ArrayList<>();
	private TeamMemberListAdapter adapter;
	private LinearLayoutManager linearLayoutManager;

	private View rootView;
	Context context;

	@Override
	protected void init() {
		super.init();

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

		return rootView;
	}

}
