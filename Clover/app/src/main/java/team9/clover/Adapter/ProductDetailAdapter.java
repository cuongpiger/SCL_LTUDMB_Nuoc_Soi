package team9.clover.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import team9.clover.Fragment.ProductDescriptionFragment;
import team9.clover.Fragment.ProductInformationFragment;
import team9.clover.Model.ProductModel;

public class ProductDetailAdapter extends FragmentPagerAdapter {
    int noTabs;
    ProductModel product;

    public ProductDetailAdapter(@NonNull FragmentManager fm, int noTabs, ProductModel product) {
        super(fm);
        Log.v("db", "" + noTabs);
        this.noTabs = (int) noTabs;

        this.product = product;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProductDescriptionFragment(product.getDescription());
            case 1:
                return new ProductInformationFragment(product.getInfo());
            case 2: return new ProductDescriptionFragment(product.getPrice());

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return noTabs;
    }
}
