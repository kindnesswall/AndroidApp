package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class CategoryGridHolder extends RecyclerView.ViewHolder {

    //	private ImageView mCategoryIc;
    private TextView mCategoryTv;
    private TextView mCategoryFontIcon;
    public View mItemView;

    public CategoryGridHolder(View itemView) {
        super(itemView);

//		mCategoryIc = (ImageView) itemView.findViewById(R.id.cell_category_ic);
        mCategoryTv = (TextView) itemView.findViewById(R.id.cell_category_tv);
        mCategoryFontIcon = (TextView) itemView.findViewById(R.id.cell_category_font_icon);
        mItemView = itemView;
    }

    public TextView getmCategoryFontIcon() {
        return mCategoryFontIcon;
    }

//	public ImageView getmCategoryIc() {
//		return mCategoryIc;
//	}

    public TextView getmCategoryTv() {
        return mCategoryTv;
    }
}
