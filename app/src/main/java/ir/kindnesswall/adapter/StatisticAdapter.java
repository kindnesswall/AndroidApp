package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ir.kindnesswall.R;
import ir.kindnesswall.holder.StatisticHolder;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class StatisticAdapter extends RecyclerView.Adapter<StatisticHolder> {

	private LinkedHashMap<String, String> statistics;
	private Context mContext;

	public StatisticAdapter(Context context, LinkedHashMap<String, String> statistics) {
		this.statistics = statistics;
		this.mContext = context;
	}

	@Override
	public StatisticHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_statistic, null);
		StatisticHolder mh = new StatisticHolder(v);
		return mh;

	}

	@Override
	public void onBindViewHolder(StatisticHolder statisticHolder, final int position) {

		statisticHolder.mKeyTv.setText((new ArrayList<String>(statistics.keySet())).get(position));
		statisticHolder.mValueTv.setText((new ArrayList<String>(statistics.values())).get(position));
	}

	@Override
	public int getItemCount() {
		return (null != statistics ? statistics.size() : 0);
	}
}
