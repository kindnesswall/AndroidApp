package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ir.kindnesswall.R;
import ir.kindnesswall.activity.BottomBarActivity;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.fragment.HomeFragment;
import ir.kindnesswall.fragment.category.CategoriesGridFragment;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.model.GetGiftPathQuery;
import ir.kindnesswall.model.GiftListModel;
import ir.kindnesswall.model.Place;
import ir.kindnesswall.model.api.Category;
import ir.kindnesswall.model.api.Gift;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ShowcaseMoreInfoHolder> implements ApiRequest.AdapterListener {

	private Context mContext;
	private ApiRequest apiRequest;
	private ArrayList<Category> categories;
	private LinearLayoutManager mLayoutManager;

	private final Map<Integer, GiftListModel> mGiftPositionMap = new HashMap<>();
	private String searchTxt = "";
	private Place place,region;

	public HomeCategoriesAdapter(Context context, ArrayList<Category> categories) {
		this.mContext = context;
		this.categories = categories;

		apiRequest = new ApiRequest(mContext, this);

		for (int i = 0; i < categories.size(); i++) {
			mGiftPositionMap.put(i, new GiftListModel());
		}
	}

	@Override
	public ShowcaseMoreInfoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_category_row, null);
		ShowcaseMoreInfoHolder showcaseMoreInfoHolder = new ShowcaseMoreInfoHolder(v);

		return showcaseMoreInfoHolder;
	}

	@Override
	public void onBindViewHolder(ShowcaseMoreInfoHolder showcaseMoreInfoHolder, final int position) {

		showcaseMoreInfoHolder.description.setText(categories.get(position).title);
		Glide
				.with(mContext)
				.load(categories.get(position).imageUrl)
				.centerCrop()
				.placeholder(R.color.background)
				.crossFade()
				.into(showcaseMoreInfoHolder.categoryImg);

		showcaseMoreInfoHolder.mRippleBtnMore.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
			@Override
			public void onComplete(RippleView rippleView) {
				((BottomBarActivity) mContext).replaceFragment(
						HomeFragment.newInstance(Constants.CATEGORY_PAGETYPE, categories.get(position)),
						HomeFragment.class.getName() + CategoriesGridFragment.class.getName()
				);
			}
		});



		mLayoutManager = new LinearLayoutManager(mContext);
		mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		mLayoutManager.setReverseLayout(true);

		showcaseMoreInfoHolder.mHorizontalRecycleView.setNestedScrollingEnabled(false);

		if (mGiftPositionMap.get(position).gifts.size() == 0) {
			mGiftPositionMap.get(position).showcaseMoreInfoAdapter
					= new ShowcaseMoreInfoAdapter(mContext, mGiftPositionMap.get(position).gifts);

			getGifts(
					position,
					mGiftPositionMap.get(position).start_index);
		}
		showcaseMoreInfoHolder.mHorizontalRecycleView.setAdapter(
				mGiftPositionMap.get(position).showcaseMoreInfoAdapter);

		showcaseMoreInfoHolder.mHorizontalRecycleView.setLayoutManager(mLayoutManager);

		//SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
		//TODO LinearSnapHelper sometimes cause crash, plz check why and fix it
//                (new LinearSnapHelper()).attachToRecyclerView(categoryRow.mHorizontalRecycleView);

//		showcaseMoreInfoHolder.mHorizontalRecycleView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
//			@Override
//			public void onLoadMore(int page, int totalItemsCount) {
//				getGifts(
//						position,
//						mGiftPositionMap.get(position).start_index);
//			}
//		});

	}

	private void getGifts(final int position,
	                      int startIndex) {

		apiRequest.getGifts(
				new GetGiftPathQuery(

//						(place == null ? AppController.getStoredString(Constants.MY_LOCATION_ID) : place.id),
						(place == null ? "0" : place.id),
						(region == null ? "0" : region.id),
						(categories.get(position).categoryId),
						startIndex + "",
						startIndex + Constants.LIMIT + "",
						searchTxt

				),
				position
		);

		mGiftPositionMap.get(position).start_index += Constants.LIMIT;
	}



	@Override
	public int getItemCount() {

		return categories != null ? categories.size() : 0;

	}

	@Override
	public void onResponse(Call call, Response response, int position) {

		ArrayList<Gift> gifts = (ArrayList<Gift>) response.body();
		mGiftPositionMap.get(position).gifts.addAll(gifts);

		mGiftPositionMap.get(position).showcaseMoreInfoAdapter.notifyDataSetChanged();

	}

	@Override
	public void onFailure(Call call, Throwable t) {

	}


	class ShowcaseMoreInfoHolder extends RecyclerView.ViewHolder {

		public int position;
		//TODO : change variable names & remove unused variables
		private TextView more;
		private RelativeLayout mMoreLayout;
		public TextView description;
		//TODO: its not a listview! change its name
		public RecyclerView mHorizontalRecycleView;
		public ImageView categoryImg;
		public RippleView mRippleBtnMore;

		public ShowcaseMoreInfoHolder(View view) {
			super(view);
			findViews(view);
//            setListeners();
		}

		public void setPosition(int position) {
			this.position = position;
		}

		void findViews(View view) {
			more = (TextView) view.findViewById(R.id.more);
			mMoreLayout = (RelativeLayout) view.findViewById(R.id.more_layout);
			description = (TextView) view.findViewById(R.id.description);
			mHorizontalRecycleView = (RecyclerView) view.findViewById(R.id.recycler_view_collection);
			mRippleBtnMore = (RippleView) view.findViewById(R.id.ripple_btn_more);
			categoryImg = (ImageView) view.findViewById(R.id.category_img);
		}

	}
}
