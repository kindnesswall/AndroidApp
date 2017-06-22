package ir.kindnesswall.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ir.hamed_gh.kindnesswall.R;
import ir.kindnesswall.helper.ApiRequest;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class BaseFragment extends Fragment implements ApiRequest.Listener {

	protected Context context;
	protected AppCompatActivity mainActivity;
	protected ApiRequest apiRequest;
	private TextView mToolbarTitleTextView;

	protected void init() {
		context = getActivity();
		mainActivity = (AppCompatActivity) getActivity();

		mToolbarTitleTextView = (TextView) ((AppCompatActivity) context).findViewById(R.id.toolbar_title_textView);

		apiRequest = new ApiRequest(context, this);
	}

	@Override
	public void onResponse(Call call, Response response) {

	}

	@Override
	public void onFailure(Call call, Throwable t) {

	}
}
