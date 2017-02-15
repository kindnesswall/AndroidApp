package ir.hamed_gh.divaremehrabani.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.BottomBarActivity;
import ir.hamed_gh.divaremehrabani.activity.SplashScreenActivity;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
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

    private DialogFragment dialogFragment;
    private Context mContext;
    private String fromActivity;
    private ArrayList<Place> places;
    private FragmentActivity activity;

    public ChooseCityAdapter(DialogFragment dialogFragment,
                             Context context,
                             String fromActivity,
                             ArrayList<Place> places) {

        this.dialogFragment = dialogFragment;
        this.mContext = context;
        this.fromActivity = fromActivity;
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

        chooseCityHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppController.storeString(Constants.LOCATION_ID, places.get(i).id);
                AppController.storeString(Constants.LOCATION_NAME, places.get(i).name);

                if (fromActivity!=null &&
                        fromActivity.equals(SplashScreenActivity.class.getName())) {

                    AppController.storeString(Constants.MY_LOCATION_ID, places.get(i).id);
                    AppController.storeString(Constants.MY_LOCATION_NAME, places.get(i).name);

                    Intent mainIntent = new Intent(mContext, BottomBarActivity.class);
                    mContext.startActivity(mainIntent);
                    ((Activity)mContext).finish();

                }else{

                    dialogFragment.dismiss();

                }


            }
        });

        chooseCityHolder.getCityNameTv().setText(places.get(i).name);
//		categoryHolder.getCityNameTv().setText(mContext.getResources().getText(categoriesTitleRes[i]));
//		categoryHolder.getmCategoryIc().setImageDrawable(mContext.getResources().getDrawable(iconRes[i]));

    }

    @Override
    public int getItemCount() {

        return places.size();

    }
}
