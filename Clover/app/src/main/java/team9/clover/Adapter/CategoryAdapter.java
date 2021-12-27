package team9.clover.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.CategoryActivity;
import team9.clover.Fragment.ProductDetailFragment;
import team9.clover.Fragment.SpecificProductFragment;
import team9.clover.Model.CategoryModel;
import team9.clover.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<CategoryModel> categoryList;

    public CategoryAdapter(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel item = categoryList.get(position);
        holder.setTitle(item.getTitle(), position);
        holder.setImage(item.getImage());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        MaterialTextView mTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.ivImage);
            mTitle = itemView.findViewById(R.id.mtvTitle);
        }

        private void setImage(String imageUrl) {
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.home))
                    .into(mImage);
        }

        private void setTitle(final String title, final int position) {
            mTitle.setText(title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("broadcast");
                    intent.putExtra(SpecificProductFragment.class.getSimpleName(), position);
                    LocalBroadcastManager.getInstance(itemView.getContext()).sendBroadcast(intent);

//                    if (position != 0) {
//                        Intent intent = new Intent(itemView.getContext(), CategoryActivity.class);
//                        intent.putExtra("CategoryName", title);
//                        itemView.getContext().startActivity(intent);
//                    }
                }
            });
        }
    }
}
