package team9.clover.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

import team9.clover.Module.Reuse;
import team9.clover.R;

public class ProductSizeAdapter extends ArrayAdapter<String> {

    List<String> sizeList;
    Context context;

    public ProductSizeAdapter(Context context, int itemId, List<String> sizeList) {
        super(context, itemId, sizeList);
        this.context = context;
        this.sizeList = sizeList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView mSize;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mSize = (TextView) inflater.inflate(R.layout.item_product_size,parent,false);
        } else {
            mSize = (TextView) convertView;
        }

        String size = sizeList.get(position);
        mSize.setText(size);
        mSize.setBackgroundColor(context.getColor(R.color.white));
        if (!NumberUtils.isParsable(size)) {
            mSize.setTextColor(getContext().getColor(R.color.black));
            mSize.setTypeface(null, Typeface.BOLD);

            if (StringUtils.isAllUpperCase(size)) {
                mSize.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                mSize.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
        } else {
            mSize.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        return mSize;
    }
}