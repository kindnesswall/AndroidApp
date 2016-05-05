package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.adapter.RecyclerViewAdapter;
import hamed_gh.ir.divaaremehrabani.helper.EndlessRecyclerViewScrollListener;
import hamed_gh.ir.divaaremehrabani.model.MyModel;

/**
 * Created by 5 on 02/21/2016.
 */
public class testFragment extends BaseFragment {

	private RecyclerView mRecyclerView;
	private RecyclerViewAdapter adapter;

	private ArrayList<MyModel> list = new ArrayList<>();
	private int pageNumber = 0;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);

        ButterKnife.bind(this, rootView);

		/* Initialize recyclerview */
	    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
	    adapter = new RecyclerViewAdapter(context, list );
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
