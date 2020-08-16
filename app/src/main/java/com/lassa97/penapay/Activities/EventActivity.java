package com.lassa97.penapay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lassa97.penapay.Adapters.UsersAdapter;
import com.lassa97.penapay.Entities.Event;
import com.lassa97.penapay.Entities.Guest;
import com.lassa97.penapay.Entities.User;
import com.lassa97.penapay.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventActivity extends AppCompatActivity {

    private boolean open = false;
    private Toast toast;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }

    @Override
    protected void onResume() {
        super.onResume();

        realm = Realm.getDefaultInstance();
        toast = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 10);

        Intent intent = getIntent();
        final int eventID = intent.getIntExtra("eventID", -1);

        ListView userList = findViewById(R.id.userList);
        UsersAdapter usersAdapter = new UsersAdapter(this, getUsers(eventID));
        userList.setAdapter(usersAdapter);

        FloatingActionButton addOptions = findViewById(R.id.addOptions);
        addOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open) {
                    ((FloatingActionButton) findViewById(R.id.addOptions)).setImageResource(R.drawable.ic_add_black_24dp);
                    findViewById(R.id.addUser).animate().translationX(0);
                    findViewById(R.id.addItem).animate().translationY(0);
                    open = false;
                } else {
                    ((FloatingActionButton) findViewById(R.id.addOptions)).setImageResource(R.drawable.ic_clear_black_24dp);
                    findViewById(R.id.addUser).animate().translationX(-250);
                    findViewById(R.id.addItem).animate().translationY(-250);
                    open = true;
                }
            }
        });

        addOptions.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.addHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton addUser = findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGuest(eventID);
            }
        });

        addUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.addUserHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton addItem = findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(eventID);
            }
        });

        addItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.addItemHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton backInfo = findViewById(R.id.backInfo);
        backInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.backHint);
                toast.show();
                return true;
            }
        });
    }

    private void addGuest (int eventID) {
        Intent intent = new Intent(this, AddGuestActivity.class);
        intent.putExtra("eventID", eventID);
        startActivity(intent);
    }

    private ArrayList<User> getUsers (int eventID) {
        RealmResults<Guest> events = realm.where(Guest.class).equalTo("eventID", eventID).findAll();
        Integer[] usersID = new Integer[events.size()];
        for (int i = 0; i < events.size(); i++) {
            usersID[i] = events.get(i).userID;
        }

        RealmResults<User> users = realm.where(User.class).in("userID", usersID).findAll();
        ArrayList<User> userList = new ArrayList<>();
        userList.addAll(users);
        return userList;

    }

    private void addItem (int eventID) {
        Intent intent = new Intent(this, AddOrderActivity.class);
        intent.putExtra("eventID", eventID);
        startActivity(intent);
    }
}
