package hamed_gh.ir.divaaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.model.MyModel;

/**
 * Created by 5 on 3/8/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    private ArrayList<MyModel> list;

    private Context mContext;
    String schoolId, childID;
    private FragmentActivity activity;

	public class MyHolder extends RecyclerView.ViewHolder {

		public MyHolder(View itemView) {
			super(itemView);
		}
	}
    public RecyclerViewAdapter(Context context, ArrayList<MyModel> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, null);
	    MyHolder mh = new MyHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(MyHolder feedListRowHolder,final int i) {


    }

    @Override
    public int getItemCount() {

	    return (null != list ? list.size() : 0);

    }
}
