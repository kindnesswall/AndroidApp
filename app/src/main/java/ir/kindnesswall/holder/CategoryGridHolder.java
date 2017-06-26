package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.kindnesswall.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class CategoryGridHolder extends RecyclerView.ViewHolder {

	public ImageView mCategoryIc;
	//    private TextView mCategoryFontIcon;
	public View mItemView;
	private TextView mCategoryTv;

	public CategoryGridHolder(View itemView) {
		super(itemView);

		mCategoryIc = (ImageView) itemView.findViewById(R.id.cell_category_ic);
		mCategoryTv = (TextView) itemView.findViewById(R.id.cell_category_tv);
//        mCategoryFontIcon = (TextView) itemView.findViewById(R.id.cell_category_font_icon);
		mItemView = itemView;
	}

	public TextView getmCategoryTv() {
		return mCategoryTv;
	}
}
