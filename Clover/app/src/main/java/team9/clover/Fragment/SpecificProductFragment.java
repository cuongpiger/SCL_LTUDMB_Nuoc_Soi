package team9.clover.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import team9.clover.Adapter.SpecificProductAdapter;
import team9.clover.MainActivity;
import team9.clover.Model.DatabaseModel;
import team9.clover.Model.HomePageModel;
import team9.clover.Model.ProductModel;
import team9.clover.R;

public class SpecificProductFragment extends Fragment {

    public static int CATEGORY_ID = 1, VIEW_ALL_ID = 2;

    int specificId;
    GridView mContainer;
    SpecificProductAdapter adapter = null;

    List<ProductModel> productList;

    public SpecificProductFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specific_product, container, false);

        specificId = getArguments().getInt("category");

        refer(view);
        setData();

        return view;
    }


    private void refer(View view) {
        mContainer = view.findViewById(R.id.gvContainer);
    }

    private void setData() {
        if (specificId > 0) {
            DatabaseModel.loadProduct("category", (long) specificId)
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                productList = new ArrayList<>();
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    productList.add(snapshot.toObject(ProductModel.class));
                                }

                                if (productList.size() % 2 == 1) {
                                    productList.add(new ProductModel());
                                }

                                adapter = new SpecificProductAdapter(productList);
                                mContainer.setAdapter(adapter);
                            }
                        }
                    });
        } else {
            DatabaseModel.loadProduct("screen", (long) (specificId == -1 ? 3 : 4))
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                productList = new ArrayList<>();
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    productList.add(snapshot.toObject(ProductModel.class));
                                }

                                if (productList.size() % 2 == 1) {
                                    productList.add(new ProductModel());
                                }

                                adapter = new SpecificProductAdapter(productList);
                                mContainer.setAdapter(adapter);
                            }
                        }
                    });
        }
    }
}