package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;

public class Auction {
    private String title;
    private byte[] images;
    private String description;
    private Wear wear;
    private Category category;


    public Auction() {}


    public Auction(String title, byte[] images, String description, Wear wear, Category category) {
        this.title = title;
        this.images = images;
        this.description = description;
        this.wear = wear;
        this.category = category;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public byte[] getImages() {
        return images;
    }


    public void setImages(byte[] images) {
        this.images = images;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Wear getWear() {
        return wear;
    }


    public void setWear(Wear wear) {
        this.wear = wear;
    }


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }
}
