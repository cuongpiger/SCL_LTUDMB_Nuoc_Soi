package team9.clover;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import team9.clover.Adapter.CategoryAdapter;
import team9.clover.Fragment.HomeFragment;
import team9.clover.Fragment.MyAccountFragment;
import team9.clover.Fragment.MyCartFragment;
import team9.clover.Fragment.MyOrdersFragment;
import team9.clover.Fragment.MyRewardFragment;
import team9.clover.Fragment.MyWishlistFragment;
import team9.clover.Model.DatabaseModel;
import team9.clover.Module.Reuse;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout frameLayout;
    NavigationView navigationView;
    ImageView actionBarLogo;
    Window window;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView mCategory;

    CategoryAdapter categoryAdapter;

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARDS_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    public static Boolean showCart = false;

//    private int currentFragment = -1;

    public static String currentFragment = HomeFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refer();
        setToolbar();
        setCategory();
        setNavigationView();
        setFragment(new HomeFragment());
    }


    private void refer() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        frameLayout = findViewById(R.id.main_framelayout);
        mCategory = findViewById(R.id.rvCategory);
    }

    private void setToolbar() {
        toolbar.setTitleTextColor(Color.BLACK);
        actionBarLogo = findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        DrawerArrowDrawable arrowDrawable = toggle.getDrawerArrowDrawable();
        arrowDrawable.setColor(getResources().getColor(R.color.black));
        toggle.setDrawerArrowDrawable(arrowDrawable);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    /*
    * Hàm này giúp hamburger có animation, đồng thời chỉnh background cho selected item view
    * */
    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void setFragment(Fragment fragment) {
        if (currentFragment.equals(HomeFragment.class.getSimpleName())) {
            Reuse.setFragment(MainActivity.this, fragment, frameLayout, 0);
        }

//        if (fragmentNo != currentFragment) {
//            if (fragmentNo == REWARDS_FRAGMENT) {
//                window.setStatusBarColor(getColor(R.color.violet_deep));
//                toolbar.setBackgroundColor(getColor(R.color.violet_deep));
//                toolbar.setTitleTextColor(Color.WHITE);
//                arrowDrawable.setColor(getResources().getColor(R.color.white));
//                toggle.setDrawerArrowDrawable(arrowDrawable);
//                toggle.syncState();
//            } else if (fragmentNo == ACCOUNT_FRAGMENT) {
//                window.setStatusBarColor(getColor(R.color.violet_deep));
//                toolbar.setBackgroundColor(getColor(R.color.black));
//                toolbar.setTitleTextColor(Color.WHITE);
//                arrowDrawable.setColor(getColor(R.color.white));
//                toggle.setDrawerArrowDrawable(arrowDrawable);
//                toggle.syncState();
//            } else {
//                window.setStatusBarColor(getColor(R.color.violet_mid));
//                toolbar.setBackgroundColor(Color.WHITE);
//                toolbar.setTitleTextColor(Color.BLACK);
//                arrowDrawable.setColor(getResources().getColor(R.color.black));
//                toggle.setDrawerArrowDrawable(arrowDrawable);
//                toggle.syncState();
//            }
//
//            currentFragment = fragmentNo;
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//            fragmentTransaction.replace(frameLayout.getId(), fragment);
//            fragmentTransaction.commit();
//        }
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            if (currentFragment == HOME_FRAGMENT) {
//                currentFragment = -1;
//                super.onBackPressed();
//            } else {
//                if (showCart) {
//                    Log.v("flow", "1");
//                    showCart = false;
//                    finish();
//                } else {
//                    actionBarLogo.setVisibility(View.VISIBLE);
//                    invalidateOptionsMenu(); // đồng bộ hóa actionbar
//                    setFragment(new HomeFragment(), HOME_FRAGMENT);
//                    navigationView.getMenu().getItem(0).setChecked(true);
//                }
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
//        if (currentFragment == HOME_FRAGMENT) {
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getMenuInflater().inflate(R.menu.main, menu);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

//        if (itemId == R.id.abSearch) {
//            return true;
//
//        } else if (itemId == R.id.abNotification) {
//            return true;
//
//        } else if (itemId == R.id.abCart) {
//            goToFragment("Giỏ hàng", new MyCartFragment(), CART_FRAGMENT);
//            return true;
//        } else if (itemId == android.R.id.home) {
//            if (showCart) {
//                Log.v("flow", "2");
//                showCart = false;
//                finish();
//            }
//        }

        return super.onOptionsItemSelected(item);
    }

    private void goToFragment(String title, Fragment fragment, int fragmentNo) {
//        actionBarLogo.setVisibility(View.GONE);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle(title);
//        invalidateOptionsMenu();
//        setFragment(fragment, fragmentNo);
//
//        if (fragmentNo == CART_FRAGMENT) {
//            navigationView.getMenu().getItem(2).setChecked(true);
//        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

//        if (itemId == R.id.nbMall) {
//            actionBarLogo.setVisibility(View.VISIBLE);
//            invalidateOptionsMenu(); // đồng bộ hóa actionbar
//            setFragment(new HomeFragment(), HOME_FRAGMENT);
//        } else if (itemId == R.id.nbOrder) {
//            goToFragment("Đơn hàng", new MyOrdersFragment(), ORDERS_FRAGMENT);
//        } else if (itemId == R.id.nbCart) {
//            goToFragment("Giỏ hàng", new MyCartFragment(), CART_FRAGMENT);
//        } else if (itemId == R.id.nbFavorite) {
//            goToFragment("Yêu thích", new MyWishlistFragment(), WISHLIST_FRAGMENT);
//        } else if (itemId == R.id.nbReward) {
//            goToFragment("Khuyến mãi", new MyRewardFragment(), REWARDS_FRAGMENT);
//        } else if (itemId == R.id.nbProfile) {
//            goToFragment("Tài khoản", new MyAccountFragment(), ACCOUNT_FRAGMENT);
//        } else if (itemId == R.id.nbLogOut) {
//            Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
//        }
//
//        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
//        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setFragment2(Fragment fragment, int fragmentNo) {
//        if (fragmentNo != currentFragment) {
//            if (fragmentNo == REWARDS_FRAGMENT) {
//                window.setStatusBarColor(getColor(R.color.violet_deep));
//                toolbar.setBackgroundColor(getColor(R.color.violet_deep));
//                toolbar.setTitleTextColor(Color.WHITE);
//                arrowDrawable.setColor(getResources().getColor(R.color.white));
//                toggle.setDrawerArrowDrawable(arrowDrawable);
//                toggle.syncState();
//            } else if (fragmentNo == ACCOUNT_FRAGMENT) {
//                window.setStatusBarColor(getColor(R.color.violet_deep));
//                toolbar.setBackgroundColor(getColor(R.color.black));
//                toolbar.setTitleTextColor(Color.WHITE);
//                arrowDrawable.setColor(getColor(R.color.white));
//                toggle.setDrawerArrowDrawable(arrowDrawable);
//                toggle.syncState();
//            } else {
//                window.setStatusBarColor(getColor(R.color.violet_mid));
//                toolbar.setBackgroundColor(Color.WHITE);
//                toolbar.setTitleTextColor(Color.BLACK);
//                arrowDrawable.setColor(getResources().getColor(R.color.black));
//                toggle.setDrawerArrowDrawable(arrowDrawable);
//                toggle.syncState();
//            }
//
//            currentFragment = fragmentNo;
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//            fragmentTransaction.replace(frameLayout.getId(), fragment);
//            fragmentTransaction.commit();
//        }
    }

    /*
     * Thiết lập cho thanh category
     * */
    private void setCategory() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // thiết lập recycler view theo chiều ngang
        mCategory.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(DatabaseModel.categoryModelList);
        mCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }
}