package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;


/**
 * Created by Hamed on 6/10/16.
 */
public class CategoryDialogHolder extends RecyclerView.ViewHolder {

    //	private ImageView mCategoryIc;
    private TextView mCategoryTv;
    public View mItemView;
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
