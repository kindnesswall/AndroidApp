package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ir.kindnesswall.R;

/**
 * Created by Hamed on 7/14/17.
 */

public class StatisticHolder extends RecyclerView.ViewHolder {

	public TextView mKeyTv, mValueTv;

	public StatisticHolder(View itemView) {
		super(itemView);

		mKeyTv = (TextView) itemView.findViewById(R.id.key_tv);
		mValueTv = (TextView) itemView.findViewById(R.id.value_tv);
	}
}
