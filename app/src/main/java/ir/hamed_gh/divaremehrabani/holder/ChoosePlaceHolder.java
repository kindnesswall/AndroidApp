package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class ChoosePlaceHolder extends RecyclerView.ViewHolder {

	public View itemView;
	private TextView mPlaceNameTv;
	private TextView mURHereTxt;

	public ChoosePlaceHolder(View itemView) {
		super(itemView);

		this.itemView = itemView;
		mPlaceNameTv = (TextView) itemView.findViewById(R.id.rw_choose_place_name_tv);
		mURHereTxt = (TextView) itemView.findViewById(R.id.rw_choose_place_ur_here_tv);
	}

	public TextView getPlaceNameTv() {
		return mPlaceNameTv;
	}

	public TextView getURHereTxt() {
		return mURHereTxt;
	}
}
