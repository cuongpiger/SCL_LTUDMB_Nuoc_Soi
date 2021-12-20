package team9.clover.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import team9.clover.AddressActivity;
import team9.clover.DeliveryActivity;
import team9.clover.Model.CartItemModel;
import team9.clover.Module.CartAdapter;
import team9.clover.R;

public class MyCartFragment extends Fragment {

    public MyCartFragment() {
        // Required empty public constructor
    }

    RecyclerView cartItemsRecyclerView;
    LinearLayout continueBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartItemsRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        continueBtn = view.findViewById(R.id.cart_continue_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        cartItemsRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
//        int type, String productTitle, String productPrice, String cuttedPrice, int productImage, int freeCoupens, int productQuantity, int offersApplied, int coupensApplied
        cartItemModelList.add(new CartItemModel(0, R.drawable.p01, "CARO Croptop", 2, "520.000 đ", "720.000 đ", 1, 0, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.hz_product1, "Pink blazer", 0, "520.000 đ", "720.000 đ", 1, 1, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.product1, "Tree Dress", 2, "520.000 đ", "720.000 đ", 1, 2, 0));
        cartItemModelList.add(new CartItemModel(1, "Tổng tiền (3 sản phẩm)", "1.930.000 đ", "Miễn phí", "1.930.000 đ", "Bạn tiết kiệm được 123.000 đ"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(getContext(), AddressActivity.class);
                getContext().startActivity(deliveryIntent);
            }
        });

        return view;
    }
}