package hamed_gh.ir.divaaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.holder.CategoryHolder;

/**
 * Created by 5 on 3/8/2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoryHolder> {

    String schoolId, childID;
    private int[] iconRes = {
            R.mipmap.ic_books,
            R.mipmap.ic_jumper,
            R.mipmap.ic_dining_room,
            R.mipmap.ic_sofa
    };

    private String[] fontIcons = {
            "\uE801",
            "\uE800",
            "\uE832",
            "\uE804"
    };

    private int[] categoriesTitleRes = {
            R.string.book,
            R.string.clothes,
            R.string.food,
            R.string.accessories
    };

    private Context mContext;
    private FragmentActivity activity;

    public CategoriesAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_category, null);
        CategoryHolder categoryHolder = new CategoryHolder(v);

        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(CategoryHolder categoryHolder, final int i) {

        categoryHolder.getmCategoryTv().setText(mContext.getResources().getText(categoriesTitleRes[i]));
//		categoryHolder.getmCategoryIc().setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));
        categoryHolder.getmCategoryFontIcon().setText(fontIcons[i]);
    }

    @Override
    public int getItemCount() {

        return 4;

    }
}
