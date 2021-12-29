package team9.clover.Module;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.HorizontalProductScroll;
import team9.clover.ProductDetailActivity;
import team9.clover.R;

public class GridProductAdapter extends BaseAdapter {

    List<HorizontalProductScroll> horizontalProductScrollList;

    public GridProductAdapter(List<HorizontalProductScroll> horizontalProductScrollList) {
        this.horizontalProductScrollList = horizontalProductScrollList;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollList.size();
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(viewGroup.getContext(), ProductDetailActivity.class);
                    viewGroup.getContext().startActivity(productDetailsIntent);
                }
            });

            ImageView productImage = view.findViewById(R.id.ivProduct);
            MaterialTextView productTitle = view.findViewById(R.id.mtvProductTitle);
            MaterialTextView productStuff = view.findViewById(R.id.mtvSize);
            MaterialTextView productPrice = view.findViewById(R.id.mtvTotal);

            HorizontalProductScroll product = horizontalProductScrollList.get(position);
            Glide.with(view.getContext()).load(product.getImage()).apply(new RequestOptions().placeholder(R.drawable.product1)).into(productImage);
            productTitle.setText(product.getTitle());
            productStuff.setText(product.getStuff());
            productPrice.setText(product.getPrice() + " đ");
        } else {
            view = contentView;
        }

        return view;
    }
}
