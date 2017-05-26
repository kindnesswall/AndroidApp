package ir.hamed_gh.divaremehrabani.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.customviews.textviews.TextViewIranSansRegular;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestToAGiftHolder extends RecyclerView.ViewHolder {

	public View itemView;

	public RelativeLayout rootLay;
	public TextViewIranSansRegular mPhoneTv;
	public ImageView mDenyIconIv, mAcceptIconIv, mCallIconIv, mSmsIconIv;

	public RequestToAGiftHolder(View itemView) {
		super(itemView);

		this.itemView = itemView;
		rootLay = (RelativeLayout) itemView.findViewById(R.id.root_lay);

		mPhoneTv = (TextViewIranSansRegular) itemView.findViewById(R.id.rw_phone_number_tv);

		mDenyIconIv = (ImageView) itemView.findViewById(R.id.rw_deny_icon_iv);
		mAcceptIconIv = (ImageView) itemView.findViewById(R.id.rw_accept_icon_iv);
		mCallIconIv = (ImageView) itemView.findViewById(R.id.rw_call_icon_iv);
		mSmsIconIv = (ImageView) itemView.findViewById(R.id.rw_sms_icon_iv);
	}
}
