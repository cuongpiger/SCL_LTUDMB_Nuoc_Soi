package team9.clover;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.Category;
import team9.clover.Model.HomePage;
import team9.clover.Model.HorizontalProductScroll;
import team9.clover.Model.Slider;
import team9.clover.Module.CategoryAdapter;
import team9.clover.Module.HomePageAdapter;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slideout_from_left);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.rvCategory);

        setView();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slideout_from_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slideout_from_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.abSearch) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void setView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        List<Slider> sliderList = new ArrayList<Slider>();
        sliderList.add(new Slider(R.drawable.banner6, "#EFEFEF"));
        sliderList.add(new Slider(R.drawable.banner7, "#7696A5"));
        sliderList.add(new Slider(R.drawable.banner1, "#EF6540"));
        sliderList.add(new Slider(R.drawable.banner2, "#988F7E"));
        sliderList.add(new Slider(R.drawable.banner3, "#898989"));
        sliderList.add(new Slider(R.drawable.banner4, "#775440"));
        sliderList.add(new Slider(R.drawable.banner5, "#FAC6CD"));
        sliderList.add(new Slider(R.drawable.banner6, "#EFEFEF"));
        sliderList.add(new Slider(R.drawable.banner7, "#7696A5"));
        sliderList.add(new Slider(R.drawable.banner1, "#EF6540"));
        sliderList.add(new Slider(R.drawable.banner2, "#988F7E"));

        List<HorizontalProductScroll> horizontalProductScrollList = new ArrayList<>();
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "M  L  XL", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "S  M  L", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.product1, "Olympiah", "XS  S", "720.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.product1, "Olympiah", "L  XL", "720.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "S  M  L", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "XS  S", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "L  XL", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "L", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "XS", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "XL", "1.390.000 đ"));

        List<HomePage> homePageList = new ArrayList<>();
        homePageList.add(new HomePage(HomePage.BANNER_SLIDER, sliderList));
        homePageList.add(new HomePage(HomePage.STRIP_AD_BANNER, R.drawable.banner_ad1, "#D5D7D6"));
        homePageList.add(new HomePage(HomePage.HORIZONTAL_PRODUCT_VIEW,"Khuyến mãi hôm nay", horizontalProductScrollList));
        homePageList.add(new HomePage(HomePage.GRID_PRODUCT_VIEW, "Sản phẩm mới", horizontalProductScrollList));

        HomePageAdapter adapter = new HomePageAdapter(homePageList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}