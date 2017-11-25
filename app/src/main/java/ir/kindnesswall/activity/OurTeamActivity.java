package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.fragment.mywall.OurTeamFragment;

public class OurTeamActivity extends BaseActivity {

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), OurTeamActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);

		replaceFragment(
				new OurTeamFragment(), OurTeamFragment.class.getName()
		);

		mToolbarTitleTextView.setText("تیم ما");

	}

}
