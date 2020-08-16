package com.lassa97.penapay.Entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Item extends RealmObject {

    @PrimaryKey
    public int itemID;

    @Required
    public String type;

    @Required
    public String description;

    public float price;

    public String image;

}
