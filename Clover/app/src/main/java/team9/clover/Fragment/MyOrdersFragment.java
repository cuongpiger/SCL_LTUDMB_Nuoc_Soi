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

import team9.clover.Model.MyOrderItemModel;
import team9.clover.Module.MyOrderAdapter;
import team9.clover.R;

public class MyOrdersFragment extends Fragment {

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    RecyclerView myOrdersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        myOrdersRecyclerView = view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.product1, 3 , "Đầm công chúa Disney", "Đã giao hàng thành công vào ngày 13/12/2021."));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.hz_product1, 0 , "Áo hồng Binz", "Hủy"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.p01, 4 , "CARO Croptop", "Đã giao hàng thành công vào ngày 12/12/2021."));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.p03, 5 , "Spider-Man No way home", "Hủy"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }
}