package team9.clover.Adapter;

import static team9.clover.Model.DatabaseModel.masterUser;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import team9.clover.Fragment.FavoriteFragment;
import team9.clover.Fragment.ProductDetailFragment;
import team9.clover.Model.ProductModel;
import team9.clover.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    List<ProductModel> productList;
    int noProducts;

    public FavoriteAdapter(List<ProductModel> productList) {
        this.productList = productList;
        this.noProducts = productList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.set(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView mImage;
        MaterialTextView mTitle, mSize, mPrice, mCutPrice;
        FloatingActionButton mFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.rivImage);
            mTitle = itemView.findViewById(R.id.mtvTitle);
            mSize = itemView.findViewById(R.id.mtvSize);
            mPrice = itemView.findViewById(R.id.mtvPrice);
            mCutPrice = itemView.findViewById(R.id.mtvCutPrice);
            mFavorite = itemView.findViewById(R.id.fabFavorite);
        }

        private void set(ProductModel product) {
            Glide.with(itemView.getContext()).load(product.getImage().get(0)).into(mImage);
            mTitle.setText(product.getTitle());
            mSize.setText(String.join("  ", product.getSize()));
            mPrice.setText(product.getPrice());
            mCutPrice.setText(product.getCutPrice());
            mFavorite.setTag(1);


            mFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFavorite.setTag((1 - (int) mFavorite.getTag()));

                    if ((int) mFavorite.getTag() == 0) {
                        masterUser.removeFavorite(product.getId());
                        mFavorite.setImageResource(R.drawable.icon_empty_heart);
                        noProducts -= 1;
                    } else {
                        masterUser.addFavorite(product.getId());
                        mFavorite.setImageResource(R.drawable.icon_filled_heart);
                        noProducts += 1;
                    }

                    FavoriteFragment.isChanged = (noProducts != masterUser.getFavorite().size());
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("broadcast");
                    intent.putExtra(ProductDetailFragment.class.getSimpleName(), product);
                    LocalBroadcastManager.getInstance(itemView.getContext()).sendBroadcast(intent);
                }
            });
        }
    }
}
