package com.lassa97.penapay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lassa97.penapay.Adapters.GuestsAdapter;
import com.lassa97.penapay.Entities.Guest;
import com.lassa97.penapay.Entities.User;
import com.lassa97.penapay.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddGuestActivity extends AppCompatActivity {

    private int i, eventID;
    private GuestsAdapter guestsAdapter;
    private Toast toast;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);

        toast = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 10);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        eventID = intent.getIntExtra("eventID", -1);

        realm = Realm.getDefaultInstance();

        ListView guestList = findViewById(R.id.guestList);
        guestsAdapter = new GuestsAdapter(this, getUsers());
        guestList.setAdapter(guestsAdapter);

        FloatingActionButton addGuest = findViewById(R.id.addGuest);
        addGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGuest(eventID);
            }
        });

        addGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.addGuestHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton backGuest = findViewById(R.id.backGuest);
        backGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.backHint);
                toast.show();
                return true;
            }
        });
    }

    private void addGuest (final int eventID) {
        final ArrayList<User> guests = guestsAdapter.getGuests();

        for (i = 0; i < guests.size(); i++) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    Guest guest = bgRealm.createObject(Guest.class);
                    guest.eventID = eventID;
                    guest.userID = guests.get(i).userID;
                }
            });
        }

        toast.setText("Success");
        toast.show();
        finish();
    }

    private ArrayList<User> getUsers() {
        RealmResults<Guest> events = realm.where(Guest.class).equalTo("eventID", eventID).findAll();
        Integer[] usersID = new Integer[events.size()];
        for (i = 0; i < events.size(); i++) {
            usersID[i] = events.get(i).userID;
        }

        RealmResults<User> users = realm.where(User.class).not().in("userID", usersID).findAll();
        ArrayList<User> userList = new ArrayList<>();
        userList.addAll(users);
        return userList;
    }
}
