package team9.clover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Module.ProductDetailsAdapter;
import team9.clover.Module.ProductImagesAdapter;

public class ProductDetailActivity extends AppCompatActivity {

    ViewPager productImageViewPager, productDetailsViewpager;
    TabLayout tlIndicator, productDetailsTabLayout;
    LinearLayout buyNowBtn;
    static boolean ALREADY_ADDED_TO_WISH_LIST = false;
    FloatingActionButton addWish;

    /* Rating layout */
    LinearLayout rateNowContainer;
    /****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager = findViewById(R.id.vpImagesContainer);
        tlIndicator = findViewById(R.id.tlIndicator);
        addWish = findViewById(R.id.fbAddWish);
        productDetailsViewpager = findViewById(R.id.vpMore);
        productDetailsTabLayout = findViewById(R.id.tlMore);
        buyNowBtn = findViewById(R.id.buy_now_btn);

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

        /* Rating layout */
        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int x = 0; x < rateNowContainer.getChildCount(); ++x) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating(starPosition);
                }
            });
        }
        /* ****************/

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(ProductDetailActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
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
            finish();
            return true;
        } else if (itemId == R.id.abSearch) {
            return true;
        } else if (itemId == R.id.abCart) {
            Intent cartIntent = new Intent(ProductDetailActivity.this, MainActivity.class);
            startActivity(cartIntent);
            Log.v("here", "product detail");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRating(int starPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); ++x) {
            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);

            if (x <= starPosition) {
                starBtn.setImageResource(R.drawable.filled_star);
            } else {
                starBtn.setImageResource(R.drawable.empty_start);
            }
        }
    }
}