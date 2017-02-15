package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.R;


/**
 * Created by Hamed on 6/10/16.
 */
public class ChooseCityHolder extends RecyclerView.ViewHolder {

    private TextView mCityNameTv;
    private TextView mURHereTxt;
    public View itemView;

    public ChooseCityHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        mCityNameTv = (TextView) itemView.findViewById(R.id.rw_choose_city_name_tv);
        mURHereTxt = (TextView) itemView.findViewById(R.id.rw_choose_city_ur_here_tv);
    }

    public TextView getCityNameTv() {
        return mCityNameTv;
    }

    public TextView getURHereTxt() {
        return mURHereTxt;
    }
}
