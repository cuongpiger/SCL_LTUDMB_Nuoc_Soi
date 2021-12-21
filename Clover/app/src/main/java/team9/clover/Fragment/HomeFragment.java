package team9.clover.Fragment;

import static team9.clover.Module.DBqueries.categoryList;
import static team9.clover.Module.DBqueries.firebaseFirestore;
import static team9.clover.Module.DBqueries.homePageList;
import static team9.clover.Module.DBqueries.loadCategories;
import static team9.clover.Module.DBqueries.loadFragmentData;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team9.clover.Model.Category;
import team9.clover.Model.HomePage;
import team9.clover.Model.HorizontalProductScroll;
import team9.clover.Model.Slider;
import team9.clover.Module.CategoryAdapter;
import team9.clover.Module.Config;
import team9.clover.Module.GridProductAdapter;
import team9.clover.Module.HomePageAdapter;
import team9.clover.Module.HorizontalProductScrollAdapter;
import team9.clover.Module.SliderAdapter;
import team9.clover.R;
import team9.clover.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView categoryRecyclerView;
    RecyclerView rvHomePage;
    CategoryAdapter categoryAdapter;
    HomePageAdapter adapter;
    ImageView noInternetConnection;

    public HomeFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        noInternetConnection = root.findViewById(R.id.no_internet_connection);

        // check internet connection is possible
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            noInternetConnection.setVisibility(View.GONE);
            referWidgets(root);
            setViewCategory();
            setViewRemaining(root);
        } else {
            Glide.with(this).load(R.drawable.no_internet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void referWidgets(View view) {
        categoryRecyclerView = view.findViewById(R.id.rvCategory);
    }

    private void setViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        categoryAdapter = new CategoryAdapter(categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // load category data from firebase
        if (categoryList.size() == 0) {
            loadCategories(categoryAdapter, getContext());
        } else {
            categoryAdapter.notifyDataSetChanged();
        }
    }

    private void setViewRemaining(View view) {
        rvHomePage = view.findViewById(R.id.rvHomePage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHomePage.setLayoutManager(linearLayoutManager);

        adapter = new HomePageAdapter(homePageList);
        rvHomePage.setAdapter(adapter);

        // load category data from firebase
        if (homePageList.size() == 0) {
            loadFragmentData(adapter, getContext());
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}