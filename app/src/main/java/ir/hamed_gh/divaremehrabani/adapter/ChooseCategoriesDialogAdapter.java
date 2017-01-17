package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.holder.CategoryDialogHolder;
import ir.hamed_gh.divaremehrabani.model.api.Category;

/**
 * Created by 5 on 3/8/2016.
 */
public class ChooseCategoriesDialogAdapter extends RecyclerView.Adapter<CategoryDialogHolder> {

    private Context mContext;
    private ArrayList<Category> categories;
    private FragmentActivity activity;

    public ChooseCategoriesDialogAdapter(Context context, ArrayList<Category> categories) {
        this.mContext = context;
        this.categories = categories;
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

            }
        });
    }

    @Override
    public int getItemCount() {

        return categories !=null? categories.size(): 0;

    }
}