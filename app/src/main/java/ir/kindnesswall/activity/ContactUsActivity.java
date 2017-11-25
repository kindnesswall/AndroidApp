package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.fragment.mywall.ContactUsFragment;

public class ContactUsActivity extends BaseActivity {

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), ContactUsActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);

		replaceFragment(
				new ContactUsFragment(), ContactUsFragment.class.getName()
		);

		mToolbarTitleTextView.setText("ارتباط با ما");

	}

}
