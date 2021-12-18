package team9.clover.Module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import team9.clover.Model.RewardModel;
import team9.clover.R;

public class MyRewardAdapter extends RecyclerView.Adapter<MyRewardAdapter.ViewHolder> {

    public MyRewardAdapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;
    }

    List<RewardModel> rewardModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RewardModel rewardModel = rewardModelList.get(position);
        String title = rewardModel.getTitle();
        String date = rewardModel.getExpiredDate();
        String body = rewardModel.getCoupenBody();
        holder.setData(title, date, body);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView rewardTitle, rewardExpiredDate, rewardBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rewardTitle = itemView.findViewById(R.id.reward_title);
            rewardExpiredDate = itemView.findViewById(R.id.reward_expired_date);
            rewardBody = itemView.findViewById(R.id.reward_body);
        }

        private void setData(String title, String expiredDate, String body) {
            rewardTitle.setText(title);
            rewardExpiredDate.setText(expiredDate);
            rewardBody.setText(body);
        }
    }
}
