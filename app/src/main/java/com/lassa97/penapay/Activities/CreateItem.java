package com.lassa97.penapay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lassa97.penapay.Entities.Item;
import com.lassa97.penapay.R;

import java.util.Iterator;
import java.util.Locale;

import io.realm.Realm;

public class CreateItem extends AppCompatActivity {

    private Toast toast;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        realm = Realm.getDefaultInstance();
        toast = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 10);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FloatingActionButton newItem = findViewById(R.id.newItem);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewItem();
            }
        });

        newItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.newItemHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton backItem = findViewById(R.id.backItem);
        backItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.backHint);
                toast.show();
                return true;
            }
        });
    }

    private void createNewItem() {
        final EditText type = findViewById(R.id.type);
        final EditText description = findViewById(R.id.description);
        final EditText price = findViewById(R.id.price);

        if (type.getText().toString().isEmpty()) {
            toast.setText("Introduce una categoria");
            toast.show();
        } else if (description.getText().toString().isEmpty()) {
            toast.setText("Introduce una descripcion");
            toast.show();
        } else if (price.getText().toString().isEmpty()) {
            toast.setText("Introduce un precio");
            toast.show();
        } else {
            //toast.setText(String.format(Locale.getDefault(), "%s - %s: %.2f", type.getText().toString(), description.getText().toString(), Float.valueOf(price.getText().toString())));
            //toast.show();

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
            toast.show();
            finish();
        }
    }
}
