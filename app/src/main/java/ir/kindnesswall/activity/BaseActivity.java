package ir.kindnesswall.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import ir.kindnesswall.constants.Constants;

public class BaseActivity extends AppCompatActivity {

	@Bind(R.id.search_imageview)
	ImageView searchIV;

	@Bind(R.id.toolbar_right_icon)
	ImageView backIV;

	@Bind(R.id.toolbar_title_textView)
	public TextView mToolbarTitleTextView;

	@Bind(R.id.fab)
	FloatingActionButton fab;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		ButterKnife.bind(this);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (AppController.getStoredString(Constants.Authorization) != null) {
					startActivity(RegisterGiftActivity.createIntent());
				} else {
					startActivity(LoginActivity.createIntent());
				}
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
