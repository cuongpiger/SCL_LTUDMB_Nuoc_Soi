package team9.clover.Module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import team9.clover.Model.WishlistModel;
import team9.clover.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    List<WishlistModel> wishlistModelList;

    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishlistModel wishlistModel = wishlistModelList.get(position);
        int resource = wishlistModel.getProductImage();
        String title = wishlistModel.getProductTitle();
        int freeCoupens = wishlistModel.getFreeCoupens();
        String rating = wishlistModel.getRating();
        int totalRatings = wishlistModel.getTotalRatings();
        String productPrice = wishlistModel.getProductPrice();
        String cuttedPrice = wishlistModel.getCuttedPrice();
        // int resource, String title, int freeCoupensNo, String averageRate, int totalRatingsNo, String price, String cuttedPrceValue
        holder.setData(resource, title, freeCoupens, rating, totalRatings, productPrice, cuttedPrice);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView productImage;
        ImageView coupenIcon;
        ImageButton deleteBtn;
        View priceCut;
        MaterialTextView productTitle, freeCoupens, productPrice, cuttedPrice, rating, totalRatings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            coupenIcon = itemView.findViewById(R.id.coupen_icon);
            deleteBtn = itemView.findViewById(R.id.delete_button);
            priceCut = itemView.findViewById(R.id.price_cutted_divider);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupens);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            rating = itemView.findViewById(R.id.rating);
            totalRatings = itemView.findViewById(R.id.total_rating);
        }

        private void setData(int resource, String title, int freeCoupensNo, String averageRate, int totalRatingsNo, String price, String cuttedPrceValue) {
            productImage.setImageResource(resource);
            productTitle.setText(title);

            if (freeCoupensNo != 0) {
                coupenIcon.setVisibility(View.VISIBLE);
                freeCoupens.setText(String.format("có %d mã giảm giá", freeCoupensNo));
            } else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }

            rating.setText(averageRate);
            totalRatings.setText(String.format("(%d đánh giá)", totalRatingsNo));
            productPrice.setText(price);
            cuttedPrice.setText(cuttedPrceValue);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
