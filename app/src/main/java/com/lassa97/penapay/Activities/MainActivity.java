package com.lassa97.penapay.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lassa97.penapay.Adapters.EventsAdapter;
import com.lassa97.penapay.Entities.Event;
import com.lassa97.penapay.Entities.Item;
import com.lassa97.penapay.Entities.User;
import com.lassa97.penapay.R;

import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    // SELECT u.name, u.surname, e.title, i.name, i.type, i.price, o.time FROM User u JOIN Orders o ON u.userID = o.userID JOIN Items i ON o.itemID = i.itemID JOIN Events e ON e.eventID = o.eventID;

    private boolean open = false;
    private Toast toast;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        toast = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 10);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ListView eventList = findViewById(R.id.eventList);
        EventsAdapter eventsAdapter = new EventsAdapter(this, getEvents());
        eventList.setAdapter(eventsAdapter);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int eventID = ((Event) eventList.getItemAtPosition(position)).eventID;
                openEvent(eventID);
            }
        });

        eventList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toast.setText(R.string.eventListHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton createOptions = findViewById(R.id.createOptions);
        createOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open) {
                    ((FloatingActionButton) findViewById(R.id.createOptions)).setImageResource(R.drawable.ic_add_black_24dp);
                    findViewById(R.id.createEvent).animate().translationY(0);
                    findViewById(R.id.createItem).animate().translationX(0);
                    findViewById(R.id.createItem).animate().translationY(0);
                    findViewById(R.id.createUser).animate().translationX(0);
                    open = false;
                } else {
                    ((FloatingActionButton) findViewById(R.id.createOptions)).setImageResource(R.drawable.ic_clear_black_24dp);
                    findViewById(R.id.createEvent).animate().translationY(-250);
                    findViewById(R.id.createItem).animate().translationX(-250);
                    findViewById(R.id.createItem).animate().translationY(-250);
                    findViewById(R.id.createUser).animate().translationX(-250);
                    open = true;
                }
            }
        });

        createOptions.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.createHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton createEvent = findViewById(R.id.createEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });

        createEvent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.createEventHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton createItem = findViewById(R.id.createItem);
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItem();
            }
        });

        createItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.createItemHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton createUser = findViewById(R.id.createUser);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        createUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.createUserHint);
                toast.show();
                return true;
            }
        });
    }

    /*private void refresh() {
        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object object) {
                eventsAdapter = new EventsAdapter(MainActivity.this, getEvents());
                eventList.setAdapter(eventsAdapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }*/

    private void createEvent() {
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_create_event, null);

        final EditText title = view.findViewById(R.id.titleDialog);
        final DatePicker datePicker = view.findViewById(R.id.dateDialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        builder.setPositiveButton("Add event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (title.getText().toString().isEmpty()) {
                    toast.setText("Introduce un titulo para el evento");
                } else {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            Number maxID = bgRealm.where(Event.class).max("eventID");
                            int newKey = (maxID == null) ? 1 : maxID.intValue() + 1;

                            Event event = bgRealm.createObject(Event.class, newKey);
                            event.title = title.getText().toString();
                            event.image = String.format(Locale.getDefault(), getString(R.string.date), datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear());
                        }
                    });
                    toast.setText("Success");
                    MainActivity.this.onResume();
                }
                toast.show();
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                toast.setText("Cancel");
                toast.show();
            }
        });

        builder.create();
        builder.show();
    }

    private ArrayList<Event> getEvents() {
        RealmResults<Event> events = realm.where(Event.class).findAll();
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.addAll(events);
        return eventList;
    }

    //Change the name, it loads the event activity

    private void openEvent (int eventID) {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("eventID", eventID);
        startActivity(intent);
    }

    private void createItem() {
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_create_item, null);

        final EditText type = view.findViewById(R.id.typeDialog);
        final EditText description = view.findViewById(R.id.descriptionDialog);
        final EditText price = view.findViewById(R.id.priceDialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        builder.setPositiveButton("Add item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (type.getText().toString().isEmpty() || description.getText().toString().isEmpty() || price.getText().toString().isEmpty()) {
                    toast.setText("Introduce una categoria, una descripcion y un precio");
                } else {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            Number maxID = bgRealm.where(Item.class).max("itemID");
                            int newKey = (maxID == null) ? 1 : maxID.intValue() + 1;

                            Item item = bgRealm.createObject(Item.class, newKey);
                            item.type = type.getText().toString();
                            item.description = description.getText().toString();
                            item.price = Float.valueOf(price.getText().toString());
                        }
                    });
                    toast.setText("Success");
                }
                toast.show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                toast.setText("Cancel");
                toast.show();
            }
        });

        builder.create();
        builder.show();
    }

    private void createUser() {
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_create_user, null);

        final EditText name = view.findViewById(R.id.nameDialog);
        final EditText surname = view.findViewById(R.id.surnameDialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        builder.setPositiveButton("Add user", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    if (name.getText().toString().isEmpty() || surname.getText().toString().isEmpty()) {
                        toast.setText("Introduce un nombre y un apellido");
                    } else {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm bgRealm) {
                                Number maxID = bgRealm.where(User.class).max("userID");
                                int newKey = (maxID == null) ? 1 : maxID.intValue() + 1;

                                User user = bgRealm.createObject(User.class, newKey);
                                user.name = name.getText().toString();
                                user.surname = surname.getText().toString();
                            }
                        });
                        toast.setText("Success");
                    }
                    toast.show();
                }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    toast.setText("Cancel");
                    toast.show();
                }
        });

        builder.create();
        builder.show();
    }
}
