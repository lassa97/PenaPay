package com.lassa97.penapay.Adapters;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lassa97.penapay.Entities.User;
import com.lassa97.penapay.R;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> users;

    public UsersAdapter (Context context, ArrayList<User> users) {
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
        convertView = layoutInflater.inflate(R.layout.user_item, parent, false);

        TextView userID = convertView.findViewById(R.id.userID);
        TextView userName = convertView.findViewById(R.id.userName);
        TextView userSurname = convertView.findViewById(R.id.userSurname);

        User user = getItem(position);

        userID.setText(String.valueOf(user.userID));
        userName.setText(user.name);
        userSurname.setText(user.surname);

        return convertView;
    }
}
