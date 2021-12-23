package team9.clover.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import team9.clover.Model.CarouselModel;
import team9.clover.R;

public class CarouselAdapter extends PagerAdapter {

    List<CarouselModel> carouselModelList;

    public CarouselAdapter(List<CarouselModel> carouselModelList) {
        this.carouselModelList = carouselModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_carousel, container, false);
        RoundedImageView mImage = view.findViewById(R.id.rivImage);
        Glide.with(container.getContext()).load(carouselModelList.get(position).getImage()).into(mImage);
        container.addView(view, 0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return carouselModelList.size();
    }
}
