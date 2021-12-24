package team9.clover.Module;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textview.MaterialTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import team9.clover.Model.WishlistModel;
import team9.clover.ProductDetailActivity;
import team9.clover.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    List<WishlistModel> wishlistModelList;
    Boolean wishlist;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
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
        String resource = wishlistModel.getProductImage();
        String title = wishlistModel.getProductTitle();
        long freeCoupens = wishlistModel.getFreeCoupens();
        String rating = wishlistModel.getRating();
        long totalRatings = wishlistModel.getTotalRatings();
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
            priceCut = itemView.findViewById(R.id.mtvCutPrice);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupens);
            productPrice = itemView.findViewById(R.id.mtvPrice);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            rating = itemView.findViewById(R.id.rating);
            totalRatings = itemView.findViewById(R.id.total_rating);
        }

        private void setData(String resource, String title, long freeCoupensNo, String averageRate, long totalRatingsNo, String price, String cuttedPrceValue) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.product1)).into(productImage);
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

            if (wishlist) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else  {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
