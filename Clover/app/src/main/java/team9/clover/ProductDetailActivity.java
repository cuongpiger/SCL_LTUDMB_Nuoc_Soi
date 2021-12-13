package team9.clover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Module.ProductDetailsAdapter;
import team9.clover.Module.ProductImagesAdapter;

public class ProductDetailActivity extends AppCompatActivity {

    ViewPager productImageViewPager, productDetailsViewpager;
    TabLayout tlIndicator, productDetailsTabLayout;
    static boolean ALREADY_ADDED_TO_WISH_LIST = false;
    FloatingActionButton addWish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager = findViewById(R.id.vpProductImages);
        tlIndicator = findViewById(R.id.tlIndicator);
        addWish = findViewById(R.id.fbAddWish);
        productDetailsViewpager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);

        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.p01);
        productImages.add(R.drawable.p02);
        productImages.add(R.drawable.p03);
        productImages.add(R.drawable.p04);
        productImages.add(R.drawable.p05);

        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        productImageViewPager.setAdapter(productImagesAdapter);


        tlIndicator.setupWithViewPager(productImageViewPager, true);
        addWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ALREADY_ADDED_TO_WISH_LIST) {
                    ALREADY_ADDED_TO_WISH_LIST = false;
                    addWish.setSupportImageTintList(getResources().getColorStateList(R.color.violet_light));
                } else {
                    ALREADY_ADDED_TO_WISH_LIST = true;
                    addWish.setSupportImageTintList(getResources().getColorStateList(R.color.violet_dark));
                }
            }
        });

        productDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount()));
        productDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewpager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slideout_from_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slideout_from_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            return true;
        } else if (itemId == R.id.abSearch) {
            return true;
        } else if (itemId == R.id.abCart) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}