package hamed_gh.ir.divaaremehrabani.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.IOException;

import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.app.AppController;
import hamed_gh.ir.divaaremehrabani.app.RestAPI;
import hamed_gh.ir.divaaremehrabani.app.URIs;
import hamed_gh.ir.divaaremehrabani.helper.MaterialDialogBuilder;
import hamed_gh.ir.divaaremehrabani.helper.MetricConverter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hamed on 4/3/16.
 */
public class BaseActivity extends AppCompatActivity {

	public RestAPI service;

	public enum HowToBack{
        HOME,
        NOTHING,
        BACK
    }

    public static HowToBack howToBack;

    private static String TAG = MainActivity.class.getSimpleName();

    protected Context context;
    protected MainActivity mainActivity;
    public AccountHeader headerResult;

    private RelativeLayout toolbarLayout;
    protected TextView mToolbarTitleTextView;
    private ImageView mFirstIV;
    private ImageView mSecondIV;
    public Drawer drawer;
    protected int headerBgColor;
    private Toolbar mToolbar;
    private boolean showDrawerMenu = false;

    public PrimaryDrawerItem logoutDrawerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//	    OkHttpClient httpClient = new OkHttpClient();
	    OkHttpClient httpClient = new OkHttpClient.Builder()
			    .addInterceptor(
					    new Interceptor() {
						    @Override
						    public Response intercept(Interceptor.Chain chain) throws IOException {
							    Request request = chain.request().newBuilder()
							    .addHeader("token", "s:s").build();
							    return chain.proceed(request);
						    }
					    }).build();

//	    httpClient.networkInterceptors().add(new Interceptor() {
//		    @Override
//		    public Response intercept(Chain chain) throws IOException {
//			    Request request = chain.request().newBuilder().addHeader("token", "s:s").build();
//			    return chain.proceed(request);
//		    }
//	    });

//	    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).client(httpClient).build();

	    //Creating Rest Services
	    Retrofit retrofit = new Retrofit.Builder()
			    .baseUrl(URIs.API)
			    .addConverterFactory(GsonConverterFactory.create())
			    .client(httpClient).build();

	    service = retrofit.create(RestAPI.class);

    }

    public boolean showingDrawerMenu() {
        return showDrawerMenu;
    }

    public void setShowDrawerMenu(boolean showDrawerMenu) {
        this.showDrawerMenu = showDrawerMenu;
    }

    protected void init(int layoutResID) {

        context = this;

	    setContentView(layoutResID);

	    // -- set Toolbar ---
        toolbarLayout = (RelativeLayout) findViewById(R.id.toolbar);
        mToolbar = (Toolbar) toolbarLayout.findViewById(R.id.main_toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {

        }

        mToolbarTitleTextView = (TextView) toolbarLayout.findViewById(R.id.toolbar_title_textView);

        mSecondIV = (ImageView) toolbarLayout.findViewById(R.id.toolbar_second_iv);
        mFirstIV = (ImageView) toolbarLayout.findViewById(R.id.toolbar_first_iv);
    }

    protected void setDrawer() {

        // -- set Drawer --

        Typeface iranSans = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_regular.ttf");

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(headerBgColor)
                .withCompactStyle(false)
                .withAlternativeProfileHeaderSwitching(false)
                .withSelectionListEnabledForSingleProfile(false)
                .withTypeface(iranSans)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withIcon(
                                        context.getResources().getDrawable(R.drawable.ic_profile))
                                .withName(AppController.getStoredString("FirstName")
                                        +
                                        " "
                                        + AppController.getStoredString("LastName"))
//								.withEmail("ایمیل من")
                )
                .build();

        int darkWhite = context.getResources().getColor(R.color.dark_white);
        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withDrawerGravity(Gravity.RIGHT)
                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        displayView(position);
                        return true;
                    }
                });

        logoutDrawerItem = new PrimaryDrawerItem().withName(context.getString(R.string.logout))
                .withIcon(R.mipmap.icon_logout).withIconTintingEnabled(true)
                .withTextColor(darkWhite)
                .withIconColor(darkWhite);

	    drawerBuilder
			    .addDrawerItems(
					    logoutDrawerItem
			    );

        drawer = drawerBuilder.build();
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//
//        // Loading image with placeholder and error image
//        BezelImageView profileIV = (BezelImageView) headerResult.getView().findViewById(R.id.material_drawer_account_header_current);
//        String imageURI = URIs.DOMAIN + AppController.getStoredString("ImageSrc").substring(1);
//        imageLoader.get(imageURI, ImageLoader.getImageListener(profileIV, R.drawable.ic_profile, R.drawable.error_image));

//		headerResult.updateProfile(
//				new ProfileDrawerItem()
//						.withName(User.getTeam().getCoach())
//						.withEmail(User.getTeam().getName())
//						.withIcon(User.team.getAvatar())
//						.withIdentifier(0)
//                            .withIcon("http://static.dnaindia.com/sites/default/files/styles/square/public/2015/02/10/309230-cristiano-ronaldo-of-real-madrid-getty.jpg?itok=_Pg6kf6w")
//		);

    }

    protected void setToolbarTitle(String title) {
        mToolbarTitleTextView.setText(title);
    }

    protected void showSecondBtn(int drawableResId) {
        mSecondIV.setVisibility(View.VISIBLE);
        mSecondIV.setImageDrawable(ResourcesCompat.getDrawable(getResources(), drawableResId, null));
        mSecondIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondBtnListener();
            }
        });
    }

    protected void secondBtnListener() {

    }

    protected void showFirstBtn(int drawableResId) {
        mFirstIV.setVisibility(View.VISIBLE);
        mFirstIV.setImageDrawable(ResourcesCompat.getDrawable(getResources(), drawableResId, null));
        mFirstIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstBtnListener();
            }
        });
    }

    protected void firstBtnListener() {
        onBackPressed();
    }

    protected void displayView(int position) {

	    switch (position) {
		    case 1:
			    BaseActivity.howToBack = HowToBack.NOTHING;
			    showLogoutDialog();
			    break;
		    default:
			    break;
	    }
    }

    public void showLogoutDialog() {
        MaterialDialog.Builder builder = MaterialDialogBuilder.create(this);

        builder
                .title("خروج از حساب")
                .content("آیا مطمئن هستید؟")
                .positiveText("خیر")
                .negativeText("بله")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        AppController.getPreferences().edit().clear().commit();
                        try {
                            finishAffinity();
                        } catch (Throwable e) {
                            finish();
                            Log.w("API error", "Older api level");
                        }
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (showingDrawerMenu()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        TextView textview = (TextView) toolbarLayout.findViewById(R.id.toolbar_title_textView);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textview.getLayoutParams();
        lp.setMargins(0, 0, MetricConverter.px2dp(context, 15), 0);
        textview.setLayoutParams(lp);

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu) {
            if (drawer != null) {
                drawer.openDrawer();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (drawer!=null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            switch (howToBack){
                case BACK:
                    super.onBackPressed();
                    break;
                case HOME:
//                    replaceFragment(new InformationFragment(),"پیشخوان","NOTHING");
                    break;
                case NOTHING:
                    break;
            }
        }
    }
}
