package ir.kindnesswall.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.activity.GiftDetailActivity;
import ir.kindnesswall.constants.SentRequestStatus;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.holder.SentRequestItemHolder;
import ir.kindnesswall.model.api.RequestModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class SentRequestAdapter extends RecyclerView.Adapter<SentRequestItemHolder> implements ApiRequest.AdapterTagListener {

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
		return Integer.valueOf(requestModels.get(position).toStatus);
	}

	@Override
	public SentRequestItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View v;

        if (viewType == SentRequestStatus.PENDING){
	        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sent_requests_pending, null);
        }else{
	        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sent_requests_else, null);
        }

		SentRequestItemHolder mh = new SentRequestItemHolder(v);
		return mh;
	}

	@Override
	public void onBindViewHolder(SentRequestItemHolder myHolder, final int i) {


		int status = getItemViewType(i);
		if (status == SentRequestStatus.ACCEPTED){

			myHolder.mItemStatusTv.setText("(درخواست شما پذیرفته شد)");
			myHolder.mItemStatusTv.setTextColor(mContext.getResources().getColor(R.color.green_700));
		}else if (status == SentRequestStatus.DONATED_TO_SOMEONE_ELSE){

			myHolder.mItemStatusTv.setText("(به فرد دیگری اهدا شد)");
			myHolder.mItemStatusTv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

		}else if (status == SentRequestStatus.PENDING){

			myHolder.btnLay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					apiRequest.deleteMyRequest(requestModels.get(i).giftId, i);
				}
			});

		}else if (status == SentRequestStatus.REJECTED) {

			myHolder.mItemStatusTv.setText("(درخواست شما رد شد)");
			myHolder.mItemStatusTv.setTextColor(mContext.getResources().getColor(R.color.register_gift_warning_text));

		}


		myHolder.mItemTitleTv.setText(requestModels.get(i).gift);
		myHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("Test", "onClick: ");
				GiftDetailActivity.start(mContext,requestModels.get(i).giftId);
			}
		});

	}

	@Override
	public int getItemCount() {
		return (null != requestModels ? requestModels.size() : 0);
	}

	@Override
	public void onResponse(Call call, Response response, int position, String tag) {
		requestModels.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeRemoved(position, getItemCount());
//		notifyDataSetChanged();
	}

	@Override
	public void onFailure(Call call, Throwable t, String tag) {

	}
}
