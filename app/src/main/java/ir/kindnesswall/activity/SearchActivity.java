package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.interfaces.HomeFilteringCallback;
import ir.kindnesswall.model.Place;
import ir.kindnesswall.model.api.Category;

public class SearchActivity extends AppCompatActivity implements HomeFilteringCallback {

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), SearchActivity.class);
//		intent.putExtra(Constants.GIFT, gift);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
	}

	@Override
	public void onApplyFiltering(Place place, Place region, Category category) {

	}

	@Override
	public void onPointerCaptureChanged(boolean hasCapture) {

	}
}
