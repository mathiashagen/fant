package com.example.oblig1.data.model;

public class ItemForSale {
    private String title;
    private String description;
    private String price;

    public ItemForSale(String title, String description, String price) {
        this.title = title;
        this.description = description;
        this.price = price;;
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
}
