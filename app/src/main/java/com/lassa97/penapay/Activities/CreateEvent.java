package com.lassa97.penapay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lassa97.penapay.Entities.Event;
import com.lassa97.penapay.Entities.Order;
import com.lassa97.penapay.Entities.User;
import com.lassa97.penapay.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateEvent extends AppCompatActivity {

    private int day, month, year;
    private Toast toast;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        realm = Realm.getDefaultInstance();
        toast = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 10);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView date = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        date.setText(String.format(Locale.getDefault(), getString(R.string.date), day, month + 1, year));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int newYear, int newMonth, int newDay) {
                        date.setText(String.format(Locale.getDefault(), getString(R.string.date), newDay, newMonth + 1, newYear));
                        day = newDay;
                        month = newMonth;
                        year = newYear;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        FloatingActionButton newEvent = findViewById(R.id.newEvent);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewEvent();
            }
        });

        newEvent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.newEventHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton backButton = findViewById(R.id.backEvent);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.backHint);
                toast.show();
                return true;
            }
        });
    }

    private void createNewEvent() {
        final EditText title = findViewById(R.id.title);
        if (title.getText().toString().isEmpty()) {
            toast.setText("Introduce un titulo");
            toast.show();
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    Number maxID = bgRealm.where(Event.class).max("eventID");
                    int newKey = (maxID == null) ? 1 : maxID.intValue() + 1;

                    Event event = bgRealm.createObject(Event.class, newKey);
                    event.title = title.getText().toString();
                    event.image = String.format(Locale.getDefault(), getString(R.string.date), day, month + 1, year);
                }
            });

            toast.setText("Success");
            toast.show();
            finish();
        }
    }
}
