package team9.clover.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.WishlistModel;
import team9.clover.Module.WishlistAdapter;
import team9.clover.R;

public class MyWishlistFragment extends Fragment {

    RecyclerView wishlistRecyclerView;

    public MyWishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        //int productImage, int freeCoupens, int totalRatings, String productTitle, String rating, String productPrice, String cuttedPrice
        List<WishlistModel> wishlistModelList = new ArrayList<>();
        wishlistModelList.add(new WishlistModel(R.drawable.hz_product1, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));
        wishlistModelList.add(new WishlistModel(R.drawable.p03, "Áo Blazer", 0, "3", 145, "790.000 đ", "1.200.000 đ"));
        wishlistModelList.add(new WishlistModel(R.drawable.product1, "Áo Blazer", 2, "3", 145, "790.000 đ", "1.200.000 đ"));
        wishlistModelList.add(new WishlistModel(R.drawable.p05, "Áo Blazer", 4, "3", 145, "790.000 đ", "1.200.000 đ"));
        wishlistModelList.add(new WishlistModel(R.drawable.p04, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));
        wishlistModelList.add(new WishlistModel(R.drawable.p01, "Áo Blazer", 1, "3", 145, "790.000 đ", "1.200.000 đ"));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return view;
    }
}