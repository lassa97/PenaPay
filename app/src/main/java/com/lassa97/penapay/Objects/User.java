package com.lassa97.penapay.Objects;

public class User {

    private String name, surname;
    private String image;

    public User (String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname (String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }
}
