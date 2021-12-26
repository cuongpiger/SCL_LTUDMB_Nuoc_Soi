package team9.clover.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.R;

public class ProductInformationAdapter extends RecyclerView.Adapter<ProductInformationAdapter.ViewHolder> {

    List<String> infoList;

    public ProductInformationAdapter(List<String> infoList) {
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_product_information, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.set(infoList.get(position));
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView mInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mInfo= itemView.findViewById(R.id.mtvInfo);
        }

        public void set(String feature) {
            mInfo.setText(feature);
        }
    }
}
