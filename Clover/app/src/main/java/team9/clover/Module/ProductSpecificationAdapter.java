package team9.clover.Module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.ProductSpecificationModel;
import team9.clover.R;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {

    List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @NonNull
    @Override
    public ProductSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdapter.ViewHolder holder, int position) {
        String featureTitle = productSpecificationModelList.get(position).getFeatureName();
        String featureDetail = productSpecificationModelList.get(position).getFeatureValue();

        holder.setFeatures(featureTitle, featureDetail);
    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView featureName, featureValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            featureName = itemView.findViewById(R.id.feature_name);
            featureValue = itemView.findViewById(R.id.feature_value);
        }

        private void setFeatures(String featureTitle, String featuredetail) {
            featureName.setText(featureTitle);
            featureValue.setText(featuredetail);
        }
    }
}
