package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.fragment.GiftCategoryFilterFragment;
import ir.hamed_gh.divaremehrabani.holder.CategoryHolder;
import ir.hamed_gh.divaremehrabani.model.api.Category;

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
    private ArrayList<Category> categories;
    private FragmentActivity activity;

    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.mContext = context;
        this.categories = categories;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_category, null);
        CategoryHolder categoryHolder = new CategoryHolder(v);

        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(CategoryHolder categoryHolder, final int i) {

        categoryHolder.getmCategoryTv().setText(categories.get(i).title);
//		categoryHolder.getmCategoryIc().setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));
        categoryHolder.getmCategoryFontIcon().setText(fontIcons[i]);

        categoryHolder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BottomBarActivity) mContext).setFragment(
                        new GiftCategoryFilterFragment(),
                        GiftCategoryFilterFragment.class.getName()
                );

            }
        });
    }

    @Override
    public int getItemCount() {

        return categories !=null? categories.size(): 0;

    }
}
