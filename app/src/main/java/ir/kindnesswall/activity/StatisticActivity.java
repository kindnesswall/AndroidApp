package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;

import ir.kindnesswall.app.AppController;
import ir.kindnesswall.fragment.mywall.StatisticFragment;

public class StatisticActivity extends BaseActivity {

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), StatisticActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		replaceFragment(
				new StatisticFragment(), StatisticFragment.class.getName()
		);

		mToolbarTitleTextView.setText("آمار و ارقام");

	}

}
