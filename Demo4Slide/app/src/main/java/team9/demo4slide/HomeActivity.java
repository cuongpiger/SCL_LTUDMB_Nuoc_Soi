package team9.demo4slide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView mRvContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // tham chiếu đến @rvContainer trên activity_home.xml
        mRvContainer = findViewById(R.id.rvContainer);

        // chuẩn bị dữ liệu cho adapter
        List<HomeModel> containers = new ArrayList<>();
        containers.add(new HomeModel(HomeModel.IMAGE_CONTAINER,
                "https://raw.githubusercontent.com/" +
                        "nkmk/python-snippets/master/notebook/data/src/lena.jpg"));
        containers.add(new HomeModel(HomeModel.TEXT_CONTAINER,
                "Manh Cuong đẹp trai vô địch siêu cấp vũ trụ"));

        // thiết lập adapter và layout-manager
        HomeAdapter adapter = new HomeAdapter(containers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRvContainer.setLayoutManager(layoutManager);
        mRvContainer.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}