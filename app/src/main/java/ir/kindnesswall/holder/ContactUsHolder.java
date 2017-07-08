package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ir.kindnesswall.R;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class ContactUsHolder extends RecyclerView.ViewHolder {

	public final ImageView mIv;
	public View itemView;

	public ContactUsHolder(View itemView) {
		super(itemView);

		this.itemView = itemView;

		mIv = (ImageView) itemView.findViewById(R.id.contact_iv);

	}

}
