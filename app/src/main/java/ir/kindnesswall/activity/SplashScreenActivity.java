package ir.kindnesswall.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.dialogfragment.ChoosePlaceDialogFragment;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.helper.DeviceInfo;
import ir.kindnesswall.helper.UpdateChecker;
import ir.kindnesswall.interfaces.ChoosePlaceCallback;
import ir.kindnesswall.model.Place;
import ir.kindnesswall.model.api.output.UpdateOutput;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */

public class SplashScreenActivity extends AppCompatActivity implements ChoosePlaceCallback,ApiRequest.Listener  {

	/**
	 * Duration of wait
	 **/
	private final int SPLASH_DISPLAY_LENGTH = 1000;

	/**
	 * Called when the activity is first created.
	 */

	Context context;
	private int REQUEST_CODE_INTRO = 321;
	private ApiRequest apiRequest;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_splash);

		context = this;
		apiRequest = new ApiRequest(this, this);

		if (AppController.getStoredBoolean(Constants.SHOW_INTRO_BEFOR, false)){

			apiRequest.getUpdatedVersion();

		}else {
			startActivityForResult(AppIntro.createIntent(), REQUEST_CODE_INTRO);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_INTRO) {
			if (resultCode == RESULT_OK) {
				// Finished the intro
			} else {
				// Cancelled the intro. You can then e.g. finish this activity too.
			}
			apiRequest.getUpdatedVersion();
		}
	}

	@Override
	public void onCitySelected(Place city) {
		AppController.storeString(Constants.MY_LOCATION_ID, city.id);
		AppController.storeString(Constants.MY_LOCATION_NAME, city.name);

		Intent mainIntent = new Intent(this, BottomBarActivity.class);
		startActivity(mainIntent);

		finish();
	}

	@Override
	public void onRegionSelected(Place region) {

	}

	private void onUpdateVersionResponse(UpdateOutput updateOutput) {

		boolean isForcedUpdate;

		if (updateOutput.force_update != null && updateOutput.force_update.equalsIgnoreCase("true")) {
			isForcedUpdate = true;
		} else {
			isForcedUpdate = false;//todo use this
		}

		UpdateChecker updateChecker = new UpdateChecker(
				getResources().getString(R.string.app_name),
				updateOutput.version,
				updateOutput.apk_url,
				null,
				updateOutput.changes);

		if (!isForcedUpdate) {
//					callApiGetHomeByTagId();

			if (AppController.getStoredString(Constants.MY_LOCATION_ID) != null) {

				Intent mainIntent = new Intent(SplashScreenActivity.this, BottomBarActivity.class);
				SplashScreenActivity.this.startActivity(mainIntent);
				SplashScreenActivity.this.finish();

			} else {

				FragmentManager fm = getSupportFragmentManager();
				ChoosePlaceDialogFragment.newInstance()
						.show(fm, ChoosePlaceDialogFragment.class.getName());
			}

		}

		if (DeviceInfo.getAppVersionCode() < Integer.valueOf(updateChecker.mUpdateDetail.latestVersion)) {
			//Notify Update
			Intent[] intents = new Intent[1];
			intents[0] = Intent.makeMainActivity(new ComponentName(AppController.getAppContext(),
					BottomBarActivity.class));
			// intents[1] = new Intent(AppController.getAppContext(), HomeActivity.class);
			updateChecker.showUpdaterDialog(
					context,
					getString(R.string.update_to_new_version),
					getString(R.string.exist_new_version),
					updateOutput.changes,
					updateOutput.version,
					intents,
					isForcedUpdate);

			AppController.getInstance().setIsCheckedUpdate(true);

		}

	}

	@Override
	public void onResponse(Call call, Response response) {
		if (response.body() instanceof UpdateOutput) {
			onUpdateVersionResponse((UpdateOutput) response.body());
		}
	}

	@Override
	public void onFailure(Call call, Throwable t) {

	}
}
