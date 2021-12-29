package team9.clover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.CartItemModell;
import team9.clover.Module.CartAdapter;

public class DeliveryActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView deliveryRecyclerView;
    MaterialButton changeOrAddNewAddressBtn;
    public static final int SELECT_ADDRESS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Giao hàng");

        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);
        changeOrAddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        deliveryRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModell> cartItemModelList = new ArrayList<>();
//        int type, String productTitle, String productPrice, String cuttedPrice, int productImage, int freeCoupens, int productQuantity, int offersApplied, int coupensApplied
        cartItemModelList.add(new CartItemModell(0, R.drawable.p01, "CARO Croptop", 2, "520.000 đ", "720.000 đ", 1, 0, 0));
        cartItemModelList.add(new CartItemModell(0, R.drawable.hz_product1, "Pink blazer", 0, "520.000 đ", "720.000 đ", 1, 1, 0));
        cartItemModelList.add(new CartItemModell(0, R.drawable.product1, "Tree Dress", 2, "520.000 đ", "720.000 đ", 1, 2, 0));
        cartItemModelList.add(new CartItemModell(1, "Tổng tiền (3 sản phẩm)", "1.930.000 đ", "Miễn phí", "1.930.000 đ", "Bạn tiết kiệm được 123.000 đ"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);

        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryActivity.this, MyAddressActivity.class);
                intent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}