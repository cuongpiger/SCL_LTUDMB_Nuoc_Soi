package team9.clover.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Adapter.ProductSizeAdapter;
import team9.clover.R;

public class ProductSizeFragment extends Fragment {

    GridView mContainer;

    List<String> bodyNameList, sizeList;
    List<Long> measureList;

    public ProductSizeFragment() { }

    public ProductSizeFragment(List<String> bodyNameList, List<String> sizeList, List<Long> measureList) {
        this.bodyNameList = bodyNameList;
        this.sizeList = sizeList;
        this.measureList = measureList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_size, container, false);

        mContainer = view.findViewById(R.id.gvContainer);
        mContainer.setNumColumns(sizeList.size() + 1);
        setView();

        return view;
    }

    private void setView() {
        int n = sizeList.size();
        List<String> sizeData = new ArrayList<>();
        sizeData.add("(centimet)");
        sizeData.addAll(sizeList);

        for (int i = 0; i < bodyNameList.size(); ++i) {
            sizeData.add(bodyNameList.get(i));
            for (int j = 0; j < n; ++j) {
                sizeData.add(Long.toString(measureList.get(n*i + j)));
            }
        }
        ProductSizeAdapter adapter = new ProductSizeAdapter(getContext(), R.layout.item_product_size, sizeData);
        mContainer.setAdapter(adapter);
        Toast.makeText(getActivity(), "chsdfsdfd", Toast.LENGTH_LONG).show();
    }
}