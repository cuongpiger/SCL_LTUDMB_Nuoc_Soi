package team9.clover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import team9.clover.Adapter.CategoryAdapter;
import team9.clover.Fragment.HomeFragment;
import team9.clover.Fragment.ProductDetailFragment;
import team9.clover.Fragment.SpecificProductFragment;
import team9.clover.Model.DatabaseModel;
import team9.clover.Model.ProductModel;
import team9.clover.Module.Reuse;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String FIRST_RUN = "0";

    FrameLayout frameLayout;
    NavigationView navigationView;
    ImageView actionBarLogo;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView mCategory;
    ActionBarDrawerToggle toggle;


    CategoryAdapter categoryAdapter;

    public static String currentFragment = HomeFragment.class.getSimpleName();
    public static int previousCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refer();
        setToolbar();
        setCategory();
        setNavigationView();
        setBroadcast();
        setFirstFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentFragment = HomeFragment.class.getSimpleName();
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
        actionBarLogo = findViewById(R.id.ivLogo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_left);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        DrawerArrowDrawable arrowDrawable = toggle.getDrawerArrowDrawable();
        arrowDrawable.setColor(getColor(R.color.black));
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


    private void setFirstFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_framelayout, new HomeFragment(), null);
        transaction.commit();
    }

    private void setFragment(Fragment fragment, Object object) {
        if (currentFragment.equals(HomeFragment.class.getSimpleName())) {
            Reuse.setFragment(getSupportFragmentManager(), R.id.main_framelayout, fragment, null, (int) object);
        } else if (currentFragment.equals(ProductDetailFragment.class.getSimpleName())) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(MainActivity.class.getSimpleName(), (ProductModel) object);
            fragment.setArguments(bundle);
            mCategory.setVisibility(View.GONE); // ẩn recycler view category
            actionBarLogo.setVisibility(View.GONE); // ẩn logo thương hiệu

            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            Reuse.setFragment(getSupportFragmentManager(), R.id.main_framelayout, fragment, null, 0);
        } else if (currentFragment.equals(SpecificProductFragment.class.getSimpleName())) {
            Bundle bundle = new Bundle();
            bundle.putInt(MainActivity.class.getSimpleName(), (int) object);
            fragment.setArguments(bundle);
            Reuse.setFragment(getSupportFragmentManager(), R.id.main_framelayout, fragment, null, 1);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        return true;
    }

    private void setBroadcast() {
        BroadcastReceiver broadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ProductModel productModel = (ProductModel) intent.getSerializableExtra(ProductDetailFragment.class.getSimpleName());

                int specificId = intent.getIntExtra(SpecificProductFragment.class.getSimpleName(), -3);

                if (productModel != null) {
                    currentFragment = ProductDetailFragment.class.getSimpleName();
                    setFragment(new ProductDetailFragment(), productModel);
                }

                if (specificId != -3) {
                    if (specificId >= 0 && specificId != previousCategory) {
                        previousCategory = specificId;
                        if (specificId == 0) {
                            currentFragment = HomeFragment.class.getSimpleName();
                            setFragment(new HomeFragment(), 1);
                        } else {
                            currentFragment = SpecificProductFragment.class.getSimpleName();
                            setFragment(new SpecificProductFragment(), specificId);
                        }
                    }
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcast, new IntentFilter("broadcast"));
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

    private void returnHomePageFragment() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // xóa button go-back
        toggle.setDrawerIndicatorEnabled(true); // hiển thị hamburger
        toggle.setToolbarNavigationClickListener(null);
        mCategory.setVisibility(View.VISIBLE); // hiển thị lại thanh category navigation view

        currentFragment = HomeFragment.class.getSimpleName();
        actionBarLogo.setVisibility(View.VISIBLE); // hiển thị lại logo trên action bar
    }

    @Override
    public void onBackPressed() {
        if (currentFragment.equals(ProductDetailFragment.class.getSimpleName())) {
            returnHomePageFragment();
            super.onBackPressed();
        } else if (currentFragment.equals(HomeFragment.class.getSimpleName())) {
            currentFragment = MainActivity.class.getSimpleName();
            Toast.makeText(this, getString(R.string.on_back_press), Toast.LENGTH_SHORT).show();
        } else if (currentFragment.equals(MainActivity.class.getSimpleName())) {
            finishAffinity();
            System.exit(0);
        } else {
            super.onBackPressed();
        }
    }
}