package ir.kindnesswall.fragment.mywall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.adapter.ContactUsAdapter;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.helper.MetricConverter;
import ir.kindnesswall.helper.RtlGridAutofitLayoutManager;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class ContactUsFragment extends BaseFragment {

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.fragment_progressBar)
	ProgressView fragmentProgressBar;

	@Bind(R.id.btn_lay)
	RelativeLayout BtnLay;

	@Bind(R.id.btn_tv)
	TextView btnTv;

	private ContactUsAdapter adapter;

	private View rootView;
	Context context;

	@Override
	protected void init() {
		super.init();

		adapter = new ContactUsAdapter(context);
		mRecyclerView.setAdapter(adapter);
		GridLayoutManager layoutManager =
				new RtlGridAutofitLayoutManager(AppController.getAppContext(), (int) MetricConverter.dp2px(context,80));
		mRecyclerView.setLayoutManager(layoutManager);

		mRecyclerView.setVisibility(View.VISIBLE);
		fragmentProgressBar.setVisibility(View.INVISIBLE);
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

		btnTv.setText("انتقادات و پیشنهادات");
		BtnLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent telegram = new Intent(
						Intent.ACTION_VIEW ,
						Uri.parse("https://telegram.me/Kindness_Wall_Admin")
				);
				startActivity(telegram);
			}
		});

		return rootView;
	}



}
