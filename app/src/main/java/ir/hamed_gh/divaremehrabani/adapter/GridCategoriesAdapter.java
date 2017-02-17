package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.fragment.HomeFragment;
import ir.hamed_gh.divaremehrabani.fragment.category.CategoriesGridFragment;
import ir.hamed_gh.divaremehrabani.holder.CategoryGridHolder;
import ir.hamed_gh.divaremehrabani.model.api.Category;

/**
 * Created by 5 on 3/8/2016.
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
//		categoryGridHolder.getmCategoryIc().setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));
		categoryGridHolder.getmCategoryFontIcon().setText(fontIcons[i]);

		categoryGridHolder.mItemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				((BottomBarActivity) mContext).setFragment(
						HomeFragment.newInstance(categories.get(i)),
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
