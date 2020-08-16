package com.lassa97.penapay.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lassa97.penapay.Adapters.ItemsAdapter;
import com.lassa97.penapay.Entities.Guest;
import com.lassa97.penapay.Entities.Item;
import com.lassa97.penapay.Entities.User;
import com.lassa97.penapay.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddOrderActivity extends AppCompatActivity {

    private int i, eventID;
    private ItemsAdapter itemsAdapter;
    private Toast toast;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        realm = Realm.getDefaultInstance();
        toast = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 10);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        eventID = intent.getIntExtra("eventID", -1);

        ListView itemList = findViewById(R.id.itemList);
        itemsAdapter = new ItemsAdapter(this, getItems());
        itemList.setAdapter(itemsAdapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addOrder();
            }
        });

        FloatingActionButton addOrder = findViewById(R.id.addOrder);
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast.setText(R.string.addOrderHint);
                toast.show();
            }
        });

        addOrder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.addOrderHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton backOrder = findViewById(R.id.backOrder);
        backOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backOrder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.backHint);
                toast.show();
                return true;
            }
        });
    }

    private void addOrder() {
        final ArrayList<User> guests = getGuests();
        final String[] guestsList = new String[guests.size()];
        String fullname;

        for (i = 0; i < guests.size(); i++) {
            fullname = String.format(Locale.getDefault(), "%d: %s %s", guests.get(i).userID, guests.get(i).name, guests.get(i).surname);
            guestsList[i] = fullname;
        }

        final ArrayList<String> customers = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick the guests...");
        builder.setMultiChoiceItems(guestsList, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    customers.add(guestsList[which]);
                } else if (customers.contains(guestsList[which])) {
                    customers.remove(guestsList[which]);
                }
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //toast.setText("Okey");
                //toast.show();
                for (i = 0; i < customers.size(); i++) {
                    Log.d("Selections", customers.get(i));
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //toast.setText("Cancelar");
                //toast.show();
            }
        });

        builder.create();
        builder.show();
    }

    private ArrayList<Item> getItems() {
        RealmResults<Item> items = realm.where(Item.class).findAll();
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.addAll(items);
        return itemList;
    }

    private ArrayList<User> getGuests() {
        RealmResults<Guest> events = realm.where(Guest.class).equalTo("eventID", eventID).findAll();
        Integer[] usersID = new Integer[events.size()];
        for (i = 0; i < events.size(); i++) {
            usersID[i] = events.get(i).userID;
        }

        RealmResults<User> users = realm.where(User.class).in("userID", usersID).findAll();
        ArrayList<User> userList = new ArrayList<>();
        userList.addAll(users);
        return userList;
    }
}
