package team9.demo4slide;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ProductAdapter extends PagerAdapter {

    List<String> images;

    public ProductAdapter(List<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int position) {
        RoundedImageView image = new RoundedImageView(viewGroup.getContext());
        image.setCornerRadius((float) 30);
        Glide.with(viewGroup.getContext()).load(images.get(position)).into(image);
        viewGroup.addView(image, 0);
        return image;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RoundedImageView) object);
    }
}
