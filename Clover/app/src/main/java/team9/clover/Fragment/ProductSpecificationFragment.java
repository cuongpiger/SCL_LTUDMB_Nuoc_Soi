package team9.clover.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.ProductSpecificationModel;
import team9.clover.Module.ProductSpecificationAdapter;
import team9.clover.R;

public class ProductSpecificationFragment extends Fragment {
    private RecyclerView productSpecificationRecyclerView;

    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);

        productSpecificationRecyclerView = view.findViewById(R.id.product_specification_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);

        List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
        productSpecificationModelList.add(new ProductSpecificationModel("Chất liệu", "Vải flannel"));
        productSpecificationModelList.add(new ProductSpecificationModel("Giới tính", "Nữ"));
        productSpecificationModelList.add(new ProductSpecificationModel("Kích cỡ", "XS, S, M, L, XL"));
        productSpecificationModelList.add(new ProductSpecificationModel("Loại sản phẩm", "Váy"));
        productSpecificationModelList.add(new ProductSpecificationModel("Màu sắc", "Đen"));
        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

        return view;
    }
}