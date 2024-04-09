package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;

import java.io.Serializable;
import java.util.List;

public class Auction implements Serializable {

    private Integer idAuction;
    private AuctionType type;
    private List<Image> images;
    private String title;
    private String description;
    private Wear wear;
    private AuctionStatus status;
    private Category category;
    private User owner;

    public Auction() {}

    public Auction(User owner, String title, String description, Wear wear, Category category, AuctionStatus status, AuctionType auctionType) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.wear = wear;
        this.category = category;
        this.status = status;
        this.type = auctionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(Integer idAuction) {
        this.idAuction = idAuction;
    }

    public AuctionType getType() {
        return type;
    }

    public void setType(AuctionType type) {
        this.type = type;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return idAuction;
    }
}
