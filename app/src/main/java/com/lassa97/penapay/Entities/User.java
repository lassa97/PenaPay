package com.lassa97.penapay.Entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {

    @PrimaryKey
    public int userID;

    @Required
    public String name;

    @Required
    public String surname;

    public String image;

}
