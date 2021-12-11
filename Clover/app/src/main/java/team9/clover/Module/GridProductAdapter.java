package team9.clover.Module;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.HorizontalProductScroll;
import team9.clover.R;

public class GridProductAdapter extends BaseAdapter {

    List<HorizontalProductScroll> horizontalProductScrollList;

    public GridProductAdapter(List<HorizontalProductScroll> horizontalProductScrollList) {
        this.horizontalProductScrollList = horizontalProductScrollList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        View view;

        if (contentView == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout, null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ImageView productImage = view.findViewById(R.id.ivProduct);
            MaterialTextView productTitle = view.findViewById(R.id.mtvProductTitle);
            MaterialTextView productStuff = view.findViewById(R.id.mtvSize);
            MaterialTextView productPrice = view.findViewById(R.id.mtvPrice);

            HorizontalProductScroll product = horizontalProductScrollList.get(position);
            productImage.setImageResource(product.getImage());
            productTitle.setText(product.getTitle());
            productStuff.setText(product.getStuff());
            productPrice.setText(product.getPrice());
        } else {
            view = contentView;
        }

        return view;
    }
}
