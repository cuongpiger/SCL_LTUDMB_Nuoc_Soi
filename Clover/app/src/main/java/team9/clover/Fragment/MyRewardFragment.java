package team9.clover.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.RewardModel;
import team9.clover.Module.MyRewardAdapter;
import team9.clover.R;

public class MyRewardFragment extends Fragment {

    RecyclerView rewardsRecyclerView;

    public MyRewardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_reward, container, false);

        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rewardsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("The end 2021", "hết hạn 30/12/2021", "Các nàng ơi, sắp kết thúc năm 2021 rồi, hãy ghé Clover để nhận ưu đại sale 20% cho toàn bộ trang phục nhé."));
        rewardModelList.add(new RewardModel("Mạnh Cường cute", "vĩnh viễn", "Nhập mã \"cuongdeptrai\" để giảm 30% trên toàn bộ đơn hàng nhé."));
        rewardModelList.add(new RewardModel("The end 2021", "hết hạn 30/12/2021", "Các nàng ơi, sắp kết thúc năm 2021 rồi, hãy ghé Clover để nhận ưu đại sale 20% cho toàn bộ trang phục nhé."));
        rewardModelList.add(new RewardModel("Mạnh Cường handsome", "vĩnh viễn", "Nhập mã \"cuonghandsome\" để giảm 30% trên toàn bộ đơn hàng nhé."));
        rewardModelList.add(new RewardModel("Mạnh Cường đáng yêu", "vĩnh viễn", "Nhập mã \"cuongdangyeu\" để giảm 30% trên toàn bộ đơn hàng nhé."));
        rewardModelList.add(new RewardModel("Mạnh Cường vô địch", "vĩnh viễn", "Nhập mã \"cuongvodich\" để giảm 30% trên toàn bộ đơn hàng nhé."));
        rewardModelList.add(new RewardModel("Mạnh Cường siêu cấp", "vĩnh viễn", "Nhập mã \"cuongsieucap\" để giảm 30% trên toàn bộ đơn hàng nhé."));
        rewardModelList.add(new RewardModel("Mạnh Cường vũ trụ", "vĩnh viễn", "Nhập mã \"cuongvutru\" để giảm 30% trên toàn bộ đơn hàng nhé."));

        MyRewardAdapter myRewardAdapter = new MyRewardAdapter(rewardModelList);
        rewardsRecyclerView.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();

        return view;
    }
}