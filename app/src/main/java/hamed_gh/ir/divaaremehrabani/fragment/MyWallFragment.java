package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.activity.MainActivity;
import hamed_gh.ir.divaaremehrabani.adapter.RecyclerViewAdapter;
import hamed_gh.ir.divaaremehrabani.helper.EndlessRecyclerViewScrollListener;
import hamed_gh.ir.divaaremehrabani.model.Gallery;
import hamed_gh.ir.divaaremehrabani.model.Meta;
import hamed_gh.ir.divaaremehrabani.model.PhotoGalleryResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class MyWallFragment extends BaseFragment {

	@Bind(R.id.message_textview)
	TextView mMessageTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_my_wall, container, false);

		ButterKnife.bind(this, rootView);
		init();



		return rootView;
	}
}
