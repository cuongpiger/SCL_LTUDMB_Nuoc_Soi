package team9.clover.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import team9.clover.Adapter.ProductInformationAdapter;
import team9.clover.R;

public class ProductInformationFragment extends Fragment {

    RecyclerView mContainer;
    List<String> infoList;

    public ProductInformationFragment() { }

    public ProductInformationFragment(List<String> infoList) {
        this.infoList = infoList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_information, container, false);

        ProductInformationAdapter adapter = new ProductInformationAdapter(infoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContainer = view.findViewById(R.id.rvContainer);
        mContainer.setLayoutManager(layoutManager);
        mContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getContext(), "hello", Toast.LENGTH_LONG).show();
    }
}