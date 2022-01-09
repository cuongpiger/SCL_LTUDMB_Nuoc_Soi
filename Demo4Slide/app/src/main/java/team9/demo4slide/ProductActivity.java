package team9.demo4slide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    ViewPager mContainer;
    TabLayout mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        mContainer = findViewById(R.id.vpContainer);
        mIndicator = findViewById(R.id.tlIndicator);

        List<String> images = new ArrayList<>();
        images.add("https://channel-korea.com/wp-content/uploads/2018/02/00180668.jpg");
        images.add("https://media-cdn.laodong.vn/storage/newsportal/2021/3/29" +
                "/893926/Blackpink.jpg?w=414&h=276&crop=auto&scale=both");
        images.add("https://vcdn-vnexpress.vnecdn.net/2021/09/24/lisa-6874-1632475092.jpg");
        images.add("https://media-cdn.laodong.vn/storage/newsportal" +
                "/2020/10/2/841202/20200617123919-E0c6.jpg");
        mContainer.setAdapter(new ProductAdapter(images));
        mIndicator.setupWithViewPager(mContainer, true);
    }
}




