package ir.kindnesswall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.fragment.BasicFragment;

public class BasicActivity extends AppCompatActivity {
  private static final String TAG = BasicActivity.class.getSimpleName();

  public static Intent createIntent(String uri) {
    Intent intent = new Intent(AppController.getAppContext(), BasicActivity.class);
//    intent.putExtra(Constants., uri);
    return intent;
  }

  public static Intent createIntent(Uri uri) {
    Intent intent = new Intent(AppController.getAppContext(), BasicActivity.class);
    intent.putExtra(Constants.URI, uri);
    return intent;
  }

  public static Intent createIntent(Activity activity) {
    return new Intent(activity, BasicActivity.class);
  }

  // Lifecycle Method ////////////////////////////////////////////////////////////////////////////

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_basic);

    Bundle bundle = getIntent().getExtras();
    Uri uri;
    if (bundle!= null){
      uri = bundle.getParcelable(Constants.URI);
      if(savedInstanceState == null){
        getSupportFragmentManager().beginTransaction().add(R.id.container, BasicFragment.newInstance(uri)).commit();
      }
    }

    // apply custom font
//    FontUtils.setFont(findViewById(R.id.root_layout));
    initToolbar();
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  @Override public boolean onSupportNavigateUp() {
    onBackPressed();
    return super.onSupportNavigateUp();
  }

  private void initToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
//    FontUtils.setTitle(actionBar, "Basic Sample");
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
  }

  public void startResultActivity(Uri uri) {

    if (isFinishing()) return;
    // Start ResultActivity
    Intent resultIntent = new Intent();
    resultIntent.putExtra(Constants.URI, uri);
    setResult(Activity.RESULT_OK, resultIntent);
    finish();

//    startActivity(ResultActivity.createIntent(this, uri));
  }
}
