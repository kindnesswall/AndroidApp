package ir.hamed_gh.divaremehrabani.helper;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public abstract class CallbackWithRetry<T> implements Callback<T> {
	private static final String TAG = CallbackWithRetry.class.getSimpleName();
	private final Call<T> call;
	private Context context;//Should Be Activity Context!

	public CallbackWithRetry(Call<T> call, Context context) {
		this.call = call;
		this.context = context;
	}

	@Override
	public void onResponse(Call<T> call, Response<T> response) {
		if (!call.isCanceled()) {
			if (!response.isSuccessful()) {
				onResponseDoExtraTaskWhenNotSuccessfull();
				ConnectionDetector.ShowServerProblemDialog(context, this);
				return;
			}
		}
	}

	public void onResponseDoExtraTaskWhenNotSuccessfull() {
		//Help : for doing st like progressview.setvisibility(View.GONE);
	}

	@Override
	public void onFailure(Call<T> call, Throwable t) {
		if (call.isCanceled()) {
			//nothing to do when reason is by our wish to cancle it
		} else {
			if (t instanceof IOException) {//network problem

				if (((Activity) context).hasWindowFocus()) {
					ConnectionDetector.ShowNetwrokConnectionProblemDialog(context, this);
				}

			}
		}
	}

	public void retry() {
		call.clone().enqueue(this);
	}
}