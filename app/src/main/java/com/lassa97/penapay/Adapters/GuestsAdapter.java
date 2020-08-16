package com.lassa97.penapay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.lassa97.penapay.Entities.User;
import com.lassa97.penapay.R;

import java.util.ArrayList;
import java.util.Locale;

public class GuestsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> users;
    private ArrayList<User> guests = new ArrayList<>();

    public GuestsAdapter (Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.checkbox_item, parent, false);

        final CheckedTextView simpleCheckedTextView = convertView.findViewById(R.id.checkedTextView);
        final User user = getItem(position);
        simpleCheckedTextView.setText(String.format(Locale.getDefault(), "%s %s", user.name, user.surname));

        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleCheckedTextView.isChecked()) {
                    simpleCheckedTextView.setCheckMarkDrawable(null);
                    simpleCheckedTextView.setChecked(false);
                    guests.remove(user);
                } else {
                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
                    simpleCheckedTextView.setChecked(true);
                    guests.add(user);
                }
            }
        });

        return convertView;
    }

    public ArrayList<User> getGuests() {
        return guests;
    }
}
