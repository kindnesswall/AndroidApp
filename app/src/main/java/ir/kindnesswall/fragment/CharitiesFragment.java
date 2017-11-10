package ir.kindnesswall.fragment;

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
import ir.kindnesswall.activity.BottomBarActivity;
import ir.kindnesswall.adapter.CharityListAdapter;
import ir.kindnesswall.model.Charity;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class CharitiesFragment extends BaseFragment {

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.btn_lay)
	RelativeLayout joinUsLay;

	private ArrayList<Charity> charities = new ArrayList<>();
	private CharityListAdapter adapter;
	private LinearLayoutManager linearLayoutManager;

	private View rootView;
	Context context;

	public static CharitiesFragment newInstance() {
		CharitiesFragment charitiesFragment = new CharitiesFragment();
		return charitiesFragment;
	}

	@Override
	protected void init() {
		super.init();

		((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("خیریه‌ها");

		createListOfTeamMembers();
		adapter = new CharityListAdapter(context, charities);
		mRecyclerView.setAdapter(adapter);
		linearLayoutManager = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(linearLayoutManager);

	}

	private void createListOfTeamMembers() {

		Charity charity = new Charity();
		charity.name = "بنياد خيريه مهرگان";
		charity.about = " كمك به كودكان در حال تحصيل بي سرپرست يا بد سرپرست";
		charity.telegramUrl = "https://telegram.me/nikbonyadanemehrgaan";
		charity.website = "http://www.mehrgaan.org";
		charity.telephone = "08337288191";
		charity.drawableResId = R.drawable.charity_mehregan;
		
		charities.add(charity);
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
		rootView = inflater.inflate(R.layout.fragment_charities, container, false);

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
