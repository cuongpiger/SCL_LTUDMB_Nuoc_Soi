package team9.demo4slide;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter {

    List<SongModel> songs;

    public SearchAdapter() {
        songs = new ArrayList<>();
    }

    public List<SongModel> getSongs() { return songs; }

    public void setSongs(List<SongModel> songs) { this.songs = songs; }

    @Override
    public int getCount() { return songs.size(); }

    @Override
    public Object getItem(int i) { return null; }

    @Override
    public long getItemId(int i) { return 0; }

    @Override
    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, View contentView, ViewGroup viewGroup) {
         View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                 R.layout.item_song, null);
        TextView mTitle = view.findViewById(R.id.tvTitle);
        mTitle.setText(songs.get(position).getTitle());

        return view;
    }
}
