package team9.clover.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import team9.clover.Adapter.ProductImageAdapter;
import team9.clover.MainActivity;
import team9.clover.Model.ProductModel;
import team9.clover.R;

public class ProductDetailFragment extends Fragment {

    ViewPager mImageViewPager, mDescriptionViewPager;
    TabLayout mIndicator, mDescription;
    FloatingActionButton mFavourite;
    MaterialTextView mTitle, mSize, mPrice, mCutPrice;
    MaterialButton mAddCart;

    ProductModel productModel;
    static boolean ALREADY_ADDED_TO_WISH_LIST = false;

    public ProductDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        if (MainActivity.currentFragment.equals(this.getClass().getSimpleName())) {
            productModel = (ProductModel) getArguments().getSerializable(MainActivity.class.getSimpleName());
        }

        refer(view);
        setView();

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
        mDescription = view.findViewById(R.id.tlDescription);
        mDescriptionViewPager = view.findViewById(R.id.vpDescription);
        mAddCart = view.findViewById(R.id.mbAddCart);
    }

    private void setView() {
        ProductImageAdapter adapter = new ProductImageAdapter(productModel.getImage());
        mImageViewPager.setAdapter(adapter);

        mIndicator.setupWithViewPager(mImageViewPager, true);
    }
}