package team9.clover.Fragment;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Adapter.CartAdapter;
import team9.clover.MainActivity;
import team9.clover.Model.DatabaseModel;
import team9.clover.Module.Reuse;
import team9.clover.R;


public class CartFragment extends Fragment {
    
    public static final String NAME = "Cart";
    public static final int ID = 6;

    RecyclerView mContainer;
    MaterialButton mContinue;

    ActionBar actionBar;

    public static boolean isChanged = false;
    
    public CartFragment(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        refer(view);
        setEvent();
        setData();
        
        return view;
    }

    private void setData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        CartAdapter adapter = new CartAdapter(DatabaseModel.masterCart);
        mContainer.setLayoutManager(layoutManager);
        mContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isChanged) {
            DatabaseModel.updateMasterCart();
        }
    }

    private void refer(View view) {
        mContainer = view.findViewById(R.id.rvContainer);
        mContinue = view.findViewById(R.id.mbCheck);
    }

    private void setEvent() {
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DatabaseModel.firebaseUser != null) {
                    Intent intent = new Intent("broadcast");
                    intent.putExtra("Fragment", ID);
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                }
            }
        });
    }

    private void setActionBar() {
        actionBar.setTitle("Giỏ hàng");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        MainActivity.toggle.setDrawerIndicatorEnabled(true); // hiển thị hamburger
        MainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        MainActivity.actionBarLogo.setVisibility(View.GONE);
        MainActivity.displayActionBarMenu(false);
        MainActivity.displayCategory(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setActionBar();
        isChanged = false;
    }
}