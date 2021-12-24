package team9.clover.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.ProductModel;
import team9.clover.R;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

    List<ProductModel> productModelList;

    public NewProductAdapter(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);
        holder.set(productModel.getImage().get(0), productModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mInage;
        MaterialTextView mTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mInage = itemView.findViewById(R.id.ivImage);
            mTitle = itemView.findViewById(R.id.mtvTitle);
        }

        private void set(String image, String title) {
            Glide.with(itemView.getContext()).load(image).into(mInage);
            mTitle.setText(title);
        }
    }
}
