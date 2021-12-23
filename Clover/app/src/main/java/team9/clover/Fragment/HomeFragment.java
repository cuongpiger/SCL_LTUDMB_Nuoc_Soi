package team9.clover.Fragment;

import static team9.clover.Module.DBqueries.homePageList;
import static team9.clover.Module.DBqueries.loadFragmentData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import team9.clover.Model.DatabaseModel;
import team9.clover.Module.CategoryAdapter;
import team9.clover.Adapter.HomePageAdapter;
import team9.clover.R;
import team9.clover.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    ////////////////////////////////////////
    private FragmentHomeBinding binding;

    RecyclerView categoryRecyclerView;
    RecyclerView rvHomePage;
    CategoryAdapter catgoryAdapter;
    HomePageAdapter homeadapter;
    ImageView noInternetConnection;
    //////////////////////////////////

    RecyclerView mCategory;
    RecyclerView mHomePage;

    CategoryAdapter categoryAdapter;
    HomePageAdapter homePageAdapter ;



    public HomeFragment() {}

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

        View view = FragmentHomeBinding.inflate(inflater, container, false).getRoot();

        refer(view);
        setCategory();
        setView(view);

        return view;
    }

    private void refer(View view) {
        mCategory = view.findViewById(R.id.rvCategory);
        mHomePage = view.findViewById(R.id.rvHomePage);
    }

    /*
    * Thiết lập cho thanh category
    * */
    private void setCategory() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // thiết lập recycler view theo chiều ngang
        mCategory.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(DatabaseModel.categoryModelList);
        mCategory.setAdapter(categoryAdapter);

        if (DatabaseModel.categoryModelList.size() == 0) {
            DatabaseModel.loadCategory(categoryAdapter, getActivity());
        } else {
            categoryAdapter.notifyDataSetChanged();
        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

//    private void referWidgets(View view) {
//        categoryRecyclerView = view.findViewById(R.id.rvCategory);
//    }

//    private void setViewCategory() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        categoryRecyclerView.setLayoutManager(linearLayoutManager);
//
//        catgoryAdapter = new CategoryAdapter(categoryList);
//        categoryRecyclerView.setAdapter(catgoryAdapter);
//
//        // load category data from firebase
//        if (categoryList.size() == 0) {
//            loadCategories(categoryAdapter, getContext());
//        } else {
//            categoryAdapter.notifyDataSetChanged();
//        }
//    }

    private void setView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHomePage.setLayoutManager(layoutManager);

        homePageAdapter = new HomePageAdapter(DatabaseModel.homePageModelList);
        mHomePage.setAdapter(homePageAdapter);

        if (DatabaseModel.homePageModelList.size() == 0) {
            DatabaseModel.loadCarousel(homePageAdapter, getActivity());
        } else {
            homePageAdapter.notifyDataSetChanged();
        }
    }

//    private void setViewRemaining(View view) {
//        rvHomePage = view.findViewById(R.id.rvHomePage);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rvHomePage.setLayoutManager(linearLayoutManager);
//
//        homeadapter = new HomePageAdapter(homePageList);
//        rvHomePage.setAdapter(homeadapter);
//
//        // load category data from firebase
//        if (homePageList.size() == 0) {
//            loadFragmentData(homeadapter, getContext());
//        } else {
//            homeadapter.notifyDataSetChanged();
//        }
//    }
}