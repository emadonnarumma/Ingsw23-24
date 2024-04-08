package com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder;

import android.net.Uri;

import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;

import java.util.ArrayList;

public class  AuctionHolder {
    private String title;
    private ArrayList<Uri> images;
    private String description;
    private Wear wear;
    private Category category;

    public AuctionHolder() {}

    public AuctionHolder(String title, ArrayList<Uri> images, String description, Wear wear, Category category) {
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

    public ArrayList<Uri> getImages() {
        return images;
    }

    public void setImages(ArrayList<Uri> images) {
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
