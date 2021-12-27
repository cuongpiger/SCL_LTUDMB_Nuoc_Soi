package team9.clover;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

    FrameLayout frameLayout;
    NavigationView navigationView;
    ImageView actionBarLogo;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView mCategory;
    ActionBarDrawerToggle toggle;


    CategoryAdapter categoryAdapter;

    public static int previousCategory = 0;
    private static boolean quitApp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quitApp = false;

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
        quitApp = false;
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

    private void hideCategory() {
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
    }

    private void showCategory() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // xóa button go-back
        toggle.setDrawerIndicatorEnabled(true); // hiển thị hamburger
        toggle.setToolbarNavigationClickListener(null);
        mCategory.setVisibility(View.VISIBLE); // hiển thị lại thanh category navigation view
        actionBarLogo.setVisibility(View.VISIBLE); // hiển thị lại logo trên action bar
    }

    private void setFragment(int fragmentId, Fragment fragment, Object object, boolean showCategory) {
        if (showCategory) { // hiển thị category recycler
            if (fragmentId == HomeFragment.ID) {
                // nếu là trang home fragment hoặc là trang hiển thị sản phẩm theo danh mục
                Reuse.setFragment(getSupportFragmentManager(), R.id.main_framelayout, fragment, 1);
            }  else if (fragmentId == SpecificProductFragment.CATEGORY_ID) {
                // nếu là trang hiển thị sản phẩm theo danh mục
                Bundle bundle = new Bundle();
                bundle.putInt("category", (int) object);
                fragment.setArguments(bundle);
                Reuse.setFragment(getSupportFragmentManager(), R.id.main_framelayout, fragment, 1);
            }
        } else { // ẩn category recycler view
            hideCategory(); // ẩn category, logo, tắt navigation view ở action bar
            if (fragmentId == ProductDetailFragment.ID) {
                // nếu là fragment hiển thị chi tiết sản phẩm
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", (ProductModel) object);
                fragment.setArguments(bundle);
                Reuse.setFragment(getSupportFragmentManager(), R.id.main_framelayout, fragment, 0);
            } else if (fragmentId == SpecificProductFragment.VIEW_ALL_ID) {

            }
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
                // nếu là data gửi cho trang product detail fragment
                ProductModel productModel = (ProductModel) intent.getSerializableExtra(ProductDetailFragment.class.getSimpleName());

                int specificId = intent.getIntExtra(SpecificProductFragment.class.getSimpleName(), -3);

                // đi đến trang chi tiết sản phẩm
                if (productModel != null) {
                    quitApp = false;
                    setFragment(ProductDetailFragment.ID, new ProductDetailFragment(), productModel, false);
                }

                if (specificId != -3) {
                    if (specificId >= 0 && specificId != previousCategory) {
                        previousCategory = specificId;
                        quitApp = false;
                        if (specificId == 0) { // quay về trang home fragment
                            setFragment(HomeFragment.ID, new HomeFragment(), null, true);
                        } else { // đi đến trang danh mục sản phẩm
                            setFragment(SpecificProductFragment.CATEGORY_ID, new SpecificProductFragment(), specificId, true);
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBackPressed() {
        if (quitApp) {
            quitApp = false;
            finishAffinity();
            System.exit(0);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                quitApp = true;
                Toast.makeText(this, getString(R.string.on_back_press), Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (Reuse.getLastFragmentName(getSupportFragmentManager()).equals(ProductDetailFragment.class.getSimpleName())) {
                    super.onBackPressed();

                    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        showCategory();
                    }
                } else if (Reuse.getLastFragmentName(getSupportFragmentManager()).equals(SpecificProductFragment.class.getSimpleName())) {
                    getSupportFragmentManager().popBackStack(SpecificProductFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    CategoryAdapter.currentTab = 0;
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}