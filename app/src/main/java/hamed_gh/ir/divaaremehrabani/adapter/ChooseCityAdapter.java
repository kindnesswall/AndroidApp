package hamed_gh.ir.divaaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.holder.ChooseCityHolder;

/**
 * Created by 5 on 3/8/2016.
 */
public class ChooseCityAdapter extends RecyclerView.Adapter<ChooseCityHolder> {

	String schoolId, childID;
	private int[] iconRes = {
			R.drawable.ic_books,
			R.drawable.ic_jumper,
			R.drawable.ic_dining_room,
			R.drawable.ic_sofa
	};

	private int[] categoriesTitleRes = {
			R.string.book,
			R.string.clothes,
			R.string.food,
			R.string.accessories
	};

	private Context mContext;
	private FragmentActivity activity;

	public ChooseCityAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public ChooseCityHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_choose_city, null);
		ChooseCityHolder chooseCityHolder = new ChooseCityHolder(v);

		return chooseCityHolder;
	}

	@Override
	public void onBindViewHolder(ChooseCityHolder chooseCityHolder, final int i) {

//		categoryHolder.getmCategoryTv().setText(mContext.getResources().getText(categoriesTitleRes[i]));
//		categoryHolder.getmCategoryIc().setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));

	}

	@Override
	public int getItemCount() {

		return 20;

	}
}
