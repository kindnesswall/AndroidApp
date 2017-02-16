package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.dialogfragment.ChoosePlaceDialogFragment;
import ir.hamed_gh.divaremehrabani.holder.ChoosePlaceHolder;
import ir.hamed_gh.divaremehrabani.model.Place;

/**
 * Created by 5 on 3/8/2016.
 */
public class ChoosePlaceAdapter extends RecyclerView.Adapter<ChoosePlaceHolder> {

    private ChoosePlaceDialogFragment dialogFragment;
    private Context mContext;
//    private String fromActivity;
    private ArrayList<Place> places;
    private FragmentActivity activity;

    public ChoosePlaceAdapter(ChoosePlaceDialogFragment dialogFragment,
                              Context context,
                              ArrayList<Place> places) {

        this.dialogFragment = dialogFragment;
        this.mContext = context;
//        this.fromActivity = fromActivity;
        this.places = places;
    }

    @Override
    public ChoosePlaceHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_choose_place, null);
        ChoosePlaceHolder choosePlaceHolder = new ChoosePlaceHolder(v);

        return choosePlaceHolder;
    }

    @Override
    public void onBindViewHolder(ChoosePlaceHolder choosePlaceHolder, final int i) {

        choosePlaceHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                else{

                    dialogFragment.onPlaceSelected(places.get(i));

//                }

            }
        });

        choosePlaceHolder.getPlaceNameTv().setText(places.get(i).name);

    }

    @Override
    public int getItemCount() {

        return places.size();

    }
}
