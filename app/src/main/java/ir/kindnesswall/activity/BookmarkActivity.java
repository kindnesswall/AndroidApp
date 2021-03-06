package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;

import ir.kindnesswall.app.AppController;
import ir.kindnesswall.fragment.mywall.BookmarkFragment;

public class BookmarkActivity extends BaseActivity {

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), BookmarkActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		replaceFragment(
				new BookmarkFragment(), BookmarkFragment.class.getName()
		);

		mToolbarTitleTextView.setText("نشان شده‌ها");
	}

}
