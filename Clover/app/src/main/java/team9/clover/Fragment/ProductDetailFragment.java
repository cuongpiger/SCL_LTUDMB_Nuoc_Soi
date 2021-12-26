package team9.clover.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import team9.clover.Adapter.ProductDetailAdapter;
import team9.clover.Adapter.ProductImageAdapter;
import team9.clover.MainActivity;
import team9.clover.Model.DatabaseModel;
import team9.clover.Model.ProductModel;
import team9.clover.R;

public class ProductDetailFragment extends Fragment {

    ViewPager mImageViewPager, mMoreViewPager;
    TabLayout mIndicator, mMore;
    FloatingActionButton mFavourite;
    MaterialTextView mTitle, mSize, mPrice, mCutPrice;
    MaterialButton mAddCart;

    ProductModel productModel;

    public ProductDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        if (MainActivity.currentFragment.equals(this.getClass().getSimpleName())) {
            productModel = (ProductModel) getArguments().getSerializable(MainActivity.class.getSimpleName());
        }

        refer(view);
        setView1();
        setView2();

        return view;
    }

    private void refer(View view) {
        mImageViewPager = view.findViewById(R.id.vpImagesContainer);
        mIndicator = view.findViewById(R.id.tlIndicator);
        mTitle = view.findViewById(R.id.mtvTitle);
        mSize = view.findViewById(R.id.mtvSize);
        mPrice = view.findViewById(R.id.mtvPrice);
        mCutPrice = view.findViewById(R.id.mtvCutPrice);
        mFavourite = view.findViewById(R.id.fabFavorite);
        mMore = view.findViewById(R.id.tlMore);
        mMoreViewPager = view.findViewById(R.id.vpMore);
        mAddCart = view.findViewById(R.id.mbAddCart);
    }

    private void setView1() {
        ProductImageAdapter adapter = new ProductImageAdapter(productModel.getImage());
        mImageViewPager.setAdapter(adapter);
        mIndicator.setupWithViewPager(mImageViewPager, true);

        mTitle.setText(productModel.getTitle());
        mSize.setText(String.join("  ", productModel.getSize()));
        mPrice.setText(productModel.getPrice());

        if (productModel.getPrice() != null && !productModel.getCutPrice().isEmpty()) {
            mCutPrice.setText(productModel.getCutPrice());
        } else {
            mCutPrice.setVisibility(View.GONE);
        }

        if (DatabaseModel.masterUser.getFavorite().contains((String) productModel.getId())) {
            mFavourite.setImageResource(R.drawable.icon_filled_heart);
        }
    }

    private void setView2() {
        mMoreViewPager.setAdapter(new ProductDetailAdapter(getParentFragmentManager(), mMore.getTabCount(), productModel));
        mMoreViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mMore));
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
    }
}