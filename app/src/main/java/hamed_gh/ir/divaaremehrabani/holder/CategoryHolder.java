package hamed_gh.ir.divaaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hamed_gh.ir.divaaremehrabani.R;

/**
 * Created by Hamed on 6/10/16.
 */
public class CategoryHolder extends RecyclerView.ViewHolder {

	private ImageView mCategoryIc;
	private TextView mCategoryTv;


	public CategoryHolder(View itemView) {
		super(itemView);

		mCategoryIc = (ImageView)itemView.findViewById(R.id.cell_category_ic);
		mCategoryTv = (TextView) itemView.findViewById(R.id.cell_category_tv);
	}

	public ImageView getmCategoryIc() {
		return mCategoryIc;
	}

	public TextView getmCategoryTv() {
		return mCategoryTv;
	}
}
