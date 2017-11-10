package ir.kindnesswall.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.helper.Toasti;
import ir.kindnesswall.holder.CharityHolder;
import ir.kindnesswall.model.Charity;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class CharityListAdapter extends RecyclerView.Adapter<CharityHolder> {

	private ArrayList<Charity> charities;
	private Context mContext;

	public CharityListAdapter(Context context, ArrayList<Charity> charities) {
		this.charities = charities;
		this.mContext = context;
	}

	@Override
	public CharityHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_charity, null);
		CharityHolder mh = new CharityHolder(v);
		return mh;

	}

	@Override
	public void onBindViewHolder(final CharityHolder charityHolder, final int position) {

		charityHolder.mNameTv.setText(charities.get(position).name);
		charityHolder.mAboutTv.setText(charities.get(position).about);
		charityHolder.mAvatarIv.setImageDrawable(
				mContext.getResources().getDrawable(charities.get(position).drawableResId)
		);

//		if (members.get(position).telegramUrl!=null)
		charityHolder.mTelegramIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(charities.get(position).telegramUrl==null || charities.get(position).telegramUrl.equals("")) {
					Toasti.showS("آدرس تلگرام موجود نمی‌باشد");
					return;
				}

				Intent telegram = new Intent(
						Intent.ACTION_VIEW ,
						Uri.parse(charities.get(position).telegramUrl)
				);

				mContext.startActivity(telegram);
			}
		});

		charityHolder.mWebsiteLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(charities.get(position).website==null || charities.get(position).website.equals("")) {
					Toasti.showS("آدرس وبسایت موجود نمی‌باشد");
					return;
				}

				Intent browserIntent =
						new Intent(
								Intent.ACTION_VIEW,
								Uri.parse(charities.get(position).website)
						);

				mContext.startActivity(browserIntent);
			}
		});

		charityHolder.mTelephoneLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				String tel = charities.get(position).telephone;

				if(tel==null || tel.equals("")) {
					Toasti.showS("شماره تلفن موجود نمی‌باشد");
					return;
				}

				String uri = "tel:" + tel;
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse(uri));
				mContext.startActivity(intent);

			}
		});
	}

	@Override
	public int getItemCount() {
		return (null != charities ? charities.size() : 0);
	}
}
