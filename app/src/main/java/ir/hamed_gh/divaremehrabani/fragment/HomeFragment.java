package ir.hamed_gh.divaremehrabani.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.adapter.GiftListAdapter;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.customviews.textviews.TextViewDivarIcons;
import ir.hamed_gh.divaremehrabani.customviews.textviews.TextViewIranSansRegular;
import ir.hamed_gh.divaremehrabani.dialogfragment.HomeFilteringDialogFragment;
import ir.hamed_gh.divaremehrabani.helper.EndlessRecyclerViewScrollListener;
import ir.hamed_gh.divaremehrabani.interfaces.HomeFilteringCallback;
import ir.hamed_gh.divaremehrabani.model.GetGiftPathQuery;
import ir.hamed_gh.divaremehrabani.model.Place;
import ir.hamed_gh.divaremehrabani.model.api.Category;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 5 on 02/21/2016.
 */
public class HomeFragment extends BaseFragment implements HomeFilteringCallback{

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.message_textview)
    TextView mMessageTextView;

    @Bind(R.id.fragment_progressBar)
    ProgressView progressView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.filter_lay)
    RelativeLayout mFilterLayBtn;

    @Bind(R.id.filter_ic)
    TextViewDivarIcons filterIc;

    @Bind(R.id.filter_txt)
    TextViewIranSansRegular filterTxt;

    private GiftListAdapter adapter;

    private ArrayList<Gift> gifts = new ArrayList<>();
    private int pageNumber = 0;

    private int startIndex = 0;
    private LinearLayoutManager linearLayoutManager;

	Place place;
	Category category;

	public static HomeFragment newInstance(Category category) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putParcelable(Constants.CATEGORY, category);
		fragment.setArguments(args);
		return fragment;
	}

	private void extractDataFromBundle() {
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			category = bundle.getParcelable(Constants.CATEGORY);
			filterTxt.setText("فیلتر شده بر اساس دسته‌بندی");
			setFilteringBtnColor(R.color.colorPrimary);

		}
	}

    @Override
    protected void init() {
        super.init();

	    extractDataFromBundle();
        //		foo.things(ImmutableMap.of("foo", "bar", "kit", "kat")
        /* Initialize recyclerview */
        adapter = new GiftListAdapter(context, gifts);
        mRecyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        getGifts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, rootView);

        init();
        setListeners();

        return rootView;
    }

    private void setListeners() {

        mFilterLayBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    filterIc.setAlpha(0.5f);
                    filterTxt.setAlpha(0.5f);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    filterIc.setAlpha(1f);
                    filterTxt.setAlpha(1f);
                }
                return false;
            }
        });

        mFilterLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

	            HomeFilteringDialogFragment fragment = HomeFilteringDialogFragment.ShowME(
			            getActivity().getSupportFragmentManager(),
			            category,
			            place
	            );

	            fragment.setTargetFragment(HomeFragment.this, 0);
	            fragment.show(getActivity().getSupportFragmentManager(),
			            HomeFragment.class.getName());

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh gifts
                refreshItems();
            }
        });

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Toasti.showS("need more data, page: " + page + ", totalItemsCount: " + totalItemsCount);
                pageNumber++;
                getGifts();
            }
        });

    }

    void refreshItems() {
        // Load gifts
        // ...

        pageNumber = 1;
        gifts.clear();

        startIndex = 0;

        getGifts();

        // Load complete
        onItemsLoadComplete();
    }

    private void getGifts() {
        apiRequest.getGifts(
                new GetGiftPathQuery(
		                (place==null? AppController.getStoredString(Constants.MY_LOCATION_ID): place.id),
                        startIndex + "",
                        startIndex + Constants.LIMIT + "",
		                (category==null? null: category.categoryId),
                        null
                )
        );

        startIndex += Constants.LIMIT;
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResponse(Call call, Response response) {
        progressView.setVisibility(View.INVISIBLE);

        List<Gift> responseGifts = (List<Gift>) response.body();
        gifts.addAll(responseGifts);

	    if (gifts.size()==0){
		    mRecyclerView.setVisibility(View.INVISIBLE);
		    mMessageTextView.setVisibility(View.VISIBLE);
		    mMessageTextView.setText(getString(R.string.no_gift_found));

	    }else {
		    adapter.notifyDataSetChanged();
		    mRecyclerView.setVisibility(View.VISIBLE);
		    mMessageTextView.setVisibility(View.INVISIBLE);
	    }

    }

    @Override
    public void onFailure(Call call, Throwable t) {
        progressView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mMessageTextView.setText("خطا در دریافت اطلاعات");
    }

	private void setFilteringBtnColor(int colorId){
		filterIc.setTextColor(getContext().getResources().getColor(colorId));
		filterTxt.setTextColor(getContext().getResources().getColor(colorId));
	}

	@Override
	public void onApplyFiltering(Place place, Category category) {
		this.place = place;
		this.category = category;

		progressView.setVisibility(View.VISIBLE);
		mRecyclerView.setVisibility(View.INVISIBLE);
		mMessageTextView.setVisibility(View.INVISIBLE);
		if (place!=null || category != null){
			setFilteringBtnColor(R.color.colorPrimary);
		}
		String filterText = "";
		if (place != null){
			((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("همه هدیه‌های " + place.name);
			filterText += "فیلتر شده بر اساس محل";
		}
		if (category!=null){
			if (filterText.equals("")){
				filterText = "فیلتر شده بر اساس دسته‌بندی";
			}else {
				filterText += " / دسته‌بندی";
			}
		}

		if (place == null && category == null){
			setFilteringBtnColor(R.color.text_secondary);


			filterText = "فیلتر کردن";

			((BottomBarActivity) getActivity()).
					mToolbarTitleTextView.setText("همه هدیه‌های " +
					AppController.getStoredString(Constants.MY_LOCATION_NAME));

		}

		filterTxt.setText(filterText);

		gifts.clear();
		startIndex = 0;
		getGifts();
	}
}
