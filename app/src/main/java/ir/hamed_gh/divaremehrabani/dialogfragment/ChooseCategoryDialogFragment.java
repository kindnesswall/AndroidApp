package ir.hamed_gh.divaremehrabani.dialogfragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.adapter.ChooseCategoriesDialogAdapter;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.model.api.Category;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class ChooseCategoryDialogFragment extends DialogFragment implements ApiRequest.Listener {

    @Bind(R.id.choose_city_recyclerview)
    RecyclerView recyclerView;

    ArrayList<Category> categories = new ArrayList<>();
    ApiRequest apiRequest;
    private ChooseCategoriesDialogAdapter chooseCategoriesDialogAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.dialogfragment_choose_category, container, false);

        ButterKnife.bind(this, rootView);

        apiRequest = new ApiRequest(getActivity(),this);
        apiRequest.getCategories();

        chooseCategoriesDialogAdapter =
                new ChooseCategoriesDialogAdapter(getActivity(), categories);
        recyclerView.setAdapter(chooseCategoriesDialogAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onResponse(Call call, Response response) {
        ArrayList<Category> categories = (ArrayList<Category>) response.body();
        this.categories.addAll(categories);
        chooseCategoriesDialogAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}
