package team9.clover.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.ProductModel;
import team9.clover.R;

public class SliderProductAdapter extends RecyclerView.Adapter<SliderProductAdapter.ViewHolder> {

    List<ProductModel> productModelList;

    public SliderProductAdapter(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);
        holder.set(productModel.getImage().get(0),
                productModel.getTitle(),
                String.join("  ", productModel.getSize()),
                productModel.getPrice());
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        MaterialTextView mTitle, mSize, mPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.ivImage);
            mTitle = itemView.findViewById(R.id.mtvTitle);
            mSize = itemView.findViewById(R.id.mtvSize);
            mPrice = itemView.findViewById(R.id.mtvPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "item home clicked", Toast.LENGTH_LONG).show();
                }
            });
        }

        private void set(String image, String title, String size, String price) {
            Glide.with(itemView.getContext()).load(image).into(mImage);
            mTitle.setText(title);
            mSize.setText(size);
            mPrice.setText(price);
        }
    }
}
