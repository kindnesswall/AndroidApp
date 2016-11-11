package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hamed_gh.ir.divaaremehrabani.R;
import ir.hamed_gh.divaremehrabani.holder.ChooseCityHolder;
import ir.hamed_gh.divaremehrabani.model.Place;

/**
 * Created by 5 on 3/8/2016.
 */
public class ChooseCityAdapter extends RecyclerView.Adapter<ChooseCityHolder> {

    String schoolId, childID;
    private int[] iconRes = {
            R.mipmap.ic_books,
            R.mipmap.ic_jumper,
            R.mipmap.ic_dining_room,
            R.mipmap.ic_sofa
    };

    private int[] categoriesTitleRes = {
            R.string.book,
            R.string.clothes,
            R.string.food,
            R.string.accessories
    };

    private Context mContext;
    private ArrayList<Place> places;
    private FragmentActivity activity;

    public ChooseCityAdapter(Context context, ArrayList<Place> places) {
        this.mContext = context;
        this.places = places;
    }

    @Override
    public ChooseCityHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_choose_city, null);
        ChooseCityHolder chooseCityHolder = new ChooseCityHolder(v);

        return chooseCityHolder;
    }

    @Override
    public void onBindViewHolder(ChooseCityHolder chooseCityHolder, final int i) {

        chooseCityHolder.getCityNameTv().setText(places.get(i).name);
//		categoryHolder.getCityNameTv().setText(mContext.getResources().getText(categoriesTitleRes[i]));
//		categoryHolder.getmCategoryIc().setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));

    }

    @Override
    public int getItemCount() {

        return places.size();

    }
}
