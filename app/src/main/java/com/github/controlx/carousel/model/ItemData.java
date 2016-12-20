package com.github.controlx.carousel.model;

/**
 * Created by Abhishek on 12/2/2016.
 */
public class ItemData {
    String url;
    String name;
    String price;

    public ItemData(String url, String name, String price) {
        this.url = url;
        this.name = name;
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
