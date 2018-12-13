package ir.kindnesswall.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import ir.kindnesswall.R;
import ir.kindnesswall.constants.TapSellConstants;
import ir.tapsell.sdk.nativeads.TapsellNativeBannerManager;
import ir.tapsell.sdk.nativeads.TapsellNativeBannerViewManager;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class TapsellListCellAdHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	private RelativeLayout adContainer;
	private TapsellNativeBannerViewManager nativeBannerViewManager;
	private Context context;

	public TapsellListCellAdHolder(View itemView, Context context) {
		super(itemView);
		this.context = context;
		adContainer = itemView.findViewById(R.id.adContainer);
		nativeBannerViewManager = new TapsellNativeBannerManager.Builder()
				.setParentView(adContainer)
				.setContentViewTemplate(R.layout.cell_tapsell_ad)
				.inflateTemplate(context);
		itemView.setOnClickListener(this);
	}

	public void bindView(String id) {
		TapsellNativeBannerManager.bindAd(
				context,
				nativeBannerViewManager,
				TapSellConstants.ZoneID.NativeBanner,
				id);
	}


	@Override
	public void onClick(View view) {
		Button btn = adContainer.findViewById(R.id.tapsell_nativead_cta);
		btn.callOnClick();
	}
}
