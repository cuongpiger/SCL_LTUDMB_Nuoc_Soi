package team9.clover.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

import team9.clover.R;

public class ProductDetailMoreAdapter extends ArrayAdapter<String> {

    List<String> itemData;
    Context context;
    int tab;

    public ProductDetailMoreAdapter(Context context, int itemId, int tab, List<String> itemData) {
        super(context, itemId, itemData);
        this.context = context;
        this.itemData = itemData;
        this.tab = tab;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MaterialTextView mItem;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mItem = (MaterialTextView) inflater.inflate(R.layout.item_product_detail_more,parent,false);
        } else {
            mItem = (MaterialTextView) convertView;
        }

        setData(position, mItem);
        return mItem;
    }

    private void setData(int position, MaterialTextView mItem) {
        String itemText = itemData.get(position);
        mItem.setText(itemText);
        mItem.setBackgroundColor(context.getColor(R.color.white));

        if (tab == 1) {
            mItem.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        } else {
            if (!NumberUtils.isParsable(itemText)) {
                mItem.setTextColor(getContext().getColor(R.color.black));
                mItem.setTypeface(null, Typeface.BOLD);

                if (StringUtils.isAllUpperCase(itemText)) {
                    mItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    mItem.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                }
            } else {
                mItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }
}