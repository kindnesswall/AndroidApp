package ir.kindnesswall.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.activity.BottomBarActivity;
import ir.kindnesswall.adapter.GiftListAdapter;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.constants.TapSellConstants;
import ir.kindnesswall.customviews.edit_text.EditTextIranSans;
import ir.kindnesswall.customviews.textviews.TextViewDivarIcons;
import ir.kindnesswall.dialogfragment.HomeFilteringDialogFragment;
import ir.kindnesswall.helper.EndlessRecyclerViewScrollListener;
import ir.kindnesswall.helper.Toasti;
import ir.kindnesswall.interfaces.HomeFilteringCallback;
import ir.kindnesswall.model.GetGiftPathQuery;
import ir.kindnesswall.model.Place;
import ir.kindnesswall.model.api.Category;
import ir.kindnesswall.model.api.Gift;
import ir.tapsell.sdk.AdRequestCallback;
import ir.tapsell.sdk.nativeads.TapsellNativeBannerManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class HomeFragment extends BaseFragment implements HomeFilteringCallback {

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

//	@Bind(R.id.filter_txt)
//	TextViewIranSansRegular filterTxt;

	@Bind(R.id.search_btn)
	ImageView mSearchBtn;

	@Bind(R.id.search_backspace_btn)
	ImageView mSearchBackspaceBtn;

	@Bind(R.id.search_et)
	EditTextIranSans mSearchET;

	@Bind(R.id.search_textbox_lay)
	RelativeLayout mSearchTextboxLay;
	Place place;
	Place region;

	Category category;
	private String pageType;
	private String searchTxt = "";
	private GiftListAdapter adapter;
	private ArrayList<Gift> gifts = new ArrayList<>();
	private int pageNumber = 0;
	private int startIndex = 0;
	private LinearLayoutManager linearLayoutManager;
	private View rootView;
	private int count = 0;

	public static HomeFragment newInstance(String pageType, Category category) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putString(Constants.PAGETYPE, pageType);
		args.putParcelable(Constants.CATEGORY_PARCELABLE, category);
		fragment.setArguments(args);
		return fragment;
	}

	private void extractDataFromBundle() {
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			pageType = bundle.getString(Constants.PAGETYPE);
			switch (pageType) {
				case Constants.HOME_PAGETYPE:

					break;
				case Constants.CATEGORY_PAGETYPE:
					category = bundle.getParcelable(Constants.CATEGORY_PARCELABLE);
					if (category != null) {
//						filterTxt.setText("فیلتر شده بر اساس دسته‌بندی");
						setFilteringBtnColor(R.color.colorPrimary);
					}
					break;
				case Constants.SEARCH_PAGETYPE:
					mSearchTextboxLay.setVisibility(View.VISIBLE);
					break;
			}
		}
	}

	private void getTapsellAd() {
		TapsellNativeBannerManager.getAd(context, TapSellConstants.ZoneID.NativeBanner,
				new AdRequestCallback() {
					@Override
					public void onResponse(String[] strings) {
						onAdResponse(strings);
					}

					@Override
					public void onFailed(String s) {
						Log.e(getClass().getName(), "get ad fail");
					}
				});
	}

	private void onAdResponse(String[] adsId) {
		Gift gift = new Gift();

		gift.giftId = adsId[0];
		gift.isAd = true;

		gifts.add(gift);

		progressView.setVisibility(View.INVISIBLE);
		adapter.notifyDataSetChanged();
		mRecyclerView.setVisibility(View.VISIBLE);
		mMessageTextView.setVisibility(View.INVISIBLE);
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

		setBackspaceSearchtxtIcon();

		getGifts();
	}

	private void setBackspaceSearchtxtIcon() {
		Drawable myIcon = getResources().getDrawable(R.mipmap.ic_backspace_black_24dp);
		myIcon.setColorFilter(getResources().getColor(R.color.dark_white), PorterDuff.Mode.SRC_ATOP);
		mSearchBackspaceBtn.setImageDrawable(myIcon);
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
		rootView = inflater.inflate(R.layout.fragment_information, container, false);
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
//					filterTxt.setAlpha(0.5f);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					filterIc.setAlpha(1f);
//					filterTxt.setAlpha(1f);
				}
				return false;
			}
		});

		mFilterLayBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				HomeFilteringDialogFragment fragment = HomeFilteringDialogFragment.ShowME(
						category,
						place,
						region
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

		mSearchBackspaceBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSearchET.setText("");
				mSearchBackspaceBtn.setVisibility(View.INVISIBLE);
			}
		});

		mSearchET.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					mSearchBackspaceBtn.setVisibility(View.VISIBLE);
				} else {
					mSearchBackspaceBtn.setVisibility(View.INVISIBLE);
				}
			}
		});

		mSearchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					Toasti.showS("search");
					searchText();
					return true;
				}
				return false;
			}
		});

		mSearchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchText();
			}
		});
	}

	void searchText() {
		startIndex = 0;
		gifts.clear();
		adapter.notifyDataSetChanged();
		searchTxt = mSearchET.getText().toString();
		getGifts();
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
		if (gifts.isEmpty()){
			getTapsellAd();
		}
		if (gifts.size() > 0 && !gifts.get(gifts.size()-1).isAd){
			getTapsellAd();
		}

		apiRequest.getGifts(
				new GetGiftPathQuery(

//						(place == null ? AppController.getStoredString(Constants.MY_LOCATION_ID) : place.id),
						(place == null ? "0" : place.id),
						(region == null ? "0" : region.id),
						(category == null ? "0" : category.categoryId),
						startIndex + "",
						startIndex + Constants.LIMIT + "",
						searchTxt

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

		if (gifts.size() == 0) {
			mRecyclerView.setVisibility(View.INVISIBLE);
			mMessageTextView.setVisibility(View.VISIBLE);
			mMessageTextView.setText(getString(R.string.no_gift_found));

		} else {
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

	private void setFilteringBtnColor(int colorId) {
		filterIc.setTextColor(getContext().getResources().getColor(colorId));
//		filterTxt.setTextColor(getContext().getResources().getColor(colorId));
	}

	@Override
	public void onApplyFiltering(Place place, Place region, Category category) {
		this.place = place;
		this.category = category;
		this.region = region;

		progressView.setVisibility(View.VISIBLE);
		mRecyclerView.setVisibility(View.INVISIBLE);
		mMessageTextView.setVisibility(View.INVISIBLE);

		if (place != null || category != null) {
			setFilteringBtnColor(R.color.colorPrimary);
		}
		String filterText = "";
		if (place != null) {
			if (pageType.equals(Constants.HOME_PAGETYPE)) {
				((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("همه هدیه‌های " + place.name);
			}
			filterText += "فیلتر شده بر اساس محل";
		}else {
			((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("همه هدیه‌ها");
		}
		if (category != null) {
			if (filterText.equals("")) {
				filterText = "فیلتر شده بر اساس دسته‌بندی";
			} else {
				filterText += " / دسته‌بندی";
			}
		}else {
			setFilteringBtnColor(R.color.text_secondary);

			filterText = "فیلتر کردن";
		}

//		filterTxt.setText(filterText);

		gifts.clear();
		startIndex = 0;
		getGifts();
	}
}
