package team9.clover;

import static team9.clover.DeliveryActivity.SELECT_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.AddressesMOdel;
import team9.clover.Module.AddressesAdapter;

public class MyAddressActivity extends AppCompatActivity {

    RecyclerView myAddressRecyclerView;
    private MaterialButton deliveryHereBtn;
    static AddressesAdapter adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getColor(R.color.violet_deep));
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Địa chỉ của tôi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAddressRecyclerView = findViewById(R.id.addresses_recyclerview);
        deliveryHereBtn = findViewById(R.id.deliveryHereBtn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressRecyclerView.setLayoutManager(layoutManager);

        List<AddressesMOdel> addressesMOdelList = new ArrayList<>();
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", true));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", false));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", false));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", false));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", false));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", false));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", false));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", false));
        addressesMOdelList.add(new AddressesMOdel("Mạnh Cường", "224, Nguyễn Văn Cừ phường 4 quận 5", "1234567890", false));

        int mode = getIntent().getIntExtra("MODE", -1);

        if (mode == SELECT_ADDRESS) {
            deliveryHereBtn.setVisibility(View.VISIBLE);
        } else {
            deliveryHereBtn.setVisibility(View.GONE);
        }
        adapter = new AddressesAdapter(addressesMOdelList, mode);
        myAddressRecyclerView.setAdapter(adapter);
        ((SimpleItemAnimator) myAddressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect, int select) {
        adapter.notifyItemChanged(deselect);
        adapter.notifyItemChanged(select);

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