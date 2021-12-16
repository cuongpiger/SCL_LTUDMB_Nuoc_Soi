package team9.clover;

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

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import team9.clover.Fragment.HomeFragment;
import team9.clover.Fragment.MyCartFragment;
import team9.clover.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private ImageView actionBarLogo;

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;

    private static int currentFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slideout_from_left);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        actionBarLogo = findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        DrawerArrowDrawable arrowDrawable = toggle.getDrawerArrowDrawable();
        arrowDrawable.setColor(getResources().getColor(R.color.black));
        toggle.setDrawerArrowDrawable(arrowDrawable);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);



        frameLayout = findViewById(R.id.main_framelayout);
        setFragment(new HomeFragment(), HOME_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.abSearch) {
            return true;

        } else if (itemId == R.id.abNotification) {
            return true;

        } else if (itemId == R.id.abCart) {
            myCart();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void myCart() {
        actionBarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        invalidateOptionsMenu();
        setFragment(new MyCartFragment(), CART_FRAGMENT);
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nbMall) {
            actionBarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu(); // đồng bộ hóa actionbar
            setFragment(new HomeFragment(), HOME_FRAGMENT);

        } else if (itemId == R.id.nbOrder) {

        } else if (itemId == R.id.nbCart) {
            myCart();
        } else if (itemId == R.id.nbFavorite) {

        } else if (itemId == R.id.nbReward) {

        } else if (itemId == R.id.nbProfile) {
            Toast.makeText(MainActivity.this, "Hello2", Toast.LENGTH_SHORT).show();

        } else if (itemId == R.id.nbLogOut) {
            Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }
}