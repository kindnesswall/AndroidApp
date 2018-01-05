package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.fragment.chat.ChatListFragment;

public class ChatActivity extends BaseActivity {

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), ChatActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);

		replaceFragment(
				new ChatListFragment(), ChatListFragment.class.getName()
		);
	}

	public void hideFab() {
		fab.setVisibility(View.INVISIBLE);
	}

	public void showFab(){
		fab.setVisibility(View.VISIBLE);
	}

}
