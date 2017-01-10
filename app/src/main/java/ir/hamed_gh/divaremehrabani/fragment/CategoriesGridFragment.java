package ir.hamed_gh.divaremehrabani.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.CategoriesAdapter;
import ir.hamed_gh.divaremehrabani.model.api.Category;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class CategoriesGridFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CategoriesAdapter adapter;
    ArrayList<Category> categories = new ArrayList<>();
	String TAG = CategoriesGridFragment.class.getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);

        ButterKnife.bind(this, rootView);
        init();

//		foo.things(ImmutableMap.of("foo", "bar", "kit", "kat")
        /* Initialize recyclerview */
        adapter = new CategoriesAdapter(context,categories);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));

        apiRequest.getCategories();

        return rootView;
    }

    @Override
    public void onResponse(Call call, Response response) {
        super.onResponse(call, response);
	    Log.d(TAG, "onResponse");
	    ArrayList<Category> categories = (ArrayList<Category>) response.body();
        this.categories.addAll(categories);
        adapter.notifyDataSetChanged();
    }
}
