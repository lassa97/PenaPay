package com.lassa97.penapay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lassa97.penapay.Entities.Item;
import com.lassa97.penapay.R;

import java.util.ArrayList;
import java.util.Locale;

public class ItemsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> items;

    public ItemsAdapter (Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.items_item, parent, false);

        TextView itemID = convertView.findViewById(R.id.itemID);
        TextView itemType = convertView.findViewById(R.id.itemType);
        TextView itemDescription = convertView.findViewById(R.id.itemDescription);
        TextView itemPrice = convertView.findViewById(R.id.itemPrice);

        Item item = getItem(position);

        itemID.setText(String.valueOf(item.itemID));
        itemType.setText(item.type);
        itemDescription.setText(item.description);
        itemPrice.setText(String.format(Locale.getDefault(), "%.2f €", item.price));

        return convertView;
    }
}
