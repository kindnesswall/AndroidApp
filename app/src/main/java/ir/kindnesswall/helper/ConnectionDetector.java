package ir.kindnesswall.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;

import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;


/**
 * Created by HamedGh on 3/8/2016.
 */
public class ConnectionDetector {

	public static boolean isConnectedToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) AppController.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}

	public static void ShowNetwrokConnectionProblemDialog(final Context activity, final CallbackWithRetry callbackWithRetry) {
		MaterialDialog.Builder builder = MaterialDialogBuilder.create(activity);
		final MaterialDialog dialog = builder
				.customView(R.layout.activity_no_internet_connection, false).show();

		final RippleView rippleBtnTry;
		rippleBtnTry = (RippleView) dialog.findViewById(R.id.ripple_btn_internet_retry_btn_cardview);

		rippleBtnTry.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
			@Override
			public void onComplete(RippleView rippleView) {
				if (ConnectionDetector.isConnectedToInternet()) {
					dialog.dismiss();
					callbackWithRetry.retry();
				} else {
					dialog.dismiss();
					dialog.show();
				}
			}
		});

		dialog.setCancelable(true);
	}

	public static void ShowServerProblemDialog(Context activity, final CallbackWithRetry callbackWithRetry) {
		MaterialDialog.Builder builder = MaterialDialogBuilder.create(activity);
		final MaterialDialog dialog = builder
				.customView(R.layout.fragment_server_problem_connection, false).show();
		final RippleView rippleBtnTry;
		rippleBtnTry = (RippleView) dialog.findViewById(R.id.ripple_btn_internet_retry_btn_cardview);
		rippleBtnTry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				rippleBtnTry.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
					@Override
					public void onComplete(RippleView rippleView) {
						if (ConnectionDetector.isConnectedToInternet()) {
							dialog.dismiss();
							callbackWithRetry.retry();
						} else {
							dialog.dismiss();
							dialog.show();
						}
					}
				});
			}
		});

		dialog.setCancelable(true);
	}

}