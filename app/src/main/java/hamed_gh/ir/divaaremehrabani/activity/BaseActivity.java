package hamed_gh.ir.divaaremehrabani.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import hamed_gh.ir.divaaremehrabani.MainActivity;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.app.AppController;
import hamed_gh.ir.divaaremehrabani.fragment.BaseFragment;
import hamed_gh.ir.divaaremehrabani.fragment.testFragment;
import hamed_gh.ir.divaaremehrabani.helper.MaterialDialogBuilder;
import hamed_gh.ir.divaaremehrabani.helper.MetricConverter;
import hamed_gh.ir.divaaremehrabani.helper.Toasti;

/**
 * Created by Hamed on 4/3/16.
 */
public class BaseActivity extends AppCompatActivity {

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
    private String userType;
    public Drawer drawer;
    protected int headerBgColor;
    private Toolbar mToolbar;
    private int newActivity;
    private boolean showDrawerMenu = false;

    public PrimaryDrawerItem logoutDrawerItem;
    public PrimaryDrawerItem homeDrawerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {

                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toasti.showL("در حال حاضر ابردانش با خطا با مواجه شده است و نیاز به راه اندازی مجدد دارد.");
                        Looper.loop();
                    }
                }.start();
                try
                {
                    Thread.sleep(4000); // Let the Toast display before app will get shutdown
                }
                catch (InterruptedException e) {    }
                System.exit(2);
            }
        });
    }

    public boolean showingDrawerMenu() {
        return showDrawerMenu;
    }

    public void setShowDrawerMenu(boolean showDrawerMenu) {
        this.showDrawerMenu = showDrawerMenu;
    }

    protected void init(int layoutResID) {

        context = this;

        // -- set Toolbar ---
        toolbarLayout = (RelativeLayout) findViewById(R.id.toolbar);
        mToolbar = (Toolbar) toolbarLayout.findViewById(R.id.main_toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(headerBgColor));
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {

        }

        mToolbarTitleTextView = (TextView) toolbarLayout.findViewById(R.id.toolbar_title_textView);

        mSecondIV = (ImageView) toolbarLayout.findViewById(R.id.toolbar_second_iv);
        mFirstIV = (ImageView) toolbarLayout.findViewById(R.id.toolbar_first_iv);
    }

    protected void setDrawer(int newAct) {

        newActivity = newAct;

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

        homeDrawerItem = new PrimaryDrawerItem().withName(getString(R.string.app_name))
                .withIcon(R.mipmap.ic_launcher).withIconTintingEnabled(true)
                .withTextColor(darkWhite)
                .withIconColor(darkWhite);

        logoutDrawerItem = new PrimaryDrawerItem().withName(context.getString(R.string.logout))
                .withIcon(R.mipmap.icon_logout).withIconTintingEnabled(true)
                .withTextColor(darkWhite)
                .withIconColor(darkWhite);

        if (userType.equals("student")) {

            drawerBuilder
                    .addDrawerItems(
		                    homeDrawerItem,
                            logoutDrawerItem
                    );

        } else if (userType.equals("parent")) {

            drawerBuilder
                    .addDrawerItems(
		                    homeDrawerItem,
                            logoutDrawerItem
                    );

        } else if (userType.equals("teacher")) {

            drawerBuilder
                    .addDrawerItems(
		                    homeDrawerItem,
                            logoutDrawerItem
                    );

        }

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

        Fragment fragment = null;
        Intent intent;
        String title = getString(R.string.app_name);
        if (userType.equals("login")) {
            switch (position - 1) {
                case 0:
                    BaseActivity.howToBack = HowToBack.NOTHING;
                    showLogoutDialog();
                    position = -1;
                    break;

                default:
                    break;
            }
        } else if (userType.equals("logout")) {
            switch (position - 1) {
                case 0:
                    fragment = new testFragment();
                    title = "پیشخوان";
                    TAG = "InformationFragment";
                    break;
                case 3:
                    BaseActivity.howToBack = HowToBack.NOTHING;
                    showLogoutDialog();
                    position = -1;
                    break;
                default:
                    break;
            }
        }

        setFragment(fragment, title, position);
    }

    protected void setFragment(Fragment fragment, String title, int position) {
        if (newActivity == 1) {
            if (position != -1) { //not log out
                Intent intent = new Intent(this, MainActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("FRAGMENT", position);
                intent.putExtras(mBundle);
                startActivity(intent);
                finish();
            }
        } else if (fragment != null) {

            Bundle args = new Bundle();
            args.putString("how_to_back", "HOME");
            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                String name = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();

                if (name.equals(title)) { //just close drawer when I choose current fragment
                    if (drawer != null) {
                        drawer.closeDrawer();
                    }
                    return;
                }
                String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                if (getSupportFragmentManager().findFragmentByTag(tag) != null)
                    fragmentTransaction.remove(getSupportFragmentManager().findFragmentByTag(tag));
            }
            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.addToBackStack(title);
            fragmentTransaction.commit();

            // set the toolbar title
            mToolbarTitleTextView.setText(title);
            if (drawer != null) {
                drawer.closeDrawer();
            }
        }
    }

    protected void replaceFragment(BaseFragment newFragment, String title, String howToBack){
        Bundle args = new Bundle();
        args.putString("how_to_back", howToBack);
        newFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            String tag = getSupportFragmentManager().getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            if (fragmentManager.findFragmentByTag(tag) != null)
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(tag));
        }

        fragmentTransaction.replace(R.id.container_body, newFragment, title);
        fragmentTransaction.addToBackStack( title);
        fragmentTransaction.commit();
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
