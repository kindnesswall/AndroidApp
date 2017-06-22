package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.kindnesswall.R;
import ir.kindnesswall.dialogfragment.ChooseCategoryDialogFragment;
import ir.kindnesswall.holder.CategoryDialogHolder;
import ir.kindnesswall.model.api.Category;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ChooseCategoriesDialogAdapter extends RecyclerView.Adapter<CategoryDialogHolder> {

	private Context mContext;
	private ArrayList<Category> categories;
	private FragmentActivity activity;
	private ChooseCategoryDialogFragment dialogFragment;

	public ChooseCategoriesDialogAdapter(
			Context context,
			ChooseCategoryDialogFragment dialogFragment,
			ArrayList<Category> categories) {

		this.mContext = context;
		this.categories = categories;
		this.dialogFragment = dialogFragment;
	}

	@Override
	public CategoryDialogHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_choose_category, null);
		CategoryDialogHolder categoryHolder = new CategoryDialogHolder(v);

		return categoryHolder;
	}

	@Override
	public void onBindViewHolder(CategoryDialogHolder categoryHolder, final int i) {

		categoryHolder.getmCategoryTv().setText(categories.get(i).title);
//		categoryHolder.getmCategoryIc().setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));

		categoryHolder.mItemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//                AppController.storeString(Constants.CATEGORY_ID, categories.get(i).categoryId);
//                AppController.storeString(Constants.CATEGORY_NAME, categories.get(i).title);

				dialogFragment.onCategorySelected(categories.get(i));

			}
		});
	}

	@Override
	public int getItemCount() {

		return categories != null ? categories.size() : 0;

	}
}
