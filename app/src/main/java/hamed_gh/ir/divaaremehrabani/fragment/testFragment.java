package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
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
import hamed_gh.ir.divaaremehrabani.helper.Toasti;
import hamed_gh.ir.divaaremehrabani.model.Gallery;
import hamed_gh.ir.divaaremehrabani.model.Meta;
import hamed_gh.ir.divaaremehrabani.model.PhotoGalleryResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class testFragment extends BaseFragment {

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.message_textview)
	TextView mMessageTextView;

	@Bind(R.id.fragment_progressBar)
	ProgressView progressView;

	private RecyclerViewAdapter adapter;

	private ArrayList<Gallery> galleries = new ArrayList<>();
	private int pageNumber = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_information, container, false);

		ButterKnife.bind(this, rootView);
		init();

		Map<String, String> params = new HashMap<>();
		params.put("pageSize", "10");
		params.put("pageNo", "1");

		Call<PhotoGalleryResponse> call = ((MainActivity) context).service.getPhotoGallery(params);

		call.enqueue(new Callback<PhotoGalleryResponse>() {
			@Override
			public void onResponse(Call<PhotoGalleryResponse> call, Response<PhotoGalleryResponse> response) {
				Toasti.showS("onResponse");
				progressView.setVisibility(View.INVISIBLE);

				try {
					Meta meta = response.body().getMeta();
					if (meta.getErrorCode() == 1000) {
						galleries.addAll(response.body().getData().getGallery());
						adapter.notifyDataSetChanged();
						mRecyclerView.setVisibility(View.VISIBLE);
						mMessageTextView.setVisibility(View.INVISIBLE);
					}else {
						mMessageTextView.setVisibility(View.VISIBLE);
						mRecyclerView.setVisibility(View.INVISIBLE);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Call<PhotoGalleryResponse> call, Throwable t) {
				Toasti.showS("onFailure");
				progressView.setVisibility(View.INVISIBLE);
				mRecyclerView.setVisibility(View.INVISIBLE);
				mMessageTextView.setText("خطا در دریافت اطلاعات");
			}
		});

//		foo.things(ImmutableMap.of("foo", "bar", "kit", "kat")
		/* Initialize recyclerview */
		adapter = new RecyclerViewAdapter(context, galleries);
		mRecyclerView.setAdapter(adapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

		mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(new LinearLayoutManager(context)) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Toasti.showS("need more data, page: " + page + ", totalItemsCount: " + totalItemsCount);
				pageNumber++;
			}
		});

		return rootView;
	}
}
