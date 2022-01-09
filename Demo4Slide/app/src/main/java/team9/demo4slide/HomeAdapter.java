package team9.demo4slide;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Comparator;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter {

    List<HomeModel> containers;

    public HomeAdapter(List<HomeModel> containers) {
        this.containers = containers;

        // sắp xếp các thành phần tăng dần dựa theo @type của chúng
        this.containers.sort(Comparator.comparing(HomeModel::getType));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType) {
            case HomeModel.IMAGE_CONTAINER:
                return new ImageViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.container_image, parent, false));
            case HomeModel.TEXT_CONTAINER:
                return new TextViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.container_text, parent, false));

            default: return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (containers.get(position).getType()) {
            case HomeModel.IMAGE_CONTAINER:
                return HomeModel.IMAGE_CONTAINER;

            case HomeModel.TEXT_CONTAINER:
                return HomeModel.TEXT_CONTAINER;

            default: return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (containers.get(position).getType()) {
            case HomeModel.IMAGE_CONTAINER:
                ((ImageViewHolder) holder).set(containers.get(position).getValue()); break;

            case HomeModel.TEXT_CONTAINER:
                ((TextViewHolder) holder).set(containers.get(position).getValue()); break;

            default: return;
        }
    }

    @Override
    public int getItemCount() {
        return containers.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.ivImage);
        }

        private void set(String image) {
            Glide.with(itemView.getContext()).load(image).into(mImage);
            Log.v("db", "here");
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        TextView mText;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.tvText);
        }

        private void set(String text) {
            mText.setText(text);
        }
    }
}