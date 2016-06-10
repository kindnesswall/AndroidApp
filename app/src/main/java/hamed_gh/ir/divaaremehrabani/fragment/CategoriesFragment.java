package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.adapter.CategoriesAdapter;

/**
 * Created by 5 on 02/21/2016.
 */
public class CategoriesFragment extends BaseFragment {

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	private CategoriesAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.recyclerview, container, false);

		ButterKnife.bind(this, rootView);
		init();

//		foo.things(ImmutableMap.of("foo", "bar", "kit", "kat")
		/* Initialize recyclerview */
		adapter = new CategoriesAdapter(context);
		mRecyclerView.setAdapter(adapter);
		mRecyclerView.setLayoutManager(new GridLayoutManager(context,2));

		return rootView;
	}
}
