package team9.clover.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

import team9.clover.R;

public class ProductDescriptionFragment extends Fragment {

    String description;

    public ProductDescriptionFragment() { }

    public ProductDescriptionFragment(String description) {
        this.description = description;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_product_description, container, false);
        MaterialTextView mDescription = view.findViewById(R.id.mtvDescription);
        mDescription.setText(description);
        return view;
    }
}