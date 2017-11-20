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
import ir.kindnesswall.holder.ContactUsHolder;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsHolder> {

	private ArrayList<Integer> drawables  = new ArrayList<Integer>() {{
		add(R.drawable.github_box);
		add(R.drawable.facebook_box);
		add(R.drawable.gmail);
		add(R.drawable.instagram);
		add(R.drawable.telegram);
		add(R.drawable.website);
	}};

	private ArrayList<String> urls  = new ArrayList<String>() {{
		add("https://github.com/kindnesswall");
		add("https://www.facebook.com/profile.php?id=100018883545560");
		add("info@kindnesswall.ir");
		add("https://www.instagram.com/kindness_wall");
		add("https://telegram.me/Kindness_Wall_Admin");
		add("http://www.kindnesswall.ir");
	}};

	private Context mContext;

	public ContactUsAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public ContactUsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_contact_us, null);
		ContactUsHolder mh = new ContactUsHolder(v);
		return mh;

	}

	@Override
	public void onBindViewHolder(ContactUsHolder holder, final int position) {

		holder.mIv.setImageDrawable(mContext.getResources().getDrawable(drawables.get(position)));
		holder.mIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (position == 2){

					Intent email = new Intent(Intent.ACTION_SEND);
					email.putExtra(Intent.EXTRA_EMAIL, new String[]{urls.get(position)});
					email.putExtra(Intent.EXTRA_SUBJECT, "انتقاد/پیشنهاد");
					email.putExtra(Intent.EXTRA_TEXT, "از طرف اپ اندروید دیوار مهربانی");
					email.setType("message/rfc822");
					mContext.startActivity(Intent.createChooser(email, "Choose an Email client :"));

					return;
				}

				Intent telegram = new Intent(
						Intent.ACTION_VIEW ,
						Uri.parse(urls.get(position))
				);
				mContext.startActivity(telegram);
			}
		});

	}

	@Override
	public int getItemCount() {
		return (null != drawables ? drawables.size() : 0);
	}
}
