package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.fragment.mywall.requests.MyRequestsFragment;

public class MyRequestsActivity extends AppCompatActivity {

	@Bind(R.id.search_imageview)
	ImageView searchIV;

	@Bind(R.id.toolbar_right_icon)
	ImageView backIV;

	@Bind(R.id.toolbar_title_textView)
	public TextView mToolbarTitleTextView;

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), MyRequestsActivity.class);
//		intent.putExtra(Constants.GIFT, gift);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_requests);
		ButterKnife.bind(this);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		searchIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(SearchActivity.createIntent());
			}
		});
		backIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		replaceFragment(
				new MyRequestsFragment(), MyRequestsFragment.class.getName()
		);
	}

	public void replaceFragment(Fragment fragment, String title) {
		try {

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			fragmentTransaction.replace(R.id.container_body, fragment, title);
			fragmentTransaction.addToBackStack(title);
			fragmentTransaction.commit();
			fragmentManager.executePendingTransactions();

		} catch (Exception e) {
			//Todo : when app is finishing and homefragment request is not cancled or other requests exists:
			// java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
		}
	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() <= 1) {
			finish();
		}else {
			super.onBackPressed();
		}
	}
}
