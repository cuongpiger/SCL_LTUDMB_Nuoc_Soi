package team9.clover.Module;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import team9.clover.Model.MyOrderItemModel;
import team9.clover.R;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        MyOrderItemModel myOrderItemModel = myOrderItemModelList.get(position);
        int resource = myOrderItemModel.getProductImage();
        int rating = myOrderItemModel.getRating();
        String title = myOrderItemModel.getProductTitle();
        String deliveryDate = myOrderItemModel.getDeliveryStatus();

        // int resource, String title, String deliveryDate, int rating
        holder.setData(resource, title, deliveryDate, rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView productImage;
        MaterialTextView deliveryStatus, productTitle;
        ImageView deliveryDot;
        LinearLayout rateNowContainer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            deliveryStatus = itemView.findViewById(R.id.deliveryStatus);
            deliveryDot = itemView.findViewById(R.id.deliveryDot);
            rateNowContainer = itemView.findViewById(R.id.rate_now_container);
        }

        private void setData(int resource, String title, String deliveryDate, int rating) {
            productImage.setImageResource(resource);
            productTitle.setText(title);

            if (deliveryDate.equals("Hủy")) {
                deliveryDot.setImageTintList(ColorStateList.valueOf(itemView.getContext().getColor(R.color.red)));
            } else {
                deliveryDot.setImageTintList(ColorStateList.valueOf(itemView.getContext().getColor(R.color.green)));
            }

            deliveryStatus.setText(deliveryDate);
            setRating(rating - 1);
            for (int x = 0; x < rateNowContainer.getChildCount(); ++x) {
                final int starPosition = x;
                rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRating(starPosition);
                    }
                });
            }

        }

        private void setRating(int starPosition) {
            for (int x = 0; x < rateNowContainer.getChildCount(); ++x) {
                ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);

                if (x <= starPosition) {
                    starBtn.setImageResource(R.drawable.filled_star);
                } else {
                    starBtn.setImageResource(R.drawable.empty_start);
                }
            }
        }
    }
}
