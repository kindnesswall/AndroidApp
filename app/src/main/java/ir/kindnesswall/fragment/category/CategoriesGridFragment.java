package ir.kindnesswall.fragment.category;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.kindnesswall.R;
import ir.kindnesswall.adapter.GridCategoriesAdapter;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.model.api.Category;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class CategoriesGridFragment extends BaseFragment {

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.fragment_progressBar)
	ProgressView mProgressView;
	ArrayList<Category> categories = new ArrayList<>();
	String TAG = CategoriesGridFragment.class.getName();
	private GridCategoriesAdapter adapter;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (rootView != null) {
			if (rootView.getParent() != null)
				((ViewGroup) rootView.getParent()).removeView(rootView);
			return rootView;
		}
		rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);

		ButterKnife.bind(this, rootView);
		init();

//		foo.things(ImmutableMap.of("foo", "bar", "kit", "kat")
        /* Initialize recyclerview */
		adapter = new GridCategoriesAdapter(context, categories);
		mRecyclerView.setAdapter(adapter);
		mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));

		apiRequest.getCategories();

		return rootView;
	}

	@Override
	public void onResponse(Call call, Response response) {
		super.onResponse(call, response);
		Log.d(TAG, "onResponse");

		mRecyclerView.setVisibility(View.VISIBLE);
		mProgressView.setVisibility(View.INVISIBLE);

		ArrayList<Category> categories = (ArrayList<Category>) response.body();
		this.categories.clear();
		this.categories.addAll(categories);
		adapter.notifyDataSetChanged();
	}
}
