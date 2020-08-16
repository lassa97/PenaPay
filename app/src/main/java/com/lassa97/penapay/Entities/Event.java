package com.lassa97.penapay.Entities;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Event extends RealmObject {

    @PrimaryKey
    public int eventID;

    @Required
    public String title;

    /*@Required
    public Date date;*/

    public String image;

}
