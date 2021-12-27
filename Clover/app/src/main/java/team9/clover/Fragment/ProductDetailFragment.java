package team9.clover.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Adapter.ProductDetailAdapter;
import team9.clover.Adapter.ProductImageAdapter;
import team9.clover.Adapter.SliderProductAdapter;
import team9.clover.Model.DatabaseModel;
import team9.clover.Model.ProductModel;
import team9.clover.R;

public class ProductDetailFragment extends Fragment {

    public static int ID = 3;

    ViewPager mImageViewPager;
    ViewPager2 mMoreViewPager;
    TabLayout mIndicator, mMore;
    FloatingActionButton mFavourite;
    MaterialTextView mTitle, mSize, mPrice, mCutPrice;
    MaterialButton mAddCart;
    RecyclerView mMoreProductContainer;

    ProductModel productModel;

    public ProductDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        productModel = (ProductModel) getArguments().getSerializable("product");

        refer(view);
        setView1();
        setView2(view);
        setEvent();

        return view;
    }

    private void refer(View view) {
        mImageViewPager = view.findViewById(R.id.vpImagesContainer);
        mIndicator = view.findViewById(R.id.tlIndicator);
        mTitle = view.findViewById(R.id.mtvProductTitle);
        mSize = view.findViewById(R.id.mtvSize);
        mPrice = view.findViewById(R.id.mtvPrice);
        mCutPrice = view.findViewById(R.id.mtvCutPrice);
        mFavourite = view.findViewById(R.id.fabFavorite);
        mMore = view.findViewById(R.id.tlMore);
        mMoreViewPager = view.findViewById(R.id.vpMore);
        mAddCart = view.findViewById(R.id.mbAddCart);
        mMoreProductContainer = view.findViewById(R.id.rvContainer);
    }

    private void setView1() {
        ProductImageAdapter adapter = new ProductImageAdapter(productModel.getImage());
        mImageViewPager.setAdapter(adapter);
        mIndicator.setupWithViewPager(mImageViewPager, true);

        mTitle.setText(productModel.getTitle());
        mSize.setText(String.join("  ", productModel.getSize()));
        mPrice.setText(productModel.getPrice());

        mMoreViewPager.setAdapter(new ProductDetailAdapter(getChildFragmentManager(), getLifecycle(), mMore.getTabCount(), productModel));

        if (productModel.getPrice() != null && !productModel.getCutPrice().isEmpty()) {
            mCutPrice.setText(productModel.getCutPrice());
        } else {
            mCutPrice.setVisibility(View.GONE);
        }

        if (DatabaseModel.masterUser.getFavorite().contains((String) productModel.getId())) {
            mFavourite.setImageResource(R.drawable.icon_filled_heart);
            mFavourite.setTag(1);
        } else {
            mFavourite.setTag(0);
        }
    }

    private void setView2(View view) {
        MaterialTextView title = view.findViewById(R.id.mtvTitle);
        title.setText("Sản phẩm tương tự");
        view.findViewById(R.id.mbViewAll).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.mbViewAll).setClickable(false);

        DatabaseModel.loadProduct("category", productModel.getCategory())
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductModel> products = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                ProductModel product = snapshot.toObject(ProductModel.class);
                                if (product.getId().equals(productModel.getId())) continue;
                                products.add(snapshot.toObject(ProductModel.class));
                            }

                            SliderProductAdapter adapter = new SliderProductAdapter(products);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            mMoreProductContainer.setLayoutManager(layoutManager);
                            mMoreProductContainer.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void setEvent() {
        mFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int) mFavourite.getTag() == 0) {
                    mFavourite.setTag(1);
                    mFavourite.setImageResource(R.drawable.icon_filled_heart);
                } else {
                    mFavourite.setTag(0);
                    mFavourite.setImageResource(R.drawable.icon_empty_heart);
                }
            }
        });

        mMore.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mMoreViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        mMoreViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mMore.selectTab(mMore.getTabAt(position));
            }
        });
    }
}