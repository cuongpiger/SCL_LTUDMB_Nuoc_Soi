package team9.clover.Module;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import team9.clover.Model.Slider;
import team9.clover.R;

public class SliderAdapter extends PagerAdapter {

    private List<Slider> sliderList;

    public SliderAdapter(List<Slider> sliderList) {
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout, container, false);
        ConstraintLayout bannerContainer = view.findViewById(R.id.bannerContainer);
        bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderList.get(position).getBackgroundColor())));
        ImageView ivBanner = view.findViewById(R.id.ivBannerSlider);

        Glide.with(container.getContext()).load(sliderList.get(position).getBanner()).apply(new RequestOptions().placeholder(R.drawable.banner1)).into(ivBanner);

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
        return sliderList.size();
    }
}
