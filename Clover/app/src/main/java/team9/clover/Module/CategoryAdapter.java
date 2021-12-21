package team9.clover.Module;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import team9.clover.CategoryActivity;
import team9.clover.Model.Category;
import team9.clover.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category item = categoryList.get(position);
        String icon = item.getCategoryIcon();
        String name = item.getCategoryName();

        holder.setCategory(name, position);
        holder.setCategoryIcon(icon);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryIcon;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryIcon = itemView.findViewById(R.id.ivCategory);
            categoryName = itemView.findViewById(R.id.tvCategoryName);
        }

        /*
        * Thiệt lập icon cho Category RecyclerView.
        * PARAMS:
        *   @iconUrl: Downloaded URL được Firebase Storage cấp cho icon
        * */
        private void setCategoryIcon(String iconUrl) {
            if (!iconUrl.equals("null")) {
                Glide.with(itemView.getContext())
                        .load(iconUrl)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_home_24))
                        .into(categoryIcon);
            }
        }

        private void setCategory(final String name, final int position) {
            categoryName.setText(name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != 0) {
                        Intent categoryItent = new Intent(itemView.getContext(), CategoryActivity.class);
                        categoryItent.putExtra("CategoryName", name);
                        itemView.getContext().startActivity(categoryItent);
                    }
                }
            });
        }
    }
}
