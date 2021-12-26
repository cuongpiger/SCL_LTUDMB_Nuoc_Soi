package team9.clover.Adapter;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import team9.clover.Fragment.ProductDescriptionFragment;
import team9.clover.Fragment.ProductDetailMoreFragment;
import team9.clover.Fragment.ProductInformationFragment;
import team9.clover.Fragment.ProductSizeFragment;
import team9.clover.Model.ProductModel;

public class ProductDetailAdapter extends FragmentPagerAdapter {
    int noTabs;
    ProductModel product;

    public ProductDetailAdapter(@NonNull FragmentManager fm, int noTabs, ProductModel product) {
        super(fm);
        this.noTabs = noTabs;
        this.product = product;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.v("db", "" + position);
        switch (position) {
            case 0:
                return new ProductDetailMoreFragment(product.getDescription());
            case 1:
                return new ProductDetailMoreFragment(product.getInfo());
            case 2:
                return new ProductDetailMoreFragment(product.getBodyName(), product.getSize(), product.getMeasure());

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return noTabs;
    }
}
