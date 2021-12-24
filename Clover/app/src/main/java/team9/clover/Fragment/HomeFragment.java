package team9.clover.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import team9.clover.Model.DatabaseModel;
import team9.clover.Adapter.CategoryAdapter;
import team9.clover.Adapter.HomePageAdapter;
import team9.clover.Module.Reuse;
import team9.clover.R;
import team9.clover.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    RecyclerView mCategory;
    RecyclerView mHomePage;

    CategoryAdapter categoryAdapter;
    HomePageAdapter homePageAdapter ;

    public HomeFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        refer(view);
        setView(view);
//        DatabaseModel.addProduct();

        return view;
    }

    private void refer(View view) {
        mHomePage = view.findViewById(R.id.rvHomePage);
    }

    private void setView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHomePage.setLayoutManager(layoutManager);

        homePageAdapter = new HomePageAdapter(DatabaseModel.homePageModelList);
        mHomePage.setAdapter(homePageAdapter);

        if (DatabaseModel.homePageModelList.size() == 0) {
            DatabaseModel.loadHomePage(homePageAdapter, getActivity());
        } else {
            homePageAdapter.notifyDataSetChanged();
        }
    }
}













//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//        noInternetConnection = root.findViewById(R.id.no_internet_connection);
//
//        // check internet connection is possible
//        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//            noInternetConnection.setVisibility(View.GONE);
//            referWidgets(root);
//            setViewCategory();
//            setViewRemaining(root);
//        } else {
//            Glide.with(this).load(R.drawable.no_internet).into(noInternetConnection);
//            noInternetConnection.setVisibility(View.VISIBLE);
//        }
//
//        return root;

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        refer(view);
        setCategory();
        setView(view);

        return view;
    }

    private void refer(View view) {
        mCategory = view.findViewById(R.id.rvCategory);
        mHomePage = view.findViewById(R.id.rvHomePage);
    }
* */