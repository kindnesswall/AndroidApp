package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.activity.BottomBarActivity;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.fragment.HomeFragment;
import ir.kindnesswall.fragment.category.CategoriesGridFragment;
import ir.kindnesswall.holder.CategoryGridHolder;
import ir.kindnesswall.model.api.Category;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class GridCategoriesAdapter extends RecyclerView.Adapter<CategoryGridHolder> {

	private String[] fontIcons = {
			"\uE801",
			"\uE800",
			"\uE832",
			"\uE804"
	};

	private Context mContext;
	private ArrayList<Category> categories;

	public GridCategoriesAdapter(Context context, ArrayList<Category> categories) {
		this.mContext = context;
		this.categories = categories;
	}

	@Override
	public CategoryGridHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_category, null);
		CategoryGridHolder categoryGridHolder = new CategoryGridHolder(v);

		return categoryGridHolder;
	}

	@Override
	public void onBindViewHolder(CategoryGridHolder categoryGridHolder, final int i) {

		categoryGridHolder.getmCategoryTv().setText(categories.get(i).title);
		Glide
				.with(mContext)
				.load(categories.get(i).imageUrl)
				.centerCrop()
				.placeholder(R.color.background)
				.crossFade()
				.into(categoryGridHolder.mCategoryIc);

//		categoryGridHolder.mItemView.setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));
//        categoryGridHolder.getmCategoryFontIcon().setText(fontIcons[i]);

		categoryGridHolder.mItemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				((BottomBarActivity) mContext).replaceFragment(
						HomeFragment.newInstance(Constants.CATEGORY_PAGETYPE, categories.get(i)),
						HomeFragment.class.getName() + CategoriesGridFragment.class.getName()
				);

			}
		});
	}

	@Override
	public int getItemCount() {

		return categories != null ? categories.size() : 0;

	}
}
