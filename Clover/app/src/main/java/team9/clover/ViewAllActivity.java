package team9.clover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.HorizontalProductScroll;
import team9.clover.Model.WishlistModel;
import team9.clover.Module.GridProductAdapter;
import team9.clover.Module.WishlistAdapter;

public class ViewAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View all");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview);

        gridView = findViewById(R.id.gridView);

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        if (layout_code == 0) {
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            List<WishlistModel> wishlistModelList = new ArrayList<>();
            wishlistModelList.add(new WishlistModel(R.drawable.hz_product1, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.p03, "Áo Blazer", 0, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.product1, "Áo Blazer", 2, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.p05, "Áo Blazer", 4, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.p04, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.p01, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.hz_product1, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.p03, "Áo Blazer", 0, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.product1, "Áo Blazer", 2, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.p05, "Áo Blazer", 4, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.p04, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));
            wishlistModelList.add(new WishlistModel(R.drawable.p01, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));


            WishlistAdapter adapter = new WishlistAdapter(wishlistModelList, false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else if (layout_code == 1) {

            gridView.setVisibility(View.VISIBLE);
            List<HorizontalProductScroll> horizontalProductScrollList = new ArrayList<>();

            GridProductAdapter gridProductAdapter = new GridProductAdapter(horizontalProductScrollList);
            gridView.setAdapter(gridProductAdapter);
            gridProductAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}