package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ir.hamed_gh.kindnesswall.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class CategoryDialogHolder extends RecyclerView.ViewHolder {

	public View mItemView;
	//	private ImageView mCategoryIc;
	private TextView mCategoryTv;

	public CategoryDialogHolder(View itemView) {
		super(itemView);

//		mCategoryIc = (ImageView) itemView.findViewById(R.id.cell_category_ic);
		mCategoryTv = (TextView) itemView.findViewById(R.id.rw_category_name_tv);
		mItemView = itemView;
	}

//	public ImageView getmCategoryIc() {
//		return mCategoryIc;
//	}

	public TextView getmCategoryTv() {
		return mCategoryTv;
	}
}
