package com.lassa97.penapay;

import android.app.Application;

import io.realm.Realm;

public class PeñaPay extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
