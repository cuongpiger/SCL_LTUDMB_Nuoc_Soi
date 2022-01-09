package team9.demo4slide;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    EditText mSearch;
    GridView mContainer;
    CollectionReference songRef;
    SearchAdapter adapter;
    boolean canSearch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        songRef = FirebaseFirestore.getInstance().collection("SongModel");
        adapter = new SearchAdapter();

        mSearch = findViewById(R.id.etSearch);
        mContainer = findViewById(R.id.gvContainer);
        mContainer.setAdapter(adapter);

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyword = charSequence.toString().trim();

                if (canSearch && !keyword.isEmpty()) {
                    canSearch = false;
                    songRef.whereArrayContainsAny("split",
                                Arrays.asList(keyword.split(" "))).get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    List<SongModel> foundedSongs = new ArrayList<>();
                                    for (QueryDocumentSnapshot snapshot : task.getResult())
                                        foundedSongs.add(snapshot.toObject(SongModel.class));
                                    adapter.setSongs(foundedSongs);
                                    adapter.notifyDataSetChanged();
                                }
                                canSearch = true;
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }
}




