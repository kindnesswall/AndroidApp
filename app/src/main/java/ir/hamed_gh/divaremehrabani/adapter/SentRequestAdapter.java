package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.holder.SentRequestItemHolder;
import ir.hamed_gh.divaremehrabani.model.api.RequestModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class SentRequestAdapter extends RecyclerView.Adapter<SentRequestItemHolder> implements ApiRequest.AdapterListener {

	private final int WAITING = 0, ACCEPTED = 1, REJECTED = 2, DONATED = 3;
	String schoolId, childID;
	private ArrayList<RequestModel> requestModels;
	private Context mContext;
	private FragmentActivity activity;
	private ApiRequest apiRequest;

	public SentRequestAdapter(Context context, ArrayList<RequestModel> requestModels) {
		this.requestModels = requestModels;
		this.mContext = context;
		apiRequest = new ApiRequest(context, this);
	}

	@Override
	public int getItemViewType(int position) {
		return position % 2;
	}

	@Override
	public SentRequestItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sent_requests, null);
		SentRequestItemHolder mh = new SentRequestItemHolder(v);

//        switch (viewType){
//            case 0:
//                mh.rootLay.setBackgroundColor(mContext.getResources().getColor(R.color.lime_A700));
//                break;
//            case 1:
//                mh.rootLay.setBackgroundColor(mContext.getResources().getColor(R.color.orange_A400));
//                break;
//        }

		return mh;
	}

	@Override
	public void onBindViewHolder(SentRequestItemHolder myHolder, final int i) {

		myHolder.mItemTitleTv.setText(requestModels.get(i).gift);
		myHolder.btnLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				apiRequest.deleteMyRequest(requestModels.get(i).giftId, i);
			}
		});

	}

	@Override
	public int getItemCount() {
		return (null != requestModels ? requestModels.size() : 0);
	}

	@Override
	public void onResponse(Call call, Response response, int position) {
		requestModels.remove(position);
		notifyDataSetChanged();
	}

	@Override
	public void onFailure(Call call, Throwable t) {

	}
}
