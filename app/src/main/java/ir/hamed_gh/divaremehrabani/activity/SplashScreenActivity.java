package ir.hamed_gh.divaremehrabani.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.app.AppController;
import ir.hamed_gh.divaremehrabani.app.Constants;
import ir.hamed_gh.divaremehrabani.dialogfragment.ChoosePlaceDialogFragment;
import ir.hamed_gh.divaremehrabani.interfaces.ChoosePlaceCallback;
import ir.hamed_gh.divaremehrabani.model.Place;

/**
 * Created by Hamed on 2/14/17.
 */

public class SplashScreenActivity extends AppCompatActivity implements ChoosePlaceCallback {

	/** Duration of wait **/
	private final int SPLASH_DISPLAY_LENGTH = 1000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
		if (AppController.getStoredString(Constants.MY_LOCATION_ID)!=null) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
                /* Create an Intent that will start the Menu-Activity. */
					Intent mainIntent = new Intent(SplashScreenActivity.this, BottomBarActivity.class);
					SplashScreenActivity.this.startActivity(mainIntent);
					SplashScreenActivity.this.finish();
				}
			}, SPLASH_DISPLAY_LENGTH);

		}else {

//			Bundle bundle = new Bundle();
//			bundle.putString(Constants.FROM_ACTIVITY, SplashScreenActivity.class.getName());

			FragmentManager fm = getSupportFragmentManager();
			ChoosePlaceDialogFragment choosePlaceDialogFragment = new ChoosePlaceDialogFragment();
//			choosePlaceDialogFragment.setArguments(bundle);

			choosePlaceDialogFragment.show(fm, ChoosePlaceDialogFragment.class.getName());
		}
	}

	@Override
	public void onPlaceSelected(Place place) {
		finish();
	}
}
