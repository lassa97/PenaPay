package com.lassa97.penapay.Entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Order extends RealmObject {

    @PrimaryKey
    public int orderID;

    public int itemID, userID, eventID;

    //@Required
    //public Date time;
}
