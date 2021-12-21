package team9.clover.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import team9.clover.DeliveryActivity;
import team9.clover.MyAddressActivity;
import team9.clover.R;

public class MyAccountFragment extends Fragment {

    public static final int MANAGE_ADDRESS = 1;
    MaterialButton viewAllAddressBtn;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_my_account, container, false);

        viewAllAddressBtn = view.findViewById(R.id.view_all_address_btn);
        viewAllAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyAddressActivity.class);
                intent.putExtra("MODE", MANAGE_ADDRESS);
                startActivity(intent);
            }
        });

        return view;
    }
}