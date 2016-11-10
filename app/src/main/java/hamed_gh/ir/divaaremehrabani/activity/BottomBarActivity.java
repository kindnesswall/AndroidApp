package hamed_gh.ir.divaaremehrabani.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hamed_gh.ir.divaaremehrabani.R;
import hamed_gh.ir.divaaremehrabani.bottombar.BottomBar;
import hamed_gh.ir.divaaremehrabani.bottombar.OnMenuTabClickListener;
import hamed_gh.ir.divaaremehrabani.fragment.CategoriesGridFragment;
import hamed_gh.ir.divaaremehrabani.fragment.HomeFragment;
import hamed_gh.ir.divaaremehrabani.fragment.MyWallFragment;
import hamed_gh.ir.divaaremehrabani.fragment.SearchFragment;
import hamed_gh.ir.divaaremehrabani.helper.Toasti;

public class BottomBarActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_title_textView)
    public TextView mToolbarTitleTextView;
    @Bind(R.id.main_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_new_gift_btn_tv)
    TextView mToolbarNewGiftBtnTv;

    private Context context;
    private BottomBar mBottomBar;

    private void settingToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {

        }
        mToolbarTitleTextView.setText("دیوار مهربانی");
    }

    private void settingBottomBar(Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.noTopOffset();
        mBottomBar.useFixedMode();
        mBottomBar.setTypeFace("fonts/IRANSansMobile_Light-4.1.ttf");
        mBottomBar.setItems(R.menu.menu_bottombar);
        mBottomBar.findViewById(R.id.bb_bottom_bar_background_view).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        int color = getResources().getColor(R.color.white);

        ((ImageView) mBottomBar.findViewById(R.id.bb_bottom_bar_icon)).setColorFilter(color);


        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarHome) {

                    mToolbarTitleTextView.setText("همه هدیه‌های تهران");
                    setFragment(new HomeFragment(), HomeFragment.class.getName());
                    // The user reselected item number one, scroll your content to top.
                } else if (menuItemId == R.id.bottomBarCategories) {

                    mToolbarTitleTextView.setText(R.string.categories);
                    setFragment(new CategoriesGridFragment(), CategoriesGridFragment.class.getName());

                    // The user selected item number one.
                } else if (menuItemId == R.id.bottomBarSearch) {

                    mToolbarTitleTextView.setText(R.string.search);
                    setFragment(new SearchFragment(), SearchFragment.class.getName());

                    // The user selected item number one.
                } else if (menuItemId == R.id.bottomBarMyWall) {

                    mToolbarTitleTextView.setText(R.string.my_wall);
                    setFragment(new MyWallFragment(), MyWallFragment.class.getName());

                    // The user selected item number one.
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarHome) {
                    Toasti.showS("Home reselected");
                    // The user reselected item number one, scroll your content to top.
                } else if (menuItemId == R.id.bottomBarCategories) {
                    Toasti.showS("Catagories reselected");
                    // The user selected item number one.
                } else if (menuItemId == R.id.bottomBarSearch) {
                    Toasti.showS("Search reselected");
                    // The user selected item number one.
                } else if (menuItemId == R.id.bottomBarMyWall) {
                    Toasti.showS("MyWall reselected");
                    // The user selected item number one.
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar);
        ButterKnife.bind(this);

        context = this;

        settingToolbar();

        setFragment(new HomeFragment(), "Home");

        settingBottomBar(savedInstanceState);

        mToolbarNewGiftBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, RegisterGiftActivity.class));

            }
        });

        mBottomBar.selectTabAtPosition(3,false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setFragment(Fragment fragment, String title) {
        try {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.container_body, fragment, title);
            fragmentTransaction.addToBackStack(title);
            fragmentTransaction.commit();

        } catch (Exception e) {
            //Todo : when app is finishing and homefragment request is not cancled or other requests exists:
            // java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        }
    }

}