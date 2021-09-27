package com.example.oblig1.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemForSale implements Serializable {
    private String title;
    private String description;
    private String price;
    private String id;
    private ArrayList<String> photos;

    public ItemForSale(String title, String description, String price, ArrayList<String> photos, String id) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.photos = photos;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return "Description:\n" + description;
    }

    public String getPrice() {
        return "Price: " + price + "kr";
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public String getId() {
        return id;
    }

    public String getFirstPhotoURL() {
        return "http://192.168.1.161:8080/api/fant/photo/" + photos.get(0);
    }
}
