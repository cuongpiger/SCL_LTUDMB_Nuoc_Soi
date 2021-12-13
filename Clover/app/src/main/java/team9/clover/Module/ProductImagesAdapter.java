package team9.clover.Module;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import team9.clover.R;

public class ProductImagesAdapter extends PagerAdapter {

    public ProductImagesAdapter(List<Integer> productImages) {
        this.productImages = productImages;
    }

    List<Integer> productImages;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        RoundedImageView productImage = new RoundedImageView(container.getContext());
        productImage.setCornerRadius((float) 50);
        productImage.setImageResource(productImages.get(position));

//        ImageView productImage = new ImageView(container.getContext());
//        productImage.setImageResource(productImages.get(position));
        container.addView(productImage, 0);
        return productImage;
    }

    @Override
    public int getCount() {
        return productImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

}
