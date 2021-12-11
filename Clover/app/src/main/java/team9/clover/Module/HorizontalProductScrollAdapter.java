package team9.clover.Module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.HorizontalProductScroll;
import team9.clover.R;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    List<HorizontalProductScroll> horizontalProductScrollList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScroll> horizontalProductScrollList) {
        this.horizontalProductScrollList = horizontalProductScrollList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        int resource = horizontalProductScrollList.get(position).getImage();
        String title = horizontalProductScrollList.get(position).getTitle();
        String stuff = horizontalProductScrollList.get(position).getStuff();
        String price = horizontalProductScrollList.get(position).getPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductStuff(stuff);
        holder.setProductPrice(price);
    }

    @Override
    public int getItemCount() {
        return horizontalProductScrollList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private MaterialTextView title, stuff, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivProduct);
            title = itemView.findViewById(R.id.mtvProductTitle);
            stuff = itemView.findViewById(R.id.mtvSize);
            price = itemView.findViewById(R.id.mtvPrice);
        }

        private void setProductImage(int resorce) {
            image.setImageResource(resorce);
        }

        private void setProductTitle(String productTitle) {
            title.setText(productTitle);
        }

        private void setProductStuff(String productStuff) {
            stuff.setText(productStuff);
        }

        private void setProductPrice(String productPrice) {
            price.setText(productPrice);
        }
    }
}
