package com.lassa97.penapay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lassa97.penapay.Entities.User;
import com.lassa97.penapay.R;

import java.util.Locale;

import io.realm.Realm;

public class CreateUser extends AppCompatActivity {

    private Toast toast;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        realm = Realm.getDefaultInstance();
        toast = Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0,10);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FloatingActionButton newUser = findViewById(R.id.newUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });

        newUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.newUserHint);
                toast.show();
                return true;
            }
        });

        FloatingActionButton backUser = findViewById(R.id.backUser);
        backUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast.setText(R.string.backHint);
                toast.show();
                return true;
            }
        });
    }

    private void createNewUser() {
        final EditText name = findViewById(R.id.name);
        final EditText surname = findViewById(R.id.surname);

        if (name.getText().toString().isEmpty()) {
            toast.setText("Introduce un nombre");
            toast.show();
        } else if (surname.getText().toString().isEmpty()) {
            toast.setText("Introduce un apellido");
            toast.show();
        } else {
            //toast.setText(String.format(Locale.getDefault(), "%s %s", name.getText().toString(), surname.getText().toString()));
            //toast.show();

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
            toast.show();
            finish();
        }
    }

}
