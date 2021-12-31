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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Stack;

import team9.clover.Adapter.CategoryAdapter;
import team9.clover.Fragment.CartFragment;
import team9.clover.Fragment.DeliveryFragment;
import team9.clover.Fragment.FavoriteFragment;
import team9.clover.Fragment.HomeFragment;
import team9.clover.Fragment.OrdersFragment;
import team9.clover.Fragment.ProductDetailFragment;
import team9.clover.Fragment.SpecificProductFragment;
import team9.clover.Fragment.ViewMoreFragment;
import team9.clover.Model.DatabaseModel;
import team9.clover.Model.ProductModel;
import team9.clover.Module.Reuse;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout frameLayout;
    NavigationView navigationView;
    public static ActionBarDrawerToggle toggle;
    public static DrawerLayout drawerLayout;
    public static RecyclerView mCategory;
    public static ImageView actionBarLogo;
    public static MenuItem mSearch, mBell, mBag;
    public static Toolbar toolbar;
    public static DrawerArrowDrawable arrowDrawable;

    CategoryAdapter categoryAdapter;
    HomeFragment homeFragment;

    private static int previousNavigation = 0;
    private static boolean quitApp = false;
    private static Stack<Integer> stackNavi = new Stack<>();


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

    /*============================================================================================== THIẾT LẬP CÁC FRAGMENT SẼ HIỂN THỊ TRÊN MÀN HÌNH
    *
    * */
    private void setFragment(int fragmentId, Fragment fragment, Object object) {
        Bundle bundle = new Bundle();
        quitApp = false;
        if (fragmentId == ProductDetailFragment.ID) {
            activeBackButton();
            bundle.putSerializable(ProductDetailFragment.NAME, (ProductModel) object);
            fragment.setArguments(bundle);
            Reuse.setFragment(getSupportFragmentManager(), fragment, ProductDetailFragment.NAME, R.id.main_framelayout, 0);
        } else if (fragmentId == SpecificProductFragment.ID) {
            bundle.putInt(SpecificProductFragment.NAME, (int) object);
            fragment.setArguments(bundle);
            Reuse.setFragment(getSupportFragmentManager(), fragment, SpecificProductFragment.NAME, R.id.main_framelayout, 1);
        } else if (fragmentId == ViewMoreFragment.ID) {
            activeBackButton();
            bundle.putInt(ViewMoreFragment.NAME, (int) object);
            fragment.setArguments(bundle);
            Reuse.setFragment(getSupportFragmentManager(), fragment, ViewMoreFragment.NAME, R.id.main_framelayout, 0);
        } else if (fragmentId == FavoriteFragment.ID) {
            Reuse.setFragment(getSupportFragmentManager(), fragment, FavoriteFragment.NAME, R.id.main_framelayout, 0);
        } else if (fragmentId == CartFragment.ID) {
            Reuse.setFragment(getSupportFragmentManager(), fragment, CartFragment.NAME, R.id.main_framelayout, 0);
        } else if (fragmentId == DeliveryFragment.ID) {
            activeBackButton();
            Reuse.setFragment(getSupportFragmentManager(), fragment, DeliveryFragment.NAME, R.id.main_framelayout, 0);
        } else if (fragmentId == OrdersFragment.ID) {
            Reuse.setFragment(getSupportFragmentManager(), fragment, OrdersFragment.NAME, R.id.main_framelayout, 0);
        }
    }

    /*
    *
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.abBag) {
            // user chọn giỏ hàng
            clearBackStack();
            previousNavigation = R.id.nvCart;
            navigationView.getMenu().findItem(previousNavigation).setChecked(true);
            setFragment(CartFragment.ID, new CartFragment(getSupportActionBar()), null);
        }

        return super.onOptionsItemSelected(item);
    }

    /*============================================================================================== THIẾT LẬP ITEM CLICKING TRÊN NAVIGATION VIEW
    *
    * */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        drawerLayout.closeDrawers();

        if (itemId != previousNavigation) {
            if (itemId == R.id.nvMall) {
                clearBackStack();
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                toggle.setDrawerIndicatorEnabled(true); // hiển thị hamburger
                previousNavigation = itemId;
            } else if (itemId == R.id.nvFavorite) {
                // nếu user nhấp vào mục sản phẩm yêu thích
                if (DatabaseModel.firebaseUser != null) {
                    clearBackStack();
                    previousNavigation = itemId;
                    setFragment(FavoriteFragment.ID, new FavoriteFragment(getSupportActionBar()), null);
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng đăng nhập để sử dụng tính năng này.", Toast.LENGTH_LONG).show();
                    navigationView.getMenu().findItem(previousNavigation).setChecked(true);
                }
            } else if (itemId == R.id.nvCart) {
                clearBackStack();
                previousNavigation = R.id.nvCart;
                setFragment(CartFragment.ID, new CartFragment(getSupportActionBar()), null);
            } else if (itemId == R.id.nvOrder) {
                clearBackStack();
                previousNavigation = R.id.nvOrder;
                setFragment(OrdersFragment.ID, new OrdersFragment(getSupportActionBar()), null);
            }
        }

        return true;
    }

    /*============================================================================================== THIẾT LẬP CATEGORY RECYCLER VIEW
     * Thiết lập cho thanh category
     * */
    private void setCategory() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCategory.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(DatabaseModel.categoryModelList);
        mCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    /*============================================================================================== KHI USER NHẤN BUTTON BACK
    *
    * */
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
            } else {
                String lastFragmentName = Reuse.getLastFragmentName(getSupportFragmentManager());
                if (lastFragmentName.equals(ProductDetailFragment.NAME) ||
                        lastFragmentName.equals(ViewMoreFragment.NAME) ||
                        lastFragmentName.equals(DeliveryFragment.NAME)) {
                    getSupportFragmentManager().popBackStack();
                } else if (lastFragmentName.equals(SpecificProductFragment.NAME)) {
                    getSupportFragmentManager().popBackStack(SpecificProductFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    CategoryAdapter.currentTab = 0;
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        quitApp = false;
        previousNavigation = R.id.nvMall;
    }

    //============================================================================================== THAM CHIẾU ĐẾN CÁC COMPONENT CỦA ACTIVITY
    private void refer() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        frameLayout = findViewById(R.id.main_framelayout);
        mCategory = findViewById(R.id.rvCategory);
    }

    //============================================================================================== THIẾT LẬP CHO TOOLBAR
    private void setToolbar() {
        toolbar.setTitleTextColor(Color.BLACK);
        actionBarLogo = findViewById(R.id.ivLogo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_left); // thiết lập icon trở về

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        arrowDrawable = toggle.getDrawerArrowDrawable();
        arrowDrawable.setColor(getColor(R.color.black));
        toggle.setDrawerArrowDrawable(arrowDrawable);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    /*============================================================================================== THIẾT LẬP NAVIGATION VIEW
     * Hàm này giúp hamburger có animation, đồng thời chỉnh background cho selected item view
     * */
    private void setNavigationView() {
        View view =  navigationView.getHeaderView(0);
        MaterialTextView fullName = view.findViewById(R.id.mtvFullName),
                email = view.findViewById(R.id.mtvEmail);
        DatabaseModel.loadMasterUser(fullName, email);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public void activeBackButton() {
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

    private void setFirstFragment() {
        homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_framelayout, homeFragment, null);
        transaction.commit();
    }

    public static void displayActionBarMenu(boolean show) {
        if (show) {
            mSearch.setVisible(true);
            mBell.setVisible(true);
            mBag.setVisible(true);
        } else {
            mSearch.setVisible(false);
            mBell.setVisible(false);
            mBag.setVisible(false);
        }
    }

    public static void displayCategory(boolean show) {
        if (show)
            mCategory.setVisibility(View.VISIBLE);
        else
            mCategory.setVisibility(View.GONE);
    }

    /*============================================================================================== THIẾT LẬP MENU TRÊN ACTION BAR
     *
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // tham chiếu đến các item trên menu của action bar
        mSearch = menu.getItem(0);
        mBell = menu.getItem(1);
        mBag = menu.getItem(2);
        homeFragment.setActionBar(getSupportActionBar());

        return true;

    }

    public void resetToolbarAndCategory() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // xóa button go-back
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle.setDrawerIndicatorEnabled(true); // hiển thị hamburger
        toggle.setToolbarNavigationClickListener(null);
        mCategory.setVisibility(View.VISIBLE); // hiển thị lại thanh category navigation view
        actionBarLogo.setVisibility(View.VISIBLE); // hiển thị lại logo trên action bar

        displayActionBarMenu(true);
    }

    //============================================================================================== THIẾT LẬP BROADCAST
    private void setBroadcast() {
        BroadcastReceiver broadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int screen = intent.getIntExtra(ViewMoreFragment.NAME, -1);
                if (screen != -1) {
                    setFragment(ViewMoreFragment.ID, new ViewMoreFragment(getSupportActionBar()), (int) screen);
                }

                int category = intent.getIntExtra(SpecificProductFragment.NAME, -1);
                if (category != -1) {
                    if (category == 0) {
                        clearBackStack();
                        navigationView.getMenu().findItem(R.id.nvMall).setChecked(true);
                        previousNavigation = R.id.nvMall;
                    } else {
                        setFragment(SpecificProductFragment.ID, new SpecificProductFragment(getSupportActionBar()), (int) category);
                    }
                }

                ProductModel productBroadcast = (ProductModel) intent.getSerializableExtra(ProductDetailFragment.NAME);
                if (productBroadcast != null) {
                    setFragment(ProductDetailFragment.ID, new ProductDetailFragment(getSupportActionBar()), productBroadcast);
                }

                int fragmentId = intent.getIntExtra("Fragment", -1);
                if (fragmentId == DeliveryFragment.ID) {
                    setFragment(DeliveryFragment.ID, new DeliveryFragment(getSupportActionBar()), productBroadcast);
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcast, new IntentFilter("broadcast"));
    }

    private void clearBackStack() {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}