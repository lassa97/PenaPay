package com.lassa97.penapay.Objects;

import java.util.Date;
import java.util.List;

public class Event {

    private String title;
    private Date date;
    private String image;
    private List<User> guests;

    public Event (String title, Date date) {
        this.title = title;
        this.date = date;
    }

    /*public Event (String title, Date date, List<Profile> guests) {
        this.title = title;
        this.date = date;
        this.guests = guests;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
    }

    public List<User> getGuests() {
        return guests;
    }

    public void setGuests (List<User> guests) {
        this.guests = guests;
    }

    public String getImage() {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }
}
