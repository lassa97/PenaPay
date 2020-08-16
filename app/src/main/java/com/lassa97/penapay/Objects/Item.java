package com.lassa97.penapay.Objects;

public class Item {

    private String name, type;
    private float price;
    private String image;

    public Item (String name, String type, float price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice (float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }
}
