package hamed_gh.ir.divaaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.activity.BottomBarActivity;
import hamed_gh.ir.divaaremehrabani.adapter.RecyclerViewAdapter;
import hamed_gh.ir.divaaremehrabani.customviews.textviews.TextViewDivarIcons;
import hamed_gh.ir.divaaremehrabani.customviews.textviews.TextViewIranSansRegular;
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
public class CategoryFragment extends BaseFragment {

    @Bind(R.id.message_textview)
    TextView mMessageTextView;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.search_progressBar)
    ProgressView progressView;

    @Bind(R.id.filter_lay)
    RelativeLayout mFilterLayBtn;

    @Bind(R.id.filter_ic)
    TextViewDivarIcons filterIc;

    @Bind(R.id.filter_txt)
    TextViewIranSansRegular filterTxt;

    private RecyclerViewAdapter adapter;

    private ArrayList<Gallery> galleries = new ArrayList<>();
    private int pageNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        ButterKnife.bind(this, rootView);
        init();

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

        mFilterLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ChooseCityDialogFragment chooseCityDialogFragment = new ChooseCityDialogFragment();
                chooseCityDialogFragment.show(fm, "fragment_name");
            }
        });

        return rootView;
    }

    void sendRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");
        params.put("pageNo", "1");

        Call<PhotoGalleryResponse> call = ((BottomBarActivity) context).service.getPhotoGallery(params);

        call.enqueue(new Callback<PhotoGalleryResponse>() {
            @Override
            public void onResponse(Call<PhotoGalleryResponse> call, Response<PhotoGalleryResponse> response) {
                progressView.setVisibility(View.INVISIBLE);

                try {
                    Meta meta = response.body().getMeta();
                    if (meta.getErrorCode() == 1000) {
                        galleries.addAll(response.body().getData().getGallery());
                        adapter.notifyDataSetChanged();
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mMessageTextView.setVisibility(View.INVISIBLE);
                    } else {
                        mMessageTextView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.INVISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PhotoGalleryResponse> call, Throwable t) {
                progressView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mMessageTextView.setText("خطا در دریافت اطلاعات");
            }
        });
    }
}
