package com.example.oblig1.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemForSale {
    private String title;
    private String description;
    private String price;
    private JSONArray photos;

    public ItemForSale(String title, String description, String price, JSONArray photos) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.photos = photos;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getFirstPhotoURL() throws JSONException {
        JSONObject jsonObject = (JSONObject) photos.get(0);
        String subpath = jsonObject.getString("subpath");
        return "http://192.168.1.161:8080/api/fant/photo/" + subpath;
    }
}
